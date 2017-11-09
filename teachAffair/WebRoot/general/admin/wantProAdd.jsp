<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div align="center" style="padding: 5px;overflow: hidden;">
	<form id="addForm" method="post">
		<input type="hidden" name="code"/>
		<table  id="addtable"cellpadding="1" cellspacing="0"　 border="1"   align="center" style="border-collapse: collapse">
			<TR height="25">
				<TD colspan="4" height="31" align="center" valign="middle" bgcolor="#a9cfd5">提案信息</TD>
			</TR>
			<TR height="25">
				<TD nowrap width="50"  bgcolor="#ddf5f9" style="padding:6px">提案编号:</TD>
				<TD width="260" style="padding:6px">0025</td>
				<TD  bgcolor="#ddf5f9" nowrap width="50" style="padding:6px">届&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;次:</TD>
				<TD  width="260" style="padding:6px">1101</TD>
			</TR>
			<TR height="25">
				<TD  bgcolor="#ddf5f9" nowrap style="padding:6px">提案题目:</TD>
				<TD  colspan="3" style="padding:6px">关于在城乡社区建设家庭教育站点的提案</TD>
			</TR>
			<TR height="25">
				<TD  bgcolor="#ddf5f9" nowrap style="padding:6px">提案类型:</TD>
				<TD  width="260" style="padding:6px">委员提案</td>
				<TD  bgcolor="#ddf5f9" nowrap style="padding:6px">第一提案人:</TD>
				<TD  width="260" style="padding:6px">同力</TD>
			</TR>	
			<TR height="25">
				<TD  bgcolor="#ddf5f9" nowrap style="padding:6px">附议者:</TD>
				<TD  style="padding:6px">test</TD>
				<TD  bgcolor="#ddf5f9" nowrap style="padding:6px">附议日期:</TD>
				<TD  style="padding:6px">2015-01-28</TD>										
			</TR>	
			<TR height="25">
				<TD colspan="4" height="31" align="center" valign="middle" bgcolor="#a9cfd5">建议</TD>
			</TR>
			</TR>
			<TR height="25">
				<TD colspan="4" align="center" valign="middle"><textarea rows="10" style="width:99%;"></textarea></TD>
			</TR>
		</table>
	</form>
</div>
