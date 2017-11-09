package com.tlzn.action.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.exception.ValidateFieldsException;
import com.tlzn.model.sys.Tdolog;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.base.SessionInfo;
import com.tlzn.pageModel.dailywork.Notice;
import com.tlzn.pageModel.sys.Auth;
import com.tlzn.pageModel.sys.Comm;
import com.tlzn.pageModel.sys.Comp;
import com.tlzn.pageModel.sys.Seco;
import com.tlzn.pageModel.sys.User;
import com.tlzn.service.base.BaseServiceI;
import com.tlzn.service.dailywork.NoticeServiceI;
import com.tlzn.service.info.PropServiceI;
import com.tlzn.service.sys.AuthServiceI;
import com.tlzn.service.sys.CommServiceI;
import com.tlzn.service.sys.CompServiceI;
import com.tlzn.service.sys.SecoServiceI;
import com.tlzn.service.sys.UserServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.ExceptionUtil;
import com.tlzn.util.base.IpUtil;
import com.tlzn.util.base.ResourceUtil;
import com.tlzn.util.base.Util;

/**
 * 用户管理ACTION
 * 
 * @author 刘平
 * 
 */
@Action(value = "user", results = { 
		@Result(name = "user", location = "/general/admin/user.jsp"), 
		@Result(name = "userAdd", location = "/general/admin/userAdd.jsp"), 
		@Result(name = "userEdit", location = "/general/admin/userEdit.jsp"), 
		@Result(name = "userRoleEdit", location = "/general/admin/userRoleEdit.jsp"), 
		@Result(name = "doNotNeedAuth_userInfo", location = "/general/user/userInfo.jsp"),
		@Result(name = "doNotNeedAuth_index", location = "/index.jsp"), 
		@Result(name = "doNotNeedAuth_index_main", location = "/index_main.jsp"), 
		@Result(name = "login", location = "/login.jsp"),
		@Result(name = "reg", location = "/reg.jsp"),
		@Result(name = "userComm", location = "/general/admin/userComm.jsp"),
		@Result(name = "userComp", location = "/general/admin/userComp.jsp"),
		@Result(name = "userPass", location = "/general/admin/userPass.jsp"),
		@Result(name = "portal", location = "/public/layout/portal/portalSys.jsp")
		})
public class UserAction extends BaseAction implements ModelDriven<User> {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(UserAction.class);

	private UserServiceI userService;

	private AuthServiceI authService;
	
	private SecoServiceI secoService;
	
	private CommServiceI commService;
	
	private CompServiceI compService;
	
	private BaseServiceI baseService;
	
	private PropServiceI propService;
	
	private NoticeServiceI noticeService;

	public AuthServiceI getAuthService() {
		return authService;
	}

	@Autowired
	public void setAuthService(AuthServiceI authService) {
		this.authService = authService;
	}

	public UserServiceI getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(UserServiceI userService) {
		this.userService = userService;
	}

	public SecoServiceI getSecoService() {
		return secoService;
	}
	@Autowired
	public void setSecoService(SecoServiceI secoService) {
		this.secoService = secoService;
	}

	public CommServiceI getCommService() {
		return commService;
	}
	@Autowired
	public void setCommService(CommServiceI commService) {
		this.commService = commService;
	}

	public CompServiceI getCompService() {
		return compService;
	}
	@Autowired
	public void setCompService(CompServiceI compService) {
		this.compService = compService;
	}

	public BaseServiceI getBaseService() {
		return baseService;
	}
	@Autowired
	public void setBaseService(BaseServiceI baseService) {
		this.baseService = baseService;
	}
	public PropServiceI getPropService() {
		return propService;
	}
	@Autowired
	public void setPropService(PropServiceI propService) {
		this.propService = propService;
	}
	public NoticeServiceI getNoticeService() {
		return noticeService;
	}
	@Autowired
	public void setNoticeService(NoticeServiceI noticeService) {
		this.noticeService = noticeService;
	}


	private User user = new User();

	public User getModel() {
		return user;
	}

	public String user() {
		return "user";
	}

	//首页
	public String doNotNeedAuth_sysCount(){
		return "portal";
	}
	public String userAdd() {
		return "userAdd";
	}

	public String userEdit() {
		return "userEdit";
	}

