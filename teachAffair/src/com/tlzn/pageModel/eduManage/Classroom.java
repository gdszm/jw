package com.tlzn.pageModel.eduManage;

import java.util.Date;

import com.tlzn.model.base.BaseObject;

/**
 * 教室
 */
public class Classroom extends BaseObject implements java.io.Serializable{
	
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
	

	
//	private TclassroomType classroomtype;
//	private TteachingBuilding teachingbuilding;
	
	//非数据库
	private String typeId;         //教室类型ID
	private String typeName;       //教室类型名称
	private String deptName;       //所在系名称
	private String buildingName;   //教学楼名称
	private String campusName;     //校区名称
	private String isCanUseName;   //是否可用名称
	private String statusName;     //教室状态名称（1:空闲 2:占用 3:停用）
	private String occupyTypeName; //占用类型名称（1:排课占用 2:考试占用 3:试验占用 4:个人借用）

	
//	private Set<Tprofess> professes = new HashSet<Tprofess>(0);

	
	
	
	public Classroom() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public String getHouseNo() {
		return houseNo;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getIsCanUse() {
		return isCanUse;
	}

	public void setIsCanUse(String isCanUse) {
		this.isCanUse = isCanUse;
	}

	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public Integer getSeatingNum() {
		return seatingNum;
	}

	public void setSeatingNum(Integer seatingNum) {
		this.seatingNum = seatingNum;
	}

	public Integer getExamSeatingNum() {
		return examSeatingNum;
	}

	public void setExamSeatingNum(Integer examSeatingNum) {
		this.examSeatingNum = examSeatingNum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOccupyType() {
		return occupyType;
	}

	public void setOccupyType(String occupyType) {
		this.occupyType = occupyType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getIsCanUseName() {
		return isCanUseName;
	}

	public void setIsCanUseName(String isCanUseName) {
		this.isCanUseName = isCanUseName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getOccupyTypeName() {
		return occupyTypeName;
	}

	public void setOccupyTypeName(String occupyTypeName) {
		this.occupyTypeName = occupyTypeName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	
	public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

	public Date getUptTime() {
		return uptTime;
	}

	public void setUptTime(Date uptTime) {
		this.uptTime = uptTime;
	}

	public String getCampusName() {
		return campusName;
	}

	public void setCampusName(String campusName) {
		this.campusName = campusName;
	}
}