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
			url : 'bug!datagrid.do',
			iconCls : 'icon-save',
			pagination : true,
			pagePosition : 'bottom',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40 ],
			fit : true,
			fitColumns : true,
			nowrap : false,
			border : false,
			idField : 'cid',
			sortName : 'ccreatedatetime',
			sortOrder : 'desc',
			frozenColumns : [ [ {
				title : '编号',
				field : 'cid',
				width : 150,
				sortable : true,
				checkbox : true
			}, {
				title : 'BUG名称',
				field : 'cname',
				width : 150,
				sortable : true
			} ] ],
			columns : [ [ {
				title : 'BUG创建时间',
				field : 'ccreatedatetime',
				sortable : true,
				width : 150
			}, {
				title : 'BUG描述',
				field : 'cdesc',
				width : 150,
				formatter : function(value, rowData, rowIndex) {
					return '<span class="icon-search" style="display:inline-block;vertical-align:middle;width:16px;height:16px;"></span><a href="javascript:void(0);" onclick="showCdesc(' + rowIndex + ');">查看详细</a>';
				}
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
				title : '修改上报BUG',
				href : '${pageContext.request.contextPath}/bug!bugEdit.do?cid=' + rows[0].cid,
				width : 500,
				height : 450,
				buttons : [ {
					text : '修改',
					handler : function() {
						var f = p.find('form');
						f.form({
							url : '${pageContext.request.contextPath}/bug!edit.do',
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
						f.submit();
					}
				} ],
				onLoad : function() {
					var f = p.find('form');
					f.find('input[name=cid]').val(rows[0].cid);
					f.find('input[name=cname]').val(rows[0].cname);
					var editor = f.find('textarea[name=cdesc]').xheditor({
						tools : 'full',
						html5Upload : true,
						upMultiple : 4,
						upLinkUrl : '${pageContext.request.contextPath}/bug!upload.do',
						upLinkExt : 'zip,rar,txt,doc,docx,xls,xlsx',
						upImgUrl : '${pageContext.request.contextPath}/bug!upload.do',
						upImgExt : 'jpg,jpeg,gif,png'
					});
				}
			});
		} else if (rows.length > 1) {
			parent.dj.messagerAlert('提示', '同一时间只能编辑一条记录！', 'error');
		} else {
			parent.dj.messagerAlert('提示', '请选择要编辑的记录！', 'error');
		}
	}
	function append() {
		var p = parent.dj.dialog({
			title : '上报BUG',
			href : '${pageContext.request.contextPath}/bug!bugAdd.do',
			width : 500,
			height : 450,
			buttons : [ {
				text : '上报',
				handler : function() {
					var f = p.find('form');
					f.form({
						url : '${pageContext.request.contextPath}/bug!add.do',
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
					f.submit();
				}
			} ],
			onLoad : function() {
				var f = p.find('form');
				var editor = f.find('textarea[name=cdesc]').xheditor({
					tools : 'full',
					html5Upload : true,
					upMultiple : 4,
					upLinkUrl : '${pageContext.request.contextPath}/bug!upload.do',
					upLinkExt : 'zip,rar,txt,doc,docx,xls,xlsx',
					upImgUrl : '${pageContext.request.contextPath}/bug!upload.do',
					upImgExt : 'jpg,jpeg,gif,png'
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
						url : '${pageContext.request.contextPath}/bug!delete.do',
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
	function showCdesc(rowIndex) {
		var rows = datagrid.datagrid('getRows');
		var row = rows[rowIndex];

		var p = parent.dj.dialog({
			title : 'BUG名称[' + row.cname + ']',
			modal : true,
			maximizable : true,
			width : 800,
			height : 600,
			content : '<iframe src="${pageContext.request.contextPath}/bug!showCdesc.do?cid=' + row.cid + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>'
		});

		datagrid.datagrid('unselectAll');
	}
</script>
</head>
<body class="easyui-layout">
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