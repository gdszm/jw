package com.tlzn.pageModel.activity;

import java.util.Date;

import com.tlzn.model.base.BaseObject;

public class Activitypeo extends BaseObject implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String auid;//AUID	编码
	private String aid;//AID	活动ID
	private String code;//COMM_CODE	用户ID
	private String astatus;//ASTATUS	出席状态
	private Date createtime;//CREATETIME	创建日期
	private String memo;//MEMO	备注
	private String leaveType;//LEAVE_TYPE	请假类型
	private String leaveReason;//LEAVE_REASON	请假事由
	private String astatusName;//出席状态
	private String leaveTypeName;//请假类型
	
	private String commCode;
	private String name;//用户姓名s
	private String sex;
	private String sexName;//性别
	private String nation;
	private String nationName;//民族
	private String job;//工作
	private String circleCode;
	private String circleName;//界别
	private String telephone;//电话
	private String committee;//所属专委会
	private String committeeName;//所属专委会
	private String partyCode;
	private String partyName;//党派
	private String comparyPhone;//固定电话
	private String email;//电子邮箱
	private String companyName;
	private String comparyAddress;//通讯地址
	private String comparyPost;//邮编
	
	
	private String atheme;//	活动主题
	private String aspecies;//	活动种类
	private String agency;//	承办机构
	private String place;//	地点
	private String secondaryId;//	所属届次
	private Date timebeg;//	活动开始时间
	private Date timeend;//	结束时间
	private String aspeciesName;//	活动种类
	private String agencyName;//	承办机构
	private String secondaryName;//	所属届次
	private String year;
	private String actStatus; //活动状态
	
	private int invitnumb; // 邀请人员数量
	private int attendnumb;// 参加人员数量
	
	
	public String getAuid() {
		return auid;
	}
	public void setAuid(String auid) {
		this.auid = auid;
	}
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAstatus() {
		return astatus;
	}
	public void setAstatus(String astatus) {
		this.astatus = astatus;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	public String getLeaveReason() {
		return leaveReason;
	}
	public void setLeaveReason(String leaveReason) {
		this.leaveReason = leaveReason;
	}
	public String getAstatusName() {
		return astatusName;
	}
	public void setAstatusName(String astatusName) {
		this.astatusName = astatusName;
	}
	public String getLeaveTypeName() {
		return leaveTypeName;
	}
	public void setLeaveTypeName(String leaveTypeName) {
		this.leaveTypeName = leaveTypeName;
	}
	public String getSexName() {
		return sexName;
	}
	public void setSexName(String sexName) {
		this.sexName = sexName;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public String getAtheme() {
		return atheme;
	}
	public void setAtheme(String atheme) {
		this.atheme = atheme;
	}
	public String getAspecies() {
		return aspecies;
	}
	public void setAspecies(String aspecies) {
		this.aspecies = aspecies;
	}
	public String getAgency() {
		return agency;
	}
	public void setAgency(String agency) {
		this.agency = agency;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getSecondaryId() {
		return secondaryId;
	}
	public void setSecondaryId(String secondaryId) {
		this.secondaryId = secondaryId;
	}
	public Date getTimebeg() {
		return timebeg;
	}
	public void setTimebeg(Date timebeg) {
		this.timebeg = timebeg;
	}
	public Date getTimeend() {
		return timeend;
	}
	public void setTimeend(Date timeend) {
		this.timeend = timeend;
	}
	public String getAspeciesName() {
		return aspeciesName;
	}
	public void setAspeciesName(String aspeciesName) {
		this.aspeciesName = aspeciesName;
	}
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSecondaryName() {
		return secondaryName;
	}
	public void setSecondaryName(String secondaryName) {
		this.secondaryName = secondaryName;
	}
	public String getCommitteeName() {
		return committeeName;
	}
	public void setCommitteeName(String committeeName) {
		this.committeeName = committeeName;
	}
	public String getComparyPhone() {
		return comparyPhone;
	}
	public void setComparyPhone(String comparyPhone) {
		this.comparyPhone = comparyPhone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getComparyAddress() {
		return comparyAddress;
	}
	public void setComparyAddress(String comparyAddress) {
		this.comparyAddress = comparyAddress;
	}
	public String getComparyPost() {
		return comparyPost;
	}
	public void setComparyPost(String comparyPost) {
		this.comparyPost = comparyPost;
	}
	public String getYear() {
		return year;
	}
	public int getInvitnumb() {
		return invitnumb;
	}
	public void setInvitnumb(int invitnumb) {
		this.invitnumb = invitnumb;
	}
	public int getAttendnumb() {
		return attendnumb;
	}
	public void setAttendnumb(int attendnumb) {
		this.attendnumb = attendnumb;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getCommCode() {
		return commCode;
	}
	public void setCommCode(String commCode) {
		this.commCode = commCode;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getNationName() {
		return nationName;
	}
	public void setNationName(String nationName) {
		this.nationName = nationName;
	}
	public String getCircleCode() {
		return circleCode;
	}
	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
	}
	public String getCommittee() {
		return committee;
	}
	public void setCommittee(String committee) {
		this.committee = committee;
	}
	public String getPartyCode() {
		return partyCode;
	}
	public void setPartyCode(String partyCode) {
		this.partyCode = partyCode;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public String getActStatus() {
		return actStatus;
	}
	public void setActStatus(String actStatus) {
		this.actStatus = actStatus;
	}

}
