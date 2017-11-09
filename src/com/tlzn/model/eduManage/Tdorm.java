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
 * 宿舍OK
 */
@Entity
@Table(name = "dorm", catalog = "artcollege")
public class Tdorm extends BaseObject implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String no;//宿舍号
	private String addr;//宿舍地址
	private String adminStu;//舍长编号
	private Integer bedNum;//床位数
	private Integer sleepNum;//现居住人数
	private Date crtTime; //创建时间
	private Date uptTime; //更新时间
	
	private Set<Tbed> beds = new HashSet<Tbed>(0);

	public Tdorm() {
	}
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 16)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "no", length = 16)
	public String getNo() {
		return this.no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	@Column(name = "addr", length = 45)
	public String getAddr() {
		return this.addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Column(name = "adminStu", length = 16)
	public String getAdminStu() {
		return this.adminStu;
	}

	public void setAdminStu(String adminStu) {
		this.adminStu = adminStu;
	}

	@Column(name = "bedNum")
	public Integer getBedNum() {
		return this.bedNum;
	}

	public void setBedNum(Integer bedNum) {
		this.bedNum = bedNum;
	}

	@Column(name = "sleepNum")
	public Integer getSleepNum() {
		return this.sleepNum;
	}

	public void setSleepNum(Integer sleepNum) {
		this.sleepNum = sleepNum;
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
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "dorm")
	public Set<Tbed> getBeds() {
		return this.beds;
	}

	public void setBeds(Set<Tbed> beds) {
		this.beds = beds;
	}
}
