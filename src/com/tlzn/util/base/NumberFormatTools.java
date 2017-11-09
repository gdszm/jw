/**
 * @(#)NumberFormatTools.java 1.3 08/09/12
 * MODIFY MEMO:
 * 
 * 刘平/2008_09_12/主要修改程序注释，因为程序要按照统一的JAVA编码规范
 * 
 */
package com.tlzn.util.base;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Set;

/**
 * <p>Description: 基本类型数据转换
 * 
 * @version 	1.3
 * @author 		liup
 * @since 		jdk1.4 以上
 */
public class NumberFormatTools
{
	private static NumberFormatTools instance;
  public NumberFormatTools() {
  }
	public static NumberFormatTools getInstance() {
		if (instance != null)
			return instance;
		else
			return instance = new NumberFormatTools();
	}
  /**
   * 得到 Integer类型
   * 
   * @param obj 要转换的对象
   * 
   * @return	Integer 返回转换后的值
   */
  public Integer toInteger(Object obj) {
	  try
	  {
		    if (obj == null || obj.equals("")) {
		        return 0;
		      }
		      else {
		        return new Integer(this.toNumber(obj).intValue());
		      }		  
	  }
	  catch(Exception error)
	  {		  
		  return 0;
	  }
  }

  /**
   * 得到 Float类型
   * 
   * @param obj 要转换的对象
   * 
   * @return	Float 返回转换后的值
   */

  public Float toFloat(Object obj) {
    if (obj == null || obj.equals("")) {
      return new Float(0);
    }
    else {
      return new Float(this.toNumber(obj).floatValue());
    }

  }

  /**
   * 得到 Short类型
   * 
   * @param obj 要转换的对象
   * 
   * @return	Short 返回转换后的值
   */

  public Short toShort(Object obj) {
    if (obj == null || obj.equals("")) {
      return null;
    }
    else {
      return new Short(this.toNumber(obj).shortValue());
    }

  }
  /**
   * 对Double空对象进行格式化
   * 
   * @param obj 要进行格式化的对象
   * 
   * @return	Double 返回格式化的对象后的值
   */
  public Double formatNull(Double obj)
  {
	  if(obj==null||"".equals(obj)) return 0.0;
	  else return obj;
  }
  /**
   * 对Integer空对象进行格式化
   * 
   * @param obj 要进行格式化的对象
   * 
   * @return	Integer 返回格式化的对象后的值
   */
  public Integer formatNull(Integer obj)
  {
	  if(obj==null||"".equals(obj)) return 0;
	  else return obj;
  }  
  /**
   * 对Long空对象进行格式化
   * 
   * @param obj 要进行格式化的对象
   * 
   * @return	Long 返回格式化的对象后的值
   */
  public Long formatNull(Long obj)
  {
	  if(obj==null||"".equals(obj)) return Long.parseLong("0");
	  else return obj;
  }  
  /**
   * 得到 Long类型
   * 
   * @param obj 要转换的对象
   * 
   * @return	Long 返回转换后的值
   */

  public Long toLong(Object obj) {
	  //System.out.println("toLong obj===="+obj);
    if (obj == null || obj.equals("")||"null".equals(obj)) {
      return new Long(0);
    }
    else {
      return new Long(this.toNumber(obj).longValue());
    }

  }

  /**
   * 得到 Double类型
   * 
   * @param obj 要转换的对象
   * 
   * @return	Double 返回转换后的值
   */
  public Double toDouble(Object obj) {
	  try
	  {
		    if (obj == null ||obj.toString()==null||"".equals(obj.toString())||"0".equals(obj.toString())) {
		        return 0.0;
		      }
		      else
		      {
		      	obj=(String.valueOf(obj)).trim();
		          return new Double(this.toNumber(obj).doubleValue());
		      }
		  
	  }
	  catch(Exception err)
	  {
		  return 0.0;
	  }
  }

  /**
   * 构造一个  Number对象
   * 
   * @param obj Number 要转换的对象
   * 
   * @return	Number 返回转换后的值
   */
  public Number toNumber(Object obj) {
    if (obj == null) {
      return null;
    }
    if (obj instanceof Number) {
      return (Number) obj;
    }
    try {
      NumberFormat parser = getNumberFormat(Locale.getDefault());
      return parser.parse(String.valueOf(obj));
    }
    catch (Exception e) {
      return null;
    }
  }

  /**
   * 构造一个  Locale对象
   * 
   * @param locale Locale对象
   * 
   * @return	NumberFormat
   */
  private NumberFormat getNumberFormat(Locale locale)
  {
    return NumberFormat.getNumberInstance(locale);
  }
  /**
   * 格式化数值，如果小数值是零的值，格式化成整数
	* 如：2.00和2.01 格式后结果:2和2.01 
	*/ 
  public String getFormatNu(Object object)
  {	    
		Util util=Util.getInstance();		
		if(!util.isEmpty(object))
		{
			String str=String.valueOf(object);
			int idx=str.indexOf(".");
			String s=str.substring(idx+1,str.length());//
			long in=Long.parseLong(s);//s			
			if(in==0)
			{
				return str.substring(0,idx);//
			} else return str;
		}
		return "";
  }
  /**
   * 提供精确的加法运算。
   * @param ob1 被加数
   * @param ob2 加数
   * @return 两个参数的和 
   */
  public double add(Object ob1, Object ob2) {   
	  double v1=this.toDouble(ob1);
	  double v2=this.toDouble(ob2);
   BigDecimal b1 = new BigDecimal(Double.toString(v1));
   BigDecimal b2 = new BigDecimal(Double.toString(v2));
   return b1.add(b2).doubleValue();
  }
  /**
   * 提供精确的减法运算。
   * @param ob1 被减数
   * @param ob2 减数
   * @return 两个参数的和 
   */
  public double sub(double ob1,double ob2) {
	  double v1=this.toDouble(ob1);
	  double v2=this.toDouble(ob2);
   BigDecimal b1 = new BigDecimal(Double.toString(v1));
   BigDecimal b2 = new BigDecimal(Double.toString(v2));
   return   b1.subtract(b2).doubleValue();
  }
  /**
   * 对金额进行格式化
   * @param objmoney
   * @return	double	返回格式化后的金额
   */
  public double formatMny(Object objmoney)
  {
	  try
	  {		
		  return Util.getInstance().formatMny(objmoney);
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
	  try
	  {
		  return Util.getInstance().formatNum(objnum);
	  }
	  catch(Exception err)
	  {
		  //log.error("对数量进行格式化时出错："+err.getMessage());
		  return 0.00;
	  }
  }	  
	
	public static void main(String[] arge) {
		NumberFormatTools util=NumberFormatTools.getInstance();
		System.out.println(util.getFormatNu(21.00000));
	}  
}
