

package com.tlzn.action.activity;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.action.meeting.MeetingAction;
import com.tlzn.pageModel.activity.Activity;
import com.tlzn.pageModel.activity.Activitypeo;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.base.SessionInfo;
import com.tlzn.service.activity.ActiServiceI;
import com.tlzn.util.base.Util;

@Action(value = "activity", results = {
		@Result(name = "activity", location = "/general/activity/activity.jsp") ,
		@Result(name = "amanage", location = "/general/activity/amanage.jsp") ,
		@Result(name = "actAdd", location = "/general/activity/actAdd.jsp") ,
		@Result(name = "actEdit", location = "/general/activity/actEdit.jsp") ,
		@Result(name = "actView", location = "/general/activity/actView.jsp") ,
		@Result(name = "actQuery", location = "/general/activity/actQuery.jsp") ,
		@Result(name = "actNotice", location = "/general/activity/actNotice.jsp") ,
		//活动管理首页
		@Result(name = "portal", location = "/public/layout/portal/portalActivity.jsp"),
})
public class ActivityAction extends BaseAction implements ModelDriven<Activity>{
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(ActivityAction.class);

	private ActiServiceI actiService;
	
	public ActiServiceI getActiService() {
		return actiService;
	}
	@Autowired
	public void setActiService(ActiServiceI actiService) {
		this.actiService = actiService;
	}
	
	private Activity activity = new Activity();

	public Activity getModel() {
		return activity;
	}
	
	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	
	/**
	 * 活动情况页面
	 */
	public String activity() {
		String status = request.getParameter("status");
		String beginTime=request.getParameter("beginTime");
		String endTime=request.getParameter("endTime");
		request.setAttribute("status", status);
		request.setAttribute("beginTime", beginTime);
		request.setAttribute("endTime", endTime);
		return "activity";
	}
	/**
	 * 活动管理页面
	 */
	public String amanage() {
		if(!new Util().isEmpty(activity.getStatus())) {
			request.setAttribute("status",activity.getStatus());
		}
		return "amanage";
	}
	/**
	 * 活动添加页面
	 */
	public String actAdd() {
		try {
			this.request.setAttribute("obj", actiService.defaul(httpSession));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "actAdd";
	}
	/**
	 * 活动修改页面
	 */
	public String actEdit() {
		try {
			Activity a = actiService.getTactivity(activity.getAid());
			ServletActionContext.getRequest().setAttribute("obj", a);;
			Activitypeo p = actiService.getTactivitypeo(activity.getAid());
			ServletActionContext.getRequest().setAttribute("peo", p);;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "actEdit";
	}
	/**
	 * 活动查看页面
	 */
	public String actView() {
		try {
			Activity a = actiService.getTactivity(activity.getAid());
			ServletActionContext.getRequest().setAttribute("obj", a);
			Activitypeo p = actiService.getTactivitypeo(activity.getAid());
			ServletActionContext.getRequest().setAttribute("peo", p);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "actView";
	}
	/**
	 * 活动查询页面
	 */
	public String actQuery() {
		return "actQuery";
	}
	/**
	 * 活动通知页面
	 */
	public String actNotice() {
		return "actNotice";
	}
	/**
	 * 活动信息查询
	 */
	public void datagrid(){
		try {
			super.writeDataGridJson(actiService.datagrid(activity,httpSession));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 活动信息查询
	 */
	public void queryDatagrid(){
		try {
			super.writeDataGridJson(actiService.queryDatagrid(activity,httpSession));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 活动情况信息查询
	 */
	public void situationDatagrid(){
		try {
			super.writeDataGridJson(actiService.queryDatagrid(activity,httpSession));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		
	}
	/**
	 * 活动信息添加
	 */
	public void add(){
		Json j = new Json();
		try {
//			String deptId=((SessionInfo) httpSession.getAttribute("sessionInfo")).getDeptId();
			//String astatus=request.getParameter("astatus");
//			activity.setReleaseage(deptId);
			actiService.add(activity);
			j.setSuccess(true);
			j.setMsg("添加成功!");
		} catch (Exception e) {
			j.setMsg("添加失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	/**
	 * 活动信息修改
	 */
	public void edit() {
		Json j = new Json();
		try {
			String astatus=request.getParameter("astatus");
			actiService.update(activity,astatus);
			j.setSuccess(true);
			j.setMsg("编辑成功!");
		} catch (Exception e) {
			j.setMsg("编辑失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	/**
	 * 活动状态修改
	 */
	public void change()  {
		Json j = new Json();
		try {
			actiService.change(activity);
			j.setSuccess(true);
			j.setMsg("操作成功!");
		} catch (Exception e) {
			j.setMsg("操作失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	/**
	 * 活动信息删除
	 */
	public void delete() {
		Json j = new Json();
		try {
			actiService.delete(activity.getIds());
			j.setSuccess(true);
			j.setMsg("删除成功!");
		} catch (Exception e) {
			j.setMsg("删除失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	
	//活动管理首页统计
	public String doNotNeedAuth_activityCount(){
		/*try {
			Map<String,Integer> countMeetingMap=actiService.activityCount(httpSession);
			this.request.setAttribute("countMeetingMap", countMeetingMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}*/
		return "portal";
	}
	
	/**
	 * 最新活动查询
	 */
	public void doNotNeedAuth_zxdatagrid(){
		try {
			super.writeDataGridJson(actiService.zxdatagrid(activity,httpSession));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	
	/**
	 * 未发布活动查询
	 */
	public void doNotNeedAuth_wfbdatagrid(){
		try {
			super.writeDataGridJson(actiService.wfbdatagrid(activity,httpSession));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	
	/**
	 * 未办结活动查询
	 */
	public void doNotNeedAuth_wbjdatagrid(){
		try {
			super.writeDataGridJson(actiService.wbjdatagrid(activity,httpSession));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
}
