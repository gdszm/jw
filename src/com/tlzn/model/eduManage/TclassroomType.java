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

/**
 * 教室类型
 */
@Entity
@Table(name = "classroomtype", catalog = "artcollege")
public class TclassroomType extends BaseObject implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String typeName;          //教室类型名称
	private String enTypeName;        //英文教室类型说明
	private String classRoomTypeSign; //教室特征码
	private String comment;           //备注
	private Date crtTime;             //创建时间
	private Date uptTime;             //更新时间
	
	private Set<Tclassroom> classrooms = new HashSet<Tclassroom>(0);

	public TclassroomType() {
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 16)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "typeName", length = 100)
	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Column(name = "enTypeName", length = 100)
	public String getEnTypeName() {
		return this.enTypeName;
	}

	public void setEnTypeName(String enTypeName) {
		this.enTypeName = enTypeName;
	}

	@Column(name = "classRoomTypeSign", length = 100)
	public String getClassRoomTypeSign() {
		return this.classRoomTypeSign;
	}

	public void setClassRoomTypeSign(String classRoomTypeSign) {
		this.classRoomTypeSign = classRoomTypeSign;
	}

	@Column(name = "comment", length = 2000)
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
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "classroomtype")
	public Set<Tclassroom> getClassrooms() {
		return this.classrooms;
	}

	public void setClassrooms(Set<Tclassroom> classrooms) {
		this.classrooms = classrooms;
	}

}