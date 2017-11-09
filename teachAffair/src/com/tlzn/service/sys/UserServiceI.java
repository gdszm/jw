package com.tlzn.service.sys;


import com.tlzn.model.sys.Tuser;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.sys.User;

public interface UserServiceI {

	public User login(User user);

	public void save(User user) throws Exception;
	
	public void reg(User user) throws Exception;
	
	public boolean isUniqueUser(String userName, String id) throws Exception;
	
	public boolean isValidUser(String userName) throws Exception;
	
	public DataGrid datagrid(User user);

	public void delete(String ids)throws Exception;

	public void update(User user) throws Exception;

	public void roleEdit(User user) throws Exception;

	public void editUserInfo(User user);

	public String findUserID(User user);

	public void changePass(User user) throws Exception;

	public Tuser findByUserCode(String userCode);
}
