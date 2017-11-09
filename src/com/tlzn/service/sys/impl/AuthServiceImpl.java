package com.tlzn.service.sys.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlzn.comparator.AuthComparator;
import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.sys.Tauth;
import com.tlzn.model.sys.Troletauth;
import com.tlzn.pageModel.base.TreeNode;
import com.tlzn.pageModel.sys.Auth;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.sys.AuthServiceI;
import com.tlzn.util.base.Constants;

@Service("authService")
public class AuthServiceImpl extends BaseServiceImpl implements AuthServiceI {

	private BaseDaoI<Tauth> authDao;
	private BaseDaoI<Object> objDao;

	private BaseDaoI<Troletauth> roleauthDao;

	public BaseDaoI<Troletauth> getRoleauthDao() {
		return roleauthDao;
	}

	@Autowired
	public void setRoleauthDao(BaseDaoI<Troletauth> roleauthDao) {
		this.roleauthDao = roleauthDao;
	}

	public BaseDaoI<Tauth> getAuthDao() {
		return authDao;
	}

	@Autowired
	public void setAuthDao(BaseDaoI<Tauth> authDao) {
		this.authDao = authDao;
	}

	public BaseDaoI<Object> getObjDao() {
		return objDao;
	}
	@Autowired
	public void setObjDao(BaseDaoI<Object> objDao) {
		this.objDao = objDao;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Auth> treegrid(Auth auth) {
		List<Tauth> l;
		if (auth != null && auth.getId() != null) {
			l = authDao.find("from Tauth t where t.tauth.cid = ? order by t.cseq", new Object[] { auth.getId() });
		} else {
			l = authDao.find("from Tauth t where t.tauth is null order by t.cseq");
		}
		return changeModel(l);
	}

	private List<Auth> changeModel(List<Tauth> tauths) {
		List<Auth> l = new ArrayList<Auth>();
		if (tauths != null && tauths.size() > 0) {
			for (Tauth t : tauths) {
				Auth a = new Auth();
				BeanUtils.copyProperties(t, a);
				if (t.getTauth() != null) {
					a.setPid(t.getTauth().getCid());
					a.setPname(t.getTauth().getCname());
				}
				if (countChildren(t.getCid()) > 0) {
					a.setState("closed");
				}
				l.add(a);
			}
		}
		return l;
	}

	/**
	 * 统计有多少子节点
	 */
	private Long countChildren(String pid) {
		return authDao.count("select count(*) from Tauth t where t.tauth.cid = ?", new Object[] { pid });
	}

	public void delete(Auth auth) {
		del(auth.getCid());
	}

	private void del(String cid) {
		Tauth t = authDao.get(Tauth.class, cid);
		if (t != null) {
			roleauthDao.executeHql("delete Troletauth t where t.tauth = ?", new Object[] { t });
			Set<Tauth> auths = t.getTauths();
			if (auths != null && !auths.isEmpty()) {
				// 说明当前权限下面还有子权限
				for (Tauth tauth : auths) {
					del(tauth.getCid());
				}
			}
			authDao.delete(t);
		}
	}

	public void edit(Auth auth) {
		Tauth t = authDao.get(Tauth.class, auth.getCid());// 要修改的权限
		BeanUtils.copyProperties(auth, t);
		if (auth.getPid() != null && !auth.getPid().equals(auth.getCid())) {
			Tauth pAuth = authDao.get(Tauth.class, auth.getPid());// 要设置的上级权限
			if (pAuth != null) {
				if (isDown(t, pAuth)) {// 要将当前节点修改到当前节点的子节点中
					Set<Tauth> tauths = t.getTauths();// 当前要修改的权限的所有下级权限
					if (tauths != null && tauths.size() > 0) {
						for (Tauth tauth : tauths) {
							if (tauth != null) {
								tauth.setTauth(null);
							}
						}
					}
				}
				t.setTauth(pAuth);
			}
		}
	}

	/**
	 * 判断是否是将当前节点修改到当前节点的子节点
	 * 
	 * @param t
	 * @param pt
	 * @return
	 */
	private boolean isDown(Tauth t, Tauth pt) {
		if (pt.getTauth() != null) {
			if (pt.getTauth().getCid().equals(t.getCid())) {
				return true;
			} else {
				return isDown(t, pt.getTauth());
			}
		}
		return false;
	}

	public void add(Auth auth) {
		Tauth t = new Tauth();
		BeanUtils.copyProperties(auth, t);
		t.setCid(UUID.randomUUID().toString());
		if (auth.getPid() != null && !auth.getPid().equals(auth.getCid())) {
			t.setTauth(authDao.get(Tauth.class, auth.getPid()));
		}
		authDao.save(t);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<TreeNode> tree(Auth auth, boolean b) {
		List<Object> param = new ArrayList<Object>();
		String hql = "from Tauth t where t.tauth is null order by t.cseq";
		if (auth != null && auth.getId() != null && !auth.getId().trim().equals("")) {
			hql = "from Tauth t where t.tauth.cid = ? order by t.cseq";
			param.add(auth.getId());
		}
		List<Tauth> l = authDao.find(hql, param);
		List<TreeNode> tree = new ArrayList<TreeNode>();
		for (Tauth t : l) {
			tree.add(this.tree(t, b));
		}
		return tree;
	}

	public List<Auth> getAuthMenu(String role){
		String sql = "select t.* from Tauth t where exists (select tt.cauthid from troletauth tt where tt.croleid='"+role+"' and tt.cauthid=t.cid) and t.cpid='"+Constants.MODULE_CODE+"' order by t.cseq";
		List<Object>list=objDao.executeSql(sql);
		List<Auth> auth=new ArrayList<Auth>();
		for(int i=0;i<list.size();i++){
			Object[] o=(Object[]) list.get(i);
			Auth a=new Auth();
			a.setCid(o[0]==null ? "" : o[0].toString());
			a.setCname(o[2]==null ? "" : o[2].toString());
			a.setCurl(o[4]==null ? "" : o[4].toString());
			a.setPid(o[5]==null ? "" : o[5].toString());
			a.setIconCls(o[6]==null ? "" : o[6].toString());
			auth.add(a);
		}
		return auth;
	}
	
	public List<Auth> getAuthMenu(String modCode,String role){
		String sql = "select t.* from Tauth t where exists (select tt.cauthid from troletauth tt where tt.croleid='"+role+"' and tt.cauthid=t.cid) and t.cpid='"+modCode+"' order by t.cseq";
	    //+" union "
		//+" select ta.* from tauth ta where exists (select a.cid from ( select t.* from Tauth t where exists (select tt.cauthid from troletauth tt where tt.croleid='"+role+"' and tt.cauthid=t.cid) and t.cpid='"+modCode+"') a where a.cid=ta.cpid)";
		List<Object>list=objDao.executeSql(sql);
		List<Auth> auth=new ArrayList<Auth>();
		for(int i=0;i<list.size();i++){
			Object[] o=(Object[]) list.get(i);
			Auth a=new Auth();
			a.setCid(o[0]==null ? "" : o[0].toString());
			a.setCname(o[2]==null ? "" : o[2].toString());
			a.setCurl(o[4]==null ? "" : o[4].toString());
			a.setPid(o[5]==null ? "" : o[5].toString());
			a.setIconCls(o[6]==null ? "" : o[6].toString());
			auth.add(a);
		}
		return auth;
	}
	public List<Auth> getAuthSubMenu(String modCode,String role){
		String sql = "select ta.* from tauth ta where exists ("
                     +" select t.cid from Tauth t where exists (select tt.cauthid from troletauth tt where tt.croleid='"+role+"' and tt.cauthid=t.cid) and t.cpid='"+modCode+"' and  t.cid=ta.cpid)"
                     +" and exists(select tt.cauthid from troletauth tt where tt.croleid='"+role+"' and tt.cauthid=ta.cid) order by ta.cseq";
		List<Object>list=objDao.executeSql(sql);
		List<Auth> auth=new ArrayList<Auth>();
		for(int i=0;i<list.size();i++){
			Object[] o=(Object[]) list.get(i);
			Auth a=new Auth();
			a.setCid(o[0]==null ? "" : o[0].toString());
			a.setCname(o[2]==null ? "" : o[2].toString());
			a.setCurl(o[4]==null ? "" : o[4].toString());
			a.setPid(o[5]==null ? "" : o[5].toString());
			a.setIconCls(o[6]==null ? "" : o[6].toString());
			auth.add(a);
		}
		return auth;
	}
	private TreeNode tree(Tauth t, boolean recursive) {
		TreeNode node = new TreeNode();
		node.setId(t.getCid());
		node.setText(t.getCname());
		Map<String, Object> attributes = new HashMap<String, Object>();
		node.setAttributes(attributes);
		if (t.getTauths() != null && t.getTauths().size() > 0) {
			node.setState("closed");
			if (recursive) {// 递归查询子节点
				List<Tauth> l = new ArrayList<Tauth>(t.getTauths());
				Collections.sort(l, new AuthComparator());// 排序
				List<TreeNode> children = new ArrayList<TreeNode>();
				for (Tauth r : l) {
					TreeNode tn = tree(r, true);
					children.add(tn);
				}
				node.setChildren(children);
				node.setState("open");
			}
		}
		return node;
	}

}
