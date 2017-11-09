<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<script type="text/javascript">

$(document).ready(function() {

	var editor = new baidu.editor.ui.Editor();
	editor.render('editor');

	editor.addListener('ready',function(){
		 this.setContent('');
	});
});
//上传
var pfile;var br;var file;var img;var FileCount=1;
function uploadFile(num){
	pfile = dj.dialog({
		title : '文件上传',
		href : '${pageContext.request.contextPath}/fileUpload!uploadFile.do',
		width : 320,
		height : 160,
		buttons : [ {
			text : '确定',
			iconCls:'icon-ok',
			handler : function() {
				doUpload(pfile,'${pageContext.request.contextPath}','f'+num,'vImg'+num,'vSpan'+num);
				//img='<img id="vImg'+FileCount+'" width="98%" height="98%" /> ';
		        //$("#ImgList").append(br);
		        //$("#ImgList").append(img);
			}
		},{ 
			text: ' 取 消 ', 
			iconCls:'icon-cancel',
			handler: function() { 
				pfile.dialog('close'); 
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
<div data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc;">
<center>
<form id="meetFileAddForm" class="form-horizontal"  method="post">
<input type="hidden"  id="idId" name="dataId" />
<input type="hidden"  id="fromtype" name="fromtype" value="1" />
<input type="hidden"  id="content" name="content" />
<%--上传用--%>
<input type="hidden"  id="attNames" name="atts" value="" />

<table border="0" align="center" style="width:1024px;">
      <tr>
        <td><table  class="tableborder" id="formTable"  >
          <tbody  >
			<tr height="30">
				<td height="45" colspan="4" align="middle" nowrap="nowrap"
					class="tablemain">
					<div align="center">
						<strong>文件新增</strong>
					</div></td>
			</tr>
            <tr height="25"  >
              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">文件标题：</td>
              <td height="25" colspan="3" class="tablecontent"  align="left"><input type="text" id="title" name="title" class="easyui-validatebox" data-options="required:true" value="${meetFile.title}"  style="width:99.8%;" /></td>
            </tr>
            <tr>
              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">关键字：</td>
              <td height="25" colspan="3" class="tablecontent"  align="left">
               <input type="text" id="key" name="key" class="easyui-validatebox"  value="${meetFile.key}"  style="width:99.8%;" />
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
					<script id="editor" type="text/plain" style="width:99%;height:350px;"></script>
				  </td>
	            </tr>
		<%--附件上传功能--%>
			<tr height="25"  >
              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">附件：</td>
              <td id="fil" height="25" colspan="3" class="tablecontent"  align="left">
					<input type="text" style="width:100px" id="f1" name="f1" readonly >
					<input type="button" value="上传" onclick="uploadFile(1)">
	              	<input type="button" value="添加" onclick="addFile(this)"/>
              </td>
            </tr>
            <tr height="25"  >
              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">备注：</td>
              <td height="25" class="tablecontent"  align="left" colspan="3">
              	<textarea name="memo" style="width:99.8%;height: 50px; font-size: 14px; ">${meetFile.memo}</textarea>
              </td>
             </tr>
            
            <%--
            <tr height="25"  >
              	<td height="25" colspan="4" class="tablecontent">
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
<%--<div data-options="region:'south',border:false" style="text-align:center;padding:5px 0;height:40px">--%>
<%--			<a id="saveBtn" class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0)" onclick="saveMeetFile()"> 保 存 </a>　--%>
<%--			<a id="submitBtn" class="easyui-linkbutton" data-options="iconCls:'icon-submit'" href="javascript:void(0)" onclick="javascript:submitMeetFile()"> 提 交 </a>　--%>
<%--			<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:window.parent.closetab();"> 关 闭 </a>--%>
<%--</div>--%>
