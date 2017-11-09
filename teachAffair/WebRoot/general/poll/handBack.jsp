<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
	td{ 
		padding:5px;
	} 
</style>
<script type="text/javascript">
	var d=new Date();
	var rq=d.getFullYear() + "-" + (d.getMonth()+1) +"-" + d.getDate();
	$('#rq').html(rq);
	var op='${handle.operator}';
	if(op.length>0){
		$('#addForm input[name=operator]').val('${handle.operator}');
	}else{
		$('#addForm input[name=operator]').val('${handle.comp.companyName}');
	}
</script>

<div align="center" style="padding: 5px;overflow: hidden;">
	<form id="addForm" method="post">
		<input type="hidden" name="handleId" value="${handle.handleId}" />
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		      <tr>
		        <td><table class="tableborder" id="formTable" sizset="false" >
		           <tbody sizset="false" >
		            <tr height="25">
		              <td height="25" colspan="4" align="middle" nowrap="nowrap"  class="tablemain">
		                <div align="center" ><strong>社情民意信息</strong></div>
		              </td>
		            </tr>
					<tr height="25">
						<td nowrap width="80" class="tablelabel">社情民意编号:</td>
						<td width="250" class="tablecontent">${handle.poll.pollCode}</td>
						<td nowrap width="80" class="tablelabel">届&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;次:</td>
						<td width="250" class="tablecontent">${handle.poll.tsecondary.secondayName}</td>
					</tr>
					<tr height="25">
						<td nowrap class="tablelabel">社情民意名称：</td>
						<td colspan="3" class="tablecontent">${handle.poll.title}</td>
					</tr>
					<tr height="25">
						<td nowrap class="tablelabel">办理单位:</td>
						<td class="tablecontent">${handle.comp.companyName}</td>
						<td nowrap class="tablelabel">办理类型:</td>
						<td class="tablecontent">${handle.poll.handleTypeName}</td>
					</tr>
					<tr height="25">
						<td nowrap colspan="4" align="center" valign="middle"  class="tablemain">申退理由</td>
					</tr>
					<tr height="25">
						<td colspan="4" class="tablecontent">
							<textarea id="opinionExplainId" name="opinionExplain" class="textarea easyui-validatebox"  style="width:550px;padding:2px" rows="4">${handle.opinionExplain}</textarea>
						</td>
					</tr>
					<tr height="25">
						<td nowrap class="tablelabel">操作人员:</td>
						<td class="tablecontent"><input type="text" name="operator" style="width:240px;padding:2px" /></td>
						<td nowrap class="tablelabel">操作时间:</TD>
						<td class="tablecontent"><label id="rq"></label></td>
					</tr>
				</tbody>
				</table>
			</td>
		</tr>
	</table>
	</form>
</div>
