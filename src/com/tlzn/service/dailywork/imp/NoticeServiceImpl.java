package com.tlzn.service.dailywork.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;
import com.tlzn.util.base.Util;
import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.dailywork.Tnotice;
import com.tlzn.model.sys.Tdept;
import com.tlzn.model.sys.Tsecondary;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.SessionInfo;
import com.tlzn.pageModel.dailywork.Notice;
import com.tlzn.pageModel.sys.Seco;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.dailywork.NoticeServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Generator;
import com.tlzn.util.base.ResourceUtil;

@Service("noticeService")
public class NoticeServiceImpl extends BaseServiceImpl implements NoticeServiceI{

	private BaseDaoI<Tnotice> noticeDAO;
	
	public BaseDaoI<Tnotice> getNoticeDAO() {
		return noticeDAO;
	}
	
	
	@Autowired
	public void setNoticeDAO(BaseDaoI<Tnotice> noticeDAO) {
		this.noticeDAO = noticeDAO;
	}
	
	/**
	 * 
	 * 查询单条通知
	 * 
	 * @param notice
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Notice get(String id) throws Exception {
		Util util=new Util();
		Tnotice tnotice= noticeDAO.get(Tnotice.class, id);
		Notice notice = new Notice();
//		notice.setYear(tnotice.getTsecondary().getYear());
//		notice.setSecondaryId(tnotice.getTsecondary().getSecondaryId());
//		notice.setSecondayName(tnotice.getTsecondary().getSecondayName());
//		notice.setYear((tnotice.getTsecondary().getYear()));
//		tnotice.setTsecondary(null);
		BeanUtils.copyProperties(tnotice, notice);
		notice.setStatusName(this.findDicName("PUBSTATUS", notice.getStatus()));
		notice.setReplyName(this.findDicName("YESNO", notice.getReply()));
		notice.setNoticeTypeName(this.findDicName("NOTICETYPE", notice.getNoticeType()));
		
		notice.setNoticeTypeName(this.findDicName("NOTICETYPE", notice.getNoticeType()));
		if(!util.isEmpty(tnotice.getPubUnit())){
			Tdept dept = this.getDeptDao().get(Tdept.class, tnotice.getPubUnit());
			String cname =dept==null?null:dept.getCname();
			notice.setPubUnitName(cname);
		}
		return notice;
	}

	/**
	 * 
	 * 查询
	 * 
	 * @param notice
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(Notice notice) throws Exception {
		String hql=" from Tnotice t where 1=1";
		DataGrid j = new DataGrid();
		j.setRows(editNotices(find(notice,hql)));
		j.setTotal(total(notice,hql));
		return j;
	}
	private List<Notice> editNotices(List<Tnotice> noticeList) throws Exception {
		Util util=new Util();
		List<Notice> notices = new ArrayList<Notice>();
		if (noticeList != null && noticeList.size() > 0) {
			for (Tnotice tnotice: noticeList) {
				Notice notice = new Notice();
				BeanUtils.copyProperties(tnotice, notice);
				notice.setStatusName(this.findDicName("PUBSTATUS", notice.getStatus()));
				notice.setReplyName(this.findDicName("YESNO", notice.getReply()));
				notice.setNoticeTypeName(this.findDicName("NOTICETYPE", notice.getNoticeType()));
				if(!util.isEmpty(tnotice.getPubUnit())){
					Tdept dept = this.getDeptDao().get(Tdept.class, tnotice.getPubUnit());
					String cname =dept==null?null:dept.getCname();
					notice.setPubUnitName(cname);
				}
				notice.setContent("");
				notice.setMemo("");
				notices.add(notice);
			}
		}
		return notices;
	}
	/**
	 * 最新5条通知公告
	 */
	public List<Notice> getNewNotice() throws Exception {
//		String secondaryId=((Seco) ActionContext.getContext().getSession().get(ResourceUtil.getSessionSeco())).getSecondaryId();
//		String hql="from Tnotice t where t.status='"+Constants.CODE_TYPE_PUBSTATUS_YES+"' and t.tsecondary.secondaryId='"+secondaryId+"' order by t.pubDate desc,t.creatDate desc";
		String hql="from Tnotice t where t.status='"+Constants.CODE_TYPE_PUBSTATUS_YES+"' order by t.pubDate desc,t.creatDate desc";
		List<Tnotice> noticeList = noticeDAO.find(hql, 1, 5);
		return editNotices(noticeList);
	}
	
	
	private List<Tnotice> find(Notice notice,String hql) throws Exception {
		hql = addWhere(notice, hql);
		if (notice.getSort() != null && notice.getOrder() != null) {
			hql += " order by " + notice.getSort() + " " + notice.getOrder();
		}else {
			hql+=" order by t.pubDate desc,t.creatDate desc";
		}
		List<Tnotice> noticeList = noticeDAO.find(hql, notice.getPage(), notice.getRows());
		return noticeList;
	}
	private Long total(Notice notice,String hql) throws Exception {
		hql = addWhere(notice, hql);
		hql = "select count(*) "+hql;
		return noticeDAO.count(hql);
	}
	private String addWhere(Notice notice, String hql) throws Exception {
		Util util=new Util();
		if(!util.isEmpty(notice.getId())){
			 hql += " and t.id like '%"+notice.getId().trim()+"%'";
		}
		if(!util.isEmpty(notice.getTitle())){
			 hql += " and t.title like '%"+notice.getTitle().trim()+"%'";
		}
		if(!util.isEmpty(notice.getContent())){
			hql += " and t.content like '%" + notice.getContent().trim() + "%'";
		}
		if (!util.isEmpty(notice.getPubUnitName())) {
			hql += " and exists (from Tdept b where t.pubUnit=b.cid and b.cname like'%"+ notice.getPubUnitName().trim() + "%')";
		}
		if (!util.isEmpty(notice.getNoticeType())) {
			hql += " and t.noticeType='" + notice.getNoticeType() + "' ";
		}
		if (!util.isEmpty(notice.getStatus())) {
			hql += " and t.status='" + notice.getStatus() + "'";
		}
		if(notice.getCreatDateStart()!=null){
			 hql+=" and to_char(t.creatDate,'yyyy-MM-dd') >= '"+util.getFormatDate(notice.getCreatDateStart(), "yyyy-MM-dd")+"'";
		}
		 if(notice.getCreatDateEnd()!=null){
		 hql+=" and to_char(t.creatDate,'yyyy-MM-dd') <= '"+util.getFormatDate(notice.getCreatDateEnd(),"yyyy-MM-dd")+"'";
		 }
		 if(notice.getPubDateStart()!=null){
			 hql+=" and to_char(t.pubDate,'yyyy-MM-dd') >= '"+util.getFormatDate(notice.getPubDateStart(),"yyyy-MM-dd")+"'";
		 }
		 if(notice.getPubDateEnd()!=null){
		 hql+=" and to_char(t.pubDate,'yyyy-MM-dd') <= '"+util.getFormatDate(notice.getPubDateEnd(),"yyyy-MM-dd")+"'";
		 }
		return hql;
	}
	
