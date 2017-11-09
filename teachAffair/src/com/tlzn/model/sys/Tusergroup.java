package com.tlzn.model.sys;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Id;

import javax.persistence.Table;

import com.tlzn.model.Tcommitteeman;


@Entity
@Table(name = "TUSERGROUP")
public class Tusergroup implements java.io.Serializable {

	
	private static final long serialVersionUID = 1L;
	// Fields	
	private String gcid;//用户分组编码
	private Tcommitteeman tcomm;//用户id
	private Tgroup tgroup;//用户组id
	private Date createTime;
	
	
	// Constructors
	@Id
	@Column(name = "GCID",  length = 50)	
	public String getGcid() {
		return gcid;
	}
	public void setGcid(String gcid) {
		this.gcid = gcid;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CID")
	public Tcommitteeman getTcomm() {
		return tcomm;
	}
	public void setTcomm(Tcommitteeman tcomm) {
		this.tcomm = tcomm;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GID")
	public Tgroup getTgroup() {
		return tgroup;
	}
	public void setTgroup(Tgroup tgroup) {
		this.tgroup = tgroup;
	}
	@Column(name = "CREATE_TIME")	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}