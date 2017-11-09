<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<script type="text/javascript">
var editor = new baidu.editor.ui.Editor({ 
    textarea:'endContent' 
}); 
editor.render("editor");
</script>
<div data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc;">
<center>
<form id="markPollForm" class="form-horizontal"  method="post">
<input type="hidden"  id="pollId" name="pollId" value="${obj.pollId}" />
<input type="hidden"  id="createMan" name="createMan" value="${obj.createMan}" />
<table border="0" align="center" cellpadding="0" cellspacing="0" style="width:1024px;">
      <tr>
        <td><table  class="tableborder" id="formTable" sizset="false" >
          <tbody sizset="false" >
            <tr height="30">
              <td height="45" colspan="4" align="middle" nowrap="nowrap" class="tablemain">
               	<div align="center"><strong>政协巴彦淖尔市${sessionSeco.year}年度社情民意提交</strong></div>
              </td>
            </tr>
            <tr height="25" sizset="false" >
              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题：</td>
              <td height="25" colspan="3" class="tablecontent" sizset="false" align="left"><input type="text" id="title" name="title" class="easyui-validatebox" data-options="required:true" value="${obj.title}"  style="width:99%;padding:2px" /></td>
            </tr>
			<tr  height="25" sizset="false">
              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">撰稿人：</td>
              <td height="25"　colspan="3" class="tablecontent" sizset="false" align="left" >${obj.writer}</td>
            </tr>
			 <tr height="25" sizset="false" >
              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">主&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;送：</td>
              <td height="25" colspan="3" class="tablecontent" sizset="false" align="left"><input type="text" id="master" name="master" class="easyui-validatebox"  value="${obj.master}"  style="width:99%;padding:2px" /></td>
            </tr>
             <tr height="25" sizset="false" >
              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">抄&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;送：</td>
              <td height="25" colspan="3" class="tablecontent" sizset="false" align="left"><input type="text" id="copyMan" name="copyMan" class="easyui-validatebox"  value="${obj.copyMan}"  style="width:99%;padding:2px" /></td>
            </tr>
            <c:if test="${obj.pollType=='4'}">
			<tr height="25" sizset="false" >
              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">提交者：</td>
              <td height="25" width="240px" class="tablecontent" sizset="false"  align="left">${obj.createMan}</td>
              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">联系电话：</td>
              <td height="25" width="240px" class="tablecontent" sizset="false"  align="left">${obj.unit}
              </td>
            </tr>
            <tr height="25" sizset="false" >
              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">联系地址：</td>
              <td height="25" width="240px" class="tablecontent" sizset="false" align="left">${obj.phone}</td>
              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">电子邮箱：</td>
              <td height="25" width="240px" class="tablecontent" sizset="false"  align="left">${obj.email}
              </td>
            </tr>
             <tr height="25" sizset="false" >
              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">联系地址：</td>
              <td height="25" colspan="3" width="240px" class="tablecontent" sizset="false" align="left" >${obj.address}</td>
            </tr>
           </c:if>
           <c:if test="${obj.mergeFlg=='1'}">
				<tr height="25">
					<td height="25" width="80px" align="middle" nowrap="nowrap"
						class="tablelabel">
						合并意见：
					</td>
					<td height="25" colspan="3" valign="middle" width="240px" class="tablecontent"
						sizset="false" align="left">
						<textarea style="width:99%;height: 50px; font-size: 14px; ">${obj.adoptExplain}</textarea>
					</td>
					
				</tr>
			</c:if>
             <tr height="32" sizset="false" >
	              <td height="25" align="center"　 colspan="4" nowrap="nowrap" class="tablespecial">
	              	<font color="#F00"><strong>重要提示：如提交内容较多，请先在记事本或word中提前录好保存，然后再复制到内容框中，以免在线超时，引起信息丢失！</strong></font>
	             </td>
	            </tr>
 			<tr>
				<td colspan="4" align="center" class="tablecontent">
					<div class="easyui-tabs" style="width:1024px;height:600px;"> 
						<div title="&nbsp;&nbsp;社情民意审查修改稿&nbsp;&nbsp;" style="padding:3px;overflow:hidden" align="center">
							<script id="editor" type="text/plain" style="width:99%;height:480px;"> ${obj.endContent}</script>
						</div>
						<div title="&nbsp;&nbsp;社情民意原稿&nbsp;&nbsp;"	style="padding: 3px">
							<div style="padding:12px;line-height:25pt;" class="tanr-class" id="tanr">
								${obj.content}
							</div>
						</div>
						<c:if test="${obj.pollType!='4'}">
						<div title="提交者信息" style="padding:3px;height:480px;">  
							<table id="submittergrid"></table> 
						</div>
						</c:if>
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