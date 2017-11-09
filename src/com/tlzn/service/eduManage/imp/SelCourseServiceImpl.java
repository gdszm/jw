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
import com.tlzn.model.eduManage.TselCourse;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.eduManage.SelCourse;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.eduManage.SelCourseServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Util;

@Service("selCourseService")
public class SelCourseServiceImpl extends BaseServiceImpl implements SelCourseServiceI{

	private BaseDaoI<TselCourse> selCourseDAO;
	/**
	 * 
	 * 查询单条选课
	 * 
	 * @param selCourse
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public SelCourse get(String id) throws Exception {
		Util util=new Util();
		TselCourse tselCourse= selCourseDAO.get(TselCourse.class, id);
		SelCourse selCourse = new SelCourse();
		BeanUtils.copyProperties(tselCourse, selCourse);
//		selCourse.setArchNo(tselCourse.getArchives().getArchNo());
//		selCourse.setCid(tselCourse.getTdept().getCid());
//		selCourse.setSex(tselCourse.getArchives().getBasInfoPers().getSex());
//		selCourse.setPicName(tselCourse.getArchives().getBasInfoPers().getPicName());
//		if(!util.isEmpty(tselCourse.getArchives().getBasInfoPers())) {
//			//取老师姓名
//			selCourse.setName(tselCourse.getArchives().getBasInfoPers().getName());
//		}
//		selCourse.setSexName(this.findDicName("SEX",tselCourse.getArchives().getBasInfoPers().getSex()));
//		selCourse.setRankName(this.findDicName("rank",tselCourse.getRank()));
//		selCourse.setMarryName(this.findDicName("MARRY",tselCourse.getMarry()));
//		selCourse.setHasSelCourseCertName(this.findDicName("hasNo",tselCourse.getHasSelCourseCert()));
//		selCourse.setEducationName(this.findDicName("EDUCATION",tselCourse.getEducation()));
//		selCourse.setDegreeName(this.findDicName("DEGREE",tselCourse.getDegree()));
			return selCourse;
	}

	/**
	 * 
	 * 条件查询
	 * 
	 * @param selCourse
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(SelCourse selCourse) throws Exception {
		String hql=" from TselCourse t where 1=1";
		DataGrid j = new DataGrid();
		j.setRows(editSelCourses(find(selCourse,hql)));
		j.setTotal(total(selCourse,hql));
		return j;
	}
	private List<SelCourse> editSelCourses(List<TselCourse> selCourseList) throws Exception {
		Util util=new Util();
		List<SelCourse> selCourses = new ArrayList<SelCourse>();
		if (selCourseList != null && selCourseList.size() > 0) {
			for (TselCourse tselCourse: selCourseList) {
				SelCourse selCourse = new SelCourse();
				BeanUtils.copyProperties(tselCourse, selCourse);
//				if(!util.isEmpty(tselCourse.getArchives().getBasInfoPers())) {
//					//取老师姓名
//					selCourse.setName(tselCourse.getArchives().getBasInfoPers().getName());
//				}
//				selCourse.setSexName(this.findDicName("SEX",tselCourse.getArchives().getBasInfoPers().getSex()));
//				selCourse.setRankName(this.findDicName("rank",tselCourse.getRank()));
//				selCourse.setMarryName(this.findDicName("MARRY",tselCourse.getMarry()));
//				selCourse.setHasSelCourseCertName(this.findDicName("hasNo",tselCourse.getHasSelCourseCert()));
//				selCourse.setEducationName(this.findDicName("EDUCATION",tselCourse.getEducation()));
//				selCourse.setDegreeName(this.findDicName("DEGREE",tselCourse.getDegree()));
//				selCourse.setRemark("");
				selCourses.add(selCourse);
			}
		}
		return selCourses;
	}
	private List<TselCourse> find(SelCourse selCourse,String hql) throws Exception {
		hql = addWhere(selCourse, hql);
		if (selCourse.getSort() != null && selCourse.getOrder() != null) {
			hql += " order by " + selCourse.getSort() + " " + selCourse.getOrder();
		}else {
			hql+=" order by t.uptTime desc, t.crtTime desc";
		}
		List<TselCourse> selCourseList = selCourseDAO.find(hql, selCourse.getPage(), selCourse.getRows());
		return selCourseList;
	}
	private Long total(SelCourse selCourse,String hql) throws Exception {
		hql = addWhere(selCourse, hql);
		hql = "select count(*) "+hql;
		return selCourseDAO.count(hql);
	}
	private String addWhere(SelCourse selCourse, String hql) throws Exception {
		Util util=new Util();
		if(!util.isEmpty(selCourse.getId())){
			 hql += " and t.id like '%"+selCourse.getId().trim()+"%'";
		}
//		if(!util.isEmpty(selCourse.getName())){
//			 hql += " and t.archives.basInfoPers.name like '%"+selCourse.getName().trim()+"%'";
//		}
//		if (!util.isEmpty(selCourse.getSex())) {
//			hql += " and t.archives.basInfoPers.sex='" + selCourse.getSex().trim() + "' ";
//		}
//		if (!util.isEmpty(selCourse.getCid())) {
//			hql += " and t.tdept.cid='" + selCourse.getCid().trim() + "' ";
//		}
//		if (!util.isEmpty(selCourse.getArchNo())) {
//			hql += " and t.archives.archNo='" + selCourse.getArchNo().trim() + "' ";
//		}
//		if (!util.isEmpty(selCourse.getRank())) {
//			hql += " and t.rank='" + selCourse.getRank().trim() + "' ";
//		}
//		if (!util.isEmpty(selCourse.getHasSelCourseCert())) {
//			hql += " and t.hasSelCourseCert='" + selCourse.getHasSelCourseCert().trim() + "' ";
//		}
//		if (!util.isEmpty(selCourse.getEducation())) {
//			hql += " and t.education='" + selCourse.getEducation().trim() + "' ";
//		}
//		if(!util.isEmpty(selCourse.getGradFrom())){
//			 hql += " and t.gradFrom like '%"+selCourse.getGradFrom().trim()+"%'";
//		}
//		if(!util.isEmpty(selCourse.getMajorName())){
//			 hql += " and t.majorName like '%"+selCourse.getMajorName().trim()+"%'";
//		}
//		if (!util.isEmpty(selCourse.getDegree())) {
//			hql += " and t.degree='" + selCourse.getDegree().trim() + "' ";
//		}
//		if(!util.isEmpty(selCourse.getRemark())){
//			 hql += " and t.remark like '%"+selCourse.getRemark().trim()+"%'";
//		}
//		//录入系统开始时间
//		if(!util.isEmpty(selCourse.getCrtSTime())){
//			hql+=" and date_format(t.crtTime,'%Y-%m-%d') >= '"+util.getFormatDate(selCourse.getCrtSTime(), "yyyy-MM-dd")+"'";
//		}
//		//录入系统结束时间
//		if(!util.isEmpty(selCourse.getCrtETime())){
//	    	hql+=" and date_format(t.crtTime,'%Y-%m-%d') <= '"+util.getFormatDate(selCourse.getCrtETime(),"yyyy-MM-dd")+"'";
//	    }
		return hql;
	}
	
	/**
	 * 保存选课信息
	 */
	public String save(SelCourse selCourse,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		TselCourse tselCourse = new TselCourse();
		BeanUtils.copyProperties(selCourse, tselCourse);
//		tselCourse.setId(Generator.getInstance().getSelCourseNO());
//		tselCourse.setCreatDate(new Date());
//		//tselCourse.setPubDate(new Date());
//		tselCourse.setStatus(Constants.CODE_TYPE_PUBSTATUS_NO);
//		//届次
//		Object secoObj=httpSession.getAttribute(ResourceUtil.getSessionSeco());
//		Seco seco=secoObj==null?null:((Seco)secoObj);
//		if(!util.isEmpty(seco)) {
//			Tsecondary ts=new Tsecondary();
//			BeanUtils.copyProperties(seco,ts);
//		}
		
		selCourseDAO.save(tselCourse);
		
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_ADD, tselCourse.getId(), "[选课]保存选课");
		
