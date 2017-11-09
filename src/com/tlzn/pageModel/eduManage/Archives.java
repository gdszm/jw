package com.tlzn.pageModel.eduManage;

import java.util.Date;

import com.tlzn.model.base.BaseObject;
/*
 * 档案OK
 */
public class Archives extends BaseObject implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	//基本信息
	private String archNo;     //档案编号
	private String urgentTel;  //紧急情况下联系号码
	private String gradSchool; //毕业学校
	private String selfAppr ;  //自我评价
	private String remark;     //备注
	private String certFiles;  //证明材料（列表）
	private String certInstrs;  //证明材料说明（列表）
	private Date crtTime; //创建时间
	private Date uptTime; //更新时间
	private String crtUsr; //创建用户
	private String uptUsr; //更新用户
	private String status; //发布状态（0：否 1：是）
	//人员基本信息
//	private String id;
	private String baseInfoId;
	private String name;       //姓名
	private String nickName;   //曾用名
	private String sex;       //性别(代码)   
	private String nation;    //民族 (代码)
	private String political; //政治面貌(代码)
	private String nativePlace;//籍贯  
	private String birthPlace; //出生地
	private Date birthDate; //出生日期
	private String resPlace;   //户口所在地
	private String resType;    //户口性质（1：城镇 2：农村）
	private String nowAdd;     //现在住址
	private String picName;     //照片
	private Integer height = 0;    //身高
	private Integer weight = 0;     //体重
	private String health;     //健康状况(代码)
	private String mobilePhone;//手机
//	private String tel;        //固定电话
	private String parPhone; //家长手机
	private String qq; 		   //QQ
	private String identCardNo;//身份证号码
	private String email;      //E-mail
	private String special;    //特长
	private String compuLevel; //计算机水平(1:优　2:良　3:差)
	private String foreignType;//所学外语语种(1:英　2:日　3:俄)
	private String interestAndHobby;//兴趣爱好
//	private Date crtTime; //创建时间
//	private Date uptTime; //更新时间
	private Date baseInfoCrtTime; //创建时间
	private Date baseInfoUptTime; //更新时间
	
	//家庭信息
	private String id;       //家庭编号
	private Integer peoNum = 0;  //家庭人口数
	private String addr;     //详细通讯地址
	private String tel;      //联系电话
	private String family; //联系电话
	private String ecoStatus;//家庭经济状况  特困（ 1 ）  困难（2   ） 一般 （3） 富裕 （3） 
	private String ecoFrom;  //主要经济来源
	private Integer ecoTotal = 0; //总收入（每年）
	private String ecoPay;   //主要支出
	private String teachBack;//家庭教育背景 良好（ 1 ）     一般（  2 ）     不良（3  ）
	private String introduce;//家庭情况介绍(文本)
	private String basicLive;//是否有低保证 没有(0) 有(1)  
	private String helping;  //是否享受过补助 没有(0) 有(1)  
	private String specialStatus;//家庭成员如果有特殊情况（疾病、残疾、单亲等）必须进行说明，家庭如果困难，说明困难情况）(文本)
