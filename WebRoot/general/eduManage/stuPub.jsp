<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.tlzn.util.base.Constants"%>	
<jsp:include page="../../public/inc.jsp"></jsp:include>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
//班级选择部分 开始//
var pClass;
function classSelect(){
	pClass=dj.dialog({
		title : '选择所属班级',
		href : '${pageContext.request.contextPath}/stu!classSelect.do',
		width : 600,
		height : 360,
		left:350,
		top:100,
		buttons : [ {
			text : '确定',
			iconCls:'icon-ok',
			handler : function() {
				saveClassAdd();
			}
		},{ 
			text: ' 取 消', 
			iconCls:'icon-cancel',
			handler: function() { 
				pClass.dialog('close'); 
			}
		}],
		onLoad : function() {
			initClass();
		}
	});
}

//班级选择添加
function saveClassAdd(){
	var rows=$('#classgrid').datagrid('getSelected');
	  if(rows){
		  $('#classIdId').val(rows.id);
		  $('#classNameId').val(rows.className);
	  }else{
		  $('#classIdId').val('');
		  $('#classNameId').val('');
	  }
	  pClass.dialog('close');
}
//班级选择部分 结束//



//档案选择部分 开始//
var pArch;
function archSelect(){
	pArch=dj.dialog({
		title : '选择学生档案',
		href : '${pageContext.request.contextPath}/stu!archSelect.do',
		width : 600,
		height : 360,
		left:350,
		top:100,
		buttons : [ {
			text : '确定',
			iconCls:'icon-ok',
			handler : function() {
				saveArchAdd();
			}
		},{
			text: ' 取 消', 
			iconCls:'icon-cancel',
			handler: function() { 
				pArch.dialog('close'); 
			}
		}],
		onLoad : function() {
			initArch();
		}
	});
}

//档案选择添加
function saveArchAdd(){
	var rows=$('#archgrid').datagrid('getSelected');
	  if(rows){
		  	$('#archNoId').val(rows.archNo);
		    $('#nameId').val(rows.name);
		    $('#pNameId').text(rows.name);
		    $('#pSexNameId').text(rows.sexName);
	  }else{
		  $('#archNoId').val('');
		  $('#nameId').val('');
		  $('#pNameId').text('');
		  $('#pSexNameId').text('');
	  }
	  pArch.dialog('close');
}

//提交学生信息
function submitStu(){
	var f =$("#addForm");
	f.form('submit', {
		url : '${pageContext.request.contextPath}/stu!add.do',
		success : function(d) {
			var json = $.parseJSON(d);
			if (json.success) {
				parent.dj.messagerShow({
					msg : json.msg,
					title : '提示'
				});
			 	window.parent.closetab();
			}
		}
	
	});
}