		return tselCourse.getId();
	}
	/**
	 * 删除选课信息
	 */
	public void del(SelCourse selCourse) throws Exception {
		
		Util util=new Util();
		String ids = selCourse.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					TselCourse tselCourse = selCourseDAO.get(TselCourse.class,id);
					selCourseDAO.delete(tselCourse);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_DEL, id, "[选课]选课删除（选课ID："+id+"）");
				}
			}
		}
	}
	/**
	 * 发布选课信息
	 */
	public void pub(SelCourse selCourse,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		String ids = selCourse.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					TselCourse tselCourse = selCourseDAO.get(TselCourse.class,id);
//					tselCourse.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES); //已发布状态
//					if(util.isEmpty(tselCourse.getPubDate())){
//						String deptId=((SessionInfo) httpSession.getAttribute("sessionInfo")).getDeptId();
//						tselCourse.setPubUnit(deptId);
//						tselCourse.setPubDate(new Date());
//					}
					selCourseDAO.update(tselCourse);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[选课]选课发布（选课ID："+id+"）");
				}
			}
		}
	}
	/**
	 * 取消发布选课信息
	 */
	public void cancelPub(SelCourse selCourse) throws Exception {
		
		Util util=new Util();
		String ids = selCourse.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					TselCourse tselCourse = selCourseDAO.get(TselCourse.class,id);
//					tselCourse.setStatus(Constants.CODE_TYPE_PUBSTATUS_NO); //已发布状态
					selCourseDAO.update(tselCourse);
//					if(!util.isEmpty(tselCourse.getPubDate())){
//						tselCourse.setPubUnit(null);
//						tselCourse.setPubDate(null);
//					}
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[选课]选课取消发布（选课ID："+id+"）");
				}
			}
		}
	}
	/**
	 * 修改选课信息
	 */
	public void edit(SelCourse selCourse) throws Exception {
		
		String id = selCourse.getId();
			if(id!=null && !"".equals(id)) {
				TselCourse tselCourse = selCourseDAO.get(TselCourse.class,id);
				BeanUtils.copyProperties(selCourse, tselCourse, new String[]{"crtTime"});
//				Tarchives arch = archivesDAO.get(Tarchives.class, selCourse.getArchNo());
//				tselCourse.setArchives(arch);
//				Tdept dept=deptDAO.get(Tdept.class, selCourse.getCid());
//				tselCourse.setTdept(dept);
//				tselCourse.getArchives().getBasInfoPers().setPicName(selCourse.getPicName());
				tselCourse.setUptTime(new Date());
			selCourseDAO.update(tselCourse);
			//生成日志
			this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[选课]选课修改（选课ID："+id+"）");
		}
	}

	public BaseDaoI<TselCourse> getSelCourseDAO() {
		return selCourseDAO;
	}
	
	@Autowired
	public void setSelCourseDAO(BaseDaoI<TselCourse> selCourseDAO) {
		this.selCourseDAO = selCourseDAO;
	}
}
