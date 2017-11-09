package com.tlzn.action.eduManage;


import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.eduManage.OpenCourse;
import com.tlzn.service.eduManage.OpenCourseServiceI;
import com.tlzn.util.base.Util;

@Action(value = "openCourse", results = {
		@Result(name = "openCourse", location = "/general/eduManage/openCourse.jsp"),
		@Result(name = "openCourseView", location = "/general/eduManage/openCourseView.jsp"),
		@Result(name = "openCourseAdd", location = "/general/eduManage/openCourseAdd.jsp"),
		@Result(name = "openCourseEdit", location = "/general/eduManage/openCourseEdit.jsp"),
		@Result(name = "openCourseQuery", location = "/general/eduManage/openCourseQuery.jsp"),
})
public class OpenCourseAction extends BaseAction implements ModelDriven<OpenCourse>{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(OpenCourseAction.class);
	
	private OpenCourseServiceI openCourseService;
	
	private OpenCourse openCourse=new OpenCourse();
	
	public OpenCourse getModel() {
		return openCourse;
	}
	
	/**
	 * 开课列表页面
	 */
	public String openCourse(){
		return "openCourse";
	}
	/**
	 * 开课查询页面
	 */
	public String openCourseQuery(){
		return "openCourseQuery";
	}
	/**
	 * 开课详细页面
	 */
	public String openCourseView(){
		return "openCourseView";
	}
	/**
	 * 开课新增页面
	 */
	public String openCourseAdd(){
		return "openCourseAdd";
	}
	/**
	 * 开课发布页面
	 */
	public String openCoursePub(){
		request.setAttribute("openCoursePub","openCoursePub");
		return "openCourseAdd";
	}
	/**
	 * 
	 * 查询开课列表
	 */
	public void datagrid() {
		try {
			DataGrid dataGrid = openCourseService.datagrid(openCourse);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询开课列表（不验证权限）
	 */
	public void doNotNeedAuth_datagrid() {
		try {
			DataGrid dataGrid = openCourseService.datagrid(openCourse);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询开课列表(开课查询模块)
	 */
	public void datagridQuery() {
		try {
//			openCourse.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES);
			DataGrid dataGrid = openCourseService.datagrid(openCourse);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询 开课单条（跳转 开课详细页面）
	 */
	public String get() {
		try {
			String id=openCourse.getId();
			if(!Util.getInstance().isEmpty(id)){
					OpenCourse openCourse =openCourseService.get(id);
					request.setAttribute("openCourse",openCourse);
//					String atts= openCourse.getAtts();
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
		return "openCourseView";
	}
	/**
	 * 开课修改页面
	 */
	public String openCourseEdit(){
		try {
			String id=openCourse.getId();
			if(!Util.getInstance().isEmpty(id)){
				OpenCourse openCourse =openCourseService.get(id);
				request.setAttribute("openCourse",openCourse);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "openCourseEdit";
	}
	/**
	 * 
	 * 保存开课
	 */
	public void save() {
		Json j = new Json();
		try {
			if(Util.getInstance().isEmpty(openCourse.getId())){
				j.setObj(openCourseService.save(openCourse,httpSession));
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
	 * 修改开课
	 */
	public void edit() {
		Json j = new Json();
		try {
			openCourseService.edit(openCourse);
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
	 * 删除开课
	 */
	public void delete() {
		Json j = new Json();
		try {
			openCourseService.del(openCourse);
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
	 * 发布开课
	 */
	public void pub() {
		Json j = new Json();
		try {
			openCourseService.pub(openCourse,httpSession);
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
	 * 取消发布开课
	 */
	public void cancelPub() {
		Json j = new Json();
		try {
			openCourseService.cancelPub(openCourse);
			j.setSuccess(true);
			j.setMsg("取消发布成功!");
		} catch (Exception e) {
			j.setMsg("取消发布失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	public OpenCourseServiceI getOpenCourseService() {
		return openCourseService;
	}
	@Autowired
	public void setOpenCourseService(OpenCourseServiceI openCourseService) {
		this.openCourseService = openCourseService;
	}
}
