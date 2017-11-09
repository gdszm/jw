<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc;">
	<center>
		<form id="rebutForm" class="form-horizontal" action="/zxta/tatj/saveta" method="post">
		<input type="hidden" id="pollId" name="pollId" value="${obj.poll.pollId}"/>
		<input type="hidden" id="handleId" name="handleId" value="${obj.handleId}"/>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		      <tr>
		        <td><table class="tableborder" id="formTable" sizset="false" >
		           <tbody sizset="false" >
		            <tr height="25">
		              <td height="25" colspan="4" align="middle" nowrap="nowrap" class="tablemain">
		                <div align="center"><strong>${obj.comp.companyName}</strong></div>
		              </div></td>
<%--					<tr height="25">--%>
<%--						<td width="80" nowrap="nowrap" class="tablelabel">沟通方式：</td>--%>
<%--						<td class="tablecontent">${obj.answerModeName}</td>--%>
<%--						<td width="80" nowrap="nowrap" class="tablelabel">解决程度：</td>--%>
<%--						<td class="tablecontent">${obj.solveHowName}</td>--%>
<%--					</tr>--%>
					<tr height="25">
						<td width="80" nowrap="nowrap" class="tablelabel">办理类型：</td>
						<td class="tablecontent" style="width:50%;padding:2px">${obj.poll.handleTypeName}</td>
						<td width="80" nowrap="nowrap" class="tablelabel">办结日期：</td>
						<td class="tablecontent" style="width:50%;padding:2px">${obj.factEnddate}</td>
					</tr>
					<tr height="25">
						<td width="80" nowrap="nowrap" class="tablelabel">意见说明：</td>
						<td colspan="3">
						<textarea name="rebutInfo" id="rebutInfo" style="width:99%;height:150px;padding:2px">${obj.rebutInfo}</textarea>
						</td>
					</tr>
		 		</tbody>
		        </table></td>
		        </tr>
		    </table>
		   </form>
		 </center>
	</div>