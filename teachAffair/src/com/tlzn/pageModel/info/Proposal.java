package com.tlzn.pageModel.info;

import java.util.Date;


public class Proposal implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields

	private String proposalId;
	private String secondaryId;
	private String secondayName;
	private String proposalCode;
	private String title;
	private String content;
	private String webFlg;
	private String webFlgName;
	private String secondFlg;
	private String secondFlgName;
	private String stutas;
	private String stutasName;
	private String businessCode;
	private String businessName;
	private String fileMethod;
	private String fileMethodName;
	private String undertakeUnit;
	private String meetingFlg;
	private String meetingFlgName;
	private String propertiesType;
	private String propTypeName;
	private String typeId;
	private String handleType;
	private String handleTypeName;
	private String stressFlg;
	private String stressFlgName;
	private String leader;
	private String excellentFlg;
	private String excellentFlgName;
	private String secretFlg;
	private String secretFlgName;
	private Date createTime;
	private Date demandEnddate;
	private Date checkTime;
	private String handleHow;
	private String handleHowName;
	private String fistProposaler;
	private String proposalerNum;
	private String proposalType;
	private String proposalTypeName;
	private String submitFlg;
	private String submitFlgName;
	private Date submitTime;
	private String revokeFlg;
	private String revokeFlgName;
	private String adoptFlg="1";
	private String adoptFlgName;
	private String adoptExplain;
	private String analySisUnit;
	private String analySisUnitName;
	private String sponsorIds;
	private String hostFlgs;
	private String companyIds;
	private String mainFlgs;
	private String mergeFlag;
	private String mergeFlagName;
	private String mergeIds;
	private String adviceInfo;
	private String adviceFlg;
	private String noFillingReason;
	private String adviceUnit;
	private String adviceUnitName;
	private String satisfyFlg;
	private String satisfyFlgName;
	private String replyPass;
	private String replyPassName;
	private String updateFlg;
	private String instructions;
	private String filingFlg;
	private String filingFlgName;
	
	private String companyId;
	private String companyName;
	
	
	private String comps;
	private String solveHow;
	private String solveHowName;
	private String carryoutFlg;
	private String carryoutFlgName;
	private String committeemanOpinion;
	private String committeemanOpinionName;
	
	private String ids;			// ids字符串
	private int page; 			// 当前页
	private int rows; 			// 每页显示记录数
	private String sort; 		// 排序字段名
	private String order; 		// 按什么排序(asc,desc)
	public String getComps() {
		return comps;
	}
	public void setComps(String comps) {
		this.comps = comps;
	}
	public String getProposalId() {
		return proposalId;
	}
	public void setProposalId(String proposalId) {
		this.proposalId = proposalId;
	}
	public String getSecondaryId() {
		return secondaryId;
	}
	public void setSecondaryId(String secondaryId) {
		this.secondaryId = secondaryId;
	}
	public String getSecondayName() {
		return secondayName;
	}
	public void setSecondayName(String secondayName) {
		this.secondayName = secondayName;
	}
	public String getProposalCode() {
		return proposalCode;
	}
	public void setProposalCode(String proposalCode) {
		this.proposalCode = proposalCode;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWebFlg() {
		return webFlg;
	}
	public void setWebFlg(String webFlg) {
		this.webFlg = webFlg;
	}
	public String getWebFlgName() {
		return webFlgName;
	}
	public void setWebFlgName(String webFlgName) {
		this.webFlgName = webFlgName;
	}
	public String getSecondFlg() {
		return secondFlg;
	}
	public void setSecondFlg(String secondFlg) {
		this.secondFlg = secondFlg;
	}
	public String getSecondFlgName() {
		return secondFlgName;
	}
	public void setSecondFlgName(String secondFlgName) {
		this.secondFlgName = secondFlgName;
	}
	public String getStutas() {
		return stutas;
	}
	public void setStutas(String stutas) {
		this.stutas = stutas;
	}
	public String getStutasName() {
		return stutasName;
	}
	public void setStutasName(String stutasName) {
		this.stutasName = stutasName;
	}
	public String getBusinessCode() {
		return businessCode;
	}
	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getFileMethod() {
		return fileMethod;
	}
	public void setFileMethod(String fileMethod) {
		this.fileMethod = fileMethod;
	}
	public String getFileMethodName() {
		return fileMethodName;
	}
	public void setFileMethodName(String fileMethodName) {
		this.fileMethodName = fileMethodName;
	}
	public String getUndertakeUnit() {
		return undertakeUnit;
	}
	public void setUndertakeUnit(String undertakeUnit) {
		this.undertakeUnit = undertakeUnit;
	}
	public String getMeetingFlg() {
		return meetingFlg;
	}
	public void setMeetingFlg(String meetingFlg) {
		this.meetingFlg = meetingFlg;
	}
	public String getMeetingFlgName() {
		return meetingFlgName;
	}
	public void setMeetingFlgName(String meetingFlgName) {
		this.meetingFlgName = meetingFlgName;
	}
	public String getPropertiesType() {
		return propertiesType;
	}
	public void setPropertiesType(String propertiesType) {
		this.propertiesType = propertiesType;
	}
	public String getPropTypeName() {
		return propTypeName;
	}
	public void setPropTypeName(String propTypeName) {
		this.propTypeName = propTypeName;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getHandleType() {
		return handleType;
	}
	public void setHandleType(String handleType) {
		this.handleType = handleType;
	}
	public String getHandleTypeName() {
		return handleTypeName;
	}
	public void setHandleTypeName(String handleTypeName) {
		this.handleTypeName = handleTypeName;
	}
	public String getStressFlg() {
		return stressFlg;
	}
	public void setStressFlg(String stressFlg) {
		this.stressFlg = stressFlg;
	}
	public String getStressFlgName() {
		return stressFlgName;
	}
	public void setStressFlgName(String stressFlgName) {
		this.stressFlgName = stressFlgName;
	}
	public String getLeader() {
		return leader;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	public String getExcellentFlg() {
		return excellentFlg;
	}
	public void setExcellentFlg(String excellentFlg) {
		this.excellentFlg = excellentFlg;
	}
	public String getExcellentFlgName() {
		return excellentFlgName;
	}
	public void setExcellentFlgName(String excellentFlgName) {
		this.excellentFlgName = excellentFlgName;
	}
	public String getSecretFlg() {
		return secretFlg;
	}
	public void setSecretFlg(String secretFlg) {
		this.secretFlg = secretFlg;
	}
	public String getSecretFlgName() {
		return secretFlgName;
	}
	public void setSecretFlgName(String secretFlgName) {
		this.secretFlgName = secretFlgName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getDemandEnddate() {
		return demandEnddate;
	}
	public void setDemandEnddate(Date demandEnddate) {
		this.demandEnddate = demandEnddate;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public String getHandleHow() {
		return handleHow;
	}
	public void setHandleHow(String handleHow) {
		this.handleHow = handleHow;
	}
	public String getHandleHowName() {
		return handleHowName;
	}
	public void setHandleHowName(String handleHowName) {
		this.handleHowName = handleHowName;
	}
	public String getFistProposaler() {
		return fistProposaler;
	}
	public void setFistProposaler(String fistProposaler) {
		this.fistProposaler = fistProposaler;
	}
	public String getProposalerNum() {
		return proposalerNum;
	}
	public void setProposalerNum(String proposalerNum) {
		this.proposalerNum = proposalerNum;
	}
	public String getProposalType() {
		return proposalType;
	}
	public void setProposalType(String proposalType) {
		this.proposalType = proposalType;
	}
	public String getProposalTypeName() {
		return proposalTypeName;
	}
	public void setProposalTypeName(String proposalTypeName) {
		this.proposalTypeName = proposalTypeName;
	}
	public String getSubmitFlg() {
		return submitFlg;
	}
	public void setSubmitFlg(String submitFlg) {
		this.submitFlg = submitFlg;
	}
	public String getSubmitFlgName() {
		return submitFlgName;
	}
	public void setSubmitFlgName(String submitFlgName) {
		this.submitFlgName = submitFlgName;
	}
	public Date getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	public String getRevokeFlg() {
		return revokeFlg;
	}
	public void setRevokeFlg(String revokeFlg) {
		this.revokeFlg = revokeFlg;
	}
	public String getRevokeFlgName() {
		return revokeFlgName;
	}
	public void setRevokeFlgName(String revokeFlgName) {
		this.revokeFlgName = revokeFlgName;
	}
	public String getAdoptFlg() {
		return adoptFlg;
	}
	public void setAdoptFlg(String adoptFlg) {
		this.adoptFlg = adoptFlg;
	}
	public String getAdoptFlgName() {
		return adoptFlgName;
	}
	public void setAdoptFlgName(String adoptFlgName) {
		this.adoptFlgName = adoptFlgName;
	}
	public String getAdoptExplain() {
		return adoptExplain;
	}
	public void setAdoptExplain(String adoptExplain) {
		this.adoptExplain = adoptExplain;
	}
	public String getAnalySisUnit() {
		return analySisUnit;
	}
	public void setAnalySisUnit(String analySisUnit) {
		this.analySisUnit = analySisUnit;
	}
	public String getSponsorIds() {
		return sponsorIds;
	}
	public void setSponsorIds(String sponsorIds) {
		this.sponsorIds = sponsorIds;
	}
	public String getHostFlgs() {
		return hostFlgs;
	}
	public void setHostFlgs(String hostFlgs) {
		this.hostFlgs = hostFlgs;
	}
	public String getCompanyIds() {
		return companyIds;
	}
	public void setCompanyIds(String companyIds) {
		this.companyIds = companyIds;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getMainFlgs() {
		return mainFlgs;
	}
	public void setMainFlgs(String mainFlgs) {
		this.mainFlgs = mainFlgs;
	}
	public String getMergeFlag() {
		return mergeFlag;
	}
	public void setMergeFlag(String mergeFlag) {
		this.mergeFlag = mergeFlag;
	}
	public String getMergeFlagName() {
		return mergeFlagName;
	}
	public void setMergeFlagName(String mergeFlagName) {
		this.mergeFlagName = mergeFlagName;
	}
	public String getSolveHow() {
		return solveHow;
	}
	public void setSolveHow(String solveHow) {
		this.solveHow = solveHow;
	}
	public String getCarryoutFlg() {
		return carryoutFlg;
	}
	public void setCarryoutFlg(String carryoutFlg) {
		this.carryoutFlg = carryoutFlg;
	}
	public String getCommitteemanOpinion() {
		return committeemanOpinion;
	}
	public void setCommitteemanOpinion(String committeemanOpinion) {
		this.committeemanOpinion = committeemanOpinion;
	}
	public String getSolveHowName() {
		return solveHowName;
	}
	public void setSolveHowName(String solveHowName) {
		this.solveHowName = solveHowName;
	}
	public String getCarryoutFlgName() {
		return carryoutFlgName;
	}
	public void setCarryoutFlgName(String carryoutFlgName) {
		this.carryoutFlgName = carryoutFlgName;
	}
	public String getCommitteemanOpinionName() {
		return committeemanOpinionName;
	}
	public void setCommitteemanOpinionName(String committeemanOpinionName) {
		this.committeemanOpinionName = committeemanOpinionName;
	}
	public String getAdviceInfo() {
		return adviceInfo;
	}
	public void setAdviceInfo(String adviceInfo) {
		this.adviceInfo = adviceInfo;
	}
	public String getAdviceFlg() {
		return adviceFlg;
	}
	public void setAdviceFlg(String adviceFlg) {
		this.adviceFlg = adviceFlg;
	}
	public String getMergeIds() {
		return mergeIds;
	}
	public void setMergeIds(String mergeIds) {
		this.mergeIds = mergeIds;
	}
	public String getNoFillingReason() {
		return noFillingReason;
	}
	public void setNoFillingReason(String noFillingReason) {
		this.noFillingReason = noFillingReason;
	}
	public String getAdviceUnit() {
		return adviceUnit;
	}
	public void setAdviceUnit(String adviceUnit) {
		this.adviceUnit = adviceUnit;
	}
	public String getAnalySisUnitName() {
		return analySisUnitName;
	}
	public void setAnalySisUnitName(String analySisUnitName) {
		this.analySisUnitName = analySisUnitName;
	}
	public String getSatisfyFlg() {
		return satisfyFlg;
	}
	public void setSatisfyFlg(String satisfyFlg) {
		this.satisfyFlg = satisfyFlg;
	}
	public String getSatisfyFlgName() {
		return satisfyFlgName;
	}
	public void setSatisfyFlgName(String satisfyFlgName) {
		this.satisfyFlgName = satisfyFlgName;
	}
	public String getReplyPass() {
		return replyPass;
	}
	public void setReplyPass(String replyPass) {
		this.replyPass = replyPass;
	}
	public String getReplyPassName() {
		return replyPassName;
	}
	public void setReplyPassName(String replyPassName) {
		this.replyPassName = replyPassName;
	}
	public String getUpdateFlg() {
		return updateFlg;
	}
	public void setUpdateFlg(String updateFlg) {
		this.updateFlg = updateFlg;
	}
	public String getInstructions() {
		return instructions;
	}
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	public String getAdviceUnitName() {
		return adviceUnitName;
	}
	public void setAdviceUnitName(String adviceUnitName) {
		this.adviceUnitName = adviceUnitName;
	}
	public String getFilingFlg() {
		return filingFlg;
	}
	public void setFilingFlg(String filingFlg) {
		this.filingFlg = filingFlg;
	}
	public String getFilingFlgName() {
		return filingFlgName;
	}
	public void setFilingFlgName(String filingFlgName) {
		this.filingFlgName = filingFlgName;
	}
	
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
}
