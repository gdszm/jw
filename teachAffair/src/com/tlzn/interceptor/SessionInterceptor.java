package com.tlzn.interceptor;

import org.apache.struts2.ServletActionContext;

import com.tlzn.pageModel.base.SessionInfo;
import com.tlzn.util.base.ResourceUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * session拦截器
 * 
 * @author 刘平
 * 
 */
public class SessionInterceptor extends MethodFilterInterceptor {

	private static final long serialVersionUID = 1L;
	
	protected String doIntercept(ActionInvocation actionInvocation) throws Exception {
		SessionInfo sessionInfo = (SessionInfo) ServletActionContext.getRequest().getSession().getAttribute(ResourceUtil.getSessionInfoName());
		if (sessionInfo == null) {
			ServletActionContext.getRequest().setAttribute("msg", "您还没有登录或登录已超时，请重新登录，2秒后将跳转到登录页面！<br><a href='javascript:void(0)' onclick='skip()'>如不能自动跳转请点击此处</a>");
			return "noSession";
		}
		return actionInvocation.invoke();
	}

}
