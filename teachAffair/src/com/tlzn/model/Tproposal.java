package com.tlzn.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.tlzn.model.base.BaseObject;
import com.tlzn.model.sys.Tsecondary;

/**
 * Tproposal entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TPROPOSAL", schema = "", uniqueConstraints = @UniqueConstraint(columnNames = {
		"PROPOSAL_ID", "PROPOSAL_CODE", "SECONDARY_ID" }))
public class Tproposal extends BaseObject  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields
	private String proposalId;
	private Tsecondary tsecondary;
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
	private String revokeFlg="0";
	private String revokeFlgName;
	private String adoptFlg="1";
	private String adoptFlgName;
	private String adoptExplain;
	private String analySisUnit;
	private String mergeFlag;
	private String mergeFlagName;
	private String mergeIds;
	private String adviceFlg;
	private String adviceInfo;
	private String noFillingReason;
	private String adviceUnit;
	private String satisfyFlg;
	private String replyPass;
	private Set<ThandleReply> thandleReplies = new HashSet<ThandleReply>(0);
	private Set<Tproposaler> tproposalers = new HashSet<Tproposaler>(0);
	private String sponsorIds;
	private String hostFlgs;
	private String companyIds;
	private String updateFlg;
	private String instructions;
	private String filingFlg;
	
	private String solveHow;
	private String carryoutFlg;
	private String committeemanOpinion;

	
	// Constructors

	/** default constructor */
	public Tproposal() {
	}

	/** default constructor */
	public Tproposal(String id) {
		this.proposalId=id;
	}

	/** minimal constructor */
	public Tproposal(Tsecondary tsecondary, String proposalCode) {
		this.tsecondary = tsecondary;
		this.proposalCode = proposalCode;
	}

	/** full constructor */
	public Tproposal(Tsecondary tsecondary, String proposalCode, String title,
			String content, String webFlg, String secondFlg, String stutas,
			String businessCode, String fileMethod, String undertakeUnit,
			String meetingFlg, String propertiesType, String typeId,
			String handleType, String stressFlg, String leader,
			String excellentFlg, String secretFlg, Date createTime,
			Date demandEnddate, String handleHow,String fistProposaler,String proposalerNum,
			Set<ThandleReply> thandleReplies, Set<Tproposaler> tproposalers) {
		this.tsecondary = tsecondary;
		this.proposalCode = proposalCode;
		this.title = title;
		this.content = content;
		this.webFlg = webFlg;
		this.secondFlg = secondFlg;
		this.stutas = stutas;
		this.businessCode = businessCode;
		this.fileMethod = fileMethod;
		this.undertakeUnit = undertakeUnit;
		this.meetingFlg = meetingFlg;
		this.propertiesType = propertiesType;
		this.typeId = typeId;
		this.handleType = handleType;
		this.stressFlg = stressFlg;
		this.leader = leader;
		this.excellentFlg = excellentFlg;
		this.secretFlg = secretFlg;
		this.createTime = createTime;
		this.demandEnddate = demandEnddate;
		this.handleHow = handleHow;
		this.fistProposaler=fistProposaler;
		this.proposalerNum=proposalerNum;
		this.thandleReplies = thandleReplies;
		this.tproposalers = tproposalers;
		
	}

	// Property accessors
	@Id
	@Column(name = "PROPOSAL_ID", unique = true, nullable = false, length = 12)
	public String getProposalId() {
		return this.proposalId;
	}

	public void setProposalId(String proposalId) {
		this.proposalId = proposalId;
	}

	@ManyToOne(targetEntity=Tsecondary.class)//fetch=FetchType.LAZY
	@JoinColumn(name = "SECONDARY_ID", nullable = false)
	public Tsecondary getTsecondary() {
		return this.tsecondary;
	}

	public void setTsecondary(Tsecondary tsecondary) {
		this.tsecondary = tsecondary;
	}

	@Column(name = "PROPOSAL_CODE", nullable = false, length = 12)
	public String getProposalCode() {
		return this.proposalCode;
	}

	public void setProposalCode(String proposalCode) {
		this.proposalCode = proposalCode;
	}

	@Column(name = "TITLE", length = 200)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "CONTENT")
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "WEB_FLG", length = 2)
	public String getWebFlg() {
		return this.webFlg;
	}

	public void setWebFlg(String webFlg) {
		this.webFlg = webFlg;
	}

	@Column(name = "SECOND_FLG", length = 2)
	public String getSecondFlg() {
		return this.secondFlg;
	}

	public void setSecondFlg(String secondFlg) {
		this.secondFlg = secondFlg;
	}

	@Column(name = "STUTAS", length = 2)
	public String getStutas() {
		return this.stutas;
	}

	public void setStutas(String stutas) {
		this.stutas = stutas;
	}

	@Column(name = "BUSINESS_GROUP", length = 4)
	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	@Column(name = "FILE_METHOD", length = 4)
	public String getFileMethod() {
		return this.fileMethod;
	}

	public void setFileMethod(String fileMethod) {
		this.fileMethod = fileMethod;
	}

	@Column(name = "UNDERTAKE_UNIT", length = 200)
	public String getUndertakeUnit() {
		return this.undertakeUnit;
	}

	public void setUndertakeUnit(String undertakeUnit) {
		this.undertakeUnit = undertakeUnit;
	}

	@Column(name = "MEETING_FLG", length = 4)
	public String getMeetingFlg() {
		return this.meetingFlg;
	}

	public void setMeetingFlg(String meetingFlg) {
		this.meetingFlg = meetingFlg;
	}

	@Column(name = "PROPERTIES_TYPE", length = 10)
	public String getPropertiesType() {
		return this.propertiesType;
	}

	public void setPropertiesType(String propertiesType) {
		this.propertiesType = propertiesType;
	}

	@Column(name = "TYPE_ID", length = 5)
	public String getTypeId() {
		return this.typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	@Column(name = "HANDLE_TYPE", length = 4)
	public String getHandleType() {
		return this.handleType;
	}

	public void setHandleType(String handleType) {
		this.handleType = handleType;
	}

	@Column(name = "STRESS_FLG", length = 4)
	public String getStressFlg() {
		return this.stressFlg;
	}

	public void setStressFlg(String stressFlg) {
		this.stressFlg = stressFlg;
	}

	@Column(name = "LEADER", length = 100)
	public String getLeader() {
		return this.leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	@Column(name = "EXCELLENT_FLG", length = 4)
	public String getExcellentFlg() {
		return this.excellentFlg;
	}

	public void setExcellentFlg(String excellentFlg) {
		this.excellentFlg = excellentFlg;
	}

	@Column(name = "SECRET_FLG", length = 4)
	public String getSecretFlg() {
		return this.secretFlg;
	}

	public void setSecretFlg(String secretFlg) {
		this.secretFlg = secretFlg;
	}
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "DEMAND_ENDDATE")
	public Date getDemandEnddate() {
		return this.demandEnddate;
	}
	public void setDemandEnddate(Date demandEnddate) {
		this.demandEnddate = demandEnddate;
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "CHECK_TIME")
	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "SUBMIT_TIME")
	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	@Column(name = "HANDLE_HOW", length = 4)
	public String getHandleHow() {
		return this.handleHow;
	}

	public void setHandleHow(String handleHow) {
		this.handleHow = handleHow;
	}

	@Column(name = "FIST_PROPOSALER", length = 50)
	public String getFistProposaler() {
		return fistProposaler;
	}

	public void setFistProposaler(String fistProposaler) {
		this.fistProposaler = fistProposaler;
	}
	@Column(name = "PROPOSALER_NUM", length = 12)
	public String getProposalerNum() {
		return proposalerNum;
	}

	public void setProposalerNum(String proposalerNum) {
		this.proposalerNum = proposalerNum;
	}
	@Column(name = "ADVICE_FLG", length = 4)
	public String getAdviceFlg() {
		return adviceFlg;
	}

	public void setAdviceFlg(String adviceFlg) {
		this.adviceFlg = adviceFlg;
	}

	@Column(name = "ADVICE_INFO", length = 2500)
	public String getAdviceInfo() {
		return adviceInfo;
	}

	public void setAdviceInfo(String adviceInfo) {
		this.adviceInfo = adviceInfo;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tproposal")
	public Set<ThandleReply> getThandleReplies() {
		return this.thandleReplies;
	}

	public void setThandleReplies(Set<ThandleReply> thandleReplies) {
		this.thandleReplies = thandleReplies;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tproposal")
	public Set<Tproposaler> getTproposalers() {
		return this.tproposalers;
	}

	public void setTproposalers(Set<Tproposaler> tproposalers) {
		this.tproposalers = tproposalers;
	}
	
	@Column(name = "PROPOSAL_TYPE", length = 4)
	public String getProposalType() {
		return proposalType;
	}

	public void setProposalType(String proposalType) {
		this.proposalType = proposalType;
	}
	@Transient
	public String getProposalTypeName() {
		return proposalTypeName;
	}

	public void setProposalTypeName(String proposalTypeName) {
		this.proposalTypeName = proposalTypeName;
	}
	@Column(name = "SUBMIT_FLG", length = 4)
	public String getSubmitFlg() {
		return submitFlg;
	}

	public void setSubmitFlg(String submitFlg) {
		this.submitFlg = submitFlg;
	}
	@Transient
	public String getSubmitFlgName() {
		return submitFlgName;
	}

	public void setSubmitFlgName(String submitFlgName) {
		this.submitFlgName = submitFlgName;
	}
	
	@Column(name = "REVOKE_FLG", length = 4)
	public String getRevokeFlg() {
		return revokeFlg;
	}

	public void setRevokeFlg(String revokeFlg) {
		this.revokeFlg = revokeFlg;
	}
	@Column(name = "ADOPT_EXPLAIN", length = 2000)
	public String getAdoptExplain() {
		return adoptExplain;
	}

	public void setAdoptExplain(String adoptExplain) {
		this.adoptExplain = adoptExplain;
	}
	@Column(name = "ADOPT_FLG", length = 4)
	public String getAdoptFlg() {
		return adoptFlg;
	}

	public void setAdoptFlg(String adoptFlg) {
		this.adoptFlg = adoptFlg;
	}
	@Column(name = "ANALYSIS_UNIT", length = 200)
	public String getAnalySisUnit() {
		return analySisUnit;
	}

	public void setAnalySisUnit(String analySisUnit) {
		this.analySisUnit = analySisUnit;
	}
	@Column(name = "NOFILLING_REASON", length = 2000)
	public String getNoFillingReason() {
		return noFillingReason;
	}

	public void setNoFillingReason(String noFillingReason) {
		this.noFillingReason = noFillingReason;
	}
	@Column(name = "ADVICE_UNIT", length = 2000)
	public String getAdviceUnit() {
		return adviceUnit;
	}

	public void setAdviceUnit(String adviceUnit) {
		this.adviceUnit = adviceUnit;
	}
	@Column(name = "SATISFY_FLG", length = 4)
	public String getSatisfyFlg() {
		return satisfyFlg;
	}

	public void setSatisfyFlg(String satisfyFlg) {
		this.satisfyFlg = satisfyFlg;
	}
	@Column(name = "REPLY_PASS", length = 4)
	public String getReplyPass() {
		return replyPass;
	}

	public void setReplyPass(String replyPass) {
		this.replyPass = replyPass;
	}

	@Transient
	public String getAdoptFlgName() {
		return adoptFlgName;
	}

	public void setAdoptFlgName(String adoptFlgName) {
		this.adoptFlgName = adoptFlgName;
	}

	@Transient
	public String getSponsorIds() {
		return sponsorIds;
	}

	public void setSponsorIds(String sponsorIds) {
		this.sponsorIds = sponsorIds;
	}
	@Transient
	public String getHostFlgs() {
		return hostFlgs;
	}

	public void setHostFlgs(String hostFlgs) {
		this.hostFlgs = hostFlgs;
	}
	@Transient
	public String getWebFlgName() {
		return webFlgName;
	}

	public void setWebFlgName(String webFlgName) {
		this.webFlgName = webFlgName;
	}
	@Transient
	public String getSecondFlgName() {
		return secondFlgName;
	}

	public void setSecondFlgName(String secondFlgName) {
		this.secondFlgName = secondFlgName;
	}
	@Transient
	public String getStutasName() {
		return stutasName;
	}

	public void setStutasName(String stutasName) {
		this.stutasName = stutasName;
	}
	@Transient
	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	@Transient
	public String getFileMethodName() {
		return fileMethodName;
	}

	public void setFileMethodName(String fileMethodName) {
		this.fileMethodName = fileMethodName;
	}
	@Transient
	public String getMeetingFlgName() {
		return meetingFlgName;
	}

	public void setMeetingFlgName(String meetingFlgName) {
		this.meetingFlgName = meetingFlgName;
	}
	@Transient
	public String getPropTypeName() {
		return propTypeName;
	}

	public void setPropTypeName(String propTypeName) {
		this.propTypeName = propTypeName;
	}
	@Transient
	public String getHandleTypeName() {
		return handleTypeName;
	}

	public void setHandleTypeName(String handleTypeName) {
		this.handleTypeName = handleTypeName;
	}
	@Transient
	public String getStressFlgName() {
		return stressFlgName;
	}

	public void setStressFlgName(String stressFlgName) {
		this.stressFlgName = stressFlgName;
	}
	@Transient
	public String getExcellentFlgName() {
		return excellentFlgName;
	}

	public void setExcellentFlgName(String excellentFlgName) {
		this.excellentFlgName = excellentFlgName;
	}
	@Transient
	public String getSecretFlgName() {
		return secretFlgName;
	}

	public void setSecretFlgName(String secretFlgName) {
		this.secretFlgName = secretFlgName;
	}
	@Transient
	public String getHandleHowName() {
		return handleHowName;
	}

	public void setHandleHowName(String handleHowName) {
		this.handleHowName = handleHowName;
	}
	@Transient
	public String getRevokeFlgName() {
		return revokeFlgName;
	}

	public void setRevokeFlgName(String revokeFlgName) {
		this.revokeFlgName = revokeFlgName;
	}
	@Transient
	public String getCompanyIds() {
		return companyIds;
	}

	public void setCompanyIds(String companyIds) {
		this.companyIds = companyIds;
	}
	@Column(name = "MERGE_FLG", length = 4)
	public String getMergeFlag() {
		return mergeFlag;
	}

	public void setMergeFlag(String mergeFlag) {
		this.mergeFlag = mergeFlag;
	}
	@Transient
	public String getMergeFlagName() {
		return mergeFlagName;
	}

	public void setMergeFlagName(String mergeFlagName) {
		this.mergeFlagName = mergeFlagName;
	}
	@Column(name = "MERGE_IDS", length = 1000)
	public String getMergeIds() {
		return mergeIds;
	}

	public void setMergeIds(String mergeIds) {
		this.mergeIds = mergeIds;
	}

	@Column(name = "SOLVE_HOW", length = 4)
	public String getSolveHow() {
		return solveHow;
	}

	public void setSolveHow(String solveHow) {
		this.solveHow = solveHow;
	}
	@Column(name = "CARRYOUT_FLG", length = 4)
	public String getCarryoutFlg() {
		return carryoutFlg;
	}

	public void setCarryoutFlg(String carryoutFlg) {
		this.carryoutFlg = carryoutFlg;
	}
	@Column(name = "COMMITTEEMAN_OPINION", length = 4)
	public String getCommitteemanOpinion() {
		return committeemanOpinion;
	}

	public void setCommitteemanOpinion(String committeemanOpinion) {
		this.committeemanOpinion = committeemanOpinion;
	}
	@Column(name = "UPDATE_FLG", length = 4)
	public String getUpdateFlg() {
		return updateFlg;
	}
	public void setUpdateFlg(String updateFlg) {
		this.updateFlg = updateFlg;
	}
	@Column(name = "INSTRUCTIONS", length = 1000)
	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	@Column(name = "FILING_FLG", length = 4)
	public String getFilingFlg() {
		return filingFlg;
	}

	public void setFilingFlg(String filingFlg) {
		this.filingFlg = filingFlg;
	}


}