package com.tlzn.pageModel.eduManage;

import java.util.Date;

import com.tlzn.model.base.BaseObject;

/**
 * 教室类型
 */
public class ClassroomType extends BaseObject implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String typeName;          //教室类型名称
	private String enTypeName;        //英文教室类型说明
	private String classRoomTypeSign; //教室特征码
	private String comment;           //备注
	private Date crtTime;             //创建时间
	private Date uptTime;             //更新时间
	
	//下拉列表
	private String code;
	private String name;
	
	public ClassroomType() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getEnTypeName() {
		return enTypeName;
	}

	public void setEnTypeName(String enTypeName) {
		this.enTypeName = enTypeName;
	}

	public String getClassRoomTypeSign() {
		return classRoomTypeSign;
	}

	public void setClassRoomTypeSign(String classRoomTypeSign) {
		this.classRoomTypeSign = classRoomTypeSign;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}