<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<script type="text/javascript">
var editor = new baidu.editor.ui.Editor({ 
        textarea:'acontent' 
    }); 
    editor.render("editor");
</script>
<div data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc;">
<div align="center" style="padding: 5px;overflow: hidden;">
	<form id="editForm"  method="post">
		<input type="hidden" name="aid"  value="${obj.aid}"/>
		<input type="hidden" name="invitnumb" id="invitnumb" value="${obj.invitnumb}"/>
		<input type="hidden" name="secondaryId"  value="${obj.secondaryId}"/>
		<input type="hidden" name="status"  value="${obj.status}"/>
		<input type="hidden" name="ids" id="commCode" value=""/>
<!-- 		<input type="hidden" name="acontent" id="acontent" value=""/> -->
<table border="0" align="center" cellpadding="0" cellspacing="0" style="width:1024px;">
    <tr>
      <td>		
        <table class="tableborder" id="formTable">
        		<tr height="30">
	              <td height="45" colspan="4" align="middle" nowrap="nowrap" class="tablemain">
	               	<div align="center"><strong>政协巴彦淖尔市${obj.year}年度活动新增</strong></div>
	              </td>
	            </tr>
				<tr height="30">
					<td nowrap class="tablelabel">活动主题：</td>
					<td align="left" class="tablecontent"><input type="text" name="atheme"  class="easyui-validatebox" value="${obj.atheme}" data-options="required:true,missingMessage:'请填写主题'" style="width:300px;padding:2px" /></td>
				</tr>
				<tr height="30">
					<td nowrap class="tablelabel">活动种类：</td>
					<td align="left" class="tablecontent" ><input type="text" name="aspecies" style="width:200px;padding:2px" value="${obj.aspecies}"/></td>
				</tr>
				<tr height="30" id="commtr_1">
					<td nowrap class="tablelabel">活动时间：</td>
					<td align="left" class="tablecontent"><input class="easyui-datebox" name="timebeg" value="${obj.timebeg}"/>--<input class="easyui-datebox" name="timeend" value="${obj.timeend}"/></td>
				</tr>
				<tr height="30" id="commtr_1">
					<td nowrap class="tablelabel">活动地点：</td>
					<td align="left" class="tablecontent"><input type="text" name="place" style="width:400px;padding:2px"value="${obj.place}" /></td>
				</tr>
				<tr height="30" id="commtr_4">
					<td nowrap class="tablelabel">承办机构：</td>
					<td align="left" class="tablecontent"><input type="text"  name="agency" style="width:200px;padding:2px" value="${obj.agency}"/></td>
				</tr>
				<tr height="30"  id="commtr_5">
					<td nowrap class="tablelabel">默认出席状态：</td>
					<td align="left" class="tablecontent"><input type="text" name="astatus" style="width:200px;padding:2px" value="${obj.astatus}"/></td>
				</tr>
				<tr height="32" sizset="false" >
	              <td height="25" align="center"　 colspan="4" nowrap="nowrap" class="tablespecial">
	              	<font color="#F00"><strong>重要提示：如提交内容较多，请先在记事本或word中提前录好保存，然后再复制到内容框中，以免在线超时，引起信息丢失！</strong></font>
	             </td>
	            </tr>
	 			<tr>
					<td colspan="4" align="center" class="tablecontent">
						<div class="easyui-tabs" style="width:1024px;height:500px;"> 
							<div title="&nbsp;&nbsp;活动内容&nbsp;&nbsp;" style="padding:3px;overflow:hidden" align="center">
								<script id="editor" type="text/plain" style="width:99%;height:480px;">${obj.acontent}</script>
							</div>
							<div title="邀请人信息" style="padding:3px;height:480px;">  
								<table id="commcodegrid"></table> 
							</div>
							
						</div> 
					</td>
				</tr>
		</table>
	</td>
	</tr>
</table>
	</form>
</div>
</div>