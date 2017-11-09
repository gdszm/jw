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
import com.tlzn.model.eduManage.Tbed;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.eduManage.Bed;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.eduManage.BedServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Util;

@Service("bedService")
public class BedServiceImpl extends BaseServiceImpl implements BedServiceI{

	private BaseDaoI<Tbed> bedDAO;
	/**
	 * 
	 * 查询单条住宿
	 * 
	 * @param bed
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Bed get(String id) throws Exception {
		Util util=new Util();
		Tbed tbed= bedDAO.get(Tbed.class, id);
		Bed bed = new Bed();
		BeanUtils.copyProperties(tbed, bed);
//		bed.setArchNo(tbed.getArchives().getArchNo());
//		bed.setCid(tbed.getTdept().getCid());
//		bed.setSex(tbed.getArchives().getBasInfoPers().getSex());
//		bed.setPicName(tbed.getArchives().getBasInfoPers().getPicName());
//		if(!util.isEmpty(tbed.getArchives().getBasInfoPers())) {
//			//取老师姓名
//			bed.setName(tbed.getArchives().getBasInfoPers().getName());
//		}
//		bed.setSexName(this.findDicName("SEX",tbed.getArchives().getBasInfoPers().getSex()));
//		bed.setRankName(this.findDicName("rank",tbed.getRank()));
//		bed.setMarryName(this.findDicName("MARRY",tbed.getMarry()));
//		bed.setHasBedCertName(this.findDicName("hasNo",tbed.getHasBedCert()));
//		bed.setEducationName(this.findDicName("EDUCATION",tbed.getEducation()));
//		bed.setDegreeName(this.findDicName("DEGREE",tbed.getDegree()));
			return bed;
	}

	/**
	 * 
	 * 条件查询
	 * 
	 * @param bed
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(Bed bed) throws Exception {
		String hql=" from Tbed t where 1=1";
		DataGrid j = new DataGrid();
		j.setRows(editBeds(find(bed,hql)));
		j.setTotal(total(bed,hql));
		return j;
	}
	private List<Bed> editBeds(List<Tbed> bedList) throws Exception {
		Util util=new Util();
		List<Bed> beds = new ArrayList<Bed>();
		if (bedList != null && bedList.size() > 0) {
			for (Tbed tbed: bedList) {
				Bed bed = new Bed();
				BeanUtils.copyProperties(tbed, bed);
//				if(!util.isEmpty(tbed.getArchives().getBasInfoPers())) {
//					//取老师姓名
//					bed.setName(tbed.getArchives().getBasInfoPers().getName());
//				}
//				bed.setSexName(this.findDicName("SEX",tbed.getArchives().getBasInfoPers().getSex()));
//				bed.setRankName(this.findDicName("rank",tbed.getRank()));
//				bed.setMarryName(this.findDicName("MARRY",tbed.getMarry()));
//				bed.setHasBedCertName(this.findDicName("hasNo",tbed.getHasBedCert()));
//				bed.setEducationName(this.findDicName("EDUCATION",tbed.getEducation()));
//				bed.setDegreeName(this.findDicName("DEGREE",tbed.getDegree()));
//				bed.setRemark("");
				beds.add(bed);
			}
		}
		return beds;
	}
	private List<Tbed> find(Bed bed,String hql) throws Exception {
		hql = addWhere(bed, hql);
		if (bed.getSort() != null && bed.getOrder() != null) {
			hql += " order by " + bed.getSort() + " " + bed.getOrder();
		}else {
			hql+=" order by t.uptTime desc, t.crtTime desc";
		}
		List<Tbed> bedList = bedDAO.find(hql, bed.getPage(), bed.getRows());
		return bedList;
	}
	private Long total(Bed bed,String hql) throws Exception {
		hql = addWhere(bed, hql);
		hql = "select count(*) "+hql;
		return bedDAO.count(hql);
	}
	private String addWhere(Bed bed, String hql) throws Exception {
		Util util=new Util();
		if(!util.isEmpty(bed.getId())){
			 hql += " and t.id like '%"+bed.getId().trim()+"%'";
		}
//		if(!util.isEmpty(bed.getName())){
//			 hql += " and t.archives.basInfoPers.name like '%"+bed.getName().trim()+"%'";
//		}
//		if (!util.isEmpty(bed.getSex())) {
//			hql += " and t.archives.basInfoPers.sex='" + bed.getSex().trim() + "' ";
//		}
//		if (!util.isEmpty(bed.getCid())) {
//			hql += " and t.tdept.cid='" + bed.getCid().trim() + "' ";
//		}
//		if (!util.isEmpty(bed.getArchNo())) {
//			hql += " and t.archives.archNo='" + bed.getArchNo().trim() + "' ";
//		}
//		if (!util.isEmpty(bed.getRank())) {
//			hql += " and t.rank='" + bed.getRank().trim() + "' ";
//		}
//		if (!util.isEmpty(bed.getHasBedCert())) {
//			hql += " and t.hasBedCert='" + bed.getHasBedCert().trim() + "' ";
//		}
//		if (!util.isEmpty(bed.getEducation())) {
//			hql += " and t.education='" + bed.getEducation().trim() + "' ";
//		}
//		if(!util.isEmpty(bed.getGradFrom())){
//			 hql += " and t.gradFrom like '%"+bed.getGradFrom().trim()+"%'";
//		}
//		if(!util.isEmpty(bed.getMajorName())){
//			 hql += " and t.majorName like '%"+bed.getMajorName().trim()+"%'";
//		}
//		if (!util.isEmpty(bed.getDegree())) {
//			hql += " and t.degree='" + bed.getDegree().trim() + "' ";
//		}
//		if(!util.isEmpty(bed.getRemark())){
//			 hql += " and t.remark like '%"+bed.getRemark().trim()+"%'";
//		}
//		//录入系统开始时间
//		if(!util.isEmpty(bed.getCrtSTime())){
//			hql+=" and date_format(t.crtTime,'%Y-%m-%d') >= '"+util.getFormatDate(bed.getCrtSTime(), "yyyy-MM-dd")+"'";
//		}
//		//录入系统结束时间
//		if(!util.isEmpty(bed.getCrtETime())){
//	    	hql+=" and date_format(t.crtTime,'%Y-%m-%d') <= '"+util.getFormatDate(bed.getCrtETime(),"yyyy-MM-dd")+"'";
//	    }
		return hql;
	}
	
	/**
	 * 保存住宿信息
	 */
	public String save(Bed bed,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		Tbed tbed = new Tbed();
		BeanUtils.copyProperties(bed, tbed);
//		tbed.setId(Generator.getInstance().getBedNO());
//		tbed.setCreatDate(new Date());
//		//tbed.setPubDate(new Date());
//		tbed.setStatus(Constants.CODE_TYPE_PUBSTATUS_NO);
//		//届次
//		Object secoObj=httpSession.getAttribute(ResourceUtil.getSessionSeco());
//		Seco seco=secoObj==null?null:((Seco)secoObj);
//		if(!util.isEmpty(seco)) {
//			Tsecondary ts=new Tsecondary();
//			BeanUtils.copyProperties(seco,ts);
//		}
		
		bedDAO.save(tbed);
		
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_ADD, tbed.getId(), "[住宿]保存住宿");
		
