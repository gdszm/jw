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
<script>
	var d=new Date();
	var rq=d.getFullYear() + "-" + (d.getMonth()+1) +"-" + d.getDate();
	$('#rq').html(rq);
</script>
<div align="center" style="padding: 5px;overflow: hidden;">
	<form id="addForm" method="post">
		<input type="hidden" id="proposalId" name="proposalId" />
		<table id="addtable" cellpadding="5" cellspacing="5" align="center" border="1" bordercolor="#003366" style="border-collapse: collapse;">				
			<tr height="30">
				<td colspan="4" height="31" align="center" valign="middle"  bgcolor="#a9cfd5">提案信息</td>
			</tr>
			<tr height="30">
				<td nowrap width="62">提案编号:</td>
				<td width="200"><label id="proposalCode"></label></td>
				<td nowrap width="62">届&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;次:</td>
				<td width="200">${sessionSeco.secondayName}</td>
			</tr>
			<tr height="30">
				<td nowrap>案&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;由:</td>
				<td colspan="3"><label id="title"></label></td>
			</tr>
			<tr height="30">
				<td nowrap>提案类型:</td>
				<td><label id="proposalTypeName"></label></td>
				<td nowrap width="90">提案人:</td>
				<td><label id="fistProposaler"></label></td>
			</tr>
			<tr height="30">
				<td nowrap>附议人:</td>
				<td>${sessionInfo.realName}</td>
				<td nowrap>附议日期:</TD>
				<td wstyle="padding:6px"><label id="rq"></label></td>
			</tr>
			<tr height="30">
				<td colspan="4" height="31" align="center" valign="middle"  bgcolor="#a9cfd5">建&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;议</td>
			</tr>
			<tr height="30">
				<td colspan="4"><textarea name="remark" style="width:615px;height:80px;padding:2px"></textarea></td>
			</tr>
		</table>
	</form>
</div>
