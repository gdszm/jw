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
 * 教室
 */
@Entity
@Table(name = "classroom", catalog = "artcollege")
public class Tclassroom extends BaseObject implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;

	private String id;

	private String deptNo;          //所在系编号
	private String houseNo;         //门牌号
	private String name;            //教室名称
	private String enName;          //英文教室名称
	private String isCanUse;        //是否可用
	private Integer floor;          //所在楼层
	private Integer seatingNum;     //座位数
	private Integer examSeatingNum; //考试座位数
	private String status;          //教室状态（1:空闲 2:占用 3:停用）
	private String occupyType;      //占用类型（1:排课占用 2:考试占用 3:试验占用 4:个人借用）
	private String comment;			//备注
	private Date crtTime;           //创建时间
	private Date uptTime;           //更新时间
	
	private TclassroomType classroomtype;
	private TteachingBuilding teachingbuilding;
	
	private Set<Tprofess> professes = new HashSet<Tprofess>(0);

	public Tclassroom() {
	}
	
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 16)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "deptNo", length = 16)
	public String getDeptNo() {
		return this.deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	@Column(name = "houseNo", length = 16)
	public String getHouseNo() {
		return this.houseNo;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
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

	@Column(name = "isCanUse", length = 10)
	public String getIsCanUse() {
		return this.isCanUse;
	}

	public void setIsCanUse(String isCanUse) {
		this.isCanUse = isCanUse;
	}

	@Column(name = "floor")
	public Integer getFloor() {
		return this.floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	@Column(name = "seatingNum")
	public Integer getSeatingNum() {
		return this.seatingNum;
	}

	public void setSeatingNum(Integer seatingNum) {
		this.seatingNum = seatingNum;
	}

	@Column(name = "examSeatingNum")
	public Integer getExamSeatingNum() {
		return this.examSeatingNum;
	}

	public void setExamSeatingNum(Integer examSeatingNum) {
		this.examSeatingNum = examSeatingNum;
	}

	@Column(name = "status", length = 10)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "occupyType", length = 10)
	public String getOccupyType() {
		return this.occupyType;
	}

	public void setOccupyType(String occupyType) {
		this.occupyType = occupyType;
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
	@JoinColumn(name = "classroomTypeId")
	public TclassroomType getClassroomtype() {
		return this.classroomtype;
	}

	public void setClassroomtype(TclassroomType classroomtype) {
		this.classroomtype = classroomtype;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teachingBuildingId")
	public TteachingBuilding getTeachingbuilding() {
		return this.teachingbuilding;
	}

	public void setTeachingbuilding(TteachingBuilding teachingbuilding) {
		this.teachingbuilding = teachingbuilding;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "classroom")
	public Set<Tprofess> getProfesses() {
		return this.professes;
	}

	public void setProfesses(Set<Tprofess> professes) {
		this.professes = professes;
	}

}