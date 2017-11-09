package com.tlzn.model;

import java.sql.Clob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TreplySummary entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TREPLY_SUMMARY", schema = "")
public class TreplySummary implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields

	private String summaryId;
	private String title;
	private Clob content;
	private String sumbitUnit;
	private String entryTime;

	// Constructors

	/** default constructor */
	public TreplySummary() {
	}

	/** full constructor */
	public TreplySummary(String title, Clob content, String sumbitUnit,
			String entryTime) {
		this.title = title;
		this.content = content;
		this.sumbitUnit = sumbitUnit;
		this.entryTime = entryTime;
	}

	// Property accessors
	@Id
	@Column(name = "SUMMARY_ID", unique = true, nullable = false, length = 12)
	public String getSummaryId() {
		return this.summaryId;
	}

	public void setSummaryId(String summaryId) {
		this.summaryId = summaryId;
	}

	@Column(name = "TITLE", length = 200)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "CONTENT")
	public Clob getContent() {
		return this.content;
	}

	public void setContent(Clob content) {
		this.content = content;
	}

	@Column(name = "SUMBIT_UNIT", length = 200)
	public String getSumbitUnit() {
		return this.sumbitUnit;
	}

	public void setSumbitUnit(String sumbitUnit) {
		this.sumbitUnit = sumbitUnit;
	}

	@Column(name = "ENTRY_TIME")
	public String getEntryTime() {
		return this.entryTime;
	}

	public void setEntryTime(String entryTime) {
		this.entryTime = entryTime;
	}

}