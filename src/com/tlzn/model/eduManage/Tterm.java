package com.tlzn.model.eduManage;

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
@Entity
@Table(name = "term", catalog = "artcollege")
public class Tterm extends BaseObject implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String id;       // 学期编号
	private String acadeYear;// 学年(代码)
	private String termName; // 学期名
	private String termType; // 学期类型（代码）
	private String isNowTerm;// 是否当前学期(1:是 0:否)
	private Integer weeks;    // 学期周数
	private Integer weekDays; // 周天数
	private String comment;  // 备注
	private Date crtTime;    // 创建时间
	private Date uptTime;    // 更新时间

	public Tterm() {
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 16)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "acadeYear", length = 10)
	public String getAcadeYear() {
		return this.acadeYear;
	}

	public void setAcadeYear(String acadeYear) {
		this.acadeYear = acadeYear;
	}

	@Column(name = "termName", length = 50)
	public String getTermName() {
		return this.termName;
	}

	public void setTermName(String termName) {
		this.termName = termName;
	}

	@Column(name = "termType", length = 10)
	public String getTermType() {
		return this.termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
	}

	@Column(name = "isNowTerm", length = 10)
	public String getIsNowTerm() {
		return this.isNowTerm;
	}

	public void setIsNowTerm(String isNowTerm) {
		this.isNowTerm = isNowTerm;
	}

	@Column(name = "weeks")
	public Integer getWeeks() {
		return this.weeks;
	}

	public void setWeeks(Integer weeks) {
		this.weeks = weeks;
	}

	@Column(name = "weekDays")
	public Integer getWeekDays() {
		return this.weekDays;
	}

	public void setWeekDays(Integer weekDays) {
		this.weekDays = weekDays;
	}

	@Column(name = "comment", length = 2000)
	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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
}
