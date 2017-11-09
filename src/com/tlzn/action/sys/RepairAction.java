package com.tlzn.action.sys;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.tlzn.action.base.BaseAction;
import com.tlzn.service.sys.RepairServiceI;

/**
 * 修复数据库ACTION
 * 
 * @author 刘平
 * 
 */
@Action(value = "repair", results = { @Result(name = "success", location = "/", type = "redirect") })
public class RepairAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private RepairServiceI repairService;

	public RepairServiceI getRepairService() {
		return repairService;
	}

	@Autowired
	public void setRepairService(RepairServiceI repairService) {
		this.repairService = repairService;
	}

	/**
	 * 修复数据库
	 * 
	 * @return
	 */
	public String doNotNeedSession_repairAction() {
		repairService.repair();
		return "success";
	}

	/**
	 * 先清空数据库，然后再修复数据库
	 * 
	 * @return
	 */
	public String doNotNeedSession_deleteAndRepair() {
		repairService.deleteAndRepair();
		return "success";
	}

}
