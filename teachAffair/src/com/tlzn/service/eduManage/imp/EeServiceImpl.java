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
import com.tlzn.model.eduManage.TeduExp;
import com.tlzn.model.sys.Tsecondary;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.eduManage.EduExp;
import com.tlzn.pageModel.sys.Seco;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.eduManage.EeServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Generator;
import com.tlzn.util.base.ResourceUtil;
import com.tlzn.util.base.Util;

@Service("eduExpService")
public class EeServiceImpl extends BaseServiceImpl implements EeServiceI{

	private BaseDaoI<TeduExp> eduExpDAO;
	private BaseDaoI<Tarchives> archivesDAO;
	/**
	 * 
	 * 查询单条教育经历
	 * 
	 * @param eduExp
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public EduExp get(String id) throws Exception {
		Util util=new Util();
		TeduExp teduExp= eduExpDAO.get(TeduExp.class, id);
		EduExp eduExp = new EduExp();
		BeanUtils.copyProperties(teduExp, eduExp);
//		eduExp.setArchNo(teduExp.getArchives().getArchNo());
//		eduExp.setCid(teduExp.getTdept().getCid());
//		eduExp.setSex(teduExp.getArchives().getBasInfoPers().getSex());
//		eduExp.setPicName(teduExp.getArchives().getBasInfoPers().getPicName());
//		if(!util.isEmpty(teduExp.getArchives().getBasInfoPers())) {
//			//取老师姓名
//			eduExp.setName(teduExp.getArchives().getBasInfoPers().getName());
//		}
//		eduExp.setSexName(this.findDicName("SEX",teduExp.getArchives().getBasInfoPers().getSex()));
			return eduExp;
	}

	/**
	 * 
	 * 条件查询
	 * 
	 * @param eduExp
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(EduExp eduExp) throws Exception {
		String hql=" from TeduExp t where 1=1";
		DataGrid j = new DataGrid();
		j.setRows(editEduExps(find(eduExp,hql)));
		j.setTotal(total(eduExp,hql));
		return j;
	}
	private List<EduExp> editEduExps(List<TeduExp> eduExpList) throws Exception {
		Util util=new Util();
		List<EduExp> eduExps = new ArrayList<EduExp>();
		if (eduExpList != null && eduExpList.size() > 0) {
			for (TeduExp teduExp: eduExpList) {
				EduExp eduExp = new EduExp();
				BeanUtils.copyProperties(teduExp, eduExp);
				// if(!util.isEmpty(teduExp.getArchives().getBasInfoPers()))
				// {
				// //取老师姓名
				// eduExp.setName(teduExp.getArchives().getBasInfoPers().getName());
				// }
				// eduExp.setSexName(this.findDicName("SEX",teduExp.getArchives().getBasInfoPers().getSex()));
				// eduExp.setRankName(this.findDicName("rank",teduExp.getRank()));
				// eduExp.setMarryName(this.findDicName("MARRY",teduExp.getMarry()));
				// eduExp.setHasEduExpCertName(this.findDicName("hasNo",teduExp.getHasEduExpCert()));
				// eduExp.setEducationName(this.findDicName("EDUCATION",teduExp.getEducation()));
				// eduExp.setDegreeName(this.findDicName("DEGREE",teduExp.getDegree()));
				// eduExp.setRemark("");
				eduExps.add(eduExp);
			}
		}
		return eduExps;
	}
	private List<TeduExp> find(EduExp eduExp,String hql) throws Exception {
		hql = addWhere(eduExp, hql);
		if (eduExp.getSort() != null && eduExp.getOrder() != null) {
			hql += " order by " + eduExp.getSort() + " " + eduExp.getOrder();
		}else {
			hql+=" order by t.uptTime desc, t.crtTime desc";
		}
		List<TeduExp> eduExpList = eduExpDAO.find(hql, eduExp.getPage(), eduExp.getRows());
		return eduExpList;
	}
	private Long total(EduExp eduExp,String hql) throws Exception {
		hql = addWhere(eduExp, hql);
		hql = "select count(*) "+hql;
		return eduExpDAO.count(hql);
	}
	private String addWhere(EduExp eduExp, String hql) throws Exception {
		Util util=new Util();
//		if(!util.isEmpty(eduExp.getId())){
//			 hql += " and t.id like '%"+eduExp.getId().trim()+"%'";
//		}
//		if(!util.isEmpty(eduExp.getName())){
//			 hql += " and t.archives.basInfoPers.name like '%"+eduExp.getName().trim()+"%'";
//		}
//		if (!util.isEmpty(eduExp.getSex())) {
//			hql += " and t.archives.basInfoPers.sex='" + eduExp.getSex().trim() + "' ";
//		}
//		if (!util.isEmpty(eduExp.getCid())) {
//			hql += " and t.tdept.cid='" + eduExp.getCid().trim() + "' ";
//		}
//		if (!util.isEmpty(eduExp.getArchNo())) {
//			hql += " and t.archives.archNo='" + eduExp.getArchNo().trim() + "' ";
//		}
//		if (!util.isEmpty(eduExp.getRank())) {
//			hql += " and t.rank='" + eduExp.getRank().trim() + "' ";
//		}
//		if (!util.isEmpty(eduExp.getHasEduExpCert())) {
//			hql += " and t.hasEduExpCert='" + eduExp.getHasEduExpCert().trim() + "' ";
//		}
//		if (!util.isEmpty(eduExp.getEducation())) {
//			hql += " and t.education='" + eduExp.getEducation().trim() + "' ";
//		}
//		if(!util.isEmpty(eduExp.getGradFrom())){
//			 hql += " and t.gradFrom like '%"+eduExp.getGradFrom().trim()+"%'";
//		}
//		if(!util.isEmpty(eduExp.getMajorName())){
//			 hql += " and t.majorName like '%"+eduExp.getMajorName().trim()+"%'";
//		}
//		if (!util.isEmpty(eduExp.getDegree())) {
//			hql += " and t.degree='" + eduExp.getDegree().trim() + "' ";
//		}
//		if(!util.isEmpty(eduExp.getRemark())){
//			 hql += " and t.remark like '%"+eduExp.getRemark().trim()+"%'";
//		}
//		//录入系统开始时间
//		if(!util.isEmpty(eduExp.getCrtSTime())){
//			hql+=" and date_format(t.crtTime,'%Y-%m-%d') >= '"+util.getFormatDate(eduExp.getCrtSTime(), "yyyy-MM-dd")+"'";
//		}
//		//录入系统结束时间
//		if(!util.isEmpty(eduExp.getCrtETime())){
//	    	hql+=" and date_format(t.crtTime,'%Y-%m-%d') <= '"+util.getFormatDate(eduExp.getCrtETime(),"yyyy-MM-dd")+"'";
//	    }
		return hql;
	}
	
	/**
	 * 保存教育经历信息
	 */
	public EduExp save(EduExp eduExp,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		TeduExp teduExp = new TeduExp();
		BeanUtils.copyProperties(eduExp, teduExp);
		if(!util.isEmpty(eduExp.getArchNo())) {
			Tarchives tarchives= archivesDAO.get(Tarchives.class,eduExp.getArchNo());
			if(!util.isEmpty(tarchives)) {
				teduExp.setArchives(tarchives);
			}
		}
		teduExp.setId(UUID.randomUUID().toString());
		teduExp.setCrtTime(new Date());
		teduExp.setUptTime(new Date());
		eduExpDAO.save(teduExp);
		
		eduExp.setId(teduExp.getId());
		eduExp.setCrtTime(teduExp.getCrtTime());
		eduExp.setUptTime(teduExp.getUptTime());
		
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_ARCH, Constants.LOG_TYPE_OPERTYPE_ADD, teduExp.getId(), "[教育经历]保存教育经历");
		
