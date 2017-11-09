package com.tlzn.pageModel.sys;

import java.util.Date;

public class Seco {
	private static final long serialVersionUID = 1L;

	private String secondaryId;
	private String secondayName;
	private String year;
	private String status;
	private Date createTime;
	private String remark;
	private String period;
	private String periodName;
	private String ext;
	
	private String statusName;
	private String ids;// 传入ID
	private int page;// 当前页
	private int rows;// 每页显示记录数
	private String sort;// 排序字段名
	private String order;// 按什么排序(asc,desc)

	public Seco() {
	}

	public Seco(String secondayName, String year, String status,
			Date createTime, String remark) {
		this.secondayName = secondayName;
		this.year = year;
		this.status = status;
		this.createTime = createTime;
		this.remark = remark;
	}

	public String getSecondaryId() {
		return this.secondaryId;
	}

	public void setSecondaryId(String secondaryId) {
		this.secondaryId = secondaryId;
	}

	public String getSecondayName() {
		return this.secondayName;
	}

	public void setSecondayName(String secondayName) {
		this.secondayName = secondayName;
	}

	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getPeriodName() {
		return periodName;
	}

	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}

}
