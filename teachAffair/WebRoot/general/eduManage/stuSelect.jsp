<%@page import="com.tlzn.util.base.Constants"%>
<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<script type="text/javascript">
function initStu() {
	initCombobox('stu_queryForm','dept','${pageContext.request.contextPath}/dic!combox.do?ctype=yxb');
	initCombobox('stu_queryForm','major','${pageContext.request.contextPath}/dic!combox.do?ctype=major');
	$('#stuGrid').datagrid({
		url:'${pageContext.request.contextPath}/stu!datagrid.do?classId=${classId}',
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
		idField:'stuNo',
		fit:true,
		frozenColumns:[[
				{field:'id',title:'ID',width:100,align:'center',checkbox : true},
				{field:'stuNo',title:'学号',width:100,align:'center'},
				{field:'name',title:'学生姓名',width:100,align:'center'},
				{field:'sex',title:'性别',width:50,align:'center'},		
				]],
		columns:[[
			{field:'dept',title:'院、系、部',width:100,align:'center'},
			{field:'major',title:'专业',width:100,align:'center'},
			{field:'inTime',title:'入学时间',width:100,align:'center'},
			{field:'enSour',title:'入学来源',width:100,align:'center'},
			{field:'priPro',title:'主要问题',width:200,align:'center'}
		]]
	});
}

//学生搜索
function stu_search() {
	$('#stuGrid').datagrid('load', dj.serializeObject($('#stu_queryForm')));
}
//学生搜索取消
function stu_cleanSearch() {
	$('#stuGrid').datagrid('load', {});
	$('#stu_queryForm input[name="stuNo"]').val('');
	$('#stu_queryForm input[name="name"]').val('');
	$('#deptId').combobox('setValue', '');
	$('#majorId').combobox('setValue', '');
}
</script>
<%--弹出选择会议窗口--%>
<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
	<center>
	<table id="tar_queryTable">			
		<tr height="25">
			<td colspan="4">
			<form id="stu_queryForm">
<!-- 			<input id="classIdId"  type="hidden" name="classId" value="${classId}"/> -->
			学号：<input  id="stuNoId" type="text"  name="stuNo" style="width:50px;padding:2px"/>
			学生姓名：<input type="text"  name="name" style="width:50px;padding:2px"/>
			院、系、部：<input id="deptId" name="dept" style="width:100px;padding:2px"/>
			专业：<input id="majorId" name="major" style="width:100px;padding:2px"/>
				<a href="javascript:void(0);" class="easyui-linkbutton" onclick="stu_search();">搜索</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" onclick="stu_cleanSearch();">取消</a>
				</form>
			</td>
		
		</tr>
		<tr height="230" >
			<td colspan="4" nowrap> <table id="stuGrid"></table></td>
		</tr>
	</table>
	
	</center>
</div>
