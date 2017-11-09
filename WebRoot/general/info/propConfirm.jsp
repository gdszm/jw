<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<div data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc;">
	<center>
	<form id="confirmForm" class="form-horizontal" method="post">
	<input type="hidden"  id="proposalId" name="proposalId" value="${obj.proposalId}"/>
	<input type="hidden"  id="proposalCode" name="proposalCode" value="${obj.proposalCode}"/>
	<input type="hidden"  id="sponsorIds" name="sponsorIds" value="${obj.sponsorIds}"/>
	<input type="hidden"  id="companyIds" name="companyIds" value="${obj.companyIds}"/>
	<input type="hidden"  id="mainFlgs" name="mainFlgs" />
	<table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
	      <tr>
	        <td><table  class="tableborder" id="formTable" sizset="false" >
	          <tbody sizset="false">
	           <tr height="30">
	              <td height="45" colspan="4" align="center" nowrap="nowrap"  class="tablemain" >
	                <c:if test="${obj.meetingFlg=='0'}">
			   			<div align="center"><input type="hidden" name="secondaryId" value="${sessionSeco.secondaryId}"/><strong>政协巴彦淖尔市第${fn:substring( sessionSeco.secondayName,0,fn:indexOf(sessionSeco.secondayName,'届')+1)}委员会第${fn:substring(sessionSeco.secondayName,fn:indexOf(sessionSeco.secondayName,'届')+1,fn:length(sessionSeco.secondayName))}会议提案</strong></div>
			   		</c:if>
			   		<c:if test="${obj.meetingFlg=='1'}">
			   			<div align="center"><input type="hidden" name="secondaryId" value="${sessionSeco.secondaryId}"/><strong>政协巴彦淖尔市第${fn:substring( sessionSeco.secondayName,0,fn:indexOf(sessionSeco.secondayName,'届')+1)}委员会${sessionSeco.year}年度提案</strong></div>
			   		</c:if>
	              </td>
	            </tr>
	            <tr height="25" sizset="false" >
	              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">案&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;由：</td>
	              <td height="25" colspan="3" class="tablecontent" sizset="false" >${obj.title}</td>
	            </tr>
	            <tr >
	              <td height="25" nowrap="nowrap" class="tablelabel">提案内容： </td>
	              <td height="25" colspan="4" class="tablecontent"> 
					<div align="left" class="tanr-class" style="height:260px;padding:12px;line-height:25pt; overflow:auto; border:1px" id="contents">${obj.content}</div>
				  </td>
	            </tr>
				<tr height="25" sizset="false" >
	              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">第一提案人：</td>
	              <td height="25" colspan="3" class="tablecontent" sizset="false" >${obj.fistProposaler}</td>
	            </tr>
	            <tr  height="25" sizset="false">
	              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">提案人建议办理单位：</td>
	              <td height="25"　 colspan="3" class="tablecontent" sizset="false" >${obj.undertakeUnit}</td>
	            </tr>
	            <tr  height="25" sizset="false">
	              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">拟定办理单位：</td>
	              <td height="25"　 colspan="3" class="tablecontent" sizset="false" >${obj.adviceUnitName}</td>
	            </tr>
	            <tr height="32" sizset="false">
	              <td height="25" align="center"　 colspan="4" nowrap="nowrap" class="tablemain">确定办理单位</td>
	            </tr>
	             <tr height="25" sizset="false" >
	              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">是否立案：</td>
	              <td height="25" width="240px" class="tablecontent" sizset="false" >${obj.fileMethodName}</td>
	              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">立案日期 ：</td>
	              <td height="25" width="300px" class="tablecontent" sizset="false" >${obj.checkTime}</td>
	            </tr>
	            <tr height="25" sizset="false" >
	              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">办理方式：</td>
	              <td height="25" width="360px" colspan="3" class="tablecontent" sizset="false" ><input name="handleType" id="handleTypeCheck"  value="${obj.handleType}" panelheight="auto" style="width:300px">
	                </td>
	            </tr>
	           <tr height="200" sizset="false" >
	              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">办理单位：</td>
	              <td height="25" colspan="3" class="tablecontent" sizset="false" > 
					<table id="cbdwgrid"></table>
	              </td>
	            </tr>
	             <tr height="25" sizset="false" >
	              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">办理单位类型：</td>
	              <td height="25" width="300px" colspan="3" class="tablecontent" sizset="false" ><input name="analySisUnit" id="analySisUnit"  value="${obj.analySisUnit}" panelheight="auto" style="width:300px"></td>
	            </tr>
	            </tr>
	             <tr height="25" sizset="false" >
	              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">批&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;示：</td>
	              <td height="25" colspan="3" class="tablecontent" sizset="false" ><textarea name="instructions" style="width:99%;height:100px;padding:2px">${obj.instructions}</textarea> </td>
	            </tr>
	             <tr height="25" sizset="false" >
	              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">审查人员：</td>
	              <td height="25" width="240px" class="tablecontent" sizset="false" >${sessionInfo.realName}</td>
	              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">审查时间：</td>
	              <td height="25" width="300px" class="tablecontent" sizset="false" ><input type="text" name="submitTime" id="submitTime"  style="width:300px"/>
	              </td>
	            </tr>
	           
	          </tbody>
	        </table></td>
	        </tr>
	    </table>
	   </form>
	 </center>
</div>

<script type="text/javascript">
	$.extend($.fn.validatebox.defaults.rules, {  
		sel: {  
			validator: function(value){ 
				return value != '请选择...';  
			},  
			message: '此项必须选择！'  
		}  
	});  
</script>
