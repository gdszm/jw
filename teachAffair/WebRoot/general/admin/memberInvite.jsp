<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(document).ready(function() {
	//参会人员信息列表
	$('#membergrid').datagrid({
		url:'${pageContext.request.contextPath}/comm!getCurSecComm.do',
		width: 'auto',
		height:'auto',
		striped:true,
		nowrap:true,
		pagination:true,
		pageSize:10,
		pageList:[10,15,20],
		rownumbers:true,
		sortName: 'code',
		sortOrder: 'asc',
		idField:'code',
		fit:true,
		frozenColumns:[[
			{field:'code',title:'编号',checkbox:true,align:'center',width:40},
			{field:'name',title:'姓名',align:'left',width:100,sortable:false}
		]],
		columns:[[
			{field:'circleName',title:'界别',align:'center',width:100,sortable:false},
			{field:'committeeName',title:'专委会',align:'center',width:100,sortable:false},
			{field:'telephone',title:'联系电话',align:'left',width:120,sortable:false},
			{field:'email',title:'电子邮箱',align:'center',width:120,sortable:false}
		]]
	});
});
//查询
function member_search() {
	$('#membergrid').datagrid('load', dj.serializeObject($('#member_queryForm')));
}
//重置
function member_cleanSearch() {
	$('#membergrid').datagrid('load', {});
	$('#member_queryForm input').val('');
}
</script>
<div align="center" style="padding: 1px;overflow: hidden;">
	<center>
		<table id="member_queryTable">			
			<tr height="25">
				<td width="600">
				<form id="member_queryForm">
				委员：
				 <input type="text"  name="name" style="width:180px;padding:2px"/>
					<a href="javascript:void(0);" class="easyui-linkbutton" onclick="member_search();">搜索</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" onclick="member_cleanSearch();">取消</a>
					</form>
				</td>
			
			</tr>
			<tr >
				<td height="250"  width="100%"> <table id="membergrid"></table></td>
			</tr>
		</table>
	</center>
</div>			

