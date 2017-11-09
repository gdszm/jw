package com.tlzn.model.dailywork;

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

/**
 * 通知公告实体类
 * 
 * @author gds
 * @Time 2016-10-11 08:45:00
 * 
 */

@Entity
@Table(name = "TFILEDATA")
public class TfileData extends BaseObject implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String dataId; //编码
	private String title; //标题
	private String fromtype; //来源类型
	private String datatype; //文件类型
	private String key; //关键词
	private String content; //正文内容
	private String atts; //附件
	private String depid; //部门ID
	private Date createtime; //创建时间
	private Date pubdate; //发布日期
	private String status; //状态
	private String memo; //备注
	
	
//	private Set<TfiledataMan> tfiledataMans = new HashSet<TfiledataMan>(0);

	/** default constructor */
	public TfileData() {
	}

	// Property accessors
	@Id
	@Column(name = "DATA_ID", unique = true, nullable = false, length = 20)
	public String getDataId() {
		return this.dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	@Column(name = "TITLE", length = 300)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "FROMTYPE", length = 4)
	public String getFromtype() {
		return this.fromtype;
	}

	public void setFromtype(String fromtype) {
		this.fromtype = fromtype;
	}

	@Column(name = "DATATYPE", length = 4)
	public String getDatatype() {
		return this.datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	@Column(name = "KEY_STR", length = 500)
	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Column(name = "CONTENT")
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "ATTS", length = 500)
	public String getAtts() {
		return this.atts;
	}

	public void setAtts(String atts) {
		this.atts = atts;
	}

	@Column(name = "DEPID", length = 20)
	public String getDepid() {
		return this.depid;
	}

	public void setDepid(String depid) {
		this.depid = depid;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATETIME")
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "PUBDATE", length = 7)
	public Date getPubdate() {
		return this.pubdate;
	}

	public void setPubdate(Date pubdate) {
		this.pubdate = pubdate;
	}

	@Column(name = "STATUS", length = 4)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "MEMO", length = 2000)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tfiledata")
//	public Set<TfiledataMan> getTfiledataMans() {
//		return this.tfiledataMans;
//	}
//
//	public void setTfiledataMans(Set<TfiledataMan> tfiledataMans) {
//		this.tfiledataMans = tfiledataMans;
//	}
}
