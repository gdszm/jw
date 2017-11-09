package com.tlzn.service.eduManage;


import javax.servlet.http.HttpSession;

import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.eduManage.ProfessTime;

public interface ProfessTimeServiceI {
	public DataGrid datagrid(ProfessTime professTime)throws Exception;
	public String save(ProfessTime professTime,HttpSession httpSession) throws Exception;
	public void del(ProfessTime professTime) throws Exception;
	public void edit(ProfessTime professTime) throws Exception;
	public ProfessTime get(String id) throws Exception;
}
