package com.tlzn.action.poll;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.model.poll.TpollCheck;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.service.poll.PollCheckServiceI;

@Action(value = "pollCheck", results = {
		@Result(name = "", location = "/general/poll/"),
		
		
})
public class PollCheckAction extends BaseAction implements ModelDriven<TpollCheck>{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(PollCheckAction.class);
	
	private PollCheckServiceI pollCheckService;
	
	public PollCheckServiceI getPollCheckService() {
		return pollCheckService;
	}
	@Autowired
	public void setPollCheckService(PollCheckServiceI pollCheckService) {
		this.pollCheckService = pollCheckService;
	}
	
	TpollCheck pCheck=new TpollCheck();
	public TpollCheck getModel() {
		return pCheck;
	}
	/**
	 * 
		 * 社情民意签发情况
	 */
	public void datagrid() {
		try {
			DataGrid dataGrid=pollCheckService.datagrid(pCheck);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
}
