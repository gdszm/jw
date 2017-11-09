package com.tlzn.service.sys;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.tlzn.model.sys.Tsecondary;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.sys.Seco;


public interface SecoServiceI {
	
	public DataGrid datagrid(Seco seco) throws Exception;
	
	public DataGrid datagridNoPage(Seco seco) throws Exception;
	
	public void save(Seco seco) throws Exception;

	public void update(Seco seco) throws Exception;
	
	public void setAtt(Seco seco) throws Exception;
	
	public void change(Seco seco,HttpSession httpSession) throws Exception;
	
	public void setSave(String left,String right,String secoid) throws Exception;

	public Seco findCurrent() throws Exception;
	
	public Tsecondary find(String secondaryId)throws Exception;
	
	public List<Tsecondary> combobox() throws Exception;
	
	public List<Tsecondary> comboboxYear() throws Exception;
	
}
