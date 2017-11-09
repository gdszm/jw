<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	function initCombox(form){
		//initCombobox(form,'sex','${pageContext.request.contextPath}/dic!combox.do?ctype=SEX');
	}
	
	var datagrid;	
	$(document).ready(function() {
		datagrid=$('#listgrid');
		
		datagrid.datagrid({
			url:'${pageContext.request.contextPath}/comm!datagrid.do',
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
			idField:'code',
			fit:true,
			columns:[[
				{field:'tatm',title:'案由',align:'left',width:420,sortable:true},
				{field:'tar',title:'第一提案人',align:'center',width:90,sortable:true},				
				{field:'tars',title:'提案人数',align:'center',width:65,sortable:true},
				{field:'talx',title:'提案类型',align:'center',width:75,sortable:true},
				{field:'kffy',title:'可否附议',align:'center',width:65,sortable:true},
				{field:'labz',title:'立案状态',align:'center',width:130,sortable:true}
				
			]],				
			toolbar : [{
				text : '查询',
				iconCls : 'icon-search',
				handler : function() {
					query();
				}
			}, '-',  {
				text : '新增附议',
				iconCls : 'icon-add',
				handler : function() {
					add();
				}
			}, '-', {
				text : '查看提案',
				iconCls : 'icon-view',
				handler : function() {
					view();
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
	/////////////////////////////
	function add() {
		var p = dj.dialog({
			title : '添加提案人信息',
			href : '${pageContext.request.contextPath}/comm!commAE.do',
			width : 500,
			height : 400,
			buttons : [ {
				text : '添加',
				handler : function() {
					var f = p.find('form');
					f.form('submit', {
						url : '${pageContext.request.contextPath}/comm!add.do',
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
			} ],
			onLoad : function() {
				initCombox('addForm');
			}
		});
	}
	
	function edit() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			var p = dj.dialog({
				title : '编辑提案人信息',
				href : '${pageContext.request.contextPath}/comm!commAE.do',
				width : 550,
				height : 400,
				buttons : [ {
					text : '编辑',
					handler : function() {
						var f = p.find('form');
						f.form('submit', {
							url : '${pageContext.request.contextPath}/comm!edit.do',
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
					var f = p.find('form');
						f.form('load', {
						code : rows[0].code,
						name : rows[0].name,
						pingyin : rows[0].pingyin,
						groupCode : rows[0].groupCode,
						sex : rows[0].sex.trim(),
						nation : rows[0].nation,
						partyCode : rows[0].partyCode,
						circleCode : rows[0].circleCode,
						eduCode : rows[0].eduCode,
						degreeCode : rows[0].degreeCode,
						vocation : rows[0].vocation,
						title : rows[0].title,
						email : rows[0].email,
						birthDate : rows[0].birthDate,
						telephone : rows[0].telephone,
						job : rows[0].job,
						companyName : rows[0].companyName,
						comparyAddress : rows[0].comparyAddress,
						comparyPhone : rows[0].comparyPhone,
						comparyPost : rows[0].comparyPost,
						familyAddress : rows[0].familyAddress,
						familyPhone : rows[0].familyPhone,
						familyPost : rows[0].familyPost,
						status : rows[0].status.trim(),
						secondaryCode : rows[0].secondaryCode.split(',')
					});
				}
			});
		} else if (rows.length > 1) {
			parent.dj.messagerAlert('提示', '同一时间只能编辑一条记录！', 'error');
		} else {
			parent.dj.messagerAlert('提示', '请选择要编辑的记录！', 'error');
		}
	}
	
	function view(){
		var rows = datagrid.datagrid('getSelections');
		var ids = [];
		if (rows.length > 0) {
			for ( var i = 0; i < rows.length; i++) {
				ids.push(rows[i].code);
			}
			$.ajax({
				url : '${pageContext.request.contextPath}/comm!view.do',
				data : {
					ids : ids.join(',')
				},
				dataType : 'json',
				success : function(d) {
					datagrid.datagrid('load');
					datagrid.datagrid('unselectAll');
					$.messager.alert('提示', d.msg, 'error');
				}
			});
		} else {
			$.messager.alert('提示', '请勾选要删除的记录！', 'error');
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
	
</script>
</head>

<body class="easyui-layout">
	<!-- 查询窗口-->
	<div id="win_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:550px;height:360px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="searchForm">
				<table id=tableForm>				
					<tr height="30">
						<td nowrap>提案编号：</td>
						<td align="left"><input name="name" style="width:356px;padding:2px" /></td>
					</tr>
					<tr height="30">
						<td nowrap>提案题目：</td>
						<td align="left"><input name="sex" style="width:356px;padding:2px" /></td>
					</tr>
					<tr height="30">
						<td nowrap>主提者编号：</td>
						<td align="left"><input name="partyCode" style="width:356px;padding:2px" /></td>
					</tr>
					<tr height="30">
						<td nowrap>主提者姓名：</td>
						<td align="left"><input  name="eduCode" style="width:148px;padding:2px" /></td>
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
		<div onclick="add();" data-options="iconCls:'icon-add'">新增附议</div>
		<div onclick="view();" data-options="iconCls:'icon-view'">查看</div>
	</div>
</body>
</html>