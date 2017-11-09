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
import com.tlzn.model.eduManage.TclassroomType;
import com.tlzn.model.eduManage.Tstu;
import com.tlzn.model.eduManage.Tteacher;
import com.tlzn.model.sys.Tusergroup;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.eduManage.Classroom;
import com.tlzn.pageModel.eduManage.ClassroomType;
import com.tlzn.pageModel.sys.UserGroup;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.eduManage.ClassroomServiceI;
import com.tlzn.service.eduManage.ClassroomTypeServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Generator;
import com.tlzn.util.base.Util;

@Service("classroomTypeService")
public class ClassroomTypeServiceImpl extends BaseServiceImpl implements ClassroomTypeServiceI{

	private BaseDaoI<TclassroomType> classroomTypeDAO;
	
	/**
	 * 
	 * 查询单条班级
	 * 
	 * @param classroomType
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public ClassroomType get(String id) throws Exception {
		Util util=new Util();
		TclassroomType tclassroomType= classroomTypeDAO.get(TclassroomType.class, id);
		ClassroomType classroomType = new ClassroomType();
		BeanUtils.copyProperties(tclassroomType, classroomType);
//		if(!util.isEmpty(tclassroomType.getAdminTeacherId())) {
//			Tteacher teacher=teacherDAO.get(Tteacher.class, tclassroomType.getAdminTeacherId());
//			//取老师姓名
//			String name=util.isEmpty(util.isEmpty(util.isEmpty(teacher)?"":teacher.getArchives())?"":teacher.getArchives().getBasInfoPers())?"":teacher.getArchives().getBasInfoPers().getName();
//			classroomType.setAdminTeacherName(name);
//		}
//		if(!util.isEmpty(tclassroomType.getAdminStuId())) {
//			Tstu stuAdmin=stuDAO.get(Tstu.class,tclassroomType.getAdminStuId());
//			//取学生姓名
//			String name=util.isEmpty(util.isEmpty(util.isEmpty(stuAdmin)?"":stuAdmin.getArchives())?"":stuAdmin.getArchives().getBasInfoPers())?"":stuAdmin.getArchives().getBasInfoPers().getName();
//			classroomType.setAdminStuName(name);
//		}
//		if(!util.isEmpty(tclassroomType.getSecStuId())) {
//			Tstu stuSec=stuDAO.get(Tstu.class,tclassroomType.getSecStuId());
//			//取学生姓名
//			String name=util.isEmpty(util.isEmpty(util.isEmpty(stuSec)?"":stuSec.getArchives())?"":stuSec.getArchives().getBasInfoPers())?"":stuSec.getArchives().getBasInfoPers().getName();
//			classroomType.setSecStuName(name);
//		}
//		classroomType.setTrainLevelName(this.findDicName("trainLevel", classroomType.getTrainLevel()));
		return classroomType;
	}

	/**
	 * 
	 * 条件查询
	 * 
	 * @param classroomType
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(ClassroomType classroomType) throws Exception {
		String hql=" from TclassroomType t where 1=1";
		DataGrid j = new DataGrid();
		j.setRows(editClassroomTypes(find(classroomType,hql)));
		j.setTotal(total(classroomType,hql));
		return j;
	}
	private List<ClassroomType> editClassroomTypes(List<TclassroomType> classroomTypeList) throws Exception {
		Util util=new Util();
		List<ClassroomType> classroomTypes = new ArrayList<ClassroomType>();
		if (classroomTypeList != null && classroomTypeList.size() > 0) {
			for (TclassroomType tclassroomType: classroomTypeList) {
				ClassroomType classroomType = new ClassroomType();
				BeanUtils.copyProperties(tclassroomType, classroomType);
				
//				//所在系名称
//				classroomType.setDeptName(this.findNameByDepid(tclassroomType.getDeptNo()));
//				//教学楼名称
//				classroomType.setBuildingName(tclassroomType.getTeachingbuilding().getName());
//				//是否可用名称
//				classroomType.setIsCanUseName(this.findDicName("YESNO", tclassroomType.getIsCanUse()));
//				//教室状态名称（1:空闲 2:占用 3:停用）
//				classroomType.setStatusName(this.findDicName("classroomTypeStatus", tclassroomType.getStatus()));
//				//占用类型名称（1:排课占用 2:考试占用 3:试验占用 4:个人借用）
//				classroomType.setOccupyTypeName(this.findDicName("classroomTypeOccupyType", tclassroomType.getOccupyType()));
//				//教室类型名称
//				classroomType.setTypeName(this.findNameByClassroomTypeId(tclassroomType.getClassroomtype().getId()));
				
//				if(!util.isEmpty(tclassroomType.getAdminTeacherId())) {
//					Tteacher teacher=teacherDAO.get(Tteacher.class, tclassroomType.getAdminTeacherId());
//					//取老师姓名
//					String name=util.isEmpty(util.isEmpty(util.isEmpty(teacher)?"":teacher.getArchives())?"":teacher.getArchives().getBasInfoPers())?"":teacher.getArchives().getBasInfoPers().getName();
//					classroomType.setAdminTeacherName(name);
//				}
//				if(!util.isEmpty(tclassroomType.getAdminStuId())) {
//					Tstu stuAdmin=stuDAO.get(Tstu.class,tclassroomType.getAdminStuId());
//					//取学生姓名
//					String name=util.isEmpty(util.isEmpty(util.isEmpty(stuAdmin)?"":stuAdmin.getArchives())?"":stuAdmin.getArchives().getBasInfoPers())?"":stuAdmin.getArchives().getBasInfoPers().getName();
//					classroomType.setAdminStuName(name);
//				}
//				if(!util.isEmpty(tclassroomType.getSecStuId())) {
//					Tstu stuSec=stuDAO.get(Tstu.class,tclassroomType.getSecStuId());
//					//取学生姓名
//					String name=util.isEmpty(util.isEmpty(util.isEmpty(stuSec)?"":stuSec.getArchives())?"":stuSec.getArchives().getBasInfoPers())?"":stuSec.getArchives().getBasInfoPers().getName();
//					classroomType.setSecStuName(name);
//				}
				//培养层次(1：中专 2：专科 3：本科 4：研究生)
//				classroomType.setTrainLevelName(this.findDicName("trainLevel", classroomType.getTrainLevel()));
//				classroomType.setProblem("");
//				classroomType.setProSolve("");
				
				//下拉列表用
				classroomType.setCode(tclassroomType.getId());
				classroomType.setName(tclassroomType.getTypeName());
				
				classroomTypes.add(classroomType);
			}
		}
		return classroomTypes;
	}
	/**
	 * 最新5条班级
	 */
	public List<ClassroomType> getNewClassroomType() throws Exception {
		String hql="from TclassroomType t where t.status='"+Constants.CODE_TYPE_PUBSTATUS_YES+"' order by t.pubDate desc,t.creatDate desc";
		List<TclassroomType> classroomTypeList = classroomTypeDAO.find(hql, 1, 5);
		return editClassroomTypes(classroomTypeList);
	}
	
	
	private List<TclassroomType> find(ClassroomType classroomType,String hql) throws Exception {
		hql = addWhere(classroomType, hql);
		if (classroomType.getSort() != null && classroomType.getOrder() != null) {
			hql += " order by " + classroomType.getSort() + " " + classroomType.getOrder();
		}else {
			hql+=" order by t.uptTime desc, t.crtTime desc";
		}
		List<TclassroomType> classroomTypeList = classroomTypeDAO.find(hql, classroomType.getPage(), classroomType.getRows());
		return classroomTypeList;
	}
	private Long total(ClassroomType classroomType,String hql) throws Exception {
		hql = addWhere(classroomType, hql);
		hql = "select count(*) "+hql;
		return classroomTypeDAO.count(hql);
	}
	private String addWhere(ClassroomType classroomType, String hql) throws Exception {
		Util util=new Util();
//		if(!util.isEmpty(classroomType.getClassName())){
//			 hql += " and t.className like '%"+classroomType.getClassName().trim()+"%'";
//		}
//		if (!util.isEmpty(classroomType.getTrainLevel())) {
//			hql += " and t.trainLevel='" + classroomType.getTrainLevel() + "' ";
//		}
//		if(!util.isEmpty(classroomType.getSysLength())){
//			 hql += " and t.sysLength like '%"+classroomType.getSysLength().trim()+"%'";
//		}
//		if (!util.isEmpty(classroomType.getBuildDate())) {
//			hql += " and t.buildDate=" + classroomType.getBuildDate() + "";
//		}
//		if(!util.isEmpty(classroomType.getAdminTeacherName())){
//			hql += " and ( exists (select id from Tteacher tt where t.adminTeacherId=tt.id and tt.archives.basInfoPers.name like '%"+classroomType.getAdminTeacherName()+"%'))";
//		}
		return hql;
	}
	
