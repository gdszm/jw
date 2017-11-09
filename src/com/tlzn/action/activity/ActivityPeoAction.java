package com.tlzn.action.activity;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.activity.Activitypeo;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.base.SessionInfo;
import com.tlzn.service.activity.ActiServiceI;
@Action(value = "activitypeo", results = {
		@Result(name = "leave", location = "/general/activity/leave.jsp") ,
		@Result(name = "activityOwn", location = "/general/activity/activityOwn.jsp") ,
		@Result(name = "actAstatus", location = "/general/activity/actAstatus.jsp") ,
		@Result(name = "actSituation", location = "/general/activity/actSituation.jsp") ,
		@Result(name = "actpeoQuery", location = "/general/activity/actpeoQuery.jsp") ,
})
public class ActivityPeoAction extends BaseAction implements ModelDriven<Activitypeo>{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ActivityAction.class);
	private ActiServiceI actiService;
	
	public ActiServiceI getActiService() {
		return actiService;
	}
	@Autowired
	public void setActiService(ActiServiceI actiService) {
		this.actiService = actiService;
	}
	private Activitypeo activitypeo = new Activitypeo();

	public Activitypeo getActivitypeo() {
		return activitypeo;
	}

	public void setActivitypeo(Activitypeo activitypeo) {
		this.activitypeo = activitypeo;
	}
	
	public Activitypeo getModel() {
		return activitypeo;
	}
	/**
	 * 活动状态修改页面
	 */
	public String actAstatus() {
		return "actAstatus";
	}
	/**
	 * 我的活动查询页面
	 */
	public String activityOwn() {
		String status = request.getParameter("status");
		String beginTime=request.getParameter("beginTime");
		request.setAttribute("status", status);
		request.setAttribute("beginTime", beginTime);
		return "activityOwn";
	}
	/**
	 * 活动统计页面
	 */
	public String actSituation() {
		return "actSituation";
	}
	/**
	 * 活动情况查询页面
	 */
	public String actpeoQuery() {
		return "actpeoQuery";
	}
	/**
	 * 请假详情页面
	 */
	public String leave() {
		try {
			Activitypeo p = actiService.getLeave(activitypeo.getAuid());
			ServletActionContext.getRequest().setAttribute("obj", p);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "leave";
	}
	/**
	 * 活动人员信息查询
	 */
	public void peoQuery() {
		String secondaryId=request.getParameter("secondaryId");
		if(secondaryId!=null){
			activitypeo.setSecondaryId(secondaryId);
		}
		try {
			super.writeDataGridJson(actiService.datagridpeo(activitypeo,httpSession));
		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
	}
	/**
	 * 活动人员信息查询
	 */
	public void peoQueryDatagrid() {
		String secondaryId=request.getParameter("secondaryId");
		if(secondaryId!=null){
			activitypeo.setSecondaryId(secondaryId);
		}
		try {
			super.writeDataGridJson(actiService.peoQueryDatagrid(activitypeo,httpSession));
		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * 委员履职详情，活动情况列表查询
	 */
	public void datagridFulfil() {
		String secondaryCode =request.getParameter("secondaryCode");
		String userCode = request.getParameter("userCode");
		try {
			DataGrid dataGrid = actiService.datagridFulfil(activitypeo, userCode,secondaryCode);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 出席情况修改
	 */
	public void astatus(){
		Json j = new Json();
		try {
			actiService.astatus(activitypeo);
			j.setSuccess(true);
			j.setMsg("操作成功!");
		} catch (Exception e) {
			j.setMsg("操作失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	/**
	 * 我的活动信息查询
	 */
	public void activityMy() {
		String code=((SessionInfo) httpSession.getAttribute("sessionInfo")).getUserCode();
		String name=((SessionInfo) httpSession.getAttribute("sessionInfo")).getRealName();
		activitypeo.setCode(code);
		activitypeo.setName(name);
		System.out.println("code=="+code+"===name==="+name);
		try {
			super.writeDataGridJson(actiService.datagridpeo(activitypeo,httpSession));
		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 请假申请提交
	 */
	public void leavesq() {
		Json j = new Json();
		try {
			actiService.leave(activitypeo);
			j.setSuccess(true);
			j.setMsg("操作成功!");
		} catch (Exception e) {
			j.setMsg("操作失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	/**
	 * 活动人员信息添加
	 */
	public void add(){
		Json j = new Json();
		try {
			String aid=request.getParameter("aid");
			System.out.println("aid=="+aid+"===code===="+activitypeo.getCode());
			actiService.peoadd(activitypeo.getCode(),aid);
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
	 * 活动信息删除
	 */
	public void delete() {
		Json j = new Json();
		try {
			System.out.println("ids==="+activitypeo.getIds());
			actiService.peodel(activitypeo.getIds());
			j.setSuccess(true);
			j.setMsg("删除成功!");
		} catch (Exception e) {
			j.setMsg("删除失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	
	/**
	 * 活动情况统计表头数据获取
	 */
	public void getColumns() {
		try {
			
			super.writeListJson(actiService.getColumns());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 活动统计查询
	 */
	public void situationCount(){
		try {
					
			super.writeListJson(actiService.situationCount(activitypeo));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 *请假驳回提交
	 */
	public void change(){
		Json j = new Json();
		try {
			actiService.change(activitypeo);
			j.setSuccess(true);
			j.setMsg("操作成功!");
		} catch (Exception e) {
			j.setMsg("操作失败!");
			e.printStackTrace();
		}
		writeJson(j);
	}
}
