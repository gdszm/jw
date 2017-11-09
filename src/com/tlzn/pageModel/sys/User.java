package com.tlzn.pageModel.sys;

import java.util.Date;

public class User implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String ids;
	private String roleIds;
	private String roleNames;
	private String deptId;
	private String deptName;
	private String authIds;
	private String authNames;
	private String authUrls;
	private String userCode;
	private String validCode; //验证码图片
	private String userGroup;
	private int page;// 当前页
	private int rows;// 每页显示记录数
	private String sort;// 排序字段名
	private String order;// 按什么排序(asc,desc)
	private Date ccreatedatetimeStart;
	private Date cmodifydatetimeStart;
	private Date ccreatedatetimeEnd;
	private Date cmodifydatetimeEnd;

	private String cid;
    private String csn;
	private Date ccreatedatetime;
	private Date cmodifydatetime;
	private String cname;
	private String oldpass;
	private String cpwd;
	private String mobile;
	private String cemail;
	private String cstatus;
	private String crealname;
	private String userGroupName;


	public String getAuthUrls() {
		return authUrls;
	}

	public void setAuthUrls(String authUrls) {
		this.authUrls = authUrls;
	}

	public String getAuthNames() {
		return authNames;
	}

	public void setAuthNames(String authNames) {
		this.authNames = authNames;
	}

	public String getAuthIds() {
		return authIds;
	}

	public void setAuthIds(String authIds) {
		this.authIds = authIds;
	}

	public Date getCcreatedatetimeStart() {
		return ccreatedatetimeStart;
	}

	public void setCcreatedatetimeStart(Date ccreatedatetimeStart) {
		this.ccreatedatetimeStart = ccreatedatetimeStart;
	}

	public Date getCmodifydatetimeStart() {
		return cmodifydatetimeStart;
	}

	public void setCmodifydatetimeStart(Date cmodifydatetimeStart) {
		this.cmodifydatetimeStart = cmodifydatetimeStart;
	}

	public Date getCcreatedatetimeEnd() {
		return ccreatedatetimeEnd;
	}

	public void setCcreatedatetimeEnd(Date ccreatedatetimeEnd) {
		this.ccreatedatetimeEnd = ccreatedatetimeEnd;
	}

	public Date getCmodifydatetimeEnd() {
		return cmodifydatetimeEnd;
	}

	public void setCmodifydatetimeEnd(Date cmodifydatetimeEnd) {
		this.cmodifydatetimeEnd = cmodifydatetimeEnd;
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

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public Date getCcreatedatetime() {
		return ccreatedatetime;
	}

	public void setCcreatedatetime(Date ccreatedatetime) {
		this.ccreatedatetime = ccreatedatetime;
	}

	public Date getCmodifydatetime() {
		return cmodifydatetime;
	}

	public void setCmodifydatetime(Date cmodifydatetime) {
		this.cmodifydatetime = cmodifydatetime;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCpwd() {
		return cpwd;
	}

	public void setCpwd(String cpwd) {
		this.cpwd = cpwd;
	}

	public String getCemail() {
		return cemail;
	}

	public void setCemail(String cemail) {
		this.cemail = cemail;
	}

	public String getCsn() {
		return csn;
	}

	public void setCsn(String csn) {
		this.csn = csn;
	}

	public String getCstatus() {
		return cstatus;
	}

	public void setCstatus(String cstatus) {
		this.cstatus = cstatus;
	}

	public String getCrealname() {
		return crealname;
	}

	public void setCrealname(String crealname) {
		this.crealname = crealname;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}

	public String getOldpass() {
		return oldpass;
	}

	public void setOldpass(String oldpass) {
		this.oldpass = oldpass;
	}

	public String getUserGroupName() {
		return userGroupName;
	}

	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getValidCode() {
		return validCode;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

}
