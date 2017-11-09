package com.tlzn.service.eduManage;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.eduManage.Stu;

public interface StuServiceI {
	public Stu get(String id) throws Exception;
	public Stu getStuArch(String id) throws Exception;
	public DataGrid datagrid(Stu stu) throws Exception;
	public DataGrid fmDatagrid(Stu stu) throws Exception;
	public DataGrid apDatagrid(Stu stu) throws Exception;
	public DataGrid eeDatagrid(Stu stu) throws Exception;
	public DataGrid weDatagrid(Stu stu) throws Exception;
	public String save(Stu stu,HttpSession httpSession) throws Exception;
	public String saveForUpate(Stu stu,HttpSession httpSession) throws Exception;
	public String upDateOrAdd(Stu stu,HttpSession httpSession) throws Exception;
	public void del(Stu stu) throws Exception;
	public void pub(Stu stu,HttpSession httpSession) throws Exception;
	public void cancelPub(Stu stu) throws Exception;
	public void edit(Stu stu,HttpSession httpSession) throws Exception;
	public void archAdd(Stu stu) throws Exception;
	public void archEdit(Stu stu) throws Exception;
	public Stu saveBlankStuArch() throws Exception;
	/**
	 * 
	 * 首页学生统计
	 */
	public Map<String, Integer> stuCount(HttpSession httpSession);
	/**
	 * 最新5条学生
	 */
	public List<Stu> getNewStu() throws Exception;
}
