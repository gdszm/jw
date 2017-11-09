package com.tlzn.pageModel.eduManage;

import java.util.Date;

import com.tlzn.model.base.BaseObject;
/*
 * 考试（学生-课程）OK
 */
public class Exam extends BaseObject implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private Integer year;        //考试年度
	private Date examSDate; //考试开始日期
	private Date examEDate; //考试结束日期
	private String examPlace;    //考试地点
	private Integer term;        //学期
	private String examName;     //考试名称
	private String inviTeacherId;//监考教师ID
	private String isLate;       //是否迟到
	private String isAbsent;     //是否缺考
	private String isCribbing;   //是否作弊
	private String resultType;   //成绩类型  满分100 ，满分120， 等级 ABCD， 优秀、良好、一般、较差、差
	private String comment;      //备注
	
	//非数据库
	private String courseName;//课程名称
	private String examSubjName;  //考试科目名称
	private String examSDateString; //考试开始日期
	private String examEDateString; //考试结束日期
	
	private String examSDateStart; //考试开始日期（开始）
	private String examSDateEnd;   //考试开始日期（结束）
	private String examEDateStart; //考试结束日期（开始）
	private String examEDateEnd;   //考试结束日期（结束）
	
	private String resultTypeString;//成绩类型
	private String inviTeacherName; //监考教师姓名
	
	public Exam() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Date getExamSDate() {
		return examSDate;
	}

	public void setExamSDate(Date examSDate) {
		this.examSDate = examSDate;
	}

	public Date getExamEDate() {
		return examEDate;
	}

	public void setExamEDate(Date examEDate) {
		this.examEDate = examEDate;
	}

	public String getExamPlace() {
		return examPlace;
	}

	public void setExamPlace(String examPlace) {
		this.examPlace = examPlace;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getInviTeacherId() {
		return inviTeacherId;
	}

	public void setInviTeacherId(String inviTeacherId) {
		this.inviTeacherId = inviTeacherId;
	}

	public String getIsLate() {
		return isLate;
	}

	public void setIsLate(String isLate) {
		this.isLate = isLate;
	}

	public String getIsAbsent() {
		return isAbsent;
	}

	public void setIsAbsent(String isAbsent) {
		this.isAbsent = isAbsent;
	}

	public String getIsCribbing() {
		return isCribbing;
	}

	public void setIsCribbing(String isCribbing) {
		this.isCribbing = isCribbing;
	}

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getExamSubjName() {
		return examSubjName;
	}

	public void setExamSubjName(String examSubjName) {
		this.examSubjName = examSubjName;
	}

	public String getExamSDateString() {
		return examSDateString;
	}

	public void setExamSDateString(String examSDateString) {
		this.examSDateString = examSDateString;
	}

	public String getExamEDateString() {
		return examEDateString;
	}

	public void setExamEDateString(String examEDateString) {
		this.examEDateString = examEDateString;
	}

	public String getExamSDateStart() {
		return examSDateStart;
	}

	public void setExamSDateStart(String examSDateStart) {
		this.examSDateStart = examSDateStart;
	}

	public String getExamSDateEnd() {
		return examSDateEnd;
	}

	public void setExamSDateEnd(String examSDateEnd) {
		this.examSDateEnd = examSDateEnd;
	}

	public String getExamEDateStart() {
		return examEDateStart;
	}

	public void setExamEDateStart(String examEDateStart) {
		this.examEDateStart = examEDateStart;
	}

	public String getExamEDateEnd() {
		return examEDateEnd;
	}

	public void setExamEDateEnd(String examEDateEnd) {
		this.examEDateEnd = examEDateEnd;
	}

	public String getResultTypeString() {
		return resultTypeString;
	}

	public void setResultTypeString(String resultTypeString) {
		this.resultTypeString = resultTypeString;
	}

	public String getInviTeacherName() {
		return inviTeacherName;
	}

	public void setInviTeacherName(String inviTeacherName) {
		this.inviTeacherName = inviTeacherName;
	}
	
}
