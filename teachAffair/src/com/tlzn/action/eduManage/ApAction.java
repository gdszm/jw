package com.tlzn.action.eduManage;


import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.eduManage.AwardPenalty;
import com.tlzn.service.eduManage.ApServiceI;
import com.tlzn.util.base.Util;

@Action(value = "awardPenalty", results = {
		@Result(name = "awardPenalty", location = "/general/eduManage/awardPenalty.jsp"),
		@Result(name = "awardPenaltyView", location = "/general/eduManage/awardPenaltyView.jsp"),
		@Result(name = "awardPenaltyAdd", location = "/general/eduManage/awardPenaltyAdd.jsp"),
		@Result(name = "awardPenaltyEdit", location = "/general/eduManage/awardPenaltyEdit.jsp"),
		@Result(name = "awardPenaltyAE", location = "/general/eduManage/awardPenaltyAE.jsp"),
		@Result(name = "awardPenaltyQuery", location = "/general/eduManage/awardPenaltyQuery.jsp"),
})
public class ApAction extends BaseAction implements ModelDriven<AwardPenalty>{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ApAction.class);
	
	private ApServiceI awardPenaltyService;
	
	private AwardPenalty awardPenalty=new AwardPenalty();
	
	public AwardPenalty getModel() {
		return awardPenalty;
	}
	
	/**
	 * 奖惩记录列表页面
	 */
	public String awardPenalty(){
		return "awardPenalty";
	}
	/**
	 * 奖惩记录查询页面
	 */
	public String awardPenaltyQuery(){
		return "awardPenaltyQuery";
	}
	/**
	 * 奖惩记录详细页面
	 */
	public String awardPenaltyView(){
		return "awardPenaltyView";
	}
	/**
	 * 奖惩记录新增页面
	 */
	public String awardPenaltyAdd(){
		return "awardPenaltyAdd";
	}
	/**
	 * 奖惩记录发布页面
	 */
	public String awardPenaltyPub(){
		request.setAttribute("awardPenaltyPub","awardPenaltyPub");
		return "awardPenaltyAdd";
	}
	/**
	 * 
	 * 查询奖惩记录列表
	 */
	public void datagrid() {
		try {
			DataGrid dataGrid = awardPenaltyService.datagrid(awardPenalty);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询奖惩记录列表（不验证权限）
	 */
	public void doNotNeedAuth_datagrid() {
		try {
			DataGrid dataGrid = awardPenaltyService.datagrid(awardPenalty);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询奖惩记录列表(奖惩记录查询模块)
	 */
	public void datagridQuery() {
		try {
//			awardPenalty.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES);
			DataGrid dataGrid = awardPenaltyService.datagrid(awardPenalty);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询 奖惩记录单条（跳转 奖惩记录详细页面）
	 */
	public String get() {
		try {
			String id=awardPenalty.getId();
			if(!Util.getInstance().isEmpty(id)){
					AwardPenalty awardPenalty =awardPenaltyService.get(id);
					request.setAttribute("awardPenalty",awardPenalty);
//					String atts= awardPenalty.getAtts();
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
		return "awardPenaltyView";
	}
	/**
	 * 奖惩记录修改页面
	 */
	public String awardPenaltyEdit(){
		try {
			String id=awardPenalty.getId();
			if(!Util.getInstance().isEmpty(id)){
				AwardPenalty awardPenalty =awardPenaltyService.get(id);
				request.setAttribute("awardPenalty",awardPenalty);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "awardPenaltyEdit";
	}
	/**
	 * 奖惩记录添加修改页面
	 */
	public String awardPenaltyAE() {
		try {
			String awardPenaltyNo=awardPenalty.getId();
			if(!Util.getInstance().isEmpty(awardPenaltyNo)){
				AwardPenalty awardPenalty=awardPenaltyService.get(awardPenaltyNo);
				request.setAttribute("awardPenalty",awardPenalty);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "awardPenaltyAE";
	}
	/**
	 * 
	 * 保存奖惩记录
	 */
	public void save() {
		Json j = new Json();
		try {
			j.setObj(awardPenaltyService.save(awardPenalty,httpSession));
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
	 * 更新或新增奖惩记录
	 */
	public void upDateOrAdd() {
		Json j = new Json();
		try {
			j.setObj(awardPenaltyService.upDateOrAdd(awardPenalty,httpSession));
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
	 * 修改奖惩记录
	 */
	public void edit() {
		Json j = new Json();
		try {
			awardPenaltyService.edit(awardPenalty);
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
	 * 删除奖惩记录
	 */
	public void delete() {
		Json j = new Json();
		try {
			awardPenaltyService.del(awardPenalty);
			j.setSuccess(true);
			j.setMsg("删除成功!");
		} catch (Exception e) {
			j.setMsg("删除失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	public ApServiceI getAwardPenaltyService() {
		return awardPenaltyService;
	}
	@Autowired
	public void setAwardPenaltyService(ApServiceI awardPenaltyService) {
		this.awardPenaltyService = awardPenaltyService;
	}
}
