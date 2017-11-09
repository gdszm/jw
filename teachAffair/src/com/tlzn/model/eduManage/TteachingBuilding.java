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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tlzn.model.base.BaseObject;

/**
 * 教学楼
 */
@Entity
@Table(name = "teachingbuilding", catalog = "artcollege")
public class TteachingBuilding extends BaseObject implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;     
	private String name;   //教学楼名称
	private String enName; //英文教学楼名称
	private String longi;  //经度
	private String lat;    //纬度
	private String comment;//备注
	private Date crtTime;  //创建时间
	private Date uptTime;  //更新时间
	
	private Tcampus campus; //所属校区
	
	private Set<Tclassroom> classrooms = new HashSet<Tclassroom>(0);

	public TteachingBuilding() {
	}
	
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 16)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "name", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "enName", length = 100)
	public String getEnName() {
		return this.enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}
	
	@Column(name = "long", length = 100)
	public String getLongi() {
		return longi;
	}

	public void setLongi(String longi) {
		this.longi = longi;
	}

	@Column(name = "lat", length = 100)
	public String getLat() {
		return this.lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	@Column(name = "comment", length = 3000)
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "campusId")
	public Tcampus getCampus() {
		return this.campus;
	}

	public void setCampus(Tcampus campus) {
		this.campus = campus;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "teachingbuilding")
	public Set<Tclassroom> getClassrooms() {
		return this.classrooms;
	}

	public void setClassrooms(Set<Tclassroom> classrooms) {
		this.classrooms = classrooms;
	}

}