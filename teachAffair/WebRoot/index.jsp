<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>内蒙古美术职业学院教务管理系统</title>
<jsp:include page="public/inc.jsp"></jsp:include>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',href:'${pageContext.request.contextPath}/public/layout/north.jsp'" style="height: 120px;overflow: hidden;" ></div>
	<div data-options="region:'west',title:'功能导航',href:'${pageContext.request.contextPath}/public/layout/west.jsp'" style="width: 200px;overflow: hidden;"></div>
<%-- <div data-options="region:'east',title:'日历',split:true,href:'${pageContext.request.contextPath}/public/layout/east.jsp'" style="width: 200px;overflow: hidden;"></div>--%>
	<div data-options="region:'center',href:'${pageContext.request.contextPath}/public/layout/center.jsp'" style="overflow: hidden;"></div>
	<div data-options="region:'south',href:'${pageContext.request.contextPath}/public/layout/south.jsp'" style="height: 20px;overflow: hidden;"></div>

	<jsp:include page="public/isIe.jsp"></jsp:include>
</body>
</html>