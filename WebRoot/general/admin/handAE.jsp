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
	$('#addForm input[name=committeemanOpinion]').val(${handle.committeemanOpinion}); 
	$('#addForm input[name=factEnddate]').val('${handle.factEnddate}');
	
	
	var op='${handle.operator}';
	if(op.length>0){
		$('#addForm input[name=operator]').val('${handle.operator}');
	}else{
		$('#addForm input[name=operator]').val('${handle.companyName}');
	}
	 //办复建议
    function info(handleReplyId){
    	 p = dj.dialog({
				title : '办复建议',
				href : '${pageContext.request.contextPath}/hand!adviceInfo.do?handleReplyId='+handleReplyId,
				width : 600,
				height : 400,
				iconCls:'icon-save',
				buttons : [ {
						text: '关闭', 
						iconCls:'icon-cancel',
						handler: function() { 
							p.dialog('close'); 
						} 
					}]
		});
    }
</script>

<div align="center" style="padding: 5px;overflow: hidden;">
	<form id="addForm" method="post">
		<input type="hidden" name="handleReplyId" value="${handle.handleReplyId}" />
		<table width="70%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td>
								<table id="addtable" class="tableborder">				
									<tr height="45"  align="center">
										<td colspan="4" height="31" align="center" valign="middle"  class="tablemain">提案信息</td>
									</tr>
									<tr height="30">
										<td nowrap width="90" class="tablelabel">提案编号：</td>
										<td width="300" class="tablecontent">${handle.proposalCode}</td>
										<td nowrap width="90" class="tablelabel">届&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;次：</td>
										<td width="300" class="tablecontent">${handle.secondayName}</td>
									</tr>
									<tr height="30">
										<td nowrap class="tablelabel">案&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;由：</td>
										<td colspan="3" class="tablecontent">${handle.title}</td>
									</tr>
									<tr height="30">
										<td nowrap width="90" class="tablelabel">第一提案人：</td>
										<td class="tablecontent">${handle.fistProposaler}</td>
										<td nowrap class="tablelabel">提案类型：</td>
										<td class="tablecontent">${handle.proposalTypeName}</td>
									</tr>
									
									<tr height="30">
										<td nowrap width="90" class="tablelabel">要求办结日期：</td>
										<td colspan="2" class="tablecontent">${handle.demandEnddate}</td>
										<td class="tablecontent" align="right"><a class="easyui-linkbutton" style="background:#ddf5f9" href="javascript:void(0)" onclick="info(${handle.handleReplyId});">建议详情</a></td>
									</tr>
									<tr height="30">
										<td colspan="4" align="center" valign="middle" class="tablemain">办复信息</td>
									</tr>
									<tr height="30">
										<td nowrap class="tablelabel">办理单位：</td>
										<td colspan="3" class="tablecontent">${handle.companyName}</td>
									</tr>
									<tr height="30">
										<td nowrap class="tablelabel">办理类型：</td>
										<td class="tablecontent">${handle.handleTypeName}</td>
										<td nowrap class="tablelabel">沟通方式：</td>
										<td class="tablecontent"><input name="answerMode" data-options="required:true,editable:false,validType:'sel'" style="width:300px;padding:2px" /></td>
									</tr>
									<tr height="30">
										<td nowrap class="tablelabel">解决程度：</td>
										<td class="tablecontent"><input name="solveHow" data-options="required:true,editable:false,validType:'sel'" style="width:300px;padding:2px" /></td>
										<!--  <td nowrap class="tablelabel">是否落实：</td>
										<td colspan="3" class="tablecontent"><input name="carryoutFlg" data-options="required:true,editable:false,validType:'sel'" style="width:300px;padding:2px" /></td>-->
									
										<td nowrap class="tablelabel">提案人意见：</td>
										<td class="tablecontent"><input name="committeemanOpinion" style="width:300px;padding:2px" /></td>
									</tr>
									<tr height="30">
										<td nowrap class="tablelabel">实际办结日期：</td>
										<td colspan="3" class="tablecontent"><input name="factEnddate"  class="easyui-datebox" style="width:300px;padding:2px"/></td>
									</tr>
									<!-- <tr height="30">
										<td nowrap>意见说明：</td>
										<td colspan="3"><textarea name="opinionExplain" style="width:614px;height:60px;padding:2px">${handle.opinionExplain}</textarea></td>
									</tr> -->
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