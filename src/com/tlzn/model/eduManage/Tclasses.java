package com.tlzn.model.eduManage;

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


import com.tlzn.model.base.BaseObject;
/*
 * 班级类OK
 */
@Entity
@Table(name = "classes", catalog = "artcollege")
public class Tclasses extends BaseObject implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;

	private String id;
	private String className;   //班级名称
	private String trainLevel; 	//培养层次(1：中专 2：大专 3：本科 4：研究生)
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
	private Date crtTime;       //创建时间
	private Date uptTime;       //更新时间
	
	private Set<Tstu> stus = new HashSet<Tstu>(0);
	private Set<TclassManage> classManages = new HashSet<TclassManage>(0);
	private Set<TopenCourse> openCourses = new HashSet<TopenCourse>(0);
	public Tclasses() {
	}
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 16)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "className", length = 60)
	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	@Column(name = "buildDate")
	public Integer getBuildDate() {
		return this.buildDate;
	}

	public void setBuildDate(Integer buildDate) {
		this.buildDate = buildDate;
	}
	@Column(name = "adminStuId", length = 16)
	public String getAdminStuId() {
		return this.adminStuId;
	}

	public void setAdminStuId(String adminStuId) {
		this.adminStuId = adminStuId;
	}

	@Column(name = "secStuId", length = 16)
	public String getSecStuId() {
		return this.secStuId;
	}

	public void setSecStuId(String secStuId) {
		this.secStuId = secStuId;
	}

	@Column(name = "manNum")
	public Integer getManNum() {
		return this.manNum;
	}

	public void setManNum(Integer manNum) {
		this.manNum = manNum;
	}

	@Column(name = "womanNum")
	public Integer getWomanNum() {
		return this.womanNum;
	}

	public void setWomanNum(Integer womanNum) {
		this.womanNum = womanNum;
	}

	@Column(name = "livingNum")
	public Integer getLivingNum() {
		return this.livingNum;
	}

	public void setLivingNum(Integer livingNum) {
		this.livingNum = livingNum;
	}

	@Column(name = "problem", length = 1000)
	public String getProblem() {
		return this.problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	@Column(name = "proSolve", length = 1000)
	public String getProSolve() {
		return this.proSolve;
	}

	public void setProSolve(String proSolve) {
		this.proSolve = proSolve;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "crtTime")
	public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "uptTime")
	public Date getUptTime() {
		return uptTime;
	}
	
	public void setUptTime(Date uptTime) {
		this.uptTime = uptTime;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "classes")
	public Set<Tstu> getStus() {
		return this.stus;
	}
	
	public void setStus(Set<Tstu> stus) {
		this.stus = stus;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "classes")
	public Set<TclassManage> getClassManages() {
		return this.classManages;
	}

	public void setClassManages(Set<TclassManage> classManages) {
		this.classManages = classManages;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "classes")
	public Set<TopenCourse> getOpenCourses() {
		return this.openCourses;
	}

	public void setOpenCourses(Set<TopenCourse> openCourses) {
		this.openCourses = openCourses;
	}
	@Column(name = "trainLevel", length = 20)
	public String getTrainLevel() {
		return trainLevel;
	}
	public void setTrainLevel(String trainLevel) {
		this.trainLevel = trainLevel;
	}
	@Column(name = "sysLength", length = 10)
	public String getSysLength() {
		return sysLength;
	}
	public void setSysLength(String sysLength) {
		this.sysLength = sysLength;
	}
	@Column(name = "adminTeacherId", length = 16)
	public String getAdminTeacherId() {
		return adminTeacherId;
	}
	public void setAdminTeacherId(String adminTeacherId) {
		this.adminTeacherId = adminTeacherId;
	}

}
