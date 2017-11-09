package com.tlzn.service.sys;

import java.util.List;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.sys.UserGroup;

public interface UserGroupServiceI{
	
	public DataGrid datagrid(UserGroup userGroup)throws Exception;
	
	public void update(UserGroup userGroup)throws Exception;
	
	public void save(UserGroup userGroup)throws Exception;
	
	public List<UserGroup> combobox(UserGroup userGroup)throws Exception;

	public void delete(UserGroup userGroup)throws Exception;

}
