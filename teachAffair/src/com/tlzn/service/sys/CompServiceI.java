package com.tlzn.service.sys;

import java.util.List;

import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.sys.Comp;
import com.tlzn.pageModel.sys.Dic;

public interface CompServiceI {
	
	public void save(Comp comp) throws Exception ;

	public DataGrid datagrid(Comp comp) throws Exception;

	public void change(Comp comp) throws Exception ;

	public Comp update(Comp comp) throws Exception ;
	
	public Comp getCompBycode(String code)  throws Exception;
	
	public List<Dic> combobox()throws Exception;
}
