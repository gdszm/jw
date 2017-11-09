package com.tlzn.action.sys;

import java.util.List;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.model.sys.Tusergroup;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.sys.UserGroup;
import com.tlzn.service.sys.UserGroupServiceI;


@Action(value = "userGroup", results = {
		})
public class UserGroupAction extends BaseAction implements ModelDriven<UserGroup>{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(UserGroupAction.class);
	private UserGroupServiceI userGroupService;

	private UserGroup userGroup = new UserGroup();

	public UserGroup getModel() {
		return userGroup;
	}

	public UserGroupServiceI getUserGroupService() {
		return userGroupService;
	}

	@Autowired
	public void setuUserGroupService(UserGroupServiceI userGroupService) {
		this.userGroupService = userGroupService;
	}

	public String userGroup() {
		return "userGroup";
	}
	public String edit() {
		return "edit";
	}
	public void datagrid() {
		try {
			//System.out.println("gid==="+userGroup.getGid());
			writeDataGridJson(userGroupService.datagrid(userGroup));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public void  userSave() {
		Json j = new Json();
		try {
			//System.out.println("gid==="+userGroup.getGid());
			userGroupService.save(userGroup);
			j.setSuccess(true);
			j.setMsg("操作成功!");
		} catch (Exception e) {
			j.setMsg("操作失败！");
			e.printStackTrace();
			
		}
		writeJson(j);
	}
	public void delete() {
		Json j = new Json();
		try {
			userGroupService.delete(userGroup);
			j.setSuccess(true);
			j.setMsg("删除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg("操作失败！");
		}
		writeJson(j);
	}
	//下拉列表
	public void comboxGroupUser() {
		List<UserGroup> list;
		try {
			list = userGroupService.combobox(userGroup);
			UserGroup t=new UserGroup();
			t.setName("请选择...");
			t.setCode("");
			list.add(0,t);
			writeJson(list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		
	}
}
