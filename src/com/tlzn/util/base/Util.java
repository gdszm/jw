  /**
 * @(#)Util.java 1.23 08/09/12
 * MODIFY MEMO:
 * 
 * 刘平/2008_09_12/主要修改程序注释，因为程序要按照统一的JAVA编码规范
 * 
 */
package com.tlzn.util.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.dao.base.impl.BaseDaoImpl;
import com.tlzn.model.sys.TbaseSequence;
import com.tlzn.pageModel.base.SessionInfo;

/**
 * <p>Description: 系统调用的公共方法的类
 * 
 * @version 	1.0
 * @author 		zhangle
 * @since 		jdk5.0 以上
 */
public class Util {
	private static Util instance;
	BaseDaoI<TbaseSequence> sequDao=new BaseDaoImpl<TbaseSequence>();

	protected final Log log = LogFactory.getLog(getClass());

	/**
	 * @return Returns the log.
	 */
	public Log getLog() {
		return log;
	}

	public static void setInstance(Util instance) {
		Util.instance = instance;
	}

	public static Util getInstance() {
		if (instance != null)
			return instance;
		else
			return instance = new Util();
	}
	/**
	 * 得到访问者IP地址
	 * @param request
	 * @return
	 */
	 public String getIpAddr() {
		 String ip = null;
		 try {			 
			 ip = InetAddress.getLocalHost().getHostAddress();      
		 } catch(Exception error) {
			 error.printStackTrace();
		 }
		 return ip;
	  }	
	/**
	 * 字符串格式化成Date
	 * 
	 * @param strdate
	 * 
	 * @throws AppException
	 * 
	 * @return Date
	 */
	public Date getFormatDate(String strdate) throws Exception
	{
		if(strdate==null||strdate.equals(""))
		return null;
			try 
			{
				//log.info("strdate===:"+strdate.length());
				String pattern = "";
				if(strdate.length()>18)
				{
					pattern = "yyyy-MM-dd kk:mm:ss";
				}
				else
				{
					pattern = "yyyy-MM-dd";
				}
				SimpleDateFormat formater = new SimpleDateFormat(pattern);				
				return formater.parse(strdate);
			} catch (ParseException error)
			{
				log.error("字符串格式化成Date时程序出错：(Util类中的getFormatDate方法)"
						+ error.getMessage());
				return null;
			}
	}
	/**
	 * 字符串格式化成Date
	 * 
	 * @param strdate
	 * 
	 * @throws Exception
	 * 
	 * @return Date
	 */
	public Date getFormatDateNotException(String strdate)
	{
		if(strdate==null||strdate.equals(""))
		return null;
			try 
			{
				//log.info("strdate===:"+strdate.length());
				String pattern = "";
				if(strdate.length()>18)
				{
					pattern = "yyyy-MM-dd kk:mm:ss";
				}
				else
				{
					pattern = "yyyy-MM-dd";
				}
				SimpleDateFormat formater = new SimpleDateFormat(pattern);				
				return formater.parse(strdate);
			} catch (ParseException error)
			{
				log.error("字符串格式化成Date时程序出错：(Util类中的getFormatDate方法)"
						+ error.getMessage());
				return null;
			}
	}
	/**
	 * 过滤文件名特殊字符
	 * 
	 * @param str
	 * 
	 * @throws Exception
	 * 
	 * @return	String
	 */
	public String filtKey(String str) throws Exception
	{
		if(str==null||str.equals(""))
			return "";
				try 
				{
					  String reg="[,./\\;'？?：:*！!  ~/]";   
					  Pattern   p   =   Pattern.compile(reg);   
					  Matcher   m   =   p.matcher(str);
					  return m.replaceAll("");
				} catch (Exception error)
				{
					log.error("过滤文件名特殊字符时程序出错：(Util类中的filtKey方法)"
							+ error.getMessage());
					return str;
				}
	}
	
	/**
	 * 与系统日期比较
	 * 
	 * @param strdate
	 * 
	 * @throws Exception
	 * 
	 * @return boolean
	 */
	public boolean  compareSysDate(String strdate) throws Exception
	{
		if(strdate==null||strdate.equals(""))
		return false;
		Date today=new Date();
		Date compDate=getFormatDateNotException(strdate);
		if(compDate.getTime()<=today.getTime()){
			return true;
		}else{
			return false;
		}
		
	}

	/**
	 * 日期字符串转换成指定格式日期字符串格式
	 * 
	 * @param date	需要转化的字符串日期
	 * @param formate	转换的格式
	 * 
	 * @throws Exception
	 * 
	 * @return	String	返回转换后的日期字符串
	 */
	public String getFormatString (String date,String formate){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat(formate);
		Date date2=null;
		try {
			if(date!=null) date2 = format.parse(date);
		} catch (ParseException e) {
			//log.info("转换日期格式失败@");
			return date;
		}
		String date3 = "";
		if(date2!=null)
		{
			date3 = format2.format(date2);
		}		
		return date3;
	}
	
