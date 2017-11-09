<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	//拒绝申退
	function refuse(handleId) {
		$.ajax({
			url : '${pageContext.request.contextPath}/pollHandle!handleRefuse.do?handleId='+handleId,
			success : function(d) {
				var json = $.parseJSON(d);
				if (json.success) {
					gridReview.datagrid('reload');
					p.dialog('close');
				}
					dj.messagerShow({
					msg : json.msg,
					title : '提示'
				});
			}
		});
	}
</script>
<div data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc;">
	<center>
		<form id="stressForm" class="form-horizontal" action="/zxta/tatj/saveta" method="post">
		<input type="hidden" id="pollId" name="pollId" value="${poll.pollId} "/>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		      <tr>
		        <td><table class="tableborder" id="formTable" sizset="false" >
		           <tbody sizset="false" >
		            <tr height="25">
		              <td height="25" colspan="4" align="middle" nowrap="nowrap"  class="tablemain">
		                <div align="center"><strong>社情民意信息</strong></div>
		              </td>
		            </tr>
			         <tr height="25" sizset="false">
		              <td height="25" width="80" nowrap="nowrap" class="tablelabel">社情民意编号：</td>
		              <td height="25" width="160" class="tablecontent" sizset="false"> ${poll.pollCode} </td>
		              <td height="25" width="80" nowrap="nowrap" class="tablelabel">年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度：</td>
		              <td height="25" width="160" class="tablecontent" sizset="false" >${poll.tsecondary.secondaryId}</td>
		            </tr>
					<tr height="25" sizset="false" >
		              <td height="25"　width="80" nowrap="nowrap" class="tablelabel">标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题：</td>
		              <td height="25" colspan="3" class="tablecontent"  sizset="false" >${poll.title}</td>
		            </tr>
		            <tr height="25" sizset="false" >
		              <td height="25" width="80" nowrap="nowrap" class="tablelabel">社情民意类型：</td>
		              <td height="25" width="160" class="tablecontent" sizset="false" >${poll.pollTypeName}</td>
		              <td height="25" width="80" nowrap="nowrap" class="tablelabel">提交者：</td>
		              <td height="25" width="160" class="tablecontent" sizset="false" >${poll.createManName}</td>
		            </tr>
		             <tr height="25" sizset="false" >
		              <td height="25" width="80" nowrap="nowrap" align="middle" class="tablelabel">办理方式：</td>
		              <td height="25" width="160" colspan="3" class="tablecontent" sizset="false">${poll.handleTypeName}</td>
		            </tr>
		          		<tr>
							<td colspan="4" align="center" class="tablecontent" >
								<div id="tabs" class="easyui-tabs" style="width:590px; height: auto">
									<c:forEach var="h" items="${list}">
										<c:if test="${h.status=='2'}">
											<div title="${h.comp.companyName}" style="padding: 3px">
												<table class="tableborder" >	
													<tr height="30">
														<td colspan="4" class="tablecontent" height="130px">${h.opinionExplain}</td>
													</tr>
													<tr height="30">
														<td colspan="4" align="right" class="tablemain"><a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)" onclick="refuse(${h.handleId});">拒绝申退</a></td>
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