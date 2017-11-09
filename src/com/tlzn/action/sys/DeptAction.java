package com.tlzn.action.sys;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.sys.Dept;
import com.tlzn.service.sys.DeptServiceI;
import com.tlzn.util.base.ExceptionUtil;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 部门ACTION
 * 
 * @author 刘平
 * 
 */
@Action(value = "dept", results = { @Result(name = "dept", location = "/general/admin/dept.jsp"), @Result(name = "deptAdd", location = "/general/admin/deptAdd.jsp"), @Result(name = "deptEdit", location = "/general/admin/deptEdit.jsp") })
public class DeptAction extends BaseAction implements ModelDriven<Dept> {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(DeptAction.class);

	private DeptServiceI deptService;

	public DeptServiceI getDeptService() {
		return deptService;
	}

	@Autowired
	public void setDeptService(DeptServiceI deptService) {
		this.deptService = deptService;
	}

	private Dept dept = new Dept();

	public Dept getModel() {
		return dept;
	}

	/**
	 * 动态获取部门树（当展开下级时才会加载下级部门）
	 */
	public void doNotNeedSession_tree() {
		super.writeJson(deptService.tree(dept, false));
	}
	
	/**
	 * 一次性获取全部部门树
	 */
	public void doNotNeedSession_treeRecursive() {
		super.writeJson(deptService.tree(dept, true));
	}

	public String dept() {
		return "dept";
	}

	/**
	 * 获得部门treegrid
	 */
	public void treegrid() {
		super.writeJson(deptService.treegrid(dept));
	}

	/**
	 * 删除一个部门
	 */
	public void delete() {
		Json j = new Json();
		try {
			deptService.delete(dept);
			j.setSuccess(true);
			j.setMsg("删除成功！");
			j.setObj(dept.getCid());
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("删除失败！部门已使用!");
		}
		super.writeJson(j);
	}

	public String deptAdd() {
		return "deptAdd";
	}

	public void add() {
		Json j = new Json();
		try {
			deptService.add(dept);
			j.setSuccess(true);
			j.setMsg("添加成功！");
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("添加失败！");
		}
		super.writeJson(j);
	}

	public String deptEdit() {
		return "deptEdit";
	}

	/**
	 * 编辑部门
	 */
	public void edit() {
		Json j = new Json();
		try {
			deptService.edit(dept);
			j.setSuccess(true);
			j.setMsg("编辑成功!");
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("编辑失败！");
		}
		writeJson(j);
	}

}
