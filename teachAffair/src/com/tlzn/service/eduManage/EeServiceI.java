package com.tlzn.service.eduManage;

import javax.servlet.http.HttpSession;

import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.eduManage.EduExp;

public interface EeServiceI {
	public EduExp get(String id) throws Exception;
	public DataGrid datagrid(EduExp eduExp)throws Exception;
	public EduExp save(EduExp eduExp,HttpSession httpSession) throws Exception;
	public String upDateOrAdd(EduExp eduExp,HttpSession httpSession) throws Exception;
	public void del(EduExp eduExp) throws Exception;
	public void edit(EduExp eduExp) throws Exception;
}
