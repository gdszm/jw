package com.tlzn.action.sys;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.sys.Role;
import com.tlzn.service.sys.RoleServiceI;
import com.tlzn.util.base.ExceptionUtil;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 角色管理ACTION
 * 
 * @author 刘平
 * 
 */
@Action(value = "role", results = { @Result(name = "role", location = "/general/admin/role.jsp"), @Result(name = "roleAdd", location = "/general/admin/roleAdd.jsp"), @Result(name = "roleEdit", location = "/general/admin/roleEdit.jsp") })
public class RoleAction extends BaseAction implements ModelDriven<Role> {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(RoleAction.class);

	private Role role = new Role();

	private RoleServiceI roleService;

	public RoleServiceI getRoleService() {
		return roleService;
	}

	@Autowired
	public void setRoleService(RoleServiceI roleService) {
		this.roleService = roleService;
	}

	public Role getModel() {
		return role;
	}

	public String role() {
		return "role";
	}

	public String roleAdd() {
		return "roleAdd";
	}

	public String roleEdit() {
		return "roleEdit";
	}

	/**
	 * 获得角色datagrid
	 */
	public void datagrid() {
		super.writeJson(roleService.datagrid(role));
	}

	/**
	 * 添加一个角色
	 */
	public void add() {
		Json j = new Json();
		try {
			roleService.add(role);
			j.setSuccess(true);
			j.setMsg("添加成功！");
		} catch (Exception e) {
			j.setMsg("添加失败！");
			logger.error(ExceptionUtil.getExceptionMessage(e));
		}
		super.writeJson(j);
	}

	/**
	 * 编辑一个角色
	 */
	public void edit() {
		Json j = new Json();
		try {
			roleService.edit(role);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("编辑失败！");
		}
		super.writeJson(j);
	}

	/**
	 * 删除一个或多个角色
	 */
	public void delete() {
		Json j = new Json();
		roleService.delete(role.getIds());
		j.setSuccess(true);
		j.setMsg("删除成功！");
		super.writeJson(j);
	}

	/**
	 * 获得角色下拉列表
	 */
	public void doNotNeedSession_combobox() {
		super.writeJson(roleService.combobox());
	}
	
	/**
	 * 获得无承办单位和提案人列表
	 */
	public void doNotNeedSession_comboboxNo2() {
		super.writeJson(roleService.comboboxNo2());
	}
}
