package com.tlzn.action.eduManage;


import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.eduManage.EduExp;
import com.tlzn.service.eduManage.EeServiceI;
import com.tlzn.util.base.Util;

@Action(value = "eduExp", results = {
		@Result(name = "eduExp", location = "/general/eduManage/eduExp.jsp"),
		@Result(name = "eduExpView", location = "/general/eduManage/eduExpView.jsp"),
		@Result(name = "eduExpAdd", location = "/general/eduManage/eduExpAdd.jsp"),
		@Result(name = "eduExpEdit", location = "/general/eduManage/eduExpEdit.jsp"),
		@Result(name = "eduExpAE", location = "/general/eduManage/eduExpAE.jsp"),
		@Result(name = "eduExpQuery", location = "/general/eduManage/eduExpQuery.jsp"),
})
public class EeAction extends BaseAction implements ModelDriven<EduExp>{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(EeAction.class);
	
	private EeServiceI eduExpService;
	
	private EduExp eduExp=new EduExp();
	
	public EduExp getModel() {
		return eduExp;
	}
	
	/**
	 * 教育经历列表页面
	 */
	public String eduExp(){
		return "eduExp";
	}
	/**
	 * 教育经历查询页面
	 */
	public String eduExpQuery(){
		return "eduExpQuery";
	}
	/**
	 * 教育经历详细页面
	 */
	public String eduExpView(){
		return "eduExpView";
	}
	/**
	 * 教育经历新增页面
	 */
	public String eduExpAdd(){
		return "eduExpAdd";
	}
	/**
	 * 教育经历发布页面
	 */
	public String eduExpPub(){
		request.setAttribute("eduExpPub","eduExpPub");
		return "eduExpAdd";
	}
	/**
	 * 
	 * 查询教育经历列表
	 */
	public void datagrid() {
		try {
			DataGrid dataGrid = eduExpService.datagrid(eduExp);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询教育经历列表（不验证权限）
	 */
	public void doNotNeedAuth_datagrid() {
		try {
			DataGrid dataGrid = eduExpService.datagrid(eduExp);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询教育经历列表(教育经历查询模块)
	 */
	public void datagridQuery() {
		try {
//			eduExp.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES);
			DataGrid dataGrid = eduExpService.datagrid(eduExp);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询 教育经历单条（跳转 教育经历详细页面）
	 */
	public String get() {
		try {
			String id=eduExp.getId();
			if(!Util.getInstance().isEmpty(id)){
					EduExp eduExp =eduExpService.get(id);
					request.setAttribute("eduExp",eduExp);
//					String atts= eduExp.getAtts();
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
		return "eduExpView";
	}
	/**
	 * 教育经历修改页面
	 */
	public String eduExpEdit(){
		try {
			String id=eduExp.getId();
			if(!Util.getInstance().isEmpty(id)){
				EduExp eduExp =eduExpService.get(id);
				request.setAttribute("eduExp",eduExp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "eduExpEdit";
	}
	/**
	 * 教育经历添加修改页面
	 */
	public String eduExpAE() {
		try {
			String eduExpNo=eduExp.getId();
			if(!Util.getInstance().isEmpty(eduExpNo)){
				EduExp eduExp=eduExpService.get(eduExpNo);
				request.setAttribute("eduExp",eduExp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "eduExpAE";
	}
	/**
	 * 
	 * 保存教育经历
	 */
	public void save() {
		Json j = new Json();
		try {
			j.setObj(eduExpService.save(eduExp,httpSession));
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
	 * 更新或新增教育经历
	 */
	public void upDateOrAdd() {
		Json j = new Json();
		try {
			j.setObj(eduExpService.upDateOrAdd(eduExp,httpSession));
			j.setSuccess(true);
			j.setMsg("操作成功!");
		} catch (Exception e) {
			j.setMsg("操作失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	
	/**
	 * 
	 * 修改教育经历
	 */
	public void edit() {
		Json j = new Json();
		try {
			eduExpService.edit(eduExp);
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
	 * 删除教育经历
	 */
	public void delete() {
		Json j = new Json();
		try {
			eduExpService.del(eduExp);
			j.setSuccess(true);
			j.setMsg("删除成功!");
		} catch (Exception e) {
			j.setMsg("删除失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	public EeServiceI getEduExpService() {
		return eduExpService;
	}
	@Autowired
	public void setEduExpService(EeServiceI eduExpService) {
		this.eduExpService = eduExpService;
	}
}
