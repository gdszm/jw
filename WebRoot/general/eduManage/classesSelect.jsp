<%@page import="com.tlzn.util.base.Constants"%>
<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<script type="text/javascript">
function initClass() {
	$('#classgrid').datagrid({
		url:'${pageContext.request.contextPath}/classes!datagrid.do',
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
		idField:'id',
		fit:true,
		frozenColumns:[[
			{field:'id',width:10,sortable:false,checkbox:true}, 
			{field:'className',title:'班级名称',halign:'center',align:'center',width:200,sortable:true},
		]],
		columns:[[
			{field:'adminTeacherName',title:'班主任姓名',align:'center',width:120,sortable:true},
			{field:'adminStuName',title:'班长姓名',align:'center',width:120,sortable:true},
			{field:'secStuName',title:'团支书姓名',align:'center',width:120,sortable:true}				
		]]
	});
}

//班级搜索
function class_search() {
	$('#classgrid').datagrid('load', dj.serializeObject($('#class_queryForm')));
}
//班级搜索取消
function class_cleanSearch() {
	$('#classgrid').datagrid('load', {});
	$('#class_queryForm input[name="className"]').val('');
	$('#class_queryForm input[name="adminTeacherName"]').val('');
	$('#typeId').combobox('setValue', '');
}
</script>
<%--弹出选择班级窗口--%>
<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
	<center>
	<table id="tar_queryTable">			
		<tr height="25">
			<td colspan="4">
			<form id="class_queryForm">
			班级名称：<input type="text"  name="className" style="width:80px;padding:2px"/>
			班级类型：<input id="typeId" name="type" style="width:70px;padding:2px"/>
			班主任姓名：<input type="text"  name="adminTeacherName" style="width:70px;padding:2px"/>
				<a href="javascript:void(0);" class="easyui-linkbutton" onclick="class_search();">搜索</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" onclick="class_cleanSearch();">取消</a>
				</form>
			</td>
		
		</tr>
		<tr height="230" >
			<td colspan="4" nowrap> <table id="classgrid"></table></td>
		</tr>
	</table>
	
	</center>
</div>
