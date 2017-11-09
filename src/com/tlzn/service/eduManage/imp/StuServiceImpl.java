package com.tlzn.service.eduManage.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.eduManage.Tarchives;
import com.tlzn.model.eduManage.TawardPenalty;
import com.tlzn.model.eduManage.TbasInfoPers;
import com.tlzn.model.eduManage.Tclasses;
import com.tlzn.model.eduManage.TeduExp;
import com.tlzn.model.eduManage.Tfamily;
import com.tlzn.model.eduManage.TfamilyMem;
import com.tlzn.model.eduManage.Tstu;
import com.tlzn.model.eduManage.TworkExp;
import com.tlzn.model.sys.Tsecondary;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.SessionInfo;
import com.tlzn.pageModel.eduManage.AwardPenalty;
import com.tlzn.pageModel.eduManage.EduExp;
import com.tlzn.pageModel.eduManage.FamilyMem;
import com.tlzn.pageModel.eduManage.Stu;
import com.tlzn.pageModel.eduManage.WorkExp;
import com.tlzn.pageModel.sys.Seco;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.eduManage.StuServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Generator;
import com.tlzn.util.base.ResourceUtil;
import com.tlzn.util.base.Util;

@Service("stuService")
public class StuServiceImpl extends BaseServiceImpl implements StuServiceI{

