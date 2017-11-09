package com.tlzn.model.sys;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tlzn.model.base.BaseObject;

/**
 * Tdolog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TDOLOG", schema = "")
public class Tdolog extends BaseObject implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields


	private String logCode;
	private String title;
	private String info;
	private Date operateTime;
	private String ip;
	private String operator;
	private String logType;
	private String keyId;

	// Constructors

	/** default constructor */
	public Tdolog() {
	}

	/** full constructor */
	public Tdolog(String logCode,String title, String info, Date operateTime, String ip,String operator) {
		this.logCode=logCode;
		this.title = title;
		this.info = info;
		this.operateTime = operateTime;
		this.ip = ip;
		this.operator = operator;
	}

	// Property accessors
	@Id
	@Column(name = "LOG_CODE", unique = true, nullable = false, length = 12)
	public String getLogCode() {
		return this.logCode;
	}

	public void setLogCode(String logCode) {
		this.logCode = logCode;
	}

	@Column(name = "TITLE", length = 100)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "INFO", length = 1000)
	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Column(name = "OPERATE_TIME")
	public Date getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	@Column(name = "IP", length = 20)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "OPERATOR", length = 50)
	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	@Column(name = "LOG_TYPE", length = 50)
	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}
	@Column(name = "KEY_ID", length = 4000)
	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

}