package com.tlzn.action.speech;


import java.util.ArrayList;
import java.util.List;
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
import com.tlzn.pageModel.speech.Speech;
import com.tlzn.pageModel.sys.Seco;
import com.tlzn.service.speech.SpeechServiceI;
import com.tlzn.util.base.Util;
import com.tlzn.util.base.Constants;
@Action(value = "speech", results = {
		//提交发言
		@Result(name = "speechSub", location = "/general/speech/speechSub.jsp"),
		//新增发言
		@Result(name = "speechAdd", location = "/general/speech/speechAdd.jsp"),
		//我的发言
		@Result(name = "speechOwn", location = "/general/speech/speechOwn.jsp"),
		//查看消息
		@Result(name = "speechSee", location = "/general/speech/speechSee.jsp"),
		//发言管理
		@Result(name = "speech", location = "/general/speech/speech.jsp"),
		//发言修改
		@Result(name = "speechEdit", location = "/general/speech/speechEdit.jsp"),
		//已采用发言
		@Result(name = "speechAdopted", location = "/general/speech/speechAdopted.jsp"),
		//发言查询
		@Result(name = "speechQuery", location = "/general/speech/speechQuery.jsp"),
		//发言审查
		@Result(name = "speechAudit", location = "/general/speech/speechAudit.jsp"),
		//定稿详细
		@Result(name = "speechConfirm", location = "/general/speech/speechConfirm.jsp"),
		//定稿的发言列表
		@Result(name = "speechConfirmed", location = "/general/speech/speechConfirmed.jsp"),
		//转弃详细
		@Result(name = "speechDiscard", location = "/general/speech/speechDiscard.jsp"),
		//转弃的发言列表
		@Result(name = "speechDiscarded", location = "/general/speech/speechDiscarded.jsp"),
		//会议发言首页
		@Result(name = "portal", location = "/public/layout/portal/portalSpeech.jsp"),
		//会议选择页面
		@Result(name = "meetingSelect", location = "/general/speech/meeting_select.jsp")
})
public class SpeechAction extends BaseAction implements ModelDriven<Speech>{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(SpeechAction.class);
	
	private SpeechServiceI speechService;
	
	private String statusType;
	
	private Speech speech=new Speech();
	public Speech getModel() {
		return speech;
	}
	
	/**
	 * 提交发言页面
	 */
	public String speechSub(){
		
		return "speechSub";
	}
	/**
	 * 新增发言页面
	 */
	public String speechAdd(){
		
		return "speechAdd";
	}
	/**
	 * 我的发言页面
	 */
	public String speechOwn(){
		if(statusType!=null){
			ServletActionContext.getRequest().setAttribute("statusType",statusType);
		}
		return "speechOwn";
	}
	/**
	 * 发言管理页面
	 */
	public String speech(){
		if(statusType!=null){
			ServletActionContext.getRequest().setAttribute("statusType",statusType);
		}
		return "speech";
	}

