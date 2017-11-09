package com.tlzn.action.committee;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import antlr.Utils;

import com.opensymphony.xwork2.ModelDriven;
import com.sun.xml.internal.fastinfoset.stax.events.Util;
import com.tlzn.action.activity.ActivityAction;
import com.tlzn.action.base.BaseAction;
import com.tlzn.model.sys.Tdolog;
import com.tlzn.model.sys.Tuser;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.base.SessionInfo;
import com.tlzn.pageModel.sys.Comm;
import com.tlzn.pageModel.sys.Seco;
import com.tlzn.pageModel.sys.User;
import com.tlzn.service.committee.CommitteeServiceI;
import com.tlzn.service.sys.CommServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Encrypt;
import com.tlzn.util.base.ResourceUtil;

@Action(value = "committee", results = { 
		@Result(name = "committee", location = "/general/committee/committee.jsp"),
		@Result(name = "committeeAE", location = "/general/committee/committeeAE.jsp"),
		@Result(name = "view", location = "/general/committee/committeeView.jsp"),
		@Result(name = "committeeQ", location = "/general/committee/committeeQ.jsp"),
		@Result(name = "committeeC", location = "/general/committee/countCommittee.jsp"),
		@Result(name = "countCommittee", location = "/general/committee/countCommittee.jsp"),
		//委员管理首页
		@Result(name = "portal", location = "/public/layout/portal/portalCommittee.jsp"),
		//系统统合首页
		@Result(name = "portalMain", location = "/public/layout/portal/portal.jsp"),
		})
public class CommitteeAction extends BaseAction implements ModelDriven<Comm> {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ActivityAction.class);
	private CommitteeServiceI committeeService;
	
	public CommitteeServiceI getCommitteeService() {
		return committeeService;
	}

	public void setCommitteeService(CommitteeServiceI committeeService) {
		this.committeeService = committeeService;
	}
	private Comm comm=new Comm();

	public Comm getModel() {
		return comm;
	}
	
	//委员信息管理页面跳转
	public String committee() {
		return "committee";
	}
	//委员信息添加修改页面跳转
	public String committeeAE() {
//		try {
//			this.request.setAttribute("comm", committeeService.getCommBycode(comm.getCode()));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return "committeeAE";
	}
	//委员信息查看详情页面跳转
	public String view() {
		try {
			this.request.setAttribute("comm", committeeService.getCommBycode(comm.getCode()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "view";
	}
	//委员信息查询页面跳转
	public String committeeQ() {
		return "committeeQ";
	}
	//委员信息统计页面跳转
	public String committeeC() {
		return "committeeC";
	}
	
	//系统统合管理首页
	public String doNotNeedAuth_main(){
		String userCode=((SessionInfo)httpSession.getAttribute("sessionInfo")).getUserCode();
		try {
			if(!Util.isEmptyString(userCode)){
				this.request.setAttribute("comm", committeeService.getCommBycode(userCode));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "portalMain";
	}

	public void datagrid() {
		try {
			String secondaryId=request.getParameter("secondaryId");
			comm.setSecondaryCode(secondaryId);
			super.writeDataGridJson(committeeService.datagrid(comm));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	//委员管理 委员履职 履职情况 委员信息查询
	public void wylzDatagrid() {
		try {
			String secondaryId=request.getParameter("secondaryId");
			comm.setSecondaryCode(secondaryId);
			super.writeDataGridJson(committeeService.datagrid(comm));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
		 * 当前届次委员
		 * @throws 	Exception
		 * 
		 * @return 		void
	 */
	public void doNotNeedAuth_currentDatagrid() {
		try {
			String secondaryId=((Seco)httpSession.getAttribute(ResourceUtil.getSessionSeco())).getSecondaryId();
			comm.setSecondaryCode(secondaryId);
			super.writeDataGridJson(committeeService.datagrid(comm));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	public void add(){
		Json j = new Json();
		try {
			committeeService.save(comm);
			j.setMsg("添加成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			j.setMsg(e.getLocalizedMessage());
		}
		super.writeJson(j);
	}

	public void edit(){
		Json j = new Json();
		try {
			committeeService.update(comm);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			j.setMsg(e.getLocalizedMessage());
		}
		super.writeJson(j);
	}
	
	public void report() {
		Json j = new Json();
		try {
			j.setObj(committeeService.report(comm));
			j.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			j.setMsg(e.getLocalizedMessage());
		}
		super.writeJson(j);
	}
	
	public void change(){
		Json j = new Json();
		try {
			committeeService.change(comm);
			j.setSuccess(true);
			j.setMsg("操作成功!");
		} catch (Exception e) {
			j.setMsg("操作失败！");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		super.writeJson(j);
	}
	//会议发言首页统计
	public String doNotNeedAuth_committeeCount(){
		try {
			Map<String,Integer> countMeetingMap=committeeService.committeeCount(httpSession);
			this.request.setAttribute("countMeetingMap", countMeetingMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "portal";
	}
	
	/**
	 * 参加活动大于5次的委员
	 */
	public void doNotNeedAuth_cjdatagrid(){
		try {
			super.writeDataGridJson(committeeService.cjdatagrid(comm));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 缺席活动大于5次的委员
	 */
	public void doNotNeedAuth_wcjdatagrid(){
		try {
			super.writeDataGridJson(committeeService.wcjdatagrid(comm));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 从未参加活动的委员
	 */
	public void doNotNeedAuth_cwdatagrid(){
		try {
			super.writeDataGridJson(committeeService.cwdatagrid(comm));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	
	/**
	 * 参加会议大于5次的委员
	 */
	public void doNotNeedAuth_cxdatagrid(){
		try {
			super.writeDataGridJson(committeeService.cxdatagrid(comm));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 缺席会议大于5次的委员
	 */
	public void doNotNeedAuth_qxdatagrid(){
		try {
			super.writeDataGridJson(committeeService.qxdatagrid(comm));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 从未参加会议的委员
	 */
	public void doNotNeedAuth_nodatagrid(){
		try {
			super.writeDataGridJson(committeeService.nodatagrid(comm));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 重置密码
	 */
	public void resetPass() throws Exception {
		Json j = new Json();
		try {
			String code=comm.getCode();
			committeeService.resetPass(code);
			j.setSuccess(true);
			j.setMsg("操作成功!");
		} catch (Exception e) {
			j.setMsg("操作失败！");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		super.writeJson(j);
	}
}
