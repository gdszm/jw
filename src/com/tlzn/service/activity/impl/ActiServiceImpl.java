package com.tlzn.service.activity.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;
import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.Tcommitteeman;
import com.tlzn.model.activity.Tactivity;
import com.tlzn.model.activity.Tactivitypeo;
import com.tlzn.model.meeting.TmeetingMan;
import com.tlzn.model.sys.Tdic;
import com.tlzn.pageModel.activity.Activity;
import com.tlzn.pageModel.activity.Activitypeo;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.SessionInfo;
import com.tlzn.pageModel.meeting.MeetingMan;
import com.tlzn.pageModel.sys.Comm;
import com.tlzn.pageModel.sys.Seco;
import com.tlzn.service.activity.ActiServiceI;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.committee.CommitteeServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Generator;
import com.tlzn.util.base.PropertyUtil;
import com.tlzn.util.base.ResourceUtil;
import com.tlzn.util.base.Util;


@Service("actiService")
public class ActiServiceImpl extends BaseServiceImpl implements ActiServiceI {
	
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	private BaseDaoI<Tactivity> activityDao;

	public BaseDaoI<Tactivity> getActivityDao() {
		return activityDao;
	}

	@Autowired
	public void setActivityDao(BaseDaoI<Tactivity> activityDao) {
		this.activityDao = activityDao;
	}
	
	private BaseDaoI<Tactivitypeo> activitypeoDao;

	public BaseDaoI<Tactivitypeo> getActivitypeoDao() {
		return activitypeoDao;
	}

	@Autowired
	public void setActivitypeoDao(BaseDaoI<Tactivitypeo> activitypeoDao) {
		this.activitypeoDao = activitypeoDao;
	}
	
	private CommitteeServiceI committeeService;
	

	public CommitteeServiceI getCommitteeService() {
		return committeeService;
	}
	@Autowired
	public void setCommitteeService(CommitteeServiceI committeeService) {
		this.committeeService = committeeService;
	}

