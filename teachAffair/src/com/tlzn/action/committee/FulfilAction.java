package com.tlzn.action.committee;


import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.model.sys.Tsecondary;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.dailywork.Notice;
import com.tlzn.pageModel.sys.Comm;
import com.tlzn.pageModel.sys.Seco;
import com.tlzn.service.committee.CommitteeServiceI;
import com.tlzn.service.committee.FulfilServiceI;
import com.tlzn.service.dailywork.NoticeServiceI;
import com.tlzn.service.sys.SecoServiceI;

@Action(value = "fulfil", results = {
		//履职管理页面
		@Result(name = "fulfil", location = "/general/committee/fulfil.jsp"),
		//履职信息页面
		@Result(name = "fulfilDetail", location = "/general/committee/fulfil_detail.jsp"),
		//履职情况统计页面
		@Result(name = "fulfilStatistics", location = "/general/committee/fulfilStatistics.jsp"),
		//未履职委员统计页面
		@Result(name = "unFulfilCommittee", location = "/general/committee/unFulfilCommittee.jsp"),
})
public class FulfilAction extends BaseAction implements ModelDriven<Comm>{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(FulfilAction.class);
	
	private CommitteeServiceI committeeService;
	
	private SecoServiceI secoService;
	
	private FulfilServiceI fulfilService;
	
	public CommitteeServiceI getCommitteeService() {
		return committeeService;
	}
	@Autowired
	public void setCommitteeService(CommitteeServiceI committeeService) {
		this.committeeService = committeeService;
	}
	
	public SecoServiceI getSecoService() {
		return secoService;
	}
	
	@Autowired
	public void setSecoService(SecoServiceI secoService) {
		this.secoService = secoService;
	}
	
	
	public FulfilServiceI getFulfilService() {
		return fulfilService;
	}
	@Autowired
	public void setFulfilService(FulfilServiceI fulfilService) {
		this.fulfilService = fulfilService;
	}
	
	private Comm comm=new Comm();

	public Comm getModel() {
		return comm;
	}
	/**
	 * 履职管理页面跳转
	 */
	public String fulfil(){
		return "fulfil";
	}
	/**
	 * 履职信息页面跳转
	 */
	public String fulfilDetail(){
		try {
			//查询该委员信息
			request.setAttribute("comm", committeeService.getCommBycode(comm.getCode()));
			
			//根据所有选择的届次查询届次详细
			String secondaryCode = comm.getSecondaryCode();
			Tsecondary secondary =secoService.find(secondaryCode);
			request.setAttribute("secondary", secondary);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "fulfilDetail";
	}
	
	/**
	 * 履职情况统计页面跳转
	 */
	public String fulfilStatistics(){
		return "fulfilStatistics";
	}
	/**
	 * 未履职委员统计页面跳转
	 */
	public String unFulfilCommittee(){
		return "unFulfilCommittee";
	}

	/**
	 * 委员履职情况统计表头数据获取
	 */
	public void getFulfilColumns() {
		try {
			super.writeListJson(fulfilService.getColumns());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void fulfilCount(){
		try {
			super.writeListJson(fulfilService.fulfilCount(comm));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	//报表生成
	public void report(){
		Json j = new Json();
		try {
			j.setObj(fulfilService.report(comm));
			j.setSuccess(true);
			j.setMsg("报表生成成功!");
		} catch (Exception e) {
			j.setMsg("报表生成失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	public void unFulfil() {
		try {
			super.writeDataGridJson(committeeService.unFulfil(comm));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
}


