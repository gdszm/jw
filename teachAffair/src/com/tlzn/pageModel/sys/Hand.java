package com.tlzn.pageModel.sys;

import java.util.Date;

public class Hand {
	private static final long serialVersionUID = 1L;

	private String handleReplyId;
	private String proposalId;
	private String answerMode;
	private String replySecret;
	private String committeemanOpinion;
	private String solveHow;
	private String carryoutFlg;
	private Date factEnddate;
	private String opinionExplain;
	private String reply;
	private String operator;
	private Date operateTime;
	private String status;
	private String remark;
	private String mainFlg;
	private String satisfyFlg;
	private String satisfyInfo;
	private String replyPass;
	private String rebutInfo;
	

	private String proposalCode;	//提案编号
	private String secondayName;	//届次名称
	private String title;	//提案标题
	private String proposalTypeName;	//提案类型名称(dic表)
	private Date createTime;	// 立案日期
	private String fistProposaler;	//第一提案人
	private String proposalerNum;	//提案人数
	private Date demandEnddate;		//要求交办日期
	private String companyId;		//承办单位ID
	private String companyName;		//承办单位名称
	private String companyType;		//承办单位类型
	private String companyTypeName; //承办单位类型名称
	private String handleType;		//办理类型(dic表)
	private String handleTypeName;		//办理类型名称(dic表)
	private String solveHowName;		//解决程序名称(dic表)
	private String carryoutFlgName;		//是否落实名称(dic表)
	private String commOpName;		//委员意见名称(dic表)
	private String statusName;		//回复状态名称(dic表)
	private String mainFlgName;     //主协办名称
	private String answerModeName;  //沟通方式名称
	private String satisfyFlgName; //满意度名称
	private String replyPassName; //办复审查状态名称
	
	private String ids;// 传入ID
	private int page;// 当前页
	private int rows;// 每页显示记录数
	private String sort;// 排序字段名
	private String order;// 按什么排序(asc,desc)

	/** default constructor */
	public Hand() {
	}

	public String getHandleReplyId() {
		return this.handleReplyId;
	}

	public void setHandleReplyId(String handleReplyId) {
		this.handleReplyId = handleReplyId;
	}

	public String getProposalId() {
		return proposalId;
	}

	public void setProposalId(String proposalId) {
		this.proposalId = proposalId;
	}

	public String getAnswerMode() {
		return this.answerMode;
	}

	public void setAnswerMode(String answerMode) {
		this.answerMode = answerMode;
	}

	public String getReplySecret() {
		return this.replySecret;
	}

	public void setReplySecret(String replySecret) {
		this.replySecret = replySecret;
	}

	public String getCommitteemanOpinion() {
		return this.committeemanOpinion;
	}

	public void setCommitteemanOpinion(String committeemanOpinion) {
		this.committeemanOpinion = committeemanOpinion;
	}

	public String getSolveHow() {
		return this.solveHow;
	}

	public void setSolveHow(String solveHow) {
		this.solveHow = solveHow;
	}

	public String getCarryoutFlg() {
		return this.carryoutFlg;
	}

	public void setCarryoutFlg(String carryoutFlg) {
		this.carryoutFlg = carryoutFlg;
	}

	public Date getFactEnddate() {
		return this.factEnddate;
	}

	public void setFactEnddate(Date factEnddate) {
		this.factEnddate = factEnddate;
	}

	public String getOpinionExplain() {
		return this.opinionExplain;
	}

	public void setOpinionExplain(String opinionExplain) {
		this.opinionExplain = opinionExplain;
	}

	public String getReply() {
		return this.reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMainFlg() {
		return mainFlg;
	}

	public void setMainFlg(String mainFlg) {
		this.mainFlg = mainFlg;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
	public String getProposalCode() {
		return proposalCode;
	}

	public void setProposalCode(String proposalCode) {
		this.proposalCode = proposalCode;
	}

	public String getSecondayName() {
		return secondayName;
	}

	public void setSecondayName(String secondayName) {
		this.secondayName = secondayName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProposalTypeName() {
		return proposalTypeName;
	}

	public void setProposalTypeName(String proposalTypeName) {
		this.proposalTypeName = proposalTypeName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getFistProposaler() {
		return fistProposaler;
	}

	public void setFistProposaler(String fistProposaler) {
		this.fistProposaler = fistProposaler;
	}

	public String getProposalerNum() {
		return proposalerNum;
	}

	public void setProposalerNum(String proposalerNum) {
		this.proposalerNum = proposalerNum;
	}

	public Date getDemandEnddate() {
		return demandEnddate;
	}

	public void setDemandEnddate(Date demandEnddate) {
		this.demandEnddate = demandEnddate;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getHandleType() {
		return handleType;
	}

	public void setHandleType(String handleType) {
		this.handleType = handleType;
	}

	public String getHandleTypeName() {
		return handleTypeName;
	}

	public void setHandleTypeName(String handleTypeName) {
		this.handleTypeName = handleTypeName;
	}

	public String getSolveHowName() {
		return solveHowName;
	}

	public void setSolveHowName(String solveHowName) {
		this.solveHowName = solveHowName;
	}

	public String getCarryoutFlgName() {
		return carryoutFlgName;
	}

	public void setCarryoutFlgName(String carryoutFlgName) {
		this.carryoutFlgName = carryoutFlgName;
	}

	public String getCommOpName() {
		return commOpName;
	}

	public void setCommOpName(String commOpName) {
		this.commOpName = commOpName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getMainFlgName() {
		return mainFlgName;
	}

	public void setMainFlgName(String mainFlgName) {
		this.mainFlgName = mainFlgName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getCompanyTypeName() {
		return companyTypeName;
	}

	public void setCompanyTypeName(String companyTypeName) {
		this.companyTypeName = companyTypeName;
	}

	public String getAnswerModeName() {
		return answerModeName;
	}

	public void setAnswerModeName(String answerModeName) {
		this.answerModeName = answerModeName;
	}

	public String getSatisfyFlg() {
		return satisfyFlg;
	}

	public void setSatisfyFlg(String satisfyFlg) {
		this.satisfyFlg = satisfyFlg;
	}

	public String getSatisfyInfo() {
		return satisfyInfo;
	}

	public void setSatisfyInfo(String satisfyInfo) {
		this.satisfyInfo = satisfyInfo;
	}

	public String getReplyPass() {
		return replyPass;
	}

	public void setReplyPass(String replyPass) {
		this.replyPass = replyPass;
	}

	public String getRebutInfo() {
		return rebutInfo;
	}

	public void setRebutInfo(String rebutInfo) {
		this.rebutInfo = rebutInfo;
	}

	public String getSatisfyFlgName() {
		return satisfyFlgName;
	}

	public void setSatisfyFlgName(String satisfyFlgName) {
		this.satisfyFlgName = satisfyFlgName;
	}

	public String getReplyPassName() {
		return replyPassName;
	}

	public void setReplyPassName(String replyPassName) {
		this.replyPassName = replyPassName;
	}
		
}
