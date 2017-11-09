package com.tlzn.service.poll;

import javax.servlet.http.HttpSession;

import com.tlzn.model.poll.TpollCheck;
import com.tlzn.pageModel.base.DataGrid;


public interface PollCheckServiceI {
	
	public void add(TpollCheck pollCheck,HttpSession httpSession) throws Exception;

	public DataGrid datagrid(TpollCheck pollCheck)throws Exception;

}
