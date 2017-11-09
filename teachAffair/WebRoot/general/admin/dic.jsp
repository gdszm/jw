<%@ page language="java"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	var datagrid;
	$(function(){
		
		//初始化基础类型
		$('#cbasetypeId').combotree({
			lines : true,
			url : '${pageContext.request.contextPath}/dic!doNotNeedSession_tree.do',
		});
		datagrid = $('#dicDatagrid').datagrid({
			url:'${pageContext.request.contextPath}/dic!datagrid.do',
        	pagination:true,
        	fit:true,
        	fitColumns :true,
			nowrap:true, 
			border:false,
			sortName:'ctype',
			sortOrder:'desc',
			checkOnSelect : true,
			selectOnCheck : true,
			columns:[[   
	        	{field:'cid',title:'ID',width:100,align:'center',checkbox : true},   
        		{field:'ckey',title:'名称',width:100,align:'center'},
        		{field:'cvalue',title:'值',width:100,align:'center'},
        		{field:'ctype',title:'类型',width:100,align:'center'},
        		{field:'ctypeName',title:'类型名称',width:100,align:'center'},
        		//{field:'cbasetype',title:'基础类型',width:100,align:'center'},
        		{field:'cbasetypeName',title:' 基础类型',width:100,align:'center'},
				{field:'cstatus',title:'状态',width:100,align:'center',
       	 			formatter : function(value, rowData, rowIndex) {
						if (value==0) {
							return "启用";
						}else{
							return "停用";
						}
					}
				}    
    		]],   
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
			},  '-', {
				text : '启用',
				iconCls : 'icon-enable',
				handler : function() {
					enable();
				}
			}, '-', {
				text : '停用',
				iconCls : 'icon-stop',
				handler : function() {
					disable();
				}
			} ,'-', {
				text : '取消选中',
				iconCls : 'icon-undo',
				handler : function() {
					datagrid.datagrid('clearSelections');
					datagrid.datagrid('unselectAll');
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
			},
			pageSize :20 ,
			pageList : [20, 50, 100 ]
		});
	});
	
	function append() {
		var p = parent.dj.dialog({
			title : '添加字典',
			href : '${pageContext.request.contextPath}/dic!dicAdd.do',
			width : 440,
			height : 230,
			buttons : [ {
				text : '添加',
				handler : function() {
					var f = p.find('form');
					f.form('submit', {
						url : '${pageContext.request.contextPath}/dic!add.do',
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
				var pid = f.find('input[name=cbasetype]');
				var cbasetypeName=f.find('input[name=cbasetypeName]');
				var ptree = pid.combotree({
					lines : true,
					url : '${pageContext.request.contextPath}/dic!doNotNeedSession_tree.do',
					onClick: function(node){
						//给表单隐藏的基础类型名称赋值
						cbasetypeName.val(node.text);
					}
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
						url : '${pageContext.request.contextPath}/dic!delete.do',
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
	
	function edit() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			var p = parent.dj.dialog({
				title : '编辑字典',
				href : '${pageContext.request.contextPath}/dic!dicEdit.do?cid='+rows[0].cid,
				width : 450,
				height : 230,
				buttons : [ {
					text : '编辑',
					handler : function() {
						var f = p.find('form');
						f.form('submit', {
							url : '${pageContext.request.contextPath}/dic!edit.do',
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
					var pid = f.find('input[name=cbasetype]');
					var cbasetypeName=f.find('input[name=cbasetypeName]');
					var ptree = pid.combotree({
						lines : true,
						url : '${pageContext.request.contextPath}/dic!doNotNeedSession_treeRecursive.do',
						onClick: function(node){
							//给表单隐藏的基础类型名称赋值
							cbasetypeName.val(node.text);
						}
					});
					f.find('input[name=cstatus]').combobox({    
					    url:'${pageContext.request.contextPath}/dic!combox.do?ctype=CSTATUS',    
					    valueField:'cvalue', 
					    panelHeight:'100',
					    textField:'ckey'
					}); 
					//initCombobox('editForm','cstatus','${pageContext.request.contextPath}/dic!combox.do?ctype=STATUS');
					//ptree.combotree('expandAll');
					//ptree.combotree('setValue', rows[0].cbasetype);
					//ptree.combotree('select', rows[0].cbasetype);
				}
			});
		} else if (rows.length > 1) {
			parent.dj.messagerAlert('提示', '同一时间只能编辑一条记录！', 'error');
		} else {
			parent.dj.messagerAlert('提示', '请选择要编辑的记录！', 'error');
		}
	}
	
	function enable(){
		var rows = datagrid.datagrid('getSelections');
		var ids = [];
		if (rows.length > 0) {
			parent.dj.messagerConfirm('请确认', '您要启用当前所选项目？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].cid);
					}
					$.ajax({
						type:"POST",
						url:"${pageContext.request.contextPath}/dic!change.do",
						data : {
							ids : ids.join(','),
							cstatus : '0'
						},
						dataType:"json",
						success:function(t){
							if(t.success){
								$.messager.show({msg : t.msg,title : '成功'});
								datagrid.datagrid('reload');
							}else{
								$.messager.show({msg : t.msg,title : '失败'});
							}
						}
					});
				}
			});
		} else {
			parent.dj.messagerAlert('提示', '请勾选要删除的记录！', 'error');
		}
	}
	
	function disable(){
		var rows = datagrid.datagrid('getSelections');
		var ids = [];
		if (rows.length > 0) {
			parent.dj.messagerConfirm('请确认', '您要停用当前所选项目？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].cid);
					}
					$.ajax({
						type:"POST",
						url:"${pageContext.request.contextPath}/dic!change.do",
						data : {
							ids : ids.join(','),
							cstatus : '1'
						},
						dataType:"json",
						success:function(t){
							if(t.success){
								$.messager.show({msg : t.msg,title : '成功'});
								datagrid.datagrid('reload');
							}else{
								$.messager.show({msg : t.msg,title : '失败'});
							}
						}
					});
				}
			});
		} else {
			parent.dj.messagerAlert('提示', '请勾选要删除的记录！', 'error');
		}
	}
	
	function _search() {
		datagrid.datagrid('load', dj.serializeObject($('#searchForm')));
	}
	function cleanSearch() {
		datagrid.datagrid('load', {});
		$('#searchForm input').val('');
	}
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false,title:'过滤条件'" style="height: 55px;overflow: hidden;" align="left">
		<form id="searchForm">
			<table class="tableForm datagrid-toolbar" style="width: 100%;height: 100%;">
				<tr>
					<td>
					名称:<input name="ckey" style="width:150px;" />
					值:<input name="cvalue" style="width:150px;" />
					类型:<input name="ctype" style="width:150px;" />
					类型名称:<input name="ctypeName" style="width:150px;" />
					基础类型:<input id="cbasetypeId" name="cbasetype" style="width:150px;" />
					<a href="javascript:void(0);" class="easyui-linkbutton" onclick="_search();">搜索</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" onclick="cleanSearch();">取消</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="dicDatagrid"></table>
	</div>
	
	<div id="menu" class="easyui-menu" style="width:120px;display: none;">
		<div onclick="append();" data-options="iconCls:'icon-add'">增加</div>
		<div onclick="remove();" data-options="iconCls:'icon-remove'">删除</div>
		<div onclick="edit();" data-options="iconCls:'icon-edit'">编辑</div>
		<div onclick="enable();" data-options="iconCls:'icon-enable'">启用</div>
		<div onclick="disable();" data-options="iconCls:'icon-stop'">停用</div>
	</div>
</body>
</html>