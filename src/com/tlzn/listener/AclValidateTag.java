package com.tlzn.listener;

import javax.servlet.http.*;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.*;
import com.tlzn.util.*;
import java.util.ArrayList;

/**
 * <p>Description: 处理系统的权限问题
 * 
 * @version 	1.1
 * @author 		liup
 * @since 		jdk1.4 以上
 */
public class AclValidateTag extends TagSupport
{
    
    private String aclURL;//权限资源地址
    private String type;//验证类型；如：打印验证
    public AclValidateTag()
    {
    }
    public int doStartTag()throws JspException
    {
        HttpSession reqsession=super.pageContext.getSession();
        try
        {
        	/**
            if (reqsession != null &&
                (reqsession.getAttribute(Constants.SESSION_HQM_USER_MAINTAIN) != null||reqsession.getAttribute(Constants.SESSION_VIP_USER_MAINTAIN) != null))
            {
            	Users userinfo=null;
            	if(reqsession.getAttribute(Constants.SESSION_HQM_USER_MAINTAIN)!=null)
            	{
            		userinfo=(Users)reqsession.getAttribute(Constants.SESSION_HQM_USER_MAINTAIN);
            	}
            	if(reqsession.getAttribute(Constants.SESSION_VIP_USER_MAINTAIN)!=null)
            	{
            		userinfo=(Users)reqsession.getAttribute(Constants.SESSION_VIP_USER_MAINTAIN);
            	}
	            //如果不是系统管理员时，则进行权限验证
	            if(!Constants.USER_SUPER_TYPE.equals(userinfo.getUsertype()))
	            {
                    //HQM用户和VIP用户
                    if (Constants.USER_HQM_TYPE.equals(userinfo.getUsertype())||Constants.USER_VIP_TYPE.equals(userinfo.getUsertype()))
                    {
                    	//所有权限资源
                    	ArrayList allrighturl=(ArrayList)ServiceLocator.getInstance().getSystemService().findAllRightUrl();
                        //判断该URL资源是否受资源保护
                        if (allrighturl.indexOf(aclURL) == -1)
                        {
                          //不在保护之内则显示操作地址
                          return rTrue();
                        }
                        ArrayList rightList = userinfo.getRighturl();
                        //判断是否有访问权限
                        if (rightList.indexOf(aclURL) == -1)
                        {
                        	return rFalse();
                        }
                        else
                        {
                            //有访问权限则显示操作地址
                            return rTrue();
                        }
                    }
                } else return rTrue();
	            
	           
            }
             **/
            return rFalse();
        }
        catch(Exception error)
        {
            return rFalse();
        }
    }
    private int rTrue()
    {
        int rTrue=1;//有权限则返回真 struts标签里定义1为真、0为假
        int rFalse=0;//有权限则返回假－－－－－－－－郝兵
        try
        {
	        //判断是否有特殊类型验证
	        if(type!=null&&!"".equals(type))
	        {
	        	//导出打印验证
	        	if("aclprint".equals(type))
	        	{
        		  pageContext.getOut().println("<input type=hidden name=aclprint value=true>");
	        	}
	        }
	    	return rTrue;
        }
		catch (Exception apperr)
		{
			return rFalse;
		}
    }
    private int rFalse()
    {
        int rFalse=0;//有权限则返回假
        try
        {
	        //判断是否有特殊类型验证
	        if(type!=null&&!"".equals(type))
	        {
	        	//导出打印验证
	        	if("aclprint".equals(type))
	        	{
        		  pageContext.getOut().println("<input type=hidden name=aclprint value=false>");
	        	}
	        }
	    	return rFalse;
        }
		catch (Exception apperr)
		{
			return rFalse;
		}
    }    

    public String getAclURL() {
        return aclURL;
    }
    public void setAclURL(String aclURL) {
        this.aclURL = aclURL;
    }
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
    
}