	private BaseDaoI<Tstu> stuDAO;
	private BaseDaoI<Tclasses> classesDAO;
	private BaseDaoI<Tarchives> archivesDAO;
	private BaseDaoI<TbasInfoPers> basInfoPersDAO;
	private BaseDaoI<Tfamily> familyDAO;
	private BaseDaoI<TfamilyMem> familyMemDAO;
	private BaseDaoI<TawardPenalty> awardPenaltyDAO;
	private BaseDaoI<TeduExp> eduExpDAO;
	private BaseDaoI<TworkExp> workExpDAO;
	/**
	 * 
	 * 查询单条学生
	 * 
	 * @param stu
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Stu get(String id) throws Exception {
		Tstu tstu= stuDAO.get(Tstu.class, id);
		Stu stu = new Stu();
		BeanUtils.copyProperties(tstu.getArchives().getBasInfoPers(), stu);
		BeanUtils.copyProperties(tstu, stu);
		stu.setClassId(tstu.getClasses().getId());
		stu.setClassName((tstu.getClasses().getClassName()));
		stu.setArchNo(tstu.getArchives().getArchNo());
		stu.setSexName(this.findDicName("SEX", tstu.getArchives().getBasInfoPers().getSex()));
		stu.setDeptName(this.findDicName("yxb", tstu.getDept()));
		stu.setMajorName(this.findDicName("major", tstu.getMajor()));
		stu.setEnSourName(this.findDicName("enSour", tstu.getEnSour()));
		stu.setPriProName(this.findDicName("priPro", tstu.getPriPro()));
		return stu;
	}
	/**
	 * 
	 * 查询单条学生
	 * 
	 * @param stu
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Stu getStuArch(String id) throws Exception {
		Util util=new Util();
		Tstu tstu= stuDAO.get(Tstu.class, id);
		Stu stu = new Stu();
		//基本信息
		if(!util.isEmpty(tstu) && 
			!util.isEmpty(tstu.getArchives()) && 
			!util.isEmpty(tstu.getArchives().getBasInfoPers())) {
			TbasInfoPers basInfoPers = tstu.getArchives().getBasInfoPers();
			BeanUtils.copyProperties(basInfoPers, stu);
			
			stu.setBasInfoPersId(basInfoPers.getId());
//			stu.setBasInfoPersTel(basInfoPers.getTel());
			stu.setBasInfoPersCrtTime(basInfoPers.getCrtTime());
			stu.setBasInfoPersUptTime(basInfoPers.getUptTime());
			stu.setSexName(this.findDicName("SEX", basInfoPers.getSex()));
			stu.setPoliticalName(this.findDicName("POLITICAL", basInfoPers.getPolitical()));
			stu.setNationName(this.findDicName("NATION", basInfoPers.getNation()));
			stu.setHealthName(this.findDicName("HEALTH", basInfoPers.getHealth()));
			stu.setForeignTypeName(this.findDicName("foreignType", basInfoPers.getForeignType()));
		}
		//家庭信息
		if(!util.isEmpty(tstu) && 
			!util.isEmpty(tstu.getArchives()) && 
			!util.isEmpty(tstu.getArchives().getFamily())
			) {
			Tfamily family=tstu.getArchives().getFamily();
			BeanUtils.copyProperties(family, stu);
			stu.setFamilyId(family.getId());
			stu.setFamilyTel(family.getTel());
			stu.setFamilyCrtTime(family.getCrtTime());
			stu.setFamilyUptTime(family.getUptTime());
		}
		//家庭成员信息
		if(!util.isEmpty(tstu) && 
				!util.isEmpty(tstu.getArchives()) && 
				!util.isEmpty(tstu.getArchives().getFamily()) &&
				!util.isEmpty(tstu.getArchives().getFamily().getFamilyMems())
				) {
				String familymemId="";
				Set<TfamilyMem> familyMems=tstu.getArchives().getFamily().getFamilyMems();
				for (TfamilyMem tfamilyMem : familyMems) {
					familymemId+=(tfamilyMem.getId()+",");
				}
				stu.setFamilymemId(familymemId);
			} else stu.setFamilymemId("");
		//教育经历
		//奖惩记录
		if(!util.isEmpty(tstu) && 
			!util.isEmpty(tstu.getArchives()) && 
			!util.isEmpty(tstu.getArchives().getAwardPenaltys())
			) {
			String awardPenaltyIds="";
			Set<TawardPenalty> awardPenaltys=tstu.getArchives().getAwardPenaltys();
			for (TawardPenalty tawardPenalty : awardPenaltys) {
				awardPenaltyIds+=(tawardPenalty.getId()+",");
			}
			stu.setAwardPenaltyIds(awardPenaltyIds);
		} else stu.setAwardPenaltyIds("");
		//教育经历
		if(!util.isEmpty(tstu) && 
			!util.isEmpty(tstu.getArchives()) && 
			!util.isEmpty(tstu.getArchives().getEduExps())
			) {
			String eduExpIds="";
			Set<TeduExp> eduExps=tstu.getArchives().getEduExps();
			for (TeduExp teduExp : eduExps) {
				eduExpIds+=(teduExp.getId()+",");
			}
			stu.setEduExpIds(eduExpIds);
		} else stu.setEduExpIds("");
		//工作经历
		if(!util.isEmpty(tstu) && 
			!util.isEmpty(tstu.getArchives()) && 
			!util.isEmpty(tstu.getArchives().getWorkExps())
			) {
			String workExpIds="";
			Set<TworkExp> workExps=tstu.getArchives().getWorkExps();
			for (TworkExp tworkExp : workExps) {
				workExpIds+=(tworkExp.getId()+",");
			}
			stu.setWorkExpIds(workExpIds);
		} else stu.setWorkExpIds("");
		//班级
		if(!util.isEmpty(tstu) && 
			!util.isEmpty(tstu.getClasses())){
			stu.setClassId(tstu.getClasses().getId());
			stu.setClassName((tstu.getClasses().getClassName()));
		}
		//学生信息
		if(!util.isEmpty(tstu)){
			stu.setDeptName(this.findDicName("yxb", tstu.getDept()));
			stu.setMajorName(this.findDicName("major", tstu.getMajor()));
			stu.setInTimeString(tstu.getInTime());
			stu.setEnSourName(this.findDicName("enSour", tstu.getPriPro()));
			stu.setPriProName(this.findDicName("priPro", tstu.getPriPro()));
		}
		//档案信息
		if(!util.isEmpty(tstu) && 
			!util.isEmpty(tstu.getArchives())){
			stu.setArchNo(tstu.getArchives().getArchNo());
			stu.setArchUptTime(tstu.getArchives().getUptTime());
			stu.setUrgentTel(tstu.getArchives().getUrgentTel());
			stu.setGradSchool(tstu.getArchives().getGradSchool());
			stu.setSelfAppr(tstu.getArchives().getSelfAppr());
			stu.setArchRemark(tstu.getArchives().getRemark());
		}
		if(!util.isEmpty(tstu)){
			BeanUtils.copyProperties(tstu, stu, new String[] {"remark","crtTime","uptTime"});
			stu.setDeptName(this.findDicName("yxb", tstu.getDept()));
			stu.setEnSourName(this.findDicName("enSour", tstu.getEnSour()));
		}
		return stu;
	}
	/**
	 * 
	 * 条件查询
	 * 
	 * @param stu
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(Stu stu) throws Exception {
		String hql=" from Tstu t where 1=1";
		DataGrid j = new DataGrid();
		j.setRows(editStus(find(stu,hql)));
		j.setTotal(total(stu,hql));
		return j;
	}
	private List<Stu> editStus(List<Tstu> stuList) throws Exception {
		Util util=new Util();
		List<Stu> stus = new ArrayList<Stu>();
		if (stuList != null && stuList.size() > 0) {
			for (Tstu tstu: stuList) {
				Stu stu= new Stu();
				editStu(tstu,stu);
				stus.add(stu);
			}
		}
		return stus;
	}
	private Stu editStu(Tstu tstu,Stu stu) throws Exception {
		Util util=new Util();
		BeanUtils.copyProperties(tstu, stu);
		BeanUtils.copyProperties(tstu.getArchives().getBasInfoPers(), stu);
		stu.setDept(this.findDicName("yxb", tstu.getDept()));
		stu.setMajor(this.findDicName("major", tstu.getMajor()));
		stu.setEnSour(this.findDicName("enSour", tstu.getEnSour()));
		stu.setPriPro(this.findDicName("priPro", tstu.getPriPro()));
		stu.setCompuLevel(this.findDicName("compuLevel", tstu.getArchives().getBasInfoPers().getCompuLevel()));
		stu.setForeignType(this.findDicName("foreignType", tstu.getArchives().getBasInfoPers().getForeignType()));
		stu.setSex(this.findDicName("SEX", tstu.getArchives().getBasInfoPers().getSex()));
		stu.setPriPro(this.findDicName("priPro", tstu.getPriPro()));
		stu.setClassName(tstu.getClasses().getClassName());
		stu.setArchNo(tstu.getArchives().getArchNo());
		stu.setHeight(0);
		stu.setWeight(0);
		stu.setPeoNum(0);
		stu.setEcoTotal(0);
		stu.setRemark("");
		return stu;
	}
	/**
	 * 查询家庭成员
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid fmDatagrid(Stu stu) throws Exception {
		Util util=new Util();
		DataGrid j = new DataGrid();
		if(!util.isEmpty(stu.getStuNo())) {
			Tstu tstu= stuDAO.get(Tstu.class, stu.getStuNo());
			//家庭信息
			if(!util.isEmpty(tstu) && 
				!util.isEmpty(tstu.getArchives()) && 
				!util.isEmpty(tstu.getArchives().getFamily())
				) {
				String familyId=tstu.getArchives().getFamily().getId();
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
	public DataGrid apDatagrid(Stu stu) throws Exception {
		Util util=new Util();
		DataGrid j = new DataGrid();
		if(!util.isEmpty(stu.getStuNo())) {
			Tstu tstu= stuDAO.get(Tstu.class, stu.getStuNo());
			//奖惩记录
			if(!util.isEmpty(tstu) && 
				!util.isEmpty(tstu.getArchives()) && 
				!util.isEmpty(tstu.getArchives().getArchNo())
				) {
				String archNo=tstu.getArchives().getArchNo();
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
	public DataGrid eeDatagrid(Stu stu) throws Exception {
		Util util=new Util();
		DataGrid j = new DataGrid();
		if(!util.isEmpty(stu.getStuNo())) {
			Tstu tstu= stuDAO.get(Tstu.class, stu.getStuNo());
			//教育经历
			if(!util.isEmpty(tstu) && 
				!util.isEmpty(tstu.getArchives()) && 
				!util.isEmpty(tstu.getArchives().getArchNo())
				) {
				String archNo=tstu.getArchives().getArchNo();
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
	public DataGrid weDatagrid(Stu stu) throws Exception {
		Util util=new Util();
		DataGrid j = new DataGrid();
		if(!util.isEmpty(stu.getStuNo())) {
			Tstu tstu= stuDAO.get(Tstu.class, stu.getStuNo());
			//奖惩记录
			if(!util.isEmpty(tstu) && 
				!util.isEmpty(tstu.getArchives()) && 
				!util.isEmpty(tstu.getArchives().getArchNo())
				) {
				String archNo=tstu.getArchives().getArchNo();
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
	/**
	 * 最新5条学生
	 */
	public List<Stu> getNewStu() throws Exception {
		String hql="from Tstu t where t.status='"+Constants.CODE_TYPE_PUBSTATUS_YES+"' order by t.pubDate desc,t.creatDate desc";
		List<Tstu> stuList = stuDAO.find(hql, 1, 5);
		return editStus(stuList);
	}
	
	
	private List<Tstu> find(Stu stu,String hql) throws Exception {
		hql = addWhere(stu, hql);
		if (stu.getSort() != null && stu.getOrder() != null) {
			hql += " order by " + stu.getSort() + " " + stu.getOrder();
		}else {
			hql+=" order by t.uptTime desc, t.crtTime desc";
		}
		List<Tstu> stuList = stuDAO.find(hql, stu.getPage(), stu.getRows());
		return stuList;
	}
	private Long total(Stu stu,String hql) throws Exception {
		hql = addWhere(stu, hql);
		hql = "select count(*) "+hql;
		return stuDAO.count(hql);
	}
	private String addWhere(Stu stu, String hql) throws Exception {
		Util util=new Util();
		HttpSession httpSession =  ServletActionContext.getRequest().getSession();
		SessionInfo sessionInfo = util.isEmpty(httpSession)?null: (SessionInfo) httpSession.getAttribute("sessionInfo");
		if(!util.isEmpty(sessionInfo)) {
			String loginName=sessionInfo.getLoginName();
			if (!util.isEmpty(loginName) && !Constants.DIC_TYPE_YHZB_ADMIN.equals(sessionInfo.getUserGroup())) {
				hql += " and t.crtUsr='" + sessionInfo.getLoginName() + "' ";
			}
		}
		//学号
		if (!util.isEmpty(stu.getStuNo())) {
			hql += " and t.stuNo='" + stu.getStuNo() + "' ";
		}
		//学生姓名
		if(!util.isEmpty(stu.getName())){
			hql += " and t.archives.basInfoPers.name like '%"+stu.getName().trim()+"%'";
		}
		//院、系、部
		if (!util.isEmpty(stu.getDept())) {
			hql += " and t.dept='" + stu.getDept() + "' ";
		}
		//专业
		if (!util.isEmpty(stu.getMajor())) {
			hql += " and t.major='" + stu.getMajor() + "' ";
		}
		//班级Id
		if(!util.isEmpty(stu.getClassId())){
			hql += " and t.classes.id='"+stu.getClassId().trim()+"'";
		}
		//班级名称
		if(!util.isEmpty(stu.getClassName())){
			hql += " and t.classes.className like '%"+stu.getClassName().trim()+"%'";
		}
		//入学开始时间
		if(stu.getInSTime()!=null){
			hql+=" and date_format(t.inTime,'%Y-%m-%d') >= '"+util.getFormatDate(stu.getInSTime(), "yyyy-MM-dd")+"'";
		}
		//入学结束时间
	    if(stu.getInETime()!=null){
	    	hql+=" and date_format(t.inTime,'%Y-%m-%d') <= '"+util.getFormatDate(stu.getInETime(),"yyyy-MM-dd")+"'";
	    }
		//入学来源
		if (!util.isEmpty(stu.getEnSour())) {
			hql += " and t.enSour='" + stu.getEnSour() + "' ";
		}
		//主要问题
		if (!util.isEmpty(stu.getPriPro())) {
			hql += " and t.priPro='" + stu.getPriPro() + "' ";
		}
		//备注
		if(!util.isEmpty(stu.getRemark())){
			hql += " and t.remark like '%"+stu.getRemark().trim()+"%'";
		}
		return hql;
	}
	/**
	 * 保存空学生档案
	 */
	public Stu saveBlankStuArch() throws Exception {
		
		Util util=new Util();
		Tarchives tarch = new Tarchives();
		tarch.setArchNo(UUID.randomUUID().toString());
		Tfamily family = new Tfamily();
		family.setId(Generator.getInstance().getFamilyNO());
		family.setCrtTime(new Date());
		family.setUptTime(new Date());
		tarch.setFamily(family);
		TbasInfoPers basInfoPers = new TbasInfoPers();
		basInfoPers.setId(Generator.getInstance().getBaseInfoNO());
		basInfoPers.setCrtTime(new Date());
		basInfoPers.setUptTime(new Date());
		tarch.setBasInfoPers(basInfoPers);
		tarch.setCrtTime(new Date());
		tarch.setUptTime(new Date());
		
		archivesDAO.save(tarch);
		
		Tstu tstu = new Tstu();
		Stu stu= this.getStuArch(tarch.getArchNo());
		
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_ARCH, Constants.LOG_TYPE_OPERTYPE_ADD, tstu.getStuNo(), "[档案]保存空学生档案");
		
