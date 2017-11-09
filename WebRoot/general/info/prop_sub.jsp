<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript">
	
	$(document).ready(function() {
		var editor = UE.getEditor('editor');
		editor.addListener('ready',function(){
			 this.setContent('');
		});
		getcombobox('secondFlg','${pageContext.request.contextPath}/dic!combox.do?ctype=FYYGK');
		getcombobox('webFlg','${pageContext.request.contextPath}/dic!combox.do?ctype=FYYGK');
	});
    //添加提案
	function saveTa(){
		var f =$("#tatjForm");
		$("#content").val(UE.getEditor('editor').getContent()); 
		f.form('submit', {
			url : '${pageContext.request.contextPath}/prop!add.do',
			success : function(d) {
				var json = $.parseJSON(d);
				if (json.success) {
					$("#proposalId").val(json.obj);
					dj.messagerShow({
					msg : json.msg,
					title : '提示'
					});
				}
			}
	
		});
	}
    
    //提交提案
	function submitTa(){
		parent.dj.messagerConfirm('请确认', '点击提交后，提案不能再进行修改！', function(r) {
			if (r) {
				$("#content").val(UE.getEditor('editor').getContent());
				var f =$("#tatjForm"); 
				f.form('submit', {
					url : '${pageContext.request.contextPath}/prop!submit.do',
					success : function(d) {
						var json = $.parseJSON(d);
						if (json.success) {
							parent.dj.messagerConfirm('操作提示','提交成功！您是否继续提交新提案?',function(ok){  
								if (ok){  
									window.location.href="${pageContext.request.contextPath}/prop!propsub.do";  
								}else{
									//window.location.href="/zx/tatj/myta";
									window.parent.closetab();
								}
							});

						}
					}
			
				});
				
			}
		});
	}

    $.extend($.fn.validatebox.defaults.rules, {  
		sel: {  
			validator: function(value){ 
				return value != '请选择...';  
			},  
			message: '此项必须选择！'  
		}  
	});  
    
</script>
</head>

<body>
	<div class="easyui-layout" data-options="fit:true" class="cs-home-remark">
		<div data-options="region:'center',border:false" style="padding:10px;border:1px solid #ccc;" class="cs-home-remark">
			<center>
			<form id="tatjForm" class="form-horizontal"  method="post">
				<input type="hidden"  id="proposalId" name="proposalId" value="" />
				<input type="hidden"  id="hostFlgs" name="hostFlgs" value="1" />
				<input type="hidden"  id="content" name="content" />
				<input type="hidden"  id="proposalType" name="proposalType" value="${sessionComm.groupCode}" />
				<table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
			      <tr>
			        <td><table  class="tableborder" id="formTable" sizset="false" >
			          <tbody sizset="false">
			           <tr height="30">
			              <td height="45" colspan="4" align="center" nowrap="nowrap"  class="tablemain" >
			              	<c:if test="${sessionSeco.period=='0'}">
					   			<div align="center"><input type="hidden" name="secondaryId" value="${sessionSeco.secondaryId}"/><strong>政协巴彦淖尔市第${fn:substring( sessionSeco.secondayName,0,fn:indexOf(sessionSeco.secondayName,'届')+1)}委员会第${fn:substring(sessionSeco.secondayName,fn:indexOf(sessionSeco.secondayName,'届')+1,fn:length(sessionSeco.secondayName))}会议提案</strong></div>
					   		</c:if>
					   		<c:if test="${sessionSeco.period=='1'}">
					   			<div align="center"><input type="hidden" name="secondaryId" value="${sessionSeco.secondaryId}"/><strong>政协巴彦淖尔市第${fn:substring( sessionSeco.secondayName,0,fn:indexOf(sessionSeco.secondayName,'届')+1)}委员会${sessionSeco.year}年度提案</strong></div>
					   		</c:if>
			              </td>
			            </tr>
			            <tr height="25" sizset="false" >
			              <td height="25" width="100px" nowrap="nowrap" class="tablelabel" >案&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;由：</td>
			              <td height="25" colspan="3" class="tablecontent" sizset="false" align="left"><input type="text" id="title" name="title" value="" class="easyui-validatebox" data-options="required:true" style="width:99%;padding:2px" /></td>
			            </tr>
						<tr height="25" sizset="false" >
			              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">提案人：</td>
			              <td height="25" width="99%" class="tablecontent" sizset="false"  align="left"><input type="hidden" name="sponsorIds" value="${sessionComm.code}" /><input name="fistProposaler" type="text" class="bian" readonly="readonly" id="fistProposaler" style="width:99%" value="${sessionComm.name}" /></td>
			              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">联系电话：</td>
			              <td height="25" width="99%" class="tablecontent" sizset="false"  align="left"><input name="telephone" class="bian" id="telephone" type="text" readonly="readonly" value="${sessionComm.telephone}"  style="width:99%" />
			              </td>
			            </tr>
			            <tr height="25" sizset="false" >
			              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">联系地址：</td>
			              <td height="25" class="tablecontent" sizset="false" align="left"><input name="comparyAddress"  class="bian" id="comparyAddress" type="text" readonly="readonly" value="${sessionComm.comparyAddress}" style="width:98%;padding:2px" /></td>
			              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">电子邮箱：</td>
			              <td height="25" class="tablecontent" sizset="false"  align="left"><input name="email" class="bian" id="email" type="text" readonly="readonly" value="${sessionComm.email}"  style="width:99%" />
			              </td>
			            </tr>
			            <tr  height="25" sizset="false">
			              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">建议办理单位：</td>
			              <td height="25"　 colspan="3" class="tablecontent" sizset="false" align="left" ><input type="text" name="undertakeUnit" class="bian" id="undertakeUnit" style="width:99%;padding:2px" /></td>
			              
			            </tr>
			            <tr height="25" sizset="false">
			              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">可否附议：</td>
			              <td height="25"  class="tablecontent" sizset="false"  align="left">
			              	<input id="secondFlg" name="secondFlg" data-options="required:true,editable:false,validType:'sel'" value="" panelHeight="auto" style="width:300px;padding:2px;border:1px solid #000;"/>
			              </td>
			              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">可否公开：</td>
			              <td height="25"  class="tablecontent" sizset="false"  align="left">
			              	<input id="webFlg" name="webFlg" data-options="required:true,editable:false,validType:'sel'" value="" panelHeight="auto" style="width:300px;padding:2px;border:1px solid #000;"/>
			              </td>
			            </tr>
			            
			            <tr height="32" sizset="false" >
			              <td height="25" align="center"　 colspan="4" nowrap="nowrap" class="tablespecial">
			              	<strong>重要提示：如提案内容较多，请先在记事本或word中提前录好保存，然后再复制到内容框中，以免在线超时，引起信息丢失！</strong>
			             </td>
			            </tr>
			            <tr >
			              <td height="25" colspan="4" align="center"  class="tablecontent"> 
							<script id="editor" type="text/plain" style="width:99%;height:650px;"></script>
						  </td>
			            </tr>
			          </tbody>
			        </table></td>
			        </tr>
			    </table>
		</form>
	</center>
	</div>
		<div data-options="region:'south',border:false" style="text-align:center;padding:5px 0;height:40px">
			<a id="saveBtn" class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0)" onclick="saveTa()"> 保 存 </a>　
			<a id="submitBtn" class="easyui-linkbutton" data-options="iconCls:'icon-submit'" href="javascript:void(0)" onclick="javascript:submitTa()"> 提 交 </a>　
			<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:window.parent.closetab();"> 关 闭 </a>
		</div>
	</div>
</body>
</html>