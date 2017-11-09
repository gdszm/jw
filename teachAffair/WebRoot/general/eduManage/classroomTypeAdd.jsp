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
		  $('#adminTeacherId').val(rows.id);
		  $('#adminTeacherNameId').val(rows.name);
	  }else{
		  $('#adminTeacherId').val('');
		  $('#adminTeacherNameId').val('');
	  }
	  pTeacher.dialog('close');
}
//班组任选择部分 结束//


//班长选择部分 开始//
var pAdminStu;
function adminStuSelect(){
	pAdminStu=dj.dialog({
		title : '选择该班班长',
		href : '${pageContext.request.contextPath}/stu!stuSelect.do?classId=${classroom.id}',
		width : 700,
		height : 350,
		buttons : [ {
			text : '确定',
			iconCls:'icon-ok',
			handler : function() {
				saveAdminStuAdd();
			}
		},{ 
			text: ' 取 消', 
			iconCls:'icon-cancel',
			handler: function() { 
				pAdminStu.dialog('close'); 
			}
		}],
		onLoad : function() {
			initStu();
		}
	});
}
//班长选择添加
function saveAdminStuAdd(){
	var rows=$('#stuGrid').datagrid('getSelected');
	  if(rows){
		  $('#adminStuIdId').val(rows.stuNo);
		  $('#adminStuNameId').val(rows.name);
	  }else{
		  $('#adminStuIdId').val('');
		  $('#adminStuNameId').val('');
	  }
	  pAdminStu.dialog('close');
}
//班长选择部分 结束//


//团支书选择部分 开始//
var pSecStu;
function secStuSelect(){
	pSecStu=dj.dialog({
		title : '选择该班团支书',
		href : '${pageContext.request.contextPath}/stu!stuSelect.do?classId=${classroom.id}',
		width : 700,
		height : 350,
		buttons : [ {
			text : '确定',
			iconCls:'icon-ok',
			handler : function() {
				saveSecStuAdd();
			}
		},{ 
			text: ' 取 消', 
			iconCls:'icon-cancel',
			handler: function() { 
				pSecStu.dialog('close'); 
			}
		}],
		onLoad : function() {
			initStu();
		}
	});
}
//团支书选择添加
function saveSecStuAdd(){
	var rows=$('#stuGrid').datagrid('getSelected');
	  if(rows){
		  $('#secStuIdId').val(rows.stuNo);
		  $('#secStuNameId').val(rows.name);
	  }else{
		  $('#secStuIdId').val('');
		  $('#secStuNameId').val('');
	  }
	  pSecStu.dialog('close');
}
//团支书选择部分 结束//
</script>
<div data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc;">
<div align="center" style="padding: 5px;overflow: hidden;">
	<form id="addForm"  method="post">
		<input type="hidden" name="id"  value="${classroom.id}"/>
<table style="width:1024px;">
    <tr>
      <td>		
        <table class="tableborder" id="formTable">
        		<tr height="30">
	              <td height="45" colspan="4" nowrap="nowrap" class="tablemain">
	               	<div align="center"><strong>班级新增</strong></div>
	              </td>
	            </tr>
				<tr height="30">
					<td nowrap class="tablelabel">班级名称：</td>
					<td align="left" class="tablecontent">
						<input type="text"  name="className" value="${classroom.className}" style="width:300px;padding:2px" />
					</td>
				</tr>
				<tr height="30">
					<td nowrap class="tablelabel">培养层次：</td>
					<td align="left" class="tablecontent">
						<input name="trainLevel" value="${classroom.trainLevel}" style="width:300px;padding:2px"/>
					</td>
				</tr>
				<tr height="30">
					<td nowrap class="tablelabel">学制(年)：</td>
					<td align="left" class="tablecontent">
						<input type="text"  name="sysLength" value="${classroom.sysLength}" style="width:300px;padding:2px" />
					</td>
				</tr>
				<tr height="30">
					<td nowrap class="tablelabel">建班年月：</td>
					<td align="left" class="tablecontent">
						<input name="buildDate" type="text" class="easyui-numberbox" value="${classroom.buildDate}" data-options="min:0" /> 
					</td>
				</tr>
				<tr height="30">
					<td nowrap class="tablelabel">班主任：</td>
					<td align="left" class="tablecontent">
						<input id="adminTeacherId" name="adminTeacherId" type="text" value="${classroom.adminTeacherId}" readonly="readonly">
						<input id="adminTeacherNameId" type="text"  name="adminTeacherName" value="${classroom.adminTeacherName}" style="width:300px;padding:2px" readonly="readonly"/>
		              	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="teacherSelect()">选择</a>
		             	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="$('#adminTeacherId').val('');$('#adminTeacherNameId').val('');">取消</a>
					</td>
				</tr>
				<tr height="30">
					<td nowrap class="tablelabel">班长：</td>
					<td align="left" class="tablecontent">
						<input id="adminStuIdId" name="adminStuId" type="text" value="${classroom.adminStuId}" readonly="readonly">
						<input id="adminStuNameId" type="text"  name="adminStuName" value="${classroom.adminStuName}" style="width:300px;padding:2px" readonly="readonly"/>
		              	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="adminStuSelect()">选择</a>
		             	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="$('#adminStuIdId').val('');$('#adminStuNameId').val('');">取消</a>
					</td>
				</tr>
				<tr height="30">
					<td nowrap class="tablelabel">团支书：</td>
					<td align="left" class="tablecontent" >
						<input id="secStuIdId" name="secStuId" type="text" value="${classroom.secStuId}" readonly="readonly">
						<input id="secStuNameId" type="text"  name="secStuName" value="${classroom.secStuName}" style="width:300px;padding:2px" readonly="readonly"/>
		              	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="secStuSelect()">选择</a>
		             	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="$('#secStuIdId').val('');$('#secStuNameId').val('');">取消</a>

					</td>
				</tr>
				<tr height="30">
					<td nowrap class="tablelabel">男生人数：</td>
					<td align="left" class="tablecontent">
						<input name="manNum" type="text" class="easyui-numberbox" value="${classroom.manNum}" data-options="min:0" /> 
					</td>
				</tr>
				<tr height="30">
					<td nowrap class="tablelabel">女生人数：</td>
					<td align="left" class="tablecontent">
						<input name="womanNum" type="text" class="easyui-numberbox" value="${classroom.womanNum}" data-options="min:0" /> 
					</td>
				</tr>
				<tr height="30">
					<td nowrap class="tablelabel">住校生人数：</td>
					<td align="left" class="tablecontent">
						<input name="livingNum" type="text" class="easyui-numberbox" value="${classroom.livingNum}" data-options="min:0" /> 
					</td>
				</tr>
				<tr height="60">
	              <td nowrap class="tablelabel" width="10%">存在问题：</td>
	              <td colspan="4" class="tablecontent"  align="left">
	              	<textarea name="problem" style="width:99.8%;height:100px;font-size: 14px;">${classroom.problem}</textarea>
	              </td>
				</tr>
	            <tr height="60">
	              <td nowrap class="tablelabel" width="10%">主要措施：</td>
	              <td colspan="4" class="tablecontent"  align="left">
	              	<textarea name="proSolve" style="width:99.8%;height:100px;font-size: 14px;">${classroom.proSolve}</textarea>
	              </td>
	            </tr>
		</table>
	</td>
	</tr>
</table>
	</form>
</div>
</div>