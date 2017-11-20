package com.tlzn.service.eduManage.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.dailywork.Tnotice;
import com.tlzn.model.eduManage.Tclassroom;
import com.tlzn.model.eduManage.Tcourse;
import com.tlzn.model.eduManage.Tprofess;
import com.tlzn.model.eduManage.TprofessTime;
import com.tlzn.model.eduManage.Tteacher;
import com.tlzn.model.eduManage.TteachingBuilding;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.dailywork.Notice;
import com.tlzn.pageModel.eduManage.Profess;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.eduManage.ProfessServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Generator;
import com.tlzn.util.base.Util;

@Service("professService")
public class ProfessServiceImpl extends BaseServiceImpl implements ProfessServiceI{

	private BaseDaoI<Tprofess> professDAO;
	private BaseDaoI<Tteacher> teacherDAO;
	/**
	 * 
	 * 查询单条授课
	 * 
	 * @param profess
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Profess get(String id) throws Exception {
		Util util=new Util();
		Tprofess tprofess= professDAO.get(Tprofess.class, id);
		Profess profess = new Profess();
		BeanUtils.copyProperties(tprofess, profess);
		//教师号
		profess.setTeacherId(tprofess.getTeacher().getId());
		if(!util.isEmpty(tprofess.getTeacher().getArchives())) {
			//教师姓名
			profess.setTeacherName(tprofess.getTeacher().getArchives().getBasInfoPers().getName());
			//教师性别
			profess.setSex(tprofess.getTeacher().getArchives().getBasInfoPers().getSex());
		}
		
		//课程号
		profess.setCourseId(tprofess.getCourse().getId());
		//课程名称
		profess.setCourseName(tprofess.getCourse().getName());
		//课程名称(英文)
		profess.setEnName(tprofess.getCourse().getEnName());
		//课程属性
		profess.setCourseAtt(tprofess.getCourse().getCourseAtt());
		
		//教室号
		profess.setClassroomId(tprofess.getClassroom().getId());
		//校区名称
		profess.setCampusName(tprofess.getClassroom().getTeachingbuilding().getCampus().getName());
		//教学楼名称
		profess.setTeachingbuildingName(tprofess.getClassroom().getTeachingbuilding().getName());
		//所在楼层
		profess.setFloor(tprofess.getClassroom().getFloor());
		//门牌号
		profess.setHouseNo(tprofess.getClassroom().getHouseNo());
		//教室名称
		profess.setClassroomName(tprofess.getClassroom().getName());
		
		//授课时间ID
		profess.setProfessTimeId(tprofess.getProfesstime().getId());
		//周次 
		profess.setWeeks(tprofess.getProfesstime().getWeeks());
		//星期
		profess.setWeek(tprofess.getProfesstime().getWeek());
		//节次
		profess.setSection(tprofess.getProfesstime().getSection());
		
		//设空值，防止json转化进报错
		if(util.isEmpty(profess.getCourseCon())) profess.setCourseCon(0);          //课容量
		if(util.isEmpty(profess.getCourseSelNum())) profess.setCourseSelNum(0);    //选课人数
		if(util.isEmpty(profess.getCourseSpareNum())) profess.setCourseSpareNum(0);//课余量
		if(util.isEmpty(profess.getFloor())) profess.setFloor(0);                  //所在楼层
		return profess;
	}

	/**
	 * 
	 * 条件查询
	 * 
	 * @param profess
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(Profess profess) throws Exception {
		String hql=" from Tprofess t where 1=1";
		DataGrid j = new DataGrid();
		j.setRows(editProfesss(find(profess,hql)));
		j.setTotal(total(profess,hql));
		return j;
	}
	private List<Profess> editProfesss(List<Tprofess> professList) throws Exception {
		Util util=new Util();
		List<Profess> professs = new ArrayList<Profess>();
		if (professList != null && professList.size() > 0) {
			for (Tprofess tprofess: professList) {
				Profess profess = new Profess();
				if(!util.isEmpty(tprofess.getCourse())) 
					BeanUtils.copyProperties(tprofess.getCourse(), profess);
				if(!util.isEmpty(tprofess.getTeacher())) 
					BeanUtils.copyProperties(tprofess.getTeacher(), profess);
				if(!util.isEmpty(tprofess.getProfesstime())) 
					BeanUtils.copyProperties(tprofess.getProfesstime(), profess);
				BeanUtils.copyProperties(tprofess, profess);
				
				// 教师
				if(!util.isEmpty(tprofess.getTeacher().getId())) {
					//教师编号
					profess.setTeacherId(tprofess.getTeacher().getId());
				}
				//课程
				if(!util.isEmpty(tprofess.getCourse())) {
					//课程号
					profess.setCourseId(tprofess.getCourse().getId());
					//课程名称
					profess.setCourseName(tprofess.getCourse().getName());
					//课程名称(英文)
					profess.setEnName(tprofess.getCourse().getEnName());
					//课程属性名称
					profess.setCourseAttName(this.findDicName("courseAtt", tprofess.getCourse().getCourseAtt()));
				}
				//教室、教学楼、校区设置
				Tclassroom classroom=tprofess.getClassroom();
				if(!util.isEmpty(classroom)) {
					//教室名称
					profess.setClassroomName(classroom.getName());
					//教学楼名称
					TteachingBuilding tb=classroom.getTeachingbuilding();
					if(!util.isEmpty(tb)) {
						profess.setTeachingbuildingName(tb.getName());
						//校区名称
						if(!util.isEmpty(tb.getCampus())) profess.setCampusName(tb.getCampus().getName());
					}
				}
				
				//设空值，防止json转化进报错
				
				//课容量 选课人数 都不为空时
				if(!util.isEmpty(profess.getCourseCon()) && !util.isEmpty(profess.getCourseSelNum())) {
					profess.setCourseSpareNum(profess.getCourseCon().intValue()-profess.getCourseSelNum().intValue()); //课余量
				}
				
				if(util.isEmpty(profess.getCourseCon())) profess.setCourseCon(0);          //课容量
				if(util.isEmpty(profess.getCourseSelNum())) profess.setCourseSelNum(0);    //选课人数
				if(util.isEmpty(profess.getCourseSpareNum())) profess.setCourseSpareNum(0);//课余量
				if(util.isEmpty(profess.getFloor())) profess.setFloor(0);                  //所在楼层
				
				professs.add(profess);
			}
		}
		return professs;
	}
	private List<Tprofess> find(Profess profess,String hql) throws Exception {
		hql = addWhere(profess, hql);
		if (profess.getSort() != null && profess.getOrder() != null) {
			if(profess.getSort().equals("teacherName")) {
				hql += " order by " + "t.teacher.archives.basInfoPers.name" + " " + profess.getOrder();
			}
			if(profess.getSort().equals("courseName")) {
				hql += " order by " + "t.course.name" + " " + profess.getOrder();
			}
//			hql += " order by " + profess.getSort() + " " + profess.getOrder();
		}else {
			hql+=" order by t.uptTime desc, t.crtTime desc";
		}
		List<Tprofess> professList = professDAO.find(hql, profess.getPage(), profess.getRows());
		return professList;
	}
	private Long total(Profess profess,String hql) throws Exception {
		hql = addWhere(profess, hql);
		hql = "select count(*) "+hql;
		return professDAO.count(hql);
	}
	private String addWhere(Profess profess, String hql) throws Exception {
		Util util=new Util();
		
		if(!util.isEmpty(profess.getId())){
			 hql += " and t.id like '%"+profess.getId().trim()+"%'";
		}
		if (!util.isEmpty(profess.getTeacherId())) {
			hql += " and t.teacher.id='" + profess.getTeacherId().trim() + "' ";
		}
				
		if(!util.isEmpty(profess.getTeacherName())){
			 hql += " and t.teacher.archives.basInfoPers.name like '%"+profess.getTeacherName().trim()+"%'";
		}
		if (!util.isEmpty(profess.getSex())) {
			hql += " and t.teacher.archives.basInfoPers.sex='" + profess.getSex().trim() + "' ";
		}
		if(!util.isEmpty(profess.getCourseName())){
			 hql += " and t.course.name like '%"+profess.getCourseName().trim()+"%'";
		}
		if(!util.isEmpty(profess.getEnName())){
			 hql += " and t.course.enName like '%"+profess.getEnName().trim()+"%'";
		}
		if (!util.isEmpty(profess.getCourseAtt())) {
			hql += " and t.course.courseAtt='" + profess.getCourseAtt().trim() + "' ";
		}
		
//		if (!util.isEmpty(profess.getCid())) {
//			hql += " and t.tdept.cid='" + profess.getCid().trim() + "' ";
//		}
//		if (!util.isEmpty(profess.getArchNo())) {
//			hql += " and t.archives.archNo='" + profess.getArchNo().trim() + "' ";
//		}
//		if (!util.isEmpty(profess.getRank())) {
//			hql += " and t.rank='" + profess.getRank().trim() + "' ";
//		}
//		if (!util.isEmpty(profess.getHasProfessCert())) {
//			hql += " and t.hasProfessCert='" + profess.getHasProfessCert().trim() + "' ";
//		}
//		if (!util.isEmpty(profess.getEducation())) {
//			hql += " and t.education='" + profess.getEducation().trim() + "' ";
//		}
//		if(!util.isEmpty(profess.getGradFrom())){
//			 hql += " and t.gradFrom like '%"+profess.getGradFrom().trim()+"%'";
//		}
//		if(!util.isEmpty(profess.getMajorName())){
//			 hql += " and t.majorName like '%"+profess.getMajorName().trim()+"%'";
//		}
//		if (!util.isEmpty(profess.getDegree())) {
//			hql += " and t.degree='" + profess.getDegree().trim() + "' ";
//		}
//		if(!util.isEmpty(profess.getRemark())){
//			 hql += " and t.remark like '%"+profess.getRemark().trim()+"%'";
//		}
//		//录入系统开始时间
//		if(!util.isEmpty(profess.getCrtSTime())){
//			hql+=" and date_format(t.crtTime,'%Y-%m-%d') >= '"+util.getFormatDate(profess.getCrtSTime(), "yyyy-MM-dd")+"'";
//		}
//		//录入系统结束时间
//		if(!util.isEmpty(profess.getCrtETime())){
//	    	hql+=" and date_format(t.crtTime,'%Y-%m-%d') <= '"+util.getFormatDate(profess.getCrtETime(),"yyyy-MM-dd")+"'";
//	    }
		
		hql += " and t.course.id is not null ";

		return hql;
	}
	
	/**
	 * 新增授课教师
	 */
	public String professTeacherAdd(Profess profess,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		Tprofess tprofess = new Tprofess();
		tprofess.setId(Generator.getInstance().getProfessNO());
		Tteacher teacher = teacherDAO.get(Tteacher.class, profess.getTeacherId());
		tprofess.setTeacher(teacher);
		tprofess.setCrtTime(new Date());
		tprofess.setUptTime(new Date());
		professDAO.save(tprofess);
		
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_PROFESS, Constants.LOG_TYPE_OPERTYPE_ADD, tprofess.getId(), "[授课]保存授课");
		
