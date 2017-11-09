package com.tlzn.action.base;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.tlzn.model.poll.Tpoll;
import com.tlzn.pageModel.activity.Activitypeo;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.info.Proposal;
import com.tlzn.pageModel.meeting.MeetingMan;
import com.tlzn.pageModel.speech.Speech;
import com.tlzn.pageModel.sys.Comm;
import com.tlzn.service.activity.ActiServiceI;
import com.tlzn.service.base.CreateWordServiceI;
import com.tlzn.service.committee.CommitteeServiceI;
import com.tlzn.service.info.PropServiceI;
import com.tlzn.service.meeting.MeetingManServiceI;
import com.tlzn.service.poll.PollServiceI;
import com.tlzn.service.speech.SpeechServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.DocConverter;
import com.tlzn.util.base.Util;

@Action(value = "word", results = {@Result(name = "propswf", location = "/general/info/propSwf.jsp"),})
public class CreateWordAction extends BaseAction{
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CreateWordAction.class);
	private String proposalId;
	private String fileName;
	private String pollId;
	private String code; //委员编码（委员履职信息word用）
	private String secondaryCode; //届次ID
	private String year; //年
	private String speakId; //发言编码（已采用发言word用）
	private String stuNo; //学号（委员履职信息word用）
	
	private CreateWordServiceI createWordService;
	private PropServiceI propService;
	private PollServiceI pollService;
	private CommitteeServiceI committeeService; 
	private SpeechServiceI speechService;
	private MeetingManServiceI meetingManService;
	private ActiServiceI actiService;
	//	/**
