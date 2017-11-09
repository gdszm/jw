package com.tlzn.service.eduManage;


import javax.servlet.http.HttpSession;

import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.eduManage.ClassManage;

public interface ClassManageServiceI {
	public ClassManage get(String id) throws Exception;
	public DataGrid datagrid(ClassManage classManage)throws Exception;
	public String save(ClassManage classManage,HttpSession httpSession) throws Exception;
	public void del(ClassManage classManage) throws Exception;
	public void pub(ClassManage classManage,HttpSession httpSession) throws Exception;
	public void cancelPub(ClassManage classManage) throws Exception;
	public void edit(ClassManage classManage) throws Exception;
}
