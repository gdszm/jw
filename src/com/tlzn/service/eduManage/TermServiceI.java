package com.tlzn.service.eduManage;

import javax.servlet.http.HttpSession;

import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.eduManage.Term;

public interface TermServiceI {
	public Term get(String id) throws Exception;
	public DataGrid datagrid(Term term) throws Exception;
	public String add(Term term,HttpSession httpSession) throws Exception;
	public void del(Term term) throws Exception;
	public void edit(Term term) throws Exception;
}
