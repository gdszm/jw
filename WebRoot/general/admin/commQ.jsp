<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	
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
			sortName : 'code',
			sortOrder : 'asc',
			fit:true,
			columns:[[
				{field:'edit',title:'操作',align:'center',checkbox:true},
				{field:'name',title:'团体/专委会',align:'center',width:200},
				{field:'groupName',title:'分组',align:'center',width:100},
				/*{field:'sexName',title:'性别',align:'center',width:40},
				{field:'nation',title:'民族',align:'center',width:60},
				{field:'birthDate',title:'出生年月',align:'center',width:100},
				{field:'partyName',title:'党派',align:'center',width:150},
				{field:'circleName',title:'界别',align:'center',width:100},*/
				{field:'email',title:'邮箱',align:'center',width:100},
				{field:'telephone',title:'手机号',align:'center',width:100},
				{field:'comparyAddress',title:'通讯地址',align:'center',width:200},
				{field:'comparyPost',title:'邮编',align:'center',width:100},
				{field:'statusName',title:'状态',align:'center',width:80},
				{field:'secondaryName',title:'有效届次',align:'center',width:400}
			]],				
			toolbar : [{
				text : '查询',
				iconCls : 'icon-search',
				handler : function() {
					query();
				}
			}, '-', {
				text : '查看',
				iconCls : 'icon-view',
				handler : function() {
					view();
				}
			}, '-', {
				text : '报表',
				iconCls : 'icon-export',
				handler : function() {
					report();
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
		initCombobox('searchForm','groupCode','${pageContext.request.contextPath}/dic!combox.do?ctype=GROUP');
		initCombobox('searchForm','status','${pageContext.request.contextPath}/dic!combox.do?ctype=STATUS');
		$('#searchForm input[name=secondaryCode]').combobox({    
		    url:'${pageContext.request.contextPath}/seco!combobox.do',    
		    valueField:'secondaryId',    
		    textField:'secondayName',
		    multiple:true
		}); 
	});
	/////////////////////////////
	function view(){
		var row = datagrid.datagrid('getSelected');
		if (row) {
			var p = dj.dialog({
				title : '团体/专委会信息',
				href : '${pageContext.request.contextPath}/comm!view.do?code=' + row.code,
				width : 650,
				height : 500,
				buttons : [ {
					text : '关闭',
					iconCls:'icon-cancel',
					handler : function() {
						datagrid.datagrid('reload');
						p.dialog('close');
					}
				} ]
			});
		} else {
			$.messager.alert('提示', '请勾选要查看的记录！', 'error');
		}
	}

	function report(){
		var totalRowNum = datagrid.datagrid('getPager').data("pagination").options.total;
		if(totalRowNum>0){
			$.ajax({
				url : '${pageContext.request.contextPath}/comm!report.do',
				success : function(d) {
					var json = $.parseJSON(d);
					if (json.success) {
						window.open("${pageContext.request.contextPath}/" + json.obj);
					}
				}
			});
		}else{
			$.messager.alert('提示', '当前无记录，无需导出数据！', 'info');
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
		datagrid.datagrid('load', {});
		$("input,textarea,select").val("");
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
						<td nowrap>团体/专委会：</td>
						<td align="left" colspan="3"><input type="text" name="name" style="width:356px;padding:2px" /></td>
					</tr>
					<tr height="30">
						<td nowrap>分组：</td>
						<td align="left" colspan="3"><input name="groupCode"  data-options="required:true,editable:false,validType:'sel'" style="width:356px;padding:2px" /></td>
					</tr>
					<tr height="30">
						<td nowrap>状态：</td>
						<td align="left"><input id="status" name="status" style="width:148px;padding:2px" /></td>
						<td nowrap>有效届次：</td>
						<td align="left"><input name="secondaryCode" multiple="true" style="width:148px;padding:2px" /></td>
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
		<div onclick="view();" data-options="iconCls:'icon-view'">查看</div>
		<div onclick="report();" data-options="iconCls:'icon-export'">报表</div>
	</div>
</body>
</html>