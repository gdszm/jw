<%@page import="com.tlzn.util.base.Constants"%>
<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<script type="text/javascript">
function initCourse() {
	initCombox("course_searchForm");
	$('#coursegrid').datagrid({
		url:'${pageContext.request.contextPath}/course!datagrid.do',
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
			{field:'name',title:'课程名称',halign:'center',align:'center',width:150,sortable:true},
		]],
		columns:[[
			{field:'courseAttName',title:'课程属性',align:'center',width:150,sortable:true},
			{field:'credit',title:'课程学分',align:'center',width:80,sortable:true}				
		]]
	});
}

//班级搜索
function course_search() {
	$('#coursegrid').datagrid('load', dj.serializeObject($('#course_searchForm')));
}
//班级搜索取消
function class_cleanSearch() {
	$('#coursegrid').datagrid('load', {});
	$('#course_searchForm input[name="name"]').val('');
	$('#course_searchForm input[name="credit"]').val('');
	$('#courseAttId').combobox('setValue', '');
}
</script>
<%--弹出选择课程窗口--%>
<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
	<center>
	<table id="tar_queryTable">			
		<tr height="25">
			<td colspan="4">
			<form id="course_searchForm">
			课程名称：<input type="text"  name="name" style="width:80px;padding:2px"/>
			课程属性：<input id="courseAttId" name="courseAtt" style="width:70px;padding:2px"/>
			课程学分：<input type="text"  name="credit" style="width:70px;padding:2px"/>
				<a href="javascript:void(0);" class="easyui-linkbutton" onclick="course_search();">搜索</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" onclick="class_cleanSearch();">取消</a>
				</form>
			</td>
		
		</tr>
		<tr height="230" >
			<td colspan="4" nowrap> <table id="coursegrid"></table></td>
		</tr>
	</table>
	
	</center>
</div>
