package com.tlzn.action.dailywork;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.dailywork.FileData;
import com.tlzn.service.dailywork.ReferMaterialServiceI;
import com.tlzn.util.base.Util;

@Action(value = "referMaterial", results = {
		@Result(name = "referMaterialManage", location = "/general/dailywork/referMaterialManage.jsp"),
		
		@Result(name = "referMaterialView", location = "/general/dailywork/referMaterialView.jsp"),
		@Result(name = "referMaterialAdd", location = "/general/dailywork/referMaterialAdd.jsp"),
		@Result(name = "referMaterialEdit", location = "/general/dailywork/referMaterialEdit.jsp"),
		
		@Result(name = "referMaterialQuery", location = "/general/dailywork/referMaterialQuery.jsp"),
})
public class ReferMaterialAction extends BaseAction implements ModelDriven<FileData>{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ReferMaterialAction.class);
	
	private ReferMaterialServiceI referMaterialService;
	
	private FileData referMaterial=new FileData();
	public FileData getModel() {
		return referMaterial;
	}
	
	/**
	 * 会议文件管理页面
	 */
	public String referMaterialManage(){
		return "referMaterialManage";
	}
	/**
	 * 会议文件查询页面
	 */
	public String referMaterialQuery(){
		return "referMaterialQuery";
	}
	/**
	 * 会议文件详细页面
	 */
	public String referMaterialView(){
		return "referMaterialView";
	}
	/**
	 * 会议文件新增页面
	 */
	public String referMaterialAdd(){
//		Object obj=request.getAttribute("fromtype");
//		String fromtype=obj==null?"":(String)obj;
//		referMaterial.setFromtype(fromtype);
//		request.setAttribute("referMaterial",referMaterial);
		return "referMaterialAdd";
	}
	/**
	 * 
	 * 查询会议文件列表
	 */
	public void datagrid() {
		try {
//			String secondaryId=((Seco) httpSession.getAttribute("sessionSeco")).getSecondaryId();
//			referMaterial.setSecondaryId(secondaryId);
			DataGrid dataGrid = referMaterialService.datagrid(referMaterial);
			
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询会议文件列表
	 */
	public void doNotNeedAuth_datagrid() {
		try {
//			String secondaryId=((Seco) httpSession.getAttribute("sessionSeco")).getSecondaryId();
//			referMaterial.setSecondaryId(secondaryId);
			DataGrid dataGrid = referMaterialService.datagrid(referMaterial);
			
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询会议文件列表(通知查询模块)
	 */
	public void datagridQuery() {
		try {
			DataGrid dataGrid = referMaterialService.datagrid(referMaterial);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询 会议文件单条（跳转 会议文件详细页面）
	 */
	public String get() {
		try {
			String id=referMaterial.getDataId();
			if(!Util.getInstance().isEmpty(id)){
					FileData referMaterial =referMaterialService.get(id);
					request.setAttribute("referMaterial",referMaterial);
					String atts= referMaterial.getAtts();
					List<String> attList = null;
					if(!Util.getInstance().isEmpty(atts)){
						attList = new ArrayList<String>();
						String attArry[] = atts.split(",");
						for (String att : attArry) {
							attList.add(att);
						}
						request.setAttribute("attList",attList);
					}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "referMaterialView";
	}
	/**
	 * 会议文件修改页面
	 */
	public String referMaterialEdit(){
		try {
			String id=referMaterial.getDataId();
			if(!Util.getInstance().isEmpty(id)){
					FileData referMaterial =referMaterialService.get(id);
					request.setAttribute("referMaterial",referMaterial);
					
					String atts= referMaterial.getAtts();
					List<String> attList = null;
					if(!Util.getInstance().isEmpty(atts)){
						attList = new ArrayList<String>();
						String attArry[] = atts.split(",");
						for (String att : attArry) {
							attList.add(att);
						}
						request.setAttribute("attList",attList);
					}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "referMaterialEdit";
	}
	/**
	 * 
	 * 保存会议文件
	 */
	public void save() {
		Json j = new Json();
		try {
			if(Util.getInstance().isEmpty(referMaterial.getDataId())){
				j.setObj(referMaterialService.save(referMaterial,httpSession));
			} else {
				j.setObj(referMaterialService.saveForUpate(referMaterial,httpSession));
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
	 * 更新或新增会议文件
	 */
	public void upDateOrAdd() {
		Json j = new Json();
		try {
			j.setObj(referMaterialService.upDateOrAdd(referMaterial,httpSession));
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
	 * 修改会议文件
	 */
	public void edit() {
		Json j = new Json();
		try {
			referMaterialService.edit(referMaterial);
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
	 * 删除会议文件
	 */
	public void delete() {
		Json j = new Json();
		try {
			referMaterialService.del(referMaterial);
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
	 * 发布会议文件
	 */
	public void pub() {
		Json j = new Json();
		try {
			referMaterialService.pub(referMaterial,httpSession);
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
	 * 取消发布会议文件
	 */
	public void cancelPub() {
		Json j = new Json();
		try {
			referMaterialService.cancelPub(referMaterial);
			j.setSuccess(true);
			j.setMsg("取消发布成功!");
		} catch (Exception e) {
			j.setMsg("取消发布失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	public ReferMaterialServiceI getReferMaterialService() {
		return referMaterialService;
	}
	@Autowired
	public void setReferMaterialService(ReferMaterialServiceI referMaterialService) {
		this.referMaterialService = referMaterialService;
	}

}
