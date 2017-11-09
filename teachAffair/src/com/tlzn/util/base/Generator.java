package com.tlzn.util.base;

import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>Description: 编码生成器，根据规则生成唯一编码，如：采购单号、退货单号、发货单号等
 * 
 * @version 	1.6
 * @author 		刘平
 * @since 		jdk1.4 以上
 */
public class Generator 
{
	private static Generator instance;
	protected final Log log = LogFactory.getLog(getClass());
	public Log getLog() {
		return log;
	}	
	public static Generator getInstance() throws Exception
	{	
		if (instance != null) return instance;
		else return instance = new Generator();
	}
	/**
	 * 得到提案人编码
	 * @return
	 * @throws Exception
	 * author  邓宏伟
	 */
	public String getCodeNO() throws Exception{
		String prefix="";
		String seqfield="commNO";		
		return this.getGeneratorThree(prefix,seqfield,4);
	}
	/**
	 * 得到服务商编码
	 * @return
	 * @throws Exception
	 * author  邓宏伟
	 */
	public String getCompanyNO() throws Exception {
		String prefix="";
		String seqfield="companyNO";		
		return this.getGeneratorThree(prefix,seqfield,4);
	}	
	/**
	 * 得到服务商编码
	 * @return
	 * @throws Exception
	 * author  邓宏伟
	 */
	public String getSecondaryNO() throws Exception {
		String prefix=String.valueOf(Calendar.getInstance().get(Calendar.YEAR)).substring(2);
		String seqfield="secondaryId";		
		return this.getGeneratorThree(prefix,seqfield,5);
	}	
	//日志编码
	public String getDoLogNO() throws Exception  {
		String prefix="";
		String seqfield="logCode";		
		return this.getGeneratorThree(prefix,seqfield,9);
	}
	//提案ID
	public String getProposalNO() throws Exception  {
		String prefix="";
		String seqfield="proposalNO";		
		return this.getGeneratorThree(prefix,seqfield,-1);
	}
	//提案Code
	public String getProposalCode(String ext, String propCode) throws Exception  {
		int num=0;
		if(propCode==null || "".equals(propCode)){
			num=1;
		}else{
			num=NumberFormatTools.getInstance().toInteger(propCode.substring(4, propCode.length()))+1;
		}
		return this.getGeneratorSix(ext,num,"000");
	}
	//提案人ID
	public String getSponsorNO() throws Exception  {
		String prefix="";
		String seqfield="sponsorNO";		
		return this.getGeneratorThree(prefix,seqfield,-1);
	}
	//办复表ID
	public String getHandleReplyNO() throws Exception  {
		String prefix="";
		String seqfield="handleReplyNO";		
		return this.getGeneratorThree(prefix,seqfield,-1);
	}
	//计分规则编码
	public String getRulesNO() throws Exception  {
		String prefix="";
		String seqfield="rulesNO";		
		return this.getGeneratorThree(prefix,seqfield,5);
	}
	//社情民意ID
	public String getPollNO() throws Exception  {
		String prefix="";
		String seqfield="pollNO";		
		return this.getGeneratorThree(prefix,seqfield,-1);
	}
	//提案Code
	public String getPollCode(String propCode) throws Exception  {
		int num=0;
		if(propCode==null || "".equals(propCode)){
			num=1;
		}else{
			num=NumberFormatTools.getInstance().toInteger(propCode)+1;
		}
		return this.getGeneratorFive(num,4);
	}
	//社情民意审批ID
	public String getPollCheckNO() throws Exception  {
		String prefix="";
		String seqfield="pollcheckNO";		
		return this.getGeneratorThree(prefix,seqfield,-1);
	}
	//批示与采用记分登记ID
	public String getPollScoreNO() throws Exception  {
		String prefix="";
		String seqfield="pollScoreNO";		
		return this.getGeneratorThree(prefix,seqfield,-1);
	}
	//办复表ID
	public String getPollHandleNO() throws Exception  {
		String prefix="";
		String seqfield="PollHandleNO";		
		return this.getGeneratorThree(prefix,seqfield,-1);
	}
	
