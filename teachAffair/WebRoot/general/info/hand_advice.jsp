<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc;">
	<center>
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
						<td width="80px" align="middle" nowrap="nowrap" class="tablelabel">提案编号:</td>
						<td width="160" class="tablecontent">${handle.proposalCode}</td>
						<td width="80px" align="middle" nowrap="nowrap" class="tablelabel">届&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;次:</td>
						<td width="160" class="tablecontent">${handle.secondayName}</td>
					</tr>
					<tr height="30">
						<td nowrap align="middle" class="tablelabel">案&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;由:</td>
						<td colspan="3" class="tablecontent">${handle.title}</td>
					</tr>
					<tr height="30">
						<td width="80px" align="middle" nowrap="nowrap" class="tablelabel" >第一提案人:</td>
						<td class="tablecontent">${handle.fistProposaler}</td>
						<td width="80px" align="middle" nowrap="nowrap" class="tablelabel">提案类型:</td>
						<td class="tablecontent">${handle.proposalTypeName}</td>
					</tr>
		              <tr height="25" sizset="false" >
		              <td height="25" width="80" align="middle" nowrap="nowrap" class="tablelabel">办理方式：</td>
		              <td height="25" width="160" colspan="3" class="tablecontent" sizset="false">${handle.handleTypeName}</td>
		            </tr>
		              <tr height="25" sizset="false" >
		              <td height="25" width="80" align="middle" nowrap="nowrap" class="tablelabel">政府办建议：</td>
		              <td colspan="3" class="tablecontent">
							<textarea name="rebutInfo" id="rebutInfo" style="width:98%;height:60px;padding:2px">${handle.rebutInfo}</textarea>
						</td>
		            </tr>
		              <tr height="25" sizset="false" >
		              <td height="25" width="80" align="middle" nowrap="nowrap" class="tablelabel">提案人建议：</td>
		              <td colspan="3" bgcolor="#FFFFFF">
							<textarea name="satisfyInfo" id="satisfyInfo" style="width:98%;height:60px;padding:2px">${handle.satisfyInfo}</textarea>
						</td>
		            </tr>
		          </tbody>
		        </table></td>
		        </tr>
		    </table>
		 </center>
	</div>