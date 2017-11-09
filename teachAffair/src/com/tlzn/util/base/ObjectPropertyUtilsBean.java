package com.tlzn.util.base;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.ContextClassLoaderLocal;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;

public class ObjectPropertyUtilsBean extends PropertyUtilsBean{    
    Log log = LogFactory.getLog(ObjectPropertyUtilsBean.class);    
        
        
     private static final ContextClassLoaderLocal beansByClassLoader = new ContextClassLoaderLocal() {    
                 // Creates the default instance used when the context classloader is unavailable    
                 protected Object initialValue() {    
                     return new ObjectPropertyUtilsBean();    
                 }    
             };    
    public static ObjectPropertyUtilsBean getInstance(){    
        return (ObjectPropertyUtilsBean)beansByClassLoader.get();    
    }
    /**
     * 将值赋值给目标对象
     * @param	dest  目标对象
     * @param	orig 赋值对象
     * @throws DataAccessException
     * @throws AppException
     * author zcl
     */
    public void copyProperties(Object dest, Object orig)    
            throws IllegalAccessException, InvocationTargetException,    
            NoSuchMethodException {    
   
        if (dest != null&&orig!=null)
        { 
            //throw new IllegalArgumentException ("目标对象为空");
        	
            if (orig instanceof DynaBean) {    
                DynaProperty origDescriptors[] =    
                    ((DynaBean) orig).getDynaClass().getDynaProperties();    
                for (int i = 0; i < origDescriptors.length; i++) {    
                    String name = origDescriptors[i].getName();    
                    if (dest instanceof DynaBean) {    
                        if (isWriteable(dest, name)) {    
                            Object value = ((DynaBean) orig).get(name);    
                            ((DynaBean) dest).set(name, value);    
                        }    
                    } else /* if (dest是一个标准的JavaBean) */ {    
                        if (isWriteable(dest, name)) {    
                            Object value = ((DynaBean) orig).get(name);    
                            setSimpleProperty(dest, name, value);    
                        }    
                    }    
                }    
            } else if (orig instanceof Map) {    
                Iterator names = ((Map) orig).keySet().iterator();    
                while (names.hasNext()) {    
                    String name = (String) names.next();    
                    if (dest instanceof DynaBean) {    
                        if (isWriteable(dest, name)) {    
                            Object value = ((Map) orig).get(name);    
                            ((DynaBean) dest).set(name, value);    
                        }    
                    } else /* if (dest is a standard JavaBean) */ {    
                        if (isWriteable(dest, name)) {    
                            Object value = ((Map) orig).get(name);    
                            setSimpleProperty(dest, name, value);    
                        }    
                    }    
                }    
            } else /* if (orig is a standard JavaBean) */ {    
                PropertyDescriptor origDescriptors[] =    
                    getPropertyDescriptors(orig);    
                for (int i = 0; i < origDescriptors.length; i++) {    
                    String name = origDescriptors[i].getName();    
                    if (isReadable(orig, name)) {    
                        if (dest instanceof DynaBean) {    
                            if (isWriteable(dest, name)) {    
                                Object value = getSimpleProperty(orig, name);    
                                ((DynaBean) dest).set(name, value);    
                            }    
                        } else /* if (dest is a standard JavaBean) */ {    
                            if (isWriteable(dest, name)) {    
                                Object value = getSimpleProperty(orig, name);    
                                setSimpleProperty(dest, name, value);    
                            }    
                        }    
                    }    
                }    
            }
        }           
   
    }
    /**
     * 将值赋值给目标对象
     * @param	bean  目标对象
     * @param	name  form对象的变量名称
     * @param	value form对象的变量值
     * @throws DataAccessException
     * @throws AppException
     * author zcl
     */
    public void setSimpleProperty(Object bean,String name, Object value)throws IllegalAccessException, InvocationTargetException,NoSuchMethodException 
    {    
    	//System.out.println("===对象值拷贝对象==::"+bean);
    	//System.out.println("===对象值拷贝属性名称==::"+name);
    	//System.out.println("===对象值拷贝属性值==::"+value);

    	//该变量值不能为空，为空的则不进行赋值
        if(value!= null&&!"".equals(value))
        {
	        if (bean == null) {    
	            throw new IllegalArgumentException("对象为空");    
	        }    
	        if (name == null) {    
	            throw new IllegalArgumentException("属性名为空");    
	        }    
	   
	        // Validate the syntax of the property name    
	        if (name.indexOf(PropertyUtils.NESTED_DELIM) >= 0) {    
	            throw new IllegalArgumentException ("属性名不规范");    
	        } else if (name.indexOf(PropertyUtils.INDEXED_DELIM) >= 0) {    
	            throw new IllegalArgumentException("属性名不规范");    
	        } else if (name.indexOf(PropertyUtils.MAPPED_DELIM) >= 0) {    
	            throw new IllegalArgumentException ("属性名不规范");    
	        }    
	   
	        // Handle DynaBean instances specially    
	        if (bean instanceof DynaBean) {    
	            DynaProperty descriptor =((DynaBean) bean).getDynaClass().getDynaProperty(name);    
	            if (descriptor == null) {//不存在该属性    
	                return;    
	            }    
	            ((DynaBean) bean).set(name, value);    
	            return;    
	        }    
	   
	        // Retrieve the property setter method for the specified property    
	        PropertyDescriptor descriptor = getPropertyDescriptor(bean, name);    
	        if (descriptor == null) {//不存在该属性    
	            return;    
	        }    
	        Method writeMethod = getWriteMethod(descriptor);    
	        if (writeMethod == null) {    
	            throw new NoSuchMethodException("属性 '" + name + "' 没有Setter方法");    
	        }    
	        Class cl = getPropertyType(bean, name); 
	
	
        	try
        	{
                /** 
                 * 1、该变量值不能为空
                 * 2、处理目标对象与赋值对象里的属性名一样，但类型不一致的情况
                 */
		        if(!cl.getName().equals(value.getClass().getName()))
		        {
		        	//目标对象属性类型是Long型，赋值对象是String则转换成Long型
		            if(cl.getName().equals(Long.class.getName()))
		            {    
		                if(value.getClass().getName().equals(String.class.getName()))    
		                	value = Long.valueOf((String)value);	                
		            }
		            //目标对象属性类型是Integer型，赋值对象是String则转换成Integer型
		            if(cl.getName().equals(Integer.class.getName()))
		            {    
		                if(value.getClass().getName().equals(String.class.getName()))    
		                	value = Integer.valueOf((String)value);	                
		            }
		           //目标对象属性类型是Double型，赋值对象是String则转换成Double型
		            if(cl.getName().equals(Double.class.getName()))
		            {    
		                if(value.getClass().getName().equals(String.class.getName()))    
		                	value = Double.valueOf((String)value);	                
		            }
		        	//目标对象属性类型是Date型，赋值对象是String则转换成Date型
		            if(cl.getName().equals(Date.class.getName()))
		            {
		                if(value.getClass().getName().equals(String.class.getName())&&!"".equals(value))
		                {
		                	Calendar temcl=Calendar.getInstance();
		                	temcl.setTime(Util.getInstance().getFormatDate((String)value));
		                	//转换的日期，如果没有时：分：秒则取系统当前的
		                	if(temcl!=null&&temcl.get(Calendar.HOUR_OF_DAY)<1)
		                	{
		                		Calendar c=Calendar.getInstance();
		                		temcl.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY));
		                		temcl.set(Calendar.MINUTE, c.get(Calendar.MINUTE));
		                		temcl.set(Calendar.SECOND, c.get(Calendar.SECOND));
		                		//System.out.println("getTime==:"+temcl.getTime());
		                		//System.out.println("HOUR_OF_DAY==:"+temcl.HOUR_OF_DAY);
		                	}
		                	value=temcl.getTime();
		                }
		            }	            
		        }
			} catch (Exception error)
			{
				//error.printStackTrace();
				log.error("对象之间值拷贝时程序出错：(ObjectPropertyUtilsBean类中的setSimpleProperty方法)"
						+ error.getMessage());
			}
	               
	        // Call the property setter method    
	        Object values[] = new Object[1];    
	        values[0] = value;    
	        invokeMethod(writeMethod, bean, values);
        }
   
    }    
        
    private Object invokeMethod(Method method,Object bean,Object[] values) throws IllegalAccessException,InvocationTargetException
    {    
       try {    
                    
                return method.invoke(bean, values);    
                    
            }
        	catch (IllegalArgumentException e)
            {                        
                log.error("方法反射失败.", e);    
                throw new IllegalArgumentException(    
                    "不能反射: " + method.getDeclaringClass().getName() + "."     
                    + method.getName() + " - " + e.getMessage());    
                    
                }    
            }    
   
   
}