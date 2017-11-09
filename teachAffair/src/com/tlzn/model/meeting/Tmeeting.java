package com.tlzn.model.meeting;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tlzn.model.sys.Tsecondary;


@Entity
@Table(name = "TMEETING")
public class Tmeeting   implements java.io.Serializable{


   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String  meetId;
	private String  meetName;
	private String  shortName;
	private String  meetType;
	private String  content;
	private Date  beginTime;
	private Date  endTime;
	private String  address;
	private String  depid;
	private Date  createTime;
	private Date  pubDate;
	private String  pubDepdid;
	private String  status;
	private String  attendStatus;
	private Tsecondary  tsecondary;
	private String  memo;
	  
	public Tmeeting() {
	}
	
	public Tmeeting(String meetId) {
		this.meetId = meetId;
	}

	public Tmeeting(String meetId, String meetName, String shortName,
			String meetType, String content, Date beginTime, Date endTime,
			String address, String depid, Date createTime, Date pubDate,
			String pubDepdid, String status, String attendStatus,
			Tsecondary tsecondary, String memo) {
		this.meetId = meetId;
		this.meetName = meetName;
		this.shortName = shortName;
		this.meetType = meetType;
		this.content = content;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.address = address;
		this.depid = depid;
		this.createTime = createTime;
		this.pubDate = pubDate;
		this.pubDepdid = pubDepdid;
		this.status = status;
		this.attendStatus = attendStatus;
		this.tsecondary = tsecondary;
		this.memo = memo;
	}

	@Id
	@Column(name = "MEET_ID", unique = true, nullable = false, length = 20)
	public String getMeetId() {
		return meetId;
	}
	public void setMeetId(String meetId) {
		this.meetId = meetId;
	}
	@Column(name = "MEET_NAME",length = 500)
	public String getMeetName() {
		return meetName;
	}
	public void setMeetName(String meetName) {
		this.meetName = meetName;
	}
	@Column(name = "SHORT_NAME",length = 100)
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	@Column(name = "MEET_TYPE",length = 4)
	public String getMeetType() {
		return meetType;
	}
	public void setMeetType(String meetType) {
		this.meetType = meetType;
	}
	@Column(name = "CONTENT")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "BEGIN_TIME", length = 7)
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "END_TIME", length = 7)
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	@Column(name = "ADDRESS",length = 500)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(name = "DEPID",length = 36)
	public String getDepid() {
		return depid;
	}
	public void setDepid(String depid) {
		this.depid = depid;
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "PUB_DATE")
	public Date getPubDate() {
		return pubDate;
	}
	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}
	@Column(name = "PUB_DEPDID",length = 36)
	public String getPubDepdid() {
		return pubDepdid;
	}
	public void setPubDepdid(String pubDepdid) {
		this.pubDepdid = pubDepdid;
	}
	@Column(name = "STATUS",length = 4)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name = "ATTEND_STATUS",length = 4)
	public String getAttendStatus() {
		return attendStatus;
	}
	public void setAttendStatus(String attendStatus) {
		this.attendStatus = attendStatus;
	}
	@ManyToOne(targetEntity=Tsecondary.class)//fetch=FetchType.LAZY
	@JoinColumn(name = "SECONDARY_ID", nullable = false)
	public Tsecondary getTsecondary() {
		return tsecondary;
	}
	public void setTsecondary(Tsecondary tsecondary) {
		this.tsecondary = tsecondary;
	}
	@Column(name = "MEMO",length = 2000)
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	  
	  
}
