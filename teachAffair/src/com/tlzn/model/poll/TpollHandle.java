package com.tlzn.model.poll;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.tlzn.model.Tcompany;
import com.tlzn.model.base.BaseObject;

@Entity
@Table(name = "TPOLL_HANDLE")
public class TpollHandle extends BaseObject  implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String handleId;
	private Tpoll poll;
	private Tcompany comp;
	private String solveHow; // 解决程度
	private String solveHowName;
	private String carryoutFlg; // 是否落实
	private String carryoutFlgName;
	private Date factEnddate; // 实际办结日期
	private String reply; // 办复报告
	private String status; // 状态
	private String statusName;
	private String memo; // 备注
	private String mainFlg; // 主办1/协办0（会办）
	private String mainFlgName;
	private String operator; // 操作人员
	private String operatorName;
	private Date operateTime; // 操作时间
	private String answerMode; // 沟通方式
	private String answerModeName;
	private String replyPass; // 办复审查状态
	private String replyPassName;
	private String opinionExplain; // 意见说明
	private String rebutInfo; // 驳回理由
	
	public TpollHandle() {
	}

	public TpollHandle(String handleId, Tpoll poll, Tcompany comp,
			String solveHow, String solveHowName, String carryoutFlg,
			String carryoutFlgName, Date factEnddate, String reply,
			String status, String statusName, String memo) {
		this.handleId = handleId;
		this.poll = poll;
		this.comp = comp;
		this.solveHow = solveHow;
		this.solveHowName = solveHowName;
		this.carryoutFlg = carryoutFlg;
		this.carryoutFlgName = carryoutFlgName;
		this.factEnddate = factEnddate;
		this.reply = reply;
		this.status = status;
		this.statusName = statusName;
		this.memo = memo;
	}
	@Id
	@Column(name = "HANDLE_ID", unique = true, nullable = false, length = 12)
	public String getHandleId() {
		return handleId;
	}

	public void setHandleId(String handleId) {
		this.handleId = handleId;
	}
	@ManyToOne(targetEntity=Tpoll.class)//fetch=FetchType.LAZY
	@JoinColumn(name = "poll_id", nullable = false)
	public Tpoll getPoll() {
		return poll;
	}

	public void setPoll(Tpoll poll) {
		this.poll = poll;
	}
	@ManyToOne(targetEntity=Tcompany.class)//fetch=FetchType.LAZY
	@JoinColumn(name = "comp_id", nullable = false)
	public Tcompany getComp() {
		return comp;
	}

	public void setComp(Tcompany comp) {
		this.comp = comp;
	}
	@Column(name = "SOLVE_HOW")
	public String getSolveHow() {
		return solveHow;
	}

	public void setSolveHow(String solveHow) {
		this.solveHow = solveHow;
	}
	@Transient
	public String getSolveHowName() {
		return solveHowName;
	}

	public void setSolveHowName(String solveHowName) {
		this.solveHowName = solveHowName;
	}
	@Column(name = "CARRYOUT_FLG")
	public String getCarryoutFlg() {
		return carryoutFlg;
	}

	public void setCarryoutFlg(String carryoutFlg) {
		this.carryoutFlg = carryoutFlg;
	}
	@Transient
	public String getCarryoutFlgName() {
		return carryoutFlgName;
	}

	public void setCarryoutFlgName(String carryoutFlgName) {
		this.carryoutFlgName = carryoutFlgName;
	}
	@Column(name = "FACT_ENDDATE")
	public Date getFactEnddate() {
		return factEnddate;
	}

	public void setFactEnddate(Date factEnddate) {
		this.factEnddate = factEnddate;
	}
	@Column(name = "REPLY")
	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}
	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@Transient
	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	@Column(name = "MEMO")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	@Column(name = "OPERATOR")
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	@Column(name = "OPERATE_TIME")
	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	@Column(name = "MAIN_FLG")
	public String getMainFlg() {
		return mainFlg;
	}

	public void setMainFlg(String mainFlg) {
		this.mainFlg = mainFlg;
	}
	@Column(name = "REPLY_PASS")
	public String getReplyPass() {
		return replyPass;
	}

	public void setReplyPass(String replyPass) {
		this.replyPass = replyPass;
	}
	@Column(name = "ANSWER_MODE")
	public String getAnswerMode() {
		return answerMode;
	}

	public void setAnswerMode(String answerMode) {
		this.answerMode = answerMode;
	}
	@Column(name = "OPINION_EXPLAIN")
	public String getOpinionExplain() {
		return opinionExplain;
	}

	public void setOpinionExplain(String opinionExplain) {
		this.opinionExplain = opinionExplain;
	}
	@Transient
	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	@Transient
	public String getMainFlgName() {
		return mainFlgName;
	}

	public void setMainFlgName(String mainFlgName) {
		this.mainFlgName = mainFlgName;
	}
	@Transient
	public String getReplyPassName() {
		return replyPassName;
	}

	public void setReplyPassName(String replyPassName) {
		this.replyPassName = replyPassName;
	}
	@Transient
	public String getAnswerModeName() {
		return answerModeName;
	}

	public void setAnswerModeName(String answerModeName) {
		this.answerModeName = answerModeName;
	}
	@Column(name = "REBUT_INFO")
	public String getRebutInfo() {
		return rebutInfo;
	}

	public void setRebutInfo(String rebutInfo) {
		this.rebutInfo = rebutInfo;
	}
	
}
