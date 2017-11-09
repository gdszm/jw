package com.tlzn.service.eduManage;

import javax.servlet.http.HttpSession;

import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.eduManage.AwardPenalty;
import com.tlzn.pageModel.eduManage.WorkExp;

public interface ApServiceI {
	public AwardPenalty get(String id) throws Exception;
	public DataGrid datagrid(AwardPenalty awardPenalty)throws Exception;
	public AwardPenalty save(AwardPenalty awardPenalty,HttpSession httpSession) throws Exception;
	public String upDateOrAdd(AwardPenalty awardPenalty,HttpSession httpSession) throws Exception;
	public void del(AwardPenalty awardPenalty) throws Exception;
	public void edit(AwardPenalty awardPenalty) throws Exception;
}
