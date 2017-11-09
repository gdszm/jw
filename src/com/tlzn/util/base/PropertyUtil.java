package com.tlzn.util.base;

import java.lang.reflect.InvocationTargetException;    
import org.apache.commons.beanutils.PropertyUtils;    
import org.springframework.dao.DataAccessException;

   
public class PropertyUtil extends PropertyUtils
{
    /**
     * 将值赋值给目标对象
     * @param	dest  目标对象
     * @param	orig 赋值对象
     * @throws DataAccessException
     * @throws AppException
     * author zcl
     */
    public static void copyProperties(Object dest, Object orig) throws IllegalAccessException, InvocationTargetException,NoSuchMethodException
    {    
             ObjectPropertyUtilsBean.getInstance().copyProperties(dest, orig);    
    }    
} 

