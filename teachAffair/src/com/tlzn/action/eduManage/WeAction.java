package com.tlzn.action.eduManage;


import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.eduManage.WorkExp;
import com.tlzn.service.eduManage.WeServiceI;
import com.tlzn.util.base.Util;

@Action(value = "workExp", results = {
		@Result(name = "workExp", location = "/general/eduManage/workExp.jsp"),
		@Result(name = "workExpView", location = "/general/eduManage/workExpView.jsp"),
		@Result(name = "workExpAdd", location = "/general/eduManage/workExpAdd.jsp"),
		@Result(name = "workExpEdit", location = "/general/eduManage/workExpEdit.jsp"),
		@Result(name = "workExpAE", location = "/general/eduManage/workExpAE.jsp"),
		@Result(name = "workExpQuery", location = "/general/eduManage/workExpQuery.jsp"),
})
public class WeAction extends BaseAction implements ModelDriven<WorkExp>{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(WeAction.class);
	
	private WeServiceI workExpService;
	
	private WorkExp workExp=new WorkExp();
	
	public WorkExp getModel() {
		return workExp;
	}
	
	/**
	 * 家庭成员列表页面
	 */
	public String workExp(){
		return "workExp";
	}
	/**
	 * 家庭成员查询页面
	 */
	public String workExpQuery(){
		return "workExpQuery";
	}
	/**
	 * 家庭成员详细页面
	 */
	public String workExpView(){
		return "workExpView";
	}
	/**
	 * 家庭成员新增页面
	 */
	public String workExpAdd(){
		return "workExpAdd";
	}
	/**
	 * 家庭成员发布页面
	 */
	public String workExpPub(){
		request.setAttribute("workExpPub","workExpPub");
		return "workExpAdd";
	}
	/**
	 * 
	 * 查询家庭成员列表
	 */
	public void datagrid() {
		try {
			DataGrid dataGrid = workExpService.datagrid(workExp);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询家庭成员列表（不验证权限）
	 */
	public void doNotNeedAuth_datagrid() {
		try {
			DataGrid dataGrid = workExpService.datagrid(workExp);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询家庭成员列表(家庭成员查询模块)
	 */
	public void datagridQuery() {
		try {
//			workExp.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES);
			DataGrid dataGrid = workExpService.datagrid(workExp);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询 家庭成员单条（跳转 家庭成员详细页面）
	 */
	public String get() {
		try {
			String id=workExp.getId();
			if(!Util.getInstance().isEmpty(id)){
					WorkExp workExp =workExpService.get(id);
					request.setAttribute("workExp",workExp);
//					String atts= workExp.getAtts();
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
		return "workExpView";
	}
	/**
	 * 家庭成员修改页面
	 */
	public String workExpEdit(){
		try {
			String id=workExp.getId();
			if(!Util.getInstance().isEmpty(id)){
				WorkExp workExp =workExpService.get(id);
				request.setAttribute("workExp",workExp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "workExpEdit";
	}
	/**
	 * 家庭成员添加修改页面
	 */
	public String workExpAE() {
		try {
			String workExpNo=workExp.getId();
			if(!Util.getInstance().isEmpty(workExpNo)){
				WorkExp workExp=workExpService.get(workExpNo);
				request.setAttribute("workExp",workExp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "workExpAE";
	}
	/**
	 * 
	 * 保存家庭成员
	 */
	public void save() {
		Json j = new Json();
		try {
			j.setObj(workExpService.save(workExp,httpSession));
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
	 * 更新或新增家庭成员
	 */
	public void upDateOrAdd() {
		Json j = new Json();
		try {
			j.setObj(workExpService.upDateOrAdd(workExp,httpSession));
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
	 * 修改家庭成员
	 */
	public void edit() {
		Json j = new Json();
		try {
			workExpService.edit(workExp);
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
	 * 删除家庭成员
	 */
	public void delete() {
		Json j = new Json();
		try {
			workExpService.del(workExp);
			j.setSuccess(true);
			j.setMsg("删除成功!");
		} catch (Exception e) {
			j.setMsg("删除失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	public WeServiceI getWorkExpService() {
		return workExpService;
	}
	@Autowired
	public void setWorkExpService(WeServiceI workExpService) {
		this.workExpService = workExpService;
	}
}
