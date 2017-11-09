package com.tlzn.action.poll;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.model.poll.Tpoll;
import com.tlzn.model.poll.TpollHandle;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.sys.Comp;
import com.tlzn.pageModel.sys.Hand;
import com.tlzn.service.poll.PollHandleServiceI;
import com.tlzn.service.poll.PollServiceI;

@Action(value = "pollHandle", results = {
		@Result(name = "pollHandle", location = "/general/poll/poll_handle.jsp"),
		@Result(name = "pollHandleAdd", location = "/general/poll/poll_handleAdd.jsp"),
		@Result(name = "handleCheck", location = "/general/poll/poll_review.jsp"),
		@Result(name = "pollBackInfo", location = "/general/poll/poll_handleBackInfo.jsp"),
		@Result(name = "reviewCheck", location = "/general/poll/poll_reviewCheck.jsp"),
		@Result(name = "pollRebut", location = "/general/poll/poll_reviewRebut.jsp"),
		@Result(name = "hand", location = "/general/poll/hand.jsp"),//社情民意办复页面
		@Result(name = "handAE", location = "/general/poll/handAE.jsp"), //社情民意办理回复页面
		@Result(name = "adviceInfo", location = "/general/poll/hand_advice.jsp"), //办复意见页面
		@Result(name = "handBack", location = "/general/poll/handBack.jsp") , //申退页面

})
public class PollHandleAction extends BaseAction implements ModelDriven<TpollHandle>{
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(PollHandleAction.class);
	
	private PollHandleServiceI pollHandleService;
	private PollServiceI pollService;
	private String statusType;
	
