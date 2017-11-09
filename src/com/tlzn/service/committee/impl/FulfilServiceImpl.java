package com.tlzn.service.committee.impl;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;


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

import com.opensymphony.xwork2.ActionContext;
import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.sys.Tdic;
import com.tlzn.model.sys.Tsecondary;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.sys.Comm;
import com.tlzn.pageModel.sys.Dic;
import com.tlzn.pageModel.sys.Seco;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.committee.FulfilServiceI;
import com.tlzn.service.sys.DicServiceI;
import com.tlzn.service.sys.SecoServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Util;

@Service("fulfilService")
public class FulfilServiceImpl  extends BaseServiceImpl implements FulfilServiceI{
	private DicServiceI dicService;
	private SecoServiceI secoService;
	private BaseDaoI<Comm> fulfilDao;
	
	public DicServiceI getDicService() {
		return dicService;
	}
	@Autowired
	public void setDicService(DicServiceI dicService) {
		this.dicService = dicService;
	}

	public SecoServiceI getSecoService() {
		return secoService;
	}
	@Autowired
	public void setSecoService(SecoServiceI secoService) {
		this.secoService = secoService;
	}
	
	public BaseDaoI<Comm> getFulfilDao() {
		return fulfilDao;
	}
	@Autowired
	public void setFulfilDao(BaseDaoI<Comm> fulfilDao) {
		this.fulfilDao = fulfilDao;
	}
	
	/**
	 * 委员履职情况统计表头数据获取
	 */
	public String getColumns() throws Exception {
		String json="[{title:'工作单位',field:'companyName',halign:'center',align:'left',width:200,rowspan:2},{title:'职务',field:'job',halign:'center',align:'left',width:120,rowspan:2},";
		json+="{title:'建言献策情况',align:'center',colspan:3},";
		Dic dic=new Dic();
		dic.setCtype(Constants.MEETING_TYPE);
		List<Tdic> mTypeList=dicService.findAllTdic(dic);
		json+="{title:'参加会议情况',align:'center',colspan:"+mTypeList.size()+"},";
		dic.setCtype(Constants.ACT_TYPE_ASPECIES);
		List<Tdic> aTypeList=dicService.findAllTdic(dic);
		json+="{title:'参加活动情况',align:'center',colspan:"+aTypeList.size()+"},";
		json=json.substring(0,json.length()-1);
		json+="],[";
		json+="{title:'会议发言情况',field:'speakNum',align:'center',width:100},{title:'提交提案情况',field:'propNum',align:'center',width:100},{title:'反映社情民意情况',field:'pollNum',align:'center',width:100},";
		for(Tdic td : mTypeList){
			json+="{title:'"+td.getCkey()+"',field:'mnum"+td.getCvalue()+"',align:'center',width:80},";
		}
		for(Tdic td : aTypeList){
			json+="{title:'"+td.getCkey()+"',field:'anum"+td.getCvalue()+"',align:'center',width:80},";
		}
		json=json.substring(0,json.length()-1);
		json+="]";
		System.out.println("json=="+json);
		return json;
	}
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public String fulfilCount(Comm comm)	throws Exception {
		Dic dic=new Dic();
		dic.setCtype(Constants.MEETING_TYPE);
		List<Tdic> mTypeList=dicService.findAllTdic(dic);
		dic.setCtype(Constants.ACT_TYPE_ASPECIES);
		List<Tdic> aTypeList=dicService.findAllTdic(dic);
		
		String json="{\"total\":0,\"rows\":[";	//datagrid数据
		List<Object[]> cList=getDataCount(comm, mTypeList, aTypeList);
		for (Iterator<Object[]> it = cList.iterator(); it.hasNext();) {
			Object[] objects = it.next();
			json+="{\"code\":\""+objects[0]+"\",\"name\":\"";
			json+=objects[1]+"\",";
			json+="\"sex\":\"";
			if(Util.getInstance().isEmpty(objects[2])){
				json+="\",";
			}else{
				json+=this.findDicName(Constants.CODE_TYPE_ID_SEX, objects[2].toString())+"\",";
			}
			json+="\"circleName\":\"";
			if(Util.getInstance().isEmpty(objects[3])){
				json+="\",";
			}else{
				json+=this.findDicName(Constants.CODE_TYPE_ID_CIRCLE, objects[3].toString())+"\",";
			}
			json+="\"committeeName\":\"";
			if(Util.getInstance().isEmpty(objects[4])){
				json+="\",";
			}else{
				json+=this.findDicName(Constants.CODE_TYPE_ID_COMMITTEE, objects[4].toString())+"\",";
			}
			json+="\"companyName\":\"";
			if(Util.getInstance().isEmpty(objects[5])){
				json+="\",";
			}else{
				json+=objects[5]+"\",";
			}
			json+="\"job\":\"";
			if(Util.getInstance().isEmpty(objects[6])){
				json+="\",";
			}else{
				json+=objects[6]+"\",";
			}
			json+="\"telephone\":\"";
			if(Util.getInstance().isEmpty(objects[7])){
				json+="\",";
			}else{
				json+=objects[7]+"\",";
			}
			json+="\"email\":\"";
			if(Util.getInstance().isEmpty(objects[8])){
				json+="\",";
			}else{
				json+=objects[8]+"\",";
			}
			json+="\"speakNum\":\"";
			if(Util.getInstance().isEmpty(objects[9])){
				json+="0\",";
			}else{
				json+=objects[9]+"\",";
			}
			json+="\"propNum\":\"";
			if(Util.getInstance().isEmpty(objects[10])){
				json+="0\",";
			}else{
				json+=objects[10]+"\",";
			}
			json+="\"pollNum\":\"";
			if(Util.getInstance().isEmpty(objects[11])){
				json+="0\",";
			}else{
				json+=objects[11]+"\",";
			}
			int num=12;
			for(Tdic td : mTypeList){
				json+="\"mnum"+td.getCvalue()+"\":\"";
				if(Util.getInstance().isEmpty(objects[num])){
					json+="0\",";
				}else{
					json+=objects[num]+"\",";
				}
				num+=1;
			}
			for(Tdic td : aTypeList){
				json+="\"anum"+td.getCvalue()+"\":\"";
				if(Util.getInstance().isEmpty(objects[num])){
					json+="0\",";
				}else{
					json+=objects[num]+"\",";
				}
				num+=1;
			}
			json=json.substring(0,json.length()-1);
			json+="},";
			
		}
		if(",".equals(json.substring(json.length()-1))){
			json=json.subSequence(0,json.length()-1)+"]}";
		}else{
			json+="]}";
		}
		System.out.println("json=="+json);
		return json;
	}
	
