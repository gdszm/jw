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
 * 奖惩记录OK
 */
@Entity
@Table(name = "awardpenalty", catalog = "artcollege")
public class TawardPenalty extends BaseObject implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private Date apDate;      //奖惩日期
	private String apAtt;     //奖惩性质（1：奖励 2：惩罚）
	private String apMethod;  //奖惩方式
	//11：表扬 12：嘉奖 13：奖金  14：记功 15：提职 16：晋级
	//21：口头警告 22：书面警告 23：返款 24：赔偿 25：降级 26：降薪 27：解聘 28：其他惩罚
	private String apScore;  //奖惩分数/金额(奖为正，惩罚为负数)
	private String apContent; //奖惩描述（文本区）
	private String apRemark;  //奖惩备注（文本区）
	private String apUnit;    //奖惩单位
	private Date crtTime; //创建时间
	private Date uptTime; //更新时间

	private Tarchives archives;

	public TawardPenalty() {
	}
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "apDate", length = 10)
	public Date getApDate() {
		return this.apDate;
	}

	public void setApDate(Date apDate) {
		this.apDate = apDate;
	}

	@Column(name = "apAtt", length = 10)
	public String getApAtt() {
		return this.apAtt;
	}

	public void setApAtt(String apAtt) {
		this.apAtt = apAtt;
	}

	@Column(name = "apMethod", length = 10)
	public String getApMethod() {
		return this.apMethod;
	}

	public void setApMethod(String apMethod) {
		this.apMethod = apMethod;
	}

	@Column(name = "apScore", length = 45)
	public String getApScore() {
		return this.apScore;
	}

	public void setApScore(String apScore) {
		this.apScore = apScore;
	}

	@Column(name = "apContent", length = 4000)
	public String getApContent() {
		return this.apContent;
	}

	public void setApContent(String apContent) {
		this.apContent = apContent;
	}

	@Column(name = "apRemark", length = 2000)
	public String getApRemark() {
		return this.apRemark;
	}

	public void setApRemark(String apRemark) {
		this.apRemark = apRemark;
	}

	@Column(name = "apUnit", length = 60)
	public String getApUnit() {
		return this.apUnit;
	}

	public void setApUnit(String apUnit) {
		this.apUnit = apUnit;
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
	@JoinColumn(name = "foreignId")
	public Tarchives getArchives() {
		return this.archives;
	}

	public void setArchives(Tarchives archives) {
		this.archives = archives;
	}
}
