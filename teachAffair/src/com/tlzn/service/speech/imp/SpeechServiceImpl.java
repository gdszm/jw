package com.tlzn.service.speech.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;
import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.Tcommitteeman;
import com.tlzn.model.dailywork.Tnotice;
import com.tlzn.model.meeting.Tmeeting;
import com.tlzn.model.poll.Tpoll;
import com.tlzn.model.speech.Tspeech;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.SessionInfo;
import com.tlzn.pageModel.speech.Speech;
import com.tlzn.pageModel.sys.Comm;
import com.tlzn.pageModel.sys.Seco;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.meeting.MeetingServiceI;
import com.tlzn.service.poll.PollServiceI;
import com.tlzn.service.speech.SpeechServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Generator;
import com.tlzn.util.base.NumberFormatTools;
import com.tlzn.util.base.ResourceUtil;
import com.tlzn.util.base.Util;

@Service("speechService")
public class SpeechServiceImpl extends BaseServiceImpl implements SpeechServiceI{

	private BaseDaoI<Tspeech> speechDAO;
	
	private MeetingServiceI meetingService;
	
	private PollServiceI pollService;
	/**
	 * 
	 * 查询单条通知
	 * 
	 * @param speech
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Speech get(String id) throws Exception {
		Tspeech tspeech= speechDAO.get(Tspeech.class, id);
		Speech speech = new Speech();
		BeanUtils.copyProperties(tspeech, speech);
		//采用情况
		speech.setUseSituationName(this.findDicName("USESITUATION", speech.getUseSituation()));
		//转弃方向
		speech.setDiscardTypeName(this.findDicName("DISCARDTYPE", speech.getDiscardType()));
		//发言状态
		speech.setStatusName(this.findDicName("SPEECHSTATUS", speech.getStatus()));
		
		
		speech.setMeetId((tspeech.getTmeeting().getMeetId()));
		speech.setMeetName(tspeech.getTmeeting().getMeetName());
		speech.setMeetType((tspeech.getTmeeting().getMeetType()));
		speech.setMeetTypeName(this.findDicName("MEETINGTYPE", speech.getMeetType()));
		speech.setSecondaryId(tspeech.getTmeeting().getTsecondary().getSecondaryId());
		speech.setSecondayName(tspeech.getTmeeting().getTsecondary().getSecondayName());
		speech.setYear(tspeech.getTmeeting().getTsecondary().getYear());
		
		speech.setCode(tspeech.getTcommitteeman().getCode());
		speech.setName(tspeech.getTcommitteeman().getName());
		speech.setTelephone(tspeech.getTcommitteeman().getTelephone());
		speech.setComparyAddress(tspeech.getTcommitteeman().getComparyAddress());
		speech.setEmail(tspeech.getTcommitteeman().getEmail());
		
		
		return speech;
	}

	/**
	 * 
	 * 查询
	 * 
	 * @param speech
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(Speech speech) throws Exception {
		String hql=" from Tspeech t where 1=1 and t.status!="+"'"+Constants.CODE_TYPE_SPEECH_STATUS_YBC+"'";
		DataGrid j = new DataGrid();
		j.setRows(editSpeechs(find(speech,hql)));
		j.setTotal(total(speech,hql));
		return j;
	}
	/**
	 * 
	 * 我的发言查询
	 * 
	 * @param speech
	 * 
	 * @throws 	Exception
	 * 
	 */
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagridOwn(Speech speech) throws Exception {
		Util util=new Util();
		String code = util.isEmpty(speech.getCode())?"":speech.getCode().trim();
		String hql=" from Tspeech t where 1=1 and t.tcommitteeman.code='"+ code + "' ";
		DataGrid j = new DataGrid();
		j.setRows(editSpeechs(find(speech,hql)));
		j.setTotal(total(speech,hql));
		return j;
	}
	private List<Speech> editSpeechs(List<Tspeech> speechList) throws Exception {
		List<Speech> speechs = new ArrayList<Speech>();
		if (speechList != null && speechList.size() > 0) {
			for (Tspeech tspeech: speechList) {
				Speech speech = new Speech();
				BeanUtils.copyProperties(tspeech, speech);
				//采用情况
				speech.setUseSituationName(this.findDicName("USESITUATION", speech.getUseSituation()));
				//发言状态
				speech.setStatusName(this.findDicName("SPEECHSTATUS", speech.getStatus()));
				//转弃方向
				speech.setDiscardTypeName(this.findDicName("DISCARDTYPE", speech.getDiscardType()));
				
				speech.setContent("");
				speech.setMemo("");
				
				speech.setMeetId((tspeech.getTmeeting().getMeetId()));
				speech.setMeetName(tspeech.getTmeeting().getMeetName());
				speech.setMeetTypeName(this.findDicName(Constants.MEETING_TYPE, tspeech.getTmeeting().getMeetType()));
				
				speech.setCode(tspeech.getTcommitteeman().getCode());
				speech.setName(tspeech.getTcommitteeman().getName());
				speechs.add(speech);
			}
		}
		return speechs;
	}
	private List<Tspeech> find(Speech speech,String hql) throws Exception {
		hql = addWhere(speech, hql);
		if (speech.getSort() != null && speech.getOrder() != null) {
			hql += " order by " + speech.getSort() + " " + speech.getOrder();
			}else {
				hql+=" order by t.createTime desc";
			}
		List<Tspeech> speechList = speechDAO.find(hql, speech.getPage(), speech.getRows());
		return speechList;
	}
	private Long total(Speech speech,String hql) throws Exception {
		hql = addWhere(speech, hql);
		hql = "select count(*) "+hql;
		return speechDAO.count(hql);
	}
	private String addWhere(Speech speech, String hql) throws Exception {
		Util util=new Util();
		if(!util.isEmpty(speech.getSpeakId())){
			 hql += " and speakId.code='" + speech.getSpeakId().trim() + "' ";
		}

		 if (!util.isEmpty(speech.getSecondaryId())) {
				String s="";
				for(String t : speech.getSecondaryId().split(",")){
					s += " tmeeting.tsecondary.secondaryId='"+t+"' or ";
				}
				s="(" + s.substring(0,s.length()-3) + ")";
				hql += " and " + s;
			}else{
				String secondaryId=((Seco) ActionContext.getContext().getSession().get(ResourceUtil.getSessionSeco())).getSecondaryId();
				hql += " and tmeeting.tsecondary.secondaryId='" + secondaryId + "' ";
		}
		 
		//状态
		if(!util.isEmpty(speech.getStatus())){
			 hql += " and status='" + speech.getStatus().trim() + "' ";
		}
		//采用情况
		if(!util.isEmpty(speech.getUseSituation())){
			 hql += " and useSituation='" + speech.getUseSituation().trim() + "' ";
		}
		//所属会议
		String meetIds = speech.getMeetId();
		if(!util.isEmpty(meetIds)){
			 hql += " and t.tmeeting.meetId in("+meetIds+")";
		}
		//所属会议
		if(!util.isEmpty(speech.getMeetName())){
			 hql += " and t.tmeeting.meetName like '%"+speech.getMeetName().trim()+"%'";
		}
		//发言人
		if(!util.isEmpty(speech.getName())){
			 hql += " and t.tcommitteeman.name like '%"+speech.getName().trim()+"%'";
		}
		//发言主题
		if(!util.isEmpty(speech.getTitle())){
			 hql += " and t.title like '%"+speech.getTitle().trim()+"%'";
		}

		if(speech.getCreateTimeStart()!=null){
			 hql+=" and to_char(t.createTime,'yyyy-MM-dd') >= '"+util.getFormatDate(speech.getCreateTimeStart(), "yyyy-MM-dd")+"'";
		}
		 if(speech.getCreateTimeEnd()!=null){
		 hql+=" and to_char(t.createTime,'yyyy-MM-dd') <= '"+util.getFormatDate(speech.getCreateTimeEnd(),"yyyy-MM-dd")+"'";
		 }
		 
		return hql;
	}
	/**
	 * 
	 * 已定稿发言查询
	 * 
	 * @param speech
	 * 
	 * @throws 	Exception
	 * 
	 */
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagridDg(Speech speech) throws Exception {
		String hql=" from Tspeech t where 1=1 and t.useSituation in('"
		//书面发言
		+Constants.CODE_TYPE_SPEECHADOPTED_STATUS_SMFY+"','"
		//口头发言
		+Constants.CODE_TYPE_SPEECHADOPTED_STATUS_KTFY+"') and t.status in('"
		//已审查
		+Constants.CODE_TYPE_SPEECH_STATUS_YSC+"','"
		//定稿
		+Constants.CODE_TYPE_SPEECH_STATUS_DG+"')";
		DataGrid j = new DataGrid();
		j.setRows(editSpeechs(find(speech,hql)));
		j.setTotal(total(speech,hql));
		return j;
	}
	/**
	 * 
	 * 已转弃发言查询
	 * 
	 * @param speech
	 * 
	 * @throws 	Exception
	 * 
	 */
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagridZq(Speech speech) throws Exception {
		String hql=" from Tspeech t where 1=1 and t.useSituation='"
		//转弃
		+Constants.CODE_TYPE_SPEECHADOPTED_STATUS_ZQ+"'";
		DataGrid j = new DataGrid();
		j.setRows(editSpeechs(find(speech,hql)));
		j.setTotal(total(speech,hql));
		return j;
	}
	/**
	 * 
	 * 已采用发言查询
	 * 
	 * @param speech
	 * 
	 * @throws 	Exception
	 * 
	 */
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagridAdopted(Speech speech) throws Exception {
		String hql=" from Tspeech t where 1=1 and t.useSituation in('"
		+Constants.CODE_TYPE_SPEECHADOPTED_STATUS_SMFY+"','"
		+Constants.CODE_TYPE_SPEECHADOPTED_STATUS_KTFY+"')";
		DataGrid j = new DataGrid();
		j.setRows(editSpeechs(find(speech,hql)));
		j.setTotal(total(speech,hql));
		return j;
	}
	
