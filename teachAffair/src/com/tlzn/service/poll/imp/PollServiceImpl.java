package com.tlzn.service.poll.imp;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import jxl.SheetSettings;
import jxl.Workbook;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sun.org.apache.commons.beanutils.BeanUtils;
import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.Tproposal;
import com.tlzn.model.Tproposaler;
import com.tlzn.model.poll.Tpoll;
import com.tlzn.model.poll.TpollCheck;
import com.tlzn.model.sys.Tdolog;
import com.tlzn.model.sys.Tsecondary;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.SessionInfo;
import com.tlzn.pageModel.info.Proposal;
import com.tlzn.pageModel.sys.Comm;
import com.tlzn.pageModel.sys.Comp;
import com.tlzn.pageModel.sys.Seco;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.poll.PollCheckServiceI;
import com.tlzn.service.poll.PollServiceI;
import com.tlzn.service.sys.CommServiceI;
import com.tlzn.service.sys.CompServiceI;
import com.tlzn.service.sys.SecoServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Generator;
import com.tlzn.util.base.NumberFormatTools;
import com.tlzn.util.base.ResourceUtil;
import com.tlzn.util.base.Util;
@Service("pollService")
public class PollServiceImpl extends BaseServiceImpl implements PollServiceI{

	private BaseDaoI<Tpoll> pollDAO;
	
	private CompServiceI compService;
	
	private CommServiceI commService;
	
	private PollCheckServiceI pollCheckService;
	//届次
	private SecoServiceI secoService;
	
	public BaseDaoI<Tpoll> getPollDAO() {
		return pollDAO;
	}
	@Autowired
	public void setPollDAO(BaseDaoI<Tpoll> pollDAO) {
		this.pollDAO = pollDAO;
	}
	
	public CompServiceI getCompService() {
		return compService;
	}
	@Autowired
	public void setCompService(CompServiceI compService) {
		this.compService = compService;
	}
	public CommServiceI getCommService() {
		return commService;
	}
	@Autowired
	public void setCommService(CommServiceI commService) {
		this.commService = commService;
	}
	
	public PollCheckServiceI getPollCheckService() {
		return pollCheckService;
	}
	@Autowired
	public void setPollCheckService(PollCheckServiceI pollCheckService) {
		this.pollCheckService = pollCheckService;
	}
	
