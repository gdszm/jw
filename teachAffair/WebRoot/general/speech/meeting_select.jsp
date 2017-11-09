<%@page import="com.tlzn.util.base.Constants"%>
<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<script type="text/javascript">
function initMeeing() {
	initCombobox('meet_queryForm','meetType','${pageContext.request.contextPath}/dic!combox.do?ctype=<%=Constants.MEETING_TYPE%>');
	$('#meetinggrid').datagrid({
		url:'${pageContext.request.contextPath}/meeting!getCurSecMeeting.do',
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
		idField:'meetId',
		fit:true,
		frozenColumns:[[
			{field:'meetId',width:10,sortable:false,checkbox:true}, 
			{field:'meetTypeName',title:'会议类型',align:'center',width:100,sortable:true},
			{field:'meetName',title:'会议名称',halign:'center',align:'left',width:200,sortable:true}
		]],
		columns:[[
			{field:'shortName',title:'会议简称',align:'center',width:120,sortable:true},				
			{field:'beginTime',title:'会议时间',align:'center',width:160,sortable:true ,formatter : function(value,row){
					return row.beginTime+"至"+row.endTime;
			}}
		]]
	});
}

//会议搜索
function meeting_search() {
	$('#meetinggrid').datagrid('load', dj.serializeObject($('#meet_queryForm')));
}
//会议搜索取消
function meeting_cleanSearch() {
	$('#meetinggrid').datagrid('load', {});
	$('#meet_queryForm input[name="meetName"]').val('');
	$('#meet_queryForm input[name="shortName"]').val('');
	$('#meetTypeId').combobox('setValue', 0);
}
</script>
<%--弹出选择会议窗口--%>
<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
	<center>
	<table id="tar_queryTable">			
		<tr height="25">
			
			<td colspan="4">
			<form id="meet_queryForm">
			会议名称：<input type="text"  name="meetName" style="width:80px;padding:2px"/>
			会议简称：<input type="text"  name="shortName" style="width:70px;padding:2px"/>
			会议类型：<input id="meetTypeId" name="meetType" style="width:70px;padding:2px"/>
				<a href="javascript:void(0);" class="easyui-linkbutton" onclick="meeting_search();">搜索</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" onclick="meeting_cleanSearch();">取消</a>
				</form>
			</td>
		
		</tr>
		<tr height="230" >
			<td colspan="4" nowrap> <table id="meetinggrid"></table></td>
		</tr>
	</table>
	
	</center>
</div>
