package com.tlzn.service.eduManage;


import javax.servlet.http.HttpSession;

import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.eduManage.Bed;

public interface BedServiceI {
	public Bed get(String id) throws Exception;
	public DataGrid datagrid(Bed bed)throws Exception;
	public String save(Bed bed,HttpSession httpSession) throws Exception;
	public void del(Bed bed) throws Exception;
	public void pub(Bed bed,HttpSession httpSession) throws Exception;
	public void cancelPub(Bed bed) throws Exception;
	public void edit(Bed bed) throws Exception;
}
