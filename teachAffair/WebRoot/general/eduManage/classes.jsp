<%@page import="com.tlzn.util.base.Constants"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	function initCombox(form){
		initCombobox(form,'type','${pageContext.request.contextPath}/dic!combox.do?ctype=classesType');
		initCombobox(form,'trainLevel','${pageContext.request.contextPath}/dic!combox.do?ctype=trainLevel');
	}
	var p;
	var datagrid;
	$(function(){
		initCombox("searchForm"); 
		initCombox("classesDatagrid");
		initCombox("classes_queryForm");
		datagrid = $('#classesDatagrid').datagrid({
			url:'${pageContext.request.contextPath}/classes!datagrid.do',
        	width: 'auto',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			idField:'id',
			singleSelect:true,
			pagination:true,
			pageSize:20,
			pageList:[20,50],
			pageNumber:1,
			fit:true,
			fitColumns :true,
			checkOnSelect:false,
			selectOnCheck:false,
			frozenColumns:[[
				{field:'id',title:'ID',width:100,align:'center',checkbox : true},
				{field:'className',title:'班级名称',width:250,align:'center'}
				]],
			columns:[[
				{field:'trainLevelName',title:'培养层次',width:100,align:'center'},
				{field:'sysLength',title:'学制',width:100,align:'center'},
				{field:'buildDate',title:'建班年月',width:100,align:'center'},
				{field:'adminTeacherName',title:'班主任',width:100,align:'center'},
				{field:'adminStuName',title:'班长',width:100,align:'center'},
				{field:'secStuName',title:'团支书',width:100,align:'center'},
				{field:'manNum',title:'男生人数',width:100,align:'center'},
				{field:'womanNum',title:'女生人数',width:100,align:'center'},
				{field:'livingNum',title:'住校生人数',width:100,align:'center'},
				{field:'crtTime',title:'录入系统时间',width:150,align:'center',
					formatter: function(value,row,index){
						if (value){
							return value.substring(0,19);
						} else {
							return value;
						}
					}
				}
    		]],   
			toolbar : [
			{
				text : '查询',
				iconCls : 'icon-search',
				handler : function() {
					$('#win_classes_query').window('open');
				}
			}, '-', {
				text : '新增',
				iconCls : 'icon-add',
				handler : function() {
					appand();
				}
			}, '-', {
				text : '修改',
				iconCls : 'icon-edit',
				handler : function() {
					edit();
				}
			}, '-', {
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					remove();
				}
			}, '-', {
				text : '取消选中',
				iconCls : 'icon-undo',
				handler : function() {
					datagrid.datagrid('clearChecked');
					datagrid.datagrid('clearSelections');
					datagrid.datagrid('unselectAll');
				}
			}, '-',
			{
				text : '查看详细',
				iconCls : 'icon-info',
				handler : function() {
					view();
				}
			}
			]
		});
	});
	
	//查看
	function view() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			var p = dj.dialog({
				title : '班级详情',
				href : '${pageContext.request.contextPath}/classes!get.do?id='+rows[0].id,
				maximized:true,
				width : 300,
				height : 200,
				onLoad : function() {
					initCombox('classesViewForm');
				},
				buttons : [ {
					text: '关闭 ', 
					iconCls:'icon-cancel',
					handler: function() { 
					p.dialog('close'); 
					} 
				}]
				
			});
			
		} else if (rows.length > 1) {
			parent.dj.messagerAlert('提示', '同一时间只能查看一条记录！', 'error');
		} else {
			parent.dj.messagerAlert('提示', '请选择要查看的记录！', 'error');
		}
	}
		
	//新增
	function appand() {
			var p = dj.dialog({
			title : '新增班级',
			href : '${pageContext.request.contextPath}/classes!classesAdd.do',
			maximized:true,
			iconCls:'icon-save',
			buttons : [ {
					text : ' 提交 ',
					iconCls:'icon-ok',
					handler : function() {
							var f = p.find('form');
							f.form('submit', {
								url : '${pageContext.request.contextPath}/classes!add.do',
								success : function(d) {
									var json = $.parseJSON(d);
									if (json.success) {
										datagrid.datagrid('reload');
										p.dialog('close');
									}
									parent.dj.messagerAlert('提示', json.msg, 'info');
								}
							});
					}
				},{
					text: ' 取 消 ', 
					iconCls:'icon-cancel',
					handler: function() { 
						p.dialog('close'); 
					} 
				}],
				onLoad : function() {
					initCombox('addForm');
					$('#trainLevelAddId').combobox({
						url : '${pageContext.request.contextPath}/dic!combox.do?ctype=trainLevel',
						valueField:'cvalue', 
						panelHeight:'100',
				        textField:'ckey',
				        required:true,
				        validType:'sel',
				        onSelect:function(row){
						}
					});
				}
		});
	}
	//修改 
	function edit() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			var p = dj.dialog({
				title : ' 班级修改 ',
				href : '${pageContext.request.contextPath}/classes!classesEdit.do?id='+rows[0].id,
				maximized:true,
				width : 800,
				height : 600,
				buttons : [ {
					text : ' 提交修改 ',
					iconCls : 'icon-ok',
					handler : function() {
						var f = p.find('form');
						f.form('submit', {
							url : '${pageContext.request.contextPath}/classes!edit.do',
							success : function(d) {
								var json = $.parseJSON(d);
								if (json.success) {
									datagrid.datagrid('reload');
									p.dialog('close');
								}
								parent.dj.messagerShow({
									msg : json.msg,
									title : '提示'
								});
							}
						});
					}
				} ,{ 
					text: ' 取 消 ', 
					iconCls:'icon-cancel',
					handler: function() { 
						p.dialog('close'); 
					} 
				}
				],
				onLoad : function() {
					initCombox('editForm');
					$('#trainLevelAddId').combobox({
						url : '${pageContext.request.contextPath}/dic!combox.do?ctype=trainLevel',
						valueField:'cvalue', 
						panelHeight:'100',
				        textField:'ckey',
				        required:true,
				        validType:'sel',
				        onSelect:function(row){
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

	//删除
	function remove() {
		var rows = datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			parent.dj.messagerConfirm('请确认', '您要删除当前所选班级吗？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/classes!delete.do',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(d) {
							datagrid.datagrid('load');
							datagrid.datagrid('unselectAll');
							parent.dj.messagerShow({
								title : '提示',
								msg : d.msg
							});
						}
					});
				}
			});
		} else {
			parent.dj.messagerAlert('提示', '请勾选要删除的记录！', 'error');
		}
	}
	//发布班级处理
	function pub(){
		var rows = datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			parent.dj.messagerConfirm('请确认', '您要发布当前所选班级？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/classes!pub.do',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(d) {
							datagrid.datagrid('load');
							datagrid.datagrid('unselectAll');
							parent.dj.messagerShow({
								title : '提示',
								msg : d.msg
							});
						}
					});
				}
			});
		} else {
			parent.dj.messagerAlert('提示', '请勾选要发布的班级！', 'error');
		}
	}
	//取消发布班级处理
	function cancelPub(){
		var rows = datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			parent.dj.messagerConfirm('请确认', '您要取消发布当前所选班级？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/classes!cancelPub.do',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(d) {
							datagrid.datagrid('load');
							datagrid.datagrid('unselectAll');
							parent.dj.messagerShow({
								title : '提示',
								msg : d.msg
							});
						}
					});
				}
			});
		} else {
			parent.dj.messagerAlert('提示', '请勾选要取消发布的班级！', 'error');
		}
	}
	//查询
	function _search() {
		datagrid.datagrid('load', dj.serializeObject($('#classes_queryForm')));
		 $('#win_classes_query').window('close');
	}
	
	function cleanSearch() {
		datagrid.datagrid('load', {});
		$('#classes_queryForm input').val('');
		$('#typeId').combobox('setValue', '');
	}
	
	$.extend($.fn.validatebox.defaults.rules, {  
		sel: {  
			validator: function(value){ 
				return value != '请选择...';  
			},  
			message: '此项必须选择！'  
		}  
	}); 
