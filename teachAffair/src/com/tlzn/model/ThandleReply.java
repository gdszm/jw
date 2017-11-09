package com.tlzn.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ThandleReply entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "THANDLE_REPLY", schema = "")
public class ThandleReply implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields

	private String handleReplyId;
	private Tproposal tproposal;
	private Tcompany tcompany;
	private String answerMode;
	private String replySecret;
	private String committeemanOpinion;
	private String solveHow;
	private String carryoutFlg;
	private Date factEnddate;
	private String opinionExplain;
	private String reply;
	private String operator;
	private Date operateTime;
	private String status;
	private String remark;
	private String mainFlg;
	private String satisfyFlg;
	private String satisfyInfo;
	private String replyPass;
	private String rebutInfo;

	// Constructors

	/** default constructor */
	public ThandleReply() {
	}

	/** minimal constructor */
	public ThandleReply(Tcompany tcompany) {
		this.tcompany = tcompany;
	}

	/** full constructor */
	public ThandleReply(Tproposal tproposal, Tcompany tcompany,
			String answerMode, String replySecret, String committeemanOpinion,
			String solveHow, String carryoutFlg, Date factEnddate,
			String opinionExplain, String reply, String operator,
			Date operateTime, String status, String remark) {
		this.tproposal = tproposal;
		this.tcompany = tcompany;
		this.answerMode = answerMode;
		this.replySecret = replySecret;
		this.committeemanOpinion = committeemanOpinion;
		this.solveHow = solveHow;
		this.carryoutFlg = carryoutFlg;
		this.factEnddate = factEnddate;
		this.opinionExplain = opinionExplain;
		this.reply = reply;
		this.operator = operator;
		this.operateTime = operateTime;
		this.status = status;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "HANDLE_REPLY_ID", unique = true, nullable = false, length = 12)
	public String getHandleReplyId() {
		return this.handleReplyId;
	}

	public void setHandleReplyId(String handleReplyId) {
		this.handleReplyId = handleReplyId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROPOSAL_ID")
	public Tproposal getTproposal() {
		return this.tproposal;
	}

	public void setTproposal(Tproposal tproposal) {
		this.tproposal = tproposal;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID", nullable = false)
	public Tcompany getTcompany() {
		return this.tcompany;
	}

	public void setTcompany(Tcompany tcompany) {
		this.tcompany = tcompany;
	}

	@Column(name = "ANSWER_MODE", length = 4)
	public String getAnswerMode() {
		return this.answerMode;
	}

	public void setAnswerMode(String answerMode) {
		this.answerMode = answerMode;
	}

	@Column(name = "REPLY_SECRET", length = 4)
	public String getReplySecret() {
		return this.replySecret;
	}

	public void setReplySecret(String replySecret) {
		this.replySecret = replySecret;
	}

	@Column(name = "COMMITTEEMAN_OPINION", length = 4)
	public String getCommitteemanOpinion() {
		return this.committeemanOpinion;
	}

	public void setCommitteemanOpinion(String committeemanOpinion) {
		this.committeemanOpinion = committeemanOpinion;
	}

	@Column(name = "SOLVE_HOW", length = 4)
	public String getSolveHow() {
		return this.solveHow;
	}

	public void setSolveHow(String solveHow) {
		this.solveHow = solveHow;
	}

	@Column(name = "CARRYOUT_FLG", length = 4)
	public String getCarryoutFlg() {
		return this.carryoutFlg;
	}

	public void setCarryoutFlg(String carryoutFlg) {
		this.carryoutFlg = carryoutFlg;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FACT_ENDDATE")
	public Date getFactEnddate() {
		return this.factEnddate;
	}

	public void setFactEnddate(Date factEnddate) {
		this.factEnddate = factEnddate;
	}

	@Column(name = "OPINION_EXPLAIN", length = 4000)
	public String getOpinionExplain() {
		return this.opinionExplain;
	}

	public void setOpinionExplain(String opinionExplain) {
		this.opinionExplain = opinionExplain;
	}

	@Column(name = "REPLY")
	public String getReply() {
		return this.reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	@Column(name = "OPERATOR", length =50)
	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	@Column(name = "OPERATE_TIME")
	public Date getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	@Column(name = "STATUS", length = 4)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "REMARK", length = 1000)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "MAIN_FLG", length = 4)
	public String getMainFlg() {
		return mainFlg;
	}

	public void setMainFlg(String mainFlg) {
		this.mainFlg = mainFlg;
	}
	@Column(name = "SATISFY_FLG", length = 4)
	public String getSatisfyFlg() {
		return satisfyFlg;
	}

	public void setSatisfyFlg(String satisfyFlg) {
		this.satisfyFlg = satisfyFlg;
	}
	@Column(name = "SATISFY_INFO", length = 2000)
	public String getSatisfyInfo() {
		return satisfyInfo;
	}

	public void setSatisfyInfo(String satisfyInfo) {
		this.satisfyInfo = satisfyInfo;
	}
	@Column(name = "REPLY_PASS", length = 4)
	public String getReplyPass() {
		return replyPass;
	}

	public void setReplyPass(String replyPass) {
		this.replyPass = replyPass;
	}
	
	@Column(name = "REBUT_INFO", length = 2000)
	public String getRebutInfo() {
		return rebutInfo;
	}

	public void setRebutInfo(String rebutInfo) {
		this.rebutInfo = rebutInfo;
	}

}