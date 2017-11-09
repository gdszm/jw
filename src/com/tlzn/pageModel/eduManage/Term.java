package com.tlzn.pageModel.eduManage;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tlzn.model.base.BaseObject;

/*
 * 学期
 */

public class Term extends BaseObject implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String id;       // 学期编号
	private String acadeYear;// 学年(代码)
	private String termName; // 学期名
	private String termType; // 学期类型（代码）
	private String isNowTerm;// 是否当前学期(1:是 0:否)
	private Integer weeks;   // 学期周数
	private Integer weekDays;// 周天数
	private String comment;  // 备注
	private Date crtTime;    // 创建时间
	private Date uptTime;    // 更新时间

	//非数据库
	private String acadeYearName;// 学年名称
	private String termTypeName; // 学期类型名称
	private String isNowTermName;// 是否当前学期名称
	
	public Term() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAcadeYear() {
		return acadeYear;
	}

	public void setAcadeYear(String acadeYear) {
		this.acadeYear = acadeYear;
	}

	public String getTermName() {
		return termName;
	}

	public void setTermName(String termName) {
		this.termName = termName;
	}

	public String getTermType() {
		return termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
	}

	public String getIsNowTerm() {
		return isNowTerm;
	}

	public void setIsNowTerm(String isNowTerm) {
		this.isNowTerm = isNowTerm;
	}

	public Integer getWeeks() {
		return weeks;
	}

	public void setWeeks(Integer weeks) {
		this.weeks = weeks;
	}

	public Integer getWeekDays() {
		return weekDays;
	}

	public void setWeekDays(Integer weekDays) {
		this.weekDays = weekDays;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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
