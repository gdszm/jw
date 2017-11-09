package com.tlzn.pageModel.eduManage;

import java.util.Date;

import com.tlzn.model.base.BaseObject;
/*
 * 工作经历（社会实践）OK
 */

public class WorkExp extends BaseObject implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private Date beginDate;    //开始日期
	private Date endDate;      //结束日期
	private String workUnit;   //工作单位
	private String workDuty;   //职务
	private String workContent;//内容（文本区）
	private Date crtTime; //创建时间
	private Date uptTime; //更新时间
	
	//非数据库
	private String archNo;     //档案编号
	
	public WorkExp() {
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


	public String getWorkUnit() {
		return workUnit;
	}


	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}


	public String getWorkDuty() {
		return workDuty;
	}


	public void setWorkDuty(String workDuty) {
		this.workDuty = workDuty;
	}


	public String getWorkContent() {
		return workContent;
	}


	public void setWorkContent(String workContent) {
		this.workContent = workContent;
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
	
}
