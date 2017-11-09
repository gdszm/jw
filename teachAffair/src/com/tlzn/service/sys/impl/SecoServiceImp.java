package com.tlzn.service.sys.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sun.org.apache.commons.beanutils.BeanUtils;
import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.Tcommitteeman;
import com.tlzn.model.sys.Tdolog;
import com.tlzn.model.sys.Tsecondary;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.sys.Comm;
import com.tlzn.pageModel.sys.Seco;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.sys.SecoServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Generator;
import com.tlzn.util.base.ResourceUtil;

@Service("secoService")
public class SecoServiceImp extends BaseServiceImpl implements SecoServiceI {
	private BaseDaoI<Tsecondary> secoDao;
	private BaseDaoI<Tcommitteeman> commDao;
	protected final Log log = LogFactory.getLog(getClass());
	
	public BaseDaoI<Tsecondary> getSecoDao() {
		return secoDao;
	}
	@Autowired
	public void setSecoDao(BaseDaoI<Tsecondary> secoDao) {
		this.secoDao = secoDao;
	}

	public BaseDaoI<Tcommitteeman> getCommDao() {
		return commDao;
	}
	@Autowired
	public void setCommDao(BaseDaoI<Tcommitteeman> commDao) {
		this.commDao = commDao;
	}
	public void save(Seco seco) throws Exception{
		this.setStatusZero(seco);
		Tsecondary t=new Tsecondary();
		BeanUtils.copyProperties(t,seco);
		t.setSecondaryId(Generator.getInstance().getSecondaryNO());
		t.setCreateTime(new Date());
		secoDao.save(t);
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_SYS);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_ADD);
		dolog.setKeyId(t.getSecondaryId());
		dolog.setInfo( "[新增届次]新增"+t.getSecondayName()+"届次");
		this.saveLog(dolog);
	}

	public void update(Seco seco) throws Exception{
		this.setStatusZero(seco);
		Tsecondary t = secoDao.get(Tsecondary.class, seco.getSecondaryId());
		BeanUtils.copyProperties(t,seco);
		secoDao.update(t);
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_SYS);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_EDIT);
		dolog.setKeyId(t.getSecondaryId());
		dolog.setInfo( "[修改届次]修改"+t.getSecondayName()+"届次信息");
		this.saveLog(dolog);
	}
	
	public void change(Seco seco,HttpSession httpSession) throws Exception {
		if (!"".equals(seco.getIds()) && null!=seco.getIds()) {
			for (String id : seco.getIds().split(",")) {
				Tsecondary t = secoDao.get(Tsecondary.class, id.trim());
				this.setStatusZero(seco);
				t.setStatus(seco.getStatus());
				if (t != null) {
					secoDao.update(t);
					httpSession.setAttribute(ResourceUtil.getSessionSeco(), t);
					Tdolog dolog=new Tdolog();
					dolog.setLogType(Constants.LOG_TYPE_CODE_SYS);
					dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_SET);
					dolog.setKeyId(t.getSecondaryId());
					dolog.setInfo( "[设置届次状态]设置"+t.getSecondayName()+"届次状态");
					this.saveLog(dolog);
				}
			}
		}
	}
	public void setSave(String left,String right,String secoid) throws Exception{
		String[] leftarr=left.split(",");
		String[] rightarr=right.split(",");
		System.out.println("leftarr===="+leftarr[0]);
		System.out.println("right===="+rightarr.length);
		if (leftarr.length>0) {
			for (int i = 0; i < leftarr.length; i++) {
				Tcommitteeman tcomm=commDao.get(Tcommitteeman.class, leftarr[i]);
				String secoids="";
				if (tcomm!=null) {
					secoids=tcomm.getSecondaryCode();
				}
				if(secoids.indexOf(secoid)!=-1){
					String[] secoarr=secoids.split(",");
					for (int j = 0; j < secoarr.length; j++) {
						if(secoid.equals(secoarr[j])){
							for (int j2 = j; j2 < secoarr.length-1; j2++) {
								secoarr[j2]=secoarr[j2+1];
							}
							String secocode="";
							for (int l = 0; l < secoarr.length-1; l++) {
								secocode+=secoarr[l]+",";
							}
							secocode=secocode.substring(0,secocode.length()-1);
							tcomm.setSecondaryCode(secocode);
							commDao.saveOrUpdate(tcomm);
							break;
						}
					}
				}
				
			}
		}
		if (rightarr.length>0) {
			for (int i = 0; i < rightarr.length; i++) {
				Tcommitteeman tcomm=commDao.get(Tcommitteeman.class, rightarr[i]);
				String secoids="";
				if (tcomm!=null) {
					secoids=tcomm.getSecondaryCode();
				}
				if("".equals(secoids)){
					secoids=secoid;
					tcomm.setSecondaryCode(secoids);
					commDao.saveOrUpdate(tcomm);
				}else if(secoids.indexOf(secoid)==-1){
					secoids+=","+secoid;
					tcomm.setSecondaryCode(secoids);
					commDao.saveOrUpdate(tcomm);
				}
			}
		}
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_SYS);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_SET);
		dolog.setKeyId(secoid);
		dolog.setInfo( "[设置届次提案人]设置届次ID"+secoid+"的提案人");
		this.saveLog(dolog);
	}
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(Seco seco) throws Exception{
		DataGrid j = new DataGrid();
		j.setRows(getSecosFromTSecos(find(seco)));
		j.setTotal(total(seco));
		return j;
	}

	@SuppressWarnings("unchecked")
	public DataGrid datagridNoPage(Seco seco) throws Exception{
		DataGrid j = new DataGrid();
		j.setRows(getSecosFromTSecos(findNoPage(seco)));
		j.setTotal(total(seco));
		List<Seco> list=j.getRows();
		if(list!=null){
			for(int i=0;i<list.size();i++){
				list.get(i).setStatusName(this.findDicName("STATUS", list.get(i).getStatus()));
			}
			j.setRows(list);
		}
		return j;
	}

	private List<Seco> getSecosFromTSecos(List<Tsecondary> Tsecos) throws Exception {
		List<Seco> ads = new ArrayList<Seco>();
		if (Tsecos != null && Tsecos.size() > 0) {
			for (Tsecondary t : Tsecos) {
				Seco p = new Seco();
				BeanUtils.copyProperties(p,t);
				p.setStatusName(this.findDicName("STATUS", t.getStatus()));
				p.setPeriodName(this.findDicName("HYPS", t.getPeriod()));
				ads.add(p);
			}
		}
		return ads;
	}

	private List<Tsecondary> find(Seco seco) {
		String hql = "from Tsecondary t where 1=1 ";

		List<Object> values = new ArrayList<Object>();
		hql = addWhere(seco, hql, values);

		if (seco.getSort() != null && seco.getOrder() != null) {
			hql += " order by " + seco.getSort() + " " + seco.getOrder();
		}
		return secoDao.find(hql, values, seco.getPage(), seco.getRows());
	}
	
	public Tsecondary find(String secondaryId)throws Exception {
		return secoDao.get(Tsecondary.class, secondaryId);
	}

	private List<Tsecondary> findNoPage(Seco seco) {
		String hql = "from Tsecondary t where 1=1 ";

		List<Object> values = new ArrayList<Object>();
		hql = addWhere(seco, hql, values);

		if (seco.getSort() != null && seco.getOrder() != null) {
			hql += " order by " + seco.getSort() + " " + seco.getOrder();
		}else{
			hql += " order by t.secondaryId DESC";
		}
		return secoDao.find(hql, values);
	}

	private Long total(Seco seco) {
		String hql = "select count(*) from Tsecondary t where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(seco, hql, values);
		return secoDao.count(hql, values);
	}

	private String addWhere(Seco seco, String hql, List<Object> values) {
		if (seco.getSecondayName() != null && !"".equals(seco.getSecondayName())) {
			hql += " and t.secondayName like ? ";
			values.add(seco.getSecondayName());
		}
		if (seco.getYear() != null && !"".equals(seco.getYear())) {
			hql += " and t.year =? ";
			values.add(seco.getYear()); 
		}
		if (seco.getStatus() != null && !"".equals(seco.getStatus())) {
			hql += " and t.status =? ";
			values.add(seco.getStatus());
		}
		return hql;
	}

	private void setStatusZero(Seco s) throws Exception{
		if("1".equals(s.getStatus())){
			String hql="update Tsecondary s set s.status='" + Constants.CODE_TYPE_YESNO_NO  + "'";
			secoDao.executeHql(hql);
		}
	}
	
	public Seco findCurrent() throws Exception{
		String hql="from Tsecondary where status='" + Constants.CODE_TYPE_YESNO_YES  + "'";
		List<Seco> list= this.getSecosFromTSecos(secoDao.find(hql));
		
		return list.size()>0 ? list.get(0) : null;
	}
	
	public List<Tsecondary> combobox() throws Exception{
		return secoDao.find("from Tsecondary t");
	}
	
	public List<Tsecondary> comboboxYear() throws Exception{
		return secoDao.find("select distinct year,year as y from Tsecondary order by year desc");
	}
	/**
	 * 将届次属性会议改为平时
	 */
	public void setAtt(Seco seco) throws Exception {
		Tsecondary t = secoDao.get(Tsecondary.class, seco.getSecondaryId());
		System.out.println("secondaryId==="+t.getSecondaryId());
		t.setPeriod(Constants.CODE_TYPE_HYPS_PS);
		secoDao.update(t);
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_SYS);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_SET);
		dolog.setKeyId(t.getSecondaryId());
		dolog.setInfo( "[设置届次]设置届次"+t.getSecondayName()+"为会议/平时");
		this.saveLog(dolog);
	}


}
