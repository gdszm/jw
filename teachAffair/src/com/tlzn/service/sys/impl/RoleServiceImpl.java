package com.tlzn.service.sys.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.sys.Tauth;
import com.tlzn.model.sys.Trole;
import com.tlzn.model.sys.Troletauth;
import com.tlzn.model.sys.Tusertrole;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.sys.Role;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.sys.RoleServiceI;
import com.tlzn.util.base.Constants;

@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl implements RoleServiceI {

	private BaseDaoI<Trole> roleDao;
	private BaseDaoI<Tauth> authDao;
	private BaseDaoI<Troletauth> roleauthDao;
	private BaseDaoI<Tusertrole> userroleDao;

	public BaseDaoI<Tauth> getAuthDao() {
		return authDao;
	}

	@Autowired
	public void setAuthDao(BaseDaoI<Tauth> authDao) {
		this.authDao = authDao;
	}

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

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(Role role) {
		DataGrid j = new DataGrid();
		j.setRows(changeModel(find(role)));
		j.setTotal(total(role));
		return j;
	}

	private List<Role> changeModel(List<Trole> troles) {
		List<Role> roles = new ArrayList<Role>();
		if (troles != null && troles.size() > 0) {
			for (Trole tu : troles) {
				Role u = new Role();
				BeanUtils.copyProperties(tu, u);

				Set<Troletauth> troletauths = tu.getTroletauths();
				String authIds = "";
				String authNames = "";
				boolean b = false;
				if (troletauths != null && troletauths.size() > 0) {
					for (Troletauth troletauth : troletauths) {
						if (troletauth.getTauth() != null) {
							if (b) {
								authIds += ",";
								authNames += ",";
							}
							authIds += troletauth.getTauth().getCid();
							authNames += troletauth.getTauth().getCname();
							b = true;
						}
					}
				}
				u.setAuthIds(authIds);
				u.setAuthNames(authNames);

				roles.add(u);
			}
		}
		return roles;
	}

	private List<Trole> find(Role role) {
		String hql = "from Trole t where 1=1 ";

		List<Object> values = new ArrayList<Object>();
		hql = addWhere(role, hql, values);

		if (role.getSort() != null && role.getOrder() != null) {
			hql += " order by " + role.getSort() + " " + role.getOrder();
		}
		return roleDao.find(hql, values, role.getPage(), role.getRows());
	}

	private Long total(Role role) {
		String hql = "select count(*) from Trole t where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(role, hql, values);
		return roleDao.count(hql, values);
	}

	private String addWhere(Role role, String hql, List<Object> values) {
		return hql;
	}

	public void add(Role role) {
		Trole r = new Trole();
		BeanUtils.copyProperties(role, r);
		r.setCid(UUID.randomUUID().toString());
		roleDao.save(r);

		this.saveRoleAuth(role, r);
	}

	/**
	 * 保存Trole和Tauth的关系
	 * 
	 * @param role
	 * @param r
	 */
	private void saveRoleAuth(Role role, Trole r) {
		roleauthDao.executeHql("delete Troletauth t where t.trole = ?", new Object[] { r });
		if (role.getAuthIds() != null) {
			for (String id : role.getAuthIds().split(",")) {
				Troletauth troletauth = new Troletauth();
				troletauth.setCid(UUID.randomUUID().toString());
				troletauth.setTauth(authDao.get(Tauth.class, id.trim()));
				troletauth.setTrole(r);
				roleauthDao.save(troletauth);
			}
		}
	}

	public void edit(Role role) {
		Trole r = roleDao.get(Trole.class, role.getCid());
		BeanUtils.copyProperties(role, r, new String[] { "cid" });

		this.saveRoleAuth(role, r);
	}

	public void delete(String ids) {
		if (ids != null) {
			for (String id : ids.split(",")) {
				Trole r = roleDao.get(Trole.class, id.trim());
				if (r != null) {
					roleauthDao.executeHql("delete Troletauth t where t.trole = ?", new Object[] { r });
					userroleDao.executeHql("delete Tusertrole t where t.trole = ?", new Object[] { r });
					roleDao.delete(r);
				}
			}
		}
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Role> combobox() {
		List<Role> rl = new ArrayList<Role>();
		List<Trole> l = roleDao.find("from Trole");
		if (l != null && l.size() > 0) {
			for (Trole t : l) {
				Role r = new Role();
					r.setCid(t.getCid());
					r.setCname(t.getCname().length()>8?t.getCname().substring(0,8)+"...":t.getCname());
					rl.add(r);
			}
		}
		return rl;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Role> comboboxNo2() {	//去承办单位和提案人
		List<Role> rl = new ArrayList<Role>();
		List<Trole> l = roleDao.find("from Trole where cid!='0'");
		if (l != null && l.size() > 0) {
			for (Trole t : l) {
				Role r = new Role();
				if(!Constants.CBDW_ROLE_CID.equals(t.getCid()) && !Constants.TAR_ROLE_CID.equals(t.getCid())){
					r.setCid(t.getCid());
					r.setCname(t.getCname().length()>8?t.getCname().substring(0,8)+"...":t.getCname());
					rl.add(r);
				}
			}
		}
		return rl;
	}

}
