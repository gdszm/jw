package com.tlzn.model.sys;

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
 * 数据字典 entity
 * 
 * @author 刘平
 */

@Entity
@Table(name = "TDIC")
public class Tdic implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	// Fields

	private String cid; 		// ID
	private String ckey;		// key
	private String cvalue;		// value
	private String ctype;		// 类型
	private String ctypeName;	// 类型名称
	private String cstatus;		// 状态 0启用 1停用
	
	private Tdic tdic;
	private Set<Tdic> tdics = new HashSet<Tdic>(0);
	
	// Constructors

	/** default constructor */
	public Tdic() {
	}

	/** full constructor */
	public Tdic(String cid, String ckey, String cvalue, String ctype) {
		super();
		this.cid = cid;
		this.ckey = ckey;
		this.cvalue = cvalue;
		this.ctype = ctype;
	}

	@Id
	@Column(name = "CID", nullable = false, length = 36)
	public String getCid() {
		return cid;
	}	

	public void setCid(String cid) {
		this.cid = cid;
	}

	@Column(name = "CKEY", nullable = false, length = 50)
	public String getCkey() {
		return ckey;
	}

	public void setCkey(String ckey) {
		this.ckey = ckey;
	}

	@Column(name = "CVALUE" , length = 50)
	public String getCvalue() {
		return cvalue;
	}

	public void setCvalue(String cvalue) {
		this.cvalue = cvalue;
	}

	@Column(name = "CTYPE" , length = 36)
	public String getCtype() {
		return ctype;
	}

	public void setCtype(String ctype) {
		this.ctype = ctype;
	}

	@Column(name = "CSTATUS" , length = 10)
	public String getCstatus() {
		return cstatus;
	}

	public void setCstatus(String cstatus) {
		this.cstatus = cstatus;
	}
	@Column(name = "CTYPENAME" , length = 100)
	public String getCtypeName() {
		return ctypeName;
	}

	public void setCtypeName(String ctypeName) {
		this.ctypeName = ctypeName;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CBASETYPE")
	public Tdic getTdic() {
		return tdic;
	}

	public void setTdic(Tdic tdic) {
		this.tdic = tdic;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tdic")
	public Set<Tdic> getTdics() {
		return tdics;
	}

	public void setTdics(Set<Tdic> tdics) {
		this.tdics = tdics;
	}
}
