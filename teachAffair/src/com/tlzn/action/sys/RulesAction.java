package com.tlzn.action.sys;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.model.sys.Trules;
import com.tlzn.pageModel.base.Json;
import com.tlzn.service.sys.RulesServiceI;
import com.tlzn.util.base.Constants;

@Action(value = "rules", results = { 
		@Result(name = "rules", location = "/general/admin/rules.jsp") ,
		@Result(name = "rulesAdd", location = "/general/admin/rulesAdd.jsp"),
		@Result(name = "rulesEdit", location = "/general/admin/rulesEdit.jsp")})
public class RulesAction extends BaseAction implements ModelDriven<Trules> {

	
	private static final long serialVersionUID = 1L;
	protected final Log log = LogFactory.getLog(getClass());
	
	private RulesServiceI rulesService;

	public RulesServiceI getRulesService() {
		return rulesService;
	}
	@Autowired
	public void setRulesService(RulesServiceI rulesService) {
		this.rulesService = rulesService;
	}

	private Trules rules= new Trules();
	public Trules getModel() {
		return rules;
	}
	
	/**
	 * 计分规则管理
	 */
	public String rules() {
		return "rules";
	}
	/**
	 * 计分规则新增
	 */
	public String rulesAdd() {
		return "rulesAdd";
	}
	/**
	 * 计分规则修改
	 */
	public String rulesEdit() {
		
		try {
			Trules r = rulesService.getTrules(rules.getId());
			ServletActionContext.getRequest().setAttribute("obj", r);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "rulesEdit";
	}
	/**
	 * 数据获取
	 */
	public void datagrid() {
		try {
			super.writeDataGridJson(rulesService.datagrid(rules));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 启用状态数据获取
	 */
	public void useDatagrid() {
		try {
			rules.setStatus(Constants.CODE_TYPE_QYTY_YES);
			super.writeDataGridJson(rulesService.datagrid(rules));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 新增
	 */
	public void add(){
		Json j = new Json();
		try {
			rulesService.save(rules);
			j.setMsg("添加成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg(e.getLocalizedMessage());
		}
		super.writeJson(j);
	}
	/**
	 * 修改
	 */
	public void edit(){
		Json j = new Json();
		try {
			rulesService.update(rules);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg("编辑失败！");
		}
		super.writeJson(j);
	}
	/**
	 * 状态改变
	 */
	public void change(){
		Json j = new Json();
		try {
			rulesService.change(rules);
			j.setSuccess(true);
			j.setMsg("操作成功!");
		} catch (Exception e) {
			j.setMsg("操作失败！");
			e.printStackTrace();
		}
		super.writeJson(j);
	}
}