	public String getStatusType() {
		return statusType;
	}
	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}
	public PollHandleServiceI getPollHandleService() {
		return pollHandleService;
	}
	@Autowired
	public void setPollHandleService(PollHandleServiceI pollHandleService) {
		this.pollHandleService = pollHandleService;
	}
	
	public PollServiceI getPollService() {
		return pollService;
	}
	@Autowired
	public void setPollService(PollServiceI pollService) {
		this.pollService = pollService;
	}

	private TpollHandle handle=new TpollHandle();
	public TpollHandle getModel() {
		
		return handle;
	}
	/**
	 * 社情民意转办页面
	 */
	public String pollHandle() {
		return "pollHandle";
	}
	/**
	 * 社情民意办复审查页面
	 */
	public String handleCheck() {
		return "handleCheck";
	}
	/**
	 * 社情民意转办
	 */
	public String pollHandleAdd() {
		try {
			Tpoll poll=pollService.findTpoll(handle.getPoll().getPollId());
			request.setAttribute("obj",poll);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "pollHandleAdd";
	}
	//社情民意申退理由
	public String pollBackInfo(){
		try {
			this.request.setAttribute("list", pollHandleService.getPollHandle(handle));
			this.request.setAttribute("poll", pollService.findTpoll(handle.getPoll().getPollId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "pollBackInfo"; 
	}
	//社情民意申退理由
	public String reviewCheck(){
		try {
			this.request.setAttribute("list", pollHandleService.getPollHandle(handle));
			this.request.setAttribute("poll", pollService.findTpoll(handle.getPoll().getPollId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "reviewCheck"; 
	}
	public String pollRebut() {
		try {
			this.request.setAttribute("obj", pollHandleService.findObj(handle));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "pollRebut";
	}
	
	
	/**
	 * 办理单位获取
	 */
	public void handleGrid(){
		try {
			DataGrid dataGrid = pollHandleService.handleDatagrid(handle);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	//转办数据保存
	public void confirm(){
		Json j = new Json();
		try {
			pollHandleService.confirm(handle);
			j.setSuccess(true);
			j.setMsg("社情民意转办设置成功!");
		} catch (Exception e) {
			j.setMsg("社情民意转办设置失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	//单位调整
	public void hangleChange(){
		Json j = new Json();
		try {
			pollHandleService.handleChange(handle);
			j.setSuccess(true);
			j.setMsg("承办单位调整设置成功!");
		} catch (Exception e) {
			j.setMsg("承办单位调整设置失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	
	//拒绝申退
	public void handleRefuse(){
		Json j = new Json();
		try {
			System.out.println("handleId==="+handle.getHandleId());
			pollHandleService.refuse(handle);
			j.setMsg("拒绝申退操作成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg("拒绝申退操作失败!");
			e.printStackTrace();
		}
		super.writeJson(j);
	}
	//拒绝申退
	public void pollRefuse(){
		Json j = new Json();
		try {
			pollHandleService.pollRefuse(handle);
			j.setSuccess(true);
			j.setMsg("拒绝申退操作成功！");
		} catch (Exception e) {
			j.setMsg("拒绝申退操作失败!");
			e.printStackTrace();
		}
		super.writeJson(j);
	}
	/**
	 * 
		 * 办复报告审查驳回
		 * 
		 * @param 参数
		 * 
		 * @throws 	Exception
		 * 
		 * @return 		返回值
	 */
	public void reject() {
		Json j = new Json();
		try {
			pollHandleService.reject(handle);
			j.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg(e.getLocalizedMessage());
		}
		super.writeJson(j);
	}
	/**
	 * 
		 * 办复报告审查通过
		 * 
		 * @param hand 参数
		 * 
		 * 
	 */
	public void checkPass(){
		Json j = new Json();
		try {
			pollHandleService.checkPass(handle);
			j.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg(e.getLocalizedMessage());
		}
		super.writeJson(j);
	}
	/**
	 * 社情民意办复
	 */
	public String hand() {
		if(statusType!=null){
			ServletActionContext.getRequest().setAttribute("statusType",statusType);
		}
		return "hand";
	}
	/**
	 * 社情民意办复列表
	 */
	public void datagrid() {
		try {
			if((Comp)httpSession.getAttribute("sessionComp")==null) return;
			DataGrid dataGrid=pollHandleService.datagrid(handle, ((Comp)httpSession.getAttribute("sessionComp")).getCompanyId());
			super.writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 社情民意签收
	 */
	public void receiv(){
		Json j = new Json();
		try {
			pollHandleService.receiv(handle);
			j.setMsg("签收成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg(e.getLocalizedMessage());
		}
		super.writeJson(j);
	}
	/**
	 * 社情民意办理回复按钮跳转
	 */
	public String handAE() {
		try {
			this.request.setAttribute("handle", pollHandleService.findObj(handle));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "handAE";
	}
	/**
	 * 社情民意办理回复提交
	 */
	public void add(){
		Json j = new Json();
		try {
			pollHandleService.save(handle);
			j.setMsg("办复成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg(e.getLocalizedMessage());
		}
		super.writeJson(j);
	}
	/**
	 * 社情民意办理回复意见跳转
	 */
	public String adviceInfo() {
		try {
			this.request.setAttribute("handle", pollHandleService.findObj(handle));
			System.out.println("======"+((Hand)request.getAttribute("handle")).getAnswerMode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "adviceInfo";
	}
	/**
	 * 社情民意申退跳转
	 */
	public String handBack() {
		try {
			this.request.setAttribute("handle", pollHandleService.findObj(handle));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "handBack";
	}
	/**
	 * 社情民意申退处理
	 */
	public void back(){
		Json j = new Json();
		try {
			pollHandleService.back(handle);
			j.setMsg("申退成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg(e.getLocalizedMessage());
		}
		super.writeJson(j);
	}
	/**
	 * 社情民意报表
	 */
	public void report() {
		Json j = new Json();
		try {
			j.setObj(pollHandleService.report(handle, ((Comp)httpSession.getAttribute("sessionComp")).getCompanyId()));
			j.setSuccess(true);
			j.setMsg("报表生成成功!");
		} catch (Exception e) {
			j.setMsg("报表生成失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		super.writeJson(j);
	}
	
}
