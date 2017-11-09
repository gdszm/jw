package com.tlzn.util.base;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * <p>Description: 系统启动，需要初始化的数据
 * @version 	1.1
 * @author 		liup
 * @since 		jdk1.4 以上
 */
public class InitSysListener implements ServletContextListener
{
	  private ServletContext servletContext;	

	  public void contextDestroyed(final ServletContextEvent arg0)
	  {
	  }
	  public void contextInitialized(final ServletContextEvent event)
	  {
	      servletContext = event.getServletContext();
	      initContext();
	      initRootPath();
	  }
	
	  private void initContext()
	  {
		  final ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
	      ServiceLocator.getInstance().setContext(ctx);

	  }  
	  private void initRootPath()
	  {
		    String rootpath = servletContext.getRealPath("/");
		    if (rootpath != null)
		    {
		      rootpath = rootpath.replaceAll("\\\\", "/");
		    }
		    else
		    {
		      rootpath = "/";
		    }
		    if (!rootpath.endsWith("/"))
		    {
		      rootpath = rootpath + "/";
		    }
		    //初始化系统应用目录路径(如：/home/tomcat5/webapps/)
		    Constants.ROOTPATH=rootpath;
		    String[] str_arr=Constants.ROOTPATH.split("/");
		    Constants.CONTEXTPATH=str_arr[str_arr.length-1];
		    //System.out.println("应用服务名=："+Constants.CONTEXTPATH);
		    //初始化编码类型(在web.xml中设置)
		    Constants.ENCODING=servletContext.getInitParameter("encoding");		    
	  }  
}
