package com.tlzn.pageModel.eduManage;

import java.util.Date;

import com.tlzn.model.base.BaseObject;

/*
 * 学生类Ok
 */
public class Stu extends BaseObject implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String stuNo; //学号
	private String dept; //院、系、部(编码)
	private String major; //专业(编码)
	private Date inTime; //入学时间
	private String enSour; //入学来源 （1：城镇 2:农村)
	private String priPro; //主要问题（ 1） 学习困难（ 2） 行为有偏差（ 3） 心理有障碍（ 4 ） 其他（单亲家庭 父、母）（ 5） 重组家庭（随父、随母）（6） 挂靠、托靠
	private String planAfterGrad;//毕业以后的打算（考研、出国、入伍、公务员、工作<国、私、外>、其他）
	private String remark; //备注
	private Date crtTime; //创建时间
	private Date uptTime; //更新时间
	private String crtUsr; //创建用户
	private String uptUsr; //更新用户
	
	// 班级
	private String className;// 班级名称
	
	//档案
	private String archNo; // 档案编号
	private String urgentTel;  //紧急情况下联系号码
	private String gradSchool; //毕业学校
	private String selfAppr ;  //自我评价

	// 基本信息
	private String name;       //姓名
	private String nickName;   //曾用名
	private String sex;        //性别(代码)   
	private String nation;     //民族 (代码)
	private String political;  //政治面貌(代码)
	private String nativePlace;//籍贯  
	private String birthPlace; //出生地
	private Date birthDate;    //出生日期
	private String resPlace;   //户口所在地
	private String resType;    //户口性质（1：城镇 2：农村）
	private String nowAdd;     //现在住址
	private String picName;     //照片
	private Integer height;    //身高
	private Integer weight;     //体重
	private String health;     //健康状况(代码)
	private String mobilePhone;//手机
	private String tel;        //固定电话
	private String qq; 		   //QQ
	private String identCardNo;//身份证号码
	private String email;      //E-mail
	private String special;    //特长
	private String compuLevel; //计算机水平(1:优　2:良　3:差)
	private String foreignType;//所学外语语种(1:英　2:日　3:俄)
	private String interestAndHobby;//兴趣爱好
	
	//家庭信息
	private Integer peoNum;  //家庭人口数
	private String addr;     //详细通讯地址
	private String ecoStatus;//家庭经济状况  特困（ 1 ）  困难（2   ） 一般 （3） 富裕 （3） 
	private String ecoFrom;  //主要经济来源
	private Integer ecoTotal; //总收入（每年）
	private String ecoPay;   //主要支出
	private String teachBack;//家庭教育背景 良好（ 1 ）     一般（  2 ）     不良（3  ）
	private String introduce;//家庭情况介绍
	private String basicLive;//是否有低保证 没有(0) 有(1)  
	private String helping;  //是否享受过补助 没有(0) 有(1)  
	private String specialStatus;//家庭成员如果有特殊情况（疾病、残疾、单亲等）必须进行说明，家庭如果困难，说明困难情况）

	
