<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
	<center>
	<form id="assignForm" class="form-horizontal"  method="post">
	<input type="hidden" id="ids" name="ids"/>
	<table id="assignTable">	
		<tr height="25">
			<td nowrap>正式交办日期：</td>
			<td><input type="text"  class="easyui-datebox" data-options="required:true" name="submitTime" style="width:180px;padding:2px"></td>
		</tr>			
		<tr height="25">
			<td nowrap>要求办结日期：</td>
			<td><input type="text" class="easyui-datebox" data-options="required:true" name="demandEnddate" style="width:180px;padding:2px"></td>
		</tr>
		
	</table>
	</form>
	</center>
</div>