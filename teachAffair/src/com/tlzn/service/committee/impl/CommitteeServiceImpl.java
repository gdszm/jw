package com.tlzn.service.committee.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
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
import org.springframework.transaction.config.TxNamespaceHandler;

import com.opensymphony.xwork2.ActionContext;
import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.Tcommitteeman;
import com.tlzn.model.sys.Tdolog;
import com.tlzn.model.sys.Tsecondary;
import com.tlzn.model.sys.Tuser;
import com.tlzn.pageModel.activity.Activity;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.sys.Comm;
import com.tlzn.pageModel.sys.Seco;
import com.tlzn.pageModel.sys.User;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.committee.CommitteeServiceI;
import com.tlzn.service.sys.UserServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Encrypt;
import com.tlzn.util.base.Generator;
import com.tlzn.util.base.ObjectPropertyUtilsBean;
import com.tlzn.util.base.Util;

@Service("committeeService")
public class CommitteeServiceImpl  extends BaseServiceImpl implements CommitteeServiceI {
	
	private BaseDaoI<Tcommitteeman> commDao;
	private BaseDaoI<Tsecondary> secoDao;
	private BaseDaoI<Tuser> userDao;
	private UserServiceI userService;
	
	public BaseDaoI<Tcommitteeman> getCommDao() {
		return commDao;
	}
	@Autowired
	public void setCommDao(BaseDaoI<Tcommitteeman> commDao) {
		this.commDao = commDao;
	}
	
	public BaseDaoI<Tsecondary> getSecoDao() {
		return secoDao;
	}
	
