<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="seal">
<meta http-equiv="description" content="seal">
<!-- jquery库 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/jquery-1.8.2.min.js" charset="utf-8"></script>
<!-- jquery Cookie插件 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/jquery.cookie.js" charset="utf-8"></script>
<!-- easyui相关库 -->
<link id="easyuiTheme" rel="stylesheet" href="${pageContext.request.contextPath}/jslib/jquery-easyui-1.3.6/themes/<c:out value="${cookie.easyuiThemeName.value}" default="default"/>/easyui.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/jslib/jquery-easyui-1.3.6/themes/icon.css" type="text/css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/jquery-easyui-1.3.6/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/jquery-easyui-1.3.6/datagrid-detailview.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/jquery-easyui-1.3.6/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
<!-- easyui插件库 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/jslib/jquery-easyui-portal/portal.css" type="text/css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/jquery-easyui-portal/jquery.portal.js" charset="utf-8"></script>
<!-- 图表库 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/highcharts/highcharts.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/highcharts/highcharts-3d.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/highcharts/highcharts-more.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/highcharts/exporting.js" charset="utf-8"></script>
<!-- 自定义库 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/style/djCss.css" type="text/css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/djUtil.js" charset="utf-8"></script>
<!-- Editor插件库 -->
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/jslib/ueditor1_4_3/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/jslib/ueditor1_4_3/ueditor.all.min.js"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/jslib/ueditor1_4_3/lang/zh-cn/zh-cn.js"></script>

<%@ taglib uri="/WEB-INF/tlzn.tld" prefix='tlzn'%>