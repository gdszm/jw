<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
var editor;
$(document).ready(function() {
	$('#EditForm input[name=type]').combobox({
		url : '${pageContext.request.contextPath}/dic!combox.do?ctype=GTYPE',
		valueField:'cvalue', 
		panelHeight:'100',
        textField:'ckey',
        required:true,
        validType:'sel'
	});
});

$.extend($.fn.validatebox.defaults.rules, {  
	sel: {  
		validator: function(value){ 
			return value != '请选择...';  
		},  
		message: '此项必须选择！'  
	}  
}); 
</script>
<div align="center" style="padding: 5px;overflow: hidden;">
	<form id="EditForm" enctype=multipart/form-data method="post" >
	<input type="hidden" name="gid"/>
		<table class="tableForm">
			<tr>
				<td style="height: 50px;">组名称:</td>
				<td><input type="text" name="gname" style="height: 20px;"/></td>
			</tr>
			<tr>
				<td style="height: 50px;">组类型:</td>
				<td><input type="text" id="type" name="type" style="height: 20px;"/></td>
			</tr>
		</table>
	</form>
</div>