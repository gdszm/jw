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
<script type="text/javascript">
    var editor = new baidu.editor.ui.Editor({ 
        textarea:'reply' 
    }); 
    editor.render("editor"); 
	var d=new Date();
	var rq=d.getFullYear() + "-" + (d.getMonth()+1) +"-" + d.getDate();
	$('#rq').html(rq);
	$('#addForm input[name=answerMode]').val(${handle.answerMode});
	$('#addForm input[name=solveHow]').val(${handle.solveHow}); 
	$('#addForm input[name=carryoutFlg]').val(${handle.carryoutFlg}); 
	$('#addForm input[name=factEnddate]').val('${handle.factEnddate}');
	
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
		<table width="70%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td>
								<table id="addtable" class="tableborder">				
									<tr height="45"  align="center">
										<td colspan="4" height="31" align="center" valign="middle"  class="tablemain">社情民意信息</td>
									</tr>
									<tr height="30">
										<td nowrap width="90" class="tablelabel">社情民意编号：</td>
										<td width="300" class="tablecontent">${handle.poll.pollCode}</td>
										<td nowrap width="90" class="tablelabel">届&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;次：</td>
										<td width="300" class="tablecontent">${handle.poll.tsecondary.secondayName}</td>
									</tr>
									<tr height="30">
										<td nowrap class="tablelabel">社情民意名称：</td>
										<td colspan="3" class="tablecontent">${handle.poll.title}</td>
									</tr>
									<tr height="30">
										<td nowrap width="90" class="tablelabel">社情民意提交人：</td>
										<td class="tablecontent">${handle.poll.createManName}</td>
										<td nowrap class="tablelabel">社情民意类型：</td>
										<td class="tablecontent">${handle.poll.pollTypeName}</td>
									</tr>
									<tr height="30">
										<td colspan="4" align="center" valign="middle" class="tablemain">办复信息</td>
									</tr>
									<tr height="30">
										<td nowrap class="tablelabel">办理单位：</td>
										<td colspan="3" class="tablecontent">${handle.comp.companyName}</td>
									</tr>
									<tr height="30">
										<td nowrap class="tablelabel">办理类型：</td>
										<td class="tablecontent">${handle.poll.handleTypeName}</td>
										<td nowrap class="tablelabel">办结日期：</td>
										<td colspan="3" class="tablecontent"><input name="factEnddate"  class="easyui-datebox" style="width:300px;padding:2px"/></td>
<%--										<td nowrap class="tablelabel">沟通方式：</td>--%>
<%--										<td class="tablecontent"><input name="answerMode" data-options="required:true,editable:false,validType:'sel'" style="width:300px;padding:2px" /></td>--%>
									</tr>
<%--									<tr height="30">--%>
<%--										<td nowrap class="tablelabel">解决程度：</td>--%>
<%--										<td class="tablecontent"><input name="solveHow" data-options="required:true,editable:false,validType:'sel'" style="width:300px;padding:2px" /></td>--%>
<%--										<td nowrap class="tablelabel">是否落实：</td>--%>
<%--										<td colspan="3" class="tablecontent"><input name="carryoutFlg" data-options="required:true,editable:false,validType:'sel'" style="width:300px;padding:2px" /></td>--%>
<%--									<td nowrap class="tablelabel">提案人意见：</td>--%>
<%--									<td class="tablecontent"><input name="committeemanOpinion" style="width:300px;padding:2px" /></td>--%>
<%--									</tr>--%>
<%--									<tr height="30">--%>
<%--										<td nowrap class="tablelabel">办结日期：</td>--%>
<%--										<td colspan="3" class="tablecontent"><input name="factEnddate"  class="easyui-datebox" style="width:300px;padding:2px"/></td>--%>
<%--									</tr>--%>
									<tr height="30">
										<td nowrap class="tablelabel">办复建议：</td>
										<td colspan="3" class="tablecontent">
											<textarea id="rebutInfoId" name="rebutInfo" class="textarea easyui-validatebox" rows="5" style="width:99%;padding:2px">${handle.rebutInfo}</textarea>
										</td>
									</tr>
<%--									<tr height="30">--%>
<%--										<td nowrap>意见说明：</td>--%>
<%--										<td colspan="3"><textarea name="opinionExplain" style="width:614px;height:60px;padding:2px">${handle.opinionExplain}</textarea></td>--%>
<%--									</tr>--%>
									<tr height="30">
										<td colspan="4" class="tablespecial"><strong>【重要提示】如答复报告内容较多，请先在记事本或word中提前录好保存，然后再复制到内容框中，以免在线超时，引起信息丢失！</strong></td>
									</tr>
									<tr height="30">
										<td colspan="4" align="center" class="tablecontent"><script id="editor" type="text/plain" style="width:99%;height:300px">${handle.reply}</script></td>
									</tr>
									<tr height="30">
										<td nowrap class="tablelabel">操作人员：</td>
										<td  class="tablecontent"><input name="operator" style="width:99%;padding:2px" /></td>
										<td nowrap class="tablelabel">操作时间：</TD>
										<td class="tablecontent"><label id="rq"></label></td>
									</tr>
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