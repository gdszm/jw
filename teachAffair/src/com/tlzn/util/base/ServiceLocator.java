package com.tlzn.util.base;

import javax.servlet.http.HttpServletRequest;

import com.tlzn.service.base.BaseServiceI;
import com.tlzn.service.info.PropServiceI;
import com.tlzn.service.sys.CommServiceI;
import com.tlzn.service.sys.DicServiceI;
import com.tlzn.service.sys.SecoServiceI;
import com.tlzn.service.sys.UserServiceI;
import org.springframework.context.ApplicationContext;
/**
 * <p>Description: 系统调用各业务接口的类
 * @version 	1.2
 * @author 		liup
 * @since 		jdk1.4 以上
 */
public final class ServiceLocator
{
    private static ServiceLocator instance;
    private ApplicationContext context = null;

    public static ServiceLocator getInstance()
    {
        if(instance!=null) return instance;
        else return instance = new ServiceLocator();
    }
    private ApplicationContext getContext()
    {
        return context;
    }
    public void setContext(ApplicationContext context)
    {
        this.context = context;
    }
    private Object getService(String service)
    {
    	return context.getBean(service);
    }
    /**
     * 系统公共业务接口
     * @return BaseService
     */
    public UserServiceI getUserServiceI()
    {
        return (UserServiceI)this.getService("userService");
    }
   public BaseServiceI getBaseServiceI()
    {
        return (BaseServiceI)this.getService("baseService");
    }
   public DicServiceI getDicService()
   {
       return (DicServiceI)this.getService("dicService");
   }
   public PropServiceI getPropService()
   {
       return (PropServiceI)this.getService("propService");
   }
   public CommServiceI getCommService()
   {
       return (CommServiceI)this.getService("commService");
   }
   public SecoServiceI getSecoService()
   {
       return (SecoServiceI)this.getService("secoService");
   }
}
