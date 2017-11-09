package com.tlzn.model.eduManage;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.tlzn.model.base.BaseObject;

/**
 * 住宿表OK
 */
@Entity
@Table(name = "bed", catalog = "artcollege", 
uniqueConstraints = @UniqueConstraint(columnNames = "personId"))
public class Tbed  extends BaseObject implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String dormBedNo;   //所在床位号
	private Date dormIn;      //入住时间
	private Date dormOut;     //搬出时间
	private Date crtTime; //创建时间
	private Date uptTime; //更新时间
	
	private Tdorm dorm;//宿舍
	private Tstu stu;  //人员

	public Tbed() {
	}
	
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 16)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "dormBedNo", length = 16)
	public String getDormBedNo() {
		return this.dormBedNo;
	}

	public void setDormBedNo(String dormBedNo) {
		this.dormBedNo = dormBedNo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "dormIn", length = 10)
	public Date getDormIn() {
		return this.dormIn;
	}

	public void setDormIn(Date dormIn) {
		this.dormIn = dormIn;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "dormOut", length = 10)
	public Date getDormOut() {
		return this.dormOut;
	}

	public void setDormOut(Date dormOut) {
		this.dormOut = dormOut;
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
	@JoinColumn(name = "dormId")
	public Tdorm getDorm() {
		return this.dorm;
	}

	public void setDorm(Tdorm dorm) {
		this.dorm = dorm;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="personId")
	public Tstu getStu() {
		return this.stu;
	}

	public void setStu(Tstu stu) {
		this.stu = stu;
	}
}