		return tprofess.getId();
	}
	/**
	 * 新增授课
	 */
	public String add(Profess profess,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		
		String teacherId = profess.getTeacherId();
		
		Tprofess tprofess = new Tprofess();
		
		if(!util.isEmpty(teacherId)) {
			
			tprofess.setId(Generator.getInstance().getProfessNO());
			
			//教师
			Tteacher teacher = teacherDAO.get(Tteacher.class, profess.getTeacherId());
			if(!util.isEmpty(teacher)) {
				tprofess.setTeacher(teacher);
			}
			
			//课程
			if(!util.isEmpty(profess.getCourseId())) {
				Tcourse course = new Tcourse();
				course.setId(profess.getCourseId());
				tprofess.setCourse(course);
			}
			//上课教室
			if(!util.isEmpty(profess.getClassroomId())) {
				Tclassroom classroom = new Tclassroom();
				classroom.setId(profess.getClassroomId());
				tprofess.setClassroom(classroom);
			}
			//授课时间
			if(!util.isEmpty(profess.getProfessTimeId())) {
				TprofessTime professtime = new TprofessTime();
				professtime.setId(profess.getProfessTimeId());
				tprofess.setProfesstime(professtime);
			}

			if(util.isEmpty(tprofess.getCourseCon())) tprofess.setCourseCon(0);
			if(util.isEmpty(tprofess.getCourseSelNum())) tprofess.setCourseSelNum(0);
			if(util.isEmpty(tprofess.getCourseSpareNum())) tprofess.setCourseSpareNum(0);
			
			tprofess.setCrtTime(new Date());
			tprofess.setUptTime(new Date());
			
			professDAO.save(tprofess);
			
			//生成日志
			this.saveLog(Constants.LOG_TYPE_CODE_PROFESS, Constants.LOG_TYPE_OPERTYPE_ADD, tprofess.getId(), "[授课]保存授课");
			
		}
		
		return tprofess.getId();
	
	}
	/**
	 * 删除授课信息
	 */
	public void del(Profess profess) throws Exception {
		
		Util util=new Util();
		String ids = profess.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					List list = new ArrayList();
					list.add(id);
					Tprofess tprofess =professDAO.get("from Tprofess t where t.id=?",list);
					if(!util.isEmpty(tprofess)) {
						professDAO.delete(tprofess);
						//生成日志
						this.saveLog(Constants.LOG_TYPE_CODE_PROFESS, Constants.LOG_TYPE_OPERTYPE_DEL, id, "[授课]授课删除（授课ID："+id+"）");
					}
				}
			}
		}
	}
	/**
	 * 删除授课教师
	 */
	public void professTeacherdel(Profess profess) throws Exception {
		
		Util util=new Util();
		String ids = profess.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					List list = new ArrayList();
					list.add(id);
					Tprofess tprofess =professDAO.get("from Tprofess t where t.teacher.id=?",list);
					if(!util.isEmpty(tprofess)) {
						professDAO.delete(tprofess);
						//生成日志
						this.saveLog(Constants.LOG_TYPE_CODE_PROFESS, Constants.LOG_TYPE_OPERTYPE_DEL, id, "[授课]授课删除（授课ID："+id+"）");
					}
				}
			}
		}
	}
	/**
	 * 修改授课信息
	 */
	public void edit(Profess profess) throws Exception {
		
		Util util=new Util();
		String id = profess.getId();
			if(id!=null && !"".equals(id)) {
				Tprofess tprofess = professDAO.get(Tprofess.class,id);
				
				//课程
				if(!util.isEmpty(profess.getCourseId())) {
					Tcourse course = new Tcourse();
					course.setId(profess.getCourseId());
					tprofess.setCourse(course);
				}
				//上课教室
				if(!util.isEmpty(profess.getClassroomId())) {
					Tclassroom classroom = new Tclassroom();
					classroom.setId(profess.getClassroomId());
					tprofess.setClassroom(classroom);
				}
				//授课时间
				if(!util.isEmpty(profess.getProfessTimeId())) {
					TprofessTime professtime = new TprofessTime();
					professtime.setId(profess.getProfessTimeId());
					tprofess.setProfesstime(professtime);
				}
				
				tprofess.setUptTime(new Date());
//				BeanUtils.copyProperties(profess, tprofess, new String[]{"crtTime"});
//				Tarchives arch = archivesDAO.get(Tarchives.class, profess.getArchNo());
//				tprofess.setArchives(arch);
//				Tdept dept=deptDAO.get(Tdept.class, profess.getCid());
//				tprofess.setTdept(dept);
//				tprofess.getArchives().getBasInfoPers().setPicName(profess.getPicName());
				
			professDAO.update(tprofess);
			//生成日志
			this.saveLog(Constants.LOG_TYPE_CODE_PROFESS, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[授课]授课修改（授课ID："+id+"）");
		}
	}

	
	/**
	 * 
	 * 条件查询
	 * 
	 * @param profess
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagridCount(Profess profess) throws Exception {
		String hql="select t.teacher.id as teacherId" +
				",t.teacher.archives.basInfoPers.name as teacherName" +
				",t.teacher.archives.basInfoPers.sex as sex" +
				",count(t.course.id) as courseSu" +
				" from Tprofess t where 1=1";
		DataGrid j = new DataGrid();
		j.setRows(editProfesssCount(countFind(profess,hql)));
		List professList = j.getRows();
		if(null!=professList && professList.size()>0) {
			j.setTotal(Long.parseLong(String.valueOf(professList.size())));
		}
		return j;
	}
	private List<Tprofess> countFind(Profess profess,String hql) throws Exception {
		hql = addCountWhere(profess, hql);
		if ("teacherName".equals(profess.getSort()) && profess.getOrder() != null) {
			hql += " order by t.teacher.archives.basInfoPers.name "+profess.getOrder()+" ";
		} else if ("sexName".equals(profess.getSort()) && profess.getOrder() != null) {
			hql += " order by t.teacher.archives.basInfoPers.sex "+profess.getOrder()+" ";
		} else if ("courseSu".equals(profess.getSort()) && profess.getOrder() != null) {
			hql += " order by courseSu "+profess.getOrder()+" ";
		} else {
			hql+=" order by t.teacher.archives.basInfoPers.id";
		}
		List<Tprofess> professList = professDAO.find(hql, profess.getPage(), profess.getRows());
		return professList;
	}
	private String addCountWhere(Profess profess, String hql) throws Exception {
		Util util=new Util();
		if(!util.isEmpty(profess.getTeacherId())){
			 hql += " and t.teacher.id like '%"+profess.getTeacherId().trim()+"%'";
		}
		if(!util.isEmpty(profess.getTeacherName())){
			 hql += " and t.teacher.archives.basInfoPers.name like '%"+profess.getTeacherName().trim()+"%'";
		}
		if (!util.isEmpty(profess.getSex())) {
			hql += " and t.teacher.archives.basInfoPers.sex='" + profess.getSex() + "'";
		}
		hql += " group by t.teacher.archives.basInfoPers.id,t.teacher.archives.basInfoPers.name ";
		if (!util.isEmpty(profess.getCourseSu())) {
			hql += " having count(t.course.id) = " + profess.getCourseSu();
		}
		if(!util.isEmpty(profess.getCourseSuStart()) || !util.isEmpty(profess.getCourseSuEnd())){
			hql += " having 1=1 ";
		}
		if(!util.isEmpty(profess.getCourseSuStart())){
			hql += " and count(t.course.id) >= " + profess.getCourseSuStart();
		}
		if(!util.isEmpty(profess.getCourseSuEnd())){
			hql += " and count(t.course.id) <= " + profess.getCourseSuEnd();
		}
		return hql;
	}
	
	private List<Profess> editProfesssCount(List<Tprofess> professList) throws Exception {
		Util util=new Util();
		List<Profess> professsList = new ArrayList<Profess>();
		if (professList != null && professList.size() > 0) {
			for (int i = 0; i < professList.size(); i++) {
				Object obj =(Object)professList.get(i);
				Object[] objArray = (Object[])obj;
				if (null != objArray && objArray.length > 0) {
					Profess profess = new Profess();
					profess.setTeacherId(null==objArray[0]?"":objArray[0].toString());
					profess.setTeacherName(null==objArray[1]?"":objArray[1].toString());
					String sex= null==objArray[2]?"":objArray[2].toString();
					profess.setSex(sex);
					profess.setSexName(this.findDicName("SEX", sex));
					profess.setCourseSu(null==objArray[3]?"":objArray[3].toString());
					
					//设空值，防止json转化进报错
					profess.setCourseCon(0);
					profess.setCourseSelNum(0);
					profess.setCourseSpareNum(0);
					profess.setFloor(0);
					professsList.add(profess);
				}
			}
		}
		return professsList;
	}
	public BaseDaoI<Tprofess> getProfessDAO() {
		return professDAO;
	}
	
	@Autowired
	public void setProfessDAO(BaseDaoI<Tprofess> professDAO) {
		this.professDAO = professDAO;
	}

	public BaseDaoI<Tteacher> getTeacherDAO() {
		return teacherDAO;
	}
	@Autowired
	public void setTeacherDAO(BaseDaoI<Tteacher> teacherDAO) {
		this.teacherDAO = teacherDAO;
	}
}
