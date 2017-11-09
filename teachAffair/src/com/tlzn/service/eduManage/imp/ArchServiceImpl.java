package com.tlzn.service.eduManage.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.dailywork.Tnotice;
import com.tlzn.model.eduManage.Tarchives;
import com.tlzn.model.eduManage.TawardPenalty;
import com.tlzn.model.eduManage.TbasInfoPers;
import com.tlzn.model.eduManage.TeduExp;
import com.tlzn.model.eduManage.Tfamily;
import com.tlzn.model.eduManage.TfamilyMem;
import com.tlzn.model.eduManage.Tstu;
import com.tlzn.model.eduManage.TworkExp;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.SessionInfo;
import com.tlzn.pageModel.dailywork.Notice;
import com.tlzn.pageModel.eduManage.Archives;
import com.tlzn.pageModel.eduManage.AwardPenalty;
import com.tlzn.pageModel.eduManage.EduExp;
import com.tlzn.pageModel.eduManage.FamilyMem;
import com.tlzn.pageModel.eduManage.Stu;
import com.tlzn.pageModel.eduManage.WorkExp;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.eduManage.ArchServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Generator;
import com.tlzn.util.base.Util;

@Service("archivesService")
public class ArchServiceImpl extends BaseServiceImpl implements ArchServiceI{

