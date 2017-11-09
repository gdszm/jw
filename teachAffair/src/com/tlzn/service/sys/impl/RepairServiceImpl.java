package com.tlzn.service.sys.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.sys.Tauth;
import com.tlzn.model.sys.Tdept;
import com.tlzn.model.sys.Tmenu;
import com.tlzn.model.sys.Trole;
import com.tlzn.model.sys.Troletauth;
import com.tlzn.model.sys.Tuser;
import com.tlzn.model.sys.Tusertrole;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.sys.RepairServiceI;
import com.tlzn.util.base.Encrypt;

@Service("repairService")
public class RepairServiceImpl extends BaseServiceImpl implements RepairServiceI {

	private BaseDaoI<Tuser> userDao;
	private BaseDaoI<Tdept> deptDao;
	private BaseDaoI<Tmenu> menuDao;
	private BaseDaoI<Tauth> authDao;
	private BaseDaoI<Trole> roleDao;
	private BaseDaoI<Tusertrole> userroleDao;
	private BaseDaoI<Troletauth> roleauthDao;


	public BaseDaoI<Troletauth> getRoleauthDao() {
		return roleauthDao;
	}

	@Autowired
	public void setRoleauthDao(BaseDaoI<Troletauth> roleauthDao) {
		this.roleauthDao = roleauthDao;
	}

	public BaseDaoI<Tusertrole> getUserroleDao() {
		return userroleDao;
	}

	@Autowired
	public void setUserroleDao(BaseDaoI<Tusertrole> userroleDao) {
		this.userroleDao = userroleDao;
	}

	public BaseDaoI<Trole> getRoleDao() {
		return roleDao;
	}

	@Autowired
	public void setRoleDao(BaseDaoI<Trole> roleDao) {
		this.roleDao = roleDao;
	}

	public BaseDaoI<Tauth> getAuthDao() {
		return authDao;
	}

	@Autowired
	public void setAuthDao(BaseDaoI<Tauth> authDao) {
		this.authDao = authDao;
	}


	public BaseDaoI<Tmenu> getMenuDao() {
		return menuDao;
	}

