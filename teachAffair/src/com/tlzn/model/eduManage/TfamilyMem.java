package com.tlzn.model.eduManage;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tlzn.model.base.BaseObject;
/*
 * 家庭成员OK
 */
@Entity
@Table(name = "familymem", catalog = "artcollege")
public class TfamilyMem extends BaseObject implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String id;    	     //成员编号
	private String name;         //姓名
	private Integer age;         //年龄
	private String familyRole;   //家庭角色(与本人关系)
	private String unit;         //工作单位
	private String duty;         //职业
	private Integer yearIncome ; //年收入  (万元)
	private String healthStatus; //健康状况(代码)
	private String tel;          //电话
	private Date crtTime; 		 //创建时间
	private Date uptTime; 		 //更新时间
	
	private Tfamily family;
	
	public TfamilyMem() {
	}
	
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
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

	@Column(name = "age")
	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Column(name = "familyRole", length = 20)
	public String getFamilyRole() {
		return this.familyRole;
	}

	public void setFamilyRole(String familyRole) {
		this.familyRole = familyRole;
	}

	@Column(name = "unit", length = 200)
	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "duty", length = 100)
	public String getDuty() {
		return this.duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	@Column(name = "yearIncome")
	public Integer getYearIncome() {
		return this.yearIncome;
	}

	public void setYearIncome(Integer yearIncome) {
		this.yearIncome = yearIncome;
	}

	@Column(name = "healthStatus", length = 10)
	public String getHealthStatus() {
		return this.healthStatus;
	}

	public void setHealthStatus(String healthStatus) {
		this.healthStatus = healthStatus;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "familyId")
	public Tfamily getFamily() {
		return this.family;
	}

	public void setFamily(Tfamily family) {
		this.family = family;
	}
	
	@Column(name = "tel", length = 50)
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
}