	public String userRoleEdit() {
		return "userRoleEdit";
	}

	public String doNotNeedAuth_userComm() {
		return "userComm";
	}
	
	public String doNotNeedAuth_userComp() {
		return "userComp";
	}

	public String doNotNeedAuth_userPass() {
		return "userPass";
	}

	public String doNotNeedAuth_userInfo() {
		return "doNotNeedAuth_userInfo";
	}
	public String doNotNeedSession_index() {
		SessionInfo sessionInfo = (SessionInfo) httpSession.getAttribute("sessionInfo");
		
		String modCode=request.getParameter("modCode");
		if(Util.getInstance().isEmpty(modCode)){
			//委员登录时
//			if(Constants.DIC_TYPE_YHZB_WY.equals(sessionInfo.getUserGroup())) {
//				modCode=Constants.MODULE_CODE_MAINPAGE;
//			} else {
				//具有的导航条的权限查询
				List<Auth> authListDH=authService.getAuthMenu(sessionInfo.getRoleIds());
				if(authListDH!=null && authListDH.size()>0) {
					modCode=authListDH.get(0).getCid();
				} else {
					modCode=null;
				}
//			}
		}
		httpSession.setAttribute("modCode", modCode);
		if (sessionInfo != null) {
			if(!Constants.MODULE_CODE_MAINPAGE.equals(modCode)){
				List<Auth> authList=authService.getAuthMenu(modCode,sessionInfo.getRoleIds());
				httpSession.setAttribute("menuList", authList);
				List<Auth> subAuthList=authService.getAuthSubMenu(modCode,sessionInfo.getRoleIds());
				httpSession.setAttribute("subMenuList", subAuthList);
				return "doNotNeedAuth_index";
			} else {
				return "doNotNeedAuth_index_main";
			}
		} else {
			return "login";
		}
	}
	
	public void doNotNeedAuth_editUserInfo() {
		Json j = new Json();
		try {
			userService.editUserInfo(user);
			j.setSuccess(true);
			j.setMsg("修改成功！");
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("修改失败！");
		}
		super.writeJson(j);
	}

