package com.tlzn.pageModel.eduManage;

import com.tlzn.model.base.BaseObject;
/*
 * 课程
 */
public class Course extends BaseObject implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;       //课程名称
	private String enName;     //课程名称(英文)
	private String sortNo;     //课程序号
	private String credit;     //课程学分
	private String courseAtt;  //课程属性（1:公共必修 2: 公共选修 3:基础必修 4:基础选修 5:专业必修 6:专业选修  7:实验  8:实习 ）
	private String teachMeth;  //教学方法（文本区）
	private String teachBook;  //使用教材（文本区）
	private String asMethAnReq;//考核方式及其要求（文本区）
	private String comment;    //备注（文本区）
	
	// 非数据库
	private String courseAttName;  //课程属性（1:公共必修 2: 公共选修 3:基础必修 4:基础选修 5:专业必修 6:专业选修  7:实验  8:实习 ）
	
	public Course() {
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

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getCourseAtt() {
		return courseAtt;
	}

	public void setCourseAtt(String courseAtt) {
		this.courseAtt = courseAtt;
	}

	public String getTeachMeth() {
		return teachMeth;
	}

	public void setTeachMeth(String teachMeth) {
		this.teachMeth = teachMeth;
	}

	public String getTeachBook() {
		return teachBook;
	}

	public void setTeachBook(String teachBook) {
		this.teachBook = teachBook;
	}

	public String getAsMethAnReq() {
		return asMethAnReq;
	}

	public void setAsMethAnReq(String asMethAnReq) {
		this.asMethAnReq = asMethAnReq;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getSortNo() {
		return sortNo;
	}

	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}

	public String getCourseAttName() {
		return courseAttName;
	}

	public void setCourseAttName(String courseAttName) {
		this.courseAttName = courseAttName;
	}

}
