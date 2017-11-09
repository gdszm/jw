package com.tlzn.service.eduManage;

import javax.servlet.http.HttpSession;

import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.dailywork.Notice;
import com.tlzn.pageModel.eduManage.Archives;
import com.tlzn.pageModel.eduManage.Stu;

public interface ArchServiceI {

	public DataGrid datagrid(Archives archives)throws Exception;
	public String save(Archives archives,HttpSession httpSession) throws Exception;
	public void del(Archives archives) throws Exception;
	public void edit(Archives archives,HttpSession httpSession) throws Exception;
	public Archives get(String id) throws Exception;
	public void pub(Archives archives,HttpSession httpSession) throws Exception;
	public void cancelPub(Archives archives) throws Exception;
	public DataGrid fmDatagrid(Archives archives) throws Exception;
	public DataGrid apDatagrid(Archives archives) throws Exception;
	public DataGrid eeDatagrid(Archives archives) throws Exception;
	public DataGrid weDatagrid(Archives archives) throws Exception;
}