	public String save(Notice notice,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		Tnotice tnotice = new Tnotice();
		BeanUtils.copyProperties(notice, tnotice);
		tnotice.setId(Generator.getInstance().getNoticeNO());
		tnotice.setCreatDate(new Date());
		//tnotice.setPubDate(new Date());
		tnotice.setStatus(Constants.CODE_TYPE_PUBSTATUS_NO);
		//届次
		Object secoObj=httpSession.getAttribute(ResourceUtil.getSessionSeco());
		Seco seco=secoObj==null?null:((Seco)secoObj);
		if(!util.isEmpty(seco)) {
			Tsecondary ts=new Tsecondary();
			BeanUtils.copyProperties(seco,ts);
//			tnotice.setTsecondary(ts);
		}
		
		noticeDAO.save(tnotice);
		
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_ADD, tnotice.getId(), "[通知公告]保存通知");
		
		return tnotice.getId();
	}
	//多次点保存时，更新记录
	public String saveForUpate(Notice notice,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		Tnotice tnotice = null;
		//更新
		tnotice=noticeDAO.get(Tnotice.class, notice.getId().trim());
		BeanUtils.copyProperties(notice, tnotice);
		tnotice.setCreatDate(new Date());
		tnotice.setStatus(Constants.CODE_TYPE_PUBSTATUS_NO);
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, tnotice.getId(), "[通知公告]保存更新通知");
		//届次
		Object secoObj=httpSession.getAttribute(ResourceUtil.getSessionSeco());
		Seco seco=secoObj==null?null:((Seco)secoObj);
		if(!util.isEmpty(seco)) {
			Tsecondary ts=new Tsecondary();
			BeanUtils.copyProperties(seco,ts);
//			tnotice.setTsecondary(ts);
		}
		
		noticeDAO.update(tnotice);
		
		return tnotice.getId();
	}
	public String upDateOrAdd(Notice notice,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		Tnotice tnotice = null;
		if(util.isEmpty(notice.getId())) {
		//新增
			tnotice = new Tnotice();
			BeanUtils.copyProperties(notice, tnotice);
			tnotice.setId(Generator.getInstance().getNoticeNO());
			tnotice.setCreatDate(new Date());
		} else {
		//更新
			tnotice=noticeDAO.get(Tnotice.class, notice.getId().trim());
			Date creatDate = tnotice.getCreatDate();
			BeanUtils.copyProperties(notice, tnotice);
			tnotice.setCreatDate(creatDate);
		}
		String deptId=((SessionInfo) httpSession.getAttribute("sessionInfo")).getDeptId();
		tnotice.setPubUnit(deptId);
		tnotice.setPubDate(new Date());
		tnotice.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES);
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, tnotice.getId(), "[通知公告]提交通知");
		//届次
		Object secoObj=httpSession.getAttribute(ResourceUtil.getSessionSeco());
		Seco seco=secoObj==null?null:((Seco)secoObj);
		if(!util.isEmpty(seco)) {
			Tsecondary ts=new Tsecondary();
			BeanUtils.copyProperties(seco,ts);
//			tnotice.setTsecondary(ts);
		}
		
		noticeDAO.saveOrUpdate(tnotice);
		
		
		
		return tnotice.getId();
	}

	public void del(Notice notice) throws Exception {
		
		Util util=new Util();
		String ids = notice.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					Tnotice tnotice = noticeDAO.get(Tnotice.class,id);
					noticeDAO.delete(tnotice);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_DEL, id, "[通知公告]通知删除（通知ID："+id+"）");
				}
			}
		}
	}
	public void pub(Notice notice,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		String ids = notice.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					Tnotice tnotice = noticeDAO.get(Tnotice.class,id);
					tnotice.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES); //已发布状态
					if(util.isEmpty(tnotice.getPubDate())){
						String deptId=((SessionInfo) httpSession.getAttribute("sessionInfo")).getDeptId();
						tnotice.setPubUnit(deptId);
						tnotice.setPubDate(new Date());
					}
					noticeDAO.update(tnotice);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[通知公告]通知发布（通知ID："+id+"）");
				}
			}
		}
	}
	public void cancelPub(Notice notice) throws Exception {
		
		Util util=new Util();
		String ids = notice.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					Tnotice tnotice = noticeDAO.get(Tnotice.class,id);
					tnotice.setStatus(Constants.CODE_TYPE_PUBSTATUS_NO); //已发布状态
					noticeDAO.update(tnotice);
					if(!util.isEmpty(tnotice.getPubDate())){
						tnotice.setPubUnit(null);
						tnotice.setPubDate(null);
					}
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[通知公告]通知取消发布（通知ID："+id+"）");
				}
			}
		}
	}
	public void edit(Notice notice) throws Exception {
		
		String id = notice.getId();
			if(id!=null && !"".equals(id)) {
			Tnotice tnotice = noticeDAO.get(Tnotice.class,id);
			tnotice.setTitle(notice.getTitle());
			tnotice.setPubUnit(notice.getPubUnit());
			tnotice.setReply(notice.getReply());
			tnotice.setNoticeType(notice.getNoticeType());
			tnotice.setValidDate(notice.getValidDate());
			tnotice.setMemo(notice.getMemo());
			tnotice.setContent(notice.getContent());
			tnotice.setAtts(notice.getAtts());
			//BeanUtils.copyProperties(notice, tnotice);
			//tnotice.setPubDate(new Date());
			noticeDAO.update(tnotice);
			//生成日志
			this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[通知公告]通知修改（通知ID："+id+"）");
		}
	}

	//日常办公首页统计
	public Map<String, Integer> noticeCount(HttpSession httpSession) {
		return null;
	}
}
