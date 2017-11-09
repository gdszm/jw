<%@ page language="java"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	function initCombox(form){
		initCombobox(form,'status','${pageContext.request.contextPath}/dic!combox.do?ctype=STATUS');
		initCombobox(form,'period','${pageContext.request.contextPath}/dic!combox.do?ctype=HYPS');
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
			url:'${pageContext.request.contextPath}/seco!datagrid.do',
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
			idField:'secondaryId',
			sortName:'secondaryId',
			sortOrder : 'asc',
			fit:true,
			columns:[[
				{field:'sel',title:'选择项',align:'center',checkbox:true},
				{field:'secondayName',title:'届次名称',align:'center',width:200},
				{field:'year',title:'对应年份',align:'center',width:150},
				{field:'periodName',title:'提案所属',align:'center',width:60},
				{field:'ext',title:'编号前缀',align:'center',width:60},
				{field:'statusName',title:'启用状态',align:'center',width:100}
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
			} ,'-', {
				text : '届次委员设置',
				iconCls : 'icon-orange',
				handler : function() {
					setwy();
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
			href : '${pageContext.request.contextPath}/seco!secoAE.do',
			width : 440,
			height : 200,
			buttons : [ {
				text : '添加',
				handler : function() {
					var f = p.find('form');
					f.form('submit', {
						url : '${pageContext.request.contextPath}/seco!add.do',
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
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			var p = dj.dialog({
				title : '编辑届次',
				href : '${pageContext.request.contextPath}/seco!secoAE.do',
				width : 450,
				height : 200,
				buttons : [ {
					text : '编辑',
					handler : function() {
						var f = p.find('form');
						f.form('submit', {
							url : '${pageContext.request.contextPath}/seco!edit.do',
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
						secondaryId : rows[0].secondaryId,
						secondayName : rows[0].secondayName,
						year:rows[0].year,
						period:rows[0].period,
						ext:rows[0].ext,
						status:rows[0].status
					});
				}
			});
		} else if (rows.length > 1) {
			parent.dj.messagerAlert('提示', '同一时间只能编辑一条记录！', 'error');
		} else {
			parent.dj.messagerAlert('提示', '请选择要编辑的记录！', 'error');
		}
	}
	
	function change(status) {
		var rows = datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			dj.messagerConfirm('请确认', '您要启用当前所选项目？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].secondaryId);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/seco!change.do',
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
	function setwy() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			var p = dj.dialog({
				title : '届次委员设置',
				href : '${pageContext.request.contextPath}/seco!secoSetWy.do?secoid='+rows[0].secondaryId,
				maximized:true,
				maximizable:true,
				width : 800,
				height : 500,
				iconCls:'icon-save',
				buttons : [ {
					text : '保存',
					handler : function() {
						var f = p.find('form');
						
						var leftopts=$("#leftSelect option");
						var rightopts=$("#rightSelect option");
						var leftdata=[];
						var rightdata=[];
						
						for(var i = 0; i<leftopts.length ;i++){
							leftdata.push(leftopts[i].value);
						}
						for(var i = 0; i<rightopts.length ;i++){
							rightdata.push(rightopts[i].value);
						}
						$("#leftdata").val(leftdata.join(','));
						$("#rightdata").val(rightdata.join(','));
						f.form('submit', {
							url : '${pageContext.request.contextPath}/seco!setSave.do',
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
					f.form('load', {
						secondaryId : rows[0].secondaryId,
						secondayName : rows[0].secondayName
					});
				}
			});
		} else if (rows.length > 1) {
			parent.dj.messagerAlert('提示', '同一时间只能设置一条记录！', 'error');
		} else {
			parent.dj.messagerAlert('提示', '请选择要设置的记录！', 'error');
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
					届次名称：<input name="secondayName" style="width:150px;" />
					对应年份：<input name="year" style="width:80px;padding:2px" />
					启用状态：<input name="status" style="width:80px;padding:2px" />
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