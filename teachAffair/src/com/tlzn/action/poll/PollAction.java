package com.tlzn.action.poll;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.model.poll.Tpoll;
import com.tlzn.model.sys.Tsecondary;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.base.SessionInfo;
import com.tlzn.pageModel.sys.Seco;
import com.tlzn.service.info.PropServiceI;
import com.tlzn.service.poll.PollHandleServiceI;
import com.tlzn.service.poll.PollServiceI;
import com.tlzn.util.base.PropertyUtil;
import com.tlzn.util.base.Util;

@Action(value = "poll", results = {
		@Result(name = "pollsub", location = "/general/poll/poll_sub.jsp"),
		@Result(name = "pollown", location = "/general/poll/poll_own.jsp"),
		@Result(name = "pollOwnEdit", location = "/general/poll/poll_ownEdit.jsp"),
		@Result(name = "poll", location = "/general/poll/poll.jsp"),
		@Result(name = "pollAdd", location = "/general/poll/pollAdd.jsp"),
		@Result(name = "pollEdit", location = "/general/poll/pollEdit.jsp"),
		@Result(name = "pollSee", location = "/general/poll/poll_see.jsp"),
		@Result(name = "pollMerge", location = "/general/poll/pollMerge.jsp"),
		@Result(name = "pollStress", location = "/general/poll/pollStress.jsp"),
		@Result(name = "pollcheck", location = "/general/poll/poll_check.jsp"),
		@Result(name = "pollCheckMark", location = "/general/poll/poll_checkMark.jsp"),
		@Result(name = "pollIssue", location = "/general/poll/poll_issue.jsp"),
		@Result(name = "pollIssued", location = "/general/poll/poll_issued.jsp"),
		//社情民意首页
		@Result(name = "portal", location = "/public/layout/portal/pollPortal.jsp")
})
public class PollAction extends BaseAction implements ModelDriven<Tpoll> {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(PollAction.class);

	private PollServiceI pollService;
	
	private PollHandleServiceI pollHandleService;
	//gds add start
	private String statusType;
	
