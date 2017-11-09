<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript">
var activity;
var beginTime='${beginTime}';
var statusMore='${status}';
	$(document).ready(function() {
		var f = $('#win_dolog_query');
		//var agency = f.find('input[id=agency]');
		//var ptree = agency.combotree({
		//	lines : true,
		//	url : '${pageContext.request.contextPath}/dept!doNotNeedSession_treeRecursive.do',
		//	onLoadSuccess : function() {
		//		parent.$.messager.progress('close');
		//	}
		//});
		getcombobox('aspecies','${pageContext.request.contextPath}/dic!combox.do?ctype=ASPECIES');
		getcombobox('actStatus','${pageContext.request.contextPath}/dic!combox.do?ctype=HSTATUS');
		$('#actStatus').combobox('setValue', statusMore);
		activity=$('#Activitygrid');
		activity.datagrid({
			url:'${pageContext.request.contextPath}/activitypeo!activityMy.do',
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
			frozenColumns:[[
				{field:'aspeciesName',title:'活动种类',align:'center',width:100,sortable:true},
				{field:'atheme',title:'活动主题',align:'center',width:400,sortable:true}
			]],
			columns:[[
				//{field:'name',title:'参加人',align:'center',width:100,sortable:true},				
				
				{field:'place',title:'活动地点',align:'center',width:150,sortable:true},
				{field:'agency',title:'承办机构',align:'center',width:140,sortable:true},
				{field:'time',title:'活动时间',align:'center',width:300,sortable:true,formatter : function(value,row){
					if(row.timebeg&&row.timeend){return row.timebeg+"至"+row.timeend;}
				}},
				{field:'invitnumb',title:'邀请人员数',align:'center',width:80,sortable:true},
				{field:'attendnumb',title:'参加人员数',align:'center',width:80,sortable:true},
				{field:'astatusName',title:'出席情况',align:'center',width:150,sortable:true}
			]],	
			toolbar: '#tb',
			queryParams: {
				actStatus:statusMore,
				timebeg: beginTime
			}
		});
	});

	function view() {
		var rows = activity.datagrid('getSelected');
		if (rows) {
			var aid=rows.aid;
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
					$('#commcodegrid').datagrid({
						url:'${pageContext.request.contextPath}/activitypeo!peoQuery.do?aid='+aid,
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
							{field:'name',title:'委员',align:'center',width:100,sortable:false},
							{field:'circleName',title:'界别',align:'center',width:100,sortable:false},
							{field:'committeeName',title:'所属专委会',align:'center',width:120,sortable:false},
							{field:'telephone',title:'联系电话',align:'left',width:120,sortable:false},
							{field:'email',title:'电子邮箱',align:'center',width:120,sortable:false},
							{field:'companyName',title:'工作单位',align:'center',width:200,sortable:false},
							{field:'job',title:'职务',align:'center',width:150,sortable:false},
							{field:'astatusName',title:'出席情况',align:'center',width:120,sortable:false,}
						]],
					});
				}
			});
		} else {
			dj.messagerAlert('提示', '请选择要查看的记录！', 'error');
		}
	}

	function leavesq(){
		var rows = $('#Activitygrid').datagrid('getSelected');
		if (rows) {
			var auid=rows.auid;
			var p = dj.dialog({
				title : '请假详细',
				href : '${pageContext.request.contextPath}/activitypeo!leave.do?auid='+auid,
				width : 400,
				height : 200,
				buttons : [{
					text : '提交',
					iconCls:'icon-ok',
					handler : function() {
						var f = p.find('form');
						f.form('submit', {
							url : '${pageContext.request.contextPath}/activitypeo!leavesq.do',
							success : function(d) {
								var json = $.parseJSON(d);
								if (json.success) {
									activity.datagrid('reload');
									p.dialog('close');
								}
								dj.messagerShow({
									msg : json.msg,
									title : '提示'
								});
							}
						});
					},
				}],
				onLoad : function(){
					getcombobox('leaveType','${pageContext.request.contextPath}/dic!combox.do?ctype=LEAVE');
				}
			});
		}else {
			dj.messagerAlert('提示', '请选择要申请的记录！', 'error');
		}
	}
	
	function _search() {
		activity.datagrid('load', dj.serializeObject($('#dolog_queryForm')));
		 //$('#dolog_queryForm').form('clear');
		 $('#win_dolog_query').window('close');
	}	
	function cleanSearch() {
		activity.datagrid({queryParams:{timebeg:'',actStatus:''}});
		activity.datagrid('load', {});
		$('#dolog_queryForm input').val('');
		$('#aspecies').combobox('setValue', '');
		$('#dolog_queryForm').combobox('setValue', '');
		$('#actStatus').combobox('setValue', '');
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
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="leavesq()">请假申请</a>
		</div>
	</div>
	<div id="win_dolog_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:500px;height:280px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="dolog_queryForm" enctype=multipart/form-data method="post">
					<table id="dolog_queryTable" >
						<tr height="25">
							<td nowrap>活动主题：</td>
							<td>  <input type="text" name="activitypeo.atheme" class="easyui-validatebox" style="width:354px;padding:2px;"/></td>
						</tr>
						<tr height="25">
							<td nowrap>活动种类：</td>
							<td><input type="text" name="activitypeo.aspecies" id="aspecies" style="width:360px;padding:2px;border:1px solid #000;"/></td>
						</tr>
						<tr height="25">
							<td nowrap>活动地点：</td>
							<td>  <input type="text" name="activitypeo.place" class="easyui-validatebox" style="width:354px;padding:2px;"/></td>
						</tr>
						<tr height="25">
							<td nowrap>活动状态：</td>
							<td><input type="text" name="actStatus" id="actStatus" style="width:360px;padding:2px;border:1px solid #000;"/></td>
						</tr>
						<tr height="25">
							<td nowrap>承办机构：</td>
							<td>  <input type="text" name="activitypeo.agency" id="agency" class="easyui-validatebox" style="width:354px;padding:2px;"/></td>
						</tr>
						<tr height="25">
							<td nowrap>活动日期：</td>
							<td align="left"> <input class="easyui-datebox" name="activitypeo.timebeg"   style="width:174px" value="${beginTime}"/>--<input class="easyui-datebox" name="activitypeo.timeend"  style="width:174px"/></td>
						</tr>
					</table>
				</form>
				</center>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;height:40px">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)" onclick="_search()">查询</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)" onclick="cleanSearch()">重置</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#win_dolog_query').window('close');">取消</a>
			</div>
		</div>
	</div>
</body>
</html>