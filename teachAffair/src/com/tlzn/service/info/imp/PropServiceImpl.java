package com.tlzn.service.info.imp;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpSession;

import jxl.SheetSettings;
import jxl.Workbook;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sun.org.apache.commons.beanutils.BeanUtils;
import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.Tcommitteeman;
import com.tlzn.model.Tcompany;
import com.tlzn.model.ThandleReply;
import com.tlzn.model.Tproposal;
import com.tlzn.model.Tproposaler;
import com.tlzn.model.activity.Tactivitypeo;
import com.tlzn.model.sys.Tdolog;
import com.tlzn.model.sys.Tsecondary;
import com.tlzn.pageModel.activity.Activitypeo;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.SessionInfo;
import com.tlzn.pageModel.info.Proposal;
import com.tlzn.pageModel.sys.Seco;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.info.PropServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Generator;
import com.tlzn.util.base.NumberFormatTools;
import com.tlzn.util.base.ResourceUtil;
import com.tlzn.util.base.Util;
/**
 * 提案业务类
 * @author zhangle
 */
@Service("propService")
public class PropServiceImpl extends BaseServiceImpl implements PropServiceI {

	private BaseDaoI<Tproposal> propDAO;
	
	private BaseDaoI<Tproposaler> sponsorDAO;
	
	private BaseDaoI<ThandleReply> handDao;
	
	private BaseDaoI<Tcompany> compDao;
	
	public BaseDaoI<Tproposal> getPropDAO() {
		return propDAO;
	}
	@Autowired
	public void setPropDAO(BaseDaoI<Tproposal> propDAO) {
		this.propDAO = propDAO;
	}
	
	public BaseDaoI<Tproposaler> getSponsorDAO() {
		return sponsorDAO;
	}
	@Autowired
	public void setSponsorDAO(BaseDaoI<Tproposaler> sponsorDAO) {
		this.sponsorDAO = sponsorDAO;
	}
	public BaseDaoI<ThandleReply> getHandDao() {
		return handDao;
	}
	
	@Autowired
	public void setHandDao(BaseDaoI<ThandleReply> handDao) {
		this.handDao = handDao;
	}
	
	public BaseDaoI<Tcompany> getCompDao() {
		return compDao;
	}
	@Autowired
	public void setCompDao(BaseDaoI<Tcompany> compDao) {
		this.compDao = compDao;
	}
	/**
	 * 
	 * 获取提案
	 * 
	 * @param prop
	 * 
	 * @throws 	Exception
	 * 
	 * @return 		DataGrid返回值
	 */
	public DataGrid datagrid(Proposal prop,String secondaryCode)throws Exception {
		String hql=" from Tproposal t1 where t1.tsecondary.secondaryId='"+secondaryCode+"' and t1.stutas!='" + Constants.CODE_TYPE_TAZT_YBC + "' and t1.adoptFlg!='"+Constants.CODE_TYPE_YESNO_NO+"'";
		hql=this.addWhere(prop, hql);
		hql+=" order by t1.proposalCode";
		DataGrid res=new DataGrid();
		res.setRows(this.getPropList(propDAO.find(hql,prop.getPage(), prop.getRows())));
		res.setTotal(propDAO.count("select Count(*) "+ hql));
		return res;
		
	}
	
	/**
	 * 
	 * 委员履职详情=>提案情况列表查询
	 * 
	 * @param activitypeo,userCode,secondaryCode
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagridFulfil(Proposal prop,String userCode, String secondaryCode) 
			throws Exception {
		String hql=" from Tproposaler t1 where t1.tproposal.tsecondary.secondaryId='"+secondaryCode
			+"' and t1.tcommitteeman.code='"+userCode
			+"' and t1.hostFlg='"+Constants.CODE_TYPE_YESNO_YES //主提
			+"' and t1.tproposal.adoptFlg='"+Constants.CODE_TYPE_YESNO_YES+"'"; //是否采纳（1是,0否）
		hql+=" order by t1.tproposal.createTime desc";
		List<Tproposaler> proposalerList = sponsorDAO.find(hql, prop.getPage(), prop.getRows());
		List<Tproposal> proposalList = new ArrayList<Tproposal>();
		for (Tproposaler tproposaler : proposalerList) {
			proposalList.add(tproposaler.getTproposal());
		}
		DataGrid j = new DataGrid();
		j.setRows(getPropList(proposalList));
		hql = "select count(*) "+hql;
		j.setTotal(sponsorDAO.count(hql));
		return j;
	}
	
	//我要附议
	public DataGrid Pdatagrid(Proposal prop,String secondaryCode,String userCode)throws Exception {
		String hql=" from Tproposal t1 where t1.tsecondary.secondaryId='"+secondaryCode+"' and t1.stutas!='" + Constants.CODE_TYPE_TAZT_YBC + "'and t1.adoptFlg!='"+Constants.CODE_TYPE_YESNO_NO+"' and t1.fileMethod is null and not exists(select t2.tproposal.proposalId from Tproposaler t2 where t2.tcommitteeman.code='"+userCode+"' and t2.tproposal.proposalId=t1.proposalId)";
		hql=this.addWhere(prop, hql);
		hql+=" order by t1.proposalCode";
		DataGrid res=new DataGrid();
		res.setRows(this.getPropList(propDAO.find(hql,prop.getPage(), prop.getRows())));
		res.setTotal(propDAO.count("select Count(*) "+hql));
		return res;
		
	}
	
	//办复情况
	public DataGrid sdatagrid(Proposal prop,String secondaryCode,HttpSession httpSession)throws Exception {
		SessionInfo sessionInfo=(SessionInfo)httpSession.getAttribute("sessionInfo");
		String hql=" from Tproposal t1 where t1.tsecondary.secondaryId='"+secondaryCode+"' and t1.submitFlg='" + Constants.CODE_TYPE_TJZT_YTJ + "'and t1.analySisUnit='"+sessionInfo.getUserCode()+"'";
		hql=this.addWhere(prop, hql);
		hql+=" order by t1.proposalCode";
		DataGrid res=new DataGrid();
		res.setRows(this.getPropList(propDAO.find(hql,prop.getPage(), prop.getRows())));
		res.setTotal(propDAO.count("select Count(*) "+hql));
		return res;
		
	}
	//办复情况查看
	public DataGrid sdatagrid(Proposal prop,String secondaryCode)throws Exception {
		String hql=" from Tproposal t1 where t1.tsecondary.secondaryId='"+secondaryCode+"' and t1.submitFlg='" + Constants.CODE_TYPE_TJZT_YTJ + "'";
		hql=this.addWhere(prop, hql);
		hql+=" order by t1.proposalCode";
		System.out.println(hql);
		DataGrid res=new DataGrid();
		res.setRows(this.getPropList(propDAO.find(hql,prop.getPage(), prop.getRows())));
		res.setTotal(propDAO.count("select Count(*) "+hql));
		return res;
		
	}
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Proposal findObj(String id) throws Exception {
		if(id!=null && !"".equals(id)){	
			Proposal p=new Proposal();
			Tproposal t=propDAO.get(Tproposal.class, id);
			BeanUtils.copyProperties(p, t);
			return findProp(p);
		}
		return null;
	}
	
	/*public DataGrid datagrid(Proposal prop,String secondaryCode)throws Exception {
		String hql=" from Tproposal t1 where t1.tsecondary.secondaryId='"+secondaryCode+"'";
		if(prop.getStutas()!=null && !"".equals(prop.getStutas()) && !"请选择...".equals(prop.getStutas())){
			hql += " and t1.stutas = '"+prop.getStutas()+"'";
		}
		if (prop.getProposalCode() != null && !"".equals(prop.getProposalCode())) {
			hql += " and t1.proposalCode = '"+prop.getProposalCode()+"'";
		}
		if (prop.getTitle() != null && !"".equals(prop.getTitle())) {
			hql += " and t1.title like '%"+prop.getTitle()+"%' ";
		}
		if(prop.getFileMethod()!=null && !"".equals(prop.getFileMethod()) && !"请选择...".equals(prop.getFileMethod())){
			hql += " and t1.fileMethod = '"+prop.getFileMethod()+"'";
		}
		if(prop.getPropertiesType()!=null && !"".equals(prop.getPropertiesType()) && !"请选择...".equals(prop.getPropertiesType())){
			hql += " and t1.propertiesType = '"+prop.getPropertiesType()+"'";
		}
		if(prop.getHandleType()!=null && !"".equals(prop.getHandleType()) && !"请选择...".equals(prop.getHandleType())){
			hql += " and t1.handleType = '"+prop.getHandleType()+"'";
		}
		return propDAO.getPageGrid(hql, (BaseObject)prop);
	}*/
	/**
	 * 
	 * 获取委员提案
	 * 
	 * @param prop
	 * 
	 * @throws 	Exception
	 * 
	 * @return 		DataGrid返回值
	 */
	public DataGrid datagrid(Proposal prop,String userCode,String secondaryCode)throws Exception {
		String hql=" from Tproposal t1 where exists(select t2.tproposal.proposalId from Tproposaler t2 where t2.tcommitteeman.code='"+userCode+"' and t2.hostFlg='"+Constants.CODE_TYPE_YESNO_YES+"' and t2.tproposal.proposalId=t1.proposalId) and t1.tsecondary.secondaryId='"+secondaryCode+"' and t1.adoptFlg!='"+Constants.CODE_TYPE_YESNO_NO+"'";
		hql=this.addWhere(prop, hql);
		hql+=" order by t1.proposalCode";
		DataGrid res=new DataGrid();
		res.setRows(this.getPropList(propDAO.find(hql,prop.getPage(), prop.getRows())));
		res.setTotal(propDAO.count("select Count(*) "+hql));
		return res;
	}
	/**
	 * 
	 * 获取审查提案
	 * 
	 * @param prop
	 * 
	 * @throws 	Exception
	 * 
	 * @return 		DataGrid返回值
	 */
	
	public DataGrid reviewdatagrid(Proposal prop,HttpSession httpSession)throws Exception {
		String secondaryCode=((Seco)httpSession.getAttribute("sessionSeco")).getSecondaryId();
		String hql=" from Tproposal t1 where t1.tsecondary.secondaryId='"+secondaryCode+"' and t1.revokeFlg is null and (t1.stutas ='"+Constants.CODE_TYPE_TAZT_YSC+"' or t1.stutas ='"+Constants.CODE_TYPE_TAZT_WSC+"' )and t1.adoptFlg!='"+Constants.CODE_TYPE_YESNO_NO+"'"; //and t1.submitFlg!='"+Constants.CODE_TYPE_TJZT_YTJ+"'";
		hql=this.addWhere(prop, hql);
		hql+=" order by t1.proposalCode";
		DataGrid res=new DataGrid();
		res.setRows(this.getPropList(propDAO.find(hql,prop.getPage(), prop.getRows())));
		res.setTotal(propDAO.count("select Count(*) "+hql));
		return res;
		
	}
	/**
	 * 
	 * 获取立案提案
	 * 
	 * @param prop
	 * 
	 * @throws 	Exception
	 * 
	 * @return 		DataGrid返回值
	 */
	
	public DataGrid assigndatagrid(Proposal prop,HttpSession httpSession)throws Exception {
		if(httpSession.getAttribute("sessionSeco")==null) return null;
		SessionInfo sessionInfo=(SessionInfo)httpSession.getAttribute("sessionInfo");
		String secondaryCode=((Seco)httpSession.getAttribute("sessionSeco")).getSecondaryId();
		String hql=" from Tproposal t1 where t1.tsecondary.secondaryId='"+secondaryCode+"' and t1.analySisUnit='"+sessionInfo.getUserCode()+"'";
		hql=this.addWhere(prop, hql);
		hql+=" order by t1.proposalCode";
		DataGrid res=new DataGrid();
		res.setRows(this.getPropList(propDAO.find(hql,prop.getPage(), prop.getRows())));
		res.setTotal(propDAO.count("select Count(*) "+hql));
		return res;
		
	}
	/**
	 * 
	 * 提案查询
	 * 
	 * @param prop
	 * 
	 * @throws 	Exception
	 * 
	 * @return 		DataGrid返回值
	 */
	
	public DataGrid querydatagrid(Proposal prop)throws Exception {
		String ids="";
		if(prop.getIds()!=null&&!"".equals(prop.getIds())){
			String[] secondaryIds=prop.getIds().split(",");
			for(int i=0;i<secondaryIds.length;i++){
				ids+="'"+secondaryIds[i]+"',";
			}
		}
		
		String hql=" from Tproposal t1 where t1.stutas='"+Constants.CODE_TYPE_TAZT_YBL+"' and t1.replyPass='"+Constants.CODE_TYPE_BFSC_YTG+"'";
		if(!"".equals(ids)){
			ids=ids.substring(0,ids.length()-1);
			hql+=" and t1.tsecondary.secondaryId in ("+ids+")";
		}
		hql=this.addWhere(prop, hql);
		hql+=" order by t1.tsecondary.secondaryId desc,t1.proposalCode";
		System.out.println(hql);
		DataGrid res=new DataGrid();
		res.setRows(this.getPropList(propDAO.find(hql,prop.getPage(), prop.getRows())));
		res.setTotal(propDAO.count("select Count(*) "+hql));
		return res;
		
	}
	/**
	 * 
	 * 提案查询
	 * 
	 * @param prop
	 * 
	 * @throws 	Exception
	 * 
	 * @return 		DataGrid返回值
	 */
	
