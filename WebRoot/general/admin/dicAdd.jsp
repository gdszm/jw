<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div align="center" style="padding: 5px;overflow: hidden;">
	<form id="dicForm" enctype=multipart/form-data method="post">
		<%--基础类型名称 --%>
		<input type="hidden" name="cbasetypeName" />
		<table class="tableForm">
			<tr>
				<td>名称:</td>
				<td><input type="text" name="ckey"/></td>
			</tr>
			<tr>
				<td>值:</td>
				<td><input type="text"  name="cvalue" /></td>
			</tr>
			<tr>
				<td>类型:</td>
				<td><input type="text" name="ctype"/></td>
			</tr>
			<tr>
				<td>类型名称:</td>
				<td><input type="text" name="ctypeName"/></td>
			</tr>
			<tr>
				<td>基础类型:</td>
				<td><input type="text" name="cbasetype" style="width:260px;"/></td>
			</tr>
					<tr>
				<td>状态:</td>
				<td><select name="cstatus">
						<option value="0">启用</option>
						<option value="1">停用</option>
					</select></td>
			</tr>
		</table>
	</form>
</div>