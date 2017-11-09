package com.tlzn.pageModel.sys;

import com.tlzn.model.Tcommitteeman;
import com.tlzn.model.base.BaseObject;


public class UserGroup extends BaseObject{
	
	private String gcid;//用户分组编码
	private String code;//用户编码
	private String name;
	private String gid;//用户组编号
	private String gname;
	private String createTime;
	private String cids;
	private String type;
	private String circleName;
	private String committeeName;
	private String companyName;
	private String job;
	
	public String getGcid() {
		return gcid;
	}
	public void setGcid(String gcid) {
		this.gcid = gcid;
	}
	
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getGname() {
		return gname;
	}
	public void setGname(String gname) {
		this.gname = gname;
	}
	public String getCids() {
		return cids;
	}
	public void setCids(String cids) {
		this.cids = cids;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public String getCommitteeName() {
		return committeeName;
	}
	public void setCommitteeName(String committeeName) {
		this.committeeName = committeeName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	
}
