package com.tlzn.action.eduManage;


import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.eduManage.FamilyMem;
import com.tlzn.service.eduManage.FmServiceI;
import com.tlzn.util.base.Util;

@Action(value = "familyMem", results = {
		@Result(name = "familyMem", location = "/general/eduManage/familyMem.jsp"),
		@Result(name = "familyMemView", location = "/general/eduManage/familyMemView.jsp"),
		@Result(name = "familyMemAdd", location = "/general/eduManage/familyMemAdd.jsp"),
		@Result(name = "familyMemEdit", location = "/general/eduManage/familyMemEdit.jsp"),
		@Result(name = "familyMemAE", location = "/general/eduManage/familyMemAE.jsp"),
		@Result(name = "familyMemQuery", location = "/general/eduManage/familyMemQuery.jsp"),
})
public class FmAction extends BaseAction implements ModelDriven<FamilyMem>{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(FmAction.class);
	
	private FmServiceI familyMemService;
	
	private FamilyMem familyMem=new FamilyMem();
	
	public FamilyMem getModel() {
		return familyMem;
	}
	
	/**
	 * 家庭成员列表页面
	 */
	public String familyMem(){
		return "familyMem";
	}
	/**
	 * 家庭成员查询页面
	 */
	public String familyMemQuery(){
		return "familyMemQuery";
	}
	/**
	 * 家庭成员详细页面
	 */
	public String familyMemView(){
		return "familyMemView";
	}
	/**
	 * 家庭成员新增页面
	 */
	public String familyMemAdd(){
		return "familyMemAdd";
	}
	/**
	 * 家庭成员发布页面
	 */
	public String familyMemPub(){
		request.setAttribute("familyMemPub","familyMemPub");
		return "familyMemAdd";
	}
	/**
	 * 
	 * 查询家庭成员列表
	 */
	public void datagrid() {
		try {
			DataGrid dataGrid = familyMemService.datagrid(familyMem);
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
			DataGrid dataGrid = familyMemService.datagrid(familyMem);
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
//			familyMem.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES);
			DataGrid dataGrid = familyMemService.datagrid(familyMem);
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
			String id=familyMem.getId();
			if(!Util.getInstance().isEmpty(id)){
					FamilyMem familyMem =familyMemService.get(id);
					request.setAttribute("familyMem",familyMem);
//					String atts= familyMem.getAtts();
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
		return "familyMemView";
	}
	/**
	 * 家庭成员修改页面
	 */
	public String familyMemEdit(){
		try {
			String id=familyMem.getId();
			if(!Util.getInstance().isEmpty(id)){
				FamilyMem familyMem =familyMemService.get(id);
				request.setAttribute("familyMem",familyMem);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "familyMemEdit";
	}
	/**
	 * 家庭成员添加修改页面
	 */
	public String familyMemAE() {
		try {
			String familyMemNo=familyMem.getId();
			if(!Util.getInstance().isEmpty(familyMemNo)){
				FamilyMem familyMem=familyMemService.get(familyMemNo);
				request.setAttribute("familyMem",familyMem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "familyMemAE";
	}
	/**
	 * 
	 * 保存家庭成员
	 */
	public void save() {
		Json j = new Json();
		try {
			j.setObj(familyMemService.save(familyMem,httpSession));
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
			j.setObj(familyMemService.upDateOrAdd(familyMem,httpSession));
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
			familyMemService.edit(familyMem);
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
			familyMemService.del(familyMem);
			j.setSuccess(true);
			j.setMsg("删除成功!");
		} catch (Exception e) {
			j.setMsg("删除失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	public FmServiceI getFamilyMemService() {
		return familyMemService;
	}
	@Autowired
	public void setFamilyMemService(FmServiceI familyMemService) {
		this.familyMemService = familyMemService;
	}
}
