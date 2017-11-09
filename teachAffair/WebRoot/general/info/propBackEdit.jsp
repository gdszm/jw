<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc;">
	<center>
		<form id="backEditForm" class="form-horizontal"  method="post">
		<input type="hidden" id="proposalId" name="proposalId"/>
		<input type="hidden" id="proposalCode" name="proposalCode"/>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		      <tr>
		        <td><table class="tableborder" id="formTable" sizset="false" >
		           <tbody sizset="false" >
		            <tr height="25">
		              <td height="25" colspan="4" align="middle" nowrap="nowrap" class="tablemain">
		                <div align="center"><strong>提案信息</strong></div>
		              </td>
		            </tr>
		            <tr height="25" sizset="false">
		              <td height="25" width="80" nowrap="nowrap" class="tablelabel">提案编号：</td>
		              <td height="25" width="160px" class="tablecontent" sizset="false"><label id="propCode" />  </td>
		              <td height="25" width="80" nowrap="nowrap" class="tablelabel">届&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;次：</td>
		              <td height="25" width="160px" class="tablecontent" sizset="false" ><label id="secondaryCode" /></td>
		            </tr>
					<tr height="25" sizset="false" >
		              <td height="25"　width="80" nowrap="nowrap" class="tablelabel">案&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;由：</td>
		              <td height="25" colspan="3" class="tablecontent"  sizset="false" ><label id="tl" /></td>
		            </tr>
		            <tr height="25" sizset="false" >
		              <td height="25" width="80" nowrap="nowrap" class="tablelabel">提案类型：</td>
		              <td height="25" width="160px" class="tablecontent" sizset="false" ><label id="proposalType" /></td>
		              <td height="25" width="80" nowrap="nowrap" class="tablelabel">第一提案人：</td>
		              <td height="25" width="160px" class="tablecontent" sizset="false" ><label id="fistProposaler" /></td>
		            </tr>
		            <tr height="50">
						<td  height="25" width="80" nowrap="nowrap" class="tablelabel">修改建议：</td>
						<td colspan="3" class="tablecontent">
							<textarea name="adviceInfo" id="adviceInfo" style="width:99%;height:150px;padding:2px"></textarea>
						</td>
					</tr>
		          </tbody>
		        </table></td>
		        </tr>
		    </table>
		   </form>
		 </center>
	</div>