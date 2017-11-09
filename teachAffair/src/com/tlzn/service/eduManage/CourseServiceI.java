package com.tlzn.service.eduManage;

import javax.servlet.http.HttpSession;

import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.eduManage.Course;

public interface CourseServiceI {
	public Course get(String id) throws Exception;
	public DataGrid datagrid(Course course) throws Exception;
	public String save(Course course,HttpSession httpSession) throws Exception;
	public void del(Course course) throws Exception;
	public void pub(Course course,HttpSession httpSession) throws Exception;
	public void cancelPub(Course course) throws Exception;
	public void edit(Course course) throws Exception;
}
