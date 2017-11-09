<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<script type="text/javascript">

//班主任选择部分 开始//
var pTeacher;
function teacherSelect(){
	pTeacher=dj.dialog({
		title : '选择该班班组任',
		href : '${pageContext.request.contextPath}/teacher!teacherSelect.do',
		width : 700,
		height : 400,
		buttons : [ {
			text : '确定',
			iconCls:'icon-ok',
			handler : function() {
				saveTeacherAdd();
			}
		},{ 
			text: ' 取 消', 
			iconCls:'icon-cancel',
			handler: function() { 
				pTeacher.dialog('close'); 
			}
		}],
		onLoad : function() {
			initTeacher();
		}
	});
}
//班主任选择添加
function saveTeacherAdd(){
	var rows=$('#teacherGrid').datagrid('getSelected');
	  if(rows){
		  $('#teacherId').val(rows.id);
		  $('#teacherNameId').val(rows.name);
	  }else{
		  $('#teacherId').val('');
		  $('#teacherNameId').val('');
	  }
	  pTeacher.dialog('close');
}
//班组任选择部分 结束//
</script>
<form id="addForm"  method="post">
	<table class="tableborder" id="formTable">
		<tr height="40">
			<td nowrap class="tablelabel">教师编号：</td>
			<td align="left" class="tablecontent">
				<input id="teacherId" name="teacherId" type="text" value="${classes.teacherId}" class="easyui-validatebox" data-options="required:true,missingMessage:'请点击选择按钮选择一位教师后再提交！'" readonly="readonly" style="margin-left:2px" >
			</td>
		</tr>
		<tr height="40">
			<td nowrap class="tablelabel">教师姓名：</td>
			<td align="left" class="tablecontent">
				<input id="teacherNameId" type="text"  name="adminTeacherName" value="${classes.adminTeacherName}" style="margin-left:2px" readonly="readonly"/>
			</td>
		</tr>
		<tr height="40">
			<td align="left" class="tablecontent" colspan="2" >
	        	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="teacherSelect()" style="margin-left:150px;">选择</a>
	       		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="$('#teacherId').val('');$('#teacherNameId').val('');">取消</a>
			</td>
		</tr>
	</table>
</form>