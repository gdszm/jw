package com.tlzn.service.sys.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.Tcommitteeman;
import com.tlzn.model.sys.Tdolog;
import com.tlzn.model.sys.Tsecondary;
import com.tlzn.model.sys.Tuser;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.sys.Comm;
import com.tlzn.pageModel.sys.Dic;
import com.tlzn.pageModel.sys.Seco;
import com.tlzn.pageModel.sys.User;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.sys.CommServiceI;
import com.tlzn.service.sys.UserServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Generator;
import com.tlzn.util.base.ObjectPropertyUtilsBean;

@Service("commService")
public class CommServiceImp extends BaseServiceImpl implements CommServiceI {
	
	private BaseDaoI<Tuser> userDao;
	private BaseDaoI<Tcommitteeman> commDao;
	private BaseDaoI<Tsecondary> secoDao;
	private UserServiceI userService;
	protected final Log log = LogFactory.getLog(getClass());
	
	public BaseDaoI<Tuser> getUserDao() {
		return userDao;
	}
	@Autowired
	public void setUserDao(BaseDaoI<Tuser> userDao) {
		this.userDao = userDao;
	}
	
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
	
	public UserServiceI getUserService() {
		return userService;
	}
	@Autowired
	public void setUserService(UserServiceI userService) {
		this.userService = userService;
	}
	
