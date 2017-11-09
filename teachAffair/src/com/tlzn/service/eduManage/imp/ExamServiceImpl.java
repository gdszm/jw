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
import com.tlzn.model.eduManage.Texam;
import com.tlzn.model.sys.Tsecondary;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.SessionInfo;
import com.tlzn.pageModel.eduManage.Exam;
import com.tlzn.pageModel.sys.Seco;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.eduManage.ExamServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Generator;
import com.tlzn.util.base.ResourceUtil;
import com.tlzn.util.base.Util;

@Service("examService")
public class ExamServiceImpl extends BaseServiceImpl implements ExamServiceI{

	private BaseDaoI<Texam> examDAO;
	
	public BaseDaoI<Texam> getExamDAO() {
		return examDAO;
	}
	
	
	@Autowired
	public void setExamDAO(BaseDaoI<Texam> examDAO) {
		this.examDAO = examDAO;
	}
	
	/**
	 * 
	 * 查询单条通知
	 * 
	 * @param exam
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Exam get(String id) throws Exception {
		Util util=new Util();
		Texam texam= examDAO.get(Texam.class, id);
		Exam exam = new Exam();
		BeanUtils.copyProperties(texam, exam);
//		exam.setStatusName(this.findDicName("PUBSTATUS", exam.getStatus()));
//		exam.setReplyName(this.findDicName("YESNO", exam.getReply()));
//		exam.setExamTypeName(this.findDicName("NOTICETYPE", exam.getExamType()));
//		
//		exam.setExamTypeName(this.findDicName("NOTICETYPE", exam.getExamType()));
//		if(!util.isEmpty(texam.getPubUnit())){
//			Tdept dept = this.getDeptDao().get(Tdept.class, texam.getPubUnit());
//			String cname =dept==null?null:dept.getCname();
//			exam.setPubUnitName(cname);
//		}
		return exam;
	}

	/**
	 * 
	 * 查询
	 * 
	 * @param exam
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(Exam exam) throws Exception {
		String hql=" from Texam t where 1=1";
		DataGrid j = new DataGrid();
		j.setRows(editExams(find(exam,hql)));
		j.setTotal(total(exam,hql));
		return j;
	}
	private List<Exam> editExams(List<Texam> examList) throws Exception {
		Util util=new Util();
		List<Exam> exams = new ArrayList<Exam>();
		if (examList != null && examList.size() > 0) {
			for (Texam texam: examList) {
				Exam exam = new Exam();
				BeanUtils.copyProperties(texam, exam);
				//课程名称
				exam.setCourseName(texam.getCourse().getName());
				//开始考试日期
				exam.setExamSDateString(texam.getExamSDate().toString());
				//结束考试日期
				exam.setExamEDateString(texam.getExamEDate().toString());
				//成绩类型
				exam.setResultTypeString(this.findDicName("RESULTTYPE", texam.getResultType()));
				
//				exam.setStatusName(this.findDicName("PUBSTATUS", exam.getStatus()));
//				exam.setReplyName(this.findDicName("YESNO", exam.getReply()));
//				exam.setExamTypeName(this.findDicName("NOTICETYPE", exam.getExamType()));
//				if(!util.isEmpty(texam.getPubUnit())){
//					Tdept dept = this.getDeptDao().get(Tdept.class, texam.getPubUnit());
//					String cname =dept==null?null:dept.getCname();
//					exam.setPubUnitName(cname);
//				}
//				exam.setContent("");
//				exam.setMemo("");
				exams.add(exam);
			}
		}
		return exams;
	}
	/**
	 * 最新5条成绩
	 */
	public List<Exam> getNewExam() throws Exception {
		String hql="from Texam t where t.status='"+Constants.CODE_TYPE_PUBSTATUS_YES+"' order by t.pubDate desc,t.creatDate desc";
		List<Texam> examList = examDAO.find(hql, 1, 5);
		return editExams(examList);
	}
	
	
	private List<Texam> find(Exam exam,String hql) throws Exception {
		hql = addWhere(exam, hql);
		if (exam.getSort() != null && exam.getOrder() != null) {
			hql += " order by " + exam.getSort() + " " + exam.getOrder();
		}else {
//			hql+=" order by t.pubDate desc,t.creatDate desc";
		}
		List<Texam> examList = examDAO.find(hql, exam.getPage(), exam.getRows());
		return examList;
	}
	private Long total(Exam exam,String hql) throws Exception {
		hql = addWhere(exam, hql);
		hql = "select count(*) "+hql;
		return examDAO.count(hql);
	}
	private String addWhere(Exam exam, String hql) throws Exception {
		Util util=new Util();
//		if(!util.isEmpty(exam.getId())){
//			 hql += " and t.id like '%"+exam.getId().trim()+"%'";
//		}
//		if(!util.isEmpty(exam.getTitle())){
//			 hql += " and t.title like '%"+exam.getTitle().trim()+"%'";
//		}
//		if(!util.isEmpty(exam.getContent())){
//			hql += " and t.content like '%" + exam.getContent().trim() + "%'";
//		}
//		if (!util.isEmpty(exam.getPubUnitName())) {
//			hql += " and exists (from Tdept b where t.pubUnit=b.cid and b.cname like'%"+ exam.getPubUnitName().trim() + "%')";
//		}
//		if (!util.isEmpty(exam.getExamType())) {
//			hql += " and t.examType='" + exam.getExamType() + "' ";
//		}
//		if (!util.isEmpty(exam.getStatus())) {
//			hql += " and t.status='" + exam.getStatus() + "'";
//		}
//		if(exam.getCreatDateStart()!=null){
//			 hql+=" and to_char(t.creatDate,'yyyy-MM-dd') >= '"+util.getFormatDate(exam.getCreatDateStart(), "yyyy-MM-dd")+"'";
//		}
//		 if(exam.getCreatDateEnd()!=null){
//		 hql+=" and to_char(t.creatDate,'yyyy-MM-dd') <= '"+util.getFormatDate(exam.getCreatDateEnd(),"yyyy-MM-dd")+"'";
//		 }
//		 if(exam.getPubDateStart()!=null){
//			 hql+=" and to_char(t.pubDate,'yyyy-MM-dd') >= '"+util.getFormatDate(exam.getPubDateStart(),"yyyy-MM-dd")+"'";
//		 }
//		 if(exam.getPubDateEnd()!=null){
//		 hql+=" and to_char(t.pubDate,'yyyy-MM-dd') <= '"+util.getFormatDate(exam.getPubDateEnd(),"yyyy-MM-dd")+"'";
//		 }
		return hql;
	}
	
