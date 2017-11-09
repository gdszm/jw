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
import com.tlzn.model.eduManage.Tarchives;
import com.tlzn.model.eduManage.Tteacher;
import com.tlzn.model.sys.Tdept;
import com.tlzn.model.sys.Tsecondary;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.eduManage.Teacher;
import com.tlzn.pageModel.sys.Seco;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.eduManage.TeacherServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Generator;
import com.tlzn.util.base.ResourceUtil;
import com.tlzn.util.base.Util;

@Service("teacherService")
public class TeacherServiceImpl extends BaseServiceImpl implements TeacherServiceI{

	private BaseDaoI<Tteacher> teacherDAO;
	private BaseDaoI<Tarchives> archivesDAO;
	private BaseDaoI<Tdept> deptDAO;
	/**
	 * 
	 * 查询单条教师
	 * 
	 * @param teacher
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Teacher get(String id) throws Exception {
		Util util=new Util();
		Tteacher tteacher= teacherDAO.get(Tteacher.class, id);
		Teacher teacher = new Teacher();
		BeanUtils.copyProperties(tteacher, teacher);
		teacher.setArchNo(tteacher.getArchives().getArchNo());
		teacher.setCid(tteacher.getTdept().getCid());
		if(!util.isEmpty(teacher.getCid())) {
			teacher.setCname(this.findNameByDepid(teacher.getCid()));
		}
		
		teacher.setSex(tteacher.getArchives().getBasInfoPers().getSex());
		if(!util.isEmpty(tteacher.getArchives().getBasInfoPers())) {
			//取老师姓名
			teacher.setName(tteacher.getArchives().getBasInfoPers().getName());
		}
		teacher.setSexName(this.findDicName("SEX",tteacher.getArchives().getBasInfoPers().getSex()));

		teacher.setRankName(this.findDicName("rank",tteacher.getRank()));
		teacher.setMarryName(this.findDicName("MARRY",tteacher.getMarry()));
		teacher.setHasTeacherCertName(this.findDicName("hasNo",tteacher.getHasTeacherCert()));
		teacher.setEducationName(this.findDicName("EDUCATION",tteacher.getEducation()));
		teacher.setDegreeName(this.findDicName("DEGREE",tteacher.getDegree()));

//	teacher.setRankName(this.findDicName("rank",tteacher.getRank()));
//		teacher.setMarryName(this.findDicName("MARRY",tteacher.getMarry()));
//		teacher.setHasTeacherCertName(this.findDicName("hasNo",tteacher.getHasTeacherCert()));
//		teacher.setEducationName(this.findDicName("EDUCATION",tteacher.getEducation()));
//		teacher.setDegreeName(this.findDicName("DEGREE",tteacher.getDegree()));
			return teacher;
	}

	/**
	 * 
	 * 条件查询
	 * 
	 * @param teacher
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(Teacher teacher) throws Exception {
		String hql=" from Tteacher t where 1=1";
		DataGrid j = new DataGrid();
		j.setRows(editTeachers(find(teacher,hql)));
		j.setTotal(total(teacher,hql));
		return j;
	}
	private List<Teacher> editTeachers(List<Tteacher> teacherList) throws Exception {
		Util util=new Util();
		List<Teacher> teachers = new ArrayList<Teacher>();
		if (teacherList != null && teacherList.size() > 0) {
			for (Tteacher tteacher: teacherList) {
				Teacher teacher = new Teacher();
				BeanUtils.copyProperties(tteacher, teacher);
				if(!util.isEmpty(tteacher.getArchives().getBasInfoPers())) {
					//取老师姓名
					teacher.setName(tteacher.getArchives().getBasInfoPers().getName());
				}
				teacher.setSexName(this.findDicName("SEX",tteacher.getArchives().getBasInfoPers().getSex()));
				teacher.setRankName(this.findDicName("rank",tteacher.getRank()));
				teacher.setMarryName(this.findDicName("MARRY",tteacher.getMarry()));
				teacher.setHasTeacherCertName(this.findDicName("hasNo",tteacher.getHasTeacherCert()));
				teacher.setEducationName(this.findDicName("EDUCATION",tteacher.getEducation()));
				teacher.setDegreeName(this.findDicName("DEGREE",tteacher.getDegree()));
				teacher.setRemark("");
				teachers.add(teacher);
			}
		}
		return teachers;
	}
	
	private List<Tteacher> find(Teacher teacher,String hql) throws Exception {
		hql = addWhere(teacher, hql);
		if (teacher.getSort() != null && teacher.getOrder() != null) {
			hql += " order by " + teacher.getSort() + " " + teacher.getOrder();
		}else {
			hql+=" order by t.uptTime desc, t.crtTime desc";
		}
		List<Tteacher> teacherList = teacherDAO.find(hql, teacher.getPage(), teacher.getRows());
		return teacherList;
	}
	private Long total(Teacher teacher,String hql) throws Exception {
		hql = addWhere(teacher, hql);
		hql = "select count(*) "+hql;
		return teacherDAO.count(hql);
	}
	private String addWhere(Teacher teacher, String hql) throws Exception {
		Util util=new Util();
		if(!util.isEmpty(teacher.getId())){
			 hql += " and t.id like '%"+teacher.getId().trim()+"%'";
		}
		if(!util.isEmpty(teacher.getName())){
			 hql += " and t.archives.basInfoPers.name like '%"+teacher.getName().trim()+"%'";
		}
		if (!util.isEmpty(teacher.getSex())) {
			hql += " and t.archives.basInfoPers.sex='" + teacher.getSex().trim() + "' ";
		}
		if (!util.isEmpty(teacher.getCid())) {
			hql += " and t.tdept.cid='" + teacher.getCid().trim() + "' ";
		}
		if (!util.isEmpty(teacher.getArchNo())) {
			hql += " and t.archives.archNo='" + teacher.getArchNo().trim() + "' ";
		}
		if (!util.isEmpty(teacher.getRank())) {
			hql += " and t.rank='" + teacher.getRank().trim() + "' ";
		}
		if (!util.isEmpty(teacher.getHasTeacherCert())) {
			hql += " and t.hasTeacherCert='" + teacher.getHasTeacherCert().trim() + "' ";
		}
		if (!util.isEmpty(teacher.getEducation())) {
			hql += " and t.education='" + teacher.getEducation().trim() + "' ";
		}
		if(!util.isEmpty(teacher.getGradFrom())){
			 hql += " and t.gradFrom like '%"+teacher.getGradFrom().trim()+"%'";
		}
		if(!util.isEmpty(teacher.getMajorName())){
			 hql += " and t.majorName like '%"+teacher.getMajorName().trim()+"%'";
		}
		if (!util.isEmpty(teacher.getDegree())) {
			hql += " and t.degree='" + teacher.getDegree().trim() + "' ";
		}
		if(!util.isEmpty(teacher.getRemark())){
			 hql += " and t.remark like '%"+teacher.getRemark().trim()+"%'";
		}
		//录入系统开始时间
		if(!util.isEmpty(teacher.getCrtSTime())){
			hql+=" and date_format(t.crtTime,'%Y-%m-%d') >= '"+util.getFormatDate(teacher.getCrtSTime(), "yyyy-MM-dd")+"'";
		}
		//录入系统结束时间
		if(!util.isEmpty(teacher.getCrtETime())){
	    	hql+=" and date_format(t.crtTime,'%Y-%m-%d') <= '"+util.getFormatDate(teacher.getCrtETime(),"yyyy-MM-dd")+"'";
	    }
		return hql;
	}
	
	/**
	 * 保存教师信息
	 */
	public String add(Teacher teacher,HttpSession httpSession) throws Exception {
		
		Tteacher tteacher = new Tteacher();
		BeanUtils.copyProperties(teacher, tteacher);
		tteacher.setId(Generator.getInstance().getTeacherNO());
		
		Tarchives arch = archivesDAO.get(Tarchives.class, teacher.getArchNo());
		tteacher.setArchives(arch);
		Tdept dept=deptDAO.get(Tdept.class, teacher.getCid());
		tteacher.setTdept(dept);
		//tteacher.getArchives().getBasInfoPers().setPicName(teacher.getPicName());
		
		tteacher.setCrtTime(new Date());
		tteacher.setUptTime(new Date());
		
		teacherDAO.save(tteacher);
		
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_TEACHER, Constants.LOG_TYPE_OPERTYPE_ADD, tteacher.getId(), "[教师]保存教师");
		
