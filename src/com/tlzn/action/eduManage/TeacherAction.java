package com.tlzn.action.eduManage;


import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.eduManage.Teacher;
import com.tlzn.service.eduManage.TeacherServiceI;
import com.tlzn.util.base.Util;

@Action(value = "teacher", results = {
		@Result(name = "teacher", location = "/general/eduManage/teacher.jsp"),
		@Result(name = "teacherView", location = "/general/eduManage/teacherView.jsp"),
		@Result(name = "teacherAdd", location = "/general/eduManage/teacherAdd.jsp"),
		@Result(name = "teacherEdit", location = "/general/eduManage/teacherEdit.jsp"),
		@Result(name = "teacherAE", location = "/general/eduManage/teacherAE.jsp"),
		@Result(name = "teacherQuery", location = "/general/eduManage/teacherQuery.jsp"),
		//教师选择页面
		@Result(name = "teacherSelect", location = "/general/eduManage/teacherSelect.jsp"),
})
public class TeacherAction extends BaseAction implements ModelDriven<Teacher>{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(TeacherAction.class);
	
	private TeacherServiceI teacherService;
	
	private Teacher teacher=new Teacher();
	
	public Teacher getModel() {
		return teacher;
	}
	
	/**
	 * 教师列表页面
	 */
	public String teacher(){
		return "teacher";
	}
	/**
	 * 教师查询页面
	 */
	public String teacherQuery(){
		return "teacherQuery";
	}
	/**
	 * 教师详细页面
	 */
	public String teacherView(){
		return "teacherView";
	}
	/**
	 * 教师新增页面
	 */
	public String teacherAdd(){
		return "teacherAdd";
	}
	/**
	 * 教师发布页面
	 */
	public String teacherPub(){
		request.setAttribute("teacherPub","teacherPub");
		return "teacherAdd";
	}
	/**
	 * 弹出教师选择页面
	 */
	public String teacherSelect(){
		return "teacherSelect";
	}
	/**
	 * 
	 * 查询教师列表
	 */
	public void datagrid() {
		try {
			DataGrid dataGrid = teacherService.datagrid(teacher);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询教师列表（不验证权限）
	 */
	public void doNotNeedAuth_datagrid() {
		try {
			DataGrid dataGrid = teacherService.datagrid(teacher);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询教师列表(教师查询模块)
	 */
	public void datagridQuery() {
		try {
//			teacher.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES);
			DataGrid dataGrid = teacherService.datagrid(teacher);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询 教师单条（跳转 教师详细页面）
	 */
	public String get() {
		try {
			String id=teacher.getId();
			if(!Util.getInstance().isEmpty(id)){
					Teacher teacher =teacherService.get(id);
					request.setAttribute("teacher",teacher);
//					String atts= teacher.getAtts();
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
		return "teacherView";
	}
	/**
	 * 教师修改页面
	 */
	public String teacherEdit(){
		try {
			String id=teacher.getId();
			if(!Util.getInstance().isEmpty(id)){
				Teacher teacher =teacherService.get(id);
				request.setAttribute("teacher",teacher);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "teacherEdit";
	}

	/**
	 * 
	 * 新增教师
	 */
	public void add() {
		Json j = new Json();
		try {
			j.setObj(teacherService.add(teacher,httpSession));
			j.setSuccess(true);
			j.setMsg("保存成功!");
		} catch (Exception e) {
			j.setMsg("保存失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
//	/**
//	 * 
//	 * 更新或新增教师
//	 */
//	public void upDateOrAdd() {
//		Json j = new Json();
//		try {
//			j.setObj(teacherService.upDateOrAdd(teacher,httpSession));
//			j.setSuccess(true);
//			j.setMsg("操作成功!");
//		} catch (Exception e) {
//			j.setMsg("操作失败!");
//			e.printStackTrace();
//			logger.info(e.getMessage());
//		}
//		writeJson(j);
//	}
	
	/**
	 * 
	 * 修改教师
	 */
	public void edit() {
		Json j = new Json();
		try {
			teacherService.edit(teacher);
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
	 * 删除教师
	 */
	public void delete() {
		Json j = new Json();
		try {
			teacherService.del(teacher);
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
	 * 发布教师
	 */
	public void pub() {
		Json j = new Json();
		try {
			teacherService.pub(teacher,httpSession);
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
	 * 取消发布教师
	 */
	public void cancelPub() {
		Json j = new Json();
		try {
			teacherService.cancelPub(teacher);
			j.setSuccess(true);
			j.setMsg("取消发布成功!");
		} catch (Exception e) {
			j.setMsg("取消发布失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	public TeacherServiceI getTeacherService() {
		return teacherService;
	}
	@Autowired
	public void setTeacherService(TeacherServiceI teacherService) {
		this.teacherService = teacherService;
	}
}
