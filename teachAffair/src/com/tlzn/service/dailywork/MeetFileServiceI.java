package com.tlzn.service.dailywork;

import javax.servlet.http.HttpSession;

import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.dailywork.FileData;

public interface MeetFileServiceI {
	public FileData get(String id) throws Exception;
	public DataGrid datagrid(FileData fileData)throws Exception;
	public String save(FileData fileData,HttpSession httpSession) throws Exception;
	public String saveForUpate(FileData fileData,HttpSession httpSession) throws Exception;
	public String upDateOrAdd(FileData fileData,HttpSession httpSession) throws Exception;
	public void del(FileData fileData) throws Exception;
	public void pub(FileData fileData,HttpSession httpSession) throws Exception;
	public void cancelPub(FileData fileData) throws Exception;
	public void edit(FileData fileData) throws Exception;
	
}
