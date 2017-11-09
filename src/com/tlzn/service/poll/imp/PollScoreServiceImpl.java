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
import com.tlzn.model.poll.TpollScore;
import com.tlzn.model.sys.Tdolog;
import com.tlzn.model.sys.Trules;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.poll.PollScoreServiceI;
import com.tlzn.service.poll.PollServiceI;
import com.tlzn.service.sys.RulesServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Generator;
import com.tlzn.util.base.PropertyUtil;
import com.tlzn.util.base.Util;
@Service("pollScoreService")
public class PollScoreServiceImpl extends BaseServiceImpl implements PollScoreServiceI{

	//计分
	private BaseDaoI<TpollScore> pollScoreDAO;
	//记分规则
	private RulesServiceI rulesService;
	
	private PollServiceI pollService;

	/**
	 * 
		 * 记分登记
		 * 
		 * @param pollScore
		 * 
		 * @throws Exception
		 * 
	 */
	public String addPollInsAndScor(TpollScore pollScore) throws Exception {
		String res="";
		boolean flg=isExistBypollScore(pollScore);
		if (!flg) {
			pollScore.setScoreId(Generator.getInstance().getPollScoreNO());
			pollScore.setCreatTime(new Date());
			pollScoreDAO.save(pollScore);
			res="保存成功！";
		}else{
			res="计分信息已存在！";
		}
		
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_ADD);
		dolog.setKeyId(pollScore.getPoll().getPollId());
		dolog.setInfo( "[社情民意]计分信息批量登记（计分规则Id："+pollScore.getRules().getId()+"）");
		this.saveLog(dolog);
		return res;
	}
	

	/**
	 * 
		 * 批示记分列表
		 * 
		 * @param poll
		 * 
		 * @throws Exception
		 * 
		 * @return  Tpoll
	 */
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public DataGrid scoreRecordDatagrid(TpollScore pollScore) throws Exception {
		DataGrid j = new DataGrid();
		j.setRows(editScoreRecord(find(pollScore)));
		j.setTotal(total(pollScore));
		return j;
	}
	private List<TpollScore> editScoreRecord(List<TpollScore> scoreList) throws Exception {
		List<TpollScore> resultList = new ArrayList<TpollScore>();
		if (scoreList != null && scoreList.size() > 0) {
			for (TpollScore t : scoreList) {
				Tpoll poll=new Tpoll();
				PropertyUtil.copyProperties(poll,t.getPoll());
				poll.setContent("");
				poll.setEndContent("");
				t.setPoll(poll);
				Trules rules=new Trules();
				PropertyUtil.copyProperties(rules,t.getRules());
				rules.setTypeName(this.findDicName(Constants.DIC_TYPE_RULESTYPE,rules.getTypeid()));
				t.setRules(rules);
				if(t.getComment()!=null && !"".equals(t.getComment())) {
					t.setComment(t.getComment().replace("\n", "<br/>").replace("\r", ""));
				}
				if(t.getMemo()!=null && !"".equals(t.getMemo())) {
					t.setMemo(t.getMemo().replace("\n", "<br/>").replace("\r", ""));
				}
				resultList.add(t);
			}
		}
		return resultList;
	}
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	private List<TpollScore> find(TpollScore pollScore) {
		String hql = "from TpollScore t where t.poll.pollId="+pollScore.getPoll().getPollId()+" order by t.creatTime" ;
		return pollScoreDAO.find(hql, pollScore.getPage(),pollScore.getRows());
	}
	private Long total(TpollScore pollScore) {
		String hql = "select count(*) from TpollScore t where t.poll.pollId="+pollScore.getPoll().getPollId()+" order by t.creatTime" ;
		return pollScoreDAO.count(hql);
	}
	
	/**
	 * 批注和备注查询
	 */
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public TpollScore commMemo_find(TpollScore pollScore) throws Exception {
		String scoreId = pollScore.getScoreId();
		TpollScore ps = null;
		if(scoreId!=null && !"".equals(scoreId)) {
			ps = pollScoreDAO.get(TpollScore.class,scoreId);
		}
		return ps;
	}

	/**
	 * 批注和备注提交
	 */
	public void commMemo_submit(TpollScore ps, HttpSession httpSession) throws Exception {
		String scoreId =ps.getScoreId();
		TpollScore pollScore = null;
		if(scoreId!=null && !"".equals(scoreId)) {
			pollScore=pollScoreDAO.get(TpollScore.class, scoreId);
		}
		
		if(pollScore!=null) {
			pollScore.setComment(ps.getComment());
			pollScore.setMemo(ps.getMemo());
			pollScoreDAO.update(pollScore);
		}
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_EDIT);
		dolog.setKeyId(pollScore.getPoll().getPollId());
		dolog.setInfo( "[社情民意]计分信息修改（计分信息Id："+pollScore.getScoreId()+"）");
		this.saveLog(dolog);
	}
	/**
	 * 
		 * 批注和备注删除
		 * 
		 * @param poll
		 * 
		 * @throws Exception
		 * 
	 */
	public void scoreRecordDel(TpollScore ps) throws Exception {
		String scoreId = ps.getScoreId();
		if(scoreId!=null && !"".equals(scoreId)) {
			TpollScore pollScore = pollScoreDAO.get(TpollScore.class,scoreId);
			pollScoreDAO.delete(pollScore);
			
			Tdolog dolog=new Tdolog();
			dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
			dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_DEL);
			dolog.setKeyId(pollScore.getPoll().getPollId());
			dolog.setInfo( "[社情民意]计分信息删除（计分规则Id："+pollScore.getRules().getId()+"）");
			this.saveLog(dolog);
		}
	}
	
	/**
	 * 
		 * 判断是否为领导批示
		 * 
		 * @param ps
		 * 
		 * @throws Exception
		 * 
	 */
	public boolean isLeader(TpollScore ps) throws Exception {
		boolean res=false;
		String sql="from TpollScore t where t.poll.pollId='"+ps.getPoll().getPollId()+"' and t.rules.typeid='"+Constants.DIC_TYPE_RULESTYPE_LDPS+"'";
		List<TpollScore> list=pollScoreDAO.find(sql);
		if(list.size()>0)res=true;
		return res;
	}
	/**
	 * 
		 * 计分批量登记
		 * 
		 * @param pollScore
		 * 
		 * @throws Exception
		 * 
	 */
	public void batchAddScor(TpollScore pollScore) throws Exception {
		String[] rulesIds=pollScore.getIds().split(",");
		System.out.println("rulesIds==="+pollScore.getIds());
		for (int i = 0; i < rulesIds.length; i++) {
			TpollScore ps=new TpollScore();
			Trules rules=rulesService.getTrules(rulesIds[i]);
			ps.setRules(rules);
			ps.setPoll(pollScore.getPoll());
			boolean flg=isExistBypollScore(ps);
			if (!flg) {
				ps.setScoreId(Generator.getInstance().getPollScoreNO());
				ps.setCreatTime(new Date());
				pollScoreDAO.save(ps);
			}
			
		}
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_ADD);
		dolog.setKeyId(pollScore.getPoll().getPollId());
		dolog.setInfo( "[社情民意]计分信息批量登记（计分规则Ids："+pollScore.getIds()+"）");
		this.saveLog(dolog);
		
	}
	/**
	 * 
		 * 通过社情民意ID与计分规则ID判断计分信息是否存在
		 * 
		 * @param pScore
		 * 
		 * @throws 	Exception
		 * 
		 * @return 		返回值
	 */
	private boolean isExistBypollScore(TpollScore pScore)throws Exception{
		boolean res=false;
		String hql="from TpollScore t where t.poll.pollId='"+pScore.getPoll().getPollId()+"' and t.rules.id='"+pScore.getRules().getId()+"'";
		List<TpollScore> list =pollScoreDAO.find(hql);
		System.out.println("long===="+list.size());
		if (list.size()>0) {
			res=true;
		}
		return res;
		
	}
	
	
	public PollServiceI getPollService() {
		return pollService;
	}
	@Autowired
	public void setPollService(PollServiceI pollService) {
		this.pollService = pollService;
	}
	
	public RulesServiceI getRulesService() {
		return rulesService;
	}
	@Autowired
	public void setRulesService(RulesServiceI rulesService) {
		this.rulesService = rulesService;
	}

	public BaseDaoI<TpollScore> getPollScoreDAO() {
		return pollScoreDAO;
	}
	
	@Autowired
	public void setPollScoreDAO(BaseDaoI<TpollScore> pollScoreDAO) {
		this.pollScoreDAO = pollScoreDAO;
	}

	
}
