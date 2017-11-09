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
 * 选课(学生-课程) OK
 */
@Entity
@Table(name = "selcourse", catalog = "artcollege")
public class TselCourse extends BaseObject implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private Date selDate;       //选课时间
	private String isReStudySel;//是否自学重新学习选课
	private String isTrapSel;   //是否补漏课选课
	
//	成绩
//	学分绩点
	
	private Date crtTime; //创建时间
	private Date uptTime; //更新时间
	
	private Tcourse course;
	private Tstu stu;
	
	public TselCourse() {
	}
	
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 16)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "selDate", length = 10)
	public Date getSelDate() {
		return this.selDate;
	}

	public void setSelDate(Date selDate) {
		this.selDate = selDate;
	}

	@Column(name = "isReStudySel", length = 10)
	public String getIsReStudySel() {
		return this.isReStudySel;
	}

	public void setIsReStudySel(String isReStudySel) {
		this.isReStudySel = isReStudySel;
	}

	@Column(name = "isTrapSel", length = 10)
	public String getIsTrapSel() {
		return this.isTrapSel;
	}

	public void setIsTrapSel(String isTrapSel) {
		this.isTrapSel = isTrapSel;
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
	@JoinColumn(name = "courseId")
	public Tcourse getCourse() {
		return this.course;
	}

	public void setCourse(Tcourse course) {
		this.course = course;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stuNo")
	public Tstu getStu() {
		return this.stu;
	}

	public void setStu(Tstu stu) {
		this.stu = stu;
	}
}
