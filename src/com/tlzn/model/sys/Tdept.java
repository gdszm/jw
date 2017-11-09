package com.tlzn.model.sys;

import java.math.BigDecimal;
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

/**
 * Tauth entity. @author MyEclipse Persistence Tools
 */

@Entity
@Table(name = "TDEPT", schema = "")
public class Tdept implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;
	
	private String cid; // 部门ID
	private Tdept tdept; // 上级部门
	private String ciconcls; // 部门图标
	private String cname; // 部门名称
	private BigDecimal cseq; // 排序
	private String cdesc;//部门描述
	private Set<Tdept> tdepts = new HashSet<Tdept>(0);

	// Constructors

	/** default constructor */
	public Tdept() {
	}

	/** minimal constructor */
	public Tdept(String cid, String cname) {
		this.cid = cid;
		this.cname = cname;
	}

	/** full constructor */
	public Tdept(String cid, Tdept tdept, String ciconcls, String cname, BigDecimal cseq, Set<Tdept> tdepts) {
		super();
		this.cid = cid;
		this.tdept = tdept;
		this.ciconcls = ciconcls;
		this.cname = cname;
		this.cseq = cseq;
		this.tdepts = tdepts;
	}

	@Id
	@Column(name = "CID", length = 36)
	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CPID")
	public Tdept getTdept() {
		return tdept;
	}

	public void setTdept(Tdept tdept) {
		this.tdept = tdept;
	}

	@Column(name = "CICONCLS", length = 100)
	public String getCiconcls() {
		return ciconcls;
	}

	public void setCiconcls(String ciconcls) {
		this.ciconcls = ciconcls;
	}

	@Column(name = "CNAME", nullable = false, length = 100)
	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	@Column(name = "CSEQ", precision = 22, scale = 0)
	public BigDecimal getCseq() {
		return cseq;
	}

	public void setCseq(BigDecimal cseq) {
		this.cseq = cseq;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tdept")
	public Set<Tdept> getTdepts() {
		return tdepts;
	}

	public void setTdepts(Set<Tdept> tdepts) {
		this.tdepts = tdepts;
	}

	@Column(name = "CDESC",length = 250 )
	public String getCdesc() {
		return cdesc;
	}

	public void setCdesc(String cdesc) {
		this.cdesc = cdesc;
	}

}
