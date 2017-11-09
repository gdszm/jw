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
import com.tlzn.model.eduManage.Tclassroom;
import com.tlzn.model.eduManage.Tstu;
import com.tlzn.model.eduManage.Tteacher;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.eduManage.Classroom;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.eduManage.ClassroomServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Generator;
import com.tlzn.util.base.Util;

@Service("classroomService")
public class ClassroomServiceImpl extends BaseServiceImpl implements ClassroomServiceI{

	private BaseDaoI<Tclassroom> classroomDAO;
	private BaseDaoI<Tteacher> teacherDAO;
	private BaseDaoI<Tstu> stuDAO;
	
	/**
	 * 
	 * 查询单条班级
	 * 
	 * @param classroom
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Classroom get(String id) throws Exception {
		Util util=new Util();
		Tclassroom tclassroom= classroomDAO.get(Tclassroom.class, id);
		Classroom classroom = new Classroom();
		BeanUtils.copyProperties(tclassroom, classroom);
//		if(!util.isEmpty(tclassroom.getAdminTeacherId())) {
//			Tteacher teacher=teacherDAO.get(Tteacher.class, tclassroom.getAdminTeacherId());
//			//取老师姓名
//			String name=util.isEmpty(util.isEmpty(util.isEmpty(teacher)?"":teacher.getArchives())?"":teacher.getArchives().getBasInfoPers())?"":teacher.getArchives().getBasInfoPers().getName();
//			classroom.setAdminTeacherName(name);
//		}
//		if(!util.isEmpty(tclassroom.getAdminStuId())) {
//			Tstu stuAdmin=stuDAO.get(Tstu.class,tclassroom.getAdminStuId());
//			//取学生姓名
//			String name=util.isEmpty(util.isEmpty(util.isEmpty(stuAdmin)?"":stuAdmin.getArchives())?"":stuAdmin.getArchives().getBasInfoPers())?"":stuAdmin.getArchives().getBasInfoPers().getName();
//			classroom.setAdminStuName(name);
//		}
//		if(!util.isEmpty(tclassroom.getSecStuId())) {
//			Tstu stuSec=stuDAO.get(Tstu.class,tclassroom.getSecStuId());
//			//取学生姓名
//			String name=util.isEmpty(util.isEmpty(util.isEmpty(stuSec)?"":stuSec.getArchives())?"":stuSec.getArchives().getBasInfoPers())?"":stuSec.getArchives().getBasInfoPers().getName();
//			classroom.setSecStuName(name);
//		}
//		classroom.setTrainLevelName(this.findDicName("trainLevel", classroom.getTrainLevel()));
		return classroom;
	}

	/**
	 * 
	 * 条件查询
	 * 
	 * @param classroom
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(Classroom classroom) throws Exception {
		String hql=" from Tclassroom t where 1=1";
		DataGrid j = new DataGrid();
		j.setRows(editClassrooms(find(classroom,hql)));
		j.setTotal(total(classroom,hql));
		return j;
	}
	private List<Classroom> editClassrooms(List<Tclassroom> classroomList) throws Exception {
		Util util=new Util();
		List<Classroom> classrooms = new ArrayList<Classroom>();
		if (classroomList != null && classroomList.size() > 0) {
			for (Tclassroom tclassroom: classroomList) {
				Classroom classroom = new Classroom();
				BeanUtils.copyProperties(tclassroom, classroom);
				//所在系名称
				classroom.setDeptName(this.findNameByDepid(tclassroom.getDeptNo()));
				//校区
				classroom.setCampusName(tclassroom.getTeachingbuilding().getCampus().getName());
				//教学楼名称
				classroom.setBuildingName(tclassroom.getTeachingbuilding().getName());
				//是否可用名称
				if(util.isEmpty(tclassroom.getIsCanUse())) {
					classroom.setIsCanUseName(this.findDicName("YESNO", "1"));
				} else {
					classroom.setIsCanUseName(this.findDicName("YESNO", tclassroom.getIsCanUse()));
				}
				//教室状态名称（1:空闲 2:占用 3:停用）
				classroom.setStatusName(this.findDicName("classroomStatus", tclassroom.getStatus()));
				//占用类型名称（1:排课占用 2:考试占用 3:试验占用 4:个人借用）
				classroom.setOccupyTypeName(this.findDicName("classroomOccupyType", tclassroom.getOccupyType()));
				//教室类型名称
				classroom.setTypeName(this.findNameByClassroomTypeId(tclassroom.getClassroomtype().getId()));
				
//				if(!util.isEmpty(tclassroom.getAdminTeacherId())) {
//					Tteacher teacher=teacherDAO.get(Tteacher.class, tclassroom.getAdminTeacherId());
//					//取老师姓名
//					String name=util.isEmpty(util.isEmpty(util.isEmpty(teacher)?"":teacher.getArchives())?"":teacher.getArchives().getBasInfoPers())?"":teacher.getArchives().getBasInfoPers().getName();
//					classroom.setAdminTeacherName(name);
//				}
//				if(!util.isEmpty(tclassroom.getAdminStuId())) {
//					Tstu stuAdmin=stuDAO.get(Tstu.class,tclassroom.getAdminStuId());
//					//取学生姓名
//					String name=util.isEmpty(util.isEmpty(util.isEmpty(stuAdmin)?"":stuAdmin.getArchives())?"":stuAdmin.getArchives().getBasInfoPers())?"":stuAdmin.getArchives().getBasInfoPers().getName();
//					classroom.setAdminStuName(name);
//				}
//				if(!util.isEmpty(tclassroom.getSecStuId())) {
//					Tstu stuSec=stuDAO.get(Tstu.class,tclassroom.getSecStuId());
//					//取学生姓名
//					String name=util.isEmpty(util.isEmpty(util.isEmpty(stuSec)?"":stuSec.getArchives())?"":stuSec.getArchives().getBasInfoPers())?"":stuSec.getArchives().getBasInfoPers().getName();
//					classroom.setSecStuName(name);
//				}
				//培养层次(1：中专 2：专科 3：本科 4：研究生)
//				classroom.setTrainLevelName(this.findDicName("trainLevel", classroom.getTrainLevel()));
//				classroom.setProblem("");
//				classroom.setProSolve("");
				
				//设空值，防止json转化进报错
				if(util.isEmpty(classroom.getFloor())) classroom.setFloor(0); 
				if(util.isEmpty(classroom.getSeatingNum())) classroom.setSeatingNum(0); 
				if(util.isEmpty(classroom.getExamSeatingNum())) classroom.setExamSeatingNum(0); 
				
				classrooms.add(classroom);
			}
		}
		return classrooms;
	}
	/**
	 * 最新5条班级
	 */
	public List<Classroom> getNewClassroom() throws Exception {
		String hql="from Tclassroom t where t.status='"+Constants.CODE_TYPE_PUBSTATUS_YES+"' order by t.pubDate desc,t.creatDate desc";
		List<Tclassroom> classroomList = classroomDAO.find(hql, 1, 5);
		return editClassrooms(classroomList);
	}
	
	
	private List<Tclassroom> find(Classroom classroom,String hql) throws Exception {
		hql = addWhere(classroom, hql);
		if (classroom.getSort() != null && classroom.getOrder() != null) {
			hql += " order by " + classroom.getSort() + " " + classroom.getOrder();
		}else {
			hql+=" order by t.uptTime desc, t.crtTime desc";
		}
		List<Tclassroom> classroomList = classroomDAO.find(hql, classroom.getPage(), classroom.getRows());
		return classroomList;
	}
	private Long total(Classroom classroom,String hql) throws Exception {
		hql = addWhere(classroom, hql);
		hql = "select count(*) "+hql;
		return classroomDAO.count(hql);
	}
	private String addWhere(Classroom classroom, String hql) throws Exception {
		Util util=new Util();
		
		if(!util.isEmpty(classroom.getName())){
			 hql += " and t.name like '%"+classroom.getName().trim()+"%'";
		}
		
	    if (!util.isEmpty(classroom.getDeptNo())) {
			   hql += " and (" +
						   		"exists (from Tdept b where b.cid=t.deptNo and t.deptNo='"+classroom.getDeptNo().trim()+"') " +
						   		"or " +
						   		"exists (from Tdept c where c.tdept.cid='"+classroom.getDeptNo().trim()+"')" +
						   	 ")";
		}
	    if(!util.isEmpty(classroom.getHouseNo())){
			 hql += " and t.houseNo like '%"+classroom.getHouseNo().trim()+"%'";
		}
	    //教室状态（1:空闲 2:占用 3:停用）
		if (!util.isEmpty(classroom.getStatus())) {
			hql += " and t.status='" + classroom.getStatus() + "' ";
		}
		//占用类型（1:排课占用 2:考试占用 3:试验占用 4:个人借用）
		if (!util.isEmpty(classroom.getOccupyType())) {
			hql += " and t.occupyType='" + classroom.getOccupyType() + "' ";
		}
//		if(!util.isEmpty(classroom.getSysLength())){
//			 hql += " and t.sysLength like '%"+classroom.getSysLength().trim()+"%'";
//		}
//		if (!util.isEmpty(classroom.getBuildDate())) {
//			hql += " and t.buildDate=" + classroom.getBuildDate() + "";
//		}
//		if(!util.isEmpty(classroom.getAdminTeacherName())){
//			hql += " and ( exists (select id from Tteacher tt where t.adminTeacherId=tt.id and tt.archives.basInfoPers.name like '%"+classroom.getAdminTeacherName()+"%'))";
//		}
		return hql;
	}
		
