<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc;">
	<center>
		<form id="mergePollForm" class="form-horizontal"  method="post">
		<input type="hidden" id="ids" name="ids"/>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		      <tr>
		        <td><table class="tableborder" id="formTable" sizset="false" >
		           <tbody sizset="false" >
		            <tr height="25">
		              <td height="25" colspan="4" align="middle" nowrap="nowrap" class="tablemain">
		                <div align="center"><strong>社情民意信息</strong></div>
		              </td>
		            </tr>
		            <tr height="25" sizset="false">
		              <td height="25" width="80" nowrap="nowrap" class="tablelabel">合并主社情民意：</td>
		              <td height="25" width="100%" class="tablecontent" sizset="false"><label id="mainPoll" name="mainPoll" />  </td>
		            </tr>
					<tr height="25" sizset="false" >
		              <td height="25"　width="80" nowrap="nowrap" class="tablelabel">合并社情民意：</td>
		              <td  class="tablecontent">
		              	<textarea  id="mergePoll" name="mergePoll" readonly="readonly"　 style="width:99%;height:40px;padding:2px"></textarea>
		              </td>
		            </tr>
		            <tr height="25">
						<td  height="25" width="80" nowrap="nowrap" class="tablelabel">合并意见：</td>
						<td  class="tablecontent">
							<textarea name="adoptExplain" id="adoptExplain" style="width:99%;height:100px;padding:2px"></textarea>
						</td>
					</tr>
		           
		          </tbody>
		        </table></td>
		        </tr>
		    </table>
		   </form>
		 </center>
	</div>