	private BaseDaoI<Tarchives> archivesDAO;
	private BaseDaoI<TbasInfoPers> basInfoPersDAO;
	private BaseDaoI<Tfamily> familyDAO;
	private BaseDaoI<TfamilyMem> familyMemDAO;
	private BaseDaoI<TawardPenalty> awardPenaltyDAO;
	private BaseDaoI<TeduExp> eduExpDAO;
	private BaseDaoI<TworkExp> workExpDAO;
	/**
	 * 
	 * 查询单条档案
	 * 
	 * @param archives
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Archives get(String id) throws Exception {
		Util util=new Util();
		Tarchives tarchives= archivesDAO.get(Tarchives.class, id);
		Archives archives = new Archives();
		if(!util.isEmpty(tarchives.getBasInfoPers())) {
			BeanUtils.copyProperties(tarchives.getBasInfoPers(), archives,new String[]{"id","tel","crtTime","uptTime"});
			archives.setBaseInfoId(tarchives.getBasInfoPers().getId());
			archives.setSexName(this.findDicName("SEX",tarchives.getBasInfoPers().getSex()));
			archives.setNationName(this.findDicName("NATION",tarchives.getBasInfoPers().getNation()));
			archives.setPoliticalName(this.findDicName("POLITICAL",tarchives.getBasInfoPers().getPolitical()));
			archives.setResTypeName(this.findDicName("resType",tarchives.getBasInfoPers().getResType()));
			archives.setHealthName(this.findDicName("health",tarchives.getBasInfoPers().getHealth()));
			archives.setCompuLevelName(this.findDicName("compuLevel",tarchives.getBasInfoPers().getCompuLevel()));
			archives.setForeignTypeName(this.findDicName("foreignType",tarchives.getBasInfoPers().getForeignType()));
			
			archives.setBaseInfoCrtTime(tarchives.getBasInfoPers().getCrtTime());
			archives.setBaseInfoUptTime(tarchives.getBasInfoPers().getUptTime());
		}
		if(!util.isEmpty(tarchives.getFamily())) {
			BeanUtils.copyProperties(tarchives.getFamily(), archives,new String[]{"crtTime","uptTime"});
			archives.setEcoStatusName(this.findDicName("ecoStatus",tarchives.getFamily().getEcoStatus()));
			archives.setTeachBackName(this.findDicName("teachBack",tarchives.getFamily().getTeachBack()));
			archives.setBasicLiveName(this.findDicName("YESNO",tarchives.getFamily().getBasicLive()));
			archives.setHelpingName(this.findDicName("YESNO",tarchives.getFamily().getHelping()));
			archives.setFamilyCrtTime(tarchives.getFamily().getCrtTime());
			archives.setFamilyUptTime(tarchives.getFamily().getUptTime());
//			archives.setIntroduce("");
		}
		BeanUtils.copyProperties(tarchives, archives,new String[]{"family","basInfoPers","awardPenaltys","eduExps","workExps"});
//		archives.setSelfAppr("");
//		archives.setRemark("");
		return archives;
	}

	/**
	 * 
	 * 条件查询
	 * 
	 * @param archives
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(Archives archives) throws Exception {
		String hql=" from Tarchives t where 1=1";
		DataGrid j = new DataGrid();
		j.setRows(editArchivess(find(archives,hql)));
		j.setTotal(total(archives,hql));
		return j;
	}
	private List<Archives> editArchivess(List<Tarchives> archivesList) throws Exception {
		Util util=new Util();
		List<Archives> archivess = new ArrayList<Archives>();
		if (archivesList != null && archivesList.size() > 0) {
			for (Tarchives tarchives: archivesList) {
				Archives archives = new Archives();
				if(!util.isEmpty(tarchives.getBasInfoPers())) {
					BeanUtils.copyProperties(tarchives.getBasInfoPers(), archives,new String[]{"id","tel","crtTime","uptTime"});
					archives.setBaseInfoId(tarchives.getBasInfoPers().getId());
					archives.setBaseInfoCrtTime(tarchives.getBasInfoPers().getCrtTime());
					archives.setBaseInfoUptTime(tarchives.getBasInfoPers().getUptTime());
					archives.setSexName(this.findDicName("SEX",tarchives.getBasInfoPers().getSex()));
				}
				if(!util.isEmpty(tarchives.getFamily())) {
					BeanUtils.copyProperties(tarchives.getFamily(), archives,new String[]{"crtTime","uptTime"});
					archives.setFamilyCrtTime(tarchives.getFamily().getCrtTime());
					archives.setFamilyUptTime(tarchives.getFamily().getUptTime());
					archives.setIntroduce("");
					archives.setSpecialStatus("");
				}
				BeanUtils.copyProperties(tarchives, archives,new String[]{"family","basInfoPers","awardPenaltys","eduExps","workExps"});
				archives.setStatusName(this.findDicName("PUBSTATUS",tarchives.getStatus()));
				
				archives.setSelfAppr("");
				archives.setRemark("");
				// if(!util.isEmpty(tarchives.getArchives().getBasInfoPers())) {
//					//取老师姓名
//					archives.setName(tarchives.getArchives().getBasInfoPers().getName());
//				}
				
//				archives.setRankName(this.findDicName("rank",tarchives.getRank()));
//				archives.setMarryName(this.findDicName("MARRY",tarchives.getMarry()));
//				archives.setHasArchivesCertName(this.findDicName("hasNo",tarchives.getHasArchivesCert()));
//				archives.setEducationName(this.findDicName("EDUCATION",tarchives.getEducation()));
//				archives.setDegreeName(this.findDicName("DEGREE",tarchives.getDegree()));
//				archives.setRemark("");
				archivess.add(archives);
			}
		}
		return archivess;
	}
	private List<Tarchives> find(Archives archives,String hql) throws Exception {
		hql = addWhere(archives, hql);
		if (archives.getSort() != null && archives.getOrder() != null) {
			hql += " order by " + archives.getSort() + " " + archives.getOrder();
		}else {
			hql+=" order by t.uptTime desc, t.crtTime desc";
		}
		List<Tarchives> archivesList = archivesDAO.find(hql, archives.getPage(), archives.getRows());
		return archivesList;
	}
	private Long total(Archives archives,String hql) throws Exception {
		hql = addWhere(archives, hql);
		hql = "select count(*) "+hql;
		return archivesDAO.count(hql);
	}
	private String addWhere(Archives archives, String hql) throws Exception {
		Util util=new Util();
		HttpSession httpSession =  ServletActionContext.getRequest().getSession();
		SessionInfo sessionInfo = util.isEmpty(httpSession)?null: (SessionInfo) httpSession.getAttribute("sessionInfo");
		if(!util.isEmpty(sessionInfo)) {
			String loginName=sessionInfo.getLoginName();
			if (!util.isEmpty(loginName) && !Constants.DIC_TYPE_YHZB_ADMIN.equals(sessionInfo.getUserGroup())) {
				hql += " and t.crtUsr='" + sessionInfo.getLoginName() + "' ";
			}
		}
		if(!util.isEmpty(archives.getArchNo())){
			 hql += " and t.archNo like '%"+archives.getArchNo().trim()+"%'";
		}
		if(!util.isEmpty(archives.getName())){
			 hql += " and t.basInfoPers.name like '%"+archives.getName().trim()+"%'";
		}
		if (!util.isEmpty(archives.getSex())) {
			hql += " and t.basInfoPers.sex='" + archives.getSex().trim() + "' ";
		}
		if (!util.isEmpty(archives.getUrgentTel())) {
			 hql += " and t.urgentTel like '%"+archives.getUrgentTel().trim()+"%'";
		}
		if (!util.isEmpty(archives.getGradSchool())) {
			 hql += " and t.gradSchool like '%"+archives.getGradSchool().trim()+"%'";
		}
		if (!util.isEmpty(archives.getStatus())) {
			hql += " and t.status='" + archives.getStatus().trim() + "' ";
		}
		//创建开始时间
		if(!util.isEmpty(archives.getCrtSTime())){
			hql+=" and date_format(t.crtTime,'%Y-%m-%d') >= '"+util.getFormatDate(archives.getCrtSTime(), "yyyy-MM-dd")+"'";
		}
		//创建结束时间
		if(!util.isEmpty(archives.getCrtETime())){
	    	hql+=" and date_format(t.crtTime,'%Y-%m-%d') <= '"+util.getFormatDate(archives.getCrtETime(),"yyyy-MM-dd")+"'";
	    }
		//更新开始时间
		if(!util.isEmpty(archives.getUptSTime())){
			hql+=" and date_format(t.uptTime,'%Y-%m-%d') >= '"+util.getFormatDate(archives.getUptSTime(), "yyyy-MM-dd")+"'";
		}
		//更新结束时间
		if(!util.isEmpty(archives.getUptETime())){
	    	hql+=" and date_format(t.uptTime,'%Y-%m-%d') <= '"+util.getFormatDate(archives.getUptETime(),"yyyy-MM-dd")+"'";
	    }
		if(!util.isEmpty(archives.getRemark())){
			 hql += " and t.remark like '%"+archives.getRemark().trim()+"%'";
		}
		//查询没有分配给学生的档案
		if("1".equals(archives.getSelectNotExist())){
			hql += " and ( not exists (select stuNo from Tstu tt where t.archNo=tt.archives.archNo))";
		}
		return hql;
	}
	
	/**
	 * 保存档案信息
	 */
	/* (non-Javadoc)
	 * @see com.tlzn.service.eduManage.ArchServiceI#save(com.tlzn.pageModel.eduManage.Archives, javax.servlet.http.HttpSession)
	 */
	public String save(Archives archives,HttpSession httpSession) throws Exception {
		Util util=new Util();
		
		//档案
		Tarchives tarchives = new Tarchives();
		BeanUtils.copyProperties(archives, tarchives);
		tarchives.setArchNo(Generator.getInstance().getArchNO());
		tarchives.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES);
		tarchives.setCertFiles(archives.getCertFiles());
		tarchives.setCrtTime(new Date());
		tarchives.setUptTime(new Date());
		SessionInfo sessionInfo = util.isEmpty(httpSession.getAttribute("sessionInfo"))?null: (SessionInfo) httpSession.getAttribute("sessionInfo");
		if(!util.isEmpty(sessionInfo)) {
			String loginName = sessionInfo.getLoginName();
			tarchives.setCrtUsr(loginName);
			tarchives.setUptUsr(loginName);
		}
		