	@Autowired
	public void setSecoDao(BaseDaoI<Tsecondary> secoDao) {
		this.secoDao = secoDao;
	}
	public BaseDaoI<Tuser> getUserDao() {
		return userDao;
	}
	@Autowired
	public void setUserDao(BaseDaoI<Tuser> userDao) {
		this.userDao = userDao;
	}
	public UserServiceI getUserService() {
		return userService;
	}
	@Autowired
	public void setUserService(UserServiceI userService) {
		this.userService = userService;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(Comm comm) throws Exception {
		String hql = "from Tcommitteeman t where groupCode ='"+Constants.DIC_TYPE_YHZB_WY+"' ";
		DataGrid j = new DataGrid();
		j.setRows(getCommsFromTComms(find(hql,comm)));
		j.setTotal(total(hql,comm));
		return j;
	}

	/*
	 * 查找记录并筛选记录
	 */	
	private List<Tcommitteeman> find(String hql,Comm comm) {
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(comm, hql, values);
		hql+=" order by t.groupCode,t.code";
		return commDao.find(hql, values, comm.getPage(), comm.getRows());
	}
	/*
	 * 统计记录总数
	 */	
	private Long total(String hql,Comm comm) {
		hql = "select count(*) "+hql;
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(comm, hql, values);
		return commDao.count(hql, values);
	}
	/*
	 * 从实体对象列表中复制出新建自造对象
	 */
	private List<Comm> getCommsFromTComms(List<Tcommitteeman> Tcomms) throws Exception {
		List<Comm> ads = new ArrayList<Comm>();
		if (Tcomms != null && Tcomms.size() > 0) {
			for (Tcommitteeman t : Tcomms) {
				Comm p = new Comm();
				BeanUtils.copyProperties(t,p);
				p.setSexName(this.findDicName("SEX", t.getSex()));
				p.setNationName(this.findDicName("NATION", t.getNation()));
				p.setGroupName(this.findDicName("GROUP", t.getGroupCode()));
				p.setPartyName(this.findDicName("PARTY", t.getPartyCode()));
				p.setCircleName(this.findDicName("CIRCLE", t.getCircleCode()));
				p.setEduName(this.findDicName("EDUCATION", t.getEduCode()));
				p.setDegreeName(this.findDicName("DEGREE",t.getDegreeCode()));
				p.setVocationName(this.findDicName("VOCATION", t.getVocation()));
				p.setTitleName(this.findDicName("TITLE", t.getTitle()));
				p.setJobName(this.findDicName("JOB", t.getJob()));
				p.setStatusName(this.findDicName("STATUS", t.getStatus()));
				p.setCommitteeName(this.findDicName("COMMITTEE", t.getCommittee()));
				String secondaryName="";				
				if (!"".equals(p.getSecondaryCode()) && null!=p.getSecondaryCode())  {
					for (String id :p.getSecondaryCode().split(",")) {//将届次编号替换成届次名称
						Tsecondary s = secoDao.get(Tsecondary.class, id.trim());
						if (s != null) {
							secondaryName+=s.getSecondayName()+",";
						}
					}
				}
				if(secondaryName.length()>0)secondaryName=secondaryName.substring(0,secondaryName.length()-1);
				p.setSecondaryName(secondaryName);
				ads.add(p);
			}
		}
		return ads;
	}
	
	/*
	 * 组合hql语句
	 */	
	private String addWhere(Comm comm, String hql, List<Object> values) {
		System.out.println("name=="+comm.getName()+"*****");
		if (comm.getName() != null && !"".equals(comm.getName())) {
			hql += " and t.name like ? ";
			values.add("%" + comm.getName() + "%");
		}
		if (comm.getSex() != null && !"".equals(comm.getSex())) {
			hql += " and t.sex = ? ";
			values.add(comm.getSex());
		}
		if (comm.getNation() != null && !"".equals(comm.getNation())) {
			hql += " and t.nation = ? ";
			values.add(comm.getNation());
		}
		if (comm.getTelephone() != null && !"".equals(comm.getTelephone())) {
			hql += " and t.telephone = ? ";
			values.add(comm.getTelephone());
		}
		if (comm.getPartyCode() != null && !"".equals(comm.getPartyCode())) {
			hql += " and t.partyCode = ? ";
			values.add(comm.getPartyCode());
		}
		if (comm.getCircleCode() != null && !"".equals(comm.getCircleCode())) {
			hql += " and t.circleCode = ? ";
			values.add(comm.getCircleCode());
		}
		if (comm.getEduCode() != null && !"".equals(comm.getEduCode())) {
			hql += " and t.eduCode = ? ";
			values.add(comm.getEduCode());
		}
		if (comm.getDegreeCode() != null && !"".equals(comm.getDegreeCode())) {
			hql += " and t.degreeCode = ? ";
			values.add(comm.getDegreeCode());
		}
		if (comm.getCommittee() != null && !"".equals(comm.getCommittee())) {
			hql += " and t.committee = ? ";
			values.add(comm.getCommittee());
		}
		if (comm.getJob() != null && !"".equals(comm.getJob())) {
			hql += " and t.job like ? ";
			values.add(comm.getJob());
		}
		if (comm.getCompanyName() != null && !"".equals(comm.getCompanyName())) {
			hql += " and t.companyName like ? ";
			values.add("%" + comm.getCompanyName() + "%");
		}
		if (comm.getStatus() != null && !comm.getStatus().trim().equals("")) {
			hql += " and t.status = ? ";
			values.add(comm.getStatus());
		}
//		if (comm.getSecondaryCode() != null && !"".equals(comm.getSecondaryCode())) {
//			String s="";
//			for(String t : comm.getSecondaryCode().split(",")){
//				s += " t.secondaryCode like ? or ";
//				values.add("%" + t + "%");
//			}
//			s="(" + s.substring(0,s.length()-3) + ")";
//			hql += " and " + s;
//		}
		
 		if (comm.getSecondaryCode() != null && !"".equals(comm.getSecondaryCode())) {
			String s="";
			for(String t : comm.getSecondaryCode().split(",")){
				s += " t.secondaryCode like ? or ";
				values.add("%" + t + "%");
			}
			s="(" + s.substring(0,s.length()-3) + ")";
			hql += " and " + s;
		}else{
			String secondaryId=((Seco) ActionContext.getContext().getSession().get("sessionSeco")).getSecondaryId();
			hql += " and t.secondaryCode like ? ";
			values.add("%" + secondaryId + "%");
		}
		
		return hql;
	}
	public void save(Comm comm) throws Exception {
		Tcommitteeman t=new Tcommitteeman();
		if(comm.getSecondaryCode().startsWith(","))comm.setSecondaryCode(comm.getSecondaryCode().substring(1));
		BeanUtils.copyProperties(comm,t);
		t.setCode(Generator.getInstance().getCodeNO());
		if(t.getSecondaryCode()!=null)t.setSecondaryCode(t.getSecondaryCode().replace(" ", ""));
		t.setCreateTime(new Date());
		t.setUpdateTime(new Date());
		commDao.save(t);
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_MEMBER);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_ADD);
		dolog.setKeyId(t.getCode());
		dolog.setInfo( "[委员管理]新增委员");
		this.saveLog(dolog);
		this.operUser(t);//同时判断用户是否存在，不存在则保存提案人到用户表，并且状态为启用
	}
	private void operUser(Tcommitteeman t) throws Exception{
		User user=new User();
		user.setUserCode(t.getCode());
		user.setUserGroup(Constants.DIC_TYPE_YHZB_WY);
		user.setCname(t.getTelephone());
		user.setCrealname(t.getName());
		user.setCpwd(t.getTelephone().substring(t.getTelephone().length()-6));
		user.setCid(userService.findUserID(user));
		user.setRoleIds(Constants.TAR_ROLE_CID);	//"提案人"角色ID
		user.setCstatus(t.getStatus());
		if("".equals(user.getCid())){
			userService.save(user);
			Tdolog dolog=new Tdolog();
			dolog.setLogType(Constants.LOG_TYPE_CODE_SYS);
			dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_ADD);
			dolog.setKeyId(user.getCid());
			dolog.setInfo( "[用户]添加新用户");
			this.saveLog(dolog);
		}else{
			userService.update(user);
			Tdolog dolog=new Tdolog();
			dolog.setLogType(Constants.LOG_TYPE_CODE_SYS);
			dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_ADD);
			dolog.setKeyId(user.getCid());
			dolog.setInfo( "[用户]修改用户信息");
			this.saveLog(dolog);
		}
	}
	public Comm update(Comm comm) throws Exception {
		Tcommitteeman t = commDao.get(Tcommitteeman.class, comm.getCode());
		if(comm.getSecondaryCode()!=null){
			if(comm.getSecondaryCode().startsWith(","))comm.setSecondaryCode(comm.getSecondaryCode().substring(1));
		}
		ObjectPropertyUtilsBean.getInstance().copyProperties(t,comm);
		ObjectPropertyUtilsBean.getInstance().copyProperties(comm, t);
		if(t.getSecondaryCode()!=null)t.setSecondaryCode(t.getSecondaryCode().replace(" ", ""));
		t.setUpdateTime(new Date());
		//commDao.update(t);	//Get已经与数据库记录产生映射，可通过set直接进行修改
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_MEMBER);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_EDIT);
		dolog.setKeyId(t.getCode());
		dolog.setInfo( "[委员管理]修改委员信息");
		this.saveLog(dolog);
		this.operUser(t);//同时判断用户是否存在，不存在则保存提案人到用户表，并且状态为启用
		return comm;
	}
	
	public Comm getCommBycode(String code) throws Exception {
		Comm p=new Comm();
		Tcommitteeman t=commDao.get(Tcommitteeman.class,code);
		if(t==null){
			return new Comm();
		}else{
			BeanUtils.copyProperties(t,p);
			p.setSexName(this.findDicName("SEX", t.getSex()));
			p.setNationName(this.findDicName("NATION", t.getNation()));
			p.setGroupName(this.findDicName("GROUP", t.getGroupCode()));
			p.setPartyName(this.findDicName("PARTY", t.getPartyCode()));
			p.setCircleName(this.findDicName("CIRCLE", t.getCircleCode()));
			p.setEduName(this.findDicName("EDUCATION", t.getEduCode()));
			p.setDegreeName(this.findDicName("DEGREE",t.getDegreeCode()));
			p.setVocationName(this.findDicName("VOCATION", t.getVocation()));
			p.setTitleName(this.findDicName("TITLE", t.getTitle()));
			p.setJobName(this.findDicName("JOB", t.getJob()));
			p.setStatusName(this.findDicName("STATUS", t.getStatus()));
			p.setCommitteeName(this.findDicName("COMMITTEE", t.getCommittee()));
			String secondaryName="";				
			if (!"".equals(p.getSecondaryCode()) && null!=p.getSecondaryCode())  {
				for (String id :p.getSecondaryCode().split(",")) {//将届次编号替换成届次名称
					Tsecondary s = secoDao.get(Tsecondary.class, id.trim());
					if (s != null) {
						secondaryName+=s.getSecondayName()+",";
					}
				}
			}
			if(secondaryName.length()>0)secondaryName=secondaryName.substring(0,secondaryName.length()-1);
			p.setSecondaryName(secondaryName);
			return p;
		}		
	}
	public String report(Comm comm) throws Exception {
		String hql=" from Tcommitteeman t where groupCode ='1'";
		List<Object> values = new ArrayList<Object>();
		hql=this.addWhere(comm, hql, values);
		return this.writeExcel(getCommsFromTComms(commDao.find(hql,values)));
	}
	
	/*
	 * 判断对象属性是否为空，用空字符串替换null	
	*/
	public String changeNull(Object o){
		return o==null ? "" : o.toString().trim();
	}
	
	/*
	 * 写入数据Excel文件	
	*/
	public String writeExcel(List<Comm> list)throws Exception{
		
		Properties prop = new Properties();   
    	prop.load(this.getClass().getResourceAsStream("/config.properties")); //读取config.properties中的导出
    	String exportPath=prop.getProperty("exportPath");
        File path=new File(Constants.ROOTPATH + exportPath);
        if(!path.exists())path.mkdir();//如果路径不存在，则创建
        String filePath=exportPath + "/export.xls";
        
        WritableWorkbook book= Workbook.createWorkbook(new File(path ,"export.xls"));
		WritableSheet sheet  =  book.createSheet( "Sheet1 " ,  0 );
		
		sheet.getSettings().setVerticalFreeze(2);  // 设置行冻结前2行   // ss.setHorizontalFreeze(2);  // 设置列冻结 
		WritableCellFormat wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("微软雅黑"), 14 ,WritableFont.BOLD));           
		WritableCellFormat wcf2 = new WritableCellFormat(new WritableFont(WritableFont.createFont("微软雅黑"), 9 ,WritableFont.NO_BOLD));            
		        
		wcf.setAlignment(jxl.format.Alignment.CENTRE);  //平行居中 // wcf2.setBackground(Colour.LIGHT_ORANGE);    
		wcf.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中
		wcf2.setAlignment(jxl.format.Alignment.CENTRE);  //平行居中 
		wcf2.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中 
		
		sheet.mergeCells( 0 , 0 , 26 , 0 ); // 合并单元格  
		jxl.write.Label titleLabel = new Label( 0 , 0 , "提案人明细表",wcf);
		sheet.addCell(titleLabel);           //          将定义好的单元格添加到工作表中 
		sheet.setRowView(0, 500); // 设置第一行的高度  20121111           
		
		int[] headerArrHight = {5,10,10,5,10,20,20,10,10,10,20,20,20,20,15,20,20,40,40,15,40,15,40,20,20,30,30}; //列宽           
		String[] title={"序号","提案人","提案人分组","性别","民族","党派","界别","学历","学位","出生年月","职业","职称","职务","联系人","电子邮箱","手机","固定电话","单位名称","通讯地址","邮编","家庭地址","状态","有效届次","注册时间","更新时间","所属专委会","备注"};
		for(int i=0;i<title.length;i++){
			sheet.addCell(new  Label( i , 1 , title[i], wcf2));
			sheet.setColumnView(i, headerArrHight[i]);
		}
		int row=2;
		for(int i=0;i<list.size();i++){
			sheet.addCell(new  Label( 0 , row , String.valueOf(row-1), wcf2));
			sheet.addCell(new  Label( 1 , row , changeNull(list.get(i).getName())));
			sheet.addCell(new  Label( 2 , row , changeNull(list.get(i).getGroupName()), wcf2));
			sheet.addCell(new  Label( 3 , row , changeNull(list.get(i).getSexName()), wcf2));
			sheet.addCell(new  Label( 4 , row , changeNull(list.get(i).getNationName()), wcf2));
			sheet.addCell(new  Label( 5 , row , changeNull(list.get(i).getPartyName()), wcf2));
			sheet.addCell(new  Label( 6 , row , changeNull(list.get(i).getCircleName()), wcf2));
			sheet.addCell(new  Label( 7 , row , changeNull(list.get(i).getEduName()), wcf2));
			sheet.addCell(new  Label( 8 , row , changeNull(list.get(i).getDegreeName()), wcf2));
			sheet.addCell(new  Label( 9 , row , changeNull(list.get(i).getBirthDate()), wcf2));
			sheet.addCell(new  Label( 10 , row , changeNull(list.get(i).getVocation()), wcf2));
			sheet.addCell(new  Label( 11 , row , changeNull(list.get(i).getTitle()), wcf2));
			sheet.addCell(new  Label( 12 , row , changeNull(list.get(i).getJob()), wcf2));
			sheet.addCell(new  Label( 13 , row , changeNull(list.get(i).getLinkMan()), wcf2));
			sheet.addCell(new  Label( 14 , row , changeNull(list.get(i).getEmail()), wcf2));
			sheet.addCell(new  Label( 15 , row , changeNull(list.get(i).getTelephone()), wcf2));
			sheet.addCell(new  Label( 16 , row , changeNull(list.get(i).getComparyPhone())));
			sheet.addCell(new  Label( 17 , row , changeNull(list.get(i).getCompanyName())));
			sheet.addCell(new  Label( 18 , row , changeNull(list.get(i).getComparyAddress())));
			sheet.addCell(new  Label( 19 , row , changeNull(list.get(i).getComparyPost())));
			sheet.addCell(new  Label( 20 , row , changeNull(list.get(i).getFamilyAddress())));
			sheet.addCell(new  Label( 21 , row , changeNull(list.get(i).getStatusName())));
			sheet.addCell(new  Label( 22 , row , changeNull(list.get(i).getSecondaryName())));
			sheet.addCell(new  Label( 23 , row , changeNull(list.get(i).getCreateTime())));
			sheet.addCell(new  Label( 24 , row , changeNull(list.get(i).getUpdateTime())));
			sheet.addCell(new  Label( 25 , row , changeNull(list.get(i).getCommitteeName())));
			sheet.addCell(new  Label( 26 , row , changeNull(list.get(i).getRemark())));
			row++;
		}
		book.write();
        book.close();	
        return filePath;
	}
	public void change(Comm comm) throws Exception {
		if (!"".equals(comm.getIds()) && null!=comm.getIds()){
			for (String id : comm.getIds().split(",")) {
				Tcommitteeman t = commDao.get(Tcommitteeman.class, id.trim());
				t.setStatus(comm.getStatus());
				if (t != null) {
					commDao.update(t);
					Tdolog dolog=new Tdolog();
					dolog.setLogType(Constants.LOG_TYPE_CODE_MEMBER);
					dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_SET);
					dolog.setKeyId(t.getCode());
					dolog.setInfo( "[委员管理]设置委员状态");
					this.saveLog(dolog);
					this.operUser(t);//同时判断用户是否存在，不存在则保存提案人到用户表，并且状态为启用
				}
			}
		}
	}
	public void resetPass(String code) throws Exception {
		if(code!=null && !"".equals(code)) {
			Tcommitteeman t = commDao.get(Tcommitteeman.class, code.trim());
			if(t!=null && t.getCode()!=null && !"".equals(t.getCode())) {
				String pass=t.getTelephone().substring(t.getTelephone().length()-6);
				Tuser tuser = userService.findByUserCode(t.getCode());
				tuser.setCpwd(Encrypt.e(pass));
				userDao.update(tuser);
				Tdolog dolog=new Tdolog();
				dolog.setLogType(Constants.LOG_TYPE_CODE_SYS);
				dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_EDIT);
				dolog.setKeyId(tuser.getCid());
				dolog.setInfo( "[密码修改]用户名："+tuser.getCname()+",名称："+tuser.getCrealname()+"  重置账号密码");
				this.saveLog(dolog);
			}
		}
	}
	//委员管理首页统计
	public Map<String, Integer> committeeCount(HttpSession httpSession) {
		return null;
	}
	public DataGrid cjdatagrid(Comm comm)
			throws Exception {
		String hql = "from Tcommitteeman t where groupCode =1 and exists( select a.comm.code from Tactivitypeo a where a.astatus='"+Constants.ACT_TYPE_ASTATUS_CX+"' and a.comm.code=t.code group by a.comm.code having count(a.comm.code)>=5 )";
		DataGrid j = new DataGrid();
		j.setRows(getCommsFromTComms(find(hql,comm)));
		j.setTotal(total(hql,comm));
		return j;
	}
	public DataGrid wcjdatagrid(Comm comm) throws Exception {
		String hql = "from Tcommitteeman t where groupCode =1 and exists( select a.comm.code from Tactivitypeo a where a.astatus='"+Constants.ACT_TYPE_ASTATUS_QX+"' and a.comm.code=t.code group by a.comm.code having count(a.comm.code)>=5 )";
		DataGrid j = new DataGrid();
		j.setRows(getCommsFromTComms(find(hql,comm)));
		j.setTotal(total(hql,comm));
		return j;
	}
	public DataGrid cwdatagrid(Comm comm) throws Exception {
		String hql = "from Tcommitteeman t where groupCode =1 and not exists( select a.comm.code from Tactivitypeo a where a.astatus='"+Constants.ACT_TYPE_ASTATUS_CX+"' and a.comm.code=t.code group by a.comm.code having count(a.comm.code) > 0 )";
		DataGrid j = new DataGrid();
		j.setRows(getCommsFromTComms(find(hql,comm)));
		j.setTotal(total(hql,comm));
		return j;
	}
	public DataGrid cxdatagrid(Comm comm) throws Exception {
		String hql = "from Tcommitteeman t where groupCode =1 and exists( select a.tcomm.code from TmeetingMan a where a.status='"+Constants.ACT_TYPE_ASTATUS_CX+"' and a.tcomm.code=t.code group by a.tcomm.code having count(a.tcomm.code)>=5 )";
		DataGrid j = new DataGrid();
		j.setRows(getCommsFromTComms(find(hql,comm)));
		j.setTotal(total(hql,comm));
		return j;
	}
	public DataGrid qxdatagrid(Comm comm) throws Exception {
		String hql = "from Tcommitteeman t where groupCode =1 and exists( select a.tcomm.code from TmeetingMan a where a.status='"+Constants.ACT_TYPE_ASTATUS_QX+"' and a.tcomm.code=t.code group by a.tcomm.code having count(a.tcomm.code)>=5 )";
		DataGrid j = new DataGrid();
		j.setRows(getCommsFromTComms(find(hql,comm)));
		j.setTotal(total(hql,comm));
		return j;
	}
	public DataGrid nodatagrid(Comm comm) throws Exception {
		String hql = "from Tcommitteeman t where groupCode =1 and not exists( select a.tcomm.code from TmeetingMan a where a.status='"+Constants.ACT_TYPE_ASTATUS_CX+"' and a.tcomm.code=t.code group by a.tcomm.code having count(a.tcomm.code) > 0 )";
		DataGrid j = new DataGrid();
		j.setRows(getCommsFromTComms(find(hql,comm)));
		j.setTotal(total(hql,comm));
		return j;
	}
	/**
	 * 
		 * 委员查询
		 * @param  comm
		 * @throws 	Exception
		 * @return 	list
	 */
	public List<Comm> queryCommittee(Comm comm)throws Exception{
		List<Comm> resList=new ArrayList<Comm>();
		String hql = "from Tcommitteeman t where groupCode = 1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(comm, hql, values);
		hql+=" order by t.code";
		resList=this.getCommsFromTComms(commDao.find(hql, values));
		return resList;
	}
	/**
	 * 
		 * 未履职委员统计
		 * @param  comm
		 * @throws 	Exception
		 * @return 	DataGrid
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid unFulfil(Comm comm) throws Exception {
		String secondaryId=((Seco) ActionContext.getContext().getSession().get("sessionSeco")).getSecondaryId();
		if (Util.getInstance().isEmpty(comm.getSecondaryCode())) {
			comm.setSecondaryCode(secondaryId);
		}
		String hql = "select * from tcommitteeman t where t.group_code='"+Constants.DIC_TYPE_YHZB_WY+"' and not exists(select tp.committeeman_code  from tproposaler tp where tp.host_flg='"+Constants.CODE_TYPE_YESNO_YES+"' and exists(select pr.proposal_id from tproposal pr where pr.secondary_id='"+comm.getSecondaryCode()+"' and tp.proposal_id=pr.proposal_id )  and  tp.committeeman_code=t.code group by tp.committeeman_code)"
				+" and not exists(select po.create_man  from (select regexp_substr(tpo.create_man,'[^,]+',1,n) create_man,tpo.discard,tpo.status,tpo.adopt_flg,tpo.secondary_id from tpoll tpo,(select level n from dual connect by level<=10) extend_rows where regexp_substr(tpo.create_man,'[^,]+',1,n) is not null) po"
				+" where po.discard='"+Constants.CODE_TYPE_YESNO_NO+"' and po.status!='"+Constants.CODE_TYPE_POLL_STATUS_YBC+"' and po.adopt_flg='"+Constants.CODE_TYPE_YESNO_YES+"' and po.secondary_id ='"+comm.getSecondaryCode()+"' and po.create_man=t.code group by po.create_man)"
				+" and not exists(select ts.comm_code from tmeet_speak ts where ts.use_situation!='"+Constants.CODE_TYPE_SPEECHADOPTED_STATUS_ZQ+"' and ts.status!='"+Constants.CODE_TYPE_SPEECH_STATUS_YBC+"' and exists (select meet_id from tmeeting me where me.secondary_id='"+comm.getSecondaryCode()+"' and me.meet_id=ts.meet_id) and ts.comm_code=t.code group by ts.comm_code)"
				+" and not exists( select mm.comm_code from tmeeting_man mm left join tmeeting m on m.meet_id=mm.meet_id where mm.status='"+Constants.PRESENT_TYPE_STATUS_CX+"' and m.secondary_id='"+comm.getSecondaryCode()+"' and mm.comm_code=t.code  group by mm.comm_code)"
				+" and not exists(select  ap.comm_code from tactivitypeo ap left join tactivity a on a.aid=ap.aid where ap.astatus='"+Constants.PRESENT_TYPE_STATUS_CX+"'  and a.secondary_id='"+comm.getSecondaryCode()+"' and ap.comm_code=t.code group by ap.comm_code)";
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
			hql += " and t.committee = ='"+comm.getCommittee()+"'";
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
 		List<Object[]> objList=commDao.executeCountSql(hql);
		DataGrid j = new DataGrid();
		j.setRows(getCommsFromTComms(getObjToTComms(objList)));
		return j;
	}
	
	private List<Tcommitteeman> getObjToTComms(List<Object[]> objects) throws Exception{
		List<Tcommitteeman> resList= new ArrayList<Tcommitteeman>();
		for (Iterator<Object[]> it = objects.iterator(); it.hasNext();) {
			Object[] objs = it.next();
			
			Tcommitteeman tc=new Tcommitteeman();
			tc.setCode((String)objs[0]);
			tc.setName((String)objs[1]);
			tc.setPingyin((String)objs[2]);
			tc.setSex((String)objs[3]);
			tc.setNation((String)objs[4]);
			tc.setGroupCode((String)objs[5]);
			tc.setPartyCode((String)objs[6]);
			tc.setCircleCode((String)objs[7]);
			tc.setEduCode((String)objs[8]);
			tc.setDegreeCode((String)objs[9]);
			tc.setVocation((String)objs[10]);
			tc.setTitle((String)objs[11]);
			tc.setEmail((String)objs[12]);
			tc.setBirthDate(Util.getInstance().getFormatDate(Util.getInstance().isEmpty(objs[13])?"":objs[13].toString()));
			tc.setTelephone((String)objs[14]);
			tc.setJob((String)objs[15]);
			tc.setCompanyName((String)objs[16]);
			tc.setComparyAddress((String)objs[17]);
			tc.setComparyPhone((String)objs[18]);
			tc.setComparyPost((String)objs[19]);
			tc.setFamilyAddress((String)objs[20]);
			tc.setFamilyPhone((String)objs[21]);
			tc.setFamilyPost((String)objs[22]);
			tc.setStatus((String)objs[23]);
			tc.setSecondaryCode((String)objs[24]);
			tc.setCreateTime(Util.getInstance().getFormatDate(Util.getInstance().isEmpty(objs[25])?"":objs[25].toString()));
			tc.setUpdateTime(Util.getInstance().getFormatDate(Util.getInstance().isEmpty(objs[26])?"":objs[26].toString()));
			tc.setRemark((String)objs[27]);
			tc.setLinkMan((String)objs[28]);
			tc.setCommittee((String)objs[29]);
			tc.setPicName((String)objs[30]);
			
			resList.add(tc);
		}
		return resList;
	}
}
