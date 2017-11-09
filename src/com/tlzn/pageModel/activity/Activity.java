package com.tlzn.pageModel.activity;

import java.util.Date;

import com.tlzn.model.base.BaseObject;

public class Activity extends BaseObject implements java.io.Serializable {

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
	private String aspeciesName;//ASPECIES	活动种类
	private String agencyName;//AGENCY	承办机构
	private String releaseageName;//RELEASEAGE	发布单位
	private String statusName;//STATUS	状态
	private String secondaryName;//SECONDARY_ID	所属届次
	private String year;
	private String userGroup;
	private String astatus;//默认出席状态
	private String code;//委员code
	
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public String getAtheme() {
		return atheme;
	}
	public void setAtheme(String atheme) {
		this.atheme = atheme;
	}
	public String getAspecies() {
		return aspecies;
	}
	public void setAspecies(String aspecies) {
		this.aspecies = aspecies;
	}
	public String getAcontent() {
		return acontent;
	}
	public void setAcontent(String acontent) {
		this.acontent = acontent;
	}
	public String getAgency() {
		return agency;
	}
	public void setAgency(String agency) {
		this.agency = agency;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReleaseage() {
		return releaseage;
	}
	public void setReleaseage(String releaseage) {
		this.releaseage = releaseage;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Date getTimebeg() {
		return timebeg;
	}
	public void setTimebeg(Date timebeg) {
		this.timebeg = timebeg;
	}
	public Date getTimeend() {
		return timeend;
	}
	public void setTimeend(Date timeend) {
		this.timeend = timeend;
	}
	public Date getReleasetime() {
		return releasetime;
	}
	public void setReleasetime(Date releasetime) {
		this.releasetime = releasetime;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public int getInvitnumb() {
		return invitnumb;
	}
	public void setInvitnumb(int invitnumb) {
		this.invitnumb = invitnumb;
	}
	public int getAttendnumb() {
		return attendnumb;
	}
	public void setAttendnumb(int attendnumb) {
		this.attendnumb = attendnumb;
	}
	public String getAspeciesName() {
		return aspeciesName;
	}
	public void setAspeciesName(String aspeciesName) {
		this.aspeciesName = aspeciesName;
	}
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	public String getReleaseageName() {
		return releaseageName;
	}
	public void setReleaseageName(String releaseageName) {
		this.releaseageName = releaseageName;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getSecondaryId() {
		return secondaryId;
	}
	public void setSecondaryId(String secondaryId) {
		this.secondaryId = secondaryId;
	}
	public String getSecondaryName() {
		return secondaryName;
	}
	public void setSecondaryName(String secondaryName) {
		this.secondaryName = secondaryName;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getUserGroup() {
		return userGroup;
	}
	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}
	public String getAstatus() {
		return astatus;
	}
	public void setAstatus(String astatus) {
		this.astatus = astatus;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}
