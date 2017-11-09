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
 * 教育经历OK
 */
@Entity
@Table(name = "eduexp", catalog = "artcollege")
public class TeduExp extends BaseObject implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private Date beginDate;  //开始年月
	private Date endDate;    //结束年月
	private String unit;     //教育单位
	private String major;    //专业
	private String cert;     //所获证书
	private String certifier;//证明人
	private Date crtTime;    //创建时间
	private Date uptTime;    //更新时间
	
	private Tarchives archives;
	public TeduExp() {
	}
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "beginDate", length = 10)
	public Date getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "endDate", length = 10)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "unit", length = 100)
	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "major", length = 100)
	public String getMajor() {
		return this.major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	@Column(name = "cert", length = 200)
	public String getCert() {
		return this.cert;
	}

	public void setCert(String cert) {
		this.cert = cert;
	}
	@Column(name = "certifier", length = 45)
	public String getCertifier() {
		return certifier;
	}
	public void setCertifier(String certifier) {
		this.certifier = certifier;
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
	@JoinColumn(name = "foreignId")
	public Tarchives getArchives() {
		return this.archives;
	}

	public void setArchives(Tarchives archives) {
		this.archives = archives;
	}
}
