package com.tlzn.service.eduManage.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.eduManage.Tclasses;
import com.tlzn.model.eduManage.Tstu;
import com.tlzn.model.eduManage.Tteacher;
import com.tlzn.model.sys.Tsecondary;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.eduManage.Classes;
import com.tlzn.pageModel.sys.Seco;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.eduManage.ClassesServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Generator;
import com.tlzn.util.base.ResourceUtil;
import com.tlzn.util.base.Util;

@Service("classesService")
public class ClassesServiceImpl extends BaseServiceImpl implements ClassesServiceI{

	private BaseDaoI<Tclasses> classesDAO;
	private BaseDaoI<Tteacher> teacherDAO;
	private BaseDaoI<Tstu> stuDAO;
	
	/**
	 * 
	 * 查询单条班级
	 * 
	 * @param classes
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Classes get(String id) throws Exception {
		Util util=new Util();
		Tclasses tclasses= classesDAO.get(Tclasses.class, id);
		Classes classes = new Classes();
		BeanUtils.copyProperties(tclasses, classes);
		if(!util.isEmpty(tclasses.getAdminTeacherId())) {
			Tteacher teacher=teacherDAO.get(Tteacher.class, tclasses.getAdminTeacherId());
			//取老师姓名
			String name=util.isEmpty(util.isEmpty(util.isEmpty(teacher)?"":teacher.getArchives())?"":teacher.getArchives().getBasInfoPers())?"":teacher.getArchives().getBasInfoPers().getName();
			classes.setAdminTeacherName(name);
		}
		if(!util.isEmpty(tclasses.getAdminStuId())) {
			Tstu stuAdmin=stuDAO.get(Tstu.class,tclasses.getAdminStuId());
			//取学生姓名
			String name=util.isEmpty(util.isEmpty(util.isEmpty(stuAdmin)?"":stuAdmin.getArchives())?"":stuAdmin.getArchives().getBasInfoPers())?"":stuAdmin.getArchives().getBasInfoPers().getName();
			classes.setAdminStuName(name);
		}
		if(!util.isEmpty(tclasses.getSecStuId())) {
			Tstu stuSec=stuDAO.get(Tstu.class,tclasses.getSecStuId());
			//取学生姓名
			String name=util.isEmpty(util.isEmpty(util.isEmpty(stuSec)?"":stuSec.getArchives())?"":stuSec.getArchives().getBasInfoPers())?"":stuSec.getArchives().getBasInfoPers().getName();
			classes.setSecStuName(name);
		}
		classes.setTrainLevelName(this.findDicName("trainLevel", classes.getTrainLevel()));
		return classes;
	}

	/**
	 * 
	 * 条件查询
	 * 
	 * @param classes
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(Classes classes) throws Exception {
		String hql=" from Tclasses t where 1=1";
		DataGrid j = new DataGrid();
		j.setRows(editClassess(find(classes,hql)));
		j.setTotal(total(classes,hql));
		return j;
	}
	private List<Classes> editClassess(List<Tclasses> classesList) throws Exception {
		Util util=new Util();
		List<Classes> classess = new ArrayList<Classes>();
		if (classesList != null && classesList.size() > 0) {
			for (Tclasses tclasses: classesList) {
				Classes classes = new Classes();
				BeanUtils.copyProperties(tclasses, classes);
				if(!util.isEmpty(tclasses.getAdminTeacherId())) {
					Tteacher teacher=teacherDAO.get(Tteacher.class, tclasses.getAdminTeacherId());
					//取老师姓名
					String name=util.isEmpty(util.isEmpty(util.isEmpty(teacher)?"":teacher.getArchives())?"":teacher.getArchives().getBasInfoPers())?"":teacher.getArchives().getBasInfoPers().getName();
					classes.setAdminTeacherName(name);
				}
				if(!util.isEmpty(tclasses.getAdminStuId())) {
					Tstu stuAdmin=stuDAO.get(Tstu.class,tclasses.getAdminStuId());
					//取学生姓名
					String name=util.isEmpty(util.isEmpty(util.isEmpty(stuAdmin)?"":stuAdmin.getArchives())?"":stuAdmin.getArchives().getBasInfoPers())?"":stuAdmin.getArchives().getBasInfoPers().getName();
					classes.setAdminStuName(name);
				}
				if(!util.isEmpty(tclasses.getSecStuId())) {
					Tstu stuSec=stuDAO.get(Tstu.class,tclasses.getSecStuId());
					//取学生姓名
					String name=util.isEmpty(util.isEmpty(util.isEmpty(stuSec)?"":stuSec.getArchives())?"":stuSec.getArchives().getBasInfoPers())?"":stuSec.getArchives().getBasInfoPers().getName();
					classes.setSecStuName(name);
				}
				//培养层次(1：中专 2：专科 3：本科 4：研究生)
				classes.setTrainLevelName(this.findDicName("trainLevel", classes.getTrainLevel()));
//				classes.setReplyName(this.findDicName("YESNO", classes.getReply()));
//				classes.setClassesTypeName(this.findDicName("NOTICETYPE", classes.getClassesType()));
//				if(!util.isEmpty(tclasses.getPubUnit())){
//					Tdept dept = this.getDeptDao().get(Tdept.class, tclasses.getPubUnit());
//					String cname =dept==null?null:dept.getCname();
//					classes.setPubUnitName(cname);
//				}
				classes.setProblem("");
				classes.setProSolve("");
				classess.add(classes);
			}
		}
		return classess;
	}
	/**
	 * 最新5条班级
	 */
	public List<Classes> getNewClasses() throws Exception {
		String hql="from Tclasses t where t.status='"+Constants.CODE_TYPE_PUBSTATUS_YES+"' order by t.pubDate desc,t.creatDate desc";
		List<Tclasses> classesList = classesDAO.find(hql, 1, 5);
		return editClassess(classesList);
	}
	
	
	private List<Tclasses> find(Classes classes,String hql) throws Exception {
		hql = addWhere(classes, hql);
		if (classes.getSort() != null && classes.getOrder() != null) {
			hql += " order by " + classes.getSort() + " " + classes.getOrder();
		}else {
			hql+=" order by t.className, t.uptTime desc, t.crtTime desc";
		}
		List<Tclasses> classesList = classesDAO.find(hql, classes.getPage(), classes.getRows());
		return classesList;
	}
	private Long total(Classes classes,String hql) throws Exception {
		hql = addWhere(classes, hql);
		hql = "select count(*) "+hql;
		return classesDAO.count(hql);
	}
	private String addWhere(Classes classes, String hql) throws Exception {
		Util util=new Util();
		if(!util.isEmpty(classes.getClassName())){
			 hql += " and t.className like '%"+classes.getClassName().trim()+"%'";
		}
		if (!util.isEmpty(classes.getTrainLevel())) {
			hql += " and t.trainLevel='" + classes.getTrainLevel() + "' ";
		}
		if(!util.isEmpty(classes.getSysLength())){
			 hql += " and t.sysLength like '%"+classes.getSysLength().trim()+"%'";
		}
		if (!util.isEmpty(classes.getBuildDate())) {
			hql += " and t.buildDate=" + classes.getBuildDate() + "";
		}
		if(!util.isEmpty(classes.getAdminTeacherName())){
			hql += " and ( exists (select id from Tteacher tt where t.adminTeacherId=tt.id and tt.archives.basInfoPers.name like '%"+classes.getAdminTeacherName()+"%'))";
		}
//		if(!util.isEmpty(classes.getTitle())){
//			 hql += " and t.title like '%"+classes.getTitle().trim()+"%'";
//		}
//		if(!util.isEmpty(classes.getContent())){
//			hql += " and t.content like '%" + classes.getContent().trim() + "%'";
//		}
//		if (!util.isEmpty(classes.getPubUnitName())) {
//			hql += " and exists (from Tdept b where t.pubUnit=b.cid and b.cname like'%"+ classes.getPubUnitName().trim() + "%')";
//		}
//		if (!util.isEmpty(classes.getClassesType())) {
//			hql += " and t.classesType='" + classes.getClassesType() + "' ";
//		}
//		if (!util.isEmpty(classes.getStatus())) {
//			hql += " and t.status='" + classes.getStatus() + "'";
//		}
//		if(classes.getCreatDateStart()!=null){
//			 hql+=" and to_char(t.creatDate,'yyyy-MM-dd') >= '"+util.getFormatDate(classes.getCreatDateStart(), "yyyy-MM-dd")+"'";
//		}
//		 if(classes.getCreatDateEnd()!=null){
//		 hql+=" and to_char(t.creatDate,'yyyy-MM-dd') <= '"+util.getFormatDate(classes.getCreatDateEnd(),"yyyy-MM-dd")+"'";
//		 }
//		 if(classes.getPubDateStart()!=null){
//			 hql+=" and to_char(t.pubDate,'yyyy-MM-dd') >= '"+util.getFormatDate(classes.getPubDateStart(),"yyyy-MM-dd")+"'";
//		 }
//		 if(classes.getPubDateEnd()!=null){
//		 hql+=" and to_char(t.pubDate,'yyyy-MM-dd') <= '"+util.getFormatDate(classes.getPubDateEnd(),"yyyy-MM-dd")+"'";
//		 }
		return hql;
	}
	
