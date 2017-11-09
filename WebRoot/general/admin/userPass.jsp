<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div align="center" style="padding: 5px; overflow: hidden;">
	<form id="passForm" method="post">
		<input type="hidden" name="cid" value="${ sessionInfo.userId }" />
		<table id="addtable">
			<tr height="35">
				<td nowrap>
					原密码：
				</td>
				<td align="left">
					<input type="password" name="oldpass" class="easyui-validatebox" required="required" style="width: 200px; padding: 2px" />
				</td>
			</tr>
			<tr height="35">
				<td nowrap>
					新密码：
				</td>
				<td align="left">
					<input type="password" id="cpwd" name="cpwd" class="easyui-validatebox" required="required" validType="length[6,18]" style="width: 200px; padding: 2px" />
				</td>
			</tr>
			<tr height="35">
				<td nowrap>
					确认密码：
				</td>
				<td align="left">
					<input type="password" name="repassword" id="repassword" required="true" class="easyui-validatebox"  validType="equalTo['#cpwd']" invalidMessage="两次输入密码不匹配" style="width: 200px; padding: 2px" />
				</td>
			</tr>
		</table>
	</form>
</div>

<script>
	$.extend($.fn.validatebox.defaults.rules, {
        equalTo: { validator: function (value, param) { return $(param[0]).val() == value; }, message: '字段不匹配' }
	});
</script>