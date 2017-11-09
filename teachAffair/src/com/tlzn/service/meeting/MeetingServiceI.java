package com.tlzn.service.meeting;


import java.util.Map;

import javax.servlet.http.HttpSession;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.meeting.Meeting;

public interface MeetingServiceI {
	
	/**
	 * 
		 * 会议数据列表
		 * @param  meeting
		 * @throws 	Exception
		 * @return 	DataGrid
	 */
	public DataGrid datagrid(Meeting meeting) throws Exception;
	/**
	 * 
		 * 会议查询
		 * @param  meeting
		 * @throws 	Exception
		 * @return 	DataGrid
	 */
	public DataGrid queryDatagrid(Meeting meeting) throws Exception;
	/**
	 * 
		 * 参会情况数据列表
		 * @param  meeting
		 * @throws 	Exception
		 * @return 	DataGrid
	 */
	public DataGrid situationDatagrid(Meeting meeting) throws Exception;
	/**
	 * 
		 * 根据会议ID会议查询
		 * @param  meeting
		 * @throws 	Exception
		 * @return 	Meeting
	 */
	public Meeting findMeeting(Meeting meeting) throws Exception;
	/**
	 * 
		 * 添加会议
		 * @param  meeting
		 * @throws 	Exception
		 * @return 	void
	 */
	public void add(Meeting meeting) throws Exception;
	/**
	 * 
		 * 更新会议
		 * @param  meeting
		 * @throws 	Exception
		 * @return 	void
	 */
	public void update(Meeting meeting) throws Exception;
	/**
	 * 
		 * 删除会议
		 * @param  meeting
		 * @throws 	Exception
		 * @return 	void
	 */
	public void remove(Meeting meeting) throws Exception;
	/**
	 * 
		 * 发布/取消发布
		 * @param  meeting
		 * @throws 	Exception
		 * @return 	void
	 */
	public void change(Meeting meeting) throws Exception;
	
//	gds add
	/**
	 * 
		 * 获取当前届次的所有会议
		 * @param  meeting
		 * @param  httpSession
		 * @throws 	Exception
		 * @return 	void
	 */
	public DataGrid getCurSecMeeting(Meeting meeting)throws Exception;
	/**
	 * 
		 * 会议管理首页统计
		 * @param  meeting
		 * @param  httpSession
		 * @throws 	Exception
		 * @return 	void
	 */
	public Map<String, Integer> meetingCount(HttpSession httpSession);
//	gds add
	
	
	/**
	 * 最新活动查询
	 */
	public DataGrid zxdatagrid(Meeting meeting)throws Exception;
	/**
	 * 未发布活动查询
	 */
	public DataGrid wfbdatagrid(Meeting meeting)throws Exception;
	/**
	 * 未办结活动查询
	 */
	public DataGrid wbjdatagrid(Meeting meeting)throws Exception;

}
