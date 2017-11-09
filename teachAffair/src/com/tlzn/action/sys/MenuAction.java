package com.tlzn.action.sys;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.sys.Menu;
import com.tlzn.service.sys.MenuServiceI;
import com.tlzn.util.base.ExceptionUtil;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 菜单ACTION
 * 
 * @author 刘平
 * 
 */
@Action(value = "menu", results = { @Result(name = "menu", location = "/general/admin/menu.jsp"), @Result(name = "menuAdd", location = "/general/admin/menuAdd.jsp"), @Result(name = "menuEdit", location = "/general/admin/menuEdit.jsp") })
public class MenuAction extends BaseAction implements ModelDriven<Menu> {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(MenuAction.class);

	private MenuServiceI menuService;

	public MenuServiceI getMenuService() {
		return menuService;
	}

	@Autowired
	public void setMenuService(MenuServiceI menuService) {
		this.menuService = menuService;
	}

	private Menu menu = new Menu();
	
	public Menu getModel() {
		return menu;
	}
	
	public String menuAdd() {
		return "menuAdd";
	}
	
	public String menuEdit() {
		return "menuEdit";
	}

	/**
	 * 动态加载菜单树（当展开下级时才会加载下级菜单）
	 */
	public void doNotNeedSession_tree() {
		super.writeJson(menuService.tree(menu, false));
	}

	/**
	 * 一次性加载全部菜单树
	 */
	public void doNotNeedSession_treeRecursive() {
		super.writeJson(menuService.tree(menu, true));
	}

	public String menu() {
		return "menu";
	}

	/**
	 * 获得菜单treegrid
	 */
	public void treegrid() {
		super.writeJson(menuService.treegrid(menu));
	}

	/**
	 * 删除一个菜单
	 */
	public void delete() {
		Json j = new Json();
		try {
			menuService.delete(menu);
			j.setSuccess(true);
			j.setMsg("删除成功！");
			j.setObj(menu.getCid());
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("删除失败！");
		}
		super.writeJson(j);
	}

	public void add() {
		Json j = new Json();
		try {
			menuService.add(menu);
			j.setSuccess(true);
			j.setMsg("添加成功！");
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("添加失败！");
		}
		super.writeJson(j);
	}


	/**
	 * 编辑菜单
	 */
	public void edit() {
		Json j = new Json();
		try {
			menuService.edit(menu);
			j.setSuccess(true);
			j.setMsg("编辑成功!");
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("编辑失败！");
		}
		writeJson(j);
	}

}
