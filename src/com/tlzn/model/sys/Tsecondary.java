package com.tlzn.model.sys;

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

import com.tlzn.model.Tproposal;

/**
 * Tsecondary entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TSECONDARY", schema = "")
public class Tsecondary implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields

	private String secondaryId;
	private String secondayName;
	private String year;
	private String period;
	private String ext;
	private String status;
	private Date createTime;
	private String remark;
	private Set<Tproposal> tproposals = new HashSet<Tproposal>(0);

	// Constructors

	/** default constructor */
	public Tsecondary() {
	}

	/** full constructor */
	public Tsecondary(String secondayName, String year, String status,
			Date createTime, String remark, Set<Tproposal> tproposals) {
		this.secondayName = secondayName;
		this.year = year;
		this.status = status;
		this.createTime = createTime;
		this.remark = remark;
		this.tproposals = tproposals;
	}

	// Property accessors
	@Id
	@Column(name = "SECONDARY_ID", unique = true, nullable = false, length = 12)
	public String getSecondaryId() {
		return this.secondaryId;
	}

	public void setSecondaryId(String secondaryId) {
		this.secondaryId = secondaryId;
	}

	@Column(name = "SECONDAY_NAME", length = 100)
	public String getSecondayName() {
		return this.secondayName;
	}

	public void setSecondayName(String secondayName) {
		this.secondayName = secondayName;
	}

	@Column(name = "YEAR", length = 4)
	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Column(name = "STATUS", length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "REMARK", length = 1000)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tsecondary")
	public Set<Tproposal> getTproposals() {
		return this.tproposals;
	}

	public void setTproposals(Set<Tproposal> tproposals) {
		this.tproposals = tproposals;
	}
	@Column(name = "PERIOD", length = 4)
	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}
	@Column(name = "EXT", length = 8)
	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

}