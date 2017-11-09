package com.tlzn.action.sys;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.sys.Comm;
import com.tlzn.service.sys.CommServiceI;
import com.tlzn.util.base.ResourceUtil;

@Action(value = "comm", results = { 
		@Result(name = "comm", location = "/general/admin/comm.jsp") ,
		@Result(name = "commA", location = "/general/admin/commA.jsp"),
		@Result(name = "commE", location = "/general/admin/commE.jsp"),
		@Result(name = "commQ", location = "/general/admin/commQ.jsp"),
		@Result(name = "view", location = "/general/admin/commView.jsp"),
		@Result(name = "memberInvite", location = "/general/admin/memberInvite.jsp"),
		@Result(name = "memberSelect", location = "/general/admin/memberSelect.jsp")
		})
public class CommAction extends BaseAction implements ModelDriven<Comm> {
	/**
	 * 
	 */
	protected final Log log = LogFactory.getLog(getClass());

	private static final long serialVersionUID = 1L;
	private CommServiceI commService;
	private Comm comm=new Comm();
	private InputStream downFile;

	public Comm getModel() {
		return comm;
	}
	
	public String comm() {
		return "comm";
	}
	public String commA() {
		return "commA";
	}
	public String commE() {
		return "commE";
	}

	public String commQ() {
		return "commQ";
	}

	public CommServiceI getCommService() {
		return commService;
	}

	public void setCommService(CommServiceI commService) {
		this.commService = commService;
	}

	public InputStream getDownFile() {
		return downFile;
	}

	public void setDownFile(InputStream downFile) {
		this.downFile = downFile;
	}

	public void add(){
		Json j = new Json();
		try {
			commService.save(comm);
			j.setMsg("添加成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg(e.getLocalizedMessage());
		}
		super.writeJson(j);
	}
	
	public String view() {
		try {
			this.request.setAttribute("comm", commService.getCommBycode(comm.getCode()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "view";
	}

	public void edit(){
		Json j = new Json();
		try {
			commService.update(comm);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg(e.getLocalizedMessage());
		}
		super.writeJson(j);
	}
	//页面顶部用户信息修改
	public void doNotNeedAuth_userEdit(){
		Json j = new Json();
		try {
			httpSession.setAttribute(ResourceUtil.getSessionComm(),commService.update(comm));
			System.out.println("bac==="+((Comm)httpSession.getAttribute("sessionComm")).getBirthDate());
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg(e.getLocalizedMessage());
		}
		super.writeJson(j);
	}

	public void change(){
		Json j = new Json();
		try {
			commService.change(comm);
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
			super.writeDataGridJson(commService.datagrid(comm));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void getCurSecComm(){
		try {
			super.writeDataGridJson(commService.getCurSecComm(comm,httpSession));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void report() {
		Json j = new Json();
		try {
			j.setObj(commService.report(comm));
			j.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg(e.getLocalizedMessage());
		}
		super.writeJson(j);
	}
	//下拉列表
	public void combox() {
		try {
			writeJson(commService.combobox());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获得可选委员datagrid（除去已有的）（委员选择弹出框用）
	 */
	public void selectDatagrid(){
		//System.out.println("====="+user.getRoleNames());
		try {
			super.writeDataGridJson(commService.selectDatagrid(comm));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获得已选用户，（除去已有的）（委员选择弹出框用）
	 */
	public void commsDatagrid() {
		//super.writeJson(userService.datagrid(user));
		try {
			super.writeDataGridJson(commService.getComms(comm));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//添加委员页面跳转
	public String memberInvite() {
		return "memberInvite";
	}
	//添加委员页面跳转(datagrid方式)
	public String memberSelect() {
		//用所在行班次编号，查询选择该班次的用户ID集合
		request.setAttribute("commCodes", request.getParameter("commCodes"));
		return "memberSelect";
	}
}
