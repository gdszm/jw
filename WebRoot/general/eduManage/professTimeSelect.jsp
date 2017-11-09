<%@page import="com.tlzn.util.base.Constants"%>
<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<script type="text/javascript">
function initTime() {
		$('#professTime_queryForm input[name=weeks]').combobox({
			url : '${pageContext.request.contextPath}/dic!combox.do?ctype=weeks',
			valueField:'cvalue',
			panelHeight:'100',
	        textField:'ckey',
	        multiple:true,
	        required:false
		});
		$('#professTime_queryForm input[name=week]').combobox({
			url : '${pageContext.request.contextPath}/dic!combox.do?ctype=week',
			valueField:'cvalue',
			panelHeight:'100',
	        textField:'ckey',
	        multiple:true,
	        required:false
		});
		$('#professTime_queryForm input[name=section]').combobox({
			url : '${pageContext.request.contextPath}/dic!combox.do?ctype=section',
			valueField:'cvalue',
			panelHeight:'100',
	        textField:'ckey',
	        multiple:true,
	        required:false
		});
		
	$('#timeGrid').datagrid({
		url:'${pageContext.request.contextPath}/professTime!datagrid.do',
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
		columns:[[
			{field:'id',width:10,sortable:false,checkbox:true}, 
				{field:'weeks',title:'周次',width:400,align:'center'},
				{field:'week',title:'星期',width:150,align:'center'},
				{field:'section',title:'节次',width:200,align:'center'},
				{field:'crtTime',title:'创建时间',width:150,align:'center',
					formatter: function(value,row,index){
						if (value){
							return value.substring(0,19);
						} else {
							return value;
						}
					}
				},
				{field:'uptTime',title:'更新时间',width:150,align:'center',
					formatter: function(value,row,index){
						if (value){
							return value.substring(0,19);
						} else {
							return value;
						}
					}
				}
		]]
	});
}

//授课时间搜索
function professTime_search() {
	$('#timeGrid').datagrid('load', dj.serializeObject($('#professTime_queryForm')));
}
//授课时间搜索取消
function professTime_cleanSearch() {
	$('#timeGrid').datagrid('load', {});
	$('#professTime_queryForm input').val('');
}
</script>
<%--弹出选择授课时间窗口--%>
<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
	<center>
	<table id="tar_queryTable">			
		<tr height="25">
			<td colspan="4">
			<form id="professTime_queryForm">
			周次：<input id="weeksId" type="text" name="weeks" style="width:80px;padding:2px"/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			星期：<input id="weekId"  name="week"  type="text" style="width:70px;padding:2px"/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			节次：<input id="sectionId" name="section" type="text" style="width:70px;padding:2px"/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="javascript:void(0);" class="easyui-linkbutton" onclick="professTime_search();">搜索</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" onclick="professTime_cleanSearch();">取消</a>
			</form>
			</td>
		</tr>
		<tr height="200" >
			<td colspan="4" nowrap> <table id="timeGrid"></table></td>
		</tr>
	</table>
	
	</center>
</div>
