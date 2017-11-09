package com.tlzn.model.sys;

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

/**
 * Tuser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TUSER")
public class Tuser implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;
	
	private String cid;
	private String cname;
	private String cpwd;	
	private String crealname;
	private String mobile;
	private String cemail;
	
	private String csn;
	private String cstatus;
//	private Date ccreatedatetime;
//	private Date cmodifydatetime;

	private String userCode;
	private String userGroup;
	
	private Date crtTime;
	private Date updTime;
	private String crtUsr;
	private String updUsr;
	
	private Tdept tdept;
	private Set<Tusertrole> tusertroles = new HashSet<Tusertrole>(0);

	// Constructors

	/** default constructor */
	public Tuser() {
	}

	// Property accessors
	@Id
	@Column(name = "CID",   length = 36)
	public String getCid() {
		return this.cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "CCREATEDATETIME", length = 7)
//	public Date getCcreatedatetime() {
//		return this.ccreatedatetime;
//	}
//
//	public void setCcreatedatetime(Date ccreatedatetime) {
//		this.ccreatedatetime = ccreatedatetime;
//	}
//
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "CMODIFYDATETIME", length = 7)
//	public Date getCmodifydatetime() {
//		return this.cmodifydatetime;
//	}
//
//	public void setCmodifydatetime(Date cmodifydatetime) {
//		this.cmodifydatetime = cmodifydatetime;
//	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "crtTime", length = 7)
	public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updTime", length = 7)
	public Date getUpdTime() {
		return updTime;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}
	@Column(name = "crtUsr", length = 36)
	public String getCrtUsr() {
		return crtUsr;
	}

	public void setCrtUsr(String crtUsr) {
		this.crtUsr = crtUsr;
	}
	@Column(name = "updUsr", length = 36)
	public String getUpdUsr() {
		return updUsr;
	}

	public void setUpdUsr(String updUsr) {
		this.updUsr = updUsr;
	}

	@Column(name = "CNAME", unique = true, nullable = false, length = 100)
	public String getCname() {
		return this.cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	@Column(name = "CPWD", nullable = false, length = 100)
	public String getCpwd() {
		return this.cpwd;
	}

	public void setCpwd(String cpwd) {
		this.cpwd = cpwd;
	}
	@Column(name = "MOBILE",length = 36)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "CEMAIL", unique = true, length = 50)
	public String getCemail() {
		return cemail;
	}

	public void setCemail(String cemail) {
		this.cemail = cemail;
	}

	@Column(name = "CSTATUS", length = 10)
	public String getCstatus() {
		return cstatus;
	}

	public void setCstatus(String cstatus) {
		this.cstatus = cstatus;
	}

	@Column(name = "CREALNAME", nullable = false, length = 200)
	public String getCrealname() {
		return crealname;
	}

	public void setCrealname(String crealname) {
		this.crealname = crealname;
	}

	@Column(name = "CSN", length = 36)
	public String getCsn() {
		return csn;
	}

	public void setCsn(String csn) {
		this.csn = csn;
	}
	@Column(name = "USERCODE", length = 16)
	public String getUserCode() {
		return userCode;
	}
	
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	@Column(name = "USERGROUP", length = 12)
	public String getUserGroup() {
		return userGroup;
	}
	
	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cdeptid")
	public Tdept getTdept() {
		return tdept;
	}

	public void setTdept(Tdept tdept) {
		this.tdept = tdept;
	}
	

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tuser")
	public Set<Tusertrole> getTusertroles() {
		return this.tusertroles;
	}

	public void setTusertroles(Set<Tusertrole> tusertroles) {
		this.tusertroles = tusertroles;
	}
}