	/**
	 * 
	 * 委员履职详情，会议发言语列表查询
	 * 
	 * @param poll,userCode,secondaryCode
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagridFulfil(Speech speech,String userCode, String secondaryCode) 
			throws Exception {
		String hql=" from Tspeech t1 where t1.tmeeting.tsecondary.secondaryId='"+secondaryCode
			+"' and t1.tcommitteeman.code='"+userCode
			+"' and t1.status!='"+Constants.CODE_TYPE_SPEECH_STATUS_YBC
			+"' and t1.useSituation!='"+Constants.CODE_TYPE_SPEECHADOPTED_STATUS_ZQ+"'";
		hql+=" order by t1.createTime desc";
		List<Tspeech> speechList = speechDAO.find(hql, speech.getPage(), speech.getRows());
		DataGrid j = new DataGrid();
		j.setRows(editSpeechs(speechList));
		
		hql = "select count(*) "+hql;
		j.setTotal(speechDAO.count(hql));
		return j;
	}
	
	//保存会议发言
	public String save(Speech speech,HttpSession httpSession) throws Exception {
		
		Tspeech tspeech = new Tspeech();
		BeanUtils.copyProperties(speech, tspeech);
		tspeech.setSpeakId(Generator.getInstance().getSpeechNO());
		tspeech.setCreateTime(new Date());
		tspeech.setStatus(Constants.CODE_TYPE_SPEECH_STATUS_YBC);
		
		Tmeeting tmeeting = new Tmeeting();
		tmeeting.setMeetId(speech.getMeetId());
		
		tspeech.setTmeeting(tmeeting);
		
		Tcommitteeman committeeman = new Tcommitteeman();
		committeeman.setCode(speech.getCode());
		tspeech.setTcommitteeman(committeeman);
		
		speechDAO.save(tspeech);
		
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_SPEECH, Constants.LOG_TYPE_OPERTYPE_ADD, tspeech.getSpeakId(), "[会议发言]保存发言");
		
		return tspeech.getSpeakId();
	}
	//多次点保存时，更新记录
	public String saveForUpate(Speech speech,HttpSession httpSession) throws Exception {
		
		Tspeech tspeech = null;
		//更新
		tspeech=speechDAO.get(Tspeech.class, speech.getSpeakId().trim());
		BeanUtils.copyProperties(speech, tspeech);
		tspeech.setCreateTime(new Date());
		tspeech.setStatus(Constants.CODE_TYPE_SPEECH_STATUS_YBC);
		
		Tmeeting tmeeting = new Tmeeting();
		tmeeting.setMeetId(speech.getMeetId());
		tspeech.setTmeeting(tmeeting);
		
		Tcommitteeman committeeman = new Tcommitteeman();
		committeeman.setCode(speech.getCode());
		tspeech.setTcommitteeman(committeeman);
		
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_SPEECH, Constants.LOG_TYPE_OPERTYPE_EDIT, tspeech.getSpeakId(), "[会议发言]保存更新发言");
		
		speechDAO.update(tspeech);
		
		return tspeech.getSpeakId();
	}
	// 新发布时点击提交
	public String upDateOrAdd(Speech speech,HttpSession httpSession) throws Exception {
		
		Util util=new Util();
		Tspeech tspeech = null;
		if(util.isEmpty(speech.getSpeakId())) {
		//新增
			tspeech = new Tspeech();
			BeanUtils.copyProperties(speech, tspeech);
			tspeech.setSpeakId(Generator.getInstance().getSpeechNO());
			tspeech.setCreateTime(new Date());
			
			Tmeeting tmeeting = new Tmeeting();
			tmeeting.setMeetId(speech.getMeetId());
			tspeech.setTmeeting(tmeeting);
			
			Tcommitteeman committeeman = new Tcommitteeman();
			committeeman.setCode(speech.getCode());
			tspeech.setTcommitteeman(committeeman);
			
		} else {
		//更新
			tspeech=speechDAO.get(Tspeech.class, speech.getSpeakId().trim());
			Date createTime = tspeech.getCreateTime();
			BeanUtils.copyProperties(speech, tspeech);
			tspeech.setCreateTime(createTime);
			
			Tmeeting tmeeting = new Tmeeting();
			tmeeting.setMeetId(speech.getMeetId());
			tspeech.setTmeeting(tmeeting);
			
			Tcommitteeman committeeman = new Tcommitteeman();
			committeeman.setCode(speech.getCode());
			tspeech.setTcommitteeman(committeeman);
			
		}
		
		tspeech.setStatus(Constants.CODE_TYPE_SPEECH_STATUS_WSC);
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_SPEECH, Constants.LOG_TYPE_OPERTYPE_EDIT, tspeech.getSpeakId(), "[会议发言]提交或更新发言");
		
		speechDAO.saveOrUpdate(tspeech);
		
		return tspeech.getSpeakId();
	}
	// 修改发言
	public String edit(Speech speech,HttpSession httpSession) throws Exception {
			
			Util util=new Util();
			Tspeech tspeech = null;
			if(!util.isEmpty(speech.getSpeakId())){
			//更新
				tspeech=speechDAO.get(Tspeech.class, speech.getSpeakId().trim());
				tspeech.setTitle(speech.getTitle());
				tspeech.setContent(speech.getContent());
				tspeech.setAttsDepict(speech.getAttsDepict());
				tspeech.setAtts(speech.getAtts());
				
				Tmeeting tmeeting = new Tmeeting();
				tmeeting.setMeetId(speech.getMeetId());
				tspeech.setTmeeting(tmeeting);
				
				Tcommitteeman committeeman = new Tcommitteeman();
				committeeman.setCode(speech.getCode());
				tspeech.setTcommitteeman(committeeman);
				
			}
			//生成日志
			this.saveLog(Constants.LOG_TYPE_CODE_SPEECH, Constants.LOG_TYPE_OPERTYPE_EDIT, tspeech.getSpeakId(), "[会议发言]修改发言");
			
			speechDAO.update(tspeech);
			
			return tspeech.getSpeakId();
	}
	// 审查发言
	public String audit(Speech speech,HttpSession httpSession) throws Exception {
				
				Util util=new Util();
				Tspeech tspeech = speechDAO.get(Tspeech.class, speech.getSpeakId().trim());
				if(!util.isEmpty(tspeech)){
					//数据库中转弃为空 页面不为空时
					if(util.isEmpty(tspeech.getDiscardType()) && Constants.CODE_TYPE_SPEECH_DISCARDTYPE_POLL.equals(speech.getDiscardType())) {
						this.discard(speech, httpSession);
						//转弃
						tspeech.setDiscardType(Constants.CODE_TYPE_SPEECH_DISCARDTYPE_POLL);
					}
					//更新
					tspeech.setTitle(speech.getTitle());
					tspeech.setContent(speech.getContent());
					tspeech.setAttsDepict(speech.getAttsDepict());
					tspeech.setAtts(speech.getAtts());
					//采用情况
					tspeech.setUseSituation(speech.getUseSituation());
					//转弃方向
					tspeech.setDiscardType(speech.getDiscardType());
					//状态 (已审查)
					tspeech.setStatus(Constants.CODE_TYPE_SPEECH_STATUS_YSC);
					//审查日期
					tspeech.setAuditTime(new Date());
					
					Tmeeting tmeeting = new Tmeeting();
					tmeeting.setMeetId(speech.getMeetId());
					tspeech.setTmeeting(tmeeting);
					
					Tcommitteeman committeeman = new Tcommitteeman();
					committeeman.setCode(speech.getCode());
					tspeech.setTcommitteeman(committeeman);
					
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_SPEECH, Constants.LOG_TYPE_OPERTYPE_EDIT, tspeech.getSpeakId(), "[会议发言]审查发言");
					
					speechDAO.update(tspeech);
					
					return tspeech.getSpeakId();
				} else {
					return "";
				}
		
	}
	//发言定稿
	public String confirm(Speech speech,HttpSession httpSession) throws Exception {
			
			Util util=new Util();
			Tspeech tspeech = speechDAO.get(Tspeech.class, speech.getSpeakId().trim());
			if(!util.isEmpty(tspeech)){
				//数据库中转弃为空 页面不为空时
				if(util.isEmpty(tspeech.getDiscardType()) && Constants.CODE_TYPE_SPEECH_DISCARDTYPE_POLL.equals(speech.getDiscardType())) {
					this.discard(speech, httpSession);
					//转弃
					tspeech.setDiscardType(Constants.CODE_TYPE_SPEECH_DISCARDTYPE_POLL);
				}
				//更新
				tspeech.setTitle(speech.getTitle());
				tspeech.setContent(speech.getContent());
				tspeech.setAttsDepict(speech.getAttsDepict());
				tspeech.setAtts(speech.getAtts());
				//采用情况
				tspeech.setUseSituation(speech.getUseSituation());
				//转弃方向
				tspeech.setDiscardType(speech.getDiscardType());
				//状态 (定稿)
				tspeech.setStatus(Constants.CODE_TYPE_SPEECH_STATUS_DG);
				
				Tmeeting tmeeting = new Tmeeting();
				tmeeting.setMeetId(speech.getMeetId());
				tspeech.setTmeeting(tmeeting);
				
				Tcommitteeman committeeman = new Tcommitteeman();
				committeeman.setCode(speech.getCode());
				tspeech.setTcommitteeman(committeeman);
				
				//生成日志
				this.saveLog(Constants.LOG_TYPE_CODE_SPEECH, Constants.LOG_TYPE_OPERTYPE_EDIT, tspeech.getSpeakId(), "[会议发言]发言审查");
				speechDAO.update(tspeech);
				
				return tspeech.getSpeakId();
			} else {
				return "";
			}
			
	}
	//发言转弃
	public String discard(Speech speech,HttpSession httpSession) throws Exception {
				Util util=new Util();
				Tspeech tspeech = null;
				if(!util.isEmpty(speech.getSpeakId())){
					tspeech=speechDAO.get(Tspeech.class, speech.getSpeakId().trim());
					
					Tpoll poll= new Tpoll();
					
					poll.setTsecondary(tspeech.getTmeeting().getTsecondary());
					
					Tcommitteeman comm = tspeech.getTcommitteeman();
					if(comm!=null){
						if(Constants.DIC_TYPE_GROUP_WY.equals(comm.getGroupCode())){
							poll.setPollType(Constants.DIC_TYPE_POLLTYPE_WY);
							poll.setWriter(comm.getName());
							poll.setCreateMan(comm.getCode());
						}else if(Constants.DIC_TYPE_GROUP_ZWH.equals(comm.getGroupCode())){
							poll.setPollType(Constants.DIC_TYPE_POLLTYPE_ZWH);
						}else if(Constants.DIC_TYPE_GROUP_QXQ.equals(comm.getGroupCode())){
							poll.setPollType(Constants.DIC_TYPE_POLLTYPE_QXQ);
						}else{
							poll.setPollType(Constants.DIC_TYPE_POLLTYPE_QT);
						}
					}
					poll.setTitle(tspeech.getTitle());
					poll.setContent(tspeech.getContent());
					poll.setEndContent(tspeech.getContent());
					poll.setPollId(Generator.getInstance().getPollNO());
					//未审查
					poll.setStatus(Constants.CODE_TYPE_POLL_STATUS_WSC);
					poll.setUpdateFlg(Constants.CODE_TYPE_UPDATE_YES);
					poll.setDiscard(Constants.CODE_TYPE_YESNO_NO);
					poll.setCreateTime(new Date());
					pollService.discardAdd(poll, httpSession);
					tspeech.setDiscardType(Constants.CODE_TYPE_SPEECH_DISCARDTYPE_POLL);
					speechDAO.update(tspeech);
				}
				return tspeech.getSpeakId();
		}
	//发言管理“提交发言”处理
	public void submit(Speech speech) throws Exception {
		Util util=new Util();
		String ids = speech.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					Tspeech t = speechDAO.get(Tspeech.class,id);
					t.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES); //已发布状态
					speechDAO.update(t);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_SPEECH, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[会议发言]提交发言(管理)（发言ID："+id+"）");
				}
			}
		}
	}
	//发言管理“撤销提交”处理
	public void cancelSubmit(Speech speech) throws Exception {
		Util util=new Util();
		String ids = speech.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					Tspeech t = speechDAO.get(Tspeech.class,id);
					t.setStatus(Constants.CODE_TYPE_SPEECH_STATUS_YBC); //已保存状态
					speechDAO.update(t);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_SPEECH, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[会议发言]取消提交发言(管理)（会议ID："+id+"）");
				}
			}
		}
	}
	public void del(Speech speech) throws Exception {
		Util util=new Util();
		String ids = speech.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					Tspeech tspeech = speechDAO.get(Tspeech.class,id);
					speechDAO.delete(tspeech);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_SPEECH, Constants.LOG_TYPE_OPERTYPE_DEL, id, "[会议发言]发言删除（发言ID："+id+"）");
				}
			}
		}
	}
	
	//会议发言首页统计
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Map<String, Integer> speechCount(HttpSession httpSession) {
		Map<String,Integer> countMap=new HashMap<String, Integer>();
		SessionInfo sessionInfo=((SessionInfo)httpSession.getAttribute("sessionInfo"));
		String secondaryCode=((Seco)httpSession.getAttribute("sessionSeco")).getSecondaryId();
		
		String ybcHql="";String wscHql="";String ysc_smfyHql="";String ysc_ktfyHql="";
		String ysc_zqHql="";String ysc_kongHql="";String dg_smfyHql="";String dg_ktfyHql="";
		if(Constants.DIC_TYPE_YHZB_WY.equals(sessionInfo.getUserGroup())){
			ybcHql="select count(*) from Tspeech t where t.tmeeting.tsecondary.secondaryId='"+secondaryCode+"'"
					+" and t.status='"+Constants.CODE_TYPE_SPEECH_STATUS_YBC+"'" //已保存
					+" and t.tcommitteeman.code='"+sessionInfo.getUserCode()+"'";
			wscHql="select count(*) from Tspeech t where t.tmeeting.tsecondary.secondaryId='"+secondaryCode+"'"
					+" and t.status='"+Constants.CODE_TYPE_SPEECH_STATUS_WSC+"'" //未审查
					+" and t.tcommitteeman.code='"+sessionInfo.getUserCode()+"'";
			ysc_smfyHql="select count(*) from Tspeech t where t.tmeeting.tsecondary.secondaryId='"+secondaryCode+"'"
					+" and t.status='"+Constants.CODE_TYPE_SPEECH_STATUS_YSC+"'" //已审查-书面发言
					+" and t.useSituation='"+Constants.CODE_TYPE_SPEECHADOPTED_STATUS_SMFY+"'"
					+" and t.tcommitteeman.code='"+sessionInfo.getUserCode()+"'";
			ysc_ktfyHql="select count(*) from Tspeech t where t.tmeeting.tsecondary.secondaryId='"+secondaryCode+"'"
					+" and t.status='"+Constants.CODE_TYPE_SPEECH_STATUS_YSC+"'" //已审查-口头发言
					+" and t.useSituation='"+Constants.CODE_TYPE_SPEECHADOPTED_STATUS_KTFY+"'" 
					+" and t.tcommitteeman.code='"+sessionInfo.getUserCode()+"'";
			ysc_zqHql="select count(*) from Tspeech t where t.tmeeting.tsecondary.secondaryId='"+secondaryCode+"'"
					+" and t.status='"+Constants.CODE_TYPE_SPEECH_STATUS_YSC+"'" //已审查-转弃
					+" and t.useSituation='"+Constants.CODE_TYPE_SPEECHADOPTED_STATUS_ZQ+"'"
					+" and t.tcommitteeman.code='"+sessionInfo.getUserCode()+"'";
			dg_smfyHql="select count(*) from Tspeech t where t.tmeeting.tsecondary.secondaryId='"+secondaryCode+"'"
					+" and t.status='"+Constants.CODE_TYPE_SPEECH_STATUS_DG+"'" //定稿-书面发言
					+" and t.useSituation='"+Constants.CODE_TYPE_SPEECHADOPTED_STATUS_SMFY+"'"
					+" and t.tcommitteeman.code='"+sessionInfo.getUserCode()+"'";
			dg_ktfyHql="select count(*) from Tspeech t where t.tmeeting.tsecondary.secondaryId='"+secondaryCode+"'"
					+" and t.status='"+Constants.CODE_TYPE_SPEECH_STATUS_DG+"'" //定稿-口头发言
					+" and t.useSituation='"+Constants.CODE_TYPE_SPEECHADOPTED_STATUS_KTFY+"'" 
					+" and t.tcommitteeman.code='"+sessionInfo.getUserCode()+"'";
				Long ybcLong=speechDAO.count(ybcHql);
				Long wscLong=speechDAO.count(wscHql);
				Long ysc_smfyLong=speechDAO.count(ysc_smfyHql);
				Long ysc_ktfyLong=speechDAO.count(ysc_ktfyHql);
				Long ysc_zqLong=speechDAO.count(ysc_zqHql);
				Long dg_smfyLong=speechDAO.count(dg_smfyHql);
				Long dg_ktfyLong=speechDAO.count(dg_ktfyHql);
				
				int ybcSpeechNum=ybcLong==null?0:ybcLong.intValue();
				int wscSpeechNum=wscLong==null?0:wscLong.intValue();
				int ysc_smfy_Num=ysc_smfyLong==null?0:ysc_smfyLong.intValue();
				int ysc_ktfy_Num=ysc_ktfyLong==null?0:ysc_ktfyLong.intValue();
				int ysc_zq_Num=ysc_zqLong==null?0:ysc_zqLong.intValue();
				int dg_smfy_Num=dg_smfyLong==null?0:dg_smfyLong.intValue();
				int dg_ktfy_Num=dg_ktfyLong==null?0:dg_ktfyLong.intValue();
				
				countMap.put("ybcSpeechNum", ybcSpeechNum);
				countMap.put("wscSpeechNum", wscSpeechNum);
				countMap.put("ysc_smfy_Num", ysc_smfy_Num);
				countMap.put("ysc_ktfy_Num", ysc_ktfy_Num);
				countMap.put("ysc_zq_Num", ysc_zq_Num);
				countMap.put("dg_smfy_Num", dg_smfy_Num);
				countMap.put("dg_ktfy_Num", dg_ktfy_Num);
		}
		else {
			wscHql="select count(*) from Tspeech t where t.tmeeting.tsecondary.secondaryId='"+secondaryCode+"'"
					+" and t.status='"+Constants.CODE_TYPE_SPEECH_STATUS_WSC+"'"; //未审查
			ysc_smfyHql="select count(*) from Tspeech t where t.tmeeting.tsecondary.secondaryId='"+secondaryCode+"'"
					+" and t.status='"+Constants.CODE_TYPE_SPEECH_STATUS_YSC+"'" //已审查-书面发言
					+" and t.useSituation='"+Constants.CODE_TYPE_SPEECHADOPTED_STATUS_SMFY+"'";
			ysc_ktfyHql="select count(*) from Tspeech t where t.tmeeting.tsecondary.secondaryId='"+secondaryCode+"'"
					+" and t.status='"+Constants.CODE_TYPE_SPEECH_STATUS_YSC+"'" //已审查-口头发言
					+" and t.useSituation='"+Constants.CODE_TYPE_SPEECHADOPTED_STATUS_KTFY+"'" ;
			ysc_zqHql="select count(*) from Tspeech t where t.tmeeting.tsecondary.secondaryId='"+secondaryCode+"'"
					+" and t.status='"+Constants.CODE_TYPE_SPEECH_STATUS_YSC+"'" //已审查-转弃
					+" and t.useSituation='"+Constants.CODE_TYPE_SPEECHADOPTED_STATUS_ZQ+"'";
			dg_smfyHql="select count(*) from Tspeech t where t.tmeeting.tsecondary.secondaryId='"+secondaryCode+"'"
					+" and t.status='"+Constants.CODE_TYPE_SPEECH_STATUS_DG+"'" //定稿-书面发言
					+" and t.useSituation='"+Constants.CODE_TYPE_SPEECHADOPTED_STATUS_SMFY+"'"
					+" and t.tcommitteeman.code='"+sessionInfo.getUserCode()+"'";
			dg_ktfyHql="select count(*) from Tspeech t where t.tmeeting.tsecondary.secondaryId='"+secondaryCode+"'"
					+" and t.status='"+Constants.CODE_TYPE_SPEECH_STATUS_DG+"'" //定稿-口头发言
					+" and t.useSituation='"+Constants.CODE_TYPE_SPEECHADOPTED_STATUS_KTFY+"'" 
					+" and t.tcommitteeman.code='"+sessionInfo.getUserCode()+"'";
			
				Long wscLong=speechDAO.count(wscHql);
				Long ysc_smfyLong=speechDAO.count(ysc_smfyHql);
				Long ysc_ktfyLong=speechDAO.count(ysc_ktfyHql);
				Long ysc_zqLong=speechDAO.count(ysc_zqHql);
				Long dg_smfyLong=speechDAO.count(dg_smfyHql);
				Long dg_ktfyLong=speechDAO.count(dg_ktfyHql);
				
				int wscSpeechNum=wscLong==null?0:wscLong.intValue();
				int ysc_smfy_Num=ysc_smfyLong==null?0:ysc_smfyLong.intValue();
				int ysc_ktfy_Num=ysc_ktfyLong==null?0:ysc_ktfyLong.intValue();
				int ysc_zq_Num=ysc_zqLong==null?0:ysc_zqLong.intValue();
				int dg_smfy_Num=dg_smfyLong==null?0:dg_smfyLong.intValue();
				int dg_ktfy_Num=dg_ktfyLong==null?0:dg_ktfyLong.intValue();
				
				countMap.put("wscSpeechNum", wscSpeechNum);
				countMap.put("ysc_smfy_Num", ysc_smfy_Num);
				countMap.put("ysc_ktfy_Num", ysc_ktfy_Num);
				countMap.put("ysc_zq_Num", ysc_zq_Num);
				countMap.put("dg_smfy_Num", dg_smfy_Num);
				countMap.put("dg_ktfy_Num", dg_ktfy_Num);
		}
		return countMap;
	}
	
	public MeetingServiceI getMeetingService() {
		return meetingService;
	}
	@Autowired
	public void setMeetingService(MeetingServiceI meetingService) {
		this.meetingService = meetingService;
	}
	
	public BaseDaoI<Tspeech> getSpeechDAO() {
		return speechDAO;
	}
	@Autowired
	public void setSpeechDAO(BaseDaoI<Tspeech> speechDAO) {
		this.speechDAO = speechDAO;
	}
	
	public PollServiceI getPollService() {
		return pollService;
	}

	@Autowired
	public void setPollService(PollServiceI pollService) {
		this.pollService = pollService;
	}

}
