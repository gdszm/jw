package com.tlzn.service.meeting;


import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.meeting.Meeting;
import com.tlzn.pageModel.meeting.MeetingMan;
import com.tlzn.pageModel.speech.Speech;

public interface MeetingManServiceI {
	/**
	 *参会人员批量添加
	 */
	void addMans(Meeting meeting)throws Exception;
	/**
	 * 根据id获取人员请假信息
	 */
	public MeetingMan getLeave(String id) throws Exception;
	/**
	 * 请假申请提交
	 */
	public void leave(MeetingMan meetingMan)throws Exception;
	
	DataGrid datagrid(MeetingMan meetingMan)throws Exception;
	
	public DataGrid datagridOwn(MeetingMan meetingMan) throws Exception;
	
	public DataGrid datagridFulfil(MeetingMan meetingMan,String userCode, String secondaryCode) throws Exception;
	/**
	 * 参会情况查询
	 */
	public DataGrid queryDatagrid(MeetingMan meetingMan) throws Exception;
	/**
	 * 会义人数统计
	 * @param  meetId ,status
	 * @throws 	Exception
	 * @return 	String 
	 */
	String count(String meetId,String status)throws Exception;
	/**
	 *参会人员调整保存
	 */
	public void memberSave(MeetingMan meetingMan)throws Exception;
	/**
	 *请假驳回提交
	 */
	public void change(MeetingMan meetingMan)throws Exception;
	/**
	 *出席情况调整提交
	 */
	public void setSave(MeetingMan meetingMan)throws Exception;
	/**
	 * 参会情况统计表头数据
	 */
	public String getColumns() throws Exception;
	/**
	 *参会情况统计
	 */
	public String situationCount(MeetingMan meetingMan) throws Exception;


}
