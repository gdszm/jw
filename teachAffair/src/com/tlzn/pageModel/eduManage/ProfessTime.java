package com.tlzn.pageModel.eduManage;

import java.util.Date;

import com.tlzn.model.base.BaseObject;

/**
 * 授课时间
 */
public class ProfessTime extends BaseObject implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String id; 
	private String weeks;  //周次
	private String week;   //星期
	private String section;//节次
	private Date crtTime;  //创建时间
	private Date uptTime;  //更新时间
	
	//非数据库
	private Date crtTimeStart; //创建时间 开始
	private Date crtTimeEnd;   //创建时间 结束
	private Date uptTimeStart; //更新时间开始
	private Date uptTimeEnd;   //更新时间 结束
	
	public ProfessTime() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWeeks() {
		return weeks;
	}

	public void setWeeks(String weeks) {
		this.weeks = weeks;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
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

	public Date getCrtTimeStart() {
		return crtTimeStart;
	}

	public void setCrtTimeStart(Date crtTimeStart) {
		this.crtTimeStart = crtTimeStart;
	}

	public Date getCrtTimeEnd() {
		return crtTimeEnd;
	}

	public void setCrtTimeEnd(Date crtTimeEnd) {
		this.crtTimeEnd = crtTimeEnd;
	}

	public Date getUptTimeStart() {
		return uptTimeStart;
	}

	public void setUptTimeStart(Date uptTimeStart) {
		this.uptTimeStart = uptTimeStart;
	}

	public Date getUptTimeEnd() {
		return uptTimeEnd;
	}

	public void setUptTimeEnd(Date uptTimeEnd) {
		this.uptTimeEnd = uptTimeEnd;
	}
}