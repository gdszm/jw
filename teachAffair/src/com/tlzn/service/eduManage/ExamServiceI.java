package com.tlzn.service.eduManage;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.eduManage.Exam;

public interface ExamServiceI {
	public Exam get(String id) throws Exception;
	public DataGrid datagrid(Exam exam)throws Exception;
	public String save(Exam exam,HttpSession httpSession) throws Exception;
	public String saveForUpate(Exam exam,HttpSession httpSession) throws Exception;
	public String upDateOrAdd(Exam exam,HttpSession httpSession) throws Exception;
	public void del(Exam exam) throws Exception;
	public void pub(Exam exam,HttpSession httpSession) throws Exception;
	public void cancelPub(Exam exam) throws Exception;
	public void edit(Exam exam) throws Exception;
	/**
	 * 
		 * 日常办公首页统计
		 * @param  meeting
		 * @param  httpSession
		 * @throws 	Exception
		 * @return 	void
	 */
	public Map<String, Integer> examCount(HttpSession httpSession);
	/**
	 * 最新5条通知公告
	 */
	public List<Exam> getNewExam() throws Exception;
}
