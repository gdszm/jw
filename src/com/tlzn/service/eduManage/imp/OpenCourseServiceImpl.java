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
import com.tlzn.model.eduManage.TopenCourse;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.eduManage.OpenCourse;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.eduManage.OpenCourseServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Util;

@Service("openCourseService")
public class OpenCourseServiceImpl extends BaseServiceImpl implements OpenCourseServiceI{

	private BaseDaoI<TopenCourse> openCourseDAO;
	/**
	 * 
	 * 查询单条开课
	 * 
	 * @param openCourse
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public OpenCourse get(String id) throws Exception {
		Util util=new Util();
		TopenCourse topenCourse= openCourseDAO.get(TopenCourse.class, id);
		OpenCourse openCourse = new OpenCourse();
		BeanUtils.copyProperties(topenCourse, openCourse);
//		openCourse.setArchNo(topenCourse.getArchives().getArchNo());
//		openCourse.setCid(topenCourse.getTdept().getCid());
//		openCourse.setSex(topenCourse.getArchives().getBasInfoPers().getSex());
//		openCourse.setPicName(topenCourse.getArchives().getBasInfoPers().getPicName());
//		if(!util.isEmpty(topenCourse.getArchives().getBasInfoPers())) {
//			//取老师姓名
//			openCourse.setName(topenCourse.getArchives().getBasInfoPers().getName());
//		}
//		openCourse.setSexName(this.findDicName("SEX",topenCourse.getArchives().getBasInfoPers().getSex()));
//		openCourse.setRankName(this.findDicName("rank",topenCourse.getRank()));
//		openCourse.setMarryName(this.findDicName("MARRY",topenCourse.getMarry()));
//		openCourse.setHasOpenCourseCertName(this.findDicName("hasNo",topenCourse.getHasOpenCourseCert()));
//		openCourse.setEducationName(this.findDicName("EDUCATION",topenCourse.getEducation()));
//		openCourse.setDegreeName(this.findDicName("DEGREE",topenCourse.getDegree()));
			return openCourse;
	}

	/**
	 * 
	 * 条件查询
	 * 
	 * @param openCourse
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(OpenCourse openCourse) throws Exception {
		String hql=" from TopenCourse t where 1=1";
		DataGrid j = new DataGrid();
		j.setRows(editOpenCourses(find(openCourse,hql)));
		j.setTotal(total(openCourse,hql));
		return j;
	}
	private List<OpenCourse> editOpenCourses(List<TopenCourse> openCourseList) throws Exception {
		Util util=new Util();
		List<OpenCourse> openCourses = new ArrayList<OpenCourse>();
		if (openCourseList != null && openCourseList.size() > 0) {
			for (TopenCourse topenCourse: openCourseList) {
				OpenCourse openCourse = new OpenCourse();
				BeanUtils.copyProperties(topenCourse, openCourse);
//				if(!util.isEmpty(topenCourse.getArchives().getBasInfoPers())) {
//					//取老师姓名
//					openCourse.setName(topenCourse.getArchives().getBasInfoPers().getName());
//				}
//				openCourse.setSexName(this.findDicName("SEX",topenCourse.getArchives().getBasInfoPers().getSex()));
//				openCourse.setRankName(this.findDicName("rank",topenCourse.getRank()));
//				openCourse.setMarryName(this.findDicName("MARRY",topenCourse.getMarry()));
//				openCourse.setHasOpenCourseCertName(this.findDicName("hasNo",topenCourse.getHasOpenCourseCert()));
//				openCourse.setEducationName(this.findDicName("EDUCATION",topenCourse.getEducation()));
//				openCourse.setDegreeName(this.findDicName("DEGREE",topenCourse.getDegree()));
//				openCourse.setRemark("");
				openCourses.add(openCourse);
			}
		}
		return openCourses;
	}
	private List<TopenCourse> find(OpenCourse openCourse,String hql) throws Exception {
		hql = addWhere(openCourse, hql);
		if (openCourse.getSort() != null && openCourse.getOrder() != null) {
			hql += " order by " + openCourse.getSort() + " " + openCourse.getOrder();
		}else {
			hql+=" order by t.uptTime desc, t.crtTime desc";
		}
		List<TopenCourse> openCourseList = openCourseDAO.find(hql, openCourse.getPage(), openCourse.getRows());
		return openCourseList;
	}
	private Long total(OpenCourse openCourse,String hql) throws Exception {
		hql = addWhere(openCourse, hql);
		hql = "select count(*) "+hql;
		return openCourseDAO.count(hql);
	}
	private String addWhere(OpenCourse openCourse, String hql) throws Exception {
		Util util=new Util();
		if(!util.isEmpty(openCourse.getId())){
			 hql += " and t.id like '%"+openCourse.getId().trim()+"%'";
		}
//		if(!util.isEmpty(openCourse.getName())){
//			 hql += " and t.archives.basInfoPers.name like '%"+openCourse.getName().trim()+"%'";
//		}
//		if (!util.isEmpty(openCourse.getSex())) {
//			hql += " and t.archives.basInfoPers.sex='" + openCourse.getSex().trim() + "' ";
//		}
//		if (!util.isEmpty(openCourse.getCid())) {
//			hql += " and t.tdept.cid='" + openCourse.getCid().trim() + "' ";
//		}
//		if (!util.isEmpty(openCourse.getArchNo())) {
//			hql += " and t.archives.archNo='" + openCourse.getArchNo().trim() + "' ";
//		}
//		if (!util.isEmpty(openCourse.getRank())) {
//			hql += " and t.rank='" + openCourse.getRank().trim() + "' ";
//		}
//		if (!util.isEmpty(openCourse.getHasOpenCourseCert())) {
//			hql += " and t.hasOpenCourseCert='" + openCourse.getHasOpenCourseCert().trim() + "' ";
//		}
//		if (!util.isEmpty(openCourse.getEducation())) {
//			hql += " and t.education='" + openCourse.getEducation().trim() + "' ";
//		}
//		if(!util.isEmpty(openCourse.getGradFrom())){
//			 hql += " and t.gradFrom like '%"+openCourse.getGradFrom().trim()+"%'";
//		}
//		if(!util.isEmpty(openCourse.getMajorName())){
//			 hql += " and t.majorName like '%"+openCourse.getMajorName().trim()+"%'";
//		}
//		if (!util.isEmpty(openCourse.getDegree())) {
//			hql += " and t.degree='" + openCourse.getDegree().trim() + "' ";
//		}
//		if(!util.isEmpty(openCourse.getRemark())){
//			 hql += " and t.remark like '%"+openCourse.getRemark().trim()+"%'";
//		}
//		//录入系统开始时间
//		if(!util.isEmpty(openCourse.getCrtSTime())){
//			hql+=" and date_format(t.crtTime,'%Y-%m-%d') >= '"+util.getFormatDate(openCourse.getCrtSTime(), "yyyy-MM-dd")+"'";
//		}
//		//录入系统结束时间
//		if(!util.isEmpty(openCourse.getCrtETime())){
//	    	hql+=" and date_format(t.crtTime,'%Y-%m-%d') <= '"+util.getFormatDate(openCourse.getCrtETime(),"yyyy-MM-dd")+"'";
//	    }
		return hql;
	}
	
	/**
	 * 保存开课信息
	 */
	public String save(OpenCourse openCourse,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		TopenCourse topenCourse = new TopenCourse();
		BeanUtils.copyProperties(openCourse, topenCourse);
//		topenCourse.setId(Generator.getInstance().getOpenCourseNO());
//		topenCourse.setCreatDate(new Date());
//		//topenCourse.setPubDate(new Date());
//		topenCourse.setStatus(Constants.CODE_TYPE_PUBSTATUS_NO);
//		//届次
//		Object secoObj=httpSession.getAttribute(ResourceUtil.getSessionSeco());
//		Seco seco=secoObj==null?null:((Seco)secoObj);
//		if(!util.isEmpty(seco)) {
//			Tsecondary ts=new Tsecondary();
//			BeanUtils.copyProperties(seco,ts);
//		}
		
		openCourseDAO.save(topenCourse);
		
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_ADD, topenCourse.getId(), "[开课]保存开课");
		
