package com.tlzn.pageModel.dailywork;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tlzn.model.base.BaseObject;

/**
 * 通知公告实体类
 * 
 * @author gds
 * @Time 2016-10-11 08:45:00
 * 
 */

public class Notice extends BaseObject implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String id; // 通知ID
	private String title; // 通知标题
	private String content; // 通知内容
	private String pubUnit; // 发布单位
	private String reply; // 是否回复
	private Date creatDate; // 创建日期
	private Date pubDate; // 发布日期
	private Date validDate; // 有效日期
	private String status; // 状态（0：未发布，1：已发布）
	private String noticeType; // 通知类型
	private String atts; // 附件(多)
	private String memo; // 备注

//	private String year; // 年度
//	private String secondaryId; // 届次
//	private String secondayName; // 届次名称

	private String replyName; // 是否回复
	private String statusName; // 状态名称 （0：未发布，1：已发布）
	private String noticeTypeName; // 通知类型名称
	private Date creatDateStart; // 创建日期 开始
	private Date creatDateEnd; // 创建日期 结束
	private Date pubDateStart; // 发布日期 开始
	private Date pubDateEnd; // 发布日期 结束
	private String pubUnitName; // 发布单位名称

	public Notice() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPubUnit() {
		return pubUnit;
	}

	public void setPubUnit(String pubUnit) {
		this.pubUnit = pubUnit;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public Date getValidDate() {
		return validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAtts() {
		return atts;
	}

	public void setAtts(String atts) {
		this.atts = atts;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Date getCreatDate() {
		return creatDate;
	}

	public void setCreatDate(Date creatDate) {
		this.creatDate = creatDate;
	}

	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getNoticeTypeName() {
		return noticeTypeName;
	}

	public void setNoticeTypeName(String noticeTypeName) {
		this.noticeTypeName = noticeTypeName;
	}

	public String getReplyName() {
		return replyName;
	}

	public void setReplyName(String replyName) {
		this.replyName = replyName;
	}

//	public String getSecondaryId() {
//		return secondaryId;
//	}
//
//	public void setSecondaryId(String secondaryId) {
//		this.secondaryId = secondaryId;
//	}
//
//	public String getSecondayName() {
//		return secondayName;
//	}
//
//	public void setSecondayName(String secondayName) {
//		this.secondayName = secondayName;
//	}

	public Date getCreatDateStart() {
		return creatDateStart;
	}

	public void setCreatDateStart(Date creatDateStart) {
		this.creatDateStart = creatDateStart;
	}

	public Date getCreatDateEnd() {
		return creatDateEnd;
	}

	public void setCreatDateEnd(Date creatDateEnd) {
		this.creatDateEnd = creatDateEnd;
	}

	public Date getPubDateStart() {
		return pubDateStart;
	}

	public void setPubDateStart(Date pubDateStart) {
		this.pubDateStart = pubDateStart;
	}

	public Date getPubDateEnd() {
		return pubDateEnd;
	}

	public void setPubDateEnd(Date pubDateEnd) {
		this.pubDateEnd = pubDateEnd;
	}
//	public String getYear() {
//		return year;
//	}
//
//	public void setYear(String year) {
//		this.year = year;
//	}

	public String getPubUnitName() {
		return pubUnitName;
	}

	public void setPubUnitName(String pubUnitName) {
		this.pubUnitName = pubUnitName;
	}
}
