package com.tlzn.service.activity;

import java.util.Map;

import javax.servlet.http.HttpSession;
import com.tlzn.pageModel.activity.Activity;
import com.tlzn.pageModel.activity.Activitypeo;
import com.tlzn.pageModel.base.DataGrid;

public interface ActiServiceI {
	/**
	 * 活动信息查询
	 */
	public DataGrid datagrid(Activity activity, HttpSession httpSession) throws Exception ;
	/**
	 * 活动信息查询
	 */
	public DataGrid queryDatagrid(Activity activity, HttpSession httpSession) throws Exception ;
	
	/**
	 * 获取默认数据（界次，部门，日期）
	 */
	public Object defaul(HttpSession httpSession) throws Exception ;
	/**
	 * 活动信息添加
	 */
	public void add(Activity activity) throws Exception;
	/**
	 * 根据活动id获取活动信息
	 */
	public Activity getTactivity(String aid) throws Exception ;
	/**
	 * 根据活动id获取活动人员信息
	 */
	public Activitypeo getTactivitypeo(String aid) throws Exception ;
	/**
	 * 活动信息修改
	 */
	public void update(Activity activity, String astatus)throws Exception;
	/**
	 * 活动状态修改
	 */
	public void change(Activity activity)throws Exception;
	/**
	 * 活动信息删除
	 */
	public void delete(String ids)throws Exception;
	/**
	 * 活动人员信息查询
	 */
	public DataGrid datagridpeo(Activitypeo activitypeo, HttpSession httpSession)throws Exception ;
	/**
	 * 活动人员信息查询
	 */
	public DataGrid peoQueryDatagrid(Activitypeo activitypeo, HttpSession httpSession)throws Exception ;
	/**
	 * 委员履职详情，活动情况列表查询
	 */
	public DataGrid datagridFulfil(Activitypeo activitypeo,String userCode, String secondaryCode) throws Exception;
	/**
	 * 根据auid获取人员请假信息
	 */
	public Activitypeo getLeave(String auid)throws Exception;
	/**
	 * 出席情况修改
	 */
	public void astatus(Activitypeo activitypeo)throws Exception;
	/**
	 * 请假申请提交
	 */
	public void leave(Activitypeo activitypeo)throws Exception;
	/**
	 * 请假驳回提交
	 */
	public void change(Activitypeo activitypeo) throws Exception;
	/**
	 * 活动人员信息添加
	 */
	public void peoadd(String code, String aid)throws Exception;
	/**
	 * 活动人员信息删除
	 */
	public void peodel(String ids)throws Exception;
	/**
	 * 
		 * 活动管理首页统计
		 * @param  meeting
		 * @param  httpSession
		 * @throws 	Exception
		 * @return 	void
	 */
	public Map<String, Integer> activityCount(HttpSession httpSession);
	/**
	 * 参会情况统计表头数据
	 */
	public String getColumns() throws Exception;
	/**
	 *参会情况统计
	 */
	public String situationCount(Activitypeo activitypeo) throws Exception;
	/**
	 * 最新活动查询
	 */
	public DataGrid zxdatagrid(Activity activity, HttpSession httpSession)throws Exception;
	/**
	 * 未发布活动查询
	 */
	public DataGrid wfbdatagrid(Activity activity, HttpSession httpSession)throws Exception;
	/**
	 * 未办结活动查询
	 */
	public DataGrid wbjdatagrid(Activity activity, HttpSession httpSession)throws Exception;
	
}
