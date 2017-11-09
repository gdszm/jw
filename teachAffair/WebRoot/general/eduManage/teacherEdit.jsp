<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>	
<script type="text/javascript">
//档案选择部分 开始//
var pArch;
function archSelect(){
	pArch=dj.dialog({
		title : '选择档案',
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
</script>
<div data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc;">
<div align="center" style="padding: 5px;overflow: hidden;">
		<form id="addForm" method="post">
			<input type="hidden" name="id" value="${teacher.id}" /> 
			<table border="0" align="center" cellpadding="0" cellspacing="0" style="width:1024px;">
				<tr>
					<td>
						<table class="tableborder" id="formTable">
							<tr height="40">
				              <td height="45" colspan="5" align="middle" nowrap="nowrap" class="tablemain" width="100%">
				               	<div align="center"><strong>教师信息修改</strong></div>
				              </td>
				            </tr>
				      		<tr height="40" class="tablelabel">
				          		<td nowrap class="tablelabel" width="10%">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</td>
								<td align="left" class="tablecontent" width="20%">
									<p id="pNameId" style="height:15px;line-height:15px;">${teacher.name}</p>
								</td>
								<td nowrap class="tablelabel" width="10%">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</td>
								<td align="left" class="tablecontent" width="20%">
									<p id="pSexNameId" style="height:15px;line-height:15px;">${teacher.sexName}</p>
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">所在院系：</td>
								<td align="left" class="tablecontent" width="20%">
									<input type="text" name="cid" value="${teacher.cid}" class="easyui-validatebox"  style="width:354px;padding:2px;"/>
								</td>
								<td nowrap class="tablelabel" width="10%">档案编号：</td>
								<td align="left" class="tablecontent" width="20%">
									<input id="archNoId"  name="archNo" type="text" value="${teacher.archNo}" class="easyui-validatebox"  style="width:100px;" data-options="required:true" readonly="readonly"/>
									<input id="nameId"  name="name" type="text" value="${teacher.name}" class="easyui-validatebox"  style="width:100px;" data-options="required:true" readonly="readonly"/>
					              	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="archSelect()">选择</a>
					             	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="$('#archNoId').val('');$('#nameId').val('');$('#pNameId').text('');$('#pSexNameId').text('');">取消</a>
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">教师职称：</td>
								<td align="left" class="tablecontent" width="20%">
									<input name="rank" value="${teacher.rank}" style="width:354px;padding:2px"  />
								</td>
								<td nowrap class="tablelabel" width="10%">婚姻状况：</td>
								<td align="left" class="tablecontent" width="20%">
									<input name="marry" value="${teacher.marry}" style="width:300px;padding:2px"/>
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">有无教师资格证：</td>
								<td align="left" class="tablecontent" width="20%">
									<input name="hasTeacherCert" value="${teacher.hasTeacherCert}" style="width:354px;padding:2px" />
								</td>
								<td nowrap class="tablelabel" width="10%">学历：</td>
								<td align="left" class="tablecontent" width="20%">
									<input name="education" value="${teacher.education}" style="width:300px;padding:2px" />
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">毕业院校：</td>
								<td align="left" class="tablecontent" width="20%">
									<input name="gradFrom" value="${teacher.gradFrom}" style="width:350px;padding:2px" />
								</td>
								<td nowrap class="tablelabel" width="10%">专业名称：</td>
								<td align="left" class="tablecontent" width="20%">
									<input type="text"  name="majorName" value="${teacher.majorName}" style="width:98%;padding:2px"/>
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">学位：</td>
								<td colspan="4" align="left" class="tablecontent" width="20%">
									<input name="degree" value="${teacher.degree}" style="width:654px;padding:2px"/>
								</td>
							</tr>
							<tr height="60">
				              <td nowrap class="tablelabel" width="10%">备注：</td>
				              <td colspan="4" class="tablecontent"  align="left">
				              	 <textarea name="remark" style="width:99.8%;height:100px;font-size: 14px;">${teacher.remark}</textarea>
				              </td>
				            </tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
<script type="text/javascript">
	$.extend($.fn.validatebox.defaults.rules, {  
		sel: {  
			validator: function(value){ 
				return value != '请选择...';  
			},  
			message: '此项必须选择！'  
		}  
	});  
</script>