package com.tlzn.service.sys.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.Tcommitteeman;
import com.tlzn.model.sys.Tgroup;
import com.tlzn.model.sys.Tusergroup;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.sys.UserGroup;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.sys.UserGroupServiceI;
import com.tlzn.util.base.Generator;

@Service("userGroupService")
public class UserGroupServiceImpl extends BaseServiceImpl implements UserGroupServiceI{
	public static List<Tusergroup> usergroupList;

	private BaseDaoI<Tusergroup> usergroupDao;
	private BaseDaoI<Tcommitteeman> commDao;
	
	public BaseDaoI<Tusergroup> getUsergroupDao() {
		return usergroupDao;
	}
	
	@Autowired
	public void setUsergroupDao(BaseDaoI<Tusergroup> usergroupDao) {
		this.usergroupDao = usergroupDao;
	}

	public BaseDaoI<Tcommitteeman> getCommDao() {
		return commDao;
	}
	@Autowired
	public void setCommDao(BaseDaoI<Tcommitteeman> commDao) {
		this.commDao = commDao;
	}
	
	@Override
	public void update(UserGroup userGroup) throws Exception {
		Tusergroup t = usergroupDao.get(Tusergroup.class, userGroup.getGcid());
		if (userGroup.getGid() != null && userGroup.getGid() != "") {
			Tgroup tg=new Tgroup();
			tg.setGid(userGroup.getGid());
			t.setTgroup(tg);
		}
		if (userGroup.getCode() != null && userGroup.getCode() != "") {
			Tcommitteeman tu=new Tcommitteeman();
			tu.setCode(userGroup.getCode());
			t.setTcomm(tu);
		}
			Date d = new Date();
			t.setCreateTime(d);
			usergroupDao.save(t);			
	}

	@Override
	public DataGrid datagrid(UserGroup userGroup) throws Exception {
		DataGrid j = new DataGrid();
		j.setRows(this.changeModel(this.find(userGroup)));
		//j.setTotal(this.total(userGroup));
		return j;
	}

	private List<UserGroup> changeModel(List<Tusergroup> tugs) throws Exception {
		List<UserGroup> users = new ArrayList<UserGroup>();
		if (tugs != null && tugs.size() > 0) {
			for (Tusergroup tu : tugs) {
				UserGroup u = new UserGroup();
				u.setGcid(tu.getGcid());
				u.setCode(tu.getTcomm().getCode());
				u.setName("("+tu.getTgroup().getGname()+")"+tu.getTcomm().getName());
				u.setGid(tu.getTgroup().getGid());
				u.setGname(tu.getTgroup().getGname());
				u.setCircleName(this.findDicName("CIRCLE", tu.getTcomm().getCircleCode()));
				u.setJob(tu.getTcomm().getJob());
				u.setCommitteeName(this.findDicName("COMMITTEE", tu.getTcomm().getCommittee()));
				u.setCompanyName(tu.getTcomm().getCompanyName());
				users.add(u);
			}
		}
		return users;
	}

	private List<Tusergroup> find(UserGroup userGroup) throws Exception{
		String hql = "from Tusergroup t where 1=1";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(userGroup, hql, values);
		return usergroupDao.find(hql, values);
	}

	private Long total(UserGroup userGroup)throws Exception {
		String hql = "select count(*) from Tusergroup t where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(userGroup, hql, values);
		return usergroupDao.count(hql, values);
	}

	private String addWhere(UserGroup ug, String hql, List<Object> values)throws Exception {
		if (ug.getGid() != null && !ug.getGid().trim().equals("")) {
			hql += " and t.tgroup.gid = ? ";
			values.add(ug.getGid().trim());
		}
		return hql;
	}
	/**
	 * 新增
	 */
	@Override
	public void save(UserGroup userGroup) throws Exception {
		//System.out.println("cid==="+userGroup.getCids());
		String[] uids=userGroup.getCids().split(",");
		List<Tusergroup> tulist=this.find(userGroup);
		for (Iterator it = tulist.iterator(); it.hasNext();) {
			Tusergroup tu = (Tusergroup) it.next();
			usergroupDao.delete(tu);
			//this.saveLog("删除组成员", "[组成员]ID:"+tu.getGcid());
		}
		for(int i=0;i<uids.length;i++){	
			Tusergroup tg=new Tusergroup();
			tg.setGcid(Generator.getInstance().getUserGroupID());
			Tcommitteeman u=new Tcommitteeman();
			u.setCode(uids[i]);
			Tgroup g=new Tgroup();
			g.setGid(userGroup.getGid());
			tg.setTgroup(g);
			tg.setTcomm(u);
			tg.setCreateTime(new Date());
			usergroupDao.save(tg);
			//System.out.println("[组成员]ID:"+tg.getGcid());
			//this.saveLog("保存组成员", "[组成员]ID:"+tg.getGcid());
		}
	}
	/**
	 * 组用户下拉列表
	 */
	@Override
	public List<UserGroup> combobox(UserGroup userGroup) throws Exception {
		List<UserGroup> list=new ArrayList<UserGroup>();
		String hql = "from Tusergroup t where 1=1";
		//hql+=" and t.tgroup.street='"+userGroup.getStreet()+"'";
		list=this.changeModel(usergroupDao.find(hql));
		return list;
	}

	@Override
	public void delete(UserGroup userGroup) throws Exception {
		String gids=userGroup.getGcid();
		if (gids != null) {
			for (String gid: gids.split(",")) {					
				Tusergroup t = usergroupDao.get(Tusergroup.class,gid.trim());
				if (t != null) {
					usergroupDao.delete(t);
				}
			}
		}		
	}

	

}
