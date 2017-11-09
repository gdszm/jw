package com.tlzn.service.sys.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.sys.Tdolog;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.sys.Dolog;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.sys.DologServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Util;

/**
 * 日志业务类
 * @author zhangle
 */
@Service("dologService")
public class DologServiceImpl extends BaseServiceImpl implements DologServiceI {

	private BaseDaoI<Tdolog> dologDAO;
	
	
	
	public BaseDaoI<Tdolog> getDologDAO() {
		return dologDAO;
	}
	@Autowired
	public void setDologDAO(BaseDaoI<Tdolog> dologDAO) {
		this.dologDAO = dologDAO;
	}

	/**
	 * 
		 * 获取日志
		 * 
		 * @param dolog
		 * 
		 * @throws 	DataAccessException
		 * @throws 	AppException
		 * 
		 * @return 		DataGrid返回值
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(Dolog dolog) throws Exception {
		DataGrid j = new DataGrid();
		j.setRows(getDologlList(find(dolog)));
		j.setTotal(total(dolog));
		return j;
	}
	/**
	 * 
		 * 数据转换
		 * 
		 * @param tdologArr
		 * 
		 * @throws Exception
		 * 
		 * @return 		List<Dolog> 
	 */
	private List<Dolog> getDologlList(List<Tdolog> tdologArr) throws Exception {
		List<Dolog> ads = new ArrayList<Dolog>();
		if (tdologArr != null && tdologArr.size() > 0) {
			for (Tdolog t : tdologArr) {
				Dolog d = new Dolog();
				BeanUtils.copyProperties(t,d);
				String title=findDicName(Constants.LOG_TYPE_OPERTYPE,d.getTitle());
				
				if(Util.getInstance().isEmpty(title)){
					title=d.getTitle();
				}
				System.out.println("title===="+title);
				d.setTitle(title);
				d.setLogTypeName(findDicName(Constants.LOG_TYPE_CODE,d.getLogType()));
				ads.add(d);
			}
		}
		System.out.println("ads===="+ads.size());
		return ads;
	}
	public List<Tdolog> find(Dolog dolog) throws Exception {
		String hql = "from Tdolog t where 1=1 ";

		hql = addWhere(dolog, hql);

		if (dolog.getSort() != null && dolog.getOrder() != null) {
			hql += " order by " + dolog.getSort() + " " + dolog.getOrder();
		}
		return dologDAO.find(hql, dolog.getPage(), dolog.getRows());
	}

	private Long total(Dolog dolog) {
		String hql = "select count(*) from Tdolog t where 1=1 ";
		hql = addWhere(dolog, hql);
		return dologDAO.count(hql);
	}

	private String addWhere(Dolog dolog, String hql) {
		if (dolog.getTitle() != null && !"".equals(dolog.getTitle())) {
			hql += " and t.title = '"+dolog.getTitle()+"'";
		}
		if (dolog.getInfo() != null && !"".equals(dolog.getInfo())) {
			hql += " and t.info like '%"+dolog.getInfo()+"%' ";
		}
		if (dolog.getKeyId() != null && !"".equals(dolog.getKeyId())) {
			hql += " and t.keyId = '"+dolog.getKeyId()+"' ";
		}
		if (dolog.getLogType() != null && !"".equals(dolog.getLogType())) {
			hql += " and t.logType = '"+dolog.getLogType()+"' ";
		}
		if (dolog.getOperator() != null && !"".equals(dolog.getOperator())) {
			hql += " and t.operator like '%"+dolog.getOperator()+"%' ";
		}
		if (dolog.getStartDate() != null && !"".equals(dolog.getStartDate())&& dolog.getEndDate() != null && !"".equals(dolog.getEndDate())) {

			hql += " and t.operateTime Between  to_date('"+dolog.getStartDate()+"','YYYY-MM-DD') and to_date('"+dolog.getEndDate()+"','YYYY-MM-DD')";
		}
		return hql;
	}

}
