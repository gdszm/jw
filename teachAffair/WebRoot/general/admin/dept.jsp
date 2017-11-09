<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	var treegrid;
	var iconData;
	$(function() {

		iconData = [ {
			value : '',
			text : '默认'
		}, {
			value : 'icon-add',
			text : 'icon-add'
		}, {
			value : 'icon-edit',
			text : 'icon-edit'
		}, {
			value : 'icon-remove',
			text : 'icon-remove'
		}, {
			value : 'icon-save',
			text : 'icon-save'
		}, {
			value : 'icon-cut',
			text : 'icon-cut'
		}, {
			value : 'icon-ok',
			text : 'icon-ok'
		}, {
			value : 'icon-no',
			text : 'icon-no'
		}, {
			value : 'icon-cancel',
			text : 'icon-cancel'
		}, {
			value : 'icon-reload',
			text : 'icon-reload'
		}, {
			value : 'icon-search',
			text : 'icon-search'
		}, {
			value : 'icon-print',
			text : 'icon-print'
		}, {
			value : 'icon-help',
			text : 'icon-help'
		}, {
			value : 'icon-undo',
			text : 'icon-undo'
		}, {
			value : 'icon-redo',
			text : 'icon-redo'
		}, {
			value : 'icon-back',
			text : 'icon-back'
		}, {
			value : 'icon-sum',
			text : 'icon-sum'
		}, {
			value : 'icon-tip',
			text : 'icon-tip'
		} ];

		treegrid = $('#treegrid').treegrid({
			url : '${pageContext.request.contextPath}/dept!treegrid.do',
			toolbar : [ {
				text : '展开',
				iconCls : 'icon-redo',
				handler : function() {
					var node = treegrid.treegrid('getSelected');
					if (node) {
						treegrid.treegrid('expandAll', node.cid);
					} else {
						treegrid.treegrid('expandAll');
					}
				}
			}, '-', {
				text : '折叠',
				iconCls : 'icon-undo',
				handler : function() {
					var node = treegrid.treegrid('getSelected');
					if (node) {
						treegrid.treegrid('collapseAll', node.cid);
					} else {
						treegrid.treegrid('collapseAll');
					}
				}
			}, '-', {
				text : '刷新',
				iconCls : 'icon-reload',
				handler : function() {
					treegrid.treegrid('reload');
				}
			}, '-', {
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
				text : '编辑',
				iconCls : 'icon-edit',
				handler : function() {
					edit();
				}
			}, '-', {
				text : '取消选中',
				iconCls : 'icon-undo',
				handler : function() {
					treegrid.treegrid('unselectAll');
				}
			}, '-' ],
			title : '',
			iconCls : 'icon-save',
			fit : true,
			fitColumns : false,
			nowrap : false,
			animate : false,
			border : false,
			idField : 'cid',
			treeField : 'cname',
			frozenColumns : [ [ {
				title : 'cid',
				field : 'cid',
				width : 150,
				hidden : true
			}, {
				field : 'cname',
				title : '部门名称',
				width : 180,
				formatter : function(value) {
					if (value) {
						return dj.fs('<span title="{0}">{1}</span>', value, value);
					}
				}
			} ] ],
			columns : [ [ {
				field : 'iconCls',
				title : '部门图标',
				width : 70,
				formatter : function(value) {
					if (!value) {
						return '';
					} else {
						return dj.fs('<span class="{0}" style="display:inline-block;vertical-align:middle;width:16px;height:16px;"></span>', value);
					}
				}
			}, {
				field : 'cseq',
				title : '排序',
				width : 40
			}, {
				field : 'pid',
				title : '上级部门ID',
				width : 150,
				hidden : true
			}, {
				field : 'pname',
				title : '上级部门',
				width : 150
			}, {
				field : 'cdesc',
				title : '部门描述',
				width : 250
			} ] ],
			onContextMenu : function(e, row) {
				e.preventDefault();
				$(this).treegrid('unselectAll');
				$(this).treegrid('select', row.cid);
				$('#menu').dept('show', {
					left : e.pageX,
					top : e.pageY
				});
			},
			onLoadSuccess : function(row, data) {
				/*var t = $(this);
				if (data) {
					$(data).each(function(index, d) {
						if (this.state == 'closed') {
							t.treegrid('expandAll');
						}
					});
				}*/
			},
			onExpand : function(row) {
				treegrid.treegrid('unselectAll');
			},
			onCollapse : function(row) {
				treegrid.treegrid('unselectAll');
			}
		});

	});

	function edit() {
		var node = treegrid.treegrid('getSelected');
		if (node) {
			var p = parent.dj.dialog({
				title : '编辑部门',
				href : '${pageContext.request.contextPath}/dept!deptEdit.do',
				width : 460,
				height : 220,
				buttons : [ {
					text : '编辑',
					handler : function() {
						var f = p.find('form');
						f.form('submit', {
							url : '${pageContext.request.contextPath}/dept!edit.do',
							success : function(d) {
								var json = $.parseJSON(d);
								if (json.success) {
									treegrid.treegrid('reload');
									p.dialog('close');
									parent.ctrlTree.tree('reload');
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
					var pid = f.find('input[name=pid]');
					var iconCls = f.find('input[name=iconCls]');
					var iconCombo = iconCls.combobox({
						data : iconData,
						formatter : function(v) {
							return dj.fs('<span class="{0}" style="display:inline-block;vertical-align:middle;width:16px;height:16px;"></span>{1}', v.value, v.value);
						}
					});
					var ptree = pid.combotree({
						lines : true,
						url : '${pageContext.request.contextPath}/dept!doNotNeedSession_treeRecursive.do',
						onLoadSuccess : function() {
							parent.$.messager.progress('close');
						}
					});
					f.form('load', node);
				}
			});
		} else {
			parent.dj.messagerAlert('提示', '请选中要编辑的记录！', 'error');
		}
	}
	function append() {
		var p = parent.dj.dialog({
			title : '添加部门',
			href : '${pageContext.request.contextPath}/dept!deptAdd.do',
			width : 460,
			height : 220,
			buttons : [ {
				text : '添加',
				handler : function() {
					var f = p.find('form');
					f.form('submit', {
						url : '${pageContext.request.contextPath}/dept!add.do',
						success : function(d) {
							var json = $.parseJSON(d);
							if (json.success) {
								treegrid.treegrid('reload');
								p.dialog('close');
								parent.ctrlTree.tree('reload');/*刷新左侧菜单树*/
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
				var pid = f.find('input[name=pid]');
				var iconCls = f.find('input[name=iconCls]');
				var iconCombo = iconCls.combobox({
					data : iconData,
					formatter : function(v) {
						return dj.fs('<span class="{0}" style="display:inline-block;vertical-align:middle;width:16px;height:16px;"></span>{1}', v.value, v.value);
					}
				});
				var ptree = pid.combotree({
					lines : true,
					url : '${pageContext.request.contextPath}/dept!doNotNeedSession_tree.do'
				});
			}
		});
	}
	function remove() {
		var node = treegrid.treegrid('getSelected');
		if (node) {
			parent.dj.messagerConfirm('询问', '您确定要删除【' + node.cname + '】？', function(b) {
				if (b) {
					$.ajax({
						url : '${pageContext.request.contextPath}/dept!delete.do',
						data : {
							cid : node.cid
						},
						cache : false,
						dataType : 'JSON',
						success : function(r) {
							if (r.success) {
								treegrid.treegrid('remove', r.obj);
								parent.ctrlTree.tree('reload');/*刷新左侧菜单树*/
							}
							parent.dj.messagerShow({
								msg : r.msg,
								title : '提示'
							});
						}
					});
				}
			});
		}
	}
</script>
</head>
<body class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="treegrid"></table>
	</div>

	<div id="menu" class="easyui-menu" style="width:120px;display: none;">
		<div onclick="append();" data-options="iconCls:'icon-add'">增加</div>
		<div onclick="remove();" data-options="iconCls:'icon-remove'">删除</div>
		<div onclick="edit();" data-options="iconCls:'icon-edit'">编辑</div>
	</div>
</body>
</html>