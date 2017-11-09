package com.tlzn.service.sys;

import java.util.List;

import com.tlzn.model.sys.Tauth;
import com.tlzn.pageModel.base.TreeNode;
import com.tlzn.pageModel.sys.Auth;

public interface AuthServiceI {

	public List<Auth> treegrid(Auth auth);

	public void delete(Auth auth);

	public void edit(Auth auth);

	public void add(Auth auth);
	
	public List<Auth> getAuthMenu(String role);
	public List<Auth> getAuthMenu(String modCode,String role);
	public List<Auth> getAuthSubMenu(String modCode,String role);
	
	/**
	 * 获取权限树
	 * 
	 * @param auth
	 * @param b
	 *            是否递归子节点
	 * @return
	 */
	public List<TreeNode> tree(Auth auth, boolean b);

}
