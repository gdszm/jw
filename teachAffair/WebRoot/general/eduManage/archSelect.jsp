<%@page import="com.tlzn.util.base.Constants"%>
<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<%@page import="com.tlzn.util.base.Constants"%>
<script type="text/javascript">
function initArch() {
	initCombobox('arch_queryForm','sex','${pageContext.request.contextPath}/dic!combox.do?ctype=<%=Constants.CODE_TYPE_ID_SEX%>');
	$('#archgrid').datagrid({
		url:'${pageContext.request.contextPath}/archives!datagrid.do?status=<%=Constants.CODE_TYPE_PUBSTATUS_YES %>&selectNotExist=1',
		width: 'auto',
		height:'auto',
		striped:true,
		nowrap:true,
		rownumbers:true,
		pagination:true,
		pageSize:10,
		pageList:[10,30,50],
		pageNumber:1,
		singleSelect:true,
		idField:'archNo',
		fit:true,
		frozenColumns:[[
			{field:'archNo',title:'ID',width:100,align:'center',checkbox : true},
				{field:'archNoV',title:'档案编号',width:80,align:'center',
					formatter: function(value,row,index){
						return row.archNo;
					}
			},
			{field:'name',title:'姓名',align:'center',width:100,sortable:true},
			{field:'sexName',title:'性别',halign:'center',align:'center',width:50,sortable:true}
		]],
		columns:[[
			{field:'urgentTel',title:'紧急情况下联系号码',align:'center',width:120,sortable:true},				
			{field:'gradSchool',title:'毕业院校',align:'center',width:160,sortable:true}
		]]
	});
}

//档案搜索
function arch_search() {
	$('#archgrid').datagrid('load', dj.serializeObject($('#arch_queryForm')));
}
//档案搜索取消
function arch_cleanSearch() {
	$('#archgrid').datagrid('load', {});
	$('#arch_queryForm input[name="archNo"]').val('');
	$('#arch_queryForm input[name="name"]').val('');
	$('#sexId').combobox('setValue', '');
}
</script>
<%--弹出选择档案窗口--%>
<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
	<center>
	<table id="tar_queryTable">			
		<tr height="25">
			
			<td colspan="4">
			<form id="arch_queryForm">
			档案编号：<input type="text"  name="archNo" style="width:70px;padding:2px"/>
			学生姓名：<input type="text"  name="name" style="width:80px;padding:2px"/>
			性别：<input id="sexId" name="sex" style="width:70px;padding:2px"/>
				<a href="javascript:void(0);" class="easyui-linkbutton" onclick="arch_search();">搜索</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" onclick="arch_cleanSearch();">取消</a>
				</form>
			</td>
		
		</tr>
		<tr height="230" >
			<td colspan="4" nowrap> <table id="archgrid"></table></td>
		</tr>
	</table>
	
	</center>
</div>
