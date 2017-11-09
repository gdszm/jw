package com.tlzn.service.dailywork.imp;

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
import com.tlzn.model.dailywork.TfileData;
import com.tlzn.model.sys.Tsecondary;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.SessionInfo;
import com.tlzn.pageModel.dailywork.FileData;
import com.tlzn.pageModel.sys.Seco;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.dailywork.MeetFileServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Generator;
import com.tlzn.util.base.ResourceUtil;

@Service("fileDataService")
public class MeetFileServiceImpl extends BaseServiceImpl implements MeetFileServiceI{

	private BaseDaoI<TfileData> fileDataDAO;
	
	public BaseDaoI<TfileData> getFileDataDAO() {
		return fileDataDAO;
	}
	@Autowired
	public void setFileDataDAO(BaseDaoI<TfileData> fileDataDAO) {
		this.fileDataDAO = fileDataDAO;
	}
	
	/**
	 * 
	 * 查询单条文件
	 * 
	 * @param fileData
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public FileData get(String id) throws Exception {
		TfileData tfileData= fileDataDAO.get(TfileData.class, id);
		FileData fileData = new FileData();
		BeanUtils.copyProperties(tfileData, fileData);

		fileData.setFromtypeName(this.findDicName("FROMTYPE", fileData.getFromtype()));
		fileData.setDatatypeName(this.findDicName("MEETINGTYPE", fileData.getDatatype()));
		fileData.setDepName(this.findNameByDepid(fileData.getDepid()));
		fileData.setStatusName(this.findDicName("PUBSTATUS", fileData.getStatus()));
		
		return fileData;
	}

	/**
	 * 
	 * 查询
	 * 
	 * @param fileData
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(FileData fileData) throws Exception {
		String hql=" from TfileData t where 1=1";
		DataGrid j = new DataGrid();
		j.setRows(editFileDatas(find(fileData,hql)));
		j.setTotal(total(fileData,hql));
		return j;
	}
	private List<FileData> editFileDatas(List<TfileData> fileDataList) throws Exception {
		List<FileData> fileDatas = new ArrayList<FileData>();
		if (fileDataList != null && fileDataList.size() > 0) {
			for (TfileData tfileData: fileDataList) {
				FileData fileData = new FileData();
				BeanUtils.copyProperties(tfileData, fileData);
				
				fileData.setFromtypeName(this.findDicName("FROMTYPE", fileData.getFromtype()));
				fileData.setDatatypeName(this.findDicName("MEETINGTYPE", fileData.getDatatype()));
				fileData.setDepName(this.findNameByDepid(fileData.getDepid()));
				fileData.setStatusName(this.findDicName("PUBSTATUS", fileData.getStatus()));
				
				fileData.setContent("");
				fileData.setMemo("");
				fileDatas.add(fileData);
			}
		}
		return fileDatas;
	}
	private List<TfileData> find(FileData fileData,String hql) throws Exception {
		hql = addWhere(fileData, hql);
		if (fileData.getSort() != null && fileData.getOrder() != null) {
			hql += " order by " + fileData.getSort() + " " + fileData.getOrder();
		}else {
			hql+=" order by t.pubdate desc,t.createtime desc";
		}
		List<TfileData> fileDataList = fileDataDAO.find(hql, fileData.getPage(), fileData.getRows());
		return fileDataList;
	}
	private Long total(FileData fileData,String hql) throws Exception {
		hql = addWhere(fileData, hql);
		hql = "select count(*) "+hql;
		return fileDataDAO.count(hql);
	}
	private String addWhere(FileData fileData, String hql) throws Exception {
		Util util=new Util();
		if (!util.isEmpty(fileData.getFromtype())) {
			hql += " and t.fromtype='" + fileData.getFromtype() + "' ";
		}
		if (!util.isEmpty(fileData.getDatatype())) {
			hql += " and t.datatype='" + fileData.getDatatype() + "' ";
		}
		if(!util.isEmpty(fileData.getTitle())){
			 hql += " and t.title like '%"+fileData.getTitle().trim()+"%'";
		}
		if(!util.isEmpty(fileData.getContent())){
			hql += " and t.content like '%" + fileData.getContent().trim() + "%'";
		}
	    if (!util.isEmpty(fileData.getDepName())) {
		   hql += " and exists (from Tdept b where t.depid=b.cid and b.cname like'%"+fileData.getDepName()+"%')";
	    }
		if (!util.isEmpty(fileData.getStatus())) {
			hql += " and t.status='" + fileData.getStatus() + "' ";
		}
		 if(fileData.getPubDateStart()!=null){
			 hql+=" and to_char(t.pubdate,'yyyy-MM-dd') >= '"+util.getFormatDate(fileData.getPubDateStart(),"yyyy-MM-dd")+"'";
		 }
		 if(fileData.getPubDateEnd()!=null){
		 hql+=" and to_char(t.pubdate,'yyyy-MM-dd') <= '"+util.getFormatDate(fileData.getPubDateEnd(),"yyyy-MM-dd")+"'";
		 }
		return hql;
	}
	
	public String save(FileData fileData,HttpSession httpSession) throws Exception {
		
		TfileData tfileData = new TfileData();
		BeanUtils.copyProperties(fileData, tfileData);
		tfileData.setDataId(Generator.getInstance().getFileDataNO());
		tfileData.setCreatetime(new Date());
		tfileData.setStatus(Constants.CODE_TYPE_PUBSTATUS_NO);
		//届次
//		OBJECT SECOOBJ=HTTPSESSION.GETATTRIBUTE(RESOURCEUTIL.GETSESSIONSECO());
//		SECO SECO=SECOOBJ==NULL?NULL:((SECO)SECOOBJ);
//		IF(!UTIL.ISEMPTY(SECO)) {
//			TSECONDARY TS=NEW TSECONDARY();
//			BEANUTILS.COPYPROPERTIES(SECO,TS);
//			tfileData.setTsecondary(ts);
//		}
		
		fileDataDAO.save(tfileData);
		
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_ADD, tfileData.getDataId(), "[会议文件]保存文件");
		
		return tfileData.getDataId();
	}
	//多次点保存时，更新记录
	public String saveForUpate(FileData fileData,HttpSession httpSession) throws Exception {
		
		TfileData tfileData = null;
		//更新
		tfileData=fileDataDAO.get(TfileData.class, fileData.getDataId().trim());
		BeanUtils.copyProperties(fileData, tfileData);
		tfileData.setCreatetime(new Date());
		tfileData.setStatus(Constants.CODE_TYPE_PUBSTATUS_NO);
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, tfileData.getDataId(), "[会议文件]保存更新文件");
		//届次
//		Object secoObj=httpSession.getAttribute(ResourceUtil.getSessionSeco());
//		Seco seco=secoObj==null?null:((Seco)secoObj);
//		if(!util.isEmpty(seco)) {
//			Tsecondary ts=new Tsecondary();
//			BeanUtils.copyProperties(seco,ts);
//			tfileData.setTsecondary(ts);
//		}
		
		fileDataDAO.update(tfileData);
		
		return tfileData.getDataId();
	}
	public String upDateOrAdd(FileData fileData,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		TfileData tfileData = null;
		if(util.isEmpty(fileData.getDataId())) {
		//新增
			tfileData = new TfileData();
			BeanUtils.copyProperties(fileData, tfileData);
			tfileData.setDataId(Generator.getInstance().getFileDataNO());
			tfileData.setCreatetime(new Date());
		} else {
		//更新
			tfileData=fileDataDAO.get(TfileData.class, fileData.getDataId().trim());
			Date creatDate = tfileData.getCreatetime();
			BeanUtils.copyProperties(fileData, tfileData);
			tfileData.setCreatetime(creatDate);
		}
		String deptId=((SessionInfo) httpSession.getAttribute("sessionInfo")).getDeptId();
		tfileData.setDepid(deptId);
		tfileData.setPubdate(new Date());
		tfileData.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES);
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, tfileData.getDataId(), "[会议文件]提交文件");
		//届次
//		Object secoObj=httpSession.getAttribute(ResourceUtil.getSessionSeco());
//		Seco seco=secoObj==null?null:((Seco)secoObj);
//		if(!util.isEmpty(seco)) {
//			Tsecondary ts=new Tsecondary();
//			BeanUtils.copyProperties(seco,ts);
//			tfileData.setTsecondary(ts);
//		}
		
		fileDataDAO.saveOrUpdate(tfileData);
		
		return tfileData.getDataId();
	}

	public void del(FileData fileData) throws Exception {
		
		Util util=new Util();
		String ids = fileData.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					TfileData tfileData = fileDataDAO.get(TfileData.class,id);
					fileDataDAO.delete(tfileData);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_DEL, id, "[会议文件]文件删除（文件ID："+id+"）");
				}
			}
		}
	}
	public void pub(FileData fileData,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		String ids = fileData.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					TfileData tfileData = fileDataDAO.get(TfileData.class,id);
					tfileData.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES); //已发布状态
					if(util.isEmpty(tfileData.getPubdate())){
						String deptId=((SessionInfo) httpSession.getAttribute("sessionInfo")).getDeptId();
						tfileData.setDepid(deptId);
						tfileData.setPubdate(new Date());
					}
					
					fileDataDAO.update(tfileData);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[会议文件]文件发布（文件ID："+id+"）");
				}
			}
		}
	}
	public void cancelPub(FileData fileData) throws Exception {
		
		Util util=new Util();
		String ids = fileData.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					TfileData tfileData = fileDataDAO.get(TfileData.class,id);
					tfileData.setStatus(Constants.CODE_TYPE_PUBSTATUS_NO); //已发布状态
					if(!util.isEmpty(tfileData.getPubdate())){
						tfileData.setDepid(null);
						tfileData.setPubdate(null);
					}
					fileDataDAO.update(tfileData);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[会议文件]文件取消发布（文件ID："+id+"）");
				}
			}
		}
	}
	public void edit(FileData fileData) throws Exception {
		
		String id = fileData.getDataId();
			if(id!=null && !"".equals(id)) {
			TfileData tfileData = fileDataDAO.get(TfileData.class,id);
			tfileData.setTitle(fileData.getTitle());
			tfileData.setDatatype(fileData.getDatatype());
			tfileData.setKey(fileData.getKey());
			tfileData.setFromtype(fileData.getFromtype());
			tfileData.setContent(fileData.getContent());
			tfileData.setMemo(fileData.getMemo());
			tfileData.setAtts(fileData.getAtts());
			fileDataDAO.update(tfileData);
			//生成日志
			this.saveLog(Constants.LOG_TYPE_CODE_DAILYWORK, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[会议文件]文件修改（文件ID："+id+"）");
		}
	}
}
