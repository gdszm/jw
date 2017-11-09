package com.tlzn.service.eduManage.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.eduManage.Tarchives;
import com.tlzn.model.eduManage.TawardPenalty;
import com.tlzn.model.sys.Tsecondary;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.eduManage.AwardPenalty;
import com.tlzn.pageModel.sys.Seco;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.eduManage.ApServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Generator;
import com.tlzn.util.base.ResourceUtil;
import com.tlzn.util.base.Util;

@Service("awardPenaltyService")
public class ApServiceImpl extends BaseServiceImpl implements ApServiceI{

	private BaseDaoI<TawardPenalty> awardPenaltyDAO;
	private BaseDaoI<Tarchives> archDAO;
	/**
	 * 
	 * 查询单条奖惩记录
	 * 
	 * @param awardPenalty
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public AwardPenalty get(String id) throws Exception {
		Util util=new Util();
		TawardPenalty tawardPenalty= awardPenaltyDAO.get(TawardPenalty.class, id);
		AwardPenalty awardPenalty = new AwardPenalty();
		BeanUtils.copyProperties(tawardPenalty, awardPenalty);
//		awardPenalty.setArchNo(tawardPenalty.getArchives().getArchNo());
//		awardPenalty.setCid(tawardPenalty.getTdept().getCid());
//		awardPenalty.setSex(tawardPenalty.getArchives().getBasInfoPers().getSex());
//		awardPenalty.setPicName(tawardPenalty.getArchives().getBasInfoPers().getPicName());
//		if(!util.isEmpty(tawardPenalty.getArchives().getBasInfoPers())) {
//			//取老师姓名
//			awardPenalty.setName(tawardPenalty.getArchives().getBasInfoPers().getName());
//		}
//		awardPenalty.setSexName(this.findDicName("SEX",tawardPenalty.getArchives().getBasInfoPers().getSex()));
			return awardPenalty;
	}

	/**
	 * 
	 * 条件查询
	 * 
	 * @param awardPenalty
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(AwardPenalty awardPenalty) throws Exception {
		String hql=" from TawardPenalty t where 1=1";
		DataGrid j = new DataGrid();
		j.setRows(editAwardPenaltys(find(awardPenalty,hql)));
		j.setTotal(total(awardPenalty,hql));
		return j;
	}
	private List<AwardPenalty> editAwardPenaltys(List<TawardPenalty> awardPenaltyList) throws Exception {
		Util util=new Util();
		List<AwardPenalty> awardPenaltys = new ArrayList<AwardPenalty>();
		if (awardPenaltyList != null && awardPenaltyList.size() > 0) {
			for (TawardPenalty tawardPenalty: awardPenaltyList) {
				AwardPenalty awardPenalty = new AwardPenalty();
				BeanUtils.copyProperties(tawardPenalty, awardPenalty);
				// if(!util.isEmpty(tawardPenalty.getArchives().getBasInfoPers()))
				// {
				// //取老师姓名
				// awardPenalty.setName(tawardPenalty.getArchives().getBasInfoPers().getName());
				// }
				// awardPenalty.setSexName(this.findDicName("SEX",tawardPenalty.getArchives().getBasInfoPers().getSex()));
				// awardPenalty.setRankName(this.findDicName("rank",tawardPenalty.getRank()));
				// awardPenalty.setMarryName(this.findDicName("MARRY",tawardPenalty.getMarry()));
				// awardPenalty.setHasAwardPenaltyCertName(this.findDicName("hasNo",tawardPenalty.getHasAwardPenaltyCert()));
				// awardPenalty.setEducationName(this.findDicName("EDUCATION",tawardPenalty.getEducation()));
				// awardPenalty.setDegreeName(this.findDicName("DEGREE",tawardPenalty.getDegree()));
				// awardPenalty.setRemark("");
				awardPenaltys.add(awardPenalty);
			}
		}
		return awardPenaltys;
	}
	private List<TawardPenalty> find(AwardPenalty awardPenalty,String hql) throws Exception {
		hql = addWhere(awardPenalty, hql);
		if (awardPenalty.getSort() != null && awardPenalty.getOrder() != null) {
			hql += " order by " + awardPenalty.getSort() + " " + awardPenalty.getOrder();
		}else {
			hql+=" order by t.uptTime desc, t.crtTime desc";
		}
		List<TawardPenalty> awardPenaltyList = awardPenaltyDAO.find(hql, awardPenalty.getPage(), awardPenalty.getRows());
		return awardPenaltyList;
	}
	private Long total(AwardPenalty awardPenalty,String hql) throws Exception {
		hql = addWhere(awardPenalty, hql);
		hql = "select count(*) "+hql;
		return awardPenaltyDAO.count(hql);
	}
	private String addWhere(AwardPenalty awardPenalty, String hql) throws Exception {
		Util util=new Util();
//		if(!util.isEmpty(awardPenalty.getId())){
//			 hql += " and t.id like '%"+awardPenalty.getId().trim()+"%'";
//		}
//		if(!util.isEmpty(awardPenalty.getName())){
//			 hql += " and t.archives.basInfoPers.name like '%"+awardPenalty.getName().trim()+"%'";
//		}
//		if (!util.isEmpty(awardPenalty.getSex())) {
//			hql += " and t.archives.basInfoPers.sex='" + awardPenalty.getSex().trim() + "' ";
//		}
//		if (!util.isEmpty(awardPenalty.getCid())) {
//			hql += " and t.tdept.cid='" + awardPenalty.getCid().trim() + "' ";
//		}
//		if (!util.isEmpty(awardPenalty.getArchNo())) {
//			hql += " and t.archives.archNo='" + awardPenalty.getArchNo().trim() + "' ";
//		}
//		if (!util.isEmpty(awardPenalty.getRank())) {
//			hql += " and t.rank='" + awardPenalty.getRank().trim() + "' ";
//		}
//		if (!util.isEmpty(awardPenalty.getHasAwardPenaltyCert())) {
//			hql += " and t.hasAwardPenaltyCert='" + awardPenalty.getHasAwardPenaltyCert().trim() + "' ";
//		}
//		if (!util.isEmpty(awardPenalty.getEducation())) {
//			hql += " and t.education='" + awardPenalty.getEducation().trim() + "' ";
//		}
//		if(!util.isEmpty(awardPenalty.getGradFrom())){
//			 hql += " and t.gradFrom like '%"+awardPenalty.getGradFrom().trim()+"%'";
//		}
//		if(!util.isEmpty(awardPenalty.getMajorName())){
//			 hql += " and t.majorName like '%"+awardPenalty.getMajorName().trim()+"%'";
//		}
//		if (!util.isEmpty(awardPenalty.getDegree())) {
//			hql += " and t.degree='" + awardPenalty.getDegree().trim() + "' ";
//		}
//		if(!util.isEmpty(awardPenalty.getRemark())){
//			 hql += " and t.remark like '%"+awardPenalty.getRemark().trim()+"%'";
//		}
//		//录入系统开始时间
//		if(!util.isEmpty(awardPenalty.getCrtSTime())){
//			hql+=" and date_format(t.crtTime,'%Y-%m-%d') >= '"+util.getFormatDate(awardPenalty.getCrtSTime(), "yyyy-MM-dd")+"'";
//		}
//		//录入系统结束时间
//		if(!util.isEmpty(awardPenalty.getCrtETime())){
//	    	hql+=" and date_format(t.crtTime,'%Y-%m-%d') <= '"+util.getFormatDate(awardPenalty.getCrtETime(),"yyyy-MM-dd")+"'";
//	    }
		return hql;
	}
	
	/**
	 * 保存奖惩记录信息
	 */
	public AwardPenalty save(AwardPenalty awardPenalty,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		TawardPenalty tawardPenalty = new TawardPenalty();
		BeanUtils.copyProperties(awardPenalty, tawardPenalty);
		if(!util.isEmpty(awardPenalty.getArchNo())) {
			Tarchives tarchives= archDAO.get(Tarchives.class,awardPenalty.getArchNo());
			if(!util.isEmpty(tarchives)) {
				tawardPenalty.setArchives(tarchives);
			}
		}
		tawardPenalty.setId(UUID.randomUUID().toString());
		tawardPenalty.setCrtTime(new Date());
		tawardPenalty.setUptTime(new Date());
		
		awardPenaltyDAO.save(tawardPenalty);
		awardPenalty.setId(tawardPenalty.getId());
		awardPenalty.setCrtTime(tawardPenalty.getCrtTime());
		awardPenalty.setUptTime(tawardPenalty.getUptTime());
		
		//奖惩性质（1：奖励 2：惩罚）
		awardPenalty.setApAttName(this.findDicName("apAtt",tawardPenalty.getApAtt()));
		//奖惩方式
		//11：表扬 12：嘉奖 13：奖金  14：记功 15：提职 16：晋级
		//21：口头警告 22：书面警告 23：返款 24：赔偿 25：降级 26：降薪 27：解聘 28：其他惩罚
		awardPenalty.setApMethodName(this.findDicName("apMethod",tawardPenalty.getApMethod()));
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_ARCH, Constants.LOG_TYPE_OPERTYPE_ADD, tawardPenalty.getId(), "[奖惩记录]保存奖惩记录");
		
