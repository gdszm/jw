<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div align="center" style="padding: 5px;overflow: hidden;">
	<form id="commForm" method="post">
		<input type="hidden" name="companyId" value="${sessionComp.companyId}"/>
		<table id="addtable">				
			<tr height="35">
				<td nowrap>单位名称：</td>
				<td align="left" colspan="4"><input type="text" name="companyName" value="${sessionComp.companyName}" class="easyui-validatebox" required="true" style="width:350px;padding:2px" /></td>
			</tr>
			<tr height="35">
				<td nowrap>单位简称：</td>
				<td align="left" colspan="4"><input type="text" name="shortName" value="${sessionComp.shortName}" style="width:350px;padding:2px" /></td>
			</tr>
			<tr height="35">
				<td nowrap>单位类型：</td>
				<td align="left"><input name="companyType" value="${sessionComp.companyType}" style="width:140px;padding:2px" /></td>
				<td nowrap>单位邮编：</td>
				<td align="left"><input type="text" name="companyPost" value="${sessionComp.companyPost}" style="width:140px;padding:2px" /></td>
			</tr>
			<tr height="35">
				<td nowrap>单位地址：</td>
				<td align="left" colspan="4"><input type="text" name="companyAddress" value="${sessionComp.companyAddress}" style="width:350px;padding:2px" /></td>
			</tr>
			<tr height="35">
				<td nowrap>联系人：</td>
				<td align="left"><input type="text" name="linkman" value="${sessionComp.linkman}" style="width:140px;padding:2px" /></td>
				<td nowrap>联系电话：</td>
				<td align="left"> <input type="text" id="phone" name="phone" value="${sessionComp.phone}" class="easyui-validatebox" required="true" validType="length[7,12]" style="width:140px;padding:2px" /></td>
			</tr>
			<tr height="35">
				<td nowrap colspan="4"><font color="#F00">“手机号等同用户名，修改电话用户名也随即修改”</font></td>
			</tr>
		</table>
	</form>
</div>