//	private Date crtTime; //创建时间
//	private Date uptTime; //更新时间
	private Date familyCrtTime; //创建时间
	private Date familyUptTime; //更新时间
	
	//非数据库
	private Date crtSTime; //创建开始时间
	private Date crtETime; //创建结束时间
	private Date uptSTime; //更新开始时间
	private Date uptETime; //更新结束时间
	private String sexName;        //性别名称
	private String nationName;     //民族 名称
	private String politicalName;  //政治面貌(代码)
	private String resTypeName;    //户口性质（1：城镇 2：农村）
	private String healthName;     //健康状况(代码)
	private String compuLevelName; //计算机水平(1:优　2:良　3:差)
	private String foreignTypeName;//所学外语语种(1:英　2:日　3:俄)
	
	private String ecoStatusName;//家庭经济状况  特困（ 1 ）  困难（2   ） 一般 （3） 富裕 （3） 
	private String teachBackName;//家庭教育背景 良好（ 1 ）     一般（  2 ）     不良（3  ）
	private String basicLiveName;//是否有低保证 没有(0) 有(1)  
	private String helpingName;  //是否享受过补助 没有(0) 有(1)  

	private String statusName; //废弃标识（0：否 1：是）
	
	private String fmIds;//家庭编号列表
	private String eeIds;//奖惩记录列表
	private String apIds;//教育经历列表
	private String weIds;//工作经历列表
	
	private String selectNotExist; //查询没有分配给学生的档案（业务用）
	
	public Archives() {
	}

	public String getArchNo() {
		return archNo;
	}

	public void setArchNo(String archNo) {
		this.archNo = archNo;
	}

	public String getUrgentTel() {
		return urgentTel;
	}

	public void setUrgentTel(String urgentTel) {
		this.urgentTel = urgentTel;
	}

	public String getGradSchool() {
		return gradSchool;
	}

	public void setGradSchool(String gradSchool) {
		this.gradSchool = gradSchool;
	}

	public String getSelfAppr() {
		return selfAppr;
	}

	public void setSelfAppr(String selfAppr) {
		this.selfAppr = selfAppr;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

	public Date getUptTime() {
		return uptTime;
	}

	public void setUptTime(Date uptTime) {
		this.uptTime = uptTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getPeoNum() {
		return peoNum;
	}

	public void setPeoNum(Integer peoNum) {
		this.peoNum = peoNum;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public String getEcoStatus() {
		return ecoStatus;
	}

	public void setEcoStatus(String ecoStatus) {
		this.ecoStatus = ecoStatus;
	}

	public String getEcoFrom() {
		return ecoFrom;
	}

	public void setEcoFrom(String ecoFrom) {
		this.ecoFrom = ecoFrom;
	}

	public Integer getEcoTotal() {
		return ecoTotal;
	}

	public void setEcoTotal(Integer ecoTotal) {
		this.ecoTotal = ecoTotal;
	}

	public String getEcoPay() {
		return ecoPay;
	}

	public void setEcoPay(String ecoPay) {
		this.ecoPay = ecoPay;
	}

	public String getTeachBack() {
		return teachBack;
	}

	public void setTeachBack(String teachBack) {
		this.teachBack = teachBack;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getBasicLive() {
		return basicLive;
	}

	public void setBasicLive(String basicLive) {
		this.basicLive = basicLive;
	}

	public String getHelping() {
		return helping;
	}

	public void setHelping(String helping) {
		this.helping = helping;
	}

	public String getSpecialStatus() {
		return specialStatus;
	}

	public void setSpecialStatus(String specialStatus) {
		this.specialStatus = specialStatus;
	}

	public Date getFamilyCrtTime() {
		return familyCrtTime;
	}

	public void setFamilyCrtTime(Date familyCrtTime) {
		this.familyCrtTime = familyCrtTime;
	}

	public Date getFamilyUptTime() {
		return familyUptTime;
	}

	public void setFamilyUptTime(Date familyUptTime) {
		this.familyUptTime = familyUptTime;
	}

	public String getBaseInfoId() {
		return baseInfoId;
	}

	public void setBaseInfoId(String baseInfoId) {
		this.baseInfoId = baseInfoId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getPolitical() {
		return political;
	}

	public void setPolitical(String political) {
		this.political = political;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getResPlace() {
		return resPlace;
	}

	public void setResPlace(String resPlace) {
		this.resPlace = resPlace;
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public String getNowAdd() {
		return nowAdd;
	}

	public void setNowAdd(String nowAdd) {
		this.nowAdd = nowAdd;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getHealth() {
		return health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getParPhone() {
		return parPhone;
	}

	public void setParPhone(String parPhone) {
		this.parPhone = parPhone;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getIdentCardNo() {
		return identCardNo;
	}

	public void setIdentCardNo(String identCardNo) {
		this.identCardNo = identCardNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}

	public String getCompuLevel() {
		return compuLevel;
	}

	public void setCompuLevel(String compuLevel) {
		this.compuLevel = compuLevel;
	}

	public String getForeignType() {
		return foreignType;
	}

	public void setForeignType(String foreignType) {
		this.foreignType = foreignType;
	}

	public String getInterestAndHobby() {
		return interestAndHobby;
	}

	public void setInterestAndHobby(String interestAndHobby) {
		this.interestAndHobby = interestAndHobby;
	}

	public Date getBaseInfoCrtTime() {
		return baseInfoCrtTime;
	}

	public void setBaseInfoCrtTime(Date baseInfoCrtTime) {
		this.baseInfoCrtTime = baseInfoCrtTime;
	}

	public Date getBaseInfoUptTime() {
		return baseInfoUptTime;
	}

	public void setBaseInfoUptTime(Date baseInfoUptTime) {
		this.baseInfoUptTime = baseInfoUptTime;
	}

	public Date getCrtSTime() {
		return crtSTime;
	}

	public void setCrtSTime(Date crtSTime) {
		this.crtSTime = crtSTime;
	}

	public Date getCrtETime() {
		return crtETime;
	}

	public void setCrtETime(Date crtETime) {
		this.crtETime = crtETime;
	}

	public String getSexName() {
		return sexName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}

	public String getFmIds() {
		return fmIds;
	}

	public void setFmIds(String fmIds) {
		this.fmIds = fmIds;
	}

	public String getWeIds() {
		return weIds;
	}

	public void setWeIds(String weIds) {
		this.weIds = weIds;
	}

	public String getEeIds() {
		return eeIds;
	}

	public void setEeIds(String eeIds) {
		this.eeIds = eeIds;
	}

	public String getApIds() {
		return apIds;
	}

	public void setApIds(String apIds) {
		this.apIds = apIds;
	}

	public Date getUptSTime() {
		return uptSTime;
	}

	public void setUptSTime(Date uptSTime) {
		this.uptSTime = uptSTime;
	}

	public Date getUptETime() {
		return uptETime;
	}

	public void setUptETime(Date uptETime) {
		this.uptETime = uptETime;
	}

	public String getNationName() {
		return nationName;
	}

	public void setNationName(String nationName) {
		this.nationName = nationName;
	}

	public String getPoliticalName() {
		return politicalName;
	}

	public void setPoliticalName(String politicalName) {
		this.politicalName = politicalName;
	}

	public String getResTypeName() {
		return resTypeName;
	}

	public void setResTypeName(String resTypeName) {
		this.resTypeName = resTypeName;
	}

	public String getHealthName() {
		return healthName;
	}

	public void setHealthName(String healthName) {
		this.healthName = healthName;
	}

	public String getCompuLevelName() {
		return compuLevelName;
	}

	public void setCompuLevelName(String compuLevelName) {
		this.compuLevelName = compuLevelName;
	}

	public String getForeignTypeName() {
		return foreignTypeName;
	}

	public void setForeignTypeName(String foreignTypeName) {
		this.foreignTypeName = foreignTypeName;
	}

	public String getEcoStatusName() {
		return ecoStatusName;
	}

	public void setEcoStatusName(String ecoStatusName) {
		this.ecoStatusName = ecoStatusName;
	}

	public String getTeachBackName() {
		return teachBackName;
	}

	public void setTeachBackName(String teachBackName) {
		this.teachBackName = teachBackName;
	}

	public String getBasicLiveName() {
		return basicLiveName;
	}

	public void setBasicLiveName(String basicLiveName) {
		this.basicLiveName = basicLiveName;
	}

	public String getHelpingName() {
		return helpingName;
	}

	public void setHelpingName(String helpingName) {
		this.helpingName = helpingName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getSelectNotExist() {
		return selectNotExist;
	}

	public void setSelectNotExist(String selectNotExist) {
		this.selectNotExist = selectNotExist;
	}

	public String getCertFiles() {
		return certFiles;
	}

	public void setCertFiles(String certFiles) {
		this.certFiles = certFiles;
	}

	public String getCertInstrs() {
		return certInstrs;
	}

	public void setCertInstrs(String certInstrs) {
		this.certInstrs = certInstrs;
	}
	public String getCrtUsr() {
		return crtUsr;
	}

	public void setCrtUsr(String crtUsr) {
		this.crtUsr = crtUsr;
	}

	public String getUptUsr() {
		return uptUsr;
	}

	public void setUptUsr(String uptUsr) {
		this.uptUsr = uptUsr;
	}
	
}
