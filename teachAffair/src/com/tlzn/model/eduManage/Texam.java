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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.tlzn.model.base.BaseObject;
/*
 * 考试（学生-课程）OK
 */
@Entity
@Table(name = "exam", catalog = "artcollege")
public class Texam extends BaseObject implements java.io.Serializable{
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
	private Date crtTime; //创建时间
	private Date uptTime; //更新时间
	
	private Tcourse course;       //课程
	private Set<TjoinExam> joinExams = new HashSet<TjoinExam>(0);
	
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
	
	public Texam() {
	}
	
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 16)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "year")
	public Integer getYear() {
		return this.year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "examSDate")
	public Date getExamSDate() {
		return examSDate;
	}

	public void setExamSDate(Date examSDate) {
		this.examSDate = examSDate;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "examEDate")
	public Date getExamEDate() {
		return examEDate;
	}

	public void setExamEDate(Date examEDate) {
		this.examEDate = examEDate;
	}
	@Column(name = "examPlace", length = 150)
	public String getExamPlace() {
		return this.examPlace;
	}

	public void setExamPlace(String examPlace) {
		this.examPlace = examPlace;
	}

	@Column(name = "term")
	public Integer getTerm() {
		return this.term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	@Column(name = "examName", length = 100)
	public String getExamName() {
		return this.examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	@Column(name = "inviTeacherId", length = 16)
	public String getInviTeacherId() {
		return this.inviTeacherId;
	}

	public void setInviTeacherId(String inviTeacherId) {
		this.inviTeacherId = inviTeacherId;
	}

	@Column(name = "isLate", length = 10)
	public String getIsLate() {
		return this.isLate;
	}

	public void setIsLate(String isLate) {
		this.isLate = isLate;
	}

	@Column(name = "isAbsent", length = 10)
	public String getIsAbsent() {
		return this.isAbsent;
	}

	public void setIsAbsent(String isAbsent) {
		this.isAbsent = isAbsent;
	}

	@Column(name = "isCribbing", length = 10)
	public String getIsCribbing() {
		return this.isCribbing;
	}

	public void setIsCribbing(String isCribbing) {
		this.isCribbing = isCribbing;
	}

	@Column(name = "resultType", length = 20)
	public String getResultType() {
		return this.resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	@Column(name = "comment", length = 2000)
	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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
	@JoinColumn(name = "courseId")
	public Tcourse getCourse() {
		return this.course;
	}

	public void setCourse(Tcourse course) {
		this.course = course;
	}

	@Transient
	public String getResultTypeString() {
		return resultTypeString;
	}

	public void setResultTypeString(String resultTypeString) {
		this.resultTypeString = resultTypeString;
	}
	@Transient
	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	@Transient
	public String getInviTeacherName() {
		return inviTeacherName;
	}

	public void setInviTeacherName(String inviTeacherName) {
		this.inviTeacherName = inviTeacherName;
	}
	@Transient
	public String getExamSubjName() {
		return examSubjName;
	}

	public void setExamSubjName(String examSubjName) {
		this.examSubjName = examSubjName;
	}
	@Transient
	public String getExamSDateString() {
		return examSDateString;
	}

	public void setExamSDateString(String examSDateString) {
		this.examSDateString = examSDateString;
	}
	@Transient
	public String getExamEDateString() {
		return examEDateString;
	}

	public void setExamEDateString(String examEDateString) {
		this.examEDateString = examEDateString;
	}
	@Transient
	public String getExamSDateStart() {
		return examSDateStart;
	}

	public void setExamSDateStart(String examSDateStart) {
		this.examSDateStart = examSDateStart;
	}
	@Transient
	public String getExamSDateEnd() {
		return examSDateEnd;
	}

	public void setExamSDateEnd(String examSDateEnd) {
		this.examSDateEnd = examSDateEnd;
	}
	@Transient
	public String getExamEDateStart() {
		return examEDateStart;
	}

	public void setExamEDateStart(String examEDateStart) {
		this.examEDateStart = examEDateStart;
	}
	@Transient
	public String getExamEDateEnd() {
		return examEDateEnd;
	}

	public void setExamEDateEnd(String examEDateEnd) {
		this.examEDateEnd = examEDateEnd;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "exam")
	public Set<TjoinExam> getJoinExams() {
		return this.joinExams;
	}

	public void setJoinExams(Set<TjoinExam> joinExams) {
		this.joinExams = joinExams;
	}
}
