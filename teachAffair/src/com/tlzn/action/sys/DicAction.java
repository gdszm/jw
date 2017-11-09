package com.tlzn.action.sys;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.sys.Dic;
import com.tlzn.service.sys.DicServiceI;

@Action(value = "dic", results = {
		@Result(name = "dic", location = "/general/admin/dic.jsp"),
		@Result(name = "dicAdd", location = "/general/admin/dicAdd.jsp"),
		@Result(name = "dicEdit", location = "/general/admin/dicEdit.jsp") })
public class DicAction extends BaseAction implements ModelDriven<Dic> {

	private static final long serialVersionUID = 1L;

	private DicServiceI dicService;

	private Dic dic = new Dic();

	public Dic getModel() {
		return dic;
	}

	public DicServiceI getDicService() {
		return dicService;
	}

	@Autowired
	public void setDicService(DicServiceI dicService) {
		this.dicService = dicService;
	}

	public String dic() {
		return "dic";
	}
	
	public String dicAdd() {
		return "dicAdd";
	}
	
	public String dicEdit() {
		try {
			Dic dicOne= dicService.get(dic.getCid());
			request.setAttribute("dic", dicOne);
		} catch (Exception e) {
			e.getStackTrace();
		}
		
		return "dicEdit";
	}

	public void datagrid() {
		writeJson(dicService.datagrid(dic));
	}

	public void add() {
		Json j = new Json();
		try {
			dicService.add(dic);
			j.setSuccess(true);
			j.setMsg("添加成功!");
		} catch (RuntimeException e) {
			j.setMsg("添加失败!");
		}
		writeJson(j);
	}
	
	public void getckey() {
		Json j = new Json();
		try {
			String ckey=dicService.getCkey(dic);
			j.setSuccess(true);
			j.setObj(ckey);
			j.setMsg("获取成功!");
		} catch (Exception e) {
			j.setMsg("获取失败!");
		}
		writeJson(j);
	}
	
	public void delete() {
 		Json j = new Json();
		try {
			dicService.delete(dic.getIds());
			j.setSuccess(true);
			j.setMsg("删除成功!");
		} catch (Exception e) {
			j.setMsg("删除失败!");
		}
		writeJson(j);
	}

	public void edit() {
		Json j = new Json();
		try {
			dicService.update(dic);
			j.setSuccess(true);
			j.setMsg("操作成功!");
		} catch (Exception e) {
			j.setMsg("操作失败!");
		}
		writeJson(j);
	}

	public void change() {
		Json j = new Json();
		try {
			dicService.change(dic);
			j.setSuccess(true);
			j.setMsg("操作成功!");
		} catch (Exception e) {
			j.setMsg("操作失败!");
		}
		writeJson(j);
	}

	public void combox() {
		writeJson(dicService.combobox(dic));
	}
	public void doNotNeedSession_comboxGroup() {
		writeJson(dicService.comboxGroup(dic));
	}
	
	/**
	 * 动态获取菜单树（当展开下级时才去加载下级权限）
	 */
	public void doNotNeedSession_tree() {
		super.writeJson(dicService.tree(dic, false));
	}

	/**
	 * 一次性加载全部菜单树
	 */
	public void doNotNeedSession_treeRecursive() {
		super.writeJson(dicService.tree(dic, true));
	}
}