	public void save(Comm comm) throws Exception{
		Tcommitteeman t=new Tcommitteeman();
		if(comm.getSecondaryCode().startsWith(","))comm.setSecondaryCode(comm.getSecondaryCode().substring(1));
		BeanUtils.copyProperties(comm,t);
		t.setCode(Generator.getInstance().getCodeNO());
		if(t.getSecondaryCode()!=null)t.setSecondaryCode(t.getSecondaryCode().replace(" ", ""));
		t.setCreateTime(new Date());
		t.setUpdateTime(new Date());
		commDao.save(t);
		Tdolog dolog=new Tdolog();
		dolog.setLogType(Constants.LOG_TYPE_CODE_SYS);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_ADD);
		dolog.setKeyId(t.getCode());
		dolog.setInfo( "[提案人]新增提案人");
		this.saveLog(dolog);
		this.operUser(t);//同时判断用户是否存在，不存在则保存提案人到用户表，并且状态为启用
	}

	public Comm update(Comm comm) throws Exception{
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
		dolog.setLogType(Constants.LOG_TYPE_CODE_SYS);
		dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_EDIT);
		dolog.setKeyId(t.getCode());
		dolog.setInfo( "[提案人]修改提案人信息");
		this.saveLog(dolog);
		this.operUser(t);//同时判断用户是否存在，不存在则保存提案人到用户表，并且状态为启用
		System.out.println("birthdate==="+comm.getBirthDate());
		return comm;
	}
	
	public void change(Comm comm) throws Exception {
		if (!"".equals(comm.getIds()) && null!=comm.getIds()){
			for (String id : comm.getIds().split(",")) {
				Tcommitteeman t = commDao.get(Tcommitteeman.class, id.trim());
				t.setStatus(comm.getStatus());
				if (t != null) {
					commDao.update(t);
					Tdolog dolog=new Tdolog();
					dolog.setLogType(Constants.LOG_TYPE_CODE_SYS);
					dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_SET);
					dolog.setKeyId(t.getCode());
					dolog.setInfo( "[提案人]设置提案人状态");
					this.saveLog(dolog);
					this.operUser(t);//同时判断用户是否存在，不存在则保存提案人到用户表，并且状态为启用
				}
			}
		}
	}

	private void operUser(Tcommitteeman t) throws Exception{
		Tuser tuser =   userService.findByUserCode(t.getCode());
		if(tuser == null){
			User user=new User();
			user.setUserCode(t.getCode());
			user.setUserGroup(Constants.DIC_TYPE_YHZB_WY);
			user.setCrealname(t.getName());
			if(t.getUsername()!=null && !"".equals(t.getUsername())) {
				user.setCname(t.getUsername().trim());
				user.setCpwd("abc111");
			} 
			user.setCid(userService.findUserID(user));
			user.setRoleIds(Constants.TAR_ROLE_CID);	//"提案人"角色ID
			user.setCstatus(t.getStatus());
			userService.save(user);
			Tdolog dolog=new Tdolog();
			dolog.setLogType(Constants.LOG_TYPE_CODE_SYS);
			dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_ADD);
			dolog.setKeyId(user.getCid());
			dolog.setInfo( "[用户]添加新用户");
			this.saveLog(dolog);
		} else {
			tuser.setUserCode(t.getCode());
			if(t.getUsername()!=null && !"".equals(t.getUsername())) {
				tuser.setCname(t.getUsername().trim());
			} 
			tuser.setUserGroup(Constants.DIC_TYPE_YHZB_WY);
			tuser.setCrealname(t.getName());
			userDao.update(tuser);
			Tdolog dolog=new Tdolog();
			dolog.setLogType(Constants.LOG_TYPE_CODE_SYS);
			dolog.setTitle(Constants.LOG_TYPE_OPERTYPE_ADD);
			dolog.setKeyId(tuser.getCid());
			dolog.setInfo( "[用户]修改用户信息");
			this.saveLog(dolog);
		}
	}
	
	public String report(Comm comm) throws Exception {
		String hql=" from Tcommitteeman t where groupCode !=1";
		List<Object> values = new ArrayList<Object>();
		hql=this.addWhere(comm, hql, values);
		return this.writeExcel(getCommsFromTComms(commDao.find(hql)));
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(Comm comm) throws Exception {
		DataGrid j = new DataGrid();
		j.setRows(getCommsFromTComms(find(comm)));
		j.setTotal(total(comm));
		return j;
	}
	/**
	 *获取当前届次的所有委员
	 */
	public DataGrid getCurSecComm(Comm comm, HttpSession httpSession)throws Exception {
		String secoCode=((Seco)httpSession.getAttribute("sessionSeco")).getSecondaryId();
		DataGrid j = new DataGrid();
		String hql ="from Tcommitteeman t where t.secondaryCode like ? and t.status='"+Constants.CODE_TYPE_QYTY_YES+"'";
		List<Object> values = new ArrayList<Object>();
		values.add("%"+secoCode+"%");
		if (comm.getName() != null && !"".equals(comm.getName())) {
			hql += " and t.name like ?";
			values.add("%" + comm.getName() + "%");
		}
		if(comm.getGroupCode() != null && !"".equals(comm.getGroupCode())){
			hql += " and t.groupCode = ?";
			values.add(comm.getGroupCode());
		}
		hql+="order by t.groupCode,t.code";
		j.setTotal(commDao.count("select count(*)"+hql,values));
		j.setRows(getCommsFromTComms(commDao.find(hql, values,comm.getPage(),comm.getRows())));
		return j;
	}
	/**
	 * 
		 * 根据Code获取提案人信息
		 * 
		 * @param  code
		 * 
		 * @throws 	Exception
		 * 
		 * @return 		comm
	 */
	public Comm getCommBycode(String code)  throws Exception{
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
	 * 查找记录并筛选记录
	 */	
	private List<Tcommitteeman> find(Comm comm) {
		String hql = "from Tcommitteeman t where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(comm, hql, values);
		hql+="order by t.groupCode,t.code";
		return commDao.find(hql, values, comm.getPage(), comm.getRows());
	}
	/*
	 * 查找所选届次提案人信息
	 */
	public List<Comm> findSeco(String secoId){
		String hql = "from Tcommitteeman t where t.secondaryCode like "+"'%"+secoId+"%'"+" order by t.groupCode,t.code";
		try {
			return getCommsFromTComms(commDao.find(hql.toString()));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/*
	 * 查找所选届次外提案人信息
	 */
	public List<Comm> findNOSeco(String secoId){
		String hql = "from Tcommitteeman t where t.secondaryCode not like "+"'%"+secoId+"%'"+" order by t.groupCode,t.code";
		try {
			return getCommsFromTComms(commDao.find(hql.toString()));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/*
	 * 下拉列表
	 * 
	 */
	public List<Dic> combobox()throws Exception{
		String hql = "from Tcommitteeman t ";
		List<Tcommitteeman> list=commDao.find(hql);
		List<Dic> dicList=new ArrayList<Dic>();
		for (Tcommitteeman t : list) {
			Dic d=new Dic();
			d.setCvalue(t.getCode());
			d.setCkey(t.getName());
			dicList.add(d);
		}
		return dicList;
	}
	/*
	 * 统计记录总数
	 */	
	private Long total(Comm comm) {
		String hql = "select count(*) from Tcommitteeman t where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(comm, hql, values);
		return commDao.count(hql, values);
	}
	/*
	 * 组合hql语句
	 */	
	private String addWhere(Comm comm, String hql, List<Object> values) {
		if (comm.getName() != null && !"".equals(comm.getName())) {
			hql += " and t.name like ? ";
			values.add("%" + comm.getName() + "%");
		}
		if (comm.getSex() != null && !"".equals(comm.getSex())) {
			hql += " and t.sex = ? ";
			values.add(comm.getSex());
		}
		if (comm.getTelephone() != null && !"".equals(comm.getTelephone())) {
			hql += " and t.telephone = ? ";
			values.add(comm.getTelephone());
		}
		if (comm.getGroupCode() != null && !"".equals(comm.getGroupCode())) {
			if(comm.getGroupCode().equals("1")){
				hql += " and t.groupCode !=1 ";
			}else{
				hql += " and t.groupCode = ? ";
				values.add(comm.getGroupCode());
			}
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
		if (comm.getSecondaryCode() != null && !"".equals(comm.getSecondaryCode())) {
			String s="";
			for(String t : comm.getSecondaryCode().split(",")){
				s += " t.secondaryCode like ? or ";
				values.add("%" + t + "%");
			}
			s="(" + s.substring(0,s.length()-3) + ")";
			hql += " and " + s;
		}
		return hql;
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

	/**
	 * 获取可选委员（委员选择弹出框用）（除去已有的）
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid selectDatagrid(Comm comm) throws Exception {
		
		String secoCode=((Seco)ServletActionContext.getContext().getSession().get("sessionSeco")).getSecondaryId();
		DataGrid j = new DataGrid();
		//委员
		String groupCode=Constants.DIC_TYPE_GROUP_WY;
		String hql ="from Tcommitteeman t where t.groupCode='"+groupCode+"' and t.secondaryCode like '%"+secoCode+"%'";
		List<Object> values = new ArrayList<Object>();
		if (comm.getName() != null && !"".equals(comm.getName())) {
			hql += " and t.name like '%"+comm.getName()+"%'";
		}
		if(comm.getGroupCode() != null && !"".equals(comm.getGroupCode())){
			hql += " and t.groupCode = '"+comm.getGroupCode()+"'";
		}
		if(comm.getGid()!= null && !"".equals(comm.getGid())){
			hql += " and exists (from Tusergroup t2 where t.code=t2.tcomm.code and t2.tgroup.gid ='"+comm.getGid()+"') ";
		}
		
		
		if(comm.getIds()!= null && !"".equals(comm.getIds())){
			String ids[]=comm.getIds().split(",");
			String idsString="";
			for (String id : ids) {
				if("".equals(idsString)) {
					idsString=idsString+"'"+id+"'";
				} else {
					idsString=idsString+",'"+id+"'";
				}
			}
			hql += " and t.code not in("+idsString+")";
		}
		hql+=" order by t.groupCode,t.code";
		List<Comm> list =canSelectEdit(commDao.find(hql, values,comm.getPage(),comm.getRows()));
		
		//System.out.println("list_user_long===="+list.size());
		j.setRows(list);
		j.setTotal(commDao.count("select count(*)"+hql,values));
		return j;
	}
	/*
	 * 从实体对象列表中复制出新建自造对象
	 */
	private List<Comm> canSelectEdit(List<Tcommitteeman> Tcomms) throws Exception {
		List<Comm> ads = new ArrayList<Comm>();
		if (Tcomms != null && Tcomms.size() > 0) {
			for (Tcommitteeman t : Tcomms) {
				Comm p = new Comm();
				BeanUtils.copyProperties(t,p);
				p.setGroupName(this.findDicName("GROUP", t.getGroupCode()));
				p.setCircleName(this.findDicName("CIRCLE", t.getCircleCode()));
				p.setCommitteeName(this.findDicName("COMMITTEE", t.getCommittee()));
				ads.add(p);
			}
		}
		return ads;
	}
	
	/**
	 * 获取选中委员（委员选择弹出框用）（除去已有的）
	 */
	@Override
	public DataGrid getComms(Comm comm) throws Exception {
		DataGrid j = new DataGrid();
		List<Tcommitteeman> cList=new ArrayList<Tcommitteeman>();
		if (comm.getIds() != null) {
			for (String id : comm.getIds().split(",")) {
				Tcommitteeman c = commDao.get(Tcommitteeman.class, id.trim());
				if (c != null) {
					cList.add(c);
				}
			}
		}
		
		j.setRows(getCommsFromTComms(cList));
		return j;
	}
}
