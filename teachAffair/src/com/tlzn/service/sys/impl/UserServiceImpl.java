package com.tlzn.service.sys.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.exception.ValidateFieldsException;
import com.tlzn.model.sys.Tauth;
import com.tlzn.model.sys.Tdept;
import com.tlzn.model.sys.Tdolog;
import com.tlzn.model.sys.Trole;
import com.tlzn.model.sys.Troletauth;
import com.tlzn.model.sys.Tuser;
import com.tlzn.model.sys.Tusertrole;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.sys.User;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.sys.UserServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Encrypt;
import com.tlzn.util.base.Util;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl implements UserServiceI {

	private BaseDaoI<Tuser> userDao;
	private BaseDaoI<Trole> roleDao;
	private BaseDaoI<Tdept> deptDao;
	private BaseDaoI<Tusertrole> userroleDao;

	public BaseDaoI<Trole> getRoleDao() {
		return roleDao;
	}

	@Autowired
	public void setRoleDao(BaseDaoI<Trole> roleDao) {
		this.roleDao = roleDao;
	}

	public BaseDaoI<Tusertrole> getUserroleDao() {
		return userroleDao;
	}

	@Autowired
	public void setUserroleDao(BaseDaoI<Tusertrole> userroleDao) {
		this.userroleDao = userroleDao;
	}

	public BaseDaoI<Tuser> getUserDao() {
		return userDao;
	}

	@Autowired
	public void setUserDao(BaseDaoI<Tuser> userDao) {
		this.userDao = userDao;
	}

	public BaseDaoI<Tdept> getDeptDao() {
		return deptDao;
	}
	
	@Autowired
	public void setDeptDao(BaseDaoI<Tdept> deptDao) {
		this.deptDao = deptDao;
	}

	public User login(User user) {
		Tuser t = userDao.get("from Tuser t where t.cname = ? and t.cpwd = ?", new Object[] { user.getCname(), Encrypt.e(user.getCpwd()) });
		if (t != null) {
			user.setCid(t.getCid());
			user.setCname(t.getCname());
			user.setCemail(t.getCemail());
			user.setCrealname(t.getCrealname());
			user.setCcreatedatetime(t.getCrtTime());
			user.setCmodifydatetime(t.getUpdTime());
			user.setCstatus(t.getCstatus());
			user.setUserCode(t.getUserCode());
			user.setUserGroup(t.getUserGroup());
			if(t.getTdept()!=null){
				user.setDeptId(t.getTdept().getCid());
				user.setDeptName(t.getTdept().getCname());
			}

			Map<String, String> authIdsMap = new HashMap<String, String>();
			String authIds = "";
			String authNames = "";
			Map<String, String> authUrlsMap = new HashMap<String, String>();
			String authUrls = "";
			String roleIds = "";
			String roleNames = "";
			boolean b1 = false;
			Set<Tusertrole> tusertroles = t.getTusertroles();
			if (tusertroles != null && tusertroles.size() > 0) {
				for (Tusertrole tusertrole : tusertroles) {
					Trole trole = tusertrole.getTrole();
					if (b1) {
						roleIds += ",";
						roleNames += ",";
					}
					roleIds += trole.getCid();
					roleNames += trole.getCname();
					b1 = true;

					Set<Troletauth> troletauths = trole.getTroletauths();
					if (troletauths != null && troletauths.size() > 0) {
						for (Troletauth troletauth : troletauths) {
							Tauth tauth = troletauth.getTauth();
							authIdsMap.put(tauth.getCid(), tauth.getCname());
							authUrlsMap.put(tauth.getCid(), tauth.getCurl());
						}
					}
				}
			}
			boolean b2 = false;
			for (String id : authIdsMap.keySet()) {
				if (b2) {
					authIds += ",";
					authNames += ",";
				}
				authIds += id;
				authNames += authIdsMap.get(id);
				b2 = true;
			}
			user.setAuthIds(authIds);
			user.setAuthNames(authNames);
			user.setRoleIds(roleIds);
			user.setRoleNames(roleNames);
			boolean b3 = false;
			for (String id : authUrlsMap.keySet()) {
				if (b3) {
					authUrls += ",";
				}
				authUrls += authUrlsMap.get(id);
				b3 = true;
			}
			user.setAuthUrls(authUrls);

			return user;
		}
		return null;
	}

	public void save(User user) throws Exception {
		Tuser u = new Tuser();
		System.out.println("======"+user.getCname());
		if (isUniqueUser(user.getCname(), null)) {
			throw new ValidateFieldsException("登录账号已存在,请重新输入. ");
		}
		u.setCname(user.getCname());
		u.setMobile(user.getMobile());
		u.setCemail(user.getCemail());
		u.setCrealname(user.getCrealname());
		u.setCstatus(user.getCstatus());
		u.setUserCode(user.getUserCode());
		u.setUserGroup(user.getUserGroup());
		if(user.getRoleIds()!=null && !"".equals(user.getRoleIds())) {
			if(user.getRoleIds().indexOf(Constants.GLY_ROLE_CID)!=-1){
				u.setUserCode("");
				u.setUserGroup("3");
			}
			if(user.getRoleIds().indexOf(Constants.ZFB_ROLE_CID)!=-1){
				u.setUserCode(Constants.ZFB_CADE_CID);
				u.setUserGroup("4");
			}
			if(user.getRoleIds().indexOf(Constants.SWB_ROLE_CID)!=-1){
				u.setUserCode(Constants.SWB_CADE_CID);
				u.setUserGroup("4");
			}
			if(user.getRoleIds().indexOf(Constants.RD_ROLE_CID)!=-1){
				u.setUserCode(Constants.RD_CADE_CID);
				u.setUserGroup("4");
			}
			if(user.getRoleIds().indexOf(Constants.ZX_ROLE_CID)!=-1){
				u.setUserCode(Constants.ZX_CADE_CID);
				u.setUserGroup("4");
			}
			if(user.getRoleIds().indexOf(Constants.FY_ROLE_CID)!=-1){
				u.setUserCode(Constants.FY_CADE_CID);
				u.setUserGroup("4");
			}
			if(user.getRoleIds().indexOf(Constants.JCY_ROLE_CID)!=-1){
				u.setUserCode(Constants.JCY_CADE_CID);
				u.setUserGroup("4");
			}
			if(user.getRoleIds().indexOf(Constants.JFQ_ROLE_CID)!=-1){
				u.setUserCode(Constants.JFQ_CADE_CID);
				u.setUserGroup("4");
			}
			if(user.getRoleIds().indexOf(Constants.POLLXXY_ROLE_CID)!=-1){
				u.setUserCode(Constants.JFQ_CADE_CID);
				u.setUserGroup("5");
			}
			if(user.getRoleIds().indexOf(Constants.POLLSP_ROLE_CID)!=-1){
				u.setUserCode(Constants.JFQ_CADE_CID);
				u.setUserGroup("6");
			}
			if(user.getRoleIds().indexOf(Constants.POLLSQ_ROLE_CID)!=-1){
				u.setUserCode(Constants.JFQ_CADE_CID);
				u.setUserGroup("7");
			}
		}
		u.setCid(UUID.randomUUID().toString());
		if (user.getCcreatedatetime() == null) {
			u.setCrtTime(new Date());
		}
		if (user.getCmodifydatetime() == null) {
			u.setUpdTime(new Date());
		}
		u.setCpwd(Encrypt.e(user.getCpwd()));
		
		if(user.getDeptId()!=null)u.setTdept(deptDao.get(Tdept.class, user.getDeptId()));
		
		userDao.save(u);
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_SYS);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_ADD);
		dolog.setKeyId(u.getCid());
		dolog.setInfo( "[新增用户]添加用户名："+u.getCname()+",名称："+u.getCrealname()+"  的新用户");
		this.saveLog(dolog);
		this.saveUserRole(user, u);
	}
	public void reg(User user) throws Exception {
		Tuser u = new Tuser();
		if (isUniqueUser(user.getCname(), null)) {
			throw new ValidateFieldsException("登录账号已存在,请重新输入. ");
		}
		u.setCname(user.getCname());
		u.setMobile(user.getMobile());
		u.setCemail(user.getCemail());
		u.setCrealname(user.getCrealname());
		u.setCstatus(Constants.CODE_TYPE_QYTY_YES); //1:启用/0:停用状态
		u.setUserGroup(user.getUserGroup());
		//组别：学生
		if(Constants.DIC_TYPE_YHZB_STU.equals(user.getUserGroup())){
			user.setRoleIds(Constants.NEWSTU_ROLE_CID);
		}
		//组别：教师
		if(Constants.DIC_TYPE_YHZB_TEACHER.equals(user.getUserGroup())){
			user.setRoleIds(Constants.NEWTEACHER_ROLE_CID);
		}
//		u.setUserCode(user.getUserCode());
//		u.setUserGroup(user.getUserGroup());
//		if(user.getRoleIds()!=null && !"".equals(user.getRoleIds())) {
//			if(user.getRoleIds().indexOf(Constants.GLY_ROLE_CID)!=-1){
//				u.setUserCode("");
//				u.setUserGroup("3");
//			}
//			if(user.getRoleIds().indexOf(Constants.ZFB_ROLE_CID)!=-1){
//				u.setUserCode(Constants.ZFB_CADE_CID);
//				u.setUserGroup("4");
//			}
//			if(user.getRoleIds().indexOf(Constants.SWB_ROLE_CID)!=-1){
//				u.setUserCode(Constants.SWB_CADE_CID);
//				u.setUserGroup("4");
//			}
//			if(user.getRoleIds().indexOf(Constants.RD_ROLE_CID)!=-1){
//				u.setUserCode(Constants.RD_CADE_CID);
//				u.setUserGroup("4");
//			}
//			if(user.getRoleIds().indexOf(Constants.ZX_ROLE_CID)!=-1){
//				u.setUserCode(Constants.ZX_CADE_CID);
//				u.setUserGroup("4");
//			}
//			if(user.getRoleIds().indexOf(Constants.FY_ROLE_CID)!=-1){
//				u.setUserCode(Constants.FY_CADE_CID);
//				u.setUserGroup("4");
//			}
//			if(user.getRoleIds().indexOf(Constants.JCY_ROLE_CID)!=-1){
//				u.setUserCode(Constants.JCY_CADE_CID);
//				u.setUserGroup("4");
//			}
//			if(user.getRoleIds().indexOf(Constants.JFQ_ROLE_CID)!=-1){
//				u.setUserCode(Constants.JFQ_CADE_CID);
//				u.setUserGroup("4");
//			}
//			if(user.getRoleIds().indexOf(Constants.POLLXXY_ROLE_CID)!=-1){
//				u.setUserCode(Constants.JFQ_CADE_CID);
//				u.setUserGroup("5");
//			}
//			if(user.getRoleIds().indexOf(Constants.POLLSP_ROLE_CID)!=-1){
//				u.setUserCode(Constants.JFQ_CADE_CID);
//				u.setUserGroup("6");
//			}
//			if(user.getRoleIds().indexOf(Constants.POLLSQ_ROLE_CID)!=-1){
//				u.setUserCode(Constants.JFQ_CADE_CID);
//				u.setUserGroup("7");
//			}
//		}
		u.setCid(UUID.randomUUID().toString());
		u.setCrtTime(new Date());
//		if (user.getCmodifydatetime() == null) {
//			u.setUpdTime(new Date());
//		}
		u.setCpwd(Encrypt.e(user.getCpwd()));
		
//		if(user.getDeptId()!=null)u.setTdept(deptDao.get(Tdept.class, user.getDeptId()));
		
		userDao.save(u);
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_SYS);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_ADD);
		dolog.setKeyId(u.getCid());
		dolog.setInfo( "[新增用户]添加用户名："+u.getCname()+",名称："+u.getCrealname()+"  的新用户");
		this.saveLog(dolog);
		this.saveUserRole(user, u);
	}
	public void changePass(User user) throws Exception {
		Tuser u = userDao.get(Tuser.class, user.getCid());
		if (u==null) {
			throw new Exception("用户名不存在！ ");
		}
		if(!u.getCpwd().equals(Encrypt.e(user.getOldpass()))){
			throw new Exception("原始密码不正确");
		}
		u.setCpwd(Encrypt.e(user.getCpwd()));
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_SYS);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_EDIT);
		dolog.setKeyId(u.getCid());
		dolog.setInfo( "[密码修改]用户名："+u.getCname()+",名称："+u.getCrealname()+"  修改账号密码");
		this.saveLog(dolog);
		userDao.update(u);
	}
	

	/**
	 * 保存用户和角色的关系
	 * 
	 * @param user
	 * @param u
	 */
	private void saveUserRole(User user, Tuser u) {
		userroleDao.executeHql("delete Tusertrole t where t.tuser = ?", new Object[] { u });
		if (user.getRoleIds() != null) {
			for (String id : user.getRoleIds().split(",")) {
				Tusertrole tusertrole = new Tusertrole();
				tusertrole.setCid(UUID.randomUUID().toString());
				tusertrole.setTrole(roleDao.get(Trole.class, id.trim()));
				tusertrole.setTuser(u);
				System.out.println(u.getCid());
				userroleDao.save(tusertrole);
			}
		}
	}

	public void update(User user) throws Exception {
		if (isUniqueUser(user.getCname(), user.getCid())) {
			throw new ValidateFieldsException("登录账号已存在,请重新输入. ");
		}
		Tuser u = userDao.get(Tuser.class, user.getCid());
		if(user.getCpwd()!=null&&!"".equals(user.getCpwd())){
				u.setCpwd(Encrypt.e(user.getCpwd()));
		}
		
		if(user.getCemail()!=null&&!"".equals(user.getCemail())){
			u.setCemail(user.getCemail());
		}
		if(user.getCrealname()!=null&&!"".equals(user.getCrealname())){
			u.setCrealname(user.getCrealname());
		}
		if(user.getDeptId()!=null&&!"".equals(user.getDeptId())){
			u.setTdept(deptDao.get(Tdept.class, user.getDeptId()));
		}
		if(user.getCstatus()!=null&&!"".equals(user.getCstatus())){
			u.setCstatus(user.getCstatus());
		}
		if (user.getCmodifydatetime() == null) {
			u.setUpdTime(new Date());
		}
		if(user.getUserCode()!=null&& !user.getUserCode().trim().equals("")){
			u.setUserCode(user.getUserCode());
		}
		if(user.getUserGroup()!=null&& !user.getUserGroup().trim().equals("")){
			u.setUserGroup(user.getUserGroup());
		}
		if(user.getRoleIds().indexOf(Constants.GLY_ROLE_CID)!=-1){
			u.setUserCode("");
			u.setUserGroup("3");
		}
		if(user.getRoleIds().indexOf(Constants.SWB_ROLE_CID)!=-1){
			u.setUserCode(Constants.SWB_CADE_CID);
			u.setUserGroup("4");
		}
		if(user.getRoleIds().indexOf(Constants.RD_ROLE_CID)!=-1){
			u.setUserCode(Constants.RD_CADE_CID);
			u.setUserGroup("4");
		}
		if(user.getRoleIds().indexOf(Constants.ZX_ROLE_CID)!=-1){
			u.setUserCode(Constants.ZX_CADE_CID);
			u.setUserGroup("4");
		}
		if(user.getRoleIds().indexOf(Constants.FY_ROLE_CID)!=-1){
			u.setUserCode(Constants.FY_CADE_CID);
			u.setUserGroup("4");
		}
		if(user.getRoleIds().indexOf(Constants.JCY_ROLE_CID)!=-1){
			u.setUserCode(Constants.JCY_CADE_CID);
			u.setUserGroup("4");
		}
		if(user.getRoleIds().indexOf(Constants.JFQ_ROLE_CID)!=-1){
			u.setUserCode(Constants.JFQ_CADE_CID);
			u.setUserGroup("4");
		}
		userDao.saveOrUpdate(u);
		
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_SYS);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_EDIT);
		dolog.setKeyId(u.getCid());
		dolog.setInfo( "[修改用户]用户名："+u.getCname()+",名称："+u.getCrealname()+"  的用户信息修改");
		this.saveLog(dolog);
		this.saveUserRole(user, u);
	}

	/**
	 * 判断用户名是否唯一
	 * 
	 * @param user
	 * @param u
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public boolean isUniqueUser(String userName, String id) {
		Tuser tu = null;
		if (id == null) {
			tu = this.userDao.get("from Tuser t where t.cname = ?", new String[] { userName });
			if (tu == null){
				return false;
			}
		}else{
			tu = this.userDao.get("from Tuser t where t.cname = ?", new String[] { userName });
			if (tu == null){
				return false;
			}
			if(tu!=null&&tu.getCid().equals(id)){
				return false;
			}
		}
		return true;
	}
	/**
	 * 判断用户名是否唯一
	 * 
	 * @param user
	 * @param u
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public boolean isValidUser(String userName) {
		List<Tuser> list = null;
		if (userName != null && !"".equals(userName)) {
			list=userDao.find("from Tuser t where t.cname = '" +userName+"'");
			if (list != null && list.size()>0){
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(User user) {
		DataGrid j = new DataGrid();
		j.setRows(this.changeModel(this.find(user)));
		j.setTotal(this.total(user));
		return j;
	}

	private List<User> changeModel(List<Tuser> tusers) {
		List<User> users = new ArrayList<User>();
		if (tusers != null && tusers.size() > 0) {
			for (Tuser tu : tusers) {
				User u = new User();
				u.setCsn(tu.getCsn());
				u.setCid(tu.getCid());
				u.setCname(tu.getCname());
				u.setCemail(tu.getCemail());
				u.setCrealname(tu.getCrealname());
				u.setCcreatedatetime(tu.getCrtTime());
				u.setCmodifydatetime(tu.getUpdTime());
				u.setCstatus(tu.getCstatus());
				u.setUserCode(tu.getUserCode());
				u.setUserGroup(tu.getUserGroup());
				u.setUserGroupName(this.findDicName("YHZB", tu.getUserGroup()));
				
				if(tu.getTdept()!=null)u.setDeptId(tu.getTdept().getCid());
				if(tu.getTdept()!=null)u.setDeptName(tu.getTdept().getCname());

				Set<Tusertrole> tusertroles = tu.getTusertroles();
				String roleIds = "";
				String roleNames = "";
				boolean b = false;
				if (tusertroles != null && tusertroles.size() > 0) {
					for (Tusertrole tusertrole : tusertroles) {
						if (tusertrole.getTrole() != null) {
							if (b) {
								roleIds += ",";
								roleNames += ",";
							}
							roleIds += tusertrole.getTrole().getCid();
							roleNames += tusertrole.getTrole().getCname();
							b = true;
						}
					}
				}
				u.setRoleIds(roleIds);
				u.setRoleNames(roleNames);

				users.add(u);
			}
		}
		return users;
	}

	private List<Tuser> find(User user) {
//		String hql = "from Tuser t where t.cid!='0' and (t.userGroup='"+Constants.DIC_TYPE_YHZB_GLY+"' or t.userGroup='"+Constants.DIC_TYPE_YHZB_LDSP+"')";

		//		String hql = "from Tuser t where t.cid!='0' and (t.userGroup='"+Constants.DIC_TYPE_YHZB_GLY+"'"
//				+" or t.userGroup='"+Constants.DIC_TYPE_YHZB_LDSP+"'" 
//				+" or t.userGroup='"+Constants.DIC_TYPE_YHZB_XXY+"'" 
//				+" or t.userGroup='"+Constants.DIC_TYPE_YHZB_XXCZ+"'" 
//				+" or t.userGroup='"+Constants.DIC_TYPE_YHZB_MSZ+"'" 
//				+")";
		//=>
		String hql = "from Tuser t where t.cid!='0'";
		
		
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(user, hql, values);

		if (user.getSort() != null && user.getOrder() != null) {
			hql += " order by " + user.getSort() + " " + user.getOrder();
		}
		return userDao.find(hql, values, user.getPage(), user.getRows());
	}

	private Long total(User user) {
//		String hql = "select count(*) from Tuser t where (t.userGroup='"+Constants.DIC_TYPE_YHZB_GLY+"' or t.userGroup='"+Constants.DIC_TYPE_YHZB_LDSP+"') ";
	
//		String hql = "select count(*) from Tuser t where t.cid!='0' and (t.userGroup='"+Constants.DIC_TYPE_YHZB_GLY+"'"
//				+" or t.userGroup='"+Constants.DIC_TYPE_YHZB_LDSP+"'" 
//				+" or t.userGroup='"+Constants.DIC_TYPE_YHZB_XXY+"'" 
//				+" or t.userGroup='"+Constants.DIC_TYPE_YHZB_XXCZ+"'" 
//				+" or t.userGroup='"+Constants.DIC_TYPE_YHZB_MSZ+"'" 
//				+")";
	//=>
		String hql = "select count(*) from Tuser t where t.cid!='0'";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(user, hql, values);
		return userDao.count(hql, values);
	}

	private String addWhere(User user, String hql, List<Object> values) {
		if (user.getCname() != null && !user.getCname().trim().equals("")) {
			hql += " and t.cname like ? ";
			values.add("%" + user.getCname().trim() + "%");
		}
		if (user.getUserGroup() != null) {
			hql += " and t.userGroup=? ";
			values.add(user.getUserGroup());
		}
		if (user.getCcreatedatetimeStart() != null) {
			hql += " and t.ccreatedatetime>=? ";
			values.add(user.getCcreatedatetimeStart());
		}
		if (user.getCcreatedatetimeEnd() != null) {
			hql += " and t.ccreatedatetime<=? ";
			values.add(user.getCcreatedatetimeEnd());
		}
		if (user.getCmodifydatetimeStart() != null) {
			hql += " and t.cmodifydatetime>=? ";
			values.add(user.getCmodifydatetimeStart());
		}
		if (user.getCmodifydatetimeEnd() != null) {
			hql += " and t.cmodifydatetime<=? ";
			values.add(user.getCmodifydatetimeEnd());
		}
		return hql;
	}

	public void delete(String ids) throws Exception {
		if (ids != null) {
			for (String id : ids.split(",")) {
				if (!id.trim().equals("0")) {
					Tuser u = userDao.get(Tuser.class, id.trim());
					if (u != null) {
						userroleDao.executeHql("delete Tusertrole t where t.tuser = ?", new Object[] { u });
						userDao.delete(u);
					}
				}
			}
			Tdolog dolog=new Tdolog();
			dolog.setLogType(Constants.LOG_TYPE_CODE_SYS);
			dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_DEL);
			dolog.setInfo( "[删除用户]用户ID："+ids+"  的用户信息");
			this.saveLog(dolog);
		}
	}

	public void roleEdit(User user) throws Exception {
		if (user.getIds() != null) {
			for (String id : user.getIds().split(",")) {
				Tuser u = userDao.get(Tuser.class, id);
				this.saveUserRole(user, u);
			}
			Tdolog dolog=new Tdolog();
			dolog.setLogType(Constants.LOG_TYPE_CODE_SYS);
			dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_EDIT);
			dolog.setInfo( "[修改用户角色]用户ID："+user.getIds()+"  的用户角色");
			this.saveLog(dolog);
		}
	}

	public void editUserInfo(User user) {
		if (user.getCpwd() != null && !user.getCpwd().trim().equals("")) {
			Tuser t = userDao.get(Tuser.class, user.getCid());
			t.setCpwd(Encrypt.e(user.getCpwd()));
		}
	}
	
	public String findUserID(User user){
		List<Tuser> list=userDao.find("from Tuser t where t.userCode='" + user.getUserCode() + "' and t.userGroup='" + user.getUserGroup() + "'");
		if(list!=null && list.size()>0)return list.get(0).getCid();
		return "";
	}
	
	public Tuser findByUserCode(String userCode){
		if(userCode!=null && !"".equals(userCode)) {
			Tuser tuser=userDao.get("from Tuser t where t.userCode=? and t.userGroup=? ",new Object[] {userCode,"1"});
			return tuser;
		} else {
			return null;
		}
	}
}
