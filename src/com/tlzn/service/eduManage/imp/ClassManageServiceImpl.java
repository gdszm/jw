package com.tlzn.service.eduManage.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.eduManage.TclassManage;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.eduManage.ClassManage;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.eduManage.ClassManageServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Util;

@Service("classManageService")
public class ClassManageServiceImpl extends BaseServiceImpl implements ClassManageServiceI{

	private BaseDaoI<TclassManage> classManageDAO;
	/**
	 * 
	 * 查询单条班级管理
	 * 
	 * @param classManage
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public ClassManage get(String id) throws Exception {
		Util util=new Util();
		TclassManage tclassManage= classManageDAO.get(TclassManage.class, id);
		ClassManage classManage = new ClassManage();
		BeanUtils.copyProperties(tclassManage, classManage);
//		classManage.setArchNo(tclassManage.getArchives().getArchNo());
//		classManage.setCid(tclassManage.getTdept().getCid());
//		classManage.setSex(tclassManage.getArchives().getBasInfoPers().getSex());
//		classManage.setPicName(tclassManage.getArchives().getBasInfoPers().getPicName());
//		if(!util.isEmpty(tclassManage.getArchives().getBasInfoPers())) {
//			//取老师姓名
//			classManage.setName(tclassManage.getArchives().getBasInfoPers().getName());
//		}
//		classManage.setSexName(this.findDicName("SEX",tclassManage.getArchives().getBasInfoPers().getSex()));
//		classManage.setRankName(this.findDicName("rank",tclassManage.getRank()));
//		classManage.setMarryName(this.findDicName("MARRY",tclassManage.getMarry()));
//		classManage.setHasClassManageCertName(this.findDicName("hasNo",tclassManage.getHasClassManageCert()));
//		classManage.setEducationName(this.findDicName("EDUCATION",tclassManage.getEducation()));
//		classManage.setDegreeName(this.findDicName("DEGREE",tclassManage.getDegree()));
			return classManage;
	}

	/**
	 * 
	 * 条件查询
	 * 
	 * @param classManage
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(ClassManage classManage) throws Exception {
		String hql=" from TclassManage t where 1=1";
		DataGrid j = new DataGrid();
		j.setRows(editClassManages(find(classManage,hql)));
		j.setTotal(total(classManage,hql));
		return j;
	}
	private List<ClassManage> editClassManages(List<TclassManage> classManageList) throws Exception {
		Util util=new Util();
		List<ClassManage> classManages = new ArrayList<ClassManage>();
		if (classManageList != null && classManageList.size() > 0) {
			for (TclassManage tclassManage: classManageList) {
				ClassManage classManage = new ClassManage();
				BeanUtils.copyProperties(tclassManage, classManage);
//				if(!util.isEmpty(tclassManage.getArchives().getBasInfoPers())) {
//					//取老师姓名
//					classManage.setName(tclassManage.getArchives().getBasInfoPers().getName());
//				}
//				classManage.setSexName(this.findDicName("SEX",tclassManage.getArchives().getBasInfoPers().getSex()));
//				classManage.setRankName(this.findDicName("rank",tclassManage.getRank()));
//				classManage.setMarryName(this.findDicName("MARRY",tclassManage.getMarry()));
//				classManage.setHasClassManageCertName(this.findDicName("hasNo",tclassManage.getHasClassManageCert()));
//				classManage.setEducationName(this.findDicName("EDUCATION",tclassManage.getEducation()));
//				classManage.setDegreeName(this.findDicName("DEGREE",tclassManage.getDegree()));
//				classManage.setRemark("");
				classManages.add(classManage);
			}
		}
		return classManages;
	}
	private List<TclassManage> find(ClassManage classManage,String hql) throws Exception {
		hql = addWhere(classManage, hql);
		if (classManage.getSort() != null && classManage.getOrder() != null) {
			hql += " order by " + classManage.getSort() + " " + classManage.getOrder();
		}else {
			hql+=" order by t.uptTime desc, t.crtTime desc";
		}
		List<TclassManage> classManageList = classManageDAO.find(hql, classManage.getPage(), classManage.getRows());
		return classManageList;
	}
	private Long total(ClassManage classManage,String hql) throws Exception {
		hql = addWhere(classManage, hql);
		hql = "select count(*) "+hql;
		return classManageDAO.count(hql);
	}
	private String addWhere(ClassManage classManage, String hql) throws Exception {
		Util util=new Util();
		if(!util.isEmpty(classManage.getId())){
			 hql += " and t.id like '%"+classManage.getId().trim()+"%'";
		}
//		if(!util.isEmpty(classManage.getName())){
//			 hql += " and t.archives.basInfoPers.name like '%"+classManage.getName().trim()+"%'";
//		}
//		if (!util.isEmpty(classManage.getSex())) {
//			hql += " and t.archives.basInfoPers.sex='" + classManage.getSex().trim() + "' ";
//		}
//		if (!util.isEmpty(classManage.getCid())) {
//			hql += " and t.tdept.cid='" + classManage.getCid().trim() + "' ";
//		}
//		if (!util.isEmpty(classManage.getArchNo())) {
//			hql += " and t.archives.archNo='" + classManage.getArchNo().trim() + "' ";
//		}
//		if (!util.isEmpty(classManage.getRank())) {
//			hql += " and t.rank='" + classManage.getRank().trim() + "' ";
//		}
//		if (!util.isEmpty(classManage.getHasClassManageCert())) {
//			hql += " and t.hasClassManageCert='" + classManage.getHasClassManageCert().trim() + "' ";
//		}
//		if (!util.isEmpty(classManage.getEducation())) {
//			hql += " and t.education='" + classManage.getEducation().trim() + "' ";
//		}
//		if(!util.isEmpty(classManage.getGradFrom())){
//			 hql += " and t.gradFrom like '%"+classManage.getGradFrom().trim()+"%'";
//		}
//		if(!util.isEmpty(classManage.getMajorName())){
//			 hql += " and t.majorName like '%"+classManage.getMajorName().trim()+"%'";
//		}
//		if (!util.isEmpty(classManage.getDegree())) {
//			hql += " and t.degree='" + classManage.getDegree().trim() + "' ";
//		}
//		if(!util.isEmpty(classManage.getRemark())){
//			 hql += " and t.remark like '%"+classManage.getRemark().trim()+"%'";
//		}
//		//录入系统开始时间
//		if(!util.isEmpty(classManage.getCrtSTime())){
//			hql+=" and date_format(t.crtTime,'%Y-%m-%d') >= '"+util.getFormatDate(classManage.getCrtSTime(), "yyyy-MM-dd")+"'";
//		}
//		//录入系统结束时间
//		if(!util.isEmpty(classManage.getCrtETime())){
//	    	hql+=" and date_format(t.crtTime,'%Y-%m-%d') <= '"+util.getFormatDate(classManage.getCrtETime(),"yyyy-MM-dd")+"'";
//	    }
		return hql;
	}
	
	/**
	 * 保存班级管理信息
	 */
	public String save(ClassManage classManage,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		TclassManage tclassManage = new TclassManage();
		BeanUtils.copyProperties(classManage, tclassManage);
//		tclassManage.setId(Generator.getInstance().getClassManageNO());
//		tclassManage.setCreatDate(new Date());
//		//tclassManage.setPubDate(new Date());
//		tclassManage.setStatus(Constants.CODE_TYPE_PUBSTATUS_NO);
//		//届次
//		Object secoObj=httpSession.getAttribute(ResourceUtil.getSessionSeco());
//		Seco seco=secoObj==null?null:((Seco)secoObj);
//		if(!util.isEmpty(seco)) {
//			Tsecondary ts=new Tsecondary();
//			BeanUtils.copyProperties(seco,ts);
//		}
		
		classManageDAO.save(tclassManage);
		
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_ADD, tclassManage.getId(), "[班级管理]保存班级管理");
		
