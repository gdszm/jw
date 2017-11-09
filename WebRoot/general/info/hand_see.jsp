<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		initCombobox('addForm','solveHow','${pageContext.request.contextPath}/dic!combox.do?ctype=JJCD');
		initCombobox('addForm','carryoutFlg','${pageContext.request.contextPath}/dic!combox.do?ctype=YESNO');
		initCombobox('addForm','committeemanOpinion','${pageContext.request.contextPath}/dic!combox.do?ctype=WYYJ');
		$('#addForm input[name=solveHow]').val(${prop.solveHow}); 
		$('#addForm input[name=carryoutFlg]').val(${prop.carryoutFlg}); 
		$('#addForm input[name=committeemanOpinion]').val(${prop.committeemanOpinion}); 
	});
	
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
<div data-options="region:'center',border:false" align="center" style="padding: 5px;">
		<div data-options="region:'center',border:false"
				style="padding: 10px; border: 1px solid #ccc;"
				class="cs-home-remark">
				<center>
					<table width="70%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td>
								<table id="addtable" class="tableborder">				
									<tr height="45"  align="center">
										<td height="45" colspan="4" align="center" nowrap="nowrap"  class="tablemain" >
											<c:if test="${prop.meetingFlg=='0'}">
									   			<div align="center"><input type="hidden" name="secondaryId" value="${sessionSeco.secondaryId}"/><strong>政协巴彦淖尔市第${fn:substring( sessionSeco.secondayName,0,fn:indexOf(sessionSeco.secondayName,'届')+1)}委员会第${fn:substring(sessionSeco.secondayName,fn:indexOf(sessionSeco.secondayName,'届')+1,fn:length(sessionSeco.secondayName))}会议提案答复报告</strong></div>
									   		</c:if>
									   		<c:if test="${prop.meetingFlg=='1'}">
									   			<div align="center"><input type="hidden" name="secondaryId" value="${sessionSeco.secondaryId}"/><strong>政协巴彦淖尔市第${fn:substring( sessionSeco.secondayName,0,fn:indexOf(sessionSeco.secondayName,'届')+1)}委员会${sessionSeco.year}年度提案答复报告</strong></div>
									   		</c:if>
										</td>
									</tr>
									<tr height="30">
										<td width="100px" align="middle" nowrap="nowrap" class="tablelabel">提案编号:</td>
										<td width="260" class="tablecontent">${prop.proposalCode}</td>
										<td width="100px" align="middle" nowrap="nowrap" class="tablelabel">届&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;次:</td>
										<td width="260" class="tablecontent">${prop.secondayName}</td>
									</tr>
									<tr height="30">
										<td width="100px" align="middle" nowrap="nowrap" class="tablelabel">案&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;由:</td>
										<td colspan="3" class="tablecontent">${prop.title}</td>
									</tr>
									<tr height="30">
										<td width="100px" align="middle" nowrap="nowrap" class="tablelabel">提案类型:</td>
										<td class="tablecontent">${prop.proposalTypeName}</td>
										<td width="100px" align="middle" nowrap="nowrap" class="tablelabel" >第一提案人:</td>
										<td class="tablecontent">${prop.fistProposaler}</td>
									</tr>
									<tr height="30">
										<td width="100px" align="middle" nowrap="nowrap" class="tablelabel">是否立案:</td>
										<td  class="tablecontent">${prop.fileMethodName}</td>
										<td width="100px" align="middle" nowrap="nowrap" class="tablelabel" >立案时间:</td>
										<td class="tablecontent">${prop.checkTime}</td>
									</tr>
									<tr height="30">
										<td width="100px" align="middle" nowrap="nowrap" class="tablelabel">办理方式:</td>
										<td class="tablecontent">${prop.handleTypeName}</td>
										<td width="100px" align="middle" nowrap="nowrap" class="tablelabel">要求办结日期:</td>
										<td class="tablecontent">${prop.demandEnddate}</td>
									</tr>
									<tr>
										<td colspan="4" class="tablecontent" >
											<div id="tabs" class="easyui-tabs" style="width: 1010px; height: auto">
												<c:forEach var="h" items="${list}">
													<div title="${h.companyName}" style="padding: 3px">
														<table id="addtable" class="tableborder" >	
															<tr height="30">
																<td width="100px" align="middle" nowrap="nowrap" class="tablelabel">办理单位：</td>
																<td height="25" width="240px" class="tablecontent" sizset="false" align="left">${h.companyName}</td>
																<td width="100px" align="middle" nowrap="nowrap" class="tablelabel">沟通方式：</td>
																<td height="25" width="240px" class="tablecontent" sizset="false" align="left">${h.answerModeName}</td>
															</tr>
															<tr height="30">
																<td width="100px" align="middle" nowrap="nowrap" class="tablelabel">解决程度：</td>
																<td height="25" width="240px" class="tablecontent" sizset="false" align="left">${h.solveHowName}</td>
																<!-- <td width="100px" align="middle" nowrap="nowrap" class="tablelabel">是否落实：</td>
																<td height="25" width="240px" class="tablecontent" sizset="false" align="left">${h.carryoutFlgName}</td> -->
															
																<td width="100px" align="middle" nowrap="nowrap" class="tablelabel">提案人意见：</td>
																<td height="25" width="240px" class="tablecontent" sizset="false" align="left">${h.commOpName}</td>
															</tr>
															<tr height="30">
																<td width="100px" align="middle" nowrap="nowrap" class="tablelabel">实际办结日期：</td>
																<td colspan="3" height="25" width="240px" class="tablecontent" sizset="false" align="left">${h.factEnddate}</td>
															</tr>
															<tr height="30">
																<td width="100px" align="middle" nowrap="nowrap" class="tablelabel">答复审查状态：</td>
																<td height="25" class="tablecontent" sizset="false" align="left" colspan="3">${h.replyPassName}</td>
															</tr>
															<!-- <tr height="30">
																<td width="100px" align="middle" nowrap="nowrap" class="tablelabel">提案人办复建议：</td>
																<td colspan="3" height="25"class="tablecontent" sizset="false" align="left"><textarea name="satisfyInfo" id="satisfyInfo" style="width:99%;height:100px;padding:2px">${h.satisfyInfo}</textarea></td>
															</tr>
															<tr height="30">
																<td width="100px" align="middle" nowrap="nowrap" class="tablelabel">政府办复建议：</td>
																<td colspan="3" height="25"class="tablecontent" sizset="false" align="left"><textarea name="rebutInfo" id="rebutInfo" style="width:99%;height:100px;padding:2px">${h.satisfyInfo}</textarea></td>
															</tr> -->
															<tr>
																<td colspan="4" align="center" class="tablemain" >答复报告</td>
															</tr>
															<tr height="30">
																<td colspan="4" height="300" class="tablecontent" ><div style="height:295px;overflow:auto;">${h.reply}</div></td>
															</tr>
														</table>
													</div>
												</c:forEach>
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</center>
			</div>
</div>
<div data-options="title:'提案总体评价',region:'south',border:true" style="text-align:center;padding:20px 0;height:120px;">
	<form id="addForm" method="post">
		解决程度：<input name="solveHow" data-options="editable:false"  style="width:240px;padding:2px" />　
		<!-- 是否落实：<input name="carryoutFlg" data-options="editable:false"  style="width:80px;padding:2px" /> -->　
		提案人意见：<input name="committeemanOpinion" data-options="editable:false"  style="width:80px;padding:2px" />　　
		<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)" onclick="setOpt(${prop.proposalId})">设置</a>　
		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:window.close()">关闭</a>
	</form>
</div>
