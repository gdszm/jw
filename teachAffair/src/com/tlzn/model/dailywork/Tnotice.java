package com.tlzn.model.dailywork;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tlzn.model.base.BaseObject;
import com.tlzn.model.sys.Tsecondary;

/**
 * 通知公告实体类
 * 
 * @author gds
 * @Time 2016-10-11 08:45:00
 * 
 */

@Entity
@Table(name = "TNOTICE")
public class Tnotice extends BaseObject implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String id;      // 通知ID
	private String title;   // 通知标题
	private String content; // 通知内容
	private String pubUnit; // 发布单位
	private String reply;   // 是否回复
	private Date creatDate; // 创建日期
	private Date pubDate;   // 发布日期
	private Date validDate; // 有效日期
	private String status;  // 状态（0：未发布，1：已发布）
	private String noticeType; // 通知类型
	private String atts;    // 附件(多)
	private String memo; // 备注
	
//	private Tsecondary tsecondary;
	public Tnotice() {
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 12)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "TITLE", length = 500)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "CONTENT")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "PUBUNIT", length = 100)
	public String getPubUnit() {
		return pubUnit;
	}

	public void setPubUnit(String pubUnit) {
		this.pubUnit = pubUnit;
	}

	@Column(name = "REPLY", length = 4)
	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	@Column(name = "PUBDATE")
	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "VALIDDATE", length = 7)
	public Date getValidDate() {
		return validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	@Column(name = "STATUS", length = 4)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "ATTS", length = 500)
	public String getAtts() {
		return atts;
	}

	public void setAtts(String atts) {
		this.atts = atts;
	}
	@Column(name = "CREATDATE")
	public Date getCreatDate() {
		return creatDate;
	}

	public void setCreatDate(Date creatDate) {
		this.creatDate = creatDate;
	}
	@Column(name = "NOTICETYPE", length = 4)
	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}
	@Column(name = "MEMO", length = 2000)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
//	@ManyToOne(targetEntity=Tsecondary.class)//fetch=FetchType.LAZY
//	@JoinColumn(name = "SECONDARY_ID", nullable = false)
//	public Tsecondary getTsecondary() {
//		return tsecondary;
//	}
//
//	public void setTsecondary(Tsecondary tsecondary) {
//		this.tsecondary = tsecondary;
//	}
}
