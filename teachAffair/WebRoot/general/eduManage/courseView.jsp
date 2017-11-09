<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>	
<div data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc;">
<div align="center" style="padding: 5px;overflow: hidden;">
		<form id="addForm" method="post">
			<input type="hidden" name="id" value="${course.id}" /> 
			<table border="0" align="center" cellpadding="0" cellspacing="0" style="width:1024px;">
				<tr>
					<td>
						<table class="tableborder" id="formTable">
							<tr height="40">
				              <td height="45" colspan="4" align="middle" nowrap="nowrap" class="tablemain" width="100%">
				               	<div align="center"><strong>课程信息详情</strong></div>
				              </td>
				            </tr>
				            <tr height="40" class="tablelabel">
				           	 	<td nowrap class="tablelabel" width="10%">ID：</td>
								<td align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${course.id}</p>
								</td>
								<td nowrap class="tablelabel" width="10%">课程名称：</td>
								<td align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${course.name}</p>
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">课程英文名称：</td>
								<td align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${course.enName}</p>
								</td>
								<td nowrap class="tablelabel" width="10%">课程序号：</td>
								<td align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${course.sortNo}</p>
								</td>
								
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">课程学分：</td>
								<td align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${course.credit}</p>
								</td>
								<td nowrap class="tablelabel" width="10%">课程属性</td>
								<td align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${course.courseAttName}</p>
								</td>
							</tr>
							<tr height="60">
				              <td nowrap class="tablelabel" width="10%">教学方法：</td>
				              <td colspan="3" class="tablecontent"  align="left">
				              	<textarea name="teachMeth" style="width:99.8%;height:100px;font-size: 14px;padding:2px;" readonly="readonly">${course.teachMeth}</textarea>
				              </td>
				            </tr>
				            <tr height="60">
				              <td nowrap class="tablelabel" width="10%">使用教材：</td>
				              <td colspan="3" class="tablecontent"  align="left">
				              	<textarea name="teachBook" style="width:99.8%;height:100px;font-size: 14px;padding:2px;" readonly="readonly">${course.teachBook}</textarea>
				              </td>
				            </tr>
				            <tr height="60">
				              <td nowrap class="tablelabel" width="10%">考核方式及其要求：</td>
				              <td colspan="3" class="tablecontent"  align="left">
				              	<textarea name="asMethAnReq" style="width:99.8%;height:100px;font-size: 14px;padding:2px;" readonly="readonly">${course.asMethAnReq}</textarea>
				              </td>
				            </tr>
				            <tr height="60">
				              <td nowrap class="tablelabel" width="10%">备注：</td>
				              <td colspan="3" class="tablecontent"  align="left">
				              	<textarea name="comment" style="width:99.8%;height:100px;font-size: 14px;padding:2px;" readonly="readonly">${course.comment}</textarea>
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