package com.tlzn.service.dailywork;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.dailywork.Notice;

public interface NoticeServiceI {
	public Notice get(String id) throws Exception;
	public DataGrid datagrid(Notice notice)throws Exception;
	public String save(Notice notice,HttpSession httpSession) throws Exception;
	public String saveForUpate(Notice notice,HttpSession httpSession) throws Exception;
	public String upDateOrAdd(Notice notice,HttpSession httpSession) throws Exception;
	public void del(Notice notice) throws Exception;
	public void pub(Notice notice,HttpSession httpSession) throws Exception;
	public void cancelPub(Notice notice) throws Exception;
	public void edit(Notice notice) throws Exception;
	/**
	 * 
		 * 日常办公首页统计
		 * @param  meeting
		 * @param  httpSession
		 * @throws 	Exception
		 * @return 	void
	 */
	public Map<String, Integer> noticeCount(HttpSession httpSession);
	/**
	 * 最新5条通知公告
	 */
	public List<Notice> getNewNotice() throws Exception;
}
