package com.tlzn.model.eduManage;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.tlzn.model.base.BaseObject;
/*
 * 学生类Ok
 */
@Entity
@Table(name = "stu", catalog = "artcollege", 
uniqueConstraints = @UniqueConstraint(columnNames = "archNo"))
public class Tstu extends BaseObject implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String stuNo;       //学号
	private String dept;		//院、系、部	
	private String major;		//专业
	private Date inTime;        //入学时间
	private String enSour;      //入学来源 （1：城镇 2:农村) 
	private String priPro;      //主要问题（ 1） 学习困难（ 2） 行为有偏差（  3） 心理有障碍（ 4  ）  其他（单亲家庭 父、母）（  5） 重组家庭（随父、随母）（6） 挂靠、托靠
	private String planAfterGrad;//毕业以后的打算（考研、出国、入伍、公务员、工作<国、私、外>、其他）
	private String remark;      //备注
	private Date crtTime; //创建时间
	private Date uptTime; //更新时间
	private String crtUsr; //创建用户
	private String uptUsr; //更新用户
	
	private Tarchives archives;  //学生档案
	private Tclasses classes;    //所在班级
	
	private Tbed bed;			
	private Set<TselCourse> selCourses = new HashSet<TselCourse>(0);
	private Set<TjoinExam> joinExams = new HashSet<TjoinExam>(0);
	
	public Tstu() {
	}
	
	@Id
	@Column(name = "stuNo", unique = true, nullable = false, length = 16)
	public String getStuNo() {
		return this.stuNo;
	}

	public void setStuNo(String stuNo) {
		this.stuNo = stuNo;
	}

	@Column(name = "dept", length = 45)
	public String getDept() {
		return this.dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	@Column(name = "major", length = 45)
	public String getMajor() {
		return this.major;
	}

	public void setMajor(String major) {
		this.major = major;
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "inTime")
	public Date getInTime() {
		return inTime;
	}

	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}

	@Column(name = "enSour", length = 10)
	public String getEnSour() {
		return this.enSour;
	}

	public void setEnSour(String enSour) {
		this.enSour = enSour;
	}

	@Column(name = "priPro", length = 45)
	public String getPriPro() {
		return this.priPro;
	}

	public void setPriPro(String priPro) {
		this.priPro = priPro;
	}

	@Column(name = "planAfterGrad", length = 45)
	public String getPlanAfterGrad() {
		return this.planAfterGrad;
	}

	public void setPlanAfterGrad(String planAfterGrad) {
		this.planAfterGrad = planAfterGrad;
	}

	@Column(name = "remark", length = 1000)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
	
	@Column(name = "crtUsr", length = 100)
	public String getCrtUsr() {
		return crtUsr;
	}

	public void setCrtUsr(String crtUsr) {
		this.crtUsr = crtUsr;
	}

	@Column(name = "uptUsr", length = 100)
	public String getUptUsr() {
		return uptUsr;
	}

	public void setUptUsr(String uptUsr) {
		this.uptUsr = uptUsr;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="archNo")
	public Tarchives getArchives() {
		return this.archives;
	}

	public void setArchives(Tarchives archives) {
		this.archives = archives;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "classesId")
	public Tclasses getClasses() {
		return this.classes;
	}

	public void setClasses(Tclasses classes) {
		this.classes = classes;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "stu")
	public Tbed getBed() {
		return this.bed;
	}

	public void setBed(Tbed bed) {
		this.bed = bed;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "stu")
	public Set<TselCourse> getSelCourses() {
		return this.selCourses;
	}

	public void setSelCourses(Set<TselCourse> selCourses) {
		this.selCourses = selCourses;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "stu")
	public Set<TjoinExam> getJoinExams() {
		return this.joinExams;
	}

	public void setJoinExams(Set<TjoinExam> joinExams) {
		this.joinExams = joinExams;
	}
}
