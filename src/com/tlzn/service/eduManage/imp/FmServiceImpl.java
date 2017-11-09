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
import com.tlzn.model.eduManage.Tfamily;
import com.tlzn.model.eduManage.TfamilyMem;
import com.tlzn.model.sys.Tsecondary;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.eduManage.FamilyMem;
import com.tlzn.pageModel.sys.Seco;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.eduManage.FmServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Generator;
import com.tlzn.util.base.ResourceUtil;
import com.tlzn.util.base.Util;

@Service("familyMemService")
public class FmServiceImpl extends BaseServiceImpl implements FmServiceI{

	private BaseDaoI<TfamilyMem> familyMemDAO;
	private BaseDaoI<Tfamily> familyDAO;
	/**
	 * 
	 * 查询单条家庭成员
	 * 
	 * @param familyMem
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public FamilyMem get(String id) throws Exception {
		Util util=new Util();
		TfamilyMem tfamilyMem= familyMemDAO.get(TfamilyMem.class, id);
		FamilyMem familyMem = new FamilyMem();
		BeanUtils.copyProperties(tfamilyMem, familyMem);
		
//		familyMem.setArchNo(tfamilyMem.getArchives().getArchNo());
//		familyMem.setCid(tfamilyMem.getTdept().getCid());
//		familyMem.setSex(tfamilyMem.getArchives().getBasInfoPers().getSex());
//		familyMem.setPicName(tfamilyMem.getArchives().getBasInfoPers().getPicName());
//		if(!util.isEmpty(tfamilyMem.getArchives().getBasInfoPers())) {
//			//取老师姓名
//			familyMem.setName(tfamilyMem.getArchives().getBasInfoPers().getName());
//		}
//		familyMem.setSexName(this.findDicName("SEX",tfamilyMem.getArchives().getBasInfoPers().getSex()));
			return familyMem;
	}

	/**
	 * 
	 * 条件查询
	 * 
	 * @param familyMem
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(FamilyMem familyMem) throws Exception {
		String hql=" from TfamilyMem t where 1=1";
		DataGrid j = new DataGrid();
		j.setRows(editFamilyMems(find(familyMem,hql)));
		j.setTotal(total(familyMem,hql));
		return j;
	}
	private List<FamilyMem> editFamilyMems(List<TfamilyMem> familyMemList) throws Exception {
		Util util=new Util();
		List<FamilyMem> familyMems = new ArrayList<FamilyMem>();
		if (familyMemList != null && familyMemList.size() > 0) {
			for (TfamilyMem tfamilyMem: familyMemList) {
				FamilyMem familyMem = new FamilyMem();
				BeanUtils.copyProperties(tfamilyMem, familyMem);
				// if(!util.isEmpty(tfamilyMem.getArchives().getBasInfoPers()))
				// {
				// //取老师姓名
				// familyMem.setName(tfamilyMem.getArchives().getBasInfoPers().getName());
				// }
				// familyMem.setSexName(this.findDicName("SEX",tfamilyMem.getArchives().getBasInfoPers().getSex()));
				// familyMem.setRankName(this.findDicName("rank",tfamilyMem.getRank()));
				// familyMem.setMarryName(this.findDicName("MARRY",tfamilyMem.getMarry()));
				// familyMem.setHasFamilyMemCertName(this.findDicName("hasNo",tfamilyMem.getHasFamilyMemCert()));
				// familyMem.setEducationName(this.findDicName("EDUCATION",tfamilyMem.getEducation()));
				// familyMem.setDegreeName(this.findDicName("DEGREE",tfamilyMem.getDegree()));
				// familyMem.setRemark("");
				familyMems.add(familyMem);
			}
		}
		return familyMems;
	}
	private List<TfamilyMem> find(FamilyMem familyMem,String hql) throws Exception {
		hql = addWhere(familyMem, hql);
		if (familyMem.getSort() != null && familyMem.getOrder() != null) {
			hql += " order by " + familyMem.getSort() + " " + familyMem.getOrder();
		}else {
			hql+=" order by t.uptTime desc, t.crtTime desc";
		}
		List<TfamilyMem> familyMemList = familyMemDAO.find(hql, familyMem.getPage(), familyMem.getRows());
		return familyMemList;
	}
	private Long total(FamilyMem familyMem,String hql) throws Exception {
		hql = addWhere(familyMem, hql);
		hql = "select count(*) "+hql;
		return familyMemDAO.count(hql);
	}
	private String addWhere(FamilyMem familyMem, String hql) throws Exception {
		Util util=new Util();
//		if(!util.isEmpty(familyMem.getId())){
//			 hql += " and t.id like '%"+familyMem.getId().trim()+"%'";
//		}
//		if(!util.isEmpty(familyMem.getName())){
//			 hql += " and t.archives.basInfoPers.name like '%"+familyMem.getName().trim()+"%'";
//		}
//		if (!util.isEmpty(familyMem.getSex())) {
//			hql += " and t.archives.basInfoPers.sex='" + familyMem.getSex().trim() + "' ";
//		}
//		if (!util.isEmpty(familyMem.getCid())) {
//			hql += " and t.tdept.cid='" + familyMem.getCid().trim() + "' ";
//		}
//		if (!util.isEmpty(familyMem.getArchNo())) {
//			hql += " and t.archives.archNo='" + familyMem.getArchNo().trim() + "' ";
//		}
//		if (!util.isEmpty(familyMem.getRank())) {
//			hql += " and t.rank='" + familyMem.getRank().trim() + "' ";
//		}
//		if (!util.isEmpty(familyMem.getHasFamilyMemCert())) {
//			hql += " and t.hasFamilyMemCert='" + familyMem.getHasFamilyMemCert().trim() + "' ";
//		}
//		if (!util.isEmpty(familyMem.getEducation())) {
//			hql += " and t.education='" + familyMem.getEducation().trim() + "' ";
//		}
//		if(!util.isEmpty(familyMem.getGradFrom())){
//			 hql += " and t.gradFrom like '%"+familyMem.getGradFrom().trim()+"%'";
//		}
//		if(!util.isEmpty(familyMem.getMajorName())){
//			 hql += " and t.majorName like '%"+familyMem.getMajorName().trim()+"%'";
//		}
//		if (!util.isEmpty(familyMem.getDegree())) {
//			hql += " and t.degree='" + familyMem.getDegree().trim() + "' ";
//		}
//		if(!util.isEmpty(familyMem.getRemark())){
//			 hql += " and t.remark like '%"+familyMem.getRemark().trim()+"%'";
//		}
//		//录入系统开始时间
//		if(!util.isEmpty(familyMem.getCrtSTime())){
//			hql+=" and date_format(t.crtTime,'%Y-%m-%d') >= '"+util.getFormatDate(familyMem.getCrtSTime(), "yyyy-MM-dd")+"'";
//		}
//		//录入系统结束时间
//		if(!util.isEmpty(familyMem.getCrtETime())){
//	    	hql+=" and date_format(t.crtTime,'%Y-%m-%d') <= '"+util.getFormatDate(familyMem.getCrtETime(),"yyyy-MM-dd")+"'";
//	    }
		return hql;
	}
	
	/**
	 * 保存家庭成员信息
	 */
	public FamilyMem save(FamilyMem familyMem,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		TfamilyMem tfamilyMem = new TfamilyMem();
		BeanUtils.copyProperties(familyMem, tfamilyMem);
		if(!Util.getInstance().isEmpty(familyMem.getFamilyId())){
			Tfamily tfamily= familyDAO.get(Tfamily.class,familyMem.getFamilyId());
			if(!util.isEmpty(tfamily)) {
				tfamilyMem.setFamily(tfamily);
			}
		}
		tfamilyMem.setId(UUID.randomUUID().toString());
		if(!util.isEmpty(familyMem.getAge())) tfamilyMem.setAge(familyMem.getAge());
		if(!util.isEmpty(familyMem.getYearIncome())) tfamilyMem.setYearIncome(familyMem.getYearIncome());
		tfamilyMem.setCrtTime(new Date());
		tfamilyMem.setUptTime(new Date());
		
		familyMemDAO.save(tfamilyMem);
		
		familyMem.setId(tfamilyMem.getId());
		familyMem.setCrtTime(tfamilyMem.getCrtTime());
		familyMem.setUptTime(tfamilyMem.getUptTime());
		
		familyMem.setHealthStatusName(this.findDicName("HEALTH",tfamilyMem.getHealthStatus()));
		
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_ARCH, Constants.LOG_TYPE_OPERTYPE_ADD, tfamilyMem.getId(), "[家庭成员]保存家庭成员");
		
