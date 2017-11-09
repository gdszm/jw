package com.tlzn.model.speech;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tlzn.model.Tcommitteeman;
import com.tlzn.model.base.BaseObject;
import com.tlzn.model.meeting.Tmeeting;

/**
 * 会议发言实体类
 * 
 * @author gds
 * @Time 2016-10-11 08:45:00
 * 
 */

@Entity
@Table(name = "TMEET_SPEAK")
public class Tspeech extends BaseObject implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String speakId;      //发言编码
	private String title;        //发言主题
	private String content;      //正文内容
	private String reportFlg;    //是否上报
	private Date createTime;     //提交日期
	private String atts;         //附件
	private String attsDepict;   //附件描述
	private String useSituation; //采用情况
	private String status;       //状态
	private String opinion;      //审核意见
	private Date auditTime;      //审核日期
	private String discardType;  //转弃方向
	private String memo;         //备注
	
	private Tmeeting tmeeting;
	private Tcommitteeman tcommitteeman;
	
	public Tspeech() {
	}

	@Id
	@Column(name = "SPEAK_ID", unique = true, nullable = false, length = 20)
	public String getSpeakId() {
		return this.speakId;
	}

	public void setSpeakId(String speakId) {
		this.speakId = speakId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEET_ID")
	public Tmeeting getTmeeting() {
		return this.tmeeting;
	}

	public void setTmeeting(Tmeeting tmeeting) {
		this.tmeeting = tmeeting;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMM_CODE")
	public Tcommitteeman getTcommitteeman() {
		return this.tcommitteeman;
	}

	public void setTcommitteeman(Tcommitteeman tcommitteeman) {
		this.tcommitteeman = tcommitteeman;
	}

	@Column(name = "TITLE", length = 300)
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

	@Column(name = "REPORT_FLG", length = 4)
	public String getReportFlg() {
		return this.reportFlg;
	}

	public void setReportFlg(String reportFlg) {
		this.reportFlg = reportFlg;
	}

	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "ATTS", length = 500)
	public String getAtts() {
		return this.atts;
	}

	public void setAtts(String atts) {
		this.atts = atts;
	}

	@Column(name = "ATTS_DEPICT", length = 2000)
	public String getAttsDepict() {
		return this.attsDepict;
	}

	public void setAttsDepict(String attsDepict) {
		this.attsDepict = attsDepict;
	}

	@Column(name = "USE_SITUATION", length = 4)
	public String getUseSituation() {
		return this.useSituation;
	}

	public void setUseSituation(String useSituation) {
		this.useSituation = useSituation;
	}

	@Column(name = "STATUS", length = 4)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "OPINION", length = 2000)
	public String getOpinion() {
		return this.opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	@Column(name = "AUDIT_TIME")
	public Date getAuditTime() {
		return this.auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	@Column(name = "DISCARD_TYPE", length = 4)
	public String getDiscardType() {
		return this.discardType;
	}

	public void setDiscardType(String discardType) {
		this.discardType = discardType;
	}

	@Column(name = "MEMO", length = 2000)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
