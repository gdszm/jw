package com.tlzn.pageModel.eduManage;

import java.util.Date;

import com.tlzn.model.base.BaseObject;
/*
 * 教育经历OK
 */
public class EduExp extends BaseObject implements java.io.Serializable{
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
	
	//非数据库
	private String archNo;  //档案编号
	
	public EduExp() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getCert() {
		return cert;
	}

	public void setCert(String cert) {
		this.cert = cert;
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

	public String getArchNo() {
		return archNo;
	}

	public void setArchNo(String archNo) {
		this.archNo = archNo;
	}

	public String getCertifier() {
		return certifier;
	}

	public void setCertifier(String certifier) {
		this.certifier = certifier;
	}
}