	public String getStatusType() {
		return statusType;
	}
	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}
	//gds add end
	public PollServiceI getPollService() {
		return pollService;
	}

	@Autowired
	public void setPollService(PollServiceI pollService) {
		this.pollService = pollService;
	}

	public PollHandleServiceI getPollHandleService() {
		return pollHandleService;
	}
	@Autowired
	public void setPollHandleService(PollHandleServiceI pollHandleService) {
		this.pollHandleService = pollHandleService;
	}
	
	private Tpoll poll = new Tpoll();

	public Tpoll getModel() {
		return poll;
	}

	/**
	 * 社情民意提交窗口
	 */
	public String pollsub() {
		return "pollsub";
	}

	/**
	 * 我的社情民意
	 */
	public String pollown() {
		return "pollown";
	}

	/**
	 * 社情民意管理
	 */
	public String poll() {
		if(statusType!=null){
			ServletActionContext.getRequest().setAttribute("statusType",statusType);
		}
		return "poll";
	}

	/**
	 * 社情民意新增
	 */
	public String pollAdd() {
		return "pollAdd";
	}

	/**
	 * 社情民意审查
	 */
	public String pollEdit() {
		try {
			Tpoll tpoll = pollService.findTpoll(poll.getPollId());
			request.setAttribute("obj", tpoll);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "pollEdit";
	}

	/**
	 * 社情民意详情查看
	 */
	public String pollSee() {
		try {
			Tpoll tpoll = pollService.findTpoll(poll.getPollId());
			ServletActionContext.getRequest().setAttribute("poll", tpoll);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "pollSee";
	}

	/**
	 * 社情民意合并
	 */
	public String pollMerge() {
		return "pollMerge";
	}

	/**
	 * 社情民意重点设置页面
	 */
	public String pollStress() {
		return "pollStress";
	}

	/**
	 * 社情民意签发
	 */
	public String pollcheck() {
		if(statusType!=null){
			ServletActionContext.getRequest().setAttribute("statusType",statusType);
		}
		return "pollcheck";
	}

	/**
	 * 我的社情民意修改
	 */
	public String pollOwnEdit() {
		try {
			Tpoll tpoll = pollService.findTpoll(poll.getPollId());
			request.setAttribute("obj", tpoll);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "pollOwnEdit";
	}

	public String pollCheckMark() {
		try {
			Tpoll tpoll = pollService.findTpoll(poll.getPollId());
			request.setAttribute("obj", tpoll);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "pollCheckMark";
	}

	/**
	 * 计分登记
	 */
	public String pollIssue() {
		return "pollIssue";
	}
	/**
	 * 已印发社情民意
	 */
	public String pollIssued() {
		return "pollIssued";
	}
	// 保存
	public void add() {
		Json j = new Json();
		try {
			if(Util.getInstance().isEmpty(poll.getPollId())){
				j.setObj(pollService.add(poll, httpSession));
			}else{
				pollService.update(poll);
				j.setObj(poll.getPollId());
			}
			j.setSuccess(true);
			j.setMsg("保存成功!");
		} catch (Exception e) {
			j.setMsg("保存失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}

	// 提交
	public void submit() {
		Json j = new Json();
		try {
			pollService.submit(poll, httpSession);
			j.setSuccess(true);
			j.setMsg("提交成功!");
		} catch (Exception e) {
			j.setMsg("提交失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}

	/**
	 * 
	 * 我的社情民意列表
	 */
	public void datagridown() {
		String secondaryCode = ((Seco) httpSession.getAttribute("sessionSeco"))
				.getSecondaryId();
		String userCode = ((SessionInfo) httpSession
				.getAttribute("sessionInfo")).getUserCode();
		try {
			DataGrid dataGrid = pollService.datagrid(poll, userCode,
					secondaryCode);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 委员履职详情，社情民意列表查询
	 */
	public void datagridFulfil() {
		String secondaryCode =request.getParameter("secondaryCode");
		String userCode = request.getParameter("userCode");
		try {
			DataGrid dataGrid = pollService.datagridFulfil(poll, userCode,secondaryCode);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	// 修改
	public void edit() {
		Json j = new Json();
		try {
			pollService.update(poll);
			j.setSuccess(true);
			j.setMsg("修改成功!");
		} catch (Exception e) {
			j.setMsg("修改失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}

	// 提交
	public void submitEdit() {
		Json j = new Json();
		try {
			pollService.submitEdit(poll);
			j.setSuccess(true);
			j.setMsg("提交成功!");
		} catch (Exception e) {
			j.setMsg("提交失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}

	// 撤消提交
	public void revoke() {
		Json j = new Json();
		try {
			pollService.revoke(poll);
			j.setSuccess(true);
			j.setMsg("撤销提交成功!");
		} catch (Exception e) {
			j.setMsg("撤销提交失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}

	/**
	 * 
	 * 信息员获取社情民意列表
	 */
	public void datagrid() {
		String secondaryCode = ((Seco) httpSession.getAttribute("sessionSeco"))
				.getSecondaryId();
		try {
			DataGrid dataGrid = pollService.datagrid(poll, secondaryCode);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}

	// 信息员新增
	public void submitAdd() {
		Json j = new Json();
		try {
			j.setObj(pollService.submitAdd(poll, httpSession));
			j.setSuccess(true);
			j.setMsg("添加成功!");
		} catch (Exception e) {
			j.setMsg("添加失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}

	/**
	 * 
	 * 获取社情民意提交者信息
	 */
	public void submitter() {
		try {
			DataGrid dataGrid = pollService.submitter(poll);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}

	// 信息员审查保存
	public void checkEdit() {
		Json j = new Json();
		try {
			pollService.checkEdit(poll, httpSession);
			j.setSuccess(true);
			j.setMsg("审查提交成功!");
		} catch (Exception e) {
			j.setMsg("审查提交失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}

	// 合并社情民意
	public void setMerge() {
		Json j = new Json();
		try {
			pollService.setMerge(poll);
			j.setSuccess(true);
			j.setMsg("合并提交成功!");
		} catch (Exception e) {
			j.setMsg("合并提交失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	//取消合并社情民意
	public void cancelMerge(){
		Json j = new Json();
		try {
			pollService.cancelMerge(poll);
			j.setSuccess(true);
			j.setMsg("取消合并成功!");
		} catch (Exception e) {
			j.setMsg("取消合并失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	// 合并社情民意
	public void setStress() {
		Json j = new Json();
		try {
			pollService.setStress(poll);
			j.setSuccess(true);
			j.setMsg("重点设置成功!");
		} catch (Exception e) {
			j.setMsg("重点设置失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}

	// 提交审签社情民意
	public void setFiling() {
		Json j = new Json();
		try {
			pollService.setFiling(poll);
			j.setSuccess(true);
			j.setMsg("提交审签设置成功!");
		} catch (Exception e) {
			j.setMsg("提交审签设置失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}

	// 报表生成
	public void report() {
		Json j = new Json();
		try {
			j.setObj(pollService.report(poll, httpSession));
			j.setSuccess(true);
			j.setMsg("报表生成成功!");
		} catch (Exception e) {
			j.setMsg("报表生成失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}

	// 社情民意审批数据获取
	public void datagridCheck() {

		try {
			DataGrid dataGrid = pollService.datagrid(poll, httpSession);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}

	// 签发数据保存
	public void pollMark() {
		Json j = new Json();
		try {
			pollService.pollMark(poll, httpSession);
			j.setSuccess(true);
			j.setMsg("审核签发提交成功!");
		} catch (Exception e) {
			j.setMsg("审核签发提交失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}

	// gds add start
	/**
	 * 
	 * 计分登记社情民意列表
	 */
	public void datagridIssue() {
		// 届次编号
		String secondaryCode = ((Seco) httpSession.getAttribute("sessionSeco"))
				.getSecondaryId();
		try {
			DataGrid dataGrid = pollService.issueDatagrid(poll, secondaryCode);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	
	/**
	 * 
	 * 已印发社情民意列表
	 */
	public void datagridIssued() {
		try {
			DataGrid dataGrid = pollService.issuedDatagrid(poll);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 转办社情民意列表
	 */
	public void datagridAssign() {
		// 届次编号
		Seco seco = ((Seco) httpSession.getAttribute("sessionSeco"));
		Tsecondary tsecondary=new Tsecondary();
		
		try {
			PropertyUtil.copyProperties(tsecondary,seco);
			poll.setTsecondary(tsecondary);
			DataGrid dataGrid = pollService.datagridAssign(poll);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	
	// 社情民意新增（群众）
	public void publicAdd() {
		Json j = new Json();
		try {
			j.setObj(pollService.publicAdd(poll, httpSession));
			j.setSuccess(true);
			j.setMsg("提交成功!");
		} catch (Exception e) {
			j.setMsg("提交失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	
	public String pollwy(){
		if(statusType!=null){
			ServletActionContext.getRequest().setAttribute("statusType",statusType);
		}
		return "pollown";
	}
	
	// 社情民意交办
	public void pollAssign() {
		Json j = new Json();
		try {
			pollService.pollAssign(poll);
			j.setSuccess(true);
			j.setMsg("社情民意提交办理成功!");
		} catch (Exception e) {
			j.setMsg("社情民意提交办理失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	/**
	 * 
	 * 办复审查社情民意列表
	 */
	public void datagridReview() {
		// 届次编号
		Seco seco = ((Seco) httpSession.getAttribute("sessionSeco"));
		Tsecondary tsecondary=new Tsecondary();
		
		try {
			PropertyUtil.copyProperties(tsecondary,seco);
			poll.setTsecondary(tsecondary);
			DataGrid dataGrid = pollService.datagridReview(poll);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	// 社情民意定稿整理数据保存
	public void confirmSave() {
		Json j = new Json();
		try {
			pollService.confirmSave(poll,httpSession);
			j.setSuccess(true);
			j.setMsg("社情民意定稿整理数据保存成功!");
		} catch (Exception e) {
			j.setMsg("社情民意定稿整理数据保存失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	
	//社情民意首页统计+社情民意办理统计
	public String doNotNeedAuth_pollCount(){
		try {
			Map<String,Integer> countPollMap=pollService.pollCount(httpSession);
			Map<String,Integer> countPollHandleMap=pollHandleService.pollHandleCount(httpSession);
			this.request.setAttribute("countPollMap", countPollMap);
			this.request.setAttribute("countPollHandleMap", countPollHandleMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "portal";
	}
}
