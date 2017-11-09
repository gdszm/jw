<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>	
<script type="text/javascript">
//文件上传
function doUploadPic(p,url,fileInput,imgName)
	{
		if($('#file').val()=="")
		{
			alert("请你选择要上传的文件！");
			$('#file').focus();
			return false;
		}
		else
		{
		 var f = p.find('form');
			f.form('submit', {
				url : url+'/fileUpload!upload.do',
				success : function(d) {
					var json = $.parseJSON(d);
					if (json.success) {
						var oldfilename=$('#'+fileInput).val();
						$.ajax({
							url :url+'/fileUpload!doNotNeedAuth_delImage.do?oldFileName='+oldfilename
						});
						$('#'+fileInput).val(json.obj);
						$('#'+imgName).attr("src",url+"/upload/mobile/"+json.obj);
					//	$('#'+imgName).append(json.obj);
						p.dialog('close');
					}
						dj.messagerShow({
						msg : json.msg,
						title : '提示'
					});
				}
			});
		}
  }
  
var pFile;
function uploadFile(num){
	pFile = dj.dialog({
		title : '文件上传',
		href : '${pageContext.request.contextPath}/fileUpload!uploadFile.do',
		width : 320,
		height : 160,
		buttons : [ {
			text : '确定',
			iconCls:'icon-ok',
			handler : function() {
				doUploadPic(pFile,'${pageContext.request.contextPath}','f'+num,'vImg'+num);
			}
		},{ 
			text: ' 取 消 ', 
			iconCls:'icon-cancel',
			handler: function() { 
				pFile.dialog('close'); 
			} 
		}],
		
	});
}

</script>
<div data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc;">
<div align="center" style="padding: 5px;overflow: hidden;">
		<form id="addForm" method="post">
			<input type="hidden" name="code" /> 
			<input type="hidden" id="oldtelephone" name="oldtelephone" />
			<input type="hidden"name="groupCode" value="1" />
			<input type="hidden"  id="attNames" name="atts" value="" />
			<table border="0" align="center" cellpadding="0" cellspacing="0" style="width:1024px;">
				<tr>
					<td>
						<table class="tableborder" id="formTable">
							<tr height="40">
				              <td height="45" colspan="5" align="middle" nowrap="nowrap" class="tablemain" width="100%">
				               	<div align="center"><strong>政协巴彦淖尔市委员信息</strong></div>
				              </td>
				            </tr>
				            <tr height="40" class="tablelabel">
				          		<td nowrap class="tablelabel" width="10%">委&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;员：</td>
								<td align="left" class="tablecontent" width="20%">
									<input type="text"  name="name" style="width:98%;padding:2px" />
								</td>
								<td nowrap class="tablelabel" width="10%">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</td>
								<td align="left" class="tablecontent" width="20%">
									<input name="sex" style="width:369px;padding:2px" />
								</td>
								<td align="center" class="tablecontent" width="10%" rowspan="5" style="text-align: center;">
									<input type="hidden" style="width:100px" id="f1" name="picName">
									<img id="vImg1" src="${pageContext.request.contextPath}/style/images/man.jpg" style="height:160px;width:138px;text-align:center;margin-bottom:3px;border: 1px dotted #95B8E7;padding:2px;">
									<a class="easyui-linkbutton" data-options="iconCls:'icon-submit'" href="javascript:void(0)" onclick="javascript:uploadFile(1);">上传照片</a>
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">专&nbsp;&nbsp;委&nbsp;&nbsp;会：</td>
								<td align="left" class="tablecontent" width="20%"><input name="committee" style="width:300px;padding:2px" /></td>
								<td nowrap class="tablelabel" width="10%">党&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;派：</td>
								<td align="left" class="tablecontent" width="20%"><input name="partyCode" style="width:369px;padding:2px"/></td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">界&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</td>
								<td align="left" class="tablecontent" width="20%"><input name="circleCode" style="width:300px;padding:2px" /></td>
								<td nowrap class="tablelabel" width="10%">学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位：</td>
								<td align="left" class="tablecontent" width="20%"><input name="degreeCode"  style="width:369px;padding:2px" /></td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;历：</td>
								<td align="left" class="tablecontent" width="20%"><input  name="eduCode"  style="width:300px;padding:2px" /></td>
								<td nowrap class="tablelabel" width="10%">出生年月：</td>
								<td align="left" class="tablecontent" width="20%">
									<input type="text" class="easyui-datebox" name="birthDate" style="width:369px;padding:2px" />
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;业：</td>
								<td align="left" class="tablecontent" width="20%">
									<input type="text"  name="vocation" style="width:98%;padding:2px"/>
								</td>
								<td nowrap class="tablelabel" width="10%">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称：</td>
								<td align="left" class="tablecontent" width="20%">
									<input type="text"  name="title" style="width:98%;padding:2px" />
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;务：</td>
								<td align="left" class="tablecontent" width="20%"><input name="job"  style="width:98%;padding:2px" /></td>
								<td nowrap class="tablelabel" width="10%">电子邮箱：</td>
								<td align="left" class="tablecontent" width="20%" colspan="2">
									<input type="text" name="email" style="width:99%;padding:2px" />
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族：</td>
								<td align="left" class="tablecontent" width="20%">
									<input name="nation" style="width:300px;padding:2px" />
								</td>
								<td nowrap class="tablelabel" width="10%">手&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机：</td>
								<td align="left" class="tablecontent" width="20%" colspan="2">
									<input type="text" name="telephone" class="easyui-validatebox"  
									data-options="required:true,validType:'length[7,12]',missingMessage:'请填电话号码\n登录名默认与电话号码相同'" 
									style="width:99%;padding:2px">
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">固定电话：</td>
								<td align="left" class="tablecontent" width="20%">
									<input type="text" name="comparyPhone" class="easyui-validatebox"  style="width:98%;padding:2px">
								</td>
								<td nowrap class="tablelabel" width="10%">有效届次：</td>
								<td align="left" class="tablecontent" width="20%" colspan="2">
									<input name="secondaryCode" data-options="required:true,editable:false,validType:'sel'" multiple="true" style="width:515px;padding:2px" />
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">单位名称：</td>
								<td align="left" class="tablecontent" width="20%" colspan="4">
									<input type="text" name="companyName" style="width:99.4%;padding:2px" />
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">通讯地址：</td>
								<td align="left" class="tablecontent" width="20%">	
									<input type="text" name="comparyAddress" style="width:98%;padding:2px" />
								</td>
								<td nowrap class="tablelabel" width="10%">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编：</td>
								<td align="left" class="tablecontent" width="20%" colspan="2">
									<input type="text" name="comparyPost" style="width:99%;padding:2px" />
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">家庭地址：</td>
								<td align="left" class="tablecontent" width="20%" colspan="4">
									<input type="text" name="familyAddress" style="width:99.4%;padding:2px" />
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：</td>
								<td align="left" class="tablecontent" width="20%" colspan="4">
									<input name="status" data-options="required:true,editable:false,validType:'sel'" style="width:910px;padding:2px" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
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