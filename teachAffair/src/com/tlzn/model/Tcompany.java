package com.tlzn.model;

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
import javax.persistence.Transient;

/**
 * Tcompany entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TCOMPANY")
public class Tcompany implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields

	private String companyId;
	private String companyName;
	private String shortName;
	private String companyType;
	private String companyAddress;
	private String companyPost;
	private String linkman;
	private String phone;
	private Date createTime;
	private Date updateTime;
	private String status;
	private String remark;
	private Set<ThandleReply> thandleReplies = new HashSet<ThandleReply>(0);
	private String companyTypeName;
	private String statusName;

	// Constructors

	/** default constructor */
	public Tcompany() {
	}

	/** full constructor */
	public Tcompany(String companyName, String shortName, String companyType,
			String companyAddress, String companyPost, String linkman,
			String phone, Date createTime, Date updateTime, String remark,
			Set<ThandleReply> thandleReplies) {
		this.companyName = companyName;
		this.shortName = shortName;
		this.companyType = companyType;
		this.companyAddress = companyAddress;
		this.companyPost = companyPost;
		this.linkman = linkman;
		this.phone = phone;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.remark = remark;
		this.thandleReplies = thandleReplies;
	}

	// Property accessors
	@Id
	@Column(name = "COMPANY_ID", unique = true, nullable = false, length = 5)
	public String getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	@Column(name = "COMPANY_NAME", length = 500)
	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Column(name = "SHORT_NAME", length = 50)
	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(name = "COMPANY_TYPE", length = 20)
	public String getCompanyType() {
		return this.companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	@Column(name = "COMPANY_ADDRESS", length = 500)
	public String getCompanyAddress() {
		return this.companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	@Column(name = "COMPANY_POST", length = 12)
	public String getCompanyPost() {
		return this.companyPost;
	}

	public void setCompanyPost(String companyPost) {
		this.companyPost = companyPost;
	}

	@Column(name = "LINKMAN", length = 100)
	public String getLinkman() {
		return this.linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	@Column(name = "PHONE", length = 12)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_TIME")
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "STATUS", length = 2)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "REMARK", length = 1000)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tcompany")
	public Set<ThandleReply> getThandleReplies() {
		return this.thandleReplies;
	}

	public void setThandleReplies(Set<ThandleReply> thandleReplies) {
		this.thandleReplies = thandleReplies;
	}
	@Transient
	public String getCompanyTypeName() {
		return companyTypeName;
	}

	public void setCompanyTypeName(String companyTypeName) {
		this.companyTypeName = companyTypeName;
	}
	@Transient
	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

}