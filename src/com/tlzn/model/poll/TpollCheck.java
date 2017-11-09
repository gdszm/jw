package com.tlzn.model.poll;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.tlzn.model.base.BaseObject;


@Entity
@Table(name = "TPOLL_CHECK")
public class TpollCheck extends BaseObject  implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String checkId;
	private Tpoll poll;
	private String link;
	private String linkName;
	private String res;
	private String resName;
	private String diff;
	private Date checkTime;
	private String operator;
	private String operatorName;
	
	public TpollCheck() {
	}
	
	public TpollCheck(String checkId, Tpoll poll, String link,
			String linkName, String res, String resName, String diff,
			Date checkTime, String operator, String operatorName) {
		
		this.checkId = checkId;
		this.poll=poll;
		this.link = link;
		this.linkName = linkName;
		this.res = res;
		this.resName = resName;
		this.diff = diff;
		this.checkTime = checkTime;
		this.operator = operator;
		this.operatorName = operatorName;
	}
	
	@Id
	@Column(name = "CHECK_ID", unique = true, nullable = false, length = 12)
	public String getCheckId() {
		return checkId;
	}
	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}
	@ManyToOne(targetEntity=Tpoll.class)//fetch=FetchType.LAZY
	@JoinColumn(name = "POLL_ID", nullable = false)
	public Tpoll getPoll() {
		return poll;
	}

	public void setPoll(Tpoll poll) {
		this.poll = poll;
	}
	@Column(name = "LINK")
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	@Transient
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	@Column(name = "RES")
	public String getRes() {
		return res;
	}
	public void setRes(String res) {
		this.res = res;
	}
	@Transient
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	@Column(name = "DIFF")
	public String getDiff() {
		return diff;
	}
	public void setDiff(String diff) {
		this.diff = diff;
	}
	@Column(name = "CHECK_TIME")
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	@Column(name = "OPERATOR")
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	@Transient
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	

}
