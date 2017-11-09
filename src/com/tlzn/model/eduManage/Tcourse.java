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
 * 课程OK
 */
@Entity
@Table(name = "course", catalog = "artcollege")
public class Tcourse extends BaseObject implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;       //课程名称
	private String enName;     //课程名称(英文)
	private String sortNo;     //课程序号
	private String credit;     //课程学分
	private String courseAtt;  //课程属性（1:公共必修 2: 公共选修 3:基础必修 4:基础选修 5:专业必修 6:专业选修  7:实验  8:实习 ）
	//课程类别	
	private String teachMeth;  //教学方法（文本区）
	private String teachBook;  //使用教材（文本区）
	private String asMethAnReq;//考核方式及其要求（文本区）
	private String comment;    //备注（文本区）
	private Date crtTime; //创建时间
	private Date uptTime; //更新时间
	
	private Set<TselCourse> selCourses = new HashSet<TselCourse>(0);
	private Set<Tprofess> professes = new HashSet<Tprofess>(0);
	private Set<Texam> exams = new HashSet<Texam>(0);
	private Set<TopenCourse> openCourses = new HashSet<TopenCourse>(0);
	
	public Tcourse() {
		
	}
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 16)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "name", length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "enName", length = 45)
	public String getEnName() {
		return enName;
	}
	
	public void setEnName(String enName) {
		this.enName = enName;
	}
	@Column(name = "sortNo", length = 16)
	public String getSortNo() {
		return sortNo;
	}
	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}
	@Column(name = "credit", length = 45)
	public String getCredit() {
		return this.credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	@Column(name = "courseAtt", length = 16)
	public String getCourseAtt() {
		return this.courseAtt;
	}

	public void setCourseAtt(String courseAtt) {
		this.courseAtt = courseAtt;
	}

	@Column(name = "teachMeth", length = 300)
	public String getTeachMeth() {
		return this.teachMeth;
	}

	public void setTeachMeth(String teachMeth) {
		this.teachMeth = teachMeth;
	}

	@Column(name = "teachBook", length = 400)
	public String getTeachBook() {
		return this.teachBook;
	}

	public void setTeachBook(String teachBook) {
		this.teachBook = teachBook;
	}

	@Column(name = "AsMethAnReq", length = 3000)
	public String getAsMethAnReq() {
		return this.asMethAnReq;
	}

	public void setAsMethAnReq(String asMethAnReq) {
		this.asMethAnReq = asMethAnReq;
	}

	@Column(name = "comment", length = 4000)
	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "course")
	public Set<TselCourse> getSelCourses() {
		return this.selCourses;
	}

	public void setSelCourses(Set<TselCourse> selCourses) {
		this.selCourses = selCourses;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "course")
	public Set<Tprofess> getProfesses() {
		return this.professes;
	}

	public void setProfesses(Set<Tprofess> professes) {
		this.professes = professes;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "course")
	public Set<TopenCourse> getOpenCourses() {
		return this.openCourses;
	}

	public void setOpenCourses(Set<TopenCourse> openCourses) {
		this.openCourses = openCourses;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "course")
	public Set<Texam> getExams() {
		return this.exams;
	}

	public void setExams(Set<Texam> exams) {
		this.exams = exams;
	}
}
