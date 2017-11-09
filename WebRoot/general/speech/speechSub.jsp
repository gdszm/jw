<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>

<script type="text/javascript">
var editor;
var p;
$(document).ready(function() {
		editor = new baidu.editor.ui.Editor();
		editor.render('editor');

		editor.addListener('ready',function(){
			 this.setContent('');
		});
		
		
	});
//保存发言
function saveSpeech(){
	var f =$("#speechAddForm");
	$("#content").val(UE.getEditor('editor').getContent()); 
	add();
	f.form('submit', {
		url : '${pageContext.request.contextPath}/speech!save.do',
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

//提交发言
function submitSpeech(){
			var f =$("#speechAddForm"); 
			$("#content").val(UE.getEditor('editor').getContent());
			add();
			f.form('submit', {
				url : '${pageContext.request.contextPath}/speech!upDateOrAdd.do',
				success : function(d) {
					var json = $.parseJSON(d);
					if (json.success) {
						parent.dj.messagerConfirm('操作提示','提交成功！您是否继续提交新发言?',function(ok){  
							if (ok){
								//window.location.href="${pageContext.request.contextPath}/speech!speechAdd.do";
								document.getElementById("speechAddForm").reset();
								editor.setContent('');
							}else{
								window.parent.closetab();
							}
						});

					}
				}
		
			});
}

//附件上传
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
     file='<input type="text" style="width:100px" readonly id="f'+FileCount+'" name="f'+FileCount+'"/> <input type="button" value="附件上传" onclick="uploadFile('+FileCount+')"/>';

     $("#fil").append(br);
     $("#fil").append(file);
 
 }
function add(){
    var attNames=[];
	 for(var i=1;i<=FileCount;i++){
		  attNames.push($('#f'+i).val());
	 }
	 $('#attNames').val(attNames.join(','));
}
//附件上传部分 结束


//会议选择部分 开始
function meetingSelect(){
	p=dj.dialog({
		title : '选择所属会议',
		href : '${pageContext.request.contextPath}/speech!meetingSelect.do',
		width : 600,
		height : 360,
		left:350,
		top:100,
		buttons : [ {
			text : '确定',
			iconCls:'icon-ok',
			handler : function() {
				saveadd();
			}
		},{ 
			text: ' 取 消 ', 
			iconCls:'icon-cancel',
			handler: function() { 
				p.dialog('close'); 
			} 
		}],
		onLoad : function() {
			initMeeing();
		}
	});
}

//会议选择添加
function saveadd(){
	var rows=$('#meetinggrid').datagrid('getSelected');
	  if(rows){
		  $('#meetId').val(rows.meetId);
		  $('#meetNameId').val(rows.meetName);
		  $('#meetTypeNameId').val(rows.meetTypeName);
	  }else{
		  $('#meetId').val('');
		  $('#meetNameId').val('');
		  $('#meetTypeNameId').val(''); 
	  }
	  p.dialog('close');
}
//会议选择部分 结束

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
<form id="speechAddForm" class="form-horizontal"  method="post">
<input type="hidden"  id="idId" name="speakId" />
<input type="hidden"  id="content" name="content" />
<%--附件上传用--%>
<input type="hidden"  id="attNames" name="atts" value="" />

<table border="0" align="center" style="width:1024px;">
      <tr>
        <td><table  class="tableborder" id="formTable"  >
          <tbody  >
   			<tr height="30">
              <td height="45" colspan="4" align="middle" nowrap="nowrap" class="tablemain">
               	<div align="center"><strong>政协巴彦淖尔市${sessionSeco.year}年度会议发言</strong></div>
              </td>
            </tr>
            <tr height="25"  >
              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">发言标题：</td>
              <td height="25" colspan="3" class="tablecontent"  align="left">
              	<input type="text" id="title" name="title" class="easyui-validatebox" data-options="required:true" value="${speech.title}"  style="width:100%;" />
              </td>
            </tr>
            <tr height="25"  >
              <td height="25" width="140px" nowrap="nowrap" class="tablelabel">发言人：</td>
              <td height="25"  class="tablecontent"  align="left" width="40%">
              	<input  name="code" value="${sessionComm.code}" type="hidden"/>
              	<input name="name"  value="${sessionComm.name}" type="text" readonly="readonly" style="width:410px;" />
              </td>
              <td height="25" width="140px" nowrap="nowrap" class="tablelabel">
              	手&nbsp;机
               </td>	
              <td height="25"  class="tablecontent"  align="left">
              	<input name="telephone"  value="${sessionComm.telephone}" type="text" readonly="readonly" style="width:325px;" />
              </td>
            </tr>
            <tr height="25"  >
              <td height="25" width="140px" nowrap="nowrap" class="tablelabel">通讯地址：</td>
              <td height="25"  class="tablecontent"  align="left">
             	 <input name="comparyAddress"  value="${sessionComm.comparyAddress}" type="text" readonly="readonly" style="width:410px;" />
              </td>
              <td height="25" width="140px" nowrap="nowrap" class="tablelabel">电子邮箱：</td>
              <td height="25"  class="tablecontent"  align="left">
              	 <input name="email"  value="${sessionComm.email}" type="text" readonly="readonly" style="width:325px;" />
              </td>
            </tr>
            <tr height="25"  >
              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">所属会议：</td>
              <td height="25" class="tablecontent" align="left" style="width:320px;">
              	<input id="meetId"  name="meetId"  type="hidden" style="width:320px;" readonly="readonly"/>
              	<input id="meetNameId"  name="meetName"  class="easyui-validatebox" data-options="required:true" style="width:330px;" readonly="readonly"/>
              	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="meetingSelect()">选择</a>
              	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="$('#meetId').val('');$('#meetNameId').val('');$('#meetTypeNameId').val('');">取消</a>
              </td>
              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">会议类型：</td>
              <td height="25" class="tablecontent" colspan="3"  align="left" style="width:320px;">
              	<input id="meetTypeNameId"  name="meetTypeName"  class="easyui-validatebox" style="width:100%" readonly="readonly"/>
              </td>
            </tr>
            <tr height="32">
	              <td height="25" align="center"　 colspan="4" nowrap="nowrap" class="tablespecial">
	              	<font color="#F00"><strong>重要提示：如提交内容较多，请先在记事本或word中提前录好保存，然后再复制到内容框中，以免在线超时，引起信息丢失！</strong></font>
	             </td>
	        </tr>
 			 <tr >
	              <td height="25" colspan="4" align="center"  class="tablecontent"> 
					<script id="editor" type="text/plain" style="width:99%;height:650px;"></script>
				  </td>
	            </tr>
			
		<%--附件上传功能--%>
			<tr>
            <td height="25" width="80px" nowrap="nowrap" class="tablelabel">附件描述：</td>
              <td height="25"  class="tablecontent" colspan="3"  align="left">
					<input type="text" id="attsDepict" name="attsDepict" class="easyui-validatebox" style="width:99.9%;padding:2px" />
              </td>
            </tr>
			<tr height="25"  >
              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">附件列表：</td>
              <td id="fil" height="25" colspan="3" class="tablecontent"  align="left">
					<input type="text" style="width:100px" id="f1" name="f1" readonly >
					<input type="button" value="附件上传" onclick="uploadFile(1)">
	              	<input type="button" value="添加" onclick="addFile(this)"/>
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
			<a id="saveBtn" class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0)" onclick="saveSpeech()"> 保 存 </a>　
			<a id="submitBtn" class="easyui-linkbutton" data-options="iconCls:'icon-submit'" href="javascript:void(0)" onclick="javascript:submitSpeech()"> 提 交 </a>　
			<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)" onclick="javascript:window.parent.closetab();"> 关 闭 </a>
		</div>
</body>
</html>
