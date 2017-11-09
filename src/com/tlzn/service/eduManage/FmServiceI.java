package com.tlzn.service.eduManage;

import javax.servlet.http.HttpSession;

import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.eduManage.FamilyMem;

public interface FmServiceI {
	public FamilyMem get(String id) throws Exception;
	public DataGrid datagrid(FamilyMem familyMem)throws Exception;
	public FamilyMem save(FamilyMem familyMem,HttpSession httpSession) throws Exception;
	public String upDateOrAdd(FamilyMem familyMem,HttpSession httpSession) throws Exception;
	public void del(FamilyMem familyMem) throws Exception;
	public void edit(FamilyMem familyMem) throws Exception;
}
