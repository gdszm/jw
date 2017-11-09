<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div align="center" style="padding: 5px;overflow: hidden;">
	<form id="addForm" method="post">
		<input type="hidden" name="companyId"/>
		<table id="addtable">				
			<tr height="30">
				<td nowrap>单位名称：</td>
				<td align="left"><input type="text" name="companyName" class="easyui-validatebox" data-options="required:true" style="width:280px;padding:2px" /></td>
			</tr>
			<tr height="30">
				<td nowrap>单位简称：</td>
				<td align="left"><input type="text" name="shortName" class="easyui-validatebox" data-options="required:true,missingMessage:'登录名默认与之相同'" style="width:280px;padding:2px" /></td>
			</tr>
			<tr height="30">
				<td nowrap>单位类型：</td>
				<td align="left"><input name="companyType" data-options="required:true,editable:false,validType:'sel'" style="width:285px;padding:2px" /></td>
			</tr>
			<tr height="30">
				<td nowrap>单位地址：</td>
				<td align="left"><input type="text" name="companyAddress" style="width:280px;padding:2px" /></td>
			</tr>
			<tr height="30">
				<td nowrap>单位邮编：</td>
				<td align="left"><input type="text" name="companyPost" style="width:280px;padding:2px" /></td>
			</tr>
			<tr height="30">
				<td nowrap>联系人：</td>
				<td align="left"><input type="text" name="linkman" style="width:280px;padding:2px" /></td>
			</tr>
			<tr height="30">
				<td nowrap>联系电话：</td>
				<td align="left"> <input type="text" id="phone" name="phone" class="easyui-validatebox" data-options="required:true,validType:'length[7,12]'" style="width:280px;padding:2px" /></td>
			</tr>
			<tr height="30">
				<td nowrap>状态：</td>
				<td align="left"><input name="status" data-options="required:true,editable:false,validType:'sel'" style="width:286px;padding:2px" /></td>
			</tr>
			<tr height="30">
				<td nowrap>备注：</td>
				<td align="left"><textarea name="remark" rows="3" cols="32"></textarea> </td>
			</tr>
		</table>
	</form>
</div>


<script type="text/javascript">
	$.extend($.fn.validatebox.defaults.rules, {  
		sel: {  
			validator: function(value){ 
				return value != '请选择...';  
			},  
			message: '此项必须选择！'  
		}  
	});  
</script>