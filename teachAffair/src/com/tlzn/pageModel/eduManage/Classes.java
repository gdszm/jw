package com.tlzn.pageModel.eduManage;

import java.util.Date;

import com.tlzn.model.base.BaseObject;
/*
 * 班级类OK
 */
public class Classes extends BaseObject implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;

	private String id;
	private String className;   //班级名称
	private String trainLevel; 	//培养层次(1：专科 2：本科 3：研究生)
	private String sysLength; 	//学制(年)
	private Integer buildDate;  //建班年月
	private String adminTeacherId;//班主任编号
	private String adminStuId;  //班长学号
	private String secStuId;    //团支书学号
	private Integer manNum;     //男生人数
	private Integer womanNum;   //女生人数
	private Integer livingNum;  //住校生人数
	private String problem;     //存在问题
	private String proSolve;    //主要措施
	private Date crtTime;		//创建时间
	private Date uptTime; 		//更新时间

	//非数据库
	private String adminTeacherName;//班主任姓名
	private String adminStuName;    //班长姓名
	private String secStuName;      //团支书姓名
	private String trainLevelName;  //培养层次(1：专科 2：本科 3：研究生)

	
	public Classes() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	public Integer getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(Integer buildDate) {
		this.buildDate = buildDate;
	}

	public String getAdminStuId() {
		return adminStuId;
	}

	public void setAdminStuId(String adminStuId) {
		this.adminStuId = adminStuId;
	}

	public String getSecStuId() {
		return secStuId;
	}

	public void setSecStuId(String secStuId) {
		this.secStuId = secStuId;
	}

	public Integer getManNum() {
		return manNum;
	}

	public void setManNum(Integer manNum) {
		this.manNum = manNum;
	}

	public Integer getWomanNum() {
		return womanNum;
	}

	public void setWomanNum(Integer womanNum) {
		this.womanNum = womanNum;
	}

	public Integer getLivingNum() {
		return livingNum;
	}

	public void setLivingNum(Integer livingNum) {
		this.livingNum = livingNum;
	}

	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	public String getProSolve() {
		return proSolve;
	}

	public void setProSolve(String proSolve) {
		this.proSolve = proSolve;
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

	public String getAdminTeacherName() {
		return adminTeacherName;
	}

	public void setAdminTeacherName(String adminTeacherName) {
		this.adminTeacherName = adminTeacherName;
	}

	public String getAdminStuName() {
		return adminStuName;
	}

	public void setAdminStuName(String adminStuName) {
		this.adminStuName = adminStuName;
	}

	public String getSecStuName() {
		return secStuName;
	}

	public void setSecStuName(String secStuName) {
		this.secStuName = secStuName;
	}
	public String getTrainLevel() {
		return trainLevel;
	}

	public void setTrainLevel(String trainLevel) {
		this.trainLevel = trainLevel;
	}

	public String getSysLength() {
		return sysLength;
	}

	public void setSysLength(String sysLength) {
		this.sysLength = sysLength;
	}

	public String getTrainLevelName() {
		return trainLevelName;
	}

	public void setTrainLevelName(String trainLevelName) {
		this.trainLevelName = trainLevelName;
	}

	public String getAdminTeacherId() {
		return adminTeacherId;
	}

	public void setAdminTeacherId(String adminTeacherId) {
		this.adminTeacherId = adminTeacherId;
	}
	
}
