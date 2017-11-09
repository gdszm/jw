<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript">
var activity;
	$(document).ready(function() {
		var f = $('#win_dolog_query');
		//var agency = f.find('input[name=agency]');
		//var ptree = agency.combotree({
		//	lines : true,
		//	url : '${pageContext.request.contextPath}/dept!doNotNeedSession_treeRecursive.do',
		//	onLoadSuccess : function() {
		//		parent.$.messager.progress('close');
		//	}
		//});
		activity=$('#Activitygrid');
		activity.datagrid({
			url:'${pageContext.request.contextPath}/activitypeo!peoQueryDatagrid.do',
			width: 'auto',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			singleSelect:true,
			pagination:true,
			sortName:'aspecies',
			sortOrder:'desc',
			pageSize:20,
			pageList:[20,50,100],
			pageNumber:1,
			idField:'aid',
			fit:true,
			frozenColumns:[[
				{field:'name',title:'委员',align:'center',width:100,sortable:true,formatter: function(value,row,index){
					return '<a href="javascript:void(0)" onclick="commsee(\''+row.commCode+'\')">'+row.name+'</a>';
				}},		
				{field:'secondaryName',title:'所属届次',align:'center',width:200,sortable:true,formatter : function(value,row){
					return row.secondaryName+"("+row.year+"年度)";
				}},
				{field:'aspeciesName',title:'活动种类',align:'center',width:100,sortable:true},
				{field:'atheme',title:'活动主题',align:'center',width:400,sortable:true}
			]],
			columns:[[
				{field:'time',title:'时间',align:'center',width:300,sortable:true,formatter : function(value,row){
					if(row.timebeg&&row.timeend){return row.timebeg+"至"+row.timeend;}
				}},
				{field:'agency',title:'承办机构',align:'center',width:140,sortable:true},
				{field:'place',title:'地点',align:'center',width:150,sortable:true},
				{field:'astatusName',title:'出席情况',align:'center',width:150,sortable:true}
			]],	
			toolbar: '#tb'
		});

		$('#jcgrid').datagrid({
			width: '100%',
			height:'100%',
			url:'${pageContext.request.contextPath}/seco!datagridNoPage.do',
			fit:true,
			idField:'secondaryId',
			striped:true,
			frozenColumns : [[
				{field:'secondaryId',title:'选择',checkbox:true},
			]],
			columns:[[
				{field:'secondayName',title:'届次名称',align:'center',width:140,sortable:false,formatter: function(value, row) {
				   	var str=row.secondayName+"("+row.year+"年度)";
					return str;
				}},				
			]],
			onLoadSuccess:function(row){ //当表格成功加载时执行默认届次选定                
                var rowData = row.rows;
                $.each(rowData,function(idx, val){//遍历JSON  
                    if(val.status=='1'){  
                        $('#jcgrid').datagrid("selectRow", idx); 
                    } else {
                    	$('#jcgrid').datagrid("unselectRow", idx); 
                    }
                });
            }
		});
		
	});

	function view() {
		var rows = activity.datagrid('getSelected');
		if (rows) {
			var aid=rows.aid;
			var p = dj.dialog({
				title : '查看活动',
				href : '${pageContext.request.contextPath}/activity!actView.do?aid='+aid,
				width : 600,
				height : 450,
				maximized : true,
				buttons : [{
					text : '关闭',
					iconCls:'icon-cancel',
					handler : function() {
						p.dialog('close');
					},
				}],
				onLoad : function() {
					$('#commcodegrid').datagrid({
						url:'${pageContext.request.contextPath}/activitypeo!peoQuery.do?aid='+aid,
						width:'auto',
						height:'auto',
						striped:true,
						nowrap:true,
						rownumbers:true,
						fitColumns:true,
						pagination:true,
						pageSize:20,
						pageList:[20,50,100],
						fit:true,
						columns:[[
							{field:'name',title:'委员',align:'center',width:100,sortable:false,formatter: function(value,row,index){
								return '<a href="javascript:void(0)" onclick="commsee(\''+row.commCode+'\')">'+row.name+'</a>';
						    }},		
							{field:'circleName',title:'界别',align:'center',width:100,sortable:false},
							{field:'committeeName',title:'所属专委会',align:'center',width:120,sortable:false},
							{field:'telephone',title:'联系电话',align:'left',width:120,sortable:false},
							{field:'email',title:'电子邮箱',align:'center',width:120,sortable:false},
							{field:'companyName',title:'工作单位',align:'center',width:200,sortable:false},
							{field:'job',title:'职务',align:'center',width:150,sortable:false},
							{field:'astatusName',title:'出席情况',align:'center',width:120,sortable:false,}
						]],
					});
				}
			});
		} else {
			dj.messagerAlert('提示', '请选择要查看的记录！', 'error');
		}
	}
	
	function _search() {
		activity.datagrid('load', dj.serializeObject($('#dolog_queryForm')));
		//$('#dolog_queryForm').form('clear');
		 $('#win_dolog_query').window('close');
	}
	//重置
	function cleanSearch() {
		activity.datagrid('load', {});
		$('#jcgrid').datagrid('load', {});
		$('#dolog_queryForm input').val('');
		//$("#aspecies").combobox('setValue', '');
		//$("#status").combobox('setValue', '');
		
	}
	//委员基本信息查看
	function commsee(code){
		var dp = dj.dialog({
			title : '委员信息',
			href : '${pageContext.request.contextPath}/committee!view.do?code=' + code,
			width : 650,
			height : 500,
			maximized : true,
			buttons : [ {
				text : '关闭',
				iconCls:'icon-cancel',
				handler : function() {
					dp.dialog('close');
				}
			} ]
		});
	}
