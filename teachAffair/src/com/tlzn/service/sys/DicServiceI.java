package com.tlzn.service.sys;

import java.util.List;

import com.tlzn.model.sys.Tdic;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.TreeNode;
import com.tlzn.pageModel.sys.Auth;
import com.tlzn.pageModel.sys.Dic;

/**
 * 数据字典Service
 * 
 * @author 刘平
 * 
 */
public interface DicServiceI {

	public void add(Dic dic);
	
	public Dic get(String id) throws Exception;
	
	public DataGrid datagrid(Dic dic);

	public void delete(String ids);

	public void update(Dic dic);

	public void change(Dic dic);

	public List<Dic> combobox(Dic dic);
	
	public List<Dic> comboxGroup(Dic dic);
	
	String getCkey(Dic dic)throws Exception;

	public String findDicName(String type,String value);

	public List<Tdic> findAllTdic(Dic dic);

	public List<TreeNode> tree(Dic dic, boolean b);
}
