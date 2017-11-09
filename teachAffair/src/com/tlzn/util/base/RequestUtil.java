package com.tlzn.util.base;

import javax.servlet.http.HttpServletRequest;

/**
 * request工具类
 * 
 * @author 刘平
 * 
 */
public class RequestUtil {

	/**
	 * 获得请求路径
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestPath(HttpServletRequest request) {
		String requestPath = request.getRequestURI();
		requestPath = requestPath.substring(request.getContextPath().length());// 去掉项目路径
		return requestPath;
	}

}
