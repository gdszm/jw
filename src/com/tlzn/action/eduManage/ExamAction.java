package com.tlzn.action.eduManage;


import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.eduManage.Exam;
import com.tlzn.service.eduManage.ExamServiceI;
import com.tlzn.util.base.Util;

@Action(value = "exam", results = {
		@Result(name = "exam", location = "/general/eduManage/exam.jsp"),
		@Result(name = "examView", location = "/general/eduManage/examView.jsp"),
		@Result(name = "examAdd", location = "/general/eduManage/examAdd.jsp"),
		@Result(name = "examEdit", location = "/general/eduManage/examEdit.jsp"),
		@Result(name = "examQuery", location = "/general/eduManage/examQuery.jsp"),
//		//日常办公首页
//		@Result(name = "portal", location = "/public/layout/portal/portalDailywork.jsp")
})
public class ExamAction extends BaseAction implements ModelDriven<Exam>{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ExamAction.class);
	
	private ExamServiceI examService;
	
	private Exam exam=new Exam();
	public Exam getModel() {
		return exam;
	}
	
	/**
	 * 成绩页面
	 */
	public String exam(){
		return "exam";
	}
	/**
	 * 成绩查询页面
	 */
	public String examQuery(){
		return "examQuery";
	}
	/**
	 * 成绩详细页面
	 */
	public String examView(){
		return "examView";
	}
	/**
	 * 成绩新增页面
	 */
	public String examAdd(){
		return "examAdd";
	}
	/**
	 * 成绩发布页面
	 */
	public String examPub(){
		request.setAttribute("examPub","examPub");
		return "examAdd";
	}
	/**
	 * 
	 * 查询成绩列表
	 */
	public void datagrid() {
		try {
			DataGrid dataGrid = examService.datagrid(exam);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询成绩列表
	 */
	public void doNotNeedAuth_datagrid() {
		try {
			DataGrid dataGrid = examService.datagrid(exam);
			
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询成绩列表(通知查询模块)
	 */
	public void datagridQuery() {
		try {
//			exam.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES);
			DataGrid dataGrid = examService.datagrid(exam);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询 成绩单条（跳转 成绩详细页面）
	 */
	public String get() {
		try {
			String id=exam.getId();
			if(!Util.getInstance().isEmpty(id)){
					Exam exam =examService.get(id);
					request.setAttribute("exam",exam);
//					String atts= exam.getAtts();
//					List<String> attList = null;
//					if(!Util.getInstance().isEmpty(atts)){
//						attList = new ArrayList<String>();
//						String attArry[] = atts.split(",");
//						for (String att : attArry) {
//							attList.add(att);
//						}
//						request.setAttribute("attList",attList);
//					}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "examView";
	}
	/**
	 * 成绩修改页面
	 */
	public String examEdit(){
		try {
			String id=exam.getId();
			if(!Util.getInstance().isEmpty(id)){
					Exam exam =examService.get(id);
					request.setAttribute("exam",exam);
					
//					String atts= exam.getAtts();
//					List<String> attList = null;
//					if(!Util.getInstance().isEmpty(atts)){
//						attList = new ArrayList<String>();
//						String attArry[] = atts.split(",");
//						for (String att : attArry) {
//							attList.add(att);
//						}
//						request.setAttribute("attList",attList);
//					}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "examEdit";
	}
	/**
	 * 
	 * 保存成绩
	 */
	public void save() {
		Json j = new Json();
		try {
			if(Util.getInstance().isEmpty(exam.getId())){
				j.setObj(examService.save(exam,httpSession));
			} else {
				j.setObj(examService.saveForUpate(exam,httpSession));
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
	 * 更新或新增成绩
	 */
	public void upDateOrAdd() {
		Json j = new Json();
		try {
			j.setObj(examService.upDateOrAdd(exam,httpSession));
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
	 * 修改成绩
	 */
	public void edit() {
		Json j = new Json();
		try {
			examService.edit(exam);
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
	 * 删除成绩
	 */
	public void delete() {
		Json j = new Json();
		try {
			examService.del(exam);
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
	 * 发布成绩
	 */
	public void pub() {
		Json j = new Json();
		try {
			examService.pub(exam,httpSession);
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
	 * 取消发布成绩
	 */
	public void cancelPub() {
		Json j = new Json();
		try {
			examService.cancelPub(exam);
			j.setSuccess(true);
			j.setMsg("取消发布成功!");
		} catch (Exception e) {
			j.setMsg("取消发布失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	public ExamServiceI getExamService() {
		return examService;
	}
	@Autowired
	public void setExamService(ExamServiceI examService) {
		this.examService = examService;
	}
	//会议发言首页统计
//	public String doNotNeedAuth_dailyworkCount(){
//		try {
//			Map<String,Integer> countMeetingMap=examService.examCount(httpSession);
//			this.request.setAttribute("countMeetingMap", countMeetingMap);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.info(e.getMessage());
//		}
//		return "portal";
//	}
}
