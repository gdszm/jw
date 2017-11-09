package com.tlzn.action.base;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

/**
 * druid datasource manager Action
 * 
 * @author 刘平
 * 
 */
@Namespace("/")
@Action(value = "datasource", results = { @Result(name = "druid", location = "/druid/index.html", type = "redirect") })
public class DataSourceAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	/**
	 * 跳转到连接池监控页面
	 * 
	 * @return
	 */
	public String druid() {
		return "druid";
	}

}
