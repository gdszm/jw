package com.tlzn.service.sys.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlzn.comparator.DicComparator;
import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.dailywork.Tnotice;
import com.tlzn.model.sys.Tdept;
import com.tlzn.model.sys.Tdic;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.TreeNode;
import com.tlzn.pageModel.dailywork.Notice;
import com.tlzn.pageModel.sys.Dic;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.sys.DicServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Util;

@Service("dicService")
public class DicServiceImpl extends BaseServiceImpl implements DicServiceI {

	public static List<Tdic> dicList;
	
	private BaseDaoI<Tdic> dicDao;

	public BaseDaoI<Tdic> getDicDao() {
		return dicDao;
	}

	@Autowired
	public void setDicDao(BaseDaoI<Tdic> dicDao) {
		this.dicDao = dicDao;
	}

	public void add(Dic dic) {
		Tdic t = new Tdic();
		t.setCid(UUID.randomUUID().toString());
		t.setCkey(dic.getCkey());
		t.setCvalue(dic.getCvalue());
		t.setCtype(dic.getCtype());
		t.setCtypeName(dic.getCtypeName());
		Tdic tdic= dicDao.get(Tdic.class, dic.getCbasetype());
		t.setTdic(tdic);
		t.setCstatus(dic.getCstatus());
		dicDao.save(t);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(Dic dic) {
		DataGrid j = new DataGrid();
		j.setRows(getDicsFromTdics(find(dic)));
		j.setTotal(total(dic));
		return j;
	}
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Dic get(String id) throws Exception {
		Tdic tdic = dicDao.get(Tdic.class, id);
		Dic dic = new Dic();
		BeanUtils.copyProperties(tdic, dic);
		if(tdic.getTdic()!=null) {
			dic.setCbasetype(tdic.getTdic().getCid());
		}
		return dic;
	}
	
	
	private List<Dic> getDicsFromTdics(List<Tdic> Tdics) {
		List<Dic> ads = new ArrayList<Dic>();
		if (Tdics != null && Tdics.size() > 0) {
			for (Tdic t : Tdics) {
				Dic p = new Dic();
				p.setCid(t.getCid());
				p.setCkey(t.getCkey().length()>40?t.getCkey().substring(0,40)+"...":t.getCkey());
				p.setCvalue(t.getCvalue());
				p.setCtype(t.getCtype());
				p.setCstatus(t.getCstatus());
				p.setCtypeName(t.getCtypeName());
				if(!Util.getInstance().isEmpty(t.getTdic())){
					p.setCbasetype(t.getTdic().getCid());
					p.setCbasetypeName(t.getTdic().getCtypeName());
				}
				
				ads.add(p);
			}
		}
		return ads;
	}

	private List<Tdic> find(Dic dic) {
		String hql = "from Tdic t where 1=1 ";

		List<Object> values = new ArrayList<Object>();
		hql = addWhere(dic, hql, values);

		if (dic.getSort() != null && dic.getOrder() != null) {
			hql += " order by " + dic.getSort() + " " + dic.getOrder();
		}
		return dicDao.find(hql, values, dic.getPage(), dic.getRows());
	}

	private Long total(Dic dic) {
		String hql = "select count(*) from Tdic t where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(dic, hql, values);
		return dicDao.count(hql, values);
	}

	private String addWhere(Dic dic, String hql, List<Object> values) {
		if(dic.getCkey() != null && !"".equals(dic.getCkey())){
			 hql += " and t.ckey like '%"+dic.getCkey().trim()+"%'";
		}
		if (dic.getCvalue() != null && !"".equals(dic.getCvalue())) {
			hql += " and t.cvalue =? ";
			values.add(dic.getCvalue());
		}
		if (dic.getCtype() != null && !"".equals(dic.getCtype())) {
			hql += " and t.ctype =? ";
			values.add(dic.getCtype());
		}
		if(dic.getCtypeName() != null && !"".equals(dic.getCtypeName())){
			 hql += " and t.ctypeName like '%"+dic.getCtypeName().trim()+"%'";
		}
		if (dic.getCbasetype() != null && !"".equals(dic.getCbasetype())) {
			hql += " and t.tdic.cid =? ";
			values.add(dic.getCbasetype());
		}
		if(dic.getCbasetypeName() != null && !"".equals(dic.getCbasetypeName())){
			 hql += " and t.cbasetypeName like '%"+dic.getCbasetypeName().trim()+"%'";
		}
		return hql;
	}

	public void change(Dic dic) {
		if (dic.getIds() != null) {
			for (String id : dic.getIds().split(",")) {
				Tdic t = dicDao.get(Tdic.class, id.trim());
				if (t != null) {
					t.setCstatus(dic.getCstatus());
					dicDao.save(t);
				}
			}
		}
		
	}

	public void delete(String ids) {
		if (ids != null) {
			for (String id : ids.split(",")) {
				Tdic t = dicDao.get(Tdic.class, id.trim());
				if (t != null) {
					dicDao.delete(t);
				}
			}
		}
	}

	public void update(Dic dic) {
		Tdic t = dicDao.get(Tdic.class, dic.getCid());
		if (dic.getCkey() != null && dic.getCkey() != "") {
			t.setCkey(dic.getCkey());
		}
		if (dic.getCvalue() != null && dic.getCvalue() != "") {
			t.setCvalue(dic.getCvalue());
		}
		if (dic.getCtype() != null && dic.getCtype() != "") {
			t.setCtype(dic.getCtype());
		}
		if (dic.getCtypeName() != null && dic.getCtypeName() != "") {
			t.setCtypeName(dic.getCtypeName());
		}
		if (dic.getCbasetype() != null && dic.getCbasetype() != "") {
			Tdic temp = dicDao.get(Tdic.class, dic.getCbasetype());
			t.setTdic(temp);
		}
		if (dic.getCstatus() != null && dic.getCstatus() != "") {
			t.setCstatus(dic.getCstatus());
		}
		dicDao.save(t);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Dic> combobox(Dic dic) {
		List<Tdic> list=findAllTdic(dic);
		Tdic t=new Tdic();
		t.setCkey("请选择...");
		t.setCvalue("");
		list.add(0,t);
		return getDicsFromTdics(list);
		 
	}
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Dic> comboxGroup(Dic dic) {
		String hql = "from Tdic t where t.cstatus='0' and t.ctype = '" + dic.getCtype() + "' and t.cvalue!='"+Constants.DIC_TYPE_YHZB_WY+"' and t.cvalue!='"+Constants.DIC_TYPE_YHZB_CBDW+"'";
		       hql += " order by t.cvalue";
		List<Tdic> list=dicDao.find(hql);
		Tdic t=new Tdic();
		t.setCkey("请选择...");
		t.setCvalue("");
		list.add(0,t);
		return getDicsFromTdics(list);
		 
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Tdic> findAllTdic(Dic dic) {
		String hql = "from Tdic t where t.cstatus='0' and t.ctype = '" + dic.getCtype() + "'";
			   hql += " order by t.cvalue";
		return dicDao.find(hql);
	}
	
	private void findAllTdic(){
		String hql = "from Tdic t ";
		dicList=dicDao.find(hql);
	}
	
	public String getCkey(Dic dic)throws Exception {
		
		if(dicList==null){
			findAllTdic();
		}
		for (Tdic t : dicList) {
			if(dic.getCtype().trim().equals(t.getCtype().trim())&& dic.getCvalue().trim().equals(t.getCvalue().trim())){
				return t.getCkey();
			}
		}
		return "";
	}
//	/*
//	 * 根据转入的数值，查找对应的汉字名称	
//	*/
//	public String findDicName(String type,String value){
//		String name="";
//		if(value==null)return name;
//		List<Tdic> list=dicDao.find("from Tdic t where t.ctype='" + type + "' order by CVALUE");
//		if(list!=null){
//			for(Tdic s : list){
//				if(value.equals(s.getCvalue())){
//					name=s.getCkey();
//					return name;
//				}
//			}
//		}
//		return name;
//	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<TreeNode> tree(Dic dic, boolean b) {
		List<Object> param = new ArrayList<Object>();
//		String hql = "from Tdic t where t.tdic is null order by t.cvalue";
		String hql = "from Tdic t where t.cid ='0' order by t.cvalue";
		if (dic != null && dic.getId() != null && !dic.getId().trim().equals("")) {
			hql = "from Tdic t where t.tdic.cid = ? order by t.cvalue";
			param.add(dic.getId());
		}
		List<Tdic> l = dicDao.find(hql, param);
		List<TreeNode> tree = new ArrayList<TreeNode>();
		for (Tdic t : l) {
			if(t.getTdic()!=null && "0".equals(t.getTdic().getCid())) {
				t.setTdics(null);
			}
			if("0".equals(t.getCid()) || "0".equals(t.getTdic().getCid())) 
			{
				tree.add(this.tree(t, b));
			} 
		}
		return tree;
	}
	private TreeNode tree(Tdic t, boolean recursive) {
		TreeNode node = new TreeNode();
		node.setId(t.getCid());
		node.setText(t.getCkey());
		Map<String, Object> attributes = new HashMap<String, Object>();
		node.setAttributes(attributes);
		if (t.getTdics() != null && t.getTdics().size() > 0 && "0".equals(t.getCid())) {
			node.setState("closed");
			if (recursive) {// 递归查询子节点
				List<Tdic> l = new ArrayList<Tdic>(t.getTdics());
				Collections.sort(l, new DicComparator());// 排序
				List<TreeNode> children = new ArrayList<TreeNode>();
				for (Tdic r : l) {
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
