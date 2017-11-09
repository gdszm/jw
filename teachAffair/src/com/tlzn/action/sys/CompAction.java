package com.tlzn.action.sys;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.sys.Comp;
import com.tlzn.service.sys.CompServiceI;
import com.tlzn.util.base.ResourceUtil;

@Action(value = "comp", results = { 
		@Result(name = "comp", location = "/general/admin/comp.jsp") ,
		@Result(name = "compAE", location = "/general/admin/compAE.jsp") ,
		@Result(name = "compQ", location = "/general/admin/compQ.jsp")
		})
public class CompAction extends BaseAction implements ModelDriven<Comp> {
	/**
	 * 
	 */
	protected final Log log = LogFactory.getLog(getClass());

	private static final long serialVersionUID = 1L;
	private CompServiceI compService;
	private Comp comp=new Comp();

	public Comp getModel() {
		return comp;
	}
	
	public String comp() {
		return "comp";
	}

	public String compAE() {
		return "compAE";
	}

	public String compQ() {
		return "compQ";
	}

	public CompServiceI getCompService() {
		return compService;
	}

	public void setCompService(CompServiceI compService) {
		this.compService = compService;
	}

	public void add(){
		Json j = new Json();
		try {
			compService.save(comp);
			j.setMsg("添加成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(e.getLocalizedMessage());
		}
		super.writeJson(j);
	}
	
	public void edit(){
		Json j = new Json();
		try {
			compService.update(comp);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (Exception e) {
			j.setMsg("编辑失败，用户名已存在！");
		}
		super.writeJson(j);
	}
	//页面顶部用户信息修改
	public void doNotNeedAuth_userEdit(){
		Json j = new Json();
		try {
			httpSession.setAttribute(ResourceUtil.getSessionComp(),compService.update(comp));
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (Exception e) {
			j.setMsg("编辑失败，用户名已存在！");
		}
		super.writeJson(j);
	}

	public void change(){
		Json j = new Json();
		try {
			compService.change(comp);
			j.setSuccess(true);
			j.setMsg("操作成功!");
		} catch (Exception e) {
			j.setMsg("操作失败！");
			e.printStackTrace();
		}
		super.writeJson(j);
	}

	public void datagrid() {
		try {
			super.writeDataGridJson(compService.datagrid(comp));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//下拉列表
	public void combox() {
		try {
			writeJson(compService.combobox());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
