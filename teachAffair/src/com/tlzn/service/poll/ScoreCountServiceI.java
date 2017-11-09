package com.tlzn.service.poll;

import com.tlzn.model.poll.TpollScore;
import javax.servlet.http.HttpSession;
import com.tlzn.pageModel.base.DataGrid;


public interface ScoreCountServiceI {
	public String sumCount(String groupType,String secondaryId) throws Exception;
	public String getColumns() throws Exception;
	public String detailCount(TpollScore pollScore) throws Exception;

	public DataGrid classCount(TpollScore pollScore) throws Exception;

}
