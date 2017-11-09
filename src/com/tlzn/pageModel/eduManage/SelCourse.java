package com.tlzn.pageModel.eduManage;

import java.util.Date;

import com.tlzn.model.base.BaseObject;
/*
 * 选课(学生-课程) OK
 */
public class SelCourse extends BaseObject implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private Date selDate;       //选课时间
	private String isReStudySel;//是否自学重新学习选课
	private String isTrapSel;   //是否补漏课选课
	private Date crtTime; //创建时间
	private Date uptTime; //更新时间
	
	public SelCourse() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getSelDate() {
		return selDate;
	}

	public void setSelDate(Date selDate) {
		this.selDate = selDate;
	}

	public String getIsReStudySel() {
		return isReStudySel;
	}

	public void setIsReStudySel(String isReStudySel) {
		this.isReStudySel = isReStudySel;
	}

	public String getIsTrapSel() {
		return isTrapSel;
	}

	public void setIsTrapSel(String isTrapSel) {
		this.isTrapSel = isTrapSel;
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
