package com.tlzn.service.eduManage;

import javax.servlet.http.HttpSession;

import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.eduManage.Teacher;

public interface TeacherServiceI {
	public Teacher get(String id) throws Exception;
	public DataGrid datagrid(Teacher teacher)throws Exception;
	public String add(Teacher teacher,HttpSession httpSession) throws Exception;
//	public String saveForUpate(Teacher teacher,HttpSession httpSession) throws Exception;
//	public String upDateOrAdd(Teacher teacher,HttpSession httpSession) throws Exception;
	public void del(Teacher teacher) throws Exception;
	public void pub(Teacher teacher,HttpSession httpSession) throws Exception;
	public void cancelPub(Teacher teacher) throws Exception;
	public void edit(Teacher teacher) throws Exception;
}
