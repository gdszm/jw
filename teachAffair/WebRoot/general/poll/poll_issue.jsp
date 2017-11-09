<%@page import="com.tlzn.util.base.Constants"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript">
	var gridissue;
	var p;
	var rulesGrid;
	//查询
	function _search() {
		gridissue.datagrid('load', dj.serializeObject($('#poll_queryForm')));
		 $('#win_poll_query').window('close');
	}
	//重置
	function cleanSearch() {
		gridissue.datagrid('load', {});
		$('#poll_queryForm input').val('');
	}
	
	//查看社情民意
	function lookPoll(){
		var selectRow = gridissue.datagrid('getSelected');
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
	$(document).ready(function() {
		gridissue=$('#gridissue');
		gridissue.datagrid({
			url:'${pageContext.request.contextPath}/poll!datagridIssue.do',
			width: 'auto',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			singleSelect:true,
			pagination:true,
			pageSize:20,
			pageList:[10,15,20],
			pageNumber:1,
			idField:'pollId',
			fit:true,
			frozenColumns:[[
				{field:'pollId',hidden:true}, 
				{field:'pollCode',title:'社情民意编号',align:'center',width:100,sortable:true},
				{field:'title',title:'标题',halign:'center',align:'left',width:480,sortable:true,formatter:function(val,rec){
					var str=val;	
					if(val==undefined){}
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
				//{field:'special',title:'本期专报',align:'center',width:120,sortable:true},
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
	
	//弹出批示和记分
	function insAndScor() {
		var rows = gridissue.datagrid('getSelected');
		if (rows!=null) {
			var p = dj.dialog({
				title : '批示与采用计分登记',
				href : '${pageContext.request.contextPath}/pollScore!pollInsAndScor.do',
				width : 800,
				height : 450,
				onLoad : function() {
					$("#pollId").val(rows.pollId);
				 	$("#pollCode").text(rows.pollCode);
				 	$("#secondaryYear").text(rows.tsecondary.year);
				 	$("#tl").text(rows.title);
				 	$("#pType").text(rows.pollTypeName);
				 	$("#cMan").text(rows.createManName);
					
				},
				buttons : [ {
					text: '提交 ', 
					iconCls:'icon-ok',
					handler: function() { 
						if($("#rulesId").val()!="") {
							parent.dj.messagerConfirm('请确认', '您要提交计分登记、批示吗？', function(r) {
									if (r) {
										var f = p.find('form');
										f.form('submit', {
											url : '${pageContext.request.contextPath}/pollScore!addPollInsAndScor.do',
											success : function(d) {
												var json = $.parseJSON(d);
												if (json.success) {
													gridissue.datagrid('reload');
													p.dialog('close');
												}
												parent.dj.messagerShow({
													msg : json.msg,
													title : '提示'
												});
											}
										});
									}
							});
						} else {parent.dj.messagerAlert('提示', '请选择记分规则！', 'error');}
					} 
				}]
			});
			
		} else {
			parent.dj.messagerAlert('提示', '请选择要编辑的记录！', 'error');
		}
	}
	//弹出批示与采用计分编辑
	function insAndScorEdit() {
		var rows = gridissue.datagrid('getSelected');
		if (rows!=null) {
			var p = dj.dialog({
				title : '批示与采用计分编辑',
				href : '${pageContext.request.contextPath}/pollScore!pollInsAndScorEdit.do',
				width : 800,
				height : 600,
				maximized:true,
				maximizable:true,
				onLoad : function() {
					$("#pollId").val(rows.pollId);
				 	$("#pollCode").text(rows.pollCode);
				 	$("#secondaryYear").text(rows.tsecondary.year);
				 	$("#tl").text(rows.title);
				 	$("#pType").text(rows.pollTypeName);
				 	$("#cMan").text(rows.createManName);
					
				},
				buttons : [ {
					text: '关闭 ', 
					iconCls:'icon-cancel',
					handler: function() { 
						p.dialog('close');
					} 
				}],
				onOpen:function(){
					$(document).ready(function() {
						pollId=rows.pollId;
					});
					
				}
				
			});
			
		} else {
			parent.dj.messagerAlert('提示', '请选择要编辑的记录！', 'error');
		}
	}
	//批量计分登记
	function batchAddScor() {
		var rows = gridissue.datagrid('getSelected');
		if (rows!=null) {
			var p = dj.dialog({
				title : '计分批量登记',
				href : '${pageContext.request.contextPath}/pollScore!pollBatchAddScor.do',
				width : 800,
				height : 450,
				onLoad : function() {
					$("#pollId").val(rows.pollId);
				 	$("#pollCode").text(rows.pollCode);
				 	$("#secondaryYear").text(rows.tsecondary.year);
				 	$("#tl").text(rows.title);
				 	$("#pType").text(rows.pollTypeName);
				 	$("#cMan").text(rows.createManName);
				 	rulesGrid=$('#rulesGrid');
					//计分规则
					rulesGrid.datagrid({
						url:'${pageContext.request.contextPath}/rules!datagrid.do?status=<%=Constants.CODE_TYPE_QYTY_YES%>', 
						width: 'auto',
						height:'auto',
						striped:true,
						nowrap:true,
						rownumbers:true,
						pagination:true,
						pageSize:10,
						pageList:[10,20,50],
						pageNumber:1,
						idField:'id',
						sortName:'id',
						sortOrder : 'asc',
						fit:true,
						checkOnSelect: true, 
						selectOnCheck: true,
						columns:[[
							{field:'id',title:'选择项',width:50,align:'center',checkbox : true},
							{field:'typeid',title:'计分类型',align:'center',width:100},
							{field:'typeName',title:'计分类型名称',align:'center',width:100},
							{field:'name',title:'计分名称',align:'center',width:100},
							{field:'score',title:'分值 ',align:'center',width:100},
							{field:'statusName',title:'状态',align:'center',width:100}
						]]
					});
				},
				buttons : [ {
					text: '提交 ', 
					iconCls:'icon-ok',
					handler: function() { 
						var rulesArr=rulesGrid.datagrid('getSelections');
						if(rulesArr.length==0){
							parent.dj.messagerAlert('提示', '请选择计分信息！', 'error');
						}
						var ids=[];
						for(var i = 0; i<rulesArr.length ;i++){
							ids.push(rulesArr[i].id);
							
						}
						$('#rulesIds').val(ids.join(','));
						var f = p.find('form');
						f.form('submit', {
							url : '${pageContext.request.contextPath}/pollScore!batchAddScor.do',
							success : function(d) {
								var json = $.parseJSON(d);
								if (json.success) {
									gridissue.datagrid('reload');
									p.dialog('close');
								}
								parent.dj.messagerShow({
									msg : json.msg,
									title : '提示'
								});
							}
						});
					} 
				}]
			});
			
		} else {
			parent.dj.messagerAlert('提示', '请选择要编辑的记录！', 'error');
		}
	}
</script>
</head>

<body>
	<!-- 表格 -->
	<div class="easyui-layout" data-options="fit:true">						
		<div data-options="region:'center',border:false" style="padding:1px;background:#fff;border:1px solid #ccc;">
			<table id="gridissue"></table>
		</div>
	</div>
	<div id="columnmenu" class="easyui-menu" style="width:100px;"></div>

	<div id="tb" style="padding:2px;height:auto">
		<div style="margin-bottom:1px">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="$('#win_poll_query').window('open')">查询</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-submit" plain="true" onclick="insAndScor()">批示与采用计分登记</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="insAndScorEdit()">批示与采用计分编辑</a>	
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-submit" plain="true" onclick="batchAddScor()">计分批量登记</a>	
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-view" plain="true" onclick="lookPoll()">查看</a>
		</div>
	</div>
	<div id="win_poll_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:500px;height:220px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="poll_queryForm">
				<table id="poll_queryTable">				
					<tr height="25">
						<td nowrap>社情民意编号：</td>
						<td><input type="text" name="pollCode" style="width:300px;padding:2px;border:1px solid #000;"></td>
					</tr>				
					<tr height="25">
						<td nowrap>标题：</td>
						<td><input type="text" name="title" style="width:300px;padding:2px;border:1px solid #000;"></td>
					</tr>
					<tr height="25">
						<td nowrap>提交者：</td>
						<td><input type="text" name="createMan" style="width:300px;padding:2px;border:1px solid #000;"></td>
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
