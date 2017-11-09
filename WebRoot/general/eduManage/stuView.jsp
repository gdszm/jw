<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>	
<div data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc;">
<div align="center" style="padding: 5px;overflow: hidden;">
		<form id="addForm" method="post">
			<input type="hidden" name="stuNo" value="${stu.stuNo}" /> 
			<table border="0" align="center" cellpadding="0" cellspacing="0" style="width:1024px;">
				<tr>
					<td>
						<table class="tableborder" id="formTable">
							<tr height="40">
				              <td height="45" colspan="4" align="middle" nowrap="nowrap" class="tablemain" width="100%">
				               	<div align="center"><strong>学生信息详情</strong></div>
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
									<p style="height:15px;line-height:15px;">${stu.deptName}</p>
								</td>
								<td nowrap class="tablelabel" width="10%">专业：</td>
								<td align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;">${stu.majorName}</p>
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">所在班级：</td>
								<td align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;">${stu.className}</p>
								</td>
								<td nowrap class="tablelabel" width="10%">档案：</td>
								<td align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;">编号：${stu.archNo}&nbsp;姓名：${stu.name}</p>
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">入学时间：</td>
								<td align="left" class="tablecontent" width="20%">
									<input id="inTimeId" name="inTime"  type="text" value="${stu.inTime}" class="easyui-datebox" style="width:200px;" readonly="readonly"></input>
								</td>
								<td nowrap class="tablelabel" width="10%">入学来源：</td>
								<td align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;">${stu.enSourName}</p>
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">主要问题：</td>
								<td align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;">${stu.priProName}</p>
								</td>
								<td nowrap class="tablelabel" width="10%">毕业以后的打算：</td>
								<td align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;">${stu.planAfterGrad}</p>
								</td>
							</tr>
							<tr height="60">
				              <td nowrap class="tablelabel" width="10%">备注：</td>
				              <td colspan="3" class="tablecontent"  align="left">
				              	<textarea name="remark" style="width:99.8%;height:100px;font-size: 14px;" readonly="readonly">${stu.remark}</textarea>
				              </td>
				            </tr>
<%--							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;务：</td>
								<td align="left" class="tablecontent" width="20%"><input name="job"  style="width:98%;padding:2px" /></td>
								<td nowrap class="tablelabel" width="10%">电子邮箱：</td>
								<td align="left" class="tablecontent" width="20%" colspan="2">
									<input type="text" name="email" style="width:99%;padding:2px" />
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族：</td>
								<td align="left" class="tablecontent" width="20%">
									<input name="nation" style="width:300px;padding:2px" />
								</td>
								<td nowrap class="tablelabel" width="10%">手&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机：</td>
								<td align="left" class="tablecontent" width="20%" colspan="2">
									<input type="text" name="telephone" class="easyui-validatebox"  
									data-options="required:true,validType:'length[7,12]',missingMessage:'请填电话号码\n登录名默认与电话号码相同'" 
									style="width:99%;padding:2px">
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">固定电话：</td>
								<td align="left" class="tablecontent" width="20%">
									<input type="text" name="comparyPhone" class="easyui-validatebox"  style="width:98%;padding:2px">
								</td>
								<td nowrap class="tablelabel" width="10%">有效届次：</td>
								<td align="left" class="tablecontent" width="20%" colspan="2">
									<input name="secondaryCode" data-options="required:true,editable:false,validType:'sel'" multiple="true" style="width:515px;padding:2px" />
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">单位名称：</td>
								<td align="left" class="tablecontent" width="20%" colspan="4">
									<input type="text" name="companyName" style="width:99.4%;padding:2px" />
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">通讯地址：</td>
								<td align="left" class="tablecontent" width="20%">	
									<input type="text" name="comparyAddress" style="width:98%;padding:2px" />
								</td>
								<td nowrap class="tablelabel" width="10%">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编：</td>
								<td align="left" class="tablecontent" width="20%" colspan="2">
									<input type="text" name="comparyPost" style="width:99%;padding:2px" />
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">家庭地址：</td>
								<td align="left" class="tablecontent" width="20%" colspan="4">
									<input type="text" name="familyAddress" style="width:99.4%;padding:2px" />
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：</td>
								<td align="left" class="tablecontent" width="20%" colspan="4">
									<input name="status" data-options="required:true,editable:false,validType:'sel'" style="width:910px;padding:2px" />
								</td>
							</tr>
		--%>
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