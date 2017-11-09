<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.tlzn.util.base.Constants"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript">
var gridpoll;
var p;
function initCombox(form){
	initCombobox(form,'status','${pageContext.request.contextPath}/dic!combox.do?ctype=POLLSTATUS');
}
$(document).ready(function() {
	gridpoll=$('#pollgrid');
	var startType='<%=request.getAttribute("statusType")%>';
	var status;
	var filingFlg;
	if(startType!=""&& startType!=null){
		//未审核
		if(startType=='E'){  
			$("#status").val("3");
			$("#filingFlg").val("1");
			status="3";
			filingFlg="1";
		}
		//已审核
		if(startType=='F'){
			$("#status").val("4");
			status="4";
		}
		//已签发
		if(startType=='G'){
			$("#status").val("5");
			status="5";
		}
	}
	gridpoll.datagrid({
		url:'${pageContext.request.contextPath}/poll!datagridCheck.do',
		width: 'auto',
		height:'auto',
		striped:true,
		nowrap:true,
		singleSelect:true,
		rownumbers:true,
		pagination:true,
		pageSize:20,
		pageList:[20,50,100],
		pageNumber:1,
		idField:'pollId',
		fit:true,
		queryParams: {
			status: status,
			filingFlg: filingFlg
		},
			frozenColumns:[[
				{field:'pollId',hidden:true}, 
				{field:'pollCode',title:'社情民意编号',align:'center',width:100},
				{field:'title',title:'标题',halign:'center',align:'left',width:380,sortable:true,formatter:function(val,rec){
					var str=val;				
					if(rec.mergeFlg=='<%=Constants.CODE_TYPE_YESNO_YES%>'){
						var ids=rec.mergeIds.split(",");
						str+='<font color="#0000CD"><b>    ('
						for(var j=0;j<ids.length;j++){
							str+='<a  href="javascript:void(0)" onclick="see('+ids[j]+')">合并'+(j+1)+',</a>'
						}
						str+=')</b></font>'
					}
					return str;
				}}
			]],
			columns:[[
				{field:'createMan',title:'提交者',align:'center',width:100,sortable:true,formatter: function(value, row) {
				    return row.createManName ;
				}},				
				{field:'writer',title:'撰稿人',align:'center',width:100,sortable:true},
				{field:'status',title:'状态',align:'center',width:80,sortable:true,formatter: function(value, row) {
				    return row.statusName;
				}},
				{field:'pollType',title:'类型',align:'center',width:100,sortable:true,formatter: function(value, row) {
				    return row.pollTypeName;
				}},
				{field:'editor',title:'编发人',align:'center',width:60,sortable:true,formatter: function(value, row) {
				    return row.editorName;
				}},		
				{field:'master',title:'主送',align:'center',width:150,sortable:true},
				{field:'copyMan',title:'抄送',align:'center',width:300,sortable:true},
				{field:'mergeFlg',title:'是否合并',align:'center',width:60,sortable:true,formatter: function(value, row) {
				    return row.mergeFlgName;
				}},				
				{field:'stressFlg',title:'是否重点',align:'center',width:60,sortable:true,formatter: function(value, row) {
				    return row.stressFlgName;
				}},
				{field:'createTime',title:'提交日期',align:'center',width:150,sortable:true}
			]],			
			toolbar: '#tb',
			onDblClickRow : function(rowIndex, rowData) {
				lookPollOnDblClick(rowData.pollId);
			}
		});
		
	});
	//审签社情民意
	function checkPoll(){
		var rows = gridpoll.datagrid('getSelected');
		if (rows) {
			p = dj.dialog({
				title : '审签社情民意',
				href : '${pageContext.request.contextPath}/poll!pollCheckMark.do?pollId=' + rows.pollId,
				maximized:true,
				maximizable:true,
				width : 800,
				height : 500,
				iconCls:'icon-save',
				buttons : [ {
					text : '提交',
					iconCls:'icon-save',
					handler : function() {
						submitdata();
					}
				},{ 
					text: '取消', 
					iconCls:'icon-cancel',
					handler: function() { 
						p.dialog('close'); 
					} 
				}],
				onLoad : function() {
					var f = p.find('form');
					$(document).ready(function() {
						//提交人信息列表
					 if(rows.pollType!='<%=Constants.DIC_TYPE_POLLTYPE_SHZJ%>'){
						$('#submittergrid').datagrid({
							url:'${pageContext.request.contextPath}/poll!submitter.do?createMan=' + rows.createMan,
							width:'auto',
							height:'auto',
							striped:true,
							nowrap:true,
							rownumbers:true,
							singleSelect:true,
							fitColumns:true,
							columns:[[
								{field:'code',title:'编号',align:'center',width:60,sortable:false},
								{field:'name',title:'提交者',align:'center',width:100,sortable:false},
								{field:'groupName',title:'提交者分组',align:'center',width:100,sortable:false},
								{field:'telephone',title:'联系电话',align:'left',width:120,sortable:false},
								{field:'comparyPhone',title:'固定电话',align:'center',width:100,sortable:false},
								{field:'email',title:'电子邮箱',align:'center',width:120,sortable:false},
								{field:'comparyAddress',title:'通讯地址',align:'center',width:200,sortable:false},
								{field:'comparyPost',title:'邮编',align:'center',width:100,sortable:false}
							]]
						});
					 }
					});
				}
			});
		} else if (rows.length > 1) {
			parent.dj.messagerAlert('提示', '同一时间只能编辑一条记录！', 'error');
		} else {
			parent.dj.messagerAlert('提示', '请选择要编辑的记录！', 'error');
		}
	}
	function submitdata(){
		var f =$("#markPollForm");
		//$("#content").val(UE.getEditor('editor').getContent()); 
		f.form('submit', {
			url : '${pageContext.request.contextPath}/poll!pollMark.do',
			success : function(d) {
				var json = $.parseJSON(d);
				if (json.success) {
					gridpoll.datagrid('reload');
					p.dialog('close'); 
				}
				dj.messagerShow({
					msg : json.msg,
					title : '提示'
				});
			}
		});
	}
	
	//查询
	function _search() {
		gridpoll.datagrid('load', dj.serializeObject($('#poll_queryForm')));
		 //$('#dolog_queryForm').form('clear');
		 $('#win_poll_query').window('close');
	}
	//重置
	function cleanSearch() {
		gridpoll.datagrid('load', {});
		$('#poll_queryForm input').val('');
	}
	
	//查看社情民意
	function lookPoll(){
		var selectRow = gridpoll.datagrid('getSelected');
		if (selectRow) {
			window.open("${pageContext.request.contextPath}/poll!pollSee.do?pollId="+selectRow.pollId);
		}else{
			dj.messagerAlert('提示', '请选择要查看的记录！', 'error');
		}		
		return false;
	}
	//双击行查看社情民意
	function lookPollOnDblClick(pollId){
		if (pollId) {
			window.open("${pageContext.request.contextPath}/poll!pollSee.do?pollId="+pollId);
		}else{
			dj.messagerAlert('提示', '请双击要查看的记录！', 'error');
		}		
		return false;
	}
	//查看社情民意
	function see(pollId){
		window.open("${pageContext.request.contextPath}/poll!pollSee.do?pollId="+pollId);
	}
	
