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

/**
 * 开课(班级-课程) OK
 */
@Entity
@Table(name = "opencourse", catalog = "artcollege")
public class TopenCourse  extends BaseObject implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private Date crtTime; //创建时间
	private Date uptTime; //更新时间
	
	private Tclasses classes;
	private Tcourse course;
	
	public TopenCourse() {
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 16)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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
	@JoinColumn(name = "classesId")
	public Tclasses getClasses() {
		return this.classes;
	}

	public void setClasses(Tclasses classes) {
		this.classes = classes;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "courseId")
	public Tcourse getCourse() {
		return this.course;
	}

	public void setCourse(Tcourse course) {
		this.course = course;
	}
}