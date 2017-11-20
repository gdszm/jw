package com.tlzn.action.eduManage;


import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.eduManage.Profess;
import com.tlzn.pageModel.eduManage.Teacher;
import com.tlzn.service.eduManage.ProfessServiceI;
import com.tlzn.service.eduManage.TeacherServiceI;
import com.tlzn.util.base.Util;

@Action(value = "profess", results = {
		@Result(name = "profess", location = "/general/eduManage/profess.jsp"),
		@Result(name = "professList", location = "/general/eduManage/professList.jsp"),
		@Result(name = "professView", location = "/general/eduManage/professView.jsp"),
		@Result(name = "professAdd", location = "/general/eduManage/professAdd.jsp"),
		@Result(name = "professEdit", location = "/general/eduManage/professEdit.jsp"),
		@Result(name = "professQuery", location = "/general/eduManage/professQuery.jsp"),
		//班级选择页面
		@Result(name = "classSelect", location = "/general/eduManage/classesSelect.jsp"),
		//班级选择页面
		@Result(name = "classSelect", location = "/general/eduManage/classesSelect.jsp"),
		//班级选择页面
		@Result(name = "classSelect", location = "/general/eduManage/classesSelect.jsp"),
		//授课教师新增页面
		@Result(name = "professTeacherAdd", location = "/general/eduManage/professTeacherAdd.jsp"),

})
public class ProfessAction extends BaseAction implements ModelDriven<Profess>{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ProfessAction.class);
	
	private ProfessServiceI professService;
	private TeacherServiceI teacherService;
	
	private Profess profess=new Profess();
	
	public Profess getModel() {
		return profess;
	}
	/**
	 * 授课统计页面
	 */
	public String profess(){
		return "profess";
	}
	/**
	 * 授课列表页面
	 */
	public String professList(){
		String teacherId =profess.getTeacherId();
		request.setAttribute("teacherId", teacherId);
		return "professList";
	}
	/**
	 * 授课查询页面
	 */
	public String professQuery(){
		return "professQuery";
	}
	/**
	 * 授课详细页面
	 */
	public String professView(){
		return "professView";
	}
	/**
	 * 授课教室新增页面
	 */
	public String professTeacherAddPage(){
		return "professTeacherAdd";
	}
	/**
	 * 授课新增页面
	 */
	public String professAdd(){
		try {
			String id=profess.getTeacherId();
			if(!Util.getInstance().isEmpty(id)){
				Teacher teacher=teacherService.get(id);
				if(!Util.getInstance().isEmpty(teacher)) {
					profess.setTeacherId(teacher.getId());
					profess.setTeacherName(teacher.getName());
					profess.setSex(teacher.getSex());
					request.setAttribute("profess",profess);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "professAdd";
	}
	
	/**
	 * 弹出学生选择页面
	 */
	public String stuSelect(){
//		request.setAttribute("classId",profess.getClassId());
		return "stuSelect";
	}
	/**
	 * 弹出班级选择页面
	 */
	public String classSelect(){
		return "classSelect";
	}
	/**
	 * 弹出档案选择页面
	 */
	public String archSelect(){
		return "archSelect";
	}
	
	/**
	 * 
	 * 查询授课列表
	 */
	public void datagrid() {
		try {
			DataGrid dataGrid = professService.datagrid(profess);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 统计各位教师授课信息
	 */
	public void datagridCount() {
		try {
			DataGrid dataGrid = professService.datagridCount(profess);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询授课列表（不验证权限）
	 */
	public void doNotNeedAuth_datagrid() {
		try {
			DataGrid dataGrid = professService.datagrid(profess);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询授课列表(授课查询模块)
	 */
	public void datagridQuery() {
		try {
			DataGrid dataGrid = professService.datagrid(profess);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询 授课单条（跳转 授课详细页面）
	 */
	public String get() {
		try {
			String id=profess.getId();
			if(!Util.getInstance().isEmpty(id)){
					Profess profess =professService.get(id);
					request.setAttribute("profess",profess);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "professView";
	}
	/**
	 * 授课修改页面
	 */
	public String professEdit(){
		try {
			String id=profess.getId();
			if(!Util.getInstance().isEmpty(id)){
				Profess profess =professService.get(id);
				request.setAttribute("profess",profess);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "professEdit";
	}
	/**
	 * 
	 * 新增授课教师
	 */
	public void professTeacherAdd() {
		Json j = new Json();
		try {
			Util util=Util.getInstance();
			DataGrid dataGrid = professService.datagrid(profess);
			List rows = dataGrid.getRows();
			for (Object obj : rows) {
				Profess p= (Profess)obj;
				if(!util.isEmpty(profess.getTeacherId()) && profess.getTeacherId().equals(p.getTeacherId())){
					j.setSuccess(true);
					j.setMsg("该授课教师已经存在，请查询这条记录管理授课列表就行!");
					writeJson(j);
					return;
				}
			}
			j.setObj(professService.professTeacherAdd(profess,httpSession));
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
	 * 新增授课
	 */
	public void add() {
		Json j = new Json();
		Util util=Util.getInstance();
		try {
//			DataGrid dataGrid = professService.datagrid(profess);
//			List rows = dataGrid.getRows();
//			for (Object obj : rows) {
//				Profess p= (Profess)obj;
//				if(!util.isEmpty(profess.getTeacherId()) && profess.getTeacherId().equals(p.getTeacherId())){
//					j.setSuccess(true);
//					j.setMsg("该授课教师已经存在，请查询这条记录管理授课列表就行!");
//					writeJson(j);
//					return;
//				}
//			}
			j.setObj(professService.add(profess,httpSession));
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
	 * 修改授课
	 */
	public void edit() {
		Json j = new Json();
		try {
			professService.edit(profess);
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
	 * 删除授课教师
	 */
	public void professTeacherdel() {
		Json j = new Json();
		try {
			professService.professTeacherdel(profess);
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
	 * 删除授课
	 */
	public void delete() {
		Json j = new Json();
		try {
			professService.del(profess);
			j.setSuccess(true);
			j.setMsg("删除成功!");
		} catch (Exception e) {
			j.setMsg("删除失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	
	public ProfessServiceI getProfessService() {
		return professService;
	}
	@Autowired
	public void setProfessService(ProfessServiceI professService) {
		this.professService = professService;
	}
	
	public TeacherServiceI getTeacherService() {
		return teacherService;
	}
	@Autowired
	public void setTeacherService(TeacherServiceI teacherService) {
		this.teacherService = teacherService;
	}
}
