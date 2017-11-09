package com.tlzn.action.dailywork;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.model.dailywork.Tnotice;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.dailywork.Notice;
import com.tlzn.pageModel.sys.Seco;
import com.tlzn.service.dailywork.NoticeServiceI;
import com.tlzn.util.base.Util;
import com.tlzn.util.base.Constants;

@Action(value = "notice", results = {
		@Result(name = "notice", location = "/general/dailywork/notice.jsp"),
		@Result(name = "noticeView", location = "/general/dailywork/noticeView.jsp"),
		@Result(name = "noticeAdd", location = "/general/dailywork/noticeAdd.jsp"),
		@Result(name = "noticeEdit", location = "/general/dailywork/noticeEdit.jsp"),
		@Result(name = "noticeQuery", location = "/general/dailywork/noticeQuery.jsp"),
		//日常办公首页
		@Result(name = "portal", location = "/public/layout/portal/portalDailywork.jsp")
})
public class NoticeAction extends BaseAction implements ModelDriven<Notice>{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(NoticeAction.class);
	
	private NoticeServiceI noticeService;
	
	private Notice notice=new Notice();
	public Notice getModel() {
		return notice;
	}
	
	/**
	 * 通知公告页面
	 */
	public String notice(){
		return "notice";
	}
	/**
	 * 通知公告查询页面
	 */
	public String noticeQuery(){
		return "noticeQuery";
	}
	/**
	 * 通知公告详细页面
	 */
	public String noticeView(){
		return "noticeView";
	}
	/**
	 * 通知公告新增页面
	 */
	public String noticeAdd(){
		return "noticeAdd";
	}
	/**
	 * 通知公告新增页面
	 */
	public String noticePub(){
		request.setAttribute("noticePub","noticePub");
		return "noticeAdd";
	}
	/**
	 * 
	 * 查询通知公告列表
	 */
	public void datagrid() {
		try {
			DataGrid dataGrid = noticeService.datagrid(notice);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询通知公告列表
	 */
	public void doNotNeedAuth_datagrid() {
		try {
			DataGrid dataGrid = noticeService.datagrid(notice);
			
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询通知公告列表(通知查询模块)
	 */
	public void datagridQuery() {
		try {
			notice.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES);
			DataGrid dataGrid = noticeService.datagrid(notice);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询 通知公告单条（跳转 通知公告详细页面）
	 */
	public String get() {
		try {
			String id=notice.getId();
			if(!Util.getInstance().isEmpty(id)){
					Notice notice =noticeService.get(id);
					request.setAttribute("notice",notice);
					String atts= notice.getAtts();
					List<String> attList = null;
					if(!Util.getInstance().isEmpty(atts)){
						attList = new ArrayList<String>();
						String attArry[] = atts.split(",");
						for (String att : attArry) {
							attList.add(att);
						}
						request.setAttribute("attList",attList);
					}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "noticeView";
	}
	/**
	 * 通知公告修改页面
	 */
	public String noticeEdit(){
		try {
			String id=notice.getId();
			if(!Util.getInstance().isEmpty(id)){
					Notice notice =noticeService.get(id);
					request.setAttribute("notice",notice);
					
					String atts= notice.getAtts();
					List<String> attList = null;
					if(!Util.getInstance().isEmpty(atts)){
						attList = new ArrayList<String>();
						String attArry[] = atts.split(",");
						for (String att : attArry) {
							attList.add(att);
						}
						request.setAttribute("attList",attList);
					}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "noticeEdit";
	}
	/**
	 * 
	 * 保存通知公告
	 */
	public void save() {
		Json j = new Json();
		try {
			if(Util.getInstance().isEmpty(notice.getId())){
				j.setObj(noticeService.save(notice,httpSession));
			} else {
				j.setObj(noticeService.saveForUpate(notice,httpSession));
			}
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
	 * 
	 * 更新或新增通知公告
	 */
	public void upDateOrAdd() {
		Json j = new Json();
		try {
			j.setObj(noticeService.upDateOrAdd(notice,httpSession));
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
	 * 
	 * 修改通知公告
	 */
	public void edit() {
		Json j = new Json();
		try {
			noticeService.edit(notice);
			j.setSuccess(true);
			j.setMsg("修改成功!");
		} catch (Exception e) {
			j.setMsg("修改失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	/**
	 * 删除通知公告
	 */
	public void delete() {
		Json j = new Json();
		try {
			noticeService.del(notice);
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
	 * 发布通知公告
	 */
	public void pub() {
		Json j = new Json();
		try {
			noticeService.pub(notice,httpSession);
			j.setSuccess(true);
			j.setMsg("发布成功!");
		} catch (Exception e) {
			j.setMsg("发布失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	/**
	 * 取消发布通知公告
	 */
	public void cancelPub() {
		Json j = new Json();
		try {
			noticeService.cancelPub(notice);
			j.setSuccess(true);
			j.setMsg("取消发布成功!");
		} catch (Exception e) {
			j.setMsg("取消发布失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	public NoticeServiceI getNoticeService() {
		return noticeService;
	}
	@Autowired
	public void setNoticeService(NoticeServiceI noticeService) {
		this.noticeService = noticeService;
	}
	//会议发言首页统计
	public String doNotNeedAuth_dailyworkCount(){
		try {
			Map<String,Integer> countMeetingMap=noticeService.noticeCount(httpSession);
			this.request.setAttribute("countMeetingMap", countMeetingMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "portal";
	}
}