	/**
	 * 新增班级
	 */
	public String add(Classroom classroom,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		Tclassroom tclassroom = new Tclassroom();
		BeanUtils.copyProperties(classroom, tclassroom);
		tclassroom.setId(Generator.getInstance().getClassroomNO());
		
//		tclassroom.setStatus(Constants.CODE_TYPE_PUBSTATUS_NO);
//		//届次
//		Object secoObj=httpSession.getAttribute(ResourceUtil.getSessionSeco());
//		Seco seco=secoObj==null?null:((Seco)secoObj);
//		if(!util.isEmpty(seco)) {
//			Tsecondary ts=new Tsecondary();
//			BeanUtils.copyProperties(seco,ts);
//		}
//		if(util.isEmpty(tclassroom.getBuildDate())) tclassroom.setBuildDate(0);
//		if(util.isEmpty(tclassroom.getManNum())) tclassroom.setManNum(0);
//		if(util.isEmpty(tclassroom.getWomanNum())) tclassroom.setWomanNum(0);
//		if(util.isEmpty(tclassroom.getLivingNum())) tclassroom.setLivingNum(0);
		
		tclassroom.setCrtTime(new Date());
		tclassroom.setUptTime(new Date());
		classroomDAO.save(tclassroom);
		
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_PROFESS, Constants.LOG_TYPE_OPERTYPE_ADD, tclassroom.getId(), "[班级]保存班级");
		
