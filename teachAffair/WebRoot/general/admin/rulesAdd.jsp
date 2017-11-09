<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div align="center" style="padding: 5px;overflow: hidden;">
	<form id="addForm" method="post">
		<table id="addTable">				
			<tr height="30">
				<td nowrap>计分类型：</td>
				<td align="left" colspan="3" > <input type="text" name="typeid" data-options="required:true,editable:false,validType:'sel'" style="width:120px;padding:2px"></td>
			</tr>
			<tr height="30">
				<td nowrap>计分名称：</td>
				<td align="left" colspan="3" ><input name="name" class="easyui-validatebox" data-options="required:true,missingMessage:'请填写计分名称'" style="width:240px;padding:2px" /></td>
			</tr>
			<tr height="30">
				<td nowrap>分值：</td>
				<td align="left"><input name="score" type="text" class="easyui-numberbox" data-options="min:0,max:999" style="width:100px;padding:2px"/></td>
				<td nowrap>状态：</td>
				<td align="left"><input name="status" data-options="required:true,editable:false,validType:'sel'" style="width:100px;padding:2px" /></td>
			</tr>
			<tr height="30">
				<td nowrap>备注：</td>
				<td align="left" colspan="3" ><textarea name="memo" cols="26",rows="2"> </textarea></td>
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