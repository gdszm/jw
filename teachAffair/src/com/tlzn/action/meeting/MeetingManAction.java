package com.tlzn.action.meeting;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.model.activity.Tactivitypeo;
import com.tlzn.model.poll.Tpoll;
import com.tlzn.model.sys.Tsecondary;
import com.tlzn.pageModel.activity.Activitypeo;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.base.SessionInfo;
import com.tlzn.pageModel.meeting.MeetingMan;
import com.tlzn.pageModel.sys.Seco;
import com.tlzn.service.meeting.MeetingManServiceI;
import com.tlzn.util.base.ResourceUtil;
import com.tlzn.util.base.Util;


@Action(value = "meetingMan", results = {
		@Result(name = "meetingOwn", location = "/general/meeting/meetingOwn.jsp"),
		@Result(name = "leave", location = "/general/meeting/situation_leave.jsp"),
		@Result(name = "situationSet", location = "/general/meeting/situation_set.jsp")
})
public class MeetingManAction extends BaseAction implements ModelDriven<MeetingMan>{

	
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(MeetingManAction.class);
	
	private MeetingMan meetingMan= new MeetingMan();
	public MeetingMan getModel() {
		return meetingMan;
	}
	
	private MeetingManServiceI meetingManService;
	
	public MeetingManServiceI getMeetingManService() {
		return meetingManService;
	}
	@Autowired
	public void setMeetingManService(MeetingManServiceI meetingManService) {
		this.meetingManService = meetingManService;
	}
	/**
	 * 我的参会情况跳转
	 */
	public String meetingOwn() {
		String status = request.getParameter("status");
		String beginTime=request.getParameter("beginTime");
		request.setAttribute("status", status);
		request.setAttribute("beginTime", beginTime);
		return "meetingOwn";
	}
	/**
	 * 请假详情页面
	 */
	public String leave() {
		try {
			MeetingMan mm = meetingManService.getLeave(meetingMan.getId());
			ServletActionContext.getRequest().setAttribute("obj", mm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "leave";
	}
	/**
	 * 出情情况调整页面
	 */
	public String situationSet() {
		return "situationSet";
	}
	
	/**
	 * 我的参会情况查询处理
	 */
	public void meetingMy() throws Exception {
		String code=((SessionInfo) httpSession.getAttribute("sessionInfo")).getUserCode();
		meetingMan.setCommCode(code);
		super.writeDataGridJson(meetingManService.datagridOwn(meetingMan));
	}
	/**
	 * 参会情况查询处理
	 */
	public void manQuery() throws Exception {
		String secondaryId=request.getParameter("secondaryId");
		if(secondaryId!=null){
			meetingMan.setSecondaryId(secondaryId);
		}
		super.writeDataGridJson(meetingManService.datagrid(meetingMan));
	}

	/**
	 * 请假申请提交
	 */
	public void leavesq() throws Exception {
		Json j = new Json();
		try {
			meetingManService.leave(meetingMan);
			j.setSuccess(true);
			j.setMsg("操作成功!");
		} catch (Exception e) {
			j.setMsg("操作失败!");
			e.printStackTrace();
		}
		writeJson(j);
	}
	
	/**
	 *参会人员数据获取
	 */
	public void datagridByMeeting() {
		try {
			writeDataGridJson(meetingManService.datagrid(meetingMan));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		
	}
	public void datagrid() {
		try {
			super.writeDataGridJson(meetingManService.datagrid(meetingMan));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		
	}
	/**
	 * 参会情况查询
	 */
	public void queryDatagrid() {
		try {
			super.writeDataGridJson(meetingManService.queryDatagrid(meetingMan));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		
	}
	/**
	 * 
	 * 委员履职详情，参会情况列表查询
	 */
	public void datagridFulfil() {
		String secondaryCode =request.getParameter("secondaryCode");
		String userCode = request.getParameter("userCode");
		try {
			DataGrid dataGrid = meetingManService.datagridFulfil(meetingMan, userCode,secondaryCode);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 *参会人员调整保存
	 */
	public void memberSave() {
		Json j = new Json();
		try {
			meetingManService.memberSave(meetingMan);
			j.setSuccess(true);
			j.setMsg("操作成功!");
		} catch (Exception e) {
			j.setMsg("操作失败!");
			e.printStackTrace();
		}
		writeJson(j);
	}
	/**
	 *请假驳回提交
	 */
	public void change(){
		Json j = new Json();
		try {
			meetingManService.change(meetingMan);
			j.setSuccess(true);
			j.setMsg("操作成功!");
		} catch (Exception e) {
			j.setMsg("操作失败!");
			e.printStackTrace();
		}
		writeJson(j);
	}
	/**
	 *出席情况调整提交
	 */
	public void setSave(){
		Json j = new Json();
		try {
			
			meetingManService.setSave(meetingMan);
			j.setSuccess(true);
			j.setMsg("操作成功!");
		} catch (Exception e) {
			j.setMsg("操作失败!");
			e.printStackTrace();
		}
		writeJson(j);
	}
	/**
	 * 参会情况统计表头数据
	 */
	public void getColumns() {
		try {
			super.writeListJson(meetingManService.getColumns());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 *参会情况统计
	 */
	public void situationCount() {
		try {
			super.writeListJson(meetingManService.situationCount(meetingMan));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
