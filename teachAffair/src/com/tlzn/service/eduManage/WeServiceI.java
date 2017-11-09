package com.tlzn.service.eduManage;

import javax.servlet.http.HttpSession;

import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.eduManage.WorkExp;

public interface WeServiceI {
	public WorkExp get(String id) throws Exception;
	public DataGrid datagrid(WorkExp workExp)throws Exception;
	public WorkExp save(WorkExp workExp,HttpSession httpSession) throws Exception;
	public String upDateOrAdd(WorkExp workExp,HttpSession httpSession) throws Exception;
	public void del(WorkExp workExp) throws Exception;
	public void edit(WorkExp workExp) throws Exception;
}
