package com.tlzn.model.meeting;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tlzn.model.Tcommitteeman;

@Entity
@Table(name = "TMEETING_MAN")
public class TmeetingMan implements java.io.Serializable{


   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private Tmeeting tmeet;
	private Tcommitteeman tcomm;
	private String status;
	private String leaveType;
	private String leaveReason;
	private Date  createTime;
	private String  memo;
	
	
	public TmeetingMan() {
	}


	public TmeetingMan(String id) {
		this.id = id;
	}


	public TmeetingMan(String id, Tmeeting tmeet, Tcommitteeman tcomm,
			String status, String leaveType, String leaveReason,
			Date createTime, String memo) {
		this.id = id;
		this.tmeet = tmeet;
		this.tcomm = tcomm;
		this.status = status;
		this.leaveType = leaveType;
		this.leaveReason = leaveReason;
		this.createTime = createTime;
		this.memo = memo;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 20)
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(targetEntity=Tmeeting.class)//fetch=FetchType.LAZY
	@JoinColumn(name = "MEET_ID", nullable = false)
	public Tmeeting getTmeet() {
		return tmeet;
	}


	public void setTmeet(Tmeeting tmeet) {
		this.tmeet = tmeet;
	}

	@ManyToOne(targetEntity=Tcommitteeman.class)//fetch=FetchType.LAZY
	@JoinColumn(name = "COMM_CODE", nullable = false)
	public Tcommitteeman getTcomm() {
		return tcomm;
	}


	public void setTcomm(Tcommitteeman tcomm) {
		this.tcomm = tcomm;
	}

	@Column(name = "STATUS",length = 20)
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "LEAVE_TYPE",length = 4)
	public String getLeaveType() {
		return leaveType;
	}


	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	@Column(name = "LEAVE_REASON",length = 500)
	public String getLeaveReason() {
		return leaveReason;
	}


	public void setLeaveReason(String leaveReason) {
		this.leaveReason = leaveReason;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATETIME", length = 7)
	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "MEMO",length = 2000)
	public String getMemo() {
		return memo;
	}


	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
}
