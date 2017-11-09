package com.tlzn.action.eduManage;


import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.eduManage.ClassManage;
import com.tlzn.service.eduManage.ClassManageServiceI;
import com.tlzn.util.base.Util;

@Action(value = "classManage", results = {
		@Result(name = "classManage", location = "/general/eduManage/classManage.jsp"),
		@Result(name = "classManageView", location = "/general/eduManage/classManageView.jsp"),
		@Result(name = "classManageAdd", location = "/general/eduManage/classManageAdd.jsp"),
		@Result(name = "classManageEdit", location = "/general/eduManage/classManageEdit.jsp"),
		@Result(name = "classManageQuery", location = "/general/eduManage/classManageQuery.jsp"),
})
public class ClassManageAction extends BaseAction implements ModelDriven<ClassManage>{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ClassManageAction.class);
	
	private ClassManageServiceI classManageService;
	
	private ClassManage classManage=new ClassManage();
	
	public ClassManage getModel() {
		return classManage;
	}
	
	/**
	 * 班级管理列表页面
	 */
	public String classManage(){
		return "classManage";
	}
	/**
	 * 班级管理查询页面
	 */
	public String classManageQuery(){
		return "classManageQuery";
	}
	/**
	 * 班级管理详细页面
	 */
	public String classManageView(){
		return "classManageView";
	}
	/**
	 * 班级管理新增页面
	 */
	public String classManageAdd(){
		return "classManageAdd";
	}
	/**
	 * 班级管理发布页面
	 */
	public String classManagePub(){
		request.setAttribute("classManagePub","classManagePub");
		return "classManageAdd";
	}
	/**
	 * 
	 * 查询班级管理列表
	 */
	public void datagrid() {
		try {
			DataGrid dataGrid = classManageService.datagrid(classManage);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询班级管理列表（不验证权限）
	 */
	public void doNotNeedAuth_datagrid() {
		try {
			DataGrid dataGrid = classManageService.datagrid(classManage);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询班级管理列表(班级管理查询模块)
	 */
	public void datagridQuery() {
		try {
//			classManage.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES);
			DataGrid dataGrid = classManageService.datagrid(classManage);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询 班级管理单条（跳转 班级管理详细页面）
	 */
	public String get() {
		try {
			String id=classManage.getId();
			if(!Util.getInstance().isEmpty(id)){
					ClassManage classManage =classManageService.get(id);
					request.setAttribute("classManage",classManage);
//					String atts= classManage.getAtts();
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
		return "classManageView";
	}
	/**
	 * 班级管理修改页面
	 */
	public String classManageEdit(){
		try {
			String id=classManage.getId();
			if(!Util.getInstance().isEmpty(id)){
				ClassManage classManage =classManageService.get(id);
				request.setAttribute("classManage",classManage);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "classManageEdit";
	}
	/**
	 * 
	 * 保存班级管理
	 */
	public void save() {
		Json j = new Json();
		try {
			if(Util.getInstance().isEmpty(classManage.getId())){
				j.setObj(classManageService.save(classManage,httpSession));
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
	 * 修改班级管理
	 */
	public void edit() {
		Json j = new Json();
		try {
			classManageService.edit(classManage);
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
	 * 删除班级管理
	 */
	public void delete() {
		Json j = new Json();
		try {
			classManageService.del(classManage);
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
	 * 发布班级管理
	 */
	public void pub() {
		Json j = new Json();
		try {
			classManageService.pub(classManage,httpSession);
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
	 * 取消发布班级管理
	 */
	public void cancelPub() {
		Json j = new Json();
		try {
			classManageService.cancelPub(classManage);
			j.setSuccess(true);
			j.setMsg("取消发布成功!");
		} catch (Exception e) {
			j.setMsg("取消发布失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	public ClassManageServiceI getClassManageService() {
		return classManageService;
	}
	@Autowired
	public void setClassManageService(ClassManageServiceI classManageService) {
		this.classManageService = classManageService;
	}
}
