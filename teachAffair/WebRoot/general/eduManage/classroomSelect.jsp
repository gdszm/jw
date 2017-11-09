<%@page import="com.tlzn.util.base.Constants"%>
<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<script type="text/javascript">
function initClassroom() {
	$('#classroomgrid').datagrid({
		url:'${pageContext.request.contextPath}/classroom!datagrid.do',
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
			{field:'name',title:'教室名称',halign:'center',align:'center',width:150,sortable:true},
		]],
		columns:[[
			{field:'typeName',title:'教室类型',align:'center',width:100,sortable:true},
			{field:'buildingName',title:'所在教学楼',align:'center',width:120,sortable:true},
			{field:'floor',title:'所在楼层',align:'center',width:60,sortable:true},
			{field:'houseNo',title:'门牌号',align:'center',width:50,sortable:true},
			{field:'isCanUseName',title:'是否可用',align:'center',width:60,sortable:true},
			{field:'seatingNum',title:'座位数',align:'center',width:50,sortable:true},
			{field:'examSeatingNum',title:'考试座位数',align:'center',width:80,sortable:true}				
		]]
	});
}

//教室搜索
function classroom_search() {
	$('#classroomgrid').datagrid('load', dj.serializeObject($('#classroom_queryForm')));
}
//教室搜索取消
function classroom_cleanSearch() {
	$('#classroomgrid').datagrid('load', {});
	$('#classroom_queryForm input[name="name"]').val('');
	$('#classroom_queryForm input[name="houseNo"]').val('');
// 	$('#typeId').combobox('setValue', '');
}
</script>
<%--弹出选择教室窗口--%>
<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
	<center>
	<table id="tar_queryTable">			
		<tr height="25">
			<td colspan="4">
			<form id="classroom_queryForm">
			教室名称：<input type="text"  name="name" style="width:80px;padding:2px"/>
<!-- 			教室类型：<input id="typeId" name="typeId" style="width:70px;padding:2px"/> -->
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			门牌号：<input type="text" name="houseNo" style="width:70px;padding:2px"/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="javascript:void(0);" class="easyui-linkbutton" onclick="classroom_search();">搜索</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" onclick="classroom_cleanSearch();">取消</a>
				</form>
			</td>
		</tr>
		<tr height="200" >
			<td colspan="4" nowrap> <table id="classroomgrid"></table></td>
		</tr>
	</table>
	
	</center>
</div>
