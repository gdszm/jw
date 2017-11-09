package com.tlzn.interceptor;

import org.apache.struts2.ServletActionContext;

import com.tlzn.pageModel.base.SessionInfo;
import com.tlzn.util.base.RequestUtil;
import com.tlzn.util.base.ResourceUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * 权限拦截器
 * 
 * @author 刘平
 * 
 */
public class AuthInterceptor extends MethodFilterInterceptor {

	private static final long serialVersionUID = 1L;

	protected String doIntercept(ActionInvocation actionInvocation) throws Exception {
		//ActionContext actionContext = actionInvocation.getInvocationContext();
		SessionInfo sessionInfo = (SessionInfo) ServletActionContext.getRequest().getSession().getAttribute(ResourceUtil.getSessionInfoName());
		if (sessionInfo.getLoginName().equals("admin")) {// admin用户不需要验证权限
			return actionInvocation.invoke();
		}
		String requestPath = RequestUtil.getRequestPath(ServletActionContext.getRequest());
		String authUrls = sessionInfo.getAuthUrls();
		boolean b = false;
		for (String url : authUrls.split(",")) {
			if (requestPath.equals(url)) {
				b = true;
				break;
			}
		}
		if (b) {
			return actionInvocation.invoke();
		}
		ServletActionContext.getRequest().setAttribute("msg", "您没有访问此功能的权限！权限路径为[" + requestPath + "]请联系管理员给你赋予相应权限。");
		return "noAuth";
	}

}
