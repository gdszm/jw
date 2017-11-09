package com.tlzn.pageModel.eduManage;

import java.util.Date;

import com.tlzn.model.base.BaseObject;
/*
 * 家庭成员OK
 */
public class FamilyMem extends BaseObject implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String id;    	     //成员编号
	private String name;         //姓名
	private Integer age;         //年龄
	private String familyRole;   //家庭角色(与本人关系)
	private String unit;         //工作单位
	private String duty;         //职务
	private Integer yearIncome ; //年收入  (万元)
	private String healthStatus; //健康状况(代码)
	private String tel;          //手机号码
	private Date crtTime; 		 //创建时间
	private Date uptTime; 		 //更新时间
	
	//非数据库
	private String familyId;     //家庭编号
	private String healthStatusName; //健康状况
	
	public FamilyMem() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getFamilyRole() {
		return familyRole;
	}

	public void setFamilyRole(String familyRole) {
		this.familyRole = familyRole;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public Integer getYearIncome() {
		return yearIncome;
	}

	public void setYearIncome(Integer yearIncome) {
		this.yearIncome = yearIncome;
	}

	public String getHealthStatus() {
		return healthStatus;
	}

	public void setHealthStatus(String healthStatus) {
		this.healthStatus = healthStatus;
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

	public String getHealthStatusName() {
		return healthStatusName;
	}

	public void setHealthStatusName(String healthStatusName) {
		this.healthStatusName = healthStatusName;
	}

	public String getFamilyId() {
		return familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
}
