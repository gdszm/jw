<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" charset="utf-8">
	$.messager.progress({
		text : '数据加载中....',
		interval : 100
	});
</script>
<div style="padding: 5px;">
	<form id="userForm" method="post">
		<input name="cid" type="hidden" />
		<table class="tableForm">
			<tr>
				<th style="width: 55px;">用户名</th>
				<td><input name="cname" readonly="readonly"  style="width: 150px;" />
				</td>
				<th style="width: 55px;">所属角色</th>
				<td><input name="roleIds" style="width: 150px;" /></td>
			</tr>
			<tr>
				<th>密码</th>
				<td><input name="cpwd" type="password" />
				</td>
				<td colspan="2" style="text-align: left;">如果不修改请留空</td>
			</tr>
			<tr>
				<th>真实姓名</th>
				<td><input name="crealname" type="text" class="easyui-validatebox" data-options="required:'true',missingMessage:'请填写真实姓名'" />
				</td>
				<!-- <th>部门</th>
				<td><input type="text" name="deptId" style="width: 145px;" />
				</td> -->
			</tr>
			<tr>
				<th>邮箱</th>
				<td><input name="cemail" type="text" />
				</td>
				<th>状态</th>
				<td><select name="cstatus">
					<option value="0">停用</option>
					<option value="1">启用</option>
				</select>
				</td>
			</tr>
		</table>
	</form>
</div>