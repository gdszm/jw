package com.tlzn.model.sys;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.tlzn.model.base.BaseObject;

@Entity
@Table(name = "TRULES")
public class Trules extends BaseObject implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	// Fields
	private String id;
	private String typeid;
	private String typeName;
	private String name;
	private int score;
	private String status;
	private String statusName;
	private Date createTime;
	private String memo;
	
	public Trules() {
		
	}

	public Trules(String id, String typeid, String name, int score,
			String status, Date createTime, String memo) {
		super();
		this.id = id;
		this.typeid = typeid;
		this.name = name;
		this.score = score;
		this.status = status;
		this.createTime = createTime;
		this.memo = memo;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 12)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "TYPEID")
	public String getTypeid() {
		return typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}
	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "SCORE")
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name = "MEMO")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	@Transient
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	@Transient
	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	
	

}
