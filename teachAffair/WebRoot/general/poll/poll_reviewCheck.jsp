<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	var p;
	function reject(id){
		p = dj.dialog({
			title : '驳回重办',
			href : '${pageContext.request.contextPath}/pollHandle!pollRebut.do?handleId=' + id,
			width : 600,
			height : 350,
			iconCls:'icon-save',
			buttons : [{
					text : '提交',
					iconCls:'icon-ok',
					handler : function() {
						rebut();
					}
				},{ 
					text: '取消', 
					iconCls:'icon-cancel',
					handler: function() { 
						p.dialog('close'); 
					} 
				}]
		});			
	}
	function rebut(){
		$('#rebutForm').form('submit', {
			url : '${pageContext.request.contextPath}/pollHandle!reject.do',
			success : function(d) {
				var json = $.parseJSON(d);
				if (json.success) {
					p.dialog('close'); 
					parent.window.location.reload();
				}
				dj.messagerShow({
					msg : json.msg,
					title : '提示'
				});
			}
		});
	
	}
	function checkPass(id){
		dj.messagerConfirm('请确认', '确定审查通过该办理单位办复报告吗？', function(r) {
			if (r) {
				$.ajax({
					url : '${pageContext.request.contextPath}/pollHandle!checkPass.do',
					data : {
						handleId:id
					},
					success : function(d) {
						var json = $.parseJSON(d);
						if (json.success) {
							window.location.reload();
						}
						dj.messagerShow({
							msg : json.msg,
							title : '提示'
						});
					}
				});
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
										<div align="center"><input type="hidden" name="secondaryId" value="${sessionSeco.secondaryId}"/><strong>政协巴彦淖尔市${sessionSeco.year}年度社情民意</strong></div>
										</td>
									</tr>
									<tr height="25">
										<td height="25"  width="80px" align="middle" nowrap="nowrap"
											class="tablelabel">
											社情民意编号：
										</td>
										<td height="25" width="240px" class="tablecontent"
											sizset="false" align="left">
											${poll.pollCode}
										</td>
										<td height="25" width="80px" align="middle" nowrap="nowrap"
											class="tablelabel">
											年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度：
										</td>
										<td height="25" width="240px" class="tablecontent"
											sizset="false" align="left">
											${poll.tsecondary.year}
										</td>
									</tr>
									<tr height="25">
										<td height="25" width="80px" align="middle" nowrap="nowrap"
											class="tablelabel">
											标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题：
										</td>
										<td height="25" colspan="3" width="550px" align="left"
											class="tablecontent">
											${poll.title}
										</td>
									</tr>
									<tr height="25">
										<td height="25" width="80px" align="middle" nowrap="nowrap"
											class="tablelabel">
											提交者：
										</td>
										<td height="25" width="240px" align="left"
											class="tablecontent">
											${poll.createManName}
										</td>
										<td height="25" width="80px" align="middle" nowrap="nowrap"
											class="tablelabel">
											撰稿人：
										</td>
										<td height="25" colspan="3" width="240px" align="left"
											class="tablecontent">
											${poll.writer}
										</td>
									</tr>
									<tr height="25">
										<td height="25" width="80px" align="middle" nowrap="nowrap"
											class="tablelabel">
											社情民意类型：
										</td>
										<td height="25" width="240px" class="tablecontent"
											sizset="false" align="left">
											${poll.pollTypeName}
										</td>
										<td height="25" width="80px" align="middle" nowrap="nowrap"
											class="tablelabel">
											提交日期：
										</td>
										<td height="25" width="240px" class="tablecontent"
											sizset="false" align="left">
											${poll.createTime}
										</td>
									</tr>
									<tr height="25">
										<td height="25" width="80px" align="middle" nowrap="nowrap"
											class="tablelabel">
											办理类型：
										</td>
										<td height="25" colspan="3" width="550px" align="left"
											class="tablecontent">
											${poll.handleTypeName}
										</td>
									</tr>
									<tr>
										<td colspan="4" width="100%" align="center" class="tablecontent" >
											<div id="tabs" class="easyui-tabs" style="width:980px; height: auto">
												<c:forEach var="h" items="${list}">
													<div title="${h.comp.companyName}" style="padding: 3px">
														<table id="addtable"  class="tableborder">	
															<tr height="30">
																<td width="100px" align="middle" nowrap="nowrap" class="tablelabel">办理单位：</td>
																<td height="25" width="240px" class="tablecontent" sizset="false" align="left">${h.comp.companyName}</td>
<%--																<td width="100px" align="middle" nowrap="nowrap" class="tablelabel">沟通方式：</td>--%>
<%--																<td height="25" width="240px" class="tablecontent" sizset="false" align="left">${h.answerModeName}</td>--%>
																<td width="100px" align="middle" nowrap="nowrap" class="tablelabel">办结日期：</td>
																<td height="25" width="240px" class="tablecontent" sizset="false" align="left">${h.factEnddate}</td>
															</tr>
<%--															<tr height="30">--%>
<%--																<td width="100px" align="middle" nowrap="nowrap" class="tablelabel">解决程度：</td>--%>
<%--																<td height="25" width="240px" class="tablecontent" sizset="false" align="left">${h.solveHowName}</td>--%>
<%--																<td width="100px" align="middle" nowrap="nowrap" class="tablelabel">办结日期：</td>--%>
<%--																<td height="25" width="240px" class="tablecontent" sizset="false" align="left">${h.factEnddate}</td>--%>
<%--															</tr>--%>
															<tr>
																<td colspan="4" align="center" class="tablemain" >办复报告</td>
															</tr>
															<tr height="30">
																<td colspan="4"height="300" class="tablecontent" ><div style="height:295px;overflow:auto;">${h.reply}</div></td>
															</tr>
															<tr height="35">
																<td align="right" colspan="4" class="tablemain">
																<c:if test="${h.status=='4'}">
																	<c:choose>
																		<c:when test="${h.replyPass=='1'}"> 
																	   		办复审查通过
																	   </c:when>  
																	   <c:otherwise> 
																		  <a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)" onclick="reject(${h.handleId});">驳回重办</a>
																		  <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="checkPass(${h.handleId});">审查通过</a>
																	   </c:otherwise>
																	</c:choose>
																	
																</c:if>
																</td>
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
