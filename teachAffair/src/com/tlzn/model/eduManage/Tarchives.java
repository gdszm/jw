package com.tlzn.model.eduManage;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.tlzn.model.base.BaseObject;
/*
 * 档案OK
 */
@Entity
@Table(name = "archives", catalog = "artcollege", uniqueConstraints = {
		@UniqueConstraint(columnNames = "basinfopersId"),
		@UniqueConstraint(columnNames = "familyId") })
public class Tarchives extends BaseObject implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//基本信息
	private String archNo;     //档案编号
	
	private String urgentTel;  //紧急情况下联系号码
	private String gradSchool; //毕业学校
	private String selfAppr ;  //自我评价
	private String remark;     //备注
	private String certFiles;  //证明材料（列表）
	private String certInstrs; //证明材料说明（列表）
	private Date crtTime;  //创建时间
	private Date uptTime;  //更新时间
	private String crtUsr; //创建用户
	private String uptUsr; //更新用户
	private String status; //废弃标识（0：否 1：是）
	
	private Tfamily family;                                        //家庭信息
	private TbasInfoPers basInfoPers;                                       //人员基本信息
	private Set<TawardPenalty> awardPenaltys = new HashSet<TawardPenalty>(0);//奖惩记录
	private Set<TeduExp> eduExps = new HashSet<TeduExp>(0);                  //教育经历
	private Set<TworkExp> workExps = new HashSet<TworkExp>(0);               //工作经历/学生任职情况
	
	private Tstu stu;
	private Tteacher teacher;
	
	public Tarchives() {
		
	}
	
	@Id
	@Column(name = "archNo", unique = true, nullable = false, length = 36)
	public String getArchNo() {
		return this.archNo;
	}

	public void setArchNo(String archNo) {
		this.archNo = archNo;
	}

	@Column(name = "urgentTel", length = 45)
	public String getUrgentTel() {
		return this.urgentTel;
	}

	public void setUrgentTel(String urgentTel) {
		this.urgentTel = urgentTel;
	}

	@Column(name = "selfAppr", length = 4000)
	public String getSelfAppr() {
		return this.selfAppr;
	}

	public void setSelfAppr(String selfAppr) {
		this.selfAppr = selfAppr;
	}

	@Column(name = "gradSchool", length = 45)
	public String getGradSchool() {
		return this.gradSchool;
	}

	public void setGradSchool(String gradSchool) {
		this.gradSchool = gradSchool;
	}
	
	@Column(name = "remark", length = 1000)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
	@Column(name = "crtUsr", length = 100)
	public String getCrtUsr() {
		return crtUsr;
	}

	public void setCrtUsr(String crtUsr) {
		this.crtUsr = crtUsr;
	}

	@Column(name = "uptUsr", length = 100)
	public String getUptUsr() {
		return uptUsr;
	}

	public void setUptUsr(String uptUsr) {
		this.uptUsr = uptUsr;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="familyId")
	public Tfamily getFamily() {
		return this.family;
	}

	public void setFamily(Tfamily family) {
		this.family = family;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="basinfopersId")
	public TbasInfoPers getBasInfoPers() {
		return this.basInfoPers;
	}

	public void setBasInfoPers(TbasInfoPers basInfoPers) {
		this.basInfoPers = basInfoPers;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "archives")
	public Set<TworkExp> getWorkExps() {
		return this.workExps;
	}

	public void setWorkExps(Set<TworkExp> workExps) {
		this.workExps = workExps;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "archives")
	public Set<TeduExp> getEduExps() {
		return this.eduExps;
	}

	public void setEduExps(Set<TeduExp> eduExps) {
		this.eduExps = eduExps;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "archives")
	public Set<TawardPenalty> getAwardPenaltys() {
		return this.awardPenaltys;
	}

	public void setAwardPenaltys(Set<TawardPenalty> awardPenaltys) {
		this.awardPenaltys = awardPenaltys;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "archives")
	public Tstu getStu() {
		return this.stu;
	}

	public void setStu(Tstu stu) {
		this.stu = stu;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "archives")
	public Tteacher getTeacher() {
		return this.teacher;
	}

	public void setTeacher(Tteacher teacher) {
		this.teacher = teacher;
	}
	@Column(name = "status", length = 10)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "certFiles", length = 2000)
	public String getCertFiles() {
		return certFiles;
	}

	public void setCertFiles(String certFiles) {
		this.certFiles = certFiles;
	}

	@Column(name = "certInstrs", length = 2000)
	public String getCertInstrs() {
		return certInstrs;
	}

	public void setCertInstrs(String certInstrs) {
		this.certInstrs = certInstrs;
	}
}