	/**
	 * 新增班级
	 */
	public String add(Classes classes,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		Tclasses tclasses = new Tclasses();
		BeanUtils.copyProperties(classes, tclasses);
		tclasses.setId(Generator.getInstance().getClassesNO());
		
//		tclasses.setStatus(Constants.CODE_TYPE_PUBSTATUS_NO);
//		//届次
//		Object secoObj=httpSession.getAttribute(ResourceUtil.getSessionSeco());
//		Seco seco=secoObj==null?null:((Seco)secoObj);
//		if(!util.isEmpty(seco)) {
//			Tsecondary ts=new Tsecondary();
//			BeanUtils.copyProperties(seco,ts);
//		}
		if(util.isEmpty(tclasses.getBuildDate())) tclasses.setBuildDate(0);
		if(util.isEmpty(tclasses.getManNum())) tclasses.setManNum(0);
		if(util.isEmpty(tclasses.getWomanNum())) tclasses.setWomanNum(0);
		if(util.isEmpty(tclasses.getLivingNum())) tclasses.setLivingNum(0);
		
		tclasses.setCrtTime(new Date());
		tclasses.setUptTime(new Date());
		classesDAO.save(tclasses);
		
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_ADD, tclasses.getId(), "[班级]保存班级");
		
		return tclasses.getId();
	}
	/**
	 * 删除班级信息
	 */
	public void del(Classes classes) throws Exception {
		
		Util util=new Util();
		String ids = classes.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					Tclasses tclasses = classesDAO.get(Tclasses.class,id);
					classesDAO.delete(tclasses);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_DEL, id, "[班级]班级删除（班级ID："+id+"）");
				}
			}
		}
	}
	/**
	 * 发布班级信息
	 */
	public void pub(Classes classes,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		String ids = classes.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					Tclasses tclasses = classesDAO.get(Tclasses.class,id);
//					tclasses.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES); //已发布状态
//					if(util.isEmpty(tclasses.getPubDate())){
//						String deptId=((SessionInfo) httpSession.getAttribute("sessionInfo")).getDeptId();
//						tclasses.setPubUnit(deptId);
//						tclasses.setPubDate(new Date());
//					}
					classesDAO.update(tclasses);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[班级]班级发布（班级ID："+id+"）");
				}
			}
		}
	}
	/**
	 * 取消发布班级信息
	 */
	public void cancelPub(Classes classes) throws Exception {
		
		Util util=new Util();
		String ids = classes.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					Tclasses tclasses = classesDAO.get(Tclasses.class,id);
//					tclasses.setStatus(Constants.CODE_TYPE_PUBSTATUS_NO); //已发布状态
					classesDAO.update(tclasses);
//					if(!util.isEmpty(tclasses.getPubDate())){
//						tclasses.setPubUnit(null);
//						tclasses.setPubDate(null);
//					}
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[班级]班级取消发布（班级ID："+id+"）");
				}
			}
		}
	}
	/**
	 * 修改班级信息
	 */
	public void edit(Classes classes) throws Exception {
		
		String id = classes.getId();
			if(id!=null && !"".equals(id)) {
				Tclasses tclasses = classesDAO.get(Tclasses.class,id);
				tclasses.setClassName(classes.getClassName());
				tclasses.setTrainLevel(classes.getTrainLevel());
				tclasses.setSysLength(classes.getSysLength());
				tclasses.setBuildDate(classes.getBuildDate());
				tclasses.setAdminTeacherId(classes.getAdminTeacherId());
				tclasses.setAdminStuId(classes.getAdminStuId());
				tclasses.setSecStuId(classes.getSecStuId());
				tclasses.setManNum(classes.getManNum());
				tclasses.setWomanNum(classes.getWomanNum());
				tclasses.setLivingNum(classes.getLivingNum());
				tclasses.setProblem(classes.getProblem());
				tclasses.setProSolve(classes.getProSolve());
				tclasses.setUptTime(new Date());
				classesDAO.update(tclasses);
				//生成日志
				this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[班级]班级修改（班级ID："+id+"）");
		}
	}

	//首页班级统计
	public Map<String, Integer> classesCount(HttpSession httpSession) {
		return null;
	}

	public BaseDaoI<Tclasses> getClassesDAO() {
		return classesDAO;
	}
	
	
	@Autowired
	public void setClassesDAO(BaseDaoI<Tclasses> classesDAO) {
		this.classesDAO = classesDAO;
	}
	
	public BaseDaoI<Tteacher> getTeacherDAO() {
		return teacherDAO;
	}

	@Autowired
	public void setTeacherDAO(BaseDaoI<Tteacher> teacherDAO) {
		this.teacherDAO = teacherDAO;
	}


	public BaseDaoI<Tstu> getStuDAO() {
		return stuDAO;
	}

	@Autowired
	public void setStuDAO(BaseDaoI<Tstu> stuDAO) {
		this.stuDAO = stuDAO;
	}
}
