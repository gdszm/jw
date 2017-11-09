<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.tlzn.util.base.Constants"%>	
<jsp:include page="../../public/inc.jsp"></jsp:include>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
	//提交课程信息
	function submitCourse(){
		var f =$("#addForm");
		f.form('submit', {
			url : '${pageContext.request.contextPath}/course!add.do',
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
		$('#addForm input[name=courseAtt]').combobox({
			url : '${pageContext.request.contextPath}/dic!combox.do?ctype=courseAtt',
			valueField:'cvalue',
			editable:false, 
			panelHeight:'100',
	        textField:'ckey',
	        required:true,
	        validType:'sel'
		});
	});
	$.extend($.fn.validatebox.defaults.rules, {  
		sel: {  
			validator: function(value){ 
				return value != '请选择...';  
			},  
			message: '此项必须选择！'  
		}  
	}); 
</script>
</head>
<div class="easyui-layout" data-options="fit:true" class="cs-home-remark">
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
					               	<div align="center"><strong>课程发布</strong></div>
					              </td>
					            </tr>
					            <tr height="40" class="tablelabel">
									<td nowrap class="tablelabel" width="10%">课程名称：</td>
									<td align="left" class="tablecontent" width="20%">
										<input name="name" value="${course.name}" style="width:300px;padding:2px"  />
									</td>
									<td nowrap class="tablelabel" width="10%">课程英文名称：</td>
									<td align="left" class="tablecontent" width="20%">
										<input name="enName" value="${course.enName}" style="width:300px;padding:2px"/>
									</td>
								</tr>
								<tr height="40" class="tablelabel">
									<td nowrap class="tablelabel" width="10%">课程序号：</td>
									<td align="left" class="tablecontent" width="20%">
										<input name="sortNo" value="${course.sortNo}" style="width:300px;padding:2px"  />
									</td>
									<td nowrap class="tablelabel" width="10%">课程学分：</td>
									<td align="left" class="tablecontent" width="20%">
										<input name="credit" value="${course.credit}" style="width:300px;padding:2px"/>
									</td>
								</tr>
								<tr height="40" class="tablelabel">
									<td nowrap class="tablelabel" width="10%">课程属性：</td>
									<td align="left" class="tablecontent" width="20%">
										<input name="courseAtt" value="${course.courseAtt}" style="width:300px;padding:2px"  />
									</td>
									<td nowrap class="tablelabel" width="10%"></td>
									<td align="left" class="tablecontent" width="20%">
									</td>
								</tr>
								<tr height="60">
					              <td nowrap class="tablelabel" width="10%">教学方法：</td>
					              <td colspan="3" class="tablecontent"  align="left">
					              	<textarea name="teachMeth" style="width:99.8%;height:100px;font-size: 14px;">${course.teachMeth}</textarea>
					              </td>
					            </tr>
					            <tr height="60">
					              <td nowrap class="tablelabel" width="10%">使用教材：</td>
					              <td colspan="3" class="tablecontent"  align="left">
					              	<textarea name="teachBook" style="width:99.8%;height:100px;font-size: 14px;">${course.teachBook}</textarea>
					              </td>
					            </tr>
					            <tr height="60">
					              <td nowrap class="tablelabel" width="10%">考核方式及其要求：</td>
					              <td colspan="3" class="tablecontent"  align="left">
					              	<textarea name="asMethAnReq" style="width:99.8%;height:100px;font-size: 14px;">${course.asMethAnReq}</textarea>
					              </td>
					            </tr>
					            <tr height="60">
					              <td nowrap class="tablelabel" width="10%">备注：</td>
					              <td colspan="3" class="tablecontent"  align="left">
					              	<textarea name="comment" style="width:99.8%;height:100px;font-size: 14px;">${course.comment}</textarea>
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
		<a id="submitBtn" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="javascript:submitCourse();">提交</a>　
		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:window.parent.closetab();">关闭</a>
	</div>
</div>
</body>
</html>