package com.tlzn.action.info;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.base.SessionInfo;
import com.tlzn.pageModel.info.Proposal;
import com.tlzn.pageModel.sys.Comm;
import com.tlzn.pageModel.sys.Comp;
import com.tlzn.pageModel.sys.Seco;
import com.tlzn.service.info.PropServiceI;
//import com.tlzn.service.poll.PollHandleServiceI;
//import com.tlzn.service.poll.PollServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Util;

@Action(value = "prop", results = {
		@Result(name = "propsub", location = "/general/info/prop_sub.jsp"),
		@Result(name = "propwy", location = "/general/info/prop_wy.jsp"),
		@Result(name = "prop", location = "/general/info/prop.jsp"),
		@Result(name = "propAdd", location = "/general/info/propAdd.jsp"),
		@Result(name = "propEdit", location = "/general/info/propEdit.jsp"),
		@Result(name = "propWyEdit", location = "/general/info/prop_wyedit.jsp"),
		@Result(name = "propAdopt", location = "/general/info/propAdopt.jsp"),
		@Result(name = "propStress", location = "/general/info/propStress.jsp"),
		@Result(name = "propFine", location = "/general/info/propFine.jsp"),
		@Result(name = "propSee", location = "/general/info/propSee.jsp"),
		@Result(name = "propCheck", location = "/general/info/propCheck.jsp"),
		@Result(name = "review", location = "/general/info/review.jsp"),
		@Result(name = "assign", location = "/general/info/assign.jsp"),
		@Result(name = "assignSub", location = "/general/info/assignSub.jsp"),
		@Result(name = "propQuery", location = "/general/info/prop_query.jsp"),
		@Result(name = "checkAdd", location = "/general/info/checkAdd.jsp"),
		@Result(name = "propMerge", location = "/general/info/propMerge.jsp"),
		@Result(name = "portal", location = "/public/layout/portal/portalProp.jsp"),
		@Result(name = "propBackEdit", location = "/general/info/propBackEdit.jsp"),
		@Result(name = "propConfirm", location = "/general/info/propConfirm.jsp"),
		@Result(name = "propAllQuery", location = "/general/info/propAll_query.jsp")
		
})
public class PropAction extends BaseAction implements ModelDriven<Proposal>{
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(PropAction.class);
	
	private PropServiceI propService;
	
	
	private String statusType;
	
