<%@ page language="java"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	function initCombox(form){
		initCombobox(form,'typeid','${pageContext.request.contextPath}/dic!combox.do?ctype=RULESTYPE');
		initCombobox(form,'status','${pageContext.request.contextPath}/dic!combox.do?ctype=STATUS');
		var data=[];
		for(i=new Date().getFullYear()+3;i>=2013;i--){
			data.push({"year": i, "value": i});
		}
		$('#' + form + ' input[name=year]').combobox({    
		    data:data,
		    panelHeight:'100',
		    valueField:'year',    
		    textField:'value'
		});
	}
	
	var datagrid;
	$(document).ready(function() {
		datagrid=$('#listgrid');

		datagrid.datagrid({
			url:'${pageContext.request.contextPath}/rules!datagrid.do',
			width: 'auto',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			singleSelect:true,
			pagination:true,
			pageSize:20,
			pageList:[20,50,100],
			pageNumber:1,
			idField:'id',
			sortName:'id',
			sortOrder : 'asc',
			fit:true,
			columns:[[
				{field:'id',title:'选择项',align:'center',checkbox:true},
				{field:'typeName',title:'计分类型',align:'center',width:160},
				{field:'name',title:'计分名称',align:'center',width:220},
				{field:'score',title:'分值 ',align:'center',width:60},
				{field:'statusName',title:'状态',align:'center',width:100}
			]],	
			toolbar : [ {
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
			} ],
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
			title : '添加计分规则',
			href : '${pageContext.request.contextPath}/rules!rulesAdd.do',
			width : 400,
			height : 260,
			buttons : [ {
				text : '添加',
				handler : function() {
					var f = p.find('form');
					f.form('submit', {
						url : '${pageContext.request.contextPath}/rules!add.do',
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
	
	function edit() {
		var rows = datagrid.datagrid('getSelected');
		if (rows!= null) {
			var p = dj.dialog({
				title : '编辑计分规则',
				href : '${pageContext.request.contextPath}/rules!rulesEdit.do?id='+rows.id,
				width : 400,
				height : 260,
				buttons : [ {
					text : '编辑',
					handler : function() {
						var f = p.find('form');
						f.form('submit', {
							url : '${pageContext.request.contextPath}/rules!edit.do',
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
					initCombox('editForm');
				}
			});
		} else {
			parent.dj.messagerAlert('提示', '请选择要编辑的记录！', 'error');
		}
	}
	
	function change(status) {
		var rows = datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			dj.messagerConfirm('请确认', '您要启用/停用当前所选项目？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/rules!change.do',
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
	
	function _search() {	//此处方法不能为search()
		datagrid.datagrid('load', dj.serializeObject($('#searchForm')));
	}
	
	function cleanSearch() {
		gridlist.datagrid('load', {});
		$("input,textarea,select").val("");
	}
	 
</script>
</head>

<body class="easyui-layout">
	<!-- 查询栏 -->
	<div data-options="region:'north',border:false,title:'过滤条件'" style="height: 55px;overflow: hidden;" align="left">
		<form id="searchForm">
			<table class="tableForm datagrid-toolbar" style="width: 100%;height: 100%;">
				<tr>
					<td>
					计分名称：<input name="name" style="width:220px;" />
					状态：<input name="status" style="width:80px;padding:2px" />
					<a href="javascript:void(0);" class="easyui-linkbutton" onclick="_search();">搜索</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" onclick="cleanSearch();">取消</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<!-- Datagrid表格 -->
	<div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="listgrid"></table>
	</div>
	<!-- 菜单 -->
	<div id="menu" class="easyui-menu" style="width:120px;display: none;">
		<div onclick="add();" data-options="iconCls:'icon-add'">增加</div>
		<div onclick="edit();" data-options="iconCls:'icon-edit'">编辑</div>
		<div onclick="change('1');" data-options="iconCls:'icon-enable'">启用</div>
		<div onclick="change('0');" data-options="iconCls:'icon-stop'">停用</div>
	</div>
</body>
</html>