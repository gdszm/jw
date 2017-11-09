<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>	
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
				               	<div align="center"><strong>教师信息详情</strong></div>
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
									<p style="height:15px;line-height:15px;padding-left:2px;">${teacher.cname}</p>
								</td>
								<td nowrap class="tablelabel" width="10%">档案：</td>
								<td align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;">编号：${teacher.archNo}&nbsp;&nbsp;姓名：${teacher.name}</p>
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">教师职称：</td>
								<td align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${teacher.rankName}</p>
								</td>
								<td nowrap class="tablelabel" width="10%">婚姻状况：</td>
								<td align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${teacher.marryName}</p>
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">有无教师资格证：</td>
								<td align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${teacher.hasTeacherCertName}</p>
								</td>
								<td nowrap class="tablelabel" width="10%">学历：</td>
								<td align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${teacher.educationName}</p>
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">毕业院校：</td>
								<td align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${teacher.gradFrom}</p>
								</td>
								<td nowrap class="tablelabel" width="10%">专业名称：</td>
								<td align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${teacher.majorName}</p>
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">学位：</td>
								<td colspan="4" align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${teacher.degreeName}</p>
								</td>
							</tr>
							<tr height="60">
				              <td nowrap class="tablelabel" width="10%">备注：</td>
				              <td colspan="4" class="tablecontent"  align="left">
				              	 <textarea name="remark" style="width:99.8%;height:100px;font-size: 14px;" readonly="readonly">${teacher.remark}</textarea>
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