	public String getStatusType() {
		return statusType;
	}
	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}
	public PropServiceI getPropService() {
		return propService;
	}
	@Autowired
	public void setPropService(PropServiceI propService) {
		this.propService = propService;
	}

	private Proposal prop=new Proposal();
	public Proposal getModel() {
		return prop;
	}
	/*public PollServiceI getPollService() {
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
	}*/
	public String prop() {
		if(statusType!=null){
			ServletActionContext.getRequest().setAttribute("statusType",statusType);
		}
		return "prop";
	}
	public String propAdd() {
		return "propAdd";
	}
	public String propsub() {
		return "propsub";
	}
	public String propEdit(){
		try {
			Proposal p=propService.findProp(prop);
			ServletActionContext.getRequest().setAttribute("obj", p);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "propEdit";
	}
	public String propwy(){
		if(statusType!=null){
			ServletActionContext.getRequest().setAttribute("statusType",statusType);
		}
		return "propwy";
	}
	public String propWyEdit(){
		try {
			Proposal p=propService.findProp(prop);
			ServletActionContext.getRequest().setAttribute("obj", p);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "propWyEdit";
	}
	public String propAdopt(){
		return "propAdopt";
	}
	public String propStress(){
		return "propStress";
	}
	public String propFine(){
		return "propFine";
	}
	public String propCheck(){
		try {
			Proposal p=propService.findProp(prop);
			ServletActionContext.getRequest().setAttribute("obj", p);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "propCheck";
	}
	public String propConfirm(){
		try {
			Proposal p=propService.findProp(prop);
			ServletActionContext.getRequest().setAttribute("obj", p);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "propConfirm";
	}
	public String checkAdd(){
		return "checkAdd";
	}
	public String review(){
		return "review";
	}
	public String assign(){
		if(statusType!=null){
			ServletActionContext.getRequest().setAttribute("statusType",statusType);
		}
		return "assign";
	}
	public String assignSub(){
		return "assignSub";
	}
	public String propQuery(){
		return "propQuery";
	}
	public String propMerge(){
		return "propMerge";
	}
	public String propBackEdit(){
		return "propBackEdit";
	}
	public String propAllQuery(){
		return "propAllQuery";
	}
	/**
	 * 
		 * 提案列表
	 */
	public void datagrid() {
		String secondaryCode=((Seco)httpSession.getAttribute("sessionSeco")).getSecondaryId();
		try {
			DataGrid dataGrid=propService.datagrid(prop,secondaryCode);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	　* 委员提案列表
	 */
	public void wydatagrid(){
		String userCode=((SessionInfo)httpSession.getAttribute("sessionInfo")).getUserCode();
		String secondaryCode=((Seco)httpSession.getAttribute("sessionSeco")).getSecondaryId();
		try {
			writeDataGridJson(propService.datagrid(prop,userCode,secondaryCode));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		
	}
	/**
	 * 
	　* 获取审查提案
	 */
	public void reviewdatagrid(){
		try {
			writeDataGridJson(propService.reviewdatagrid(prop,httpSession));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	　* 获取交办提案
	 */
	public void assigndatagrid(){
		try {
			writeDataGridJson(propService.assigndatagrid(prop,httpSession));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	　* 提案查询
	 */
	public void querydatagrid(){
		try {
			writeDataGridJson(propService.querydatagrid(prop));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	　* 提案查询
	 */
	public void queryAllDatagrid(){
		try {
			writeDataGridJson(propService.queryAllDatagrid(prop));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 委员履职详情=>提案情况列表查询
	 */
	public void datagridFulfil() {
		String secondaryCode =request.getParameter("secondaryCode");
		String userCode = request.getParameter("userCode");
		try {
			DataGrid dataGrid = propService.datagridFulfil(prop, userCode,secondaryCode);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	
	//我要附议
	public void pdatagrid(){
		String secondaryCode=((Seco)httpSession.getAttribute("sessionSeco")).getSecondaryId();
		String userCode=((SessionInfo)httpSession.getAttribute("sessionInfo")).getUserCode();
		try {
			writeDataGridJson(propService.Pdatagrid(prop,secondaryCode,userCode));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		
	}
	//办复情况
	public void sdatagrid(){
		String secondaryCode=((Seco)httpSession.getAttribute("sessionSeco")).getSecondaryId();
		SessionInfo sessionInfo=(SessionInfo)httpSession.getAttribute("sessionInfo");
		try {
			if(Constants.DIC_TYPE_YHZB_LDSP.equals(sessionInfo.getUserGroup())){
				writeDataGridJson(propService.sdatagrid(prop,secondaryCode,httpSession));
			}else if (Constants.DIC_TYPE_YHZB_GLY.equals(sessionInfo.getUserGroup())) {
				writeDataGridJson(propService.sdatagrid(prop,secondaryCode));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		
	}
	//办复情况
	public void situdatagrid(){
		String secondaryCode=((Seco)httpSession.getAttribute("sessionSeco")).getSecondaryId();
		try {
			writeDataGridJson(propService.sdatagrid(prop,secondaryCode));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		
	}
	
	//我附议的提案
	public void mdatagrid(){
		if(httpSession.getAttribute("sessionSeco")==null || httpSession.getAttribute("sessionComm")==null)return;
		String secondaryCode=((Seco)httpSession.getAttribute("sessionSeco")).getSecondaryId();
		String commCode=((Comm)httpSession.getAttribute("sessionComm")).getCode();
		try {
			writeDataGridJson(propService.mdatagrid(prop,commCode,secondaryCode));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	//取消附议
	public void undo(){
		Json j = new Json();
		try {
			String commCode=((Comm)httpSession.getAttribute("sessionComm")).getCode();
			propService.undo(prop,commCode);
			j.setSuccess(true);
			j.setMsg("取消成功!");
		} catch (Exception e) {
			j.setMsg("取消失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}

	//新增
	public void add(){
		Json j = new Json();
		try {
			if(Util.getInstance().isEmpty(prop.getProposalId())){
				j.setObj(propService.add(prop,httpSession));
			}else{
				propService.add(prop,httpSession);
				j.setObj(prop.getProposalId());
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
	//提交提案
	public void submit(){
		Json j = new Json();
		try {
			System.out.println("fist===="+prop.getFistProposaler());
			System.out.println("content===="+prop.getContent());
			propService.submit(prop,httpSession);
			j.setSuccess(true);
			j.setMsg("提交成功!");
		} catch (Exception e) {
			j.setMsg("提交失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	//修改
	public void edit(){
		Json j = new Json();
		try {
			propService.edit(prop,httpSession);
			j.setSuccess(true);
			j.setMsg("修改成功!");
		} catch (Exception e) {
			j.setMsg("修改失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	//初审
	public void checkEdit(){
		Json j = new Json();
		try {
			propService.checkEdit(prop);
			j.setSuccess(true);
			j.setMsg("初审操作成功!");
		} catch (Exception e) {
			j.setMsg("初审操作失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	
	//退回修改
	public void backEditSave(){
		Json j = new Json();
		try {
			propService.backEdit(prop);
			j.setSuccess(true);
			j.setMsg("退回修改操作成功!");
		} catch (Exception e) {
			j.setMsg("退回修改操作失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	//审查立案
	public void checkFiling(){
		Json j = new Json();
		try {
			String period=((Seco)httpSession.getAttribute("sessionSeco")).getPeriod();
			propService.check(prop,period);
			j.setSuccess(true);
			j.setMsg("审查立案成功!");
		} catch (Exception e) {
			j.setMsg("审查立案失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	//确认承办单位
	public void confirm(){
		Json j = new Json();
		try {
			propService.confirm(prop);
			j.setSuccess(true);
			j.setMsg("确认承办单位操作成功!");
		} catch (Exception e) {
			j.setMsg("确认承办单位操作失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	
	//撤案
	public void delete(){
		Json j = new Json();
		try {
			int res=propService.delete(prop);
			if(res>0){
				j.setSuccess(true);
				j.setMsg("撤案成功!");
			}else {
				j.setMsg("撤案失败!");
			}
		} catch (Exception e) {
			j.setMsg("撤案失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	//拒绝采纳设置
	public void setAdopt(){
		Json j = new Json();
		try {
			int res=propService.setAdopt(prop);
			if(res>0){
				j.setSuccess(true);
				j.setMsg("操作成功!");
			}else {
				j.setMsg("操作失败!");
			}
		} catch (Exception e) {
			j.setMsg("操作失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	//重点提案设置
	public void setStress(){
		Json j = new Json();
		String period=((Seco)httpSession.getAttribute("sessionSeco")).getPeriod();
		try {
			propService.setStress(prop,period);
			
			j.setSuccess(true);
			j.setMsg("操作成功!");
			
		} catch (Exception e) {
			j.setMsg("操作失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	//优秀提案设置
	public void setFine(){
		Json j = new Json();
		try {
			int res=propService.setFine(prop);
			if(res>0){
				j.setSuccess(true);
				j.setMsg("操作成功!");
			}else {
				j.setMsg("操作失败!");
			}
		} catch (Exception e) {
			j.setMsg("操作失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	//合并提案设置
	public void setMerge(){
		Json j = new Json();
		try {
			propService.setMerge(prop);
			j.setSuccess(true);
			j.setMsg("提案合并成功!");
		} catch (Exception e) {
			j.setMsg("提案合并失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	//取消合并提案
	public void cancelMerge(){
		Json j = new Json();
		try {
			propService.cancelMerge(prop);
			j.setSuccess(true);
			j.setMsg("提案取消合并成功!");
		} catch (Exception e) {
			j.setMsg("提案取消合并失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	//确定立案
	public void setFiling(){
		Json j = new Json();
		try {
			propService.setFiling(prop);
			j.setSuccess(true);
			j.setMsg("提案交办成功!");
		} catch (Exception e) {
			j.setMsg("提案交办失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	//提案查看
	public String propSee(){
		try {
			Proposal tp=propService.findProp(prop);
			ServletActionContext.getRequest().setAttribute("obj", tp);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "propSee";
	}
	//报表生成
	public void report(){
		Json j = new Json();
		try {
			j.setObj(propService.report(prop,httpSession));
			j.setSuccess(true);
			j.setMsg("报表生成成功!");
		} catch (Exception e) {
			j.setMsg("报表生成失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	//市/县委、两办详细报表
	public void assignreport(){
		Json j = new Json();
		try {
			j.setObj(propService.assignreport(prop,httpSession));
			j.setSuccess(true);
			j.setMsg("报表生成成功!");
		} catch (Exception e) {
			j.setMsg("报表生成失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	//市/县委、两办统计报表
	public void assigncountreport(){
		Json j = new Json();
		try {
			j.setObj(propService.assigncountreport(httpSession));
			j.setSuccess(true);
			j.setMsg("报表生成成功!");
		} catch (Exception e) {
			j.setMsg("报表生成失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	
	//报表生成
	public void wyreport(){
		Json j = new Json();
		try {
			j.setObj(propService.reportwy(prop,httpSession));
			j.setSuccess(true);
			j.setMsg("报表生成成功!");
		} catch (Exception e) {
			j.setMsg("报表生成失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	//报表生成
	public void queryreport(){
		Json j = new Json();
		try {
			j.setObj(propService.queryreport(prop,httpSession));
			j.setSuccess(true);
			j.setMsg("报表生成成功!");
		} catch (Exception e) {
			j.setMsg("报表生成失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	//批量交办
	public void assgnSet(){
		Json j = new Json();
		try {
			propService.assgnSub(prop);
			j.setSuccess(true);
			j.setMsg("提案交办成功!");
		} catch (Exception e) {
			j.setMsg("提案交办失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	//提案统计+社情民意统计+社情民意办理统计
	public String doNotNeedAuth_propCount(){
		try {
			Map<String,Integer> countMap=propService.propCount(httpSession);
			//Map<String,Integer> countPollMap=pollService.pollCount(httpSession);
			//Map<String,Integer> countPollHandleMap=pollHandleService.pollHandleCount(httpSession);
			System.out.println("map====="+countMap.size());
			this.request.setAttribute("countMap", countMap);
			//this.request.setAttribute("countPollMap", countPollMap);
			//this.request.setAttribute("countPollHandleMap", countPollHandleMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "portal";
	}
	//提醒信息统计
	public void doNotNeedAuth_remind(){
		Json j = new Json();
		try {
			String userGroup=((SessionInfo)httpSession.getAttribute("sessionInfo")).getUserGroup();
			String userCode=((SessionInfo)httpSession.getAttribute("sessionInfo")).getUserCode();
			if(Constants.DIC_TYPE_YHZB_CBDW.equals(userGroup)){
				Long newNum=propService.compNewRemind(userCode,httpSession);
				if (newNum!=null&&newNum!=0) {
					j.setSuccess(true);
					j.setMsg("您好！您有"+newNum+"件新提案需要办理回复！");
				}else {
					j.setSuccess(false);
				}
				
				
			}else if(Constants.DIC_TYPE_YHZB_WY.equals(userGroup)){
				Long newNum=propService.commEditRemind(userCode,httpSession);
				if (newNum!=null&&newNum!=0) {
					j.setSuccess(true);
					j.setMsg("您好！您有"+newNum+"件提案需要修改后重新提交！");
				}else {
					j.setSuccess(false);
				}
			
			}else if(Constants.DIC_TYPE_YHZB_GLY.equals(userGroup)){
				Long newNum=propService.adminNewRemind(httpSession);
				
				if(newNum!=null&&newNum!=0){
					j.setSuccess(true);
					j.setMsg("有"+newNum+"件新提案需要审查立案处理！");
				}else {
					j.setSuccess(false);
				}
				
			}else if(Constants.DIC_TYPE_YHZB_LDSP.equals(userGroup)){
				Long newNum=propService.govCheckRemind(userCode,httpSession);
				Long backNum=propService.govBackRemind(userCode, httpSession);
				if(newNum!=null&&newNum!=0){
					if (backNum!=null&&backNum!=0) {
						j.setMsg("有"+newNum+"件新提案需要分配承办单位处理；"+backNum+"件申退提案需要调整承办单位处理！");
					}else {
						j.setMsg("有"+newNum+"件新提案需要分配承办单位处理！");
					}
				}else {
					j.setSuccess(false);
				}
				
			}else {
				j.setSuccess(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
		
	}
	//设置提案总体解决程序
	public void setOpt(){
		Json j = new Json();
		try {
			propService.setOpt(prop);
			j.setSuccess(true);
			j.setMsg("设置成功!");
		} catch (Exception e) {
			j.setMsg("设置失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
}
