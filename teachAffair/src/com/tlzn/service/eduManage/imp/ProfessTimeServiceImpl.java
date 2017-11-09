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

import com.tlzn.util.base.Util;
import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.eduManage.TprofessTime;
import com.tlzn.model.sys.Tsecondary;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.eduManage.ProfessTime;
import com.tlzn.pageModel.sys.Seco;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.eduManage.ProfessTimeServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Generator;
import com.tlzn.util.base.ResourceUtil;

@Service("professTimeService")
public class ProfessTimeServiceImpl extends BaseServiceImpl implements ProfessTimeServiceI{

	private BaseDaoI<TprofessTime> professTimeDAO;
	
	public BaseDaoI<TprofessTime> getProfessTimeDAO() {
		return professTimeDAO;
	}
	
	@Autowired
	public void setProfessTimeDAO(BaseDaoI<TprofessTime> professTimeDAO) {
		this.professTimeDAO = professTimeDAO;
	}
	
	/**
	 * 
	 * 查询单条授课时间
	 * 
	 * @param professTime
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public ProfessTime get(String id) throws Exception {
		Util util=new Util();
		TprofessTime tprofessTime= professTimeDAO.get(TprofessTime.class, id);
		ProfessTime professTime = new ProfessTime();
		BeanUtils.copyProperties(tprofessTime, professTime);
//		professTime.setStatusName(this.findDicName("PUBSTATUS", professTime.getStatus()));
//		professTime.setReplyName(this.findDicName("YESNO", professTime.getReply()));
//		professTime.setProfessTimeTypeName(this.findDicName("NOTICETYPE", professTime.getProfessTimeType()));
//		
//		professTime.setProfessTimeTypeName(this.findDicName("NOTICETYPE", professTime.getProfessTimeType()));
//		if(!util.isEmpty(tprofessTime.getPubUnit())){
//			Tdept dept = this.getDeptDao().get(Tdept.class, tprofessTime.getPubUnit());
//			String cname =dept==null?null:dept.getCname();
//			professTime.setPubUnitName(cname);
//		}
		return professTime;
	}

	/**
	 * 
	 * 查询
	 * 
	 * @param professTime
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(ProfessTime professTime) throws Exception {
		String hql=" from TprofessTime t where 1=1";
		DataGrid j = new DataGrid();
		j.setRows(editProfessTimes(find(professTime,hql)));
		j.setTotal(total(professTime,hql));
		return j;
	}
	private List<ProfessTime> editProfessTimes(List<TprofessTime> professTimeList) throws Exception {
		Util util=new Util();
		List<ProfessTime> professTimes = new ArrayList<ProfessTime>();
		if (professTimeList != null && professTimeList.size() > 0) {
			for (TprofessTime tprofessTime: professTimeList) {
				ProfessTime professTime = new ProfessTime();
				BeanUtils.copyProperties(tprofessTime, professTime);
//				professTime.setStatusName(this.findDicName("PUBSTATUS", professTime.getStatus()));
//				professTime.setReplyName(this.findDicName("YESNO", professTime.getReply()));
//				professTime.setProfessTimeTypeName(this.findDicName("NOTICETYPE", professTime.getProfessTimeType()));
//				if(!util.isEmpty(tprofessTime.getPubUnit())){
//					Tdept dept = this.getDeptDao().get(Tdept.class, tprofessTime.getPubUnit());
//					String cname =dept==null?null:dept.getCname();
//					professTime.setPubUnitName(cname);
//				}
//				professTime.setContent("");
//				professTime.setMemo("");
				professTimes.add(professTime);
			}
		}
		return professTimes;
	}
	
	private List<TprofessTime> find(ProfessTime professTime,String hql) throws Exception {
		hql = addWhere(professTime, hql);
		if (professTime.getSort() != null && professTime.getOrder() != null) {
			hql += " order by " + professTime.getSort() + " " + professTime.getOrder();
		}else {
			hql+=" order by t.crtTime desc,t.uptTime desc";
		}
		List<TprofessTime> professTimeList = professTimeDAO.find(hql, professTime.getPage(), professTime.getRows());
		return professTimeList;
	}
	private Long total(ProfessTime professTime,String hql) throws Exception {
		hql = addWhere(professTime, hql);
		hql = "select count(*) "+hql;
		return professTimeDAO.count(hql);
	}
	private String addWhere(ProfessTime professTime, String hql) throws Exception {
		Util util=new Util();
		if(!util.isEmpty(professTime.getId())){
			 hql += " and t.id like '%"+professTime.getId().trim()+"%'";
		}
		//周次
		if(!util.isEmpty(professTime.getWeeks())){
			String weeks = professTime.getWeeks();
			String[] weekArray = weeks.split(",");
			if(!util.isEmpty(weekArray) && weekArray.length > 0) {
				hql += " and (1<0 ";
				for (String week : weekArray) {
					if(!util.isEmpty(week)) {
						hql += " or t.weeks like '%"+week+"%'";
					}
				}
				hql += " ) ";
			} 
		}
		//星期
		if(!util.isEmpty(professTime.getWeek())){
					String week = professTime.getWeek();
					String[] weekArray = week.split(",");
					if(!util.isEmpty(weekArray) && weekArray.length > 0) {
						hql += " and (1<0 ";
						for (String w : weekArray) {
							if(!util.isEmpty(w)) {
								hql += " or t.week like '%"+w+"%'";
							}
						}
						hql += " ) ";
					} 
				}
		//节次
		if(!util.isEmpty(professTime.getSection())){
			String sections = professTime.getSection();
			String[] sectionArray = sections.split(",");
			if(!util.isEmpty(sectionArray) && sectionArray.length > 0) {
				hql += " and (1<0 ";
				for (String section : sectionArray) {
					if(!util.isEmpty(section)) {
						hql += " or t.section like '%"+section+"%'";
					}
				}
				hql += " ) ";
			} 
		}
		
//		//星期
//		if(!util.isEmpty(professTime.getWeek())){
//			 hql += " and t.week like '%"+professTime.getWeek().trim()+"%'";
//		}
//		//节次
//		if(!util.isEmpty(professTime.getSection())){
//			 hql += " and t.section like '%"+professTime.getSection().trim()+"%'";
//		}
		
		
//		if(!util.isEmpty(professTime.getTitle())){
//			 hql += " and t.title like '%"+professTime.getTitle().trim()+"%'";
//		}
//		if(!util.isEmpty(professTime.getContent())){
//			hql += " and t.content like '%" + professTime.getContent().trim() + "%'";
//		}
//		if (!util.isEmpty(professTime.getPubUnitName())) {
//			hql += " and exists (from Tdept b where t.pubUnit=b.cid and b.cname like'%"+ professTime.getPubUnitName().trim() + "%')";
//		}
//		if (!util.isEmpty(professTime.getProfessTimeType())) {
//			hql += " and t.professTimeType='" + professTime.getProfessTimeType() + "' ";
//		}
//		if (!util.isEmpty(professTime.getStatus())) {
//			hql += " and t.status='" + professTime.getStatus() + "'";
//		}
//		if(professTime.getCreatDateStart()!=null){
//			 hql+=" and to_char(t.creatDate,'yyyy-MM-dd') >= '"+util.getFormatDate(professTime.getCreatDateStart(), "yyyy-MM-dd")+"'";
//		}
//		 if(professTime.getCreatDateEnd()!=null){
//		 hql+=" and to_char(t.creatDate,'yyyy-MM-dd') <= '"+util.getFormatDate(professTime.getCreatDateEnd(),"yyyy-MM-dd")+"'";
//		 }
//		 if(professTime.getPubDateStart()!=null){
//			 hql+=" and to_char(t.pubDate,'yyyy-MM-dd') >= '"+util.getFormatDate(professTime.getPubDateStart(),"yyyy-MM-dd")+"'";
//		 }
//		 if(professTime.getPubDateEnd()!=null){
//		 hql+=" and to_char(t.pubDate,'yyyy-MM-dd') <= '"+util.getFormatDate(professTime.getPubDateEnd(),"yyyy-MM-dd")+"'";
//		 }
		return hql;
	}
	
	public String save(ProfessTime professTime,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		TprofessTime tprofessTime = new TprofessTime();
		BeanUtils.copyProperties(professTime, tprofessTime);
		tprofessTime.setId(Generator.getInstance().getProfessTimeNO());
		tprofessTime.setCrtTime(new Date());
		tprofessTime.setUptTime(new Date());
//		tprofessTime.setStatus(Constants.CODE_TYPE_PUBSTATUS_NO);
		professTimeDAO.save(tprofessTime);
		
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_PROFESSTIME, Constants.LOG_TYPE_OPERTYPE_ADD, tprofessTime.getId(), "[授课时间]保存授课时间");
		
		return tprofessTime.getId();
	}

	public void del(ProfessTime professTime) throws Exception {
		
		Util util=new Util();
		String ids = professTime.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					TprofessTime tprofessTime = professTimeDAO.get(TprofessTime.class,id);
					professTimeDAO.delete(tprofessTime);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_PROFESSTIME, Constants.LOG_TYPE_OPERTYPE_DEL, id, "[授课时间]授课时间删除（授课时间ID："+id+"）");
				}
			}
		}
	}
	
	public void edit(ProfessTime professTime) throws Exception {
		
		String id = professTime.getId();
			if(id!=null && !"".equals(id)) {
			TprofessTime tprofessTime = professTimeDAO.get(TprofessTime.class,id);
			tprofessTime.setWeeks(professTime.getWeeks());
			tprofessTime.setWeek(professTime.getWeek());
			tprofessTime.setSection(professTime.getSection());
			tprofessTime.setUptTime(new Date());
			professTimeDAO.update(tprofessTime);
			//生成日志
			this.saveLog(Constants.LOG_TYPE_CODE_PROFESSTIME, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[授课时间]授课时间修改（授课时间ID："+id+"）");
		}
	}
}
