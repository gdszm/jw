package com.tlzn.service.sys;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.sys.Comm;
import com.tlzn.pageModel.sys.Dic;
import com.tlzn.pageModel.sys.User;

public interface CommServiceI {
	
	public void save(Comm comm) throws Exception ;

	public DataGrid datagrid(Comm comm) throws Exception;

	public void change(Comm comm) throws Exception ;

	public Comm update(Comm comm) throws Exception ;

	public String report(Comm comm) throws Exception;
	
	public DataGrid getCurSecComm(Comm comm,HttpSession httpSession) throws Exception;
	
	public Comm getCommBycode(String code)  throws Exception;
	
	public List<Dic> combobox()throws Exception;
	public List<Comm> findSeco(String secoId);
	public List<Comm> findNOSeco(String secoId);
	
	public DataGrid selectDatagrid(Comm comm) throws Exception;

	public DataGrid getComms(Comm comm) throws Exception;
}