		return tteacher.getId();
	}

	/**
	 * 删除教师信息
	 */
	public void del(Teacher teacher) throws Exception {
		
		Util util=new Util();
		String ids = teacher.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					Tteacher tteacher = teacherDAO.get(Tteacher.class,id);
					teacherDAO.delete(tteacher);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_TEACHER, Constants.LOG_TYPE_OPERTYPE_DEL, id, "[教师]教师删除（教师ID："+id+"）");
				}
			}
		}
	}
	/**
	 * 发布教师信息
	 */
	public void pub(Teacher teacher,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		String ids = teacher.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					Tteacher tteacher = teacherDAO.get(Tteacher.class,id);
//					tteacher.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES); //已发布状态
//					if(util.isEmpty(tteacher.getPubDate())){
//						String deptId=((SessionInfo) httpSession.getAttribute("sessionInfo")).getDeptId();
//						tteacher.setPubUnit(deptId);
//						tteacher.setPubDate(new Date());
//					}
					teacherDAO.update(tteacher);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_TEACHER, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[教师]教师发布（教师ID："+id+"）");
				}
			}
		}
	}
	/**
	 * 取消发布教师信息
	 */
	public void cancelPub(Teacher teacher) throws Exception {
		
		Util util=new Util();
		String ids = teacher.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					Tteacher tteacher = teacherDAO.get(Tteacher.class,id);
//					tteacher.setStatus(Constants.CODE_TYPE_PUBSTATUS_NO); //已发布状态
					teacherDAO.update(tteacher);
//					if(!util.isEmpty(tteacher.getPubDate())){
//						tteacher.setPubUnit(null);
//						tteacher.setPubDate(null);
//					}
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_TEACHER, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[教师]教师取消发布（教师ID："+id+"）");
				}
			}
		}
	}
	/**
	 * 修改教师信息
	 */
	public void edit(Teacher teacher) throws Exception {
		
		String id = teacher.getId();
			if(id!=null && !"".equals(id)) {
				Tteacher tteacher = teacherDAO.get(Tteacher.class,id);
				BeanUtils.copyProperties(teacher, tteacher, new String[]{"crtTime"});
				Tarchives arch = archivesDAO.get(Tarchives.class, teacher.getArchNo());
				tteacher.setArchives(arch);
				Tdept dept=deptDAO.get(Tdept.class, teacher.getCid());
				tteacher.setTdept(dept);
				//tteacher.getArchives().getBasInfoPers().setPicName(teacher.getPicName());
				tteacher.setUptTime(new Date());
			teacherDAO.update(tteacher);
			//生成日志
			this.saveLog(Constants.LOG_TYPE_CODE_TEACHER, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[教师]教师修改（教师ID："+id+"）");
		}
	}

	public BaseDaoI<Tteacher> getTeacherDAO() {
		return teacherDAO;
	}
	
	
	@Autowired
	public void setTeacherDAO(BaseDaoI<Tteacher> teacherDAO) {
		this.teacherDAO = teacherDAO;
	}

	public BaseDaoI<Tarchives> getArchivesDAO() {
		return archivesDAO;
	}
	@Autowired
	public void setArchivesDAO(BaseDaoI<Tarchives> archivesDAO) {
		this.archivesDAO = archivesDAO;
	}

	public BaseDaoI<Tdept> getDeptDAO() {
		return deptDAO;
	}
	@Autowired
	public void setDeptDAO(BaseDaoI<Tdept> deptDAO) {
		this.deptDAO = deptDAO;
	}
}
