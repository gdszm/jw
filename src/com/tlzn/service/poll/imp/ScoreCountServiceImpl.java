package com.tlzn.service.poll.imp;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.poll.Tpoll;
import com.tlzn.model.poll.TpollScore;
import com.tlzn.model.sys.Trules;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.poll.PollScoreServiceI;
import com.tlzn.service.poll.PollServiceI;
import com.tlzn.service.poll.ScoreCountServiceI;
import com.tlzn.service.sys.RulesServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.NumberFormatTools;
import com.tlzn.util.base.PropertyUtil;
import com.tlzn.util.base.Util;

@Service("scoreCountService")
public class ScoreCountServiceImpl extends BaseServiceImpl implements ScoreCountServiceI {
	BaseDaoI<TpollScore> countDao;

	public BaseDaoI<TpollScore> getCountDao() {
		return countDao;
	}
	@Autowired
	public void setCountDao(BaseDaoI<TpollScore> countDao) {
		this.countDao = countDao;
	}
	
	private PollScoreServiceI pollScoreService;
	
	private RulesServiceI rulesService;
	
	private PollServiceI pollService;
	

	public PollScoreServiceI getPollScoreService() {
		return pollScoreService;
	}
	@Autowired
	public void setPollScoreService(PollScoreServiceI pollScoreService) {
		this.pollScoreService = pollScoreService;
	}
	
	public RulesServiceI getRulesService() {
		return rulesService;
	}
	@Autowired
	public void setRulesService(RulesServiceI rulesService) {
		this.rulesService = rulesService;
	}
	
	public PollServiceI getPollService() {
		return pollService;
	}
	@Autowired
	public void setPollService(PollServiceI pollService) {
		this.pollService = pollService;
	}
	
