package com.tlzn.action.sys;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.sys.Dolog;
import com.tlzn.service.sys.DologServiceI;
@Action(value = "dolog", results = {@Result(name = "dolog", location = "/general/admin/dolog.jsp")})
public class DologAction extends BaseAction implements ModelDriven<Dolog>{

	private static final long serialVersionUID = 1L;
	
	private DologServiceI dologService;
	private String flag;
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}

	private Dolog dolog=new Dolog(); 
	public Dolog getModel() {
		
		return dolog;
	}
	public DologServiceI getDologService() {
		return dologService;
	}
	@Autowired
	public void setDologService(DologServiceI dologService) {
		this.dologService = dologService;
	}
	
	public String dolog() {
		return "dolog";
	}
	
	public void datagrid() {
		try {
			if("1".equals(flag)){
				dolog.setInfo("[提案]ID:"+dolog.getInfo());
			}
			writeDataGridJson(dologService.datagrid(dolog));
			//writeJson(dologService.datagrid(dolog));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void datagridByWhere() {
		try {
			writeDataGridJson(dologService.datagrid(dolog));
			//writeJson(dologService.datagrid(dolog));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}