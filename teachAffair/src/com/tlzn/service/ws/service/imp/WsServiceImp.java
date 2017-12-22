package com.tlzn.service.ws.service.imp;



import javax.jws.WebService;

import com.tlzn.pageModel.sys.User;
import com.tlzn.service.ws.service.WsServiceI;

@WebService
public class WsServiceImp implements WsServiceI {

	
	public User insertUser(String name, String age, String sex) {
		User u = new User();
		u.setUserCode("1111");
		return u;
	}
	
	public User insertUser2(String name,String age,String sex) {
		User u = new User();
		u.setUserCode("11112");
		return u;
	}

}