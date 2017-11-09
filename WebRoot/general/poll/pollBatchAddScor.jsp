<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
 <div data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc;">
	<center>
		<form id="batchScoreForm" class="form-horizontal"  method="post">
		<input type="hidden" id="pollId" name="poll.pollId"/>
		<input type="hidden" id="rulesIds" name="ids"/>
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
		              <td height="25" width="80" nowrap="nowrap" class="tablelabel">社情民意编号：</td>
		              <td height="25" width="160" class="tablecontent" sizset="false"><label id="pollCode" />  </td>
		              <td height="25" width="80" nowrap="nowrap" class="tablelabel">年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度：</td>
		              <td height="25" width="160" class="tablecontent" sizset="false" ><label id="secondaryYear" /></td>
		            </tr>
					<tr height="25" sizset="false" >
		              <td height="25"　width="80" nowrap="nowrap" class="tablelabel">标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题：</td>
		              <td height="25" colspan="3" class="tablecontent"  sizset="false" ><label id="tl" /></td>
		            </tr>
		            <tr height="25" sizset="false" >
		              <td height="25" width="80" nowrap="nowrap" class="tablelabel">社情民意类型：</td>
		              <td height="25" width="160" class="tablecontent" sizset="false" ><label id="pType" /></td>
		              <td height="25" width="80" nowrap="nowrap" class="tablelabel">提交者：</td>
		              <td height="25" width="160" class="tablecontent" sizset="false" ><label id="cMan" /></td>
		            </tr>
		             <tr height="25">
		              <td height="25" colspan="4" align="middle" nowrap="nowrap" class="tablemain">
		                <div align="center"><strong>计分信息</strong></div>
		              </td>
		            </tr>
		            <tr height="230" sizset="false" >
		              <td colspan="4" align="middle" nowrap="nowrap" >
		              <div class="easyui-layout" data-options="fit:true">						
							<div data-options="region:'center',border:false" style="padding:1px;background:#fff;border:1px solid #ccc;">
								<table id="rulesGrid"></table>
							</div>
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
	