// 非数据库
	private String basInfoPersId;   //基本信息id
	private String familyId;        //家庭信息id
	private String familymemId;     //家庭成员ids
	private String awardPenaltyIds; //奖惩记录ids
	private String eduExpIds;       //教育经历ids
	private String workExpIds;      //工作经历ids
	
	private String basInfoPersTel;  //基本信息固定电话
	private String familyTel;      //家庭信息联系电话

	
	private Date basInfoPersCrtTime; //基本信息创建时间
	private Date basInfoPersUptTime; //基本信息更新时间
	
	private Date familyCrtTime; //家庭信息创建时间
	private Date familyUptTime; //家庭信息更新时间
	
	private String sexName;       //性别
	private String nationName;    //民族
	private String politicalName; //政治面貌
	private String healthName;    //健康状况(代码)
	private String foreignTypeName;//所学外语语种(1:英　2:日　3:俄)

	//班级
	private String classId;// 班级Id
	private Date inSTime; // 入学开始时间
	private Date inETime; // 入学结束时间
	private String deptName; // 院、系、部(编码)
	private String majorName; // 专业(编码)
	private Date inTimeString; // 入学时间
	private String enSourName; // 入学来源 （1：城镇 2:农村)
	private String priProName; // 主要问题（ 1） 学习困难（ 2） 行为有偏差（ 3） 心理有障碍（ 4 ） 其他（单亲家庭 父、母）（ 5） 重组家庭（随父、随母）（6） 挂靠、托靠

	//档案
	private Date archUptTime; // 档案更新时间
	private String archRemark; //备注
	
	public Stu() {
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getEnSour() {
		return enSour;
	}

	public void setEnSour(String enSour) {
		this.enSour = enSour;
	}

	public String getPriPro() {
		return priPro;
	}

	public void setPriPro(String priPro) {
		this.priPro = priPro;
	}

	public String getPlanAfterGrad() {
		return planAfterGrad;
	}

	public void setPlanAfterGrad(String planAfterGrad) {
		this.planAfterGrad = planAfterGrad;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public String getIdentCardNo() {
		return identCardNo;
	}

	public void setIdentCardNo(String identCardNo) {
		this.identCardNo = identCardNo;
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

	public String getStuNo() {
		return stuNo;
	}

	public void setStuNo(String stuNo) {
		this.stuNo = stuNo;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getArchNo() {
		return archNo;
	}

	public void setArchNo(String archNo) {
		this.archNo = archNo;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
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

	public Date getInTime() {
		return inTime;
	}

	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}

	public Date getInSTime() {
		return inSTime;
	}

	public void setInSTime(Date inSTime) {
		this.inSTime = inSTime;
	}

	public Date getInETime() {
		return inETime;
	}

	public void setInETime(Date inETime) {
		this.inETime = inETime;
	}

	public String getSexName() {
		return sexName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}

	public Date getArchUptTime() {
		return archUptTime;
	}

	public void setArchUptTime(Date archUptTime) {
		this.archUptTime = archUptTime;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public Date getInTimeString() {
		return inTimeString;
	}

	public void setInTimeString(Date inTimeString) {
		this.inTimeString = inTimeString;
	}

	public String getEnSourName() {
		return enSourName;
	}

	public void setEnSourName(String enSourName) {
		this.enSourName = enSourName;
	}

	public String getPriProName() {
		return priProName;
	}

	public void setPriProName(String priProName) {
		this.priProName = priProName;
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

	public String getArchRemark() {
		return archRemark;
	}

	public void setArchRemark(String archRemark) {
		this.archRemark = archRemark;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
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

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getBasInfoPersId() {
		return basInfoPersId;
	}

	public void setBasInfoPersId(String basInfoPersId) {
		this.basInfoPersId = basInfoPersId;
	}

	public String getFamilyId() {
		return familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}

	public String getAwardPenaltyIds() {
		return awardPenaltyIds;
	}

	public void setAwardPenaltyIds(String awardPenaltyIds) {
		this.awardPenaltyIds = awardPenaltyIds;
	}

	public String getEduExpIds() {
		return eduExpIds;
	}

	public void setEduExpIds(String eduExpIds) {
		this.eduExpIds = eduExpIds;
	}

	public String getWorkExpIds() {
		return workExpIds;
	}

	public void setWorkExpIds(String workExpIds) {
		this.workExpIds = workExpIds;
	}

	public String getBasInfoPersTel() {
		return basInfoPersTel;
	}

	public void setBasInfoPersTel(String basInfoPersTel) {
		this.basInfoPersTel = basInfoPersTel;
	}

	public String getFamilyTel() {
		return familyTel;
	}

	public void setFamilyTel(String familyTel) {
		this.familyTel = familyTel;
	}

	public Date getBasInfoPersCrtTime() {
		return basInfoPersCrtTime;
	}

	public void setBasInfoPersCrtTime(Date basInfoPersCrtTime) {
		this.basInfoPersCrtTime = basInfoPersCrtTime;
	}

	public Date getBasInfoPersUptTime() {
		return basInfoPersUptTime;
	}

	public void setBasInfoPersUptTime(Date basInfoPersUptTime) {
		this.basInfoPersUptTime = basInfoPersUptTime;
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

	public String getFamilymemId() {
		return familymemId;
	}

	public void setFamilymemId(String familymemId) {
		this.familymemId = familymemId;
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

	public String getHealthName() {
		return healthName;
	}

	public void setHealthName(String healthName) {
		this.healthName = healthName;
	}

	public String getForeignTypeName() {
		return foreignTypeName;
	}

	public void setForeignTypeName(String foreignTypeName) {
		this.foreignTypeName = foreignTypeName;
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