</script>
</head>
<body class="easyui-layout" data-options="fit:true">
	<!-- 表格 -->
	<div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="classesDatagrid"></table>
	</div>
	<div id="win_classes_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:500px;height:274px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="classes_queryForm">
				<input type="hidden" name="filingFlg" id="filingFlgId"/>
				<table id="poll_queryTable">
					<tr height="25">
						<td nowrap>班级名称：</td>
						<td colspan="3"><input type="text" class="easyui-validatebox"  name="className" style="width:300px;"></td>
					</tr>	
					<tr height="25">
						<td nowrap>培养层次：</td>
						<td colspan="3"><input id="trainLevelId"  name="trainLevel"  type="text" class="easyui-validatebox" style="width:300px;"></td>
					</tr>			
					<tr height="25">
						<td nowrap>学制(年)：</td>
						<td colspan="3"><input type="text" class="easyui-validatebox"  name="sysLength" style="width:300px;"></td>
					</tr>	
					<tr height="25">
						<td nowrap>建班年月 ：</td>
						<td colspan="3"> <input type="text" class="easyui-validatebox"  name="buildDate" style="width:300px;"></td>
					</tr>
					<tr height="25">
						<td nowrap>班主任 ：</td>
						<td colspan="3"> <input type="text" class="easyui-validatebox"  name="adminTeacherName" style="width:300px;"></td>
					</tr>
				</table>
				</form>
				</center>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;height:40px">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)" onclick="_search()">查询</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)" onclick="cleanSearch()">重置</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#win_classes_query').window('close');">取消</a>
			</div>
		</div>
	</div>
</body>
</html>