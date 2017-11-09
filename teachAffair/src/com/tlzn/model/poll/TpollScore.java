package com.tlzn.model.poll;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.tlzn.model.base.BaseObject;
import com.tlzn.model.sys.Trules;

@Entity
@Table(name = "TPOLL_SCORE")
public class TpollScore extends BaseObject implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	private String scoreId;
	private String comment;
	private Date creatTime;
	private String memo;
	private Tpoll poll;
	private Trules rules;
	
	
	public TpollScore() {
	}
	@Id
	@Column(name = "SCORE_ID", unique = true, nullable = false, length = 12)
	public String getScoreId() {
		return scoreId;
	}

	public void setScoreId(String scoreId) {
		this.scoreId = scoreId;
	}
	@Column(name = "comm",length=3000)
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	@Column(name = "CREATE_TIME")
	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
	@Column(name = "MEMO",length=3000)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "POLL_ID")
	public Tpoll getPoll() {
		return poll;
	}

	public void setPoll(Tpoll poll) {
		this.poll = poll;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RULES_ID")
	public Trules getRules() {
		return rules;
	}

	public void setRules(Trules rules) {
		this.rules = rules;
	}
	
	
}
