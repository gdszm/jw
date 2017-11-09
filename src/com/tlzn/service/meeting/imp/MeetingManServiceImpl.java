package com.tlzn.service.meeting.imp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.Tcommitteeman;
import com.tlzn.model.meeting.Tmeeting;
import com.tlzn.model.meeting.TmeetingMan;
import com.tlzn.model.poll.Tpoll;
import com.tlzn.model.poll.TpollScore;
import com.tlzn.model.speech.Tspeech;
import com.tlzn.model.sys.Tdic;
import com.tlzn.model.sys.Trules;
import com.tlzn.model.sys.Tsecondary;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.meeting.Meeting;
import com.tlzn.pageModel.meeting.MeetingMan;
import com.tlzn.pageModel.speech.Speech;
import com.tlzn.pageModel.sys.Comm;
import com.tlzn.pageModel.sys.Dic;
import com.tlzn.pageModel.sys.Seco;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.committee.CommitteeServiceI;
import com.tlzn.service.meeting.MeetingManServiceI;
import com.tlzn.service.sys.DicServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Generator;
import com.tlzn.util.base.ResourceUtil;
import com.tlzn.util.base.Util;

@Service("meetingManService")
public class MeetingManServiceImpl extends BaseServiceImpl implements MeetingManServiceI{
	
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	private BaseDaoI<TmeetingMan> meetingManDAO;
	private CommitteeServiceI committeeService;
	
	public BaseDaoI<TmeetingMan> getMeetingManDAO() {
		return meetingManDAO;
	}
	@Autowired
	public void setMeetingManDAO(BaseDaoI<TmeetingMan> meetingManDAO) {
		this.meetingManDAO = meetingManDAO;
	}
	
	public CommitteeServiceI getCommitteeService() {
		return committeeService;
	}
	@Autowired
	public void setCommitteeService(CommitteeServiceI committeeService) {
		this.committeeService = committeeService;
	}
	
