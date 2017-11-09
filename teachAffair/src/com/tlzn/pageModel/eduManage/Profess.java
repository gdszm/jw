package com.tlzn.pageModel.eduManage;

import java.util.Date;

import com.tlzn.model.base.BaseObject;
/*
 * 授课（教师-课程）OK
 */
public class Profess extends BaseObject implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	private String id;
//	private Date beginDate; //开始任课时间
//	private Date endDate;   //结束任课时间
	
	private Integer courseCon;     //课容量
	private Integer courseSelNum;  //选课人数
	private Integer courseSpareNum;//课余量
	
	private Date crtTime; 		  //创建时间
	private Date uptTime; 		  //更新时间
	
	//教师
	private String teacherName;    //姓名
	private String sex;            //性别
	private String rank;           //教师职称（1助教、2讲师、3副教授、4教授）多选
	//课程
	private String enName;        //课程名称(英文)
	private String sortNo;        //课程序号
	private String credit;        //课程学分
	private String courseAtt;     //课程属性（1:公共必修 2: 公共选修 3:基础必修 4:基础选修 5:专业必修 6:专业选修  7:实验  8:实习 ）
	
	//上课教室
	private String houseNo;         //门牌号
	private Integer floor;          //所在楼层
	//授课时间
	private String weeks;   //周次 
	private String week;    //星期
	private String section; //节次

	//非数据库
	private String teacherId;     //教师号
	private String sexName;       //教师性别
	private String rankName;      //教师职称（1助教、2讲师、3副教授、4教授）多选
	private String courseId;  	  //课程号
	private String courseName;    //课程名称
	
	private String courseAttName; //课程属性名称
	
	private String  courseSu;	  //授课门数(统计用)
	private String  courseSuStart;//授课门数开始
	private String  courseSuEnd;  //授课门数结束
	
	private String classroomId;     //上课教室ID
	private String classroomName;   //教室名称
	
	private String teachingbuildingName;   //教学楼名称
	private String campusName;             //校区名称
	
	private String professTimeId;  //授课时间ID
	
	public Profess() {
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

//	public Date getBeginDate() {
//		return beginDate;
//	}
//
//	public void setBeginDate(Date beginDate) {
//		this.beginDate = beginDate;
//	}
//
//	public Date getEndDate() {
//		return endDate;
//	}
//
//	public void setEndDate(Date endDate) {
//		this.endDate = endDate;
//	}

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

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getSexName() {
		return sexName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getCourseAttName() {
		return courseAttName;
	}

	public void setCourseAttName(String courseAttName) {
		this.courseAttName = courseAttName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCourseAtt() {
		return courseAtt;
	}

	public void setCourseAtt(String courseAtt) {
		this.courseAtt = courseAtt;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCourseSu() {
		return courseSu;
	}

	public void setCourseSu(String courseSu) {
		this.courseSu = courseSu;
	}
	public String getCourseSuStart() {
		return courseSuStart;
	}
	public void setCourseSuStart(String courseSuStart) {
		this.courseSuStart = courseSuStart;
	}
	public String getCourseSuEnd() {
		return courseSuEnd;
	}
	public void setCourseSuEnd(String courseSuEnd) {
		this.courseSuEnd = courseSuEnd;
	}
	public String getSortNo() {
		return sortNo;
	}
	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}
	public String getCredit() {
		return credit;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
	public String getWeeks() {
		return weeks;
	}
	public void setWeeks(String weeks) {
		this.weeks = weeks;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getHouseNo() {
		return houseNo;
	}
	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}
	public String getRankName() {
		return rankName;
	}
	public void setRankName(String rankName) {
		this.rankName = rankName;
	}
	public String getClassroomId() {
		return classroomId;
	}
	public void setClassroomId(String classroomId) {
		this.classroomId = classroomId;
	}
	public String getClassroomName() {
		return classroomName;
	}
	public void setClassroomName(String classroomName) {
		this.classroomName = classroomName;
	}
	public Integer getCourseCon() {
		return courseCon;
	}
	public void setCourseCon(Integer courseCon) {
		this.courseCon = courseCon;
	}
	public Integer getCourseSelNum() {
		return courseSelNum;
	}
	public void setCourseSelNum(Integer courseSelNum) {
		this.courseSelNum = courseSelNum;
	}
	public Integer getCourseSpareNum() {
		return courseSpareNum;
	}
	public void setCourseSpareNum(Integer courseSpareNum) {
		this.courseSpareNum = courseSpareNum;
	}
	public String getTeachingbuildingName() {
		return teachingbuildingName;
	}
	public void setTeachingbuildingName(String teachingbuildingName) {
		this.teachingbuildingName = teachingbuildingName;
	}
	public String getCampusName() {
		return campusName;
	}
	public void setCampusName(String campusName) {
		this.campusName = campusName;
	}
	public String getProfessTimeId() {
		return professTimeId;
	}
	public void setProfessTimeId(String professTimeId) {
		this.professTimeId = professTimeId;
	}
	public Integer getFloor() {
		return floor;
	}
	public void setFloor(Integer floor) {
		this.floor = floor;
	}
	
}