		//人员基本信息
		TbasInfoPers tbasInfoPers = new TbasInfoPers();
		BeanUtils.copyProperties(archives, tbasInfoPers,new String[]{"id","tel","crtTime","uptTime"});
		tbasInfoPers.setId(Generator.getInstance().getBaseInfoNO());
		if(util.isEmpty(archives.getHeight())) tbasInfoPers.setHeight(0);
		if(util.isEmpty(archives.getWeight())) tbasInfoPers.setWeight(0);
		tbasInfoPers.setCrtTime(new Date());
		tbasInfoPers.setUptTime(new Date());
		
		//家庭信息
		Tfamily tfamily = new Tfamily();
		BeanUtils.copyProperties(archives, tfamily, new String[]{"id","crtTime","uptTime"});
		tfamily.setId(Generator.getInstance().getFamilyNO());
		if(util.isEmpty(archives.getPeoNum())) tfamily.setPeoNum(0);
		if(util.isEmpty(archives.getEcoTotal())) tfamily.setEcoTotal(0);
		tfamily.setCrtTime(new Date());
		tfamily.setUptTime(new Date());
		
		//设值并保存
		basInfoPersDAO.save(tbasInfoPers);
		familyDAO.save(tfamily);
		tarchives.setBasInfoPers(tbasInfoPers);
		tarchives.setFamily(tfamily);
		archivesDAO.save(tarchives);
		
