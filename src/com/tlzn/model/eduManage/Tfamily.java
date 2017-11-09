package com.tlzn.model.eduManage;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tlzn.model.base.BaseObject;
/*
 * 家庭信息OK
 */
@Entity
@Table(name = "family", catalog = "artcollege")
public class Tfamily extends BaseObject implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	private String id;       //家庭编号
	private Integer peoNum;  //家庭人口数
	private String addr;     //详细通讯地址
	private String tel;      //联系电话
	private String ecoStatus;//家庭经济状况  特困（ 1 ）  困难（2   ） 一般 （3） 富裕 （3） 
	private String ecoFrom;  //主要经济来源
	private Integer ecoTotal; //总收入（每年）
	private String ecoPay;   //主要支出
	private String teachBack;//家庭教育背景 良好（ 1 ）     一般（  2 ）     不良（3  ）
	private String introduce;//家庭情况介绍
	private String basicLive;//是否有低保证 没有(0) 有(1)  
	private String helping;  //是否享受过补助 没有(0) 有(1)  
	private String specialStatus;//家庭成员如果有特殊情况（疾病、残疾、单亲等）必须进行说明，家庭如果困难，说明困难情况）
	private Date crtTime; //创建时间
	private Date uptTime; //更新时间
	
	private Set<TfamilyMem> familyMems = new HashSet<TfamilyMem>(0);//家庭成员
	private Tarchives archives;
	
	public Tfamily() {
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 16)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "peoNum")
	public Integer getPeoNum() {
		return this.peoNum;
	}

	public void setPeoNum(Integer peoNum) {
		this.peoNum = peoNum;
	}

	@Column(name = "addr", length = 200)
	public String getAddr() {
		return this.addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Column(name = "tel", length = 100)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "ecoStatus", length = 10)
	public String getEcoStatus() {
		return this.ecoStatus;
	}

	public void setEcoStatus(String ecoStatus) {
		this.ecoStatus = ecoStatus;
	}

	@Column(name = "ecoFrom", length = 100)
	public String getEcoFrom() {
		return this.ecoFrom;
	}

	public void setEcoFrom(String ecoFrom) {
		this.ecoFrom = ecoFrom;
	}

	@Column(name = "ecoTotal")
	public Integer getEcoTotal() {
		return this.ecoTotal;
	}

	public void setEcoTotal(Integer ecoTotal) {
		this.ecoTotal = ecoTotal;
	}

	@Column(name = "ecoPay", length = 100)
	public String getEcoPay() {
		return this.ecoPay;
	}

	public void setEcoPay(String ecoPay) {
		this.ecoPay = ecoPay;
	}

	@Column(name = "teachBack", length = 10)
	public String getTeachBack() {
		return this.teachBack;
	}

	public void setTeachBack(String teachBack) {
		this.teachBack = teachBack;
	}

	@Column(name = "introduce", length = 2000)
	public String getIntroduce() {
		return this.introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	@Column(name = "basicLive", length = 10)
	public String getBasicLive() {
		return this.basicLive;
	}

	public void setBasicLive(String basicLive) {
		this.basicLive = basicLive;
	}

	@Column(name = "helping", length = 10)
	public String getHelping() {
		return this.helping;
	}

	public void setHelping(String helping) {
		this.helping = helping;
	}

	@Column(name = "specialStatus", length = 300)
	public String getSpecialStatus() {
		return this.specialStatus;
	}

	public void setSpecialStatus(String specialStatus) {
		this.specialStatus = specialStatus;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "family")
	public Set<TfamilyMem> getFamilyMems() {
		return this.familyMems;
	}

	public void setFamilyMems(Set<TfamilyMem> familyMems) {
		this.familyMems = familyMems;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "family")
	public Tarchives getArchives() {
		return this.archives;
	}

	public void setArchives(Tarchives archives) {
		this.archives = archives;
	}
}
