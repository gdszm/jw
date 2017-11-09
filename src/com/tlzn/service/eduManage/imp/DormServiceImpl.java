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
import com.tlzn.model.eduManage.Tdorm;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.eduManage.Dorm;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.eduManage.DormServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Util;

@Service("dormService")
public class DormServiceImpl extends BaseServiceImpl implements DormServiceI{

	private BaseDaoI<Tdorm> dormDAO;
	/**
	 * 
	 * 查询单条宿舍
	 * 
	 * @param dorm
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Dorm get(String id) throws Exception {
		Util util=new Util();
		Tdorm tdorm= dormDAO.get(Tdorm.class, id);
		Dorm dorm = new Dorm();
		BeanUtils.copyProperties(tdorm, dorm);
//		dorm.setArchNo(tdorm.getArchives().getArchNo());
//		dorm.setCid(tdorm.getTdept().getCid());
//		dorm.setSex(tdorm.getArchives().getBasInfoPers().getSex());
//		dorm.setPicName(tdorm.getArchives().getBasInfoPers().getPicName());
//		if(!util.isEmpty(tdorm.getArchives().getBasInfoPers())) {
//			//取老师姓名
//			dorm.setName(tdorm.getArchives().getBasInfoPers().getName());
//		}
//		dorm.setSexName(this.findDicName("SEX",tdorm.getArchives().getBasInfoPers().getSex()));
//		dorm.setRankName(this.findDicName("rank",tdorm.getRank()));
//		dorm.setMarryName(this.findDicName("MARRY",tdorm.getMarry()));
//		dorm.setHasDormCertName(this.findDicName("hasNo",tdorm.getHasDormCert()));
//		dorm.setEducationName(this.findDicName("EDUCATION",tdorm.getEducation()));
//		dorm.setDegreeName(this.findDicName("DEGREE",tdorm.getDegree()));
			return dorm;
	}

	/**
	 * 
	 * 条件查询
	 * 
	 * @param dorm
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(Dorm dorm) throws Exception {
		String hql=" from Tdorm t where 1=1";
		DataGrid j = new DataGrid();
		j.setRows(editDorms(find(dorm,hql)));
		j.setTotal(total(dorm,hql));
		return j;
	}
	private List<Dorm> editDorms(List<Tdorm> dormList) throws Exception {
		Util util=new Util();
		List<Dorm> dorms = new ArrayList<Dorm>();
		if (dormList != null && dormList.size() > 0) {
			for (Tdorm tdorm: dormList) {
				Dorm dorm = new Dorm();
				BeanUtils.copyProperties(tdorm, dorm);
//				if(!util.isEmpty(tdorm.getArchives().getBasInfoPers())) {
//					//取老师姓名
//					dorm.setName(tdorm.getArchives().getBasInfoPers().getName());
//				}
//				dorm.setSexName(this.findDicName("SEX",tdorm.getArchives().getBasInfoPers().getSex()));
//				dorm.setRankName(this.findDicName("rank",tdorm.getRank()));
//				dorm.setMarryName(this.findDicName("MARRY",tdorm.getMarry()));
//				dorm.setHasDormCertName(this.findDicName("hasNo",tdorm.getHasDormCert()));
//				dorm.setEducationName(this.findDicName("EDUCATION",tdorm.getEducation()));
//				dorm.setDegreeName(this.findDicName("DEGREE",tdorm.getDegree()));
//				dorm.setRemark("");
				dorms.add(dorm);
			}
		}
		return dorms;
	}
	private List<Tdorm> find(Dorm dorm,String hql) throws Exception {
		hql = addWhere(dorm, hql);
		if (dorm.getSort() != null && dorm.getOrder() != null) {
			hql += " order by " + dorm.getSort() + " " + dorm.getOrder();
		}else {
			hql+=" order by t.uptTime desc, t.crtTime desc";
		}
		List<Tdorm> dormList = dormDAO.find(hql, dorm.getPage(), dorm.getRows());
		return dormList;
	}
	private Long total(Dorm dorm,String hql) throws Exception {
		hql = addWhere(dorm, hql);
		hql = "select count(*) "+hql;
		return dormDAO.count(hql);
	}
	private String addWhere(Dorm dorm, String hql) throws Exception {
		Util util=new Util();
		if(!util.isEmpty(dorm.getId())){
			 hql += " and t.id like '%"+dorm.getId().trim()+"%'";
		}
//		if(!util.isEmpty(dorm.getName())){
//			 hql += " and t.archives.basInfoPers.name like '%"+dorm.getName().trim()+"%'";
//		}
//		if (!util.isEmpty(dorm.getSex())) {
//			hql += " and t.archives.basInfoPers.sex='" + dorm.getSex().trim() + "' ";
//		}
//		if (!util.isEmpty(dorm.getCid())) {
//			hql += " and t.tdept.cid='" + dorm.getCid().trim() + "' ";
//		}
//		if (!util.isEmpty(dorm.getArchNo())) {
//			hql += " and t.archives.archNo='" + dorm.getArchNo().trim() + "' ";
//		}
//		if (!util.isEmpty(dorm.getRank())) {
//			hql += " and t.rank='" + dorm.getRank().trim() + "' ";
//		}
//		if (!util.isEmpty(dorm.getHasDormCert())) {
//			hql += " and t.hasDormCert='" + dorm.getHasDormCert().trim() + "' ";
//		}
//		if (!util.isEmpty(dorm.getEducation())) {
//			hql += " and t.education='" + dorm.getEducation().trim() + "' ";
//		}
//		if(!util.isEmpty(dorm.getGradFrom())){
//			 hql += " and t.gradFrom like '%"+dorm.getGradFrom().trim()+"%'";
//		}
//		if(!util.isEmpty(dorm.getMajorName())){
//			 hql += " and t.majorName like '%"+dorm.getMajorName().trim()+"%'";
//		}
//		if (!util.isEmpty(dorm.getDegree())) {
//			hql += " and t.degree='" + dorm.getDegree().trim() + "' ";
//		}
//		if(!util.isEmpty(dorm.getRemark())){
//			 hql += " and t.remark like '%"+dorm.getRemark().trim()+"%'";
//		}
//		//录入系统开始时间
//		if(!util.isEmpty(dorm.getCrtSTime())){
//			hql+=" and date_format(t.crtTime,'%Y-%m-%d') >= '"+util.getFormatDate(dorm.getCrtSTime(), "yyyy-MM-dd")+"'";
//		}
//		//录入系统结束时间
//		if(!util.isEmpty(dorm.getCrtETime())){
//	    	hql+=" and date_format(t.crtTime,'%Y-%m-%d') <= '"+util.getFormatDate(dorm.getCrtETime(),"yyyy-MM-dd")+"'";
//	    }
		return hql;
	}
	
	/**
	 * 保存宿舍信息
	 */
	public String save(Dorm dorm,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		Tdorm tdorm = new Tdorm();
		BeanUtils.copyProperties(dorm, tdorm);
//		tdorm.setId(Generator.getInstance().getDormNO());
//		tdorm.setCreatDate(new Date());
//		//tdorm.setPubDate(new Date());
//		tdorm.setStatus(Constants.CODE_TYPE_PUBSTATUS_NO);
//		//届次
//		Object secoObj=httpSession.getAttribute(ResourceUtil.getSessionSeco());
//		Seco seco=secoObj==null?null:((Seco)secoObj);
//		if(!util.isEmpty(seco)) {
//			Tsecondary ts=new Tsecondary();
//			BeanUtils.copyProperties(seco,ts);
//		}
		
		dormDAO.save(tdorm);
		
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_ADD, tdorm.getId(), "[宿舍]保存宿舍");
		