		String fmIds= archives.getFmIds();
		String apIds= archives.getApIds();
		String eeIds= archives.getEeIds();
		String weIds= archives.getWeIds();
		
		if(!util.isEmpty(fmIds)) {
			String[] fmArray=fmIds.split(",");
			for (String fm : fmArray) {
				TfamilyMem tfamilyMem = familyMemDAO.get(TfamilyMem.class, fm);
				tfamilyMem.setFamily(tfamily);
				familyMemDAO.update(tfamilyMem);
			}
		}
		if(!util.isEmpty(apIds)) {
			String[] apArray=apIds.split(",");
			for (String ap : apArray) {
				TawardPenalty tawardPenalty = awardPenaltyDAO.get(TawardPenalty.class, ap);
				tawardPenalty.setArchives(tarchives);
				awardPenaltyDAO.update(tawardPenalty);
			}
		}
		if(!util.isEmpty(eeIds)) {
			String[] eeArray=eeIds.split(",");
			for (String ee : eeArray) {
				TeduExp teduExp = eduExpDAO.get(TeduExp.class, ee);
				teduExp.setArchives(tarchives);
				eduExpDAO.update(teduExp);
			}
		}
		if(!util.isEmpty(weIds)) {
			String[] weArray=weIds.split(",");
			for (String we : weArray) {
				TworkExp tworkExp = workExpDAO.get(TworkExp.class, we);
				tworkExp.setArchives(tarchives);
				workExpDAO.update(tworkExp);
			}
		}
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_ARCH, Constants.LOG_TYPE_OPERTYPE_ADD, tarchives.getArchNo(), "[档案]保存档案");
		
		return tarchives.getArchNo();
	}
	/**
	 * 删除档案信息
	 */
	public void del(Archives archives) throws Exception {
		
		Util util=new Util();
		String ids = archives.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					Tarchives tarchives = archivesDAO.get(Tarchives.class,id);
					archivesDAO.delete(tarchives);
					this.saveLog(Constants.LOG_TYPE_CODE_ARCH, Constants.LOG_TYPE_OPERTYPE_DEL, id, "[档案]档案删除（档案ID："+id+"）");
					basInfoPersDAO.delete(tarchives.getBasInfoPers());
					this.saveLog(Constants.LOG_TYPE_CODE_BASINFOPERS, Constants.LOG_TYPE_OPERTYPE_DEL, id, "[人员基本信息]人员基本信息删除（档案ID："+tarchives.getBasInfoPers().getId()+"）");
					familyDAO.delete(tarchives.getFamily());
					this.saveLog(Constants.LOG_TYPE_CODE_FAMILY, Constants.LOG_TYPE_OPERTYPE_DEL, id, "[家庭信息]家庭信息删除（档案ID："+tarchives.getFamily().getId()+"）");
					
				}
			}
		}
	}
	/**
	 * 修改档案信息
	 */
	public void edit(Archives archives,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		String archNo = archives.getArchNo();
		//档案
		Tarchives arch = archivesDAO.get(Tarchives.class, archNo);
		if(!util.isEmpty(arch)) {
			arch.setUrgentTel(archives.getUrgentTel());
			arch.setGradSchool(archives.getGradSchool());
			arch.setSelfAppr(archives.getSelfAppr());
			arch.setRemark(archives.getRemark());
			arch.setCertFiles(archives.getCertFiles());
			arch.setUptTime(new Date());
			SessionInfo sessionInfo = util.isEmpty(httpSession.getAttribute("sessionInfo"))?null: (SessionInfo) httpSession.getAttribute("sessionInfo");
			if(!util.isEmpty(sessionInfo)) {
				String loginName = sessionInfo.getLoginName();
				arch.setUptUsr(loginName);
			}
			
			//基本信息
			if(!util.isEmpty(archives.getBaseInfoId())) {
				TbasInfoPers basInfo = basInfoPersDAO.get(TbasInfoPers.class, archives.getBaseInfoId());
				BeanUtils.copyProperties(archives, basInfo,new String[]{"id","crtTime","uptTime","archives","tel"});
				if(util.isEmpty(archives.getHeight())) basInfo.setHeight(0);
				if(util.isEmpty(archives.getWeight())) basInfo.setWeight(0);
				basInfo.setUptTime(new Date());
				arch.setBasInfoPers(basInfo);
			}
			//家庭信息
			if(!util.isEmpty(archives.getId())) {
				Tfamily family = familyDAO.get(Tfamily.class, archives.getId());
				BeanUtils.copyProperties(archives, family,new String[]{"id","crtTime","uptTime","archives","familyMems"});
				if(util.isEmpty(archives.getPeoNum())) family.setPeoNum(0);
				if(util.isEmpty(archives.getEcoTotal())) family.setEcoTotal(0);
				family.setUptTime(new Date());
				arch.setFamily(family);
			}
			
//			tstu.setDept(stu.getDept());//院、系、部	
//			tstu.setMajor(stu.getMajor());//专业
//			tstu.setClasses(classesDAO.get(Tclasses.class, stu.getClassId()));//所在班级
//			tstu.setArchives(archivesDAO.get(Tarchives.class, stu.getArchNo()));//学生档案
//			tstu.setInTime(stu.getInTime());
//			tstu.setEnSour(stu.getEnSour());//入学来源 
//			tstu.setPriPro(stu.getPriPro());//主要问题
//			tstu.setPlanAfterGrad(stu.getPlanAfterGrad());//毕业以后的打算
//			tstu.getArchives().getBasInfoPers().setPicName(stu.getPicName());//头像
//			tstu.setRemark(stu.getRemark());//备注
//			tstu.setUptTime(new Date());
			
			archivesDAO.update(arch);
//			//生成日志
			this.saveLog(Constants.LOG_TYPE_CODE_ARCH, Constants.LOG_TYPE_OPERTYPE_EDIT, arch.getArchNo(), "[学生]学生档案修改（学生档案ID："+arch.getArchNo()+"）");
		}
	}
