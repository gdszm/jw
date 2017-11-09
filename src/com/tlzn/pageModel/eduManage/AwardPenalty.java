package com.tlzn.pageModel.eduManage;

import java.util.Date;

import com.tlzn.model.base.BaseObject;
/*
 * 奖惩记录OK
 */
public class AwardPenalty extends BaseObject implements java.io.Serializable{
	
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
	
	//非数据库
	private String archNo;     //档案编号
	private String apAttName;     //奖惩性质（1：奖励 2：惩罚）
	private String apMethodName;  //奖惩方式
	
	public AwardPenalty() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getApDate() {
		return apDate;
	}

	public void setApDate(Date apDate) {
		this.apDate = apDate;
	}

	public String getApAtt() {
		return apAtt;
	}

	public void setApAtt(String apAtt) {
		this.apAtt = apAtt;
	}

	public String getApMethod() {
		return apMethod;
	}

	public void setApMethod(String apMethod) {
		this.apMethod = apMethod;
	}

	public String getApScore() {
		return apScore;
	}

	public void setApScore(String apScore) {
		this.apScore = apScore;
	}

	public String getApContent() {
		return apContent;
	}

	public void setApContent(String apContent) {
		this.apContent = apContent;
	}

	public String getApRemark() {
		return apRemark;
	}

	public void setApRemark(String apRemark) {
		this.apRemark = apRemark;
	}

	public String getApUnit() {
		return apUnit;
	}

	public void setApUnit(String apUnit) {
		this.apUnit = apUnit;
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

	public String getApAttName() {
		return apAttName;
	}

	public void setApAttName(String apAttName) {
		this.apAttName = apAttName;
	}

	public String getApMethodName() {
		return apMethodName;
	}

	public void setApMethodName(String apMethodName) {
		this.apMethodName = apMethodName;
	}

	public String getArchNo() {
		return archNo;
	}

	public void setArchNo(String archNo) {
		this.archNo = archNo;
	}
	
}
