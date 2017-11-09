package com.tlzn.service.sys;


import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.sys.Dolog;
/**
 * 日志Service
 * @author zhangle
 * 
 */
public interface DologServiceI {
	/**
	 * 
		 * 获取日志
		 * 
		 * @param dolog
		 * @return 		DataGrid返回值
	 */
	DataGrid datagrid(Dolog dolog) throws Exception;
}