	//通知公告ID
	public String getNoticeNO() throws Exception  {
		String prefix="";
		String seqfield="noticeNO";		
		return this.getGeneratorThree(prefix,seqfield,-1);
	}
	//文件ID
	public String getFileDataNO() throws Exception  {
		String prefix="";
		String seqfield="fileDataNO";		
		return this.getGeneratorThree(prefix,seqfield,-1);
	}
	//会议ID
	public String getMeetingNO() throws Exception  {
		String prefix="M";
		String seqfield="meetingNO";		
		return this.getGeneratorThree(prefix,seqfield,5);
	}
	//参会人员ID
	public String getMeetingManNO() throws Exception  {
		String prefix="";
		String seqfield="MeetingManNO";		
		return this.getGeneratorThree(prefix,seqfield,-1);
	}
	//发言ID
	public String getSpeechNO() throws Exception  {
		String prefix="";
		String seqfield="speechNO";		
		return this.getGeneratorThree(prefix,seqfield,-1);
	}
	//考试ID
	public String getExamNO() throws Exception  {
		String prefix="";
		String seqfield="examNO";	
		return this.getGeneratorThree(prefix,seqfield,-1);
	}
	//家庭ID
	public String getFamilyNO() throws Exception  {
		String prefix="";
		String seqfield="familyNO";
		return this.getGeneratorThree(prefix,seqfield,-1);
	}
	//人员基本信息ID
		public String getBaseInfoNO() throws Exception  {
			String prefix="";
			String seqfield="baseInfoNO";
			return this.getGeneratorThree(prefix,seqfield,-1);
		}
	//家庭成员ID
	public String getFamilyMemNO() throws Exception  {
		String prefix="";
		String seqfield="familyMemNO";
		return this.getGeneratorThree(prefix,seqfield,-1);
	}
	//奖惩记录ID
	public String getAwardPenaltyNO() throws Exception  {
		String prefix="";
		String seqfield="awardPenaltyNO";
		return this.getGeneratorThree(prefix,seqfield,-1);
	}
	//教育经历ID
	public String getEduExpNO() throws Exception  {
		String prefix="";
		String seqfield="eduExpNO";
		return this.getGeneratorThree(prefix,seqfield,-1);
	}
	//工作经历ID
	public String getWorkExpNO() throws Exception  {
		String prefix="";
		String seqfield="workExpNO";
		return this.getGeneratorThree(prefix,seqfield,-1);
	}
	//工作经历ID
	public String getArchNO() throws Exception  {
		String prefix="Arch";
		String seqfield="archNO";
		return this.getGeneratorThree(prefix,seqfield,-1);
	}
	//学号
	public String getStuNO() throws Exception  {
		String prefix="SNO";
		String seqfield="stuNO";
		return this.getGeneratorThree(prefix,seqfield,-1);
	}
	//班级ID
	public String getClassesNO() throws Exception  {
			String prefix="";
			String seqfield="classesNO";
			return this.getGeneratorThree(prefix,seqfield,-1);
	}
	//课程号
	public String getCourseNO() throws Exception  {
			String prefix="";
			String seqfield="courseNO";
			return this.getGeneratorThree(prefix,seqfield,-1);
	}
	//教师号
	public String getTeacherNO() throws Exception  {
			String prefix="";
			String seqfield="teacherNO";
			return this.getGeneratorThree(prefix,seqfield,-1);
	}
	//学期号
	public String getTermNO() throws Exception  {
			String prefix="";
			String seqfield="termNO";
			return this.getGeneratorThree(prefix,seqfield,-1);
	}
	//授课号
		public String getProfessNO() throws Exception  {
				String prefix="";
				String seqfield="professNO";
				return this.getGeneratorThree(prefix,seqfield,-1);
	}
	//班级号
	public String getClassroomNO() throws Exception  {
			String prefix="";
			String seqfield="classroomNO";
			return this.getGeneratorThree(prefix,seqfield,-1);
	}
	//授课时间号
		public String getProfessTimeNO() throws Exception  {
				String prefix="";
				String seqfield="professTimeNO";
				return this.getGeneratorThree(prefix,seqfield,-1);
		}
	/**
	 * 得到派工单编码
	 * @return
	 * @throws Exception
	 * author  邓宏伟
	 */
	public String getPgdNO() throws Exception
	{
		String prefix="S";
		String seqfield="pgdNO";		
		return this.getGeneratorSecond(prefix,seqfield);
	}	
	/**
	 * 得到类别编码
	 * @throws Exception 
	 * @return String 返回餐品(产品)编码
	 * author  邓宏伟
	 */
	public String getTypeNO() throws Exception
	{
		String prefix="T";
		String seqfield="typeNO";		
		return this.getGeneratorThree(prefix, seqfield,3);
	}	
	/**
	 * 得到信息编码
	 * @throws Exception 
	 * @return String 返回餐品(产品)编码
	 * author  邓宏伟
	 */
	public String getInfoNO() throws Exception
	{
		String prefix="2";
		String seqfield="infoNO";		
		return this.getGeneratorThree(prefix, seqfield,3);
	}	
	/**
	 * 得到系统代码类型表编号 
	 * @throws Exception 
	 * @return String	返回系统代码类型表编号
	 * author  刘平
	 */
	public String getCodeTypeNO() throws Exception
	{
		String prefix="";
		String seqfield="codeTypeNO";
		return this.getGeneratorThree(prefix, seqfield,-1);
	}	
	/**
	 * 得到系统代码表编号 
	 * @throws Exception 
	 * @return String	返回系统代码表编号
	 * author  刘平
	 */
	public String getCodeDefineNO() throws Exception
	{
		String prefix="";
		String seqfield="codeDefineNO";
		return this.getGeneratorThree(prefix, seqfield,-1);
	}	
	/**
	 * 得到活动参加人编码
	 * @return
	 * @throws Exception
	 */
	public String getActivitypeoNO() throws Exception{
		String prefix="";
		String seqfield="activitypeoNO";		
		return this.getGeneratorThree(prefix,seqfield,4);
	}
	/**
	 * 得到活动编码
	 * @return
	 * @throws Exception
	 */
	public String getActivityNO() throws Exception{
		String prefix="";
		String seqfield="activityNO";		
		return this.getGeneratorThree(prefix,seqfield,4);
	}
	
