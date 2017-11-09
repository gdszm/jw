package com.tlzn.action.eduManage;


import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.eduManage.SelCourse;
import com.tlzn.service.eduManage.SelCourseServiceI;
import com.tlzn.util.base.Util;

@Action(value = "selCourse", results = {
		@Result(name = "selCourse", location = "/general/eduManage/selCourse.jsp"),
		@Result(name = "selCourseView", location = "/general/eduManage/selCourseView.jsp"),
		@Result(name = "selCourseAdd", location = "/general/eduManage/selCourseAdd.jsp"),
		@Result(name = "selCourseEdit", location = "/general/eduManage/selCourseEdit.jsp"),
		@Result(name = "selCourseQuery", location = "/general/eduManage/selCourseQuery.jsp")
})
public class SelCourseAction extends BaseAction implements ModelDriven<SelCourse>{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(SelCourseAction.class);
	
	private SelCourseServiceI selCourseService;
	
	private SelCourse selCourse=new SelCourse();
	
	public SelCourse getModel() {
		return selCourse;
	}
	
	/**
	 * 选课列表页面
	 */
	public String selCourse(){
		return "selCourse";
	}
	/**
	 * 选课查询页面
	 */
	public String selCourseQuery(){
		return "selCourseQuery";
	}
	/**
	 * 选课详细页面
	 */
	public String selCourseView(){
		return "selCourseView";
	}
	/**
	 * 选课新增页面
	 */
	public String selCourseAdd(){
		return "selCourseAdd";
	}
	/**
	 * 选课发布页面
	 */
	public String selCoursePub(){
		request.setAttribute("selCoursePub","selCoursePub");
		return "selCourseAdd";
	}
	/**
	 * 
	 * 查询选课列表
	 */
	public void datagrid() {
		try {
			DataGrid dataGrid = selCourseService.datagrid(selCourse);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询选课列表（不验证权限）
	 */
	public void doNotNeedAuth_datagrid() {
		try {
			DataGrid dataGrid = selCourseService.datagrid(selCourse);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询选课列表(选课查询模块)
	 */
	public void datagridQuery() {
		try {
//			selCourse.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES);
			DataGrid dataGrid = selCourseService.datagrid(selCourse);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询 选课单条（跳转 选课详细页面）
	 */
	public String get() {
		try {
			String id=selCourse.getId();
			if(!Util.getInstance().isEmpty(id)){
					SelCourse selCourse =selCourseService.get(id);
					request.setAttribute("selCourse",selCourse);
//					String atts= selCourse.getAtts();
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
		return "selCourseView";
	}
	/**
	 * 选课修改页面
	 */
	public String selCourseEdit(){
		try {
			String id=selCourse.getId();
			if(!Util.getInstance().isEmpty(id)){
				SelCourse selCourse =selCourseService.get(id);
				request.setAttribute("selCourse",selCourse);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "selCourseEdit";
	}
	/**
	 * 
	 * 保存选课
	 */
	public void save() {
		Json j = new Json();
		try {
			if(Util.getInstance().isEmpty(selCourse.getId())){
				j.setObj(selCourseService.save(selCourse,httpSession));
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
	 * 修改选课
	 */
	public void edit() {
		Json j = new Json();
		try {
			selCourseService.edit(selCourse);
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
	 * 删除选课
	 */
	public void delete() {
		Json j = new Json();
		try {
			selCourseService.del(selCourse);
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
	 * 发布选课
	 */
	public void pub() {
		Json j = new Json();
		try {
			selCourseService.pub(selCourse,httpSession);
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
	 * 取消发布选课
	 */
	public void cancelPub() {
		Json j = new Json();
		try {
			selCourseService.cancelPub(selCourse);
			j.setSuccess(true);
			j.setMsg("取消发布成功!");
		} catch (Exception e) {
			j.setMsg("取消发布失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	public SelCourseServiceI getSelCourseService() {
		return selCourseService;
	}
	@Autowired
	public void setSelCourseService(SelCourseServiceI selCourseService) {
		this.selCourseService = selCourseService;
	}
}
