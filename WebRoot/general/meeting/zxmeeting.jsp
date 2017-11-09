<%@page import="com.tlzn.util.base.Constants"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript">
	var gridmeeting;
	var p;
	$(document).ready(function() {
		gridmeeting=$('#meetinggrid');
		gridmeeting.datagrid({
			url:'${pageContext.request.contextPath}/meeting!doNotNeedAuth_zxdatagrid.do',
			width: 'auto',
			height:'auto',
			singleSelect:true,
			striped:true,
			nowrap:true,
			rownumbers:true,
			pagination:true,
			pageSize:20,
			pageList:[20,50,100],
			pageNumber:1,
			idField:'meetId',
			fit:true,
			frozenColumns:[[
				{field:'meetId',hidden:true}, 
				{field:'secondaryName',title:'所属届次',align:'center',width:200,sortable:true,formatter : function(value,row){
					return row.secondaryName+"("+row.secondaryYear +"年度)";
				}},	
				{field:'meetTypeName',title:'会议类型',align:'center',width:120,sortable:true},
				{field:'meetName',title:'会议名称',halign:'center',align:'left',width:380,sortable:true}
			]],
			columns:[[
				{field:'shortName',title:'会议简称',align:'center',width:150,sortable:true},				
				{field:'beginTime',title:'会议时间',align:'center',width:160,sortable:true ,formatter : function(value,row){
					return row.beginTime+"至"+row.endTime;
				}},
				{field:'address',title:'会议地点',align:'center',width:200,sortable:true},
				{field:'depName',title:'承办单位',align:'center',width:120,sortable:true},
			]],	
		});
	});
	//详细
	function view(){
		var row=gridmeeting.datagrid('getSelected');
		if(row==null){
			dj.messagerAlert('提示', '请选择要查看的记录！', 'error');
			return;
		}
		var p = dj.dialog({
			title : '会议详情',
			href : '${pageContext.request.contextPath}/meeting!meetingView.do?meetId='+row.meetId,
			width : 800,
			height : 600,
			maximized:true,
			buttons : [ {
				text: ' 关闭 ', 
				iconCls:'icon-cancel',
				handler: function() { 
					p.dialog('close'); 
				} 
			}],
			onLoad:function(){
				$('#situationgrid').datagrid({
					url:'${pageContext.request.contextPath}/meetingMan!datagridByMeeting.do?meetId='+row.meetId,
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
						{field:'name',title:'委员',align:'center',width:100,sortable:false,formatter: function(value,row,index){
							return '<a href="javascript:void(0)" onclick="commsee(\''+row.commCode+'\')">'+row.name+'</a>';
					    }},	
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
	}
</script>
</head>
<body>
	<!-- 表格 -->
	<div class="easyui-layout" data-options="fit:true">						
		<div data-options="region:'center',border:false" style="padding:1px;background:#fff;border:1px solid #ccc;">
			<table id="meetinggrid"></table>
		</div>
	</div>
</body>
</html>
