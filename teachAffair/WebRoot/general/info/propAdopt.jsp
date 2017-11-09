<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc;">
	<center>
		<form id="adoptForm" class="form-horizontal"  method="post">
		<input type="hidden" id="proposalId" name="proposalId"/>
		<input type="hidden" id="proposalCode" name="proposalCode"/>
		<input type="hidden" id="adoptFlg" name="adoptFlg"　value="0"/>
		<table width="480" border="0" align="center" cellpadding="0" cellspacing="0">
		      <tr>
		        <td><table width="480" align="center" cellpadding="3" cellspacing="1" bgcolor="#003366" id="formTable" sizset="false" >
		           <tbody sizset="false" >
		            <tr height="25">
		              <td height="25" colspan="4" align="middle" nowrap="nowrap" bgcolor="#a9cfd5"><div bgcolor="#FFFEE6">
		                <div align="center"><strong>提案信息</strong></div>
		              </div></td>
		            </tr>
		            <tr height="25" sizset="false">
		              <td height="25" width="80" nowrap="nowrap" bgcolor="#ddf5f9">提案编号：</td>
		              <td height="25" width="160" bgcolor="#FFFFFF" sizset="false"><label id="propCode" />  </td>
		              <td height="25" width="80" nowrap="nowrap" bgcolor="#ddf5f9">届&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;次：</td>
		              <td height="25" width="160" bgcolor="#FFFFFF" sizset="false" ><label id="secondaryCode" /></td>
		            </tr>
					<tr height="25" sizset="false" >
		              <td height="25"　width="80" nowrap="nowrap" bgcolor="#ddf5f9">案&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;由：</td>
		              <td height="25" colspan="3" bgcolor="#FFFFFF"  sizset="false" ><label id="tl" /></td>
		            </tr>
		            <tr height="25" sizset="false" >
		              <td height="25" width="80" nowrap="nowrap" bgcolor="#ddf5f9">提案类型：</td>
		              <td height="25" width="160" bgcolor="#FFFFFF" sizset="false" ><label id="proposalType" /></td>
		              <td height="25" width="80" nowrap="nowrap" bgcolor="#ddf5f9">第一提案人：</td>
		              <td height="25" width="160" bgcolor="#FFFFFF" sizset="false" ><label id="fistProposaler" /></td>
		            </tr>
		            <tr height="25">
						<td  height="25" width="80" nowrap="nowrap" bgcolor="#ddf5f9">拒绝采纳意见：</td>
						<td colspan="3" bgcolor="#FFFFFF">
							<textarea name="adoptExplain" id="adoptExplain" style="width:400px;height:100px;padding:2px"></textarea>
						</td>
					</tr>
		           
		          </tbody>
		        </table></td>
		        </tr>
		    </table>
		   </form>
		 </center>
	</div>