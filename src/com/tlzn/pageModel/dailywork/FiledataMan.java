package com.tlzn.pageModel.dailywork;

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

import com.tlzn.model.Tcommitteeman;

/**
 * TfiledataMan entity. @author MyEclipse Persistence Tools
 */

public class FiledataMan implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4811604301004710409L;
	
	private String id;
	private Tcommitteeman tcommitteeman;
	private Date createtime;
	private String memo;

	private String dataId;
	private String title;
	private String fromtype;
	private String datatype;
	private String key;
	private String content;
	private String atts;
	private String depid;
//	private Date createtime;
	private Date pubdate;
	private String status;
//	private String memo;
	
	public FiledataMan() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Tcommitteeman getTcommitteeman() {
		return tcommitteeman;
	}

	public void setTcommitteeman(Tcommitteeman tcommitteeman) {
		this.tcommitteeman = tcommitteeman;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFromtype() {
		return fromtype;
	}

	public void setFromtype(String fromtype) {
		this.fromtype = fromtype;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAtts() {
		return atts;
	}

	public void setAtts(String atts) {
		this.atts = atts;
	}

	public String getDepid() {
		return depid;
	}

	public void setDepid(String depid) {
		this.depid = depid;
	}

	public Date getPubdate() {
		return pubdate;
	}

	public void setPubdate(Date pubdate) {
		this.pubdate = pubdate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}