	/**
	 * 用户登录（不需要session）
	 */
	public String doNotNeedSession_login() {
		System.out.println("====login start===");
		if (user.getCname() == null || "".equals(user.getCname())) {
			request.setAttribute("msg", "提示：请输入用户名！");
			return "login";
		}
		if (user.getCpwd() == null || "".equals(user.getCpwd())) {
			request.setAttribute("msg", "提示：请输入用户密码！");
			return "login";
		}
		//检验验证码
		String codeString = user.getValidCode();
		String kaptcha = (String) httpSession.getAttribute(
			com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		if (!kaptcha.equals(codeString)) {
			request.setAttribute("msg", "验证码输入错误，请重新输入");
			return "login";
		}
		try {
			User u = userService.login(user);
//			Seco seco= secoService.findCurrent();
//			httpSession.setAttribCute(ResourceUtil.getSessionSeco(), seco);
			if (u != null) {
				SessionInfo sessionInfo = new SessionInfo();
				sessionInfo.setUserId(u.getCid());
				sessionInfo.setLoginName(user.getCname());
				sessionInfo.setLoginPassword(user.getCpwd());
				sessionInfo.setIp(IpUtil.getIpAddr(ServletActionContext.getRequest()));
				sessionInfo.setMenus(authService.getAuthMenu(user.getRoleIds()));
				sessionInfo.setAuthIds(u.getAuthIds());
				sessionInfo.setAuthNames(u.getAuthNames());
				sessionInfo.setRoleIds(u.getRoleIds());
				sessionInfo.setRoleNames(u.getRoleNames());
				sessionInfo.setAuthUrls(u.getAuthUrls());
				sessionInfo.setRealName(u.getCrealname());
				sessionInfo.setUserCode(u.getUserCode());
				sessionInfo.setUserGroup(u.getUserGroup());
				sessionInfo.setDeptId(u.getDeptId());
				sessionInfo.setDeptName(u.getDeptName());
				httpSession.setAttribute(ResourceUtil.getSessionInfoName(), sessionInfo);
				
					
				if(Constants.CODE_TYPE_QYTY_NO.equals(u.getCstatus())){
					request.setAttribute("msg", "提示：账号停用，请联系管理员!");
					return "login";
				}
				if(u.getUserGroup()==null|| "".equals(u.getUserGroup())){
					request.setAttribute("msg", "提示：用户信息错误,请联系管理员!");
					return "login";
				}else if(u.getUserGroup().equals(Constants.DIC_TYPE_YHZB_WY)){
					if(u.getUserCode()==null||"".equals(u.getUserCode())){
						request.setAttribute("msg", "提示：用户信息错误,请联系管理员!");
						return "login";
					}
//					Comm comm=commService.getCommBycode(u.getUserCode());
//					if(comm.getSecondaryCode().indexOf(seco.getSecondaryId())==-1){
//						request.setAttribute("msg", "提示：您不是本届提案人,请联系管理员!");
//						return "login";
//					}
//					httpSession.setAttribute(ResourceUtil.getSessionComm(), comm);
				}	
//				else if(u.getUserGroup().equals(Constants.DIC_TYPE_YHZB_CBDW)){
//					if(u.getUserCode()==null||"".equals(u.getUserCode())){
//						request.setAttribute("msg", "提示：用户信息错误,请联系管理员!");
//						return "login";
//					}
//					Comp comp=compService.getCompBycode(u.getUserCode());
//					httpSession.setAttribute(ResourceUtil.getSessionComp(), comp);
//				}
				
				List<Notice> noticeList=noticeService.getNewNotice();
				httpSession.setAttribute("noticeList", noticeList);
				
				Tdolog dolog=new Tdolog();
				dolog.setLogType(Constants.LOG_TYPE_CODE_SYS);
				dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_LOGIN);
				dolog.setKeyId(u.getCid());
				dolog.setInfo( "[用户登录]用户名："+u.getCname()+",名称："+u.getRoleNames()+"  的用户登录");
				baseService.saveLog(dolog);
			} else {
				request.setAttribute("msg", "提示：用户名或密码错误!");
				return "login";
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		
		return this.doNotNeedSession_index();
	}

	/**
	 * 用户注销（不需要session）
	 */
	public void doNotNeedSession_logout() {
		ServletActionContext.getRequest().getSession().invalidate();
		Json j = new Json();
		j.setSuccess(true);
		super.writeJson(j);
	}

	/**
	 * 用户注册（不需要session）
	 */
	public String doNotNeedSession_reg() {
		try {
			if (userService.isUniqueUser(user.getCname(), null)) {
				request.setAttribute("user", user);
				return "reg";
			} else {
				userService.reg(user);
				request.setAttribute("user", user);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "login";
	}
	/**
	 * 用户名验证
	 */
	public void doNotNeedSession_checkUserName() {
		Map<String,Boolean> map = new HashMap<String,Boolean>();
		try {
			boolean bl=userService.isValidUser(user.getCname());
			if (bl) {
				map.put("valid", true);
			} else {
				map.put("valid", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		super.writeJson(map);
	}
	
	/**
	 * 用户添加
	 */
	public void add() {
		Json j = new Json();
		try {
			userService.save(user);
			j.setMsg("添加成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(e.getLocalizedMessage());
		}
		super.writeJson(j);
	}

	/**
	 * 用户编辑
	 */
	public void edit() {
		Json j = new Json();
		try {
			userService.update(user);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (Exception e) {
			j.setMsg("编辑失败，用户名已存在！");
		}
		super.writeJson(j);
	}
	
	/**
	 * 用户编辑
	 */
	public void doNotNeedAuth_changePass() {
		Json j = new Json();
		try {
			userService.changePass(user);
			j.setSuccess(true);
			j.setMsg("密码修改成功！");
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		super.writeJson(j);
	}

	/**
	 * 用户角色编辑
	 */
	public void roleEdit() {
		Json j = new Json();
		try {
			userService.roleEdit(user);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("编辑失败！");
		}
		super.writeJson(j);
	}

	/**
	 * 用户删除
	 */
	public void delete() {
		Json j = new Json();
		try {
			userService.delete(user.getIds());
			j.setSuccess(true);
			j.setMsg("删除成功!");
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("删除失败！");
		}
		super.writeJson(j);
	}
	/**
	 * 获得用户datagrid
	 */
	public void datagrid() {
		//super.writeJson(userService.datagrid(user));
		super.writeDataGridJson(userService.datagrid(user));
	}
}
