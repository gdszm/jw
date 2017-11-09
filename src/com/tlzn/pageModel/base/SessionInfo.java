package com.tlzn.pageModel.base;

import java.util.List;

import com.tlzn.pageModel.sys.Auth;

/**
 * sessionInfo模型
 * 
 * @author 刘平
 * 
 */
public class SessionInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;// 用户ID
	private String loginName;// 用户登录名称
	private String loginPassword;// 登录密码
	private String ip;// IP地址
	private String authIds;// 拥有的权限ID集合
	private String authNames;
	private String authUrls;
	private String roleIds;
	private String roleNames;
	private String realName;
	private String userCode;
	private String userGroup;
	private List<Auth> menus;
	private String deptId;// 部门ID
	private String deptName;// 部门名称

	public List<Auth> getMenus() {
		return menus;
	}

	public void setMenus(List<Auth> menu) {
		this.menus = menu;
	}

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

	public String getAuthIds() {
		return authIds;
	}

	public void setAuthIds(String authIds) {
		this.authIds = authIds;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Override
	public String toString() {
		return loginName;
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

}
