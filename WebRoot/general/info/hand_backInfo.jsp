<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	//拒绝申退
	function refuse(handleReplyId,propId) {
		$.ajax({
			url : '${pageContext.request.contextPath}/hand!refuse.do?handleReplyId='+handleReplyId+'&proposalId='+propId,
			success : function(d) {
				var json = $.parseJSON(d);
				if (json.success) {
					dj.messagerShow({
						msg : json.msg,
						title : '提示'
					});
					datagrid.datagrid('reload');
				}
			}
		});
	}
</script>
<div data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc;">
	<center>
		<form id="stressForm" class="form-horizontal" action="/zxta/tatj/saveta" method="post">
		<input type="hidden" id="proposalId" name="proposalId"/>
		<input type="hidden" id="proposalCode" name="proposalCode"/>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		      <tr>
		        <td><table class="tableborder" id="formTable" sizset="false" >
		           <tbody sizset="false" >
		            <tr height="25">
		              <td height="25" colspan="4" align="middle" nowrap="nowrap"  class="tablemain">
		                <div align="center"><strong>提案信息</strong></div>
		              </td>
		            </tr>
			         <tr height="30">
							<td width="80px" align="middle" nowrap="nowrap" class="tablelabel">提案编号：</td>
							<td width="160" class="tablecontent">${prop.proposalCode}</td>
							<td width="80px" align="middle" nowrap="nowrap" class="tablelabel">届&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;次：</td>
							<td width="160" class="tablecontent">${prop.secondayName}</td>
						</tr>
						<tr height="30">
							<td width="100px" align="middle" nowrap="nowrap" class="tablelabel">案&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;由：</td>
							<td colspan="3" class="tablecontent">${prop.title}</td>
						</tr>
						<tr height="30">
							<td width="80px" align="middle" nowrap="nowrap" class="tablelabel" >第一提案人：</td>
							<td class="tablecontent">${prop.fistProposaler}</td>
							<td width="80px" align="middle" nowrap="nowrap" class="tablelabel">提案类型：</td>
							<td class="tablecontent">${prop.proposalTypeName}</td>
							
						</tr>
						<tr height="30">
							<td width="80px" align="middle" nowrap="nowrap" class="tablelabel">是否立案：</td>
							<td class="tablecontent">${prop.fileMethodName}</td>
							<td width="80px" align="middle" nowrap="nowrap" class="tablelabel" >立案时间：</td>
							<td class="tablecontent">${prop.checkTime}</td>
						</tr>
			             <tr height="25" sizset="false" >
			              <td height="25" width="80" nowrap="nowrap" align="middle" class="tablelabel">办理方式：</td>
			              <td height="25" width="160" colspan="3" class="tablecontent" sizset="false">${prop.handleTypeName} </td>
			            </tr>
		          		<tr>
							<td colspan="4" align="center" class="tablecontent" >
								<div id="tabs" class="easyui-tabs" style="width:590px; height: auto">
									<c:forEach var="h" items="${list}">
										<c:if test="${h.status=='2'}">
											<div title="${h.companyName}" style="padding: 3px">
												<table class="tableborder" >	
													<tr height="30">
														<td colspan="4" class="tablecontent" height="100px">${h.opinionExplain}</td>
													</tr>
													<tr height="30">
														<td colspan="4" align="right" class="tablemain"><a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)" onclick="refuse(${h.handleReplyId},${prop.proposalId});">拒绝申退</a></td>
													</tr>
												</table>
											</div>
										</c:if>
									</c:forEach>
								</div>
							</td>
			            </tr>
		          	</tbody>
		        </table></td>
		        </tr>
		    </table>
		   </form>
		 </center>
	</div>