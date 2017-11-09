package com.tlzn.service.eduManage;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.eduManage.Classes;

public interface ClassesServiceI {
	public Classes get(String id) throws Exception;
	public DataGrid datagrid(Classes classes)throws Exception;
	public String add(Classes classes,HttpSession httpSession) throws Exception;
	public void del(Classes classes) throws Exception;
	public void pub(Classes classes,HttpSession httpSession) throws Exception;
	public void cancelPub(Classes classes) throws Exception;
	public void edit(Classes classes) throws Exception;
	/**
	 * 
	 * 首页班级统计
	 */
	public Map<String, Integer> classesCount(HttpSession httpSession);
	/**
	 * 最新5条班级
	 */
	public List<Classes> getNewClasses() throws Exception;
}
