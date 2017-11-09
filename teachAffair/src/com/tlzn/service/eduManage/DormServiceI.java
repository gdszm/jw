package com.tlzn.service.eduManage;


import javax.servlet.http.HttpSession;

import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.eduManage.Dorm;

public interface DormServiceI {
	public Dorm get(String id) throws Exception;
	public DataGrid datagrid(Dorm dorm)throws Exception;
	public String save(Dorm dorm,HttpSession httpSession) throws Exception;
	public void del(Dorm dorm) throws Exception;
	public void pub(Dorm dorm,HttpSession httpSession) throws Exception;
	public void cancelPub(Dorm dorm) throws Exception;
	public void edit(Dorm dorm) throws Exception;
}
