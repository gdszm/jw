package com.tlzn.model.eduManage;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.tlzn.model.base.BaseObject;
/*
 * 工作经历（社会实践）OK
 */

@Entity
@Table(name = "workexp", catalog = "artcollege")
public class TworkExp extends BaseObject implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private Date beginDate;    //开始日期
	private Date endDate;      //结束日期
	private String workUnit;   //工作单位
	private String workDuty;   //职务
	private String workContent;//内容（文本区）
	private Date crtTime; //创建时间
	private Date uptTime; //更新时间
	
	private Tarchives archives; //档案
	
	public TworkExp() {
	}
	
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "beginDate", length = 10)
	public Date getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "endDate", length = 10)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "workUnit", length = 200)
	public String getWorkUnit() {
		return this.workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}

	@Column(name = "workDuty", length = 100)
	public String getWorkDuty() {
		return this.workDuty;
	}

	public void setWorkDuty(String workDuty) {
		this.workDuty = workDuty;
	}

	@Column(name = "workContent", length = 1000)
	public String getWorkContent() {
		return this.workContent;
	}

	public void setWorkContent(String workContent) {
		this.workContent = workContent;
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
	@JoinColumn(name = "foreignId")
	public Tarchives getArchives() {
		return this.archives;
	}

	public void setArchives(Tarchives archives) {
		this.archives = archives;
	}
}
