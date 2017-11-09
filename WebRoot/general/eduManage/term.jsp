<%@page import="com.tlzn.util.base.Constants"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	function initCombox(form){
		initCombobox(form,'acadeYear','${pageContext.request.contextPath}/dic!combox.do?ctype=acadeYear');
		initCombobox(form,'termType','${pageContext.request.contextPath}/dic!combox.do?ctype=termType');
		initCombobox(form,'isNowTerm','${pageContext.request.contextPath}/dic!combox.do?ctype=YESNO');
	}
	var p;
	var datagrid;
	$(function(){
		initCombox("term_queryForm");
		datagrid = $('#termDatagrid').datagrid({
			url:'${pageContext.request.contextPath}/term!datagrid.do',
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
			fitColumns :false,
			checkOnSelect:false,
			selectOnCheck:false,
			frozenColumns:[[
				{field:'id',title:'ID',width:100,align:'center',checkbox : true},
				{field:'termId',title:'学期编号',width:100,align:'center',
					formatter: function(value,row,index){
						return row.id;
					}
				},
				{field:'acadeYearName',title:'学年',width:100,align:'center'},
				{field:'termName',title:'学期名',width:300,align:'center'}
				]],
			columns:[[
				{field:'termTypeName',title:'学期类型',width:100,align:'center'},
				{field:'isNowTerm',title:'是否当前学期',width:150,align:'center'},
				{field:'weeks',title:'学期周数',width:150,align:'center'},
				{field:'weekDays',title:'周天数',width:150,align:'center'}
			]], 
			toolbar : '#tb'
		});
	});
	//查看
	function view() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			var p = dj.dialog({
				title : '学期详情',
				href : '${pageContext.request.contextPath}/term!termView.do?id='+rows[0].id,
				maximized:true,
				width : 300,
				height : 200,
				onLoad : function() {
					initCombox('noticeViewForm');
			
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
			title : '新增学期',
			href : '${pageContext.request.contextPath}/term!termAdd.do',
			maximized:true,
			width : 700,
			height : 420,
			iconCls:'icon-save',
			buttons : [ {
					text : ' 提交 ',
					iconCls:'icon-ok',
					handler : function() {
						var f = p.find('form');
						f.form('submit', {
							url : '${pageContext.request.contextPath}/term!add.do',
							success : function(d) {
								var json = $.parseJSON(d);
								if (json.success) {
									datagrid.datagrid('reload');
									p.dialog('close');
								}
								$.messager.alert('提示', json.msg, 'info');
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
				}
		});
	}
	//修改 
	function edit() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			var p = dj.dialog({
				title : '编辑学期信息',
				href : '${pageContext.request.contextPath}/term!termEdit.do?id='+rows[0].id,
				width : 700,
				height : 420,
				maximized : true,
				buttons : [ {
					text : '提交修改',
					iconCls:'icon-ok',
					handler : function() {
							var f = p.find('form');
							f.form('submit', {
								url : '${pageContext.request.contextPath}/term!edit.do',
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
					text: '关闭', 
					iconCls:'icon-cancel',
					handler: function() { 
						p.dialog('close'); 
					} 
				}
				],
				onLoad : function() {
					initCombox('addForm');
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
			parent.dj.messagerConfirm('请确认', '您要删除当前所选项目？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/term!delete.do',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(d) {
							datagrid.datagrid('load');
							datagrid.datagrid('unselectAll');
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
	
	//查询
	function _search() {
		datagrid.datagrid('load', dj.serializeObject($('#term_queryForm')));
		 $('#win_term_query').window('close');
	}
	
	function cleanSearch() {
		datagrid.datagrid('load', {});
		$('#term_queryForm input').val('');
		$('#termAtt').combobox('setValue', '');
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
		<table id="termDatagrid"></table>
	</div>
	<div id="win_term_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:500px;height:430px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="term_queryForm">
				<table id="poll_queryTable">
					<tr height="30">
						<td nowrap>学期编号：</td>
						<td colspan="3"><input type="text" class="easyui-validatebox"  name="id" style="width:300px;"></td>
					</tr>
					<tr height="30">
						<td nowrap>学年 ：</td>
						<td colspan="3"> <input id="acadeYear" name="acadeYear" style="width:302px;" panelHeight="130px" ></td>
					</tr>
					<tr height="30">
						<td nowrap>学期名：</td>
						<td colspan="3"><input type="text" class="easyui-validatebox"  name="termName" style="width:300px;"></td>
					</tr>	
					<tr height="30">
						<td nowrap>学期类型 ：</td>
						<td colspan="3"> <input id="termType" name="termType" style="width:302px;" panelHeight="130px" ></td>
					</tr>
					<tr height="30">
						<td nowrap>是否当前学期 ：</td>
						<td colspan="3"> <input id="isNowTerm" name="isNowTerm" style="width:302px;" panelHeight="130px" ></td>
					</tr>
					<tr height="30">
						<td nowrap>备注：</td>
						<td colspan="3"><input type="text" class="easyui-validatebox"  name="comment" style="width:300px;"></td>
					</tr>	
				</table>
				</form>
				</center>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;height:40px">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)" onclick="_search()">查询</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)" onclick="cleanSearch()">重置</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#win_term_query').window('close');">取消</a>
			</div>
		</div>
	</div>
	<div id="tb">
			<div style="margin-bottom:1px">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="$('#win_term_query').window('open');">查询</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="appand();">新增</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit();">修改</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="remove();">删除</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="datagrid.datagrid('clearChecked');datagrid.datagrid('clearSelections');datagrid.datagrid('unselectAll');">取消选中</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-info" plain="true" onclick="view();">查看详细</a>
			</div>
	</div>
</body>
</html>