package com.tlzn.service.sys;




import java.util.List;

import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.sys.Group;
import com.tlzn.service.base.BaseServiceI;


/**
 * 数据字典Service
 * 
 * @author 刘平
 * 
 */
public interface GroupServiceI extends BaseServiceI {

	public DataGrid datagrid(Group group)throws Exception ;

	public void add(Group group) throws Exception;

	public void delete(String ids)throws Exception;

	public void update(Group group)throws Exception;

	public List<Group> combobox()throws Exception ;
	
	
	
}
