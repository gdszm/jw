package com.tlzn.pageModel.eduManage;

import java.util.Date;

import com.tlzn.model.base.BaseObject;

/**
 * 住宿表OK
 */
public class Bed  extends BaseObject implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String dormBedNo;   //所在床位号
	private Date dormIn;      //入住时间
	private Date dormOut;     //搬出时间
	private Date crtTime; //创建时间
	private Date uptTime; //更新时间

	public Bed() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDormBedNo() {
		return dormBedNo;
	}

	public void setDormBedNo(String dormBedNo) {
		this.dormBedNo = dormBedNo;
	}

	public Date getDormIn() {
		return dormIn;
	}

	public void setDormIn(Date dormIn) {
		this.dormIn = dormIn;
	}

	public Date getDormOut() {
		return dormOut;
	}

	public void setDormOut(Date dormOut) {
		this.dormOut = dormOut;
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