	/**
	 * 已采用发言页面
	 */
	public String speechAdopted(){
		return "speechAdopted";
	}
	/**
	 * 定稿的发言列表页面
	 */
	public String speechConfirmed(){
		return "speechConfirmed";
	}
	/**
	 * 发言查询页面
	 */
	public String speechQuery(){
		return "speechQuery";
	}
	/**
	 * 发言审查页面
	 */
	public String speechAudit(){
		try {
			String speakId = speech.getSpeakId();
			if (!Util.getInstance().isEmpty(speakId)) {
				Speech speech = speechService.get(speakId);
				request.setAttribute("speech", speech);
				String atts = speech.getAtts();
				List<String> attList = null;
				if (!Util.getInstance().isEmpty(atts)) {
					attList = new ArrayList<String>();
					String attArry[] = atts.split(",");
					for (String att : attArry) {
						attList.add(att);
					}
					request.setAttribute("attList", attList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "speechAudit";
	}
	/**
	 * 发言定稿页面
	 */
	public String speechConfirm(){
		try {
			String speakId = speech.getSpeakId();
			if (!Util.getInstance().isEmpty(speakId)) {
				Speech speech = speechService.get(speakId);
				request.setAttribute("speech", speech);
				String atts = speech.getAtts();
				List<String> attList = null;
				if (!Util.getInstance().isEmpty(atts)) {
					attList = new ArrayList<String>();
					String attArry[] = atts.split(",");
					for (String att : attArry) {
						attList.add(att);
					}
					request.setAttribute("attList", attList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "speechConfirm";
	}
	/**
	 * 定稿处理页面
	 */
	public String confirmHandle(){
		return "confirmHandle";
	}
	
	/**
	 * 转弃的发言列表页面
	 */
	public String speechDiscarded(){
		return "speechDiscarded";
	}
	
	/**
	 * 转弃发言详细页面
	 */
	public String speechDiscard(){
		try {
			String speakId = speech.getSpeakId();
			if (!Util.getInstance().isEmpty(speakId)) {
				Speech speech = speechService.get(speakId);
				request.setAttribute("speech", speech);
				String atts = speech.getAtts();
				List<String> attList = null;
				if (!Util.getInstance().isEmpty(atts)) {
					attList = new ArrayList<String>();
					String attArry[] = atts.split(",");
					for (String att : attArry) {
						attList.add(att);
					}
					request.setAttribute("attList", attList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "speechDiscard";
	}
	/**
	 * 定稿处理页面
	 */
	public String discardHandle(){
		return "discardHandle";
	}
	/**
	 * 
	 * 查询发言列表
	 */
	public void datagrid() {
		try {
			String secondaryId=((Seco) httpSession.getAttribute("sessionSeco")).getSecondaryId();
			speech.setSecondaryId(secondaryId);
			DataGrid dataGrid = speechService.datagrid(speech);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询我的发言列表
	 */
	public void datagridown() {
		try {
			String userCode = ((SessionInfo) httpSession.getAttribute("sessionInfo")).getUserCode();
			speech.setCode(userCode);
			String secondaryId=((Seco) httpSession.getAttribute("sessionSeco")).getSecondaryId();
			speech.setSecondaryId(secondaryId);
			DataGrid dataGrid = speechService.datagridOwn(speech);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 已定稿发言查询
	 */
	public void datagridDg() {
		try {
			DataGrid dataGrid = speechService.datagridDg(speech);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 已转弃发言查询
	 */
	public void datagridZq() {
		try {
			DataGrid dataGrid = speechService.datagridZq(speech);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 已采用发言查询
	 */
	public void datagridAdopted() {
		try {
			DataGrid dataGrid = speechService.datagridAdopted(speech);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 委员履职详情，会议发言列表查询
	 */
	public void datagridFulfil() {
		String secondaryCode =request.getParameter("secondaryCode");
		String userCode = request.getParameter("userCode");
		try {
			DataGrid dataGrid = speechService.datagridFulfil(speech, userCode,secondaryCode);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	
	/**
	 * 会议选择页面
	 */
	public String meetingSelect(){
		return "meetingSelect";
	}
	/**
	 * 会议发言详情查看
	 */
	public String speechSee() {
		try {
			String speakId=speech.getSpeakId();
			if(!Util.getInstance().isEmpty(speakId)){
				Speech speech =speechService.get(speakId);
				request.setAttribute("speech",speech);
				String atts= speech.getAtts();
				List<String> attList = null;
				if(!Util.getInstance().isEmpty(atts)){
					attList = new ArrayList<String>();
					String attArry[] = atts.split(",");
					for (String att : attArry) {
						attList.add(att);
					}
					request.setAttribute("attList",attList);
				}
			}
			} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "speechSee";
	}
	/**
	 * 发言修改页面(跳转)
	 */
	public String speechEdit(){
		try {
			String speakId=speech.getSpeakId();
			if(!Util.getInstance().isEmpty(speakId)){
				Speech speech =speechService.get(speakId);
				request.setAttribute("speech",speech);
				String atts= speech.getAtts();
				List<String> attList = null;
				if(!Util.getInstance().isEmpty(atts)){
					attList = new ArrayList<String>();
					String attArry[] = atts.split(",");
					for (String att : attArry) {
						attList.add(att);
					}
					request.setAttribute("attList",attList);
				}
			}
			} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "speechEdit";
	}
	
	/**
	 * 
	 * 发言查询模块列表
	 */
	public void datagridQuery() {
		try {
//			String userCode = ((SessionInfo) httpSession.getAttribute("sessionInfo")).getUserCode();
//			speech.setCode(userCode);
			speech.setStatus(Constants.CODE_TYPE_SPEECH_STATUS_DG);
			DataGrid dataGrid = speechService.datagrid(speech);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 保存发言
	 */
	public void save() {
		Json j = new Json();
		try {
			if(Util.getInstance().isEmpty(speech.getSpeakId())){
				j.setObj(speechService.save(speech,httpSession));
			} else {
				j.setObj(speechService.saveForUpate(speech,httpSession));
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
	/**
	 * 
	 * 更新或新增发言(新发布提交)
	 */
	public void upDateOrAdd() {
		Json j = new Json();
		try {
			j.setObj(speechService.upDateOrAdd(speech,httpSession));
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
	 * 
	 * 修改发言
	 */
	public void edit() {
		Json j = new Json();
		try {
			j.setObj(speechService.edit(speech,httpSession));
			j.setSuccess(true);
			j.setMsg("修改成功!");
		} catch (Exception e) {
			j.setMsg("修改失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	/**
	 * 
	 * 审查发言
	 */
	public void audit() {
		Json j = new Json();
		try {
			j.setObj(speechService.audit(speech,httpSession));
			j.setSuccess(true);
			j.setMsg("审查成功!");
		} catch (Exception e) {
			j.setMsg("审查失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}	
	/**
	 * 
	 * 发言定稿
	 */
	public void confirm() {
		Json j = new Json();
		try {
			j.setObj(speechService.confirm(speech,httpSession));
			j.setSuccess(true);
			j.setMsg("定稿成功!");
		} catch (Exception e) {
			j.setMsg("定稿失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	/**
	 * 
	 * 发言转弃
	 */
	public void discard() {
		Json j = new Json();
		try {
			j.setObj(speechService.discard(speech,httpSession));
			j.setSuccess(true);
			j.setMsg("转弃成功!");
		} catch (Exception e) {
			j.setMsg("转弃失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	//发言管理“提交”处理
	public void submit() {
		Json j = new Json();
		try {
			speechService.submit(speech);
			j.setSuccess(true);
			j.setMsg("提交成功!");
		} catch (Exception e) {
			j.setMsg("提交失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	//发言管理“提交”处理
	public void cancelSubmit() {
		Json j = new Json();
		try {
			speechService.cancelSubmit(speech);
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
	 * 删除发言
	 */
	public void delete() {
		Json j = new Json();
		try {
			speechService.del(speech);
			j.setSuccess(true);
			j.setMsg("删除成功!");
		} catch (Exception e) {
			j.setMsg("删除失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	//会议发言首页统计
	public String doNotNeedAuth_speechCount(){
		try {
			Map<String,Integer> countSpeechMap=speechService.speechCount(httpSession);
			this.request.setAttribute("countSpeechMap", countSpeechMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "portal";
	}
	
	public String getStatusType() {
		return statusType;
	}

	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}
	
	public SpeechServiceI getSpeechService() {
		return speechService;
	}
	@Autowired
	public void setSpeechService(SpeechServiceI speechService) {
		this.speechService = speechService;
	}
}