		return topenCourse.getId();
	}
	/**
	 * 删除开课信息
	 */
	public void del(OpenCourse openCourse) throws Exception {
		
		Util util=new Util();
		String ids = openCourse.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					TopenCourse topenCourse = openCourseDAO.get(TopenCourse.class,id);
					openCourseDAO.delete(topenCourse);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_DEL, id, "[开课]开课删除（开课ID："+id+"）");
				}
			}
		}
	}
	/**
	 * 发布开课信息
	 */
	public void pub(OpenCourse openCourse,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		String ids = openCourse.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					TopenCourse topenCourse = openCourseDAO.get(TopenCourse.class,id);
//					topenCourse.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES); //已发布状态
//					if(util.isEmpty(topenCourse.getPubDate())){
//						String deptId=((SessionInfo) httpSession.getAttribute("sessionInfo")).getDeptId();
//						topenCourse.setPubUnit(deptId);
//						topenCourse.setPubDate(new Date());
//					}
					openCourseDAO.update(topenCourse);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[开课]开课发布（开课ID："+id+"）");
				}
			}
		}
	}
	/**
	 * 取消发布开课信息
	 */
	public void cancelPub(OpenCourse openCourse) throws Exception {
		
		Util util=new Util();
		String ids = openCourse.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					TopenCourse topenCourse = openCourseDAO.get(TopenCourse.class,id);
//					topenCourse.setStatus(Constants.CODE_TYPE_PUBSTATUS_NO); //已发布状态
					openCourseDAO.update(topenCourse);
//					if(!util.isEmpty(topenCourse.getPubDate())){
//						topenCourse.setPubUnit(null);
//						topenCourse.setPubDate(null);
//					}
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[开课]开课取消发布（开课ID："+id+"）");
				}
			}
		}
	}
	/**
	 * 修改开课信息
	 */
	public void edit(OpenCourse openCourse) throws Exception {
		
		String id = openCourse.getId();
			if(id!=null && !"".equals(id)) {
				TopenCourse topenCourse = openCourseDAO.get(TopenCourse.class,id);
				BeanUtils.copyProperties(openCourse, topenCourse, new String[]{"crtTime"});
//				Tarchives arch = archivesDAO.get(Tarchives.class, openCourse.getArchNo());
//				topenCourse.setArchives(arch);
//				Tdept dept=deptDAO.get(Tdept.class, openCourse.getCid());
//				topenCourse.setTdept(dept);
//				topenCourse.getArchives().getBasInfoPers().setPicName(openCourse.getPicName());
				topenCourse.setUptTime(new Date());
			openCourseDAO.update(topenCourse);
			//生成日志
			this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[开课]开课修改（开课ID："+id+"）");
		}
	}

	public BaseDaoI<TopenCourse> getOpenCourseDAO() {
		return openCourseDAO;
	}
	
	@Autowired
	public void setOpenCourseDAO(BaseDaoI<TopenCourse> openCourseDAO) {
		this.openCourseDAO = openCourseDAO;
	}
}
