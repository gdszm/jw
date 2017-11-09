package com.tlzn.service.sys.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.sys.Tgroup;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.sys.Group;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.sys.GroupServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Generator;

@Service("groupService")
public class GroupServiceImpl extends BaseServiceImpl implements GroupServiceI{
	
	public static List<Tgroup> groupList;

	private BaseDaoI<Tgroup> groupDao;

	public BaseDaoI<Tgroup> getGroupDao() {
		return groupDao;
	}
	
	@Autowired
	public void seTgroupDao(BaseDaoI<Tgroup> groupDao) {
		this.groupDao = groupDao;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(Group group) throws Exception {
		DataGrid j = new DataGrid();
		j.setRows(getGroupsFromTgroups(find(group)));
		j.setTotal(total(group));
		return j;
	}

	private List<Group> getGroupsFromTgroups (List<Tgroup> tgroups)throws Exception {
		List<Group> list = new ArrayList<Group>();
		if (tgroups != null && tgroups.size() > 0) {
			for (Tgroup t : tgroups) {
				Group n = new Group();
				BeanUtils.copyProperties(n,t);
				n.setTypename(findDicName(Constants.GROUP_TYPE,t.getType()));
				list.add(n);
			}
		}
		return list;
	}

	private Long total(Group group) {
		String hql = "select count(*) from Tgroup t where 1=1  ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(group, hql, values);
		//System.out.println(hql);
		return groupDao.count(hql,values);
	}
	
	private List<Tgroup> find(Group group) {
		String hql = "from Tgroup t where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		hql=addWhere(group, hql, values);
		//System.out.println(hql);
		return groupDao.find(hql,values,group.getPage(),group.getRows());
	}
	
	private String addWhere(Group group, String hql, List<Object> values) {
		if (group.getGname()!= null && !"".equals(group.getGname())) {
			hql += " and t.gname like ? ";
			values.add("%"+group.getGname()+"%");
		}
		if (group.getType()!= null && !"".equals(group.getType())) {
			hql += " and t.type = ? ";
			values.add(group.getType());
		}
		return hql;
	}

	@Override
	public void add(Group group) throws Exception {
		    Tgroup  t = new Tgroup();
		    BeanUtils.copyProperties(t,group);
			t.setGid(Generator.getInstance().getGroupNO());
			Date d = new Date();
			t.setCreateTime(d);
			groupDao.save(t);
	}

	@Override
	public void delete(String ids) throws Exception {
		System.err.println(ids);
		if (ids != null) {
			for (String gid: ids.split(",")) {
				Tgroup t = groupDao.get(Tgroup.class, gid.trim());
				if (t != null) {
					groupDao.delete(t);
				}
			}
		}		
	}

	@Override
	public void update(Group group) throws Exception {
		Tgroup t = groupDao.get(Tgroup.class, group.getGid());
		if (group.getGname() != null && group.getGname() != "") {
			t.setGname(group.getGname());
		}	
		if (group.getType()!= null && !"".equals(group.getType())) {
			t.setType(group.getType());
		}
		groupDao.save(t);
	}
	


	@Override
	public List<Group> combobox() throws Exception {
		String hql = "from Tgroup t ";
		List<Group> list= getGroupsFromTgroups(groupDao.find(hql));
		
		return list;
	}

	
	
	
	

	


}
