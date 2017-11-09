package com.tlzn.service.poll.imp;

import java.util.Iterator;
import java.util.List;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.Tcompany;
import com.tlzn.model.poll.Tpoll;
import com.tlzn.model.poll.TpollHandle;
import com.tlzn.model.sys.Tdolog;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.SessionInfo;
import com.tlzn.pageModel.sys.Comp;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.poll.PollHandleServiceI;
import com.tlzn.service.poll.PollServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Generator;
import com.tlzn.util.base.PropertyUtil;
import com.tlzn.util.base.NumberFormatTools;
import com.tlzn.util.base.Util;

@Service("pollHandleService")
public class PollHandleServiceImpl extends BaseServiceImpl implements PollHandleServiceI{

	BaseDaoI<TpollHandle> pollHandleDao;
	
	private PollServiceI pollService;
	
	
	public BaseDaoI<TpollHandle> getPollHandleDao() {
		return pollHandleDao;
	}
	@Autowired
	public void setPollHandleDao(BaseDaoI<TpollHandle> pollHandleDao) {
		this.pollHandleDao = pollHandleDao;
	}

	public PollServiceI getPollService() {
		return pollService;
	}
	@Autowired
	public void setPollService(PollServiceI pollService) {
		this.pollService = pollService;
	}
	/**
	 * 社情民意办理单位获取
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid handleDatagrid(TpollHandle handle) throws Exception {
		DataGrid j=new DataGrid();
		j.setRows(this.getPollHandle(handle));
		System.out.println("rows===="+j.getRows().size());
		return j;
	}
	/**
	 * 
	 * 转办数据保存
	 * 
	 * @param handle
	 * 
	 * @throws 	Exception
	 * 
	 */
	public void confirm(TpollHandle handle) throws Exception {
		saveHandle(handle);
		Tpoll poll=handle.getPoll();
		poll.setStatus(Constants.CODE_TYPE_POLL_STATUS_ZBZ);
		pollService.setHandle(poll);
		
		
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_REPLY);
		dolog.setKeyId(handle.getPoll().getPollId());
		dolog.setInfo( "[社情民意]社情民意转办处理");
		this.saveLog(dolog);
		
	}
	/**
	 * 
		 * 社情民意办理数据保存
		 * 
		 * @param handle
		 * 
		 * @throws 	Exception
		 * 
	 */
	private void saveHandle (TpollHandle handle) throws Exception {
		String hql="from TpollHandle t where t.poll.pollId='"+handle.getPoll().getPollId()+"'";
		List<TpollHandle> hList=pollHandleDao.find(hql);
		if(handle.getPoll().getHandleComp()!=null && !"".equals(handle.getPoll().getHandleComp())){
			String[] ids=handle.getPoll().getHandleComp().split(",");
			String[] mainflgs=null;
			if(handle.getMainFlg()!=null&&!"".equals(handle.getMainFlg())){
				mainflgs=handle.getMainFlg().split(",");
			}
			for(int i=0;i<ids.length;i++){
				int flg=0;
				for(int j=0;j<hList.size();j++){
					if(ids[i].equals(hList.get(j).getComp().getCompanyId())){
						TpollHandle h=hList.get(j);
						h.setStatus(Constants.CODE_TYPE_HFZT_NEW);
						if(mainflgs!=null&&mainflgs.length>0){
							h.setMainFlg(mainflgs[i]);
						}
						pollHandleDao.update(h);
						hList.remove(j);
						flg=1;
						break;
					}
				}
				if(flg!=1){
					TpollHandle hand = new TpollHandle();
					hand.setHandleId(Generator.getInstance().getPollHandleNO());
					Tpoll tp=handle.getPoll();
					hand.setPoll(tp);
					hand.setStatus(Constants.CODE_TYPE_HFZT_NEW);
					Tcompany tc=new Tcompany();
					tc.setCompanyId(ids[i]);
					if(mainflgs!=null&&mainflgs.length>0){
						hand.setMainFlg(mainflgs[i]);
					}
					hand.setComp(tc);
					pollHandleDao.save(hand);
				}
			}
			if(hList!=null&&hList.size()>0){
				for(TpollHandle t:hList){
					pollHandleDao.delete(t);
				}
			}
		}
				
	}
	/**
	 * 
	 * 承办单位调整
	 * 
	 * @param handle
	 * 
	 * @throws 	Exception
	 * 
	 */
	public void handleChange(TpollHandle handle) throws Exception {
		saveHandle(handle);
		Tpoll poll=handle.getPoll();
		poll.setStatus(Constants.CODE_TYPE_POLL_STATUS_WBL);
		pollService.setHandle(poll);
		
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_REPLY);
		dolog.setKeyId(handle.getPoll().getPollId());
		dolog.setInfo( "[社情民意]社情民意承办单位调整");
		this.saveLog(dolog);
		
	}
	/**
	 * 
	 * 社情民意办理信息或取
	 * 
	 * @param handle
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public List<TpollHandle> getPollHandle(TpollHandle handle) throws Exception {
		String hql="from TpollHandle t where t.poll.pollId='"+handle.getPoll().getPollId()+"' order by t.poll.pollCode";
		List<TpollHandle> list=pollHandleDao.find(hql);
		List<TpollHandle> resList=new ArrayList<TpollHandle>();
		for (Iterator<TpollHandle> it = list.iterator(); it.hasNext();) {
			TpollHandle th = it.next();
			Tcompany tc=new Tcompany();
			PropertyUtil.copyProperties(tc,th.getComp());
			tc.setCompanyTypeName(this.findDicName(Constants.CODE_TYPE_COMPANYTYPE, tc.getCompanyType()));
			tc.setStatusName(this.findDicName(Constants.CODE_TYPE_QYTY, tc.getStatus()));
			th.setComp(tc);
			Tpoll poll=new Tpoll();
			PropertyUtil.copyProperties(poll,th.getPoll());
			poll.setStatusName(this.findDicName("POLLSTATUS", poll.getStatus()));
			poll.setAdoptFlgName(this.findDicName("YESNO",poll.getAdoptFlg()));
			poll.setIssueFlgName(this.findDicName("YESNO",poll.getIssueFlg()));
			poll.setMergeFlgName(this.findDicName("YESNO",poll.getMergeFlg()));
			poll.setStressFlgName(this.findDicName("YESNO",poll.getStressFlg()));
			poll.setFilingFlgName(this.findDicName("YESNO",poll.getFilingFlg()));
			poll.setHandleTypeName(this.findDicName("BLLX", poll.getHandleType()));
			poll.setPollTypeName(this.findDicName("POLLTYPE", poll.getPollType()));
			poll.setEditorName(this.findNameByUserid(poll.getEditor()));
			poll.setContent("");
			poll.setEndContent("");
			th.setPoll(poll);
			
			th.setSolveHowName(this.findDicName(Constants.CODE_TYPE_JJCD, th.getSolveHow()));
			th.setCarryoutFlgName(this.findDicName(Constants.CODE_TYPE_YESNO, th.getCarryoutFlg()));
			th.setAnswerModeName(this.findDicName(Constants.CODE_TYPE_GTFS, th.getAnswerMode()));
			th.setReplyPassName(this.findDicName(Constants.CODE_TYPE_BFSC,th.getReplyPass()));
			th.setReply(th.getReply()==null? "":th.getReply().replaceAll("\"", "'"));//将双引号替换成单引号 
			th.setReply(th.getReply().replaceAll("\n", "&#10;").replaceAll("\r", ""));////将换行符替换成     &#10;
			th.setOpinionExplain(th.getOpinionExplain()==null? "":th.getOpinionExplain().replaceAll("\n", "&#10;").replaceAll("\r", ""));
			th.setRebutInfo(th.getRebutInfo()==null? "":th.getRebutInfo().replaceAll("\n", "&#10;").replaceAll("\r", ""));
			resList.add(th);
		}
		return resList;
	}
	/**
	 * 
	 * 拒绝申退
	 * 
	 * @param handle
	 * 
	 * @throws 	Exception
	 * 
	 */
	public void refuse(TpollHandle handle) throws Exception {
		TpollHandle th=pollHandleDao.get(TpollHandle.class,handle.getHandleId());
		th.setStatus(Constants.CODE_TYPE_HFZT_NEW);
		pollHandleDao.update(th);
		Tpoll poll=new Tpoll();
		PropertyUtil.copyProperties(poll,th.getPoll());
		if(Constants.CODE_TYPE_BLLX_DB.equals(poll.getHandleType())){
			poll.setStatus(Constants.CODE_TYPE_POLL_STATUS_WBL);
			pollService.setPollStatus(poll);
		}else{
			List<TpollHandle> list=getPollHandle(th);
			int flg=0;
			for (Iterator<TpollHandle> it = list.iterator(); it.hasNext();) {
				TpollHandle t = it.next();
				if(Constants.CODE_TYPE_HFZT_STZ.equals(t.getStatus())){
					flg=1;
					break;
				}
			}
			if(flg==0){
				poll.setStatus(Constants.CODE_TYPE_POLL_STATUS_WBL);
				pollService.setPollStatus(poll);
			}
		}
		
		
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_REPLY);
		dolog.setKeyId(th.getPoll().getPollId());
		dolog.setInfo( "[社情民意]社情民意拒绝申退（办复ID："+th.getHandleId()+"）");
		this.saveLog(dolog);
		
	}
	/**
	 * 
	 * 拒绝申退
	 * 
	 * @param handle
	 * 
	 * @throws 	Exception
	 * 
	 */
	public void pollRefuse(TpollHandle handle) throws Exception {
		Tpoll poll=pollService.findTpoll(handle.getPoll().getPollId());
		poll.setStatus(Constants.CODE_TYPE_POLL_STATUS_WBL);
		pollService.setPollStatus(poll);
		List<TpollHandle> list=getPollHandle(handle);
		for (Iterator<TpollHandle> it = list.iterator(); it.hasNext();) {
			TpollHandle t = it.next();
			
			t.setStatus(Constants.CODE_TYPE_HFZT_NEW);
			pollHandleDao.update(t);
		}
		
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_REPLY);
		dolog.setKeyId(poll.getPollId());
		dolog.setInfo( "[社情民意]社情民意拒绝申退");
		this.saveLog(dolog);
		
	}
	
	/**
	 * 社情民意办复列表获取
	 */	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(TpollHandle handle, String name)
			throws Exception {
		DataGrid j = new DataGrid();
		j.setRows(getHandsFromTHands(find(handle, name)));
		j.setTotal(total(handle, name));
		return j;
	}
	
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public TpollHandle findObj(TpollHandle handle) throws Exception {
		String id=handle.getHandleId();
		System.out.println("id==="+id);
		TpollHandle p =null;
		if(id!=null && !"".equals(id)){
			p=pollHandleDao.get(TpollHandle.class, id);
			p.setSolveHowName(this.findDicName("JJCD", p.getSolveHow())); //解决程度
			p.setCarryoutFlgName(this.findDicName("YESNO", p.getCarryoutFlg())); //是否落实
			p.setStatusName(this.findDicName("HFZT", p.getStatus())); //状态
			//  mainFlg; 主办1/协办0（会办）
			p.setAnswerModeName(this.findDicName("GTFS", p.getAnswerMode())); //沟通方式
			p.setReplyPassName(this.findDicName("BFSC",p.getReplyPass())); //办复审查状态
			
			p.getPoll().setPollTypeName(this.findDicName("POLLTYPE", p.getPoll().getPollType())); //社情民意类型
			p.getPoll().setHandleTypeName(this.findDicName("BLLX", p.getPoll().getHandleType())); //办理方式
			
			if(!Constants.DIC_TYPE_POLLTYPE_SHZJ.equals(p.getPoll().getPollType())){ //社会征集
				p.getPoll().setCreateManName(this.findNameByCommCode(p.getPoll().getCreateMan())); 
			}else{
				p.getPoll().setCreateManName(p.getPoll().getCreateMan());
			}
			
			p.setOpinionExplain(p.getOpinionExplain()==null? "":p.getOpinionExplain().replaceAll("\n", "&#10;").replaceAll("\r", ""));
			p.setRebutInfo(p.getRebutInfo()==null? "":p.getRebutInfo().replaceAll("\n", "&#10;").replaceAll("\r", ""));
		}
		return p;
	}
	/**
	 * 
	 * 审查驳回办复报告
	 * 
	 * @param handle
	 * @throws 	Exception
	 * 
	 */
	public void reject(TpollHandle handle) throws Exception {
		
		TpollHandle th= pollHandleDao.get(TpollHandle.class,handle.getHandleId());
		th.setRebutInfo(handle.getRebutInfo());
		th.setStatus(Constants.CODE_TYPE_HFZT_NEW);
		pollHandleDao.update(th);
		
		if(!Constants.CODE_TYPE_BLLX_FB.equals(th.getPoll().getHandleType())){
			String hql="update Tpoll t set t.reviewFlg='" + Constants.CODE_TYPE_YESNO_NO +"' where t.pollId='" +th.getPoll().getPollId() + "' ";
			pollHandleDao.executeHql(hql);
		}else{
			List<TpollHandle> list=getPollHandle(th);
			int flg=0;
			for (Iterator<TpollHandle> it = list.iterator(); it.hasNext();) {
				TpollHandle t = it.next();
				if(Constants.CODE_TYPE_HFZT_YBF.equals(t.getStatus())){
					flg=1;
					break;
				}
			}
			if(flg==0){
				String hql="update Tpoll t set t.reviewFlg='" + Constants.CODE_TYPE_YESNO_NO +"' where t.pollId='" +th.getPoll().getPollId() + "' ";
				pollHandleDao.executeHql(hql);
			}
		}
		
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_REPLY);
		dolog.setKeyId(th.getPoll().getPollId());
		dolog.setInfo( "[社情民意]社情民意办复审查驳回（单位名称："+th.getComp().getCompanyName()+"）");
		this.saveLog(dolog);
	}
	
	/**
	 * 
	 * 审查通过办复报告
	 * 
	 * @param handle
	 * @throws 	Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void checkPass(TpollHandle handle)throws Exception{
		TpollHandle th= pollHandleDao.get(TpollHandle.class,handle.getHandleId());
		th.setReplyPass(Constants.CODE_TYPE_YESNO_YES);
		pollHandleDao.update(th);
		
		if(!Constants.CODE_TYPE_BLLX_FB.equals(th.getPoll().getHandleType())){
			String hql="update Tpoll t set t.status='" + Constants.CODE_TYPE_POLL_STATUS_YBL +"' where t.pollId='" +th.getPoll().getPollId() + "' ";
			pollHandleDao.executeHql(hql);
		}else{
			List<TpollHandle> list=getPollHandle(th);
			int flg=0;
			for (Iterator<TpollHandle> it = list.iterator(); it.hasNext();) {
				TpollHandle t = it.next();
				if(!Constants.CODE_TYPE_HFZT_YBF.equals(t.getStatus())){
					flg=1;
					break;
				}
			}
			if(flg==0){
				String hql="update Tpoll t set t.status='" + Constants.CODE_TYPE_POLL_STATUS_YBL +"' where t.pollId='" +th.getPoll().getPollId() + "' ";
				pollHandleDao.executeHql(hql);
			}
		}
		
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_REPLY);
		dolog.setKeyId(th.getPoll().getPollId());
		dolog.setInfo( "[社情民意]社情民意办复审查通过（单位名称："+th.getComp().getCompanyName()+"）");
		this.saveLog(dolog);
	}
	/**
	 * 查找记录并筛选记录
	 */	
	private List<TpollHandle> find(TpollHandle hand, String name) {
		String hql = "from TpollHandle t where t.comp.companyId='" + name+"'";  
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(hand, hql, values);
		hql += " order by t.poll.pollCode,t.poll.pollId asc";
		return pollHandleDao.find(hql, values, hand.getPage(), hand.getRows());
	}
	/**
	 * 组合hql语句
	 */	
	private String addWhere(TpollHandle hand, String hql, List<Object> values) {
		if (!Util.getInstance().isEmpty(hand.getPoll()) ) {
			if (hand.getPoll().getPollCode() != null && !"".equals(hand.getPoll().getPollCode())) {
				hql += " and t.poll.pollCode = ? ";
				values.add(hand.getPoll().getPollCode());
			}
			if (hand.getPoll().getTitle() != null && !"".equals(hand.getPoll().getTitle())) {
				hql += " and t.poll.title like ? ";
				values.add("%" + hand.getPoll().getTitle() + "%");
			}
			//提交人名称查询
			if (hand.getPoll().getCreateManName() != null && !"".equals(hand.getPoll().getCreateManName())) {
				hql += " and ( t.poll.createMan like '%"+hand.getPoll().getCreateManName()
						+"%' or exists (select code from Tcommitteeman tt where tt.name like  '%"+hand.getPoll().getCreateManName()+"%' and t.poll.createMan like '%'||tt.code||'%'))";
			}
			if (hand.getPoll().getHandleType() != null && !"".equals(hand.getPoll().getHandleType())) {
				hql += " and t.poll.handleType = ? ";
				values.add(hand.getPoll().getHandleType());
			}
		}
			if (hand.getStatus() != null && !"".equals(hand.getStatus())) {
				hql += " and t.status = ? ";
				values.add(hand.getStatus());
			}
		return hql;
	}
	/**
	 * 从实体对象列表中复制出新建自造对象
	 */
	private List<TpollHandle> getHandsFromTHands(List<TpollHandle> hands) throws Exception {
		List<TpollHandle> ads = new ArrayList<TpollHandle>();
		if (hands != null && hands.size() > 0) {
			for (TpollHandle hand : hands) {
				Tpoll poll = new Tpoll();
				BeanUtils.copyProperties(hand.getPoll(), poll);
				poll.setHandleTypeName(this.findDicName("BLLX", poll.getHandleType()));
				poll.setPollTypeName(this.findDicName("POLLTYPE", poll.getPollType()));
				if(!Constants.DIC_TYPE_POLLTYPE_SHZJ.equals(poll.getPollType())){ //社会征集
					poll.setCreateManName(this.findNameByCommCode(poll.getCreateMan()));
				}else{
					poll.setCreateManName(poll.getCreateMan());
				}
				poll.setContent("");
				poll.setEndContent("");
				hand.setSolveHowName(this.findDicName("JJCD",hand.getSolveHow())); //解决方式
				hand.setCarryoutFlgName(this.findDicName("YESNO",hand.getCarryoutFlg())); // 是否落实
				hand.setStatusName(this.findDicName("HFZT", hand.getStatus())); //// 状态
				// mainFlg 主办1/协办0（会办）
				hand.setAnswerModeName(this.findDicName("GTFS", hand.getAnswerMode())); //沟通方式
				hand.setReplyPassName(this.findDicName("BFSC",hand.getReplyPass())); //办复审查状态
				//hand.setOpinionExplain(""); //带格式的申退理由不能显示列表，否则报错。在writejson时带格式的列表数据会抛。
				hand.setOpinionExplain(hand.getOpinionExplain()==null? "":hand.getOpinionExplain().replaceAll("\n", "&#10;").replaceAll("\r", ""));
				hand.setRebutInfo(hand.getRebutInfo()==null? "":hand.getRebutInfo().replaceAll("\n", "&#10;").replaceAll("\r", ""));
				hand.setReply("");
				hand.setPoll(poll);
				
				ads.add(hand);
			}
		}
		return ads;
	}
	/**
	 * 统计记录总数
	 */	
	private Long total(TpollHandle hand, String name) {
		String hql = "select count(*) from TpollHandle t where t.comp.companyId='" + name  
				+ "' and t.poll.status='" + Constants.CODE_TYPE_POLL_STATUS_WBL+"'"; //8:未办理
				
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(hand, hql, values);
		return pollHandleDao.count(hql, values);
	}
	
	/**
	 * 社情民意签收
	 */	
	public void receiv(TpollHandle handle) throws Exception {
		for(String id : handle.getIds().split(",")){
			TpollHandle t=pollHandleDao.get(TpollHandle.class, id);
			t.setStatus(Constants.CODE_TYPE_HFZT_YQS);	//"3"为已签收
			t.setReplyPass(Constants.CODE_TYPE_BFSC_WTG); //办复审查状态:单办
			pollHandleDao.update(t);
			Tdolog dolog=new Tdolog();
			dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
			dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_EDIT);
			dolog.setKeyId(t.getHandleId());
			dolog.setInfo( "[社情民意办复]社情民意办理签收");
			this.saveLog(dolog);
		}
	}
	
	public void save(TpollHandle handle) throws Exception {
		String id=handle.getHandleId();
		if(id!=null && !"".equals(id)){
			TpollHandle t=pollHandleDao.get(TpollHandle.class, id);
//			t.setSolveHow(handle.getSolveHow());
//			t.setAnswerMode(handle.getAnswerMode());
			t.setFactEnddate(handle.getFactEnddate());
//			t.setCarryoutFlg(handle.getCarryoutFlg());
			t.setOperator(handle.getOperator());
			t.setOperateTime(new Date());
			t.setReply(handle.getReply());
			t.setRebutInfo(handle.getRebutInfo());//办复建议
			t.setStatus(Constants.CODE_TYPE_HFZT_YBF);//已办复
			pollHandleDao.update(t);
			//修改社情民意的 是否办复审查：为1：是
			pollHandleDao.executeHql("update Tpoll t set t.reviewFlg='" + Constants.CODE_TYPE_YESNO_YES + "' where t.pollId='" + t.getPoll().getPollId() + "'");
			Tdolog dolog=new Tdolog();
			dolog.setLogType(Constants.LOG_TYPE_CODE_PROP);
			dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_REPLY);
			dolog.setKeyId(t.getPoll().getPollId());
			dolog.setInfo( "[社情民意办复]生成社情民意办复信息");
			this.saveLog(dolog);
		}
		
	}

	public void back(TpollHandle handle) throws Exception {
		String id=handle.getHandleId();
		if(id!=null && !"".equals(id)){	
			TpollHandle t=pollHandleDao.get(TpollHandle.class, id);
			t.setOpinionExplain(handle.getOpinionExplain());
			t.setOperator(handle.getOperator());
			t.setOperateTime(new Date());
			t.setStatus(Constants.CODE_TYPE_HFZT_STZ);	//"2"为申退中
			pollHandleDao.update(t);
			String hql="update Tpoll t set t.status='" + Constants.CODE_TYPE_POLL_STATUS_STZ + "' where t.pollId='" + t.getPoll().getPollId() + "'"; 
			pollHandleDao.executeHql(hql);	//将社情民意状态改为申退中
			Tdolog dolog=new Tdolog();
			dolog.setLogType(Constants.LOG_TYPE_CODE_PROP);
			dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_BACK);
			dolog.setKeyId(t.getPoll().getPollId());
			dolog.setInfo( "[社情民意办复]社情民意办理申退");
			this.saveLog(dolog);
		}
		
	}
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public String report(TpollHandle handle, String companyId) throws Exception {
		String hql="from TpollHandle t where t.comp.companyId='" + companyId  + "' ";
		List<Object> values = new ArrayList<Object>();
		hql=this.addWhere(handle, hql, values);
		hql+="order by t.poll.pollCode";
		
		List<TpollHandle> list=this.getHandsFromTHands(pollHandleDao.find(hql));
		String fileName=this.writeExcel(list, "/pollHandle_report.xls");
		
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_REPORT);
		dolog.setInfo( "[社情民意]社情民意详细数据报表生成下载");
		this.saveLog(dolog);
		return fileName;
	}
	/*
	 * 判断对象属性是否为空，用空字符串替换null	
	*/
	private String changeNull(Object o){
		return o==null ? "" : o.toString().trim();
	}
	/*
	 * 写入数据Excel文件	
	*/
	private String writeExcel(List<TpollHandle> list,String fileName)throws Exception{
		
		Properties prop = new Properties();   
    	prop.load(this.getClass().getResourceAsStream("/config.properties")); //读取config.properties中的导出
    	String exportPath=prop.getProperty("exportPath");
        File path=new File(Constants.ROOTPATH + exportPath);
        if(!path.exists())path.mkdir();//如果路径不存在，则创建
        String filePath=exportPath + fileName;
        
        WritableWorkbook book= Workbook.createWorkbook(new File(path ,fileName));
		WritableSheet sheet  =  book.createSheet( "Sheet1 " ,  0 );
		
		sheet.getSettings().setVerticalFreeze(2);  // 设置行冻结前2行   // ss.setHorizontalFreeze(2);  // 设置列冻结 
		WritableCellFormat wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("微软雅黑"), 14 ,WritableFont.BOLD));           
		WritableCellFormat wcf2 = new WritableCellFormat(new WritableFont(WritableFont.createFont("微软雅黑"), 9 ,WritableFont.NO_BOLD));            
		        
		wcf.setAlignment(jxl.format.Alignment.CENTRE);  //平行居中 // wcf2.setBackground(Colour.LIGHT_ORANGE);    
		wcf.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中
		wcf2.setAlignment(jxl.format.Alignment.CENTRE);  //平行居中 
		wcf2.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中 
		
		sheet.mergeCells( 0 , 0 , 14 , 0 ); // 合并单元格  
		jxl.write.Label titleLabel = new Label( 0 , 0 , "办理回复明细表",wcf);
		sheet.addCell(titleLabel);           //          将定义好的单元格添加到工作表中 
		sheet.setRowView(0, 500); // 设置第一行的高度  20121111           
		
		int[] headerArrHight = {5,10,20,10,40,20,20,10,15,15,20,10,20,30}; //列宽           
		//String[] title={"序号","社情民意编号","届次","社情民意类型","社情民意标题","创建时间","提交人","实际办结日期","办理单位","办理类型","解决程度","沟通方式"};
		String[] title={"序号","社情民意编号","届次","社情民意类型","社情民意标题","创建时间","提交人","实际办结日期","办理单位","办理类型"};
		for(int i=0;i<title.length;i++){
			sheet.addCell(new  Label( i , 1 , title[i], wcf2));
			sheet.setColumnView(i, headerArrHight[i]);            
		}
		
		int row=2;
		for(int i=0;i<list.size();i++){
			sheet.addCell(new  Label( 0 , row , String.valueOf(row-1), wcf2) );
			sheet.addCell(new  Label( 1 , row , changeNull(list.get(i).getPoll().getPollCode()), wcf2 ));
			sheet.addCell(new  Label( 2 , row , changeNull(list.get(i).getPoll().getTsecondary().getSecondayName())));
			sheet.addCell(new  Label( 3 , row , changeNull(list.get(i).getPoll().getPollTypeName())));
			sheet.addCell(new  Label( 4 , row , changeNull(list.get(i).getPoll().getTitle())));
			sheet.addCell(new  Label( 5 , row , changeNull(list.get(i).getPoll().getCreateTime())));
			sheet.addCell(new  Label( 6 , row , changeNull(list.get(i).getPoll().getCreateManName())));
			sheet.addCell(new  Label( 7 , row , changeNull(list.get(i).getFactEnddate())));
			sheet.addCell(new  Label( 8 , row , changeNull(list.get(i).getComp().getCompanyName())));
			sheet.addCell(new  Label( 9 , row , changeNull(list.get(i).getPoll().getHandleTypeName()), wcf2 ));
//			sheet.addCell(new  Label( 10 , row , changeNull(list.get(i).getSolveHowName())));
//			sheet.addCell(new  Label( 11 , row , changeNull(list.get(i).getAnswerModeName())));
			row++;
		}
		book.write();
        book.close();
        return filePath;
	}
	/**
	 * 
	 *社情民意处理统计
	 * 
	 * @param httpSession
	 * 
	 * @throws 	Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public Map<String, Integer> pollHandleCount(HttpSession httpSession) throws Exception{
	Map<String,Integer> countMap=new HashMap<String, Integer>();
	SessionInfo sessionInfo=((SessionInfo)httpSession.getAttribute("sessionInfo"));
	String companyId = "";
	if(httpSession.getAttribute("sessionComp")!=null) {
		companyId = ((Comp)httpSession.getAttribute("sessionComp")).getCompanyId();
	}
	
	String sql="";
	if(Constants.DIC_TYPE_YHZB_CBDW.equals(sessionInfo.getUserGroup())){
		sql="select 'newPoll',count(*) from TPOLL_HANDLE t inner join TCOMPANY t2 on t.COMP_ID=t2.COMPANY_ID and t2.COMPANY_ID='"+companyId+"'"
				+" where t.status ='"+Constants.CODE_TYPE_HFZT_NEW+"'" //新
		+" union "
	    +"select 'yqsPoll',count(*) from TPOLL_HANDLE t inner join TCOMPANY t2 on t.COMP_ID=t2.COMPANY_ID and t2.COMPANY_ID='"+companyId+"'"
			+" and t.STATUS='"+Constants.CODE_TYPE_HFZT_YQS+"'" //已签收
		+" union "
	    +"select 'stzPoll',count(*) from TPOLL_HANDLE t inner join TCOMPANY t2 on t.COMP_ID=t2.COMPANY_ID and t2.COMPANY_ID='"+companyId+"'"
			+" and t.STATUS='"+Constants.CODE_TYPE_HFZT_STZ+"'" //申退中
		+" union "
	    +"select 'ydfPoll',count(*) from TPOLL_HANDLE t inner join TCOMPANY t2 on t.COMP_ID=t2.COMPANY_ID and t2.COMPANY_ID='"+companyId+"'"
			+" and t.STATUS='"+Constants.CODE_TYPE_HFZT_YBF+"'" //已答复
		;
		System.out.println(sql);
		List<Object[]> list =(List<Object[]>) pollHandleDao.executeCountSql(sql);
			int newPollNum=0;
			int yqsPollNum=0;
			int stzPollNum=0;
			int ydfPollNum=0;
			for(int i=0;i<list.size();i++){
				Object[] objs=list.get(i);
				 //新
				if("newPoll".equals(objs[0])){
					newPollNum=NumberFormatTools.getInstance().toInteger(objs[1]);
				}
				//已签收
				if("yqsPoll".equals(objs[0])){
					yqsPollNum=NumberFormatTools.getInstance().toInteger(objs[1]);
				}
				//申退中
				if("stzPoll".equals(objs[0])){
					stzPollNum=NumberFormatTools.getInstance().toInteger(objs[1]);
				}
				//已答复
				if("ydfPoll".equals(objs[0])){
					ydfPollNum=NumberFormatTools.getInstance().toInteger(objs[1]);
				}
	
			}
			countMap.put("newPollNum", newPollNum);
			countMap.put("yqsPollNum", yqsPollNum);
			countMap.put("stzPollNum", stzPollNum);
			countMap.put("ydfPollNum", ydfPollNum);
	}
	else {
		
	}
	return countMap;
	}
}
