package com.tlzn.action.meeting;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.meeting.Meeting;
import com.tlzn.service.meeting.MeetingServiceI;




@Action(value = "meeting", results = {
		@Result(name = "meeting", location = "/general/meeting/meeting.jsp"),
		@Result(name = "meetingAdd", location = "/general/meeting/meeting_add.jsp"),
		@Result(name = "meetingEdit", location = "/general/meeting/meeting_edit.jsp"),
		@Result(name = "meetingView", location = "/general/meeting/meetingView.jsp"),
		@Result(name = "meetingSituation", location = "/general/meeting/meetingSituation.jsp"),
		@Result(name = "meetingSee", location = "/general/meeting/meetingView.jsp"),
		@Result(name = "meetingQuery", location = "/general/meeting/meetingQuery.jsp"),
		@Result(name = "situationQuery", location = "/general/meeting/situationQuery.jsp"),
		@Result(name = "situationCount", location = "/general/meeting/situationCount.jsp"),
		//会议管理首页
		@Result(name = "portal", location = "/public/layout/portal/portalMeeting.jsp"),
		@Result(name = "zxmeeting", location = "/general/meeting/zxmeeting.jsp"),
		@Result(name = "wbjmeeting", location = "/general/meeting/wbjmeeting.jsp"),
		@Result(name = "wfbmeeting", location = "/general/meeting/wfbmeeting.jsp"),
})
public class MeetingAction extends BaseAction implements ModelDriven<Meeting>{

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(MeetingAction.class);
	
	private Meeting meeting=new Meeting();
	public Meeting getModel() {
		return meeting;
	}
	
	private MeetingServiceI meetingService;
	
	
	public MeetingServiceI getMeetingService() {
		return meetingService;
	}
	@Autowired
	public void setMeetingService(MeetingServiceI meetingService) {
		this.meetingService = meetingService;
	}
	/**
	 * 会议管理页面跳转
	 */
	public String meeting() {
		String status = request.getParameter("status");
		request.setAttribute("status", status);
		return "meeting";
	}
	/**
	 * 会议情况页面跳转
	 */
	public String meetingSituation() {
		String status = request.getParameter("status");
		String beginTime=request.getParameter("beginTime");
		String endTime=request.getParameter("endTime");
		request.setAttribute("status", status);
		request.setAttribute("beginTime", beginTime);
		request.setAttribute("endTime", endTime);
		return "meetingSituation";
	}
	/**
	 * 会议详情页面跳转
	 */
	public String meetingSee() {
		return "meetingSee";
	}
	/**
	 * 会议查询页面跳转
	 */
	public String meetingQuery() {
		return "meetingQuery";
	}
	/**
	 * 参会情况查询页面跳转
	 */
	public String situationQuery() {
		return "situationQuery";
	}
	/**
	 * 参会情况统计页面跳转
	 */
	public String situationCount() {
		return "situationCount";
	}
	/**
	 * 添加页面跳转
	 */
	public String meetingAdd() {
		return "meetingAdd";
	}
	/**
	 * 编辑页面跳转
	 */
	public String meetingEdit() {
		try {
			Meeting meet=meetingService.findMeeting(meeting);
			request.setAttribute("obj", meet);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		
		return "meetingEdit";
	}
	/**
	 * 详情页面跳转
	 */
	public String meetingView() {
		try {
			Meeting meet=meetingService.findMeeting(meeting);
			request.setAttribute("obj", meet);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		
		return "meetingView";
	}
	/**
	 * 最新会议管理页面跳转
	 */
	public String zxmeeting() {
		return "zxmeeting";
	}
	/**
	 * 未办结会议管理页面跳转
	 */
	public String wbjmeeting() {
		return "wbjmeeting";
	}
	/**
	 * 未发布会议管理页面跳转
	 */
	public String wfbmeeting() {
		return "wfbmeeting";
	}
	/**
	 * 添加会议
	 */
	public void add() {
		Json j = new Json();
		try {
			
			meetingService.add(meeting);
			
			j.setSuccess(true);
			j.setMsg("保存成功!");
		} catch (Exception e) {
			j.setMsg("保存失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	/**
	 * 编辑会议
	 */
	public void update() {
		Json j = new Json();
		try {
			
			meetingService.update(meeting);
			
			j.setSuccess(true);
			j.setMsg("更新成功!");
		} catch (Exception e) {
			j.setMsg("更新失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	
//	gds add
	/**
	 * 
	 * 获取当前届次的未开会议
	 */
	public void getCurSecMeeting() {
		try {
			DataGrid dataGrid = meetingService.getCurSecMeeting(meeting);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
//	gds add
	/**
	 *会议数据获取
	 */
	public void datagrid() {
		try {
			writeDataGridJson(meetingService.datagrid(meeting));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		
	}
	/**
	 * 
	 * 会议查询
	 */
	public void queryDatagrid() {
		try {
			DataGrid dataGrid = meetingService.queryDatagrid(meeting);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 会议删除
	 */
	public void remove() {
		Json j = new Json();
		try {
			
			meetingService.remove(meeting);
			
			j.setSuccess(true);
			j.setMsg("删除成功!");
		} catch (Exception e) {
			j.setMsg("删除失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	/**
	 * 发布/取消发布/办结
	 */
	public void change() {
		Json j = new Json();
		try {
			
			meetingService.change(meeting);
			
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
	 *参会情况数据获取
	 */
	public void situationDatagrid() {
		try {
			writeDataGridJson(meetingService.situationDatagrid(meeting));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		
	}
	//会议管理首页统计
	public String doNotNeedAuth_meetingCount(){
		try {
			Map<String,Integer> countMeetingMap=meetingService.meetingCount(httpSession);
			this.request.setAttribute("countMeetingMap", countMeetingMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "portal";
	}
	
	/**
	 * 最新会议查询
	 */
	public void doNotNeedAuth_zxdatagrid() {
		try {
			writeDataGridJson(meetingService.zxdatagrid(meeting));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		
	}
	
	/**
	 * 未发布会议查询
	 */
	public void doNotNeedAuth_wfbdatagrid() {
		try {
			writeDataGridJson(meetingService.wfbdatagrid(meeting));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		
	}
	
	/**
	 * 未办结会议查询
	 */
	public void doNotNeedAuth_wbjdatagrid() {
		try {
			writeDataGridJson(meetingService.wbjdatagrid(meeting));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		
	}
}


