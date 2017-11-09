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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.tlzn.model.base.BaseObject;
import com.tlzn.model.sys.Tdept;
/*
 * 教师OK
 */
@Entity
@Table(name = "teacher", catalog = "artcollege", 
uniqueConstraints = @UniqueConstraint(columnNames = "archNo"))
public class Tteacher extends BaseObject implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String rank;           //教师职称（1助教、2讲师、3副教授、4教授）多选
	private String marry;          //婚姻状况
	private String hasTeacherCert; //有无教师资格证(0:无  1:有)
	private String education;      //学历(代码)
	private String gradFrom;       //毕业院校
	private String majorName;      //专业名称
	private String degree;         //学位(代码)
	private String remark;        //备注
	private Date crtTime; //创建时间
	private Date uptTime; //更新时间
	
	private Tarchives archives; //档案
	private Tdept tdept;		//所在院系
	
	private Set<Tprofess> professes = new HashSet<Tprofess>(0);
	private Set<TclassManage> classManages = new HashSet<TclassManage>(0);
	
	public Tteacher() {
	}
	
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 16)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "rank", length = 10)
	public String getRank() {
		return this.rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	@Column(name = "marry", length = 20)
	public String getMarry() {
		return this.marry;
	}

	public void setMarry(String marry) {
		this.marry = marry;
	}

	@Column(name = "hasTeacherCert", length = 10)
	public String getHasTeacherCert() {
		return this.hasTeacherCert;
	}

	public void setHasTeacherCert(String hasTeacherCert) {
		this.hasTeacherCert = hasTeacherCert;
	}

	@Column(name = "education", length = 50)
	public String getEducation() {
		return this.education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	@Column(name = "gradFrom", length = 100)
	public String getGradFrom() {
		return this.gradFrom;
	}

	public void setGradFrom(String gradFrom) {
		this.gradFrom = gradFrom;
	}

	@Column(name = "majorName", length = 100)
	public String getMajorName() {
		return this.majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	@Column(name = "degree", length = 10)
	public String getDegree() {
		return this.degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
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
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="archNo")
	public Tarchives getArchives() {
		return this.archives;
	}

	public void setArchives(Tarchives archives) {
		this.archives = archives;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "depId")
	public Tdept getTdept() {
		return this.tdept;
	}

	public void setTdept(Tdept tdept) {
		this.tdept = tdept;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "teacher")
	public Set<Tprofess> getProfesses() {
		return this.professes;
	}

	public void setProfesses(Set<Tprofess> professes) {
		this.professes = professes;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "teacher")
	public Set<TclassManage> getClassManages() {
		return this.classManages;
	}

	public void setClassManages(Set<TclassManage> classManages) {
		this.classManages = classManages;
	}
}
