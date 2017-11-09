package com.tlzn.service.sys;

import java.util.List;

import com.tlzn.model.sys.Trules;
import com.tlzn.pageModel.base.DataGrid;

public interface RulesServiceI {
	public DataGrid datagrid(Trules rules) throws Exception;
	
	public void save(Trules rules) throws Exception;

	public void update(Trules rules) throws Exception;
	
	public void change(Trules rules) throws Exception;
	
	public List<Trules> combobox() throws Exception;
	
	public Trules getTrules(String id) throws Exception;
}
