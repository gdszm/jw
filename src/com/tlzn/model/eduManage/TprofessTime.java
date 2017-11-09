package com.tlzn.model.eduManage;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tlzn.model.base.BaseObject;

/**
 * 授课时间
 */
@Entity
@Table(name = "professtime", catalog = "artcollege")
public class TprofessTime extends BaseObject implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id; 
	private String weeks;  //周次
	private String week;   //星期
	private String section;//节次
	private Date crtTime;  //创建时间
	private Date uptTime;  //更新时间
	
	private Set<Tprofess> professes = new HashSet<Tprofess>(0);

	public TprofessTime() {
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 16)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "weeks", length = 200)
	public String getWeeks() {
		return this.weeks;
	}

	public void setWeeks(String weeks) {
		this.weeks = weeks;
	}

	@Column(name = "week", length = 200)
	public String getWeek() {
		return this.week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	@Column(name = "section", length = 200)
	public String getSection() {
		return this.section;
	}

	public void setSection(String section) {
		this.section = section;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "professtime")
	public Set<Tprofess> getProfesses() {
		return this.professes;
	}

	public void setProfesses(Set<Tprofess> professes) {
		this.professes = professes;
	}

}