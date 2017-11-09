package com.tlzn.model.eduManage;

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


import com.tlzn.model.base.BaseObject;
/*
 * 班级管理（教师-班级）Ok
 */
@Entity
@Table(name = "classmanage", catalog = "artcollege")
public class TclassManage extends BaseObject implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	private String id;
	private String isAdTeacher;//是否该班班主任
	private Date crtTime; //创建时间
	private Date uptTime; //更新时间
	
	private Tteacher teacher;
	private Tclasses classes;
	
	public TclassManage() {
	}
	
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 16)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "crtTime")
	public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "uptTime")
	public Date getUptTime() {
		return uptTime;
	}
	
	public void setUptTime(Date uptTime) {
		this.uptTime = uptTime;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teacherId")
	public Tteacher getTeacher() {
		return this.teacher;
	}

	public void setTeacher(Tteacher teacher) {
		this.teacher = teacher;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "classesId")
	public Tclasses getClasses() {
		return this.classes;
	}

	public void setClasses(Tclasses classes) {
		this.classes = classes;
	}

	@Column(name = "isAdTeacher", length = 16)
	public String getIsAdTeacher() {
		return this.isAdTeacher;
	}

	public void setIsAdTeacher(String isAdTeacher) {
		this.isAdTeacher = isAdTeacher;
	}
}