	/**
	 * 新增班级
	 */
	public String add(ClassroomType classroomType,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		TclassroomType tclassroomType = new TclassroomType();
		BeanUtils.copyProperties(classroomType, tclassroomType);
//		tclassroomType.setId(Generator.getInstance().getClassroomTypeNO());
		
//		tclassroomType.setStatus(Constants.CODE_TYPE_PUBSTATUS_NO);
//		//届次
//		Object secoObj=httpSession.getAttribute(ResourceUtil.getSessionSeco());
//		Seco seco=secoObj==null?null:((Seco)secoObj);
//		if(!util.isEmpty(seco)) {
//			Tsecondary ts=new Tsecondary();
//			BeanUtils.copyProperties(seco,ts);
//		}
//		if(util.isEmpty(tclassroomType.getBuildDate())) tclassroomType.setBuildDate(0);
//		if(util.isEmpty(tclassroomType.getManNum())) tclassroomType.setManNum(0);
//		if(util.isEmpty(tclassroomType.getWomanNum())) tclassroomType.setWomanNum(0);
//		if(util.isEmpty(tclassroomType.getLivingNum())) tclassroomType.setLivingNum(0);
		
		tclassroomType.setCrtTime(new Date());
		tclassroomType.setUptTime(new Date());
		classroomTypeDAO.save(tclassroomType);
		
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_PROFESS, Constants.LOG_TYPE_OPERTYPE_ADD, tclassroomType.getId(), "[班级]保存班级");
		
