<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div align="center" style="padding: 5px;overflow: hidden;">
	<form id="addForm" method="post">
		<input type="hidden" name="secondaryId"/>
		<table id="addTable">				
			<tr height="30">
				<td nowrap>届次名称：</td>
				<td align="left" colspan="3"> <input type="text" name="secondayName"  class="easyui-validatebox" data-options="required:true,missingMessage:'请填写届次名称'" style="width:280px;padding:2px"></td>
			</tr>
			<tr height="30">
				<td nowrap>对应年份：</td>
				<td align="left"><input name="year" data-options="required:true,editable:false,validType:'sel'" style="width:80px;padding:2px" />年</td>
				<td nowrap>提案所属：</td>
				<td align="left"><input name="period" data-options="required:true,editable:false,validType:'sel'" style="width:80px;padding:2px" /></td>
			</tr>
			<tr height="30">
				<td nowrap>编号前缀：</td>
				<td align="left"><input name="ext"  class="easyui-validatebox" data-options="required:true,missingMessage:'例如：九届三次则输入 93'" style="width:80px;padding:2px" /></td>
				<td nowrap>启用状态：</td>
				<td align="left"><input name="status" data-options="required:true,editable:false,validType:'sel'" style="width:80px;padding:2px" /></td>
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