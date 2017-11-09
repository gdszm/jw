package com.tlzn.pageModel.eduManage;

import java.util.Date;

import com.tlzn.model.base.BaseObject;
/*
 * 班级管理（教师-班级）Ok
 */
public class ClassManage extends BaseObject implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	private String id;
	private String isAdTeacher;//是否该班班主任
	private Date crtTime; //创建时间
	private Date uptTime; //更新时间
	
	public ClassManage() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIsAdTeacher() {
		return isAdTeacher;
	}

	public void setIsAdTeacher(String isAdTeacher) {
		this.isAdTeacher = isAdTeacher;
	}

	public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

	public Date getUptTime() {
		return uptTime;
	}

	public void setUptTime(Date uptTime) {
		this.uptTime = uptTime;
	}
	
}
