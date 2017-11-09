package com.tlzn.model.eduManage;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tlzn.model.base.BaseObject;
/*
 *	人员基本信息OK
 */
@Entity
@Table(name = "basinfopers", catalog = "artcollege")
public class TbasInfoPers extends BaseObject implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	
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
	private Integer height;    //身高
	private Integer weight;     //体重
	private String health;     //健康状况(代码)
	private String mobilePhone;//手机
	private String parPhone;   //家长手机
	private String qq; 		   //QQ
	private String identCardNo;//身份证号码
	private String email;      //E-mail
	private String special;    //特长
	private String compuLevel; //计算机水平(1:优　2:良　3:差)
	private String foreignType;//所学外语语种(1:英　2:日　3:俄)
	private String interestAndHobby;//兴趣爱好
	private Date crtTime; //创建时间
	private Date uptTime; //更新时间
	
	private Tarchives archives;
	
	public TbasInfoPers() {
	}
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 16)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "name", length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "nickName", length = 45)
	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Column(name = "sex", length = 10)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "nation", length = 10)
	public String getNation() {
		return this.nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	@Column(name = "political", length = 10)
	public String getPolitical() {
		return this.political;
	}

	public void setPolitical(String political) {
		this.political = political;
	}

	@Column(name = "nativePlace", length = 45)
	public String getNativePlace() {
		return this.nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	@Column(name = "birthPlace", length = 45)
	public String getBirthPlace() {
		return this.birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	@Column(name = "resPlace", length = 200)
	public String getResPlace() {
		return this.resPlace;
	}

	public void setResPlace(String resPlace) {
		this.resPlace = resPlace;
	}

	@Column(name = "resType", length = 10)
	public String getResType() {
		return this.resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	@Column(name = "nowAdd", length = 200)
	public String getNowAdd() {
		return this.nowAdd;
	}

	public void setNowAdd(String nowAdd) {
		this.nowAdd = nowAdd;
	}

	@Column(name = "picName", length = 500)
	public String getPicName() {
		return this.picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	@Column(name = "height")
	public Integer getHeight() {
		return this.height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	@Column(name = "weight")
	public Integer getWeight() {
		return this.weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	@Column(name = "health", length = 10)
	public String getHealth() {
		return this.health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	@Column(name = "mobilePhone", length = 45)
	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	@Column(name = "parPhone", length = 45)
	public String getParPhone() {
		return this.parPhone;
	}

	public void setParPhone(String parPhone) {
		this.parPhone = parPhone;
	}

	@Column(name = "qq", length = 45)
	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@Column(name = "identCardNo", length = 60)
	public String getIdentCardNo() {
		return this.identCardNo;
	}

	public void setIdentCardNo(String identCardNo) {
		this.identCardNo = identCardNo;
	}

	@Column(name = "email", length = 100)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "special", length = 200)
	public String getSpecial() {
		return this.special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}

	@Column(name = "compuLevel", length = 10)
	public String getCompuLevel() {
		return this.compuLevel;
	}

	public void setCompuLevel(String compuLevel) {
		this.compuLevel = compuLevel;
	}

	@Column(name = "foreignType", length = 10)
	public String getForeignType() {
		return this.foreignType;
	}

	public void setForeignType(String foreignType) {
		this.foreignType = foreignType;
	}

	@Column(name = "interestAndHobby", length = 100)
	public String getInterestAndHobby() {
		return this.interestAndHobby;
	}

	public void setInterestAndHobby(String interestAndHobby) {
		this.interestAndHobby = interestAndHobby;
	}

	@Column(name = "birthDate")
	@Temporal(TemporalType.DATE)
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "crtTime")
	public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "uptTime")
	public Date getUptTime() {
		return uptTime;
	}
	
	public void setUptTime(Date uptTime) {
		this.uptTime = uptTime;
	}
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "basInfoPers")
	public Tarchives getArchives() {
		return this.archives;
	}

	public void setArchives(Tarchives archives) {
		this.archives = archives;
	}
}
