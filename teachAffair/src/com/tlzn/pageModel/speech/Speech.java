package com.tlzn.pageModel.speech;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.tlzn.model.Tcommitteeman;
import com.tlzn.model.base.BaseObject;
import com.tlzn.model.meeting.Tmeeting;

/**
 * 会议发言页面类
 * 
 * @author gds
 * @Time 2016-10-11 08:45:00
 * 
 */

public class Speech extends BaseObject implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	//数据库
	private String speakId;      //发言编码
	private String title;        //发言主题
	private String content;      //正文内容
	private String reportFlg;    //是否上报
	private Date createTime;     //提交日期
	private String atts;         //附件
	private String attsDepict;   //附件描述
	private String useSituation; //采用情况
	private String status;       //状态
	private String opinion;      //审核意见
	private Date auditTime;      //审核日期
	private String discardType;  //转弃方向
	private String memo;         //备注
	//子类
	private String code;           //发言人ID
	private String name;           //发言人
	private String telephone;      //手 机
	private String comparyAddress; //通讯地址
	private String email;          //电子邮箱
	private String  meetId;        //所属会议
	private String  meetName;      //所属会议名称
	private String  meetType;        //所属会议类型
	//其他
	private Date createTimeStart;    //提交日期开始
	private Date createTimeEnd;      //提交日期结束
	private String useSituationName; //采用情况名称
	private String discardTypeName;  //转弃方向名称
	private String statusName;       //状态名称
	private String secondaryId;     //届次ID
	private String secondayName;     //届次名称
	private String year;
	private String meetTypeName;    //所属会议类型名称
//	private Tmeeting tmeeting;
//	private Tcommitteeman tcommitteeman;
	
	public Speech() {
	}

	public String getSpeakId() {
		return speakId;
	}

	public void setSpeakId(String speakId) {
		this.speakId = speakId;
	}

	public String getContent() {
		return content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReportFlg() {
		return reportFlg;
	}

	public void setReportFlg(String reportFlg) {
		this.reportFlg = reportFlg;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getAtts() {
		return atts;
	}

	public void setAtts(String atts) {
		this.atts = atts;
	}

	public String getAttsDepict() {
		return attsDepict;
	}

	public void setAttsDepict(String attsDepict) {
		this.attsDepict = attsDepict;
	}

	public String getUseSituation() {
		return useSituation;
	}

	public void setUseSituation(String useSituation) {
		this.useSituation = useSituation;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getDiscardType() {
		return discardType;
	}

	public void setDiscardType(String discardType) {
		this.discardType = discardType;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getName() {
		return name;
	}

	public String getUseSituationName() {
		return useSituationName;
	}

	public void setUseSituationName(String useSituationName) {
		this.useSituationName = useSituationName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getComparyAddress() {
		return comparyAddress;
	}

	public void setComparyAddress(String comparyAddress) {
		this.comparyAddress = comparyAddress;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMeetId() {
		return meetId;
	}

	public void setMeetId(String meetId) {
		this.meetId = meetId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMeetName() {
		return meetName;
	}

	public void setMeetName(String meetName) {
		this.meetName = meetName;
	}

	public Date getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getSecondaryId() {
		return secondaryId;
	}

	public void setSecondaryId(String secondaryId) {
		this.secondaryId = secondaryId;
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

	public String getSecondayName() {
		return secondayName;
	}

	public void setSecondayName(String secondayName) {
		this.secondayName = secondayName;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getDiscardTypeName() {
		return discardTypeName;
	}

	public void setDiscardTypeName(String discardTypeName) {
		this.discardTypeName = discardTypeName;
	}

}
