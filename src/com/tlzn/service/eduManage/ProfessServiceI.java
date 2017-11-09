package com.tlzn.service.eduManage;


import javax.servlet.http.HttpSession;

import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.eduManage.Profess;

public interface ProfessServiceI {
	public Profess get(String id) throws Exception;
	public DataGrid datagrid(Profess profess)throws Exception;
	public String add(Profess profess,HttpSession httpSession) throws Exception;
	public void del(Profess profess) throws Exception;
	public void edit(Profess profess) throws Exception;
	public DataGrid datagridCount(Profess profess) throws Exception;
}
