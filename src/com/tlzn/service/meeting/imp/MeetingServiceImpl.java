package com.tlzn.service.meeting.imp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.meeting.Tmeeting;
import com.tlzn.model.sys.Tsecondary;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.meeting.Meeting;
import com.tlzn.pageModel.sys.Seco;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.meeting.MeetingManServiceI;
import com.tlzn.service.meeting.MeetingServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Generator;
import com.tlzn.util.base.ResourceUtil;
import com.tlzn.util.base.Util;

@Service("meetingService")
public class MeetingServiceImpl extends BaseServiceImpl implements MeetingServiceI {

	private BaseDaoI<Tmeeting> meetingDAO;
	private MeetingManServiceI meetingManService;
	
	public BaseDaoI<Tmeeting> getMeetingDAO() {
		return meetingDAO;
	}
	@Autowired
	public void setMeetingDAO(BaseDaoI<Tmeeting> meetingDAO) {
		this.meetingDAO = meetingDAO;
	}
	
	public MeetingManServiceI getMeetingManService() {
		return meetingManService;
	}
	@Autowired
	public void setMeetingManService(MeetingManServiceI meetingManService) {
		this.meetingManService = meetingManService;
	}
	
	/**
	 * 
		 * 会议数据列表
		 * @param  meeting
		 * @throws 	Exception
		 * @return 	DataGrid
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(Meeting meeting) throws Exception {
		String hql=" from Tmeeting t where 1=1";
		hql=this.addWhere(meeting, hql);
		hql+=" order by t.createTime desc";
		DataGrid res=new DataGrid();
		res.setRows(this.getMeetingList(meetingDAO.find(hql,meeting.getPage(), meeting.getRows())));
		res.setTotal(meetingDAO.count("select Count(*) "+ hql));
		return res;
	}
	/**
	 * 
		 * 会议查询
		 * @param  meeting
		 * @throws 	Exception
		 * @return 	DataGrid
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid queryDatagrid(Meeting meeting) throws Exception {
		String hql=" from Tmeeting t where t.status!='"+Constants.MEETING_TYPE_STATUS_WFB+"'";
		hql=this.addWhere(meeting, hql);
		hql+=" order by t.createTime desc";
		DataGrid res=new DataGrid();
		res.setRows(this.getMeetingList(meetingDAO.find(hql,meeting.getPage(), meeting.getRows())));
		res.setTotal(meetingDAO.count("select Count(*) "+ hql));
		return res;
	}
	/**
	 * 
		 *  参会情况数据列表
		 * @param  meeting
		 * @throws 	Exception
		 * @return 	DataGrid
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid situationDatagrid(Meeting meeting) throws Exception {
		String hql=" from Tmeeting t where t.status!='"+Constants.MEETING_TYPE_STATUS_WFB+"'";
		hql=this.addWhere(meeting, hql);
		hql+=" order by t.createTime desc";
		DataGrid res=new DataGrid();
		res.setRows(this.getMeetingList(meetingDAO.find(hql,meeting.getPage(), meeting.getRows())));
		res.setTotal(meetingDAO.count("select Count(*) "+ hql));
		return res;
	}
	/**
	 * 
		 * 根据会议主键查询会议
		 * @param  meeting
		 * @throws 	Exception
		 * @return 	Meeting
	 */
	public Meeting findMeeting(Meeting meeting)throws Exception{
		Meeting meet=new Meeting();
		if (Util.getInstance().isEmpty(meeting.getMeetId())) {
			return null;
		}
		Tmeeting tm=meetingDAO.get(Tmeeting.class,meeting.getMeetId());
		BeanUtils.copyProperties(tm,meet);
		meet.setMeetTypeName(this.findDicName(Constants.MEETING_TYPE, tm.getMeetType()));
//		meet.setDepName(this.findNameByDepid(tm.getDepid()));
		meet.setDepName(tm.getDepid());
		meet.setPubDepdName(tm.getPubDepdid());
		meet.setStatusName(this.findDicName(Constants.MEETING_TYPE_STATUS, tm.getStatus()));
		meet.setAttendStatusName(this.findDicName(Constants.ACT_TYPE_ASTATUS, tm.getAttendStatus()));
		meet.setSecondaryId(tm.getTsecondary().getSecondaryId());
		meet.setSecondaryName(tm.getTsecondary().getSecondayName());
		meet.setSecondaryYear(tm.getTsecondary().getYear());
		meet.setTotal(meetingManService.count(tm.getMeetId(),""));
		meet.setAttendance(meetingManService.count(tm.getMeetId(),Constants.ACT_TYPE_ASTATUS_CX));
		return meet;
	}
	
	
	/**
	 * 数据转换
	 */
	private List<Meeting> getMeetingList(List<Tmeeting> datalist) throws Exception {
		List<Meeting> rowlist=new ArrayList<Meeting>();
		if(datalist!=null){
			for(Tmeeting t:datalist){
				Meeting meeting=new Meeting();
				BeanUtils.copyProperties(t, meeting);
				meeting.setMeetTypeName(this.findDicName(Constants.MEETING_TYPE, meeting.getMeetType()));
				meeting.setDepName(this.findNameByDepid(meeting.getDepid()));
				meeting.setPubDepdName(this.findNameByDepid(meeting.getPubDepdid()));
				meeting.setStatusName(this.findDicName(Constants.MEETING_TYPE_STATUS, meeting.getStatus()));
				meeting.setAttendStatusName(this.findDicName(Constants.ACT_TYPE_ASTATUS, meeting.getAttendStatus()));
				meeting.setSecondaryId(t.getTsecondary().getSecondaryId());
				meeting.setSecondaryName(t.getTsecondary().getSecondayName());
				meeting.setSecondaryYear(t.getTsecondary().getYear());
				meeting.setContent("");
				rowlist.add(meeting);
			}
		}
		return rowlist;
	}
	/**
	 * 组合hql语句
	 */
	private String addWhere(Meeting meeting, String hql) throws Exception{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		if (Util.getInstance().isEmpty(meeting.getSecondaryId())) {
			String secondaryId=((Seco) ActionContext.getContext().getSession().get(ResourceUtil.getSessionSeco())).getSecondaryId();
			hql += " and t.tsecondary.secondaryId ='"+secondaryId+"'";
		}else{
			String s="";
			for(String t : meeting.getSecondaryId().split(",")){
				s += " t.tsecondary.secondaryId like '%" + t + "%' or ";
			}
			s="(" + s.substring(0,s.length()-3) + ")";
			hql += " and " + s;
		}
		//根据用户组，查询对应组用户的数据
		String userGroup = meeting.getUserGroup(); String code = meeting.getCode();
		if(userGroup != null && !"".equals(userGroup) && code != null && !"".equals(code)){
			switch (userGroup) {
			case Constants.DIC_TYPE_YHZB_WY:
				hql += " and exists (from TmeetingMan b where t.meetId=b.tmeet.meetId and b.tcomm.groupCode ='"+userGroup+"' and b.tcomm.code ='"+code+"') ";
				break;
			default:
				break;
			}
		}
		//承办单位名称 
		if(!Util.getInstance().isEmpty(meeting.getDepid())){
			hql+=" and t.depid like '%"+meeting.getDepid()+"%'";
		}
		if(!Util.getInstance().isEmpty(meeting.getMeetName())){
			hql+=" and t.meetName like '%"+meeting.getMeetName()+"%'";
		}
		if(!Util.getInstance().isEmpty(meeting.getAddress())){
			hql+=" and t.address like '%"+meeting.getAddress()+"%'";
		}
		if(!Util.getInstance().isEmpty(meeting.getMeetType())){
			hql+=" and t.meetType = '"+meeting.getMeetType()+"'";
		}
		if(!Util.getInstance().isEmpty(meeting.getStatus())){
			hql+=" and t.status = '"+meeting.getStatus()+"'";
		}
		if(!Util.getInstance().isEmpty(meeting.getBeginTime())){
			hql+=" and t.beginTime >= to_date('"+sdf.format(meeting.getBeginTime())+"','YYYY-MM-DD')";
		}
		if(!Util.getInstance().isEmpty(meeting.getEndTime())){
			hql+=" and t.endTime <= to_date('"+sdf.format(meeting.getEndTime())+"','YYYY-MM-DD')";
		}
//		if(!Util.getInstance().isEmpty(meeting.getStartDate())){
//			hql+=" and t.pubDate >= to_date('"+meeting.getStartDate()+"','YYYY-MM-DD')";
//		}
//		if(!Util.getInstance().isEmpty(meeting.getEndDate())){
//			hql+=" and t.pubDate <= to_date('"+meeting.getEndDate()+"','YYYY-MM-DD')";
//		}
		return hql;
	}
	/**
	 * 
		 * 添加会议
		 * @param  meeting
		 * @throws 	Exception
		 * @return 	void
	 */
	public void add(Meeting meeting) throws Exception{
		Tmeeting tmeeting=new Tmeeting();
		BeanUtils.copyProperties(meeting,tmeeting);
		tmeeting.setMeetId(Generator.getInstance().getMeetingNO());
		tmeeting.setCreateTime(new Date());
		Tsecondary ts=new Tsecondary();
		Seco seco=(Seco)ActionContext.getContext().getSession().get(ResourceUtil.getSessionSeco());
		ts.setSecondaryId(seco.getSecondaryId());
		tmeeting.setTsecondary(ts);
		if(Constants.MEETING_TYPE_STATUS_YFB.equals(meeting.getStatus())){
			tmeeting.setPubDate(new Date());
			tmeeting.setPubDepdid(Constants.MEETING_TYPE_PUB_DEPT);
		}
		
		meetingDAO.save(tmeeting);
		//会议人员保存
		meeting.setMeetId(tmeeting.getMeetId());
		meetingManService.addMans(meeting);
		
		//生成日志
		String logInfo="";
		if(Constants.MEETING_TYPE_STATUS_WFB.equals(meeting.getStatus())){
			logInfo="[会议管理]保存会议";
		}else {
			logInfo="[会议管理]保存发布会议";
		}
		this.saveLog(Constants.LOG_TYPE_CODE_MEETING, Constants.LOG_TYPE_OPERTYPE_ADD, tmeeting.getMeetId(), logInfo);
	}
	/**
	 * 
		 * 更新会议
		 * @param  meeting
		 * @throws 	Exception
		 * @return 	void
	 */
	public void update(Meeting meeting) throws Exception{
		
		
		Tmeeting tmeeting=meetingDAO.get(Tmeeting.class,meeting.getMeetId());
		tmeeting.setMeetName(meeting.getMeetName());
		tmeeting.setShortName(meeting.getShortName());
		tmeeting.setMeetType(meeting.getMeetType());
		tmeeting.setBeginTime(meeting.getBeginTime());
		tmeeting.setEndTime(meeting.getEndTime());
		tmeeting.setContent(meeting.getContent());
		tmeeting.setDepid(meeting.getDepid());
		tmeeting.setAddress(meeting.getAddress());
		tmeeting.setAttendStatus(meeting.getAttendStatus());
		tmeeting.setStatus(meeting.getStatus());
		if(Constants.MEETING_TYPE_STATUS_YFB.equals(meeting.getStatus())){
			tmeeting.setPubDate(new Date());
			tmeeting.setPubDepdid(tmeeting.getDepid());
		}
		
		meetingDAO.saveOrUpdate(tmeeting);
		//会议人员保存
		meeting.setMeetId(tmeeting.getMeetId());
		meetingManService.addMans(meeting);
		
		//生成日志
		String logInfo="";
		if(Constants.MEETING_TYPE_STATUS_WFB.equals(meeting.getStatus())){
			logInfo="[会议管理]更新保存会议";
		}else {
			logInfo="[会议管理]更新发布会议";
		}
		this.saveLog(Constants.LOG_TYPE_CODE_MEETING, Constants.LOG_TYPE_OPERTYPE_EDIT, tmeeting.getMeetId(), logInfo);
	}
	/**
		 * 删除会议
		 * @param  meeting
		 * @throws 	Exception
		 * @return 	void
	 */
	public void remove(Meeting meeting) throws Exception{
		if (meeting.getIds() != null) {
			for (String id : meeting.getIds().split(",")) {
				Tmeeting t = meetingDAO.get(Tmeeting.class, id.trim());
				if (t != null) {
					meetingDAO.delete(t);
				}
			}
			//生成日志
			this.saveLog(Constants.LOG_TYPE_CODE_MEETING, Constants.LOG_TYPE_OPERTYPE_DEL, meeting.getIds(),"[会议管理]删除会议");
		}
	}
	/**
	 * 
		 * 发布/取消发布/办结
		 * @param  meeting
		 * @throws 	Exception
		 * @return 	void
	 */
	public void change(Meeting meeting) throws Exception{
		if (meeting.getIds() != null) {
			for (String id : meeting.getIds().split(",")) {
				Tmeeting t = meetingDAO.get(Tmeeting.class, id.trim());
				if (t != null) {
					//发布，操作
					if(Constants.MEETING_TYPE_STATUS_YFB.equals(meeting.getStatus())) {
						t.setPubDate(new Date());
						t.setPubDepdid(Constants.MEETING_TYPE_PUB_DEPT);
					//取消发布
					} else if (Constants.MEETING_TYPE_STATUS_WFB.equals(meeting.getStatus())){
						t.setPubDate(null);
						t.setPubDepdid(null);
					}
					t.setStatus(meeting.getStatus());
					meetingDAO.saveOrUpdate(t);
				}
			}
			//生成日志
			String logInfo="";
			if(Constants.MEETING_TYPE_STATUS_WFB.equals(meeting.getStatus())){
				logInfo="[会议管理]取消发布会议";
			}else if(Constants.MEETING_TYPE_STATUS_BJ.equals(meeting.getStatus())) {
				logInfo="[会议管理]办结会议";
			}else{
				logInfo="[会议管理]发布会议";
			}
			this.saveLog(Constants.LOG_TYPE_CODE_MEETING, Constants.LOG_TYPE_OPERTYPE_EDIT, meeting.getIds(), logInfo);	
		}
	}
//	gds add
	/**
	 *获取当前届次未开的所有会议
	 */
	public DataGrid getCurSecMeeting(Meeting meeting)throws Exception {
		String secondaryId=((Seco) ActionContext.getContext().getSession().get(ResourceUtil.getSessionSeco())).getSecondaryId();
		DataGrid j = new DataGrid();
//		String hql ="from Tmeeting t where t.tsecondary.secondaryId like ? ";
		String hql ="from Tmeeting t where t.tsecondary.secondaryId ='"+secondaryId+"' and t.status='"+Constants.MEETING_TYPE_STATUS_YFB+"' and t.beginTime >= ?";
		List<Object> values = new ArrayList<Object>();
		values.add(new Date());
		if (meeting.getMeetName() != null && !"".equals(meeting.getMeetName())) {
			hql += " and t.meetName like ?";
			values.add("%" + meeting.getMeetName() + "%");
		}
		if (meeting.getShortName() != null && !"".equals(meeting.getShortName())) {
			hql += " and t.shortName like ?";
			values.add("%" + meeting.getShortName() + "%");
		}
		if (meeting.getMeetType() != null && !"".equals(meeting.getMeetType()) && !"0".equals(meeting.getMeetType())) {
			hql += " and t.meetType = ?";
			values.add(meeting.getMeetType());;
		}
		
		hql+=" order by t.pubDate desc";
		j.setTotal(meetingDAO.count("select count(*) "+hql,values));
		j.setRows(this.getMeetingList(meetingDAO.find(hql, values,meeting.getPage(),meeting.getRows())));
		return j;
	}
	/**
	 *会议管理首页统计
	 */
	public Map<String, Integer> meetingCount(HttpSession httpSession) {
		return null;
	}
//	gds add
	public DataGrid zxdatagrid(Meeting meeting) throws Exception {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String hql=" from Tmeeting t where t.status = '"+Constants.MEETING_TYPE_STATUS_YFB+"' and t.beginTime >= to_date('"+sdf.format(new Date())+"','YYYY-MM-DD')";
		hql=this.addWhere(meeting, hql);
		hql+=" order by t.createTime desc";
		DataGrid res=new DataGrid();
		meeting.setRows(7);
		res.setRows(this.getMeetingList(meetingDAO.find(hql,meeting.getPage(), meeting.getRows())));
		res.setTotal(meetingDAO.count("select Count(*) "+ hql));
		return res;
	}
	public DataGrid wfbdatagrid(Meeting meeting) throws Exception {
		String hql=" from Tmeeting t where t.status = '"+Constants.MEETING_TYPE_STATUS_WFB+"'";
		hql=this.addWhere(meeting, hql);
		hql+=" order by t.createTime desc";
		DataGrid res=new DataGrid();
		meeting.setRows(7);
		res.setRows(this.getMeetingList(meetingDAO.find(hql,meeting.getPage(), meeting.getRows())));
		res.setTotal(meetingDAO.count("select Count(*) "+ hql));
		return res;
	}
	public DataGrid wbjdatagrid(Meeting meeting) throws Exception {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String hql=" from Tmeeting t where t.status = '"+Constants.MEETING_TYPE_STATUS_YFB+"' and t.endTime < to_date('"+sdf.format(new Date())+"','YYYY-MM-DD')";
		hql=this.addWhere(meeting, hql);
		hql+=" order by t.createTime desc";
		DataGrid res=new DataGrid();
		meeting.setRows(7);
		res.setRows(this.getMeetingList(meetingDAO.find(hql,meeting.getPage(), meeting.getRows())));
		res.setTotal(meetingDAO.count("select Count(*) "+ hql));
		return res;
	}
	
}
