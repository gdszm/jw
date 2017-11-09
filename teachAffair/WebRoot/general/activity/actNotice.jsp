<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript">
var activity;
/* function initCombox(form){
	initCombobox(form,'aspecies','${pageContext.request.contextPath}/dic!combox.do?ctype=ASPECIES');
} */
	$(document).ready(function() {
	/* 	var f = $('#win_dolog_query');
		var agency = f.find('input[name=agency]');
		var ptree = agency.combotree({
			lines : true,
			url : '${pageContext.request.contextPath}/dept!doNotNeedSession_treeRecursive.do',
			onLoadSuccess : function() {
				parent.$.messager.progress('close');
			}
		});
		var secondaryId = f.find('input[name=secondaryId]');
		secondaryId.combobox({    
		    url:'${pageContext.request.contextPath}/seco!combobox.do',    
		    valueField:'secondaryId',    
		    textField:'secondayName',
		    multiple:true
		}); 
		initCombox("win_dolog_query"); */
		activity=$('#Activitygrid');
		activity.datagrid({
			//url:'${pageContext.request.contextPath}/activity!datagrid.do',
			width: 'auto',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			singleSelect:true,
			pagination:true,
			sortName:'aspecies',
			sortOrder:'desc',
			pageSize:20,
			pageList:[20,50,100],
			pageNumber:1,
			idField:'aid',
			fit:true,
			columns:[[
				//{field:'secondaryName',title:'所属届次',align:'center',width:100,sortable:true},	
				{field:'aspeciesName',title:'活动种类',align:'center',width:100,sortable:true},				
				{field:'atheme',title:'活动主题',align:'center',width:400,sortable:true},
				{field:'time',title:'时间',align:'center',width:300,sortable:true/* ,formatter : function(value,row){
						return row.timebeg+"至"+row.timeend;
				} */},
				{field:'agencyName',title:'承办机构',align:'center',width:140,sortable:true},
				{field:'place',title:'地点',align:'center',width:150,sortable:true},
				{field:'invitnumb',title:'邀请人数',align:'center',width:80,sortable:true},
				{field:'statusName',title:'状态',align:'center',width:100,sortable:true}
			]],	
			data:[
					{aspeciesName:"民生调研",atheme:"活动主题",time:'2016-10-14至2016-10-16',agencyName:"内蒙古美术职业学院",place:"活动地点",invitnumb:"2",statusName:"已发布"},
			],			
			toolbar: '#tb'
		});
	});

	/* function view() {
		var rows = activity.datagrid('getSelections');
		if (rows.length == 1) {
			var aid=rows[0].aid;
			var p = dj.dialog({
				title : '查看活动',
				href : '${pageContext.request.contextPath}/activity!actView.do?aid='+aid,
				width : 600,
				height : 450,
				maximized : true,
				buttons : [{
					text : '关闭',
					iconCls:'icon-cancel',
					handler : function() {
						p.dialog('close');
					},
				}],
				onLoad : function() {
					$('#Activitypeogrid').datagrid({
						//url:'${pageContext.request.contextPath}/comm!getCurSecComm.do',
						width: 'auto',
						height:'auto',
						striped:true,
						nowrap:true,
						pagination:true,
						pageSize:10,
						pageList:[10,15,20],
						rownumbers:true,
						sortName: 'auid',
						sortOrder: 'asc',
						idField:'auid',
						fit:true,
						columns:[[
							{field:'name',title:'姓名',align:'center',width:100,sortable:false},
							{field:'jobName',title:'工作职务',align:'center',width:120,sortable:false},
							{field:'sexName',title:'性别',align:'center',width:120,sortable:false},
							{field:'secondaryName',title:'界别',align:'center',width:120,sortable:false},
							{field:'telephone',title:'联系电话',align:'center',width:120,sortable:false},
							{field:'astatus',title:'出席情况',align:'center',width:120,sortable:false}
						]],
						data:[
							{name:"姓名",jobName:'工作职务',sexName:"性别",secondaryName:"界别",telephone:"联系电话",astatus:"astatus"},
						],
					});
				}
			});
		} else if (rows.length > 1) {
			dj.messagerAlert('提示', '同一时间只能查看一条记录！', 'error');
		} else {
			dj.messagerAlert('提示', '请选择要查看的记录！', 'error');
		}
	} */
	
	function _search() {
		activity.datagrid('load', dj.serializeObject($('#dolog_queryForm')));
		 $('#dolog_queryForm').form('clear');
		 $('#win_dolog_query').window('close');
	}
</script>
</head>

<body>
		<!-- 表格 -->
	<div class="easyui-layout" data-options="fit:true">						
		<div data-options="region:'center',border:false" style="padding:1px;background:#fff;border:1px solid #ccc;">
			<table id="Activitygrid" style="overflow: auto;"></table>
		</div>
	</div>
	<div id="columnmenu" class="easyui-menu" style="width:110px;"></div>

	<div id="tb">
		<div style="margin-bottom:1px">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="javascript:$('#win_dolog_query').window('open');">查询</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-view" plain="true" onclick="view()">查看详细</a>
		</div>
	</div>
	<div id="win_dolog_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:500px;height:280px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="dolog_queryForm" enctype=multipart/form-data method="post">
					<table id="dolog_queryTable" >				
						<tr height="25">
							<td nowrap>活动种类：</td>
							<td><input type="text" name="aspecies" style="width:360px;padding:2px;border:1px solid #000;"/></td>
						</tr>
						<tr height="25">
							<td nowrap>界次：</td>
							<td align="left"> <input type="text" name="atheme" style="width:360px;padding:2px;border:1px solid #000;"/></td>
						</tr>
						<tr height="25">
							<td nowrap>活动主题：</td>
							<td> <input type="text" name="atheme" style="width:360px;padding:2px;border:1px solid #000;"/></td>
						</tr>
						<tr height="25">
							<td nowrap>活动地点：</td>
							<td>  <input type="text" name="place" style="width:360px;padding:2px;border:1px solid #000;"/></td>
						</tr>
						<tr height="25">
							<td nowrap>承办机构：</td>
							<td>  <input type="text" name="agency" style="width:360px;padding:2px;border:1px solid #000;"/></td>
						</tr>
						<!-- <tr height="25">
							<td nowrap>所属界次：</td>
							<td>  <input type="text" name="secondaryId" style="width:360px;padding:2px;border:1px solid #000;"/></td>
						</tr> -->
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