	private List<Object[]> getDataCount(Comm comm,List<Tdic> mTypeList,List<Tdic> aTypeList)throws Exception{
		String secondaryId=((Seco) ActionContext.getContext().getSession().get("sessionSeco")).getSecondaryId();
		if (Util.getInstance().isEmpty(comm.getSecondaryCode())) {
			comm.setSecondaryCode(secondaryId);
		}
		
		String hql="select t.code,t.name,t.sex,t.circle_code,t.committee,t.company_name,t.job,t.telephone,t.email,s.tsnum,p.tpnum,ppo.tponum ";
		
		for(Tdic td : mTypeList){
			hql+=",mnum"+td.getCvalue();
		}
		for(Tdic td : aTypeList){
			hql+=",anum"+td.getCvalue();
		}
		hql+=" from Tcommitteeman t"
			+" left join (select ts.comm_code,count(*) tsnum from tmeet_speak ts where ts.use_situation!='"+Constants.CODE_TYPE_SPEECHADOPTED_STATUS_ZQ+"' and ts.status!='"+Constants.CODE_TYPE_SPEECH_STATUS_YBC+"' and exists (select meet_id from tmeeting me where me.secondary_id='"+comm.getSecondaryCode()+"' and me.meet_id=ts.meet_id) group by ts.comm_code) s on s.comm_code=t.code"
			+" left join (select tp.committeeman_code,count(*) tpnum from tproposaler tp where tp.host_flg='"+Constants.CODE_TYPE_YESNO_YES+"' and exists (select pr.proposal_id from tproposal pr where pr.adopt_flg='"+Constants.CODE_TYPE_YESNO_YES+"' and pr.secondary_id='"+comm.getSecondaryCode()+"' and (pr.file_method ='"+Constants.CODE_TYPE_LAXS_LA+"' or pr.file_method is null) and pr.proposal_id=tp.proposal_id） group by tp.committeeman_code) p on p.committeeman_code=t.code"
			+" left join (select po.create_man,count(*) tponum from"
			+" (select regexp_substr(tpo.create_man,'[^,]+',1,n) create_man,tpo.discard,tpo.status,tpo.adopt_flg,tpo.secondary_id from tpoll tpo,(select level n from dual connect by level<=10) extend_rows where regexp_substr(tpo.create_man,'[^,]+',1,n) is not null) po"
			+" where po.discard='"+Constants.CODE_TYPE_YESNO_NO+"' and po.status!='"+Constants.CODE_TYPE_POLL_STATUS_YBC+"' and po.adopt_flg='"+Constants.CODE_TYPE_YESNO_YES+"' and po.secondary_id ='"+comm.getSecondaryCode()+"' group by po.create_man) ppo on ppo.create_man=t.code "
			+" left join (select tt.comm_code";
		for(Tdic td : mTypeList){
			hql+=",sum(decode(tt.meet_type, '"+td.getCvalue()+"', tt.num , 0)) mnum"+td.getCvalue();
		}
			hql+=" from (select mm.comm_code,m.meet_type,count(*) num from tmeeting_man mm left join tmeeting m on m.meet_id=mm.meet_id where mm.status='"+Constants.PRESENT_TYPE_STATUS_CX+"' and m.secondary_id='"+comm.getSecondaryCode()+"'  group by mm.comm_code,m.meet_type) tt group by tt.comm_code) tm on tm.comm_code=t.code "
			+" left join (select tt.comm_code";
		
		for(Tdic td : aTypeList){
			hql+=",sum(decode(tt.aspecies, '"+td.getCvalue()+"', tt.num , 0)) anum"+td.getCvalue();
		}	
	
		hql+=" from (select  ap.comm_code,a.aspecies,count(*) num from tactivitypeo ap left join tactivity a on a.aid=ap.aid where ap.astatus='"+Constants.PRESENT_TYPE_STATUS_CX+"'  and a.secondary_id='"+comm.getSecondaryCode()+"' group by ap.comm_code,a.aspecies) tt group by tt.comm_code) ta on ta.comm_code=t.code "
			+"  where t.group_code='"+Constants.DIC_TYPE_YHZB_WY+"'";
		if (comm.getName() != null && !"".equals(comm.getName())) {
			hql += " and t.name like '%" + comm.getName() + "%'";
		}
		if (comm.getSex() != null && !"".equals(comm.getSex())) {
			hql += " and t.sex = '"+comm.getSex()+"'";
		}
		if (comm.getNation() != null && !"".equals(comm.getNation())) {
			hql += " and t.nation = '"+comm.getNation()+"'";
		}
		if (comm.getTelephone() != null && !"".equals(comm.getTelephone())) {
			hql += " and t.telephone ='"+comm.getTelephone()+"'";
		}
		if (comm.getPartyCode() != null && !"".equals(comm.getPartyCode())) {
			hql += " and t.party_code ='"+comm.getPartyCode()+"'";
		}
		if (comm.getCircleCode() != null && !"".equals(comm.getCircleCode())) {
			hql += " and t.circle_code ='"+comm.getCircleCode()+"'";
		}
		if (comm.getCommittee() != null && !"".equals(comm.getCommittee())) {
			hql += " and t.committee ='"+comm.getCommittee()+"'";
		}	
 		if (comm.getSecondaryCode() != null && !"".equals(comm.getSecondaryCode())) {
			String s="";
			for(String t : comm.getSecondaryCode().split(",")){
				s += " t.secondary_code like '%" + t + "%' or ";
			}
			s="(" + s.substring(0,s.length()-3) + ")";
			hql += " and " + s;
		}else{
			hql += " and t.secondary_code like '%" + secondaryId + "%'";
		}
		hql+=" order by t.code";
		List<Object[]> cList=fulfilDao.executeCountSql(hql);
		
		return cList;
	}
	/**
	 * 
	 *报表生成
	 * 
	 * @param prop
	 * 
	 * @throws 	Exception
	 */
	public String report(Comm comm) throws Exception{
		String secondaryYear="";
		
		if (Util.getInstance().isEmpty(comm.getSecondaryCode())) {
			Tsecondary tseco=secoService.find(comm.getSecondaryCode());
			secondaryYear=tseco.getYear();
		}else{
			Seco seco=(Seco) ActionContext.getContext().getSession().get("sessionSeco");
			secondaryYear=seco.getYear();
		}
		Dic dic=new Dic();
		dic.setCtype(Constants.MEETING_TYPE);
		List<Tdic> mTypeList=dicService.findAllTdic(dic);
		dic.setCtype(Constants.ACT_TYPE_ASPECIES);
		List<Tdic> aTypeList=dicService.findAllTdic(dic);
		
		List<Object[]> cList=getDataCount(comm, mTypeList, aTypeList);
		
		Properties prop = new Properties();  
		prop.load(this.getClass().getResourceAsStream("/config.properties")); //读取config.properties中的导出
    	File path=new File(Constants.ROOTPATH + prop.getProperty("exportPath"));
        if(!path.exists())path.mkdir();//如果路径不存在，则创建
        //新建文件
        String fileName="/performanceDuties.xls";
        String filepath=prop.getProperty("exportPath")+fileName;
        WritableWorkbook book= Workbook.createWorkbook(new File(path+fileName));
        System.out.println(filepath);
        //新建工作表
		WritableSheet sheet  =  book.createSheet( "Sheet1 " ,  0 );          
		SheetSettings ss = sheet.getSettings();            
		// ss.setHorizontalFreeze(2);  // 设置列冻结            
		ss.setVerticalFreeze(3);  // 设置行冻结前2行            
		ss.setDefaultRowHeight(500);//默认行高
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
		
		sheet.mergeCells( 0 , 0 , 9 , 0 ); // 合并单元格  
		//          在Label对象的构造子中指名单元格位置是第一列第一行(0,0)
		//          以及单元格内容为test            
		jxl.write.Label titleLabel = new Label( 0 , 0 , "政协委员会委员履职情况("+secondaryYear+"年度)",wcf);
		//          将定义好的单元格添加到工作表中            
		sheet.addCell(titleLabel);            
		sheet.setRowView(0, 680); // 设置第一行的高度  20121111   
		int[] headerArrWidth = {15,10,20,20,40,20,20,20}; //列宽           
		String headerArr[] = {"委员","性别","界别","专委会","工作单位","职务","联系电话","邮箱"};            
		for (int i = 0; i < headerArr.length; i++) {                
			sheet.addCell(new Label( i , 1 , headerArr[i],wcf)); 
			sheet.mergeCells(i, 1, i, 2); 
			sheet.setColumnView(i, headerArrWidth[i]);            
		} 
		int i=headerArr.length;
		sheet.addCell(new Label( i , 1 , "建言献策情况",wcf));
		sheet.mergeCells(i, 1, i+2, 1);
		sheet.addCell(new Label( i , 2 , "会议发言情况",wcf));
		sheet.setColumnView(i, 15); 
		sheet.addCell(new Label( i+1 , 2 , "提交提案情况",wcf));
		sheet.setColumnView(i+1, 15); 
		sheet.addCell(new Label( i+2 , 2 , "反映社情民意情况",wcf));
		sheet.setColumnView(i+2, 20); 
		i=i+3;
		sheet.addCell(new Label( i , 1 , "参加会议情况",wcf));
		sheet.mergeCells(i, 1, i+mTypeList.size()-1, 1);
		for(Tdic td : mTypeList){
			sheet.addCell(new Label( i , 2 , td.getCkey(),wcf));
			sheet.setColumnView(i, 15); 
			i+=1;
		}
		sheet.addCell(new Label( i , 1 , "参加活动情况",wcf));
		sheet.mergeCells(i, 1, i+aTypeList.size()-1, 1);
		for(Tdic td : aTypeList){
			sheet.addCell(new Label( i , 2 , td.getCkey(),wcf));
			sheet.setColumnView(i, 15); 
			i+=1;
		}
		int count=3;
		for (Iterator<Object[]> it = cList.iterator(); it.hasNext();) {
			Object[] objs = it.next();  
			//循环一个list里面的数据到excel中      
			sheet.addCell(new Label( 0 , count ,objs[1].toString(),wcf2));
			sheet.addCell(new Label( 1 , count ,Util.getInstance().isEmpty(objs[2])?"":this.findDicName(Constants.CODE_TYPE_ID_SEX, objs[2].toString()),wcf2));
			sheet.addCell(new Label( 2 , count ,Util.getInstance().isEmpty(objs[3])?"":this.findDicName(Constants.CODE_TYPE_ID_CIRCLE, objs[3].toString()),wcf2));
			sheet.addCell(new Label( 3 , count ,Util.getInstance().isEmpty(objs[4])?"":this.findDicName(Constants.CODE_TYPE_ID_COMMITTEE, objs[4].toString()),wcf2));
			sheet.addCell(new Label( 4 , count ,(String)objs[5],wcf2));
			sheet.addCell(new Label( 5 , count ,(String)objs[6],wcf2));
			sheet.addCell(new Label( 6 , count ,(String)objs[7],wcf2));
			sheet.addCell(new Label( 7 , count ,(String)objs[8],wcf2));
			int cnum=8;
			int anum=9;
			while (anum<objs.length) {
					sheet.addCell(new Label( cnum , count ,Util.getInstance().isEmpty(objs[anum])?"0":objs[anum].toString(),wcf2));
				cnum+=1;
				anum+=1;
			}
			count++;           
		}           
		//          写入数据并关闭文件           
		book.write();            
		book.close();           
		return filepath;		
		
	}
	
}