		return familyMem;
	}
	/**
	 * 新增或更新家庭成员信息
	 */
	public String upDateOrAdd(FamilyMem familyMem,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		TfamilyMem tfamilyMem = null;
		if(util.isEmpty(familyMem.getId())) {
		//新增
			tfamilyMem = new TfamilyMem();
			BeanUtils.copyProperties(familyMem, tfamilyMem);
//			tfamilyMem.setId(Generator.getInstance().getFamilyMemNO());
//			tfamilyMem.setCreatDate(new Date());
		} else {
		//更新
			tfamilyMem=familyMemDAO.get(TfamilyMem.class, familyMem.getId().trim());
//			Date creatDate = tfamilyMem.getCreatDate();
			BeanUtils.copyProperties(familyMem, tfamilyMem);
//			tfamilyMem.setCreatDate(creatDate);
		}
//		String deptId=((SessionInfo) httpSession.getAttribute("sessionInfo")).getDeptId();
//		tfamilyMem.setPubUnit(deptId);
//		tfamilyMem.setPubDate(new Date());
//		tfamilyMem.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES);
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, tfamilyMem.getId(), "[家庭成员]提交家庭成员");
		//届次
		Object secoObj=httpSession.getAttribute(ResourceUtil.getSessionSeco());
		Seco seco=secoObj==null?null:((Seco)secoObj);
		if(!util.isEmpty(seco)) {
			Tsecondary ts=new Tsecondary();
			BeanUtils.copyProperties(seco,ts);
		}
		
		familyMemDAO.saveOrUpdate(tfamilyMem);
		
		
		
		return tfamilyMem.getId();
	}
	/**
	 * 删除家庭成员信息
	 */
	public void del(FamilyMem familyMem) throws Exception {
		
		Util util=new Util();
		String ids = familyMem.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					TfamilyMem tfamilyMem = familyMemDAO.get(TfamilyMem.class,id);
					familyMemDAO.delete(tfamilyMem);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_ARCH, Constants.LOG_TYPE_OPERTYPE_DEL, id, "[家庭成员]家庭成员删除（家庭成员ID："+id+"）");
				}
			}
		}
	}
	
	/**
	 * 修改家庭成员信息
	 */
	public void edit(FamilyMem familyMem) throws Exception {
		
		String id = familyMem.getId();
			if(id!=null && !"".equals(id)) {
				TfamilyMem tfamilyMem = familyMemDAO.get(TfamilyMem.class,id);
				BeanUtils.copyProperties(familyMem, tfamilyMem, new String[]{"crtTime"});
//				Tarchives arch = archivesDAO.get(Tarchives.class, familyMem.getArchNo());
//				tfamilyMem.setArchives(arch);
//				Tdept dept=deptDAO.get(Tdept.class, familyMem.getCid());
//				tfamilyMem.setTdept(dept);
//				tfamilyMem.getArchives().getBasInfoPers().setPicName(familyMem.getPicName());
//				tfamilyMem.setUptTime(new Date());
			familyMemDAO.update(tfamilyMem);
			//生成日志
			this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[家庭成员]家庭成员修改（家庭成员ID："+id+"）");
		}
	}


	public BaseDaoI<TfamilyMem> getFamilyMemDAO() {
		return familyMemDAO;
	}
	
	
	@Autowired
	public void setFamilyMemDAO(BaseDaoI<TfamilyMem> familyMemDAO) {
		this.familyMemDAO = familyMemDAO;
	}

	public BaseDaoI<Tfamily> getFamilyDAO() {
		return familyDAO;
	}
	@Autowired
	public void setFamilyDAO(BaseDaoI<Tfamily> familyDAO) {
		this.familyDAO = familyDAO;
	}
}
