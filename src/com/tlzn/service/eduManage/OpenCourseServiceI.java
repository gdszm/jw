package com.tlzn.service.eduManage;


import javax.servlet.http.HttpSession;

import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.eduManage.OpenCourse;

public interface OpenCourseServiceI {
	public OpenCourse get(String id) throws Exception;
	public DataGrid datagrid(OpenCourse openCourse)throws Exception;
	public String save(OpenCourse openCourse,HttpSession httpSession) throws Exception;
	public void del(OpenCourse openCourse) throws Exception;
	public void pub(OpenCourse openCourse,HttpSession httpSession) throws Exception;
	public void cancelPub(OpenCourse openCourse) throws Exception;
	public void edit(OpenCourse openCourse) throws Exception;
}
