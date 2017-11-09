package com.tlzn.pageModel.eduManage;

import java.util.Date;

import com.tlzn.model.base.BaseObject;

/**
 * 开课(班级-课程) OK
 */
public class OpenCourse  extends BaseObject implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private Date crtTime; //创建时间
	private Date uptTime; //更新时间
	
	public OpenCourse() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
}