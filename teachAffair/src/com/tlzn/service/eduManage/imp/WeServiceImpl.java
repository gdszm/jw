package com.tlzn.service.eduManage.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.eduManage.Tarchives;
import com.tlzn.model.eduManage.TworkExp;
import com.tlzn.model.sys.Tsecondary;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.eduManage.WorkExp;
import com.tlzn.pageModel.sys.Seco;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.eduManage.WeServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Generator;
import com.tlzn.util.base.ResourceUtil;
import com.tlzn.util.base.Util;

@Service("workExpService")
public class WeServiceImpl extends BaseServiceImpl implements WeServiceI{

	private BaseDaoI<TworkExp> workExpDAO;
	private BaseDaoI<Tarchives> archivesDAO;
	/**
	 * 
	 * 查询单条家庭成员
	 * 
	 * @param workExp
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public WorkExp get(String id) throws Exception {
		Util util=new Util();
		TworkExp tworkExp= workExpDAO.get(TworkExp.class, id);
		WorkExp workExp = new WorkExp();
		BeanUtils.copyProperties(tworkExp, workExp);
//		workExp.setArchNo(tworkExp.getArchives().getArchNo());
//		workExp.setCid(tworkExp.getTdept().getCid());
//		workExp.setSex(tworkExp.getArchives().getBasInfoPers().getSex());
//		workExp.setPicName(tworkExp.getArchives().getBasInfoPers().getPicName());
//		if(!util.isEmpty(tworkExp.getArchives().getBasInfoPers())) {
//			//取老师姓名
//			workExp.setName(tworkExp.getArchives().getBasInfoPers().getName());
//		}
//		workExp.setSexName(this.findDicName("SEX",tworkExp.getArchives().getBasInfoPers().getSex()));
			return workExp;
	}

	/**
	 * 
	 * 条件查询
	 * 
	 * @param workExp
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(WorkExp workExp) throws Exception {
		String hql=" from TworkExp t where 1=1";
		DataGrid j = new DataGrid();
		j.setRows(editWorkExps(find(workExp,hql)));
		j.setTotal(total(workExp,hql));
		return j;
	}
	private List<WorkExp> editWorkExps(List<TworkExp> workExpList) throws Exception {
		Util util=new Util();
		List<WorkExp> workExps = new ArrayList<WorkExp>();
		if (workExpList != null && workExpList.size() > 0) {
			for (TworkExp tworkExp: workExpList) {
				WorkExp workExp = new WorkExp();
				BeanUtils.copyProperties(tworkExp, workExp);
				// if(!util.isEmpty(tworkExp.getArchives().getBasInfoPers()))
				// {
				// //取老师姓名
				// workExp.setName(tworkExp.getArchives().getBasInfoPers().getName());
				// }
				// workExp.setSexName(this.findDicName("SEX",tworkExp.getArchives().getBasInfoPers().getSex()));
				// workExp.setRankName(this.findDicName("rank",tworkExp.getRank()));
				// workExp.setMarryName(this.findDicName("MARRY",tworkExp.getMarry()));
				// workExp.setHasWorkExpCertName(this.findDicName("hasNo",tworkExp.getHasWorkExpCert()));
				// workExp.setEducationName(this.findDicName("EDUCATION",tworkExp.getEducation()));
				// workExp.setDegreeName(this.findDicName("DEGREE",tworkExp.getDegree()));
				// workExp.setRemark("");
				workExps.add(workExp);
			}
		}
		return workExps;
	}
	private List<TworkExp> find(WorkExp workExp,String hql) throws Exception {
		hql = addWhere(workExp, hql);
		if (workExp.getSort() != null && workExp.getOrder() != null) {
			hql += " order by " + workExp.getSort() + " " + workExp.getOrder();
		}else {
			hql+=" order by t.uptTime desc, t.crtTime desc";
		}
		List<TworkExp> workExpList = workExpDAO.find(hql, workExp.getPage(), workExp.getRows());
		return workExpList;
	}
	private Long total(WorkExp workExp,String hql) throws Exception {
		hql = addWhere(workExp, hql);
		hql = "select count(*) "+hql;
		return workExpDAO.count(hql);
	}
	private String addWhere(WorkExp workExp, String hql) throws Exception {
		Util util=new Util();
//		if(!util.isEmpty(workExp.getId())){
//			 hql += " and t.id like '%"+workExp.getId().trim()+"%'";
//		}
//		if(!util.isEmpty(workExp.getName())){
//			 hql += " and t.archives.basInfoPers.name like '%"+workExp.getName().trim()+"%'";
//		}
//		if (!util.isEmpty(workExp.getSex())) {
//			hql += " and t.archives.basInfoPers.sex='" + workExp.getSex().trim() + "' ";
//		}
//		if (!util.isEmpty(workExp.getCid())) {
//			hql += " and t.tdept.cid='" + workExp.getCid().trim() + "' ";
//		}
//		if (!util.isEmpty(workExp.getArchNo())) {
//			hql += " and t.archives.archNo='" + workExp.getArchNo().trim() + "' ";
//		}
//		if (!util.isEmpty(workExp.getRank())) {
//			hql += " and t.rank='" + workExp.getRank().trim() + "' ";
//		}
//		if (!util.isEmpty(workExp.getHasWorkExpCert())) {
//			hql += " and t.hasWorkExpCert='" + workExp.getHasWorkExpCert().trim() + "' ";
//		}
//		if (!util.isEmpty(workExp.getEducation())) {
//			hql += " and t.education='" + workExp.getEducation().trim() + "' ";
//		}
//		if(!util.isEmpty(workExp.getGradFrom())){
//			 hql += " and t.gradFrom like '%"+workExp.getGradFrom().trim()+"%'";
//		}
//		if(!util.isEmpty(workExp.getMajorName())){
//			 hql += " and t.majorName like '%"+workExp.getMajorName().trim()+"%'";
//		}
//		if (!util.isEmpty(workExp.getDegree())) {
//			hql += " and t.degree='" + workExp.getDegree().trim() + "' ";
//		}
//		if(!util.isEmpty(workExp.getRemark())){
//			 hql += " and t.remark like '%"+workExp.getRemark().trim()+"%'";
//		}
//		//录入系统开始时间
//		if(!util.isEmpty(workExp.getCrtSTime())){
//			hql+=" and date_format(t.crtTime,'%Y-%m-%d') >= '"+util.getFormatDate(workExp.getCrtSTime(), "yyyy-MM-dd")+"'";
//		}
//		//录入系统结束时间
//		if(!util.isEmpty(workExp.getCrtETime())){
//	    	hql+=" and date_format(t.crtTime,'%Y-%m-%d') <= '"+util.getFormatDate(workExp.getCrtETime(),"yyyy-MM-dd")+"'";
//	    }
		return hql;
	}
	
	/**
	 * 保存家庭成员信息
	 */
	public WorkExp save(WorkExp workExp,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		TworkExp tworkExp = new TworkExp();
		BeanUtils.copyProperties(workExp, tworkExp);
		if(!util.isEmpty(workExp.getArchNo())) {
			Tarchives tarchives= archivesDAO.get(Tarchives.class,workExp.getArchNo());
			if(!util.isEmpty(tarchives)) {
				tworkExp.setArchives(tarchives);
			}
		}
		tworkExp.setId(UUID.randomUUID().toString());
		tworkExp.setCrtTime(new Date());
		tworkExp.setUptTime(new Date());
		
		workExpDAO.save(tworkExp);
		
		workExp.setId(tworkExp.getId());
		workExp.setCrtTime(tworkExp.getCrtTime());
		workExp.setUptTime(tworkExp.getUptTime());
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_ARCH, Constants.LOG_TYPE_OPERTYPE_ADD, tworkExp.getId(), "[家庭成员]保存工作经历");
		
