package com.tlzn.service.sys;

import java.util.List;

import com.tlzn.pageModel.base.TreeNode;
import com.tlzn.pageModel.sys.Dept;

public interface DeptServiceI {

	/**
	 * 获取部门树
	 * 
	 * @param dept
	 * @param b
	 *            是否递归子节点
	 * @return
	 */
	public List<TreeNode> tree(Dept dept, Boolean b);

	/**
	 * 获得部门treegrid
	 * 
	 * @param dept
	 * @return
	 */
	public List<Dept> treegrid(Dept dept);

	/**
	 * 删除部门
	 * 
	 * @param dept
	 */
	public void delete(Dept dept);

	/**
	 * 添加部门
	 * 
	 * @param dept
	 */
	public void add(Dept dept);

	/**
	 * 编辑部门
	 * 
	 * @param dept
	 */
	public void edit(Dept dept);
	
	/**
	 * 查询所有部门项
	 * 
	 * @param dept
	 */
	public List<Dept> findAll();

}
