package com.tlzn.action.sys;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.sys.Group;
import com.tlzn.pageModel.sys.UserGroup;
import com.tlzn.service.sys.GroupServiceI;

@Action(value = "group", results = {
		@Result(name = "group", location = "/general/committee/group.jsp"),
		@Result(name = "groupAdd", location = "/general/committee/groupAdd.jsp"),
		@Result(name = "groupEdit", location = "/general/committee/groupEdit.jsp"), 
		@Result(name = "userGroup", location = "/general/admin/userGroup.jsp")})
public class GroupAction extends BaseAction implements ModelDriven<Group> {

	private static final long serialVersionUID = 1L;

	private GroupServiceI groupService;
	
	private Group group = new Group();
	
	public Group getModel() {
		return group;
	}

	public GroupServiceI getGroupService() {
		return groupService;
	}

	@Autowired
	public void setGroupService(GroupServiceI groupService) {
		this.groupService = groupService;
	}
	
	
	
	
	public String group() {
		return "group";
	}

	public String groupAdd() {
		return "groupAdd";
	}

	public String groupEdit() {
		return "groupEdit";
	}
	public String userGroup() {
		return "userGroup";
	}
	public void datagrid() {
		try {
			writeJson(groupService.datagrid(group));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void add() throws Exception {
		Json j = new Json();
		try {
			groupService.add(group);
			j.setSuccess(true);
			j.setMsg("添加成功!");
		} catch (RuntimeException e) {
			j.setMsg("添加失败！");
		} 
		writeJson(j);
	}

	public void delete() {
		Json j = new Json();
		try {
			//System.out.println("================ids====="+group.getIds());
		    groupService.delete(group.getIds());		    
			j.setSuccess(true);
			j.setMsg("删除成功!");
		} catch (Exception e) {
			j.setMsg("操作失败！");
		}
		writeJson(j);
	}

	public void edit() {
		Json j = new Json();
		try {
			groupService.update(group);
			j.setSuccess(true);
			j.setMsg("操作成功!");
		} catch (Exception e) {
			j.setMsg("操作失败！");
		}
		writeJson(j);
	}

	public String userGroupEdit() {
		return "userGroupEdit";
	}
	//下拉列表
	public void doNotNeedSession_combox() {
		List<Group> list;
		try {
			list = groupService.combobox();
			Group t=new Group();
			t.setGname("请选择...");
			t.setGid("");
			list.add(0,t);
			writeJson(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
