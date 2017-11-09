<%@ page language="java" pageEncoding="UTF-8" %>
<%@page import="com.tlzn.util.base.Constants"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<base href="<%=basePath%>">
    <script type="text/javascript" src="jslib/swfobject.js"></script>  
    <script type="text/javascript">  
        swfobject.registerObject("myId", "9.0.0", "expressInstall.swf");  
    </script>  
  </head>  
  <body>  
        <div>   
            <object id="myId" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%" height="100%">  
                <param name="movie" value="wordfile/<%=request.getParameter("swffile")%>" />  
                <!--[if !IE]>-->  
                <object type="application/x-shockwave-flash" data="wordfile/<%=request.getParameter("swffile")%>" width="100%" height="100%">  
                <!--<![endif]  
                <div>
					<h1>Alternative content</h1>
					<p><a href="http://www.adobe.com/go/getflashplayer"><img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash player" /></a></p>
				</div>-->
                <!--[if !IE]>-->  
                </object>  
                <!--<![endif]-->  
            </object>  
        </div>  
  </body>  
</html>  