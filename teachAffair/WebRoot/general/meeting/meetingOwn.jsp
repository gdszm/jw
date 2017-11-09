<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript">
var meetingMan;
var beginTime='${beginTime}';
var statusMore='${status}';
	$(document).ready(function() {
		var f = $('#win_meetOwn_query');
		//var depid = f.find('input[id=depid]');
		//var ptree = depid.combotree({
		//	lines : true,
		//	url : '${pageContext.request.contextPath}/dept!doNotNeedSession_treeRecursive.do',
		//	onLoadSuccess : function() {
		//		parent.$.messager.progress('close');
		//	}
		//});
		getcombobox('meetType','${pageContext.request.contextPath}/dic!combox.do?ctype=MEETINGTYPE');
		getcombobox('meetingStatus','${pageContext.request.contextPath}/dic!combox.do?ctype=MEETINGSTATUS');
		$('#meetingStatus').combobox('setValue', statusMore);
		meetingMan=$('#meetingManGrid');
		meetingMan.datagrid({
			url:'${pageContext.request.contextPath}/meetingMan!meetingMy.do',
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
			idField:'id',
			fit:true,
			columns:[[
				//{field:'name',title:'参加人',align:'center',width:100,sortable:true},		
				{field:'meetTypeName',title:'会议类型',align:'center',width:100,sortable:true},
				{field:'meetName',title:'会议名称',halign:'center',align:'left',width:380,sortable:true},
				{field:'shortName',title:'会议简称',align:'center',width:150,sortable:true},
				{field:'address',title:'会议地点',align:'center',width:150,sortable:true},
				{field:'depid',title:'承办单位',align:'center',width:140,sortable:true},
				{field:'beginTime',title:'会议时间',align:'center',width:160,sortable:true ,formatter : function(value,row){
					return row.beginTime+"至"+row.endTime;
				}},
				{field:'secondaryName',title:'所属届次',align:'center',width:80,sortable:true},
				{field:'total',title:'邀请人员数',align:'center',width:80,sortable:true},
				{field:'attendance',title:'参加人员数',align:'center',width:80,sortable:true},
				{field:'statusName',title:'出席情况',align:'center',width:150,sortable:true}
			]],	
			toolbar : [
						{
							text : '查询',
							iconCls : 'icon-search',
							handler : function() {
								$('#win_meetOwn_query').window('open');
							}
						}, '-', {
							text : '查看详细',
							iconCls : 'icon-view',
							handler : function() {
								view();
							}
						}, '-',
						{
							text : '请假申请',
							iconCls : 'icon-edit',
							handler : function() {
								leavesq();
							}
						}
			],
			queryParams: {
				meetingStatus:statusMore,
				beginTime: beginTime
			},
		});
	});

	function view() {
		var rows = meetingMan.datagrid('getSelected');
		if (rows) {
			var meetId=rows.meetId;
			var p = dj.dialog({
				title : '会议详情',
				href : '${pageContext.request.contextPath}/meeting!meetingView.do?meetId='+meetId,
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
				onLoad:function(){
					$('#situationgrid').datagrid({
						url:'${pageContext.request.contextPath}/meetingMan!datagridByMeeting.do?meetId='+meetId,
						width:'auto',
						height:'auto',
						striped:true,
						nowrap:true,
						rownumbers:true,
						fitColumns:true,
						pagination:true,
						pageSize:20,
						pageList:[20,50,100],
						fit:true,
						columns:[[
							{field:'name',title:'委员',align:'center',width:100,sortable:false},
							{field:'circleName',title:'界别',align:'center',width:100,sortable:false},
							{field:'committeeName',title:'所属专委会',align:'center',width:120,sortable:false},
							{field:'telephone',title:'联系电话',align:'left',width:120,sortable:false},
							{field:'email',title:'电子邮箱',align:'center',width:120,sortable:false},
							{field:'companyName',title:'工作单位',align:'center',width:200,sortable:false},
							{field:'job',title:'职务',align:'center',width:150,sortable:false},
							{field:'statusName',title:'出席状态',align:'center',width:100,sortable:false}
						]]
					});
				}
				
			});
		} else {
			dj.messagerAlert('提示', '请选择要查看的记录！', 'error');
		}
	}

	function leavesq(){
		var rows = $('#meetingManGrid').datagrid('getSelected');
		if (rows) {
			var id=rows.id;
			var p = dj.dialog({
				title : '查看请假详细',
				href : '${pageContext.request.contextPath}/meetingMan!leave.do?id='+id,
				width : 400,
				height : 200,
				buttons : [{
					text : '提交',
					iconCls:'icon-ok',
					handler : function() {
						var f = p.find('form');
						f.form('submit', {
							url : '${pageContext.request.contextPath}/meetingMan!leavesq.do',
							success : function(d) {
								var json = $.parseJSON(d);
								if (json.success) {
									meetingMan.datagrid('reload');
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
			dj.messagerAlert('提示', '请选择要查看的记录！', 'error');
		}
	}
	
	function _search() {
		meetingMan.datagrid('load', dj.serializeObject($('#meetOwn_queryForm')));
		 //$('#meetOwn_queryForm').form('clear');
		 $('#win_meetOwn_query').window('close');
	}	
	function cleanSearch() {
		meetingMan.datagrid({queryParams:{beginTime:'',meetingStatus:''}});
		meetingMan.datagrid('load', {});
		$('#meetOwn_queryForm input').val('');
		$('#meetType').combobox('setValue', '');
		$('#meetingStatus').combobox('setValue', '');
	}
</script>
</head>

<body>
		<!-- 表格 -->
	<div class="easyui-layout" data-options="fit:true">						
		<div data-options="region:'center',border:false" style="padding:1px;background:#fff;border:1px solid #ccc;">
			<table id="meetingManGrid" style="overflow: auto;"></table>
		</div>
	</div>
	<div id="columnmenu" class="easyui-menu" style="width:110px;"></div>

<%--	<div id="tb">--%>
<%--		<div style="margin-bottom:1px">--%>
<%--				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="javascript:$('#win_meetOwn_query').window('open');">查询</a>--%>
<%--				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-view" plain="true" onclick="view()">查看详细</a>--%>
<%--				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="leavesq()">请假申请</a>--%>
<%--		</div>--%>
<%--	</div>--%>
	<div id="win_meetOwn_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:500px;height:280px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="meetOwn_queryForm" enctype=multipart/form-data method="post">
					<table id="dolog_queryTable" >
						<tr height="25">
							<td nowrap>会议名称：</td>
							<td>  <input type="text" name="meetName"  class="easyui-validatebox" style="width:354px;padding:2px;"/></td>
						</tr>
						<tr height="25">
							<td nowrap>会议类型：</td>
							<td><input type="text" name="meetType" id="meetType" style="width:360px;padding:2px;border:1px solid #000;"/></td>
						</tr>
						<tr height="25">
							<td nowrap>会议地点：</td>
							<td>  <input type="text" name="address" class="easyui-validatebox" style="width:354px;padding:2px;"/></td>
						</tr>
						<tr height="25">
							<td nowrap>会议状态：</td>
							<td><input type="text" name="meetingStatus" id="meetingStatus" style="width:360px;padding:2px;border:1px solid #000;"/></td>
						</tr>
						<tr height="25">
							<td nowrap>承办单位：</td>
							<td>  <input type="text" name="depid" id="depidId"  class="easyui-validatebox" style="width:354px;padding:2px;"/></td>
						</tr>
						<tr height="25">
							<td nowrap>会议时间：</td>
							<td align="left"> <input class="easyui-datebox" name="beginTime" style="width:174px" value="${beginTime}" />--<input class="easyui-datebox" name="endTime"  style="width:174px"/></td>
						</tr>
					</table>
				</form>
				</center>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;height:40px">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)" onclick="_search()">查询</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)" onclick="cleanSearch()">重置</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#win_meetOwn_query').window('close');">取消</a>
			</div>
		</div>
	</div>
</body>
</html>