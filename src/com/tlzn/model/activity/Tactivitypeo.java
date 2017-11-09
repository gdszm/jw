package com.tlzn.model.activity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tlzn.model.Tcommitteeman;
import com.tlzn.model.base.BaseObject;
import com.tlzn.model.sys.Tsecondary;

@Entity
@Table(name = "TACTIVITYPEO", schema = "")
public class Tactivitypeo extends BaseObject implements java.io.Serializable{
private static final long serialVersionUID = 1L;
	
	private String auid;//AUID	编码
	private Tactivity activity;//AID	活动ID
	private Tcommitteeman comm;//COMM_CODE	用户ID
	private String astatus;//ASTATUS	出席状态
	private Date createtime;//CREATETIME	创建日期
	private String memo;//MEMO	备注
	private String leaveType;//LEAVE_TYPE	请假类型
	private String leaveReason;//LEAVE_REASON	请假事由
	
	public Tactivitypeo(){}
	
	
	public Tactivitypeo(String auid, String aid, String commCode,
			String astatus, Date createtime, String memo, String leaveType,
			String leaveReason) {
		super();
		this.auid = auid;
		this.astatus = astatus;
		this.createtime = createtime;
		this.memo = memo;
		this.leaveType = leaveType;
		this.leaveReason = leaveReason;
	}


	@Id
	@Column(name = "AUID",  length = 20)
	public String getAuid() {
		return auid;
	}

	public void setAuid(String auid) {
		this.auid = auid;
	}
	
	@ManyToOne(targetEntity=Tactivity.class)//fetch=FetchType.LAZY
	@JoinColumn(name = "AID", nullable = false)
	public Tactivity getActivity() {
		return activity;
	}


	public void setActivity(Tactivity activity) {
		this.activity = activity;
	}

	@ManyToOne(targetEntity=Tcommitteeman.class)//fetch=FetchType.LAZY
	@JoinColumn(name = "COMM_CODE", nullable = false)
	public Tcommitteeman getComm() {
		return comm;
	}


	public void setComm(Tcommitteeman comm) {
		this.comm = comm;
	}
	
	@Column(name = "ASTATUS",  length = 20)
	public String getAstatus() {
		return astatus;
	}

	public void setAstatus(String astatus) {
		this.astatus = astatus;
	}
	@Column(name = "CREATETIME")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	@Column(name = "MEMO",  length = 50)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "LEAVE_TYPE",  length = 4)
	public String getLeaveType() {
		return leaveType;
	}


	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	@Column(name = "LEAVE_REASON",  length = 500)
	public String getLeaveReason() {
		return leaveReason;
	}


	public void setLeaveReason(String leaveReason) {
		this.leaveReason = leaveReason;
	}

}
