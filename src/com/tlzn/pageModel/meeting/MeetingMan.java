package com.tlzn.pageModel.meeting;

import java.util.Date;

import com.tlzn.model.base.BaseObject;

public class MeetingMan extends BaseObject implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String status;
	private String statusName;
	private String leaveType;
	private String leaveTypeName;
	private String leaveReason;
	private Date  createTime;
	private String  memo;
	
	private String commCode;
	private String name;//用户姓名
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
	
	private String  meetId;
	private String  meetName;
	private String  shortName;
	private String  meetType;
	private String  meetTypeName;
	private Date  beginTime;
	private Date  endTime;
	private String  address;
	private String  depid;
	private String  depName;
	private Date  pubDate;
	private String  pubDepdid;
	private String  pubDepdName;
	private String  attendStatus;
	private String  attendStatusName;
	
	private String  secondaryId;
	private String  secondaryName;
	private String  secondaryYear;
	
	private String total;
	private String attendance;
	private String meetingStatus;//会议状态
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getCommCode() {
		return commCode;
	}
	public void setCommCode(String commCode) {
		this.commCode = commCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
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
	public String getMeetId() {
		return meetId;
	}
	public void setMeetId(String meetId) {
		this.meetId = meetId;
	}
	public String getMeetName() {
		return meetName;
	}
	public void setMeetName(String meetName) {
		this.meetName = meetName;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getMeetType() {
		return meetType;
	}
	public void setMeetType(String meetType) {
		this.meetType = meetType;
	}
	public String getMeetTypeName() {
		return meetTypeName;
	}
	public void setMeetTypeName(String meetTypeName) {
		this.meetTypeName = meetTypeName;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDepid() {
		return depid;
	}
	public void setDepid(String depid) {
		this.depid = depid;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	public Date getPubDate() {
		return pubDate;
	}
	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}
	public String getPubDepdid() {
		return pubDepdid;
	}
	public void setPubDepdid(String pubDepdid) {
		this.pubDepdid = pubDepdid;
	}
	public String getPubDepdName() {
		return pubDepdName;
	}
	public void setPubDepdName(String pubDepdName) {
		this.pubDepdName = pubDepdName;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getAttendStatus() {
		return attendStatus;
	}
	public void setAttendStatus(String attendStatus) {
		this.attendStatus = attendStatus;
	}
	public String getAttendStatusName() {
		return attendStatusName;
	}
	public void setAttendStatusName(String attendStatusName) {
		this.attendStatusName = attendStatusName;
	}
	public String getSecondaryId() {
		return secondaryId;
	}
	public void setSecondaryId(String secondaryId) {
		this.secondaryId = secondaryId;
	}
	public String getSecondaryName() {
		return secondaryName;
	}
	public void setSecondaryName(String secondaryName) {
		this.secondaryName = secondaryName;
	}
	public String getSecondaryYear() {
		return secondaryYear;
	}
	public void setSecondaryYear(String secondaryYear) {
		this.secondaryYear = secondaryYear;
	}
	public String getLeaveTypeName() {
		return leaveTypeName;
	}
	public void setLeaveTypeName(String leaveTypeName) {
		this.leaveTypeName = leaveTypeName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getAttendance() {
		return attendance;
	}
	public void setAttendance(String attendance) {
		this.attendance = attendance;
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
	public String getMeetingStatus() {
		return meetingStatus;
	}
	public void setMeetingStatus(String meetingStatus) {
		this.meetingStatus = meetingStatus;
	}
	
}