	public String save(Exam exam,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		Texam texam = new Texam();
		BeanUtils.copyProperties(exam, texam);
//		texam.setId(Generator.getInstance().getExamNO());
//		texam.setCreatDate(new Date());
//		//texam.setPubDate(new Date());
//		texam.setStatus(Constants.CODE_TYPE_PUBSTATUS_NO);
//		//届次
//		Object secoObj=httpSession.getAttribute(ResourceUtil.getSessionSeco());
//		Seco seco=secoObj==null?null:((Seco)secoObj);
//		if(!util.isEmpty(seco)) {
//			Tsecondary ts=new Tsecondary();
//			BeanUtils.copyProperties(seco,ts);
//		}
		
		examDAO.save(texam);
		
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_ADD, texam.getId(), "[成绩]保存通知");
		
		return texam.getId();
	}
	//多次点保存时，更新记录
	public String saveForUpate(Exam exam,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		Texam texam = null;
		//更新
		texam=examDAO.get(Texam.class, exam.getId().trim());
		BeanUtils.copyProperties(exam, texam);
//		texam.setCreatDate(new Date());
//		texam.setStatus(Constants.CODE_TYPE_PUBSTATUS_NO);
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, texam.getId(), "[成绩]保存更新通知");
		//届次
		Object secoObj=httpSession.getAttribute(ResourceUtil.getSessionSeco());
		Seco seco=secoObj==null?null:((Seco)secoObj);
		if(!util.isEmpty(seco)) {
			Tsecondary ts=new Tsecondary();
			BeanUtils.copyProperties(seco,ts);
		}
		
		examDAO.update(texam);
		
		return texam.getId();
	}
	public String upDateOrAdd(Exam exam,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		Texam texam = null;
		if(util.isEmpty(exam.getId())) {
		//新增
			texam = new Texam();
			BeanUtils.copyProperties(exam, texam);
			texam.setId(Generator.getInstance().getExamNO());
//			texam.setCreatDate(new Date());
		} else {
		//更新
			texam=examDAO.get(Texam.class, exam.getId().trim());
//			Date creatDate = texam.getCreatDate();
			BeanUtils.copyProperties(exam, texam);
//			texam.setCreatDate(creatDate);
		}
		String deptId=((SessionInfo) httpSession.getAttribute("sessionInfo")).getDeptId();
//		texam.setPubUnit(deptId);
//		texam.setPubDate(new Date());
//		texam.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES);
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, texam.getId(), "[成绩]提交通知");
//		//届次
//		Object secoObj=httpSession.getAttribute(ResourceUtil.getSessionSeco());
//		Seco seco=secoObj==null?null:((Seco)secoObj);
//		if(!util.isEmpty(seco)) {
//			Tsecondary ts=new Tsecondary();
//			BeanUtils.copyProperties(seco,ts);
//		}
		
		examDAO.saveOrUpdate(texam);
		
		return texam.getId();
	}

	public void del(Exam exam) throws Exception {
		
		Util util=new Util();
		String ids = exam.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					Texam texam = examDAO.get(Texam.class,id);
					examDAO.delete(texam);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_DEL, id, "[成绩]通知删除（通知ID："+id+"）");
				}
			}
		}
	}
	public void pub(Exam exam,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		String ids = exam.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					Texam texam = examDAO.get(Texam.class,id);
//					texam.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES); //已发布状态
//					if(util.isEmpty(texam.getPubDate())){
//						String deptId=((SessionInfo) httpSession.getAttribute("sessionInfo")).getDeptId();
//						texam.setPubUnit(deptId);
//						texam.setPubDate(new Date());
//					}
					examDAO.update(texam);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[成绩]通知发布（通知ID："+id+"）");
				}
			}
		}
	}
	public void cancelPub(Exam exam) throws Exception {
		
		Util util=new Util();
		String ids = exam.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					Texam texam = examDAO.get(Texam.class,id);
//					texam.setStatus(Constants.CODE_TYPE_PUBSTATUS_NO); //已发布状态
					examDAO.update(texam);
//					if(!util.isEmpty(texam.getPubDate())){
//						texam.setPubUnit(null);
//						texam.setPubDate(null);
//					}
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[成绩]通知取消发布（通知ID："+id+"）");
				}
			}
		}
	}
	public void edit(Exam exam) throws Exception {
		
		String id = exam.getId();
			if(id!=null && !"".equals(id)) {
			Texam texam = examDAO.get(Texam.class,id);
//			texam.setTitle(exam.getTitle());
//			texam.setPubUnit(exam.getPubUnit());
//			texam.setReply(exam.getReply());
//			texam.setExamType(exam.getExamType());
//			texam.setValidDate(exam.getValidDate());
//			texam.setMemo(exam.getMemo());
//			texam.setContent(exam.getContent());
//			texam.setAtts(exam.getAtts());
			texam.setUptTime(new Date());
			examDAO.update(texam);
			//生成日志
			this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[成绩]通知修改（通知ID："+id+"）");
		}
	}

	//日常办公首页统计
	public Map<String, Integer> examCount(HttpSession httpSession) {
		return null;
	}
}