	/**
	 * 将得到的日期转换成相应的 String 的类型
	 * 
	 * @param date	需要转化的日期
	 * @param formate	转换的格式
	 * 
	 * @throws Exception
	 * 
	 * @return	String	返回转换后的日期字符串
	 */
	public String getFormatDate(Date date,String formate) throws Exception
	{
	  try
		{
			//"yyyy-MM-dd"
			if(date==null || date.equals("") || formate==null || formate.equals("")) return "";
			SimpleDateFormat formatter;
		    formatter = new SimpleDateFormat(formate);
		    return formatter.format(date);
		}
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();
			log.error("日期转换时程序出错：(Util类中的getFormatDate方法)"+ ex.getMessage());
			return "";
		}
	}
	/**
	 * 根据格式将转换日期
	 * 
	 * @param date	需要转化的日期
	 * @param format	转换的格式
	 * 
	 * @throws Exception
	 * 
	 * @return	Date	返回转换后的日期
	 */
	public Date getFormat(Date date,String format) throws Exception
	{
	  try
		{
			//"yyyy-MM-dd"
			if(date==null || date.equals("") || format==null || format.equals("")) return null;
			SimpleDateFormat formatter;
		    formatter = new SimpleDateFormat(format);
		    return formatter.parse(formatter.format(date));
		}
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();
			log.error("日期转换时程序出错：(Util类中的getFormat方法)"+ ex.getMessage());
			return null;
		}
	}	
	/**
	 * List对象集合转换成Set对象集合
	 * 
	 * @param list	需要转换的list
	 * 
	 * @throws Exception
	 * 
	 * @return	Set	
	 */
	public Set getListToSet(List list) throws Exception
	{
		    Set set=new HashSet();
			if(list==null || list.equals("") || list.size()<1) return set;			
			Iterator itor=list.iterator();
			while(itor.hasNext())
			{
				set.add(itor.next());
			}
			return set;
	}	

	/**
	 * 得到访问得基本路径（如：http://www.nmtlzn.com/）
	 * 
	 * @param request
	 * 
	 * @return	String	得到访问得基本路径
	 */
	public String getBaseUrl(HttpServletRequest request) {
		//if (Constants.BASEURL != null && !"".equals(Constants.BASEURL.trim()))
			//return Constants.BASEURL;
		//else {
			String port = request.getLocalPort() + "";
			if (port != null && !"".equals(port.trim()) && !"80".equals(port))
				port = ":" + port;
			else
				port = "";
			String content = request.getContextPath();
			System.out.println(content);

			return Constants.BASEURL = "http://" + request.getServerName()
					+ port + content + "/";
		//}
	}
	/**
	 * 根据指定的位数得到随机数
	 * 
	 * @param digit
	 * 
	 * @return	String	指定的位数得到随机数
	 */
	public String getRandom(int digit)
	{
		Random random = new Random();				
		String str=String.valueOf(random.nextLong());
		str=str.substring(str.length()-digit, str.length());
		return str;
	}
	/**
	 * 截取字符串
	 * 说明：根据指定字符串的长度来截取
	 * 
	 * @param str 需要被截取的字符串
	 * @param number ArrayList
	 * 
	 * @throws Exception
	 * 
	 * @return String	截取指定字符串
	 */
	public String subString(String str, int number) throws Exception
	{
		if(str==null||"".equals(str.trim())||"null".equals(str)) return "";
		if (str.length() > number) return str.substring(0, number);
		else return str;
	}
	
	/**
	* 截取带有HTML代码字符串
	* @param str
	* @param len
	* @return
	 * @throws Exception 
	* @throws UnsupportedEncodingException
	*/
    public String subStringHTML(String param,int length,String endWith){
        
        
        StringBuffer str = new StringBuffer();
        int n = 0;

        char temp;

        boolean isCode = false; //是不是HTML代码

        boolean isHTML = false; //是不是HTML特殊字符,如

        for (int i = 0; i < param.length(); i++) {

          temp = param.charAt(i);
                
          if (temp == '<') {

            isCode = true;

          }

          else if (temp == '&') {

            isHTML = true;

          }

          else if (temp == '>' && isCode) {

            isCode = false;
            continue;
          }

          else if (temp == ';' && isHTML) {

            isHTML = false;
            continue;
          }



          if (!isCode && !isHTML) {

            n = n + 1;

            //UNICODE码字符占两个字节

            if ( (temp + "").getBytes().length > 1) {

              n = n + 1;
              

            }
            str.append(temp);
           
          }
          if (n >= length) {

            break;

          }
        }
        
       str.append(endWith);

        return str.toString();

      }

	/**
	 * 处理CheckBox复选框多选
	 * 说明：checkid的值在allcheck集合中迭代
	 * 相等则返回true
	 * 
	 * @param checkid String
	 * @param allcheck ArrayList
	 * 
	 * @throws Exception
	 * 
	 * @return boolean
	 */
	public boolean isIndexOf(String checkid, ArrayList allcheck)
			throws Exception {
		if (allcheck == null || allcheck.size() < 1 || checkid == null
				|| "".equals(checkid))
			return false;
		try {
			for (int i = 0; i < allcheck.size(); i++) {
				if (checkid.trim().equals(allcheck.get(i).toString()))
					return true;
			}
			return false;
		} catch (java.lang.Exception error) {
			throw new Exception("在过滤checkbox时遇到不可预料的出错："
					+ error.getMessage() + "重试无效后请联系管理员！");
		}
	}

	/**
	 * 返回原始输入格式
	 * 
	 * @param content
	 * 
	 * @return	String
	 */
	public String getInputFormat(String content) throws Exception {
		if (content == null || "".equals(content)) {
			return "";
		}
		StringBuffer buff = new StringBuffer(content.length() + 10);
		char ch = '　';
		for (int i = 0; i < content.length(); i++) {
			ch = content.charAt(i);
			if (ch == '\n') {
				buff.append("<br>");
			} else {
				if (ch == ' ')
					buff.append("&nbsp;");
				else
					buff.append(ch);
			}
		}
		return buff.toString();
	}

	/**
	 * 生成静态页面公用方法
	 * 
	 * @param srcURL String 生成静态页面的原路径
	 * @param destURL String 生成静态页面的目标保存路径
	 * @param beginTag String 截取输入流中的开始标记，可以为空
	 * @param endTag String 截取输入流中的结束标记，可以为空
	 * 
	 * @throws Exception
	 */
	public void buildHTML(String srcURL, String destURL, String beginTag,
			String endTag) throws Exception {
		if (srcURL == null || "".equals(srcURL.trim()))
			throw new Exception("生成静态页面的原路径不能为空！");
		if (destURL == null || "".equals(destURL.trim()))
			throw new Exception("生成静态页面的目标保存路径不能为空！");
		BufferedReader bfread = null;
		FileOutputStream fileOut = null;
		boolean isBegin = false;
		String inputLine;
		try {
			URL url = new URL(srcURL);
			URLConnection conn = url.openConnection();
			bfread = new BufferedReader(new InputStreamReader(conn
					.getInputStream(),Constants.ENCODING));
			fileOut = new FileOutputStream(destURL);

			while ((inputLine = bfread.readLine()) != null) {
				if (beginTag != null && !"".equals(beginTag)
						&& beginTag.equals(inputLine))
					isBegin = true;
				if (endTag != null && !"".equals(endTag)
						&& endTag.equals(inputLine))
					isBegin = false;
				if (isBegin) {
					//System.out.println("readLine string::"+inputLine);
					fileOut.write(inputLine.getBytes());
					fileOut.write("\r\n".getBytes());
				}
			}
		} catch (Exception error)
		{
			throw new Exception("生成静态页面程序出错：" + error.getMessage()
					+ "重试无效后请联系管理员！");
		} finally {
			try {
				fileOut.close();
				bfread.close();
			} catch (Exception clerr) {
			}
		}
	}

	/**
	 * 根据文件名和文件路径删除文件
	 * 
	 * @param filePath	需要删除的文件路径
	 * @param fileName	需要删除的文件名
	 * 
	 * @throws Exception
	 */
	public static void delFile(String pathname)
			throws Exception {
		try {
			if (pathname == null || "".equals(pathname.trim()))
				throw new Exception("删除文件时文件是空的，文件不能进行删除！");
			File file = new File(Constants.ROOTPATH+pathname.trim());
			if (file.exists())//检查是否存在
			{
				file.delete();//删除文件  
			}
		} catch (Exception error) {
			throw new Exception("删除文件时程序出错：" + error.getMessage()
					+ "重试无效后请联系管理员！");
		}
	}
	/**
	 * 检查上传的文件是否存在
	 * 
	 * @param myfile
	 * 
	 * @throws Exception
	 * 
	 * @return	boolean
	 */
	public boolean isUploadFileExist(String myfile) throws Exception
	{
		try
		{
			if (myfile == null || "".equals(myfile.trim()))
			log.error("检查上传的文件是否存在时出错(Util类中的isUploadFileExist)：文件是空的！");
			File file = new File((Constants.ROOTPATH+myfile).trim());//
			if (file.exists())//存在则返回真
			return true;
			file = new File(myfile);
			if (file.exists())//存在则返回真
			return true;			
			else return false;
		}
		catch (Exception error)
		{
			log.error("检查上传的文件是否存在时出错(Util类中的isUploadFileExist)："+error.getMessage());
			return false;
		}		
	}
	/**
	 * 得到当前年中的当前月的第一天
	 * 返回格式为：yyyy-mm-dd
	 * 
	 * @throws Exception
	 * 
	 * @return	String	当前年中的当前月的第一天
	 */
	public String getActualMinimum() throws Exception {
		try {
			//取得系统时间每月的第一天和最后一天作为初始起始时间和终止时间值

			int yyyy, mm, dd, mindd;
			Calendar c = Calendar.getInstance();
			yyyy = c.get(Calendar.YEAR);
			mm = c.get(Calendar.MONTH) + 1;
			mindd = c.getActualMinimum(Calendar.DAY_OF_MONTH);//取得系统时间每月的第一天

			Integer yyyyInt = new Integer(yyyy);
			Integer mmInt = new Integer(mm);
			Integer minddInt = new Integer(mindd);

			String yyyyStr = yyyyInt.toString();
			String mmStr = mmInt.toString();
			String minddStr = minddInt.toString();
			String begindateattri = yyyyStr + "-" + mmStr + "-" + minddStr;//显示格式：yyyy-mm-dd
			return begindateattri;
		} catch (Exception error) {
			log.error("得到当前年中的当前月的第一天时程序出错：(Util类中的getActualMinimum方法)"
					+ error.getMessage());
			throw new Exception("得到当前年中的当前月的第一天时程序出错：" + error.getMessage()
					+ "重试无效后请联系管理员！");
		}

	}
	/**
	 * 得到指定日期月最后一天
	 * @param yyyMMdd
	 * @return
	 * @throws Exception
	 */
	public String getMaxDay(String yyyyMMdd) throws Exception {
		try {
			if(yyyyMMdd==null||"".equals(yyyyMMdd)) return "";
			yyyyMMdd=yyyyMMdd.replaceAll("-","");
			String yyyy_MM_dd=yyyyMMdd.substring(0,4)+"-"+yyyyMMdd.substring(4,6)+"-"+yyyyMMdd.substring(6,8);
			int maxdd;
			Calendar c = Calendar.getInstance();
			c.setTime(this.toDate(yyyy_MM_dd));
			maxdd = c.getActualMaximum(Calendar.DAY_OF_MONTH);//取得系统时间每月的最后一天
			//log.info("指定日期月最后一天:"+maxdd);
			return maxdd+"";
		} catch (Exception error) {
			error.printStackTrace();
			log.error("指定日期月最后一天时程序出错：(Util类中的getMaxDay方法)"
					+ error.getMessage());
			throw new Exception("指定日期月最后一天时程序出错：" + error.getMessage()
					+ "重试无效后请联系管理员！");
		}
	}
	/**
	 * 得到当前年中的当前月的最后一天
	 * 返回格式为：yyyy-mm-dd
	 * 
	 * @throws Exception
	 * 
	 * @return	String	当前年中的当前月的最后一天
	 */
	public String getActualMaximum() throws Exception {
		try {
			//取得系统时间每月的第一天和最后一天作为初始起始时间和终止时间值

			int yyyy, mm, dd, maxdd;
			Calendar c = Calendar.getInstance();
			yyyy = c.get(Calendar.YEAR);
			mm = c.get(Calendar.MONTH) + 1;
			maxdd = c.getActualMaximum(Calendar.DAY_OF_MONTH);//取得系统时间每月的最后一天

			Integer yyyyInt = new Integer(yyyy);
			Integer mmInt = new Integer(mm);
			Integer maxddInt = new Integer(maxdd);

			String yyyyStr = yyyyInt.toString();
			String mmStr = mmInt.toString();
			String maxddStr = maxddInt.toString();

			String enddateattri = yyyyStr + "-" + mmStr + "-" + maxddStr;//显示格式：yyyy-mm-dd
			return enddateattri;
		} catch (Exception error) {
			log.error("得到当前年中的当前月的最后一天时程序出错：(Util类中的getActualMaximum方法)"
					+ error.getMessage());
			throw new Exception("得到当前年中的当前月的最后一天时程序出错：" + error.getMessage()
					+ "重试无效后请联系管理员！");
		}
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param begindate
	 * @param enddate
	 * 
	 * @throws Exception
	 * 
	 * @return	int	返回两个日期之间相差的天数
	 */
	public int getdiffdates(Date begindate, Date enddate) throws Exception {
		try {
			int result =(int)((begindate.getTime()-enddate.getTime())/60/60/1000/24);
			log.info(begindate+"-"+enddate+"相差的天数:"+result+" 相差的秒："+(begindate.getTime()-enddate.getTime()));
			return result;

		} catch (Exception error) {
			log.error("计算两个日期之间相差的天数时程序出错：(Util类中的getdiffdates方法)"
					+ error.getMessage());
			throw new Exception("计算两个日期之间相差的天数时程序出错：" + error.getMessage()
					+ "重试无效后请联系管理员！");
		}
	}
	/**
	 * 将字符串中的汉字进行URLEncoder编码
	 * 
	 * @param str
	 * 
	 * @throws Exception
	 * 
	 * @return	String	返回进行URLEncoder编码的字符串
	 */
	public String getStrEncoder(String str) throws Exception
	{
		if(str==null||str.length()<1) return "";
		try
		{
			StringBuffer strbuff=new StringBuffer();
		    char[] chr = str.toCharArray();
		    for (int i = 0; i < chr.length; i++)
		    {
		    	if (str.charAt(i)>0x80)
		    	{
		    		strbuff.append(URLEncoder.encode(String.valueOf(str.charAt(i)), "UTF-8"));
		    	//System.out.println(String.valueOf(s.charAt(i)));
		    	}
		    	else strbuff.append(str.charAt(i));
		    }
		    return strbuff.toString();
		}
		catch(Exception error)
		{
			log.error("将字符串中的汉字进行URLEncoder编码时程序出错：(Util类中的getStrEncoder方法)"
					+ error.getMessage());
			return "";
		}
		
	}
	
	  /**
	   * 取得当前系统时间
	   * 
	   * @return String	返回当前系统时间
	   */
	    public String getNowDateTime() {
	        java.util.Date nowDate = new Date();
	        java.text.SimpleDateFormat DateFormat = new SimpleDateFormat(
	                "yyyy-MM-dd kk:mm:ss");
	        return DateFormat.format(nowDate);
	    }
	  /**
	   * 根据输入的时间得到前天的时间
	   * 
	   * @param n 输入天数
	   * 
	   * @return	String	根据参数，得到当前时间
	   */
		public static String getNDayAfterCurrentDate(int n) {
			Calendar c = Calendar.getInstance();
			c.add(c.DAY_OF_MONTH, -n);
			return "" + c.get(c.YEAR) + "-" + (c.get(c.MONTH) + 1) + "-" + c.get(c.DATE);
			} 
	  /**
	   * 根据输入的时间得到之后的时间
	   * 
	   * @param date 输入的时间
	   * 
	   * @return	Date
	   */
		public Date getYesterday(Date date) {
			return new Date(date.getTime()+ 86400000);
		}
		/**
		 * 字符串编码
		 * 
		 * @param str
		 * @param request
		 * 
		 * @throws UnsupportedEncodingException
		 * 
		 * @return	String
		 */
		public String encode(String str,HttpServletRequest request) throws UnsupportedEncodingException
		{
			String codedfilename = null;
			//str=str.replaceAll("\n|\r", " ").trim();
			/*String agent = request.getHeader("USER-AGENT");
			if (null != agent && -1 != agent.indexOf("MSIE"))
			{
				codedfilename = URLEncoder.encode(str,"UTF-8");
//					String prefix = fileName.lastIndexOf(".") != -1 ? fileName
//						.substring(0, fileName.lastIndexOf(".")) : fileName;
//					String extension = fileName.lastIndexOf(".") != -1 ? fileName
//						.substring(fileName.lastIndexOf(".")) : "";
//					int limit = 150 - extension.length();
//					if (codedfilename.length() > limit) {
//						codedfilename = java.net.URLEncoder.encode(prefix.substring(0, Math.min(
//								prefix.length(), limit / 9)), "UTF-8");
//						codedfilename = codedfilename + extension;
//					}
			} else if (null != agent && -1 != agent.indexOf("Mozilla")) {
				codedfilename = "=?UTF-8?B?"+(new String(Base64.encodeBase64(str.getBytes("UTF-8"))))+"?=";
			} else {
				codedfilename = str;
			}*/
			return codedfilename;
		}
		/**
		 * 转换成字符串统一处理
		 * 
		 * @param obj
		 * 
		 * @throws Exception
		 * 
		 * @return	String	返回String
		 */
	  public String toString(Object obj) throws Exception
	  {
		if(obj==null||"".equals(obj)||"null".equals(obj)) return "";
		return String.valueOf(obj);
	  }
	  /**
	   * 对Double空对象进行格式化
	   * 
	   * @param obj
	   * 
	   * @return Double	返回Double
	   */
	  public Double nullVal(Double obj)
	  {
		  if(obj==null||"".equals(obj)) return 0.00;
		  else return obj;
	  }
	  /**
	   * 对Integer空对象进行格式化
	   * 
	   * @param obj
	   * 
	   * @return Integer	返回Integer
	   */
	  public Integer nullVal(Integer obj)
	  {
		  if(obj==null||"".equals(obj)) return 0;
		  else return obj;
	  }
	  /**
	   * 对String空对象进行格式化
	   * @param obj String对象
	   * @return String 返回String对象
	   */
	  public String nullVal(String obj)
	  {
		  if(obj==null||"".equals(obj)) return "";
		  else return obj;
	  }	  
	 
	 
	  /**
	   * 对金额进行格式化
	   * @param objmoney
	   * @return	double	返回格式化后的金额
	   */
	  public double formatMny(Object objmoney)
	  {
		  double money=0.00;
		  try
		  {			  
			  money=Double.valueOf(objmoney.toString());
			  //log.info("金额＝"+money);
			  BigDecimal bd=new BigDecimal(money);   
			  bd=bd.setScale(2,BigDecimal.ROUND_HALF_UP);
			  return bd.doubleValue();
		  }
		  catch(Exception err)
		  {
			  //log.error("对金额进行格式化时出错："+err.getMessage());
			  return 0.00;
		  }
	  }	
	  /**
	   * 对数量进行格式化
	   * @param objnum 
	   * @return	double	返回格式化后的数量
	   */
	  public double formatNum(Object objnum)
	  {
		  double money=0.00;
		  try
		  {			  
			  money=Double.valueOf(objnum.toString());
			  //log.info("金额＝"+money);
			  BigDecimal bd=new BigDecimal(money);   
			  bd=bd.setScale(2,BigDecimal.ROUND_HALF_UP);
			  return bd.doubleValue();
		  }
		  catch(Exception err)
		  {
			  //log.error("对数量进行格式化时出错："+err.getMessage());
			  return 0.00;
		  }
	  }	  
	  
	  /**
	   * 对金额进行格式化
	   * 
	   * @param money
	   * 
	   * @return	String	返回格式化后的金额
	   */
	  public String formatMnyStr(double money)
	  {
		  DecimalFormat format=new DecimalFormat("###,##0.00");
		  return format.format(money);
	  }	 	  
	  
	  //得到本周周一日期
	  /**
	   * 得到本周周一日期
	   * 
	   * @return	String	返回本周周一日期
	   */
		public String getMondayOfThisWeek() {
	        String strTemp = "";
	        Calendar c = Calendar.getInstance();
	        int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
	        if (dayofweek == 0)
	            dayofweek = 7;
	        c.add(Calendar.DATE, -dayofweek + 1);
	        strTemp = c.get(1) + "-";
	        if (c.get(2) + 1 < 10)
	            strTemp += "0";
	        strTemp = strTemp + (c.get(2) + 1) + "-";
	        if (c.get(5) < 10)
	            strTemp += "0";
	        strTemp += c.get(5);
	        return strTemp;
	    }
		//得到本周周七日期
	   /**
	   * 得到本周周七日期
	   * 
	   * @return	String	返回本周周七日期
	   */
		public String getSundayOfThisWeek() {
			Calendar c = Calendar.getInstance();
			int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
			if (dayofweek == 0)
			dayofweek = 7;
			c.add(Calendar.DATE, -dayofweek + 7);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(c.getTime());
		}
		
		/**
	   * 将字符串日期转换为Date日期
	   * 
	   * @param strdate	需要转换的字符串日期
	   * 
	   * @return	Date	返回将字符串日期转换为Date日期
	   */
		public Date toDate(String strdate) throws ParseException{
			Date date=null;
			if(strdate==null || strdate.equals("")) return date;
			String pattern="yyyy-MM-dd";
			if(strdate.length()>18) {
				pattern = "yyyy-MM-dd kk:mm:ss";
			}
			SimpleDateFormat format=new SimpleDateFormat(pattern);
			date=(Date)format.parse(strdate);
			return date;				
		}
	    
		/**
		   * 得到所传日期的前day天或后day天的日期
		   * 
		   * @param date	需要转换的日期
		   * @param day	天数
		   * 
		   * @return	Date	返回所传日期的前day天或后day天的日期
		   */
		public Date getAddMonthDate(Date date,int day){
		    if(date==null||"".equals(date)) return null;
			Calendar cal=Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, day);
			return cal.getTime();
		}
		
		//根据给定日期，得到其给定日期本周周一日期
		/**
		   * 得到根据给定日期，得到其给定日期本周周一日期
		   * 
		   * @param	date	给定日期
		   * 
		   * @return	String	返回给定日期本周周一日期
		   */
		public String getMondayOfThisWeek(Date date) {				
			String strTemp = "";
	        Calendar c = Calendar.getInstance();
	        c.clear();
			c.set(Calendar.YEAR,date.getYear()); //year 为 int 
			c.set(Calendar.MONTH,date.getMonth()-1);//注意,Calendar对象默认一月为0 
			c.set(Calendar.DAY_OF_MONTH,date.getDate());
	        int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
	        if (dayofweek == 0)
	            dayofweek = 7;
	        c.add(Calendar.DATE, -dayofweek + 1);
	        strTemp = c.get(1) + "-";
	        if (c.get(2) + 1 < 10)
	            strTemp += "0";
	        strTemp = strTemp + (c.get(2) + 1) + "-";
	        if (c.get(5) < 10)
	            strTemp += "0";
	        strTemp += c.get(5);
	        //System.out.println("周一为===="+strTemp);
	        return strTemp;
	    }
		
		// 根据给定日期，得到其给定日期本周周七日期
		/**
		   * 根据给定日期，得到其给定日期本周周七日期
		   * 
		   * @param	date	给定日期
		   * 
		   * @return	String	返回给定日期本周周七日期
		   */
		public String getSundayOfThisWeek(Date date) {
			Calendar c = Calendar.getInstance();
			c.clear();
			c.set(Calendar.YEAR,date.getYear()); //year 为 int 
			c.set(Calendar.MONTH,date.getMonth()-1);//注意,Calendar对象默认一月为0 
			c.set(Calendar.DAY_OF_MONTH,date.getDate());
			int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
			if (dayofweek == 0)
			dayofweek = 7;
			c.add(Calendar.DATE, -dayofweek + 7);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			//System.out.println("周七为===="+sdf.format(c.getTime()));
			return sdf.format(c.getTime());
		}
		
		//得到本月当前日期
	   /**
	    * 得到本月当前日期
	    * 
	    * @return	String	返回本月当前日期
	    */
		public String getSystemDate(){
			//得到本月当前日期有两种方法
			//方法一
			Calendar c=Calendar.getInstance();
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");		
			return format.format(c.getTime());
		}
		
		public String getformatMnyStr(double money)
		{
			DecimalFormat format=new DecimalFormat("###,##0");
			return format.format(money);
		}
