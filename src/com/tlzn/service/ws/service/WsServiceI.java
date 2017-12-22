package com.tlzn.service.ws.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.tlzn.pageModel.sys.User;

@WebService
public interface WsServiceI {

	@WebMethod(action="http://service.ws.service.tlzn.com/")
	User insertUser(String name, String age, String sex);  
	User insertUser2(String name, String age, String sex);  
}