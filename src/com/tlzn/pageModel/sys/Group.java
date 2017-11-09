package com.tlzn.pageModel.sys;

import java.util.Date;

import com.tlzn.model.base.BaseObject;

public class Group extends BaseObject   {
	
	private String id;
	private String gid;//用户组编号
	private String gname;//组名称
	private String type;//用户分组类别
	private Date createTime;//创建时间
	private String typename;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public String getGname() {
		return gname;
	}
	public void setGname(String gname) {
		this.gname = gname;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}

	
	
	


	

}