		return stu;
	}
	/**
	 * 保存学生信息
	 */
	public String save(Stu stu,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		Tstu tstu = new Tstu();
		BeanUtils.copyProperties(stu, tstu);
		tstu.setStuNo(Generator.getInstance().getStuNO());
		if(!util.isEmpty(stu.getClassId())) {
			Tclasses tclasses = classesDAO.get(Tclasses.class, stu.getClassId());
			tstu.setClasses(tclasses);
		}
		if(!util.isEmpty(stu.getArchNo())) {
			Tarchives tarchives = archivesDAO.get(Tarchives.class, stu.getArchNo());
			tstu.setArchives(tarchives);
		}
		tstu.setCrtTime(new Date());
		tstu.setUptTime(new Date());
		SessionInfo sessionInfo = util.isEmpty(httpSession.getAttribute("sessionInfo"))?null: (SessionInfo) httpSession.getAttribute("sessionInfo");
		if(!util.isEmpty(sessionInfo)) {
			String loginName = sessionInfo.getLoginName();
			tstu.setCrtUsr(loginName);
			tstu.setUptUsr(loginName);
		}
//		tstu.setStatus(Constants.CODE_TYPE_PUBSTATUS_NO);
		stuDAO.save(tstu);
		
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_STU, Constants.LOG_TYPE_OPERTYPE_ADD, tstu.getStuNo(), "[学生]保存学生");
		
		return tstu.getStuNo();
	}
	//多次点保存时，更新记录
	public String saveForUpate(Stu stu,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		Tstu tstu = null;
		//更新
		tstu=stuDAO.get(Tstu.class, stu.getStuNo().trim());
		BeanUtils.copyProperties(stu, tstu);
//		tstu.setCreatDate(new Date());
//		tstu.setStatus(Constants.CODE_TYPE_PUBSTATUS_NO);
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, tstu.getStuNo(), "[学生]保存更新学生");
		//届次
		Object secoObj=httpSession.getAttribute(ResourceUtil.getSessionSeco());
		Seco seco=secoObj==null?null:((Seco)secoObj);
		if(!util.isEmpty(seco)) {
			Tsecondary ts=new Tsecondary();
			BeanUtils.copyProperties(seco,ts);
		}
		
		stuDAO.update(tstu);
		
		return tstu.getStuNo();
	}
	/**
	 * 新增或更新学生信息
	 */
	public String upDateOrAdd(Stu stu,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		Tstu tstu = null;
		if(util.isEmpty(stu.getStuNo())) {
		//新增
			tstu = new Tstu();
			BeanUtils.copyProperties(stu, tstu);
//			tstu.setId(Generator.getInstance().getStuNO());
//			tstu.setCreatDate(new Date());
		} else {
		//更新
			tstu=stuDAO.get(Tstu.class, stu.getStuNo().trim());
//			Date creatDate = tstu.getCreatDate();
			BeanUtils.copyProperties(stu, tstu);
//			tstu.setCreatDate(creatDate);
		}
		String deptId=((SessionInfo) httpSession.getAttribute("sessionInfo")).getDeptId();
//		tstu.setPubUnit(deptId);
//		tstu.setPubDate(new Date());
//		tstu.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES);
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, tstu.getStuNo(), "[学生]提交学生");
		//届次
		Object secoObj=httpSession.getAttribute(ResourceUtil.getSessionSeco());
		Seco seco=secoObj==null?null:((Seco)secoObj);
		if(!util.isEmpty(seco)) {
			Tsecondary ts=new Tsecondary();
			BeanUtils.copyProperties(seco,ts);
		}
		
		stuDAO.saveOrUpdate(tstu);
		
		
		
		return tstu.getStuNo();
	}
	/**
	 * 删除学生信息
	 */
	public void del(Stu stu) throws Exception {
		
		Util util=new Util();
		String ids = stu.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					Tstu tstu = stuDAO.get(Tstu.class,id);
					stuDAO.delete(tstu);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_DEL, id, "[学生]学生删除（学生ID："+id+"）");
				}
			}
		}
	}
	/**
	 * 发布学生信息
	 */
	public void pub(Stu stu,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		String ids = stu.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					Tstu tstu = stuDAO.get(Tstu.class,id);
//					tstu.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES); //已发布状态
//					if(util.isEmpty(tstu.getPubDate())){
//						String deptId=((SessionInfo) httpSession.getAttribute("sessionInfo")).getDeptId();
//						tstu.setPubUnit(deptId);
//						tstu.setPubDate(new Date());
//					}
					stuDAO.update(tstu);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[学生]学生发布（学生ID："+id+"）");
				}
			}
		}
	}
	/**
	 * 取消发布学生信息
	 */
	public void cancelPub(Stu stu) throws Exception {
		
		Util util=new Util();
		String ids = stu.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					Tstu tstu = stuDAO.get(Tstu.class,id);
//					tstu.setStatus(Constants.CODE_TYPE_PUBSTATUS_NO); //已发布状态
					stuDAO.update(tstu);
//					if(!util.isEmpty(tstu.getPubDate())){
//						tstu.setPubUnit(null);
//						tstu.setPubDate(null);
//					}
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[学生]学生取消发布（学生ID："+id+"）");
				}
			}
		}
	}
	/**
	 * 修改学生信息
	 */
	public void edit(Stu stu,HttpSession httpSession) throws Exception {
		Util util=new Util();
		String id = stu.getStuNo();
			if(id!=null && !"".equals(id)) {
			Tstu tstu = stuDAO.get(Tstu.class,id);
			tstu.setDept(stu.getDept());//院、系、部	
			tstu.setMajor(stu.getMajor());//专业
			tstu.setClasses(classesDAO.get(Tclasses.class, stu.getClassId()));//所在班级
			tstu.setArchives(archivesDAO.get(Tarchives.class, stu.getArchNo()));//学生档案
			tstu.setInTime(stu.getInTime());
			tstu.setEnSour(stu.getEnSour());//入学来源 
			tstu.setPriPro(stu.getPriPro());//主要问题
			tstu.setPlanAfterGrad(stu.getPlanAfterGrad());//毕业以后的打算
			tstu.getArchives().getBasInfoPers().setPicName(stu.getPicName());//头像
			tstu.setRemark(stu.getRemark());//备注
			tstu.setUptTime(new Date());
			
			SessionInfo sessionInfo = util.isEmpty(httpSession.getAttribute("sessionInfo"))?null: (SessionInfo) httpSession.getAttribute("sessionInfo");
			if(!util.isEmpty(sessionInfo)) {
				String loginName = sessionInfo.getLoginName();
				tstu.setUptUsr(loginName);
			}
			
			stuDAO.update(tstu);
			//生成日志
			this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[学生]学生修改（学生ID："+id+"）");
		}
	}
	/**
	 * 新增学生档案信息
	 */
	public void archAdd(Stu stu) throws Exception {
		Util util=new Util();
		String stuNo = stu.getStuNo();
		if(!util.isEmpty(stuNo)) {
			//档案
			Tarchives arch = archivesDAO.get(Tarchives.class, stu.getArchNo());
			if(!util.isEmpty(arch)) {
				arch.setUrgentTel(stu.getUrgentTel());
				arch.setGradSchool(stu.getGradSchool());
				arch.setSelfAppr(stu.getSelfAppr());
				arch.setRemark(stu.getArchRemark());
				arch.setUptTime(new Date());
				//基本信息
				if(!util.isEmpty(stu.getBasInfoPersId())) {
					TbasInfoPers basInfo = basInfoPersDAO.get(TbasInfoPers.class, stu.getBasInfoPersId());
					BeanUtils.copyProperties(stu, basInfo,new String[]{"id","crtTime","uptTime","archives","tel"});
//					basInfo.setTel(stu.getBasInfoPersTel());
					basInfo.setUptTime(new Date());
					arch.setBasInfoPers(basInfo);
				}
				//家庭信息
				if(!util.isEmpty(stu.getFamilyId())) {
					Tfamily family = familyDAO.get(Tfamily.class, stu.getFamilyId());
					BeanUtils.copyProperties(stu, family,new String[]{"id","crtTime","uptTime","archives","tel","familyMems"});
					family.setTel(stu.getFamilyTel());
					family.setUptTime(new Date());
					arch.setFamily(family);
				}
				
				Tstu tstu = stuDAO.get(Tstu.class,stuNo);
				tstu.setArchives(arch);
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
				
				stuDAO.update(tstu);
	//			//生成日志
				this.saveLog(Constants.LOG_TYPE_CODE_ARCH, Constants.LOG_TYPE_OPERTYPE_EDIT, arch.getArchNo(), "[学生]学生档案修改（学生档案ID："+arch.getArchNo()+"）");
			}
		}
	}
	/**
	 * 修改学生档案信息
	 */
	public void archEdit(Stu stu) throws Exception {
		Util util=new Util();
		String stuNo = stu.getStuNo();
		if(!util.isEmpty(stuNo)) {
			//档案
			Tarchives arch = archivesDAO.get(Tarchives.class, stu.getArchNo());
			if(!util.isEmpty(arch)) {
				arch.setUrgentTel(stu.getUrgentTel());
				arch.setGradSchool(stu.getGradSchool());
				arch.setSelfAppr(stu.getSelfAppr());
				arch.setRemark(stu.getArchRemark());
				arch.setUptTime(new Date());
				//基本信息
				if(!util.isEmpty(stu.getBasInfoPersId())) {
					TbasInfoPers basInfo = basInfoPersDAO.get(TbasInfoPers.class, stu.getBasInfoPersId());
					BeanUtils.copyProperties(stu, basInfo,new String[]{"id","crtTime","uptTime","archives","tel"});
//					basInfo.setTel(stu.getBasInfoPersTel());
					basInfo.setUptTime(new Date());
					arch.setBasInfoPers(basInfo);
				}
				//家庭信息
				if(!util.isEmpty(stu.getFamilyId())) {
					Tfamily family = familyDAO.get(Tfamily.class, stu.getFamilyId());
					BeanUtils.copyProperties(stu, family,new String[]{"id","crtTime","uptTime","archives","tel","familyMems"});
					family.setTel(stu.getFamilyTel());
					family.setUptTime(new Date());
					arch.setFamily(family);
				}
				
				Tstu tstu = stuDAO.get(Tstu.class,stuNo);
				tstu.setArchives(arch);
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
				
				stuDAO.update(tstu);
	//			//生成日志
				this.saveLog(Constants.LOG_TYPE_CODE_ARCH, Constants.LOG_TYPE_OPERTYPE_EDIT, arch.getArchNo(), "[学生]学生档案修改（学生档案ID："+arch.getArchNo()+"）");
			}
		}
	}
	
	//首页学生统计
	public Map<String, Integer> stuCount(HttpSession httpSession) {
		return null;
	}
	public BaseDaoI<Tstu> getStuDAO() {
		return stuDAO;
	}
	
	@Autowired
	public void setStuDAO(BaseDaoI<Tstu> stuDAO) {
		this.stuDAO = stuDAO;
	}
	
	public BaseDaoI<Tclasses> getClassesDAO() {
		return classesDAO;
	}
	
	@Autowired
	public void setClassesDAO(BaseDaoI<Tclasses> classesDAO) {
		this.classesDAO = classesDAO;
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
	public BaseDaoI<Tfamily> getFamilyDAO() {
		return familyDAO;
	}
	@Autowired
	public void setFamilyDAO(BaseDaoI<Tfamily> familyDAO) {
		this.familyDAO = familyDAO;
	}
	public BaseDaoI<TbasInfoPers> getBasInfoPersDAO() {
		return basInfoPersDAO;
	}
	@Autowired
	public void setBasInfoPersDAO(BaseDaoI<TbasInfoPers> basInfoPersDAO) {
		this.basInfoPersDAO = basInfoPersDAO;
	}
}
