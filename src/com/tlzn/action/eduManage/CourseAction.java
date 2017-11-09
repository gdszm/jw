package com.tlzn.action.eduManage;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.eduManage.Course;
import com.tlzn.service.eduManage.CourseServiceI;
import com.tlzn.util.base.Util;

@Action(value = "course", results = {
		@Result(name = "course", location = "/general/eduManage/course.jsp"),
		@Result(name = "courseView", location = "/general/eduManage/courseView.jsp"),
		//新建课程
		@Result(name = "coursePub", location = "/general/eduManage/coursePub.jsp"),
		@Result(name = "courseAdd", location = "/general/eduManage/courseAdd.jsp"),
		@Result(name = "courseEdit", location = "/general/eduManage/courseEdit.jsp"),
		@Result(name = "courseAE", location = "/general/eduManage/courseAE.jsp"),
		@Result(name = "courseQuery", location = "/general/eduManage/courseQuery.jsp"),
		//课程选择页面
		@Result(name = "courseSelect", location = "/general/eduManage/courseSelect.jsp"),
		//班级选择页面
		@Result(name = "classSelect", location = "/general/eduManage/classesSelect.jsp"),
})
public class CourseAction extends BaseAction implements ModelDriven<Course>{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CourseAction.class);
	
	private CourseServiceI courseService;
	
	private Course course=new Course();
	
	public Course getModel() {
		return course;
	}
	
	/**
	 * 课程列表页面
	 */
	public String course(){
		return "course";
	}
	/**
	 * 课程查询页面
	 */
	public String courseQuery(){
		return "courseQuery";
	}
	/**
	 * 课程详细页面
	 */
	public String courseView(){
		try {
			String courseNo=course.getId();
			if(!Util.getInstance().isEmpty(courseNo)){
				Course course=courseService.get(courseNo);
				request.setAttribute("course",course);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "courseView";
	}
	/**
	 * 课程新增页面
	 */
	public String courseAdd(){
		return "courseAdd";
	}
	/**
	 * 课程新建页面
	 */
	public String coursePub(){
		return "coursePub";
	}
	/**
	 * 弹出课程选择页面
	 */
	public String courseSelect(){
//		request.setAttribute("classId",course.getId());
		return "courseSelect";
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
	 * 课程添加修改页面
	 */
	public String courseAE() {
		try {
			String courseNo=course.getId();
			if(!Util.getInstance().isEmpty(courseNo)){
				Course course=courseService.get(courseNo);
				request.setAttribute("course",course);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "courseAE";
	}
	/**
	 * 
	 * 查询课程列表
	 */
	public void datagrid() {
		try {
			DataGrid dataGrid = courseService.datagrid(course);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询课程列表（不验证权限）
	 */
	public void doNotNeedAuth_datagrid() {
		try {
			DataGrid dataGrid = courseService.datagrid(course);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询课程列表(课程查询模块)
	 */
	public void datagridQuery() {
		try {
			DataGrid dataGrid = courseService.datagrid(course);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询 课程单条（跳转 课程详细页面）
	 */
	public String get() {
		try {
			String id=course.getId();
			if(!Util.getInstance().isEmpty(id)){
					Course course =courseService.get(id);
					request.setAttribute("course",course);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "courseView";
	}
	/**
	 * 课程修改页面
	 */
	public String courseEdit(){
		try {
			String courseNo=course.getId();
			if(!Util.getInstance().isEmpty(courseNo)){
				Course course=courseService.get(courseNo);
				request.setAttribute("course",course);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "courseEdit";
	}
	/**
	 * 
	 * 新增课程
	 */
	public void add() {
		Json j = new Json();
		try {
			j.setObj(courseService.save(course,httpSession));
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
	 * 修改课程
	 */
	public void edit() {
		Json j = new Json();
		try {
			courseService.edit(course);
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
	 * 删除课程
	 */
	public void delete() {
		Json j = new Json();
		try {
			courseService.del(course);
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
	 * 发布课程
	 */
	public void pub() {
		Json j = new Json();
		try {
			courseService.pub(course,httpSession);
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
	 * 取消发布课程
	 */
	public void cancelPub() {
		Json j = new Json();
		try {
			courseService.cancelPub(course);
			j.setSuccess(true);
			j.setMsg("取消发布成功!");
		} catch (Exception e) {
			j.setMsg("取消发布失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	public CourseServiceI getCourseService() {
		return courseService;
	}
	@Autowired
	public void setCourseService(CourseServiceI courseService) {
		this.courseService = courseService;
	}
}
