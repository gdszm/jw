package com.tlzn.model.eduManage;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import com.tlzn.model.base.BaseObject;
/*
 * 授课（教师-课程）OK
 */
@Entity
@Table(name = "profess", catalog = "artcollege")
public class Tprofess extends BaseObject implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	private String id;
//	private Date beginDate; //开始任课时间
//	private Date endDate;   //结束任课时间
	
	//选课情况
	private Integer courseCon;     //课容量
	private Integer courseSelNum;  //选课人数
	private Integer courseSpareNum;//课余量
	
	private Date crtTime; //创建时间
	private Date uptTime; //更新时间
	
	//教师
	private Tteacher teacher;
	//课程
	private Tcourse course;
	//上课教室
	private Tclassroom classroom;
	//授课时间
	private TprofessTime professtime;
	
	public Tprofess() {
	}
	
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 16)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

//	@Temporal(TemporalType.DATE)
//	@Column(name = "beginDate", length = 10)
//	public Date getBeginDate() {
//		return this.beginDate;
//	}
//
//	public void setBeginDate(Date beginDate) {
//		this.beginDate = beginDate;
//	}
//
//	@Temporal(TemporalType.DATE)
//	@Column(name = "endDate", length = 10)
//	public Date getEndDate() {
//		return this.endDate;
//	}
//
//	public void setEndDate(Date endDate) {
//		this.endDate = endDate;
//	}
	
	@Column(name = "courseCon")
	public Integer getCourseCon() {
		return this.courseCon;
	}

	public void setCourseCon(Integer courseCon) {
		this.courseCon = courseCon;
	}

	@Column(name = "courseSelNum")
	public Integer getCourseSelNum() {
		return this.courseSelNum;
	}

	public void setCourseSelNum(Integer courseSelNum) {
		this.courseSelNum = courseSelNum;
	}

	@Column(name = "courseSpareNum")
	public Integer getCourseSpareNum() {
		return this.courseSpareNum;
	}

	public void setCourseSpareNum(Integer courseSpareNum) {
		this.courseSpareNum = courseSpareNum;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teacherId")
	public Tteacher getTeacher() {
		return this.teacher;
	}

	public void setTeacher(Tteacher teacher) {
		this.teacher = teacher;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "courseId")
	public Tcourse getCourse() {
		return this.course;
	}

	public void setCourse(Tcourse course) {
		this.course = course;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "professTimeId")
	public TprofessTime getProfesstime() {
		return this.professtime;
	}

	public void setProfesstime(TprofessTime professtime) {
		this.professtime = professtime;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "classroomId")
	public Tclassroom getClassroom() {
		return this.classroom;
	}

	public void setClassroom(Tclassroom classroom) {
		this.classroom = classroom;
	}
}
