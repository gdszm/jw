package com.tlzn.service.committee;


import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.sys.Comm;

public interface FulfilServiceI {
	
	public String getColumns() throws Exception;
	
	public String fulfilCount(Comm comm) throws Exception;
	
	//报表生成
	String report(Comm comm) throws Exception;
	
	
}