		return awardPenalty;
	}
	/**
	 * 新增或更新奖惩记录信息
	 */
	public String upDateOrAdd(AwardPenalty awardPenalty,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		TawardPenalty tawardPenalty = null;
		if(util.isEmpty(awardPenalty.getId())) {
		//新增
			tawardPenalty = new TawardPenalty();
			BeanUtils.copyProperties(awardPenalty, tawardPenalty);
//			tawardPenalty.setId(Generator.getInstance().getAwardPenaltyNO());
//			tawardPenalty.setCreatDate(new Date());
		} else {
		//更新
			tawardPenalty=awardPenaltyDAO.get(TawardPenalty.class, awardPenalty.getId().trim());
//			Date creatDate = tawardPenalty.getCreatDate();
			BeanUtils.copyProperties(awardPenalty, tawardPenalty);
//			tawardPenalty.setCreatDate(creatDate);
		}
//		String deptId=((SessionInfo) httpSession.getAttribute("sessionInfo")).getDeptId();
//		tawardPenalty.setPubUnit(deptId);
//		tawardPenalty.setPubDate(new Date());
//		tawardPenalty.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES);
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, tawardPenalty.getId(), "[奖惩记录]提交奖惩记录");
		//届次
		Object secoObj=httpSession.getAttribute(ResourceUtil.getSessionSeco());
		Seco seco=secoObj==null?null:((Seco)secoObj);
		if(!util.isEmpty(seco)) {
			Tsecondary ts=new Tsecondary();
			BeanUtils.copyProperties(seco,ts);
		}
		
		awardPenaltyDAO.saveOrUpdate(tawardPenalty);
		
		
		
		return tawardPenalty.getId();
	}
	/**
	 * 删除奖惩记录信息
	 */
	public void del(AwardPenalty awardPenalty) throws Exception {
		Util util=new Util();
		String ids = awardPenalty.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					TawardPenalty tawardPenalty = awardPenaltyDAO.get(TawardPenalty.class,id);
					awardPenaltyDAO.delete(tawardPenalty);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_ARCH, Constants.LOG_TYPE_OPERTYPE_DEL, id, "[奖惩记录]奖惩记录删除（奖惩记录ID："+id+"）");
				}
			}
		}
	}
	
	/**
	 * 修改奖惩记录信息
	 */
	public void edit(AwardPenalty awardPenalty) throws Exception {
		
		String id = awardPenalty.getId();
			if(id!=null && !"".equals(id)) {
				TawardPenalty tawardPenalty = awardPenaltyDAO.get(TawardPenalty.class,id);
				BeanUtils.copyProperties(awardPenalty, tawardPenalty, new String[]{"crtTime"});
//				Tarchives arch = archivesDAO.get(Tarchives.class, awardPenalty.getArchNo());
//				tawardPenalty.setArchives(arch);
//				Tdept dept=deptDAO.get(Tdept.class, awardPenalty.getCid());
//				tawardPenalty.setTdept(dept);
//				tawardPenalty.getArchives().getBasInfoPers().setPicName(awardPenalty.getPicName());
//				tawardPenalty.setUptTime(new Date());
			awardPenaltyDAO.update(tawardPenalty);
			//生成日志
			this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[奖惩记录]奖惩记录修改（奖惩记录ID："+id+"）");
		}
	}

	public BaseDaoI<TawardPenalty> getAwardPenaltyDAO() {
		return awardPenaltyDAO;
	}
	
	
	@Autowired
	public void setAwardPenaltyDAO(BaseDaoI<TawardPenalty> awardPenaltyDAO) {
		this.awardPenaltyDAO = awardPenaltyDAO;
	}

	public BaseDaoI<Tarchives> getArchDAO() {
		return archDAO;
	}
	@Autowired
	public void setArchDAO(BaseDaoI<Tarchives> archDAO) {
		this.archDAO = archDAO;
	}
}