		return tbed.getId();
	}
	/**
	 * 删除住宿信息
	 */
	public void del(Bed bed) throws Exception {
		
		Util util=new Util();
		String ids = bed.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					Tbed tbed = bedDAO.get(Tbed.class,id);
					bedDAO.delete(tbed);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_DEL, id, "[住宿]住宿删除（住宿ID："+id+"）");
				}
			}
		}
	}
	/**
	 * 发布住宿信息
	 */
	public void pub(Bed bed,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		String ids = bed.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					Tbed tbed = bedDAO.get(Tbed.class,id);
//					tbed.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES); //已发布状态
//					if(util.isEmpty(tbed.getPubDate())){
//						String deptId=((SessionInfo) httpSession.getAttribute("sessionInfo")).getDeptId();
//						tbed.setPubUnit(deptId);
//						tbed.setPubDate(new Date());
//					}
					bedDAO.update(tbed);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[住宿]住宿发布（住宿ID："+id+"）");
				}
			}
		}
	}
	/**
	 * 取消发布住宿信息
	 */
	public void cancelPub(Bed bed) throws Exception {
		
		Util util=new Util();
		String ids = bed.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					Tbed tbed = bedDAO.get(Tbed.class,id);
//					tbed.setStatus(Constants.CODE_TYPE_PUBSTATUS_NO); //已发布状态
					bedDAO.update(tbed);
//					if(!util.isEmpty(tbed.getPubDate())){
//						tbed.setPubUnit(null);
//						tbed.setPubDate(null);
//					}
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[住宿]住宿取消发布（住宿ID："+id+"）");
				}
			}
		}
	}
	/**
	 * 修改住宿信息
	 */
	public void edit(Bed bed) throws Exception {
		
		String id = bed.getId();
			if(id!=null && !"".equals(id)) {
				Tbed tbed = bedDAO.get(Tbed.class,id);
				BeanUtils.copyProperties(bed, tbed, new String[]{"crtTime"});
//				Tarchives arch = archivesDAO.get(Tarchives.class, bed.getArchNo());
//				tbed.setArchives(arch);
//				Tdept dept=deptDAO.get(Tdept.class, bed.getCid());
//				tbed.setTdept(dept);
//				tbed.getArchives().getBasInfoPers().setPicName(bed.getPicName());
				tbed.setUptTime(new Date());
			bedDAO.update(tbed);
			//生成日志
			this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[住宿]住宿修改（住宿ID："+id+"）");
		}
	}

	public BaseDaoI<Tbed> getBedDAO() {
		return bedDAO;
	}
	
	@Autowired
	public void setBedDAO(BaseDaoI<Tbed> bedDAO) {
		this.bedDAO = bedDAO;
	}
}
