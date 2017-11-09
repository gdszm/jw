<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>	
<script type="text/javascript">
$(document).ready(function() {
	$('#addForm input[name=acadeYear]').combobox({
		url : '${pageContext.request.contextPath}/dic!combox.do?ctype=acadeYear',
		valueField:'cvalue', 
		editable:false,
		panelHeight:'100',
        textField:'ckey',
        required:true,
        validType:'sel'
	});
	$('#addForm input[name=termType]').combobox({
		url : '${pageContext.request.contextPath}/dic!combox.do?ctype=termType',
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
			<table border="0" align="center" cellpadding="0" cellspacing="0" style="width:1024px;">
				<tr>
					<td>
						<table class="tableborder" id="formTable">
							<tr height="40">
				              <td height="45" colspan="4" align="middle" nowrap="nowrap" class="tablemain" width="100%">
				               	<div align="center"><strong>学期信息修改</strong></div>
				              </td>
				            </tr>
				            <tr height="40" class="tablelabel">
				           		<td nowrap class="tablelabel" width="10%">学期编号：</td>
								<td align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${term.id}</p>
								</td>
								<td nowrap class="tablelabel" width="10%">学年：</td>
								<td align="left" class="tablecontent" width="20%">
									<input name="acadeYear" value="${term.acadeYear}" style="width:300px;padding:2px"  />
								</td>
							</tr>
				            <tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">学期名：</td>
								<td align="left" class="tablecontent" width="20%">
									<input name="termName" value="${term.termName}" style="width:300px;padding:2px"  />
								</td>
								<td nowrap class="tablelabel" width="10%">学期类型：</td>
								<td align="left" class="tablecontent" width="20%">
									<input name="termType" value="${term.termType}" style="width:300px;padding:2px"  />
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">是否当前学期：</td>
								<td align="left" class="tablecontent" width="20%">
									<input name="isNowTerm" value="${term.isNowTerm}" style="width:300px;padding:2px"  />
								</td>
								<td nowrap class="tablelabel" width="10%">学期周数：</td>
								<td align="left" class="tablecontent" width="20%">
									<input name="weeks" value="${term.weeks}" style="width:300px;padding:2px"  />
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">周天数：</td>
								<td align="left" class="tablecontent" width="20%">
									<input name="weekDays" value="${term.weekDays}" style="width:300px;padding:2px"  />
								</td>
								<td nowrap class="tablelabel" width="10%"></td>
								<td align="left" class="tablecontent" width="20%">
								</td>
							</tr>
				            <tr height="60">
				              <td nowrap class="tablelabel" width="10%">备注：</td>
				              <td colspan="3" class="tablecontent"  align="left">
				              	<textarea name="comment" style="width:99.8%;height:100px;font-size: 14px;">${term.comment}</textarea>
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