<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
	#addtable td{ 
		padding:5px;
	} 
</style>
<div align="center" style="padding: 5px;overflow: hidden;">
	<form id="addForm" method="post">
		<table id="addtable" cellpadding="5" width="100%" cellspacing="5" align="center" border="1" bordercolor="#003366" style="border-collapse: collapse;">				
			<tr height="30">
				<td nowrap>团体/专委会：</td>
				<td align="left" >${comm.name }</td>
				<td nowrap>分组：</td>
				<td align="left">${comm.groupName}</td>
			</tr>
			<tr height="30">
				<td nowrap>联系人：</td>
				<td align="left" colspan="3">${comm.linkMan}</td>
			</tr>
			<tr height="30">
				<td nowrap>电子邮箱：</td>
				<td align="left" colspan="3">${comm.email}</td>
			</tr>
			<tr height="30">
				<td nowrap>手&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机：</td>
				<td align="left" >${comm.telephone}</td>
				<td nowrap>固定电话：</td>
				<td align="left" >${comm.telephone}</td>
			</tr>
			<tr height="30">
				<td nowrap>通讯地址：</td>
				<td align="left" >${comm.comparyAddress}</td>
				<td nowrap>邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编：</td>
				<td align="left">${comm.comparyPost}</td>
			</tr>
			<tr height="30">
				<td nowrap>状态：</td>
				<td align="left">${comm.statusName}</td>
				<td nowrap>有效届次：</td>
				<td align="left">${comm.secondaryName}</td>
			</tr>
		</table>
	</form>
</div>
