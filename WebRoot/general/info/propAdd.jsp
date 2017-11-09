<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<script type="text/javascript">

$(document).ready(function() {
		var editor = UE.getEditor('editor');
		editor.addListener('ready',function(){
			 this.setContent('');
		});
	});
//添加提案人
function append_add(){
	var rows=$('#tazgrid').datagrid('getSelections');
	var wyrows=$('#tawygrid').datagrid('getRows');
	if(rows.length>0){
		var fistFlg=$('#hostFlg').combobox('getValue');
		var fistFlgName=$('#hostFlg').combobox('getText');
		if(fistFlg!="" && fistFlg!="请选择..."){
			for(var i = 0; i<rows.length ;i++){
				var flag=0;
				for(var j=0;j<wyrows.length;j++){
					if(rows[i].code==wyrows[j].code){
						flag=1;
						break;
					}
				}
				if(flag==0){
					$('#tawygrid').datagrid('appendRow',{
						code:rows[i].code,
						name:rows[i].name,
						telephone:rows[i].telephone,
						email:rows[i].email,
						comparyPhone:rows[i].comparyPhone,
						comparyAddress:rows[i].comparyAddress,
						comparyPost:rows[i].comParyPost,
						groupCode:rows[i].groupCode,
						groupName:rows[i].groupName,
						hostFlg:fistFlg,
						hostFlgName:fistFlgName
					});
				}
			}
		}else{
			parent.dj.messagerAlert('提示', '请设置是否为第一提案人！', 'error');
			return;
		}
		parent.dj.messagerAlert('提示', '提案人添加成功！', 'success');	
	}else{
		parent.dj.messagerAlert('提示', '请选择要添加的记录！', 'error');
		return;
	}
}
</script>
<div data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc;">
<center>
<form id="taForm" class="form-horizontal"  method="post">
<input type="hidden"  id="sponsorIds" name="sponsorIds" />
<input type="hidden"  id="fistProposaler" name="fistProposaler" />
<input type="hidden"  id="hostFlgs" name="hostFlgs" />
<input type="hidden"  id="content" name="content" />
<input type="hidden"  id="proposalType" name="proposalType" />
<table border="0" align="center" cellpadding="0" cellspacing="0" style="width:1024px;">
      <tr>
        <td><table  class="tableborder" id="formTable" sizset="false" >
          <tbody sizset="false" >
            <tr height="30">
              <td height="45" colspan="4" align="middle" nowrap="nowrap" class="tablemain">
               <c:if test="${sessionSeco.period=='0'}">
		   			<div align="center"><input type="hidden" name="secondaryId" value="${sessionSeco.secondaryId}"/><strong>政协巴彦淖尔市第${fn:substring( sessionSeco.secondayName,0,fn:indexOf(sessionSeco.secondayName,'届')+1)}委员会第${fn:substring(sessionSeco.secondayName,fn:indexOf(sessionSeco.secondayName,'届')+1,fn:length(sessionSeco.secondayName))}会议提案</strong></div>
		   		</c:if>
		   		<c:if test="${sessionSeco.period=='1'}">
		   			<div align="center"><input type="hidden" name="secondaryId" value="${sessionSeco.secondaryId}"/><strong>政协巴彦淖尔市第${fn:substring( sessionSeco.secondayName,0,fn:indexOf(sessionSeco.secondayName,'届')+1)}委员会${sessionSeco.year}年度提案</strong></div>
		   		</c:if>
              </td>
            </tr>
            <tr height="25" sizset="false" >
              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">案&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;由：</td>
              <td height="25" colspan="3" class="tablecontent" sizset="false" align="left"><input type="text" id="title" name="title" class="easyui-validatebox" data-options="required:true" value=""  style="width:99%;padding:2px" /></td>
            </tr>
			<tr  height="25" sizset="false">
             
              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">建议办理单位：</td>
              <td height="25"　 colspan="3" class="tablecontent" sizset="false" align="left" ><input id="undertakeUnit" name="undertakeUnit"  style="width:99%;padding:2px" /></td>
              
            </tr>
            <tr height="25" sizset="false">
              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">可否附议：</td>
              <td height="25" width="300px" class="tablecontent" sizset="false"  align="left"><input name="secondFlg" id="secondFlg" data-options="required:true,editable:false,validType:'sel'" panelheight="auto" style="width:300px"/>
                 </td>
              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">可否公开：</td>
              <td height="25" width="300px" class="tablecontent" sizset="false"  align="left"><input name="webFlg" id="webFlg" data-options="required:true,editable:false,validType:'sel'" panelheight="auto" style="width:300px"/>
                  </td>
            </tr>
             <tr height="32" sizset="false" >
	              <td height="25" align="center"　 colspan="4" nowrap="nowrap" class="tablespecial">
	              	<font color="#F00"><strong>重要提示：如提案内容较多，请先在记事本或word中提前录好保存，然后再复制到内容框中，以免在线超时，引起信息丢失！</strong></font>
	             </td>
	            </tr>
 			<tr>
				<td colspan="4" align="center" class="tablecontent">
					<div class="easyui-tabs" style="width:1024px;height:auto;"> 
						<div title="&nbsp;&nbsp;提案内容&nbsp;&nbsp;" style="padding:3px;overflow:hidden" align="center">
							<script id="editor" type="text/plain" style="width:99%;height:480px;"></script>
						</div>
						<div title="提案人信息" style="padding:3px;height:480px;">  
							<table id="tawygrid"></table> 
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
<div id="tar_menu" class="taz_menu" style="padding:2px;height:auto">
	<div style="margin-bottom:1px">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="$('#win_taz_query').window('open')">添加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delete_add()">删除</a>
	</div>
</div>

<div id="win_taz_query" class="easyui-window" data-options="title:'添加提案人',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:650px;height:360px;padding:5px;">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
			<center>
			
			<table id="tar_queryTable">			
				<tr height="25">
					
					<td colspan="4">
					<form id="tar_queryForm">
					提案人：
							<input type="text"  name="name" style="width:180px;padding:2px"/>
					提案人分组：	<input id="groupCode" name="groupCode" style="width:120px;padding:2px"/>
						<a href="javascript:void(0);" class="easyui-linkbutton" onclick="tar_search();">搜索</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" onclick="tar_cleanSearch();">取消</a>
						</form>
					</td>
				
				</tr>
				<tr height="200" >
					<td colspan="4" nowrap> <table id="tazgrid"></table></td>
				</tr>
				<tr>
					<td nowrap>是否第一提案人：</td>
					<td colspan="3" ><input id="hostFlg" align="left" panelHeight="auto" name="hostFlg" style="width:180px;padding:2px"/></td>
					
				</tr>
			</table>
			
			</center>
		</div>
		<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;height:40px">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-add'" href="javascript:void(0)" onclick="append_add()"> 添 加 </a>
			<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#win_taz_query').window('close');"> 关 闭</a>
		</div>
	</div>
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