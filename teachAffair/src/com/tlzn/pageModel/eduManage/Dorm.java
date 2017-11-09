package com.tlzn.pageModel.eduManage;

import java.util.Date;

import com.tlzn.model.base.BaseObject;
/*
 * 宿舍OK
 */
public class Dorm extends BaseObject implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String no;       //宿舍号
	private String addr;     //宿舍地址
	private String adminStu; //舍长编号
	private Integer bedNum;  //床位数
	private Integer sleepNum;//现居住人数
	private Date crtTime;    //创建时间
	private Date uptTime;    //更新时间
	public Dorm() {
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getAdminStu() {
		return adminStu;
	}
	public void setAdminStu(String adminStu) {
		this.adminStu = adminStu;
	}
	public Integer getBedNum() {
		return bedNum;
	}
	public void setBedNum(Integer bedNum) {
		this.bedNum = bedNum;
	}
	public Integer getSleepNum() {
		return sleepNum;
	}
	public void setSleepNum(Integer sleepNum) {
		this.sleepNum = sleepNum;
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