//	 * 生成Word
//	 */
//	public void creatWord(){
//		Json j = new Json();
//		try {
//			j.setObj(createWordService.createWord(proposalId));
//			j.setSuccess(true);
//			j.setMsg("Word生成成功!");
//		} catch (Exception e) {
//			j.setMsg("Word生成失败!");
//			e.printStackTrace();
//			logger.info(e.getMessage());
//		}
//		writeJson(j);
//	}
	/**
	 * 下载Word
	 */
	public void doNotNeedAuth_download(){
		Json j = new Json();
		try {
			Proposal prop= propService.findObj(proposalId);
			if(Constants.CODE_TYPE_UPDATE_YES.equals(prop.getUpdateFlg())){
				String filePath=createWordService.download(proposalId);
				//File file=new File(filePath);
				System.out.println("文件名：==="+filePath);
				
				DocConverter d=new DocConverter(filePath);
				d.conver();
				//String newFile=FlashPaper.converter(file,".doc",filePath);
				propService.setUpdateFlg(prop);
				j.setObj(filePath);
				j.setSuccess(true);
			}else {
				/*File file=new File(Constants.ROOTPATH + "wordfile/"+"proposal_"+prop.getProposalCode()+".doc");
				File swffile=new File(Constants.ROOTPATH + "wordfile/"+"proposal_"+prop.getProposalCode()+".swf");
				if(file.exists() && swffile.exists()){
					j.setSuccess(true);
				}else{
					if(!file.exists()){
						String filePath=createWordService.download(proposalId);
						//File f=new File(filePath);
						System.out.println("文件名：====="+filePath);
						//String newFile=FlashPaper.converter(f,".doc",filePath);
						DocConverter d=new DocConverter(filePath);
						d.conver();
						j.setObj(filePath);
						j.setSuccess(true);
					}else{
						String filePath=file.getPath();//Constants.ROOTPATH + "wordfile/"+"proposal_"+prop.getProposalCode()+".doc";
						System.out.println("文件名：======="+filePath);
						//String newFile=FlashPaper.converter(file,".doc",filePath);
						DocConverter d=new DocConverter(filePath);
						d.conver();
						j.setObj(filePath);
						j.setSuccess(true);
					}
					
				}*/
				j.setSuccess(true);
			}
		} catch (Exception e) {
			j.setMsg("word文件生成失败");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	/**
	 * 社情民意下载Word
	 */
	public void doNotNeedAuth_pollDownload(){
		Json j = new Json();
		try {
			Tpoll poll= pollService.findTpoll(pollId);
			if(Constants.CODE_TYPE_UPDATE_YES.equals(poll.getUpdateFlg())){
				String filePath=createWordService.pollDownload(poll);
				//File file=new File(filePath);
				System.out.println("文件名：==="+filePath);
				
				DocConverter d=new DocConverter(filePath);
				d.conver();
				//String newFile=FlashPaper.converter(file,".doc",filePath);
				pollService.setUpdateFlg(poll);
				j.setObj(filePath);
				j.setSuccess(true);
			}else {
				/*File file=new File(Constants.ROOTPATH + "wordfile/"+"poll_"+poll.getTsecondary().getYear()+poll.getPollId()+".doc");
				File swffile=new File(Constants.ROOTPATH + "wordfile/"+"poll_"+poll.getTsecondary().getYear()+poll.getPollId()+".swf");
				if(file.exists() && swffile.exists()){
					j.setSuccess(true);
				}else{
					if(!file.exists()){
						String filePath=createWordService.pollDownload(pollId);
						//File f=new File(filePath);
						System.out.println("文件名：====="+filePath);
						//String newFile=FlashPaper.converter(f,".doc",filePath);
						DocConverter d=new DocConverter(filePath);
						d.conver();
						j.setObj(filePath);
						j.setSuccess(true);
					}else{
						String filePath=file.getPath();//Constants.ROOTPATH + "wordfile/"+"proposal_"+prop.getProposalCode()+".doc";
						System.out.println("文件名：======="+filePath);
						//String newFile=FlashPaper.converter(file,".doc",filePath);
						DocConverter d=new DocConverter(filePath);
						d.conver();
						j.setObj(filePath);
						j.setSuccess(true);
					}
					
				}*/
				j.setSuccess(true);
			}
		} catch (Exception e) {
			j.setMsg("word文件生成失败");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	/**
	 * 	委员履职信息Word下载
	 */
	public void doNotNeedAuth_fulfilDownload(){
		Json j = new Json();
		try {
			
			//委员信息
			Comm comm = committeeService.getCommBycode(code);
			
			//提案信息
			Proposal prop = new Proposal();
			prop.setPage(1);
			prop.setRows(100);
			List<Proposal> proposalList= propService.datagridFulfil(prop, code,secondaryCode).getRows();
			
			//社情民意
			Tpoll poll = new Tpoll();
			poll.setPage(1);
			poll.setRows(100);
			List<Tpoll> pollList = pollService.datagridFulfil(poll, code,secondaryCode).getRows();
			
			//会议发言
			Speech speech = new Speech();
			speech.setPage(1);
			speech.setRows(100);
			List<Speech> speechList = speechService.datagridFulfil(speech, code,secondaryCode).getRows();
			
			//参会情况
			MeetingMan meetingMan = new MeetingMan();
			meetingMan.setPage(1);
			meetingMan.setRows(100);
			List<MeetingMan> meetingManList= meetingManService.datagridFulfil(meetingMan, code,secondaryCode).getRows();
			
			//活动情况
			Activitypeo  activitypeo = new Activitypeo();
			activitypeo.setPage(1);
			activitypeo.setRows(100);
			List<Activitypeo> activitypeoList= actiService.datagridFulfil(activitypeo, code,secondaryCode).getRows();
			String filePath=createWordService.fulFilDownload(code,secondaryCode,year,comm,proposalList,pollList,speechList,meetingManList,activitypeoList);
			System.out.println("文件名：==="+filePath);
			DocConverter d=new DocConverter(filePath);
//				d.conver();
			j.setObj(filePath);
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg("word文件生成失败");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	/**
	 * 	学生基本信息Word下载
	 */
	public void doNotNeedAuth_stuBaseInfoDownload(){
		Json j = new Json();
		try {
			String filePath=createWordService.stuBasInfoDownload(stuNo);
			System.out.println("文件名：==="+filePath);
			DocConverter d=new DocConverter(filePath);
//			d.conver();
			j.setObj(filePath);
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg("word文件生成失败");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	/**
	 * 	委员履职信息下载预览
	 */
	public void doNotNeedAuth_fulfilDownloadView(){
		Json j = new Json();
		try {
			
			//委员信息
			Comm comm = committeeService.getCommBycode(code);
			
			//提案信息
			Proposal prop = new Proposal();
			prop.setPage(1);
			prop.setRows(100);
			List<Proposal> proposalList= propService.datagridFulfil(prop, code,secondaryCode).getRows();
			
			//社情民意
			Tpoll poll = new Tpoll();
			poll.setPage(1);
			poll.setRows(100);
			List<Tpoll> pollList = pollService.datagridFulfil(poll, code,secondaryCode).getRows();
			
			//会议发言
			Speech speech = new Speech();
			speech.setPage(1);
			speech.setRows(100);
			List<Speech> speechList = speechService.datagridFulfil(speech, code,secondaryCode).getRows();
			
			//参会情况
			MeetingMan meetingMan = new MeetingMan();
			meetingMan.setPage(1);
			meetingMan.setRows(100);
			List<MeetingMan> meetingManList= meetingManService.datagridFulfil(meetingMan, code,secondaryCode).getRows();
			
			//活动情况
			Activitypeo  activitypeo = new Activitypeo();
			activitypeo.setPage(1);
			activitypeo.setRows(100);
			List<Activitypeo> activitypeoList= actiService.datagridFulfil(activitypeo, code,secondaryCode).getRows();

			String filePath=createWordService.fulFilDownload(code,secondaryCode,year,comm,proposalList,pollList,speechList,meetingManList,activitypeoList);
			System.out.println("文件名：==="+filePath);
			
			DocConverter d=new DocConverter(filePath);
			d.conver();
			j.setObj(filePath);
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg("word文件生成失败");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	/**
	 * 	已采用发言=》导出
	 */
	public void doNotNeedAuth_speechDownload(){
		Json j = new Json();
		try {
			
			//发言信息
			Speech speech =speechService.get(speakId);
			String atts= speech.getAtts();
			List<String> attList = null;
			if(!Util.getInstance().isEmpty(atts)){
				attList = new ArrayList<String>();
				String attArry[] = atts.split(",");
				for (String att : attArry) {
					attList.add(att);
				}
			}
			
			String filePath=createWordService.speechDownload(speakId,secondaryCode,year,speech,attList);
			System.out.println("文件名：==="+filePath);
			DocConverter d=new DocConverter(filePath);
//				d.conver();
			j.setObj(filePath);
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg("word文件生成失败");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	//判断文件是否存在
	public void doNotNeedAuth_isfile(){
		Json j = new Json();
		try {
			System.out.println("filename===="+fileName);
			File file=new File(Constants.ROOTPATH + "wordfile/"+fileName);
			
			if(file.exists()){
				j.setSuccess(true);
			}else{
				j.setSuccess(false);
				j.setMsg("文件正在生成，请稍后再试...");
			}
		} catch (Exception e) {
			j.setMsg("系统错误，请联系管理员！");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	//判断swf文件是否存在
	public String doNotNeedAuth_isSWFFile(){
		Json j = new Json();
		try {
			File file=new File(Constants.ROOTPATH + "wordfile/"+fileName);
			
			if(file.exists()){
				ServletActionContext.getRequest().setAttribute("swfFile",fileName);
				return "propswf";
			}else{
				j.setSuccess(false);
				j.setMsg("文件正在生成，请稍后再试...");
			}
		} catch (Exception e) {
			j.setMsg("系统错误，请联系管理员！");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
		return null;
	}
	public String getProposalId() {
		return proposalId;
	}

	public void setProposalId(String proposalId) {
		this.proposalId = proposalId;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPollId() {
		return pollId;
	}

	public void setPollId(String pollId) {
		this.pollId = pollId;
	}
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSecondaryCode() {
		return secondaryCode;
	}

	public void setSecondaryCode(String secondaryCode) {
		this.secondaryCode = secondaryCode;
	}
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	public String getSpeakId() {
		return speakId;
	}

	public void setSpeakId(String speakId) {
		this.speakId = speakId;
	}
	public String getStuNo() {
		return stuNo;
	}

	public void setStuNo(String stuNo) {
		this.stuNo = stuNo;
	}
	public CreateWordServiceI getCreateWordService() {
		return createWordService;
	}
	@Autowired
	public void setCreateWordService(CreateWordServiceI createWordService) {
		this.createWordService = createWordService;
	}
	public PropServiceI getPropService() {
		return propService;
	}
	@Autowired
	public void setPropService(PropServiceI propService) {
		this.propService = propService;
	}

	public PollServiceI getPollService() {
		return pollService;
	}
	@Autowired
	public void setPollService(PollServiceI pollService) {
		this.pollService = pollService;
	}
	public CommitteeServiceI getCommitteeService() {
		return committeeService;
	}
	@Autowired
	public void setCommitteeService(CommitteeServiceI committeeService) {
		this.committeeService = committeeService;
	}
	public SpeechServiceI getSpeechService() {
		return speechService;
	}
	@Autowired
	public void setSpeechService(SpeechServiceI speechService) {
		this.speechService = speechService;
	}
	public MeetingManServiceI getMeetingManService() {
		return meetingManService;
	}
	@Autowired
	public void setMeetingManService(MeetingManServiceI meetingManService) {
		this.meetingManService = meetingManService;
	}
	public ActiServiceI getActiService() {
		return actiService;
	}
	@Autowired
	public void setActiService(ActiServiceI actiService) {
		this.actiService = actiService;
	}
}
