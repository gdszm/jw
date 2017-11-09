<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<script type="text/javascript">
var editor = new baidu.editor.ui.Editor({ 
    textarea:'content' 
}); 
editor.render("editor");

$(document).ready(function() {
<%--		var editor = new baidu.editor.ui.Editor();--%>
<%--		editor.render('editor');--%>
<%--		editor.addListener('ready',function(){--%>
<%--			 this.setContent('${notice.content}');--%>
<%--				$('#noticeAddForm input[name=content]').val('${notice.content}');--%>
<%--		});--%>
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
<form id="noticeAddForm" class="form-horizontal"  method="post">
<input type="hidden"  id="idId" name="id" value="${notice.id}"/>
<%--上传用--%>
<input type="hidden"  id="attNames" name="atts" value="${notice.atts}" />
<%--<input type="hidden"  id="content" name="content" />--%>
<table border="0" align="center" style="width:1024px;">
      <tr>
        <td><table  class="tableborder" id="formTable"  >
          <tbody  >
			<tr height="30">
				<td height="45" colspan="4" align="middle" nowrap="nowrap"
					class="tablemain">
					<div align="center">
						<strong>政协巴彦淖尔市${sessionSeco.year}年度考试公告</strong>
					</div></td>
			</tr>
			<tr height="25"  >
              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">考试标题：</td>
              <td height="25" colspan="5" class="tablecontent"  align="left"><input type="text" id="title" name="title" class="easyui-validatebox" data-options="required:true" value="${notice.title}"  style="width:99%;padding:2px" /></td>
            </tr>
<%--            <tr height="25"  >--%>
<%--              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">发布单位：</td>--%>
<%--              <td height="25" colspan="5" class="tablecontent"  align="left"><input type="text" id="pubUnit" name="pubUnit" class="easyui-validatebox" data-options="required:true" value="${notice.pubUnit}"  style="width:99%;padding:2px" /></td>--%>
<%--            </tr>--%>
<%--            <tr height="25"  >--%>
<%--              <td height="25"  nowrap="nowrap" class="tablelabel">是否回复：</td>--%>
<%--              <td height="25" class="tablecontent"  align="left" style="width:100px;">--%>
<%--              &nbsp;&nbsp;--%>
<%--              <c:choose>--%>
<%--              <c:when test="${notice.reply==0}">--%>
<%--              	 <input name="reply" value="0" checked="checked" type="radio">&nbsp;&nbsp;否&nbsp;&nbsp;&nbsp;--%>
<%--             	 <input name="reply" value="1" type="radio">&nbsp;&nbsp;是--%>
<%--              </c:when>--%>
<%--               <c:when test="${notice.reply==1}">--%>
<%--              	 <input name="reply" value="0" type="radio">&nbsp;&nbsp;否&nbsp;&nbsp;&nbsp;--%>
<%--             	 <input name="reply" value="1" checked="checked"  type="radio">&nbsp;&nbsp;是--%>
<%--              </c:when>--%>
<%--              </c:choose>--%>
<%--              </td>--%>
               <td height="25" width="80px" nowrap="nowrap" class="tablelabel">考试类型：</td>
              <td height="25" class="tablecontent"  align="left" style="width:200px;">
              	<input name="noticeType" style="width:200px;" value="${notice.noticeType}"/>
              </td>
              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">有效日期至：</td>
              <td height="25"  class="tablecontent"  align="left" style="width:200px;">
             	 <input id="validDateId" type="text" class="easyui-datebox" name="validDate" data-options="required:true,showSeconds:false" style="width:200px;" value="${notice.validDate}"></input>
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
					<script id="editor" type="text/plain" style="width:99%;height:480px;">${notice.content}</script>
				</td>
			</tr>
			
			<%--附件上传功能--%>
			<tr height="25"  >
              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">附件：<input type="button" value="添加" onclick="addFile()"/></td>
              <td id="fil" height="25" colspan="5" class="tablecontent"  align="left">
					<input type="hidden" id="FileCount" value="${fn:length(attList)}">	
	              	<c:forEach items="${attList}" var="att" varStatus="status">
				              			<input id="no${status.index+1}"  type="hidden" value="${att}" />
						              	<input type="text" style="width:100px" readonly id="f${status.index+1}" name="f${status.index+1}" value="${att}" >
										<input type="button" value="上传" onclick="uploadFile(${status.index+1})"><br/>
					</c:forEach>
              </td>
            </tr>
             <tr height="25"  >
              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">备注：</td>
              <td height="25" colspan="5" class="tablecontent"  align="left">
              	<textarea name="memo" style="width:99%;height: 50px; font-size: 14px; ">${notice.memo}</textarea>
              </td>
            </tr>
            <%--
            <tr height="25"  >
              	<td height="25" colspan="6" class="tablecontent">
						<div id="ImgList" class="div_allinline">
		                   <c:forEach items="${attList}" var="att" varStatus="status">
		              			<img id="vImg${status.index+1}" src="${pageContext.request.contextPath}/upload/mobile/${att}" width="430px"/>
			              	</c:forEach>
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

