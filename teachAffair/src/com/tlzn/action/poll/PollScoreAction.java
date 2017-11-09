package com.tlzn.action.poll;


import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.model.poll.TpollScore;
import com.tlzn.pageModel.base.Json;
import com.tlzn.service.poll.PollScoreServiceI;

@Action(value = "pollScore", results = {
		@Result(name = "pollInsAndScor", location = "/general/poll/pollInsAndScor.jsp"),
		@Result(name = "pollInsAndScorSelect", location = "/general/poll/pollInsAndScorSelect.jsp"),
		@Result(name = "pollInsAndScorEdit", location = "/general/poll/pollInsAndScorEdit.jsp"),
		@Result(name = "pollBatchAddScor", location = "/general/poll/pollBatchAddScor.jsp"),
		@Result(name = "commMemoEdit", location = "/general/poll/pollcommMemoEdit.jsp"),
		
})
public class PollScoreAction extends BaseAction implements ModelDriven<TpollScore>{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(PollScoreAction.class);
	
	private PollScoreServiceI pollScoreService;
	
	private TpollScore pollScore=new TpollScore();
	public TpollScore getModel() {
		return pollScore;
	}
	
	/**
	 * 批示与采用计分登记
	 */
	public String pollInsAndScor(){
		return "pollInsAndScor";
	}
	
	/**
	 * 批示与采用计分登记选择
	 */
	public String pollInsAndScorSelect(){
		return "pollInsAndScorSelect";
	}
	
	/**
	 * 批示与采用计分编辑
	 */
	public String pollInsAndScorEdit(){
		return "pollInsAndScorEdit";
	}
	/**
	 * 计会批量登记
	 */
	public String pollBatchAddScor(){
		return "pollBatchAddScor";
	}
	
	//采用记分登记
	public void  addPollInsAndScor(){
		Json j = new Json();
		try {
			String res=pollScoreService.addPollInsAndScor(pollScore);
			j.setSuccess(true);
			j.setMsg(res);
		} catch (Exception e) {
			j.setMsg("保存失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	
	/**
	 * 批示记分记录列表
	 */
	public void scoreRecordDatagrid() {
		try {
			super.writeDataGridJson(pollScoreService.scoreRecordDatagrid(pollScore));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 批注和备注编辑
	 */
	public String commMemoEdit(){
		
		try {
			TpollScore ps=pollScoreService.commMemo_find(pollScore);;
			request.setAttribute("pollScore",ps);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "commMemoEdit";
	}
	/**
	 * 批注和备注编辑提交
	 */
	public void commMemo_submit(){
		Json j = new Json();
		try {
			pollScoreService.commMemo_submit(pollScore,httpSession);
			j.setSuccess(true);
			j.setMsg("编辑成功!");
		} catch (Exception e) {
			j.setMsg("编辑失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	/**
	 * 批示记分记录删除
	 */
	public void scoreRecord_del() {
		Json j = new Json();
		try {
			pollScoreService.scoreRecordDel(pollScore);
			j.setSuccess(true);
			j.setMsg("删除成功!");
		} catch (Exception e) {
			j.setMsg("删除失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	//采用记分登记
	public void  batchAddScor(){
		Json j = new Json();
		try {
		pollScoreService.batchAddScor(pollScore);
			j.setSuccess(true);
			j.setMsg("计分信息添加成功!");
		} catch (Exception e) {
			j.setMsg("计分信息添加失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	
	public PollScoreServiceI getPollScoreService() {
		return pollScoreService;
	}
	@Autowired
	public void setPollScoreService(PollScoreServiceI pollScoreService) {
		this.pollScoreService = pollScoreService;
	}

}
