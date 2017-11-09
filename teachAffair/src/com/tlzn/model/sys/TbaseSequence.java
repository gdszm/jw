package com.tlzn.model.sys;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tlzn.model.base.BaseObject;

/**
 * TbaseSequence entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TBASE_SEQUENCE")
public class TbaseSequence extends BaseObject implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields

	private String seqfield;
	private String fieldname;
	private int initnum;
	private int nownum;
	private int addnum;
	private int maxnum;
	private Date updatetime;
	private String status;

	// Constructors

	/** default constructor */
	public TbaseSequence() {
	}

	/** minimal constructor */
	public TbaseSequence(String seqfield, int initnum,
			int addnum, String status) {
		this.seqfield = seqfield;
		this.initnum = initnum;
		this.addnum = addnum;
		this.status = status;
	}

	/** full constructor */
	public TbaseSequence(String seqfield, String fieldname, int initnum,
			int nownum, int addnum, int maxnum,
			Date updatetime, String status) {
		this.seqfield = seqfield;
		this.fieldname = fieldname;
		this.initnum = initnum;
		this.nownum = nownum;
		this.addnum = addnum;
		this.maxnum = maxnum;
		this.updatetime = updatetime;
		this.status = status;
	}

	// Property accessors
	@Id
	@Column(name = "SEQFIELD", length = 50)
	public String getSeqfield() {
		return this.seqfield;
	}

	public void setSeqfield(String seqfield) {
		this.seqfield = seqfield;
	}
	@Column(name = "FIELDNAME", length = 100)
	public String getFieldname() {
		return this.fieldname;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}
	@Column(name = "INITNUM")
	public int getInitnum() {
		return this.initnum;
	}

	public void setInitnum(int initnum) {
		this.initnum = initnum;
	}
	@Column(name = "NOWNUM")
	public int getNownum() {
		return this.nownum;
	}

	public void setNownum(int nownum) {
		this.nownum = nownum;
	}
	@Column(name = "ADDNUM")
	public int getAddnum() {
		return this.addnum;
	}

	public void setAddnum(int addnum) {
		this.addnum = addnum;
	}
	@Column(name = "MAXNUM")
	public int getMaxnum() {
		return this.maxnum;
	}

	public void setMaxnum(int maxnum) {
		this.maxnum = maxnum;
	}
	@Column(name = "UPDATETIME")
	public Date getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	@Column(name = "STATUS",length = 20)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}