package com.tlzn.model.sys;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Tuser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TGROUP")
public class Tgroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields
	private String gid;//用户组编号
	private String gname;//组名称
	private String type;//用户分组类别
	private Date createTime;//创建时间
	
	
	public Tgroup(){}
	

	public Tgroup(String gid, String gname, Date createTime) {
		super();
		this.gid = gid;
		this.gname = gname;
		this.createTime = createTime;
	}
	@Id
	@Column(name = "GID",length=50)
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	@Column(name = "GNAME",length=50)
	public String getGname() {
		return gname;
	}
	public void setGname(String gname) {
		this.gname = gname;
	}
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "TYPE",  length = 50)	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}