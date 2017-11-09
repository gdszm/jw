<%@page import="com.tlzn.util.base.Constants"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	function initCombox(form){
// 		initCombobox(form,'type','${pageContext.request.contextPath}/dic!combox.do?ctype=classesType');
// 		initCombobox(form,'trainLevel','${pageContext.request.contextPath}/dic!combox.do?ctype=trainLevel');
		$('#professTime_queryForm input[name=weeks]').combobox({
			url : '${pageContext.request.contextPath}/dic!combox.do?ctype=weeks',
			valueField:'cvalue',
			panelHeight:'100',
	        textField:'ckey',
	        multiple:true,
	        required:false
		});
		$('#professTime_queryForm input[name=week]').combobox({
			url : '${pageContext.request.contextPath}/dic!combox.do?ctype=week',
			valueField:'cvalue',
			panelHeight:'100',
	        textField:'ckey',
	        multiple:true,
	        required:false
		});
		$('#professTime_queryForm input[name=section]').combobox({
			url : '${pageContext.request.contextPath}/dic!combox.do?ctype=section',
			valueField:'cvalue',
			panelHeight:'100',
	        textField:'ckey',
	        multiple:true,
	        required:false
		});
	}
	var p;
	var datagrid;
	$(function(){
		initCombox("searchForm"); 
		initCombox("professTimeDatagrid");
		initCombox("professTime_queryForm");
		datagrid = $('#professTimeDatagrid').datagrid({
			url:'${pageContext.request.contextPath}/professTime!datagrid.do',
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
			columns:[[
				{field:'id',title:'ID',width:100,align:'center',checkbox : true},
				{field:'weeks',title:'周次',width:400,align:'center'},
				{field:'week',title:'星期',width:150,align:'center'},
				{field:'section',title:'节次',width:200,align:'center'},
				{field:'crtTime',title:'创建时间',width:150,align:'center',
					formatter: function(value,row,index){
						if (value){
							return value.substring(0,19);
						} else {
							return value;
						}
					}
				},
				{field:'uptTime',title:'更新时间',width:150,align:'center',
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
					$('#win_professTime_query').window('open');
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
			}
// 			, '-',
// 			{
// 				text : '查看详细',
// 				iconCls : 'icon-info',
// 				handler : function() {
// 					view();
// 				}
// 			}
			]
		});
	});
	
	//查看
	function view() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			var p = dj.dialog({
				title : '班级详情',
				href : '${pageContext.request.contextPath}/professTime!get.do?id='+rows[0].id,
				maximized:true,
				width : 300,
				height : 200,
				onLoad : function() {
					initCombox('professTimeViewForm');
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
			title : '新增授课时间',
			href : '${pageContext.request.contextPath}/professTime!professTimeAdd.do',
			maximized:false,
			width : 600,
			height : 300,
			iconCls:'icon-save',
			buttons : [ {
					text : ' 提交 ',
					iconCls:'icon-ok',
					handler : function() {
							var f = p.find('form');
							f.form('submit', {
								url : '${pageContext.request.contextPath}/professTime!add.do',
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
				title : ' 授课时间信息修改 ',
				href : '${pageContext.request.contextPath}/professTime!professTimeEdit.do?id='+rows[0].id,
				maximized:false,
				width : 600,
				height : 300,
				buttons : [ {
					text : ' 提交修改 ',
					iconCls : 'icon-ok',
					handler : function() {
						var f = p.find('form');
						f.form('submit', {
							url : '${pageContext.request.contextPath}/professTime!edit.do',
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
			parent.dj.messagerConfirm('请确认', '您要删除当前所选授课时间吗？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/professTime!delete.do',
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
						url : '${pageContext.request.contextPath}/professTime!pub.do',
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
						url : '${pageContext.request.contextPath}/professTime!cancelPub.do',
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
		datagrid.datagrid('load', dj.serializeObject($('#professTime_queryForm')));
		 $('#win_professTime_query').window('close');
	}
	
	function cleanSearch() {
		datagrid.datagrid('load', {});
		$('#professTime_queryForm input').val('');
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
		<table id="professTimeDatagrid"></table>
	</div>
	<div id="win_professTime_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:500px;height:200px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="professTime_queryForm">
				<input type="hidden" name="filingFlg" id="filingFlgId"/>
				<table id="poll_queryTable">
					<tr height="25">
						<td nowrap>周次：</td>
						<td colspan="3"><input id="weeksId" type="text" name="weeks" style="width:300px;"></td>
					</tr>
					<tr height="25">
						<td nowrap>星期：</td>
						<td colspan="3"><input id="weekId"  name="week"  type="text" style="width:300px;"></td>
					</tr>			
					<tr height="25">
						<td nowrap>节次：</td>
						<td colspan="3"><input id="sectionId"  name="section" type="text" style="width:300px;"></td>
					</tr>	
<!-- 					<tr height="25"> -->
<!-- 						<td nowrap>创建时间 ：</td> -->
<!-- 						<td colspan="3">  -->
<!-- 							<input id="crtTimeStartId" type="text" class="easyui-datebox" name="crtTimeStart" data-options="required:false,showSeconds:false" style="width:142px;"></input>  -->
<!-- 		 	              	~  -->
<!-- 			              	<input id="crtTimeEndId" type="text" class="easyui-datebox" name="crtTimeEnd" data-options="required:false,showSeconds:false" style="width:142px;"></input>  -->
<!-- 						</td> -->
<!-- 					</tr> -->
<!-- 					<tr height="25"> -->
<!-- 						<td nowrap>更新时间 ：</td> -->
<!-- 						<td colspan="3">  -->
<!-- 							<input id="uptTimeStartId" type="text" class="easyui-datebox" name="uptTimeStart" data-options="required:false,showSeconds:false" style="width:142px;"></input>  -->
<!-- 		 	              	~  -->
<!-- 			              	<input id="uptTimeEndId" type="text" class="easyui-datebox" name="uptTimeEnd" data-options="required:false,showSeconds:false" style="width:142px;"></input>   -->
						
<!-- 						</td> -->
<!-- 					</tr> -->
				</table>
				</form>
				</center>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;height:40px">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)" onclick="_search()">查询</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)" onclick="cleanSearch()">重置</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#win_professTime_query').window('close');">取消</a>
			</div>
		</div>
	</div>
</body>
</html>