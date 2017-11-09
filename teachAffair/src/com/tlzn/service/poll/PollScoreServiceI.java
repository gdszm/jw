package com.tlzn.service.poll;

import javax.servlet.http.HttpSession;

import com.tlzn.model.poll.TpollScore;
import com.tlzn.pageModel.base.DataGrid;

public interface PollScoreServiceI {
	public String addPollInsAndScor(TpollScore pollScore) throws Exception;
	public DataGrid scoreRecordDatagrid(TpollScore pollScore)  throws Exception;
	public TpollScore commMemo_find(TpollScore pollScore) throws Exception;
	public void commMemo_submit(TpollScore pollScore, HttpSession httpSession) throws Exception;
	public void scoreRecordDel(TpollScore pollScore) throws Exception;
	public void batchAddScor(TpollScore pollScore) throws Exception;
	public boolean isLeader(TpollScore ps) throws Exception;
}
