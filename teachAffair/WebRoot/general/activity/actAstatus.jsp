<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<div align="center" style="padding: 5px;overflow: hidden;">
	<form id="leaveForm"  method="post">
        <table class="tableborder" id="formTable">
				<tr height="30">
					<td nowrap class="tablelabel"  width="80">出席情况：</td>
					<td align="left" class="tablecontent"><input type="text" name="activitypeo.astatus" id="astatus"/></td>
				</tr>
				<tr height="30" id="commtr_2" style="display: none;">
					<td nowrap class="tablelabel">请假类型：</td>
					<td align="left" class="tablecontent"><input type="text" name="activitypeo.leaveType" id="leaveType" value="${obj.leaveType}" /></td>
				</tr>
				<tr height="30" id="commtr_6" style="display: none;">
					<td nowrap class="tablelabel">请假事由：</td>
					<td align="left" class="tablecontent" ><textarea name="activitypeo.leaveReason"  style="width:260px;padding:2px">${obj.leaveReason}</textarea></td>
				</tr>
		</table>
	</form>
</div>
