package com.tlzn.service.poll;

import java.util.List;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.tlzn.model.poll.Tpoll;
import com.tlzn.pageModel.base.DataGrid;

public interface PollServiceI {
	public String add(Tpoll poll,HttpSession httpSession) throws Exception;
	
	public String submitAdd(Tpoll poll,HttpSession httpSession) throws Exception;
	
	public void submit(Tpoll poll,HttpSession httpSession) throws Exception;
	
	public DataGrid datagrid(Tpoll poll,String userCode,String secondaryCode)throws Exception;
	
	public Tpoll findTpoll(String pollId) throws Exception;
	
	public void update(Tpoll poll) throws Exception;
	
	public void submitEdit(Tpoll poll) throws Exception;
	
	public void revoke(Tpoll poll) throws Exception;
	
	public DataGrid datagrid(Tpoll poll,String secondaryCode)throws Exception;
	public DataGrid datagrid(Tpoll poll,HttpSession httpSession)throws Exception; 
	
	public DataGrid submitter(Tpoll poll)throws Exception;
	
	public void checkEdit(Tpoll poll,HttpSession httpSession) throws Exception;
	
	public void setMerge(Tpoll poll) throws Exception;
	
	public void cancelMerge(Tpoll poll) throws Exception;
	
	public void setStress(Tpoll poll) throws Exception;
	
	public void setFiling(Tpoll poll) throws Exception;
	
	public void setUpdateFlg(Tpoll poll) throws Exception;
	// gds add start
	public DataGrid issueDatagrid(Tpoll poll, String secondaryCode) throws Exception;
	public Object publicAdd(Tpoll poll, HttpSession httpSession) throws Exception;
	//首页社情民意统计
	public Map<String,Integer> pollCount(HttpSession httpSession)throws Exception;
	//gds add end
	
	public List<Tpoll> findIssueData(Tpoll poll) throws Exception;
	
	public String report(Tpoll poll,HttpSession httpSession) throws Exception;
	
	public void pollMark(Tpoll poll,HttpSession httpSession) throws Exception;
	
	public void setHandle(Tpoll poll) throws Exception;
	
	public DataGrid datagridAssign(Tpoll poll) throws Exception;
	
	public void pollAssign(Tpoll poll) throws Exception;

	public DataGrid datagridReview(Tpoll poll) throws Exception;
	
	public void setPollStatus(Tpoll poll) throws Exception;
	
	public void confirmSave(Tpoll poll,HttpSession httpSession) throws Exception;

	public DataGrid issuedDatagrid(Tpoll poll) throws Exception;

	public DataGrid datagridFulfil(Tpoll poll, String userCode,String secondaryCode) throws Exception;
	
	public String discardAdd(Tpoll poll,HttpSession httpSession) throws Exception;
	
}