	/**
	 * 社情民意积分统计
	 */
	@SuppressWarnings("unchecked")
	public String sumCount(String groupType, String secondaryId) throws Exception {
		String json="{\"total\":0,\"rows\":[";	//datagrid数据
		String sql="select t.code,t.name,t.group_code,ad.adoptnum,iss.issnum,ad.tosum rnum from tcommitteeman t" 
		 +" left join (select tp.create_man,count(*) adoptnum,sum(tp.scsum) tosum from  " 
		 +" (select t0.secondary_id,t0.poll_id,t0.adopt_flg,t0.create_man,t2.scsum from tpoll t0  " 
		 +" left join  (select t1.poll_id,sum(t1.score) scsum from " 
		 +"      (select tps.poll_id,tps.rules_id,r.score from tpoll_score tps left join trules r on r.id=tps.rules_id) t1 " 
		 +"       group by t1.poll_id) t2 " 
		 +"  on t2.poll_id=t0.poll_id) tp where tp.adopt_flg='"+Constants.CODE_TYPE_YESNO_YES+"'";
		if(!Util.getInstance().isEmpty(secondaryId)){
			sql+="and  tp.secondary_id ='"+secondaryId+"'";
		}
		 sql+=" group by tp.create_man ) ad on ad.create_man=t.code" 
		 +" left join (select tpp.create_man,count(*) issnum from tpoll tpp where tpp.adopt_flg='"+Constants.CODE_TYPE_YESNO_YES+"'";
		if(!Util.getInstance().isEmpty(secondaryId)){
			sql+="and  tpp.secondary_id ='"+secondaryId+"'";
		} 
		 sql+=" and exists " 
		 +"  (select s.poll_id from tpoll_score s where exists " 
		 +"    (select tr.id from trules tr where tr.typeid='"+Constants.DIC_TYPE_RULESTYPE_LDPS+"' and tr.id=s.rules_id) and s.poll_id=tpp.poll_id )  group by tpp.create_man ) iss" 
		 +" on iss.create_man=t.code where 1=1 ";
		
		
		if(!Util.getInstance().isEmpty(groupType)){
			sql+="and  t.GROUP_CODE='"+groupType+"'";
		}
		if(!Util.getInstance().isEmpty(secondaryId)){
			sql+="and  t.SECONDARY_CODE like'%"+secondaryId+"%'";
		}
		sql+=" order by to_number (ad.tosum) desc";
		 List<Object[]> list =(List<Object[]>) countDao.executeCountSql(sql);
		 
		 sql="select t0.secondary_id,t0.poll_id,t0.adopt_flg,t0.create_man,t2.scsum from tpoll t0 " 
			 +" left join  (select t1.poll_id,sum(t1.score) scsum from "
			 +"     (select tps.poll_id,tps.rules_id,r.score from tpoll_score tps left join trules r on r.id=tps.rules_id) t1 " 
			 +"      group by t1.poll_id) t2 "
			 +" on t2.poll_id=t0.poll_id  where instr(t0.create_man,',')>0 and t0.adopt_flg='"+Constants.CODE_TYPE_YESNO_YES+"'";
		 	if(!Util.getInstance().isEmpty(secondaryId)){
				sql+="and  t0.secondary_id ='"+secondaryId+"'";
			} 
		 List<Object[]> shlist =(List<Object[]>) countDao.executeCountSql(sql);
		 List<Object[]> reslist=new ArrayList<Object[]>();
		 for (Iterator it = list.iterator(); it.hasNext();) {
				Object[] obj = (Object[]) it.next();
				for (Iterator sh = shlist.iterator(); sh.hasNext();) {
					Object[] shobj = (Object[]) sh.next();
					String[] manarr=shobj[3].toString().split(",");
					Tpoll tpoll=new Tpoll();
					tpoll.setPollId(shobj[1].toString());
					TpollScore tps=new TpollScore();
					tps.setPoll(tpoll);
					boolean isLeader=pollScoreService.isLeader(tps);
					DecimalFormat df2  = new DecimalFormat("###.00");//这样为保持2位
					String score=df2.format(((float)NumberFormatTools.getInstance().toInteger(shobj[4])/manarr.length));
					for (String str : manarr) {
						if (str.equals(obj[0])) {
							if(isLeader){
								obj[4]=NumberFormatTools.getInstance().toInteger(obj[4])+1;
							}
							obj[3]=NumberFormatTools.getInstance().toInteger(obj[3])+1;
							obj[5]=NumberFormatTools.getInstance().toFloat(obj[5])+NumberFormatTools.getInstance().toFloat(score);
							System.out.println("score="+obj[5]);
						}
						
					}
					
				 }
				reslist.add(obj);
			}
		 
		 
		 Collections.sort(reslist, Collections.reverseOrder(new Comparator<Object[]>() {
			public int compare(Object[] o1, Object[] o2) {
				 int flag=NumberFormatTools.getInstance().toFloat(o1[5]).compareTo(NumberFormatTools.getInstance().toFloat(o2[5]));
				  if(flag==0){
				   return NumberFormatTools.getInstance().toFloat(o1[3]).compareTo(NumberFormatTools.getInstance().toFloat(o2[3]));
				  }else{
				   return flag;
				  }  
			}
		}));
		 
		for(int i=0;i<reslist.size();i++){
			Object[] objs=reslist.get(i);
			json+="{\"code\":\""+objs[0]+"\",\"name\":\""+objs[1]+"\",";
			json+="\"groupcode\":\""+objs[2]+"\",";
			json+="\"adoptnum\":";
			if(objs[3]==null){
				json+="0";
			}else {
				json+=objs[3];
			}
			json+=",";
			json+="\"issnum\":";
			if(objs[4]==null){
				json+="0";
			}else {
				json+=objs[4];
			}
			json+=",";
			json+="\"tosum\":";
			if(objs[5]==null){
				json+="0";
			}else {
				json+=objs[5];
			}
			
			json+="},";
		}
		if(",".equals(json.substring(json.length()-1))){
			json=json.subSequence(0,json.length()-1)+"]}";
		}else{
			json+="]}";
		}
		System.out.println("sql=="+sql);
		System.out.println("json=="+json);
		return json;
	}
	/**
	 * 社情民意积分明细表头数据
	 */
	public String getColumns() throws Exception {
		String json="[{title:'提交者',field:'createMan',align:'center',width:80,rowspan:2},{title:'撰稿人',field:'writer',align:'center',width:80,rowspan:2},"
		+"{title:'社情民意类型',field:'pollType',align:'center',width:100,rowspan:2},{title:'提交日期',field:'createTime',align:'center',width:150,rowspan:2},";
		String sql="select t.typeid,count(*) typenum from trules t group by t.typeid";
		List<Object[]> typelist =(List<Object[]>) countDao.executeCountSql(sql);
		for (Iterator it = typelist.iterator(); it.hasNext();) {
			Object[] objs = (Object[]) it.next();
			json+="{title:'"+this.findDicName(Constants.DIC_TYPE_RULESTYPE, objs[0].toString())+"',align:'center',colspan:"+objs[1]+"},";
			
		}
		json=json.substring(0,json.length()-1);
		json+="],";
		List<Trules> ruleList=rulesService.combobox();
		if(ruleList.size()>0)json+="[";
		for (Iterator it = ruleList.iterator(); it.hasNext();) {
			Trules tr = (Trules) it.next();
			json+="{title:'"+tr.getName()+"',field:'"+tr.getId()+"',align:'center',width:100,formatter: function(value, row) {"
				  +"var str=\"\";if(value!=undefined){str=value}"
					+"  return \"<span title='\"+row.comm" + tr.getId()+ "+\"'>\"+str+\"</span>\";"
				+"}},";
		}
		json=json.substring(0,json.length()-1);
		json+="]";
		
		System.out.println("json=="+json);
		return json;
	}
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public String detailCount(TpollScore pollScore)	throws Exception {
		String json="{\"total\":0,\"rows\":[";	//datagrid数据
		String hql="from TpollScore t where 1=1";
		if(!Util.getInstance().isEmpty(pollScore.getPoll().getPollType())){
			hql+=" and  t.poll.pollType ='"+pollScore.getPoll().getPollType()+"'";
		} 
		if(!Util.getInstance().isEmpty(pollScore.getPoll().getTsecondary().getSecondaryId())){
			hql+=" and  t.poll.tsecondary.secondaryId ='"+pollScore.getPoll().getTsecondary().getSecondaryId()+"'";
		}
		if(!Util.getInstance().isEmpty(pollScore.getPoll().getPollCode())){
			hql+=" and  t.poll.pollCode ='"+pollScore.getPoll().getPollCode()+"'";
		} 
		if(!Util.getInstance().isEmpty(pollScore.getPoll().getTitle())){
			hql+=" and  t.poll.title like '%"+pollScore.getPoll().getTitle()+"%'";
		} 
		if (!Util.getInstance().isEmpty(pollScore.getPoll().getCreateMan())) {
			hql += " and ( t.poll.createMan like '%"+pollScore.getPoll().getCreateMan()+"%' or exists (select code from Tcommitteeman tt where tt.name like  '%"+pollScore.getPoll().getCreateMan()+"%' and t.poll.createMan like '%'||tt.code||'%'))";
		}
		hql+=" order by t.poll.pollId";
		List<TpollScore> tsList=countDao.find(hql);
		List<Tpoll> pollList=pollService.findIssueData(pollScore.getPoll());
		for (Iterator<Tpoll> it = pollList.iterator(); it.hasNext();) {
			Tpoll tp = it.next();
			json+="{\"pollId\":\""+tp.getPollId()+"\",\"pollCode\":\"";
			if(Util.getInstance().isEmpty(tp.getPollCode())){
				json+="\",";
			}else{
				json+=tp.getPollCode()+"\",";
			}
			json+="\"title\":\""+tp.getTitle()+"\",";
			
			json+="\"createMan\":\""+tp.getCreateManName()+"\",";
			json+="\"writer\":\"";
			if(Util.getInstance().isEmpty(tp.getWriter())){
				json+="\",";
			}else{
				json+=tp.getWriter()+"\",";
			}
			json+="\"pollType\":\"";
			if(Util.getInstance().isEmpty(tp.getPollType())){
				json+="\",";
			}else{
				json+=tp.getPollTypeName()+"\",";
			}
			json+="\"createTime\":\""+tp.getCreateTime()+"\",";
			for (Iterator<TpollScore> itt = tsList.iterator(); itt.hasNext();) {
				TpollScore ts=itt.next();
				if(tp.getPollId().equals(ts.getPoll().getPollId())){
					json+="\"comm"+ts.getRules().getId()+"\":\"";
					if(ts.getComment()!=null && !"".equals(ts.getComment())) {
						json+=ts.getComment().replace("\n", "&#10;").replace("\r", "");
					}
					json+="\",";
					json+="\""+ts.getRules().getId()+"\":\""+ts.getRules().getScore()+"\",";
					
				}
			}
			json=json.substring(0,json.length()-1);
			json+="},";
			
		}
		if(",".equals(json.substring(json.length()-1))){
			json=json.subSequence(0,json.length()-1)+"]}";
		}else{
			json+="]}";
		}
		System.out.println("json=="+json);
		return json;
	}
	
