package com.tlzn.model.poll;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.tlzn.model.base.BaseObject;
import com.tlzn.model.sys.Tsecondary;
@Entity
@Table(name = "TPOLL")
public class Tpoll extends BaseObject  implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String pollId;
	private Tsecondary tsecondary;
	private String pollCode;
	private String title;
	private String content;
	private String writer;
	private String createMan;
	private String createManName;
	private Date createTime;
	private String status;
	private String statusName;
	private String endContent;
	private String adoptFlg;
	private String adoptFlgName;
	private Date checkTime;
	private String issueFlg;
	private String issueFlgName;
	private String mergeFlg;
	private String mergeFlgName;
	private String mergeIds;
	private String stressFlg;
	private String stressFlgName;
	private String handleType;
	private String handleTypeName;
	private String handleComp;
	private String handleCompName;
	private String updateFlg;
	private String pollType;
	private String pollTypeName;
	private String editor;
	private String editorName;
	private String master;
	private String copyMan;
	private String phone;
	private String email;
	private String unit;
	private String address;
	private String adoptExplain;
	private String issuer;
	private String issuerName;
	private String filingFlg;
	private String filingFlgName;
	private String discard;
	private String assignFlg;
	private String assignFlgName;
	private String reviewFlg;
	private String reviewFlgName;
	
	
	public Tpoll() {
	}

	public Tpoll(String pollId, String pollCode, String title, String content,
			String writer, String createMan, Date createTime, String status,
			String endContent, String adoptFlg, Date checkTime,
			String issueFlg, String mergeFlg, String mergeIds,
			String stressFlg, String handleType, String handleComp,
			String updateFlg,Tsecondary tsecondary) {
		super();
		this.pollId = pollId;
		this.pollCode = pollCode;
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.createMan = createMan;
		this.createTime = createTime;
		this.status = status;
		this.endContent = endContent;
		this.adoptFlg = adoptFlg;
		this.checkTime = checkTime;
		this.issueFlg = issueFlg;
		this.mergeFlg = mergeFlg;
		this.mergeIds = mergeIds;
		this.stressFlg = stressFlg;
		this.handleType = handleType;
		this.handleComp = handleComp;
		this.updateFlg = updateFlg;
		this.tsecondary=tsecondary;
	}
	@Id
	@Column(name = "POLL_ID", unique = true, nullable = false, length = 12)
	public String getPollId() {
		return pollId;
	}

	public void setPollId(String pollId) {
		this.pollId = pollId;
	}
	@ManyToOne(targetEntity=Tsecondary.class)//fetch=FetchType.LAZY
	@JoinColumn(name = "SECONDARY_ID", nullable = false)
	public Tsecondary getTsecondary() {
		return tsecondary;
	}

	public void setTsecondary(Tsecondary tsecondary) {
		this.tsecondary = tsecondary;
	}

	@Column(name = "POLL_CODE")
	public String getPollCode() {
		return pollCode;
	}

	public void setPollCode(String pollCode) {
		this.pollCode = pollCode;
	}
	@Column(name = "TITLE")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name = "CONTENT")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	@Column(name = "WRITER")
	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}
	@Column(name = "CREATE_MAN")
	public String getCreateMan() {
		return createMan;
	}

	public void setCreateMan(String createMan) {
		this.createMan = createMan;
	}
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name = "END_CONTENT")
	public String getEndContent() {
		return endContent;
	}

	public void setEndContent(String endContent) {
		this.endContent = endContent;
	}
	@Column(name = "ADOPT_FLG")
	public String getAdoptFlg() {
		return adoptFlg;
	}

	public void setAdoptFlg(String adoptFlg) {
		this.adoptFlg = adoptFlg;
	}
	@Column(name = "CHECK_TIME")
	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	@Column(name = "ISSUE_FLG")
	public String getIssueFlg() {
		return issueFlg;
	}

	public void setIssueFlg(String issueFlg) {
		this.issueFlg = issueFlg;
	}
	@Column(name = "MERGE_FLG")
	public String getMergeFlg() {
		return mergeFlg;
	}

	public void setMergeFlg(String mergeFlg) {
		this.mergeFlg = mergeFlg;
	}
	@Column(name = "MERGE_IDS")
	public String getMergeIds() {
		return mergeIds;
	}

	public void setMergeIds(String mergeIds) {
		this.mergeIds = mergeIds;
	}
	@Column(name = "STRESS_FLG")
	public String getStressFlg() {
		return stressFlg;
	}

	public void setStressFlg(String stressFlg) {
		this.stressFlg = stressFlg;
	}
	@Column(name = "HANDLE_TYPE")
	public String getHandleType() {
		return handleType;
	}

	public void setHandleType(String handleType) {
		this.handleType = handleType;
	}
	@Column(name = "HANDLE_COMP")
	public String getHandleComp() {
		return handleComp;
	}

	public void setHandleComp(String handleComp) {
		this.handleComp = handleComp;
	}
	@Column(name = "UPDATE_FLG")
	public String getUpdateFlg() {
		return updateFlg;
	}

	public void setUpdateFlg(String updateFlg) {
		this.updateFlg = updateFlg;
	}
	@Column(name = "POLL_TYPE")
	public String getPollType() {
		return pollType;
	}

	public void setPollType(String pollType) {
		this.pollType = pollType;
	}
	@Transient
	public String getPollTypeName() {
		return pollTypeName;
	}

	public void setPollTypeName(String pollTypeName) {
		this.pollTypeName = pollTypeName;
	}
	@Column(name = "EDITOR")
	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}
	@Column(name = "MASTER")
	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}
	@Column(name = "COPY_MAN")
	public String getCopyMan() {
		return copyMan;
	}

	public void setCopyMan(String copyMan) {
		this.copyMan = copyMan;
	}
	@Column(name = "PHONE")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name = "UNIT")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	@Column(name = "ADDRESS")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	@Transient
	public String getCreateManName() {
		return createManName;
	}

	public void setCreateManName(String createManName) {
		this.createManName = createManName;
	}
	@Transient
	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	@Transient
	public String getAdoptFlgName() {
		return adoptFlgName;
	}

	public void setAdoptFlgName(String adoptFlgName) {
		this.adoptFlgName = adoptFlgName;
	}
	@Transient
	public String getIssueFlgName() {
		return issueFlgName;
	}

	public void setIssueFlgName(String issueFlgName) {
		this.issueFlgName = issueFlgName;
	}
	@Transient
	public String getMergeFlgName() {
		return mergeFlgName;
	}

	public void setMergeFlgName(String mergeFlgName) {
		this.mergeFlgName = mergeFlgName;
	}
	@Transient
	public String getStressFlgName() {
		return stressFlgName;
	}

	public void setStressFlgName(String stressFlgName) {
		this.stressFlgName = stressFlgName;
	}
	@Transient
	public String getHandleTypeName() {
		return handleTypeName;
	}

	public void setHandleTypeName(String handleTypeName) {
		this.handleTypeName = handleTypeName;
	}
	@Transient
	public String getHandleCompName() {
		return handleCompName;
	}

	public void setHandleCompName(String handleCompName) {
		this.handleCompName = handleCompName;
	}
	@Transient
	public String getEditorName() {
		return editorName;
	}

	public void setEditorName(String editorName) {
		this.editorName = editorName;
	}
	@Column(name = "ADOPT_EXPLAIN")
	public String getAdoptExplain() {
		return adoptExplain;
	}

	public void setAdoptExplain(String adoptExplain) {
		this.adoptExplain = adoptExplain;
	}
	@Column(name = "ISSUER")
	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	@Transient
	public String getIssuerName() {
		return issuerName;
	}

	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}
	@Column(name = "DISCARD")
	public String getDiscard() {
		return discard;
	}

	public void setDiscard(String discard) {
		this.discard = discard;
	}
	@Column(name = "FILING_FLG")
	public String getFilingFlg() {
		return filingFlg;
	}

	public void setFilingFlg(String filingFlg) {
		this.filingFlg = filingFlg;
	}
	@Transient
	public String getFilingFlgName() {
		return filingFlgName;
	}

	public void setFilingFlgName(String filingFlgName) {
		this.filingFlgName = filingFlgName;
	}
	@Column(name = "ASSIGN_FLG")
	public String getAssignFlg() {
		return assignFlg;
	}

	public void setAssignFlg(String assignFlg) {
		this.assignFlg = assignFlg;
	}
	@Transient
	public String getAssignFlgName() {
		return assignFlgName;
	}

	public void setAssignFlgName(String assignFlgName) {
		this.assignFlgName = assignFlgName;
	}
	@Column(name = "REVIEW_FLG")
	public String getReviewFlg() {
		return reviewFlg;
	}

	public void setReviewFlg(String reviewFlg) {
		this.reviewFlg = reviewFlg;
	}
	@Transient
	public String getReviewFlgName() {
		return reviewFlgName;
	}

	public void setReviewFlgName(String reviewFlgName) {
		this.reviewFlgName = reviewFlgName;
	}
	
}
