package com.tlzn.action.eduManage;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.model.eduManage.TclassroomType;
import com.tlzn.model.sys.Tusergroup;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.eduManage.ClassroomType;
import com.tlzn.pageModel.sys.UserGroup;
import com.tlzn.service.eduManage.ClassroomServiceI;
import com.tlzn.service.eduManage.ClassroomTypeServiceI;
import com.tlzn.util.base.Util;

@Action(value = "classroomTypeType", results = {
		@Result(name = "classroomTypeType", location = "/general/eduManage/classroomTypeType.jsp"),
		@Result(name = "classroomTypeTypeView", location = "/general/eduManage/classroomTypeTypeView.jsp"),
		@Result(name = "classroomTypeTypeAdd", location = "/general/eduManage/classroomTypeTypeAdd.jsp"),
		@Result(name = "classroomTypeTypeEdit", location = "/general/eduManage/classroomTypeTypeEdit.jsp"),
		@Result(name = "classroomTypeTypeQuery", location = "/general/eduManage/classroomTypeTypeQuery.jsp"),
})
public class ClassroomTypeAction extends BaseAction implements ModelDriven<ClassroomType>{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ClassroomTypeAction.class);
	
	private ClassroomTypeServiceI classroomTypeService;
	
	private ClassroomType classroomTypeType=new ClassroomType();
	
	public ClassroomType getModel() {
		return classroomTypeType;
	}
	
	/**
	 * 类型列表页面
	 */
	public String classroomTypeType(){
		return "classroomTypeType";
	}
	/**
	 * 类型查询页面
	 */
	public String classroomTypeTypeQuery(){
		return "classroomTypeTypeQuery";
	}
	/**
	 * 类型详细页面
	 */
	public String classroomTypeTypeView(){
		return "classroomTypeTypeView";
	}
	/**
	 * 教室类型新增页面
	 */
	public String classroomTypeTypeAdd(){
		return "classroomTypeTypeAdd";
	}
	/**
	 * 教室类型发布页面
	 */
	public String classroomTypeTypePub(){
		request.setAttribute("classroomTypeTypePub","classroomTypeTypePub");
		return "classroomTypeTypeAdd";
	}
	/**
	 * 
	 * 查询教室类型列表
	 */
	public void datagrid() {
		try {
			DataGrid dataGrid = classroomTypeService.datagrid(classroomTypeType);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询教室类型列表（不验证权限）
	 */
	public void doNotNeedAuth_datagrid() {
		try {
			DataGrid dataGrid = classroomTypeService.datagrid(classroomTypeType);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询教室类型列表(教室类型查询模块)
	 */
	public void datagridQuery() {
		try {
//			classroomTypeType.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES);
			DataGrid dataGrid = classroomTypeService.datagrid(classroomTypeType);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询 教室类型单条（跳转 教室类型详细页面）
	 */
	public String get() {
		try {
			String id=classroomTypeType.getId();
			if(!Util.getInstance().isEmpty(id)){
					ClassroomType classroomTypeType =classroomTypeService.get(id);
					request.setAttribute("classroomTypeType",classroomTypeType);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "classroomTypeTypeView";
	}
	/**
	 * 教室类型修改页面
	 */
	public String classroomTypeTypeEdit(){
		try {
			String id=classroomTypeType.getId();
			if(!Util.getInstance().isEmpty(id)){
				ClassroomType classroomTypeType =classroomTypeService.get(id);
				request.setAttribute("classroomTypeType",classroomTypeType);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "classroomTypeTypeEdit";
	}
	/**
	 * 
	 * 新增教室类型
	 */
	public void add() {
		Json j = new Json();
		try {
			j.setObj(classroomTypeService.add(classroomTypeType,httpSession));
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
	 * 修改教室类型
	 */
	public void edit() {
		Json j = new Json();
		try {
			classroomTypeService.edit(classroomTypeType);
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
	 * 删除教室类型
	 */
	public void delete() {
		Json j = new Json();
		try {
			classroomTypeService.del(classroomTypeType);
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
	 * 发布教室类型
	 */
	public void pub() {
		Json j = new Json();
		try {
			classroomTypeService.pub(classroomTypeType,httpSession);
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
	 * 取消发布教室类型
	 */
	public void cancelPub() {
		Json j = new Json();
		try {
			classroomTypeService.cancelPub(classroomTypeType);
			j.setSuccess(true);
			j.setMsg("取消发布成功!");
		} catch (Exception e) {
			j.setMsg("取消发布失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	
	//下拉列表
	public void combox() {
		List<ClassroomType> list;
		try {
			list = classroomTypeService.combobox(classroomTypeType);
			ClassroomType t=new ClassroomType();
			t.setName("请选择...");
			t.setCode("");
			list.add(0,t);
			writeJson(list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		
	}

	public ClassroomTypeServiceI getClassroomService() {
		return classroomTypeService;
	}
	@Autowired
	public void setClassroomService(ClassroomTypeServiceI classroomTypeService) {
		this.classroomTypeService = classroomTypeService;
	}
}