/************************************************************************************************************
 * 									使用正则表达式进行常用验证 －－－－begin
 ***********************************************************************************************************/
	  public  boolean startCheck(String reg,String string)   
	    {   
	        boolean tem=false;   	           
	        Pattern pattern = Pattern.compile(reg);   
	        Matcher matcher=pattern.matcher(string);	           
	        tem=matcher.matches();   
	        return tem;   
	    }   	       
	    
	    /**
	     * 检验整数,适用于正整数、负整数、0，负整数不能以-0开头
	     * 
	     * @param nr
	     * 
	     * @return	boolean
	     */
	    public boolean checkNr(String nr)   
	    {   
	        String reg="^(-?)[1-9]+\\d*|0";   
	        return startCheck(reg,nr);   
	    }
	    /**
	     * 检验金额,正负小数
	     * 
	     * @param nr
	     * 
	     * @return	boolean
	     */
	    public boolean checkMny(String nr)   
	    {   
	    	if(nr==null||"".equals(nr)) return false;
	    	boolean retflg=false;
	        String reg="^(-?)[1-9|,|0.00,|E]+\\d*|0"; 
	        char[] strc=nr.toCharArray();
	        if(strc!=null&&strc.length>1)
	        {
	        	if("0".equals(String.valueOf(strc[0])))
	        	{
	        		if(!".".equals(String.valueOf(strc[1]))) return false;
	        	}
	        }
	        retflg=startCheck(reg,nr);
	        if(!retflg) {
	        	return retflg;
	        } else {
		        double mny=0.0;
		    	try
		    	{
		    		mny=Double.valueOf(nr);
		    		if(mny>100000000)
		    		{
		    			retflg=false;
		    		}
		    	} catch(Exception err) { return false;}
		    	return retflg;
	        }	    	  
	    }
	    /**
	     * 检验百分比
	     * 
	     * @param nr
	     * 
	     * @return	boolean
	     */
	    public boolean checkPercent(String nr)   
	    {   
	    	String reg="^(-?)[1-9|,|0.00,|E,|%,|-]+\\d*|0";
	    	boolean retflg=false;
	    	char[] strc=nr.toCharArray();
	        if(strc!=null&&strc.length>1)
	        {
	        	if("0".equals(String.valueOf(strc[0])))
	        	{
	        		if(!".".equals(String.valueOf(strc[1]))) return false;
	        	}
	        }
	    	retflg=startCheck(reg,nr);
	        if(!retflg) {
	        	return retflg;
	        } else {
		        double mny=0.0;
		    	try
		    	{
		    		mny=Double.valueOf(nr.replace("%",""));
		    		if(mny>100000000)
		    		{
		    			retflg=false;
		    		}
		    	} catch(Exception err) { return false;}
		    	return retflg;
	        }
	    }	    
	    /**  
	     * 手机号码验证,11位，不知道详细的手机号码段，只是验证开头必须是1和位数  
	     * 
	     * @param cellPhoneNr
	     * 
	     * @return	boolean
	     * */  
	    public boolean checkCellPhone(String cellPhoneNr)   
	    {   
	        String reg="^[1][\\d]{10}";   
	        return startCheck(reg,cellPhoneNr);   
	    }   	       
	    /**  
	     * 检验空白符  
	     * 
	     * @param line
	     *  
	     * @return	boolean
	     * */  
	    public boolean checkWhiteLine(String line)   
	    {   
	        String regex="(\\s|\\t|\\r)+";   
	           
	        return startCheck(regex,line);   
	    }   
	    /**  
	     * 检查EMAIL地址  
	     * 用户名和网站名称必须>=1位字符  
	     * 地址结尾必须是以com|cn|com|cn|net|org|gov|gov.cn|edu|edu.cn结尾  
	     * 
	     * @param email
	     *  
	     * @return	boolean
	     * */  
	    public boolean checkEmailWithSuffix(String email)   
	    {   
	        String regex="\\w+\\@\\w+\\.(com|cn|com.cn|net|org|gov|gov.cn|edu|edu.cn)";   
	           
	        return startCheck(regex,email);   
	    }   
	    /**  
	     * 检查EMAIL地址  
	     * 用户名和网站名称必须>=1位字符  
	     * 地址结尾必须是2位以上，如：cn,test,com,info  
	     * 
	     * @param email
	     *  
	     * @return	boolean 
	     * */  
	    public boolean checkEmail(String email)   
	    {   
	        String regex="\\w+\\@\\w+\\.\\w{2,}";   
	           
	        return startCheck(regex,email);   
	    }   
	       
	    /**  
	     * 检查邮政编码(中国),6位，第一位必须是非0开头，其他5位数字为0-9  
	     * 
	     * @param postCode
	     *  
	     * @return	boolean
	     */  
	    public boolean checkPostcode(String postCode)   
	    {   
	        String regex="^[1-9]\\d{5}";   
	        return startCheck(regex,postCode);   
	    }   	       
	    /**  
	     * 检验用户名  
	     * 取值范围为a-z,A-Z,0-9,"_",汉字，不能以"_"结尾  
	     * 用户名有最小长度和最大长度限制，比如用户名必须是4-20位  
	     * 
	     * @param username
	     * @param min
	     * @param max
	     * 
	     * @return	boolean
	     */  
	    public boolean checkUsername(String username,int min,int max)   
	    {   
	        String regex="[\\w\u4e00-\u9fa5]{"+min+","+max+"}(?<!_)";   
	        return startCheck(regex,username);   
	    }   
	    /**  
	     * 检验用户名  
	     * 取值范围为a-z,A-Z,0-9,"_",汉字，不能以"_"结尾  
	     * 有最小位数限制的用户名，比如：用户名最少为4位字符  
	     * 
	     * @param username
	     * @param min
	     * 
	     * @return	boolean
	     */  
	    public boolean checkUsername(String username,int min)   
	    {   
	        //[\\w\u4e00-\u9fa5]{2,}?   
	        String regex="[\\w\u4e00-\u9fa5]{"+min+",}(?<!_)";   
	           
	        return startCheck(regex,username);   
	    }   	       
	    /**  
	     * 检验用户名  
	     * 取值范围为a-z,A-Z,0-9,"_",汉字  
	     * 最少一位字符，最大字符位数无限制，不能以"_"结尾 
	     * 
	     * @param username
	     * 
	     * @return	boolean
	     */  
	    public boolean checkUsername(String username)   
	    {   
	        String regex="[\\w\u4e00-\u9fa5]+(?<!_)";   
	        return startCheck(regex,username);   
	    }   
	       
	    /**  
	     *  查看IP地址是否合法
	     *  
	     * @param ipAddress
	     * 
	     * @return	boolean
	     */  
	    public boolean checkIP(String ipAddress)   
	    {   
	        String regex="(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\." +   
	                     "(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\." +   
	                     "(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\." +   
	                     "(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])";   
	           
	        return startCheck(regex,ipAddress);   
	    }   
	       
	    /**  
	     * 验证国内电话号码  
	     * 格式：010-67676767，区号长度3-4位，必须以"0"开头，号码是7-8位  
	     * 
	     * @param phoneNr
	     * 
	     * @return	boolean
	     */  
	    public boolean checkPhoneNr(String phoneNr)   
	    {   
	        String regex="^[0]\\d{2,3}\\-\\d{7,8}";   
	           
	        return startCheck(regex,phoneNr);   
	    }   	       
	    /**  
	     * 验证国内电话号码  
	     * 格式：6767676, 号码位数必须是7-8位,头一位不能是"0"  
	     * 
	     * @param phoneNr
	     * 
	     * @return	boolean
	     */  
	    public boolean checkPhoneNrWithoutCode(String phoneNr)   
	    {   
	        String reg="^[1-9]\\d{6,7}";   
	           
	        return startCheck(reg,phoneNr);   
	    }   	       
	    /**  
	     * 验证国内电话号码  
	     * 格式：0106767676，共11位或者12位，必须是0开头  
	     * 
	     * @param phoneNr
	     * 
	     * @return	boolean 
	     */  
	    public boolean checkPhoneNrWithoutLine(String phoneNr)   
	    {   
	        String reg="^[0]\\d{10,11}";   
	           
	        return startCheck(reg,phoneNr);   
	    }   
	       
	    /**  
	     * 验证国内身份证号码：15或18位，由数字组成，不能以0开头  
	     * 
	     * @param idNr
	     * 
	     * @return	boolean
	     */  
	    public boolean checkIdCard(String idNr)   
	    {   
	        String reg="^[1-9](\\d{14}|\\d{17})";   
	           
	        return startCheck(reg,idNr);   
	    }   
	       
	    /**  
	     * 网址验证<br>  
	     * 符合类型：<br>  
	     * http://www.test.com<br>  
	     * http://163.com 
	     * 
	     * @param url
	     * 
	     * @return	boolean
	     */  
	    public boolean checkWebSite(String url)   
	    {   
	        //http://www.163.com   
	        String reg="^(http)\\://(\\w+\\.\\w+\\.\\w+|\\w+\\.\\w+)";   
	           
	        return startCheck(reg,url);   
	    } 
