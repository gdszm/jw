package com.tlzn.action.eduManage;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.eduManage.Term;
import com.tlzn.service.eduManage.TermServiceI;
import com.tlzn.util.base.Util;

@Action(value = "term", results = {
		@Result(name = "term", location = "/general/eduManage/term.jsp"),
		@Result(name = "termView", location = "/general/eduManage/termView.jsp"),
		//新建学期
		@Result(name = "termAdd", location = "/general/eduManage/termAdd.jsp"),
		@Result(name = "termEdit", location = "/general/eduManage/termEdit.jsp"),
		//学期选择页面
		@Result(name = "termSelect", location = "/general/eduManage/termSelect.jsp"),
})
public class TermAction extends BaseAction implements ModelDriven<Term>{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(TermAction.class);
	
	private TermServiceI termService;
	
	private Term term=new Term();
	
	public Term getModel() {
		return term;
	}
	
	/**
	 * 学期列表页面
	 */
	public String term(){
		return "term";
	}
	/**
	 * 学期查询页面
	 */
	public String termQuery(){
		return "termQuery";
	}
	/**
	 * 学期详细页面
	 */
	public String termView(){
		try {
			String termNo=term.getId();
			if(!Util.getInstance().isEmpty(termNo)){
				Term term=termService.get(termNo);
				request.setAttribute("term",term);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "termView";
	}
	/**
	 * 学期新增页面
	 */
	public String termAdd(){
		return "termAdd";
	}
	/**
	 * 学期修改页面
	 */
	public String termEdit(){
		try {
			String termNo=term.getId();
			if(!Util.getInstance().isEmpty(termNo)){
				Term term=termService.get(termNo);
				request.setAttribute("term",term);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "termEdit";
	}
	/**
	 * 弹出学期选择页面
	 */
	public String termSelect(){
		request.setAttribute("classId",term.getId());
		return "termSelect";
	}
	/**
	 * 
	 * 查询学期列表
	 */
	public void datagrid() {
		try {
			DataGrid dataGrid = termService.datagrid(term);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询学期列表（不验证权限）
	 */
	public void doNotNeedAuth_datagrid() {
		try {
			DataGrid dataGrid = termService.datagrid(term);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询学期列表(学期查询模块)
	 */
	public void datagridQuery() {
		try {
			DataGrid dataGrid = termService.datagrid(term);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询 学期单条（跳转 学期详细页面）
	 */
	public String get() {
		try {
			String id=term.getId();
			if(!Util.getInstance().isEmpty(id)){
					Term term =termService.get(id);
					request.setAttribute("term",term);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "termView";
	}
	/**
	 * 
	 * 新增学期
	 */
	public void add() {
		Json j = new Json();
		try {
			j.setObj(termService.add(term,httpSession));
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
	 * 修改学期
	 */
	public void edit() {
		Json j = new Json();
		try {
			termService.edit(term);
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
	 * 删除学期
	 */
	public void delete() {
		Json j = new Json();
		try {
			termService.del(term);
			j.setSuccess(true);
			j.setMsg("删除成功!");
		} catch (Exception e) {
			j.setMsg("删除失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	public TermServiceI getTermService() {
		return termService;
	}
	@Autowired
	public void setTermService(TermServiceI termService) {
		this.termService = termService;
	}
}