	public DataGrid queryAllDatagrid(Proposal prop)throws Exception {
		String ids="";
		if(prop.getIds()!=null&&!"".equals(prop.getIds())){
			String[] secondaryIds=prop.getIds().split(",");
			for(int i=0;i<secondaryIds.length;i++){
				ids+="'"+secondaryIds[i]+"',";
			}
		}
		
		String hql=" from Tproposal t1 where t1.stutas!='"+Constants.CODE_TYPE_TAZT_YBC+"' and t1.adoptFlg!='"+Constants.CODE_TYPE_YESNO_NO+"'";
		if(!"".equals(ids)){
			ids=ids.substring(0,ids.length()-1);
			hql+=" and t1.tsecondary.secondaryId in ("+ids+")";
		}
		hql=this.addWhere(prop, hql);
		hql+=" order by t1.tsecondary.secondaryId desc,t1.proposalCode";
		System.out.println(hql);
		DataGrid res=new DataGrid();
		res.setRows(this.getPropList(propDAO.find(hql,prop.getPage(), prop.getRows())));
		res.setTotal(propDAO.count("select Count(*) "+hql));
		return res;
		
	}
	/**
	 * 
	 * 获取附议的提案
	 * 
	 * @param prop
	 * 
	 * @throws 	Exception
	 * 
	 * @return 		DataGrid返回值
	 */
	public DataGrid mdatagrid(Proposal prop,String userCode,String secondaryCode)throws Exception {
		String hql=" from Tproposal t1 where exists(select t2.tproposal.proposalId from Tproposaler t2 where t2.tcommitteeman.code='"+userCode+"' and t2.hostFlg='"+Constants.CODE_TYPE_YESNO_NO+"' and t2.tproposal.proposalId=t1.proposalId) and t1.tsecondary.secondaryId='"+secondaryCode+"'";
		if (prop.getProposalCode() != null && !"".equals(prop.getProposalCode())) {
			hql += " and t1.proposalCode = '"+prop.getProposalCode()+"'";
		}
		if (prop.getTitle() != null && !"".equals(prop.getTitle())) {
			hql += " and t1.title like '%"+prop.getTitle()+"%' ";
		}
		if (prop.getFistProposaler() != null && !"".equals(prop.getFistProposaler())) {
			hql += " and t1.fistProposaler like '%"+prop.getFistProposaler()+"%' ";
		}
		hql+=" order by t1.proposalCode";
		DataGrid res=new DataGrid();
		res.setRows(this.getPropList(propDAO.find(hql,prop.getPage(), prop.getRows())));
		res.setTotal(propDAO.count("select Count(*) "+hql));
		return res;
	}
	
	public void undo(Proposal prop,String userCode)throws Exception {
		String hql=" from Tproposaler t where t.tcommitteeman.code='"+userCode+"' and t.hostFlg='"+Constants.CODE_TYPE_YESNO_NO+"' and t.tproposal.proposalId='"+prop.getProposalId()+"'";
		List<Tproposaler> list = sponsorDAO.find(hql);
		if(list!=null && list.size()>0){
			sponsorDAO.delete(list.get(0));
			propDAO.executeHql("update Tproposal t set t.proposalerNum=t.proposalerNum-1 where t.proposalId='"+prop.getProposalId()+"'");
		}
	}
	
