package com.tlzn.service.sys.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sun.org.apache.commons.beanutils.BeanUtils;
import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.ThandleReply;
import com.tlzn.model.Tproposal;
import com.tlzn.model.sys.Tdolog;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.SessionInfo;
import com.tlzn.pageModel.sys.Hand;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.sys.HandServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.NumberFormatTools;

@Service("handService")
public class HandServiceImp extends BaseServiceImpl implements HandServiceI {
	private BaseDaoI<ThandleReply> handDao;
	private BaseDaoI<Tproposal> propDao;
	protected final Log log = LogFactory.getLog(getClass());
	
	public BaseDaoI<ThandleReply> getHandDao() {
		return handDao;
	}
	
	@Autowired
	public void setHandDao(BaseDaoI<ThandleReply> handDao) {
		this.handDao = handDao;
	}

	public BaseDaoI<Tproposal> getPropDao() {
		return propDao;
	}
	
	@Autowired
	public void setPropDao(BaseDaoI<Tproposal> propDao) {
		this.propDao = propDao;
	}

	public void save(Hand hand) throws Exception{
		String id=hand.getHandleReplyId();
		if(id!=null && !"".equals(id)){	
			ThandleReply t=handDao.get(ThandleReply.class, id);
			t.setSolveHow(hand.getSolveHow());
			t.setCarryoutFlg(hand.getCarryoutFlg());
			t.setCommitteemanOpinion(hand.getCommitteemanOpinion());
			t.setFactEnddate(hand.getFactEnddate());
			t.setOpinionExplain(hand.getOpinionExplain());
			t.setOperator(hand.getOperator());
			t.setOperateTime(new Date());
			t.setReply(hand.getReply());
			t.setAnswerMode(hand.getAnswerMode());
			t.setStatus(Constants.CODE_TYPE_HFZT_YBF);
			handDao.update(t);
			if(isComplete(t.getTproposal().getProposalId())){
				propDao.executeHql("update Tproposal t set t.stutas='" + Constants.CODE_TYPE_TAZT_YBL + "' where t.proposalId='" + t.getTproposal().getProposalId() + "'");
			}
			Tdolog dolog=new Tdolog();
			dolog.setLogType(Constants.LOG_TYPE_CODE_PROP);
			dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_REPLY);
			dolog.setKeyId(t.getTproposal().getProposalId());
			dolog.setInfo( "[提案办复]生成提案办复信息");
			this.saveLog(dolog);
		}
	}

	public void update(Hand hand) throws Exception{
		String id=hand.getHandleReplyId();
		if(id!=null && !"".equals(id)){	
			ThandleReply t=handDao.get(ThandleReply.class, id);
			t.setSatisfyFlg(hand.getSatisfyFlg());
			if(Constants.CODE_TYPE_WYYJ_BMY.equals(t.getSatisfyFlg())){
				t.setStatus(Constants.CODE_TYPE_HFZT_NEW);
				t.setSolveHow("");
				t.setCarryoutFlg(Constants.CODE_TYPE_YESNO_NO);
			}
			t.setSatisfyInfo(hand.getSatisfyInfo());
			handDao.update(t);
			Tdolog dolog=new Tdolog();
			dolog.setLogType(Constants.LOG_TYPE_CODE_PROP);
			dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_REPLY);
			dolog.setKeyId(t.getTproposal().getProposalId());
			dolog.setInfo( "[提案办复]提交提案办理信息");
			this.saveLog(dolog);
		}
	}

	private boolean isComplete(String id){
		List<ThandleReply> list=handDao.find("from ThandleReply t where t.tproposal.proposalId='" + id + "'");
		for(ThandleReply t : list){
			if(!Constants.CODE_TYPE_HFZT_YBF.equals(t.getStatus()) && (Constants.CODE_TYPE_YESNO_YES.equals(t.getMainFlg()) || t.getMainFlg()==null || "".equals(t.getMainFlg()))){
				return false;
			}
		}
		return true;
	}
	
	public void receiv(Hand hand) throws Exception{
		for(String id : hand.getIds().split(",")){
			ThandleReply t=handDao.get(ThandleReply.class, id);
			t.setStatus(Constants.CODE_TYPE_HFZT_YQS);	//"3"为已签收
			handDao.update(t);
			String hql="update Tproposal t set t.replyPass='" + Constants.CODE_TYPE_BFSC_WTG + "' where t.proposalId='" + t.getTproposal().getProposalId() + "'";
			propDao.executeHql(hql);
			Tdolog dolog=new Tdolog();
			dolog.setLogType(Constants.LOG_TYPE_CODE_PROP);
			dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_REPLY);
			dolog.setKeyId(t.getTproposal().getProposalId());
			dolog.setInfo( "[提案办复]提案办理签收");
			this.saveLog(dolog);
		}
	}

	public void back(Hand hand) throws Exception{
		String id=hand.getHandleReplyId();
		if(id!=null && !"".equals(id)){	
			ThandleReply t=handDao.get(ThandleReply.class, id);
			t.setOpinionExplain(hand.getOpinionExplain());
			t.setOperator(hand.getOperator());
			t.setOperateTime(new Date());
			t.setStatus(Constants.CODE_TYPE_HFZT_STZ);	//"2"为申退中
			handDao.update(t);
			String hql="update Tproposal t set t.stutas='" + Constants.CODE_TYPE_TAZT_STZ + "' where t.proposalId='" + t.getTproposal().getProposalId() + "'";
			propDao.executeHql(hql);	//将提案状态改为申退中
			Tdolog dolog=new Tdolog();
			dolog.setLogType(Constants.LOG_TYPE_CODE_PROP);
			dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_BACK);
			dolog.setKeyId(t.getTproposal().getProposalId());
			dolog.setInfo( "[提案办复]提案办理申退");
			this.saveLog(dolog);
		}
	}

	public void refuse(Hand hand) throws Exception{
		String id=hand.getProposalId();
		if(id!=null && !"".equals(id)){	
			String hql="update Tproposal t set t.stutas='" + Constants.CODE_TYPE_TAZT_WBL + "' where t.proposalId='" + id + "'";
			propDao.executeHql(hql);	//将提案状态"申退中"改为"新"
			hql="update ThandleReply t set t.status='" + Constants.CODE_TYPE_HFZT_NEW + "' where t.tproposal.proposalId='" + id + "' and status='" + Constants.CODE_TYPE_HFZT_STZ  + "'";
			handDao.executeHql(hql);	//将提案状态"申退中"改为"新"
			Tdolog dolog=new Tdolog();
			dolog.setLogType(Constants.LOG_TYPE_CODE_PROP);
			dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_BACK);
			dolog.setKeyId(hand.getProposalId());
			dolog.setInfo( "[提案办复]提案办理申退驳回");
			this.saveLog(dolog);
		}
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Hand findObj(Hand hand) throws Exception {
		String id=hand.getHandleReplyId();
		Hand p=new Hand();
		if(id!=null && !"".equals(id)){	
			ThandleReply t=handDao.get(ThandleReply.class, id);
			BeanUtils.copyProperties(p,t);
			p.setProposalId(t.getTproposal().getProposalId());
			p.setProposalCode(t.getTproposal().getProposalCode());
			p.setSecondayName(t.getTproposal().getTsecondary().getSecondayName());
			p.setTitle(t.getTproposal().getTitle());
			p.setCompanyId(t.getTcompany().getCompanyId());
			p.setCreateTime(t.getTproposal().getCreateTime());
			p.setFistProposaler(t.getTproposal().getFistProposaler());
			p.setProposalerNum(t.getTproposal().getProposalerNum());
			p.setDemandEnddate(t.getTproposal().getDemandEnddate());
			p.setCompanyName(t.getTcompany().getCompanyName());
			p.setCompanyType(t.getTcompany().getCompanyType());
			p.setCompanyTypeName(this.findDicName("COMPANYTYPE", t.getTcompany().getCompanyType()));
			p.setSolveHowName(this.findDicName("JJCD", t.getSolveHow()));
			p.setProposalTypeName(this.findDicName("TALX", t.getTproposal().getProposalType()));
			p.setHandleTypeName(this.findDicName("BLLX", t.getTproposal().getHandleType()));
			p.setCarryoutFlgName(this.findDicName("YESNO", t.getCarryoutFlg()));
			p.setCommOpName(this.findDicName("WYYJ", t.getCommitteemanOpinion()));
			p.setStatusName(this.findDicName("HFZT", t.getStatus()));
			p.setAnswerModeName(this.findDicName("GTFS", t.getAnswerMode()));
			p.setReplyPassName(this.findDicName("BFSC",t.getReplyPass()));
		}
		return p;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Hand> list(Hand hand) throws Exception {
		return this.datagrid(hand).getRows();
	}
	
	public String report(Hand hand, String name) throws Exception {
		String hql="from ThandleReply t where t.tcompany.companyId='" + name  + "' ";
		List<Object> values = new ArrayList<Object>();
		hql=this.addWhere(hand, hql, values);
		hql+="order by t.tproposal.proposalCode";
		return this.writeExcel(getHandsFromTHands(handDao.find(hql)));
	}

	public void reject(Hand hand) throws Exception {
		String hql="update ThandleReply t set t.rebutInfo='"+hand.getRebutInfo()+"', t.committeemanOpinion='',t.solveHow='',t.carryoutFlg='',t.status='" + Constants.CODE_TYPE_HFZT_NEW +"' where t.handleReplyId='" + hand.getHandleReplyId()  + "' ";
		handDao.executeHql(hql);	//将办理回复中"已办理"改为"未办理"
		hql="update Tproposal t set t.stutas='" + Constants.CODE_TYPE_TAZT_WBL +"' where t.proposalId='" + hand.getProposalId()  + "' ";
		propDao.executeHql(hql);	//将提案状态"已办理"改为"未办理"
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_PROP);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_BACK);
		dolog.setKeyId(hand.getProposalId());
		dolog.setInfo( "[提案办复]提案办理驳回重办（单位名称："+hand.getCompanyName()+"）");
		this.saveLog(dolog);
	}
	/**
	 * 
	 * 审查通过办复报告
	 * 
	 * @param hand
	 * @throws 	Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void checkPass(Hand hand)throws Exception{
		String hql="update ThandleReply t set t.replyPass='" + Constants.CODE_TYPE_BFSC_YTG +"' where t.handleReplyId='" + hand.getHandleReplyId()  + "' ";
		handDao.executeHql(hql);	
		List<Hand> list=this.datagrid(hand).getRows();
		boolean flg=true;
		for (Hand h : list) {
			if(Constants.CODE_TYPE_BFSC_WTG.equals(h.getReplyPass()) || "".equals(h.getReplyPass()) || h.getReplyPass()==null){
				flg=false;
				break;
			}
		}
		if(flg){
			hql="update Tproposal t set t.replyPass='" + Constants.CODE_TYPE_BFSC_YTG +"' where t.proposalId='" + hand.getProposalId()  + "' ";
			propDao.executeHql(hql);	
		}
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_PROP);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_CHECK);
		dolog.setKeyId(hand.getProposalId());
		dolog.setInfo( "[提案办复审查]提案办复审查");
		this.saveLog(dolog);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(Hand hand, String name) throws Exception {
		DataGrid j = new DataGrid();
		j.setRows(getHandsFromTHands(find(hand, name)));
		j.setTotal(total(hand, name));
		return j;
	}
	
	/*
	 * 从实体对象列表中复制出新建自造对象
	 */
	private List<Hand> getHandsFromTHands(List<ThandleReply> Thands) throws Exception {
		List<Hand> ads = new ArrayList<Hand>();
		if (Thands != null && Thands.size() > 0) {
			for (ThandleReply t : Thands) {
				Hand p = new Hand();
				BeanUtils.copyProperties(p,t);

				p.setReply("");	//因有样式，所以不检索
				p.setProposalId(t.getTproposal().getProposalId());
				p.setProposalCode(t.getTproposal().getProposalCode());
				p.setSecondayName(t.getTproposal().getTsecondary().getSecondayName());
				p.setTitle(t.getTproposal().getTitle());
				p.setCompanyId(t.getTcompany().getCompanyId());
				p.setCreateTime(t.getTproposal().getCreateTime());
				p.setFistProposaler(t.getTproposal().getFistProposaler());
				p.setProposalerNum(t.getTproposal().getProposalerNum());
				p.setDemandEnddate(t.getTproposal().getDemandEnddate());
				p.setCompanyName(t.getTcompany().getCompanyName());
				p.setCompanyType(t.getTcompany().getCompanyType());
				p.setCompanyTypeName(this.findDicName("COMPANYTYPE", t.getTcompany().getCompanyType()));
				p.setSolveHowName(this.findDicName("JJCD", t.getSolveHow()));
				p.setProposalTypeName(this.findDicName("TALX", t.getTproposal().getProposalType()));
				p.setHandleTypeName(this.findDicName("BLLX", t.getTproposal().getHandleType()));
				p.setCarryoutFlgName(this.findDicName("YESNO", t.getCarryoutFlg()));
				p.setCommOpName(this.findDicName("WYYJ", t.getCommitteemanOpinion()));
				p.setStatusName(this.findDicName("HFZT", t.getStatus()));

				p.setSatisfyFlgName(this.findDicName("WYYJ", t.getSatisfyFlg()));

				p.setAnswerModeName(this.findDicName("GTFS", t.getAnswerMode()));
				p.setReplyPassName(this.findDicName("BFSC",t.getReplyPass()));

				ads.add(p);
			}
		}
		return ads;
	}
	/**
	 * 
	 * 获取提案的承办单位
	 * 
	 * @param prop
	 * 
	 * @throws 	Exception
	 * 
	 * @return 		DataGrid返回值
	 */
	public DataGrid datagrid(Hand hand) throws Exception {
		DataGrid dataGrid= new DataGrid();
		String hql=" from ThandleReply t1 where t1.tproposal.proposalId='"+hand.getProposalId()+"'";
		hql+="order by t1.tproposal.proposalCode";
		if (hand.getSort() != null && hand.getOrder() != null) {
			hql += ","+ hand.getSort() + " " + hand.getOrder();
		}
		List<ThandleReply> list=handDao.find(hql);
		List<Hand> ads = new ArrayList<Hand>();
		if (list != null && list.size() > 0) {
			for (ThandleReply t : list) {
				Hand p = new Hand();
				BeanUtils.copyProperties(p,t);
				
				p.setReply(t.getReply()==null? "":t.getReply().replaceAll("\"", "'"));//将双引号替换成单引号      Util.filterHTMLTags(t.getReply()));
				p.setReply(t.getReply()==null? "":p.getReply().replaceAll("\n", "&#10;").replaceAll("\r", ""));//将换行符替换成     &#10;
				p.setProposalId(t.getTproposal().getProposalId());
				p.setProposalCode(t.getTproposal().getProposalCode());
				p.setSecondayName(t.getTproposal().getTsecondary().getSecondayName());
				p.setTitle(t.getTproposal().getTitle());
				p.setCompanyId(t.getTcompany().getCompanyId());
				p.setCreateTime(t.getTproposal().getCreateTime());
				p.setFistProposaler(t.getTproposal().getFistProposaler());
				p.setProposalerNum(t.getTproposal().getProposalerNum());
				p.setDemandEnddate(t.getTproposal().getDemandEnddate());
				p.setCompanyName(t.getTcompany().getCompanyName());
				p.setCompanyType(t.getTcompany().getCompanyType());
				p.setCompanyTypeName(this.findDicName("COMPANYTYPE", t.getTcompany().getCompanyType()));
				p.setSolveHowName(this.findDicName("JJCD", t.getSolveHow()));
				p.setProposalTypeName(this.findDicName("TALX", t.getTproposal().getProposalType()));
				p.setHandleTypeName(this.findDicName("BLLX", t.getTproposal().getHandleType()));
				p.setCarryoutFlgName(this.findDicName("YESNO", t.getCarryoutFlg()));
				p.setCommOpName(this.findDicName("WYYJ", t.getCommitteemanOpinion()));
				p.setStatusName(this.findDicName("HFZT", t.getStatus()));

				p.setSatisfyFlgName(this.findDicName("WYYJ", t.getSatisfyFlg()));

				p.setAnswerModeName(this.findDicName("GTFS", t.getAnswerMode()));
				p.setReplyPassName(this.findDicName("BFSC",t.getReplyPass()));

				ads.add(p);
			}
		}
		dataGrid.setRows(ads);
		return dataGrid;
	}
	/**
	 * 办理监控
	 */
	@SuppressWarnings("unchecked")
	public String monitordatagrid(HttpSession httpSession) throws Exception {
		System.out.println("====办理监控===");
		SessionInfo sessionInfo=((SessionInfo)httpSession.getAttribute("sessionInfo"));
		System.out.println(sessionInfo.getUserGroup());
		String json="{\"total\":0,\"rows\":[";	//datagrid数据
		String sql="select c.company_id,c.company_name,ta.tote_all,ty.tote_yes,tn.tote_no,sy.single_all,sn.single_no,bby.branch_all,bn.branch_no,hy.host_all,hn.host_no from tcompany c left join "
		+" (select t.company_id, count(*) tote_all from thandle_reply t where not exists(select proposal_id from tproposal p where (p.handle_type='"+Constants.CODE_TYPE_BLLX_HB+"' or p.submit_flg='"+Constants.CODE_TYPE_TJZT_WTJ+"') and p.proposal_id=t.proposal_id) group by t.company_id) ta"
		+" on ta.company_id=c.company_id left join"
		+" (select t.company_id, count(*) tote_yes from thandle_reply t where not exists(select proposal_id from tproposal p where (p.handle_type='"+Constants.CODE_TYPE_BLLX_HB+"' or p.submit_flg='"+Constants.CODE_TYPE_TJZT_WTJ+"') and p.proposal_id=t.proposal_id) and  t.status='"+Constants.CODE_TYPE_HFZT_YBF+"'group by t.company_id) ty"
		+" on ty.company_id=c.company_id  left join"
		+" (select t.company_id, count(*) tote_no from thandle_reply t where not exists(select proposal_id from tproposal p where (p.handle_type='"+Constants.CODE_TYPE_BLLX_HB+"' or p.submit_flg='"+Constants.CODE_TYPE_TJZT_WTJ+"') and p.proposal_id=t.proposal_id) and  t.status!='"+Constants.CODE_TYPE_HFZT_YBF+"'group by t.company_id) tn"
		+" on tn.company_id=c.company_id   left join"
		+" (select t.company_id, count(*) single_all from thandle_reply t where exists(select proposal_id from tproposal p where p.handle_type='"+Constants.CODE_TYPE_BLLX_DB+"' and p.submit_flg='"+Constants.CODE_TYPE_TJZT_YTJ+"' and p.proposal_id=t.proposal_id) group by t.company_id) sy"
		+" on sy.company_id=c.company_id  left join"
		+" (select t.company_id, count(*) single_no from thandle_reply t where  exists(select proposal_id from tproposal p where p.handle_type='"+Constants.CODE_TYPE_BLLX_DB+"' and p.submit_flg='"+Constants.CODE_TYPE_TJZT_YTJ+"' and p.proposal_id=t.proposal_id) and  t.status!='"+Constants.CODE_TYPE_HFZT_YBF+"'group by t.company_id) sn"
		+" on sn.company_id=c.company_id  left join"
		+" (select t.company_id, count(*) branch_all from thandle_reply t where exists(select proposal_id from tproposal p where p.handle_type='"+Constants.CODE_TYPE_BLLX_FB+"' and p.submit_flg='"+Constants.CODE_TYPE_TJZT_YTJ+"' and p.proposal_id=t.proposal_id) group by t.company_id) bby"
		+" on bby.company_id=c.company_id  left join"
		+" (select t.company_id, count(*) branch_no from thandle_reply t where  exists(select proposal_id from tproposal p where p.handle_type='"+Constants.CODE_TYPE_BLLX_FB+"' and p.submit_flg='"+Constants.CODE_TYPE_TJZT_YTJ+"' and p.proposal_id=t.proposal_id) and  t.status!='"+Constants.CODE_TYPE_HFZT_YBF+"'group by t.company_id) bn"
		+" on bn.company_id=c.company_id left join"
		+" (select t.company_id, count(*) host_all from thandle_reply t where exists(select proposal_id from tproposal p where p.handle_type='"+Constants.CODE_TYPE_BLLX_HB+"' and p.submit_flg='"+Constants.CODE_TYPE_TJZT_YTJ+"' and p.proposal_id=t.proposal_id) group by t.company_id) hy"
		+" on hy.company_id=c.company_id  left join"
		+" (select t.company_id, count(*) host_no from thandle_reply t where  exists(select proposal_id from tproposal p where p.handle_type='"+Constants.CODE_TYPE_BLLX_HB+"' and p.submit_flg='"+Constants.CODE_TYPE_TJZT_YTJ+"' and p.proposal_id=t.proposal_id) and  t.status!='"+Constants.CODE_TYPE_HFZT_YBF+"'group by t.company_id) hn"
		+" on hn.company_id=c.company_id ";
		if(Constants.DIC_TYPE_YHZB_LDSP.equals(sessionInfo.getUserGroup())){
			sql+="where c.company_type='"+sessionInfo.getUserCode()+"'";
		}
		sql+=" order by c.COMPANY_TYPE,c.company_id";
		 List<Object[]> list =(List<Object[]>) handDao.executeCountSql(sql);
		for(int i=0;i<list.size();i++){
			Object[] objs=list.get(i);
			json+="{\"companyId\":\""+objs[0]+"\",\"companyName\":\""+objs[1]+"\",";
			json+="\"rate\":";
			if(objs[2]==null || objs[3]==null){
				json+="0";
			}else {
				json+=(NumberFormatTools.getInstance().toFloat(objs[3])/NumberFormatTools.getInstance().toFloat(objs[2]))*100;
			}
			json+=",";
			json+="\"tote_all\":";
			if(objs[2]==null){
				json+="0";
			}else {
				json+=objs[2];
			}
			json+=",";
			json+="\"tote_no\":";
			if(objs[4]==null){
				json+="0";
			}else {
				json+=objs[4];
			}
			json+=",";
			json+="\"single_all\":";
			if(objs[5]==null){
				json+="0";
			}else {
				json+=objs[5];
			}
			json+=",";
			json+="\"single_no\":";
			if(objs[6]==null){
				json+="0";
			}else {
				json+=objs[6];
			}
			json+=",";
			json+="\"branch_all\":";
			if(objs[7]==null){
				json+="0";
			}else {
				json+=objs[7];
			}
			json+=",";
			json+="\"branch_no\":";
			if(objs[8]==null){
				json+="0";
			}else {
				json+=objs[8];
			}
			json+=",";
			json+="\"host_all\":";
			if(objs[9]==null){
				json+="0";
			}else {
				json+=objs[9];
			}
			json+=",";
			json+="\"host_no\":";
			if(objs[10]==null){
				json+="0";
			}else {
				json+=objs[10];
			}
			json+="},";
		}
		json=json.subSequence(0,json.length()-1)+"]}";
		System.out.println("monitorjson=="+json);
		return json;
	}
	/**
	 * 
	 * 获取提案的承办单位
	 * 
	 * @param hand
	 * 
	 * @throws 	Exception
	 * 
	 * @return 		list返回值
	 */
	public List<Hand> find(Hand hand) throws Exception {
		String hql=" from ThandleReply t1 where t1.tproposal.proposalId='"+hand.getProposalId()+"'";
		hql+="order by t1.tproposal.proposalCode";
		if (hand.getSort() != null && hand.getOrder() != null) {
			hql += ","+ hand.getSort() + " " + hand.getOrder();
		}
		List<ThandleReply> list=handDao.find(hql);
		return getHandsFromTHands(list);
	}
	/*
	 * 查找记录并筛选记录
	 */	
	private List<ThandleReply> find(Hand hand, String name) {
		String hql = "from ThandleReply t where t.tcompany.companyId='" + name  + "' and t.tproposal.fileMethod='" + Constants.CODE_TYPE_LAXS_LA + "' and t.tproposal.submitFlg='"+Constants.CODE_TYPE_TJZT_YTJ+"'";

		List<Object> values = new ArrayList<Object>();
		hql = addWhere(hand, hql, values);

		if (hand.getSort() != null && hand.getOrder() != null) {
			hql += " order by " + hand.getSort() + " " + hand.getOrder();
		}
		return handDao.find(hql, values, hand.getPage(), hand.getRows());
	}
	/*
	 * 统计记录总数
	 */	
	private Long total(Hand hand, String name) {
		String hql = "select count(*) from ThandleReply t where t.tcompany.companyId='" + name  + "' and t.tproposal.fileMethod='" + Constants.CODE_TYPE_LAXS_LA + "' and t.tproposal.submitFlg='"+Constants.CODE_TYPE_TJZT_YTJ+"'";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(hand, hql, values);
		return handDao.count(hql, values);
	}
	/*
	 * 组合hql语句
	 */	
	private String addWhere(Hand hand, String hql, List<Object> values) {
		if (hand.getProposalCode() != null && !"".equals(hand.getProposalCode())) {
			hql += " and t.tproposal.proposalCode = ? ";
			values.add(hand.getProposalCode());
		}
		if (hand.getTitle() != null && !"".equals(hand.getTitle())) {
			hql += " and t.tproposal.title like ? ";
			values.add("%" + hand.getTitle() + "%");
		}
		if (hand.getFistProposaler() != null && !"".equals(hand.getFistProposaler())) {
			hql += " and t.tproposal.fistProposaler like ? ";
			values.add("%" + hand.getFistProposaler() + "%");
		}
		if (hand.getHandleType() != null && !"".equals(hand.getHandleType())) {
			hql += " and t.tproposal.handleType = ? ";
			values.add(hand.getHandleType());
		}
		if (hand.getStatus() != null && !"".equals(hand.getStatus())) {
			hql += " and t.status = ? ";
			values.add(hand.getStatus());
		}
		return hql;
	}

	/*
	 * 判断对象属性是否为空，用空字符串替换null	
	*/
	private String changeNull(Object o){
		return o==null ? "" : o.toString().trim();
	}
	/*
	 * 写入数据Excel文件	
	*/
	private String writeExcel(List<Hand> list)throws Exception{
		
		Properties prop = new Properties();   
    	prop.load(this.getClass().getResourceAsStream("/config.properties")); //读取config.properties中的导出
    	String exportPath=prop.getProperty("exportPath");
        File path=new File(Constants.ROOTPATH + exportPath);
        if(!path.exists())path.mkdir();//如果路径不存在，则创建
        String filePath=exportPath + "/export.xls";
        
        WritableWorkbook book= Workbook.createWorkbook(new File(path ,"export.xls"));
		WritableSheet sheet  =  book.createSheet( "Sheet1 " ,  0 );
		
		sheet.getSettings().setVerticalFreeze(2);  // 设置行冻结前2行   // ss.setHorizontalFreeze(2);  // 设置列冻结 
		WritableCellFormat wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("微软雅黑"), 14 ,WritableFont.BOLD));           
		WritableCellFormat wcf2 = new WritableCellFormat(new WritableFont(WritableFont.createFont("微软雅黑"), 9 ,WritableFont.NO_BOLD));            
		        
		wcf.setAlignment(jxl.format.Alignment.CENTRE);  //平行居中 // wcf2.setBackground(Colour.LIGHT_ORANGE);    
		wcf.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中
		wcf2.setAlignment(jxl.format.Alignment.CENTRE);  //平行居中 
		wcf2.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中 
		
		sheet.mergeCells( 0 , 0 , 14 , 0 ); // 合并单元格  
		jxl.write.Label titleLabel = new Label( 0 , 0 , "办理回复明细表",wcf);
		sheet.addCell(titleLabel);           //          将定义好的单元格添加到工作表中 
		sheet.setRowView(0, 500); // 设置第一行的高度  20121111           
		
		int[] headerArrHight = {5,10,20,10,40,20,20,10,15,15,20,10,20,30}; //列宽           
		String[] title={"序号","提案编号","届次","提案类型","提案标题","立案日期","第一提案人","提案人数","要求交办日期","实际办结日期","办理单位","办理类型","解决程度","提案人意见"};
		for(int i=0;i<title.length;i++){
			sheet.addCell(new  Label( i , 1 , title[i], wcf2));
			sheet.setColumnView(i, headerArrHight[i]);            
		}
		
		int row=2;
		for(int i=0;i<list.size();i++){
			sheet.addCell(new  Label( 0 , row , String.valueOf(row-1), wcf2) );
			sheet.addCell(new  Label( 1 , row , changeNull(list.get(i).getProposalCode()), wcf2 ));
			sheet.addCell(new  Label( 2 , row , changeNull(list.get(i).getSecondayName())));
			sheet.addCell(new  Label( 3 , row , changeNull(list.get(i).getProposalTypeName())));
			sheet.addCell(new  Label( 4 , row , changeNull(list.get(i).getTitle())));
			sheet.addCell(new  Label( 5 , row , changeNull(list.get(i).getCreateTime())));
			sheet.addCell(new  Label( 6 , row , changeNull(list.get(i).getFistProposaler())));
			sheet.addCell(new  Label( 7 , row , changeNull(list.get(i).getProposalerNum()), wcf2 ));
			sheet.addCell(new  Label( 8 , row , changeNull(list.get(i).getDemandEnddate())));
			sheet.addCell(new  Label( 9 , row , changeNull(list.get(i).getFactEnddate())));
			sheet.addCell(new  Label( 10 , row , changeNull(list.get(i).getCompanyName())));
			sheet.addCell(new  Label( 11 , row , changeNull(list.get(i).getHandleTypeName()), wcf2 ));
			sheet.addCell(new  Label( 12 , row , changeNull(list.get(i).getSolveHowName())));
			sheet.addCell(new  Label( 13 , row , changeNull(list.get(i).getCommOpName())));
			row++;
		}
		book.write();
        book.close();
        return filePath;
	}
	
}
