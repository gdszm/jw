package com.tlzn.pageModel.meeting;


import java.util.Date;

import com.tlzn.model.base.BaseObject;

public class Meeting extends BaseObject implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String  meetId;
	private String  meetName;
	private String  shortName;
	private String  meetType;
	private String  meetTypeName;
	private String  content;
	private Date  beginTime;
	private Date  endTime;
	private String  address;
	private String  depid;
	private String  depName;
	private Date  createTime;
	private Date  pubDate;
	private String  pubDepdid;
	private String  pubDepdName;
	private String  status;
	private String  statusName;
	private String  attendStatus;
	private String  attendStatusName;
	private String  secondaryId;
	private String  secondaryName;
	private String secondaryYear;
	private String  memo;
	private String total;
	private String attendance;
	private String userGroup;
	private String code;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getSecondaryYear() {
		return secondaryYear;
	}
	public void setSecondaryYear(String secondaryYear) {
		this.secondaryYear = secondaryYear;
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
	public String getUserGroup() {
		return userGroup;
	}
	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}
	
}