	/**
	 * 
		 * 参会人员批量添加
		 * @param  meeting
		 * @throws 	Exception
		 * @return 	void
	 */
	public void addMans(Meeting meeting) throws Exception {
		
		String hql=" from TmeetingMan t1 where t1.tmeet.meetId='"+meeting.getMeetId()+"'";
		List<TmeetingMan> manList=meetingManDAO.find(hql);
		if(!Util.getInstance().isEmpty(meeting.getIds())){
			for(String id:meeting.getIds().split(",")){
				int flg=0;
				for(TmeetingMan t:manList){
					if(id.equals(t.getTcomm().getCode())){
						manList.remove(t);
						flg=1;
						break;
					}
				}
				if(flg!=1){
					TmeetingMan tmm=new TmeetingMan();
					tmm.setId(Generator.getInstance().getMeetingManNO());
					Tmeeting tmeeting=new Tmeeting();
					tmeeting.setMeetId(meeting.getMeetId());
					tmm.setTmeet(tmeeting);
					Tcommitteeman tcomm=new Tcommitteeman();
					tcomm.setCode(id);
					tmm.setTcomm(tcomm);
					tmm.setCreateTime(new Date());
					tmm.setStatus(meeting.getAttendStatus());
					
					meetingManDAO.save(tmm);
				}
				
			}
			
			if(manList!=null&&manList.size()>0){
				for(TmeetingMan t:manList){
					meetingManDAO.delete(t);
				}
			}
		}
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_MEETING, Constants.LOG_TYPE_OPERTYPE_ADD, meeting.getMeetId(), "[会议管理]参会人员保存");
		
		
	}
	/**
	 * 
		 * 参会人员调整保存
		 * @param  meetingMan
		 * @throws 	Exception
		 * @return 	void
	 */
	public void memberSave(MeetingMan meetingMan) throws Exception {
		
		String hql=" from TmeetingMan t1 where t1.tmeet.meetId='"+meetingMan.getMeetId()+"'";
		List<TmeetingMan> manList=meetingManDAO.find(hql);
		if(!Util.getInstance().isEmpty(meetingMan.getIds())){
			for(String id:meetingMan.getIds().split(",")){
				
				int flg=0;
				for(TmeetingMan t:manList){
					if(id.equals(t.getTcomm().getCode())){
						manList.remove(t);
						flg=1;
						break;
					}
				}
				if(flg!=1){
					TmeetingMan tmm=new TmeetingMan();
					tmm.setId(Generator.getInstance().getMeetingManNO());
					Tmeeting tmeeting=new Tmeeting();
					tmeeting.setMeetId(meetingMan.getMeetId());
					tmm.setTmeet(tmeeting);
					Tcommitteeman tcomm=new Tcommitteeman();
					tcomm.setCode(id);
					tmm.setTcomm(tcomm);
					tmm.setCreateTime(new Date());
					tmm.setStatus(Constants.ACT_TYPE_ASTATUS_QX);
					
					meetingManDAO.save(tmm);
				}
				
			}
			
			if(manList!=null&&manList.size()>0){
				for(TmeetingMan t:manList){
					meetingManDAO.delete(t);
				}
			}
		}
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_MEETING, Constants.LOG_TYPE_OPERTYPE_EDIT, meetingMan.getMeetId(), "[会议管理]参会人员调整");
		
		
	}
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(MeetingMan meetingMan) throws Exception {
		String hql=" from TmeetingMan t where 1=1";
		hql=this.addWhere(meetingMan, hql);
		hql+=" order by t.tmeet.createTime desc,t.tcomm.code";
		DataGrid res=new DataGrid();
		res.setRows(this.getMeetingManList(meetingManDAO.find(hql,meetingMan.getPage(), meetingMan.getRows())));
		res.setTotal(meetingManDAO.count("select Count(*) "+ hql));
		return res;
	}
	/**
	 * 参会情况查询
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid queryDatagrid(MeetingMan meetingMan) throws Exception {
		String hql=" from TmeetingMan t where t.tmeet.status!='"+Constants.MEETING_TYPE_STATUS_WFB+"' and t.tmeet.endTime <= to_date('"+sdf.format(new Date())+"','YYYY-MM-DD')";
		hql=this.addWhere(meetingMan, hql);
		hql+=" order by t.tmeet.createTime desc,t.tcomm.code";
		DataGrid res=new DataGrid();
		res.setRows(this.getMeetingManList(meetingManDAO.find(hql,meetingMan.getPage(), meetingMan.getRows())));
		res.setTotal(meetingManDAO.count("select Count(*) "+ hql));
		return res;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagridOwn(MeetingMan meetingMan) throws Exception {
		Util util=new Util();
		String code = util.isEmpty(meetingMan.getCommCode())?"":meetingMan.getCommCode().trim();
		String hql=" from TmeetingMan t where 1=1 and t.tcomm.code='"+ code + "' ";
		hql=this.addWhere(meetingMan, hql);
		hql+=" order by t.tmeet.createTime desc,t.tcomm.code";
		DataGrid res=new DataGrid();
		res.setRows(this.getMeetingManList(meetingManDAO.find(hql,meetingMan.getPage(), meetingMan.getRows())));
		res.setTotal(meetingManDAO.count("select Count(*) "+ hql));
		return res;
	}
	/**
	 * 数据转换
	 */
	private List<MeetingMan> getMeetingManList(List<TmeetingMan> datalist) throws Exception {
		List<MeetingMan> rowlist=new ArrayList<MeetingMan>();
		if(datalist!=null){
			for(TmeetingMan t:datalist){
				MeetingMan mm=new MeetingMan();
				BeanUtils.copyProperties(t, mm);
				BeanUtils.copyProperties(t.getTcomm(),mm);
				BeanUtils.copyProperties(t.getTmeet(), mm);
				//出席情况
				mm.setStatus(t.getStatus());
				mm.setStatusName(this.findDicName(Constants.PRESENT_TYPE_STATUS, t.getStatus()));
				mm.setLeaveTypeName(findDicName(Constants.CODE_TYPE_LEAVE,t.getLeaveType()));
				mm.setCommCode(t.getTcomm().getCode());
				mm.setSexName(findDicName(Constants.CODE_TYPE_ID_SEX, t.getTcomm().getSex()));
				mm.setCircleName(findDicName(Constants.CODE_TYPE_ID_CIRCLE, t.getTcomm().getCircleCode()));
				mm.setCommitteeName(findDicName(Constants.CODE_TYPE_ID_COMMITTEE,t.getTcomm().getCommittee()));
				mm.setMeetTypeName(this.findDicName(Constants.MEETING_TYPE, t.getTmeet().getMeetType()));
				mm.setDepName(this.findNameByDepid(t.getTmeet().getDepid()));
				mm.setPubDepdName(this.findNameByDepid(t.getTmeet().getPubDepdid()));
				mm.setTotal(this.count(t.getTmeet().getMeetId(),""));
				mm.setAttendance(this.count(t.getTmeet().getMeetId(),Constants.PRESENT_TYPE_STATUS_CX));
				
				mm.setSecondaryId(t.getTmeet().getTsecondary().getSecondaryId());
				mm.setSecondaryName(t.getTmeet().getTsecondary().getSecondayName());
				mm.setSecondaryYear(t.getTmeet().getTsecondary().getYear());
				
				mm.setLeaveReason("");
				rowlist.add(mm);
			}
		}
		return rowlist;
	}
	
	/**
	 * 
	 * 委员履职详情，参会情况列表查询
	 * 
	 * @param speech,userCode,secondaryCode
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagridFulfil(MeetingMan meetingMan,String userCode, String secondaryCode) 
			throws Exception {
		String hql=" from TmeetingMan t1 where t1.tmeet.tsecondary.secondaryId='"+secondaryCode
			+"' and t1.tcomm.code='"+userCode
			+"' and t1.status='"+Constants.PRESENT_TYPE_STATUS_CX+"'"; //出席
		hql+=" order by t1.createTime desc";
		List<TmeetingMan> meetingManList = meetingManDAO.find(hql, meetingMan.getPage(), meetingMan.getRows());
		DataGrid j = new DataGrid();
		j.setRows(getMeetingManList(meetingManList));
		
		hql = "select count(*) "+hql;
		j.setTotal(meetingManDAO.count(hql));
		return j;
	}
	
	/**
	 * 会义人数统计
	 * @param  meetId ,status
	 * @throws 	Exception
	 * @return 	String 
	 */
	public String count(String meetId,String status)throws Exception{
		String res="";
		String hql=" select Count(*) from TmeetingMan t where t.tmeet.meetId='"+meetId+"'";
		if(!Util.getInstance().isEmpty(status)){
			hql+=" and t.status='"+status+"'";
		}
		res=meetingManDAO.count(hql).toString();
		return res;
	}

	/**
	 * 组合hql语句
	 */
	private String addWhere(MeetingMan meetingMan, String hql) throws Exception{
		
		if (Util.getInstance().isEmpty(meetingMan.getSecondaryId())) {
			String secondaryId=((Seco) ActionContext.getContext().getSession().get(ResourceUtil.getSessionSeco())).getSecondaryId();
			hql += " and t.tmeet.tsecondary.secondaryId ='"+secondaryId+"'";
		}else{
			String s="";
			for(String t : meetingMan.getSecondaryId().split(",")){
				s += " t.tmeet.tsecondary.secondaryId like '%" + t + "%' or ";
			}
			s="(" + s.substring(0,s.length()-3) + ")";
			hql += " and " + s;
		}
		if (meetingMan.getMeetId() != null && !"".equals(meetingMan.getMeetId())) {
			hql += " and t.tmeet.meetId ='"+meetingMan.getMeetId()+"'";
		}
		//委员ID
//		if (meetingMan.getCommCode() != null && !"".equals(meetingMan.getCommCode())) {
//			hql += " and t.tcomm.code ='"+meetingMan.getCommCode()+"'";
//		}
		//委员名称
		if(!Util.getInstance().isEmpty(meetingMan.getName())){
			hql+=" and t.tcomm.name like '%"+meetingMan.getName()+"%'";
		}
		if(!Util.getInstance().isEmpty(meetingMan.getMeetName())){
			hql+=" and t.tmeet.meetName like '%"+meetingMan.getMeetName()+"%'";
		}
		if(!Util.getInstance().isEmpty(meetingMan.getAddress())){
			hql+=" and t.tmeet.address like '%"+meetingMan.getAddress()+"%'";
		}
		if(!Util.getInstance().isEmpty(meetingMan.getMeetType())){
			hql+=" and t.tmeet.meetType = '"+meetingMan.getMeetType()+"'";
		}
		if(!Util.getInstance().isEmpty(meetingMan.getStatus())){
			hql+=" and t.status = '"+meetingMan.getStatus()+"'";
		}
		//会议状态
		if(!Util.getInstance().isEmpty(meetingMan.getMeetingStatus())){
			hql+=" and t.tmeet.status = '"+meetingMan.getMeetingStatus()+"'";
		}
		//承办单位
		if (!Util.getInstance().isEmpty(meetingMan.getDepid())) {
			hql+=" and t.tmeet.depid like '%"+meetingMan.getDepid()+"%'";
		}
		if(!Util.getInstance().isEmpty(meetingMan.getBeginTime())){
			hql+=" and t.tmeet.beginTime >= to_date('"+sdf.format(meetingMan.getBeginTime())+"','YYYY-MM-DD')";
		}
		if(!Util.getInstance().isEmpty(meetingMan.getEndTime())){
			hql+=" and t.tmeet.endTime <= to_date('"+sdf.format(meetingMan.getEndTime())+"','YYYY-MM-DD')";
		}
//		if(!Util.getInstance().isEmpty(meetingMan.getStartDate())){
//			hql+=" and t.tmeet.pubDate >= to_date('"+meetingMan.getStartDate()+"','YYYY-MM-DD')";
//		}
//		if(!Util.getInstance().isEmpty(meetingMan.getEndDate())){
//			hql+=" and t.tmeet.pubDate <= to_date('"+meetingMan.getEndDate()+"','YYYY-MM-DD')";
//		}
		return hql;
	}
	
	
	
	/**
	 * 根据id获取人员请假信息
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public MeetingMan getLeave(String id) throws Exception {
		TmeetingMan tmeetingMan =meetingManDAO.get(TmeetingMan.class, id);
		MeetingMan mm = new MeetingMan();
		BeanUtils.copyProperties(tmeetingMan,mm);
		mm.setStatusName(this.findDicName(Constants.PRESENT_TYPE_STATUS, tmeetingMan.getStatus()));
		mm.setLeaveTypeName(findDicName(Constants.CODE_TYPE_LEAVE,tmeetingMan.getLeaveType()));
//		meetingMan.setLeaveType(tmeetingMan.getLeaveType());
		return mm;
	}
	/**
	 * 请假申请提交
	 */
	public void leave(MeetingMan meetingMan) throws Exception {
		TmeetingMan tmm = meetingManDAO.get(TmeetingMan.class, meetingMan.getId());
		tmm.setLeaveType(meetingMan.getLeaveType());
		tmm.setLeaveReason(meetingMan.getLeaveReason());
		tmm.setStatus(Constants.PRESENT_TYPE_STATUS_QJ);
		meetingManDAO.update(tmm);
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_MEETING, Constants.LOG_TYPE_OPERTYPE_SET, meetingMan.getMeetId()+"_"+meetingMan.getCommCode(), "[会议管理]请假申请");
	}
	/**
	 *请假驳回提交
	 */
	public void change(MeetingMan meetingMan) throws Exception {
		TmeetingMan tmm = meetingManDAO.get(TmeetingMan.class, meetingMan.getId());
		tmm.setStatus(meetingMan.getStatus());
		meetingManDAO.saveOrUpdate(tmm);
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_MEETING, Constants.LOG_TYPE_OPERTYPE_SET, meetingMan.getMeetId()+"_"+meetingMan.getCommCode(), "[会议管理]请假驳回");
	}
	/**
	 *出席情况调整提交
	 */
	public void setSave(MeetingMan meetingMan) throws Exception {
		if(!Util.getInstance().isEmpty(meetingMan.getIds())){
			for (String id : meetingMan.getIds().split(",")) {
				TmeetingMan tmm = meetingManDAO.get(TmeetingMan.class, id);
				System.out.println(tmm.getId()+"==="+meetingMan.getStatus());
				tmm.setStatus(meetingMan.getStatus());
				if(!Util.getInstance().isEmpty(meetingMan.getLeaveType())){
					tmm.setLeaveType(meetingMan.getLeaveType());
					tmm.setLeaveReason(meetingMan.getLeaveReason());
				}
				meetingManDAO.saveOrUpdate(tmm);
			}
		}
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_MEETING, Constants.LOG_TYPE_OPERTYPE_SET, meetingMan.getMeetId(), "[会议管理]出席情况设置");
	}
	/**
	 * 参会情况统计表头数据
	 */
	public String getColumns() throws Exception {
		String json="[{title:'性别',field:'sexName',align:'center',width:80,rowspan:2},{title:'联系电话',field:'telephone',align:'center',width:120,rowspan:2},"
		+"{title:'电子邮箱',field:'email',align:'center',width:120,rowspan:2},{title:'工作单位 职务',field:'companyName',align:'center',width:180,rowspan:2,formatter: function(value, row) {"
					+"  return row.companyName+'    '+row.job;"
				+"}},";
		
		String sql="select t.cid,t.ckey,t.cvalue from tdic t where t.ctype='"+Constants.MEETING_TYPE+"'";
		List<Object[]> typelist =(List<Object[]>) meetingManDAO.executeCountSql(sql);
		json+="{title:'参会情况',align:'center',colspan:"+typelist.size()+"}";
		json+="],";
		if(typelist.size()>0)json+="[";
		for (Iterator it = typelist.iterator(); it.hasNext();) {
			Object[] objs = (Object[]) it.next();
			json+="{title:'"+objs[1]+"',field:'type"+objs[2]+"',align:'center',width:100},";
			
		}
		json=json.substring(0,json.length()-1);
		json+="]";
		
		System.out.println("json=="+json);
		return json;
	}
	/**
	 * 参会情况统计
	 */
	public String situationCount(MeetingMan meetingMan) throws Exception {
		if(Util.getInstance().isEmpty(meetingMan.getSecondaryId())){
			meetingMan.setSecondaryId(((Seco) ActionContext.getContext().getSession().get(ResourceUtil.getSessionSeco())).getSecondaryId());
		}
		String json="{\"total\":0,\"rows\":[";	//datagrid数据
		String hql="select t.comm_code,m.meet_type,count(*) num from tmeeting_man t left join tmeeting m on m.meet_id=t.meet_id where t.status='"+Constants.ACT_TYPE_ASTATUS_CX+"'"
		         +" and m.secondary_id='"+meetingMan.getSecondaryId()+"'  and exists (select code from tcommitteeman c where c.code=t.comm_code ";
		if(!Util.getInstance().isEmpty(meetingMan.getName())){
			hql+=" and c.name like '%"+meetingMan.getName()+"%'";
		}
		if(!Util.getInstance().isEmpty(meetingMan.getTelephone())){
			hql+=" and c.telephone = '"+meetingMan.getTelephone()+"'";
		}
		if(!Util.getInstance().isEmpty(meetingMan.getSex())){
			hql+=" and c.sex = '"+meetingMan.getSex()+"'";
		}
		if(!Util.getInstance().isEmpty(meetingMan.getNation())){
			hql+=" and c.nation = '"+meetingMan.getNation()+"'";
		}
		if(!Util.getInstance().isEmpty(meetingMan.getCircleCode())){
			hql+=" and c.circle_code = '"+meetingMan.getCircleCode()+"'";
		}
		if(!Util.getInstance().isEmpty(meetingMan.getPartyCode())){
			hql+=" and c.party_code = '"+meetingMan.getPartyCode()+"'";
		}
		if(!Util.getInstance().isEmpty(meetingMan.getCommittee())){
			hql+=" and c.committee = '"+meetingMan.getCommittee()+"'";
		}
		hql+=") group by t.comm_code,m.meet_type";
		List<Object[]> countList=meetingManDAO.executeCountSql(hql);
		Comm comm=new Comm();
		comm.setSecondaryCode(meetingMan.getSecondaryId());
		comm.setName(meetingMan.getName());
		comm.setTelephone(meetingMan.getTelephone());
		comm.setSex(meetingMan.getSex());
		comm.setNation(meetingMan.getNation());
		comm.setCircleCode(meetingMan.getCircleCode());
		comm.setPartyCode(meetingMan.getPartyCode());
		comm.setCommittee(meetingMan.getCommittee());
		
		List<Comm> commList=committeeService.queryCommittee(comm);
		for (Iterator<Comm> it = commList.iterator(); it.hasNext();) {
			Comm c = it.next();
			json+="{\"commCode\":\""+c.getCode()+"\",\"name\":\"";
			json+=c.getName()+"\",";
			json+="\"circleCode\":\"";
			if(Util.getInstance().isEmpty(c.getCircleCode())){
				json+="\",";
				json+="\"circleName\":\"\",";
			}else{
				json+=c.getCircleCode()+"\",";
				json+="\"circleName\":\""+c.getCircleName()+"\",";
			}
			json+="\"committee\":\"";
			if(Util.getInstance().isEmpty(c.getCommittee())){
				json+="\",";
				json+="\"committeeName\":\"\",";
			}else{
				json+=c.getCommittee()+"\",";
				json+="\"committeeName\":\""+c.getCommitteeName()+"\",";
			}
			json+="\"sex\":\"";
			if(Util.getInstance().isEmpty(c.getSex())){
				json+="\",";
				json+="\"sexName\":\"\",";
			}else{
				json+=c.getSex()+"\",";
				json+="\"sexName\":\""+c.getSexName()+"\",";
			}
			json+="\"nation\":\"";
			if(Util.getInstance().isEmpty(c.getNation())){
				json+="\",";
				json+="\"nationName\":\"\",";
			}else{
				json+=c.getNation()+"\",";
				json+="\"nationName\":\""+c.getNationName()+"\",";
			}
			json+="\"partyCode\":\"";
			if(Util.getInstance().isEmpty(c.getPartyCode())){
				json+="\",";
				json+="\"partyName\":\"\",";
			}else{
				json+=c.getPartyCode()+"\",";
				json+="\"partyName\":\""+c.getPartyName()+"\",";
			}
			json+="\"job\":\"";
			if(Util.getInstance().isEmpty(c.getJob())){
				json+="\",";
			}else{
				json+=c.getJob()+"\",";
			}
			json+="\"telephone\":\"";
			if(Util.getInstance().isEmpty(c.getTelephone())){
				json+="\",";
			}else{
				json+=c.getTelephone()+"\",";
			}
			json+="\"email\":\"";
			if(Util.getInstance().isEmpty(c.getEmail())){
				json+="\",";
			}else{
				json+=c.getEmail()+"\",";
			}
			json+="\"companyName\":\"";
			if(Util.getInstance().isEmpty(c.getCompanyName())){
				json+="\",";
			}else{
				json+=c.getCompanyName()+"\",";
			}
			json+="\"comparyAddress\":\"";
			if(Util.getInstance().isEmpty(c.getComparyAddress())){
				json+="\",";
			}else{
				json+=c.getComparyAddress()+"\",";
			}
			for (Iterator<Object[]> itt = countList.iterator(); itt.hasNext();) {
				Object[] ts=itt.next();
				if(c.getCode().equals(ts[0])){
					json+="\"type"+ts[1]+"\":\"";
					json+=ts[2]+"\",";
				}
			}
			json=json.substring(0,json.length()-1);
			json+="},";
			
		}
		if(",".equals(json.substring(json.length()-1))){
			json=json.subSequence(0,json.length()-1)+"]}";
		}else{
			json+="]}";
		}
		System.out.println("json=="+json);
		return json;
	
	}
}
