package com.tlzn.service.eduManage;


import javax.servlet.http.HttpSession;

import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.eduManage.SelCourse;

public interface SelCourseServiceI {
	public SelCourse get(String id) throws Exception;
	public DataGrid datagrid(SelCourse selCourse)throws Exception;
	public String save(SelCourse selCourse,HttpSession httpSession) throws Exception;
	public void del(SelCourse selCourse) throws Exception;
	public void pub(SelCourse selCourse,HttpSession httpSession) throws Exception;
	public void cancelPub(SelCourse selCourse) throws Exception;
	public void edit(SelCourse selCourse) throws Exception;
}
