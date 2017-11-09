package com.tlzn.model.dailywork;

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

import com.tlzn.model.Tcommitteeman;

/**
 * TfiledataMan entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TFILEDATA_MAN")
public class TfiledataMan implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4811604301004710409L;
	
	private String id;
	private TfileData tfiledata;
	private Tcommitteeman tcommitteeman;
	private Date createtime;
	private String memo;

	/** default constructor */
	public TfiledataMan() {
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 20)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DATA_ID")
	public TfileData getTfiledata() {
		return this.tfiledata;
	}

	public void setTfiledata(TfileData tfiledata) {
		this.tfiledata = tfiledata;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMM_CODE")
	public Tcommitteeman getTcommitteeman() {
		return this.tcommitteeman;
	}

	public void setTcommitteeman(Tcommitteeman tcommitteeman) {
		this.tcommitteeman = tcommitteeman;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATETIME", length = 7)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Column(name = "MEMO", length = 2000)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}