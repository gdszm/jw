<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<script type="text/javascript">

$(document).ready(function() {
<%--		var editor = new baidu.editor.ui.Editor();--%>
<%--		editor.render('editor');--%>
<%--		editor.addListener('ready',function(){--%>
<%--			 this.setContent('${meetFile.content}');--%>
<%--				$('#meetFileAddForm input[name=content]').val('${meetFile.content}');--%>
<%--		});--%>
		$('#meetFileAddForm input[name=datatype]').combobox({
			url : '${pageContext.request.contextPath}/dic!combox.do?ctype=MEETINGTYPE',
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
	
var editor = new baidu.editor.ui.Editor({ 
    textarea:'content' 
}); 
editor.render("editor");	

//上传
//全局变量
	 var p;
    var br;
    var file;
    var img;
     // 增加文件
     function addFile(){
	       var FileCount=parseInt($("#FileCount").val(),10);  //上传文件总数
	       if(FileCount=="" || FileCount==undefined){
	    	   FileCount=0;
	       }
	       FileCount=FileCount+1;
	       br='<br id="b'+FileCount+'">';
	      file='<input id="no'+FileCount+'" type="hidden" value="kong"  />  <input type="text" style="width:100px" readonly id="f'+FileCount+'" name="f'+FileCount+'"/> <input type="button" value="上传" onclick="uploadFile('+FileCount+')"/>';
	      $("#fil").append(br);
	      $("#fil").append(file);
	      
	      
	      img='<img id="vImg'+FileCount+'" width="430px" /> ';
	      $("#ImgList").append(br);
	      $("#ImgList").append(img);
	       // $("#ImgList").append(imgdel);
	      $("#FileCount").val(FileCount); 
     }
 function uploadFile(num){
	  p = dj.dialog({
			title : '文件上传',
			href : '${pageContext.request.contextPath}/fileUpload!uploadFile.do',
			width : 320,
			height : 160,
			buttons : [ {
				text : '确定',
				iconCls:'icon-ok',
				handler : function() {
					doUpload(p,'${pageContext.request.contextPath}','f'+num,'vImg'+num);
				}
			},{ 
				text: ' 取 消 ', 
				iconCls:'icon-cancel',
				handler: function() { 
					p.dialog('close'); 
				} 
			}]
		});
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
<div data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc;">
<center>
<form id="meetFileAddForm" class="form-horizontal"  method="post">
<input type="hidden"  id="idId" name="dataId" value="${meetFile.dataId}"/>
<input type="hidden"  id="fromtype" name="fromtype" value="${meetFile.fromtype}" />
<%--上传用--%>
<input type="hidden"  id="attNames" name="atts" value="${meetFile.atts}" />
<table border="0" align="center" style="width:1024px;">
      <tr>
        <td><table  class="tableborder" id="formTable"  >
          <tbody>
          	<tr height="30">
				<td height="45" colspan="4" align="middle" nowrap="nowrap"
					class="tablemain">
					<div align="center">
						<strong>文件修改</strong>
					</div></td>
			</tr>
            <tr height="25"  >
              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">文件标题：</td>
              <td height="25" colspan="3" class="tablecontent"  align="left"><input type="text" id="title" name="title" class="easyui-validatebox" data-options="required:true" value="${meetFile.title}"  style="width:99.8%;" /></td>
            </tr>
             <tr height="25"  >
             	<td height="25" width="100px" nowrap="nowrap" class="tablelabel">关键字：</td>
              	<td height="25" colspan="3" class="tablecontent"  align="left">
              		<input type="text" id="key" name="key" class="easyui-validatebox"  value="${meetFile.key}"  style="width:575px;" />
              	</td>
             </tr>
            <tr height="25"  >
              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">文件类型：</td>
              <td height="25" colspan="3" class="tablecontent"  align="left" >
              	<input name=datatype style="width:100%;" value="${meetFile.datatype}"/>
              </td>
              
            </tr>
             <tr height="32" sizset="false" >
	              <td height="25" align="center"　 colspan="4" nowrap="nowrap" class="tablespecial">
	              	<strong>重要提示：如提交内容较多，请先在记事本或word中提前录好保存，然后再复制到内容框中，以免在线超时，引起信息丢失！</strong>
	             </td>
	            </tr>
	            <tr >
	              <td height="25" colspan="4" align="center"  class="tablecontent"> 
					<script id="editor" type="text/plain" style="width:99%;height:350px;">${meetFile.content}</script>
				  </td>
	            </tr>
	            
			<%--附件上传功能--%>
			<tr height="25"  >
              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">附件：<input type="button" value="添加" onclick="addFile()"/></td>
              <td id="fil" height="25" colspan="3" class="tablecontent"  align="left">
					<input type="hidden" id="FileCount" value="${fn:length(attList)}">	
	              	<c:forEach items="${attList}" var="att" varStatus="status">
				              			<input id="no${status.index+1}"  type="hidden" value="${att}" />
						              	<input type="text" style="width:100px" readonly id="f${status.index+1}" name="f${status.index+1}" value="${att}" >
										<input type="button" value="上传" onclick="uploadFile(${status.index+1})"><br/>
					</c:forEach>
              </td>
            </tr>
            <tr height="25"  >
              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">备注：</td>
              <td height="25" class="tablecontent"  align="left" colspan="3">
              	<textarea name="memo" style="width:99.8%;height: 50px; font-size: 14px; ">${meetFile.memo}</textarea>
              </td>
             </tr>
          </tbody>
        </table></td>
        </tr>
    </table>
   </form>
 </center>
</div>

