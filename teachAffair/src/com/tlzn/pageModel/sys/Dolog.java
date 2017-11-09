package com.tlzn.pageModel.sys;

import java.util.Date;

public class Dolog  implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	// Fields


	private String logCode;
	private String title;
	private String info;
	private Date operateTime;
	private String ip;
	private String operator;
	private String logType;
	private String logTypeName;
	private String keyId;
	private String startDate;
	private String endDate;
	private String ids;			// ids字符串
	private int page; 			// 当前页
	private int rows; 			// 每页显示记录数
	private String sort; 		// 排序字段名
	private String order; 		// 按什么排序(asc,desc)
	
	
	public String getLogCode() {
		return logCode;
	}
	public void setLogCode(String logCode) {
		this.logCode = logCode;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
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
	public String getLogType() {
		return logType;
	}
	public void setLogType(String logType) {
		this.logType = logType;
	}
	public String getLogTypeName() {
		return logTypeName;
	}
	public void setLogTypeName(String logTypeName) {
		this.logTypeName = logTypeName;
	}
	public String getKeyId() {
		return keyId;
	}
	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	
}
