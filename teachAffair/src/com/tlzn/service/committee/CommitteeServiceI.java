package com.tlzn.service.committee;

import java.util.List;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.tlzn.pageModel.activity.Activitypeo;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.sys.Comm;

public interface CommitteeServiceI {

	DataGrid datagrid(Comm comm)throws Exception;

	public void save(Comm comm)throws Exception;

	public Comm update(Comm comm)throws Exception;

	public Comm getCommBycode(String code)throws Exception;

	public String report(Comm comm)throws Exception;

	public void change(Comm comm)throws Exception;

	/**
	 * 
		 * 委员管理首页统计
		 * @param  httpSession
		 * @throws 	Exception
		 * @return 	void
	 */
	Map<String, Integer> committeeCount(HttpSession httpSession);
	
	/**
	 * 
		 * 委员查询
		 * @param  meeting
		 * @param  httpSession
		 * @throws 	Exception
		 * @return 	list
	 */
	List<Comm> queryCommittee(Comm comm)throws Exception;
	
	/**
	 * 参加活动大于5次的委员
	 */
	public DataGrid cjdatagrid(Comm comm)throws Exception;
	/**
	 * 缺席活动大于5次的委员
	 */
	public DataGrid wcjdatagrid(Comm comm)throws Exception;
	
	/**
	 * 从未参加活动的委员
	 */
	public DataGrid cwdatagrid(Comm comm)throws Exception;
	
	/**
	 * 参加会议大于5次的委员
	 */
	public DataGrid cxdatagrid(Comm comm)throws Exception;
	/**
	 * 缺席会议大于5次的委员
	 */
	public DataGrid qxdatagrid(Comm comm)throws Exception;
	/**
	 * 从未参加会议的委员
	 */
	public DataGrid nodatagrid(Comm comm)throws Exception;
	/**
	 * 未履职委员统计
	 */
	public DataGrid unFulfil(Comm comm)throws Exception;
	/**
	 * 重置成默认密码
	 */
	public void resetPass(String code) throws Exception;
}
