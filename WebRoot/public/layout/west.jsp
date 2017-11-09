<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
<!--
body {
	visibility: hidden;
	font-family: helvetica, tahoma, verdana, sans-serif;
	padding: 10px;
	font-size: 13px;
	margin: 0;
}

*{margin:0;padding:0;border:0;}
#nav {
 line-height: 27px;  list-style-type: none;text-align:left;
 left: -999em; width: 180px; position: absolute; margin-top: 5px;
}
#nav  li{
 float: left; width: 180px; margin-top: 5px;
}
#nav  a{
 display: block;width: 156px;text-align:left;padding-left:24px;
}
#nav  a:link  {
 	text-decoration:none;
}
#nav  a:visited  {
 	text-decoration:none;
}
#nav a:hover  {
 text-decoration:none;font-weight:normal;

}
#nav {
 left: auto;
}
#nav {
 left: auto;
}
#content {
 clear: left; 
}
-->
</style>

<div class="easyui-accordion" data-options="fit:true,border:false">
	<c:forEach var="item" items="${menuList}">
		<div title="${item.cname}" data-options="iconCls:'${item.iconCls}'">
			<ul id="nav">
	    	<c:forEach var="sitem" items="${subMenuList}">
				<c:if test="${item.cid==sitem.pid}">
					<li><a class="easyui-linkbutton" data-options="iconCls:'${sitem.iconCls}'" plain="true" href="javascript:void(0);"  onclick="addTab('${sitem.cname}','${sitem.curl}','${sitem.iconCls}')"> ${sitem.cname}</a></li>
				</c:if>
			</c:forEach>
			</ul>
		</div>
    </c:forEach>
</div>