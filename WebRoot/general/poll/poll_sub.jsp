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
	});
    //添加社情民意
	function savePoll(){
		var f =$("#addForm");
		$("#content").val(UE.getEditor('editor').getContent()); 
		f.form('submit', {
			url : '${pageContext.request.contextPath}/poll!add.do',
			success : function(d) {
				var json = $.parseJSON(d);
				if (json.success) {
					$("#pollId").val(json.obj);
					dj.messagerShow({
					msg : json.msg,
					title : '提示'
					});
				}
			}
	
		});
	}
    
    //提交社情民意
	function submitPoll(){
		parent.dj.messagerConfirm('请确认', '点击提交后，信息不能再进行修改！', function(r) {
			if (r) {
				$("#content").val(UE.getEditor('editor').getContent());
				var f =$("#addForm"); 
				f.form('submit', {
					url : '${pageContext.request.contextPath}/poll!submit.do',
					success : function(d) {
						var json = $.parseJSON(d);
						if (json.success) {
							parent.dj.messagerConfirm('操作提示','提交成功！您是否继续提交新社情民意?',function(ok){  
								if (ok){  
									window.location.href="${pageContext.request.contextPath}/poll!pollsub.do";  
								}else{
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
			<form id="addForm" class="form-horizontal"  method="post">
				<input type="hidden"  id="pollId" name="pollId" />
				<input type="hidden"  id="content" name="content" />
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
			              <td height="25" width="100px" nowrap="nowrap" class="tablelabel" >标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题：</td>
			              <td height="25" colspan="3" class="tablecontent" sizset="false" align="left"><input type="text" id="title" name="title" value="" class="easyui-validatebox" data-options="required:true" style="width:99%;padding:2px" /></td>
			            </tr>
						<tr height="25" sizset="false" >
			              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">提交者：</td>
			              <td height="25" width="99%" class="tablecontent" sizset="false"  align="left"><input type="hidden" name="createMan" value="${sessionComm.code}" /><input name="createManName" type="text" class="bian" readonly="readonly" id="fistProposaler" style="width:99%" value="${sessionComm.name}" /></td>
			              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">所属专委会：</td>
			              <td height="25" width="99%" class="tablecontent" sizset="false"  align="left">
			              <input name="committee" type="hidden" id="committee" value="${sessionComm.committee}" />
			              <input name="committeeName" class="bian" id="committeeName" readonly="readonly" style="width:99%" value="${sessionComm.committeeName}"/>
			              </td>
			            </tr>
			            <c:if test="${sessionComm.groupCode!='1'}">
			            <tr height="25" sizset="false" >
			              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">撰稿人：</td>
			              <td height="25" width="99%" colspan="4" class="tablecontent" sizset="false"  align="left"><input name="writer" type="text" class="easyui-validatebox" style="width:99%;padding:2px"/></td>
			            </tr>
			            </c:if>
			            <tr height="25" sizset="false" >
			              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">联系电话：</td>
			              <td height="25" width="99%" class="tablecontent" sizset="false"  align="left"><input name="telephone" class="bian" id="telephone" type="text" readonly="readonly" value="${sessionComm.telephone}"  style="width:99%" />
			              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">电子邮箱：</td>
			              <td height="25" class="tablecontent" sizset="false"  align="left"><input name="email" class="bian" id="email" type="text" readonly="readonly" value="${sessionComm.email}"  style="width:99%" />
			              </td>
			            </tr>
			            <tr height="25" sizset="false" >
			              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">联系地址：</td>
			              <td height="25" class="tablecontent" colspan="3" sizset="false" align="left"><input name="comparyAddress"  class="bian" id="comparyAddress" type="text" readonly="readonly" value="${sessionComm.comparyAddress}" style="width:98%;padding:2px" /></td>
			              </td>
			            </tr>
			            <tr height="32" sizset="false" >
			              <td height="25" align="center"　 colspan="4" nowrap="nowrap" class="tablespecial">
			              	<strong>重要提示：如提交内容较多，请先在记事本或word中提前录好保存，然后再复制到内容框中，以免在线超时，引起信息丢失！</strong>
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
			<a id="saveBtn" class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0)" onclick="savePoll()"> 保 存 </a>　
			<a id="submitBtn" class="easyui-linkbutton" data-options="iconCls:'icon-submit'" href="javascript:void(0)" onclick="javascript:submitPoll()"> 提 交 </a>　
			<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:window.parent.closetab();"> 关 闭 </a>
		</div>
	</div>
</body>
</html>