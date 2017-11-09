<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div align="center" style="padding: 5px;overflow: hidden;">
	<form id="editForm" method="post">
	<input type="hidden" name="cid" value="${dic.cid}"/>
		<%--基础类型名称 --%>
	<input type="hidden" name="cbasetypeName" value="${dic.cbasetypeName}" />

		<table class="tableForm">
			<tr>
				<td>名称:</td>
				<td><input type="text" name="ckey" value="${dic.ckey}"/></td>
			</tr>
			<tr>
				<td>值:</td>
				<td><input type="text" name="cvalue" value="${dic.cvalue}"/></td>
			</tr>
			<tr>
				<td>类型:</td>
				<td><input type="text" name="ctype" value="${dic.ctype}"/></td>
			</tr>
			<tr>
				<td>类型名称:</td>
				<td><input type="text" name="ctypeName" value="${dic.ctypeName}"/></td>
			</tr>
			<tr>
				<td>基础类型:</td>
				<td><input type="text" id="cbasetypeEditId" name="cbasetype" value="${dic.cbasetype}"/></td>
			</tr>
			<tr>
				<td>状态:</td>
				<td>
				<input name="cstatus" value="${dic.cstatus}" />
<%--				<select name="cstatus">--%>
<%--						<option value="0">启用</option>--%>
<%--						<option value="1">停用</option>--%>
<%--				</select>--%>
				</td>
			</tr>
		</table>
	</form>
</div>