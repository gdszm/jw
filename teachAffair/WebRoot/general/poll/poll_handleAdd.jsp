<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<div data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc;">
	<center>
	<form id="confirmForm" class="form-horizontal" method="post">
	<input type="hidden"  id="pollId" name="poll.pollId" value="${obj.pollId}"/>
	<input type="hidden"  id="companyIds" name="poll.handleComp" value="${obj.handleComp}"/>
	<input type="hidden"  id="mainFlgs" name="mainFlgs" />
	<table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
	      <tr>
	        <td><table  class="tableborder" id="formTable" sizset="false" >
	          <tbody sizset="false">
	           <tr height="30">
	              <td height="45" colspan="4" align="center" nowrap="nowrap"  class="tablemain" >
			   			<div align="center"><strong>政协巴彦淖尔市${sessionSeco.year}年度社情民意提交</strong></div>
	              </td>
	            </tr>
	            <tr height="25" sizset="false" >
	              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题：</td>
	              <td height="25" colspan="3" class="tablecontent" sizset="false" >${obj.title}</td>
	            </tr>
	            <tr >
	              <td height="25" nowrap="nowrap" class="tablelabel">社情民意内容： </td>
	              <td height="25" colspan="4" class="tablecontent"> 
					<div align="left" class="tanr-class" style="height:260px;padding:12px;line-height:25pt; overflow:auto; border:1px" id="contents">${obj.endContent}</div>
				  </td>
	            </tr>
				 <tr height="25" sizset="false" >
	              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">提交者：</td>
	              <td height="25" width="240px" class="tablecontent" sizset="false" >${obj.createManName}</td>
	              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">社情民意类型 ：</td>
	              <td height="25" width="300px" class="tablecontent" sizset="false" >${obj.pollTypeName}</td>
	            </tr>
	            <tr height="32" sizset="false">
	              <td height="25" align="center"　 colspan="4" nowrap="nowrap" class="tablemain">办理单位信息</td>
	            </tr>
	            <tr height="25" sizset="false" >
	              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">办理方式：</td>
	              <td height="25" width="360px" colspan="3" class="tablecontent" sizset="false" ><input name="poll.handleType" id="handleTypeCheck"  value="${obj.handleType}"  style="width:300px">
	                </td>
	            </tr>
	           <tr height="200" sizset="false" >
	              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">办理单位：</td>
	              <td height="25" colspan="3" class="tablecontent" sizset="false" > 
					<table id="cbdwgrid"></table>
	              </td>
	            </tr>
	            </tr>
	             <tr height="25" sizset="false" >
	              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">操作人员：</td>
	              <td height="25" width="240px" class="tablecontent" sizset="false" >${sessionInfo.realName}</td>
	              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">操作时间：</td>
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
