<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<div align="center" style="padding: 5px;overflow: hidden;">
	<form id="setForm"  method="post">
		<input type="hidden" name="ids" id="ids" />
        <table class="tableborder" id="formTable">
			<tr height="30">
				<td nowrap class="tablelabel">出席情况：</td>
				<td align="left" class="tablecontent"><input type="text" name="status" id="status"/></td>
			</tr>
            <tr id="leave" height="30" sizset="false" style="display:none">
				<td nowrap class="tablelabel">请假类型：</td>
				<td align="left" class="tablecontent"><input type="text" name="leaveType" id="leaveType"/></td>
			</tr>
             <tr id="leaveres" height="30" sizset="false" style="display:none">
				<td nowrap class="tablelabel">请假事由：</td>
				<td align="left" class="tablecontent" ><textarea name="leaveReason"  style="width:290px;padding:2px"></textarea></td>
			</tr>
		</table>
	</form>
</div>
