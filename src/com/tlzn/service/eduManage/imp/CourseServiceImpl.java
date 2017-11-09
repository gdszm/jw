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
import com.tlzn.model.eduManage.Tcourse;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.eduManage.Course;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.eduManage.CourseServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Generator;
import com.tlzn.util.base.Util;

@Service("courseService")
public class CourseServiceImpl extends BaseServiceImpl implements CourseServiceI{

	private BaseDaoI<Tcourse> courseDAO;
	/**
	 * 
	 * 查询单条课程
	 * 
	 * @param course
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Course get(String id) throws Exception {
		Tcourse tcourse= courseDAO.get(Tcourse.class, id);
		Course course = new Course();
//		BeanUtils.copyProperties(tcourse.getArchives().getBasInfoPers(), course);
		BeanUtils.copyProperties(tcourse, course);
		course.setCourseAttName(this.findDicName("courseAtt", tcourse.getCourseAtt()));
//		course.setClassId(tcourse.getClasses().getId());
//		course.setClassName((tcourse.getClasses().getClassName()));
//		course.setArchNo(tcourse.getArchives().getArchNo());
//		course.setSexName(this.findDicName("SEX", tcourse.getArchives().getBasInfoPers().getSex()));
//		course.setDeptName(this.findDicName("yxb", tcourse.getDept()));
//		course.setMajorName(this.findDicName("major", tcourse.getMajor()));
//		course.setEnSourName(this.findDicName("enSour", tcourse.getEnSour()));
//		course.setPriProName(this.findDicName("priPro", tcourse.getPriPro()));
		return course;
	}
	
	/**
	 * 
	 * 条件查询
	 * 
	 * @param course
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(Course course) throws Exception {
		String hql=" from Tcourse t where 1=1";
		DataGrid j = new DataGrid();
		j.setRows(editCourses(find(course,hql)));
		j.setTotal(total(course,hql));
		return j;
	}
	private List<Course> editCourses(List<Tcourse> courseList) throws Exception {
		List<Course> courses = new ArrayList<Course>();
		if (courseList != null && courseList.size() > 0) {
			for (Tcourse tcourse: courseList) {
				Course course= new Course();
				editCourse(tcourse,course);
				courses.add(course);
			}
		}
		return courses;
	}
	private Course editCourse(Tcourse tcourse,Course course) throws Exception {
		BeanUtils.copyProperties(tcourse, course);
		course.setCourseAttName(this.findDicName("courseAtt", tcourse.getCourseAtt()));
		course.setTeachMeth("");
		course.setTeachBook("");
		course.setAsMethAnReq("");
		course.setComment("");
//		BeanUtils.copyProperties(tcourse.getArchives().getBasInfoPers(), course);
//		course.setDept(this.findDicName("yxb", tcourse.getDept()));
//		course.setMajor(this.findDicName("major", tcourse.getMajor()));
//		course.setEnSour(this.findDicName("enSour", tcourse.getEnSour()));
//		course.setPriPro(this.findDicName("priPro", tcourse.getPriPro()));
//		course.setCompuLevel(this.findDicName("compuLevel", tcourse.getArchives().getBasInfoPers().getCompuLevel()));
//		course.setForeignType(this.findDicName("foreignType", tcourse.getArchives().getBasInfoPers().getForeignType()));
//		course.setSex(this.findDicName("SEX", tcourse.getArchives().getBasInfoPers().getSex()));
//		course.setPriPro(this.findDicName("priPro", tcourse.getPriPro()));
//		course.setClassName(tcourse.getClasses().getClassName());
//		course.setArchNo(tcourse.getArchives().getArchNo());
//		course.setHeight(0);
//		course.setWeight(0);
//		course.setPeoNum(0);
//		course.setEcoTotal(0);
//		course.setRemark("");
		return course;
	}
	private List<Tcourse> find(Course course,String hql) throws Exception {
		hql = addWhere(course, hql);
		if (course.getSort() != null && course.getOrder() != null) {
			hql += " order by " + course.getSort() + " " + course.getOrder();
		}else {
			hql+=" order by t.uptTime desc, t.crtTime desc";
		}
		List<Tcourse> courseList = courseDAO.find(hql, course.getPage(), course.getRows());
		return courseList;
	}
	private Long total(Course course,String hql) throws Exception {
		hql = addWhere(course, hql);
		hql = "select count(*) "+hql;
		return courseDAO.count(hql);
	}
	private String addWhere(Course course, String hql) throws Exception {
		Util util=new Util();
		//课程号
		if (!util.isEmpty(course.getId())) {
			hql += " and t.id='" + course.getId().trim() + "' ";
		}
		//课程名称
		if(!util.isEmpty(course.getName())){
			hql += " and t.name like '%"+course.getName().trim()+"%'";
		}
		//课程英文名称
		if(!util.isEmpty(course.getEnName())){
			hql += " and t.enName like '%"+course.getEnName().trim()+"%'";
		}
		//课程序号
		if (!util.isEmpty(course.getSortNo())) {
			hql += " and t.sortNo='" + course.getSortNo().trim() + "' ";
		}
		//课程学分
		if (!util.isEmpty(course.getCredit())) {
			hql += " and t.credit='" + course.getCredit().trim() + "' ";
		}
		//课程属性
		if (!util.isEmpty(course.getCourseAtt())) {
			hql += " and t.courseAtt='" + course.getCourseAtt().trim() + "' ";
		}
		//教学方法
		if(!util.isEmpty(course.getTeachMeth())){
			hql += " and t.teachMeth like '%"+course.getTeachMeth().trim()+"%'";
		}
		//使用教材
		if(!util.isEmpty(course.getTeachBook())){
			hql += " and t.teachBook like '%"+course.getTeachBook().trim()+"%'";
		}
		//考核方式及其要求
		if(!util.isEmpty(course.getAsMethAnReq())){
			hql += " and t.asMethAnReq like '%"+course.getAsMethAnReq().trim()+"%'";
		}
		//备注
		if(!util.isEmpty(course.getComment())){
			hql += " and t.comment like '%"+course.getComment().trim()+"%'";
		}
		return hql;
	}
	
	/**
	 * 保存课程信息
	 */
	public String save(Course course,HttpSession httpSession) throws Exception {
		
		Tcourse tcourse = new Tcourse();
		BeanUtils.copyProperties(course, tcourse);
		tcourse.setId(Generator.getInstance().getCourseNO());
		tcourse.setCrtTime(new Date());
		tcourse.setUptTime(new Date());
		courseDAO.save(tcourse);
		
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_COURSE, Constants.LOG_TYPE_OPERTYPE_ADD, tcourse.getId(), "[课程]保存课程");
		
