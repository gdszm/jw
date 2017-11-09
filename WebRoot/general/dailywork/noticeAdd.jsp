<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
var editor;
$(document).ready(function() {
		editor = new baidu.editor.ui.Editor();
		editor.render('editor');

		editor.addListener('ready',function(){
			 this.setContent('');
		});
		
		$('#noticeAddForm input[name=noticeType]').combobox({
			url : '${pageContext.request.contextPath}/dic!combox.do?ctype=NOTICETYPE',
			valueField:'cvalue', 
			panelHeight:'100',
	        textField:'ckey',
	        required:true,
	        validType:'sel'
		});
		
<%--		var date=new Date();--%>
<%--		alert(date.format("yyyy-MM-dd"));--%>
<%--		$("#optTimeStartId").datebox("setValue", date.format("yyyy-MM-dd")); --%>
<%--		$('#optTimeStartId').datebox('setValue', '6/1/2012');--%>
<%--		--%>
		
	});
//保存公告
function saveNotice(){
	var f =$("#noticeAddForm");
	$("#content").val(UE.getEditor('editor').getContent()); 
	add();
	f.form('submit', {
		url : '${pageContext.request.contextPath}/notice!save.do',
		success : function(d) {
			var json = $.parseJSON(d);
			if (json.success) {
				$("#idId").val(json.obj);
				dj.messagerShow({
				msg : json.msg,
				title : '提示'
				});
			}
		}

	});
}

//提交公告
function submitNotice(){
			$("#content").val(UE.getEditor('editor').getContent());
			add();
			var f =$("#noticeAddForm"); 
			f.form('submit', {
				url : '${pageContext.request.contextPath}/notice!upDateOrAdd.do',
				success : function(d) {
					var json = $.parseJSON(d);
					if (json.success) {
						parent.dj.messagerConfirm('操作提示','提交成功！您是否继续提交新公告?',function(ok){  
							if (ok){
								//window.location.href="${pageContext.request.contextPath}/notice!noticeAdd.do";
								document.getElementById("noticeAddForm").reset();
								editor.setContent('');
							}else{
								window.parent.closetab();
							}
						});

					}
				}
		
			});
}





//上传
var pFile;var br;var file;var img;var FileCount=1;
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
				doUpload(pFile,'${pageContext.request.contextPath}','f'+num,'vImg'+num,'vSpan'+num);
				//img='<img id="vImg'+FileCount+'" width="98%" height="98%" /> ';
		        //$("#ImgList").append(br);
		        //$("#ImgList").append(img);
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

function addFile(){
     FileCount++;  
     br='<br id="b'+FileCount+'">';	      
     file=' <input type="text" style="width:100px" readonly id="f'+FileCount+'" name="f'+FileCount+'"/> <input type="button" value="上传" onclick="uploadFile('+FileCount+')"/>';
     $("#fil").append(br);
     $("#fil").append(file);
     
     //img='<img id="vImg'+FileCount+'" width="430px" height="566px"/> ';
     
     //img='<img id="vImg'+FileCount+'" width="430px" /> ';
     
     
     
//      img='<a id="vImg'+FileCount+'"/></a>';
//     $("#ImgList").append(br);
//     $("#ImgList").append(img);
 }
  
function add(){
    var attNames=[];
	 for(var i=1;i<=FileCount;i++){
		  attNames.push($('#f'+i).val());
	 }
	 $('#attNames').val(attNames.join(','));
}
//上传部分 结束

$.extend($.fn.validatebox.defaults.rules, {  
	sel: {  
		validator: function(value){ 
			return value != '请选择...';  
		},  
		message: '此项必须选择！'  
	}  
}); 

</script>

