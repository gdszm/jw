package com.tlzn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tlzn.model.base.BaseObject;

/**
 * Tproposaler entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TPROPOSALER", schema = "")
public class Tproposaler extends BaseObject implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields

	private String proposalerId;
	private Tcommitteeman tcommitteeman;
	private Tproposal tproposal;
	private String hostFlg;
	private String proposalForm;
	private String remark;

	// Constructors

	/** default constructor */
	public Tproposaler() {
	}

	/** minimal constructor */
	public Tproposaler(Tcommitteeman tcommitteeman) {
		this.tcommitteeman = tcommitteeman;
	}

	/** full constructor */
	public Tproposaler(Tcommitteeman tcommitteeman, Tproposal tproposal,
			String hostFlg, String proposalForm, String remark) {
		this.tcommitteeman = tcommitteeman;
		this.tproposal = tproposal;
		this.hostFlg = hostFlg;
		this.proposalForm = proposalForm;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "PROPOSALER_ID", unique = true, nullable = false, length = 12)
	public String getProposalerId() {
		return this.proposalerId;
	}

	public void setProposalerId(String proposalerId) {
		this.proposalerId = proposalerId;
	}

	@ManyToOne(targetEntity=Tcommitteeman.class)//fetch=FetchType.LAZY
	@JoinColumn(name = "COMMITTEEMAN_CODE", nullable = false)
	public Tcommitteeman getTcommitteeman() {
		return this.tcommitteeman;
	}

	public void setTcommitteeman(Tcommitteeman tcommitteeman) {
		this.tcommitteeman = tcommitteeman;
	}

	@ManyToOne(targetEntity=Tproposal.class)//fetch = FetchType.LAZY)
	@JoinColumn(name = "PROPOSAL_ID")
	public Tproposal getTproposal() {
		return this.tproposal;
	}

	public void setTproposal(Tproposal tproposal) {
		this.tproposal = tproposal;
	}

	@Column(name = "HOST_FLG", length = 4)
	public String getHostFlg() {
		return this.hostFlg;
	}

	public void setHostFlg(String hostFlg) {
		this.hostFlg = hostFlg;
	}

	@Column(name = "PROPOSAL_FORM", length = 100)
	public String getProposalForm() {
		return this.proposalForm;
	}

	public void setProposalForm(String proposalForm) {
		this.proposalForm = proposalForm;
	}

	@Column(name = "REMARK", length = 1000)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}