public void pub(Archives archives,HttpSession httpSession) throws Exception {
		Util util=new Util();
		String ids = archives.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String archNo=str.trim();
					Tarchives tarchives = archivesDAO.get(Tarchives.class,archNo);
					tarchives.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES); //已发布状态
					tarchives.setUptTime(new Date());
					archivesDAO.update(tarchives);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_ARCH, Constants.LOG_TYPE_OPERTYPE_EDIT, archNo, "[学生档案]学生档案（档案编号："+archNo+"）");
				}
			}
		}
	}
	public void cancelPub(Archives archives) throws Exception {
		Util util=new Util();
		String ids = archives.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String arch=str.trim();
					Tarchives tarchives = archivesDAO.get(Tarchives.class,arch);
					tarchives.setStatus(Constants.CODE_TYPE_PUBSTATUS_NO); //已发布状态
					tarchives.setUptTime(new Date());
					archivesDAO.update(tarchives);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_ARCH, Constants.LOG_TYPE_OPERTYPE_EDIT, arch, "[学生档案]学生档案（档案编号："+arch+"）");
				}
			}
		}
	}
	/**
	 * 查询家庭成员
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid fmDatagrid(Archives archives) throws Exception {
		Util util=new Util();
		DataGrid j = new DataGrid();
		if(!util.isEmpty(archives.getArchNo())) {
			Tarchives tarchives= archivesDAO.get(Tarchives.class, archives.getArchNo());
			//家庭信息
			if(!util.isEmpty(tarchives.getFamily())
				) {
				String familyId=tarchives.getFamily().getId();
				String hql=" from TfamilyMem t where 1=1 and t.family.id='"+familyId+"'";
				hql+=" order by t.crtTime asc";
				List<TfamilyMem> familyMemList = familyMemDAO.find(hql);
				j.setRows(editFamilyMem(familyMemList));
				hql = "select count(*) "+hql;
				j.setTotal(familyMemDAO.count(hql));
			}
		}
		return j;
	}
	private List<FamilyMem> editFamilyMem(List<TfamilyMem> familyMemList) throws Exception {
		List<FamilyMem> familyMems= new ArrayList<FamilyMem>();
		for (TfamilyMem tfamilyMem : familyMemList) {
			FamilyMem fm = new FamilyMem();
			BeanUtils.copyProperties(tfamilyMem, fm);
			fm.setHealthStatusName(this.findDicName("HEALTH", tfamilyMem.getHealthStatus()));
			familyMems.add(fm);
		}
		return familyMems;
	}
	/**
	 * 查询奖惩记录
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid apDatagrid(Archives archives) throws Exception {
		Util util=new Util();
		DataGrid j = new DataGrid();
		if(!util.isEmpty(archives.getArchNo())) {
			Tarchives tarchives= archivesDAO.get(Tarchives.class, archives.getArchNo());
			//奖惩记录
			if(!util.isEmpty(tarchives.getArchNo())
				) {
				String archNo=tarchives.getArchNo();
				String hql=" from TawardPenalty t where 1=1 and t.archives.archNo='"+archNo+"'";
				hql+=" order by t.crtTime asc";
				List<TawardPenalty> apList = awardPenaltyDAO.find(hql);
				j.setRows(editAwardPenalty(apList));
				hql = "select count(*) "+hql;
				j.setTotal(awardPenaltyDAO.count(hql));
			}
		}
		return j;
	}
	private List<AwardPenalty> editAwardPenalty(List<TawardPenalty> apList) throws Exception {
		List<AwardPenalty> awardPenalty= new ArrayList<AwardPenalty>();
		for (TawardPenalty tap : apList) {
			AwardPenalty ap = new AwardPenalty();
			BeanUtils.copyProperties(tap, ap);
			ap.setApAttName(this.findDicName("apAtt", tap.getApAtt()));
			ap.setApMethodName(this.findDicName("apMethod", tap.getApMethod()));
			ap.setApContent("");
			ap.setApRemark("");
			awardPenalty.add(ap);
		}
		return awardPenalty;
	}
	/**
	 * 查询教育经历
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid eeDatagrid(Archives archives) throws Exception {
		Util util=new Util();
		DataGrid j = new DataGrid();
		if(!util.isEmpty(archives.getArchNo())) {
			Tarchives tarchives= archivesDAO.get(Tarchives.class, archives.getArchNo());
			//教育经历
			if(!util.isEmpty(tarchives.getArchNo())
				) {
				String archNo=tarchives.getArchNo();
				String hql=" from TeduExp t where 1=1 and t.archives.archNo='"+archNo+"'";
				hql+=" order by t.crtTime asc";
				List<TeduExp> eduExpList = eduExpDAO.find(hql);
				j.setRows(editEduExp(eduExpList));
				hql = "select count(*) "+hql;
				j.setTotal(eduExpDAO.count(hql));
			}
		}
		return j;
	}
	private List<EduExp> editEduExp(List<TeduExp> eduExpList) throws Exception {
		List<EduExp> eduExps= new ArrayList<EduExp>();
		for (TeduExp teduExp : eduExpList) {
			EduExp ee = new EduExp();
			BeanUtils.copyProperties(teduExp, ee);
			eduExps.add(ee);
		}
		return eduExps;
	}
	/**
	 * 查询工作经历
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid weDatagrid(Archives archives) throws Exception {
		Util util=new Util();
		DataGrid j = new DataGrid();
		if(!util.isEmpty(archives.getArchNo())) {
			Tarchives tarchives= archivesDAO.get(Tarchives.class, archives.getArchNo());
			//奖惩记录
			if(!util.isEmpty(tarchives.getArchNo())) {
				String archNo=tarchives.getArchNo();
				String hql=" from TworkExp t where 1=1 and t.archives.archNo='"+archNo+"'";
				hql+=" order by t.crtTime asc";
				List<TworkExp> weList = workExpDAO.find(hql);
				j.setRows(editWorkExp(weList));
				hql = "select count(*) "+hql;
				j.setTotal(workExpDAO.count(hql));
			}
		}
		return j;
	}
	private List<WorkExp> editWorkExp(List<TworkExp> weList) throws Exception {
		List<WorkExp> workExps= new ArrayList<WorkExp>();
		for (TworkExp twe : weList) {
			WorkExp we = new WorkExp();
			BeanUtils.copyProperties(twe,we);
			we.setWorkContent("");
			workExps.add(we);
		}
		return workExps;
	}
	public BaseDaoI<Tarchives> getArchivesDAO() {
		return archivesDAO;
	}
	
	@Autowired
	public void setArchivesDAO(BaseDaoI<Tarchives> archivesDAO) {
		this.archivesDAO = archivesDAO;
	}

	public BaseDaoI<TfamilyMem> getFamilyMemDAO() {
		return familyMemDAO;
	}
	@Autowired
	public void setFamilyMemDAO(BaseDaoI<TfamilyMem> familyMemDAO) {
		this.familyMemDAO = familyMemDAO;
	}

	public BaseDaoI<TawardPenalty> getAwardPenaltyDAO() {
		return awardPenaltyDAO;
	}
	@Autowired
	public void setAwardPenaltyDAO(BaseDaoI<TawardPenalty> awardPenaltyDAO) {
		this.awardPenaltyDAO = awardPenaltyDAO;
	}

	public BaseDaoI<TeduExp> getEduExpDAO() {
		return eduExpDAO;
	}
	@Autowired
	public void setEduExpDAO(BaseDaoI<TeduExp> eduExpDAO) {
		this.eduExpDAO = eduExpDAO;
	}

	public BaseDaoI<TworkExp> getWorkExpDAO() {
		return workExpDAO;
	}
	@Autowired
	public void setWorkExpDAO(BaseDaoI<TworkExp> workExpDAO) {
		this.workExpDAO = workExpDAO;
	}

	public BaseDaoI<TbasInfoPers> getBasInfoPersDAO() {
		return basInfoPersDAO;
	}
	@Autowired
	public void setBasInfoPersDAO(BaseDaoI<TbasInfoPers> basInfoPersDAO) {
		this.basInfoPersDAO = basInfoPersDAO;
	}

	public BaseDaoI<Tfamily> getFamilyDAO() {
		return familyDAO;
	}
	@Autowired
	public void setFamilyDAO(BaseDaoI<Tfamily> familyDAO) {
		this.familyDAO = familyDAO;
	}
}