		return tclassroomType.getId();
	}
	/**
	 * 删除班级信息
	 */
	public void del(ClassroomType classroomType) throws Exception {
		
		Util util=new Util();
		String ids = classroomType.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					TclassroomType tclassroomType = classroomTypeDAO.get(TclassroomType.class,id);
					classroomTypeDAO.delete(tclassroomType);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_DEL, id, "[班级]班级删除（班级ID："+id+"）");
				}
			}
		}
	}
	/**
	 * 发布班级信息
	 */
	public void pub(ClassroomType classroomType,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		String ids = classroomType.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					TclassroomType tclassroomType = classroomTypeDAO.get(TclassroomType.class,id);
//					tclassroomType.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES); //已发布状态
//					if(util.isEmpty(tclassroomType.getPubDate())){
//						String deptId=((SessionInfo) httpSession.getAttribute("sessionInfo")).getDeptId();
//						tclassroomType.setPubUnit(deptId);
//						tclassroomType.setPubDate(new Date());
//					}
					classroomTypeDAO.update(tclassroomType);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[班级]班级发布（班级ID："+id+"）");
				}
			}
		}
	}
	/**
	 * 取消发布班级信息
	 */
	public void cancelPub(ClassroomType classroomType) throws Exception {
		
		Util util=new Util();
		String ids = classroomType.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					TclassroomType tclassroomType = classroomTypeDAO.get(TclassroomType.class,id);
//					tclassroomType.setStatus(Constants.CODE_TYPE_PUBSTATUS_NO); //已发布状态
					classroomTypeDAO.update(tclassroomType);
//					if(!util.isEmpty(tclassroomType.getPubDate())){
//						tclassroomType.setPubUnit(null);
//						tclassroomType.setPubDate(null);
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
	public void edit(ClassroomType classroomType) throws Exception {
		
		String id = classroomType.getId();
			if(id!=null && !"".equals(id)) {
				TclassroomType tclassroomType = classroomTypeDAO.get(TclassroomType.class,id);
//				tclassroomType.setClassName(classroomType.getClassName());
//				tclassroomType.setTrainLevel(classroomType.getTrainLevel());
//				tclassroomType.setSysLength(classroomType.getSysLength());
//				tclassroomType.setBuildDate(classroomType.getBuildDate());
//				tclassroomType.setAdminTeacherId(classroomType.getAdminTeacherId());
//				tclassroomType.setAdminStuId(classroomType.getAdminStuId());
//				tclassroomType.setSecStuId(classroomType.getSecStuId());
//				tclassroomType.setManNum(classroomType.getManNum());
//				tclassroomType.setWomanNum(classroomType.getWomanNum());
//				tclassroomType.setLivingNum(classroomType.getLivingNum());
//				tclassroomType.setProblem(classroomType.getProblem());
//				tclassroomType.setProSolve(classroomType.getProSolve());
				tclassroomType.setUptTime(new Date());
				classroomTypeDAO.update(tclassroomType);
				//生成日志
				this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[班级]班级修改（班级ID："+id+"）");
		}
	}

	//首页班级统计
	public Map<String, Integer> classroomTypeCount(HttpSession httpSession) {
		return null;
	}

	//下拉列表
	@Override
	public List<ClassroomType> combobox(ClassroomType classroomType) throws Exception {
		List<ClassroomType> list=new ArrayList<ClassroomType>();
		String hql = "from TclassroomType t where 1=1";
		//hql+=" and t.tgroup.street='"+userGroup.getStreet()+"'";
		list=this.editClassroomTypes(classroomTypeDAO.find(hql));
		return list;
	}
	
	public BaseDaoI<TclassroomType> getClassroomTypeDAO() {
		return classroomTypeDAO;
	}
	
	
	@Autowired
	public void setClassroomTypeDAO(BaseDaoI<TclassroomType> classroomTypeDAO) {
		this.classroomTypeDAO = classroomTypeDAO;
	}

}
