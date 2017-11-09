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

/**
 * 参加考试(学生-考试）
 */
@Entity
@Table(name = "joinexam", catalog = "artcollege")
public class TjoinExam  extends BaseObject implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String score; //成绩
	private Date crtTime; //创建时间
	private Date uptTime; //更新时间
	
	private Texam exam;//考试
	private Tstu stu;  //学生

	public TjoinExam() {
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 16)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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
	@JoinColumn(name = "examId")
	public Texam getExam() {
		return this.exam;
	}

	public void setExam(Texam exam) {
		this.exam = exam;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stuNo")
	public Tstu getStu() {
		return this.stu;
	}

	public void setStu(Tstu stu) {
		this.stu = stu;
	}

	@Column(name = "score", length = 50)
	public String getScore() {
		return this.score;
	}

	public void setScore(String score) {
		this.score = score;
	}

}