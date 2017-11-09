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
	function initCombox(form){
		initCombobox(form,'meetType','${pageContext.request.contextPath}/dic!combox.do?ctype=MEETINGTYPE');
	}
	$(document).ready(function() {
		gridmeeting=$('#meetinggrid');
		gridmeeting.datagrid({
			url:'${pageContext.request.contextPath}/meetingMan!queryDatagrid.do',
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
				{field:'name',title:'委员',align:'center',width:100,sortable:true,formatter: function(value,row,index){
					return '<a href="javascript:void(0)" onclick="commsee(\''+row.commCode+'\')">'+row.name+'</a>';
			    }},	
			    {field:'secondaryName',title:'届次',align:'center',width:150,sortable:true,formatter : function(value,row){
					return row.secondaryName+"("+row.secondaryYear +"年度)";
				}},	
				{field:'meetTypeName',title:'会议类型',align:'center',width:100,sortable:true},
				{field:'meetName',title:'会议名称',halign:'center',align:'left',width:280,sortable:true}
			]],
			columns:[[
				{field:'statusName',title:'出席情况',align:'center',width:100,sortable:true},
				{field:'beginTime',title:'会议时间',align:'center',width:160,sortable:true ,formatter : function(value,row){
					return row.beginTime+"至"+row.endTime;
				}},
				{field:'address',title:'会议地点',align:'center',width:200,sortable:true},
				{field:'depid',title:'承办单位',align:'center',width:120,sortable:true}
				
			]],	
			toolbar: '#tb',
		});
		initCombox("meeting_queryForm");
		$('#jcgrid').datagrid({
			width: '100%',
			height:'100%',
			url:'${pageContext.request.contextPath}/seco!datagridNoPage.do',
			fit:true,
			idField:'secondaryId',
			striped:true,
			frozenColumns : [[
				{field:'secondaryId',title:'选择',checkbox:true},
			]],
			columns:[[
				{field:'secondayName',title:'届次名称',align:'center',width:140,sortable:false,formatter: function(value, row) {
				   	var str=row.secondayName+"("+row.year+"年度)";
					return str;
				}},				
			]],
			onLoadSuccess:function(row){ //当表格成功加载时执行默认届次选定                
                var rowData = row.rows;
                $.each(rowData,function(idx, val){//遍历JSON  
                    if(val.status=='1'){  
                        $('#jcgrid').datagrid("selectRow", idx); 
                    }  else {
                    	$('#jcgrid').datagrid("unselectRow", idx); 
                    }
                });
            }
		});

		
	});
	
	//查询
	function _search() {
		gridmeeting.datagrid('load', dj.serializeObject($('#meeting_queryForm')));
		 $('#win_meeting_query').window('close');
	}
	//重置
	function cleanSearch() {
		gridmeeting.datagrid('load', {});
//		$('#meeting_queryForm input[name=name]').val('');
//		$('#meeting_queryForm input[name=meetName]').val('');
//		$('#meeting_queryForm input[name=beginTime]').val('');
//		$('#meeting_queryForm input[name=endTime]').val('');
//		$('#meeting_queryForm input[name=startDate]').val('');
//		$('#meeting_queryForm input[name=endDate]').val('');
//		$('#meeting_queryForm input[name=depid]').val('');
//		$('#meeting_queryForm input[name=address]').val('');
//		$("#meeting_queryForm input[name=meetType]").combobox('select', '请选择...');
		
		$('#jcgrid').datagrid('load', {});
		$('#meeting_queryForm input').val('');
		$("#meetTypeId").combobox('setValue', '');
		
	}
	
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
	//委员基本信息查看
	function commsee(code){
		var dp = dj.dialog({
			title : '委员信息',
			href : '${pageContext.request.contextPath}/committee!view.do?code=' + code,
			width : 650,
			height : 500,
			maximized : true,
			buttons : [ {
				text : '关闭',
				iconCls:'icon-cancel',
				handler : function() {
					dp.dialog('close');
				}
			} ]
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
	<div id="columnmenu" class="easyui-menu" style="width:100px;"></div>

	<div id="tb">
		<div style="margin-bottom:1px">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="javascript:$('#win_meeting_query').window('open');">查询</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-view" plain="true" onclick="view()">查看详细</a>
		</div>
	</div>
	<div id="win_meeting_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:630px;height:360px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="meeting_queryForm">
				<input type="hidden" name="filingFlg" id="filingFlgId"/>
				<table id="meeting_queryTable">
				<tr height="25">
							<td nowrap>参加人：</td>
							<td> <input type="text" name="name" class="easyui-validatebox" style="width:275px;padding:2px;"/></td>
							<td rowspan="7" width="200">
								<table id="jcgrid"></table>
							</td>
						</tr>
					<tr height="25">
						<td nowrap>会议名称：</td>
						<td><input type="text" name="meetName" class="easyui-validatebox" style="width:275px;padding:2px;"></td>
					</tr>				
					<tr height="25">
						<td nowrap>会议类型：</td>
						<td><input type="text" id='meetTypeId' name="meetType" style="width:280px;padding:2px;border:1px solid #000;"></td>
					</tr>
					<tr height="25">
						<td nowrap>会议时间：</td>
						<td> <input class="easyui-datebox" name="beginTime"   style="width:134px"/>--<input class="easyui-datebox" name="endTime"  style="width:134px"/></td>
					</tr>
					<tr height="25">
						<td nowrap>承办机构：</td>
						<td> <input type="text" name="depid" class="easyui-validatebox"  style="width:275px;padding:2px;"></td>
					</tr>
					<tr height="25">
						<td nowrap>会议地点：</td>
						<td> <input type="text" name="address" class="easyui-validatebox" style="width:275px;padding:2px;"></td>
					</tr>
				</table>
				</form>
				</center>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;height:40px">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)" onclick="_search()">查询</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)" onclick="cleanSearch()">重置</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#win_meeting_query').window('close');">取消</a>
			</div>
		</div>
	</div>
</body>
</html>
