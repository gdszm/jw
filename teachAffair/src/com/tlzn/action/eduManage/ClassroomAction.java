package com.tlzn.action.eduManage;


import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.eduManage.Classroom;
import com.tlzn.service.eduManage.ClassroomServiceI;
import com.tlzn.util.base.Util;

@Action(value = "classroom", results = {
		@Result(name = "classroom", location = "/general/eduManage/classroom.jsp"),
		@Result(name = "classroomView", location = "/general/eduManage/classroomView.jsp"),
		@Result(name = "classroomAdd", location = "/general/eduManage/classroomAdd.jsp"),
		@Result(name = "classroomEdit", location = "/general/eduManage/classroomEdit.jsp"),
		@Result(name = "classroomQuery", location = "/general/eduManage/classroomQuery.jsp"),
		//教室选择页面
		@Result(name = "classroomSelect", location = "/general/eduManage/classroomSelect.jsp")
})
public class ClassroomAction extends BaseAction implements ModelDriven<Classroom>{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ClassroomAction.class);
	
	private ClassroomServiceI classroomService;
	
	private Classroom classroom=new Classroom();
	
	public Classroom getModel() {
		return classroom;
	}
	
	/**
	 * 教室列表页面
	 */
	public String classroom(){
		return "classroom";
	}
	/**
	 * 教室查询页面
	 */
	public String classroomQuery(){
		return "classroomQuery";
	}
	/**
	 * 教室详细页面
	 */
	public String classroomView(){
		return "classroomView";
	}
	/**
	 * 教室新增页面
	 */
	public String classroomAdd(){
		return "classroomAdd";
	}
	/**
	 * 弹出教室选择页面
	 */
	public String classroomSelect(){
		return "classroomSelect";
	}
	/**
	 * 教室发布页面
	 */
	public String classroomPub(){
		request.setAttribute("classroomPub","classroomPub");
		return "classroomAdd";
	}
	/**
	 * 
	 * 查询教室列表
	 */
	public void datagrid() {
		try {
			DataGrid dataGrid = classroomService.datagrid(classroom);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询教室列表（不验证权限）
	 */
	public void doNotNeedAuth_datagrid() {
		try {
			DataGrid dataGrid = classroomService.datagrid(classroom);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询教室列表(教室查询模块)
	 */
	public void datagridQuery() {
		try {
//			classroom.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES);
			DataGrid dataGrid = classroomService.datagrid(classroom);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询 教室单条（跳转 教室详细页面）
	 */
	public String get() {
		try {
			String id=classroom.getId();
			if(!Util.getInstance().isEmpty(id)){
					Classroom classroom =classroomService.get(id);
					request.setAttribute("classroom",classroom);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "classroomView";
	}
	/**
	 * 教室修改页面
	 */
	public String classroomEdit(){
		try {
			String id=classroom.getId();
			if(!Util.getInstance().isEmpty(id)){
				Classroom classroom =classroomService.get(id);
				request.setAttribute("classroom",classroom);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "classroomEdit";
	}
	/**
	 * 
	 * 新增教室
	 */
	public void add() {
		Json j = new Json();
		try {
			j.setObj(classroomService.add(classroom,httpSession));
			j.setSuccess(true);
			j.setMsg("新增成功!");
		} catch (Exception e) {
			j.setMsg("新增失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	
	/**
	 * 
	 * 修改教室
	 */
	public void edit() {
		Json j = new Json();
		try {
			classroomService.edit(classroom);
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
	 * 删除教室
	 */
	public void delete() {
		Json j = new Json();
		try {
			classroomService.del(classroom);
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
	 * 发布教室
	 */
	public void pub() {
		Json j = new Json();
		try {
			classroomService.pub(classroom,httpSession);
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
	 * 取消发布教室
	 */
	public void cancelPub() {
		Json j = new Json();
		try {
			classroomService.cancelPub(classroom);
			j.setSuccess(true);
			j.setMsg("取消发布成功!");
		} catch (Exception e) {
			j.setMsg("取消发布失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	public ClassroomServiceI getClassroomService() {
		return classroomService;
	}
	@Autowired
	public void setClassroomService(ClassroomServiceI classroomService) {
		this.classroomService = classroomService;
	}
}
