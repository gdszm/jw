package com.tlzn.service.sys;

import java.util.List;

import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.sys.Role;

public interface RoleServiceI {

	public DataGrid datagrid(Role role);

	public void add(Role role);

	public void edit(Role role);

	public void delete(String ids);

	public List<Role> combobox();

	public List<Role> comboboxNo2();

}
