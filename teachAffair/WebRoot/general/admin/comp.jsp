<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	function initCombox(form){
		initCombobox(form,'companyType','${pageContext.request.contextPath}/dic!combox.do?ctype=COMPANYTYPE');
		initCombobox(form,'status','${pageContext.request.contextPath}/dic!combox.do?ctype=STATUS');
}
	
	var datagrid;	
	$(document).ready(function() {
	datagrid=$('#listgrid');

		datagrid.datagrid({
			url:'${pageContext.request.contextPath}/comp!datagrid.do',
			width: 'auto',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			singleSelect:true,
			pagination:true,
			pageSize:10,
			pageList:[10,15,20],
			pageNumber:1,
			idField:'companyId',
			sortName : 'companyId',
			sortOrder : 'asc',
			fit:true,
			columns:[[
				{field:'sel',title:'',align:'center',checkbox:true},
				{field:'companyId',title:'单位编号',width:100,hidden:true}, 
				{field:'companyName',title:'单位名称',align:'center',width:300},				
				{field:'shortName',title:'单位简称',align:'center',width:300},				
				{field:'companyTypeName',title:'单位类别',align:'center',width:150},
				{field:'companyAddress',title:'单位地址',align:'center',width:300},
				{field:'companyPost',title:'单位邮编',align:'center',width:60},
				{field:'linkman',title:'联系人',align:'center',width:100},
				{field:'phone',title:'联系电话',align:'center',width:80},
				{field:'statusName',title:'状态',align:'center',width:80},
				{field:'remark',title:'备注',align:'center',width:100,hidden:true}
			]],				
			toolbar : [{
				text : '查询',
				iconCls : 'icon-search',
				handler : function() {
					query();
				}
			}, '-',  {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					add();
				}
			}, '-', {
				text : '修改',
				iconCls : 'icon-edit',
				handler : function() {
					edit();
				}
			}, '-', {
				text : '启用',
				iconCls : 'icon-enable',
				handler : function() {
					change('1');
				}
			} ,'-', {
				text : '停用',
				iconCls : 'icon-stop',
				handler : function() {
					change('0');
				}
			}],
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
		//以下初始化查询combox列表
		initCombox('searchForm');
	});
	function add() {
		var p = dj.dialog({
			title : '添加届次',
			href : '${pageContext.request.contextPath}/comp!compAE.do',
			width : 440,
			height : 400,
			buttons : [ {
				text : '添加',
				handler : function() {
					var f = p.find('form');
					f.form('submit', {
						url : '${pageContext.request.contextPath}/comp!add.do',
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
				initCombox('addForm');
			}
		});
	}
	
	function change(status){
		var rows = datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			dj.messagerConfirm('请确认', '您要启用当前所选项目？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].companyId);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/comp!change.do',
						data : {
							ids : ids.join(','),
							status:status
						},
						dataType : 'json',
						success : function(d) {
							datagrid.datagrid('reload');
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
			parent.dj.messagerAlert('提示', '请勾选要启用的记录！', 'error');
		}
	}
	
	function edit() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			var p = dj.dialog({
				title : '编辑届次',
				href : '${pageContext.request.contextPath}/comp!compAE.do',
				width : 450,
				height : 400,
				buttons : [ {
					text : '编辑',
					handler : function() {
						var f = p.find('form');
						f.form('submit', {
							url : '${pageContext.request.contextPath}/comp!edit.do',
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
					initCombox('addForm');
					f.form('load', {
						companyId : rows[0].companyId,
						companyName : rows[0].companyName,
						shortName : rows[0].shortName,
						companyPost : rows[0].companyPost,
						companyType : rows[0].companyType,
						companyAddress : rows[0].companyAddress,
						phone : rows[0].phone,
						linkman : rows[0].linkman,
						remark : rows[0].remark,
						status : rows[0].status
					});
				}
			});
		} else if (rows.length > 1) {
			parent.dj.messagerAlert('提示', '同一时间只能编辑一条记录！', 'error');
		} else {
			parent.dj.messagerAlert('提示', '请选择要编辑的记录！', 'error');
		}
	}
	
	function query() {
		$('#searchForm').form('clear');
		$('#win_query').window('open');
	}
	
	function _search() {	//此处方法不能为search()
		datagrid.datagrid('load', dj.serializeObject($('#searchForm')));
		$('#win_query').window('close');
	}
	
	function cleanSearch() {
		//gridlist.datagrid('load', {});
		$("input,textarea,select").val("");
	}

</script>
</head>

<body class="easyui-layout">
	<!-- 查询窗口-->
	<div id="win_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:500px;height:280px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="searchForm">
				<table id=tableForm>				
					<tr height="30">
						<td nowrap>单位名称：</td>
						<td align="left"> <input type="text" name="companyName" style="width:300px;padding:2px"></td>
					</tr>
					<tr height="30">
						<td nowrap>单位类别：</td>
						<td align="left"><input name="companyType" style="width:306px;padding:2px" /></td>
					</tr>
					<tr height="30">
						<td nowrap>联系人：</td>
						<td align="left"> <input type="text" name="linkman" style="width:300px;padding:2px"></td>
					</tr>
					<tr height="30">
						<td nowrap>状态：</td>
						<td align="left"><input id="status" name="status" style="width:148px;padding:2px" /></td>
					</tr>
				</table>
				</form>
				</center>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;height:40px">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)" onclick="_search()">查询</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)" onclick="cleanSearch()">重置</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#win_query').window('close');">取消</a>
			</div>
		</div>
	</div>
	<!-- Datagrid表格 -->
	<div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="listgrid"></table>
	</div>
	<!-- 菜单 -->
	<div id="menu" class="easyui-menu" style="width:120px;display: none;">
		<div onclick="query();" data-options="iconCls:'icon-search'">查询</div>
		<div onclick="add();" data-options="iconCls:'icon-add'">增加</div>
		<div onclick="edit();" data-options="iconCls:'icon-edit'">编辑</div>
		<div onclick="change('1');" data-options="iconCls:'icon-enable'">启用</div>
		<div onclick="change('0');" data-options="iconCls:'icon-stop'">停用</div>
	</div>
</body>
</html>