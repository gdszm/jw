<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		initCombobox('satiForm','satisfyFlg','${pageContext.request.contextPath}/dic!combox.do?ctype=WYYJ');
		initCombobox('addForm','solveHow','${pageContext.request.contextPath}/dic!combox.do?ctype=JJCD');
		initCombobox('addForm','carryoutFlg','${pageContext.request.contextPath}/dic!combox.do?ctype=YESNO');
		initCombobox('addForm','committeemanOpinion','${pageContext.request.contextPath}/dic!combox.do?ctype=WYYJ');
		$('#addForm input[name=solveHow]').val(${prop.solveHow}); 
		$('#addForm input[name=carryoutFlg]').val(${prop.carryoutFlg}); 
		$('#addForm input[name=committeemanOpinion]').val(${prop.committeemanOpinion}); 
});
	
	function openWin(handleReplyId,satisfyFlg,satisfyInfo){
		if(handleReplyId)$('#satiForm input[name=handleReplyId]').val(handleReplyId);
		if(satisfyFlg)$('#satisfyFlg').combobox('setValue',satisfyFlg);
		if(satisfyInfo)$('#satisfyInfo').val(satisfyInfo);
		$('#win_satisfy').window('open');
	}
	
	function setSati(){
		$('#satiForm').form('submit', {
			url : '${pageContext.request.contextPath}/hand!update.do',
			success : function(d) {
				var json = $.parseJSON(d);
				if (json.success) {
					dj.messagerShow({
						msg : json.msg,
						title : '提示'
					});
					$('#win_satisfy').window('close');
					
					window.location.reload();
				}
			}
		});
	}
	
	function setOpt(proposalId){
		$('#addForm').form('submit', {
			url : '${pageContext.request.contextPath}/prop!setOpt.do?proposalId=' + proposalId,
			success : function(d) {
				var json = $.parseJSON(d);
				if (json.success) {
					alert(json.msg);
					window.close();
				}
			}
		});
	}
</script>
<style type="text/css">
	#addtable td{ 
		padding:5px;
	} 
</style>

</head>

