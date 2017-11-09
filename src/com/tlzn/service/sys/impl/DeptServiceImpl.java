package com.tlzn.service.sys.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlzn.comparator.DeptComparator;
import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.sys.Tdept;
import com.tlzn.pageModel.base.TreeNode;
import com.tlzn.pageModel.sys.Dept;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.sys.DeptServiceI;

@Service("deptService")
public class DeptServiceImpl extends BaseServiceImpl implements DeptServiceI {

	private BaseDaoI<Tdept> deptDao;

	public BaseDaoI<Tdept> getDeptDao() {
		return deptDao;
	}

	@Autowired
	public void setDeptDao(BaseDaoI<Tdept> deptDao) {
		this.deptDao = deptDao;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Dept> findAll() {
		List<Dept> ld = new ArrayList<Dept>();
		String hql = "from Tdept t order by t.cseq";
		List<Tdept> ltd = deptDao.find(hql);
		for (Tdept td : ltd) {
			Dept d = new Dept();
			d.setCid(td.getCid());
			d.setCname(td.getCname());
			d.setCiconcls(td.getCiconcls());
			if(td.getTdept()!=null){
				d.setPid(td.getTdept().getCid());
			}
			ld.add(d);
		}
		return ld;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<TreeNode> tree(Dept dept, Boolean b) {
		List<Object> param = new ArrayList<Object>();
		String hql = "from Tdept t where t.tdept is null order by t.cseq";
		if (dept != null && dept.getId() != null && !dept.getId().trim().equals("")) {
			hql = "from Tdept t where t.tdept.cid = ? order by t.cseq";
			param.add(dept.getId());
		}
		List<Tdept> l = deptDao.find(hql, param);
		List<TreeNode> tree = new ArrayList<TreeNode>();
		for (Tdept t : l) {
			tree.add(this.tree(t, b));
		}
		return tree;
	}

	private TreeNode tree(Tdept t, boolean recursive) {
		TreeNode node = new TreeNode();
		node.setId(t.getCid());
		node.setText(t.getCname());
		if (t.getCiconcls() != null) {
			node.setIconCls(t.getCiconcls());
		} else {
			node.setIconCls("");
		}
		if (t.getTdepts() != null && t.getTdepts().size() > 0) {
			node.setState("closed");
			if (recursive) {// 递归查询子节点
				List<Tdept> l = new ArrayList<Tdept>(t.getTdepts());
				Collections.sort(l, new DeptComparator());// 排序
				List<TreeNode> children = new ArrayList<TreeNode>();
				for (Tdept r : l) {
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
	public List<Dept> treegrid(Dept dept) {
		List<Tdept> l;
		if (dept != null && dept.getId() != null) {
			l = deptDao.find("from Tdept t where t.tdept.cid = ? order by t.cseq", new Object[] { dept.getId() });
		} else {
			l = deptDao.find("from Tdept t where t.tdept is null order by t.cseq");
		}
		return changeModel(l);
	}

	private List<Dept> changeModel(List<Tdept> Tdepts) {
		List<Dept> l = new ArrayList<Dept>();
		if (Tdepts != null && Tdepts.size() > 0) {
			for (Tdept t : Tdepts) {
				Dept m = new Dept();
				BeanUtils.copyProperties(t, m);
				if (t.getTdept() != null) {
					m.setPid(t.getTdept().getCid());
					m.setPname(t.getTdept().getCname());
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
		return deptDao.count("select count(*) from Tdept t where t.tdept.cid = ?", new Object[] { pid });
	}

	public void delete(Dept dept) {
		del(dept.getCid());
	}

	private void del(String cid) {
		Tdept t = deptDao.get(Tdept.class, cid);
		if (t != null) {
			Set<Tdept> depts = t.getTdepts();
			if (depts != null && !depts.isEmpty()) {
				// 说明当前菜单下面还有子菜单
				for (Tdept tdept : depts) {
					del(tdept.getCid());
				}
			}
			deptDao.delete(t);
		}
	}

	public void add(Dept dept) {
		Tdept t = new Tdept();
		BeanUtils.copyProperties(dept, t);
		t.setCid(UUID.randomUUID().toString());
		if (dept.getPid() != null) {
			t.setTdept(deptDao.get(Tdept.class, dept.getPid()));
		}
		if (dept.getIconCls() != null) {
			t.setCiconcls(dept.getIconCls());
		}
		deptDao.save(t);
	}

	public void edit(Dept dept) {
		Tdept t = deptDao.get(Tdept.class, dept.getCid());
		BeanUtils.copyProperties(dept, t);
		if (dept.getIconCls() != null) {
			t.setCiconcls(dept.getIconCls());
		}
		if (dept.getPid() != null && !dept.getPid().equals(dept.getCid())) {
			Tdept pt = deptDao.get(Tdept.class, dept.getPid());
			if (pt != null) {
				if (isDown(t, pt)) {// 要将当前节点修改到当前节点的子节点中
					Set<Tdept> tdepts = t.getTdepts();// 当前要修改的节点的所有下级节点
					if (tdepts != null && tdepts.size() > 0) {
						for (Tdept tdept : tdepts) {
							if (tdept != null) {
								tdept.setTdept(null);
							}
						}
					}
				}
				t.setTdept(pt);
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
	private boolean isDown(Tdept t, Tdept pt) {
		if (pt.getTdept() != null) {
			if (pt.getTdept().getCid().equals(t.getCid())) {
				return true;
			} else {
				return isDown(t, pt.getTdept());
			}
		}
		return false;
	}

}
