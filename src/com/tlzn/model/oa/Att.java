package com.tlzn.model.oa;

import java.util.Date;

import com.tlzn.model.base.BaseObject;
import com.tlzn.model.sys.Tuser;
/*
 * 考勤
 */
public class Att extends BaseObject implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String attNo;         // 考勤编码
	private Date attDate;         // 考勤日期
	private String shift;         // 班次
	private Date checkInTime;     // 签到时间
	private Date checkOutTime;    // 签退时间
	private String checkInPlace;  // 签到地点
	private String checkOutPlace; // 签退地点
	private String checkIn_long;// 签到经度
	private String checkIn_lat; // 签到纬度
	private String checkOut_long;// 签退经度
	private String checkOut_lat; // 签退纬度
	
	private Tuser tuser;//考勤人编号
	
	//非数据库
	private String deptId;         //所属部门
	private String deptName;       // 所属部门名称
	private String trueName;       // 真实姓名
	private String shift_name;       // 班次名称
	private Date attDateBegin;       // 考勤日期开始
	private Date attDateEnd;         // 考勤日期结束
	private String attDateString;         // 考勤日期(格式化字符串)
	private String checkInTimeString;     // 签到时间(格式化字符串)
	private String checkOutTimeString;    // 签退时间(格式化字符串)
	private String inOrOut;         // 考勤类型（1:签到/2:签退）
	private String device;         // 设备类型（1:电脑/2:手机）
	
	public Att() {
		
	}

}
