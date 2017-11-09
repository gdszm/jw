package com.tlzn.service.info;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.info.Proposal;

/**
 * 提案service
 * @author zhangle
 *
 */
public interface PropServiceI {
	DataGrid datagrid(Proposal prop,String secondaryCode)throws Exception;
	//public DataGrid datagrid(Proposal prop,String secondaryCode)throws Exception;
	DataGrid datagrid(Proposal prop,String userCode,String secondaryCode)throws Exception;
	public DataGrid datagridFulfil(Proposal prop,String userCode, String secondaryCode) throws Exception;
	DataGrid Pdatagrid(Proposal prop,String secondaryCode,String userCode)throws Exception;
	DataGrid mdatagrid(Proposal prop, String secondaryCode,String commCode)throws Exception;
	void undo(Proposal prop, String commCode)throws Exception;
	
	//public DataGrid datagrid(Proposal prop,String userCode,String secondaryCode)throws Exception;
	DataGrid assigndatagrid(Proposal prop,HttpSession httpSession)throws Exception;
	DataGrid querydatagrid(Proposal prop)throws Exception;
	DataGrid queryAllDatagrid(Proposal prop)throws Exception;
	DataGrid reviewdatagrid(Proposal prop,HttpSession httpSession)throws Exception;
	Proposal findProp(Proposal prop) throws Exception;
	//统计
	Map<String,Integer> propCount(HttpSession httpSession)throws Exception;
	Long commEditRemind(String commId,HttpSession httpSession) throws Exception;
	Long compNewRemind(String companyId,HttpSession httpSession) throws Exception;
	Long adminNewRemind(HttpSession httpSession) throws Exception;
	Long govCheckRemind(String userCode,HttpSession httpSession) throws Exception;
	Long govBackRemind(String userCode,HttpSession httpSession) throws Exception;
	Proposal findObj(String id) throws Exception;
	//新增
	String add(Proposal prop,HttpSession httpSession)throws Exception;
	//当前届次的提案最大编号
	String getMaxCode(String ext,HttpSession httpSession)throws Exception;
	//修改
	void edit(Proposal pOrop,HttpSession httpSession) throws Exception;
	//初审
	void checkEdit(Proposal prop) throws Exception;
	//退回
	void backEdit(Proposal prop) throws Exception;
	void setUpdateFlg(Proposal prop) throws Exception;
	//正式交办
	void assgnSub(Proposal prop) throws Exception;
	//确定立案
	void setFiling(Proposal prop) throws Exception;
	//撤案
	int delete(Proposal prop) throws Exception;
	//拒绝采纳设置
	int setAdopt(Proposal prop) throws Exception;
	//重点提案设置
	void setStress(Proposal prop,String period) throws Exception;
	//优秀提案设置
	int setFine(Proposal prop) throws Exception;
	//报表生成
	String report(Proposal prop,HttpSession httpSession) throws Exception;
	String reportwy(Proposal prop,HttpSession httpSession) throws Exception;
	String queryreport(Proposal prop,HttpSession httpSession) throws Exception;
	/**
	 * 
	 *市/县委、两办单位报表
	 * 
	 * @param prop
	 * 
	 * @throws 	Exception
	 */
	String assignreport(Proposal prop,HttpSession httpSession) throws Exception;
	/**
	 * 
	 *市/县委、两办统计报表
	 * 
	 * @param prop
	 * 
	 * @throws 	Exception
	 */
	String assigncountreport(HttpSession httpSession) throws Exception;
	//审查立案
	void check(Proposal prop, String period) throws Exception;
	//确认承办单位
	void confirm(Proposal prop) throws Exception;
	//合并提案
	void setMerge(Proposal prop) throws Exception;
	//取消合并提案
	public void cancelMerge(Proposal prop) throws Exception;
	void setOpt(Proposal prop) throws Exception;
	void submit(Proposal prop, HttpSession httpSession) throws Exception;
	DataGrid sdatagrid(Proposal prop, String secondaryCode,HttpSession httpSession) throws Exception;
	DataGrid sdatagrid(Proposal prop,String secondaryCode)throws Exception;
}
