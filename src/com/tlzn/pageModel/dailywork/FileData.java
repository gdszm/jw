package com.tlzn.pageModel.dailywork;

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

import com.tlzn.model.Tcommitteeman;
import com.tlzn.model.base.BaseObject;
import com.tlzn.model.dailywork.TfileData;

/**
 * 通知公告实体类
 * 
 * @author gds
 * @Time 2016-10-11 08:45:00
 * 
 */

public class FileData extends BaseObject implements java.io.Serializable {

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

	private String fromtypeName; //来源类型名称
	private String datatypeName; //文件类型名称 
	private String depName; //部门名称
	private String statusName;  //状态名称 
	private Date pubDateStart;  //发布日期开始 
	private Date pubDateEnd;  //状态名称 结束
	
	/** default constructor */
	public FileData() {
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

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getFromtypeName() {
		return fromtypeName;
	}

	public void setFromtypeName(String fromtypeName) {
		this.fromtypeName = fromtypeName;
	}

	public String getDatatypeName() {
		return datatypeName;
	}

	public void setDatatypeName(String datatypeName) {
		this.datatypeName = datatypeName;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public Date getPubDateStart() {
		return pubDateStart;
	}

	public void setPubDateStart(Date pubDateStart) {
		this.pubDateStart = pubDateStart;
	}

	public Date getPubDateEnd() {
		return pubDateEnd;
	}

	public void setPubDateEnd(Date pubDateEnd) {
		this.pubDateEnd = pubDateEnd;
	}


}