$(document).ready(function() {
	$('#addForm input[name=dept]').combobox({
		url : '${pageContext.request.contextPath}/dic!combox.do?ctype=yxb',
		valueField:'cvalue', 
		panelHeight:'100',
        textField:'ckey',
        required:true,
        validType:'sel'
	});
	$('#addForm input[name=major]').combobox({
		url : '${pageContext.request.contextPath}/dic!combox.do?ctype=major',
		valueField:'cvalue', 
		panelHeight:'100',
        textField:'ckey',
        required:true,
        validType:'sel'
	});
	initCombobox('addForm','enSour','${pageContext.request.contextPath}/dic!combox.do?ctype=enSour');
	initCombobox('addForm','priPro','${pageContext.request.contextPath}/dic!combox.do?ctype=priPro');
});
//档案选择部分 结束//
</script>
</head>
<div class="easyui-layout" data-options="fit:true" class="cs-home-remark">
	<div data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc;">
	<div align="center" style="padding: 5px;overflow: hidden;">
			<form id="addForm" method="post">
				<input type="hidden" id="fmIds" name="fmIds" />
				<input type="hidden" id="apIds" name="apIds" />
				<input type="hidden" id="eeIds" name="eeIds" />
				<input type="hidden" id="weIds" name="weIds" />
				<table border="0" align="center" cellpadding="0" cellspacing="0" style="width:1024px;">
					<tr>
						<td>
							<table class="tableborder" id="formTable">
								<tr height="40">
					              <td height="45" colspan="4" align="middle" nowrap="nowrap" class="tablemain" width="100%">
					               	<div align="center"><strong>学生信息新增</strong></div>
					              </td>
					            </tr>
					            <tr height="40" class="tablelabel">
					          		<td nowrap class="tablelabel" width="10%">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</td>
									<td align="left" class="tablecontent" width="20%">
										<p id="pNameId" style="height:15px;line-height:15px;">${stu.name}</p>
									</td>
									<td nowrap class="tablelabel" width="10%">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</td>
									<td align="left" class="tablecontent" width="20%">
										<p id="pSexNameId" style="height:15px;line-height:15px;">${stu.sexName}</p>
									</td>
								</tr>
								<tr height="40" class="tablelabel">
									<td nowrap class="tablelabel" width="10%">院系部：</td>
									<td align="left" class="tablecontent" width="20%">
										<input name="dept" value="${stu.dept}" style="width:300px;padding:2px"  />
									</td>
									<td nowrap class="tablelabel" width="10%">专业：</td>
									<td align="left" class="tablecontent" width="20%">
										<input name="major" value="${stu.major}" style="width:300px;padding:2px"/>
									</td>
								</tr>
								<tr height="40" class="tablelabel">
									<td nowrap class="tablelabel" width="10%">所在班级：</td>
									<td align="left" class="tablecontent" width="20%">
										<input id="classIdId"  name="classId" type="hidden" value="${stu.classId}"/>
						              	<input id="classNameId"  name="className"  value="${stu.className}" class="easyui-validatebox" data-options="required:true" style="width:150px;" readonly="readonly"/>
						              	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="classSelect()">选择</a>
						             	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="$('#classIdId').val('');$('#classNameId').val('');">取消</a>
									</td>
									<td nowrap class="tablelabel" width="10%">档案编号：</td>
									<td align="left" class="tablecontent" width="20%">
										<input id="archNoId"  name="archNo" type="text" value="${stu.archNo}" class="easyui-validatebox"  style="width:100px;" data-options="required:true" readonly="readonly"/>
										<input id="nameId"  name="name" type="text" value="${stu.name}" class="easyui-validatebox"  style="width:100px;" data-options="required:true" readonly="readonly"/>
						              	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="archSelect()">选择</a>
						             	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="$('#archNoId').val('');$('#nameId').val('');$('#pNameId').text('');$('#pSexNameId').text('');">取消</a>
									</td>
								</tr>
								<tr height="40" class="tablelabel">
									<td nowrap class="tablelabel" width="10%">入学时间：</td>
									<td align="left" class="tablecontent" width="20%">
										<input id="inTimeId" name="inTime"  type="text" value="${stu.inTime}" class="easyui-datebox" style="width:200px;"></input>
									</td>
									<td nowrap class="tablelabel" width="10%">入学来源：</td>
									<td align="left" class="tablecontent" width="20%">
										<input name="enSour" value="${stu.enSour}" style="width:300px;padding:2px" />
									</td>
								</tr>
								<tr height="40" class="tablelabel">
									<td nowrap class="tablelabel" width="10%">主要问题：</td>
									<td align="left" class="tablecontent" width="20%">
										<input name="priPro" value="${stu.priPro}" style="width:369px;padding:2px" />
									</td>
									<td nowrap class="tablelabel" width="10%">毕业以后的打算：</td>
									<td align="left" class="tablecontent" width="20%">
										<input type="text"  name="planAfterGrad" value="${stu.planAfterGrad}" style="width:98%;padding:2px"/>
									</td>
								</tr>
								<tr height="60">
					              <td nowrap class="tablelabel" width="10%">备注：</td>
					              <td colspan="3" class="tablecontent"  align="left">
					              	<textarea name="remark" style="width:99.8%;height:100px;font-size: 14px;">${stu.remark}</textarea>
					              </td>
					            </tr>
							</table>	
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div data-options="region:'south',border:false" style="text-align:center;padding:5px 0;height:40px">
		<a id="submitBtn" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="javascript:submitStu();">提交</a>　
		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:window.parent.closetab();">关闭</a>
	</div>
</div>
</body>
</html>