<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div align="center" style="padding: 5px;overflow: hidden;">
	<form method="post">
		<table class="tableForm">
			<tr>
				<th style="width: 55px;">角色名称</th>
				<td><input name="cname" class="easyui-validatebox" data-options="required:true,missingMessage:'请填写菜单名称'" style="width:520px;" />
				</td>
			</tr>
			<tr>
				<th>拥有权限</th>
				<td><input name="authIds" style="width:520px;" />
				</td>
			</tr>
			<tr>
				<th>备注</th>
				<td><textarea name="cdesc" style="height:100px;"></textarea>
				</td>
			</tr>
		</table>
	</form>
</div>