</script>
</head>

<body>
	<!-- 表格 -->
	<div class="easyui-layout" data-options="fit:true">						
		<div data-options="region:'center',border:false" style="padding:1px;background:#fff;border:1px solid #ccc;">
			<table id="pollgrid"></table>
		</div>
	</div>
	<div id="columnmenu" class="easyui-menu" style="width:100px;"></div>

	<div id="tb" style="padding:2px;height:auto">
		<div style="margin-bottom:1px">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="$('#win_poll_query').window('open')">查询</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="checkPoll()">审核签发</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-view" plain="true" onclick="lookPoll()">查看</a>
		</div>
	</div>
	<div id="win_poll_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:500px;height:220px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="poll_queryForm">
				<input id="status" type="hidden" name="status" />
				<input id="filingFlg" type="hidden" name="filingFlg" />
				<table id="poll_queryTable">				
					<tr height="25">
						<td nowrap>标题：</td>
						<td><input type="text" name="title" style="width:360px;padding:2px;border:1px solid #000;"></td>
					</tr>
					<tr height="25">
						<td nowrap>提交者：</td>
						<td> <input type="text" name="createMan" style="width:360px;padding:2px;border:1px solid #000;"></td>
					</tr>
				</table>
				</form>
				</center>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;height:40px">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)" onclick="_search()">查询</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)" onclick="cleanSearch()">重置</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#win_poll_query').window('close');">取消</a>
			</div>
		</div>
	</div>
</body>
</html>
