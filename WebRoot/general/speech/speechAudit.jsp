<%--发言审查--%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<script type="text/javascript">
var editor = new baidu.editor.ui.Editor({ 
    textarea:'content' 
}); 
editor.render("editor");

//会议选择部分 开始//
function meetingSelect(){
	pMeeting=dj.dialog({
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
				saveMeetingAdd();
			}
		},{ 
			text: ' 取 消', 
			iconCls:'icon-cancel',
			handler: function() { 
				pMeeting.dialog('close'); 
			}
		}],
		onLoad : function() {
			initMeeing();
		}
	});
}

//会议选择添加
function saveMeetingAdd(){
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
	  pMeeting.dialog('close');
}
//会议选择部分 结束//

//上传部分开始
	//全局变量
var pFile;var br;var file;var img;var FileCount;
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
					doUpload(pFile,'${pageContext.request.contextPath}','f'+num,'vImg'+num);
				}
			},{ 
				text: ' 取 消 ', 
				iconCls:'icon-cancel',
				handler: function() { 
					pFile.dialog('close'); 
				} 
			}]
		});
}
 // 增加文件
 function addFile(){
      FileCount=parseInt($("#FileCount").val(),10);  //上传文件总数
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
//上传部分 结束

function add(){
 	FileCount=parseInt($("#FileCount").val(),10); 
    var attNames=[];
	 for(var i=1;i<=FileCount;i++){
		  attNames.push($('#f'+i).val());
	 }
	 $('#attNames').val(attNames.join(','));
}


$(document).ready(function() {
		//转弃方向
		$('#speechEditForm input[name=discardType]').combobox(
			{
				url : '${pageContext.request.contextPath}/dic!combox.do?ctype=DISCARDTYPE',
				valueField:'cvalue', 
				panelHeight:'100',
		        textField:'ckey'
			}
		);
		if($('#useSituationId').val()=='3'){
			$("#zqfxTdId").show();
			$("#discardTypeTdId").show();
		} 
		//采用情况
		$('#speechEditForm input[name=useSituation]').combobox(
			{
				url : '${pageContext.request.contextPath}/dic!combox.do?ctype=USESITUATION',
				valueField:'cvalue', 
				panelHeight:'100',
		        textField:'ckey',
		        required:true,
		        validType:'sel',
		        onSelect: function(record){
					if(record.cvalue=='3') {
						$("#zqfxTdId").show();
						$("#discardTypeTdId").show();
						
						var data = $('#discardTypeId').combobox('getData');
						$("#discardTypeId ").combobox('select',data[0].cvalue);
						
						$('#speechEditForm input[name=discardType]').val("");
					} else {
					
						var data = $('#discardTypeId').combobox('getData');
						$("#discardTypeId ").combobox('select',data[0].cvalue);
						
						$('#speechEditForm input[name=discardType]').val("");	
						$("#zqfxTdId").hide();
						$("#discardTypeTdId").hide();
					
					}
				}
			}
		);
		
});
	
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
<form id="speechEditForm" class="form-horizontal"  method="post">
<input type="hidden"  id="idId" name="speakId" value="${speech.speakId}" />
<%--<input type="hidden"  id="content" name="content" />--%>
<%--附件上传用--%>
<input type="hidden"  id="attNames" name="atts" value="${speech.atts}" />
<table border="0" align="center" style="width:1024px;">
      <tr>
        <td><table  class="tableborder" id="formTable"  >
          <tbody  >
          	<tr height="30">
              <td height="45" colspan="4" align="middle" nowrap="nowrap" class="tablemain">
               	<div align="center"><strong>政协巴彦淖尔市${sessionSeco.year}年度会议发言</strong></div>
              </td>
            </tr>
            <tr height="30">
              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">发言标题：</td>
              <td height="25" colspan="3" class="tablecontent"  align="left">
              	<input type="text" id="title" name="title" class="easyui-validatebox" data-options="required:true" value="${speech.title}"  style="width:99.8%;" />
              </td>
            </tr>
            <tr height="25"  >
              <td height="25" width="140px" nowrap="nowrap" class="tablelabel">发言人：</td>
               <td height="25"  class="tablecontent"  align="left" width="40%">
              	<input id="codeId" name="code"  type="hidden" value="${speech.code}"/>
              	<input id="nameId" name="name" value="${speech.name}"  class="easyui-validatebox" data-options="required:true" style="width:330px;" readonly="readonly" />
              	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="$('#win_wy_query').window('open')">选择</a>
              	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="$('#codeId').val('');$('#nameId').val('');$('#telephoneId').val('');$('#comparyAddressId').val('');$('#emailId').val('');">取消</a>
              </td>
              <td height="25" width="140px" nowrap="nowrap" class="tablelabel">
              	手&nbsp;机
               </td>	
              <td height="25"  class="tablecontent"  align="left">
              	<input id="telephoneId" name="telephone"  value="${speech.telephone}" type="text" readonly="readonly" style="width:325px;" />
              </td>
            </tr>
            <tr height="25"  >
              <td height="25" width="140px" nowrap="nowrap" class="tablelabel">通讯地址：</td>
              <td height="25"  class="tablecontent"  align="left">
             	 <input id="comparyAddressId" name="comparyAddress"  value="${speech.comparyAddress}" type="text" readonly="readonly" style="width:410px;" />
              </td>
              <td height="25" width="140px" nowrap="nowrap" class="tablelabel">电子邮箱：</td>
              <td height="25"  class="tablecontent"  align="left">
              	 <input id="emailId" name="email"  value="${speech.email}" type="text" readonly="readonly" style="width:325px;" />
              </td>
            </tr>
             <tr height="25"  >
              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">所属会议：</td>
              <td height="25" class="tablecontent" align="left" style="width:320px;">
              	<input id="meetId"  name="meetId"  type="hidden" style="width:320px;" value="${speech.meetId}"/>
              	<input id="meetNameId"  name="meetName" value="${speech.meetName}" class="easyui-validatebox" data-options="required:true" style="width:330px;" readonly="readonly"/>
              	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="meetingSelect()">选择</a>
              	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="$('#meetId').val('');$('#meetNameId').val('');$('#meetTypeNameId').val('');">取消</a>
              </td>
              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">会议类型：</td>
              <td height="25" class="tablecontent" colspan="3"  align="left" style="width:320px;">
              	<input id="meetTypeNameId"  name="meetTypeName"  class="easyui-validatebox" value="${speech.meetTypeName}" style="width:100%" readonly="readonly" />
              </td>
            </tr>
            <tr height="25"  >
              <td height="25" width="20px" nowrap="nowrap" class="tablelabel">采用情况：</td>
              <td height="25" style="width:200px;" class="tablecontent"  align="left">
              	<input id="useSituationId"  name="useSituation" style="width:409px;" value="${speech.useSituation}" />
              </td>
              <td id="zqfxTdId"  height="25" width="20px" nowrap="nowrap" class="tablelabel" style="display:none">转弃方向：</td>
              <td id="discardTypeTdId"  height="25" style="display:none" class="tablecontent"  align="left">
              	<input id="discardTypeId"  name="discardType" style="width:325px;" value="${speech.discardType}" />
              </td>
            </tr>
            <tr height="32">
	              <td height="25" align="center"　 colspan="4" nowrap="nowrap" class="tablespecial">
	              	<font color="#F00"><strong>重要提示：如提交内容较多，请先在记事本或word中提前录好保存，然后再复制到内容框中，以免在线超时，引起信息丢失！</strong></font>
	             </td>
	        </tr>
 			 <tr >
	              <td height="25" colspan="4" align="center"  class="tablecontent"> 
					<script id="editor" type="text/plain" style="width:99%;height:650px;">${speech.content}</script>
				  </td>
	            </tr>
			
		<%--附件上传功能--%>
		    <tr>
              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">附件描述：</td>
              <td height="25"  class="tablecontent" colspan="3"  align="left">
					<input type="text" id="attsDepict" name="attsDepict" value="${speech.attsDepict}" class="easyui-validatebox" style="width:99.4%;padding:2px" />
              </td>
            </tr>
			<%--附件上传功能--%>
			<tr height="25"  >
              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">附件列表：<input type="button" value="添加" onclick="addFile()"/></td>
              <td id="fil" height="25" colspan="5" class="tablecontent"  align="left">
					<input type="hidden" id="FileCount" value="${fn:length(attList)}">	
	              	<c:forEach items="${attList}" var="att" varStatus="status">
				              			<input id="no${status.index+1}"  type="hidden" value="${att}" />
						              	<input type="text" style="width:100px" readonly id="f${status.index+1}" name="f${status.index+1}" value="${att}" >
										<input type="button" value="上传" onclick="uploadFile(${status.index+1})"><br/>
					</c:forEach>
              </td>
            </tr>
          </tbody>
        </table></td>
        </tr>
    </table>
   </form>
 </center>
</div>
<%--弹出选择发言人窗口--%>
<div id="win_wy_query" class="easyui-window" data-options="title:'添加提交者',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:650px;height:360px;padding:5px;">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
			<center>
			<table id="tar_queryTable">			
				<tr height="25">
					<td colspan="4">
					<form id="tjr_queryForm">
					提交者：
						<input id="wyNameId"  type="text" name="name" style="width:450px;padding:2px"/>
<!-- 					提交者分组：	<input id="groupCode" name="groupCode" style="width:120px;padding:2px"/> -->
						<a href="javascript:void(0);" class="easyui-linkbutton" onclick="tjr_search();">搜索</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" onclick="tjr_cleanSearch();">取消</a>
					</form>
					</td>
				
				</tr>
				<tr height="200" >
					<td colspan="4" nowrap> <table id="tjzgrid"></table></td>
				</tr>
			</table>
			</center>
		</div>
		<div data-options="region:'south',border:false" style="text-align:center;padding:5px 0;height:40px">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="tjr_SelectConfirm();">确定</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#win_wy_query').window('close');"> 关 闭 </a>
		</div>
	</div>
</div>