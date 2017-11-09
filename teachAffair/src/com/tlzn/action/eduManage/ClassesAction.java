package com.tlzn.action.eduManage;


import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.eduManage.Classes;
import com.tlzn.service.eduManage.ClassesServiceI;
import com.tlzn.util.base.Util;

@Action(value = "classes", results = {
		@Result(name = "classes", location = "/general/eduManage/classes.jsp"),
		@Result(name = "classesView", location = "/general/eduManage/classesView.jsp"),
		@Result(name = "classesAdd", location = "/general/eduManage/classesAdd.jsp"),
		@Result(name = "classesEdit", location = "/general/eduManage/classesEdit.jsp"),
		@Result(name = "classesQuery", location = "/general/eduManage/classesQuery.jsp"),
})
public class ClassesAction extends BaseAction implements ModelDriven<Classes>{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ClassesAction.class);
	
	private ClassesServiceI classesService;
	
	private Classes classes=new Classes();
	
	public Classes getModel() {
		return classes;
	}
	
	/**
	 * 班级列表页面
	 */
	public String classes(){
		return "classes";
	}
	/**
	 * 班级查询页面
	 */
	public String classesQuery(){
		return "classesQuery";
	}
	/**
	 * 班级详细页面
	 */
	public String classesView(){
		return "classesView";
	}
	/**
	 * 班级新增页面
	 */
	public String classesAdd(){
		return "classesAdd";
	}
	/**
	 * 班级发布页面
	 */
	public String classesPub(){
		request.setAttribute("classesPub","classesPub");
		return "classesAdd";
	}
	/**
	 * 
	 * 查询班级列表
	 */
	public void datagrid() {
		try {
			DataGrid dataGrid = classesService.datagrid(classes);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询班级列表（不验证权限）
	 */
	public void doNotNeedAuth_datagrid() {
		try {
			DataGrid dataGrid = classesService.datagrid(classes);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询班级列表(班级查询模块)
	 */
	public void datagridQuery() {
		try {
//			classes.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES);
			DataGrid dataGrid = classesService.datagrid(classes);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询 班级单条（跳转 班级详细页面）
	 */
	public String get() {
		try {
			String id=classes.getId();
			if(!Util.getInstance().isEmpty(id)){
					Classes classes =classesService.get(id);
					request.setAttribute("classes",classes);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "classesView";
	}
	/**
	 * 班级修改页面
	 */
	public String classesEdit(){
		try {
			String id=classes.getId();
			if(!Util.getInstance().isEmpty(id)){
				Classes classes =classesService.get(id);
				request.setAttribute("classes",classes);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "classesEdit";
	}
	/**
	 * 
	 * 新增班级
	 */
	public void add() {
		Json j = new Json();
		try {
			j.setObj(classesService.add(classes,httpSession));
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
	 * 修改班级
	 */
	public void edit() {
		Json j = new Json();
		try {
			classesService.edit(classes);
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
	 * 删除班级
	 */
	public void delete() {
		Json j = new Json();
		try {
			classesService.del(classes);
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
	 * 发布班级
	 */
	public void pub() {
		Json j = new Json();
		try {
			classesService.pub(classes,httpSession);
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
	 * 取消发布班级
	 */
	public void cancelPub() {
		Json j = new Json();
		try {
			classesService.cancelPub(classes);
			j.setSuccess(true);
			j.setMsg("取消发布成功!");
		} catch (Exception e) {
			j.setMsg("取消发布失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	public ClassesServiceI getClassesService() {
		return classesService;
	}
	@Autowired
	public void setClassesService(ClassesServiceI classesService) {
		this.classesService = classesService;
	}
}