		return tcourse.getId();
	}
	/**
	 * 删除课程信息
	 */
	public void del(Course course) throws Exception {
		
		Util util=new Util();
		String ids = course.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					Tcourse tcourse = courseDAO.get(Tcourse.class,id);
					courseDAO.delete(tcourse);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_COURSE, Constants.LOG_TYPE_OPERTYPE_DEL, id, "[课程]课程删除（课程ID："+id+"）");
				}
			}
		}
	}
	/**
	 * 发布课程信息
	 */
	public void pub(Course course,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		String ids = course.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					Tcourse tcourse = courseDAO.get(Tcourse.class,id);
//					tcourse.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES); //已发布状态
//					if(util.isEmpty(tcourse.getPubDate())){
//						String deptId=((SessionInfo) httpSession.getAttribute("sessionInfo")).getDeptId();
//						tcourse.setPubUnit(deptId);
//						tcourse.setPubDate(new Date());
//					}
					courseDAO.update(tcourse);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[课程]课程发布（课程ID："+id+"）");
				}
			}
		}
	}
	/**
	 * 取消发布课程信息
	 */
	public void cancelPub(Course course) throws Exception {
		
		Util util=new Util();
		String ids = course.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					Tcourse tcourse = courseDAO.get(Tcourse.class,id);
//					tcourse.setStatus(Constants.CODE_TYPE_PUBSTATUS_NO); //已发布状态
					courseDAO.update(tcourse);
//					if(!util.isEmpty(tcourse.getPubDate())){
//						tcourse.setPubUnit(null);
//						tcourse.setPubDate(null);
//					}
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[课程]课程取消发布（课程ID："+id+"）");
				}
			}
		}
	}
	/**
	 * 修改课程信息
	 */
	public void edit(Course course) throws Exception {
		
		String id = course.getId();
			if(id!=null && !"".equals(id)) {
			Tcourse tcourse = courseDAO.get(Tcourse.class,id);
			BeanUtils.copyProperties(course, tcourse, new String[]{"id"});
			tcourse.setUptTime(new Date());
			courseDAO.update(tcourse);
			//生成日志
			this.saveLog(Constants.LOG_TYPE_CODE_COURSE, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[课程]课程修改（课程ID："+id+"）");
		}
	}
	public BaseDaoI<Tcourse> getCourseDAO() {
		return courseDAO;
	}
	
	@Autowired
	public void setCourseDAO(BaseDaoI<Tcourse> courseDAO) {
		this.courseDAO = courseDAO;
	}
}