		return eduExp;
	}
	/**
	 * 新增或更新教育经历信息
	 */
	public String upDateOrAdd(EduExp eduExp,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		TeduExp teduExp = null;
		if(util.isEmpty(eduExp.getId())) {
		//新增
			teduExp = new TeduExp();
			BeanUtils.copyProperties(eduExp, teduExp);
//			teduExp.setId(Generator.getInstance().getEduExpNO());
//			teduExp.setCreatDate(new Date());
		} else {
		//更新
			teduExp=eduExpDAO.get(TeduExp.class, eduExp.getId().trim());
//			Date creatDate = teduExp.getCreatDate();
			BeanUtils.copyProperties(eduExp, teduExp);
//			teduExp.setCreatDate(creatDate);
		}
//		String deptId=((SessionInfo) httpSession.getAttribute("sessionInfo")).getDeptId();
//		teduExp.setPubUnit(deptId);
//		teduExp.setPubDate(new Date());
//		teduExp.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES);
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, teduExp.getId(), "[教育经历]提交教育经历");
		//届次
		Object secoObj=httpSession.getAttribute(ResourceUtil.getSessionSeco());
		Seco seco=secoObj==null?null:((Seco)secoObj);
		if(!util.isEmpty(seco)) {
			Tsecondary ts=new Tsecondary();
			BeanUtils.copyProperties(seco,ts);
		}
		
		eduExpDAO.saveOrUpdate(teduExp);
		
		
		
		return teduExp.getId();
	}
	/**
	 * 删除教育经历信息
	 */
	public void del(EduExp eduExp) throws Exception {
		
		Util util=new Util();
		String ids = eduExp.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					TeduExp teduExp = eduExpDAO.get(TeduExp.class,id);
					eduExpDAO.delete(teduExp);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_ARCH, Constants.LOG_TYPE_OPERTYPE_DEL, id, "[教育经历]教育经历删除（教育经历ID："+id+"）");
				}
			}
		}
	}
	
	/**
	 * 修改教育经历信息
	 */
	public void edit(EduExp eduExp) throws Exception {
		
		String id = eduExp.getId();
			if(id!=null && !"".equals(id)) {
				TeduExp teduExp = eduExpDAO.get(TeduExp.class,id);
				BeanUtils.copyProperties(eduExp, teduExp, new String[]{"crtTime"});
//				Tarchives arch = archivesDAO.get(Tarchives.class, eduExp.getArchNo());
//				teduExp.setArchives(arch);
//				Tdept dept=deptDAO.get(Tdept.class, eduExp.getCid());
//				teduExp.setTdept(dept);
//				teduExp.getArchives().getBasInfoPers().setPicName(eduExp.getPicName());
//				teduExp.setUptTime(new Date());
			eduExpDAO.update(teduExp);
			//生成日志
			this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[教育经历]教育经历修改（教育经历ID："+id+"）");
		}
	}


	public BaseDaoI<TeduExp> getEduExpDAO() {
		return eduExpDAO;
	}
	
	@Autowired
	public void setEduExpDAO(BaseDaoI<TeduExp> eduExpDAO) {
		this.eduExpDAO = eduExpDAO;
	}
	
	public BaseDaoI<Tarchives> getArchivesDAO() {
		return archivesDAO;
	}
	
	@Autowired
	public void setArchivesDAO(BaseDaoI<Tarchives> archivesDAO) {
		this.archivesDAO = archivesDAO;
	}
}
