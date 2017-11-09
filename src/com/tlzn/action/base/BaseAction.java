package com.tlzn.action.base;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.util.base.JsonUtil;

/**
 * 基础ACTION,其他ACTION继承此ACTION来获得writeJson和ActionSupport的功能
 * 
 * @author 刘平
 * 
 */
@ParentPackage("basePackage")
@Namespace("/")
public class BaseAction extends ActionSupport implements ServletContextAware,
ServletResponseAware, ServletRequestAware, SessionAware {
	
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(BaseAction.class);
	
	protected ServletContext servletContext;

	protected HttpServletRequest request;

	protected HttpServletResponse response;

	protected HttpSession httpSession;

	protected Map<String, Object> session;
	
	
	public void setServletContext(ServletContext context) {
		this.servletContext = context;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
		this.httpSession = request.getSession();
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	
	/**
	 * 将对象转换成JSON字符串，并响应回前台
	 * 
	 * @param object
	 * @throws IOException
	 */
	public void writeJson(Object object) {
		try {
			// SerializeConfig serializeConfig = new SerializeConfig();
			// serializeConfig.setAsmEnable(false);
			// String json = JSON.toJSONString(object);
			// String json = JSON.toJSONString(object, serializeConfig, SerializerFeature.PrettyFormat);
			String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
			// String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss", SerializerFeature.PrettyFormat);
			ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
			ServletActionContext.getResponse().getWriter().write(json);
			ServletActionContext.getResponse().getWriter().flush();
			ServletActionContext.getResponse().getWriter().close();
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	/**
	 * 将DataGrid对象转换成JSON字符串，并响应回前台
	 * @param datagrid
	 * @throws IOException
	 */
	public void writeDataGridJson(DataGrid datagrid) {
		try {
			String json = JsonUtil.dataGridToJsonStr(datagrid);
			// String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss", SerializerFeature.PrettyFormat);
			ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
			ServletActionContext.getResponse().getWriter().write(json);
			ServletActionContext.getResponse().getWriter().flush();
			ServletActionContext.getResponse().getWriter().close();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}	
	
	public void writeListJson(String json) {
		try {
			ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
			ServletActionContext.getResponse().getWriter().write(json);
			ServletActionContext.getResponse().getWriter().flush();
			ServletActionContext.getResponse().getWriter().close();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}	
	
	public void writeObjectJson(Object object) {
		try {
			String json = JsonUtil.objectToJsonStr(object);
			ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
			ServletActionContext.getResponse().getWriter().write(json);
			ServletActionContext.getResponse().getWriter().flush();
			ServletActionContext.getResponse().getWriter().close();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}	
}