		return tclassroom.getId();
	}
	/**
	 * 删除班级信息
	 */
	public void del(Classroom classroom) throws Exception {
		
		Util util=new Util();
		String ids = classroom.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					Tclassroom tclassroom = classroomDAO.get(Tclassroom.class,id);
					classroomDAO.delete(tclassroom);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_DEL, id, "[班级]班级删除（班级ID："+id+"）");
				}
			}
		}
	}
	/**
	 * 发布班级信息
	 */
	public void pub(Classroom classroom,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		String ids = classroom.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					Tclassroom tclassroom = classroomDAO.get(Tclassroom.class,id);
//					tclassroom.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES); //已发布状态
//					if(util.isEmpty(tclassroom.getPubDate())){
//						String deptId=((SessionInfo) httpSession.getAttribute("sessionInfo")).getDeptId();
//						tclassroom.setPubUnit(deptId);
//						tclassroom.setPubDate(new Date());
//					}
					classroomDAO.update(tclassroom);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[班级]班级发布（班级ID："+id+"）");
				}
			}
		}
	}
	/**
	 * 取消发布班级信息
	 */
	public void cancelPub(Classroom classroom) throws Exception {
		
		Util util=new Util();
		String ids = classroom.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					Tclassroom tclassroom = classroomDAO.get(Tclassroom.class,id);
//					tclassroom.setStatus(Constants.CODE_TYPE_PUBSTATUS_NO); //已发布状态
					classroomDAO.update(tclassroom);
//					if(!util.isEmpty(tclassroom.getPubDate())){
//						tclassroom.setPubUnit(null);
//						tclassroom.setPubDate(null);
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
	public void edit(Classroom classroom) throws Exception {
		
		String id = classroom.getId();
			if(id!=null && !"".equals(id)) {
				Tclassroom tclassroom = classroomDAO.get(Tclassroom.class,id);
//				tclassroom.setClassName(classroom.getClassName());
//				tclassroom.setTrainLevel(classroom.getTrainLevel());
//				tclassroom.setSysLength(classroom.getSysLength());
//				tclassroom.setBuildDate(classroom.getBuildDate());
//				tclassroom.setAdminTeacherId(classroom.getAdminTeacherId());
//				tclassroom.setAdminStuId(classroom.getAdminStuId());
//				tclassroom.setSecStuId(classroom.getSecStuId());
//				tclassroom.setManNum(classroom.getManNum());
//				tclassroom.setWomanNum(classroom.getWomanNum());
//				tclassroom.setLivingNum(classroom.getLivingNum());
//				tclassroom.setProblem(classroom.getProblem());
//				tclassroom.setProSolve(classroom.getProSolve());
				tclassroom.setUptTime(new Date());
				classroomDAO.update(tclassroom);
				//生成日志
				this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[班级]班级修改（班级ID："+id+"）");
		}
	}

	//首页班级统计
	public Map<String, Integer> classroomCount(HttpSession httpSession) {
		return null;
	}

	public BaseDaoI<Tclassroom> getClassroomDAO() {
		return classroomDAO;
	}
	
	
	@Autowired
	public void setClassroomDAO(BaseDaoI<Tclassroom> classroomDAO) {
		this.classroomDAO = classroomDAO;
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
