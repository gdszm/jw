<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	var datagrid;
	$(function() {

		datagrid = $('#datagrid').datagrid({
			url : '${pageContext.request.contextPath}/role!datagrid.do',
			title : '',
			iconCls : 'icon-save',
			pagination : true,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40 ],
			fit : true,
			fitColumns : true,
			nowrap : false,
			border : false,
			idField : 'cid',
			sortName : 'cname',
			sortOrder : 'desc',
			checkOnSelect : false,
			selectOnCheck : true,
			singleSelect:true,
			frozenColumns : [ [ {
				title : '编号',
				field : 'cid',
				width : 150,
				sortable : true,
				checkbox : true
			}, {
				title : '角色名称',
				field : 'cname',
				width : 150,
				sortable : true
			} ] ],
			columns : [ [ {
				title : '备注',
				field : 'cdesc',
				width : 150
			}, {
				title : '拥有权限ID',
				field : 'authIds',
				width : 300,
				hidden : true
			}, {
				title : '拥有权限',
				field : 'authNames',
				width : 300
			} ] ],
			toolbar : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					append();
				}
			}, '-', {
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					remove();
				}
			}, '-', {
				text : '修改',
				iconCls : 'icon-edit',
				handler : function() {
					edit();
				}
			}, '-', {
				text : '取消选中',
				iconCls : 'icon-undo',
				handler : function() {
					datagrid.datagrid('unselectAll');
				}
			}, '-' ],
			onRowContextMenu : function(e, rowIndex, rowData) {
				e.preventDefault();
				$(this).datagrid('unselectAll');
				$(this).datagrid('selectRow', rowIndex);
				$('#menu').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			}
		});

	});

	function edit() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			var p = parent.dj.dialog({
				title : '编辑角色',
				href : '${pageContext.request.contextPath}/role!roleEdit.do',
				width : 820,
				height : 460,
				buttons : [ {
					text : '编辑',
					handler : function() {
						var f = p.find('form');
						f.form('submit', {
							url : '${pageContext.request.contextPath}/role!edit.do',
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
				} ],
				onLoad : function() {
					var f = p.find('form');
					var authIds = f.find('input[name=authIds]');
					var authIdsTree = authIds.combotree({
						lines : true,
						url : '${pageContext.request.contextPath}/auth!doNotNeedSession_treeRecursive.do',
						checkbox : true,
						multiple : true,
						cascadeCheck:false,						
						onLoadSuccess : function() {
							parent.$.messager.progress('close');
						},
						onCheck:function(node,checked){
							if(checked){
								checkNode($('#authIds').tree('getParent',node.target));
							}
						}
					});
					f.form('load', {
						cid : rows[0].cid,
						cname : rows[0].cname,
						cdesc : rows[0].cdesc,
						authIds : dj.getList(rows[0].authIds)
					});
				}
			});
		} else if (rows.length > 1) {
			parent.dj.messagerAlert('提示', '同一时间只能编辑一条记录！', 'error');
		} else {
			parent.dj.messagerAlert('提示', '请勾选要编辑的记录！', 'error');
		}
	}
	function append() {
		var p = parent.dj.dialog({
			title : '添加角色',
			href : '${pageContext.request.contextPath}/role!roleAdd.do',
			width : 620,
			height : 460,
			buttons : [ {
				text : '添加',
				handler : function() {
					var f = p.find('form');
					f.form('submit', {
						url : '${pageContext.request.contextPath}/role!add.do',
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
			} ],
			onLoad : function() {
				var f = p.find('form');
				var authIds = f.find('input[name=authIds]');
				var authIdsTree = authIds.combotree({
					lines : true,
					url : '${pageContext.request.contextPath}/auth!doNotNeedSession_treeRecursive.do',
					checkbox : true,
					multiple : true
				});
			}
		});
	}
	function remove() {
		var rows = datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			parent.dj.messagerConfirm('请确认', '您要删除当前所选项目？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].cid);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/role!delete.do',
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
</script>
</head>
<body class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="datagrid"></table>
	</div>

	<div id="menu" class="easyui-menu" style="width:120px;display: none;">
		<div onclick="append();" data-options="iconCls:'icon-add'">增加</div>
		<div onclick="remove();" data-options="iconCls:'icon-remove'">删除</div>
		<div onclick="edit();" data-options="iconCls:'icon-edit'">编辑</div>
	</div>
</body>
</html>