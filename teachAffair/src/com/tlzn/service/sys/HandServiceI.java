package com.tlzn.service.sys;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.tlzn.model.ThandleReply;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.sys.Hand;

public interface HandServiceI {
	
	public void save(Hand hand) throws Exception ;

	public DataGrid datagrid(Hand hand) throws Exception;

	public DataGrid datagrid(Hand hand, String name) throws Exception;

	public String report(Hand hand, String string) throws Exception;
	
	public Hand findObj(Hand hand) throws Exception;

	public void receiv(Hand hand) throws Exception;

	public void back(Hand hand) throws Exception;
	
	public void refuse(Hand hand) throws Exception;
	
	public List<Hand> find(Hand hand) throws Exception;

	public List<Hand> list(Hand hand) throws Exception;
	public String monitordatagrid(HttpSession httpSession) throws Exception;

	public void reject(Hand hand) throws Exception;

	public void update(Hand hand) throws Exception;
	
	public void checkPass(Hand hand)throws Exception;

}
