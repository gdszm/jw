<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<div align="center" style="padding: 5px;overflow: hidden;">
	<form id="leaveForm"  method="post">
		<input type="hidden" name="id" value="${obj.id}"/>
        <table class="tableborder" id="formTable">
				<tr height="30">
					<td nowrap class="tablelabel">请假类型：</td>
					<td align="left" class="tablecontent">
						<input type="text" name="leaveType" id="leaveType" value="${obj.leaveTypeName}" />
					</td>
				</tr>
				<tr height="30">
					<td nowrap class="tablelabel">请假事由：</td>
					<td align="left" class="tablecontent" ><textarea name="leaveReason" style="width:290px;padding:2px">${obj.leaveReason}</textarea></td>
				</tr>
		</table>
	</form>
</div>
