<%@page import="com.tlzn.util.base.Constants"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	function initCombox(form){
		initCombobox(form,'typeId','${pageContext.request.contextPath}/dic!combox.do?ctype=classroomStatus');
		initCombobox(form,'status','${pageContext.request.contextPath}/dic!combox.do?ctype=classroomStatus');
		initCombobox(form,'occupyType','${pageContext.request.contextPath}/dic!combox.do?ctype=classroomOccupyType');
		var f = $('#win_classroom_query');
		var pid = f.find('input[name=deptNo]');
		var ptree = pid.combotree({
			lines : true,
			url : '${pageContext.request.contextPath}/dept!doNotNeedSession_tree.do'
		});
	}
	var p;
	var datagrid;
	$(function(){
		initCombox("classroom_queryForm");
		datagrid = $('#classroomDatagrid').datagrid({
			url:'${pageContext.request.contextPath}/classroom!datagrid.do',
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
				{field:'name',title:'教室名称',width:250,align:'center'}
				]],
			columns:[[
				{field:'deptName',title:'所在系',width:250,align:'center'},
				{field:'typeName',title:'教室类型',width:100,align:'center'},
				{field:'seatingNum',title:'座位数',width:100,align:'center'},
				{field:'statusName',title:'教室状态',width:100,align:'center'},
				{field:'occupyTypeName',title:'占用类型',width:100,align:'center'},
				{field:'buildingName',title:'所在教学楼',width:100,align:'center'},
				{field:'floor',title:'所在楼层',width:100,align:'center'},
				{field:'houseNo',title:'门牌号',width:100,align:'center'}
    		]],
			toolbar : [
			{
				text : '查询',
				iconCls : 'icon-search',
				handler : function() {
					$('#win_classroom_query').window('open');
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
				href : '${pageContext.request.contextPath}/classroom!get.do?id='+rows[0].id,
				maximized:true,
				width : 300,
				height : 200,
				onLoad : function() {
					initCombox('classroomViewForm');
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
			href : '${pageContext.request.contextPath}/classroom!classroomAdd.do',
			maximized:true,
			iconCls:'icon-save',
			buttons : [ {
					text : ' 提交 ',
					iconCls:'icon-ok',
					handler : function() {
							var f = p.find('form');
							f.form('submit', {
								url : '${pageContext.request.contextPath}/classroom!add.do',
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
				}
		});
	}
	//修改 
	function edit() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			var p = dj.dialog({
				title : ' 班级修改 ',
				href : '${pageContext.request.contextPath}/classroom!classroomEdit.do?id='+rows[0].id,
				maximized:true,
				width : 800,
				height : 600,
				buttons : [ {
					text : ' 提交修改 ',
					iconCls : 'icon-ok',
					handler : function() {
						var f = p.find('form');
						f.form('submit', {
							url : '${pageContext.request.contextPath}/classroom!edit.do',
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
						url : '${pageContext.request.contextPath}/classroom!delete.do',
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
	//查询
	function _search() {
		datagrid.datagrid('load', dj.serializeObject($('#classroom_queryForm')));
		 $('#win_classroom_query').window('close');
	}
	
	function cleanSearch() {
		datagrid.datagrid('load', {});
		$('#classroom_queryForm input').val('');
		$('#typeId').combobox('setValue', '');
	}
</script>
</head>
<body class="easyui-layout" data-options="fit:true">
	<!-- 表格 -->
	<div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="classroomDatagrid"></table>
	</div>
	<div id="win_classroom_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:500px;height:274px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="classroom_queryForm">
				<table id="poll_queryTable">
					<tr height="25">
						<td nowrap>教室名称：</td>
						<td colspan="3"><input type="text" class="easyui-validatebox"  name="name" style="width:300px;"></td>
					</tr>	
					<tr height="25">
						<td nowrap>所在系：</td>
						<td colspan="3">
							<input type="text" id="depidId" name="deptNo" class="easyui-validatebox" style="width:300px;padding:2px;"/>
						</td>
					</tr>
<!-- 					<tr height="25"> -->
<!-- 						<td nowrap>教室类型：</td> -->
<!-- 						<td colspan="3"><input id="typeIdId" type="text" class="easyui-validatebox"  name="typeId" style="width:300px;"></td> -->
<!-- 					</tr>			 -->
					<tr height="25">
						<td nowrap>教室状态：</td>
						<td colspan="3"><input id="statusId" type="text" class="easyui-validatebox"  name="status" style="width:300px;"></td>
					</tr>	
					<tr height="25">
						<td nowrap>占用类型：</td>
						<td colspan="3"><input id="occupyTypeId" type="text" class="easyui-validatebox"  name="occupyType" style="width:300px;"></td>
					</tr>
				</table>
				</form>
				</center>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;height:40px">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)" onclick="_search()">查询</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)" onclick="cleanSearch()">重置</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#win_classroom_query').window('close');">取消</a>
			</div>
		</div>
	</div>
</body>
</html>