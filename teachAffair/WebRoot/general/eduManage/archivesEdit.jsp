<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>	
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<script type="text/javascript">
//照片文件上传
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
//照片文件上传

//材料上传
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
	      file='<input id="no'+FileCount+'" type="hidden" value="kong"/>  <input type="text" style="width:150px" readonly id="fCert'+
	      FileCount+'" name="fCert'+FileCount+'"/>&nbsp;<input type="button" value="上传" onclick="uploadCertFile('+FileCount
	      +')"/>&nbsp;&nbsp;材料说明：<input style="width:350px" id="certInstr'+FileCount
	      +'" name="certInstr'+FileCount+'" value="" type="text">';
	      $("#fil").append(br);
	      $("#fil").append(file);
	      
	      img='<br/><img id="vCertImg'+FileCount+'" width="230px" /> ';
	     // $("#ImgList").append(br);
	      $("#fil").append(img);
	       // $("#ImgList").append(imgdel);
	      $("#FileCount").val(FileCount); 
     }
	function uploadCertFile(num){
	 p = dj.dialog({
		title : '文件上传',
		href : '${pageContext.request.contextPath}/fileUpload!uploadFile.do',
		width : 320,
		height : 160,
		buttons : [ {
			text : '确定',
			iconCls:'icon-ok',
			handler : function() {
				doUploadPic(p,'${pageContext.request.contextPath}','fCert'+num,'vCertImg'+num);
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
//材料上传

//会议选择部分 开始//
var pMeeting;
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



//会议选择部分 开始//
var pMeeting;
function archSelect(){
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


//添加家庭成员（添加弹出的） 开始
function fmSubmit() {
	$('#fmForm').form('submit', {
		url : '${pageContext.request.contextPath}/familyMem!save.do?familyId='+'${archives.id}',
		success : function(d) {
			var json = $.parseJSON(d);
			if (json.success) {
				$('#fmForm').form('reset');
				$('#win_fm_query').window('close');
				$('#submittergrid').datagrid('reload');
			}
			parent.dj.messagerShow({
				msg : json.msg,
				title : '提示'
			});
		}
	});
	
}
//添加家庭信息（添加弹出的）结束

//添加奖惩记录（添加弹出的） 开始
function apSubmit() {
	$('#apForm').form('submit', {
		url : '${pageContext.request.contextPath}/awardPenalty!save.do?archNo='+'${archives.archNo}',
		success : function(d) {
			var json = $.parseJSON(d);
			if (json.success) {
				$('#apForm').form('reset');
				$('#win_ap_query').window('close');
				$('#apGrid').datagrid('reload');
			}
			parent.dj.messagerShow({
				msg : json.msg,
				title : '提示'
			});
		}
	});
	
}
//添加奖惩记录（添加弹出的）结束

//添加教育经历（添加弹出的） 开始
function eeSubmit() {
	$('#eeForm').form('submit', {
		url : '${pageContext.request.contextPath}/eduExp!save.do?archNo='+'${archives.archNo}',
		success : function(d) {
			var json = $.parseJSON(d);
			if (json.success) {
				$('#eeForm').form('reset');
				$('#win_ee_query').window('close');
				$('#eeGrid').datagrid('reload');
			}
			parent.dj.messagerShow({
				msg : json.msg,
				title : '提示'
			});
		}
	});
	
}
//添加教育经历（添加弹出的）结束

//添加家庭成员（添加弹出的） 开始
function weSubmit() {
	$('#weForm').form('submit', {
		url : '${pageContext.request.contextPath}/workExp!save.do?archNo='+'${archives.archNo}',
		success : function(d) {
			var json = $.parseJSON(d);
			if (json.success) {
				$('#weForm').form('reset');
				$('#win_we_query').window('close');
				$('#weGrid').datagrid('reload');
			}
			parent.dj.messagerShow({
				msg : json.msg,
				title : '提示'
			});
		}
	});
	
}
//添加家庭信息（添加弹出的）结束
$(document).ready(function() {
	$('#addForm input[name=dept]').combobox({
		url : '${pageContext.request.contextPath}/dic!combox.do?ctype=yxb',
		valueField:'cvalue', 
		panelHeight:'100',
        textField:'ckey',
        required:true,
        validType:'sel'
	});
	$('#addForm input[name=major]').combobox({
		url : '${pageContext.request.contextPath}/dic!combox.do?ctype=major',
		valueField:'cvalue', 
		panelHeight:'100',
        textField:'ckey',
        required:true,
        validType:'sel'
	});
	$('#sexId').combobox({
		url : '${pageContext.request.contextPath}/dic!combox.do?ctype=SEX',
		valueField:'cvalue', 
		panelHeight:'100',
        textField:'ckey',
        required:true,
        validType:'sel'
	});
});

</script>
<div data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc;">
<div align="center" style="padding: 5px;overflow: hidden;">
		<form id="addForm" method="post">
			<input type="hidden" name="archNo" value="${archives.archNo}" /> 
			<input type="hidden" name="baseInfoId" value="${archives.baseInfoId}" />
			<input type="hidden" name="id" value="${archives.id}" />
			<input type="hidden" id="certFilesId" name="certFiles" value="${archives.certFiles}" />
			<table border="0" align="center" cellpadding="0" cellspacing="0" style="width:800px;">
				<tr>
					<td>
						<table class="tableborder" id="formTable">
							<tr height="40">
				              <td height="45" colspan="5" align="middle" nowrap="nowrap" class="tablemain" width="100%">
				               	<div align="center"><strong>档案修改</strong></div>
				              </td>
				            </tr>
				            <tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">档案编号：</td>
								<td align="left" class="tablecontent" width="40%">
					              	<p style="height:15px;line-height:15px;padding-left:2px;">${archives.archNo}</p>
								</td>
								<td nowrap class="tablelabel" width="10%">档案更新日期：</td>
								<td  align="left" class="tablecontent" width="40%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${archives.uptTime}</p>
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">紧急情况下联系号码：</td>
								<td align="left" class="tablecontent" width="20%">
									<input type="text"  name="urgentTel" value="${archives.urgentTel}" class="easyui-validatebox" data-options="required:true" style="width:98%;padding-left:2px"/>
								</td>
								<td nowrap class="tablelabel" width="10%">毕业学校：</td>
								<td colspan="2" align="left" class="tablecontent" width="20%">
									<input type="text"  name="gradSchool" value="${archives.gradSchool}" style="width:98%;padding-left:2px"/>
								</td>
							</tr>
							<tr height="60">
				              <td nowrap class="tablelabel" width="10%">自我评价：</td>
				              <td colspan="4" class="tablecontent"  align="left">
				              	<textarea name="selfAppr" style="width:99.8%;height:100px;font-size: 14px;">${archives.selfAppr}</textarea>
				              </td>
				            </tr>
							<tr height="60">
				              <td nowrap class="tablelabel" width="10%">备注：</td>
				              <td colspan="4" class="tablecontent"  align="left">
				              	<textarea name="remark" style="width:99.8%;height:100px;font-size: 14px;">${archives.remark}</textarea>
				              </td>
				            </tr>
						</table>
					</td>
				</tr>
			</table>
			<div id="tt" class="easyui-tabs" style="width:800px;">
				<div title="人员基本信息" data-options="iconCls:'icon-role'">
					<table class="tableborder" id="formTable">
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="20%">姓名：</td>
								<td align="left" class="tablecontent" width="25%">
									<input type="text"  name="name" value="${archives.name}" class="easyui-validatebox" data-options="required:true" style="width:98%;padding-left:2px"/>
								</td>
								<td nowrap class="tablelabel" width="20%">曾用名：</td>
								<td align="left" class="tablecontent" width="25%">
									<input type="text"  name="nickName" value="${archives.nickName}" style="width:98%;padding-left:2px"/>
								</td>
								<td align="center" class="tablecontent" width="10%" rowspan="5" style="text-align: center;">
									<input type="hidden" style="width:100px" id="f1" name="picName" value="${archives.picName}">
									<c:if test="${!empty archives.picName}">
										<img id="vImg1" src="${pageContext.request.contextPath}/upload/mobile/${archives.picName}" style="height:160px;width:138px;text-align:center;margin-bottom:3px;border: 1px dotted #95B8E7;padding:2px;">
									</c:if>
									<c:if test="${empty archives.picName}">
										<c:if test="${archives.sex==1}">
										<img id="vImg1" src="${pageContext.request.contextPath}/style/images/man.jpg" style="height:160px;width:138px;text-align:center;margin-bottom:3px;border: 1px dotted #95B8E7;padding:2px;">
										</c:if>
									</c:if>
									<c:if test="${empty archives.picName}">
										<c:if test="${archives.sex==2}">
										<img id="vImg1" src="${pageContext.request.contextPath}/style/images/woman.jpg" style="height:160px;width:138px;text-align:center;margin-bottom:3px;border: 1px dotted #95B8E7;padding:2px;">
										</c:if>
									</c:if>
									<c:if test="${empty archives.picName}">
										<c:if test="${empty archives.sex}">
										<img id="vImg1" src="${pageContext.request.contextPath}/style/images/man.jpg" style="height:160px;width:138px;text-align:center;margin-bottom:3px;border: 1px dotted #95B8E7;padding:2px;">
										</c:if>
									</c:if>
									<a class="easyui-linkbutton" data-options="iconCls:'icon-submit'" href="javascript:void(0)" onclick="javascript:uploadFile(1);">上传照片</a>
								</td>
							</tr>
							
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">性别：</td>
								<td align="left" class="tablecontent" width="20%">
									<input id="sexId" name="sex" value="${archives.sex}" style="width:252px;padding:2px" />
								</td>
								<td nowrap class="tablelabel" width="10%">民族：</td>
								<td align="left" class="tablecontent" width="20%">
									<input name="nation" value="${archives.nation}" style="width:252px;padding:2px" />
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">政治面貌：</td>
								<td align="left" class="tablecontent" width="20%">
									<input name="political" value="${archives.political}" style="width:252px;padding:2px" />
								</td>
								<td nowrap class="tablelabel" width="10%">籍贯：</td>
								<td align="left" class="tablecontent" width="20%">
									<input type="text"  name="nativePlace" value="${archives.nativePlace}" style="width:98%;padding-left:2px"/>
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">出生地：</td>
								<td align="left" class="tablecontent" width="20%">
									<input type="text"  name="birthPlace" value="${archives.birthPlace}" style="width:98%;padding-left:2px"/>
								</td>
								<td nowrap class="tablelabel" width="10%">出生日期：</td>
								<td align="left" class="tablecontent" width="20%">
								<input  name="birthDate"  type="text" value="${archives.birthDate}" class="easyui-datebox" data-options="editable:false" style="width:252px;"></input>
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">户口所在地：</td>
								<td align="left" class="tablecontent" width="20%">
									<input type="text"  name="resPlace" value="${archives.resPlace}" style="width:98%;padding-left:2px"/>
								</td>	
								<td nowrap class="tablelabel" width="10%">户口性质：</td>
								<td align="left" class="tablecontent" width="20%">
									<input name="resType" value="${archives.resType}" style="width:252px;padding:2px" />
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">现在住址：</td>
								<td align="left" class="tablecontent" width="20%">
									<input type="text"  name="nowAdd" value="${archives.nowAdd}" style="width:98%;padding-left:2px"/>
								</td>
								<td nowrap class="tablelabel" width="10%">照片：</td>
								<td colspan="2" align="left" class="tablecontent" width="20%">
									 <p style="height:15px;line-height:15px;padding-left:2px;">${archives.picName}</p>
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">身高(cm)：</td>
								<td align="left" class="tablecontent" width="20%">
									<input type="text"  name="height" value="${archives.height}" style="width:98%;padding-left:2px"/>
								</td>
								<td nowrap class="tablelabel" width="10%">体重(kg)：</td>
								<td colspan="2" align="left" class="tablecontent" width="20%">
									<input type="text"  name="weight" value="${archives.weight}" style="width:98%;padding-left:2px"/>
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">健康状况：</td>
								<td align="left" class="tablecontent" width="20%">
									<input name="health" value="${archives.health}" style="width:252px;padding:2px" />
								</td>
								<td nowrap class="tablelabel" width="10%">手机：</td>
								<td colspan="2" align="left" class="tablecontent" width="20%">
									<input type="text"  name="mobilePhone" value="${archives.mobilePhone}" class="easyui-validatebox" data-options="required:true" style="width:98%;padding-left:2px"/>
								</td>
							</tr>
							
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">家长手机：</td>
								<td align="left" class="tablecontent" width="20%">
									<input type="text"  name="parPhone" value="${archives.parPhone}"  class="easyui-validatebox" data-options="required:true" style="width:98%;padding-left:2px"/>
								</td>
								<td nowrap class="tablelabel" width="10%">QQ：</td>
								<td colspan="2" align="left" class="tablecontent" width="20%">
									<input type="text"  name="qq" value="${archives.qq}" style="width:98%;padding-left:2px"/>
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">身份证号码：</td>
								<td align="left" class="tablecontent" width="20%">
									<input type="text"  name="identCardNo" value="${archives.identCardNo}" style="width:98%;padding-left:2px"/>
								</td>
								<td nowrap class="tablelabel" width="10%">E-mail：</td>
								<td colspan="2" align="left" class="tablecontent" width="20%">
									<input type="text"  name="email" value="${archives.email}" style="width:98%;padding-left:2px"/>
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">特长</td>
								<td align="left" class="tablecontent" width="20%">
									<input type="text"  name="special" value="${archives.special}" style="width:98%;padding-left:2px"/>
								</td>	
								<td nowrap class="tablelabel" width="10%">计算机水平：</td>
								<td colspan="2" align="left" class="tablecontent" width="20%">
									<input name="compuLevel" value="${archives.compuLevel}" style="width:395px;padding:2px" />
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">所学外语语种：</td>
								<td align="left" class="tablecontent" width="20%">
									<input name="foreignType" value="${archives.foreignType}" style="width:252px;padding:2px" />
								</td>
								<td nowrap class="tablelabel" width="10%">兴趣爱好：</td>
								<td colspan="2" align="left" class="tablecontent" width="20%">
									<input type="text"  name="interestAndHobby" value="${archives.interestAndHobby}" style="width:98%;padding-left:2px"/>
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">录入系统时间</td>
								<td align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${archives.baseInfoCrtTime}</p>	
								</td>
								<td nowrap class="tablelabel" width="10%">更新时间：</td>
								<td colspan="2" align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${archives.baseInfoUptTime}</p>
								</td>
							</tr>
						</table>
				</div>
				<div title="家庭信息" data-options="iconCls:'icon-group'">
					<table class="tableborder" id="formTable">
						<tr height="40" class="tablelabel">
							<td nowrap class="tablelabel" width="10%">家庭人口数：</td>
							<td align="left" class="tablecontent" width="20%">
								<input type="text"  name="peoNum" value="${archives.peoNum}" style="width:98%;padding-left:2px"/>
							</td>
							<td nowrap class="tablelabel" width="10%">详细通讯地址：</td>
							<td align="left" class="tablecontent" width="20%">
								<input type="text"  name="addr" value="${archives.addr}" style="width:98%;padding-left:2px"/>
							</td>
						</tr>
						<tr height="40" class="tablelabel">
							<td nowrap class="tablelabel" width="10%">联系电话：</td>
							<td align="left" class="tablecontent" width="20%">
								<input type="text"  name="tel" value="${archives.tel}" style="width:98%;padding-left:2px"/>
							</td>
							<td nowrap class="tablelabel" width="10%">家庭经济状况：</td>
							<td align="left" class="tablecontent" width="20%">
								<input name="ecoStatus" value="${archives.ecoStatus}" style="width:98%;padding:2px" />
							</td>
						</tr>
						<tr height="40" class="tablelabel">
							<td nowrap class="tablelabel" width="10%">主要经济来源：</td>
							<td align="left" class="tablecontent" width="20%">
								<input type="text"  name="ecoFrom" value="${archives.ecoFrom}" style="width:98%;padding-left:2px"/>
							</td>
							<td nowrap class="tablelabel" width="10%">总收入（万元/年）：</td>
							<td align="left" class="tablecontent" width="20%">
								<input type="text"  name="ecoTotal" value="${archives.ecoTotal}" style="width:98%;padding-left:2px"/>
							</td>
						</tr>
						<tr height="40" class="tablelabel">
							<td nowrap class="tablelabel" width="10%">主要支出：</td>
							<td align="left" class="tablecontent" width="20%">
								<input type="text"  name="ecoPay" value="${archives.ecoPay}" style="width:98%;padding-left:2px"/>
							</td>
							<td nowrap class="tablelabel" width="10%">家庭教育背景：</td>
							<td align="left" class="tablecontent" width="20%">
								<input name="teachBack" value="${archives.teachBack}" style="width:98%;padding:2px" />
							</td>
						</tr>
						<tr height="40" class="tablelabel">
							<td nowrap class="tablelabel" width="10%">是否有低保证：</td>
							<td align="left" class="tablecontent" width="20%">
								<input name="basicLive" value="${archives.basicLive}" style="width:98%;padding:2px" />
							</td>
							<td nowrap class="tablelabel" width="10%">是否享受过补助：</td>
							<td align="left" class="tablecontent" width="20%">
								<input name="helping" value="${archives.helping}" style="width:98%;padding:2px" />
							</td>
						</tr>
						<tr height="60">
							<td nowrap class="tablelabel" width="10%">家庭情况介绍：</td>
							<td colspan="3" class="tablecontent"  align="left">
								<textarea name="introduce" style="width:99.8%;height:100px;font-size: 14px;">${archives.introduce}</textarea>
							</td>
						</tr>
						<tr height="60">
							<td class="tablelabel" width="10%">家庭成员如果有特殊情况（疾病、残疾、单亲等）必须进行说明，家庭如果困难，说明困难情况）</td>
							<td colspan="3" class="tablecontent"  align="left">
								<textarea name="specialStatus" style="width:99.8%;height:100px;font-size: 14px;">${archives.specialStatus}</textarea>
							</td>
						</tr>
						<tr height="40" class="tablelabel">
							<td nowrap class="tablelabel" width="10%">录入系统时间</td>
							<td align="left" class="tablecontent" width="20%">
							<p style="height:15px;line-height:15px;padding-left:2px;">${archives.familyCrtTime}</p>
							</td>
							<td nowrap class="tablelabel" width="10%">更新时间：</td>
							<td align="left" class="tablecontent" width="20%">
							<p style="height:15px;line-height:15px;padding-left:2px;">${archives.familyUptTime}</p>
							</td>
						</tr>
					</table>
				</div>
				<div title="家庭成员" data-options="iconCls:'icon-sort'">
					<table id="submittergrid"></table> 
				</div>
				<div title="奖惩记录" data-options="iconCls:'icon-good'">
					<table id="apGrid"></table>
				</div>
				<div title="教育经历" data-options="iconCls:'icon-edit'">
					<table id="eeGrid"></table> 
				</div>
				<div title="工作经历" data-options="iconCls:'icon-orange'">
					<table id="weGrid"></table> 
				</div>
			</div>
<!--材料上传区域 -->
			<table border="0" align="center" cellpadding="0" cellspacing="0" style="width:800px;">
				<tr height="25"  >
	              <td height="25" width="20%" nowrap="nowrap" class="tablelabel">材料上传区域：<input type="button" value="添加" onclick="addFile()"/></td>
	              <td id="fil" height="100" colspan="5" class="tablecontent"  align="left" style="padding-left:10px;">
						<input type="hidden" id="FileCount" value="${fn:length(certMap)}">
						<c:forEach items="${certMap}" var="att" varStatus="status">
							<input id="no${status.index+1}"  type="hidden" value="${att.key}" />
		              		<input type="text" style="width:150px" id="fCert${status.index+1}" name="fCert${status.index+1}" value="${att.key}"  readonly="readonly">
							<input type="button" value="上传" onclick="uploadCertFile(${status.index+1})">
							&nbsp;材料说明：<input type="text" style="width:350px" id="certInstr${status.index+1}" name="certInstr${status.index+1}" value="${att.value}">
							<br/>
							<img id="vCertImg${status.index+1}" src="${pageContext.request.contextPath}/upload/mobile/${att.key}" width="230px">
							<br/>
						</c:forEach>
	              </td>
	            </tr>
			</table>
<!--材料上传 -->
		</form>
	</div>
</div>
<div id="tjr_menu" class="taz_menu" style="padding:2px;height:auto">
	<div style="margin-bottom:1px">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="$('#win_fm_query').window('open')">添加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delete_fm()">删除</a>
	</div>
</div>
<div id="ap_menu" class="taz_menu" style="padding:2px;height:auto">
	<div style="margin-bottom:1px">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="$('#win_ap_query').window('open')">添加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delete_ap()">删除</a>
	</div>
</div>
<div id="ee_menu" class="taz_menu" style="padding:2px;height:auto">
	<div style="margin-bottom:1px">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="$('#win_ee_query').window('open')">添加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delete_ee()">删除</a>
	</div>
</div>
<div id="we_menu" class="taz_menu" style="padding:2px;height:auto">
	<div style="margin-bottom:1px">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="$('#win_we_query').window('open');">添加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delete_we()">删除</a>
	</div>
</div>
<div id="win_fm_query" class="easyui-window" data-options="title:'添加家庭成员',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:650px;height:320px;padding:5px;">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
			<center>
			<form id="fmForm" method="post">
			<table id="tar_queryTable">
				<tr height="25">
					<td>姓名：</td>
					<td colspan="3">
						<input type="text"  name="name" style="width:180px;padding:2px"/>
					</td>
				</tr>
				<tr height="25">
					<td>年龄：</td>
					<td colspan="3">
						<input type="text"  name="age" class="easyui-numberbox" data-options="min:0,max:100" style="width:185px;padding:2px"/>
					</td>
				</tr>
				<tr height="25">
					<td>家庭角色(与本人关系)：</td>
					<td colspan="3">
						<input type="text"  name="familyRole" style="width:180px;padding:2px"/>
					</td>
				</tr>
				<tr height="25">
					<td>工作单位：</td>
					<td colspan="3">
						<input type="text"  name="unit" style="width:180px;padding:2px"/>
					</td>
				</tr>
				<tr height="25">
					<td>职业：</td>
					<td colspan="3">
						<input type="text"  name="duty" style="width:180px;padding:2px"/>
					</td>
				</tr>
				<tr height="25">
					<td>年收入  (万元)：</td>
					<td colspan="3">
						<input type="text"  name="yearIncome" class="easyui-numberbox" data-options="min:0,max:1000" style="width:185px;padding:2px"/>
					</td>
				</tr>
				<tr height="25">
					<td>健康状况：</td>
					<td colspan="3">
						<input name="healthStatus" style="width:180px;padding:2px"/>
					</td>
				</tr>
				<tr height="25">
					<td>手机号码：</td>
					<td colspan="3">
						<input name="tel" style="width:180px;padding:2px"/>
					</td>
				</tr>
			</table>
			</form>
			</center>
		</div>
		<div data-options="region:'south',border:false" style="text-align:center;padding:5px 0;height:40px">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="javascript:fmSubmit();"> 提交 </a>
		</div>
	</div>
</div>
<div id="win_ap_query" class="easyui-window" data-options="title:'添加奖惩记录',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:650px;height:300px;padding:5px;">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
			<center>
			<form id="apForm" method="post">
			<table id="tar_queryTable">
				<tr height="25">
					<td>奖惩日期：</td>
					<td colspan="3">
						<input name="apDate" type="text" class="easyui-datebox"  data-options="editable:false" style="width:180px;padding:2px">
					</td>
				</tr>
				<tr height="25">
					<td>奖惩性质：</td>
					<td colspan="3">
						<input name="apAtt" style="width:180px;padding:2px"/>
					</td>
				</tr>
				<tr height="25">
					<td>奖惩方式：</td>
					<td colspan="3">
						<input name="apMethod" style="width:180px;padding:2px"/>
					</td>
				</tr>
				<tr height="25">
					<td>奖惩分数/金额：</td>
					<td colspan="3">
						<input type="text"  name="apScore" style="width:180px;padding:2px"/>
					</td>
				</tr>
				<tr height="25">
					<td>奖惩单位：</td>
					<td colspan="3">
						<input type="text"  name="apUnit" style="width:180px;padding:2px"/>
					</td>
				</tr>
	            <tr height="25">
	             <td>奖惩描述：</td>
	              <td height="25" colspan="3" class="tablecontent"  align="left">
	              	<textarea name="apContent" style="width:99%;height: 50px; font-size: 14px; "></textarea>
	              </td>
           		</tr>
           		<tr height="25">
	             <td>奖惩备注：</td>
	              <td height="25" colspan="3" class="tablecontent"  align="left">
	              	<textarea name="apRemark" style="width:99%;height: 50px; font-size: 14px; "></textarea>
	              </td>
           		</tr>
			</table>
			</form>
			</center>
		</div>
		<div data-options="region:'south',border:false" style="text-align:center;padding:5px 0;height:40px">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="javascript:apSubmit();"> 提交 </a>
		</div>
	</div>
</div>
<div id="win_ee_query" class="easyui-window" data-options="title:'添加教育经历',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:650px;height:300px;padding:5px;">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
			<center>
			<form id="eeForm" method="post">
			<table id="tar_queryTable">
				<tr height="25">
					<td>开始年月：</td>
					<td colspan="3">
						<input name="beginDate" type="text" class="easyui-datebox" data-options="editable:false" style="width:180px;padding:2px">
					</td>
				</tr>
				<tr height="25">
					<td>结束年月：</td>
					<td colspan="3">
						<input name="endDate" type="text" class="easyui-datebox" data-options="editable:false" style="width:180px;padding:2px">
					</td>
				</tr>
				<tr height="25">
					<td>教育单位：</td>
					<td colspan="3">
						<input name="unit" type="text"   style="width:180px;padding:2px"/>
					</td>
				</tr>
				<tr height="25">
					<td>专业：</td>
					<td colspan="3">
						<input name="major" type="text" style="width:180px;padding:2px"/>
					</td>
				</tr>
				<tr height="25">
					<td>所获证书：</td>
					<td colspan="3">
						<input type="text"  name="cert" style="width:180px;padding:2px"/>
					</td>
				</tr>
				<tr height="25">
					<td>证明人：</td>
					<td colspan="3">
						<input type="text"  name="certifier" style="width:180px;padding:2px"/>
					</td>
				</tr>
			</table>
			</form>
			</center>
		</div>
		<div data-options="region:'south',border:false" style="text-align:center;padding:5px 0;height:40px">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="javascript:eeSubmit();"> 提交 </a>
		</div>
	</div>
</div>
<div id="win_we_query" class="easyui-window" data-options="title:'添加工作经历',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:650px;height:300px;padding:5px;">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
			<center>
			<form id="weForm" method="post">
			<table id="tar_queryTable">
				<tr height="25">
					<td>开始年月：</td>
					<td colspan="3">
						<input name="beginDate" type="text" class="easyui-datebox" data-options="editable:false" style="width:180px;padding:2px">
					</td>
				</tr>
				<tr height="25">
					<td>结束年月：</td>
					<td colspan="3">
						<input name="endDate" type="text" class="easyui-datebox" data-options="editable:false" style="width:180px;padding:2px">
					</td>
				</tr>
				<tr height="25">
					<td>工作单位：</td>
					<td colspan="3">
						<input name="workUnit" type="text"   style="width:180px;padding:2px"/>
					</td>
				</tr>
				<tr height="25">
					<td>职务：</td>
					<td colspan="3">
						<input name="workDuty" type="text" style="width:180px;padding:2px"/>
					</td>
				</tr>
				<tr height="25">
		             <td>内容：</td>
		             <td height="25" colspan="3" class="tablecontent"  align="left">
		              	<textarea name="workContent" style="width:99%;height: 50px; font-size: 14px; "></textarea>
		              </td>
           		</tr>
			</table>
			</form>
			</center>
		</div>
		<div data-options="region:'south',border:false" style="text-align:center;padding:5px 0;height:40px">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="javascript:weSubmit();"> 提交 </a>
		</div>
	</div>
</div>
