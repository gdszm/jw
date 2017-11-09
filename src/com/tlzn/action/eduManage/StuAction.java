package com.tlzn.action.eduManage;


import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.eduManage.Stu;
import com.tlzn.service.eduManage.StuServiceI;
import com.tlzn.util.base.Util;

@Action(value = "stu", results = {
		@Result(name = "stu", location = "/general/eduManage/stu.jsp"),
		@Result(name = "stuView", location = "/general/eduManage/stuView.jsp"),
		//新建学生
		@Result(name = "stuPub", location = "/general/eduManage/stuPub.jsp"),
		@Result(name = "stuAdd", location = "/general/eduManage/stuAdd.jsp"),
		@Result(name = "stuEdit", location = "/general/eduManage/stuEdit.jsp"),
		@Result(name = "stuAE", location = "/general/eduManage/stuAE.jsp"),
		@Result(name = "stuQuery", location = "/general/eduManage/stuQuery.jsp"),
		//学生选择页面
		@Result(name = "stuSelect", location = "/general/eduManage/stuSelect.jsp"),
		//班级选择页面
		@Result(name = "classSelect", location = "/general/eduManage/classesSelect.jsp"),
		//档案选择页面
		@Result(name = "archSelect", location = "/general/eduManage/archSelect.jsp")
})
public class StuAction extends BaseAction implements ModelDriven<Stu>{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(StuAction.class);
	
	private StuServiceI stuService;
	
	private Stu stu=new Stu();
	
	public Stu getModel() {
		return stu;
	}
	
	/**
	 * 学生列表页面
	 */
	public String stu(){
		return "stu";
	}
	/**
	 * 学生查询页面
	 */
	public String stuQuery(){
		return "stuQuery";
	}
	/**
	 * 学生详细页面
	 */
	public String stuView(){
		try {
			String stuNo=stu.getStuNo();
			if(!Util.getInstance().isEmpty(stuNo)){
				Stu stu=stuService.get(stuNo);
				request.setAttribute("stu",stu);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "stuView";
	}
	/**
	 * 学生新增页面
	 */
	public String stuAdd(){
		return "stuAdd";
	}
	/**
	 * 学生新建页面
	 */
	public String stuPub(){
		return "stuPub";
	}
	/**
	 * 弹出学生选择页面
	 */
	public String stuSelect(){
		request.setAttribute("classId",stu.getClassId());
		return "stuSelect";
	}
	/**
	 * 班级选择页面
	 */
	public String classSelect(){
		return "classSelect";
	}
	/**
	 * 档案选择页面
	 */
	public String archSelect(){
		return "archSelect";
	}
	/**
	 * 学生添加修改页面
	 */
	public String stuAE() {
		try {
			String stuNo=stu.getStuNo();
			if(!Util.getInstance().isEmpty(stuNo)){
				Stu stu=stuService.get(stuNo);
				request.setAttribute("stu",stu);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "stuAE";
	}
	/**
	 * 
	 * 查询学生列表
	 */
	public void datagrid() {
		try {
			DataGrid dataGrid = stuService.datagrid(stu);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询学生列表（不验证权限）
	 */
	public void doNotNeedAuth_datagrid() {
		try {
			DataGrid dataGrid = stuService.datagrid(stu);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询学生列表(学生查询模块)
	 */
	public void datagridQuery() {
		try {
//			stu.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES);
			DataGrid dataGrid = stuService.datagrid(stu);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询 学生单条（跳转 学生详细页面）
	 */
	public String get() {
		try {
			String id=stu.getStuNo();
			if(!Util.getInstance().isEmpty(id)){
					Stu stu =stuService.get(id);
					request.setAttribute("stu",stu);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "stuView";
	}
	/**
	 * 学生修改页面
	 */
	public String stuEdit(){
		try {
			String stuNo=stu.getStuNo();
			if(!Util.getInstance().isEmpty(stuNo)){
				Stu stu=stuService.get(stuNo);
				request.setAttribute("stu",stu);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "stuEdit";
	}
	/**
	 * 
	 * 新增学生
	 */
	public void add() {
		Json j = new Json();
		try {
			j.setObj(stuService.save(stu,httpSession));
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
	 * 修改学生
	 */
	public void edit() {
		Json j = new Json();
		try {
			stuService.edit(stu,httpSession);
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
	 * 
	 * 新增学生档案
	 */
	public void addArch() {
		Json j = new Json();
		try {
			stuService.archAdd(stu);
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
	 * 修改学生档案
	 */
	public void editArch() {
		Json j = new Json();
		try {
			stuService.archEdit(stu);
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
	 * 删除学生
	 */
	public void delete() {
		Json j = new Json();
		try {
			stuService.del(stu);
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
	 * 发布学生
	 */
	public void pub() {
		Json j = new Json();
		try {
			stuService.pub(stu,httpSession);
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
	 * 取消发布学生
	 */
	public void cancelPub() {
		Json j = new Json();
		try {
			stuService.cancelPub(stu);
			j.setSuccess(true);
			j.setMsg("取消发布成功!");
		} catch (Exception e) {
			j.setMsg("取消发布失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	public StuServiceI getStuService() {
		return stuService;
	}
	@Autowired
	public void setStuService(StuServiceI stuService) {
		this.stuService = stuService;
	}
}
