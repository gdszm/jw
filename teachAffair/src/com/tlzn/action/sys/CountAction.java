package com.tlzn.action.sys;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.sys.Count;
import com.tlzn.service.sys.CountServiceI;

@Action(value = "count", results = { @Result(name = "countComm", location = "/general/admin/countComm.jsp"), @Result(name = "countPro", location = "/general/admin/countPro.jsp"), @Result(name = "countInfo", location = "/general/admin/countInfo.jsp"), @Result(name = "countHand", location = "/general/admin/countHand.jsp")})
public class CountAction extends BaseAction implements ModelDriven<Count> {
	/**
	 * 
	 */
	protected final Log log = LogFactory.getLog(getClass());

	private static final long serialVersionUID = 1L;
	private CountServiceI countService;
	private Count count=new Count();

	public Count getModel() {
		return count;
	}
	
	public CountServiceI getCountService() {
		return countService;
	}

	public void setCountService(CountServiceI countService) {
		this.countService = countService;
	}

	public String countComm() {
		return "countComm";
	}

	public String countPro() {
		return "countPro";
	}

	public String countInfo() {
		return "countInfo";
	}

	public String countHand() {
		return "countHand";
	}

	public void datagrid() {
		try {
			super.writeListJson(countService.datagrid(count));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
