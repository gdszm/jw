package com.tlzn.service.eduManage;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.eduManage.Classroom;

public interface ClassroomServiceI {
	public Classroom get(String id) throws Exception;
	public DataGrid datagrid(Classroom classroom)throws Exception;
	public String add(Classroom classroom,HttpSession httpSession) throws Exception;
	public void del(Classroom classroom) throws Exception;
	public void pub(Classroom classroom,HttpSession httpSession) throws Exception;
	public void cancelPub(Classroom classroom) throws Exception;
	public void edit(Classroom classroom) throws Exception;
}
