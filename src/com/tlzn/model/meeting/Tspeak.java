package com.tlzn.model.meeting;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.sun.star.bridge.oleautomation.Date;
import com.tlzn.model.Tcommitteeman;
import com.tlzn.model.base.BaseObject;


//@Entity
//@Table(name = "TMEET_SPEAK", schema = "", uniqueConstraints = @UniqueConstraint(columnNames = {
//		"SPEAK_ID" }))
public class Tspeak extends BaseObject  implements java.io.Serializable {

	
	private String speakId;
	//private Tmeeting tmeeting;
	private String tille;
	private String content;
	private String reportFlg;
	private Tcommitteeman tcomm;
	private Date createTime;
	private String atts;
	private String attsDepict;
	private String useSituation;
	private String status;
	private String opinion;
	private Date auditTime;
	private String discardType;
	private String memo;
}
