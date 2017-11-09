package com.tlzn.service.info.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.org.apache.commons.beanutils.BeanUtils;
import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.Tcommitteeman;
import com.tlzn.model.Tproposal;
import com.tlzn.model.Tproposaler;
import com.tlzn.model.sys.Tdolog;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.info.Proposaler;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.info.SponsorServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Generator;

/**
 * 提案人业务类
 * @author zhangle
 */
@Service("sponsorService")
public class SponsorServiceImpl extends BaseServiceImpl implements SponsorServiceI{

	private BaseDaoI<Tproposaler> sponsorDAO;
	private BaseDaoI<Tproposal> proDAO;
	private BaseDaoI<Tcommitteeman> commDao;
	
	public BaseDaoI<Tproposaler> getSponsorDAO() {
		return sponsorDAO;
	}

	@Autowired
	public void setSponsorDAO(BaseDaoI<Tproposaler> sponsorDAO) {
		this.sponsorDAO = sponsorDAO;
	}

	public BaseDaoI<Tproposal> getProDAO() {
		return proDAO;
	}
	
	@Autowired
	public void setProDAO(BaseDaoI<Tproposal> proDAO) {
		this.proDAO = proDAO;
	}

	public BaseDaoI<Tcommitteeman> getCommDao() {
		return commDao;
	}

	@Autowired
	public void setCommDao(BaseDaoI<Tcommitteeman> commDao) {
		this.commDao = commDao;
	}

	/**
	 * 
	 * 获取提案人
	 * 
	 * @param prop
	 * 
	 * @throws 	Exception
	 * 
	 * @return 		DataGrid返回值
	 */
	public DataGrid datagrid(Proposaler proposaler) throws Exception {
		DataGrid dataGrid= new DataGrid();
		dataGrid.setRows(find(proposaler));
		return dataGrid;
	}
	
	/**
	 * 
		 * 数据查询
		 * 
		 * @param 参数
		 * 
		 * 
		 * @return 		返回值
	 */
	public List<Proposaler> find(Proposaler proposaler)throws Exception {
		String hql=" from Tproposaler t1 where t1.tproposal.proposalId='"+proposaler.getProposalId()+"' order by t1.hostFlg desc ";
		if (proposaler.getSort() != null && proposaler.getOrder() != null) {
			hql +=  proposaler.getSort() + " " + proposaler.getOrder();
		}
		return getCommsFromTComms(sponsorDAO.find(hql));
	}
	/*
	 * 从实体对象列表中复制出新建自造对象
	 */
	private List<Proposaler> getCommsFromTComms(List<Tproposaler> tproposalers) throws Exception {
		List<Proposaler> ads = new ArrayList<Proposaler>();
		if (tproposalers != null && tproposalers.size() > 0) {
			for (Tproposaler t : tproposalers) {
				Proposaler p = new Proposaler();
				Tcommitteeman man=t.getTcommitteeman();
				BeanUtils.copyProperties(p,man);
				BeanUtils.copyProperties(p,t);
				p.setProposalId(t.getTproposal().getProposalId());
				p.setSexName(this.findDicName("SEX", p.getSex()));
				p.setNationName(this.findDicName("NATION", p.getNation()));
				p.setGroupName(this.findDicName("GROUP", p.getGroupCode()));
				p.setPartyName(this.findDicName("PARTY", p.getPartyCode()));
				p.setCircleName(this.findDicName("CIRCLE", p.getCircleCode()));
				p.setEduName(this.findDicName("EDUCATION", p.getEduCode()));
				p.setDegreeName(this.findDicName("DEGREE", p.getDegreeCode()));
				p.setVocationName(this.findDicName("VOCATION", p.getVocation()));
				p.setTitleName(this.findDicName("TITLE", p.getTitle()));
				p.setJobName(this.findDicName("JOB", p.getJob()));
				p.setStatusName(this.findDicName("STATUS", p.getStatus()));
				p.setHostFlgName(this.findDicName("YESNO", p.getHostFlg()));
				ads.add(p);
			}
		}
		return ads;
	}

	public String save(Proposaler proser, String code) throws Exception{
		String id=proser.getProposalId();
		if(id==null || "".equals(id))return "提案号错误！";	
		List<Tproposaler> list=sponsorDAO.find("from Tproposaler t where t.tproposal.proposalId='" + id  + "' and t.tcommitteeman.code='" + code + "'");
		if(list.size()>0){
			if("0".equals(list.get(0).getHostFlg())){
				return "已经附议了该提案，不需要重复附议！";	//已经附议过该提案
			}
			if("1".equals(list.get(0).getHostFlg())){
				return "不能附议自己的提案！";	//不能附议自己的提案
			}
		}
		Tproposaler t=new Tproposaler();
		t.setHostFlg(Constants.CODE_TYPE_YESNO_NO);	//设置为非主提者，0
		t.setTproposal(new Tproposal(proser.getProposalId()));
		t.setTcommitteeman(new Tcommitteeman(code));
		t.setProposalerId(Generator.getInstance().getSponsorNO());
		t.setRemark(proser.getRemark());
		sponsorDAO.save(t);
		proDAO.executeHql("update Tproposal t set t.proposalerNum=t.proposalerNum+1 where t.proposalId='" + id  + "'");
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_ADD);
		dolog.setKeyId(proser.getProposalId());
		dolog.setInfo( "[提案]提案附议人保存");
		this.saveLog(dolog);
		return "附议成功！";
	}
}