<body class="easyui-layout">
<DIV class="easyui-layout" data-options="fit:true"
			class="cs-home-remark">
			<div data-options="region:'center',border:false"
				style="padding: 10px; border: 1px solid #ccc;"
				class="cs-home-remark">
				<center>
					<table width="70%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td align="center" class="tablecontent" >
								<table id="addtable" class="tableborder">				
									<tr height="45" >
										<td height="45" colspan="4" align="center" nowrap="nowrap"  class="tablemain" >
											 <c:if test="${prop.meetingFlg=='0'}">
									   			<div align="center"><input type="hidden" name="secondaryId" value="${sessionSeco.secondaryId}"/><strong>政协巴彦淖尔市第${fn:substring( sessionSeco.secondayName,0,fn:indexOf(sessionSeco.secondayName,'届')+1)}委员会第${fn:substring(sessionSeco.secondayName,fn:indexOf(sessionSeco.secondayName,'届')+1,fn:length(sessionSeco.secondayName))}会议提案答复报告</strong></div>
									   		</c:if>
									   		<c:if test="${prop.meetingFlg=='1'}">
									   			<div align="center"><input type="hidden" name="secondaryId" value="${sessionSeco.secondaryId}"/><strong>政协巴彦淖尔市第${fn:substring( sessionSeco.secondayName,0,fn:indexOf(sessionSeco.secondayName,'届')+1)}委员会${sessionSeco.year}年度提案答复报告</strong></div>
									   		</c:if>
										</td>
									</tr>
									<tr height="30" >
										<td nowrap class="tablelabel" width="90">提案编号:</td>
										<td width="300" align="left" class="tablecontent">${prop.proposalCode}</td>
										<td nowrap class="tablelabel" width="90">第一提案人:</td>
										<td width="300" align="left" class="tablecontent">${prop.fistProposaler}</td>
									</tr>
									<tr height="30">
										<td nowrap class="tablelabel" width="90">案&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;由:</td>
										<td colspan="3" align="left" class="tablecontent">${prop.title}</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td width="100%" align="center" class="tablecontent" >
								<div class="easyui-panel" title="详细"  data-options="border:false,collapsible:true,collapsed:true" >
									<table id="addtable" class="tableborder" >				
										<tr height="30">
										<td nowrap width="90" class="tablelabel">提案类型:</td>
										<td colspan="3" class="tablecontent">${prop.proposalTypeName}</td>
										</tr>
										<tr height="30">
											<td nowrap class="tablelabel" width="90">是否立案:</td>
											<td width="300" class="tablecontent">${prop.fileMethodName}</td>
											<td nowrap class="tablelabel" width="90">立案时间:</td>
											<td width="300" class="tablecontent">${prop.checkTime}</td>
										</tr>
										<tr height="30">
											<td nowrap class="tablelabel">办理方式:</td>
											<td class="tablecontent">${prop.handleTypeName}</td>
											<td nowrap class="tablelabel">要求办结日期:</td>
											<td class="tablecontent">${prop.demandEnddate}</td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
						<tr>
							<td width="100%" align="center" class="tablecontent" >
								<div id="tabs" class="easyui-tabs" style="width:1000px; height: auto">
									<c:forEach var="h" items="${list}">
										<div title="${h.companyName}" style="padding: 3px">
											<table id="addtable" class="tableborder" >	
												<tr height="30">
													<td nowrap class="tablelabel" width="90">沟通方式：</td>
													<td width="260" class="tablecontent">${h.answerModeName}</td>
													<td nowrap class="tablelabel">提案人意见：</td>
													<td class="tablecontent">${h.commOpName}</td>
												</tr>
												<tr height="30">
													<td nowrap  width="90" class="tablelabel">解决程度：</td>
													<td class="tablecontent">${h.solveHowName}</td>
													<!--<td nowrap width="90" class="tablelabel">是否落实：</td>
													<td class="tablecontent">${h.carryoutFlgName}</td>  
												</tr>
												<tr height="30">-->
													<td nowrap class="tablelabel">实际办结日期：</td>
													<td class="tablecontent">${h.factEnddate}</td>
												</tr>
												<!--<tr height="30">
													<td nowrap class="tablelabel">意见说明：</td>
													<td colspan="3" >${h.opinionExplain}</td>
												</tr> -->
												<tr height="30" class="tablemain" align="center">
													<td colspan="4">办复信息</td>
												</tr>
												<tr height="30">
													<td colspan="4" height="300" class="tablecontent" ><div style="height:295px;overflow:auto;">${h.reply}</div></td>
												</tr>
												<!-- <tr height="30">
													<td class="tablelabel">满意度</td>
													<td colspan="3" >${h.satisfyFlgName}</td>
												</tr>
												<c:if test=""></c:if>
												<tr height="30">
													<td class="tablelabel">办复意见</td>
													<td colspan="3" height="100" ><div style="height:100px;overflow:auto;">${h.satisfyInfo}</div></td>
												</tr> -->
												<tr height="35">
													<td align="right" colspan="4"  class="tablemain">
														<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="openWin(${h.handleReplyId},'${h.satisfyFlg}','${h.satisfyInfo}');">办复意见</a>
													</td>
												</tr>
											</table>
										</div>
									</c:forEach>
								</div>
							</td>
						</tr>
					</table>
				</center>
		</div>
</DIV>

<div id="win_satisfy" class="easyui-window" data-options="title:'满意度设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:600px;height:300px;padding:5px;">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
			<center>
			<form id="satiForm" method="post">
			<input type="hidden" name="handleReplyId" value="" />
			<table id="myta_queryTable">				
				<tr height="25">
					<td nowrap>满意度：</td>
					<td><input type="text" id="satisfyFlg" name="satisfyFlg" style="width:150px;padding:2px;" data-options="required:true,editable:false"></td>
				</tr>
				<tr height="25">
					<td nowrap>建议：</td>
					<td><textarea id="satisfyInfo" name="satisfyInfo" cols="50" rows="6"></textarea></td>
				</tr>
			</table>
			</form>
			</center>
		</div>
		<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;height:40px">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="setSati()">设置</a>
			<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#win_satisfy').window('close');">取消</a>
		</div>
	</div>
</div>

<!-- <div data-options="title:'提案总体评价',region:'south',border:true" style="text-align:center;padding:20px 0;height:120px;">
	<form id="addForm" method="post">
		解决程度：<input name="solveHow" data-options="editable:false"  style="width:240px;padding:2px" />　
		是否落实：<input name="carryoutFlg" data-options="editable:false"  style="width:80px;padding:2px" />　
		提案人意见：<input name="committeemanOpinion" data-options="editable:false"  style="width:80px;padding:2px" />　　
		<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)" onclick="setOpt(${prop.proposalId})">设置</a>　
		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:window.close()">关闭</a>
	</form>
</div> -->

