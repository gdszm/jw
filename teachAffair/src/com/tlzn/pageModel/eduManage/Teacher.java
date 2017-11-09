package com.tlzn.pageModel.eduManage;

import java.util.Date;

import com.tlzn.model.base.BaseObject;
/*
 * 教师OK
 */
public class Teacher extends BaseObject implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String rank;           //教师职称（1助教、2讲师、3副教授、4教授）多选
	private String marry;          //婚姻状况（代码）
	private String hasTeacherCert; //有无教师资格证(0:无  1:有)
	private String education;      //学历(代码)
	private String gradFrom;       //毕业院校
	private String majorName;      //专业名称
	private String degree;         //学位(代码)
	private String remark;        //备注
	private Date crtTime; //创建时间
	private Date uptTime; //更新时间
	
	private String archNo;     //档案编号
	private String cid;        //部门ID
	private String sex;        //性别
	private String name;       //姓名
	private String picName;    //照片
	
	//非数据库
	private String cname;              //部门名称
	private String sexName;            //性别(代码)   
	private String rankName;           //教师职称（1助教、2讲师、3副教授、4教授）多选
	private String marryName;          //婚姻状况
	private String hasTeacherCertName; //有无教师资格证(0:无  1:有)
	private String educationName;      //学历(代码)
	private String degreeName;         //学位(代码)
	private Date crtSTime; //创建时间
	private Date crtETime; //创建时间
	public Teacher() {
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getMarry() {
		return marry;
	}
	public void setMarry(String marry) {
		this.marry = marry;
	}
	public String getHasTeacherCert() {
		return hasTeacherCert;
	}
	public void setHasTeacherCert(String hasTeacherCert) {
		this.hasTeacherCert = hasTeacherCert;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getGradFrom() {
		return gradFrom;
	}
	public void setGradFrom(String gradFrom) {
		this.gradFrom = gradFrom;
	}
	public String getMajorName() {
		return majorName;
	}
	public void setMajorName(String majorName) {
		this.majorName = majorName;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRankName() {
		return rankName;
	}
	public void setRankName(String rankName) {
		this.rankName = rankName;
	}
	public String getMarryName() {
		return marryName;
	}
	public void setMarryName(String marryName) {
		this.marryName = marryName;
	}
	public String getHasTeacherCertName() {
		return hasTeacherCertName;
	}
	public void setHasTeacherCertName(String hasTeacherCertName) {
		this.hasTeacherCertName = hasTeacherCertName;
	}
	public String getEducationName() {
		return educationName;
	}
	public String getSexName() {
		return sexName;
	}
	public void setSexName(String sexName) {
		this.sexName = sexName;
	}
	public void setEducationName(String educationName) {
		this.educationName = educationName;
	}
	public String getArchNo() {
		return archNo;
	}
	public void setArchNo(String archNo) {
		this.archNo = archNo;
	}
	public String getPicName() {
		return picName;
	}
	public void setPicName(String picName) {
		this.picName = picName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getDegreeName() {
		return degreeName;
	}
	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
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
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	

}
