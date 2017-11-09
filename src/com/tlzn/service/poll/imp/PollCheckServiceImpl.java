package com.tlzn.service.poll.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.poll.Tpoll;
import com.tlzn.model.poll.TpollCheck;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.SessionInfo;
import com.tlzn.pageModel.sys.Comp;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.poll.PollCheckServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Generator;
import com.tlzn.util.base.ResourceUtil;

@Service("pollCheckService")
public class PollCheckServiceImpl extends BaseServiceImpl implements PollCheckServiceI {

	private BaseDaoI<TpollCheck> pollCheckDAO;
	
	
	public BaseDaoI<TpollCheck> getPollCheckDAO() {
		return pollCheckDAO;
	}
	@Autowired
	public void setPollCheckDAO(BaseDaoI<TpollCheck> pollCheckDAO) {
		this.pollCheckDAO = pollCheckDAO;
	}

	/**
	 * 
	 * 新增
	 * 
	 * @param pollCheck,httpSession
	 * 
	 * @throws 	Exception
	 * 
	 */
	public void add(TpollCheck pollCheck, HttpSession httpSession)throws Exception {
		SessionInfo sessionInfo=(SessionInfo)httpSession.getAttribute(ResourceUtil.getSessionInfoName());
		pollCheck.setCheckId(Generator.getInstance().getPollCheckNO());
		pollCheck.setOperator(sessionInfo.getUserId());
		pollCheck.setCheckTime(new Date());
		pollCheckDAO.save(pollCheck);
		
	}
	/**
	 * 
	 * 社情民意审批情况
	 * 
	 * @param pCheck
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(TpollCheck pCheck)throws Exception {
		String hql=" from TpollCheck t1 where t1.poll.pollId='"+pCheck.getPoll().getPollId()+"'";
		hql+=" order by t1.checkTime";
		DataGrid j = new DataGrid();
		j.setRows(getPollCheckList(pollCheckDAO.find(hql)));
		return j;
	}
	/**
	 * 
		 * 数据转换
		 * 
		 * @param TpollArr
		 * 
		 * @throws Exception
		 * 
		 * @return 		List<Tpoll> 
	 */
	private List<TpollCheck> getPollCheckList(List<TpollCheck> listArr) throws Exception {
		List<TpollCheck> ads = new ArrayList<TpollCheck>();
		if (listArr != null && listArr.size() > 0) {
			for (TpollCheck t : listArr) {
				t.setOperatorName(this.findNameByUserid(t.getOperator()));
				t.setDiff(t.getDiff()==null? "":t.getDiff().replaceAll("\"", "'"));//将双引号替换成单引号 
				t.setDiff(t.getDiff()==null? "":t.getDiff().replaceAll("\n", "&#10;").replaceAll("\r", ""));////将换行符替换成     &#10;
				Tpoll poll=t.getPoll();
				poll.setContent("");
				poll.setEndContent("");
				t.setPoll(poll);
				//t.setDiff("");
				ads.add(t);
			}
		}
		return ads;
	}
	/**
	 * 
		 * 数据查询
		 * 
		 * @param pCheck
		 * 
		 * 
		 * @return 		List<TpollCheck> 
	 */
	
	private List<TpollCheck> find(TpollCheck pCheck,String hql) {

		hql = addWhere(pCheck, hql);
		
		if (pCheck.getSort() != null && pCheck.getOrder() != null) {
			hql += " order by " + pCheck.getSort() + " " + pCheck.getOrder();
		}else {
			hql+=" order by t1.pollId";
		}
		
		return pollCheckDAO.find(hql,pCheck.getPage(),pCheck.getRows());
	}
	/**
	 * 
		 * 数据统计
		 * 
		 * @param poll
		 * 
		 * 
		 * @return 		Long
	 */
	
	private Long total(TpollCheck pCheck,String hql) {
		hql = addWhere(pCheck, hql);
		hql = "select count(*) "+hql;
		return pollCheckDAO.count(hql);
	}
	/**
	 * 
		 * 条件设置
		 * 
		 * @param pCheck，hql
		 * 
		 * 
		 * @return 		String
	 */
	private String addWhere(TpollCheck pCheck, String hql) {
		if (pCheck.getPoll().getPollId() != null && !"".equals(pCheck.getPoll().getPollId())) {
			hql += " and t1.poll.pollId = '"+pCheck.getPoll().getPollId()+"'";
		}
		
		return hql;
	}
	
}
