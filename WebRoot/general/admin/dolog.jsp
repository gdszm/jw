<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript">
var gridlog;
	$(document).ready(function() {
		gridlog=$('#dologgrid');

		gridlog.datagrid({
			url:'${pageContext.request.contextPath}/dolog!datagridByWhere.do',
			width: 'auto',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			singleSelect:true,
			pagination:true,
			sortName:'operateTime',
			sortOrder:'desc',
			pageSize:20,
			pageList:[20,50,100],
			pageNumber:1,
			idField:'logCode',
			fit:true,
			columns:[[
				{field:'title',title:'日志标题',align:'center',width:200,sortable:true},				
				{field:'info',title:'日志内容',align:'center',width:300,sortable:true},
				{field:'operator',title:'操作人员',align:'center',width:100,sortable:true},				
				{field:'operateTime',title:'操作日期',align:'center',width:140,sortable:true},
				{field:'logTypeName',title:'日志类型',align:'center',width:100,sortable:true},
				{field:'ip',title:'IP地址',align:'center',width:100,sortable:true}
			]],				
			toolbar: '#tb'
		});
	});
	function _search() {
		gridlog.datagrid('load', dj.serializeObject($('#dolog_queryForm')));
		 $('#dolog_queryForm').form('clear');
		 $('#win_dolog_query').window('close');
	}
	
	
</script>
</head>

<body>
		<!-- 表格 -->
	<div class="easyui-layout" data-options="fit:true">						
		<div data-options="region:'center',border:false" style="padding:1px;background:#fff;border:1px solid #ccc;">
			<table id="dologgrid" style="overflow: auto;"></table>
		</div>
	</div>
	<div id="columnmenu" class="easyui-menu" style="width:110px;"></div>

	<div id="tb">
		<div style="margin-bottom:1px">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="javascript:$('#win_dolog_query').window('open');">查询</a>
		</div>
	</div>
	<div id="win_dolog_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:500px;height:280px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="dolog_queryForm" enctype=multipart/form-data method="post">
					<table id="dolog_queryTable" >				
						<tr height="25">
							<td nowrap>日志标题：</td>
							<td><input type="text" name="title" style="width:360px;padding:2px;border:1px solid #000;"/></td>
						</tr>
						<tr height="25">
							<td nowrap>日志内容：</td>
							<td> <input type="text" name="info" style="width:360px;padding:2px;border:1px solid #000;"/></td>
						</tr>
						<tr height="25">
							<td nowrap>操作人员：</td>
							<td>  <input type="text" name="operator" style="width:360px;padding:2px;border:1px solid #000;"/></td>
						</tr>
						<tr height="25">
							<td nowrap>操作日期：</td>
							<td align="left"> <input class="easyui-datebox" name="startDate"   style="width:170px"/>--<input class="easyui-datebox" name="endDate"  style="width:170px"/></td>
						</tr>
					</table>
				</form>
				</center>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;height:40px">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)" onclick="_search()">查询</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#win_dolog_query').window('close');">取消</a>
			</div>
		</div>
	</div>
</body>
</html>