	/**
	 * 得到分组信息编码
	 * @return
	 * @throws Exception
	 */
	public String getGroupNO() throws Exception{
		String prefix="";
		String seqfield="groupNO";		
		return this.getGeneratorThree(prefix, seqfield,4);
	}
	
	/**
	 * 得到委员分组编码
	 * @return
	 * @throws Exception
	 */
	public String getUserGroupID() throws Exception{
		String prefix="";
		String seqfield="userGroupNO";		
		return this.getGeneratorThree(prefix, seqfield,4);
	}
	/**
	 * 编码生成方法（一）
	 * 生成规则：
	 * 编码前缀＋YYYY(4位年)＋MM（2位月）＋（2位日）＋(2位时)+(4位顺序号)
	 * 如：A20070822150086
	 * @param String prefix 编码前缀
	 * @param String seqfield 编码字段 
	 * @throws Exception 
	 * @return String	返回的编码值
	 * @author  刘平
	 */	
	private String getGeneratorOne(String prefix,String seqfield) throws Exception
	{
		String seqnum="";
		try
		{
			//得到顺序号(如：0006)
			//seqnum=ServiceLocator.getInstance().getCommonService().CreSequence(seqfield);
			seqnum="0000"+seqnum;
			seqnum=seqnum.substring(seqnum.length()-4, seqnum.length());			
	    }
	    catch(Exception aer)
	    {
	        throw new Exception(aer.getMessage());
	    }
		if(prefix==null) prefix="";
		Calendar c=Calendar.getInstance();
		int year=c.get(Calendar.YEAR);
		int month=c.get(Calendar.MONTH)+1;
		int day=c.get(Calendar.DATE);
		int hour=c.get(Calendar.HOUR_OF_DAY);
		//int minute=c.get(Calendar.MINUTE);
		//int second=c.get(Calendar.SECOND);
		prefix+=year;
		if(month<10){
			String month1="0"+month;
			prefix+=month1;
		}else{
			prefix+=month;
		}
		if(day<10){
			String day1="0"+day;
			prefix+=day1;
		}else{
			prefix+=day;
		}
		if(hour<10){
			String hour1="0"+hour;
			prefix+=hour1;
		}else{
			prefix+=hour;
		}
		prefix+=seqnum;//拼后顺序号字符串
		return prefix;		
	}
	/**
	 * 编码生成方法（二）
	 * 生成规则：
	 * 编码前缀＋YY(2位年)＋MM（2位月）＋（2位日）+(4位顺序号)
	 * 如：A0610280087
	 * @param String prefix 编码前缀
	 * @param String seqfield 编码字段 
	 * @throws Exception 
	 * @return String	返回的编码值
	 * @author  刘平
	 */	
	private String getGeneratorSecond(String prefix,String seqfield) throws Exception
	{
		String seqnum="";
		try
		{
			//得到顺序号(如：0006)
			//seqnum=ServiceLocator.getInstance().getCommonService().CreSequence(seqfield);
			seqnum="0000"+seqnum;
			seqnum=seqnum.substring(seqnum.length()-4, seqnum.length());
	    }
	    catch(Exception aer)
	    {
	        throw new Exception(aer.getMessage());
	    }
		if(prefix==null) prefix="";
		Calendar c=Calendar.getInstance();
		int year=c.get(Calendar.YEAR);
		String stryear=String.valueOf(year);
		stryear=stryear.substring(stryear.length()-2, stryear.length());
		int month=c.get(Calendar.MONTH)+1;
		int day=c.get(Calendar.DATE);
		//int minute=c.get(Calendar.MINUTE);
		//int second=c.get(Calendar.SECOND);
		prefix+=stryear;
		if(month<10){
			String month1="0"+month;
			prefix+=month1;
		}else{
			prefix+=month;
		}
		if(day<10){
			String day1="0"+day;
			prefix+=day1;
		}else{
			prefix+=day;
		}
		prefix+=seqnum;//拼后顺序号字符串
		return prefix;		
	}
	/**
	 * 编码生成方法（三）
	 * 生成规则：编码前缀＋(自定义位数顺序号)
	 * @param String prefix 编码前缀
	 * @param String seqfield 编码字段
	 * @param int digit 位数（-1表示位数不限制） 
	 * @throws Exception 
	 * @return String	返回的编码值
	 * @author  刘平
	 */	
	public String getGeneratorThree(String prefix,String seqfield,int digit) throws Exception
	{
		String seqnum="";
		try
		{
			//得到顺序号(如：0000000018)
			seqnum= ServiceLocator.getInstance().getBaseServiceI().CreSequence(seqfield);
			if(digit!=-1)
			{
				seqnum="000000000"+seqnum;
				seqnum=seqnum.substring(seqnum.length()-digit, seqnum.length());
			}
	    }
	    catch(Exception aer)
	    {
	        aer.printStackTrace();
	    	//throw new Exception(aer.getMessage());
	    }
		prefix+=seqnum;//拼后顺序号字符串
		return prefix;
	}
	/**
	 * 编码生成方法（四）
	 * 生成规则：
	 * 生成规则：编码前缀＋YYYY(4位年)＋MM（2位月）＋（2位日）+(自定义位数顺序号)
	 * @param String prefix 编码前缀
	 * @param String seqfield 编码字段
	 * @param int digit 位数（-1表示位数不限制） 
	 * @throws Exception 
	 * @return String	返回的编码值
	 * @author  刘平
	 */	
	private String getGeneratorFour(String prefix,String seqfield,int digit) throws Exception
	{
		String seqnum="";
		try
		{
			//得到顺序号(如：0000000018)
			//seqnum=ServiceLocator.getInstance().getCommonService().CreSequence(seqfield);
			if(digit!=-1)
			{
				seqnum="00000000000000"+seqnum;
				seqnum=seqnum.substring(seqnum.length()-digit, seqnum.length());				
			}
			if(prefix==null) prefix="";
			Calendar c=Calendar.getInstance();
			int year=c.get(Calendar.YEAR);
			int month=c.get(Calendar.MONTH)+1;
			int day=c.get(Calendar.DATE);
//			int hour=c.get(Calendar.HOUR_OF_DAY);
			//int minute=c.get(Calendar.MINUTE);
			//int second=c.get(Calendar.SECOND);
			prefix+=year;
			if(month<10){
				String month1="0"+month;
				prefix+=month1;
			}else{
				prefix+=month;
			}
			if(day<10){
				String day1="0"+day;
				prefix+=day1;
			}else{
				prefix+=day;
			}
//			if(hour<10){
//				String hour1="0"+hour;
//				prefix+=hour1;
//			}else{
//				prefix+=hour;
//			}
			prefix+=seqnum;//拼后顺序号字符串			
	    }
	    catch(Exception aer)
	    {
	        throw new Exception(aer.getMessage());
	    }
		return prefix;		
	}	
	
