<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>	
<script type="text/javascript">
//课程选择部分 开始//
//课程选择添加
function saveCourseAdd(){
	var rows=$('#coursegrid').datagrid('getSelected');
	  if(rows){
		  $('#courseIdId').val(rows.id);
		  $('#courseNameId').val(rows.name);
		  $('#courseAttId').combobox('setValue', rows.courseAtt);
	  }else{
		  $('#courseIdId').val('');
		  $('#courseNameId').val('');
		  $('#courseAttId').combobox('setValue', '');
	  }
	  pCourse.dialog('close');
}
//课程选择部分 结束//

//教室选择部分 开始//
//教室选择添加
function saveClassroomAdd(){
	var rows=$('#classroomgrid').datagrid('getSelected');
	  if(rows){
		  	$('#classroomIdId').val(rows.id);
		  	$('#campusNameId').val(rows.campusName);
		  	$('#teachingbuildingNameId').val(rows.buildingName);
		  	$('#floorId').val(rows.floor);
		  	$('#houseNoId').val(rows.houseNo);
		  	$('#classroomNameId').val(rows.name);
	  }else{
		  $('#classroomNoId').val('');
		  $('#campusNameId').val('');
		  $('#teachingbuildingNameId').val('');
		  $('#floorId').val('');
		  $('#houseNoId').val('');
		  $('#classroomNameId').val('');
	  }
	  pClassroom.dialog('close');
}
//教室选择部分 结束//


//授课时间选择部分 开始//
//授课时间选择添加
function saveTimeAdd(){

	var rows=$('#timeGrid').datagrid('getSelected');
	  if(rows){
		  	$('#professTimeId').val(rows.id);
		    $('#weeksId').val(rows.weeks);
		    $('#weekId').val(rows.week);
		    $('#sectionId').val(rows.section);
	  }else{
		  $('#professTimeId').val('');
		  $('#weeksId').val('');
		  $('#weekId').val('');
		  $('#sectionId').val('');
	  }
	  ptime.dialog('close');
}
//授课时间选择部分 结束//
$(document).ready(function() {
	$('#addForm input[name=professAtt]').combobox({
		url : '${pageContext.request.contextPath}/dic!combox.do?ctype=professAtt',
		valueField:'cvalue', 
		editable:false,
		panelHeight:'100',
        textField:'ckey',
        required:true,
        validType:'sel'
	});
});
</script>
<div data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc;">
<div align="center" style="padding: 5px;overflow: hidden;">
		<form id="addForm" method="post">
			<input name="id" value="${profess.id}" type="hidden" />
			<table border="0" align="center" cellpadding="0" cellspacing="0" style="width:1024px;">
				<tr>
					<td>
						<table class="tableborder" id="formTable">
							<tr height="40">
				              <td height="45" colspan="4" align="middle" nowrap="nowrap" class="tablemain" width="100%">
				               	<div align="center"><strong>授课信息新增</strong></div>
				              </td>
				            </tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">教师</td>
								<td align="left" class="tablecontent" width="90%" colspan="3">
									<input name="teacherId" value="${profess.teacherId}" type="hidden" style="width:200px;padding:2px" readonly="readonly"/>
									&nbsp;姓名：<input name="teacherName" value="${profess.teacherName}" style="width:200px;padding:2px" readonly="readonly"/>
									&nbsp;性别：<input name="sex" value="${profess.sex}" style="width:200px;padding:2px" readonly="readonly"/>
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">课程</td>
								<td align="left" class="tablecontent" width="90%" colspan="3">
									<input id="courseIdId" name="courseId" value="${profess.courseId}" class="easyui-validatebox" data-options="required:true" type="hidden" style="width:200px;padding:2px" readonly="readonly" />
									&nbsp;课程名称：<input id="courseNameId" name="courseName" value="${profess.courseName}" class="easyui-validatebox" data-options="required:true" style="width:200px;padding:2px" readonly="readonly"/>
									&nbsp;课程属性：<input id="courseAttId" name="courseAtt" value="${profess.courseAtt}" style="width:200px;padding:2px" readonly="readonly"/>
									<a href="javascript:void(0);" class="easyui-linkbutton" onclick="courseSelect()">选择</a>
					             	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="$('#courseIdId').val('');$('#courseNameId').val(''); $('#courseAttId').combobox('setValue', '');">取消</a>
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">授课教室</td>
								<td align="left" class="tablecontent" width="90%" colspan="3">
									<input id="classroomIdId" name="classroomId" value="${profess.classroomId}" type="hidden"  style="width:100px;padding:2px" readonly="readonly"/>
									&nbsp;校区：<input id="campusNameId" name="campusName" value="${profess.campusName}" style="width:100px;padding:2px" readonly="readonly"/>
									&nbsp;教学楼：<input id="teachingbuildingNameId" name="teachingbuildingName" value="${profess.teachingbuildingName}" style="width:100px;padding:2px" readonly="readonly"/>
									&nbsp;楼层：<input id="floorId" name="floor" value="${profess.floor}" style="width:15px;padding:2px" readonly="readonly"/>
									&nbsp;门牌号：<input id="houseNoId" name="houseNo" value="${profess.houseNo}" style="width:25px;padding:2px" readonly="readonly"/>
									&nbsp;教室名称：<input id="classroomNameId" name="classroomName" value="${profess.classroomName}" class="easyui-validatebox" data-options="required:true" style="width:100px;padding:2px" readonly="readonly"/>
				              		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="classRoomSelect()">选择</a>
					             	<a href="javascript:void(0);" class="easyui-linkbutton" 
					             	onclick="$('#classroomIdId').val('');$('#campusNameId').val('');$('#teachingbuildingNameId').val('');$('#floorId').val('');$('#houseNoId').val('');$('#classroomNameId').val('');">取消</a>
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">授课时间：</td>
								<td align="left" class="tablecontent" width="90%" colspan="3">
									<input id="professTimeId" name="professTimeId" value="${profess.professTimeId}" type="hidden" style="width:200px;padding:2px" readonly="readonly"/>
									&nbsp;周次：<input id="weeksId" name="weeks" value="${profess.weeks}" class="easyui-validatebox" data-options="required:true" style="width:470px;padding:2px" readonly="readonly"/>
									&nbsp;星期：<input id="weekId" name="week" value="${profess.week}" style="width:100px;padding:2px" readonly="readonly"/>
									&nbsp;节次：<input id="sectionId" name="section" value="${profess.section}" style="width:100px;padding:2px" readonly="readonly"/>
				              		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="professTimeSelect()">选择</a>
					             	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="$('#professTimeId').val('');$('#weeksId').val('');$('#weekId').val('');$('#sectionId').val('');">取消</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>