	/**
	 * 获取默认数据（界次，部门，日期）
	 */
	public Object defaul(HttpSession httpSession) throws Exception {
		Activity p = new Activity();
		String secondaryId=((Seco) httpSession.getAttribute("sessionSeco")).getSecondaryId();
		String deptId=((SessionInfo) httpSession.getAttribute("sessionInfo")).getDeptId();
		p.setSecondaryId(secondaryId);
		p.setAgency(deptId);
		p.setReleaseageName(findNameByDepid(deptId));
		p.setReleasetime(new Date());
		return p;
	}
	/**
	 * 根据活动id获取活动信息
	 */
	public Activity getTactivity(String aid) throws Exception {
		Tactivity a=activityDao.get(Tactivity.class, aid);
		Activity p = new Activity();
		PropertyUtil.copyProperties(p,a);
		p.setStatusName(findDicName(Constants.ACT_TYPE_STATUS,a.getStatus()));
		p.setAspeciesName(findDicName(Constants.ACT_TYPE_ASPECIES,a.getAspecies()));
		p.setAgencyName(findNameByDepid(a.getAgency()));
		p.setReleaseageName(findNameByDepid(a.getReleaseage()));
		p.setSecondaryName(findNameBySecondaryid(a.getSecondaryId()));
		p.setYear(findYearById(a.getSecondaryId()));
		return p;
	}
	/**
	 * 根据活动id获取活动人员信息
	 */
	public Activitypeo getTactivitypeo(String aid) throws Exception {
		String hql = "from Tactivitypeo t where activity.aid='"+aid+"'";
		List<Tactivitypeo> peo=activitypeoDao.find(hql);
		String code = "";
		String name = "";
		if(peo.size()>0){
			for(int i=0;i<peo.size();i++){
				code+=peo.get(i).getComm().getCode()+",";
				name+=peo.get(i).getComm().getName()+",";
			}
			code=code.substring(0,code.length() - 1);
			name=name.substring(0,name.length() - 1);
		}
		Activitypeo p =new Activitypeo();
		p.setIds(code);
		p.setName(name);
		return p;
	}
	/**
	 * 根据auid获取人员请假信息
	 */
	public Activitypeo getLeave(String auid) throws Exception {
		Tactivitypeo p =activitypeoDao.get(Tactivitypeo.class, auid);
		Activitypeo a = new Activitypeo();
		BeanUtils.copyProperties(p,a);
		a.setLeaveType(p.getLeaveType());
		return a;
	}
	/**
	 * 活动信息查询
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(Activity activity, HttpSession httpSession) throws Exception {
		String hql = "from Tactivity t where 1=1 ";
		DataGrid j = new DataGrid();
		j.setRows(getActiFromTacti(find(hql,activity, httpSession)));
		j.setTotal(total(hql,activity, httpSession));
		return j;
	}
	/**
	 * 活动信息查询
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid queryDatagrid(Activity activity, HttpSession httpSession) throws Exception {
		String hql = "from Tactivity t where t.status!='"+Constants.ACT_TYPE_STATUS_WFB+"'";
		DataGrid j = new DataGrid();
		j.setRows(getActiFromTacti(find(hql,activity, httpSession)));
		j.setTotal(total(hql,activity, httpSession));
		return j;
	}
	
	private List<Activity> getActiFromTacti(List<Tactivity> Tactivitys) throws Exception {
		List<Activity> ads = new ArrayList<Activity>();
		if (Tactivitys != null && Tactivitys.size() > 0) {
			for (Tactivity t : Tactivitys) {
				Activity p = new Activity();
				PropertyUtil.copyProperties(p, t);
				p.setStatusName(findDicName(Constants.ACT_TYPE_STATUS,t.getStatus()));
				p.setAspeciesName(findDicName(Constants.ACT_TYPE_ASPECIES,t.getAspecies()));
				p.setAgencyName(findNameByDepid(t.getAgency()));
				p.setReleaseageName(findNameByDepid(t.getReleaseage()));
				p.setSecondaryName(findNameBySecondaryid(t.getSecondaryId()));
				p.setYear(findYearById(t.getSecondaryId()));
				ads.add(p);
			}
		}
		return ads;
	}

	private List<Tactivity> find(String hql,Activity activity, HttpSession httpSession) throws Exception {
		
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(activity, hql, values, httpSession);
		hql += "order by t.releasetime desc,t.createtime desc";
		return activityDao.find(hql, values, activity.getPage(), activity.getRows());
	}

	private Long total(String hql,Activity activity, HttpSession httpSession) throws Exception {
		hql = "select count(*) "+hql;
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(activity, hql, values, httpSession);
		hql += "order by t.releasetime desc,t.createtime desc";
		return activityDao.count(hql, values);
	}

	private String addWhere(Activity activity, String hql, List<Object> values, HttpSession httpSession) throws Exception {
		if (activity.getSecondaryId() != null && !"".equals(activity.getSecondaryId())) {
			String s="";
			for(String t : activity.getSecondaryId().split(",")){
				s += " t.secondaryId like ? or ";
				values.add("%" + t + "%");
			}
			s="(" + s.substring(0,s.length()-3) + ")";
			hql += " and " + s;
		}else{
			String secondaryId=((Seco) httpSession.getAttribute("sessionSeco")).getSecondaryId();
			hql += " and t.secondaryId =? ";
			values.add(secondaryId);
		}
		//根据用户组，查询对应组用户的数据
		String userGroup = activity.getUserGroup(); String code = activity.getCode();
		if(userGroup != null && !"".equals(userGroup) && code != null && !"".equals(code)){
			switch (userGroup) {
			case Constants.DIC_TYPE_YHZB_WY:
				hql += " and exists (from Tactivitypeo b where t.aid=b.activity.aid and b.comm.groupCode ='"+userGroup+"' and b.comm.code='"+code+"') ";
			break;
			default:
			break;
			}
		}
		if (activity.getStatus() != null && !"".equals(activity.getStatus())) {
			System.out.println("status==="+activity.getStatus());
			hql += " and t.status=?";
			values.add(activity.getStatus());
		}
		if (activity.getAspecies() != null && !"".equals(activity.getAspecies())) {
			hql += " and t.aspecies =? ";
			values.add(activity.getAspecies());
		}
		if (activity.getAtheme() != null && !"".equals(activity.getAtheme())) {
			hql += " and t.atheme like ? ";
			values.add("%"+activity.getAtheme()+"%");
		}
		if (activity.getPlace() != null && !"".equals(activity.getPlace())) {
			hql += " and t.place like ? ";
			values.add("%"+activity.getPlace()+"%");
		}
		if (activity.getAgency() != null && !"".equals(activity.getAgency())) {
			hql += " and t.agency like ? ";
			values.add("%"+activity.getAgency()+"%");
		}
		if (activity.getTimebeg() != null && !"".equals(activity.getTimebeg())) {
			hql += " and t.timebeg >=? ";
			values.add(activity.getTimebeg());
		}
		if (activity.getTimeend() != null && !"".equals(activity.getTimeend())) {
			hql += " and t.timeend <=? ";
			values.add(activity.getTimeend());
		}
		return hql;
	}
	
	
	/**
	 * 
	 * 委员履职详情，活动情况列表查询
	 * 
	 * @param activitypeo,userCode,secondaryCode
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagridFulfil(Activitypeo activitypeo,String userCode, String secondaryCode) 
			throws Exception {
		String hql=" from Tactivitypeo t1 where t1.activity.secondaryId='"+secondaryCode
			+"' and t1.comm.code='"+userCode
			+"' and t1.astatus='"+Constants.ACT_TYPE_ASTATUS_CX+"'"; //出席
		hql+=" order by t1.createtime desc";
		List<Tactivitypeo> meetingManList = activitypeoDao.find(hql, activitypeo.getPage(), activitypeo.getRows());
		DataGrid j = new DataGrid();
		j.setRows(getActiFromTactpeo(meetingManList,ServletActionContext.getRequest().getSession()));
		
		hql = "select count(*) "+hql;
		j.setTotal(activitypeoDao.count(hql));
		return j;
	}
	
	
	/**
	 * 活动信息添加
	 */
	public void add(Activity activity) throws Exception {
		Util util=new Util();
		Tactivity a = new Tactivity();
		PropertyUtil.copyProperties(a, activity);
		a.setAid(Generator.getInstance().getActivityNO());
		Date d=new Date();
		a.setCreatetime(d);
		if(activity.getStatus().equals(Constants.ACT_TYPE_STATUS_YFB)){
			a.setStatus(Constants.ACT_TYPE_STATUS_YFB);
			a.setReleasetime(d);
			//String deptId=((SessionInfo) ActionContext.getContext().getSession().get("sessionInfo")).getDeptId();
			//a.setReleaseage(deptId);
			a.setReleaseage(Constants.ACT_TYPE_PUB_DEPT);
		}
		if(Constants.ACT_TYPE_ASTATUS_CX.equals(activity.getAstatus())){
			a.setAttendnumb(activity.getInvitnumb());
		}else{
			a.setAttendnumb(0);
		}
		activityDao.save(a);
		
		if(!util.isEmpty(activity.getIds())) {
			for (String code : activity.getIds().split(",")) {
				addapeo(code,activity.getAstatus(),a.getAid());
			}
		}
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_ACT, Constants.LOG_TYPE_OPERTYPE_ADD, a.getAid(), "[活动管理]新增活动");
	}
	/**
	 * 活动人员信息添加
	 */
	private void addapeo(String code, String astatus, String aid) throws Exception {
		Tactivitypeo p = new Tactivitypeo();
		p.setAuid(Generator.getInstance().getActivitypeoNO());
		Tactivity a = new Tactivity();
		a.setAid(aid);
		p.setActivity(a);
		Tcommitteeman c = new Tcommitteeman();
		c.setCode(code);
		p.setComm(c);
		p.setAstatus(astatus);
		p.setCreatetime(new Date());
		activitypeoDao.save(p);
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_ACT, Constants.LOG_TYPE_OPERTYPE_ADD, p.getAuid(), "[活动管理]新增活动邀请人员");
	}
	/**
	 * 活动信息修改
	 */
	public void update(Activity activity, String astatus) throws Exception {
		Tactivity a = activityDao.get(Tactivity.class, activity.getAid());
		PropertyUtil.copyProperties(a, activity);
		activityDao.save(a);
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_ACT, Constants.LOG_TYPE_OPERTYPE_EDIT, activity.getAid(), "[活动管理]活动信息修改");
		if (activity.getIds() != null) {
			for (String code : activity.getIds().split(",")) {
				String hql = "from Tactivitypeo t where activity.aid='"+activity.getAid()+"' and comm.code='"+code+"'";
				List<Tactivitypeo> peo=activitypeoDao.find(hql);
				if(peo.size()==0){
					addapeo(code,astatus,a.getAid());
				} 
				if(peo.size()>0){
					Tactivitypeo ta= peo.get(0);
					ta.setAstatus(astatus);
					activitypeoDao.update(ta);
				}
			}
		}
	}
	/**
	 * 活动状态修改
	 */
	public void change(Activity activity) throws Exception {
		if (activity.getIds() != null) {
			for (String id : activity.getIds().split(",")) {
				Tactivity t = activityDao.get(Tactivity.class, id.trim());
				if (t != null) {
					t.setStatus(activity.getStatus());
					if(activity.getStatus().equals(Constants.ACT_TYPE_STATUS_YFB)){
						t.setReleasetime(new Date());
					}
					activityDao.save(t);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_ACT, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[活动管理]活动状态修改");
				}
			}
		}
		
	}
	/**
	 *请假驳回提交
	 */
	public void change(Activitypeo activitypeo) throws Exception {
		Tactivitypeo ap = activitypeoDao.get(Tactivitypeo.class, activitypeo.getAuid());
		ap.setAstatus(activitypeo.getAstatus());
		activitypeoDao.saveOrUpdate(ap);
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_MEETING, Constants.LOG_TYPE_OPERTYPE_SET, activitypeo.getAid()+"_"+activitypeo.getCommCode(), "[会议管理]请假驳回");
	}
	/**
	 * 活动信息删除
	 */
	public void delete(String ids) throws Exception {
		if (ids != null) {
			for (String id : ids.split(",")) {
/*				String hql = "from Tactivitypeo t where activity.aid='"+id+"'";
				List<Tactivitypeo> peo=activitypeoDao.find(hql);
				for(int i=0;i<peo.size();i++){
					Tactivitypeo p = activitypeoDao.get(Tactivitypeo.class, peo.get(i).getAuid());
					if (p != null) {
						activitypeoDao.delete(p);
						//生成日志
						this.saveLog(Constants.LOG_TYPE_CODE_ACT, Constants.LOG_TYPE_OPERTYPE_DEL, id, "[活动管理]活动信息删除");
					}
				}
*/				
				Tactivity t = activityDao.get(Tactivity.class, id.trim());
				if (t != null) {
					activityDao.delete(t);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_ACT, Constants.LOG_TYPE_OPERTYPE_DEL, id, "[活动管理]活动信息删除");
				}
			}
		}
	}
	/**
	 * 活动人员信息查询
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagridpeo(Activitypeo activitypeo, HttpSession httpSession) throws Exception {
		String hql = "from Tactivitypeo t where 1=1 ";
		DataGrid j = new DataGrid();
		j.setRows(getActiFromTactpeo(findpeo(hql,activitypeo, httpSession),httpSession));
		j.setTotal(totalpeo(hql,activitypeo, httpSession));
		return j;
	}
	/**
	 * 活动人员信息查询
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid peoQueryDatagrid(Activitypeo activitypeo, HttpSession httpSession) throws Exception {
		String hql = "from Tactivitypeo t where t.activity.status!='"+Constants.ACT_TYPE_STATUS_WFB+"' and t.activity.timeend <= to_date('"+sdf.format(new Date())+"','YYYY-MM-DD')";
		DataGrid j = new DataGrid();
		j.setRows(getActiFromTactpeo(findpeo(hql,activitypeo, httpSession),httpSession));
		j.setTotal(totalpeo(hql,activitypeo, httpSession));
		return j;
	}
	private List<Activitypeo> getActiFromTactpeo(List<Tactivitypeo> Tactivitypeos, HttpSession httpSession)throws Exception  {
		List<Activitypeo> ads = new ArrayList<Activitypeo>();
		if (Tactivitypeos != null && Tactivitypeos.size() > 0) {
			for (Tactivitypeo t : Tactivitypeos) {
				Activitypeo p = new Activitypeo();
				PropertyUtil.copyProperties(p, t.getActivity());
				PropertyUtil.copyProperties(p, t.getComm());
				PropertyUtil.copyProperties(p, t);
				
				p.setLeaveReason("");
				p.setAstatusName(findDicName(Constants.ACT_TYPE_ASTATUS,t.getAstatus()));
				p.setLeaveTypeName(findDicName(Constants.ACT_TYPE_LEAVE,t.getLeaveType()));
				
				p.setCommCode(t.getComm().getCode());
				p.setSexName(findDicName(Constants.CODE_TYPE_ID_SEX, t.getComm().getSex()));
				p.setJob(t.getComm().getJob());
				p.setCircleName(findDicName(Constants.CODE_TYPE_ID_CIRCLE, t.getComm().getCircleCode()));
				p.setAgencyName(findNameByDepid(t.getActivity().getAgency()));
				p.setSecondaryName(findNameBySecondaryid(t.getActivity().getSecondaryId()));
				p.setCommitteeName(findDicName(Constants.CODE_TYPE_ID_COMMITTEE,t.getComm().getCommittee()));
				p.setYear(findYearById(t.getActivity().getSecondaryId()));
				
				p.setAspeciesName(findDicName(Constants.ACT_TYPE_ASPECIES,t.getActivity().getAspecies()));
				ads.add(p);
			}
		}
		return ads;
	}

	private List<Tactivitypeo> findpeo(String hql,Activitypeo activitypeo, HttpSession httpSession)throws Exception  {
		List<Object> values = new ArrayList<Object>();
		hql = addWherepeo(activitypeo, hql, values, httpSession);
		hql += "order by t.createtime desc";
		return activitypeoDao.find(hql,values,activitypeo.getPage(),activitypeo.getRows());
	}

	private Long totalpeo(String hql,Activitypeo activitypeo, HttpSession httpSession) throws Exception {
		hql = "select count(*)  "+hql;
		List<Object> values = new ArrayList<Object>();
		hql = addWherepeo(activitypeo, hql, values, httpSession);
		hql += " order by t.createtime desc";
		return activitypeoDao.count(hql, values);
	}

	private String addWherepeo(Activitypeo activitypeo, String hql, List<Object> values, HttpSession httpSession) throws Exception {
		if (activitypeo.getAuid() != null && !"".equals(activitypeo.getAuid())) {
			hql += " and t.auid =? ";
			values.add(activitypeo.getAuid());
		}
		if (activitypeo.getAid() != null && !"".equals(activitypeo.getAid())) {
			hql += " and t.activity.aid =? ";
			values.add(activitypeo.getAid());
		}
		if (activitypeo.getAtheme() != null && !"".equals(activitypeo.getAtheme())) {
			hql += " and t.activity.atheme like ? ";
			values.add("%"+activitypeo.getAtheme()+"%");
		}
		if (activitypeo.getCode() != null && !"".equals(activitypeo.getCode())) {
			hql += " and t.comm.code =? ";
			values.add(activitypeo.getCode());
		}
		if (activitypeo.getName() != null && !"".equals(activitypeo.getName())) {
			hql += " and t.comm.name like ? ";
			values.add("%"+activitypeo.getName()+"%");
		}
		if (activitypeo.getSecondaryId() != null && !"".equals(activitypeo.getSecondaryId())) {
			String s="";
			for(String t : activitypeo.getSecondaryId().split(",")){
				s += " t.activity.secondaryId like ? or ";
				values.add("%" + t + "%");
			}
			s="(" + s.substring(0,s.length()-3) + ")";
			hql += " and " + s;
		}else{
			String secondaryId=((Seco) httpSession.getAttribute("sessionSeco")).getSecondaryId();
			hql += " and t.activity.secondaryId =? ";
			values.add(secondaryId);
		}
		if (activitypeo.getAspecies() != null && !"".equals(activitypeo.getAspecies())) {
			hql += " and t.activity.aspecies =? ";
			values.add(activitypeo.getAspecies());
		}
		//活动状态
		if (activitypeo.getActStatus() != null && !"".equals(activitypeo.getActStatus())) {
			hql += " and t.activity.status =? ";
			values.add(activitypeo.getActStatus());
		}
		if (activitypeo.getPlace() != null && !"".equals(activitypeo.getPlace())) {
			hql += " and t.activity.place like ? ";
			values.add("%"+activitypeo.getPlace()+"%");
		}
			if (activitypeo.getAgency() != null && !"".equals(activitypeo.getAgency())) {
			hql += " and t.activity.agency like ? ";
			values.add("%"+activitypeo.getAgency()+"%");
		}
		if (activitypeo.getTimebeg() != null && !"".equals(activitypeo.getTimebeg())) {
			hql += " and t.activity.timebeg >=? ";
			values.add(activitypeo.getTimebeg());
		}
		if (activitypeo.getTimeend() != null && !"".equals(activitypeo.getTimeend())) {
			hql += " and t.activity.timeend <=? ";
			values.add(activitypeo.getTimeend());
		}
		return hql;
	}
	
	/**
	 * 请假申请提交
	 */
	public void leave(Activitypeo activitypeo) throws Exception {
		Tactivitypeo a = activitypeoDao.get(Tactivitypeo.class, activitypeo.getAuid());
		a.setLeaveType(activitypeo.getLeaveType());
		a.setLeaveReason(activitypeo.getLeaveReason());
		a.setAstatus(Constants.ACT_TYPE_ASTATUS_QJ);
		activitypeoDao.save(a);
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_ACT, Constants.LOG_TYPE_OPERTYPE_SUBMIT, activitypeo.getAuid(), "[活动管理]活动人员请假申请");
	}
	
	/**
	 * 出席情况修改
	 */
	public void astatus(Activitypeo activitypeo) throws Exception {
		if (activitypeo.getAuid() != null) {
			for (String id : activitypeo.getAuid().split(",")) {
				Tactivitypeo p = activitypeoDao.get(Tactivitypeo.class,id);
				p.setAstatus(activitypeo.getAstatus());
				if(activitypeo.getLeaveType()!=null && !"".equals(activitypeo.getLeaveType())){
					p.setLeaveType(activitypeo.getLeaveType());
				}
				if(activitypeo.getLeaveReason()!=null && !"".equals(activitypeo.getLeaveReason())){
					p.setLeaveReason(activitypeo.getLeaveReason());
				}
				activitypeoDao.save(p);
				//生成日志
				this.saveLog(Constants.LOG_TYPE_CODE_ACT, Constants.LOG_TYPE_OPERTYPE_EDIT, activitypeo.getAuid(), "[活动管理]活动人员出席情况修改");
			}
		}
		
	}

	/**
	 * 参会情况统计表头数据
	 */
	public String getColumns() throws Exception {
		String json="[{title:'性别',field:'sexName',align:'center',width:80,rowspan:2},{title:'联系电话',field:'telephone',align:'center',width:120,rowspan:2},"
		+"{title:'电子邮箱',field:'email',align:'center',width:120,rowspan:2},{title:'工作单位 职务',field:'companyName',align:'center',width:180,rowspan:2,formatter: function(value, row) {"
					+"  return row.companyName+'    '+row.job;"
				+"}},";
		
		String sql="select t.cid,t.ckey,t.cvalue from tdic t where t.ctype='"+Constants.ACT_TYPE_ASPECIES+"'";
		List<Object[]> typelist =(List<Object[]>) activitypeoDao.executeCountSql(sql);
		json+="{title:'参加活动情况',align:'center',colspan:"+typelist.size()+"}";
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
	public String situationCount(Activitypeo activitypeo) throws Exception {
		if(Util.getInstance().isEmpty(activitypeo.getSecondaryId())){
			activitypeo.setSecondaryId(((Seco) ActionContext.getContext().getSession().get(ResourceUtil.getSessionSeco())).getSecondaryId());
		}
		String json="{\"total\":0,\"rows\":[";	//datagrid数据
		String hql="select t.comm_code,m.aspecies,count(*) num from tactivitypeo t left join tactivity m on m.aid=t.aid where t.astatus='"+Constants.ACT_TYPE_ASTATUS_CX+"'"
		         +" and m.secondary_id='"+activitypeo.getSecondaryId()+"' and exists (select code from tcommitteeman c where c.code=t.comm_code ";
		if(!Util.getInstance().isEmpty(activitypeo.getName())){
			hql+=" and c.name like '%"+activitypeo.getName()+"%'";
		}
		if(!Util.getInstance().isEmpty(activitypeo.getTelephone())){
			hql+=" and c.telephone = '"+activitypeo.getTelephone()+"'";
		}
		if(!Util.getInstance().isEmpty(activitypeo.getSex())){
			hql+=" and c.sex = '"+activitypeo.getSex()+"'";
		}
		if(!Util.getInstance().isEmpty(activitypeo.getNation())){
			hql+=" and c.nation = '"+activitypeo.getNation()+"'";
		}
		if(!Util.getInstance().isEmpty(activitypeo.getCircleCode())){
			hql+=" and c.circle_code = '"+activitypeo.getCircleCode()+"'";
		}
		if(!Util.getInstance().isEmpty(activitypeo.getPartyCode())){
			hql+=" and c.party_code = '"+activitypeo.getPartyCode()+"'";
		}
		if(!Util.getInstance().isEmpty(activitypeo.getCommittee())){
			hql+=" and c.committee = '"+activitypeo.getCommittee()+"'";
		}
		hql+=") group by t.comm_code,m.aspecies";
		List<Object[]> countList=activitypeoDao.executeCountSql(hql);
		Comm comm=new Comm();
		comm.setSecondaryCode(activitypeo.getSecondaryId());
		comm.setName(activitypeo.getName());
		comm.setTelephone(activitypeo.getTelephone());
		comm.setSex(activitypeo.getSex());
		comm.setNation(activitypeo.getNation());
		comm.setCircleCode(activitypeo.getCircleCode());
		comm.setPartyCode(activitypeo.getPartyCode());
		comm.setCommittee(activitypeo.getCommittee());
		
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
	/**
	 * 活动人员信息添加
	 */
	public void peoadd(String code, String aid)throws Exception{
		if (code!= null && !"".equals(code)) {
			for (String id : code.split(",")) {
				Tactivitypeo p = new Tactivitypeo();
				p.setAuid(Generator.getInstance().getActivitypeoNO());
				Tactivity a = activityDao.get(Tactivity.class,aid);
				p.setActivity(a);
				Tcommitteeman c = getCommDao().get(Tcommitteeman.class, id);
				p.setComm(c);
				p.setCreatetime(new Date());
				activitypeoDao.save(p);
				//生成日志
				this.saveLog(Constants.LOG_TYPE_CODE_ACT, Constants.LOG_TYPE_OPERTYPE_ADD, p.getAuid(), "[活动管理]新增活动邀请人员");
			}
		}
	}

	public void peodel(String ids) throws Exception {
		if (ids != null) {
			for (String id : ids.split(",")) {
				Tactivitypeo t = activitypeoDao.get(Tactivitypeo.class, id.trim());
				if (t != null) {
					activitypeoDao.delete(t);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_ACT, Constants.LOG_TYPE_OPERTYPE_DEL, id, "[活动管理]活动人员信息删除");
				}
			}
		}
	}
	//活动管理首页统计
	public Map<String, Integer> activityCount(HttpSession httpSession) {
		return null;
	}

	public DataGrid zxdatagrid(Activity activity, HttpSession httpSession)
			throws Exception {
		
		String hql = " from Tactivity t where t.status = '"+Constants.ACT_TYPE_STATUS_YFB+"' and t.timebeg >= to_date('"+sdf.format(new Date())+"','YYYY-MM-DD')";
		DataGrid j = new DataGrid();
		activity.setRows(9);
		j.setRows(getActiFromTacti(find(hql,activity, httpSession)));
		j.setTotal(total(hql,activity, httpSession));
		return j;
	}

	public DataGrid wfbdatagrid(Activity activity, HttpSession httpSession)
			throws Exception {
		String hql = " from Tactivity t where t.status = '"+Constants.ACT_TYPE_STATUS_WFB+"'";
		DataGrid j = new DataGrid();
		activity.setRows(9);
		j.setRows(getActiFromTacti(find(hql,activity, httpSession)));
		j.setTotal(total(hql,activity, httpSession));
		return j;
	}

	public DataGrid wbjdatagrid(Activity activity, HttpSession httpSession)
			throws Exception {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String hql = " from Tactivity t where t.status = '"+Constants.ACT_TYPE_STATUS_YFB+"' and t.timeend < to_date('"+sdf.format(new Date())+"','YYYY-MM-DD')";
		DataGrid j = new DataGrid();
		activity.setRows(9);
		j.setRows(getActiFromTacti(find(hql,activity, httpSession)));
		j.setTotal(total(hql,activity, httpSession));
		return j;
	}
}
