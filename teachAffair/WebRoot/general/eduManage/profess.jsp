<%@page import="com.tlzn.util.base.Constants"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<!--公用js-->
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/jslib/common.js"></script>
<script type="text/javascript" charset="utf-8">
	function initCombox(form) {
		initCombobox(form, 'courseAtt','${pageContext.request.contextPath}/dic!combox.do?ctype=courseAtt');
		initCombobox(form, 'sex','${pageContext.request.contextPath}/dic!combox.do?ctype=SEX');
	}
	var p;
	var datagrid;
	$(function() {
		initCombox("profess_queryForm");
		datagrid = $('#professDatagrid').datagrid({
			url : '${pageContext.request.contextPath}/profess!datagridCount.do',
			width : 'auto',
			height : 'auto',
			striped : true,
			nowrap : true,
			rownumbers : true,
			idField : 'teacherId',
			singleSelect : true,
			pagination : true,
			pageSize : 20,
			pageList : [ 20, 50 ],
			pageNumber : 1,
			fit : true,
			fitColumns : false,
			checkOnSelect : false,
			selectOnCheck : false,
			frozenColumns : [ [ {
				field : 'teacherId',
				title : 'ID',
				width : 100,
				align : 'center',
				checkbox : true
			}, {
				field : 'teacherNo',
				title : '教师编号',
				width : 100,
				align : 'center',
				formatter : function(value, row, index) {
					return row.teacherId;
				}
			}, {
				field : 'teacherName',
				title : '教师姓名(点击可排序)',
				width : 150,
				align : 'center',
				sortable:true
			}
			]],
			columns : [ [{
				field : 'sexName',
				title : '教师性别(点击可排序)',
				width : 150,
				align : 'center',
				sortable:true
			},{
				field : 'courseSu',
				title : '授课门数(点击可排序)',
				width : 150,
				align : 'center',
				sortable:true
			}
			] ],
			toolbar : '#tb'
		});
	});
	//查看
	function view() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			var p = dj.dialog({
						title : '授课详情',
						href : '${pageContext.request.contextPath}/profess!professView.do?id='
								+ rows[0].id,
						maximized : true,
						width : 300,
						height : 200,
						onLoad : function() {
							initCombox('noticeViewForm');

						},
						buttons : [ {
							text : '关闭 ',
							iconCls : 'icon-cancel',
							handler : function() {
								p.dialog('close');
							}
						} ]

					});

		} else if (rows.length > 1) {
			parent.dj.messagerAlert('提示', '同一时间只能查看一条记录！', 'error');
		} else {
			parent.dj.messagerAlert('提示', '请选择要查看的记录！', 'error');
		}
	}
	//新增授课教师
	function appand() {
		var p = dj.dialog({
					title : '新增授课教师',
					href : '${pageContext.request.contextPath}/profess!professTeacherAddPage.do',
					//maximized : true,
					width : 400,
					height :250,
					iconCls : 'icon-save',
					buttons : [
							{
								text : ' 提交 ',
								iconCls : 'icon-ok',
								handler : function() {
									var f = p.find('form');
									f.form('submit',{
										url : '${pageContext.request.contextPath}/profess!professTeacherAdd.do',
										success : function(d) {
											var json = $.parseJSON(d);
											if (json.success) {
												datagrid.datagrid('reload');
												p.dialog('close');
											}
											$.messager.alert('提示',json.msg,'info');
										}
									});
								}
							}, {
								text : ' 取 消 ',
								iconCls : 'icon-cancel',
								handler : function() {
									p.dialog('close');
								}
							} ],
					onLoad : function() {
						initCombox('addForm');
					}
				});
	}
	//管理授课列表
	function professList() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			var p = dj.dialog({
						title : '授课列表',
						href : '${pageContext.request.contextPath}/profess!professList.do?teacherId='+ rows[0].teacherId,
						width : 700,
						height : 420,
						maximized : true,
						buttons : [
								{
									text : '提交修改',
									iconCls : 'icon-ok',
									handler : function() {
										var f = p.find('form');
										f.form('submit',{
												url : '${pageContext.request.contextPath}/profess!edit.do',
												success : function(d) {
													var json = $.parseJSON(d);
													if (json.success) {
														datagrid.datagrid('reload');
														p.dialog('close');
													}
													parent.dj.messagerShow({msg : json.msg,title : '提示'});
												}
											});
									}
								}, {
									text : '关闭',
									iconCls : 'icon-cancel',
									handler : function() {
										p.dialog('close');
									}
								} ],
						onLoad : function() {
							initCombox('addForm');
						}
					});
		} else if (rows.length > 1) {
			parent.dj.messagerAlert('提示', '同一时间只能管理一位教师的授课列表！', 'error');
		} else {
			parent.dj.messagerAlert('提示', '请点选要管理的一位教师（变成黄色）！', 'error');
		}
	}

	//删除
	function remove() {
		var rows = datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			parent.dj.messagerConfirm(
							'请确认',
							'您要删除当前所选项目？',
							function(r) {
								if (r) {
									for ( var i = 0; i < rows.length; i++) {
										if (rows[i].courseSu!='0') {
											parent.dj.messagerAlert('提示', '只能删除授课门数为0的教师，请重新勾选或者点选授课门数不为0的教师然后点击“管理授课列表”删除所有列表后再删除该授课教师！', 'error');
											return;
										} else {
											ids.push(rows[i].teacherId);
										}
									}
									$.ajax({
												url : '${pageContext.request.contextPath}/profess!professTeacherdel.do',
												data : {
													ids : ids.join(',')
												},
												dataType : 'json',
												success : function(d) {
													datagrid.datagrid('load');
													datagrid
															.datagrid('unselectAll');
													datagrid
															.datagrid('unselectAll');
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
		datagrid.datagrid('load', dj.serializeObject($('#profess_queryForm')));
		$('#win_profess_query').window('close');
	}

	function cleanSearch() {
		datagrid.datagrid('load', {});
		$('#profess_queryForm input').val('');
		$('#sexId').combobox('setValue', '');
		$('#courseAtt').combobox('setValue', '');
	}

	$.extend($.fn.validatebox.defaults.rules, {
		sel : {
			validator : function(value) {
				return value != '请选择...';
			},
			message : '此项必须选择！'
		}
	});
</script>
</head>
<body class="easyui-layout" data-options="fit:true">
	<!-- 表格 -->
	<div data-options="region:'center',border:false"
		style="overflow: hidden;">
		<table id="professDatagrid"></table>
	</div>
	<div id="win_profess_query" class="easyui-window"
		data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true"
		style="width:500px;height:320px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false"
				style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
					<form id="profess_queryForm">
						<table id="poll_queryTable">
							<tr height="30">
								<td nowrap>教师编号：</td>
								<td colspan="3"><input type="text"
									class="easyui-validatebox" name="teacherId" style="width:300px;">
								</td>
							</tr>
							<tr height="30">
								<td nowrap>教师姓名：</td>
								<td colspan="3"><input type="text" class="easyui-validatebox" name="teacherName" style="width:300px;">
								</td>
							</tr>
							<tr height="30">
								<td nowrap>教师性别：</td>
								<td colspan="3"><input id="sexId" name="sex" style="width:302px;" panelHeight="130px">
								</td>
							</tr> 
							<tr height="30">
								<td nowrap>授课门数(包含)：</td>
								<td colspan="3">
								<input type="text" class="easyui-numberbox" data-options="min:0,max:100" name="courseSuStart" style="width:140px;">
								~
								<input type="text" class="easyui-numberbox" data-options="min:0,max:100" name="courseSuEnd" style="width:140px;">
								</td>
							</tr>
						</table>
					</form>
				</center>
			</div>
			<div data-options="region:'south',border:false"
				style="text-align:right;padding:5px 0;height:40px">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)" onclick="_search()">查询</a> 
				<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)" onclick="cleanSearch()">重置</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#win_profess_query').window('close');">取消</a>
			</div>
		</div>
	</div>
	<div id="tb">
		<div style="margin-bottom:1px">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="$('#win_profess_query').window('open');">查询</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="appand();">新增授课教师</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="remove();">删除授课教师</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="professList();">管理授课列表</a> 
			<!-- 					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-enable" plain="true" onclick="pub();">发布</a> -->
			<!-- 					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-stop" plain="true" onclick="cancelPub();">取消发布</a> -->
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="datagrid.datagrid('clearChecked');datagrid.datagrid('clearSelections');datagrid.datagrid('unselectAll');">取消选中</a>
<!-- 			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-info" plain="true" onclick="view();">查看详细</a> -->
		</div>
	</div>
</body>
</html>