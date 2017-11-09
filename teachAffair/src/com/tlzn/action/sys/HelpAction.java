package com.tlzn.action.sys;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.dailywork.Notice;

@Action(value = "help", results = {
// 帮助首页
@Result(name = "portal", location = "/public/layout/portal/portalHelp.jsp") })
public class HelpAction extends BaseAction  {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HelpAction.class);

//	private Notice notice = new Notice();
//
//	public Notice getModel() {
//		return notice;
//	}

	// 会议发言首页统计
	public String doNotNeedAuth_help() {
		return "portal";
	}

	public void help() {
		System.out.println(11);
	}

	public void videoDownload() {
		System.out.println(22);
	}
}
