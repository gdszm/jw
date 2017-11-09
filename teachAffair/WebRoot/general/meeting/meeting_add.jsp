<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<script type="text/javascript">
$(document).ready(function() {
	
		//var editor = UE.getEditor('editor'); ==>解决发布社情民意不渲染问题，改成以下两行
		
		var editor = new baidu.editor.ui.Editor();
		editor.render('editor');

		editor.addListener('ready',function(){
			 this.setContent('');
		});
	});
</script>
<div data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc;">
	<center>
		<form id="addForm" class="form-horizontal"  method="post">
			<input type="hidden"  id="meetId" name="meetId" />
			<input type="hidden"  id="content" name="content" />
			<input type="hidden"  id="commCodes" name="ids" />
<!-- 			<input type="hidden"  id="depid" name="depid" value="${sessionInfo.deptId}" /> -->
			<table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
		      <tr>
		        <td><table  class="tableborder" id="formTable" sizset="false" >
		          <tbody sizset="false">
		           <tr height="30">
		              <td height="45" colspan="4" align="center" nowrap="nowrap"  class="tablemain" >
				   			<div align="center"><strong>政协巴彦淖尔市${sessionSeco.year}年度会议</strong></div>
		              </td>
		            </tr>
		            <tr height="25" sizset="false" >
		              <td height="25" width="100px" nowrap="nowrap" class="tablelabel" >会议名称：</td>
		              <td height="25" colspan="3" class="tablecontent" sizset="false" align="left"><input type="text" id="meetName" name="meetName" value="" class="easyui-validatebox" data-options="required:true" style="width:99%;padding:2px" /></td>
		            </tr>
					<tr height="25" sizset="false" >
		              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">会议简称：</td>
		              <td height="25" width="99%" class="tablecontent" sizset="false"  align="left"><input name="shortName" type="text" value="" class="easyui-validatebox"  style="width:99%;padding:2px"  /></td>
		              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">会议类型：</td>
		              <td height="25" width="99%" class="tablecontent" sizset="false"  align="left">
		             	<input id="meetType" panelHeight="100px" name="meetType" style="width:150px;padding:2px;border:1px solid #000;">
		              </td>
		            </tr>
		            
		            <tr height="25" sizset="false" >
		              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">会议时间：</td>
		              <td height="25" width="99%" class="tablecontent" sizset="false"  align="left"><input class="easyui-datebox" name="beginTime" data-options="required:true"  style="width:150px"/>至<input class="easyui-datebox" name="endTime" data-options="required:true" style="width:150px"/>
		              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">承办单位：</td>
		              <td height="25" class="tablecontent" sizset="false"  align="left"><input name="depid" class="easyui-validatebox" id="depName" type="text"  style="width:99%;padding:2px"  />
		              </td>
		            </tr>
		            <tr height="25" sizset="false" >
		              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">默认出席状态：</td>
		              <td height="25" class="tablecontent"  sizset="false" align="left"><input id="attendStatus" panelHeight="100px" name="attendStatus" value="请选择..." style="width:150px;padding:2px;border:1px solid #000;"></td>
		              </td>
		              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">会议地点：</td>
		              <td height="25" class="tablecontent"  sizset="false" align="left"><input name="address" class="easyui-validatebox" id="address" type="text"  value="" data-options="required:true" style="width:99%;padding:2px"/></td>
		              </td>
		            </tr>
		            <tr height="32" sizset="false" >
		              <td height="25" align="center"　 colspan="4" nowrap="nowrap" class="tablespecial">
		              	<strong>重要提示：如提交内容较多，请先在记事本或word中提前录好保存，然后再复制到内容框中，以免在线超时，引起信息丢失！</strong>
		             </td>
		            </tr>
		            <tr>
						<td colspan="4" align="center" class="tablecontent">
							<div class="easyui-tabs" style="width:1024px;height:500px;"> 
								<div title="&nbsp;&nbsp;会议内容&nbsp;&nbsp;" style="padding:3px;overflow:hidden" align="center">
									<script id="editor" type="text/plain" style="width:99%;height:480px;"></script>
								</div>
								<div title="参会人员" style="padding:3px;height:480px;">  
									<table id="participantsgrid"></table> 
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