	@Autowired
	public void setMenuDao(BaseDaoI<Tmenu> menuDao) {
		this.menuDao = menuDao;
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

	synchronized public void deleteAndRepair() {
		menuDao.executeHql("update Tmenu t set t.tmenu = null");
		menuDao.executeHql("delete Tmenu");
		roleauthDao.executeHql("delete Troletauth");
		userroleDao.executeHql("delete Tusertrole");
		authDao.executeHql("update Tauth t set t.tauth = null");
		authDao.executeHql("delete Tauth");
		roleDao.executeHql("delete Trole");
		userDao.executeHql("delete Tuser");
		repair();
	}

	synchronized public void repair() {
		repairMenu();// 修复菜单
		repairAuth();// 修复权限
		repairRole();// 修复角色
		repairDept();// 修复用户
		repairUser();// 修复用户
		repairRoleAuth();// 修复角色和权限的关系
		repairUserRole();// 修复用户和角色的关系
	}

	private void repairUserRole() {
		userroleDao.executeHql("delete Tusertrole t where t.tuser.cid in ( '0' )");

		Tusertrole userrole = new Tusertrole();
		userrole.setCid(UUID.randomUUID().toString());
		userrole.setTrole(roleDao.get(Trole.class, "0"));
		userrole.setTuser(userDao.get(Tuser.class, "0"));
		userroleDao.save(userrole);
	}

	private void repairRoleAuth() {
		roleauthDao.executeHql("delete Troletauth t where t.trole.cid = '0'");

		Trole role = roleDao.get(Trole.class, "0");

		List<Tauth> auths = authDao.find("from Tauth");
		if (auths != null && auths.size() > 0) {
			for (Tauth auth : auths) {
				Troletauth roleauth = new Troletauth();
				roleauth.setCid(UUID.randomUUID().toString());
				roleauth.setTrole(role);
				roleauth.setTauth(auth);
				roleauthDao.save(roleauth);
			}
		}
	}

	private void repairRole() {
		Trole admin = new Trole();
		admin.setCid("0");
		admin.setCname("超级管理员");
		admin.setCdesc("拥有系统所有权限");
		roleDao.saveOrUpdate(admin);

		Trole guest = new Trole();
		guest.setCid("1");
		guest.setCname("Guest");
		guest.setCdesc("");
		roleDao.saveOrUpdate(guest);
	}

	private void repairAuth() {
		authDao.executeHql("update Tauth a set a.tauth = null");

		Tauth sshe = new Tauth();
		sshe.setCid("0");
		sshe.setTauth(null);
		sshe.setCname("首页");
		sshe.setCurl("");
		sshe.setCseq(BigDecimal.valueOf(1));
		sshe.setCdesc("基础开发平台");
		authDao.saveOrUpdate(sshe);

		Tauth sjkgl = new Tauth();
		sjkgl.setCid("sjkgl");
		sjkgl.setTauth(sshe);
		sjkgl.setCname("数据库管理");
		sjkgl.setCurl("");
		sjkgl.setCseq(BigDecimal.valueOf(1));
		sjkgl.setCdesc("可查看数据库链接信息，SQL语句执行情况");
		authDao.saveOrUpdate(sjkgl);

		Tauth ljcjk = new Tauth();
		ljcjk.setCid("ljcjk");
		ljcjk.setTauth(sjkgl);
		ljcjk.setCname("连接池监控");
		ljcjk.setCurl("/datasource!druid.do");
		ljcjk.setCseq(BigDecimal.valueOf(1));
		ljcjk.setCdesc("可查看数据库链接信息");
		authDao.saveOrUpdate(ljcjk);

		Tauth xtgl = new Tauth();
		xtgl.setCid("xtgl");
		xtgl.setTauth(sshe);
		xtgl.setCname("系统管理");
		xtgl.setCurl("");
		xtgl.setCseq(BigDecimal.valueOf(2));
		xtgl.setCdesc("");
		authDao.saveOrUpdate(xtgl);

		Tauth yhgl = new Tauth();
		yhgl.setCid("yhgl");
		yhgl.setTauth(xtgl);
		yhgl.setCname("用户管理");
		yhgl.setCurl("");
		yhgl.setCseq(BigDecimal.valueOf(1));
		yhgl.setCdesc("");
		authDao.saveOrUpdate(yhgl);

		Tauth yhglym = new Tauth();
		yhglym.setCid("yhglym");
		yhglym.setTauth(yhgl);
		yhglym.setCname("用户管理页面");
		yhglym.setCurl("/user!user.do");
		yhglym.setCseq(BigDecimal.valueOf(1));
		yhglym.setCdesc("");
		authDao.saveOrUpdate(yhglym);

		Tauth yhcx = new Tauth();
		yhcx.setCid("yhcx");
		yhcx.setTauth(yhgl);
		yhcx.setCname("用户查询");
		yhcx.setCurl("/user!datagrid.do");
		yhcx.setCseq(BigDecimal.valueOf(2));
		yhcx.setCdesc("");
		authDao.saveOrUpdate(yhcx);

		Tauth yhaddym = new Tauth();
		yhaddym.setCid("yhaddym");
		yhaddym.setTauth(yhgl);
		yhaddym.setCname("添加用户页面");
		yhaddym.setCurl("/user!userAdd.do");
		yhaddym.setCseq(BigDecimal.valueOf(3));
		yhaddym.setCdesc("");
		authDao.saveOrUpdate(yhaddym);

		Tauth yhadd = new Tauth();
		yhadd.setCid("yhadd");
		yhadd.setTauth(yhgl);
		yhadd.setCname("用户添加");
		yhadd.setCurl("/user!add.do");
		yhadd.setCseq(BigDecimal.valueOf(4));
		yhadd.setCdesc("");
		authDao.saveOrUpdate(yhadd);

		Tauth yheditym = new Tauth();
		yheditym.setCid("yheditym");
		yheditym.setTauth(yhgl);
		yheditym.setCname("修改用户页面");
		yheditym.setCurl("/user!userEdit.do");
		yheditym.setCseq(BigDecimal.valueOf(5));
		yheditym.setCdesc("");
		authDao.saveOrUpdate(yheditym);

		Tauth yhedit = new Tauth();
		yhedit.setCid("yhedit");
		yhedit.setTauth(yhgl);
		yhedit.setCname("用户修改");
		yhedit.setCurl("/user!edit.do");
		yhedit.setCseq(BigDecimal.valueOf(6));
		yhedit.setCdesc("");
		authDao.saveOrUpdate(yhedit);

		Tauth yhsc = new Tauth();
		yhsc.setCid("yhsc");
		yhsc.setTauth(yhgl);
		yhsc.setCname("用户删除");
		yhsc.setCurl("/user!delete.do");
		yhsc.setCseq(BigDecimal.valueOf(7));
		yhsc.setCdesc("");
		authDao.saveOrUpdate(yhsc);

		Tauth yhxgjsym = new Tauth();
		yhxgjsym.setCid("yhxgjsym");
		yhxgjsym.setTauth(yhgl);
		yhxgjsym.setCname("修改角色页面");
		yhxgjsym.setCurl("/user!userRoleEdit.do");
		yhxgjsym.setCseq(BigDecimal.valueOf(8));
		yhxgjsym.setCdesc("批量修改用户角色");
		authDao.saveOrUpdate(yhxgjsym);

		Tauth yhxgjs = new Tauth();
		yhxgjs.setCid("yhxgjs");
		yhxgjs.setTauth(yhgl);
		yhxgjs.setCname("修改角色");
		yhxgjs.setCurl("/user!roleEdit.do");
		yhxgjs.setCseq(BigDecimal.valueOf(9));
		yhxgjs.setCdesc("批量修改用户角色");
		authDao.saveOrUpdate(yhxgjs);

		Tauth jsgl = new Tauth();
		jsgl.setCid("jsgl");
		jsgl.setTauth(xtgl);
		jsgl.setCname("角色管理");
		jsgl.setCurl("");
		jsgl.setCseq(BigDecimal.valueOf(2));
		jsgl.setCdesc("");
		authDao.saveOrUpdate(jsgl);

		Tauth jsglym = new Tauth();
		jsglym.setCid("jsglym");
		jsglym.setTauth(jsgl);
		jsglym.setCname("角色管理页面");
		jsglym.setCurl("/role!role.do");
		jsglym.setCseq(BigDecimal.valueOf(1));
		jsglym.setCdesc("");
		authDao.saveOrUpdate(jsglym);

		Tauth jscx = new Tauth();
		jscx.setCid("jscx");
		jscx.setTauth(jsgl);
		jscx.setCname("角色查询");
		jscx.setCurl("/role!datagrid.do");
		jscx.setCseq(BigDecimal.valueOf(2));
		jscx.setCdesc("");
		authDao.saveOrUpdate(jscx);

		Tauth jsaddym = new Tauth();
		jsaddym.setCid("jsaddym");
		jsaddym.setTauth(jsgl);
		jsaddym.setCname("添加角色页面");
		jsaddym.setCurl("/role!roleAdd.do");
		jsaddym.setCseq(BigDecimal.valueOf(3));
		jsaddym.setCdesc("");
		authDao.saveOrUpdate(jsaddym);

		Tauth jsadd = new Tauth();
		jsadd.setCid("jsadd");
		jsadd.setTauth(jsgl);
		jsadd.setCname("角色添加");
		jsadd.setCurl("/role!add.do");
		jsadd.setCseq(BigDecimal.valueOf(4));
		jsadd.setCdesc("");
		authDao.saveOrUpdate(jsadd);

		Tauth jseditym = new Tauth();
		jseditym.setCid("jseditym");
		jseditym.setTauth(jsgl);
		jseditym.setCname("编辑角色页面");
		jseditym.setCurl("/role!roleEdit.do");
		jseditym.setCseq(BigDecimal.valueOf(5));
		jseditym.setCdesc("");
		authDao.saveOrUpdate(jseditym);

		Tauth jsedit = new Tauth();
		jsedit.setCid("jsedit");
		jsedit.setTauth(jsgl);
		jsedit.setCname("角色编辑");
		jsedit.setCurl("/role!edit.do");
		jsedit.setCseq(BigDecimal.valueOf(6));
		jsedit.setCdesc("");
		authDao.saveOrUpdate(jsedit);

		Tauth jsdelete = new Tauth();
		jsdelete.setCid("jsdelete");
		jsdelete.setTauth(jsgl);
		jsdelete.setCname("角色删除");
		jsdelete.setCurl("/role!delete.do");
		jsdelete.setCseq(BigDecimal.valueOf(7));
		jsdelete.setCdesc("");
		authDao.saveOrUpdate(jsdelete);

		Tauth qxgl = new Tauth();
		qxgl.setCid("qxgl");
		qxgl.setTauth(xtgl);
		qxgl.setCname("权限管理");
		qxgl.setCurl("");
		qxgl.setCseq(BigDecimal.valueOf(3));
		qxgl.setCdesc("");
		authDao.saveOrUpdate(qxgl);

		Tauth qxglym = new Tauth();
		qxglym.setCid("qxglym");
		qxglym.setTauth(qxgl);
		qxglym.setCname("权限管理页面");
		qxglym.setCurl("/auth!auth.do");
		qxglym.setCseq(BigDecimal.valueOf(1));
		qxglym.setCdesc("");
		authDao.saveOrUpdate(qxglym);

		Tauth qxcx = new Tauth();
		qxcx.setCid("qxcx");
		qxcx.setTauth(qxgl);
		qxcx.setCname("权限查询");
		qxcx.setCurl("/auth!treegrid.do");
		qxcx.setCseq(BigDecimal.valueOf(2));
		qxcx.setCdesc("");
		authDao.saveOrUpdate(qxcx);

		Tauth qxaddym = new Tauth();
		qxaddym.setCid("qxaddym");
		qxaddym.setTauth(qxgl);
		qxaddym.setCname("添加权限页面");
		qxaddym.setCurl("/auth!authAdd.do");
		qxaddym.setCseq(BigDecimal.valueOf(3));
		qxaddym.setCdesc("");
		authDao.saveOrUpdate(qxaddym);

		Tauth qxadd = new Tauth();
		qxadd.setCid("qxadd");
		qxadd.setTauth(qxgl);
		qxadd.setCname("权限添加");
		qxadd.setCurl("/auth!add.do");
		qxadd.setCseq(BigDecimal.valueOf(4));
		qxadd.setCdesc("");
		authDao.saveOrUpdate(qxadd);

		Tauth qxeditym = new Tauth();
		qxeditym.setCid("qxeditym");
		qxeditym.setTauth(qxgl);
		qxeditym.setCname("编辑权限页面");
		qxeditym.setCurl("/auth!authEdit.do");
		qxeditym.setCseq(BigDecimal.valueOf(5));
		qxeditym.setCdesc("");
		authDao.saveOrUpdate(qxeditym);

		Tauth qxedit = new Tauth();
		qxedit.setCid("qxedit");
		qxedit.setTauth(qxgl);
		qxedit.setCname("权限编辑");
		qxedit.setCurl("/auth!edit.do");
		qxedit.setCseq(BigDecimal.valueOf(6));
		qxedit.setCdesc("");
		authDao.saveOrUpdate(qxedit);

		Tauth qxdelete = new Tauth();
		qxdelete.setCid("qxdelete");
		qxdelete.setTauth(qxgl);
		qxdelete.setCname("权限删除");
		qxdelete.setCurl("/auth!delete.do");
		qxdelete.setCseq(BigDecimal.valueOf(7));
		qxdelete.setCdesc("");
		authDao.saveOrUpdate(qxdelete);

		Tauth cdgl = new Tauth();
		cdgl.setCid("cdgl");
		cdgl.setTauth(xtgl);
		cdgl.setCname("菜单管理");
		cdgl.setCurl("");
		cdgl.setCseq(BigDecimal.valueOf(4));
		cdgl.setCdesc("");
		authDao.saveOrUpdate(cdgl);

		Tauth cdglym = new Tauth();
		cdglym.setCid("cdglym");
		cdglym.setTauth(cdgl);
		cdglym.setCname("菜单管理页面");
		cdglym.setCurl("/menu!menu.do");
		cdglym.setCseq(BigDecimal.valueOf(1));
		cdglym.setCdesc("");
		authDao.saveOrUpdate(cdglym);

		Tauth cdcx = new Tauth();
		cdcx.setCid("cdcx");
		cdcx.setTauth(cdgl);
		cdcx.setCname("菜单查询");
		cdcx.setCurl("/menu!treegrid.do");
		cdcx.setCseq(BigDecimal.valueOf(2));
		cdcx.setCdesc("");
		authDao.saveOrUpdate(cdcx);

		Tauth cdaddym = new Tauth();
		cdaddym.setCid("cdaddym");
		cdaddym.setTauth(cdgl);
		cdaddym.setCname("添加菜单页面");
		cdaddym.setCurl("/menu!menuAdd.do");
		cdaddym.setCseq(BigDecimal.valueOf(3));
		cdaddym.setCdesc("");
		authDao.saveOrUpdate(cdaddym);

		Tauth cdadd = new Tauth();
		cdadd.setCid("cdadd");
		cdadd.setTauth(cdgl);
		cdadd.setCname("菜单添加");
		cdadd.setCurl("/menu!add.do");
		cdadd.setCseq(BigDecimal.valueOf(4));
		cdadd.setCdesc("");
		authDao.saveOrUpdate(cdadd);

		Tauth cdeditym = new Tauth();
		cdeditym.setCid("cdeditym");
		cdeditym.setTauth(cdgl);
		cdeditym.setCname("编辑菜单页面");
		cdeditym.setCurl("/menu!menuEdit.do");
		cdeditym.setCseq(BigDecimal.valueOf(5));
		cdeditym.setCdesc("");
		authDao.saveOrUpdate(cdeditym);

		Tauth cdedit = new Tauth();
		cdedit.setCid("cdedit");
		cdedit.setTauth(cdgl);
		cdedit.setCname("菜单编辑");
		cdedit.setCurl("/menu!edit.do");
		cdedit.setCseq(BigDecimal.valueOf(6));
		cdedit.setCdesc("");
		authDao.saveOrUpdate(cdedit);

		Tauth cddelete = new Tauth();
		cddelete.setCid("cddelete");
		cddelete.setTauth(cdgl);
		cddelete.setCname("菜单删除");
		cddelete.setCurl("/menu!delete.do");
		cddelete.setCseq(BigDecimal.valueOf(7));
		cddelete.setCdesc("");
		authDao.saveOrUpdate(cddelete);
		
		Tauth bmgl = new Tauth();
		bmgl.setCid("bmgl");
		bmgl.setTauth(xtgl);
		bmgl.setCname("部门管理");
		bmgl.setCurl("");
		bmgl.setCseq(BigDecimal.valueOf(5));
		bmgl.setCdesc("");
		authDao.saveOrUpdate(bmgl);

		Tauth bmglym = new Tauth();
		bmglym.setCid("bmglym");
		bmglym.setTauth(bmgl);
		bmglym.setCname("部门管理页面");
		bmglym.setCurl("/dept!dept.do");
		bmglym.setCseq(BigDecimal.valueOf(1));
		bmglym.setCdesc("");
		authDao.saveOrUpdate(bmglym);

		Tauth bmcx = new Tauth();
		bmcx.setCid("bmcx");
		bmcx.setTauth(bmgl);
		bmcx.setCname("部门查询");
		bmcx.setCurl("/dept!treegrid.do");
		bmcx.setCseq(BigDecimal.valueOf(2));
		bmcx.setCdesc("");
		authDao.saveOrUpdate(bmcx);

		Tauth bmaddym = new Tauth();
		bmaddym.setCid("bmaddym");
		bmaddym.setTauth(bmgl);
		bmaddym.setCname("添加部门页面");
		bmaddym.setCurl("/dept!deptAdd.do");
		bmaddym.setCseq(BigDecimal.valueOf(3));
		bmaddym.setCdesc("");
		authDao.saveOrUpdate(bmaddym);

		Tauth bmadd = new Tauth();
		bmadd.setCid("bmadd");
		bmadd.setTauth(bmgl);
		bmadd.setCname("部门菜单添加");
		bmadd.setCurl("/dept!add.do");
		bmadd.setCseq(BigDecimal.valueOf(4));
		bmadd.setCdesc("");
		authDao.saveOrUpdate(bmadd);

		Tauth bmeditym = new Tauth();
		bmeditym.setCid("bmeditym");
		bmeditym.setTauth(bmgl);
		bmeditym.setCname("编辑部门页面");
		bmeditym.setCurl("/dept!deptEdit.do");
		bmeditym.setCseq(BigDecimal.valueOf(5));
		bmeditym.setCdesc("");
		authDao.saveOrUpdate(bmeditym);

		Tauth bmedit = new Tauth();
		bmedit.setCid("bmedit");
		bmedit.setTauth(bmgl);
		bmedit.setCname("部门编辑");
		bmedit.setCurl("/dept!edit.do");
		bmedit.setCseq(BigDecimal.valueOf(6));
		bmedit.setCdesc("");
		authDao.saveOrUpdate(bmedit);

		Tauth bmdelete = new Tauth();
		bmdelete.setCid("bmdelete");
		bmdelete.setTauth(bmgl);
		bmdelete.setCname("部门删除");
		bmdelete.setCurl("/dept!delete.do");
		bmdelete.setCseq(BigDecimal.valueOf(7));
		bmdelete.setCdesc("");
		authDao.saveOrUpdate(bmdelete);

		Tauth buggl = new Tauth();
		buggl.setCid("buggl");
		buggl.setTauth(xtgl);
		buggl.setCname("BUG管理");
		buggl.setCurl("");
		buggl.setCseq(BigDecimal.valueOf(5));
		buggl.setCdesc("");
		authDao.saveOrUpdate(buggl);

		Tauth bugglym = new Tauth();
		bugglym.setCid("bugglym");
		bugglym.setTauth(buggl);
		bugglym.setCname("BUG管理页面");
		bugglym.setCurl("/bug!bug.do");
		bugglym.setCseq(BigDecimal.valueOf(1));
		bugglym.setCdesc("");
		authDao.saveOrUpdate(bugglym);

		Tauth bugcx = new Tauth();
		bugcx.setCid("bugcx");
		bugcx.setTauth(buggl);
		bugcx.setCname("BUG查询");
		bugcx.setCurl("/bug!datagrid.do");
		bugcx.setCseq(BigDecimal.valueOf(2));
		bugcx.setCdesc("");
		authDao.saveOrUpdate(bugcx);

		Tauth bugaddym = new Tauth();
		bugaddym.setCid("bugaddym");
		bugaddym.setTauth(buggl);
		bugaddym.setCname("上报BUG页面");
		bugaddym.setCurl("/bug!bugAdd.do");
		bugaddym.setCseq(BigDecimal.valueOf(3));
		bugaddym.setCdesc("");
		authDao.saveOrUpdate(bugaddym);

		Tauth bugadd = new Tauth();
		bugadd.setCid("bugadd");
		bugadd.setTauth(buggl);
		bugadd.setCname("上报BUG");
		bugadd.setCurl("/bug!add.do");
		bugadd.setCseq(BigDecimal.valueOf(4));
		bugadd.setCdesc("");
		authDao.saveOrUpdate(bugadd);

		Tauth bugeditym = new Tauth();
		bugeditym.setCid("bugeditym");
		bugeditym.setTauth(buggl);
		bugeditym.setCname("编辑BUG页面");
		bugeditym.setCurl("/bug!bugEdit.do");
		bugeditym.setCseq(BigDecimal.valueOf(5));
		bugeditym.setCdesc("");
		authDao.saveOrUpdate(bugeditym);

		Tauth bugedit = new Tauth();
		bugedit.setCid("bugedit");
		bugedit.setTauth(buggl);
		bugedit.setCname("BUG编辑");
		bugedit.setCurl("/bug!edit.do");
		bugedit.setCseq(BigDecimal.valueOf(6));
		bugedit.setCdesc("");
		authDao.saveOrUpdate(bugedit);

		Tauth bugdelete = new Tauth();
		bugdelete.setCid("bugdelete");
		bugdelete.setTauth(buggl);
		bugdelete.setCname("BUG删除");
		bugdelete.setCurl("/bug!delete.do");
		bugdelete.setCseq(BigDecimal.valueOf(7));
		bugdelete.setCdesc("");
		authDao.saveOrUpdate(bugdelete);

		Tauth bugupload = new Tauth();
		bugupload.setCid("bugupload");
		bugupload.setTauth(buggl);
		bugupload.setCname("BUG上传");
		bugupload.setCurl("/bug!upload.do");
		bugupload.setCseq(BigDecimal.valueOf(8));
		bugupload.setCdesc("");
		authDao.saveOrUpdate(bugupload);

		Tauth bugdesc = new Tauth();
		bugdesc.setCid("bugdesc");
		bugdesc.setTauth(buggl);
		bugdesc.setCname("查看BUG描述");
		bugdesc.setCurl("/bug!showCdesc.do");
		bugdesc.setCseq(BigDecimal.valueOf(9));
		bugdesc.setCdesc("");
		authDao.saveOrUpdate(bugdesc);

	}

	private void repairMenu() {

		Tmenu xtgl = new Tmenu();
		xtgl.setCid("xtgl");
		xtgl.setCname("系统管理");
		xtgl.setCseq(BigDecimal.valueOf(1));
		xtgl.setCurl("");
		xtgl.setCiconcls("icon-orange");
		xtgl.setTmenu(null);
		menuDao.saveOrUpdate(xtgl);

		Tmenu yhgl = new Tmenu();
		yhgl.setCid("yhgl");
		yhgl.setCname("用户管理");
		yhgl.setCseq(BigDecimal.valueOf(1));
		yhgl.setCurl("/user!user.do");
		yhgl.setTmenu(xtgl);
		yhgl.setCiconcls("icon-user");
		menuDao.saveOrUpdate(yhgl);
		
		Tmenu bmgl = new Tmenu();
		bmgl.setCid("bmgl");
		bmgl.setCname("部门管理");
		bmgl.setCseq(BigDecimal.valueOf(2));
		bmgl.setCurl("/dept!dept.do");
		bmgl.setCiconcls("icon-group");
		bmgl.setTmenu(xtgl);
		menuDao.saveOrUpdate(bmgl);

		Tmenu jsgl = new Tmenu();
		jsgl.setCid("jsgl");
		jsgl.setCname("角色管理");
		jsgl.setCseq(BigDecimal.valueOf(3));
		jsgl.setCurl("/role!role.do");
		jsgl.setCiconcls("icon-role");
		jsgl.setTmenu(xtgl);
		menuDao.saveOrUpdate(jsgl);

		Tmenu qxgl = new Tmenu();
		qxgl.setCid("qxgl");
		qxgl.setCname("权限管理");
		qxgl.setCseq(BigDecimal.valueOf(4));
		qxgl.setCurl("/auth!auth.do");
		qxgl.setTmenu(xtgl);
		qxgl.setCiconcls("icon-auth");
		menuDao.saveOrUpdate(qxgl);

		Tmenu cdgl = new Tmenu();
		cdgl.setCid("cdgl");
		cdgl.setCname("菜单管理");
		cdgl.setCseq(BigDecimal.valueOf(5));
		cdgl.setCurl("/menu!menu.do");
		cdgl.setCiconcls("icon-menu");
		cdgl.setTmenu(xtgl);
		menuDao.saveOrUpdate(cdgl);
		
		Tmenu sjzd = new Tmenu();
		sjzd.setCid("sjzd");
		sjzd.setCname("数据字典");
		sjzd.setCseq(BigDecimal.valueOf(6));
		sjzd.setCurl("/dic!dic.do");
		sjzd.setCiconcls("icon-dic");
		sjzd.setTmenu(xtgl);
		menuDao.saveOrUpdate(sjzd);

		Tmenu buggl = new Tmenu();
		buggl.setCid("buggl");
		buggl.setCname("BUG管理");
		buggl.setCseq(BigDecimal.valueOf(7));
		buggl.setCurl("/bug!bug.do");
		buggl.setCiconcls("icon-bug");
		buggl.setTmenu(xtgl);
		menuDao.saveOrUpdate(buggl);
		
		
		
		Tmenu sjkgl = new Tmenu();
		sjkgl.setCid("sjkgl");
		sjkgl.setCname("数据库管理");
		sjkgl.setCseq(BigDecimal.valueOf(3));
		sjkgl.setCurl("");
		sjkgl.setTmenu(null);
		sjkgl.setCiconcls("icon-database");
		menuDao.saveOrUpdate(sjkgl);

		Tmenu druidIndex = new Tmenu();
		druidIndex.setCid("druidIndex");
		druidIndex.setCname("druid监控");
		druidIndex.setCseq(BigDecimal.valueOf(1));
		druidIndex.setCurl("/datasource!druid.do");
		druidIndex.setCiconcls("icon-druid");
		druidIndex.setTmenu(sjkgl);
		menuDao.saveOrUpdate(druidIndex);

//		Tmenu rzgl = new Tmenu();
//		rzgl.setCid("rzgl");
//		rzgl.setCname("日志管理");
//		rzgl.setCseq(BigDecimal.valueOf(3));
//		rzgl.setCurl("");
//		rzgl.setTmenu(null);
//		rzgl.setCiconcls("icon-theme");
//		menuDao.saveOrUpdate(rzgl);
//
//		Tmenu yhdlrz = new Tmenu();
//		yhdlrz.setCid("yhdlrz");
//		yhdlrz.setCname("用户登录日志");
//		yhdlrz.setCseq(BigDecimal.valueOf(1));
//		yhdlrz.setCurl("");
//		yhdlrz.setCiconcls("icon-theme");
//		yhdlrz.setTmenu(rzgl);
//		menuDao.saveOrUpdate(yhdlrz);
//
//		Tmenu yhzxrz = new Tmenu();
//		yhzxrz.setCid("yhzxrz");
//		yhzxrz.setCname("用户注销日志");
//		yhzxrz.setCseq(BigDecimal.valueOf(2));
//		yhzxrz.setCurl("");
//		yhzxrz.setTmenu(rzgl);
//		yhzxrz.setCiconcls("icon-theme");
//		menuDao.saveOrUpdate(yhzxrz);
//
//		Tmenu yhglrz = new Tmenu();
//		yhglrz.setCid("yhglrz");
//		yhglrz.setCname("用户管理日志");
//		yhglrz.setCseq(BigDecimal.valueOf(3));
//		yhglrz.setCurl("");
//		yhglrz.setCiconcls("icon-theme");
//		yhglrz.setTmenu(rzgl);
//		menuDao.saveOrUpdate(yhglrz);
//
//		Tmenu jsglrz = new Tmenu();
//		jsglrz.setCid("jsglrz");
//		jsglrz.setCname("角色管理日志");
//		jsglrz.setCseq(BigDecimal.valueOf(4));
//		jsglrz.setCurl("");
//		jsglrz.setCiconcls("icon-theme");
//		jsglrz.setTmenu(rzgl);
//		menuDao.saveOrUpdate(jsglrz);
//
//		Tmenu qxglrz = new Tmenu();
//		qxglrz.setCid("qxglrz");
//		qxglrz.setCname("权限管理日志");
//		qxglrz.setCseq(BigDecimal.valueOf(5));
//		qxglrz.setCurl("");
//		qxglrz.setCiconcls("icon-theme");
//		qxglrz.setTmenu(rzgl);
//		menuDao.saveOrUpdate(qxglrz);
//
//		Tmenu cdglrz = new Tmenu();
//		cdglrz.setCid("cdglrz");
//		cdglrz.setCname("菜单管理日志");
//		cdglrz.setCseq(BigDecimal.valueOf(6));
//		cdglrz.setCurl("");
//		cdglrz.setCiconcls("icon-theme");
//		cdglrz.setTmenu(rzgl);
//		menuDao.saveOrUpdate(cdglrz);

	}
	
	private void repairDept() {
		
		Tdept dept =  deptDao.get(Tdept.class, "0");
		if(dept==null){
			Tdept d = new Tdept();
			d.setCid("0");
			d.setCname("根部门");
			d.setCseq(BigDecimal.valueOf(0));
			d.setCdesc("所有部门的父级部门");
			deptDao.save(d);
		}
	}

	@SuppressWarnings("null")
	private void repairUser() {
		List<Tuser> t = userDao.find("from Tuser t where t.cname = ? and t.cid != ?", new Object[] { "admin", "0" });// cid!='0'并且cname='admin'这是错误的数据，需要修复
		if (t != null && t.size() > 0) {
			for (Tuser u : t) {
				u.setCname(u.getCname() + UUID.randomUUID().toString());
			}
		}
		Tdept dept= null;
		Tuser admin = new Tuser();
		admin.setCid("0");
		admin.setCname("admin");
		admin.setCpwd(Encrypt.e("admin"));
		admin.setCrealname("超级管理员");
		admin.setCemail("25091348@qq.com");
		admin.setUpdTime(new Date());
		admin.setCrtTime(new Date());
		admin.setCstatus("0");
		dept = deptDao.get(Tdept.class, "0");
		if(dept==null){
			dept.setCid("0");
			dept.setCname("根部门");
			deptDao.save(dept);
		}
		admin.setTdept(dept);
		userDao.saveOrUpdate(admin);
	}
	
}