		return workExp;
	}
	/**
	 * 新增或更新家庭成员信息
	 */
	public String upDateOrAdd(WorkExp workExp,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		TworkExp tworkExp = null;
		if(util.isEmpty(workExp.getId())) {
		//新增
			tworkExp = new TworkExp();
			BeanUtils.copyProperties(workExp, tworkExp);
//			tworkExp.setId(Generator.getInstance().getWorkExpNO());
//			tworkExp.setCreatDate(new Date());
		} else {
		//更新
			tworkExp=workExpDAO.get(TworkExp.class, workExp.getId().trim());
//			Date creatDate = tworkExp.getCreatDate();
			BeanUtils.copyProperties(workExp, tworkExp);
//			tworkExp.setCreatDate(creatDate);
		}
//		String deptId=((SessionInfo) httpSession.getAttribute("sessionInfo")).getDeptId();
//		tworkExp.setPubUnit(deptId);
//		tworkExp.setPubDate(new Date());
//		tworkExp.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES);
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, tworkExp.getId(), "[家庭成员]提交家庭成员");
		//届次
		Object secoObj=httpSession.getAttribute(ResourceUtil.getSessionSeco());
		Seco seco=secoObj==null?null:((Seco)secoObj);
		if(!util.isEmpty(seco)) {
			Tsecondary ts=new Tsecondary();
			BeanUtils.copyProperties(seco,ts);
		}
		
		workExpDAO.saveOrUpdate(tworkExp);
		
		
		
		return tworkExp.getId();
	}
	/**
	 * 删除家庭成员信息
	 */
	public void del(WorkExp workExp) throws Exception {
		
		Util util=new Util();
		String ids = workExp.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					TworkExp tworkExp = workExpDAO.get(TworkExp.class,id);
					workExpDAO.delete(tworkExp);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_ARCH, Constants.LOG_TYPE_OPERTYPE_DEL, id, "[家庭成员]家庭成员删除（家庭成员ID："+id+"）");
				}
			}
		}
	}
	
	/**
	 * 修改家庭成员信息
	 */
	public void edit(WorkExp workExp) throws Exception {
		
		String id = workExp.getId();
			if(id!=null && !"".equals(id)) {
				TworkExp tworkExp = workExpDAO.get(TworkExp.class,id);
				BeanUtils.copyProperties(workExp, tworkExp, new String[]{"crtTime"});
//				Tarchives arch = archivesDAO.get(Tarchives.class, workExp.getArchNo());
//				tworkExp.setArchives(arch);
//				Tdept dept=deptDAO.get(Tdept.class, workExp.getCid());
//				tworkExp.setTdept(dept);
//				tworkExp.getArchives().getBasInfoPers().setPicName(workExp.getPicName());
//				tworkExp.setUptTime(new Date());
			workExpDAO.update(tworkExp);
			//生成日志
			this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[家庭成员]家庭成员修改（家庭成员ID："+id+"）");
		}
	}


	public BaseDaoI<TworkExp> getWorkExpDAO() {
		return workExpDAO;
	}
	
	
	@Autowired
	public void setWorkExpDAO(BaseDaoI<TworkExp> workExpDAO) {
		this.workExpDAO = workExpDAO;
	}


	public BaseDaoI<Tarchives> getArchivesDAO() {
		return archivesDAO;
	}
	@Autowired
	public void setArchivesDAO(BaseDaoI<Tarchives> archivesDAO) {
		this.archivesDAO = archivesDAO;
	}
}
