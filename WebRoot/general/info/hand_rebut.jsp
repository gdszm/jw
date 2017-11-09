<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc;">
	<center>
		<form id="rebutForm" class="form-horizontal" action="/zxta/tatj/saveta" method="post">
		<input type="hidden" id="proposalId" name="proposalId" value="${handle.proposalId}"/>
		<input type="hidden" id="handleReplyId" name="handleReplyId" value="${handle.handleReplyId}"/>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		      <tr>
		        <td><table class="tableborder" id="formTable" sizset="false" >
		           <tbody sizset="false" >
		            <tr height="25">
		              <td height="25" colspan="4" align="middle" nowrap="nowrap" class="tablemain">
		                <div align="center"><strong>${handle.companyName}</strong></div>
		              </div></td>
					<tr height="25">
						<td width="80" nowrap="nowrap" class="tablelabel">沟通方式：</td>
						<td class="tablecontent">${handle.answerModeName}</td>
						<td width="80" nowrap="nowrap" class="tablelabel">解决程度：</td>
						<td class="tablecontent">${handle.solveHowName}</td>
						<!--<td width="80" nowrap="nowrap" class="tablelabel">是否落实：</td>
						<td class="tablecontent">${handle.carryoutFlgName}</td>  -->
					</tr>
					<tr height="25">
						<td width="80" nowrap="nowrap" class="tablelabel">提案人意见：</td>
						<td class="tablecontent">${handle.commOpName}</td>
						<td width="80" nowrap="nowrap" class="tablelabel">实际办结日期：</td>
						<td class="tablecontent">${handle.factEnddate}</td>
					</tr>
					<tr height="25">
						<td width="80" nowrap="nowrap" class="tablelabel">意见说明：</td>
						<td colspan="3">
						<textarea name="rebutInfo" id="rebutInfo" style="width:99%;height:150px;padding:2px"></textarea>
						</td>
					</tr>
		 		</tbody>
		        </table></td>
		        </tr>
		    </table>
		   </form>
		 </center>
	</div>