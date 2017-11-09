package com.tlzn.model.activity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tlzn.model.base.BaseObject;

@Entity
@Table(name = "TACTIVITY", schema = "")
public class Tactivity extends BaseObject implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String aid;//AID 编码
	private String atheme;//ATHEME	活动主题
	private String aspecies;//ASPECIES	活动种类
	private String acontent;//ACONTENT	活动内容
	private String agency;//AGENCY	承办机构
	private String place;//PLACE	地点
	private String status;//STATUS	状态
	private String releaseage;//RELEASEAGE	发布单位
	private String memo;//MEMO	备注
	private String secondaryId;//SECONDARY_ID	所属届次
	private Date timebeg;//TIMEBEG	活动开始时间
	private Date timeend;//TIMEEND	结束时间
	private Date releasetime;//RELEASETIME	发布时间
	private Date createtime;//CREATETIME 创建日期
	private int invitnumb;//INVITNUMB	邀请人员数量
	private int attendnumb;//ATTENDNUMB	参加人员数量
	private String astatus;//默认出席状态
	public Tactivity(){}
	
	@Id
	@Column(name = "AID",  unique = true, nullable = false, length = 20)
	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}
	@Column(name = "ATHEME", length = 200)
	public String getAtheme() {
		return atheme;
	}

	public void setAtheme(String atheme) {
		this.atheme = atheme;
	}
	@Column(name = "ASPECIES", length = 4)
	public String getAspecies() {
		return aspecies;
	}

	public void setAspecies(String aspecies) {
		this.aspecies = aspecies;
	}
	@Column(name = "ACONTENT", length = 3000)
	public String getAcontent() {
		return acontent;
	}

	public void setAcontent(String acontent) {
		this.acontent = acontent;
	}
	@Column(name = "AGENCY", length = 100)
	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}
	@Column(name = "PLACE", length = 500)
	public String getPlace() {
		return place;
	}
	
	public void setPlace(String place) {
		this.place = place;
	}
	@Column(name = "STATUS", length = 20)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name = "RELEASEAGE", length = 100)
	public String getReleaseage() {
		return releaseage;
	}

	public void setReleaseage(String releaseage) {
		this.releaseage = releaseage;
	}
	@Column(name = "MEMO", length = 50)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "TIMEBEG",length=7)
	public Date getTimebeg() {
		return timebeg;
	}

	public void setTimebeg(Date timebeg) {
		this.timebeg = timebeg;
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "TIMEEND",length=7)
	public Date getTimeend() {
		return timeend;
	}

	public void setTimeend(Date timeend) {
		this.timeend = timeend;
	}
	@Column(name = "RELEASETIME")
	public Date getReleasetime() {
		return releasetime;
	}

	public void setReleasetime(Date releasetime) {
		this.releasetime = releasetime;
	}
	@Column(name = "CREATETIME")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	@Column(name = "INVITNUMB")
	public int getInvitnumb() {
		return invitnumb;
	}

	public void setInvitnumb(int invitnumb) {
		this.invitnumb = invitnumb;
	}
	@Column(name = "ATTENDNUMB")
	public int getAttendnumb() {
		return attendnumb;
	}

	public void setAttendnumb(int attendnumb) {
		this.attendnumb = attendnumb;
	}
	@Column(name = "SECONDARY_ID", length = 12)
	public String getSecondaryId() {
		return secondaryId;
	}

	public void setSecondaryId(String secondaryId) {
		this.secondaryId = secondaryId;
	}
	@Column(name = "ASTATUS", length = 20)
	public String getAstatus() {
		return astatus;
	}

	public void setAstatus(String astatus) {
		this.astatus = astatus;
	}
	
	
}
