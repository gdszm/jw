package com.tlzn.action.sys;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.sys.Auth;
import com.tlzn.service.sys.AuthServiceI;
import com.tlzn.util.base.ExceptionUtil;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 权限ACTION
 * 
 * @author 刘平
 * 
 */
@Action(value = "auth", results = { @Result(name = "auth", location = "/general/admin/auth.jsp"), @Result(name = "authAdd", location = "/general/admin/authAdd.jsp"), @Result(name = "authEdit", location = "/general/admin/authEdit.jsp") })
public class AuthAction extends BaseAction implements ModelDriven<Auth> {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(AuthAction.class);

	private Auth auth = new Auth();

	private AuthServiceI authService;

	public AuthServiceI getAuthService() {
		return authService;
	}

	@Autowired
	public void setAuthService(AuthServiceI authService) {
		this.authService = authService;
	}

	public Auth getModel() {
		return auth;
	}

	public String auth() {
		return "auth";
	}

	public String authAdd() {
		return "authAdd";
	}

	public String authEdit() {
		return "authEdit";
	}

	/**
	 * 动态获取权限树（当展开下级时才去加载下级权限）
	 */
	public void doNotNeedSession_tree() {
		super.writeJson(authService.tree(auth, false));
	}

	/**
	 * 一次性加载全部权限树
	 */
	public void doNotNeedSession_treeRecursive() {
		super.writeJson(authService.tree(auth, true));
	}

	/**
	 * 获得权限treegrid
	 */
	public void treegrid() {
		super.writeJson(authService.treegrid(auth));
	}

	/**
	 * 删除一个权限
	 */
	public void delete() {
		Json j = new Json();
		try {
			authService.delete(auth);
			j.setSuccess(true);
			j.setMsg("删除成功！");
			j.setObj(auth.getCid());
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("删除失败！");
		}
		super.writeJson(j);
	}

	/**
	 * 编辑权限
	 */
	public void edit() {
		Json j = new Json();
		try {
			authService.edit(auth);
			j.setSuccess(true);
			j.setMsg("编辑成功!");
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("编辑失败！");
		}
		super.writeJson(j);
	}

	/**
	 * 添加权限
	 */
	public void add() {
		Json j = new Json();
		try {
			authService.add(auth);
			j.setSuccess(true);
			j.setMsg("添加成功!");
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("添加失败！");
		}
		super.writeJson(j);
	}

}
