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

import com.tlzn.comparator.MenuComparator;
import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.sys.Tmenu;
import com.tlzn.pageModel.base.TreeNode;
import com.tlzn.pageModel.sys.Menu;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.sys.MenuServiceI;

@Service("menuService")
public class MenuServiceImpl extends BaseServiceImpl implements MenuServiceI {

	private BaseDaoI<Tmenu> menuDao;

	public BaseDaoI<Tmenu> getMenuDao() {
		return menuDao;
	}

	@Autowired
	public void setMenuDao(BaseDaoI<Tmenu> menuDao) {
		this.menuDao = menuDao;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Menu> findAll() {
		List<Menu> lm = new ArrayList<Menu>();
		String hql = "from Tmenu t order by t.cseq";
		List<Tmenu> ltm = menuDao.find(hql);
		for (Tmenu tm : ltm) {
			Menu m = new Menu();
			m.setCid(tm.getCid());
			m.setCname(tm.getCname());
			m.setCiconcls(tm.getCiconcls());
			m.setCurl(tm.getCurl());
			if(tm.getTmenu()!=null){
				m.setPid(tm.getTmenu().getCid());
			}
			lm.add(m);
		}
		return lm;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<TreeNode> tree(Menu menu, Boolean b) {
		List<Object> param = new ArrayList<Object>();
		String hql = "from Tmenu t where t.tmenu is null order by t.cseq";
		if (menu != null && menu.getId() != null && !menu.getId().trim().equals("")) {
			hql = "from Tmenu t where t.tmenu.cid = ? order by t.cseq";
			param.add(menu.getId());
		}
		List<Tmenu> l = menuDao.find(hql, param);
		List<TreeNode> tree = new ArrayList<TreeNode>();
		for (Tmenu t : l) {
			tree.add(this.tree(t, b));
		}
		return tree;
	}

	private TreeNode tree(Tmenu t, boolean recursive) {
		TreeNode node = new TreeNode();
		node.setId(t.getCid());
		node.setText(t.getCname());
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("url", t.getCurl());
		node.setAttributes(attributes);
		if (t.getCiconcls() != null) {
			node.setIconCls(t.getCiconcls());
		} else {
			node.setIconCls("");
		}
		if (t.getTmenus() != null && t.getTmenus().size() > 0) {
			node.setState("closed");
			if (recursive) {// 递归查询子节点
				List<Tmenu> l = new ArrayList<Tmenu>(t.getTmenus());
				Collections.sort(l, new MenuComparator());// 排序
				List<TreeNode> children = new ArrayList<TreeNode>();
				for (Tmenu r : l) {
					TreeNode tn = tree(r, true);
					children.add(tn);
				}
				node.setChildren(children);
				node.setState("open");
			}
		}
		return node;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Menu> treegrid(Menu menu) {
		List<Tmenu> l;
		if (menu != null && menu.getId() != null) {
			l = menuDao.find("from Tmenu t where t.tmenu.cid = ? order by t.cseq", new Object[] { menu.getId() });
		} else {
			l = menuDao.find("from Tmenu t where t.tmenu is null order by t.cseq");
		}
		return changeModel(l);
	}

	private List<Menu> changeModel(List<Tmenu> Tmenus) {
		List<Menu> l = new ArrayList<Menu>();
		if (Tmenus != null && Tmenus.size() > 0) {
			for (Tmenu t : Tmenus) {
				Menu m = new Menu();
				BeanUtils.copyProperties(t, m);
				if (t.getTmenu() != null) {
					m.setPid(t.getTmenu().getCid());
					m.setPname(t.getTmenu().getCname());
				}
				if (countChildren(t.getCid()) > 0) {
					m.setState("closed");
				}
				if (t.getCiconcls() == null) {
					m.setIconCls("");
				} else {
					m.setIconCls(t.getCiconcls());
				}
				l.add(m);
			}
		}
		return l;
	}

	/**
	 * 统计有多少子节点
	 */
	private Long countChildren(String pid) {
		return menuDao.count("select count(*) from Tmenu t where t.tmenu.cid = ?", new Object[] { pid });
	}

	public void delete(Menu menu) {
		del(menu.getCid());
	}

	private void del(String cid) {
		Tmenu t = menuDao.get(Tmenu.class, cid);
		if (t != null) {
			Set<Tmenu> menus = t.getTmenus();
			if (menus != null && !menus.isEmpty()) {
				// 说明当前菜单下面还有子菜单
				for (Tmenu tmenu : menus) {
					del(tmenu.getCid());
				}
			}
			menuDao.delete(t);
		}
	}

	public void add(Menu menu) {
		Tmenu t = new Tmenu();
		BeanUtils.copyProperties(menu, t);
		t.setCid(UUID.randomUUID().toString());
		if (menu.getPid() != null) {
			t.setTmenu(menuDao.get(Tmenu.class, menu.getPid()));
		}
		if (menu.getIconCls() != null) {
			t.setCiconcls(menu.getIconCls());
		}
		menuDao.save(t);
	}

	public void edit(Menu menu) {
		Tmenu t = menuDao.get(Tmenu.class, menu.getCid());
		BeanUtils.copyProperties(menu, t);
		if (menu.getIconCls() != null) {
			t.setCiconcls(menu.getIconCls());
		}
		if (menu.getPid() != null && !menu.getPid().equals(menu.getCid())) {
			Tmenu pt = menuDao.get(Tmenu.class, menu.getPid());
			if (pt != null) {
				if (isDown(t, pt)) {// 要将当前节点修改到当前节点的子节点中
					Set<Tmenu> tmenus = t.getTmenus();// 当前要修改的节点的所有下级节点
					if (tmenus != null && tmenus.size() > 0) {
						for (Tmenu tmenu : tmenus) {
							if (tmenu != null) {
								tmenu.setTmenu(null);
							}
						}
					}
				}
				t.setTmenu(pt);
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
	private boolean isDown(Tmenu t, Tmenu pt) {
		if (pt.getTmenu() != null) {
			if (pt.getTmenu().getCid().equals(t.getCid())) {
				return true;
			} else {
				return isDown(t, pt.getTmenu());
			}
		}
		return false;
	}

}
