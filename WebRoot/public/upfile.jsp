<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>

</script>
<div data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc;">
	<form METHOD="POST" id="uploadForm" ENCTYPE="multipart/form-data">
	<input type="hidden"  name="uploadDir" id="uploadDir" value="">
	<DIV align=center>
	<table width=100%><tr><td nowrap>
	<FIELDSET align=left>
	<LEGEND align=left><font size="2">输入图片参数</font></LEGEND>
	<table align=center >
	<tr><td nowrap>
	<font size="2">文件地址：</font><INPUT TYPE="FILE" id="file" capture="camera" accept="image/*,video/*,application/msword,application/msexcel,application/pdf"  NAME="file" SIZE="26" ></td></tr></table>
	</FIELDSET></td></tr>
	<tr>
	<td align="center">
	</td>
	</tr>
	</table>
	</DIV>
	</form>
</div>  
