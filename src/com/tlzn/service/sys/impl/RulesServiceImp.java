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

import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.sys.Tdolog;
import com.tlzn.model.sys.Trules;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.sys.RulesServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Generator;
@Service("rulesService")
public class RulesServiceImp extends BaseServiceImpl implements RulesServiceI{
	
	protected final Log log = LogFactory.getLog(getClass());
	
	
	private BaseDaoI<Trules> rulesDao;
	
	public BaseDaoI<Trules> getRulesDao() {
		return rulesDao;
	}
	@Autowired
	public void setRulesDao(BaseDaoI<Trules> rulesDao) {
		this.rulesDao = rulesDao;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(Trules rules) throws Exception {
		DataGrid j = new DataGrid();
		j.setRows(getRulesList(find(rules)));
		j.setTotal(total(rules));
		return j;
	}
	private List<Trules> getRulesList(List<Trules> TrulesArr) throws Exception {
		List<Trules> ads = new ArrayList<Trules>();
		if (TrulesArr != null && TrulesArr.size() > 0) {
			for (Trules t : TrulesArr) {
				t.setStatusName(this.findDicName("STATUS", t.getStatus()));
				t.setTypeName(this.findDicName("RULESTYPE", t.getTypeid()));
				ads.add(t);
			}
		}
		return ads;
	}
	private List<Trules> find(Trules rules) {
		String hql = "from Trules t where 1=1 ";

		hql = addWhere(rules, hql);

		if (rules.getSort() != null && rules.getOrder() != null) {
			hql += " order by " + rules.getSort() + " " + rules.getOrder();
		}
		return rulesDao.find(hql, rules.getPage(), rules.getRows());
	}
	private Long total(Trules rules) {
		String hql = "select count(*) from Trules t where 1=1 ";
		hql = addWhere(rules, hql);
		return rulesDao.count(hql);
	}

	private String addWhere(Trules rules, String hql) {
		if (rules.getName() != null && !"".equals(rules.getName())) {
			hql += " and t.name like '%"+rules.getName()+"%'";
		}
		if (rules.getStatus() != null && !"".equals(rules.getStatus())) {
			hql += " and t.status ='"+rules.getStatus()+"'";
		}
		return hql;
	}
	public void save(Trules rules) throws Exception {
		rules.setId(Generator.getInstance().getRulesNO());
		rules.setCreateTime(new Date());
		rulesDao.save(rules);
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_SYS);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_ADD);
		dolog.setKeyId(rules.getId());
		dolog.setInfo( "[计分规则]新增计分规则");
		this.saveLog(dolog);
		
	}
	public Trules getTrules(String id) throws Exception {
		return rulesDao.get(Trules.class, id);
		
	}

	public void update(Trules rules) throws Exception {
		/*Trules r=rulesDao.get(Trules.class, rules.getId());
		r.setTypeid(rules.getTypeid());
		r.setName(rules.getName());
		r.setScore(rules.getScore());
		r.setStatus(rules.getStatus());
		r.setMemo(rules.getMemo());*/
		rulesDao.saveOrUpdate(rules);
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_SYS);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_EDIT);
		dolog.setKeyId(rules.getId());
		dolog.setInfo( "[计分规则]编辑计分规则");
		this.saveLog(dolog);
		
	}
	public void change(Trules rules) throws Exception {
		if (!"".equals(rules.getIds()) && null!=rules.getIds()) {
			for (String id : rules.getIds().split(",")) {
				Trules r=rulesDao.get(Trules.class, id);
				r.setStatus(rules.getStatus());
				if (r != null) {
					rulesDao.update(r);
					Tdolog dolog=new Tdolog();
					dolog.setLogType(Constants.LOG_TYPE_CODE_SYS);
					dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_SET);
					dolog.setKeyId(rules.getId());
					dolog.setInfo( "[计分规则]设置计分规则状态");
					this.saveLog(dolog);
				}
			}
		}
		
	}
	/**
	 * 下拉列表数据获取
	 */
	public List<Trules> combobox() throws Exception {
		return rulesDao.find("from Trules t where status="+Constants.CODE_TYPE_QYTY_YES+" order by t.id");
	}
}
