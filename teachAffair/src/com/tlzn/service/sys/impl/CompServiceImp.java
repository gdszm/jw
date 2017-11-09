package com.tlzn.service.sys.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sun.org.apache.commons.beanutils.BeanUtils;
import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.Tcommitteeman;
import com.tlzn.model.Tcompany;
import com.tlzn.model.sys.Tdolog;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.sys.Comp;
import com.tlzn.pageModel.sys.Dic;
import com.tlzn.pageModel.sys.User;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.sys.CompServiceI;
import com.tlzn.service.sys.UserServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Generator;
import com.tlzn.util.base.ObjectPropertyUtilsBean;

@Service("compService")
public class CompServiceImp extends BaseServiceImpl implements CompServiceI {
	private BaseDaoI<Tcompany> compDao;
	private UserServiceI userService;
	protected final Log log = LogFactory.getLog(getClass());
	
	public BaseDaoI<Tcompany> getCompDao() {
		return compDao;
	}
	@Autowired
	public void setCompDao(BaseDaoI<Tcompany> compDao) {
		this.compDao = compDao;
	}
	
	public UserServiceI getUserService() {
		return userService;
	}
	@Autowired
	public void setUserService(UserServiceI userService) {
		this.userService = userService;
	}
	
	public void save(Comp comp) throws Exception{
		Tcompany t=new Tcompany();
		BeanUtils.copyProperties(t,comp);
		t.setCompanyId(Generator.getInstance().getCompanyNO());
		t.setCreateTime(new Date());
		t.setUpdateTime(new Date());
		compDao.save(t);		
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_SYS);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_ADD);
		dolog.setKeyId(t.getCompanyId());
		dolog.setInfo( "[承办单位]新增承办单位");
		this.saveLog(dolog);
		this.operUser(t,"1");//同时判断用户是否存在，不存在则保存提案人到用户表，并且状态为启用
	}

	public Comp update(Comp comp) throws Exception{
		Tcompany t = compDao.get(Tcompany.class, comp.getCompanyId());
		ObjectPropertyUtilsBean.getInstance().copyProperties(t,comp);
		ObjectPropertyUtilsBean.getInstance().copyProperties(comp, t);
		t.setUpdateTime(new Date());
		compDao.update(t);
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_SYS);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_EDIT);
		dolog.setKeyId(t.getCompanyId());
		dolog.setInfo( "[承办单位]修改承办单位信息");
		this.saveLog(dolog);
		this.operUser(t,"1");//同时判断用户是否存在，不存在则保存提案人到用户表，并且状态为启用
		return comp;
	}
	
	public void change(Comp comp) throws Exception {
		if (!"".equals(comp.getIds()) && null!=comp.getIds())  {
			for (String id : comp.getIds().split(",")) {
				Tcompany t = compDao.get(Tcompany.class, id.trim());
				t.setStatus(comp.getStatus());
				if (t != null) {
					compDao.update(t);
					Tdolog dolog=new Tdolog();
					dolog.setLogType(Constants.LOG_TYPE_CODE_SYS);
					dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_SET);
					dolog.setKeyId(t.getCompanyId());
					dolog.setInfo( "[承办单位]设置承办单位状态");
					this.saveLog(dolog);
					this.operUser(t,t.getStatus());//同时判断用户是否存在，不存在则保存提案人到用户表，并且状态为启用
				}
			}
		}
	}

	private void operUser(Tcompany t, String status) throws Exception{
		User user=new User();
		user.setUserCode(t.getCompanyId());
		user.setUserGroup(Constants.DIC_TYPE_YHZB_CBDW);
		user.setCstatus(status);
		user.setCname(t.getShortName());	//默认用户为单位简称
		user.setCrealname(t.getCompanyName());
		user.setCpwd("abc111");	//默认初始密码
		user.setCid(userService.findUserID(user));
		user.setRoleIds(Constants.CBDW_ROLE_CID);	//"承办单位"角色ID
		if("".equals(user.getCid())){
			userService.save(user);
			Tdolog dolog=new Tdolog();
			dolog.setLogType(Constants.LOG_TYPE_CODE_SYS);
			dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_ADD);
			dolog.setKeyId(user.getCid());
			dolog.setInfo( "[用户]添加新用户");
			this.saveLog(dolog);
		}else{
			userService.update(user);
			Tdolog dolog=new Tdolog();
			dolog.setLogType(Constants.LOG_TYPE_CODE_SYS);
			dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_ADD);
			dolog.setKeyId(user.getCid());
			dolog.setInfo( "[用户]修改用户信息");
			this.saveLog(dolog);
		}
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(Comp comp) throws Exception {
		DataGrid j = new DataGrid();
		j.setRows(getCompsFromTComps(find(comp)));
		j.setTotal(total(comp));
		return j;
	}

	private List<Comp> getCompsFromTComps(List<Tcompany> Tcomps) throws Exception {
		List<Comp> ads = new ArrayList<Comp>();
		if (Tcomps != null && Tcomps.size() > 0) {
			for (Tcompany t : Tcomps) {
				Comp p = new Comp();
				BeanUtils.copyProperties(p,t);
				p.setCompanyTypeName(this.findDicName("COMPANYTYPE", t.getCompanyType()));
				p.setStatusName(this.findDicName("STATUS", t.getStatus()));
				ads.add(p);
			}
		}
		return ads;
	}

	private List<Tcompany> find(Comp comp) {
		String hql = "from Tcompany t where 1=1 ";

		List<Object> values = new ArrayList<Object>();
		hql = addWhere(comp, hql, values);

		if (comp.getSort() != null && comp.getOrder() != null) {
			hql += " order by " + comp.getSort() + " " + comp.getOrder();
		}
		return compDao.find(hql, values, comp.getPage(), comp.getRows());
	}

	private Long total(Comp comp) {
		String hql = "select count(*) from Tcompany t where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(comp, hql, values);
		return compDao.count(hql, values);
	}

	private String addWhere(Comp comp, String hql, List<Object> values) {
		if (comp.getCompanyName() != null && !"".equals(comp.getCompanyName())) {
			hql += " and t.companyName like ? ";
			values.add("%" + comp.getCompanyName() + "%");
		}
		if (comp.getCompanyType() != null && !"".equals(comp.getCompanyType())) {
			hql += " and t.companyType=? ";
			values.add(comp.getCompanyType());
		}
		if (comp.getCompanyAddress() != null && !"".equals(comp.getCompanyAddress())) {
			hql += " and t.companyAddress  like ? ";
			values.add("%" + comp.getCompanyAddress() + "%");
		}
		if (comp.getLinkman() != null && !"".equals(comp.getLinkman())) {
			hql += " and t.linkman =? ";
			values.add(comp.getLinkman());
		}
		if (comp.getStatus() != null && !"".equals(comp.getStatus())) {
			hql += " and t.status =? ";
			values.add(comp.getStatus());
		}
		return hql;
	}
	
	/**
	 * 
		 * 根据Code获取承办单位信息
		 * 
		 * @param  code
		 * 
		 * @throws 	Exception
		 * 
		 * @return 		Comp
	 */
	public Comp getCompBycode(String code)  throws Exception{
		Comp comp=new Comp();
		BeanUtils.copyProperties(comp,compDao.get(Tcompany.class,code));
		comp.setCompanyTypeName(this.findDicName("COMPANYTYPE", comp.getCompanyType()));
		comp.setStatusName(this.findDicName("STATUS", comp.getStatus()));
		return comp;
	}
	
	/**
	 * 下拉列表
	 * 
	 */
	public List<Dic> combobox()throws Exception{
		String hql = "from Tcompany t ";
		List<Tcompany> list=compDao.find(hql);
		List<Dic> dicList=new ArrayList<Dic>();
		for (Tcompany t : list) {
			Dic d=new Dic();
			d.setCvalue(t.getCompanyId());
			d.setCkey(t.getShortName());
			dicList.add(d);
		}
		System.out.println("dicList_size===="+dicList.size());
		return dicList;
	}
}
