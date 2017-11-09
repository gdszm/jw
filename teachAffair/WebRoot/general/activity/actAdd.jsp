<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<script type="text/javascript">
$(document).ready(function() {
	
		//var editor = UE.getEditor('editor'); ==>解决发布社情民意不渲染问题，改成以下两行
		
		var editor = new baidu.editor.ui.Editor();
		editor.render('editor');

		editor.addListener('ready',function(){
			 this.setContent('');
		});
		
		$('#addForm input[name=aspecies]').combobox({
		url : '${pageContext.request.contextPath}/dic!combox.do?ctype=ASPECIES',
		valueField:'cvalue', 
		panelHeight:'100',
        textField:'ckey',
        required:true,
        validType:'sel'
		});
	});
	
		
	$.extend($.fn.validatebox.defaults.rules, {  
	sel: {  
		validator: function(value){ 
			return value != '请选择...';  
		},  
		message: '此项必须选择！'  
	}  
	}); 
</script>
<div data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc;">
<div align="center" style="padding: 5px;overflow: hidden;">
	<form id="addForm"  method="post">
		<input type="hidden" name="secondaryId" value="${obj.secondaryId}"/>
		<input type="hidden" name="invitnumb" id="invitnumb" value="0"/>
		<input type="hidden" name="ids" id="commCode" value=""/>
		<input type="hidden"  id="acontent" name="acontent" />
<table border="0" align="center" cellpadding="0" cellspacing="0" style="width:1024px;">
    <tr>
      <td>
        <table class="tableborder" id="formTable">
	     	    <tr height="30">
	              <td height="45" colspan="4" align="middle" nowrap="nowrap" class="tablemain">
	               	<div align="center"><strong>政协巴彦淖尔市${sessionSeco.year}年度活动新增</strong></div>
	              </td>
	            </tr>
				<tr height="30">
					<td nowrap class="tablelabel">活动主题：</td>
					<td align="left" class="tablecontent"><input type="text" name="atheme"  class="easyui-validatebox" data-options="required:true,missingMessage:'请填写主题'" style="width:300px;padding:2px" /></td>
				</tr>
				<tr height="30">
					<td nowrap class="tablelabel">活动种类：</td>
					<td align="left" class="tablecontent" ><input type="text" name="aspecies" style="width:200px;padding:2px" /></td>
				</tr>
				<tr height="30" id="commtr_1">
					<td nowrap class="tablelabel">活动时间：</td>
					<td align="left" class="tablecontent"><input name="timebeg" class="easyui-datebox" data-options="required:true" />--<input name="timeend" class="easyui-datebox" data-options="required:true" /></td>
				</tr>
				<tr height="30" id="commtr_1">
					<td nowrap class="tablelabel">活动地点：</td>
					<td align="left" class="tablecontent"><input type="text" name="place" style="width:400px;padding:2px" /></td>
				</tr>
				<!-- <tr height="30" id="commtr_2">
					<td nowrap class="tablelabel">活动内容：</td>
					<td align="left" class="tablecontent"><textarea name="acontent"  style="width:400px;padding:2px"></textarea></td>
				</tr>
				<tr height="30" id="commtr_2">
					<td nowrap class="tablelabel">邀请人员：</td>
					<td align="left" class="tablecontent">
					<input type="text" name="commName" id="commName" style="width:70%;padding:2px" />
					<input type="text" name="ids" id="commCode" value=""/>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="$('#win_taz_query').window('open')">添加</a>
					</td>
				</tr> -->
				<tr height="30" id="commtr_4">
					<td nowrap class="tablelabel">承办机构：</td>
					<td align="left" class="tablecontent"><input type="text"  name="agency" style="width:200px;padding:2px" /></td>
				</tr>
				<tr height="30"  id="commtr_5">
					<td nowrap class="tablelabel">默认出席状态：</td>
					<td align="left" class="tablecontent"><input type="text" name="astatus" style="width:200px;padding:2px" /></td>
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
								<script id="editor" type="text/plain" style="width:99%;height:480px;"></script>
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