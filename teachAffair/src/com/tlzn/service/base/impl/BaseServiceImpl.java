package com.tlzn.service.base.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.Tcommitteeman;
import com.tlzn.model.eduManage.TclassroomType;
import com.tlzn.model.sys.TbaseSequence;
import com.tlzn.model.sys.Tdept;
import com.tlzn.model.sys.Tdic;
import com.tlzn.model.sys.Tdolog;
import com.tlzn.model.sys.Tsecondary;
import com.tlzn.model.sys.Tuser;
import com.tlzn.pageModel.sys.Dolog;
import com.tlzn.service.base.BaseServiceI;
import com.tlzn.util.base.Generator;
import com.tlzn.util.base.Util;

/**
 * 基础Service,所有ServiceImpl需要extends此Service来获得默认事务的控制
 * 
 * @author 刘平
 * 
 */
@Service("baseService")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class BaseServiceImpl implements BaseServiceI {
	private BaseDaoI<TbaseSequence> sequDao;
	private BaseDaoI<Tdolog> dologDao;
	private BaseDaoI<Tdic> dicDao;
	private BaseDaoI<Tuser> userDao;
	private BaseDaoI<Tcommitteeman> commDao;
	private BaseDaoI<Tdept> deptDao;
	private BaseDaoI<Tsecondary> secoDao;
	private BaseDaoI<TclassroomType> classroomTypeDao;
	public BaseDaoI<TbaseSequence> getSequDao() {
		return sequDao;
	}
	
	@Autowired
	public void setSequDao(BaseDaoI<TbaseSequence> sequDao) {
		this.sequDao = sequDao;
	}

	public BaseDaoI<Tdolog> getDologDao() {
		return dologDao;
	}
	
	@Autowired
	public void setDologDao(BaseDaoI<Tdolog> dologDao) {
		this.dologDao = dologDao;
	}
	
	public BaseDaoI<Tdic> getDicDao() {
		return dicDao;
	}
	
	@Autowired
	public void setDicDao(BaseDaoI<Tdic> dicDao) {
		this.dicDao = dicDao;
	}
	
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
	public BaseDaoI<Tdept> getDeptDao() {
		return deptDao;
	}
	@Autowired
	public void setDeptDao(BaseDaoI<Tdept> deptDao) {
		this.deptDao = deptDao;
	}
	public BaseDaoI<Tsecondary> getSecoDao() {
		return secoDao;
	}
	@Autowired
	public void setSecoDao(BaseDaoI<Tsecondary> secoDao) {
		this.secoDao = secoDao;
	}
	public BaseDaoI<TclassroomType> getClassroomTypeDao() {
		return classroomTypeDao;
	}
	@Autowired
	public void setClassroomTypeDao(BaseDaoI<TclassroomType> classroomTypeDao) {
		this.classroomTypeDao = classroomTypeDao;
	}
	public String CreSequence(String seqfield){
		String genseq="";
		if(seqfield!=null && !"".equals(seqfield)){
			String hql="from TbaseSequence SE where SE.seqfield='"+seqfield.trim()+"'";
			List<TbaseSequence> list=sequDao.find(hql);
			if(list!=null){
				TbaseSequence seqpo=list.get(0);
				if(seqpo.getNownum()!=0)
				{
					genseq=seqpo.getNownum()+"";
				}
				if(seqpo.getAddnum()>0)
				{
				 seqpo.setNownum(seqpo.getNownum()+seqpo.getAddnum());
				}
				else {
					seqpo.setNownum(seqpo.getNownum()+1);//默认情况下累加1
				}
				sequDao.update(seqpo);
			}
		}
		return genseq;
	}

	/**
	 * 日志生成
	 * @param title,coments
	 * @return void
	 */
	public synchronized void saveLog(Tdolog dolog)throws Exception{
			try {
				dolog.setLogCode(Generator.getInstance().getDoLogNO());
				dolog.setIp(Util.getInstance().getIpAddr());
				dolog.setOperator(Util.getInstance().getOperator());
				dolog.setOperateTime(new Date());
			} catch (Exception error) {
				error.printStackTrace();
				throw new Exception("生成日志时程序出错：" + error.getMessage()+ "重试无效后请联系管理员！");
			}
			dologDao.save(dolog);
	}
	/*
	 * 根据转入的数值，查找对应的汉字名称	
	*/
	public String findDicName(String type,String value){
		String name="";
		if(value==null)return name;
		List<Tdic> list=dicDao.find("from Tdic t where t.ctype='" + type + "' order by CVALUE");
		if(list!=null){
			for(Tdic s : list){
				if(value.equals(s.getCvalue())){
					name=s.getCkey();
					return name;
				}
			}
		}
		return name;
	}
	/*
	 * 操作人员名称获取	
	*/
	public String findNameByUserid(String userids){
		String name="";
		if("".equals(userids)||userids==null)return name;
		for (String  id : userids.split(",")) {
			if (!"".equals(id)&&id!=null) {
				Tuser user=userDao.get(Tuser.class,id.trim());
				if(user!=null){
					name+=user.getCrealname()+"、";
				}
			}
		}
		if(name.indexOf("、")>=0){
			name=name.substring(0,name.length()-1);
		}
		return name;
	}
	
	/*
	 * 提交人员名称获取	
	*/
	public String findNameByCommCode(String commCode){
		String name="";
		if("".equals(commCode)||commCode==null)return name;
		for (String  id : commCode.split(",")) {
			if (!"".equals(id)&&id!=null) {
				Tcommitteeman comm=commDao.get(Tcommitteeman.class,id.trim());
				if(comm!=null){
					name+=comm.getName()+"、";
				}else{
					name+=id+"、";
				}
			}
		}
		if(name.indexOf("、")>=0){
			name=name.substring(0,name.length()-1);
		}
		System.out.println("name========"+name);
		return name;
	}
	
	/*
	 * 增删改数据库时，日志处理
	*/
	public void saveLog(String logType,String title,String keyId,String info) throws Exception{
		Tdolog dolog = new Tdolog();
		dolog.setLogType(logType);
		dolog.setTitle(title);
		dolog.setKeyId(keyId);
		dolog.setInfo(info);
		this.saveLog(dolog);
	}
	
	/*
	 * 根据部门id获取部门名称
	*/
	public String findNameByDepid(String depids){
		String name="";
		if("".equals(depids)||depids==null)return name;
		Tdept dep=deptDao.get(Tdept.class,depids);
		if(dep!=null){
			name+=dep.getCname();
		}
		return name;
	}
	
	/*
	 * 根据界次ID获取界次名称
	*/
	public String findNameBySecondaryid(String secondaryCode){
		String secondaryName="";				
		if (!"".equals(secondaryCode)&& null!=secondaryCode)  {
			for (String id :secondaryCode.split(",")) {//将届次编号替换成届次名称
				Tsecondary s = secoDao.get(Tsecondary.class, id.trim());
				if (s != null) {
					secondaryName+=s.getSecondayName()+",";
				}
			}
		}
		if(secondaryName.length()>0)secondaryName=secondaryName.substring(0,secondaryName.length()-1);
		return secondaryName;
	}

	/*
	 * 根据界次ID获取界次名称
	*/
	public String findYearById(String secondaryCode){
		String Year="";				
		if (!"".equals(secondaryCode)&& null!=secondaryCode)  {
			for (String id :secondaryCode.split(",")) {//将届次编号替换成届次名称
				Tsecondary s = secoDao.get(Tsecondary.class, id.trim());
				if (s != null) {
					Year+=s.getYear()+",";
				}
			}
		}
		if(Year.length()>0)Year=Year.substring(0,Year.length()-1);
		return Year;
	}
	
	/*
	 * 教室类型ID取教室类型名称
	*/
	public String findNameByClassroomTypeId(String classroomTypeId){
		String name="";
		if("".equals(classroomTypeId)||classroomTypeId==null)return name;
		TclassroomType classroomType=classroomTypeDao.get(TclassroomType.class,classroomTypeId);
		if(classroomType!=null){
			name+=classroomType.getTypeName();
		}
		return name;
	}
}