	/**
	 * 编码生成方法（五）
	 * 生成规则：编码前缀＋(自定义位数顺序号)
	 * @param String prefix 编码前缀
	 * @param String seqfield 编码字段
	 * @param int digit 位数（-1表示位数不限制） 
	 * @throws Exception 
	 * @return String	返回的编码值
	 * @author  刘平
	 */	
	private String getGeneratorFive(int num,int digit) throws Exception
	{
		String seqnum="";
		try
		{
			//得到顺序号(如：0000000018)
			seqnum= String.valueOf(num);
			if(digit!=-1)
			{
				seqnum="000000000"+seqnum;
				seqnum=seqnum.substring(seqnum.length()-digit, seqnum.length());
			}
			
	    }
	    catch(Exception aer)
	    {
	        throw new Exception(aer.getMessage());
	    }
		return seqnum;		
	}
	/**
	 * 编码生成方法（六）
	 * 生成规则：YYYY(4位年)＋(自定义位数顺序号)
	 * @param String format 顺序号格式
	 * @param int num 顺序号 
	 * @throws Exception 
	 * @return String	返回的编码值
	 * @author  刘平
	 * @param ext 
	 */	
	private String getGeneratorSix(String ext, int num,String format) throws Exception
	{
		String seqnum=ext;
		try
		{
		  String numStr=Util.getInstance().toStrFormat(num, format);
		  seqnum+=numStr;
	    }
	    catch(Exception aer)
	    {
	        throw new Exception(aer.getMessage());
	    }
		
		return seqnum;		
	}
	/**
	 * 将选项数组转换成字符串，并进行分隔符处理
	 * 如：数组[ABCD]转换后为：A|B|C|D
	 * @param options
	 * @return
	 * @throws Exception
	 */
	public String getOptionToStr(char[] options) throws Exception {
		if(options==null||"".equals(options)||options.length<1) {
			return "";
		}
		try {
			StringBuffer str=new StringBuffer();
			for(int i=0;i<options.length;i++) {	
				//log.info("options＝＝："+options[i]);
				str.append(options[i]);
				if((i+1)<options.length) {
					str.append(Constants.CODE_SPLIT_QUESTION);	
				}			
			}
			log.info("选项数组转换成字符串＝"+str.toString());
			return str.toString();
		}catch(Exception aer) {
			aer.printStackTrace();
			log.info("转换试题选项时出错:"+aer.getMessage());
			return "";			
			//throw new Exception("转换试题选项时出错:"+aer.getMessage());
		}
						
	}
}
