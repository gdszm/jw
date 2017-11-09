<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div align="center" style="padding: 5px;overflow: hidden;">
	<form id="addForm" method="post">
		<input type="hidden" name="code" id="codeHideId" />
		<input type="hidden" id="oldtelephone" name="oldtelephone"/>
		<table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
		      <tr>
		        <td><table class="tableborder" id="formTable" sizset="false" >
		           	<tbody sizset="false" >		
						<tr height="30">
							<td nowrap class="tablelabel">团体/专委会：</td>
							<td align="left" class="tablecontent"><input type="text" name="name"  class="easyui-validatebox" data-options="required:true,missingMessage:'请填团体/专委会'" style="width:300px;padding:2px" /></td>
							<td nowrap class="tablelabel">分组：</td>
							<td align="left" class="tablecontent" ><input name="groupCode"  style="width:150px;padding:2px" /></td>
						</tr>
						<tr height="30" class="tablelabel">
							<td nowrap class="tablelabel">帐号：</td>
							<td align="left" class="tablecontent" colspan="3" ><input id="usernameId" type="text" name="username"  class="easyui-validatebox" data-options="required:true,missingMessage:'请输入登录帐号'" style="width:556px;padding:2px" /></td>
						</tr>
						<tr height="30" class="tablelabel">
							<td nowrap class="tablelabel">联系人：</td>
							<td align="left" class="tablecontent" colspan="3" ><input type="text" name="linkMan" style="width:556px;padding:2px" /></td>
						</tr>
						<tr height="30"  >
							<td nowrap class="tablelabel">电子邮箱：</td>
							<td align="left" class="tablecontent"><input type="text" name="email" style="width:300px;padding:2px" /></td>
							<td nowrap class="tablelabel">民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族：</td>
							<td align="left" class="tablecontent"><input name="nation" style="width:150px;padding:2px" /></td>
						</tr>
						<tr height="30" >
							<td nowrap class="tablelabel">手&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机：</td>
							<td align="left" class="tablecontent" ><input type="text" name="telephone" class="easyui-validatebox"  data-options="required:true,validType:'length[7,12]',missingMessage:'请填电话号码\n登录名默认与电话号码相同'" style="width:300px;padding:2px"></td>
							<td nowrap class="tablelabel">固定电话：</td>
							<td align="left" class="tablecontent"><input type="text" name="comparyPhone" class="easyui-validatebox"  style="width:150px;padding:2px"></td>
						</tr>
						<tr height="30" >
							<td nowrap class="tablelabel">通讯地址：</td>
							<td align="left" class="tablecontent"><input type="text" name="comparyAddress" style="width:300px;padding:2px" /></td>
							<td nowrap class="tablelabel">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编：</td>
							<td align="left" class="tablecontent"><input type="text" name="comparyPost" style="width:150px;padding:2px" /></td>
						</tr>
						<tr height="30">
							<td nowrap class="tablelabel">有效届次：</td>
							<td align="left" class="tablecontent"><input name="secondaryCode" data-options="required:true,editable:false,validType:'sel'" multiple="true" style="width:300px;padding:2px" /></td>
							<td nowrap class="tablelabel">状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：</td>
							<td align="left" class="tablecontent"><input name="status" data-options="required:true,editable:false,validType:'sel'" style="width:150px;padding:2px" /></td>
						</tr>
					</tbody>
					</table>
				</td>
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