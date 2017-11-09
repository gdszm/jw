package com.tlzn.action.eduManage;


import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.eduManage.Dorm;
import com.tlzn.service.eduManage.DormServiceI;
import com.tlzn.util.base.Util;

@Action(value = "dorm", results = {
		@Result(name = "dorm", location = "/general/eduManage/dorm.jsp"),
		@Result(name = "dormView", location = "/general/eduManage/dormView.jsp"),
		@Result(name = "dormAdd", location = "/general/eduManage/dormAdd.jsp"),
		@Result(name = "dormEdit", location = "/general/eduManage/dormEdit.jsp"),
		@Result(name = "dormQuery", location = "/general/eduManage/dormQuery.jsp"),
})
public class DormAction extends BaseAction implements ModelDriven<Dorm>{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(DormAction.class);
	
	private DormServiceI dormService;
	
	private Dorm dorm=new Dorm();
	
	public Dorm getModel() {
		return dorm;
	}
	
	/**
	 * 宿舍列表页面
	 */
	public String dorm(){
		return "dorm";
	}
	/**
	 * 宿舍查询页面
	 */
	public String dormQuery(){
		return "dormQuery";
	}
	/**
	 * 宿舍详细页面
	 */
	public String dormView(){
		return "dormView";
	}
	/**
	 * 宿舍新增页面
	 */
	public String dormAdd(){
		return "dormAdd";
	}
	/**
	 * 宿舍发布页面
	 */
	public String dormPub(){
		request.setAttribute("dormPub","dormPub");
		return "dormAdd";
	}
	/**
	 * 
	 * 查询宿舍列表
	 */
	public void datagrid() {
		try {
			DataGrid dataGrid = dormService.datagrid(dorm);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询宿舍列表（不验证权限）
	 */
	public void doNotNeedAuth_datagrid() {
		try {
			DataGrid dataGrid = dormService.datagrid(dorm);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询宿舍列表(宿舍查询模块)
	 */
	public void datagridQuery() {
		try {
//			dorm.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES);
			DataGrid dataGrid = dormService.datagrid(dorm);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询 宿舍单条（跳转 宿舍详细页面）
	 */
	public String get() {
		try {
			String id=dorm.getId();
			if(!Util.getInstance().isEmpty(id)){
					Dorm dorm =dormService.get(id);
					request.setAttribute("dorm",dorm);
//					String atts= dorm.getAtts();
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
		return "dormView";
	}
	/**
	 * 宿舍修改页面
	 */
	public String dormEdit(){
		try {
			String id=dorm.getId();
			if(!Util.getInstance().isEmpty(id)){
				Dorm dorm =dormService.get(id);
				request.setAttribute("dorm",dorm);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "dormEdit";
	}
	/**
	 * 
	 * 保存宿舍
	 */
	public void save() {
		Json j = new Json();
		try {
			if(Util.getInstance().isEmpty(dorm.getId())){
				j.setObj(dormService.save(dorm,httpSession));
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
	 * 修改宿舍
	 */
	public void edit() {
		Json j = new Json();
		try {
			dormService.edit(dorm);
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
	 * 删除宿舍
	 */
	public void delete() {
		Json j = new Json();
		try {
			dormService.del(dorm);
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
	 * 发布宿舍
	 */
	public void pub() {
		Json j = new Json();
		try {
			dormService.pub(dorm,httpSession);
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
	 * 取消发布宿舍
	 */
	public void cancelPub() {
		Json j = new Json();
		try {
			dormService.cancelPub(dorm);
			j.setSuccess(true);
			j.setMsg("取消发布成功!");
		} catch (Exception e) {
			j.setMsg("取消发布失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	public DormServiceI getDormService() {
		return dormService;
	}
	@Autowired
	public void setDormService(DormServiceI dormService) {
		this.dormService = dormService;
	}
}
