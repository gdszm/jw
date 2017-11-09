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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * Tcommitteeman entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TCOMMITTEEMAN")
public class Tcommitteeman implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields

	private String code;
	private String name;
	private String pingyin;
	private String sex;
	private String nation;
	private String groupCode;
	private String partyCode;
	private String circleCode;
	private String eduCode;
	private String degreeCode;
	private String vocation;
	private String title;
	private String email;
	private Date birthDate;
	private String telephone;
	private String job;
	private String companyName;
	private String comparyAddress;
	private String comparyPhone;
	private String comparyPost;
	private String familyAddress;
	private String familyPhone;
	private String familyPost;
	private String status;
	private String secondaryCode;
	private Date createTime;
	private Date updateTime;
	private String remark;
	private String linkMan;
	private String committee;
	private String picName; //照片名称 
	private String username;//登录用户名
	private Set<Tproposaler> tproposalers = new HashSet<Tproposaler>(0);

	// Constructors

	/** default constructor */
	public Tcommitteeman() {
	}

	/** default constructor */
	public Tcommitteeman(String code) {
		this.code=code;
	}

	/** full constructor */
	public Tcommitteeman(String name, String pingyin, String sex,
			String nation, String groupCode, String partyCode,
			String circleCode, String eduCode, String degreeCode,
			String vocation, String title, String email, Date birthDate,
			String telephone, String job, String companyName,
			String comparyAddress, String comparyPhone, String comparyPost,
			String familyAddress, String familyPhone, String familyPost,
			String status, String secondaryCode, Date createTime,
			Date updateTime, String remark, Set<Tproposaler> tproposalers) {
		this.name = name;
		this.pingyin = pingyin;
		this.sex = sex;
		this.nation = nation;
		this.groupCode = groupCode;
		this.partyCode = partyCode;
		this.circleCode = circleCode;
		this.eduCode = eduCode;
		this.degreeCode = degreeCode;
		this.vocation = vocation;
		this.title = title;
		this.email = email;
		this.birthDate = birthDate;
		this.telephone = telephone;
		this.job = job;
		this.companyName = companyName;
		this.comparyAddress = comparyAddress;
		this.comparyPhone = comparyPhone;
		this.comparyPost = comparyPost;
		this.familyAddress = familyAddress;
		this.familyPhone = familyPhone;
		this.familyPost = familyPost;
		this.status = status;
		this.secondaryCode = secondaryCode;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.remark = remark;
		this.tproposalers = tproposalers;
	}

	// Property accessors
	@Id
	@Column(name = "CODE", unique = true, nullable = false, length = 5)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "NAME", length = 40)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PINGYIN", length = 50)
	public String getPingyin() {
		return this.pingyin;
	}

	public void setPingyin(String pingyin) {
		this.pingyin = pingyin;
	}

	@Column(name = "SEX", length = 2)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "NATION", length = 50)
	public String getNation() {
		return this.nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	@Column(name = "GROUP_CODE", length = 12)
	public String getGroupCode() {
		return this.groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	@Column(name = "PARTY_CODE", length = 12)
	public String getPartyCode() {
		return this.partyCode;
	}

	public void setPartyCode(String partyCode) {
		this.partyCode = partyCode;
	}

	@Column(name = "CIRCLE_CODE", length = 12)
	public String getCircleCode() {
		return this.circleCode;
	}

	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
	}

	@Column(name = "EDU_CODE", length = 12)
	public String getEduCode() {
		return this.eduCode;
	}

	public void setEduCode(String eduCode) {
		this.eduCode = eduCode;
	}

	@Column(name = "DEGREE_CODE", length = 12)
	public String getDegreeCode() {
		return this.degreeCode;
	}

	public void setDegreeCode(String degreeCode) {
		this.degreeCode = degreeCode;
	}

	@Column(name = "VOCATION", length = 300)
	public String getVocation() {
		return this.vocation;
	}

	public void setVocation(String vocation) {
		this.vocation = vocation;
	}

	@Column(name = "TITLE", length = 300)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "EMAIL", length = 200)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "BIRTH_DATE", length = 7)
	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Column(name = "TELEPHONE", length = 12)
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "JOB", length = 300)
	public String getJob() {
		return this.job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	@Column(name = "COMPANY_NAME", length = 500)
	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Column(name = "COMPARY_ADDRESS", length = 500)
	public String getComparyAddress() {
		return this.comparyAddress;
	}

	public void setComparyAddress(String comparyAddress) {
		this.comparyAddress = comparyAddress;
	}

	@Column(name = "COMPARY_PHONE", length = 12)
	public String getComparyPhone() {
		return this.comparyPhone;
	}

	public void setComparyPhone(String comparyPhone) {
		this.comparyPhone = comparyPhone;
	}

	@Column(name = "COMPARY_POST", length = 12)
	public String getComparyPost() {
		return this.comparyPost;
	}

	public void setComparyPost(String comparyPost) {
		this.comparyPost = comparyPost;
	}

	@Column(name = "FAMILY_ADDRESS", length = 500)
	public String getFamilyAddress() {
		return this.familyAddress;
	}

	public void setFamilyAddress(String familyAddress) {
		this.familyAddress = familyAddress;
	}

	@Column(name = "FAMILY_PHONE", length = 12)
	public String getFamilyPhone() {
		return this.familyPhone;
	}

	public void setFamilyPhone(String familyPhone) {
		this.familyPhone = familyPhone;
	}

	@Column(name = "FAMILY_POST", length = 12)
	public String getFamilyPost() {
		return this.familyPost;
	}

	public void setFamilyPost(String familyPost) {
		this.familyPost = familyPost;
	}

	@Column(name = "STATUS", length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "SECONDARY_CODE", length = 300)
	public String getSecondaryCode() {
		return this.secondaryCode;
	}

	public void setSecondaryCode(String secondaryCode) {
		this.secondaryCode = secondaryCode;
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

	@Column(name = "REMARK", length = 1000)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "LINKMAN", length = 50)
	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	@Column(name = "COMMITTEE", length = 12)
	public String getCommittee() {
		return committee;
	}

	public void setCommittee(String committee) {
		this.committee = committee;
	}
	@Column(name = "PICNAME", length = 30)
	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}
	@Transient
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tcommitteeman")
	public Set<Tproposaler> getTproposalers() {
		return this.tproposalers;
	}

	public void setTproposalers(Set<Tproposaler> tproposalers) {
		this.tproposalers = tproposalers;
	}

}