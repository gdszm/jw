package com.tlzn.service.sys.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.sys.Tdic;
import com.tlzn.model.sys.Tsecondary;
import com.tlzn.pageModel.sys.Count;
import com.tlzn.pageModel.sys.Dic;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.sys.CountServiceI;
import com.tlzn.service.sys.DicServiceI;
import com.tlzn.util.base.Constants;

@Service("countService")
public class CountServiceImp extends BaseServiceImpl implements CountServiceI {
	private BaseDaoI<Count> countDao;
	private BaseDaoI<Tsecondary> secoDao;
	private DicServiceI dicService;
	
	protected final Log log = LogFactory.getLog(getClass());
	
	public BaseDaoI<Count> getCountDao() {
		return countDao;
	}
	
	@Autowired
	public void setCountDao(BaseDaoI<Count> countDao) {
		this.countDao = countDao;
	}

	public DicServiceI getDicService() {
		return dicService;
	}
	
	@Autowired
	public void setDicService(DicServiceI dicService) {
		this.dicService = dicService;
	}

	public BaseDaoI<Tsecondary> getSecoDao() {
		return secoDao;
	}
	
	@Autowired
	public void setSecoDao(BaseDaoI<Tsecondary> secoDao) {
		this.secoDao = secoDao;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public String datagrid(Count count) throws Exception{
		String json="";
		Dic dic=new Dic();
		String s=count.getItemid();
		if("FY".equals(s) || "GK".equals(s))s="FYYGK";
		if("MERGE".equals(s))s="YESNO";
		dic.setCtype(String.valueOf(s));
		List<Tdic> list=new ArrayList<Tdic>();
		if("TAANREN".equals(s) || "WEIYUAN".equals(s)){
			Tdic tdic=new Tdic();
			tdic.setCkey("提交提案");
			tdic.setCvalue("1");
			list.add(tdic);
			tdic=new Tdic();
			tdic.setCkey("未提交提案");
			tdic.setCvalue("0");
			list.add(tdic);
		}else {
			list=dicService.findAllTdic(dic);//里面包含了第一项“请选择”
		}	
		
		if(list==null)return json;
		List<List> mainList=new ArrayList();
		List<List> mainHandNumList=new ArrayList();
		List<List> mainPercent=new ArrayList();
		if("hand".equals(count.getCountType())){
			for (String jc : count.getJc().split(",")){
				long total=0;
				long handtotal=0;
				List numList=new ArrayList();
				List handNumList=new ArrayList();
				List numPercent=new ArrayList();
				System.out.println("list======="+list.size());
				for(int i=0;i<list.size();i++){//行循环
					long temp=countDao.count(this.getHql(count, jc.trim(), list.get(i).getCvalue()));//将查询的值传入函数
					total+=temp;
					numList.add(temp);
					long numtemp=countDao.count(getHandNumHql(count, jc.trim(), list.get(i).getCvalue()));//力复数统计
					handtotal+=numtemp;
					handNumList.add(numtemp);
				}
				numList.add(total);
				handNumList.add(handtotal);
				System.out.println("====num:"+numList.size());
				System.out.println("====hand:"+handNumList.size());
				for(int j=0;j<numList.size();j++){
					numPercent.add(Double.parseDouble(numList.get(j).toString())>0 ? new DecimalFormat("#.##").format(Double.parseDouble(handNumList.get(j).toString())*100/Double.parseDouble(numList.get(j).toString())) : null);
				}
				mainList.add(numList);
				mainHandNumList.add(handNumList);
				mainPercent.add(numPercent);
			}
			Tdic dictotal=new Tdic();
			dictotal.setCtype("SEX");
			dictotal.setCkey("总计");
			list.add(dictotal);
			json=getGridJson(count.getJc(), list, mainList, mainPercent,mainHandNumList);
			return json;
		}else{
			for (String jc : count.getJc().split(",")){
				long total=0;
				List numList=new ArrayList();
				List numPercent=new ArrayList();
				System.out.println("list======="+list.size());
				for(int i=0;i<list.size();i++){//行循环
					long temp=countDao.count(this.getHql(count, jc.trim(), list.get(i).getCvalue()));//将查询的值传入函数
					total+=temp;
					numList.add(temp);
				}
				numList.add(total);
				for(int j=0;j<numList.size();j++){
					numPercent.add(total>0 ? new DecimalFormat("#.##").format(Double.parseDouble(numList.get(j).toString())*100/total) : null);
				}
				mainList.add(numList);
				mainPercent.add(numPercent);
			}
			Tdic dictotal=new Tdic();
			dictotal.setCtype("SEX");
			dictotal.setCkey("总计");
			list.add(dictotal);
			json=getGridJson(count.getJc(), list, mainList, mainPercent);
			return json;
		}
	}

	private String getHandNumHql(Count count ,String q1, String q2){
		String hql="";
		String radio=count.getItemid();
		hql="Select count(*) from Tproposal t where  t.tsecondary.secondaryId = '" + q1 + "' and t.stutas='"+Constants.CODE_TYPE_TAZT_YBL+"' and t.submitFlg='"+Constants.CODE_TYPE_TJZT_YTJ+"' and t.replyPass='"+Constants.CODE_TYPE_BFSC_YTG+"'";
		if("COMPANYTYPE".equals(radio))	//单位类别
			hql+=" and trim(t.analySisUnit) ='" + q2 + "'";
		if("BLLX".equals(radio))	//办理方式
			hql+=" and trim(t.handleType) ='" + q2 + "'";
		/*if("JJCD".equals(radio))	//解决程度
			hql+=" and trim(t.solveHow) ='" + q2 + "'";
		if("WYYJ".equals(radio))	//委员意见
			hql+=" and t.committeemanOpinion ='" + q2 + "'";
		if("YESNO".equals(radio))	//是否落实
			hql+=" and trim(t.carryoutFlg) ='" + q2 + "'";*/
		
		return hql;
	}
	private String getHql(Count count , String q1, String q2) {
		String hql="";
		String radio=count.getItemid();
		if(count.getCountType()!=null && !"".equals(count.getCountType())){
			if("comm".equals(count.getCountType())){	//提案人信息统计菜单,secondaryCode届次
				String table="Tcommitteeman";
				if("SEX".equals(radio))	//按性别
					hql="Select count(sex) from " + table + " t where  t.secondaryCode like '%" + q1 + "%' and t.status='"+Constants.CODE_TYPE_QYTY_YES+"' and t.sex ='" + q2 + "'";
				if("CIRCLE".equals(radio))	//按界别
					hql="Select count(circleCode) from " + table + " t where  t.secondaryCode like '%" + q1 + "%' and t.status='"+Constants.CODE_TYPE_QYTY_YES+"' and t.circleCode ='" + q2 + "'";
				if("NATION".equals(radio))	//按民族
					hql="Select count(nation) from " + table + " t where  t.secondaryCode like '%" + q1 + "%' and t.status='"+Constants.CODE_TYPE_QYTY_YES+"' and t.nation ='" + q2 + "'";
				if("PARTY".equals(radio))	//按党派
					hql="Select count(partyCode) from " + table + " t where  t.secondaryCode like '%" + q1 + "%' and t.status='"+Constants.CODE_TYPE_QYTY_YES+"' and t.partyCode ='" + q2 + "'";
				if("GROUP".equals(radio))	//按分组
					hql="Select count(groupCode) from " + table + " t where  t.secondaryCode like '%" + q1 + "%' and t.status='"+Constants.CODE_TYPE_QYTY_YES+"' and t.groupCode ='" + q2 + "'";
				if("STATUS".equals(radio))	//按状态
					hql="Select count(status) from " + table + " t where  t.secondaryCode like '%" + q1 + "%'  and t.status ='" + q2 + "'";
				if("EDUCATION".equals(radio))	//按学历
					hql="Select count(eduCode) from " + table + " t where  t.secondaryCode like '%" + q1 + "%' and t.status='"+Constants.CODE_TYPE_QYTY_YES+"' and t.eduCode ='" + q2 + "'";
				if("DEGREE".equals(radio))	//按学位
					hql="Select count(degreeCode) from " + table + " t where  t.secondaryCode like '%" + q1 + "%' and t.status='"+Constants.CODE_TYPE_QYTY_YES+"' and t.degreeCode ='" + q2 + "'";
				if("TAANREN".equals(radio))//提案人提交情况
				{
					if("1".equals(q2)){
						hql="select count(*) from Tcommitteeman t  where exists(select distinct tp.tcommitteeman.code from Tproposaler tp where tp.tcommitteeman.code=t.code) and t.status='"+Constants.CODE_TYPE_QYTY_YES+"' and t.secondaryCode like '%" + q1 + "%'";
					}else{
						hql="select count(*) from Tcommitteeman t  where not exists(select distinct tp.tcommitteeman.code from Tproposaler tp where tp.tcommitteeman.code=t.code) and t.status='"+Constants.CODE_TYPE_QYTY_YES+"' and t.secondaryCode like '%" + q1 + "%'";
					}
				}
				if ("WEIYUAN".equals(radio))//提案人提交情况
				{
					if("1".equals(q2)){
						hql="select count(*) from Tcommitteeman t  where exists(select distinct tp.tcommitteeman.code from Tproposaler tp where tp.tcommitteeman.code=t.code) and t.secondaryCode like '%" + q1 + "%' and t.status='"+Constants.CODE_TYPE_QYTY_YES+"' and t.groupCode='"+Constants.DIC_TYPE_GROUP_WY+"'";
					}else{
						hql="select count(*) from Tcommitteeman t  where not exists(select distinct tp.tcommitteeman.code from Tproposaler tp where tp.tcommitteeman.code=t.code) and t.secondaryCode like '%" + q1 + "%' and t.status='"+Constants.CODE_TYPE_QYTY_YES+"' and t.groupCode='"+Constants.DIC_TYPE_GROUP_WY+"'";
					}
				}
			}
			if("pro".equals(count.getCountType())){		//提案人提案统计菜单,hostFlg主提案人,revokeFlg提案人未彻案（已提案）
				hql="Select count(t.tproposal.proposalId) from Tproposaler t where  t.tproposal.tsecondary.secondaryId like '%" + q1 + "%' and t.tcommitteeman.secondaryCode like '%" + q1 + "%' and t.hostFlg ='1' and (t.tproposal.revokeFlg='0' or t.tproposal.revokeFlg is null)";
				if("SEX".equals(radio))	//按性别
					hql+=" and trim(t.tcommitteeman.sex) ='" + q2 + "'";
				if("CIRCLE".equals(radio))	//按界别
					hql+=" and trim(t.tcommitteeman.circleCode) ='" + q2 + "'";
				if("NATION".equals(radio))	//按民族
					hql+=" and trim(t.tcommitteeman.nation) ='" + q2 + "'";
				if("PARTY".equals(radio))	//按党派
					hql+=" and trim(t.tcommitteeman.partyCode) ='" + q2 + "'";
			}
			if("info".equals(count.getCountType())){		//提案统计菜单,hostFlg主提案人,revokeFlg提案人未彻案（已提案）
				hql="Select count(t.proposalId) from Tproposal t where  t.tsecondary.secondaryId ='" + q1 + "' and t.stutas!='"+Constants.CODE_TYPE_TAZT_YBC+"'";
				if("TAZT".equals(radio))	//提案状态
					hql+=" and t.adoptFlg='"+Constants.CODE_TYPE_YESNO_YES+"' and trim(t.stutas) ='" + q2 + "'";
				if("TALX".equals(radio))	//提案类型
					hql+=" and trim(t.proposalType) ='" + q2 + "'";
				if("TAFL".equals(radio))	//内容分类
					hql+=" and t.adoptFlg='"+Constants.CODE_TYPE_YESNO_YES+"' and trim(t.propertiesType) ='" + q2 + "'";
				if("YWFZ".equals(radio))	//业务分组
					hql+=" and t.adoptFlg='"+Constants.CODE_TYPE_YESNO_YES+"' and trim(t.businessCode) ='" + q2 + "'";
				if("LAXS".equals(radio))	//立案状态
					hql+=" and t.adoptFlg='"+Constants.CODE_TYPE_YESNO_YES+"' and trim(t.fileMethod) ='" + q2 + "'";
				if("BLLX".equals(radio))	//办理方式
					hql+=" and t.adoptFlg='"+Constants.CODE_TYPE_YESNO_YES+"' and trim(t.handleType) ='" + q2 + "'";
				if("FY".equals(radio))	//可否附议
					hql+=" and trim(t.secondFlg) ='" + q2 + "'";
				if("GK".equals(radio))	//可否网上公开
					hql+=" and trim(t.webFlg) ='" + q2 + "'";
				if("MERGE".equals(radio))//合并提案
				{
					if(Constants.CODE_TYPE_YESNO_YES.equals(q2)){
						hql+="and t.mergeFlag='"+q2+"' and t.adoptFlg='"+Constants.CODE_TYPE_YESNO_NO+"'";
					}else {
						hql+="and t.adoptFlg='"+Constants.CODE_TYPE_YESNO_YES+"'";
					}
				}	
			}
			if("hand".equals(count.getCountType())){		//提案办理统计菜单,hostFlg主提案人,revokeFlg提案人未彻案（已提案）
				hql="Select count(*) from Tproposal t where  t.tsecondary.secondaryId = '" + q1 + "' and t.submitFlg='"+Constants.CODE_TYPE_TJZT_YTJ+"'";
				if("COMPANYTYPE".equals(radio))	//单位类别
					hql+=" and trim(t.analySisUnit) ='" + q2 + "'";
				if("BLLX".equals(radio))	//办理方式
					hql+=" and trim(t.handleType) ='" + q2 + "'";
				/*if("JJCD".equals(radio))	//解决程度
					hql+=" and trim(t.solveHow) ='" + q2 + "'";
				if("WYYJ".equals(radio))	//委员意见
					hql+=" and t.committeemanOpinion ='" + q2 + "'";
				if("YESNO".equals(radio))	//是否落实
					hql+=" and trim(t.carryoutFlg) ='" + q2 + "'";*/
			}
		}
		return hql;
	}

	@SuppressWarnings("unchecked")
	private String getGridJson(String jc, List<Tdic> list, List<List> mainList, List<List> mainPercent) {
		String json="{\"grid\":{\"total\":" + list.size() + ",\"rows\":[";	//datagrid数据
		for(int i=0;i<list.size();i++){
			json+="{\"item\":\"" + list.get(i).getCkey() + "\",";
			for(int j=0;j<mainList.size();j++){
				json+="\"value" + j + "\" : " + mainList.get(j).get(i) + ",";
				json+="\"percent" + j + "\" : " + mainPercent.get(j).get(i) + ",";
			}
			json = json.substring(0,json.length()-1);
			json += "},";
		}
		json = json.substring(0,json.length()-1);
		json += "]},\"item\":[";
		for(int i=0;i<list.size()-1;i++){
			json+="\"" + list.get(i).getCkey() + "\",";
		}
		json = json.substring(0,json.length()-1);
		json += "],\"chart\":[";	//chart数据
		int m=0;
		for(String id : jc.split(",")){
				json+="{\"name\":\"" + secoDao.get(Tsecondary.class, id).getSecondayName() + "\",\"data\":[";
				for(int j=0;j<list.size()-1;j++){//里面包含了总计行
					json+="[\"" + list.get(j).getCkey() + "\","+  mainPercent.get(m).get(j) + "],";
				}
				json = json.substring(0,json.length()-1);
				json += "],\"dataLabels\": {\"enabled\": \"true\",\"format\": \"{point.y:.1f}%\"}},";	//<b>{point.name}</b>:{point.y:.1f}%\"}}
				m++;
		}
		json = json.substring(0,json.length()-1);
		json +="]}";
		return json;
	}
	@SuppressWarnings("unchecked")
	private String getGridJson(String jc, List<Tdic> list, List<List> mainList, List<List> mainPercent,List<List> mainHandList) {
		String json="{\"grid\":{\"total\":" + list.size() + ",\"rows\":[";	//datagrid数据
		for(int i=0;i<list.size();i++){
			json+="{\"item\":\"" + list.get(i).getCkey() + "\",";
			for(int j=0;j<mainList.size();j++){
				json+="\"value" + j + "\" : " + mainList.get(j).get(i) + ",";
				json+="\"handleNum" + j + "\" : " + mainHandList.get(j).get(i) + ",";
				json+="\"percent" + j + "\" : " + mainPercent.get(j).get(i) + ",";
			}
			json = json.substring(0,json.length()-1);
			json += "},";
		}
		json = json.substring(0,json.length()-1);
		json += "]},\"item\":[";
		for(int i=0;i<list.size()-1;i++){
			json+="\"" + list.get(i).getCkey() + "\",";
		}
		json = json.substring(0,json.length()-1);
		json += "],\"chart\":[";	//chart数据
		int m=0;
		for(String id : jc.split(",")){
				json+="{\"name\":\"" + secoDao.get(Tsecondary.class, id).getSecondayName() + "\",\"data\":[";
				for(int j=0;j<list.size()-1;j++){//里面包含了总计行
					json+="[\"" + list.get(j).getCkey() + "\","+  mainPercent.get(m).get(j) + "],";
				}
				json = json.substring(0,json.length()-1);
				json += "],\"dataLabels\": {\"enabled\": \"true\",\"format\": \"{point.y:.1f}%\"}},";	//<b>{point.name}</b>:{point.y:.1f}%\"}}
				m++;
		}
		json = json.substring(0,json.length()-1);
		json +="]}";
		return json;
	}

}