	public SecoServiceI getSecoService() {
		return secoService;
	}
	@Autowired
	public void setSecoService(SecoServiceI secoService) {
		this.secoService = secoService;
	}
	/**
	 * 
	 * 新增
	 * 
	 * @param poll,httpSession
	 * 
	 * @throws 	Exception
	 * 
	 */
	public String add(Tpoll poll,HttpSession httpSession) throws Exception {
		Seco seco=(Seco)httpSession.getAttribute("sessionSeco");
		Tsecondary ts=new Tsecondary();
		BeanUtils.copyProperties(ts,seco);
		poll.setTsecondary(ts);
		Comm comm=(Comm)httpSession.getAttribute(ResourceUtil.getSessionComm());
		if(comm!=null){
			if(Constants.DIC_TYPE_GROUP_WY.equals(comm.getGroupCode())){
				poll.setPollType(Constants.DIC_TYPE_POLLTYPE_WY);
				poll.setWriter(comm.getName());
			}else if(Constants.DIC_TYPE_GROUP_ZWH.equals(comm.getGroupCode())){
				poll.setPollType(Constants.DIC_TYPE_POLLTYPE_ZWH);
			}else if(Constants.DIC_TYPE_GROUP_QXQ.equals(comm.getGroupCode())){
				poll.setPollType(Constants.DIC_TYPE_POLLTYPE_QXQ);
			}else{
				poll.setPollType(Constants.DIC_TYPE_POLLTYPE_QT);
			}
		}
		poll.setEndContent(poll.getContent());
		poll.setPollId(Generator.getInstance().getPollNO());
		poll.setStatus(Constants.CODE_TYPE_POLL_STATUS_YBC);
		poll.setUpdateFlg(Constants.CODE_TYPE_UPDATE_YES);
		poll.setDiscard(Constants.CODE_TYPE_YESNO_NO);
		poll.setCreateTime(new Date());
		pollDAO.save(poll);
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_ADD);
		dolog.setKeyId(poll.getPollId());
		dolog.setInfo( "[社情民意]新增社情民意");
		this.saveLog(dolog);
		return poll.getPollId();
		
	}
	/**
	 * 
	 * 发言转弃到社情民意处理（新增）
	 * 
	 * @param poll,httpSession
	 * 
	 * @throws 	Exception
	 * 
	 */
	public String discardAdd(Tpoll poll,HttpSession httpSession) throws Exception {
		pollDAO.save(poll);
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_ADD);
		dolog.setKeyId(poll.getPollId());
		dolog.setInfo( "[社情民意]新增社情民意(执行发言转弃时)");
		this.saveLog(dolog);
		return poll.getPollId();
		
	}
	/**
	 * 
	 * 新增
	 * 
	 * @param poll,httpSession
	 * 
	 * @throws 	Exception
	 * 
	 */
	public String submitAdd(Tpoll poll,HttpSession httpSession) throws Exception {
		Seco seco=(Seco)httpSession.getAttribute("sessionSeco");
		Tsecondary ts=new Tsecondary();
		BeanUtils.copyProperties(ts,seco);
		poll.setTsecondary(ts);
		String createMan = poll.getCreateMan();
		Comm comm=null;
		if(createMan!=null && !"".equals(createMan)) {
			String[] array = createMan.split(",");
			comm=commService.getCommBycode(array[0]);
		}
		
		if(comm!=null){
			if(Constants.DIC_TYPE_GROUP_WY.equals(comm.getGroupCode())){
				poll.setPollType(Constants.DIC_TYPE_POLLTYPE_WY);
				poll.setWriter(comm.getName());
			}else if(Constants.DIC_TYPE_GROUP_ZWH.equals(comm.getGroupCode())){
				poll.setPollType(Constants.DIC_TYPE_POLLTYPE_ZWH);
			}else if(Constants.DIC_TYPE_GROUP_QXQ.equals(comm.getGroupCode())){
				poll.setPollType(Constants.DIC_TYPE_POLLTYPE_QXQ);
			}else{
				poll.setPollType(Constants.DIC_TYPE_POLLTYPE_QT);
			}
		}
		poll.setEndContent(poll.getContent());
		poll.setPollId(Generator.getInstance().getPollNO());
		poll.setStatus(Constants.CODE_TYPE_POLL_STATUS_WSC);
		poll.setDiscard(Constants.CODE_TYPE_YESNO_NO);
		poll.setUpdateFlg(Constants.CODE_TYPE_UPDATE_YES);
		poll.setCreateTime(new Date());
		pollDAO.save(poll);
		
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_ADD);
		dolog.setKeyId(poll.getPollId());
		dolog.setInfo( "[社情民意]添加社情民意");
		this.saveLog(dolog);
		return poll.getPollId();
		
	}
	/**
	 * 
	 * 提交
	 * 
	 * @param poll,httpSession
	 * 
	 * @throws 	Exception
	 * 
	 */
	public void submit(Tpoll poll,HttpSession httpSession) throws Exception {
		if(poll.getPollId()==null || "".equals(poll.getPollId())){
			poll.setPollId(this.add(poll, httpSession));
		}
		
		Tpoll t=pollDAO.get(Tpoll.class, poll.getPollId());
		t.setStatus(Constants.CODE_TYPE_POLL_STATUS_WSC);
		t.setUpdateFlg(Constants.CODE_TYPE_UPDATE_YES);
		pollDAO.update(t);
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_SUBMIT);
		dolog.setKeyId(poll.getPollId());
		dolog.setInfo( "[社情民意]提交社情民意");
		this.saveLog(dolog);
	}
	/**
	 * 
	 * 我的社情民意
	 * 
	 * @param poll,userCode,secondaryCode
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(Tpoll poll,String userCode, String secondaryCode)
			throws Exception {
		String hql=" from Tpoll t1 where t1.tsecondary.secondaryId='"+secondaryCode+"' and t1.discard!='"+Constants.CODE_TYPE_YESNO_YES+"' and t1.createMan like '%"+userCode+"%' ";
		DataGrid j = new DataGrid();
		j.setRows(getPollList(find(poll,hql)));
		j.setTotal(total(poll,hql));
		return j;
	}
	/**
	 * 
	 * 委员履职详情，社情民意列表查询
	 * 
	 * @param poll,userCode,secondaryCode
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagridFulfil(Tpoll poll,String userCode, String secondaryCode) 
			throws Exception {
		String hql=" from Tpoll t1 where t1.tsecondary.secondaryId='"
			+secondaryCode+
			"' and t1.discard!='"+Constants.CODE_TYPE_YESNO_YES
			+"' and t1.status!='"+Constants.CODE_TYPE_POLL_STATUS_YBC
			+"' and t1.createMan like '%"+userCode+"%' ";
		hql+=" order by t1.createTime desc";
		List<Tpoll> pollList = pollDAO.find(hql, poll.getPage(), poll.getRows());
		DataGrid j = new DataGrid();
		j.setRows(getPollList(pollList));
		
		hql = "select count(*) "+hql;
		j.setTotal(pollDAO.count(hql));
		return j;
	}
	/**
	 * 
		 * 数据转换
		 * 
		 * @param TpollArr
		 * 
		 * @throws Exception
		 * 
		 * @return 		List<Tpoll> 
	 */
	private List<Tpoll> getPollList(List<Tpoll> TpollArr) throws Exception {
		List<Tpoll> ads = new ArrayList<Tpoll>();
		if (TpollArr != null && TpollArr.size() > 0) {
			for (Tpoll t : TpollArr) {
				t.setStatusName(this.findDicName("POLLSTATUS", t.getStatus()));
				t.setAdoptFlgName(this.findDicName("YESNO",t.getAdoptFlg()));
				t.setIssueFlgName(this.findDicName("YESNO",t.getIssueFlg()));
				t.setMergeFlgName(this.findDicName("YESNO",t.getMergeFlg()));
				t.setStressFlgName(this.findDicName("YESNO",t.getStressFlg()));
				t.setFilingFlgName(this.findDicName("YESNO",t.getFilingFlg()));
				
				t.setHandleTypeName(this.findDicName("BLLX", t.getHandleType()));
				//办理单位名称
				String compNames="";
				if(t.getHandleComp()!=null&& !"".equals(t.getHandleComp())){
					String[] compids=t.getHandleComp().split(",");
					for(int j=0;j<compids.length;j++){
						Comp comp= compService.getCompBycode(compids[j]);
						compNames+=comp.getShortName()+",";
					}
				}
				if(!"".equals(compNames)){
					compNames=compNames.substring(0,(compNames.length()-1));
				}
				t.setHandleCompName(compNames);
				t.setPollTypeName(this.findDicName("POLLTYPE", t.getPollType()));
				t.setEditorName(this.findNameByUserid(t.getEditor()));
				t.setCreateManName(this.findNameByCommCode(t.getCreateMan()));
				t.setContent("");
				t.setEndContent("");
				
				//gds start
				//印发人姓名
				t.setIssuerName(this.findNameByUserid(t.getIssuer()));
				//gds end
				ads.add(t);
			}
		}
		return ads;
	}
	/**
	 * 
		 * 数据查询
		 * 
		 * @param poll
		 * 
		 * 
		 * @return 		List<Tpoll> 
	 */
	private List<Tpoll> find(Tpoll poll,String hql) {

		hql = addWhere(poll, hql);
		
		if (poll.getSort() != null && poll.getOrder() != null) {
			hql += " order by " + poll.getSort() + " " + poll.getOrder();
		}else {
			//hql+=" order by t1.pollCode asc,t1.status asc,t1.checkTime desc,t1.createTime desc";
			hql+=" order by t1.createTime desc";
		}
		List<Tpoll> pollList = pollDAO.find(hql, poll.getPage(), poll.getRows());
		return pollList;
	}
	/**
	 * 
		 * 审批数据查询
		 * 
		 * @param poll
		 * 
		 * 
		 * @return 		List<Tpoll> 
	 */
	private List<Tpoll> findCheck(Tpoll poll,String hql) {

		hql = addWhere(poll, hql);
		
		if (poll.getSort() != null && poll.getOrder() != null) {
			hql += " order by " + poll.getSort() + " " + poll.getOrder();
		}else {
			hql+=" order by t1.status asc,t1.pollCode asc,t1.checkTime desc,t1.createTime desc";
		}
		
		return pollDAO.find(hql, poll.getPage(), poll.getRows());
	}
	/**
	 * 
		 * 数据统计
		 * 
		 * @param poll
		 * 
		 * 
		 * @return 		Long
	 */
	
	private Long total(Tpoll poll,String hql) {
		hql = addWhere(poll, hql);
		hql = "select count(*) "+hql;
		return pollDAO.count(hql);
	}
	/**
	 * 
		 * 条件设置
		 * 
		 * @param poll，hql
		 * 
		 * 
		 * @return 		String
	 */
	private String addWhere(Tpoll poll, String hql) {
		if (poll.getTsecondary()!= null ) {
			hql += " and t1.tsecondary.secondaryId = '"+poll.getTsecondary().getSecondaryId()+"'";
		}
		if (poll.getPollCode() != null && !"".equals(poll.getPollCode())) {
			hql += " and t1.pollCode = '"+poll.getPollCode()+"'";
		}
		if (poll.getPollType() != null && !"".equals(poll.getPollType())) {
			hql += " and t1.pollType = '"+poll.getPollType()+"'";
		}
		if (poll.getTitle() != null && !"".equals(poll.getTitle())) {
			hql += " and t1.title like '%"+poll.getTitle().trim()+"%'";
		}
		if (poll.getStatus() != null && !"".equals(poll.getStatus())&& !"请选择...".equals(poll.getStatus())) {
			hql += " and t1.status ='"+poll.getStatus()+"'";
		}
		if (poll.getCreateMan() != null && !"".equals(poll.getCreateMan())) {
			hql += " and ( t1.createMan like '%"+poll.getCreateMan()+"%' or exists (select code from Tcommitteeman tt where tt.name like  '%"+poll.getCreateMan()+"%' and t1.createMan like '%'||tt.code||'%'))";
		}
		//gds add start
		if (poll.getFilingFlg() != null && !"".equals(poll.getFilingFlg())) {
			if(Constants.CODE_TYPE_YESNO_YES.equals(poll.getFilingFlg())) {
				hql += " and t1.filingFlg ='"+poll.getFilingFlg()+"'";
			} else {
				hql += " and t1.filingFlg is null ";
			}
		}
		//gds add end
		return hql;
	}
	/**
	 * 
		 * 主键数据查询
		 * 
		 * @param pollId
		 * 
		 * @throws Exception
		 * 
		 * @return 		Tpoll
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Tpoll findTpoll(String pollId) throws Exception {
		Tpoll t=pollDAO.get(Tpoll.class,pollId);
		t.setStatusName(this.findDicName("POLLSTATUS", t.getStatus()));
		t.setAdoptFlgName(this.findDicName("YESNO",t.getAdoptFlg()));
		t.setIssueFlgName(this.findDicName("YESNO",t.getIssueFlg()));
		t.setMergeFlgName(this.findDicName("YESNO",t.getMergeFlg()));
		t.setStressFlgName(this.findDicName("YESNO",t.getStressFlg()));
		t.setFilingFlgName(this.findDicName("YESNO",t.getFilingFlg()));
		t.setHandleTypeName(this.findDicName("BLLX", t.getHandleType()));
		String compNames="";
		if(t.getHandleComp()!=null&& !"".equals(t.getHandleComp())){
			String[] compids=t.getHandleComp().split(",");
			for(int j=0;j<compids.length;j++){
				Comp comp= compService.getCompBycode(compids[j]);;
				compNames+=comp.getShortName()+",";
			}
		}
		if(!"".equals(compNames)){
			compNames=compNames.substring(0,(compNames.length()-1));
		}
		t.setHandleCompName(compNames);
		t.setPollTypeName(this.findDicName("POLLTYPE", t.getPollType()));
		t.setEditorName(this.findNameByUserid(t.getEditor()));
		t.setCreateManName(this.findNameByCommCode(t.getCreateMan()));
		t.setIssuerName(this.findNameByUserid(t.getIssuer()));
		return t;
	}
	/**
	 * 
		 * 修改保存
		 * 
		 * @param poll
		 * 
		 * @throws Exception
		 * 
	 */
	public void update(Tpoll poll) throws Exception {
		Tpoll t=pollDAO.get(Tpoll.class,poll.getPollId());
		t.setTitle(poll.getTitle());
		t.setContent(poll.getContent());
		t.setEndContent(poll.getContent());
		pollDAO.saveOrUpdate(t);
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_EDIT);
		dolog.setKeyId(poll.getPollId());
		dolog.setInfo( "[社情民意]修改社情民意内容");
		this.saveLog(dolog);
	}
	/**
	 * 
		 * 修改提交
		 * 
		 * @param poll
		 * 
		 * @throws Exception
		 * 
	 */
	public void submitEdit(Tpoll poll) throws Exception {
		Tpoll t=pollDAO.get(Tpoll.class, poll.getPollId());
		t.setTitle(poll.getTitle());
		t.setContent(poll.getContent());
		t.setEndContent(poll.getContent());
		t.setStatus(Constants.CODE_TYPE_POLL_STATUS_WSC);
		t.setUpdateFlg(Constants.CODE_TYPE_UPDATE_YES);
		pollDAO.update(t);
		
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_EDIT);
		dolog.setKeyId(poll.getPollId());
		dolog.setInfo( "[社情民意]修改并提交社情民意");
		this.saveLog(dolog);
		
	}
	/**
	 * 
		 * 撤销提交
		 * 
		 * @param poll
		 * 
		 * @throws Exception
		 * 
	 */
	public void revoke(Tpoll poll) throws Exception {
		Tpoll t=pollDAO.get(Tpoll.class, poll.getPollId());
		t.setStatus(Constants.CODE_TYPE_POLL_STATUS_YBC);
		t.setUpdateFlg(Constants.CODE_TYPE_UPDATE_YES);
		pollDAO.update(t);		
		
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_BACK);
		dolog.setKeyId(poll.getPollId());
		dolog.setInfo( "[社情民意]撤销社情民意提交");
		this.saveLog(dolog);
	}
	/**
	 * 
	 * 信息员的社情民意列表数据
	 * 
	 * @param poll,secondaryCode
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(Tpoll poll,String secondaryCode)
			throws Exception {
		String hql=" from Tpoll t1 where t1.tsecondary.secondaryId='"+secondaryCode+"' and t1.discard!='"+Constants.CODE_TYPE_YESNO_YES+"' and t1.status !='"+Constants.CODE_TYPE_POLL_STATUS_YBC+"' ";
		DataGrid j = new DataGrid();
		j.setRows(getPollList(find(poll,hql)));
		j.setTotal(total(poll,hql));
		return j;
	}
	
	/**
	 * 
	 * 社情民意审批数据
	 * 
	 * @param poll,httpSession
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(Tpoll poll,HttpSession httpSession)throws Exception {
		String secondaryCode=((Seco)httpSession.getAttribute("sessionSeco")).getSecondaryId();
		SessionInfo sInfo=(SessionInfo)httpSession.getAttribute("sessionInfo");
		String hql=" from Tpoll t1 where t1.tsecondary.secondaryId ='"+secondaryCode+"' and t1.discard!='"+Constants.CODE_TYPE_YESNO_YES+"' and t1.filingFlg='"+Constants.CODE_TYPE_YESNO_YES+"'";
		if(Constants.POLLSP_ROLE_CID.equals(sInfo.getRoleIds())){
			hql+=" and t1.status in ('"+Constants.CODE_TYPE_POLL_STATUS_YCY+"','"+Constants.CODE_TYPE_POLL_STATUS_YSH+"')";
		}
		if(Constants.POLLSQ_ROLE_CID.equals(sInfo.getRoleIds())){
			hql+=" and t1.status in ('"+Constants.CODE_TYPE_POLL_STATUS_YSH+"','"+Constants.CODE_TYPE_POLL_STATUS_YSQ+"')";
		}
		DataGrid j = new DataGrid();
		
		j.setRows(getPollList(findCheck(poll,hql)));
		j.setTotal(total(poll,hql));
		return j;
	}
	// gds add start
	/**
	 * 
	 * 记分登记的社情民意列表数据(仅查询当前届次)
	 * 
	 * @param poll,secondaryCode
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid issueDatagrid(Tpoll poll,String secondaryCode) throws Exception {
		//String hql=" from Tpoll t1 where t1.discard!='"+Constants.CODE_TYPE_YESNO_YES+"' and t1.status !='"+Constants.CODE_TYPE_POLL_STATUS_YBC+"' ";
		String hql=" from Tpoll t1 where t1.issueFlg='"+Constants.CODE_TYPE_YESNO_YES+"' and t1.tsecondary.secondaryId='"+secondaryCode+"'";
		DataGrid j = new DataGrid();
		j.setRows(getPollList(find(poll,hql)));
		j.setTotal(total(poll,hql));
		return j;
	}
	
	/**
	 * 
	 * 已印发的社情民意列表数据(查询选择的届次)
	 * 
	 * @param poll,secondaryCode
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid issuedDatagrid(Tpoll poll) throws Exception {
		
		String ids="";
		if(poll.getIds()!=null&&!"".equals(poll.getIds())){
			String[] secondaryIds=poll.getIds().split(",");
			for(int i=0;i<secondaryIds.length;i++){
				ids+="'"+secondaryIds[i]+"',";
			}
		}
		
		String hql=" from Tpoll t1 where t1.issueFlg='"+Constants.CODE_TYPE_YESNO_YES+"'";
		
		if(!"".equals(ids)){
			ids=ids.substring(0,ids.length()-1);
			hql+=" and t1.tsecondary.secondaryId in ("+ids+")";
		}
		
		DataGrid j = new DataGrid();
		j.setRows(getPollList(find(poll,hql)));
		j.setTotal(total(poll,hql));
		return j;
	}
	/**
	 * 
	 * 社情民意新增（群众）
	 * 
	 * @param poll,httpSession
	 * 
	 * @throws 	Exception
	 * 
	 */
	public String publicAdd(Tpoll poll,HttpSession httpSession) throws Exception {
		//届次
		Seco seco= secoService.findCurrent();
		Tsecondary ts=new Tsecondary();
		BeanUtils.copyProperties(ts,seco);
		Tpoll tpoll=new Tpoll();
		BeanUtils.copyProperties(tpoll,poll);
		tpoll.setTsecondary(ts);
		
		//社情民意类型:社会征集
		tpoll.setPollType(Constants.DIC_TYPE_POLLTYPE_SHZJ);
		
		//社情民意内容(定稿)
		tpoll.setEndContent(poll.getContent());
		tpoll.setPollId(Generator.getInstance().getPollNO());
		// 社情民意状态:未审查
		tpoll.setStatus(Constants.CODE_TYPE_POLL_STATUS_WSC);
		//编编状态
		tpoll.setUpdateFlg(Constants.CODE_TYPE_UPDATE_YES);
		//创建日期
		tpoll.setCreateTime(new Date());
		
		//撰稿人和提交者是同一个人
		tpoll.setCreateMan(poll.getWriter());
		
		//未废弃
		tpoll.setDiscard(Constants.CODE_TYPE_YESNO_NO);
		
		pollDAO.save(tpoll);
		
		Tdolog dolog=new Tdolog();
		//日志类型:社情民意日志
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		//操作类型:新增
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_ADD);
		dolog.setKeyId(tpoll.getPollId());
		dolog.setInfo( "社情民意新增（群众）");
		
		dolog.setOperateTime(new Date());
		this.saveLog(dolog);
		return tpoll.getPollId();
	}
	/**
	 * 
		 * 数据查询
		 * 
		 * @param poll
		 * 
		 * 
		 * @return 		List<Tpoll> 
	 */
	
	private List<Tpoll> findCount(Tpoll poll,String hql) {
		hql = addWhereCount(poll, hql);
		if (poll.getSort() != null && poll.getOrder() != null) {
			hql += " order by " + poll.getSort() + " " + poll.getOrder();
		}else {
			hql+=" order by t1.pollId";
		}
		
		return pollDAO.find(hql, poll.getPage(), poll.getRows());
	}
	/**
	 * 
		 * 数据统计
		 * 
		 * @param poll
		 * 
		 * 
		 * @return 		Long
	 */
	
	private Long totalCount(Tpoll poll,String hql) {
		hql = addWhereCount(poll, hql);
		hql = "select count(*) "+hql;
		return pollDAO.count(hql);
	}
	
	/**
	 * 
		 * 条件设置
		 * 
		 * @param poll，hql
		 * 
		 * 
		 * @return 		String
	 */
	private String addWhereCount(Tpoll poll, String hql) {
		if (poll.getStatus() != null && !"".equals(poll.getStatus())) {
			hql += " and t1.status ='"+poll.getStatus()+"'";
		}
		if (poll.getFilingFlg() != null && !"".equals(poll.getFilingFlg())) {
			if(Constants.CODE_TYPE_YESNO_YES.equals(poll.getFilingFlg())) {
				hql += " and t1.filingFlg ='"+poll.getFilingFlg()+"'";
			} else {
				hql += " and t1.filingFlg is null ";
			}
		}
		return hql;
	}
	/**
	 * 
	 * 社情民意统计
	 * 
	 * @param httpSession
	 * 
	 * @throws 	Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Map<String,Integer> pollCount(HttpSession httpSession)throws Exception{
		Map<String,Integer> countMap=new HashMap<String, Integer>();
		SessionInfo sessionInfo=((SessionInfo)httpSession.getAttribute("sessionInfo"));
		String secondaryCode=((Seco)httpSession.getAttribute("sessionSeco")).getSecondaryId();
		
		
		String sql="";
		//委员
		if(Constants.DIC_TYPE_YHZB_WY.equals(sessionInfo.getUserGroup())){
			sql="select 'ybcPoll',count(*) from TPOLL t where t.SECONDARY_ID='"+secondaryCode+"'"
					+" and t.STATUS='"+Constants.CODE_TYPE_POLL_STATUS_YBC+"'" //已保存
					+" and t.DISCARD ='"+Constants.CODE_TYPE_YESNO_NO+"'"
					+" and t.CREATE_MAN like '%"+sessionInfo.getUserCode()+"%'"
			+" union "
			    +"select 'wscPoll',count(*) from TPOLL t where t.SECONDARY_ID='"+secondaryCode+"'"
					+" and t.STATUS='"+Constants.CODE_TYPE_POLL_STATUS_WSC+"'" //未审查
					+" and t.DISCARD ='"+Constants.CODE_TYPE_YESNO_NO+"'"
					+" and t.CREATE_MAN like '%"+sessionInfo.getUserCode()+"%'"
			+" union "
		    +"select 'wcyPoll',count(*) from TPOLL t where t.SECONDARY_ID='"+secondaryCode+"'"
				+" and t.STATUS='"+Constants.CODE_TYPE_POLL_STATUS_WCY+"'" //未采用
				+" and t.DISCARD ='"+Constants.CODE_TYPE_YESNO_NO+"'"
				+" and t.CREATE_MAN like '%"+sessionInfo.getUserCode()+"%'"
			+" union "
		    +"select 'ycyPoll',count(*) from TPOLL t where t.SECONDARY_ID='"+secondaryCode+"'"
				+" and t.STATUS='"+Constants.CODE_TYPE_POLL_STATUS_YCY+"'" //已编辑
				+" and t.FILING_FLG is null "  // 未提交审签
				+" and t.DISCARD ='"+Constants.CODE_TYPE_YESNO_NO+"'"
				+" and t.CREATE_MAN like '%"+sessionInfo.getUserCode()+"%'"
			+" union "
			+"select 'wshPoll',count(*) from TPOLL t where t.SECONDARY_ID='"+secondaryCode+"'"
				+" and t.STATUS='"+Constants.CODE_TYPE_POLL_STATUS_YCY+"'" //已编辑
				+" and t.FILING_FLG='"+Constants.CODE_TYPE_YESNO_YES+"'"  // 已提交审签
				+" and t.DISCARD ='"+Constants.CODE_TYPE_YESNO_NO+"'"
				+" and t.CREATE_MAN like '%"+sessionInfo.getUserCode()+"%'"
			+" union "
			+"select 'yshPoll',count(*) from TPOLL t where t.SECONDARY_ID='"+secondaryCode+"'"
			+" and t.STATUS='"+Constants.CODE_TYPE_POLL_STATUS_YSH+"'" //已审核
			+" and t.DISCARD ='"+Constants.CODE_TYPE_YESNO_NO+"'"
			+" and t.CREATE_MAN like '%"+sessionInfo.getUserCode()+"%'"
			+" union "
			+"select 'ysqPoll',count(*) from TPOLL t where t.SECONDARY_ID='"+secondaryCode+"'"
			+" and t.STATUS='"+Constants.CODE_TYPE_POLL_STATUS_YSQ+"'" //已签发
			+" and t.DISCARD ='"+Constants.CODE_TYPE_YESNO_NO+"'"
			+" and t.CREATE_MAN like '%"+sessionInfo.getUserCode()+"%'"
			+" union "
			+"select 'yyfPoll',count(*) from TPOLL t where t.SECONDARY_ID='"+secondaryCode+"'"
			+" and t.STATUS='"+Constants.CODE_TYPE_POLL_STATUS_YF+"'" //已印发
			+" and t.DISCARD ='"+Constants.CODE_TYPE_YESNO_NO+"'"
			+" and t.CREATE_MAN like '%"+sessionInfo.getUserCode()+"%'";
			System.out.println(sql);
			List<Object[]> list =(List<Object[]>) pollDAO.executeCountSql(sql);
				int ybcPollNum=0;
				int wscPollNum=0;
				int wcyPollNum=0;
				int ycyPollNum=0;
				int wshPollNum=0;
				int yshPollNum=0;
				int ysqPollNum=0;
				int yyfPollNum=0;
				for(int i=0;i<list.size();i++){
					Object[] objs=list.get(i);
					//已保存
					if("ybcPoll".equals(objs[0])){
						ybcPollNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
					//未审查
					if("wscPoll".equals(objs[0])){
						wscPollNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
					//未采用
					if("wcyPoll".equals(objs[0])){
						wcyPollNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
					//已编辑
					if("ycyPoll".equals(objs[0])){
						ycyPollNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
					//未审核
					if("wshPoll".equals(objs[0])){
						wshPollNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
					//已审核
					if("yshPoll".equals(objs[0])){
						yshPollNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
					//已签发
					if("ysqPoll".equals(objs[0])){
						ysqPollNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
					//已印发
					if("yyfPoll".equals(objs[0])){
						yyfPollNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
				}
				countMap.put("ybcPollNum", ybcPollNum);
				countMap.put("wscPollNum", wscPollNum);
				countMap.put("wcyPollNum", wcyPollNum);
				countMap.put("ycyPollNum", ycyPollNum);
				countMap.put("wshPollNum", wshPollNum);
				countMap.put("yshPollNum", yshPollNum);
				countMap.put("ysqPollNum", ysqPollNum);
				countMap.put("yyfPollNum", yyfPollNum);
		}
		else {
			sql="select 'ybcPoll',count(*) from TPOLL t where t.SECONDARY_ID='"+secondaryCode+"'"
				+" and t.STATUS='"+Constants.CODE_TYPE_POLL_STATUS_YBC+"'" //已保存
				+" and t.DISCARD ='"+Constants.CODE_TYPE_YESNO_NO+"'"
				//+" and t.CREATE_MAN like '%"+sessionInfo.getUserCode()+"%'"
				+" union "
				    +"select 'wscPoll',count(*) from TPOLL t where t.SECONDARY_ID='"+secondaryCode+"'"
					+" and t.STATUS='"+Constants.CODE_TYPE_POLL_STATUS_WSC+"'" //未审查
					+" and t.DISCARD ='"+Constants.CODE_TYPE_YESNO_NO+"'"
					//+" and t.CREATE_MAN like '%"+sessionInfo.getUserCode()+"%'"
				+" union "
			    +"select 'wcyPoll',count(*) from TPOLL t where t.SECONDARY_ID='"+secondaryCode+"'"
					+" and t.STATUS='"+Constants.CODE_TYPE_POLL_STATUS_WCY+"'" //未采用
					+" and t.DISCARD ='"+Constants.CODE_TYPE_YESNO_NO+"'"
					//+" and t.CREATE_MAN like '%"+sessionInfo.getUserCode()+"%'"
				+" union "
			    +"select 'ycyPoll',count(*) from TPOLL t where t.SECONDARY_ID='"+secondaryCode+"'"
					+" and t.STATUS='"+Constants.CODE_TYPE_POLL_STATUS_YCY+"'" //已编辑
					+" and t.FILING_FLG is null "  // 未提交审签
					+" and t.DISCARD ='"+Constants.CODE_TYPE_YESNO_NO+"'"
					//+" and t.CREATE_MAN like '%"+sessionInfo.getUserCode()+"%'"
				+" union "
				+"select 'wshPoll',count(*) from TPOLL t where t.SECONDARY_ID='"+secondaryCode+"'"
					+" and t.STATUS='"+Constants.CODE_TYPE_POLL_STATUS_YCY+"'" //已编辑
					+" and t.FILING_FLG='"+Constants.CODE_TYPE_YESNO_YES+"'"  // 已提交审签
					+" and t.DISCARD ='"+Constants.CODE_TYPE_YESNO_NO+"'"
					//+" and t.CREATE_MAN like '%"+sessionInfo.getUserCode()+"%'"
				+" union "
					+"select 'yshPoll',count(*) from TPOLL t where t.SECONDARY_ID='"+secondaryCode+"'"
					+" and t.STATUS='"+Constants.CODE_TYPE_POLL_STATUS_YSH+"'" //已审核
					+" and t.DISCARD ='"+Constants.CODE_TYPE_YESNO_NO+"'"
					//+" and t.CREATE_MAN like '%"+sessionInfo.getUserCode()+"%'"
				+" union "
					+"select 'ysqPoll',count(*) from TPOLL t where t.SECONDARY_ID='"+secondaryCode+"'"
					+" and t.STATUS='"+Constants.CODE_TYPE_POLL_STATUS_YSQ+"'" //已签发
					+" and t.DISCARD ='"+Constants.CODE_TYPE_YESNO_NO+"'"
					//+" and t.CREATE_MAN like '%"+sessionInfo.getUserCode()+"%'"
				+" union "
					+"select 'yyfPoll',count(*) from TPOLL t where t.SECONDARY_ID='"+secondaryCode+"'"
					+" and t.STATUS='"+Constants.CODE_TYPE_POLL_STATUS_YF+"'" //已印发
					+" and t.DISCARD ='"+Constants.CODE_TYPE_YESNO_NO+"'"
					//+" and t.CREATE_MAN like '%"+sessionInfo.getUserCode()+"%'"
				+" union "
					+"select 'zbzPoll',count(*) from TPOLL t where t.SECONDARY_ID='"+secondaryCode+"'"
					+" and t.STATUS='"+Constants.CODE_TYPE_POLL_STATUS_ZBZ+"'" //转办中
					+" and t.DISCARD ='"+Constants.CODE_TYPE_YESNO_NO+"'"
					//+" and t.CREATE_MAN like '%"+sessionInfo.getUserCode()+"%'"
				+" union "
					+"select 'wblPoll',count(*) from TPOLL t where t.SECONDARY_ID='"+secondaryCode+"'"
					+" and t.STATUS='"+Constants.CODE_TYPE_POLL_STATUS_WBL+"'" //未办理
					+" and t.DISCARD ='"+Constants.CODE_TYPE_YESNO_NO+"'"
					//+" and t.CREATE_MAN like '%"+sessionInfo.getUserCode()+"%'"
				+" union "
					+"select 'stzPoll',count(*) from TPOLL t where t.SECONDARY_ID='"+secondaryCode+"'"
					+" and t.STATUS='"+Constants.CODE_TYPE_POLL_STATUS_STZ+"'" //申退中
					+" and t.DISCARD ='"+Constants.CODE_TYPE_YESNO_NO+"'"
					//+" and t.CREATE_MAN like '%"+sessionInfo.getUserCode()+"%'"
				+" union "
					+"select 'yblPoll',count(*) from TPOLL t where t.SECONDARY_ID='"+secondaryCode+"'"
					+" and t.STATUS='"+Constants.CODE_TYPE_POLL_STATUS_YBL+"'" //已办理
					+" and t.DISCARD ='"+Constants.CODE_TYPE_YESNO_NO+"'"
					//+" and t.CREATE_MAN like '%"+sessionInfo.getUserCode()+"%'"
				;
			System.out.println(sql);
			List<Object[]> list =(List<Object[]>) pollDAO.executeCountSql(sql);
				int ybcPollNum=0;
				int wscPollNum=0;
				int wcyPollNum=0;
				int ycyPollNum=0;
				int wshPollNum=0;
				int yshPollNum=0;
				int ysqPollNum=0;
				int yyfPollNum=0;
				
				int zbzPollNum=0;
				int wblPollNum=0;
				int stzPollNum=0;
				int yblPollNum=0;
				
				for(int i=0;i<list.size();i++){
					Object[] objs=list.get(i);
					//已保存
					if("ybcPoll".equals(objs[0])){
						ybcPollNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
					//未审查
					if("wscPoll".equals(objs[0])){
						wscPollNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
					//未采用
					if("wcyPoll".equals(objs[0])){
						wcyPollNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
					//已编辑
					if("ycyPoll".equals(objs[0])){
						ycyPollNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
					//未审核
					if("wshPoll".equals(objs[0])){
						wshPollNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
					//已审核
					if("yshPoll".equals(objs[0])){
						yshPollNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
					//已签发
					if("ysqPoll".equals(objs[0])){
						ysqPollNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
					//已印发
					if("yyfPoll".equals(objs[0])){
						yyfPollNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
					//转办中
					if("zbzPoll".equals(objs[0])){
						zbzPollNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
					//未办理
					if("wblPoll".equals(objs[0])){
						wblPollNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
					//申退中
					if("stzPoll".equals(objs[0])){
						stzPollNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
					//已办理
					if("yblPoll".equals(objs[0])){
						yblPollNum=NumberFormatTools.getInstance().toInteger(objs[1]);
					}
				}
				countMap.put("ybcPollNum", ybcPollNum);
				countMap.put("wscPollNum", wscPollNum);
				countMap.put("wcyPollNum", wcyPollNum);
				countMap.put("ycyPollNum", ycyPollNum);
				countMap.put("wshPollNum", wshPollNum);
				countMap.put("yshPollNum", yshPollNum);
				countMap.put("ysqPollNum", ysqPollNum);
				countMap.put("yyfPollNum", yyfPollNum);
				countMap.put("zbzPollNum", zbzPollNum);
				countMap.put("wblPollNum", wblPollNum);
				countMap.put("stzPollNum", stzPollNum);
				countMap.put("yblPollNum", yblPollNum);
				
		}
		return countMap;
	}
	// gds add end
	
	/**
	 * 
	 * 印发社情民意数据获取
	 * 
	 * @param poll,secondaryCode
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Tpoll> findIssueData(Tpoll poll) throws Exception {
		String hql=" from Tpoll t1 where t1.issueFlg='"+Constants.CODE_TYPE_YESNO_YES+"'";
		hql=addWhere(poll, hql);
		hql+=" order by t1.pollCode,t1.pollId";
		return getPollList(pollDAO.find(hql));
	}
	/**
	 * 
	 * 社情民意提交人信息
	 * 
	 * @param poll
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid submitter(Tpoll poll)throws Exception {
		List<Comm> commList=new ArrayList<Comm>();
		if(!"".equals(poll.getCreateMan())&&poll.getCreateMan()!=null){
			System.out.println("createMan=="+poll.getCreateMan());
			for (String  id : poll.getCreateMan().split(",")) {
				if (!"".equals(id)&&id!=null) {
					System.out.println("id=="+id);
					Comm comm=commService.getCommBycode(id.trim());
					if(Util.getInstance().isEmpty(comm.getCode())){
						comm.setCode(id);
						comm.setName(id);
						comm.setCommitteeName("社会征集人");
						commList.add(comm);
					}else{
						commList.add(comm);
					}
				}
			}
		}
		DataGrid j = new DataGrid();
		j.setRows(commList);
		j.setTotal(Long.valueOf(commList.size()));
		return j;
	}
	/**
	 * 
		 * 审查提交
		 * 
		 * @param poll httpSession
		 * 
		 * @throws Exception
		 * 
	 */
	public void checkEdit(Tpoll poll,HttpSession httpSession) throws Exception {
		Tpoll t=pollDAO.get(Tpoll.class, poll.getPollId());
		t.setTitle(poll.getTitle());
		t.setEndContent(poll.getEndContent());
		t.setCreateMan(poll.getCreateMan());
		String createMan = poll.getCreateMan();
		Comm comm=null;
		if(createMan!=null && !"".equals(createMan)) {
			String[] array = createMan.split(",");
			comm=commService.getCommBycode(array[0]);
		}
		
		if(comm!=null){
			if(Constants.DIC_TYPE_GROUP_WY.equals(comm.getGroupCode())){
				poll.setPollType(Constants.DIC_TYPE_POLLTYPE_WY);
				poll.setWriter(comm.getName());
			}else if(Constants.DIC_TYPE_GROUP_ZWH.equals(comm.getGroupCode())){
				poll.setPollType(Constants.DIC_TYPE_POLLTYPE_ZWH);
			}else if(Constants.DIC_TYPE_GROUP_QXQ.equals(comm.getGroupCode())){
				poll.setPollType(Constants.DIC_TYPE_POLLTYPE_QXQ);
			}else{
				poll.setPollType(Constants.DIC_TYPE_POLLTYPE_QT);
			}
		}
		if(Constants.CODE_TYPE_YESNO_YES.equals(poll.getAdoptFlg())){
			t.setStatus(Constants.CODE_TYPE_POLL_STATUS_YCY);
		}else{
			t.setStatus(Constants.CODE_TYPE_POLL_STATUS_WCY);
		}
		t.setUpdateFlg(Constants.CODE_TYPE_UPDATE_YES);
		t.setEditor(((SessionInfo)httpSession.getAttribute(ResourceUtil.getSessionInfoName())).getUserId());
		t.setCheckTime(new Date());
		t.setMaster(poll.getMaster());
		t.setCopyMan(poll.getCopyMan());
		t.setAdoptFlg(poll.getAdoptFlg());
		pollDAO.update(t);
		
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_CHECK);
		dolog.setKeyId(poll.getPollId());
		dolog.setInfo( "[社情民意]筛选审查社情民意");
		this.saveLog(dolog);
		
		TpollCheck pc= new TpollCheck();
		pc.setPoll(poll);
		pc.setDiff(poll.getEndContent());
		pollCheckService.add(pc, httpSession);
	}
	/**
	 * 
		 * 合并提交
		 * 
		 * @param poll
		 * 
		 * @throws Exception
		 * 
	 */
	public void setMerge(Tpoll poll) throws Exception {
		String[] ids=poll.getIds().split(","); //要合并社情民意的ids
		//主社情民意
		Tpoll tpOld=pollDAO.get(Tpoll.class,ids[0]); //主社情民意
		
		//新构建一个社情民意（用主社情民意） 
		Tpoll tpNew= new Tpoll();
		org.springframework.beans.BeanUtils.copyProperties(tpOld, tpNew);

		//原来附社情民意ID
		String mergeids="";
		//循环选中的社情民意(包括主社情民意)
		for (int i = 0; i < ids.length; i++) {
			//参照社情民意
			Tpoll t=pollDAO.get(Tpoll.class,ids[i]);
			//编辑创建人（追加创建人到主社情民意）
			String[] str=t.getCreateMan().split(",");
			for (int j = 0; j < str.length; j++) {
				if(tpNew.getCreateMan().indexOf(str[j])==-1){
					tpNew.setCreateMan(tpNew.getCreateMan()+","+str[j]);
				}
			}
			//设置社情民意让其不显示
			t.setStatus(Constants.CODE_TYPE_POLL_STATUS_WSC);
			t.setDiscard(Constants.CODE_TYPE_YESNO_YES);
			t.setAdoptExplain(poll.getAdoptExplain());
			t.setMergeFlg(Constants.CODE_TYPE_YESNO_YES);
			pollDAO.update(t);
			this.saveLog(Constants.LOG_TYPE_CODE_POLL, Constants.LOG_TYPE_OPERTYPE_EDIT, t.getPollId(), "[社情民意]合并-设置社情民意让其不显示");
			// 追加 参照社情民意内容
			if(i!=0)
			tpNew.setContent(tpNew.getContent()+"<p><b><font color='#F00' size='2'>参考意见：</font></b></p>" + t.getContent());
			// 合并 副提案ID
			mergeids += ids[i] + ",";
		}
		//去逗号
		mergeids=mergeids.substring(0,(mergeids.length()-1));
		//编辑主社情民意
		tpNew.setAdoptExplain(poll.getAdoptExplain());
		tpNew.setMergeFlg(Constants.CODE_TYPE_YESNO_YES);
		tpNew.setMergeIds(mergeids);
		tpNew.setEndContent(tpNew.getContent());
		tpNew.setStatus(Constants.CODE_TYPE_POLL_STATUS_WSC);
		tpNew.setUpdateFlg(Constants.CODE_TYPE_UPDATE_YES);
		tpNew.setDiscard(Constants.CODE_TYPE_YESNO_NO);
		tpNew.setPollId(Generator.getInstance().getPollNO());
		pollDAO.save(tpNew);
		this.saveLog(Constants.LOG_TYPE_CODE_POLL, Constants.LOG_TYPE_OPERTYPE_ADD, tpNew.getPollId(), "[社情民意]合并-保存新社情民意并让其显示");
	}
	/**
	 * 取消合并社情民意
	 * @param prop
	 * @throws Exception
	 */
	public void cancelMerge(Tpoll poll) throws Exception {
		//主社情民意id
		String pollId=poll.getPollId();
		//主社情民意 获取
		Tpoll tp=pollDAO.get(Tpoll.class,pollId); //主社情民意
		String mergeIds = tp.getMergeIds();
		if(mergeIds!=null && !"".equals(mergeIds) && !"".equals(mergeIds.replace(",", ""))) {
			String ids[]=mergeIds.split(",");
			for (String id : ids) {
				//循环选中的附提案并让附提案都单独显示
				Tpoll p=pollDAO.get(Tpoll.class,id.trim());
				p.setAdoptExplain("");
				p.setMergeFlg(Constants.CODE_TYPE_YESNO_NO);
				p.setStatus(Constants.CODE_TYPE_POLL_STATUS_WSC);
				p.setUpdateFlg(Constants.CODE_TYPE_UPDATE_NO);
				p.setDiscard(Constants.CODE_TYPE_YESNO_NO);
				pollDAO.update(p);
				//生成日志
				this.saveLog(Constants.LOG_TYPE_CODE_POLL, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[社情民意]取消合并-让合并前各社情民意显示");
			}
		}
		pollDAO.delete(tp);
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_POLL, Constants.LOG_TYPE_OPERTYPE_DEL, pollId, "[社情民意]取消合并-删除合并后社情民意");
	}
	/**
	 * 
	 * 是否重点设置
	 * 
	 * @param prop,httpSession
	 * 
	 * @throws 	Exception
	 * 
	 */
	public void setStress(Tpoll poll) throws Exception {
		Tpoll t=pollDAO.get(Tpoll.class, poll.getPollId());
		t.setStressFlg(poll.getStressFlg());
		pollDAO.saveOrUpdate(t);
	}
	/**
	 * 
	 * 更新状态设置
	 * 
	 * @param poll
	 * 
	 * @throws 	Exception
	 * 
	 */
	public void setUpdateFlg(Tpoll poll) throws Exception {
		Tpoll tp=pollDAO.get(Tpoll.class,poll.getPollId());
		tp.setUpdateFlg(Constants.CODE_TYPE_UPDATE_NO);
		pollDAO.update(tp);
		
	}
	/**
	 * 
	 * 状态设置
	 * 
	 * @param poll
	 * 
	 * @throws 	Exception
	 * 
	 */
	public void setPollStatus(Tpoll poll) throws Exception {
		Tpoll tp=pollDAO.get(Tpoll.class,poll.getPollId());
		tp.setStatus(poll.getStatus());
		pollDAO.update(tp);
		
	}
	/**
	 * 
	 * 转办设置
	 * 
	 * @param poll
	 * 
	 * @throws 	Exception
	 * 
	 */
	public void setHandle(Tpoll poll) throws Exception {
		Tpoll tp=pollDAO.get(Tpoll.class,poll.getPollId());
		tp.setHandleType(poll.getHandleType());
		tp.setHandleComp(poll.getHandleComp());
		tp.setStatus(poll.getStatus());
		pollDAO.update(tp);
		
	}
	public void setFiling(Tpoll poll) throws Exception {
		if(poll.getIds()!=null && !"".equals(poll.getIds())){
			String[] ids=poll.getIds().split(",");
			for(int i=0;i<ids.length;i++){
				Tpoll tp=pollDAO.get(Tpoll.class,ids[i]);
				tp.setFilingFlg(Constants.CODE_TYPE_YESNO_YES);
				
				pollDAO.update(tp);
				
				Tdolog dolog=new Tdolog();
				dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
				dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_SUBMIT);
				dolog.setKeyId(tp.getPollId());
				dolog.setInfo( "[社情民意]社情民意提交审签");
				this.saveLog(dolog);
			}
			
		}
	}
	/**
	 * 获取最大编号
	 */
	private String findMaxCode(String secoId)throws Exception{
		String hql="select max(pollCode) from Tpoll t1 where t1.tsecondary.secondaryId='"+secoId+"' and t1.discard='"+Constants.CODE_TYPE_YESNO_NO+"'";
		String code=pollDAO.findHql(hql);
		System.out.println("maxcode=========="+code);
		if(code==null){
			return "";
		}else{
			return code;
		}
	}
	
	
	/**
	 * 
		 * 签发数据提交
		 * 
		 * @param poll httpSession
		 * 
		 * @throws Exception
		 * 
	 */
	public void pollMark(Tpoll poll, HttpSession httpSession) throws Exception {
		SessionInfo sInfo=(SessionInfo)httpSession.getAttribute("sessionInfo");
		Tpoll t=pollDAO.get(Tpoll.class, poll.getPollId());
		t.setTitle(poll.getTitle());
		t.setEndContent(poll.getEndContent());
		if(Constants.POLLSP_ROLE_CID.equals(sInfo.getRoleIds())){
			t.setStatus(Constants.CODE_TYPE_POLL_STATUS_YSH);
		}else if(Constants.POLLSQ_ROLE_CID.equals(sInfo.getRoleIds())){
			t.setStatus(Constants.CODE_TYPE_POLL_STATUS_YSQ);
			t.setIssuer(sInfo.getUserId());
		}else{
			if(Constants.CODE_TYPE_POLL_STATUS_YCY.equals(t.getStatus())){
				t.setStatus(Constants.CODE_TYPE_POLL_STATUS_YSH);
			}else{
				t.setStatus(Constants.CODE_TYPE_POLL_STATUS_YSQ);
				t.setIssuer(sInfo.getUserId());
			}
		}
		t.setUpdateFlg(Constants.CODE_TYPE_UPDATE_YES);
		t.setMaster(poll.getMaster());
		t.setCopyMan(poll.getCopyMan());
		
		pollDAO.update(t);
		
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_CHECK);
		dolog.setKeyId(poll.getPollId());
		dolog.setInfo( "[社情民意]审核签发社情民意");
		this.saveLog(dolog);
		
		TpollCheck pc= new TpollCheck();
		pc.setPoll(poll);
		pc.setDiff(poll.getEndContent());
		pollCheckService.add(pc, httpSession);
		
	}
	/**
	 * 
	 *报表生成
	 * 
	 * @param poll
	 * 
	 * @throws 	Exception
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public String report(Tpoll poll,HttpSession httpSession) throws Exception{
		String secondaryCode=((Seco)httpSession.getAttribute("sessionSeco")).getSecondaryId();
		String hql=" from Tpoll t1 where t1.tsecondary.secondaryId='"+secondaryCode+"' and t1.discard !='"+Constants.CODE_TYPE_YESNO_YES+"' and t1.status!='" + Constants.CODE_TYPE_TAZT_YBC + "'";
		hql=this.addWhere(poll, hql);
		hql+=" order by t1.tsecondary.secondaryId desc,t1.pollId";
		List<Tpoll> list=this.getPollList(pollDAO.find(hql));
		String fileName=this.writeExcel(list, httpSession,"/poll_report.xls");
		
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_REPORT);
		dolog.setInfo( "[社情民意]社情民意详细数据报表生成下载");
		this.saveLog(dolog);
		
		return fileName;
	}
	
	/**
	 * 
	 *EXCEL生成
	 * 
	 * @param list
	 * 
	 * @throws 	Exception
	 */
	
	private String writeExcel(List<Tpoll> list,HttpSession httpSession,String fileName)throws Exception{
		Seco seco=(Seco)httpSession.getAttribute("sessionSeco");
		Properties prop = new Properties();   
    	prop.load(this.getClass().getResourceAsStream("/config.properties")); //读取config.properties中的导出
    	File path=new File(Constants.ROOTPATH + prop.getProperty("exportPath"));
        if(!path.exists())path.mkdir();//如果路径不存在，则创建
        //新建文件
        String filepath=prop.getProperty("exportPath")+fileName;
        WritableWorkbook book= Workbook.createWorkbook(new File(path+fileName));
        System.out.println(filepath);
        //新建工作表
		WritableSheet sheet  =  book.createSheet( "Sheet1 " ,  0 );          
		SheetSettings ss = sheet.getSettings();            
		// ss.setHorizontalFreeze(2);  // 设置列冻结            
		ss.setVerticalFreeze(2);  // 设置行冻结前2行            
		WritableFont font1 =new WritableFont(WritableFont.createFont("微软雅黑"), 10 ,WritableFont.BOLD);            
		WritableFont font2 =new WritableFont(WritableFont.createFont("微软雅黑"), 9 ,WritableFont.NO_BOLD);            
		WritableCellFormat wcf = new WritableCellFormat(font1);           
		WritableCellFormat wcf2 = new WritableCellFormat(font2);            
		//WritableCellFormat wcf3 = new WritableCellFormat(font2);
		//设置样式，字体                         
		// wcf2.setBackground(Colour.LIGHT_ORANGE);            
		wcf.setAlignment(jxl.format.Alignment.CENTRE);  //平行居中            
		wcf.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中           
		//wcf3.setAlignment(jxl.format.Alignment.CENTRE);  //平行居中           
		//wcf3.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中            
		//wcf3.setBackground(Colour.LIGHT_ORANGE);            
		wcf2.setAlignment(jxl.format.Alignment.CENTRE);  //平行居中            
		wcf2.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中             
		sheet.mergeCells( 0 , 0 , 22 , 0 ); // 合并单元格  
		//          在Label对象的构造子中指名单元格位置是第一列第一行(0,0)
		//          以及单元格内容为test            
		jxl.write.Label titleLabel = new Label( 1 , 0 , "政协巴彦淖尔市"+seco.getYear()+"年度社情民意明细表",wcf);
		//          将定义好的单元格添加到工作表中            
		sheet.addCell(titleLabel);            
		sheet.setRowView(1, 480); // 设置第一行的高度  20121111            
		int[] headerArrHight = {10,15,60,10,15,20,10,10,10,15,15,50,10,40}; //列宽           
		String headerArr[] = {"年度","编号","标题","提交者","类型","提交日期","状态","是否重点","是否合并","编发人","主送","抄送","办理方式","办理单位"};            
		for (int i = 0; i < headerArr.length; i++) {                
			sheet.addCell(new Label( i , 1 , headerArr[i],wcf));                
			sheet.setColumnView(i, headerArrHight[i]);            
		} 
//		if(usergroup.equals(Constants.DIC_TYPE_YHZB_WY)){
//			sheet.addCell(new Label(headerArr.length,1,"是否撤案",wcf));
//		}
		int conut = 2;            
		for (int i = 0; i < list.size(); i++) {   
			//循环一个list里面的数据到excel中               
			Tpoll p=list.get(i);
			sheet.addCell(new Label( 0 , conut ,p.getTsecondary().getYear(),wcf2)); 
			sheet.addCell(new Label( 1 , conut ,p.getPollCode(),wcf2));
			sheet.addCell(new Label( 2 , conut ,p.getTitle()));                
			sheet.addCell(new Label( 3 , conut ,p.getCreateManName(),wcf2));                
			sheet.addCell(new Label( 4 , conut ,p.getPollTypeName() ,wcf2));                
			sheet.addCell(new Label( 5 , conut ,Util.getInstance().getFormatDate(p.getCreateTime(),"yyyy-MM-dd H:m:s"),wcf2));               
			sheet.addCell(new Label( 6 , conut ,p.getStatusName() ,wcf2));               
			sheet.addCell(new Label( 7 , conut ,p.getStressFlgName(),wcf2));              
			sheet.addCell(new Label( 8 , conut ,p.getMergeFlgName() ,wcf2));
			sheet.addCell(new Label( 9 , conut ,p.getEditorName(),wcf2));              
			sheet.addCell(new Label( 10 , conut ,p.getMaster() ,wcf2));
			sheet.addCell(new Label( 11 , conut ,p.getCopyMan() ,wcf2));
			sheet.addCell(new Label( 12 , conut ,p.getHandleTypeName() ,wcf2));
			sheet.addCell(new Label( 13 , conut ,p.getHandleCompName() ,wcf2));
			sheet.setRowView(conut,480); // 设置行的高度
			conut++;           
		}           
		//          写入数据并关闭文件           
		book.write();            
		book.close();           
		return filepath;       
	}
	/**
	 * 
	 * 转办的社情民意列表数据
	 * 
	 * @param poll
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagridAssign(Tpoll poll)
			throws Exception {
		String hql=" from Tpoll t1 where t1.tsecondary.secondaryId='"+poll.getTsecondary().getSecondaryId()+"' and t1.discard!='"+Constants.CODE_TYPE_YESNO_YES+"' and (t1.status ='"+Constants.CODE_TYPE_POLL_STATUS_YF+"' or t1.status='"+Constants.CODE_TYPE_POLL_STATUS_ZBZ+"')";
		DataGrid j = new DataGrid();
		j.setRows(getPollList(find(poll,hql)));
		j.setTotal(total(poll,hql));
		return j;
	}
	
	/**
	 * 
	 * 转办的社情民意列表数据
	 * 
	 * @param poll
	 * 
	 * @throws 	Exception
	 * 
	 */
	public void pollAssign(Tpoll poll) throws Exception {
		if(poll.getIds()!=null && !"".equals(poll.getIds())){
			String[] ids=poll.getIds().split(",");
			for(int i=0;i<ids.length;i++){
				Tpoll tp=pollDAO.get(Tpoll.class,ids[i]);
				tp.setStatus(Constants.CODE_TYPE_POLL_STATUS_WBL);
				pollDAO.update(tp);
				Tdolog dolog=new Tdolog();
				dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
				dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_SUBMIT);
				dolog.setKeyId(tp.getPollId());
				dolog.setInfo( "[社情民意]社情民意提交办理");
				this.saveLog(dolog);
			}
			
		}
		
	}
	/**
	 * 
	 * 转办的社情民意列表数据
	 * 
	 * @param poll
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagridReview(Tpoll poll)
			throws Exception {
		String hql=" from Tpoll t1 where t1.tsecondary.secondaryId='"+poll.getTsecondary().getSecondaryId()+"' and t1.discard!='"+Constants.CODE_TYPE_YESNO_YES+"' and (t1.status ='"+Constants.CODE_TYPE_POLL_STATUS_WBL+"' or t1.status='"+Constants.CODE_TYPE_POLL_STATUS_YBL+"' or t1.status='"+Constants.CODE_TYPE_POLL_STATUS_STZ+"')";
		DataGrid j = new DataGrid();
		j.setRows(getPollList(find(poll,hql)));
		j.setTotal(total(poll,hql));
		return j;
	}
	
	/**
	 * 
		 *  社情民意定稿整理数据保存
		 * 
		 * @param poll
		 * 
		 * @throws Exception
		 * 
	 */
	public void confirmSave(Tpoll poll,HttpSession httpSession) throws Exception {
		Tpoll t=pollDAO.get(Tpoll.class, poll.getPollId());
		t.setTitle(poll.getTitle());
		t.setEndContent(poll.getEndContent());
		t.setCreateMan(poll.getCreateMan());
		String createMan = poll.getCreateMan();
		Comm comm=null;
		if(createMan!=null && !"".equals(createMan)) {
			String[] array = createMan.split(",");
			comm=commService.getCommBycode(array[0]);
		}
		if(comm!=null){
			if(Constants.DIC_TYPE_GROUP_WY.equals(comm.getGroupCode())){
				poll.setPollType(Constants.DIC_TYPE_POLLTYPE_WY);
				poll.setWriter(comm.getName());
			}else if(Constants.DIC_TYPE_GROUP_ZWH.equals(comm.getGroupCode())){
				poll.setPollType(Constants.DIC_TYPE_POLLTYPE_ZWH);
			}else if(Constants.DIC_TYPE_GROUP_QXQ.equals(comm.getGroupCode())){
				poll.setPollType(Constants.DIC_TYPE_POLLTYPE_QXQ);
			}else{
				poll.setPollType(Constants.DIC_TYPE_POLLTYPE_QT);
			}
		}
		if(Constants.CODE_TYPE_YESNO_NO.equals(poll.getAdoptFlg())){
			t.setStatus(Constants.CODE_TYPE_POLL_STATUS_WCY);
		}else{
			if(Util.getInstance().isEmpty(t.getPollCode())){
				String pollcode=findMaxCode(t.getTsecondary().getSecondaryId());
				t.setPollCode(Generator.getInstance().getPollCode(pollcode));
			}
			t.setIssueFlg(Constants.CODE_TYPE_YESNO_YES);
			t.setStatus(Constants.CODE_TYPE_POLL_STATUS_YF);
		}
		t.setMaster(poll.getMaster());
		t.setCopyMan(poll.getCopyMan());
		t.setUpdateFlg(Constants.CODE_TYPE_UPDATE_YES);
		pollDAO.update(t);
		
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_POLL);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_EDIT);
		dolog.setKeyId(poll.getPollId());
		dolog.setInfo( "[社情民意]社情民意定稿整理");
		this.saveLog(dolog);
		
		TpollCheck pc= new TpollCheck();
		pc.setPoll(poll);
		pc.setDiff(poll.getEndContent());
		pollCheckService.add(pc, httpSession);
	}
}