</script>
</head>

<body>
		<!-- 表格 -->
	<div class="easyui-layout" data-options="fit:true">						
		<div data-options="region:'center',border:false" style="padding:1px;background:#fff;border:1px solid #ccc;">
			<table id="Activitygrid" style="overflow: auto;"></table>
		</div>
	</div>
	<div id="columnmenu" class="easyui-menu" style="width:110px;"></div>

	<div id="tb">
		<div style="margin-bottom:1px">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="javascript:$('#win_dolog_query').window('open');">查询</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-view" plain="true" onclick="view()">查看详细</a>
		</div>
	</div>
	<div id="win_dolog_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:630px;height:360px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<form id="dolog_queryForm" enctype=multipart/form-data method="post">
					<table id="dolog_queryTable" >
						<tr height="25">
							<td nowrap>参加人：</td>
							<td> <input type="text" name="name" class="easyui-validatebox" style="width:275px;padding:2px;"/></td>
							<td rowspan="6" width="200">
								<table id="jcgrid"></table>
							</td>
						</tr>
						<tr>
							<td nowrap>活动主题：</td>
							<td>  <input type="text" name="atheme" class="easyui-validatebox" style="width:275px;padding:2px;"/></td>
						</tr>
						<tr height="25">
							<td nowrap>活动地点：</td>
							<td>  <input type="text" name="place" class="easyui-validatebox" style="width:275px;padding:2px;"/></td>
						</tr>
						<tr height="25">
							<td nowrap>承办机构：</td>
							<td>  <input type="text" name="agency" class="easyui-validatebox" style="width:275px;padding:2px;"/></td>
						</tr>
						<tr height="25">
							<td nowrap>活动日期：</td>
							<td align="left"> <input class="easyui-datebox" name="timebeg"   style="width:134px"/>--<input class="easyui-datebox" name="timeend"  style="width:134px"/></td>
						</tr>
					</table>
				</form>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;height:40px">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)" onclick="_search()">查询</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)" onclick="cleanSearch()">重置</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#win_dolog_query').window('close');">取消</a>
			</div>
		</div>
	</div>
</body>
</html>