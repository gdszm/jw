package com.tlzn.action.sys;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.base.SessionInfo;
import com.tlzn.pageModel.info.Proposal;
import com.tlzn.pageModel.sys.Comp;
import com.tlzn.pageModel.sys.Hand;
import com.tlzn.service.info.PropServiceI;
import com.tlzn.service.sys.HandServiceI;
import com.tlzn.util.base.Constants;

@Action(value = "hand", results = { 
		@Result(name = "hand", location = "/general/admin/hand.jsp") ,
		@Result(name = "handAE", location = "/general/admin/handAE.jsp") ,
		@Result(name = "handBack", location = "/general/admin/handBack.jsp") ,
		@Result(name = "handRefuse", location = "/general/admin/handRefuse.jsp") ,
		@Result(name = "handSitu", location = "/general/admin/handSitu.jsp") ,
		@Result(name = "handSituAE", location = "/general/admin/handSituAE.jsp"),
		@Result(name = "monitor", location = "/general/info/hand_monitor.jsp"),
		@Result(name = "propRebut", location = "/general/info/hand_rebut.jsp"),
		@Result(name = "handCheck", location = "/general/info/hand_check.jsp") ,
		@Result(name = "handSee", location = "/general/info/hand_see.jsp"),
		@Result(name = "backInfo", location = "/general/info/hand_backInfo.jsp"),
		@Result(name = "adviceInfo", location = "/general/info/hand_advice.jsp"),
		@Result(name = "satisfy", location = "/general/admin/handSatisfy.jsp")
		})
public class HandAction extends BaseAction implements ModelDriven<Hand> {

	protected final Log log = LogFactory.getLog(getClass());

	private static final long serialVersionUID = 1L;
	private HandServiceI handService;
	private PropServiceI propService;
	private Hand hand=new Hand();
	
	private String statusType;
	private String field;
	
	public String getStatusType() {
		return statusType;
	}
	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public HandServiceI getHandService() {
		return handService;
	}
	@Autowired
	public void setHandService(HandServiceI handService) {
		this.handService = handService;
	}

	public PropServiceI getPropService() {
		return propService;
	}
	
	@Autowired
	public void setPropService(PropServiceI propService) {
		this.propService = propService;
	}
	
	public Hand getModel() {
		return hand;
	}
	
	public String monitor() {
		return "monitor";
	}
	
	public String hand() {
		if(statusType!=null){
			ServletActionContext.getRequest().setAttribute("statusType",statusType);
		}
		return "hand";
	}

	public String handAE() {
		try {
			this.request.setAttribute("handle", handService.findObj(hand));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "handAE";
	}

	public String handBack() {
		try {
			this.request.setAttribute("handle", handService.findObj(hand));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "handBack";
	}

	public String handSitu() {
		if(statusType!=null){
			ServletActionContext.getRequest().setAttribute("statusType",statusType);
		}
		return "handSitu";
	}
	
	public String handquery() {
		ServletActionContext.getRequest().setAttribute("companyId",hand.getCompanyId());
		ServletActionContext.getRequest().setAttribute("field",field);
		SessionInfo sessionInfo=(SessionInfo) httpSession.getAttribute("sessionInfo");
		if (Constants.DIC_TYPE_YHZB_LDSP.equals(sessionInfo.getUserGroup())) {
			return "handCheck";
		}else{
			return "handSitu";
		}
		
	}
	public String handCheck() {
		if(statusType!=null){
			ServletActionContext.getRequest().setAttribute("statusType",statusType);
		}
		return "handCheck";
	}
	public String propRebut() {
		try {
			this.request.setAttribute("handle", handService.findObj(hand));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "propRebut";
	}
	public String adviceInfo() {
		try {
			this.request.setAttribute("handle", handService.findObj(hand));
			System.out.println("======"+((Hand)request.getAttribute("handle")).getAnswerMode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "adviceInfo";
	}
	public String list() {
		try {
			this.request.setAttribute("list", handService.list(hand));
			Proposal prop=new Proposal();
			prop.setProposalId(hand.getProposalId());
			this.request.setAttribute("prop", propService.findProp(prop));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "handSituAE";
	}
	public String handSee() {
		try {
			this.request.setAttribute("list", handService.list(hand));
			Proposal prop=new Proposal();
			prop.setProposalId(hand.getProposalId());
			this.request.setAttribute("prop", propService.findProp(prop));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "handSee";
	}
	public String backInfo(){
		try {
			this.request.setAttribute("list", handService.list(hand));
			Proposal prop=new Proposal();
			prop.setProposalId(hand.getProposalId());
			this.request.setAttribute("prop", propService.findProp(prop));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "backInfo"; 
	}
	public String satisfy() {
		try {
			this.request.setAttribute("list", handService.list(hand));
			this.request.setAttribute("prop", propService.findObj(hand.getProposalId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "satisfy";
	}

	public void add(){
		Json j = new Json();
		try {
			handService.save(hand);
			j.setMsg("办复成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg(e.getLocalizedMessage());
		}
		super.writeJson(j);
	}
	
	public void update(){
		Json j = new Json();
		try {
			handService.update(hand);
			j.setMsg("设置成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg(e.getLocalizedMessage());
		}
		super.writeJson(j);
	}
	
	public void receiv(){
		Json j = new Json();
		try {
			handService.receiv(hand);
			j.setMsg("签收成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg(e.getLocalizedMessage());
		}
		super.writeJson(j);
	}
	
	public void back(){
		Json j = new Json();
		try {
			handService.back(hand);
			j.setMsg("申退成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg(e.getLocalizedMessage());
		}
		super.writeJson(j);
	}
	//拒绝申退
	public void refuse(){
		Json j = new Json();
		try {
			handService.refuse(hand);
			j.setMsg("拒绝申退操作成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg(e.getLocalizedMessage());
		}
		super.writeJson(j);
	}
	
	public void datagrid() {
		try {
			if((Comp)httpSession.getAttribute("sessionComp")==null)return;
			super.writeDataGridJson(handService.datagrid(hand, ((Comp)httpSession.getAttribute("sessionComp")).getCompanyId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void report() {
		Json j = new Json();
		try {
			j.setObj(handService.report(hand, ((Comp)httpSession.getAttribute("sessionComp")).getCompanyId()));
			j.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg(e.getLocalizedMessage());
		}
		super.writeJson(j);
	}
	
	public void reject() {
		Json j = new Json();
		try {
			handService.reject(hand);
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
			handService.checkPass(hand);
			j.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg(e.getLocalizedMessage());
		}
		super.writeJson(j);
	}
	
	public void datagridByProp() {
		try {
			super.writeDataGridJson(handService.datagrid(hand));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
		 * 办理监控
		 * 
	 */
	public void monitordatagrid() {
		try {
			super.writeListJson(handService.monitordatagrid(httpSession));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