<body>
	<div class="easyui-layout" data-options="fit:true" class="cs-home-remark">
		<div data-options="region:'center',border:false" style="padding:10px;border:1px solid #ccc;" class="cs-home-remark">
		<center>
			<form id="noticeAddForm" class="form-horizontal"  method="post">
			<input type="hidden"  id="idId" name="id" />
			<input type="hidden"  id="content" name="content" />
			<%--上传用--%>
			<input type="hidden"  id="attNames" name="atts" value="" />
			
			<table border="0" align="center" style="width:1024px;">
			      <tr>
			        <td><table  class="tableborder" id="formTable"  >
			          <tbody  >
						<tr height="30">
			              <td height="45" colspan="6" align="middle" nowrap="nowrap" class="tablemain">
			               	<div align="center"><strong>公告发布</strong></div>
			              </td>
			            </tr>
			            <tr height="25"  >
			              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">公告标题：</td>
			              <td height="25" colspan="5" class="tablecontent"  align="left"><input type="text" id="title" name="title" class="easyui-validatebox" data-options="required:true" value=""  style="width:99%;padding:2px" /></td>
			            </tr>
			<%--            <tr height="25"  >--%>
			<%--              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">发布单位：</td>--%>
			<%--              <td height="25" colspan="5" class="tablecontent"  align="left"><input type="text" id="pubUnit" name="pubUnit" class="easyui-validatebox" data-options="required:true" value=""  style="width:99%;padding:2px" /></td>--%>
			<%--            </tr>--%>
			            <tr height="25"  >
			<%--              <td height="25"  nowrap="nowrap" class="tablelabel">是否回复：</td>--%>
			<%--              <td height="25" class="tablecontent"  align="left" style="width:100px;">--%>
			<%--              &nbsp;&nbsp;--%>
			<%--              <input name="reply" value="0" checked="checked" type="radio">&nbsp;&nbsp;否&nbsp;&nbsp;&nbsp;--%>
			<%--              <input name="reply" value="1" type="radio">&nbsp;&nbsp;是--%>
			<%--              </td>--%>
			               <td height="25" width="80px" nowrap="nowrap" class="tablelabel">公告类型：</td>
			              <td height="25" class="tablecontent"  align="left" style="width:200px;">
			              	<input name="noticeType" style="width:200px;"/>
			              </td>
			              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">有效日期至：</td>
			              <td height="25"  class="tablecontent"  align="left" style="width:200px;">
			             	 <input id="validDateId" type="text" class="easyui-datebox" name="validDate" data-options="required:true,showSeconds:false" style="width:200px;"></input>
			<%--             	&nbsp;&nbsp;<font color="red">默认为当天日期向后顺延一个星期</font>--%>
			              </td>
			            </tr>
			             <tr height="32"  >
				              <td height="25" align="center"　 colspan="6" nowrap="nowrap" class="tablespecial">
				              	<font color="#F00"><strong>重要提示：如提交内容较多，请先在记事本或word中提前录好保存，然后再复制到内容框中，以免在线超时，引起信息丢失！</strong></font>
				             </td>
				            </tr>
			 			<tr>
							<td colspan="6" align="center" class="tablecontent">
								<div  style="width:1024px;height:500px;"> 
									<div title="&nbsp;&nbsp;公告内容&nbsp;&nbsp;" style="padding:3px;overflow:hidden" align="center">
										<script id="editor" type="text/plain" style="width:99%;height:390px;"></script>
									</div>
			<%--						<div title="提交者信息" style="padding:3px;height:480px;">  --%>
			<%--							<table id="submittergrid"></table> --%>
			<%--						</div>--%>
									
								</div> 
							</td>
						</tr>
						
					<%--附件上传功能--%>
						<tr height="25"  >
			              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">附件：</td>
			              <td id="fil" height="25" colspan="5" class="tablecontent"  align="left">
								<input type="text" style="width:100px" id="f1" name="f1" readonly >
								<input type="button" value="上传" onclick="uploadFile(1)">
				              	<input type="button" value="添加" onclick="addFile(this)"/>
			              </td>
			            </tr>
			            <tr height="25"  >
			              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">备注：</td>
			              <td height="25" colspan="5" class="tablecontent"  align="left">
			              	<textarea name="memo" style="width:99%;height: 50px; font-size: 14px; " ></textarea>
			              </td>
			            </tr>
			            
			            <%--
			            <tr height="25"  >
			              	<td height="25" colspan="6" class="tablecontent">
				              	<div id="ImgList" class="div_allinline" align="center">
									<a id="vImg1"></a>
									    
								</div>
							</td>
			              </td>
			            </tr>
			            --%>
			            
			          </tbody>
			        </table></td>
			        </tr>
			    </table>
			   </form>
			 </center>
 
		</div>
		<div data-options="region:'south',border:false" style="text-align:center;padding:5px 0;height:40px">
					<a id="saveBtn" class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0)" onclick="saveNotice()"> 保 存 </a>　
					<a id="submitBtn" class="easyui-linkbutton" data-options="iconCls:'icon-submit'" href="javascript:void(0)" onclick="javascript:submitNotice()"> 提 交 </a>　
					<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:window.parent.closetab();"> 关 闭 </a>
		</div>
</body>
</html>
