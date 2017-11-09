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
 * 校区
 */
@Entity
@Table(name = "campus", catalog = "artcollege")
public class Tcampus extends BaseObject implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;   //校区名
	private String enName; //英文校区名
	private String comment;//备注
	private Date crtTime;  //创建时间
	private Date uptTime;  //更新时间
	
	private Set<TteachingBuilding> teachingBuildings = new HashSet<TteachingBuilding>(0);

	public Tcampus() {
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
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "campus")
	public Set<TteachingBuilding> getTeachingBuildings() {
		return teachingBuildings;
	}

	public void setTeachingBuildings(Set<TteachingBuilding> teachingBuildings) {
		this.teachingBuildings = teachingBuildings;
	}

//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "campus")
//	public Set<Teachingbuilding> getTeachingbuildings() {
//		return this.teachingbuildings;
//	}
//
//	public void setTeachingbuildings(Set<Teachingbuilding> teachingbuildings) {
//		this.teachingbuildings = teachingbuildings;
//	}

}