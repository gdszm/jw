package com.tlzn.action.poll;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.model.poll.Tpoll;
import com.tlzn.model.poll.TpollScore;
import com.tlzn.model.sys.Tdic;
import com.tlzn.model.sys.Tsecondary;
import com.tlzn.pageModel.sys.Dic;
import com.tlzn.pageModel.sys.Seco;
import com.tlzn.model.sys.Trules;
import com.tlzn.service.poll.ScoreCountServiceI;
import com.tlzn.service.sys.DicServiceI;
import com.tlzn.util.base.ResourceUtil;
import com.tlzn.util.base.Util;
import com.tlzn.service.sys.RulesServiceI;

@Action(value = "scoreCount", results = {
		@Result(name = "countsum", location = "/general/poll/poll_sumcount.jsp"),
		@Result(name = "countdetail", location = "/general/poll/poll_detailcount.jsp"),
		@Result(name = "countclass", location = "/general/poll/poll_classcount.jsp"),

})
public class ScoreCountAction extends BaseAction implements ModelDriven<TpollScore>{
	
	private static final long serialVersionUID = 1L;
	
	private String groupType;//分组类型
	private String secondaryId;//届次
	
	private ScoreCountServiceI scoreCountService;
	private RulesServiceI rulesService;
	private DicServiceI dicService;
	
	private TpollScore pollScore=new TpollScore();
	
	public TpollScore getModel() {
		return pollScore;
	}
	
	public String getGroupType() {
		return groupType;
	}
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	public String getSecondaryId() {
		return secondaryId;
	}
	public void setSecondaryId(String secondaryId) {
		this.secondaryId = secondaryId;
	}
	
	public ScoreCountServiceI getScoreCountService() {
		return scoreCountService;
	}
	@Autowired
	public void setScoreCountService(ScoreCountServiceI scoreCountService) {
		this.scoreCountService = scoreCountService;
	}
	public DicServiceI getDicService() {
		return dicService;
	}
	@Autowired
	public void setDicService(DicServiceI dicService) {
		this.dicService = dicService;
	}
	public RulesServiceI getRulesService() {
		return rulesService;
	}
	@Autowired
	public void setRulesService(RulesServiceI rulesService) {
		this.rulesService = rulesService;
	}
	
	/**
	 * 社情民意积分统计
	 */
	public String countsum(){
		return "countsum";
	}
	/**
	 * 社情民意积分明细统计
	 */
	public String countdetail(){
		Dic dic=new Dic();
		dic.setCtype("POLLTYPE");
		List<Tdic> ptList= dicService.findAllTdic(dic);
		request.setAttribute("ptList", ptList);
		return "countdetail";
	}
	/**
	 * 社情民意积分分类统计
	 */
	public String countclass(){
		List<Trules> ruleList = null;
		try {
			ruleList = rulesService.combobox();
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("ruleList", ruleList);
		return "countclass";
	}
	
	/**
	 * 社情民意积分排名统计
	 */
	public void sumCount() {
		if(Util.getInstance().isEmpty(secondaryId)){
			secondaryId=((Seco)httpSession.getAttribute(ResourceUtil.getSessionSeco())).getSecondaryId();
		}
		try {
			super.writeListJson(scoreCountService.sumCount(groupType,secondaryId));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 社情民意积分明细表头数据获取
	 */
	public void getColumns() {
		try {
			super.writeListJson(scoreCountService.getColumns());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 社情民意积分明细数据获取
	 */
	public void detailCount() {
		if(Util.getInstance().isEmpty(pollScore.getPoll())){
			secondaryId=((Seco)httpSession.getAttribute(ResourceUtil.getSessionSeco())).getSecondaryId();
			Tpoll poll = new Tpoll();
			Tsecondary tsecondary= new Tsecondary();
			tsecondary.setSecondaryId(secondaryId);
			poll.setTsecondary(tsecondary);
			pollScore.setPoll(poll);
		}else if(Util.getInstance().isEmpty(pollScore.getPoll().getTsecondary())){
			secondaryId=((Seco)httpSession.getAttribute(ResourceUtil.getSessionSeco())).getSecondaryId();
			Tpoll poll = pollScore.getPoll();
			Tsecondary tsecondary= new Tsecondary();
			tsecondary.setSecondaryId(secondaryId);
			poll.setTsecondary(tsecondary);
			pollScore.setPoll(poll);
		}
		try {
			super.writeListJson(scoreCountService.detailCount(pollScore));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 社情民意和积分列表
	 */
	public void classCount() {
		if(pollScore.getPoll()==null){
			Tpoll poll=new Tpoll();
			Tsecondary ts=new Tsecondary();
			ts.setSecondaryId(((Seco)httpSession.getAttribute(ResourceUtil.getSessionSeco())).getSecondaryId());
			poll.setTsecondary(ts);
			pollScore.setPoll(poll);
		}else if(pollScore.getPoll().getTsecondary()==null){
			Tpoll poll=pollScore.getPoll();
			Tsecondary ts=new Tsecondary();
			ts.setSecondaryId(((Seco)httpSession.getAttribute(ResourceUtil.getSessionSeco())).getSecondaryId());
			poll.setTsecondary(ts);
			pollScore.setPoll(poll);
		}else if(pollScore.getPoll().getTsecondary().getSecondaryId()==null||"".equals(pollScore.getPoll().getTsecondary().getSecondaryId())){
			Tpoll poll=pollScore.getPoll();
			Tsecondary ts=poll.getTsecondary();
			ts.setSecondaryId(((Seco)httpSession.getAttribute(ResourceUtil.getSessionSeco())).getSecondaryId());
			poll.setTsecondary(ts);
			pollScore.setPoll(poll);
		}
		try {
			super.writeDataGridJson(scoreCountService.classCount(pollScore));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