		return tclassManage.getId();
	}
	/**
	 * 删除班级管理信息
	 */
	public void del(ClassManage classManage) throws Exception {
		
		Util util=new Util();
		String ids = classManage.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					TclassManage tclassManage = classManageDAO.get(TclassManage.class,id);
					classManageDAO.delete(tclassManage);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_DEL, id, "[班级管理]班级管理删除（班级管理ID："+id+"）");
				}
			}
		}
	}
	/**
	 * 发布班级管理信息
	 */
	public void pub(ClassManage classManage,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		String ids = classManage.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					TclassManage tclassManage = classManageDAO.get(TclassManage.class,id);
//					tclassManage.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES); //已发布状态
//					if(util.isEmpty(tclassManage.getPubDate())){
//						String deptId=((SessionInfo) httpSession.getAttribute("sessionInfo")).getDeptId();
//						tclassManage.setPubUnit(deptId);
//						tclassManage.setPubDate(new Date());
//					}
					classManageDAO.update(tclassManage);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[班级管理]班级管理发布（班级管理ID："+id+"）");
				}
			}
		}
	}
	/**
	 * 取消发布班级管理信息
	 */
	public void cancelPub(ClassManage classManage) throws Exception {
		
		Util util=new Util();
		String ids = classManage.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					TclassManage tclassManage = classManageDAO.get(TclassManage.class,id);
//					tclassManage.setStatus(Constants.CODE_TYPE_PUBSTATUS_NO); //已发布状态
					classManageDAO.update(tclassManage);
//					if(!util.isEmpty(tclassManage.getPubDate())){
//						tclassManage.setPubUnit(null);
//						tclassManage.setPubDate(null);
//					}
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[班级管理]班级管理取消发布（班级管理ID："+id+"）");
				}
			}
		}
	}
	/**
	 * 修改班级管理信息
	 */
	public void edit(ClassManage classManage) throws Exception {
		
		String id = classManage.getId();
			if(id!=null && !"".equals(id)) {
				TclassManage tclassManage = classManageDAO.get(TclassManage.class,id);
				BeanUtils.copyProperties(classManage, tclassManage, new String[]{"crtTime"});
//				Tarchives arch = archivesDAO.get(Tarchives.class, classManage.getArchNo());
//				tclassManage.setArchives(arch);
//				Tdept dept=deptDAO.get(Tdept.class, classManage.getCid());
//				tclassManage.setTdept(dept);
//				tclassManage.getArchives().getBasInfoPers().setPicName(classManage.getPicName());
				tclassManage.setUptTime(new Date());
			classManageDAO.update(tclassManage);
			//生成日志
			this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[班级管理]班级管理修改（班级管理ID："+id+"）");
		}
	}

	public BaseDaoI<TclassManage> getClassManageDAO() {
		return classManageDAO;
	}
	
	@Autowired
	public void setClassManageDAO(BaseDaoI<TclassManage> classManageDAO) {
		this.classManageDAO = classManageDAO;
	}
}