	/**
	 * 社情民意和积分列表
	 */	
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public DataGrid classCount(TpollScore pollScore) throws Exception {
		DataGrid j = new DataGrid();
		j.setRows(getPollSecoreList(find(pollScore)));
		return j;
	}
	private List<TpollScore> find(TpollScore pollScore) {
		String hql=" from TpollScore t1 where 1=1";
		hql = addWhere(pollScore, hql);
		hql+=" order by t1.poll.pollId";
		System.out.println("hql===="+hql);
		List<TpollScore> psList = countDao.find(hql);
		return psList;
	}
	private List<TpollScore> getPollSecoreList(List<TpollScore> pollScoreList) throws Exception {
			List<TpollScore> resList=new ArrayList<TpollScore>();
		for (TpollScore tp : pollScoreList) {
			Tpoll poll=new Tpoll();
			PropertyUtil.copyProperties(poll, tp.getPoll());
			if(!Constants.DIC_TYPE_POLLTYPE_SHZJ.equals(poll.getPollType())){
				poll.setCreateManName(this.findNameByCommCode(poll.getCreateMan()));
			}else{
				poll.setCreateManName(poll.getCreateMan());
			}
			tp.setPoll(poll);
			Trules tr=new Trules();
			PropertyUtil.copyProperties(tr, tp.getRules());
			tp.setRules(tr);
			
			tp.setComment("");
			tp.setMemo("");
			tp.getPoll().setContent("");
			tp.getPoll().setEndContent("");
			resList.add(tp);
		}
		return resList;
	}
	private Long total(TpollScore pollScore) {
		String hql=" select count(*) from TpollScore t1 where 1=1";
		hql = addWhere(pollScore, hql);
		return countDao.count(hql);
	}
	private String addWhere(TpollScore pollScore, String hql) {
		// 计分名称非空
		if (pollScore.getRules()!=null && pollScore.getRules().getId() != null && !"".equals(pollScore.getRules().getId())) {
			hql += " and t1.rules.id = '" + pollScore.getRules().getId().trim()+"'";
		}
		//届次：传入值时
		if (pollScore.getPoll()!=null&&pollScore.getPoll().getTsecondary()!=null&&pollScore.getPoll().getTsecondary().getSecondaryId() != null && !"".equals(pollScore.getPoll().getTsecondary().getSecondaryId())) {
			hql += " and t1.poll.tsecondary.secondaryId='" + pollScore.getPoll().getTsecondary().getSecondaryId().trim()+"'";
		}
		//编号
		if (pollScore.getPoll()!=null&&pollScore.getPoll().getPollCode()!=null && !"".equals(pollScore.getPoll().getPollCode())) {
			hql += " and t1.poll.pollCode='" + pollScore.getPoll().getPollCode().trim()+"'";
		}
		// 标题
		if ((pollScore.getPoll().getTitle() != null) && !"".equals(pollScore.getPoll().getTitle())) {
			hql += " and t1.poll.title like '%" + pollScore.getPoll().getTitle().trim()+"%'";
		}
		// 提交者
		if (pollScore.getPoll().getCreateMan() != null && !"".equals(pollScore.getPoll().getCreateMan().trim())) {
			hql += " and ( t1.poll.createMan like '%"+pollScore.getPoll().getCreateMan().trim()+"%' or exists (select code from Tcommitteeman tt where tt.name like  '%"+pollScore.getPoll().getCreateMan().trim()+"%' and tt.code=t1.poll.createMan))";
		}
		
		return hql;
	}
}
