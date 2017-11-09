package com.tlzn.service.eduManage;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.eduManage.ClassroomType;

public interface ClassroomTypeServiceI {
	public ClassroomType get(String id) throws Exception;
	public DataGrid datagrid(ClassroomType classroomType)throws Exception;
	public String add(ClassroomType classroomType,HttpSession httpSession) throws Exception;
	public void del(ClassroomType classroomType) throws Exception;
	public void pub(ClassroomType classroomType,HttpSession httpSession) throws Exception;
	public void cancelPub(ClassroomType classroomType) throws Exception;
	public void edit(ClassroomType classroomType) throws Exception;
	public List<ClassroomType> combobox(ClassroomType classroomType)throws Exception;
}