		return tdorm.getId();
	}
	/**
	 * 删除宿舍信息
	 */
	public void del(Dorm dorm) throws Exception {
		
		Util util=new Util();
		String ids = dorm.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					Tdorm tdorm = dormDAO.get(Tdorm.class,id);
					dormDAO.delete(tdorm);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_DEL, id, "[宿舍]宿舍删除（宿舍ID："+id+"）");
				}
			}
		}
	}
	/**
	 * 发布宿舍信息
	 */
	public void pub(Dorm dorm,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		String ids = dorm.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					Tdorm tdorm = dormDAO.get(Tdorm.class,id);
//					tdorm.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES); //已发布状态
//					if(util.isEmpty(tdorm.getPubDate())){
//						String deptId=((SessionInfo) httpSession.getAttribute("sessionInfo")).getDeptId();
//						tdorm.setPubUnit(deptId);
//						tdorm.setPubDate(new Date());
//					}
					dormDAO.update(tdorm);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[宿舍]宿舍发布（宿舍ID："+id+"）");
				}
			}
		}
	}
	/**
	 * 取消发布宿舍信息
	 */
	public void cancelPub(Dorm dorm) throws Exception {
		
		Util util=new Util();
		String ids = dorm.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					Tdorm tdorm = dormDAO.get(Tdorm.class,id);
//					tdorm.setStatus(Constants.CODE_TYPE_PUBSTATUS_NO); //已发布状态
					dormDAO.update(tdorm);
//					if(!util.isEmpty(tdorm.getPubDate())){
//						tdorm.setPubUnit(null);
//						tdorm.setPubDate(null);
//					}
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[宿舍]宿舍取消发布（宿舍ID："+id+"）");
				}
			}
		}
	}
	/**
	 * 修改宿舍信息
	 */
	public void edit(Dorm dorm) throws Exception {
		
		String id = dorm.getId();
			if(id!=null && !"".equals(id)) {
				Tdorm tdorm = dormDAO.get(Tdorm.class,id);
				BeanUtils.copyProperties(dorm, tdorm, new String[]{"crtTime"});
//				Tarchives arch = archivesDAO.get(Tarchives.class, dorm.getArchNo());
//				tdorm.setArchives(arch);
//				Tdept dept=deptDAO.get(Tdept.class, dorm.getCid());
//				tdorm.setTdept(dept);
//				tdorm.getArchives().getBasInfoPers().setPicName(dorm.getPicName());
				tdorm.setUptTime(new Date());
			dormDAO.update(tdorm);
			//生成日志
			this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[宿舍]宿舍修改（宿舍ID："+id+"）");
		}
	}

	public BaseDaoI<Tdorm> getDormDAO() {
		return dormDAO;
	}
	
	@Autowired
	public void setDormDAO(BaseDaoI<Tdorm> dormDAO) {
		this.dormDAO = dormDAO;
	}
}
