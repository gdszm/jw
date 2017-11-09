package com.tlzn.pageModel.sys;

import java.util.Date;

public class Comm {

	private static final long serialVersionUID = 1L;

	private String code;
	private String name;
	private String pingyin;
	private String sex;
	private String sexName;
	private String nation;
	private String nationName;
	private String groupCode;
	private String groupName;
	private String partyCode;
	private String partyName;
	private String circleCode;
	private String circleName;
	private String eduCode;
	private String eduName;
	private String degreeCode;
	private String degreeName;
	private String vocation;
	private String vocationName;
	private String title;
	private String titleName;
	private String email;
	private Date birthDate;
	private String telephone;
	private String job;
	private String jobName;
	private String companyName;
	private String comparyAddress;
	private String comparyPhone;
	private String comparyPost;
	private String familyAddress;
	private String familyPhone;
	private String familyPost;
	private String status;
	private String statusName;
	private String secondaryCode;
	private String secondaryName;
	private Date createTime;
	private Date updateTime;
	private String remark;
	private String linkMan;
	private String committee;
	private String committeeName;
	private String picName; //照片名称 
	private String gid; //用户组Id
	private String ids;// 传入ID
	private String username;//登录用户名
	private int page;// 当前页
	private int rows;// 每页显示记录数
	private String sort;// 排序字段名
	private String order;// 按什么排序(asc,desc)

	public Comm() {
	}

	public Comm(String name, String pingyin, String sex,
			String nation, String groupCode, String partyCode,
			String circleCode, String eduCode, String degreeCode,
			String vocation, String title, String email, Date birthDate,
			String telephone, String job, String companyName,
			String comparyAddress, String comparyPhone, String comparyPost,
			String familyAddress, String familyPhone, String familyPost,
			String status, String secondaryCode, Date createTime,
			Date updateTime, String remark) {
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
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPingyin() {
		return this.pingyin;
	}

	public void setPingyin(String pingyin) {
		this.pingyin = pingyin;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNation() {
		return this.nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getGroupCode() {
		return this.groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getPartyCode() {
		return this.partyCode;
	}

	public void setPartyCode(String partyCode) {
		this.partyCode = partyCode;
	}

	public String getCircleCode() {
		return this.circleCode;
	}

	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
	}

	public String getEduCode() {
		return this.eduCode;
	}

	public void setEduCode(String eduCode) {
		this.eduCode = eduCode;
	}

	public String getDegreeCode() {
		return this.degreeCode;
	}

	public void setDegreeCode(String degreeCode) {
		this.degreeCode = degreeCode;
	}

	public String getVocation() {
		return this.vocation;
	}

	public void setVocation(String vocation) {
		this.vocation = vocation;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getJob() {
		return this.job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getComparyAddress() {
		return this.comparyAddress;
	}

	public void setComparyAddress(String comparyAddress) {
		this.comparyAddress = comparyAddress;
	}

	public String getComparyPhone() {
		return this.comparyPhone;
	}

	public void setComparyPhone(String comparyPhone) {
		this.comparyPhone = comparyPhone;
	}

	public String getComparyPost() {
		return this.comparyPost;
	}

	public void setComparyPost(String comparyPost) {
		this.comparyPost = comparyPost;
	}

	public String getFamilyAddress() {
		return this.familyAddress;
	}

	public void setFamilyAddress(String familyAddress) {
		this.familyAddress = familyAddress;
	}

	public String getFamilyPhone() {
		return this.familyPhone;
	}

	public void setFamilyPhone(String familyPhone) {
		this.familyPhone = familyPhone;
	}

	public String getFamilyPost() {
		return this.familyPost;
	}

	public void setFamilyPost(String familyPost) {
		this.familyPost = familyPost;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSecondaryCode() {
		return this.secondaryCode;
	}

	public void setSecondaryCode(String secondaryCode) {
		this.secondaryCode = secondaryCode;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSexName() {
		return sexName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}

	public String getNationName() {
		return nationName;
	}

	public void setNationName(String nationName) {
		this.nationName = nationName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	public String getEduName() {
		return eduName;
	}

	public void setEduName(String eduName) {
		this.eduName = eduName;
	}

	public String getDegreeName() {
		return degreeName;
	}

	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}

	public String getVocationName() {
		return vocationName;
	}

	public void setVocationName(String vocationName) {
		this.vocationName = vocationName;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getSecondaryName() {
		return secondaryName;
	}

	public void setSecondaryName(String secondaryName) {
		this.secondaryName = secondaryName;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getCommittee() {
		return committee;
	}

	public void setCommittee(String committee) {
		this.committee = committee;
	}

	public String getCommitteeName() {
		return committeeName;
	}

	public void setCommitteeName(String committeeName) {
		this.committeeName = committeeName;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}