	private List<Proposal> getPropList(List<Tproposal> datalist)throws Exception {
		List<Proposal> rowlist=new ArrayList<Proposal>();
		if(datalist!=null){
			for(int i=0;i<datalist.size();i++){
				Proposal p=new Proposal();
				BeanUtils.copyProperties(p,datalist.get(i));
				p.setSecondaryId(datalist.get(i).getTsecondary().getSecondaryId());
				p.setSecondayName(datalist.get(i).getTsecondary().getSecondayName());
				p.setWebFlgName(this.findDicName("FYYGK", p.getWebFlg()));
				p.setSecondFlgName(this.findDicName("FYYGK", p.getSecondFlg()));
				p.setStutasName(this.findDicName("TAZT", p.getStutas()));
				p.setFileMethodName(this.findDicName("LAXS", p.getFileMethod()));
				p.setPropTypeName(this.findDicName("TAFL", p.getPropertiesType()));
				p.setHandleTypeName(this.findDicName("BLLX", p.getHandleType()));
				p.setStressFlgName(this.findDicName("YESNO", p.getStressFlg()));
				p.setExcellentFlgName(this.findDicName("YESNO", p.getExcellentFlg()));
				p.setHandleHowName(this.findDicName("JJCD", p.getHandleHow()));
				p.setProposalTypeName(this.findDicName("TALX", p.getProposalType()));
				p.setSubmitFlgName(this.findDicName("TJZT", p.getSubmitFlg()));
				p.setRevokeFlgName(this.findDicName("YESNO", p.getRevokeFlg()));
				p.setAdoptFlgName(this.findDicName("YESNO", p.getAdoptFlg()));
				p.setSecondFlgName(this.findDicName("FYYGK", p.getSecondFlg()));
				p.setMergeFlagName(this.findDicName("YESNO", p.getMergeFlag()));
				p.setSolveHowName(this.findDicName("JJCD", p.getSolveHow()));
				p.setCarryoutFlgName(this.findDicName("YESNO", p.getCarryoutFlg()));
				p.setAnalySisUnitName(this.findDicName("FXDW", p.getAnalySisUnit()));
				p.setReplyPassName(this.findDicName("BFSC", p.getReplyPass()));
				p.setCommitteemanOpinionName(this.findDicName("WYYJ", p.getCommitteemanOpinion()));
				p.setContent("");
				String[] compids=null;
				String compNames="";
				if(p.getAdviceUnit()!=null&& !"".equals(p.getAdviceUnit())){
					compids=p.getAdviceUnit().split(",");
					for(int j=0;j<compids.length;j++){
						Tcompany comp= compDao.get(Tcompany.class,compids[j]);
						compNames+=comp.getShortName()+",";
					}
				}
				if(!"".equals(compNames)){
					compNames=compNames.substring(0,(compNames.length()-1));
				}
				p.setAdviceUnitName(compNames);
				List<ThandleReply> list=handDao.find("from ThandleReply t where t.tproposal.proposalId='" + p.getProposalId() + "'");
				String comps="";
				for(int s=0;s<list.size();s++){
					if(list.get(s).getTcompany().getCompanyName()!=null){
						comps+=list.get(s).getTcompany().getCompanyName()+"，";
					}
				}
				if(comps.length()>0){
					comps=comps.substring(0,comps.length()-1);
				}
				p.setComps(comps);
				rowlist.add(p);
			}
		}
		return rowlist;
	}
	/*public DataGrid datagrid(Proposal prop,String userCode,String secondaryCode)throws Exception {
		String hql=" from Tproposal t1 where exists(select t2.tproposal.proposalId from Tproposaler t2 where t2.tcommitteeman.code='"+userCode+"' and t2.hostFlg='1' and t2.tproposal.proposalId=t1.proposalId) and t1.tsecondary.secondaryId='"+secondaryCode+"'";
		if (prop.getProposalCode() != null && !"".equals(prop.getProposalCode())) {
			hql += " and t1.proposalCode = '"+prop.getProposalCode()+"'";
		}
		if (prop.getTitle() != null && !"".equals(prop.getTitle())) {
			hql += " and t1.title like '%"+prop.getTitle()+"%' ";
		}
		
		return propDAO.getPageGrid(hql, (BaseObject)prop);
	}
	private String addWhere(Proposal prop, String hql, List<Object> values) {
		if(prop.getStutas()!=null && !"".equals(prop.getStutas()) && !"请选择...".equals(prop.getStutas())){
			hql += " and t1.stutas = ?";
			values.add(prop.getStutas());
		}
		if (prop.getProposalCode() != null && !"".equals(prop.getProposalCode())) {
			hql += " and t1.proposalCode = ?";
			values.add(prop.getProposalCode());
		}
		if (prop.getTitle() != null && !"".equals(prop.getTitle())) {
			hql += " and t1.title like ? ";
			values.add("%"+prop.getTitle()+"%");
		}
		if(prop.getFileMethod()!=null && !"".equals(prop.getFileMethod()) && !"请选择...".equals(prop.getFileMethod())){
			hql += " and t1.fileMethod = ?";
			values.add(prop.getFileMethod());
		}
		if(prop.getPropertiesType()!=null && !"".equals(prop.getPropertiesType()) && !"请选择...".equals(prop.getPropertiesType())){
			hql += " and t1.propertiesType = ?";
			values.add(prop.getPropertiesType());
		}
		if(prop.getHandleType()!=null && !"".equals(prop.getHandleType()) && !"请选择...".equals(prop.getHandleType())){
			hql += " and t1.handleType = ?";
			values.add(prop.getHandleType());
		}
		return hql;
	}

	private List<Proposal> getProposalFromTproposal(List<Tproposal> Tproposals) throws Exception {
		List<Proposal> ads = new ArrayList<Proposal>();
		if (Tproposals != null && Tproposals.size() > 0) {
			for (Tproposal t : Tproposals) {
				Proposal p = new Proposal();
				BeanUtils.copyProperties(p,t);
				p.setSecondaryId(t.getTsecondary().getSecondaryId());
				p.setSecondayName(t.getTsecondary().getSecondayName());
				ads.add(p);
			}
		}
		return ads;
	}*/
	/**
	 * 根据ID获取提案信息
	 */
	public Proposal findProp(Proposal prop) throws Exception{
		if(prop.getProposalId()==null||"".equals(prop.getProposalId()))return null;
		Tproposal tp= propDAO.get(Tproposal.class,prop.getProposalId());
		Proposal p=new Proposal();
		BeanUtils.copyProperties(p,tp);
		p.setSecondaryId(tp.getTsecondary().getSecondaryId());
		p.setSecondayName(tp.getTsecondary().getSecondayName());
		p.setWebFlgName(this.findDicName("FYYGK", tp.getWebFlg()));
		p.setSecondFlgName(this.findDicName("FYYGK", tp.getSecondFlg()));
		p.setStutasName(this.findDicName("TAZT", tp.getStutas()));
		p.setFileMethodName(this.findDicName("LAXS", tp.getFileMethod()));
		p.setPropTypeName(this.findDicName("TAFL", tp.getPropertiesType()));
		p.setHandleTypeName(this.findDicName("BLLX", tp.getHandleType()));
		p.setStressFlgName(this.findDicName("YESNO", tp.getStressFlg()));
		p.setExcellentFlgName(this.findDicName("YESNO", tp.getExcellentFlg()));
		p.setHandleHowName(this.findDicName("JJCD", tp.getHandleHow()));
		p.setProposalTypeName(this.findDicName("TALX", tp.getProposalType()));
		p.setSubmitFlgName(this.findDicName("TJZT", tp.getSubmitFlg()));
		p.setRevokeFlgName(this.findDicName("YESNO", tp.getRevokeFlg()));
		p.setAdoptFlgName(this.findDicName("YESNO", tp.getAdoptFlg()));
		p.setMergeFlagName(this.findDicName("YESNO", tp.getMergeFlag()));
		p.setAnalySisUnitName(this.findDicName("FXDW", tp.getAnalySisUnit()));
		p.setReplyPassName(this.findDicName("BFSC", p.getReplyPass()));
		String[] compids=null;
		String compNames="";
		if(p.getAdviceUnit()!=null&& !"".equals(p.getAdviceUnit())){
			compids=p.getAdviceUnit().split(",");
			for(int j=0;j<compids.length;j++){
				Tcompany comp= compDao.get(Tcompany.class,compids[j]);
				compNames+=comp.getShortName()+",";
			}
		}
		if(!"".equals(compNames)){
			compNames=compNames.substring(0,(compNames.length()-1));
		}
		p.setAdviceUnitName(compNames);
		return p;
	}
	/**
	 * 
	 * 提案统计
	 * 
	 * @param httpSession
	 * 
	 * @throws 	Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Integer> propCount(HttpSession httpSession)throws Exception{
		Map<String,Integer> countMap=new HashMap<String, Integer>();
		SessionInfo sessionInfo=((SessionInfo)httpSession.getAttribute("sessionInfo"));
		String secondaryCode=((Seco)httpSession.getAttribute("sessionSeco")).getSecondaryId();
		String sql="";
		if(Constants.DIC_TYPE_YHZB_WY.equals(sessionInfo.getUserGroup())){
			sql="select 'wsc',count(*) from tproposal t where t.secondary_id='"+secondaryCode+"' and t.stutas='"+Constants.CODE_TYPE_TAZT_WSC+"' and t.ADOPT_FLG ='"+Constants.CODE_TYPE_YESNO_YES+"' and exists(select t2.proposal_id from Tproposaler t2 where t2.committeeman_code='"+sessionInfo.getUserCode()+"' and t2.host_flg='"+Constants.CODE_TYPE_YESNO_YES+"' and t2.proposal_id=t.proposal_id)"
			+" union"
			+" select 'ysc',count(*) from tproposal t where t.secondary_id='"+secondaryCode+"' and t.stutas='"+Constants.CODE_TYPE_TAZT_YSC+"'and t.file_method is null and t.ADOPT_FLG ='"+Constants.CODE_TYPE_YESNO_YES+"' and exists(select t2.proposal_id from Tproposaler t2 where t2.committeeman_code='"+sessionInfo.getUserCode()+"' and t2.host_flg='"+Constants.CODE_TYPE_YESNO_YES+"' and t2.proposal_id=t.proposal_id)"
			+" union"
			+" select  'wla',count(*) from tproposal t where t.secondary_id='"+secondaryCode+"' and t.file_method ='"+Constants.CODE_TYPE_LAXS_BLA+"' and t.ADOPT_FLG='"+Constants.CODE_TYPE_YESNO_YES+"' and exists(select t2.proposal_id from Tproposaler t2 where t2.committeeman_code='"+sessionInfo.getUserCode()+"' and t2.host_flg='"+Constants.CODE_TYPE_YESNO_YES+"' and t2.proposal_id=t.proposal_id)"
			+" union"
			+" select  'yla',count(*) from tproposal t where t.secondary_id='"+secondaryCode+"' and t.file_method ='"+Constants.CODE_TYPE_LAXS_LA+"'and t.filing_flg='"+Constants.CODE_TYPE_YESNO_NO+"' and t.ADOPT_FLG='"+Constants.CODE_TYPE_YESNO_YES+"' and exists(select t2.proposal_id from Tproposaler t2 where t2.committeeman_code='"+sessionInfo.getUserCode()+"' and t2.host_flg='"+Constants.CODE_TYPE_YESNO_YES+"' and t2.proposal_id=t.proposal_id)"
			+" union"
			+" select  'yjb',count(*) from tproposal t where t.secondary_id='"+secondaryCode+"' and t.filing_flg='"+Constants.CODE_TYPE_YESNO_YES+"' and  t.reply_pass is null and t.submit_flg ='"+Constants.CODE_TYPE_TJZT_YTJ+"' and t.ADOPT_FLG='"+Constants.CODE_TYPE_YESNO_YES+"' and exists(select t2.proposal_id from Tproposaler t2 where t2.committeeman_code='"+sessionInfo.getUserCode()+"' and t2.host_flg='"+Constants.CODE_TYPE_YESNO_YES+"' and t2.proposal_id=t.proposal_id)"
			+" union"
			+" select  'wjb',count(*) from tproposal t where t.secondary_id='"+secondaryCode+"' and  t.filing_flg='"+Constants.CODE_TYPE_YESNO_YES+"' and t.submit_flg ='"+Constants.CODE_TYPE_TJZT_WTJ+"' and t.ADOPT_FLG='"+Constants.CODE_TYPE_YESNO_YES+"' and exists(select t2.proposal_id from Tproposaler t2 where t2.committeeman_code='"+sessionInfo.getUserCode()+"' and t2.host_flg='"+Constants.CODE_TYPE_YESNO_YES+"' and t2.proposal_id=t.proposal_id)"
			+" union"
			+" select  'ydf',count(*) from tproposal t where t.secondary_id='"+secondaryCode+"' and t.stutas='"+Constants.CODE_TYPE_TAZT_YBL+"'and t.reply_pass ='"+Constants.CODE_TYPE_YESNO_YES+"' and exists(select t2.proposal_id from Tproposaler t2 where t2.committeeman_code='"+sessionInfo.getUserCode()+"' and t2.host_flg='"+Constants.CODE_TYPE_YESNO_YES+"' and t2.proposal_id=t.proposal_id)"
			+" union"
			+" select  'wdf',count(*) from tproposal t where t.secondary_id='"+secondaryCode+"' and t.submit_flg ='"+Constants.CODE_TYPE_TJZT_YTJ+"' and t.reply_pass ='"+Constants.CODE_TYPE_YESNO_NO+"' and exists(select t2.proposal_id from Tproposaler t2 where t2.committeeman_code='"+sessionInfo.getUserCode()+"' and t2.host_flg='"+Constants.CODE_TYPE_YESNO_YES+"' and t2.proposal_id=t.proposal_id)";
				//+" union"
				//+" select 'revoke',count(*)  from tproposal t1 where t1.revoke_flg='"+Constants.CODE_TYPE_YESNO_YES+"' and exists(select t2.proposal_id from Tproposaler t2 where t2.committeeman_code='"+sessionInfo.getUserCode()+"' and t2.host_flg='"+Constants.CODE_TYPE_YESNO_YES+"' and t2.proposal_id=t1.proposal_id)";
			System.out.println(sql);
			List<Object[]> list =(List<Object[]>) propDAO.executeCountSql(sql);
				int wscNum=0;
				int yscNum=0;
			 	int wlaNum=0;
			 	int ylaNum=0;
			 	int wjbNum=0;
			 	int yjbNum=0;
			 	int wdfNum=0;
			 	int ydfNum=0;
			 	//int revokeNum=0;
				for(int i=0;i<list.size();i++){
					Object[] objs=list.get(i);
					if("wsc".equals(objs[0])){
						wscNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
					if("ysc".equals(objs[0])){
						yscNum= NumberFormatTools.getInstance().toInteger(objs[1]);
					}
					if("wla".equals(objs[0])){
						wlaNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
					if("yla".equals(objs[0])){
						ylaNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
				    if("wjb".equals(objs[0])){
				    	wjbNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
					if("yjb".equals(objs[0])){
						yjbNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
					if("wdf".equals(objs[0])){
						wdfNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
					if("ydf".equals(objs[0])){
						ydfNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
				}
				countMap.put("wscNum", wscNum);
				countMap.put("yscNum", yscNum);
				countMap.put("wlaNum", wlaNum);
				countMap.put("ylaNum", ylaNum);
				countMap.put("wjbNum", wjbNum);
				countMap.put("yjbNum", yjbNum);
				countMap.put("wdfNum", wdfNum);
				countMap.put("ydfNum", ydfNum);
		}else if (Constants.DIC_TYPE_YHZB_CBDW.equals(sessionInfo.getUserGroup())) {
			 sql="select t.status,count(*) from thandle_reply t where t.company_id='"+sessionInfo.getUserCode()+"' and exists (select proposal_id from tproposal p where p.secondary_id='"+secondaryCode+"' and p.ADOPT_FLG!='"+Constants.CODE_TYPE_YESNO_NO+"' and p.submit_flg='"+Constants.CODE_TYPE_TJZT_YTJ+"' and p.proposal_id=t.proposal_id) group by t.status";
			 List<Object[]> list =(List<Object[]>) handDao.executeCountSql(sql);
			 	int newNum=0;
			 	int stzNum=0;
			 	int yqsNum=0;
			 	int ybfNum=0;
				for(int i=0;i<list.size();i++){
					Object[] objs=list.get(i);
					if(Constants.CODE_TYPE_HFZT_NEW.equals(objs[0])){
						newNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
					if(Constants.CODE_TYPE_HFZT_STZ.equals(objs[0])){
						stzNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
					if(Constants.CODE_TYPE_HFZT_YQS.equals(objs[0])){
						yqsNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
					if(Constants.CODE_TYPE_HFZT_YBF.equals(objs[0])){
						ybfNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
					
				}
				countMap.put("newNum", newNum);
				countMap.put("stzNum", stzNum);
				countMap.put("yqsNum", yqsNum);
				countMap.put("ybfNum", ybfNum);
		}else if (Constants.DIC_TYPE_YHZB_LDSP.equals(sessionInfo.getUserGroup())) {
			sql="select 'wjb',count(*) from tproposal t where t.secondary_id='"+secondaryCode+"' and t.analysis_unit='"+sessionInfo.getUserCode()+"' and  t.filing_flg='"+Constants.CODE_TYPE_YESNO_YES+"' and t.submit_flg ='"+Constants.CODE_TYPE_TJZT_WTJ+"'"
				+" union"
				+" select  'yjb',count(*)  from tproposal t0 where t0.secondary_id='"+secondaryCode+"' and t0.analysis_unit='"+sessionInfo.getUserCode()+"' and t0.filing_flg='"+Constants.CODE_TYPE_YESNO_YES+"' and  t0.reply_pass is null and t0.submit_flg ='"+Constants.CODE_TYPE_TJZT_YTJ+"'"
				+" union"
				+" select  'stz',count(*)  from tproposal t1 where t1.secondary_id='"+secondaryCode+"' and t1.analysis_unit='"+sessionInfo.getUserCode()+"' and t1.submit_flg='"+Constants.CODE_TYPE_TJZT_YTJ+"' and t1.stutas='"+Constants.CODE_TYPE_TAZT_STZ+"'"
				+" union"
				+" select  'ydf',count(*) from tproposal t2 where t2.secondary_id='"+secondaryCode+"' and t2.analysis_unit='"+sessionInfo.getUserCode()+"' and t2.stutas='"+Constants.CODE_TYPE_TAZT_YBL+"'and t2.reply_pass ='"+Constants.CODE_TYPE_YESNO_YES+"'"
				+" union"
				+" select  'wdf',count(*) from tproposal t3 where t3.secondary_id='"+secondaryCode+"' and t3.analysis_unit='"+sessionInfo.getUserCode()+"' and t3.stutas!='"+Constants.CODE_TYPE_TAZT_STZ+"' and t3.submit_flg ='"+Constants.CODE_TYPE_TJZT_YTJ+"' and t3.reply_pass ='"+Constants.CODE_TYPE_YESNO_NO+"'";
			List<Object[]> list =(List<Object[]>) propDAO.executeCountSql(sql);
		 	int wjbNum=0;
		 	int yjbNum=0;
		 	int stzNum=0;
		 	int wdfNum=0;
		 	int ydfNum=0;
		 	//int revokeNum=0;
			for(int i=0;i<list.size();i++){
				Object[] objs=list.get(i);
			    if("wjb".equals(objs[0])){
			    	wjbNum=NumberFormatTools.getInstance().toInteger(objs[1]);
				}
				if("yjb".equals(objs[0])){
					yjbNum=NumberFormatTools.getInstance().toInteger(objs[1]);
				}
				if("stz".equals(objs[0])){
					stzNum=NumberFormatTools.getInstance().toInteger(objs[1]);
				}
				if("wdf".equals(objs[0])){
					wdfNum=NumberFormatTools.getInstance().toInteger(objs[1]);
				}
				if("ydf".equals(objs[0])){
					ydfNum=NumberFormatTools.getInstance().toInteger(objs[1]);
				}
			}
			countMap.put("wjbNum", wjbNum);
			countMap.put("yjbNum", yjbNum);
			countMap.put("stzNum", stzNum);
			countMap.put("wdfNum", wdfNum);
			countMap.put("ydfNum", ydfNum);
			
		}else {
			sql="select 'wsc',count(*) from tproposal t where t.secondary_id='"+secondaryCode+"' and t.stutas='"+Constants.CODE_TYPE_TAZT_WSC+"' and t.ADOPT_FLG ='"+Constants.CODE_TYPE_YESNO_YES+"'"
			+" union"
			+" select 'ysc',count(*) from tproposal t where t.secondary_id='"+secondaryCode+"' and t.stutas='"+Constants.CODE_TYPE_TAZT_YSC+"'and t.file_method is null and t.ADOPT_FLG ='"+Constants.CODE_TYPE_YESNO_YES+"' "
			+" union"
			+" select  'wla',count(*) from tproposal t where t.secondary_id='"+secondaryCode+"' and t.file_method ='"+Constants.CODE_TYPE_LAXS_BLA+"' and t.ADOPT_FLG='"+Constants.CODE_TYPE_YESNO_YES+"' "
			+" union"
			+" select  'yla',count(*) from tproposal t where t.secondary_id='"+secondaryCode+"' and t.file_method ='"+Constants.CODE_TYPE_LAXS_LA+"'and t.filing_flg='"+Constants.CODE_TYPE_YESNO_NO+"' and t.ADOPT_FLG='"+Constants.CODE_TYPE_YESNO_YES+"' "
			+" union"
			+" select  'yjb',count(*) from tproposal t where t.secondary_id='"+secondaryCode+"' and t.filing_flg='"+Constants.CODE_TYPE_YESNO_YES+"' and  t.reply_pass is null and t.submit_flg ='"+Constants.CODE_TYPE_TJZT_YTJ+"' and t.ADOPT_FLG='"+Constants.CODE_TYPE_YESNO_YES+"' "
			+" union"
			+" select  'wjb',count(*) from tproposal t where t.secondary_id='"+secondaryCode+"' and  t.filing_flg='"+Constants.CODE_TYPE_YESNO_YES+"' and t.submit_flg ='"+Constants.CODE_TYPE_TJZT_WTJ+"' and t.ADOPT_FLG='"+Constants.CODE_TYPE_YESNO_YES+"' "
			+" union"
			+" select  'ydf',count(*) from tproposal t where t.secondary_id='"+secondaryCode+"' and t.stutas='"+Constants.CODE_TYPE_TAZT_YBL+"'and t.reply_pass ='"+Constants.CODE_TYPE_YESNO_YES+"'"
			+" union"
			+" select  'wdf',count(*) from tproposal t where t.secondary_id='"+secondaryCode+"' and t.stutas!='"+Constants.CODE_TYPE_TAZT_STZ+"' and t.submit_flg ='"+Constants.CODE_TYPE_TJZT_YTJ+"' and t.reply_pass ='"+Constants.CODE_TYPE_YESNO_NO+"'"
			+" union"
			+" select  'stz',count(*) from tproposal t1 where t1.secondary_id='"+secondaryCode+"' and t1.submit_flg='"+Constants.CODE_TYPE_TJZT_YTJ+"' and t1.stutas='"+Constants.CODE_TYPE_TAZT_STZ+"'";
			System.out.println(sql);
			List<Object[]> list =(List<Object[]>) propDAO.executeCountSql(sql);
				int wscNum=0;
				int yscNum=0;
			 	int wlaNum=0;
			 	int ylaNum=0;
			 	int wjbNum=0;
			 	int yjbNum=0;
			 	int stzNum=0;
			 	int wdfNum=0;
			 	int ydfNum=0;
			 	//int revokeNum=0;
				for(int i=0;i<list.size();i++){
					Object[] objs=list.get(i);
					if("wsc".equals(objs[0])){
						wscNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
					if("ysc".equals(objs[0])){
						yscNum= NumberFormatTools.getInstance().toInteger(objs[1]);
					}
					if("wla".equals(objs[0])){
						wlaNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
					if("yla".equals(objs[0])){
						ylaNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
				    if("wjb".equals(objs[0])){
				    	wjbNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
					if("yjb".equals(objs[0])){
						yjbNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
					if("stz".equals(objs[0])){
						stzNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
					if("wdf".equals(objs[0])){
						wdfNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
					if("ydf".equals(objs[0])){
						ydfNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
				}
				countMap.put("wscNum", wscNum);
				countMap.put("yscNum", yscNum);
				countMap.put("wlaNum", wlaNum);
				countMap.put("ylaNum", ylaNum);
				countMap.put("wjbNum", wjbNum);
				countMap.put("yjbNum", yjbNum);
				countMap.put("stzNum", stzNum);
				countMap.put("wdfNum", wdfNum);
				countMap.put("ydfNum", ydfNum);
		}
		return countMap;
	}
	/**
	 * 
	 * 提案人驳回修改提案统计
	 * 
	 * @param companyId
	 * 
	 * @throws 	Exception
	 * 
	 */
	public Long commEditRemind(String commId,HttpSession httpSession) throws Exception {
		Long result=null;
		String secondaryCode=((Seco)httpSession.getAttribute("sessionSeco")).getSecondaryId();
		
		String hql="from Tproposal t1 where exists(select t2.tproposal.proposalId from Tproposaler t2 where t2.tcommitteeman.code='"+commId+"' and t2.hostFlg='"+Constants.CODE_TYPE_YESNO_YES+"' and t2.tproposal.proposalId=t1.proposalId) and t1.tsecondary.secondaryId='"+secondaryCode+"' and t1.adviceFlg='"+Constants.CODE_TYPE_YESNO_YES+"'";
		result=handDao.count("select count(*) "+hql);
		return result;
	}
	/**
	 * 
	 * 承办单位新办提案统计
	 * 
	 * @param companyId
	 * 
	 * @throws 	Exception
	 * 
	 */
	public Long compNewRemind(String companyId,HttpSession httpSession) throws Exception {
		String secondaryCode=((Seco)httpSession.getAttribute("sessionSeco")).getSecondaryId();
		Long result=null;
		String hql="from ThandleReply t where t.tcompany.companyId='"+companyId+"' and exists (select p.proposalId from Tproposal p where p.tsecondary.secondaryId='"+secondaryCode+"' and p.submitFlg='"+Constants.CODE_TYPE_TJZT_YTJ+"' and p.proposalId=t.tproposal.proposalId) and t.status='"+Constants.CODE_TYPE_HFZT_NEW+"'";
		result=handDao.count("select count(*) "+hql);
		return result;
	}
	/**
	 * 
	 * 管理员未审查提案统计
	 * 
	 * @param companyId
	 * 
	 * @throws 	Exception
	 * 
	 */
	public Long adminNewRemind(HttpSession httpSession) throws Exception {
		String secondaryCode=((Seco)httpSession.getAttribute("sessionSeco")).getSecondaryId();
		Long result=null;
		String hql="from Tproposal t where t.tsecondary.secondaryId='"+secondaryCode+"' and  t.stutas='"+Constants.CODE_TYPE_TAZT_WSC+"' and t.revokeFlg is null and t.adoptFlg!='"+Constants.CODE_TYPE_YESNO_NO+"'";
		result=propDAO.count("select count(*) "+hql);
		return result;
	}
	/**
	 * 
	 * 政府办确定单位提案统计
	 * 
	 * @param companyId
	 * 
	 * @throws 	Exception
	 * 
	 */
	public Long govCheckRemind(String userCode,HttpSession httpSession) throws Exception {
		String secondaryCode=((Seco)httpSession.getAttribute("sessionSeco")).getSecondaryId();
		Long result=null;
		String hql="from Tproposal t where t.tsecondary.secondaryId='"+secondaryCode+"' and t.fileMethod='"+Constants.CODE_TYPE_LAXS_LA+"' and t.analySisUnit='"+userCode+"' and t.submitFlg!='"+Constants.CODE_TYPE_TJZT_YTJ+"'";
		result=propDAO.count("select count(*) "+hql);
		return result;
	}
	/**
	 * 
	 * 政府办申退处理提案统计
	 * 
	 * @param companyId
	 * 
	 * @throws 	Exception
	 * 
	 */
	public Long govBackRemind(String userCode,HttpSession httpSession) throws Exception {
		String secondaryCode=((Seco)httpSession.getAttribute("sessionSeco")).getSecondaryId();
		Long result=null;
		String hql="from Tproposal t where t.tsecondary.secondaryId='"+secondaryCode+"' and t.stutas='"+Constants.CODE_TYPE_TAZT_STZ+"' and t.analySisUnit='"+userCode+"'";
		result=propDAO.count("select count(*) "+hql);
		return result;
	}
	/**
	 * 
	 * 新增提案
	 * 
	 * @param prop
	 * 
	 * @throws 	Exception
	 * 
	 */
	public String add(Proposal prop,HttpSession httpSession) throws Exception {
		Tproposal tp=null;
			System.out.println("fist=="+prop.getFistProposaler());
		if(prop.getProposalId()==null || "".equals(prop.getProposalId())){
			tp=new Tproposal();
			BeanUtils.copyProperties(tp,prop);
			String proposalId=Generator.getInstance().getProposalNO();
			prop.setProposalId(proposalId);
			tp.setProposalId(proposalId);
			Tsecondary tsecondary=new Tsecondary();
			tsecondary.setSecondaryId(prop.getSecondaryId());
			tp.setTsecondary(tsecondary);
			String[] ids=prop.getSponsorIds().split(",");
			tp.setProposalerNum(ids.length+"");
			tp.setStutas(Constants.CODE_TYPE_TAZT_YBC);
			tp.setUpdateFlg(Constants.CODE_TYPE_UPDATE_YES);
			tp.setAdoptFlg(Constants.CODE_TYPE_YESNO_YES);
			tp.setFilingFlg(Constants.CODE_TYPE_YESNO_NO);
			//tp.setRevokeFlg(Constants.CODE_TYPE_YESNO_NO);
			tp.setMeetingFlg(((Seco)httpSession.getAttribute("sessionSeco")).getPeriod());
			tp.setSubmitFlg(Constants.CODE_TYPE_TJZT_WTJ);
			tp.setCreateTime(Util.getInstance().getFormat(new Date(),"yyyy-MM-dd H:m:s"));
			tp.setContent(prop.getContent());
			propDAO.save(tp);
			Tdolog dolog=new Tdolog();
			dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
			dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_ADD);
			dolog.setKeyId(tp.getProposalId());
			dolog.setInfo( "[提案]新提案保存");
			this.saveLog(dolog);
			saveProposaler(prop);
		}else{
			tp=propDAO.get(Tproposal.class, prop.getProposalId());
			tp.setContent(prop.getContent());
		}
		return tp.getProposalId();
	}
	/**
	 * 
	 * 修改提案
	 * 
	 * @param prop,httpSession
	 * 
	 * @throws 	Exception
	 * 
	 */
	public void edit(Proposal prop,HttpSession httpSession) throws Exception {
		SessionInfo info=(SessionInfo)httpSession.getAttribute(ResourceUtil.getSessionInfoName());
		Tproposal tp=propDAO.get(Tproposal.class,prop.getProposalId());
		if(info.getUserGroup().equals(Constants.DIC_TYPE_YHZB_GLY)){
			String[] ids=prop.getSponsorIds().split(",");
			tp.setProposalerNum(ids.length+"");
			//tp.setStutas(Constants.CODE_TYPE_TAZT_YSC);
		}
		System.out.println(Util.getInstance().getFormat(new Date(),"yyyy-MM-dd H:m:s"));
		tp.setCreateTime(Util.getInstance().getFormat(new Date(),"yyyy-MM-dd H:m:s"));
		tp.setFistProposaler(prop.getFistProposaler());
		tp.setProposalType(prop.getProposalType());
		System.out.println("propType======"+prop.getProposalType());
		tp.setTitle(prop.getTitle());
		tp.setUndertakeUnit(prop.getUndertakeUnit());
		tp.setWebFlg(prop.getWebFlg());
		tp.setSecondFlg(prop.getSecondFlg());
		//BeanUtils.copyProperties(tp, prop);
		tp.setContent(prop.getContent());
		tp.setUpdateFlg(Constants.CODE_TYPE_UPDATE_YES);
		propDAO.update(tp);
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_EDIT);
		dolog.setKeyId(tp.getProposalId());
		dolog.setInfo( "[提案]提案内容修改");
		this.saveLog(dolog);
		if(info.getUserGroup().equals(Constants.DIC_TYPE_YHZB_GLY)){
			saveProposaler(prop);
		}
		
	}
	/**
	 * 
	 * 初审提案
	 * 
	 * @param prop,httpSession
	 * 
	 * @throws 	Exception
	 * 
	 */
	public void checkEdit(Proposal prop) throws Exception {
		Tproposal tp=propDAO.get(Tproposal.class,prop.getProposalId());
		String[] ids=prop.getSponsorIds().split(",");
		tp.setProposalerNum(ids.length+"");
		tp.setStutas(Constants.CODE_TYPE_TAZT_YSC);
		tp.setFistProposaler(prop.getFistProposaler());
		tp.setProposalType(prop.getProposalType());
		System.out.println("propType======"+prop.getProposalType());
		tp.setTitle(prop.getTitle());
		tp.setUndertakeUnit(prop.getUndertakeUnit());
		tp.setWebFlg(prop.getWebFlg());
		tp.setSecondFlg(prop.getSecondFlg());
		//BeanUtils.copyProperties(tp, prop);
		tp.setContent(prop.getContent());
		tp.setUpdateFlg(Constants.CODE_TYPE_UPDATE_YES);
		propDAO.update(tp);
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_CHECK);
		dolog.setKeyId(tp.getProposalId());
		dolog.setInfo( "[提案]提案内容初审");
		this.saveLog(dolog);
		saveProposaler(prop);
	}
	/**
	 * 提案的提案人保存
	 * @param prop
	 * 
	 * @throws 	Exception
	 */
	private void saveProposaler(Proposal prop)throws Exception{
		//sponsorDAO.executeHql("delete Tproposaler t where t.tproposal = ?", new Object[] { prop });
		String hql=" from Tproposaler t1 where t1.tproposal.proposalId='"+prop.getProposalId()+"'";
		List<Tproposaler> spList=sponsorDAO.find(hql);
		if(prop.getSponsorIds()!=null && !"".equals(prop.getSponsorIds())){
			String[] ids=prop.getSponsorIds().split(",");
			String[] hosts=prop.getHostFlgs().split(",");
			for(int i=0;i<ids.length;i++){
				int flg=0;
				for(int j=0;j<spList.size();j++){
					if(ids[i].equals(spList.get(j).getTcommitteeman().getCode())){
						spList.remove(j);
						flg=1;
						break;
					}
				}
				if(flg!=1){
					Tproposaler sponsor = new Tproposaler();
					sponsor.setProposalerId(Generator.getInstance().getSponsorNO());
					Tproposal tp=new Tproposal();
					tp.setProposalId(prop.getProposalId());
					sponsor.setTproposal(tp);
					sponsor.setHostFlg(hosts[i]);
					Tcommitteeman man=new Tcommitteeman();
					man.setCode(ids[i]);
					sponsor.setTcommitteeman(man);
					sponsorDAO.save(sponsor);
				}
				
			}
			if(spList!=null&&spList.size()>0){
				for(Tproposaler t:spList){
					sponsorDAO.delete(t);
				}
			}
		}
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_ADD);
		dolog.setKeyId(prop.getProposalId());
		dolog.setInfo( "[提案]提案人及附议人信息保存（提案人ID:"+prop.getSponsorIds()+"）");
		this.saveLog(dolog);
	}
	/**
	 * 
	 * 提案退回修改
	 * 
	 * @param prop,httpSession
	 * 
	 * @throws 	Exception
	 * 
	 */
	public void backEdit(Proposal prop) throws Exception {
		Tproposal tp=propDAO.get(Tproposal.class,prop.getProposalId());
		tp.setStutas(Constants.CODE_TYPE_TAZT_YBC);
		tp.setAdviceInfo(prop.getAdviceInfo());
		tp.setProposalCode("");
		tp.setAdviceFlg("1");
		propDAO.update(tp);
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_BACK);
		dolog.setKeyId(prop.getProposalId());
		dolog.setInfo( "[提案]提案驳回重新修改");
		this.saveLog(dolog);
	}
	/**
	 * 
	 * 更新状态设置
	 * 
	 * @param prop,httpSession
	 * 
	 * @throws 	Exception
	 * 
	 */
	public void setUpdateFlg(Proposal prop) throws Exception {
		Tproposal tp=propDAO.get(Tproposal.class,prop.getProposalId());
		tp.setUpdateFlg(Constants.CODE_TYPE_UPDATE_NO);
		propDAO.update(tp);
		
	}
	
	/**
	 * 
	 * 提案审查立案
	 * 
	 * @param prop
	 * 
	 * @throws 	Exception
	 * 
	 */
	public void check(Proposal prop, String period) throws Exception {
		Tproposal tp=propDAO.get(Tproposal.class,prop.getProposalId());
		tp.setFileMethod(prop.getFileMethod());
		if(Constants.CODE_TYPE_LAXS_BLA.equals(prop.getFileMethod())){
			tp.setNoFillingReason(prop.getNoFillingReason());
		}
		String[] ids=prop.getSponsorIds().split(",");
		tp.setProposalerNum(ids.length+"");
		tp.setFistProposaler(prop.getFistProposaler());
		tp.setProposalType(prop.getProposalType());
		tp.setUndertakeUnit(prop.getUndertakeUnit());
		tp.setTitle(prop.getTitle());
		tp.setWebFlg(prop.getWebFlg());
		tp.setSecondFlg(prop.getSecondFlg());
		tp.setContent(prop.getContent());
		tp.setAnalySisUnit(prop.getAnalySisUnit());
		tp.setPropertiesType(prop.getPropertiesType());
		if(prop.getPropertiesType()!=null &&!"".equals(prop.getPropertiesType())){
			String typeId=prop.getTypeId();
			if(typeId==null||"".equals(typeId)){
				typeId=getMaxTypeCode(prop.getPropertiesType(),prop.getSecondaryId());
				if(typeId==null||"".equals(typeId)){
					typeId=Util.getInstance().toStrFormat(1, "000");
				}else {
					typeId=Util.getInstance().toStrFormat(NumberFormatTools.getInstance().toInteger(typeId)+1, "000");
				}
			}
			tp.setTypeId(typeId);
		}
		tp.setBusinessCode(prop.getBusinessCode());
		tp.setHandleType(prop.getHandleType());
		tp.setCheckTime(Util.getInstance().getFormat(prop.getCheckTime(), "yyyy-MM-dd"));
		tp.setAdviceUnit(prop.getAdviceUnit());
		tp.setStutas(Constants.CODE_TYPE_TAZT_YSC);
		tp.setUpdateFlg(Constants.CODE_TYPE_UPDATE_YES);
		propDAO.update(tp);
		
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_CHECK);
		dolog.setKeyId(prop.getProposalId());
		dolog.setInfo( "[提案]提案审查立案");
		this.saveLog(dolog);
		
		saveProposaler(prop);
		saveHandReply(prop);
		this.sortCode(tp, period);
	}
	/**
	 * 
	 * 确认承办单位
	 * 
	 * @param prop
	 * 
	 * @throws 	Exception
	 * 
	 */
	public void confirm(Proposal prop) throws Exception {
		Tproposal tp=propDAO.get(Tproposal.class,prop.getProposalId());
		tp.setHandleType(prop.getHandleType());
		tp.setAnalySisUnit(prop.getAnalySisUnit());
		tp.setInstructions(prop.getInstructions());
		tp.setSubmitTime(Util.getInstance().getFormat(prop.getSubmitTime(), "yyyy-MM-dd"));
		tp.setDemandEnddate(Util.getInstance().getFormat(Util.getInstance().getAddMonthDate(prop.getSubmitTime(),90), "yyyy-MM-dd"));
		tp.setStutas(Constants.CODE_TYPE_TAZT_WBL);
		tp.setSubmitFlg(Constants.CODE_TYPE_TJZT_YTJ);
		tp.setUpdateFlg(Constants.CODE_TYPE_UPDATE_YES);
		propDAO.update(tp);
		
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_CHECK);
		dolog.setKeyId(prop.getProposalId());
		dolog.setInfo( "[提案]提案确定承办单位");
		this.saveLog(dolog);
		
		saveHandReply(prop);
	}
	/**
	 * 
	 * 设置提案总体解决程序
	 * 
	 * @param prop
	 * 
	 * @throws 	Exception
	 * 
	 */
	public void setOpt(Proposal prop) throws Exception {
		Tproposal tp=propDAO.get(Tproposal.class,prop.getProposalId());
		tp.setSolveHow(prop.getSolveHow());
		tp.setCarryoutFlg(prop.getCarryoutFlg());
		tp.setCommitteemanOpinion(prop.getCommitteemanOpinion());
		
		propDAO.saveOrUpdate(tp);
		
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_SET);
		dolog.setKeyId(prop.getProposalId());
		dolog.setInfo( "[提案]设置提案解决程度");
		this.saveLog(dolog);
	}
	/**
	 * 
	 * 提案承办单位保存
	 * 
	 * @param prop
	 * 
	 * @throws 	Exception
	 * 
	 */
	private  void saveHandReply(Proposal prop) throws Exception {
		//handDao.executeHql("delete ThandleReply t where t.tproposal = ?", new Object[] { prop });
		String hql=" from ThandleReply t1 where t1.tproposal.proposalId='"+prop.getProposalId()+"'";
		List<ThandleReply> hList=handDao.find(hql);
		if(prop.getCompanyIds()!=null && !"".equals(prop.getCompanyIds())){
			String[] ids=prop.getCompanyIds().split(",");
			String[] mainflgs=null;
			if(prop.getMainFlgs()!=null&&!"".equals(prop.getMainFlgs())){
				mainflgs=prop.getMainFlgs().split(",");
			}
			for(int i=0;i<ids.length;i++){
				int flg=0;
				for(int j=0;j<hList.size();j++){
					if(ids[i].equals(hList.get(j).getTcompany().getCompanyId())){
						ThandleReply h=hList.get(j);
						h.setStatus(Constants.CODE_TYPE_HFZT_NEW);
						if(mainflgs!=null&&mainflgs.length>0){
							h.setMainFlg(mainflgs[i]);
						}
						handDao.update(h);
						hList.remove(j);
						flg=1;
						break;
					}
				}
				if(flg!=1){
					ThandleReply hand = new ThandleReply();
					hand.setHandleReplyId(Generator.getInstance().getHandleReplyNO());
					Tproposal tp=new Tproposal();
					tp.setProposalId(prop.getProposalId());
					hand.setTproposal(tp);
					hand.setStatus(Constants.CODE_TYPE_HFZT_NEW);
					Tcompany tc=new Tcompany();
					tc.setCompanyId(ids[i]);
					if(mainflgs!=null&&mainflgs.length>0){
						hand.setMainFlg(mainflgs[i]);
					}
					hand.setTcompany(tc);
					handDao.save(hand);
				}
			}
			if(hList!=null&&hList.size()>0){
				for(ThandleReply t:hList){
					handDao.delete(t);
				}
			}
		}
	}
	/**
	 * 
	 *正式交办提案
	 * 
	 * @param prop
	 * 
	 * @throws 	Exception
	 * 
	 */
	public void assgnSub(Proposal prop) throws Exception {
		if(prop.getIds()!=null && !"".equals(prop.getIds())){
			String[] ids=prop.getIds().split(",");
			for(int i=0;i<ids.length;i++){
				Tproposal tp=propDAO.get(Tproposal.class,ids[i]);
				tp.setSubmitFlg(Constants.CODE_TYPE_TJZT_YTJ);
				tp.setStutas(Constants.CODE_TYPE_TAZT_WBL);
				tp.setSubmitTime(new Date());
				tp.setDemandEnddate(Util.getInstance().getFormat(Util.getInstance().getAddMonthDate(tp.getSubmitTime(),90), "yyyy-MM-dd"));
				tp.setUpdateFlg(Constants.CODE_TYPE_UPDATE_YES);
				propDAO.update(tp);
			}
			
			Tdolog dolog=new Tdolog();
			dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
			dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_SUBMIT);
			dolog.setInfo( "[提案]交办提案（提案ID:"+prop.getIds()+"）");
			this.saveLog(dolog);
		}
		
	}
	/**
	 * 
	 *确定立案提案
	 * 
	 * @param prop
	 * 
	 * @throws 	Exception
	 * 
	 */
	public void setFiling(Proposal prop) throws Exception {
		if(prop.getIds()!=null && !"".equals(prop.getIds())){
			String[] ids=prop.getIds().split(",");
			for(int i=0;i<ids.length;i++){
				Tproposal tp=propDAO.get(Tproposal.class,ids[i]);
				tp.setFilingFlg(Constants.CODE_TYPE_YESNO_YES);
				propDAO.update(tp);
			}
			Tdolog dolog=new Tdolog();
			dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
			dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_SUBMIT);
			dolog.setInfo( "[提案]提案确定转办（提案ID:"+prop.getIds()+"）");
			this.saveLog(dolog);
		}
		
	}
	/**
	 * 
	 * 获取当前届最大编号
	 * @param ext 
	 * 
	 * @param prop
	 * 
	 * @throws 	Exception
	 * 
	 * @return 		DataGrid返回值
	 */
	public String getMaxCode(String ext, HttpSession httpSession) throws Exception {
		System.out.println("ext========"+ext);
		String secondaryCode=((Seco)httpSession.getAttribute("sessionSeco")).getSecondaryId();
		String hql="select max(proposalCode) from Tproposal t1 where t1.tsecondary.secondaryId='"+secondaryCode+"' and substr(t1.proposalCode,0," + ext.length() + ")='" + ext + "'";
		String code=propDAO.findHql(hql);
		System.out.println("maxcode=========="+code);
		if(code==null){
			return "";
		}else{
			return code;
		}
	}
	/**
	 * 
	 * 获取当前届内容分类最大编号
	 * @param ext 
	 * 
	 * @param prop
	 * 
	 * @throws 	Exception
	 * 
	 * @return 		DataGrid返回值
	 */
	public String getMaxTypeCode(String propertiesType, String secondaryCode) throws Exception {
		String hql="select max(typeId) from Tproposal t1 where t1.tsecondary.secondaryId='"+secondaryCode+"' and t1.propertiesType='"+propertiesType+"'";
		String code=propDAO.findHql(hql);
		if(code==null){
			return "";
		}else{
			return code;
		}
	}
	/**
	 * 
	 * 撤案
	 * 
	 * @param prop
	 * 
	 * @throws 	Exception
	 */
	public int delete(Proposal prop) throws Exception {
		int res=0;
		String hql="update Tproposal t set t.stutas='"+Constants.CODE_TYPE_TAZT_YBC+"',t.proposalCode=''";
		if(prop.getIds()!=null && !"".equals(prop.getIds())){
			String[] ids=prop.getIds().split(",");
			for(int i=0;i<ids.length;i++){
				String sql=hql+" where t.proposalId='"+ids[i]+"'";
				res=propDAO.executeHql(sql);
				
				Tdolog dolog=new Tdolog();
				dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
				dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_BACK);
				dolog.setKeyId(ids[i]);
				dolog.setInfo( "[提案]提案撤消");
				this.saveLog(dolog);
			}
		}
		return res;
	}
	/**
	 * 
	 *不采纳提案设置
	 * 
	 * @param prop
	 * 
	 * @throws 	Exception
	 */
	public int setAdopt(Proposal prop) throws Exception {
		int res=0;
		String hql="update Tproposal t set t.adoptFlg='"+Constants.CODE_TYPE_YESNO_NO+"'";
		if(prop.getAdoptExplain()!=null&&!"".equals(prop.getAdoptExplain())){
			hql+=",t.adoptExplain='"+prop.getAdoptExplain()+"'";
		}
		hql+="where t.proposalId='"+prop.getProposalId()+"'";
		res=propDAO.executeHql(hql);
		
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_SET);
		dolog.setKeyId(prop.getProposalId());
		dolog.setInfo( "[提案]设置提案不采纳");
		this.saveLog(dolog);
		
		return res;
	}
	/**
	 * 合并提案
	 * @param prop
	 * @throws Exception
	 */
	public void setMerge(Proposal prop) throws Exception {
		
		List<Tproposal> tas = new ArrayList<Tproposal>();
		List<Tproposaler> gxs = new ArrayList<Tproposaler>();
		
		
		String[] ids=prop.getIds().split(","); //要合并提案的ids
		
		//原来主提案
		Tproposal tpOld=propDAO.get(Tproposal.class,ids[0]); //主提案
		
		//新构建一个提案（用主提案） 
		Tproposal tpNew= new Tproposal();
		
		String[] ign={"thandleReplies","tproposalers"};
		org.springframework.beans.BeanUtils.copyProperties(tpOld, tpNew,ign);
		
		tpNew.setStutas(Constants.CODE_TYPE_TAZT_WSC);

		//原来主提案提案人数
		int num=NumberFormatTools.getInstance().toInteger(tpOld.getProposalerNum()); 
		//原来附提案ID
		String mergeids="";
		if(tpOld.getMergeIds()!=null){
			mergeids=tpOld.getMergeIds()+",";
		} else {
			mergeids=ids[0]+",";
		}
		 
		//循环选中的提案
		for (int i = 0; i < ids.length; i++) {
			// 合并第一提案人 到主提案
			Tproposal t = propDAO.get(Tproposal.class, ids[i]);
			String[] str = t.getFistProposaler().split(",");
			for (int j = 0; j < str.length; j++) {
				if (tpNew.getFistProposaler().indexOf(str[j]) == -1) {
					tpNew.setFistProposaler(tpNew.getFistProposaler() + "," + str[j]);
				}
			}
			// 提案的 提案关系 人-提案
			List<Tproposaler> tList = sponsorDAO.find("from Tproposaler t where t.tproposal.proposalId='"+ ids[i] + "'");

			//主提案关系
			List<Tproposaler> spList=sponsorDAO.find("from Tproposaler t where t.tproposal.proposalId='"+ids[0]+"'");
			// 循环 提案关系
			for (Tproposaler temp : tList) {
				// 新构建提案关系（关系不变把提案换掉）
				boolean flg = false;
				for (Tproposaler tt : spList) {
					if (temp.getTcommitteeman().getCode() == tt.getTcommitteeman().getCode()) {
						flg = true;
						break;
					}
				}
				if (!flg) {
					num += 1;
				}
			}
			//不是主提案
			if(i!=0) {
				
				//设置合并提案ID
				if(Constants.CODE_TYPE_YESNO_YES.equals(t.getMergeFlag())) {
				//附提案原来就为合并的提案
					mergeids += t.getMergeIds() + ",";
				} else {
				//附提案原来就为未合并的提案
					// 合并 副提案ID
					mergeids += ids[i] + ",";
				}
				
				if(Constants.CODE_TYPE_YESNO_YES.equals(t.getMergeFlag())) {
				//附提案原来就为合并的提案
					// 删除原来已经为合并状态的附提案 及提案关系
					Set<Tproposaler> psSet=t.getTproposalers();
					for (Tproposaler ps : psSet) {
						//ps.setTproposal(tpNew);
						//修改关系中含的提案成新提案，相当于删除。 新建合并后提案后才能用，否则报错。！！
						//sponsorDAO.delete(ps);
						gxs.add(ps);
					}
					//propDAO.delete(t);
					tas.add(t);
				} else {
				//附提案原来就为未合并的提案
					// 给附提案设置参数。并使他不显示。
					t.setStutas(Constants.CODE_TYPE_TAZT_WSC);
					t.setFileMethod("");
					t.setAdoptFlg(Constants.CODE_TYPE_YESNO_NO); // 是否采纳
					t.setAdoptExplain(prop.getAdoptExplain()); // 撤案说明
					t.setMergeFlag(Constants.CODE_TYPE_YESNO_YES); // 合并标志
					propDAO.update(t);
				}
				
				Tdolog dolog = new Tdolog();
				dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
				dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_SET);
				dolog.setKeyId(t.getProposalId());
				dolog.setInfo("[提案]设置提案被合并");
				this.saveLog(dolog);
			
				// 主提案内容追加 附提案内容
				tpNew.setContent(tpNew.getContent()+ "<p><b><font color='#F00' size='2'>参考意见：</font></b></p>" + t.getContent());
			}
		}
		
		//去逗号
		mergeids=mergeids.substring(0,(mergeids.length()-1));
		
		//设置新提案状态并保存
		tpNew.setProposalerNum(num+"");
		tpNew.setAdoptExplain(prop.getAdoptExplain());
		tpNew.setMergeFlag(Constants.CODE_TYPE_YESNO_YES);
		tpNew.setMergeIds(mergeids);
		tpNew.setAdoptFlg(Constants.CODE_TYPE_YESNO_YES);
		tpNew.setProposalId(Generator.getInstance().getProposalNO());
		propDAO.save(tpNew);
		
		//处理提案关系 循环选中的提案
		for (int i = 0; i < ids.length; i++) {
			
			// 提案的 提案关系 人-提案
			List<Tproposaler> tList2= sponsorDAO.find("from Tproposaler t where t.tproposal.proposalId='"+ids[i]+"'");
			//主提案关系
			List<Tproposaler> spList2= sponsorDAO.find("from Tproposaler t where t.tproposal.proposalId='"+ids[0]+"'");
			// 循环 提案关系
			for (Tproposaler nowTa : tList2) {
				// 新构建提案关系（关系不变把提案换掉）
				boolean flg = false;
				for (Tproposaler zta : spList2) {
					if (nowTa.getTcommitteeman().getCode() == zta.getTcommitteeman().getCode()) {
						flg = true;
						break;
					}
				}
				//查看是否关系已经存在
				List<Tproposaler>  existedSp =sponsorDAO.find("from Tproposaler t where t.tproposal.proposalId='"+tpNew.getProposalId()+"' and t.tcommitteeman.code='"+nowTa.getTcommitteeman().getCode()+"'");
//				if(!(existedSp!=null && existedSp.size()>0)) {
				//关系不存在的情况下
					//生成新提案关系
					Tproposaler sponsor = new Tproposaler();
					org.springframework.beans.BeanUtils.copyProperties(nowTa, sponsor,ign);
					sponsor.setTcommitteeman(nowTa.getTcommitteeman());
					sponsor.setTproposal(tpNew);
					sponsor.setProposalerId(Generator.getInstance().getSponsorNO());
					sponsorDAO.save(sponsor);
//				}
					
			}
		}
		//////////////////////关系替重复
		List<Tproposaler>  tcList =sponsorDAO.find("from Tproposaler t where t.tproposal.proposalId='"+tpNew.getProposalId()+"'" );
		List<Tproposaler>  tcNewList = new ArrayList<Tproposaler>();
		for (Tproposaler tproposaler : tcList) {
			String code =tproposaler.getTcommitteeman().getCode();
			boolean flg = true;
			for (Tproposaler tp2 : tcNewList) {
				if(code.equals(tp2.getTcommitteeman().getCode())) {
					flg=false;
				}
			}
			if(flg) {
				tcNewList.add(tproposaler);
			}
		}
		for (Tproposaler tp3 : tcList) {
			boolean flg = true;
			for (Tproposaler tp4 : tcNewList) {
				if(tp3.getProposalerId().equals(tp4.getProposalerId())) {
					flg=false;
				}
			}
			if(flg) {
				sponsorDAO.delete(tp3);
			}
		}
		//////////////////////
		//删除合并时付提案是已合并的提案。
		for (Tproposaler gx : gxs) {
			sponsorDAO.delete(gx);
		}
		for (Tproposal ta : tas) {
			propDAO.delete(ta);
		}
		
		
		//处理旧主提案
		if(Constants.CODE_TYPE_YESNO_YES.equals(tpOld.getMergeFlag())) {
		//旧主提案原来就为合并的提案
			// 删除旧主提案
			Set<Tproposaler> psSet=tpOld.getTproposalers();
			for (Tproposaler ps : psSet) {
				sponsorDAO.delete(ps);
			}
			propDAO.delete(tpOld);
		} else {
		//旧主提案原来就为未合并的提案
			// 取旧主提案 让旧提案不显示
			tpOld.setStutas(Constants.CODE_TYPE_TAZT_WSC);
			tpOld.setAdoptFlg(Constants.CODE_TYPE_YESNO_NO); //是否采纳
			tpOld.setAdoptExplain(prop.getAdoptExplain()); //撤案说明
			tpOld.setMergeFlag(Constants.CODE_TYPE_YESNO_YES); //合并标志
			propDAO.update(tpOld);
		}
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_SET);
		dolog.setKeyId(tpNew.getProposalId());
		dolog.setInfo( "[提案]设置提案合并");
		this.saveLog(dolog);
	}
	/**
	 * 取消合并提案
	 * @param prop
	 * @throws Exception
	 */
	public void cancelMerge(Proposal prop) throws Exception {
		//主提案id
		String proposalId=prop.getProposalId();
		
		//主提案 获取
		Tproposal tp=propDAO.get(Tproposal.class,proposalId); //主提案
		String mergeIds = tp.getMergeIds();
		//合并前的提案ID集合
		if(mergeIds!=null && !"".equals(mergeIds) && !"".equals(mergeIds.replace(",", ""))) {
			String ids[]=mergeIds.split(",");
			for (String id : ids) {
				//循环选中的附提案并让附提案都单独显示
				Tproposal ta=propDAO.get(Tproposal.class,id.trim());
				ta.setStutas(Constants.CODE_TYPE_TAZT_WSC);
				ta.setFileMethod("");
				ta.setAdoptFlg(Constants.CODE_TYPE_YESNO_YES); //是否采纳
				ta.setAdoptExplain(""); //撤案说明
				ta.setMergeFlag(Constants.CODE_TYPE_YESNO_NO); //合并标志
				propDAO.update(ta);
				//生成日志
				this.saveLog(Constants.LOG_TYPE_CODE_PROP, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[提案审查]取消合并提案-让合并前各提案显示");
			}
		}
		
		//删除合并扣的提案，提案关系
		List<Tproposaler> spList=sponsorDAO.find("from Tproposaler t where t.tproposal.proposalId='"+proposalId+"'");
		for (Tproposaler propler : spList) {
			sponsorDAO.delete(propler);
			//生成日志
			this.saveLog(Constants.LOG_TYPE_CODE_PROP, Constants.LOG_TYPE_OPERTYPE_DEL, propler.getProposalerId(), "[提案审查]取消合并提案-删除合并后提案关系");
		}
		propDAO.delete(tp);
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_PROP, Constants.LOG_TYPE_OPERTYPE_DEL, proposalId, "[提案审查]取消合并提案-删除合并后提案");
	}
	/**
	 * 
	 *重点提案设置
	 * 
	 * @param prop
	 * 
	 * @throws 	Exception
	 */
	public void setStress(Proposal prop,String period) throws Exception {
		String flg=Constants.CODE_TYPE_YESNO_NO.equals(prop.getStressFlg()) ? "" : prop.getStressFlg();	//重点提案选“否”，则设置为“”
		Tproposal tp=propDAO.get(Tproposal.class,prop.getProposalId());
		tp.setStressFlg(flg);
		tp.setLeader(prop.getLeader());
		tp.setUpdateFlg(Constants.CODE_TYPE_UPDATE_YES);
		propDAO.saveOrUpdate(tp);
		
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_SET);
		dolog.setKeyId(tp.getProposalId());
		dolog.setInfo( "[提案]重点提案设置");
		this.saveLog(dolog);
		
		if(Constants.CODE_TYPE_HYPS_HY.equals(period)&&!Constants.CODE_TYPE_YESNO_YES.equals(tp.getFilingFlg())){
			this.sortCode(tp, period);
		}
	}
	/**
	 * 
	 *优秀提案设置
	 * 
	 * @param prop
	 * 
	 * @throws 	Exception
	 */
	public int setFine(Proposal prop) throws Exception {
		int res=0;
		String flg=Constants.CODE_TYPE_YESNO_NO.equals(prop.getExcellentFlg()) ? "" : prop.getExcellentFlg();	//重点提案选“否”，则设置为“”
		String hql="update Tproposal t set t.excellentFlg='"+ flg +"'";
		hql+="where t.proposalId='"+prop.getProposalId()+"'";
		res=propDAO.executeHql(hql);
		
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_SET);
		dolog.setKeyId(prop.getProposalId());
		dolog.setInfo( "[提案]优秀提案设置");
		this.saveLog(dolog);
		
		return res;
	}
	/*
	 * 组合hql语句
	 */	
	private String addWhere(Proposal prop, String hql) {
		if(prop.getCompanyId()!=null && !"".equals(prop.getCompanyId())){
			hql += " and exists(select h.tcompany.companyId from ThandleReply h where h.tcompany.companyId='"+prop.getCompanyId()+"'and h.tproposal.proposalId= t1.proposalId)";
		}
		if(prop.getCompanyName()!=null && !"".equals(prop.getCompanyName())){
			hql += " and exists(select h.tcompany.companyId from ThandleReply h where h.tcompany.companyName like'%"+prop.getCompanyName().trim()+"%'and h.tproposal.proposalId= t1.proposalId)";
		}
		if(prop.getStutas()!=null && !"".equals(prop.getStutas()) && !"请选择...".equals(prop.getStutas())){
			System.out.println("===="+prop.getStutas());
			if(prop.getStutas().equals("37")){
				hql += " and (t1.stutas = '3' or t1.stutas='7')";
			}else{
				hql += " and t1.stutas = '"+prop.getStutas()+"'";
			}
			
		}
		if(prop.getSubmitFlg()!=null && !"".equals(prop.getSubmitFlg()) && !"请选择...".equals(prop.getSubmitFlg())){
			hql += " and t1.submitFlg = '"+prop.getSubmitFlg()+"'";
		}
		if (prop.getProposalCode() != null && !"".equals(prop.getProposalCode())) {
			hql += " and t1.proposalCode = '"+prop.getProposalCode()+"'";
		}
		if (prop.getTitle() != null && !"".equals(prop.getTitle())) {
			hql += " and t1.title like '%"+prop.getTitle()+"%' ";
		}
		if(prop.getFileMethod()!=null && !"".equals(prop.getFileMethod()) && !"请选择...".equals(prop.getFileMethod())){
			if ("space".equals(prop.getFileMethod())) {
				hql += " and t1.fileMethod is null";
			} else {
				hql += " and t1.fileMethod = '"+prop.getFileMethod()+"'";
			}
			
		}
		if(prop.getFilingFlg()!=null && !"".equals(prop.getFilingFlg())){
			hql += " and t1.filingFlg = '"+prop.getFilingFlg()+"'";
		}
		if(prop.getReplyPass()!=null && !"".equals(prop.getReplyPass()) && !"请选择...".equals(prop.getReplyPass())){
			if ("space".equals(prop.getReplyPass())) {
				hql += " and (t1.replyPass is null)";
			} else {
				hql += " and t1.replyPass ='"+prop.getReplyPass()+"'";
			}
			
		}
		if(prop.getProposalType()!=null && !"".equals(prop.getProposalType()) && !"请选择...".equals(prop.getProposalType())){
			hql += " and t1.proposalType = '"+prop.getProposalType()+"'";
		}
		if(prop.getPropertiesType()!=null && !"".equals(prop.getPropertiesType()) && !"请选择...".equals(prop.getPropertiesType())){
			hql += " and t1.propertiesType = '"+prop.getPropertiesType()+"'";
		}
		if(prop.getFistProposaler()!=null && !"".equals(prop.getFistProposaler()) && !"请选择...".equals(prop.getFistProposaler())){
			hql += " and t1.fistProposaler like '%"+prop.getFistProposaler()+"%'";
		}
		if(prop.getHandleType()!=null && !"".equals(prop.getHandleType()) && !"请选择...".equals(prop.getHandleType())){
			if("tote_all".equals(prop.getHandleType())){
				hql += " and t1.handleType!='"+Constants.CODE_TYPE_BLLX_HB+"'";
			}else if("tote_no".equals(prop.getHandleType())){
				hql += " and t1.handleType!='"+Constants.CODE_TYPE_BLLX_HB+"' and exists(select h.tcompany.companyId from ThandleReply h where h.status!='"+Constants.CODE_TYPE_HFZT_YBF+"' and h.tproposal.proposalId= t1.proposalId)";
			}else if("single_all".equals(prop.getHandleType())){
				hql += " and t1.handleType ='"+Constants.CODE_TYPE_BLLX_DB+"'";
			}else if("single_no".equals(prop.getHandleType())){
				hql += " and t1.handleType='"+Constants.CODE_TYPE_BLLX_DB+"' and exists(select h.tcompany.companyId from ThandleReply h where h.status!='"+Constants.CODE_TYPE_HFZT_YBF+"' and h.tproposal.proposalId= t1.proposalId)";
			}else if("branch_all".equals(prop.getHandleType())){
				hql += " and t1.handleType ='"+Constants.CODE_TYPE_BLLX_FB+"'";
			}else if("branch_no".equals(prop.getHandleType())){
				hql += " and t1.handleType='"+Constants.CODE_TYPE_BLLX_FB+"' and exists(select h.tcompany.companyId from ThandleReply h where h.status!='"+Constants.CODE_TYPE_HFZT_YBF+"' and h.tproposal.proposalId= t1.proposalId)";
			}else if("host_all".equals(prop.getHandleType())){
				hql += " and t1.handleType ='"+Constants.CODE_TYPE_BLLX_HB+"'";
			}else if("host_no".equals(prop.getHandleType())){
				hql += " and t1.handleType='"+Constants.CODE_TYPE_BLLX_HB+"' and exists(select h.tcompany.companyId from ThandleReply h where h.status!='"+Constants.CODE_TYPE_HFZT_YBF+"' and h.tproposal.proposalId= t1.proposalId)";
			}else{
				hql += " and t1.handleType = '"+prop.getHandleType()+"'";
			}
		}
		return hql;
	}
	/**
	 * 
	 *提案信息报表生成
	 * 
	 * @param prop
	 * 
	 * @throws 	Exception
	 */
	@SuppressWarnings("unchecked")
	public String queryreport(Proposal prop,HttpSession httpSession) throws Exception{
		
		List<Proposal> resList=querydatagrid(prop).getRows();
		String fileName=this.writeExcel(resList, httpSession,"/proposal_query.xls");
		
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_REPORT);
		dolog.setInfo( "[提案]提案报表生成");
		this.saveLog(dolog);
		
		return fileName;
		
	}
	/**
	 * 
	 *报表生成
	 * 
	 * @param prop
	 * 
	 * @throws 	Exception
	 */
	public String report(Proposal prop,HttpSession httpSession) throws Exception{
		String secondaryCode=((Seco)httpSession.getAttribute("sessionSeco")).getSecondaryId();
		String hql=" from Tproposal t1 where t1.tsecondary.secondaryId='"+secondaryCode+"' and t1.revokeFlg is null and t1.stutas!='" + Constants.CODE_TYPE_TAZT_YBC + "' and t1.adoptFlg!='"+Constants.CODE_TYPE_YESNO_NO+"'";
		hql=this.addWhere(prop, hql);
		hql+=" order by t1.tsecondary.secondaryId desc,t1.proposalCode";
		List<Tproposal> list=propDAO.find(hql);
		List<Proposal> resList=this.getPropList(list);
		String fileName=this.writeExcel(resList, httpSession,"/proposal_admin.xls");
		
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_REPORT);
		dolog.setInfo( "[提案]提案报表生成");
		this.saveLog(dolog);
		
		return fileName;
	}
	/**
	 * 
	 *市/县委、两办提案报表
	 * 
	 * @param prop
	 * 
	 * @throws 	Exception
	 */
	public String assignreport(Proposal prop,HttpSession httpSession) throws Exception{
		if(httpSession.getAttribute("sessionSeco")==null) return null;
		SessionInfo sessionInfo=(SessionInfo)httpSession.getAttribute("sessionInfo");
		String secondaryCode=((Seco)httpSession.getAttribute("sessionSeco")).getSecondaryId();
		String hql=" from Tproposal t1 where t1.tsecondary.secondaryId='"+secondaryCode+"' and t1.filingFlg='"+Constants.CODE_TYPE_YESNO_YES+"' and t1.analySisUnit='"+sessionInfo.getUserCode()+"'";
		hql=this.addWhere(prop, hql);
		hql+=" order by t1.proposalCode";
		List<Tproposal> list=propDAO.find(hql);
		List<Proposal> resList=this.getPropList(list);
		String fileName=this.writeExcel(resList, httpSession,"/proposal_gov.xls");
		
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_REPORT);
		dolog.setInfo( "[提案]提案报表生成");
		this.saveLog(dolog);
		
		return fileName;
	}
	/**
	 * 
	 *市/县委、两办统计报表
	 * 
	 * @param prop
	 * 
	 * @throws 	Exception
	 */
	public String assigncountreport(HttpSession httpSession) throws Exception{
		String fileName="/proposalcount_gov.xls";
		if(httpSession.getAttribute("sessionSeco")==null) return null;
		SessionInfo sessionInfo=(SessionInfo)httpSession.getAttribute("sessionInfo");
		String secondaryCode=((Seco)httpSession.getAttribute("sessionSeco")).getSecondaryId();
		String hql="";
		if(Constants.DIC_TYPE_YHZB_LDSP.equals(sessionInfo.getUserGroup())){
			hql=" select tp.company_id,tp.num,c.company_name,c.short_name,pr.proposal_id,pr.proposal_code,pr.title,pr.fist_proposaler,pr.proposaler_num,pr.proposal_type,pr.PROPERTIES_TYPE,pr.stress_flg,pr.leader,pr.handle_type from (select t.company_id,count(*) num from thandle_reply t where exists (select proposal_id from tproposal p where p.secondary_id='"+secondaryCode+"' and p.filing_flg='"+Constants.CODE_TYPE_YESNO_YES+"' and p.analysis_unit='"+sessionInfo.getUserCode()+"' and p.proposal_id=t.proposal_id) group by t.company_id) tp" 
				+" left join (select * from  thandle_reply h where exists (select proposal_id from tproposal p where p.secondary_id='"+secondaryCode+"' and p.filing_flg='"+Constants.CODE_TYPE_YESNO_YES+"' and p.analysis_unit='"+sessionInfo.getUserCode()+"' and p.proposal_id=h.proposal_id))th on tp.company_id=th.company_id"
				+" left join tcompany c on tp.company_id=c.company_id"
				+" left join tproposal pr on pr.proposal_id=th.proposal_id"
				+" order by  tp.company_id";
		}else{
			hql=" select tp.company_id,tp.num,c.company_name,c.short_name,pr.proposal_id,pr.proposal_code,pr.title,pr.fist_proposaler,pr.proposaler_num,pr.proposal_type,pr.PROPERTIES_TYPE,pr.stress_flg,pr.leader,pr.handle_type from (select t.company_id,count(*) num from thandle_reply t where exists (select proposal_id from tproposal p where p.secondary_id='"+secondaryCode+"'and p.filing_flg='"+Constants.CODE_TYPE_YESNO_YES+"' and p.proposal_id=t.proposal_id) group by t.company_id) tp" 
				+" left join (select * from  thandle_reply h where exists (select proposal_id from tproposal p where p.secondary_id='"+secondaryCode+"' and p.filing_flg='"+Constants.CODE_TYPE_YESNO_YES+"' and  p.proposal_id=h.proposal_id))th on tp.company_id=th.company_id"
				+" left join tcompany c on tp.company_id=c.company_id"
				+" left join tproposal pr on pr.proposal_id=th.proposal_id"
				+" order by  tp.company_id";
		}
		 List<Object[]> list =(List<Object[]>) propDAO.executeCountSql(hql);
		 
		 	String secondaryName=((Seco)httpSession.getAttribute("sessionSeco")).getSecondayName();
			String usergroup=((SessionInfo)httpSession.getAttribute("sessionInfo")).getUserGroup();
			Properties prop = new Properties();  
			prop.load(this.getClass().getResourceAsStream("/config.properties")); //读取config.properties中的导出
	    	File path=new File(Constants.ROOTPATH + prop.getProperty("exportPath"));
	        if(!path.exists())path.mkdir();//如果路径不存在，则创建
	        //新建文件
	        String filepath=prop.getProperty("exportPath")+fileName;
	        WritableWorkbook book= Workbook.createWorkbook(new File(path+fileName));
	        System.out.println(filepath);
	        //新建工作表
			WritableSheet sheet  =  book.createSheet( "Sheet1 " ,  0 );          
			SheetSettings ss = sheet.getSettings();            
			// ss.setHorizontalFreeze(2);  // 设置列冻结            
			ss.setVerticalFreeze(2);  // 设置行冻结前2行            
			WritableFont font1 =new WritableFont(WritableFont.createFont("微软雅黑"), 10 ,WritableFont.BOLD);            
			WritableFont font2 =new WritableFont(WritableFont.createFont("微软雅黑"), 9 ,WritableFont.NO_BOLD);            
			WritableCellFormat wcf = new WritableCellFormat(font1);           
			WritableCellFormat wcf2 = new WritableCellFormat(font2);            
			//WritableCellFormat wcf3 = new WritableCellFormat(font2);
			//设置样式，字体                         
			// wcf2.setBackground(Colour.LIGHT_ORANGE);            
			wcf.setAlignment(jxl.format.Alignment.CENTRE);  //平行居中            
			wcf.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中           
			//wcf3.setAlignment(jxl.format.Alignment.CENTRE);  //平行居中           
			//wcf3.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中            
			//wcf3.setBackground(Colour.LIGHT_ORANGE);            
			wcf2.setAlignment(jxl.format.Alignment.CENTRE);  //平行居中            
			wcf2.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中             
			sheet.mergeCells( 0 , 0 , 9 , 0 ); // 合并单元格  
			//          在Label对象的构造子中指名单元格位置是第一列第一行(0,0)
			//          以及单元格内容为test            
			jxl.write.Label titleLabel = new Label( 0 , 0 , "政协第"+secondaryName+"委员会提案政府系统承办情况",wcf);
			//          将定义好的单元格添加到工作表中            
			sheet.addCell(titleLabel);            
			sheet.setRowView(1, 480); // 设置第一行的高度  20121111            
			int[] headerArrHight = {40,10,60,40,10,20,20,10,10,10}; //列宽           
			String headerArr[] = {"承办单位","提案编号","案由","第一提案人","提案人数","提案类型","内容分类","重点提案","督办领导","办理方式"};            
			for (int i = 0; i < headerArr.length; i++) {                
				sheet.addCell(new Label( i , 1 , headerArr[i],wcf));                
				sheet.setColumnView(i, headerArrHight[i]);            
			} 
//			if(usergroup.equals(Constants.DIC_TYPE_YHZB_WY)){
//				sheet.addCell(new Label(headerArr.length,1,"是否撤案",wcf));
//			}
			int conut = 2;
			String comanyid=(String) list.get(0)[0];
			int comanyNum=Integer.parseInt(String.valueOf(list.get(0)[1]));
			String comanyName=(String) list.get(0)[3];
			sheet.mergeCells( 0 , conut , 0 ,(conut+comanyNum-1)); // 合并单元格  
			sheet.addCell(new Label( 0 ,conut, (comanyName+"("+comanyNum+")"),wcf2));
			for (int i = 0; i < list.size(); i++) {   
				//循环一个list里面的数据到excel中      
				
				Object[] objs=list.get(i);
				if(!comanyid.equals((String)objs[0])){
					comanyid=(String)objs[0];
					comanyNum=Integer.parseInt(String.valueOf(objs[1]));
					comanyName=(String)objs[3];
					sheet.mergeCells( 0 , conut , 0 ,(conut+comanyNum-1)); // 合并单元格  
					sheet.addCell(new Label( 0 ,conut, (comanyName+"("+comanyNum+")"),wcf2)); 
				}
				sheet.addCell(new Label( 1 , conut ,(String)objs[5],wcf2)); 
				sheet.addCell(new Label( 2 , conut ,(String)objs[6]));   
				if(!"".equals(((String)objs[7]))&&((String)objs[7])!=null){
					sheet.addCell(new Label( 3 , conut ,((String)objs[7]).replace(",","、") ,wcf2));                
				}else{
					sheet.addCell(new Label( 3 , conut ,((String)objs[7]),wcf2));                
				}
				
				
				sheet.addCell(new Label( 4 , conut ,(String)objs[8] ,wcf2)); 
				String propTypeName=this.findDicName("TALX",(String)objs[9]);
				sheet.addCell(new Label( 5 , conut ,propTypeName ,wcf2));   
				String contentTypeName=this.findDicName("TAFL",(String)objs[10]);
				sheet.addCell(new Label( 6 , conut ,contentTypeName ,wcf2)); 
				String StressFlgName=this.findDicName("YESNO",(String)objs[11]);
				sheet.addCell(new Label( 7 , conut ,StressFlgName ,wcf2));               
				sheet.addCell(new Label( 8 , conut ,(String)objs[12],wcf2)); 
				String handleTypeName=this.findDicName("BLLX",(String)objs[13]);
				sheet.addCell(new Label( 9 , conut ,handleTypeName,wcf2)); 
				
				sheet.setRowView(conut,480); // 设置行的高度
				conut++;           
			}           
			//          写入数据并关闭文件           
			book.write();            
			book.close();           
			return filepath;
		 
		//return this.writeExcel(list, httpSession,"/proposal_gov.xls");
	}
	
	/**
	 * 
	 *提案人提案报表生成
	 * 
	 * @param prop
	 * 
	 * @throws 	Exception
	 */
	public String reportwy(Proposal prop,HttpSession httpSession) throws Exception{
		String secondaryCode=((Seco)httpSession.getAttribute("sessionSeco")).getSecondaryId();
		String userCode=((SessionInfo)httpSession.getAttribute("sessionInfo")).getUserCode();
		String hql=" from Tproposal t1 where exists(select t2.tproposal.proposalId from Tproposaler t2 where t2.tcommitteeman.code='"+userCode+"' and t2.hostFlg='1' and t2.tproposal.proposalId=t1.proposalId) and t1.tsecondary.secondaryId='"+secondaryCode+"'";
		if (prop.getProposalCode() != null && !"".equals(prop.getProposalCode())) {
			hql += " and t1.proposalCode = '"+prop.getProposalCode()+"'";
		}
		if (prop.getTitle() != null && !"".equals(prop.getTitle())) {
			hql += " and t1.title like '%"+prop.getTitle()+"%' ";
		}
		hql+=" order by t1.tsecondary.secondaryId desc,t1.proposalCode";
		List<Tproposal> list=propDAO.find(hql);
		List<Proposal> resList=this.getPropList(list);
		return this.writeExcel(resList, httpSession,"/proposal_weiyuan.xls");
	}
	
	/**
	 * 
	 *EXCEL生成
	 * 
	 * @param list
	 * 
	 * @throws 	Exception
	 */
	private String writeExcel(List<Proposal> list,HttpSession httpSession,String fileName)throws Exception{
		String secondaryName=((Seco)httpSession.getAttribute("sessionSeco")).getSecondayName();
		String usergroup=((SessionInfo)httpSession.getAttribute("sessionInfo")).getUserGroup();
		Properties prop = new Properties();   
    	prop.load(this.getClass().getResourceAsStream("/config.properties")); //读取config.properties中的导出
    	File path=new File(Constants.ROOTPATH + prop.getProperty("exportPath"));
        if(!path.exists())path.mkdir();//如果路径不存在，则创建
        //新建文件
        String filepath=prop.getProperty("exportPath")+fileName;
        WritableWorkbook book= Workbook.createWorkbook(new File(path+fileName));
        System.out.println(filepath);
        //新建工作表
		WritableSheet sheet  =  book.createSheet( "Sheet1 " ,  0 );          
		SheetSettings ss = sheet.getSettings();            
		// ss.setHorizontalFreeze(2);  // 设置列冻结            
		ss.setVerticalFreeze(2);  // 设置行冻结前2行            
		WritableFont font1 =new WritableFont(WritableFont.createFont("微软雅黑"), 10 ,WritableFont.BOLD);            
		WritableFont font2 =new WritableFont(WritableFont.createFont("微软雅黑"), 9 ,WritableFont.NO_BOLD);            
		WritableCellFormat wcf = new WritableCellFormat(font1);           
		WritableCellFormat wcf2 = new WritableCellFormat(font2);            
		//WritableCellFormat wcf3 = new WritableCellFormat(font2);
		//设置样式，字体                         
		// wcf2.setBackground(Colour.LIGHT_ORANGE);            
		wcf.setAlignment(jxl.format.Alignment.CENTRE);  //平行居中            
		wcf.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中           
		//wcf3.setAlignment(jxl.format.Alignment.CENTRE);  //平行居中           
		//wcf3.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中            
		//wcf3.setBackground(Colour.LIGHT_ORANGE);            
		wcf2.setAlignment(jxl.format.Alignment.CENTRE);  //平行居中            
		wcf2.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中             
		sheet.mergeCells( 0 , 0 , 22 , 0 ); // 合并单元格  
		//          在Label对象的构造子中指名单元格位置是第一列第一行(0,0)
		//          以及单元格内容为test            
		jxl.write.Label titleLabel = new Label( 1 , 0 , "第"+secondaryName+"委员会提案明细表",wcf);
		//          将定义好的单元格添加到工作表中            
		sheet.addCell(titleLabel);            
		sheet.setRowView(1, 480); // 设置第一行的高度  20121111            
		int[] headerArrHight = {20,10,60,10,10,20,10,10,10,10,10,10,10,10,10,40,40,40,15,15,15,40,15}; //列宽           
		String headerArr[] = {"届次","提案编号","案由","第一提案人","提案人数","提案类型","办理方式","可否公开","可否附议","提案状态","内容分类","优秀提案","重点提案","督办领导","合并提案","建议办理单位","拟定办理单位","办理单位","审查日期","交办日期","要求办结日期","解决程度 ","提案人意见"};            
		for (int i = 0; i < headerArr.length; i++) {                
			sheet.addCell(new Label( i , 1 , headerArr[i],wcf));                
			sheet.setColumnView(i, headerArrHight[i]);            
		} 
//		if(usergroup.equals(Constants.DIC_TYPE_YHZB_WY)){
//			sheet.addCell(new Label(headerArr.length,1,"是否撤案",wcf));
//		}
		int conut = 2;            
		for (int i = 0; i < list.size(); i++) {   
			//循环一个list里面的数据到excel中               
			Proposal p=list.get(i);
			sheet.addCell(new Label( 0 , conut ,p.getSecondayName(),wcf2)); 
			sheet.addCell(new Label( 1 , conut ,p.getProposalCode(),wcf2));
			sheet.addCell(new Label( 2 , conut ,p.getTitle()));                
			sheet.addCell(new Label( 3 , conut ,p.getFistProposaler().replace(",","、") ,wcf2));                
			sheet.addCell(new Label( 4 , conut ,p.getProposalerNum() ,wcf2));                
			sheet.addCell(new Label( 5 , conut ,p.getProposalTypeName() ,wcf2));                
			sheet.addCell(new Label( 6 , conut ,p.getHandleTypeName() ,wcf2));               
			sheet.addCell(new Label( 7 , conut ,p.getWebFlgName(),wcf2));              
			sheet.addCell(new Label( 8 , conut ,p.getSecondFlgName() ,wcf2));
			String statusName=p.getStutasName();
			if("2".equals(p.getStutas())&&"0".equals(p.getFileMethod())){
				statusName="已立案";
			}
			if("2".equals(p.getStutas())&&"1".equals(p.getFileMethod())){
				statusName="未立案";
			}
			if("2".equals(p.getStutas())&&"0".equals(p.getFileMethod())&&"1".equals(p.getFilingFlg())){
				statusName="未交办";
			}
			if("1".equals(p.getFilingFlg())&&"1".equals(p.getSubmitFlg())){
				statusName="已交办";
			}
			if("4".equals(p.getStutas())&& "1".equals(p.getReplyPass())){
				statusName="已答复";
			}
			if(("1".equals(p.getSubmitFlg())&&"7".equals(p.getStutas()))||("1".equals(p.getSubmitFlg())&&"0".equals(p.getReplyPass()))){
				statusName="未答复";
			}
			sheet.addCell(new Label( 9 , conut ,statusName,wcf2));              
			sheet.addCell(new Label( 10 , conut ,p.getPropTypeName() ,wcf2));
			sheet.addCell(new Label( 11 , conut ,p.getExcellentFlgName() ,wcf2));
			sheet.addCell(new Label( 12 , conut ,p.getStressFlgName() ,wcf2));
			sheet.addCell(new Label( 13 , conut ,p.getLeader() ,wcf2));
			sheet.addCell(new Label( 14 , conut ,p.getMergeFlagName() ,wcf2));
			if(!"".equals(p.getUndertakeUnit())&&p.getUndertakeUnit()!=null){
				sheet.addCell(new Label( 15 , conut ,p.getUndertakeUnit().replace(",","、") ,wcf2));
			}else{
				sheet.addCell(new Label( 15 , conut ,p.getUndertakeUnit(),wcf2));
			}
			if(!"".equals(p.getAdviceUnitName())&&p.getAdviceUnitName()!=null){
				sheet.addCell(new Label( 16 , conut ,p.getAdviceUnitName().replace(",","、"),wcf2));
			}else{
				sheet.addCell(new Label( 16 , conut ,p.getAdviceUnitName(),wcf2));
			}
			if(!"".equals(p.getComps())&&p.getComps()!=null){
				sheet.addCell(new Label( 17 , conut ,p.getComps().replace(",","、"),wcf2));
			}else{
				sheet.addCell(new Label( 17 , conut ,p.getComps(),wcf2));
			}
			
			
			sheet.addCell(new Label( 18 , conut ,Util.getInstance().getFormatDate(p.getCheckTime(),"yyyy-MM-dd H:m:s"),wcf2));
			sheet.addCell(new Label( 19 , conut ,Util.getInstance().getFormatDate(p.getSubmitTime(),"yyyy-MM-dd") ,wcf2));
			sheet.addCell(new Label( 20 , conut ,Util.getInstance().getFormatDate(p.getDemandEnddate(),"yyyy-MM-dd") ,wcf2));
			sheet.addCell(new Label( 21 , conut ,p.getSolveHowName() ,wcf2));
			sheet.addCell(new Label( 22 , conut ,p.getCommitteemanOpinionName() ,wcf2));
//			if(usergroup.equals(Constants.DIC_TYPE_YHZB_WY)){
//				sheet.addCell(new Label(headerArr.length,conut,p.getRevokeFlgName() ,wcf2));
//			}
			sheet.setRowView(conut,480); // 设置行的高度
			conut++;           
		}           
		//          写入数据并关闭文件           
		book.write();            
		book.close();           
		return filepath;       
	}
	//重排提案编号,period判断如果平时，则立案编号为顺序向下
	private synchronized void sortCode(Tproposal p, String period) throws Exception {
		String hql="";
		String ext=p.getTsecondary().getExt();	//提案编号前缀
		List<Tproposal> list=null;
		List<Tproposal> newList=new ArrayList<Tproposal>();;
		if(Constants.CODE_TYPE_LAXS_LA.equals(p.getFileMethod())){
			if(Constants.CODE_TYPE_HYPS_PS.equals(period)){
				hql="select max(t.proposal_code) from tproposal t where t.secondary_id='"+p.getTsecondary().getSecondaryId() +"' and t.file_method='"+Constants.CODE_TYPE_LAXS_LA+"' and t.proposal_id!='"+p.getProposalId()+"'";
				List<Object[]> resList=propDAO.executeCountSql(hql);
				System.out.println("reslist======="+resList.get(0));
				String propCode=String.valueOf(resList.get(0));
				int newcode=NumberFormatTools.getInstance().toInteger(propCode);
				newcode+=1;
				System.out.println("code==="+newcode);
				Tproposal tp=propDAO.get(Tproposal.class, p.getProposalId());
				tp.setProposalCode(String.valueOf(newcode));
				propDAO.update(tp);
				hql="from Tproposal t where t.stutas!='" + Constants.CODE_TYPE_TAZT_YBC + "' and t.tsecondary.secondaryId='" + p.getTsecondary().getSecondaryId() + "' and t.adoptFlg!='"+Constants.CODE_TYPE_YESNO_NO+"' and (t.fileMethod is null) and t.proposalId !='"+p.getProposalId()+"' order by to_number(t.propertiesType),t.proposalCode";
				List<Tproposal> templist=propDAO.find(hql);
				for(int i=0;i<templist.size();i++){
					Tproposal t=propDAO.get(Tproposal.class, templist.get(i).getProposalId());
					newcode+=1;
					t.setProposalCode(String.valueOf(newcode));
					propDAO.update(t);
				}
			}else{
				hql="from Tproposal t where t.stutas!='" + Constants.CODE_TYPE_TAZT_YBC + "' and t.tsecondary.secondaryId='" + p.getTsecondary().getSecondaryId() + "' and t.adoptFlg!='"+Constants.CODE_TYPE_YESNO_NO+"' and (t.fileMethod!='" + Constants.CODE_TYPE_LAXS_BLA + "' or t.fileMethod is null) order by t.stressFlg,t.fileMethod,to_number(t.propertiesType),t.typeId,t.proposalCode";
				list=propDAO.find(hql);
				for(int i=0;i<list.size();i++){
					String num=ext + "000".substring(0,(3-String.valueOf(i+1).length())) + (i+1);
					Tproposal tp=propDAO.get(Tproposal.class, list.get(i).getProposalId());
					if(!num.equals(tp.getProposalCode())){
						tp.setProposalCode(num);
						tp.setUpdateFlg(Constants.CODE_TYPE_UPDATE_YES);
						propDAO.update(tp);
					}
				}
			}
		}
		if(Constants.CODE_TYPE_LAXS_BLA.equals(p.getFileMethod())){
			hql="from Tproposal t where t.tsecondary.secondaryId='" + p.getTsecondary().getSecondaryId() + "' and t.fileMethod='" + Constants.CODE_TYPE_LAXS_BLA + "' order by t.proposalCode";
			list=propDAO.find(hql);			//重排不立案提案
			for(int i=0;i<list.size();i++){
				String num="T" +ext+ "000".substring(0,(3-String.valueOf(i+1).length())) + (i+1);
				Tproposal tp=propDAO.get(Tproposal.class, list.get(i).getProposalId());
				if(!num.equals(tp.getProposalCode())){
					tp.setProposalCode(num);
					tp.setUpdateFlg(Constants.CODE_TYPE_UPDATE_YES);
					propDAO.update(tp);
				}
			}
		}
	}
	
	public void submit(Proposal prop,HttpSession httpSession) throws Exception {
		if(prop.getProposalId()==null || "".equals(prop.getProposalId())){
			prop.setProposalId(this.add(prop, httpSession));
		}
		
		Tproposal t=propDAO.get(Tproposal.class, prop.getProposalId());
		String ext=((Seco)httpSession.getAttribute("sessionSeco")).getExt();//存放排序规则前缀，如930001中的93
		String propCode=Generator.getInstance().getProposalCode(ext,getMaxCode(ext,httpSession));
		t.setProposalCode(propCode);
		t.setMeetingFlg(((Seco)httpSession.getAttribute("sessionSeco")).getPeriod());
		t.setStutas(Constants.CODE_TYPE_TAZT_WSC);
		t.setUpdateFlg(Constants.CODE_TYPE_UPDATE_YES);
		t.setAdviceFlg("0");
		propDAO.update(t);
		
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_SUBMIT);
		dolog.setKeyId(t.getProposalId());
		dolog.setInfo( "[提案]提交提案");
		this.saveLog(dolog);
	}
	
}
