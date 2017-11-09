<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>政协巴彦淖尔市社情民意社会征集</title>
<jsp:include page="/public/inc.jsp"></jsp:include>
</head>
<script type="text/javascript">
	$(document).ready(function() {
		var editor = UE.getEditor('editor');
		editor.addListener('ready',function(){
			 this.setContent('');
		});
	});
	//提交处理(群众)
	function submitPublicPoll(){
	
				var f =$("#sqmyForm");
				$("#content").val(UE.getEditor('editor').getContent());
				
				//内容非空
				if(UE.getEditor('editor').hasContents())
				{	parent.dj.messagerConfirm('请确认', '点击提交后，信息不能再进行修改！', function(r) {
						if (r) {
								//提交表单
								f.form('submit', {
									url : '${pageContext.request.contextPath}/poll!publicAdd.do',
									success : function(d) {
										var json = $.parseJSON(d);
										if (json.success) {
											//清空表单
											$("#sqmyForm input").val("");
											//清空内容
											UE.getEditor('editor').setContent('');
											dj.messagerShow({
											msg : json.msg,
											title : '提示'
											});
											
										}
									}
								});
						}
					});
				} else {
					dj.messagerShow({
						msg : "社情民意内容不能为空！",
						title : '提示'
					});
					return;
				}
	}
</script> 

        
  <body>
    <div align="center"  >
	<div style="width:1018px; height:80px; background-image:url(style/images/top.jpg);" >
		  <table width="100%" height="80" border="0" cellpadding="0" cellspacing="0" bordercolor="#cf0101">
		    <tr>
		      <td colspan="2"><div id="logo"><img src="style/images/logo.png" width="63" height="53" /></div>
		          <div id="weizi">
		            <table width="100%" border="0" cellpadding="0" cellspacing="0">
		              <tr>
		                <td><img src="style/images/title.png" width="478" height="44" /></td>
		                
		              </tr>
		            </table>
		          </div></td>
		    </tr>
		  </table>
		</div>
	</div>
	<center>
			<form id="sqmyForm" class="form-horizontal"  method="post">
				<input type="hidden"  id="proposalId" name="proposalId" value="" />
				<input type="hidden"  id="hostFlgs" name="hostFlgs" value="1" />
				<input type="hidden"  id="content" name="content" />
				<input type="hidden"  id="proposalType" name="proposalType" value="${sessionComm.groupCode}" />
				<table width="1018px" border="0" align="center" cellpadding="0" cellspacing="0">
			      <tr>
			        <td><table  class="tableborder" id="formTable" sizset="false" >
			          <tbody sizset="false">
			           <tr height="30">
			              <td height="45" colspan="4" align="center" nowrap="nowrap"  class="tablemain" >
					   			<div align="center"><strong>政协巴彦淖尔市社情民意提交</strong></div>
			              </td>
			            </tr>
			            <tr height="25" sizset="false" >
			              <td height="25" width="100px" nowrap="nowrap" class="tablelabel" >标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题：</td>
			              <td height="25" colspan="3" class="tablecontent" sizset="false" align="left">
			             	 <input type="text" id="title" name="title" value="" class="easyui-validatebox" data-options="required:true" style="width:99.3%;padding:2px" />
			              </td>
			            </tr>
						<tr height="25" sizset="false" >
			              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">撰&nbsp;稿&nbsp;人：</td>
			              <td height="25" width="99%" class="tablecontent" sizset="false"  align="left">
			             	  <input type="text" id="writer" name="writer" value="" class="easyui-validatebox" data-options="required:true" style="width:98.8%;padding:2px" />
			              </td>
			              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">联系电话：</td>
			              <td height="25" width="99%" class="tablecontent" sizset="false"  align="left">
			              	<input type="text" id="phone" name="phone" value="" class="easyui-validatebox" data-options="required:true" style="width:98%;padding:2px" />
			              </td>
			            </tr>
			            <tr height="25" sizset="false" >
			              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">工作单位：</td>
			              <td height="25" class="tablecontent" sizset="false" align="left">
			              	<input type="text" id="unit" name="unit" value="" class="easyui-validatebox" data-options="required:true" style="width:98.8%;padding:2px" />
			              </td>
			              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">电子邮箱：</td>
			              <td height="25" class="tablecontent" sizset="false"  align="left">
			             	 <input type="text" id="email" name="email" value="" class="easyui-validatebox" data-options="required:true" style="width:98%;padding:2px" />
			              </td>
			            </tr>
			            <tr height="25" sizset="false" >
			              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">联系地址：</td>
			              <td height="25" class="tablecontent" sizset="false" align="left" colspan="3">
			              	<input type="text" id="address" name="address" value="" class="easyui-validatebox" style="width:99.3%;padding:2px" />
			              </td>
			              </td>
			            </tr>
			            <tr height="32" sizset="false" >
			              <td height="25" align="center"　 colspan="4" nowrap="nowrap" class="tablespecial">
			              	<strong>重要提示：如提交内容较多，请先在记事本或word中提前录好保存，然后再复制到内容框中，以免在线超时，引起信息丢失！</strong>
			             </td>
			            </tr>
			            <tr >
			              <td height="25" colspan="4" align="center"  class="tablecontent"> 
							<script id="editor" type="text/plain" style="width:100%;height:650px;"></script>
						  </td>
			            </tr>
			          </tbody>
			        </table></td>
			        </tr>
			    </table>
		</form>
	</center>
	<div data-options="region:'south',border:false" style="text-align:center;padding:5px 0;height:40px">
			<a id="submitBtn" class="easyui-linkbutton" data-options="iconCls:'icon-submit'" href="javascript:void(0)" onclick="javascript:submitPublicPoll()"> 提 交 </a>　
		</div>
	<div align="center"  >
		<table width="1018px" align="center" cellpadding="2" cellspacing="0" style="FILTER: Alpha( style=1,opacity=10,finishOpacity=100,startX=1,finishX= 1,startY=100,finishY=80);border-right:1px dotted #0099cc;border-left:1px dotted #0099cc;">
		  <tr> 
		    <td height="32" align="center" bgcolor="#0099CC" class="f9"><font color="#FFFFFF">Copyright (C) 2009 政协巴彦淖尔市委员会</font></td>
		  </tr>
		</table>
	</div>
  </body>
</html>
