<%@page import="com.tlzn.util.base.Constants"%>
<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<script type="text/javascript">
function initTeacher() {
	initCombobox('teacher_queryForm','sex','${pageContext.request.contextPath}/dic!combox.do?ctype=SEX');
	initCombobox('teacher_queryForm','rank','${pageContext.request.contextPath}/dic!combox.do?ctype=rank');
	initCombobox('teacher_queryForm','education','${pageContext.request.contextPath}/dic!combox.do?ctype=education');
	$('#teacherGrid').datagrid({
		url:'${pageContext.request.contextPath}/teacher!datagrid.do',
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
				{field:'id',title:'ID',width:100,align:'center',checkbox : true},
				{field:'idV',title:'教师编号',width:100,align:'center',
					formatter: function(value,row,index){
						return row.id;
					}
				},
				{field:'name',title:'教师姓名',width:100,align:'center'},
				{field:'sexName',title:'性别',width:80,align:'center'},
				]],
		columns:[[
			{field:'rankName',title:'教师职称',width:100,align:'center'},
			{field:'hasTeacherCertName',title:'有无教师资格证',width:100,align:'center'},
			{field:'educationName',title:'学历',width:100,align:'center'},
			{field:'gradFrom',title:'毕业院校',width:150,align:'center'},
			{field:'majorName',title:'专业名称',width:200,align:'center'},
			{field:'degreeName',title:'学位',width:100,align:'center'}
		]]
	});
}

//学生搜索
function stu_search() {
	$('#teacherGrid').datagrid('load', dj.serializeObject($('#teacher_queryForm')));
}
//学生搜索取消
function stu_cleanSearch() {
	$('#teacherGrid').datagrid('load', {});
	$('#teacher_queryForm input').val('');
	$('#sexId').combobox('setValue', '');
	$('#rankId').combobox('setValue', '');
	$('#educationId').combobox('setValue', '');
}
</script>
<%--弹出选择教师窗口--%>
<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
	<center>
	<table id="tar_queryTable">			
		<tr height="25">
			<td colspan="4">
				<form id="teacher_queryForm">
	<!-- 			<input id="classIdId"  type="hidden" name="classId" value="${classId}"/> -->
				教师编号：<input  id="idId" type="text"  name="id" style="width:50px;padding:2px"/>
				教师姓名：<input type="text"  name="name" style="width:50px;padding:2px"/>
				性别：<input id="sexId" name="sex" style="width:100px;padding:2px"/>
				教师职称：<input id="rankId" name="rank" style="width:100px;padding:2px"/>
				<br/>学历：<input id="educationId" name="education" style="width:100px;padding:2px"/>
					<a href="javascript:void(0);" class="easyui-linkbutton" onclick="stu_search();">搜索</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" onclick="stu_cleanSearch();">取消</a>
				</form>
			</td>
		
		</tr>
		<tr height="230" >
			<td colspan="4" nowrap> <table id="teacherGrid"></table></td>
		</tr>
	</table>
	
	</center>
</div>