/************************************************************************************************************
 * 									使用正则表达式进行常用验证 －－－－end
 ***********************************************************************************************************/	  
	    
	    /**
		 * 返回整形数的指定长度，指定填充因子的字符串
		 * 
		 * @param number	指定整形数
		 * @param destLength	指定长度
		 * @param paddedChar	指定填充因子
		 * 
		 * @return	String 如果该整形数长度大于指定长度。截到一部分，如果小于指定长度，左填充指定填充因子
		 */
		public static String formatNumber(int number, int destLength, char paddedChar)
		{
			String oldString = String.valueOf(number);
			StringBuffer newString = new StringBuffer("");
			int oldLength = oldString.length();
			if (oldLength > destLength)
			{
				newString.append(oldString.substring(oldLength - destLength));
			}
			else if (oldLength == destLength)
			{
				newString.append(oldString);
			}
			else
			{
				for (int i = 0; i < destLength - oldLength; i++)
				{
					newString.append(paddedChar);
				}
				newString.append(oldString);
			}
			return newString.toString();
		}
	    /**
		 * 产生一个全局唯一的序列标
		 * 
		 * @return 年（2位）＋月（2位）＋日（2痊）＋时（2位）＋分（2位）＋秒（2位）＋毫秒（3位）＋UUID（随机15位）
		 * @since 0.1
		 */
		public static String getUUID()
		{
			/**
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(calendar.getTime());
			StringBuffer u1 = new StringBuffer();
			//u1.append(formatNumber(calendar.get(Calendar.YEAR), 2, '0'));
			//u1.append(formatNumber(calendar.get(Calendar.MONTH) + 1, 2, '0'));
			u1.append(formatNumber(calendar.get(Calendar.DAY_OF_MONTH), 2, '0'));
			u1.append(formatNumber(calendar.get(Calendar.HOUR_OF_DAY), 2, '0'));
			u1.append(formatNumber(calendar.get(Calendar.MINUTE), 2, '0'));
			u1.append(formatNumber(calendar.get(Calendar.SECOND), 2, '0'));
			u1.append(formatNumber(calendar.get(Calendar.MILLISECOND), 2, '0'));
			**/
			String u2 = UUID.randomUUID().toString();
			u2 = u2.replaceAll("-", "").substring(0,9);
			return u2;
		}
		/**
		 * 对象是否实例化验证
		 * @param object
		 * @return boolean
		 */
		public boolean isEmpty(Object object)
		{
			if(object==null||"".equals(object)||"".equals(String.valueOf(object).trim())||"null".equals(String.valueOf(object).trim())) return true;
			else {
				if(object instanceof String[]) {
					String[] strarr=(String[])object;
					String str="";
					for(int i=0;i<strarr.length;i++) {
						str=str+strarr[i];
					}
					if(str==null||"".equals(str.trim())||"null".equals(str.trim())) return true;
					else return false;
				} else return false;				
			}
		}
	   /**
		* 封装页面输入的店铺自定义店铺
		* 如：页面输入0001，0002，0003 封装'0001','0002','0003'
		*/ 
		public String getShopNO(String shopid)
	   {
			if(this.isEmpty(shopid)) return "";
			StringBuffer a=new StringBuffer();
		    if(this.isEmpty(shopid)||shopid.indexOf(",")==-1)
		    {
		    	a.append("'");
				a.append(shopid);
				a.append("'");
				return a.toString();
		    }
		    String[] shop=shopid.split(",");
			int b=shop.length-1;			
			for(int i=0;i<shop.length;i++)
			{
				if(shop.length==1||i==b)
				{
					a.append("'");
					a.append(shop[i]);
					a.append("'");
				}
				else{
					a.append("'");
					a.append(shop[i]);
					a.append("',");
				}
			}
			//log.info("封装页面输入的店铺自定义店铺:"+a.toString());
			return a.toString();
	   }	
		/**
		 * 返回转义成html
		 * @param content
		 * @return	String
		 */
		public String getToHtml(Object content) throws Exception {
			if (content == null || "".equals(content)) {
				return "";
			} else {
			   String str=String.valueOf(content);				
				str=str.replaceAll("&","&amp;");
				str=str.replaceAll(" ","&ensp;");//半方大的空白
				str=str.replaceAll(" ","&emsp;");//全方大的空白
				str=str.replaceAll(" ","&nbsp;");//不断行的空白格
				str=str.replaceAll("<","&lt;");
				str=str.replaceAll(">","&gt;");
				str=str.replaceAll("'","&acute;");
				str=str.replaceAll("\"","&quot;");				
				return str;
			}		
		}
		/**
		 * 返回转义成符合js语法格式
		 * @param content
		 * @return
		 * @throws Exception
		 */
		public String getToJs(Object content) throws Exception {
			if (content == null || "".equals(content)) {
				return "";
			} else {
			   String str=this.getToHtml(content);	
			   Pattern p = Pattern.compile("\r|\n");  
			   //System.out.println("before:"+str); 
			   Matcher m = p.matcher(str); 
			   str = m.replaceAll(""); 
			   //System.out.println("after:"+str); 			
			   return str;
			}		
		}
		/**
		 * 将数组转换成字符串
		 * @param str
		 * @param split
		 * @return
		 */
		public String getToStr(String[] str,String split) {
			if(!this.isEmpty(str)) {
				String re_str="";
				for(int i=0;i<str.length;i++) {
					//if(!this.isEmpty(str[i])) {
						re_str=re_str+str[i];
						if(!this.isEmpty(split)&&(i+1)<str.length) {
							re_str=re_str+split;
						}
					//}					
				}
				return re_str;
			} else return "";			
		}
		/**
		 * 社保状况转换
		 * @param str
		 * @param split
		 * @return
		 */
		public String getSbzk(String zbzk) {
			System.out.println("sbzk："+zbzk);
			String res="";
			System.out.println("第二位："+zbzk.substring(1,2));
			if("1".equals(zbzk.substring(0,1))){
				res+="养老保险、";
			}
			if("1".equals(zbzk.substring(1,2))){
				res+="医疗保险、";
			}
			if("1".equals(zbzk.substring(2,3))){
				res+="失业保险、";
			}
			if("1".equals(zbzk.substring(3,4))){
				res+="工伤保险、";
			}
			if("1".equals(zbzk.substring(4,5))){
				res+="生育保险、";
			}
			if("1".equals(zbzk.substring(5,6))){
				res+="低保待遇、";
			}
			if(!"".equals(res)){
				res=res.substring(0, res.length()-1);
			}
			
			return res;
		}
		/**
		 * 返回时间差分钟
		 * @param str
		 * @param split
		 * @return
		 */
		public String getIntervalMin(Date datebegin,Date dateend) {
			if(!this.isEmpty(datebegin) && !this.isEmpty(dateend)) {
				String re_str="";
				long longtemp=0;
				long longtemp2=0;
				long longmin=0;
				
				longtemp=dateend.getTime() - datebegin.getTime();
				longmin=(dateend.getTime() - datebegin.getTime())/60000;
				longtemp2=longmin*60000;
				
				if (longtemp>longtemp2){
					longmin=longmin+1;
				}			
				
				re_str=String.valueOf(longmin);
				return re_str;
			} else return "";			
		}
		/**
		 * 对象数据转换，处理对象的集合之间的转换
		 * @param vo Object
		 * @param poList ArrayList
		 * @return Collection
		 * @throws Exception
		 */
		public Collection ObjectConversionVO(Object vo, ArrayList poList)
				throws Exception {
			try {
				ArrayList listVO = new ArrayList();
				/*if (vo == null)
					throw new Exception("对象转换出错，参数vo没有被实例化！");
				if (poList == null || poList.size() < 1)
					return listVO;
				for (int i = 0; i < poList.size(); i++) {
					vo = vo.getClass().newInstance();
					PropertyUtils.copyProperties(vo, poList.get(i));
					listVO.add(vo);
				}*/
				return listVO;
			} catch (java.lang.Exception error) {
				throw new Exception("对象转换遇到不可预料的出错：" + error.getMessage()
						+ "重试无效后请联系管理员！");
			}
		}	
		/**
		 * 根据文件名和文件路径删除文件
		 * @param filePath
		 * @param fileName
		 * @throws Exception
		 */
		public static void delFile(String filePath, String fileName)
				throws Exception {
			try {
				if (filePath == null || "".equals(filePath.trim()))
					throw new Exception("删除文件时文件路径是空的，文件不能进行删除！");
				if (fileName == null || "".equals(fileName.trim()))
					throw new Exception("删除文件时文件名是空的，文件不能进行删除！");
				File file = new File(filePath.trim(), fileName);
				if (file.exists())//检查是否存在

				{
					file.delete();//删除文件  
				}
			} catch (Exception error) {
				throw new Exception("删除文件时程序出错：" + error.getMessage()
						+ "重试无效后请联系管理员！");
			}
		}	
		/**
		 * 得到访问得基本路径（如：http://211.154.44.251/tourservice/）
		 * @param request
		 * @return
		 */
		public String getBaseUrlIP(HttpServletRequest request)
		{
				String port = request.getLocalPort() + "";
				if (port != null && !"".equals(port.trim()) && !"80".equals(port))
					port = ":" + port;
				else
					port = "";
				String content = request.getContextPath();

				return "http://" + request.getLocalAddr()+ port + content + "/";
		}	
		
		/**
		 * 字符串以逗号分割成数组
		 * @param str
		 * @return array
		 */
		public String[] getStrToArray(String str)
		{
			String[] strArray=null;
			strArray = str.split(","); //拆分字符为"," ,然后把结果交给数组strArray 
			return strArray;
		}	
		/**
		 * session中获取登录信息
		 * @param 
		 * @return String
		 */
		public String getOperator()
		{
			HttpSession session=ServletActionContext.getRequest().getSession();
			SessionInfo user=(SessionInfo)session.getAttribute("sessionInfo");
			if(user==null){
				return "admin";
			}else{
				return user.getLoginName()+"["+user.getRealName()+"]";
			}
		}
		/**
		 * 将数值转成固定格式的字符串
		 * @param 　num　数值
		 * @param　format 格式“000”
		 * @return String
		 */
		public String toStrFormat(int num,String format){
			DecimalFormat df = new DecimalFormat(format);
			return df.format(num);
		}
		//过滤HTML标签
		public static String filterHTMLTags(String inputString) {
		       String htmlStr = inputString; //含html标签的字符串
		       String textStr ="";
		       java.util.regex.Pattern p_script;
		       java.util.regex.Matcher m_script;
		       java.util.regex.Pattern p_style;
		       java.util.regex.Matcher m_style;
		       java.util.regex.Pattern p_html;
		       java.util.regex.Matcher m_html;
		       try {
		        String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> }
		        String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style> }
		           String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式
		      
		           p_script = Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
		           m_script = p_script.matcher(htmlStr);
		           htmlStr = m_script.replaceAll(""); //过滤script标签
		 
		           p_style = Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
		           m_style = p_style.matcher(htmlStr);
		           htmlStr = m_style.replaceAll(""); //过滤style标签
		           
		           htmlStr=htmlStr.replaceAll("&nbsp;", "");
		           htmlStr=htmlStr.replaceAll("</p>", "\r");
		           p_html = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
		           m_html = p_html.matcher(htmlStr);
		           htmlStr = m_html.replaceAll(""); //过滤html标签
		          
		           /* 空格 ——   */
		          // p_html = Pattern.compile("\\ ", Pattern.CASE_INSENSITIVE);
		           m_html = p_html.matcher(htmlStr);
		           htmlStr = htmlStr.replaceAll(" "," ");
		           textStr = htmlStr;
		      
		       }catch(Exception e) {
		       }
		       System.out.println(textStr);
		       return textStr;
		  } 
}
