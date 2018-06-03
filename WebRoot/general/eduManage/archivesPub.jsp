<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%@page import="com.tlzn.util.base.Constants"%>	
<jsp:include page="../../public/inc.jsp"></jsp:include>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
function initCombox(form){
	initCombobox(form,'nation','${pageContext.request.contextPath}/dic!combox.do?ctype=NATION');
	initCombobox(form,'political','${pageContext.request.contextPath}/dic!combox.do?ctype=POLITICAL');
	initCombobox(form,'resType','${pageContext.request.contextPath}/dic!combox.do?ctype=resType');
	initCombobox(form,'health','${pageContext.request.contextPath}/dic!combox.do?ctype=health');
	initCombobox(form,'healthStatus','${pageContext.request.contextPath}/dic!combox.do?ctype=health');
	initCombobox(form,'compuLevel','${pageContext.request.contextPath}/dic!combox.do?ctype=compuLevel');
	initCombobox(form,'foreignType','${pageContext.request.contextPath}/dic!combox.do?ctype=foreignType');
	initCombobox(form,'ecoStatus','${pageContext.request.contextPath}/dic!combox.do?ctype=ecoStatus');
	initCombobox(form,'teachBack','${pageContext.request.contextPath}/dic!combox.do?ctype=teachBack');
	initCombobox(form,'basicLive','${pageContext.request.contextPath}/dic!combox.do?ctype=YESNO');
	initCombobox(form,'helping','${pageContext.request.contextPath}/dic!combox.do?ctype=YESNO');
	initCombobox(form,'apAtt','${pageContext.request.contextPath}/dic!combox.do?ctype=apAtt');
	initCombobox(form,'apMethod','${pageContext.request.contextPath}/dic!combox.do?ctype=apMethod');
}
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
//上传照片窗口弹出
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

//添加家庭成员（添加弹出的） 开始
var fmIds = new Array();
function fmSubmit() {
	$('#fmForm').form('submit', {
		url : '${pageContext.request.contextPath}/familyMem!save.do',
		success : function(d) {
			var json = $.parseJSON(d);
			if (json.success) {
				$('#fmForm').form('reset');
				$('#win_fm_query').window('close');
				if(json.obj) {
					fmIds.push(json.obj.id);
					$('#submittergrid').datagrid('appendRow',{
						id: json.obj.id,
						name: json.obj.name,
						age: json.obj.age,
						familyRole: json.obj.familyRole,
						unit: json.obj.unit,
						duty: json.obj.duty,
						yearIncome: json.obj.yearIncome,
						healthStatusName: json.obj.healthStatusName,
						tel: json.obj.tel,
						crtTime: json.obj.crtTime,
						uptTime: json.obj.uptTime
					});
				}
			}
			parent.dj.messagerShow({
				msg : json.msg,
				title : '提示'
			});
		}
	});
	
}
//删除家庭成员
function delete_fm(){
	var rows=$('#submittergrid').datagrid('getSelections');
	if(rows.length>0){
		dj.messagerConfirm('请确认', '您确定要删除选中的所有家庭成员吗？', function(r) {
			if (r) {
				var ids = [];
				for(var a=0; a<rows.length; a++) {
					////删除数组指定元素
					ids.push(rows[a].id);
				}
				$.ajax({
					type:"POST",
					url:"${pageContext.request.contextPath}/familyMem!delete.do",
					data : {
						ids : ids.join(','),
					},
					dataType:"json",
					success:function(t){
						if(t.success){
							for(var h=0; h<ids.length; h++) {
								////删除数组指定元素
								fmIds.removeByValue(ids[h]);
							}
							alert(fmIds);
							for(var i = rows.length-1; i>=0 ;i--){
								var rowindex=$('#submittergrid').datagrid('getRowIndex',rows[i]);
								$('#submittergrid').datagrid('deleteRow',rowindex);
							}
							parent.dj.messagerAlert('提示', '家庭成员删除成功！', 'success');
						}else{
							parent.dj.messagerAlert('提示', '家庭成员删除失败！', 'success');	
						}
					}
				});
			}
		});
	}else{
		dj.messagerAlert('提示', '请选择要删除的记录！', 'error');
	}
}
//添加家庭信息（添加弹出的）结束

//添加奖惩记录（添加弹出的） 开始
var apIds = new Array();
function apSubmit() {
	$('#apForm').form('submit', {
		url : '${pageContext.request.contextPath}/awardPenalty!save.do',
		success : function(d) {
			var json = $.parseJSON(d);
			if (json.success) {
				$('#apForm').form('reset');
				$('#win_ap_query').window('close');
				if(json.obj) {
					apIds.push(json.obj.id);
					$('#apGrid').datagrid('appendRow',{
						id: json.obj.id,
						apDate: json.obj.apDate,
						apAttName: json.obj.apAttName,
						apMethodName: json.obj.apMethodName,
						apScore: json.obj.apScore,
						apUnit: json.obj.apUnit,
						crtTime: json.obj.crtTime,
						uptTime: json.obj.uptTime
					});
				}
			}
			parent.dj.messagerShow({
				msg : json.msg,
				title : '提示'
			});
		}
	});
	
}
//删除奖惩记录
function delete_ap(){
	var rows=$('#apGrid').datagrid('getSelections');
	if(rows.length>0){
		dj.messagerConfirm('请确认', '您确定要删除选中的所有奖惩记录吗？', function(r) {
			if (r) {
				var ids = [];
				for(var a=0; a<rows.length; a++) {
					////删除数组指定元素
					ids.push(rows[a].id);
				}
				$.ajax({
					type:"POST",
					url:"${pageContext.request.contextPath}/awardPenalty!delete.do",
					data : {
						ids : ids.join(','),
					},
					dataType:"json",
					success:function(t){
						if(t.success){
							for(var h=0; h<ids.length; h++) {
								////删除数组指定元素
								apIds.removeByValue(ids[h]);
							}
							for(var i = rows.length-1; i>=0 ;i--){
								var rowindex=$('#apGrid').datagrid('getRowIndex',rows[i]);
								$('#apGrid').datagrid('deleteRow',rowindex);
							}
							parent.dj.messagerAlert('提示', '奖惩记录删除成功！', 'success');
						}else{
							parent.dj.messagerAlert('提示', '奖惩记录删除失败！', 'success');	
						}
					}
				});
			}
		});
	}else{
		dj.messagerAlert('提示', '请选择要删除的记录！', 'error');
	}
}
//添加奖惩记录（添加弹出的）结束

//添加教育经历（添加弹出的） 开始
var eeIds = new Array();
function eeSubmit() {
	$('#eeForm').form('submit', {
		url : '${pageContext.request.contextPath}/eduExp!save.do?archNo='+'${archives.archNo}',
		success : function(d) {
			var json = $.parseJSON(d);
			if (json.success) {
				$('#eeForm').form('reset');
				$('#win_ee_query').window('close');
				if(json.obj) {
					eeIds.push(json.obj.id);
					$('#eeGrid').datagrid('appendRow',{
						id: json.obj.id,
						beginDate: json.obj.beginDate,
						endDate: json.obj.endDate,
						unit: json.obj.unit,
						major: json.obj.major,
						cert: json.obj.cert,
						certifier: json.obj.certifier,
						crtTime: json.obj.crtTime,
						uptTime: json.obj.uptTime
					});
				}
			}
			parent.dj.messagerShow({
				msg : json.msg,
				title : '提示'
			});
		}
	});
	
}
//删除教育经历
function delete_ee(){
	var rows=$('#eeGrid').datagrid('getSelections');
	if(rows.length>0){
		dj.messagerConfirm('请确认', '您确定要删除选中的所有教育经历吗？', function(r) {
			if (r) {
				var ids = [];
				for(var a=0; a<rows.length; a++) {
					////删除数组指定元素
					ids.push(rows[a].id);
				}
				$.ajax({
					type:"POST",
					url:"${pageContext.request.contextPath}/eduExp!delete.do",
					data : {
						ids : ids.join(','),
					},
					dataType:"json",
					success:function(t){
						if(t.success){
							for(var h=0; h<ids.length; h++) {
								////删除数组指定元素
								eeIds.removeByValue(ids[h]);
							}
							for(var i = rows.length-1; i>=0 ;i--){
								var rowindex=$('#eeGrid').datagrid('getRowIndex',rows[i]);
								$('#eeGrid').datagrid('deleteRow',rowindex);
							}
							parent.dj.messagerAlert('提示', '教育经历删除成功！', 'success');
						}else{
							parent.dj.messagerAlert('提示', '教育经历删除失败！', 'success');	
						}
					}
				});
			}
		});
	}else{
		dj.messagerAlert('提示', '请选择要删除的记录！', 'error');
	}
}
//添加教育经历（添加弹出的）结束

//添加工作经历（添加弹出的） 开始
var weIds = new Array();
function weSubmit() {
	$('#weForm').form('submit', {
		url : '${pageContext.request.contextPath}/workExp!save.do',
		success : function(d) {
			var json = $.parseJSON(d);
			if (json.success) {
				$('#weForm').form('reset');
				$('#win_we_query').window('close');
				if(json.obj) {
					weIds.push(json.obj.id);
					$('#weGrid').datagrid('appendRow',{
						id: json.obj.id,
						beginDate: json.obj.beginDate,
						endDate: json.obj.endDate,
						workUnit: json.obj.workUnit,
						workDuty: json.obj.workDuty,
						workContent: json.obj.workContent,
						crtTime: json.obj.crtTime,
						uptTime: json.obj.uptTime
					});
				}
			}
			parent.dj.messagerShow({
				msg : json.msg,
				title : '提示'
			});
		}
	});
}
//删除工作经历
function delete_we(){
	var rows=$('#weGrid').datagrid('getSelections');
	if(rows.length>0){
		dj.messagerConfirm('请确认', '您确定要删除选中的所有工作经历吗？', function(r) {
			if (r) {
				var ids = [];
				for(var a=0; a<rows.length; a++) {
					////删除数组指定元素
					ids.push(rows[a].id);
				}
				$.ajax({
					type:"POST",
					url:"${pageContext.request.contextPath}/workExp!delete.do",
					data : {
						ids : ids.join(','),
					},
					dataType:"json",
					success:function(t){
						if(t.success){
							for(var h=0; h<ids.length; h++) {
								////删除数组指定元素
								weIds.removeByValue(ids[h]);
							}
							for(var i = rows.length-1; i>=0 ;i--){
								var rowindex=$('#weGrid').datagrid('getRowIndex',rows[i]);
								$('#weGrid').datagrid('deleteRow',rowindex);
							}
							parent.dj.messagerAlert('提示', '工作经历删除成功！', 'success');
						}else{
							parent.dj.messagerAlert('提示', '工作经历删除失败！', 'success');	
						}
					}
				});
			}
		});
	}else{
		dj.messagerAlert('提示', '请选择要删除的记录！', 'error');
	}
}
//添加工作经历（添加弹出的）结束
	
$(document).ready(function() {
	initCombox('addForm');
	initCombox('fmForm');
	initCombox('apForm');
	//initCombox('eeForm');
	//initCombox('weForm');
	$('#sexId').combobox({
		url : '${pageContext.request.contextPath}/dic!combox.do?ctype=SEX',
		valueField:'cvalue', 
		panelHeight:'100',
        textField:'ckey',
        required:true,
        validType:'sel',
        onSelect:function(row){
			if(row.cvalue==<%=Constants.CODE_TYPE_SEX_WOMAN%>){
				$('#vImg1').attr("src","${pageContext.request.contextPath}/style/images/woman.jpg");
			} else {
				$('#vImg1').attr("src","${pageContext.request.contextPath}/style/images/man.jpg");
			}
		}
	});
///////////////////////家庭成员列表
	$('#submittergrid').datagrid({
		url:"",
		width:'920',
		height:'auto',
		striped:true,
		nowrap:true,
		rownumbers:true,
		singleSelect:false,
		fitColumns:false,
		border:false,
		frozenColumns:[[
			{field:'id',title:'成员编号',align:'center',width:70,sortable:false},
			{field:'name',title:'姓名',align:'center',width:100,sortable:false}
		]],
		columns:[[
			{field:'age',title:'年龄',align:'center',width:50,sortable:false},
			{field:'familyRole',title:'家庭角色(与本人关系)',align:'center',width:200,sortable:false},
			{field:'unit',title:'工作单位',align:'left',width:120,sortable:false},
			{field:'duty',title:'职业',align:'center',width:100,sortable:false},
			{field:'yearIncome',title:'年收入  (万元)',align:'center',width:120,sortable:false},
			{field:'healthStatusName',title:'健康状况',align:'center',width:100,sortable:false},
			{field:'tel',title:'手机号码',align:'center',width:100,sortable:false},
			{field:'crtTime',title:'创建时间',align:'center',width:200,sortable:false},
			{field:'uptTime',title:'更新时间',align:'center',width:200,sortable:false}
		]],
		toolbar: '#tjr_menu'
	});
///////////////////////家庭成员列表
//////////////////////奖惩记录列表
	$('#apGrid').datagrid({
			url:"",
			width:'1022',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			singleSelect:false,
			fitColumns:false,
			border:false,
			frozenColumns:[[
				{field:'id',title:'编号',align:'center',width:70,sortable:false},
				{field:'apDate',title:'奖惩日期',align:'center',width:100,sortable:false}
			]],
			columns:[[
				{field:'apAttName',title:'奖惩性质',align:'center',width:200,sortable:false},
				{field:'apMethodName',title:'奖惩方式',align:'left',width:120,sortable:false},
				{field:'apScore',title:'奖惩分数/金额',align:'center',width:100,sortable:false},
				{field:'apUnit',title:'奖惩单位',align:'center',width:120,sortable:false},
				{field:'crtTime',title:'创建时间',align:'center',width:200,sortable:false},
				{field:'uptTime',title:'更新时间',align:'center',width:200,sortable:false}
			]],
			toolbar: '#ap_menu'
		});
//////////////////////奖惩记录列表
////////////////////教育经历录列表
		$('#eeGrid').datagrid({
			url:"",
			width:'1022',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			singleSelect:false,
			fitColumns:false,
			border:false,
			frozenColumns:[[
				{field:'id',title:'编号',align:'center',width:70,sortable:false},
				{field:'beginDate',title:'开始年月',align:'center',width:100,sortable:false},
				{field:'endDate',title:'结束年月',align:'center',width:100,sortable:false}
			]],
			columns:[[
				{field:'unit',title:'教育单位',align:'center',width:200,sortable:false},
				{field:'major',title:'专业',align:'center',width:200,sortable:false},
				{field:'cert',title:'所获证书',align:'left',width:120,sortable:false},
				{field:'certifier',title:'证明人',align:'left',width:120,sortable:false},
				{field:'crtTime',title:'创建时间',align:'center',width:200,sortable:false},
				{field:'uptTime',title:'更新时间',align:'center',width:200,sortable:false}
			]],
			toolbar: '#ee_menu'
		});
////////////////////教育经历录列表
/////////工作经历列表
	$('#weGrid').datagrid({
			url:"",
			width:'1022',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			singleSelect:false,
			fitColumns:false,
			border:false,
			frozenColumns:[[
				{field:'id',title:'编号',align:'center',width:70,sortable:false},
				{field:'beginDate',title:'开始日期',align:'center',width:100,sortable:false},
				{field:'endDate',title:'结束日期',align:'center',width:100,sortable:false}
			]],
			columns:[[
				{field:'workUnit',title:'工作单位',align:'center',width:300,sortable:false},
				{field:'workDuty',title:'职务',align:'center',width:200,sortable:false},
				{field:'crtTime',title:'创建时间',align:'center',width:200,sortable:false},
				{field:'uptTime',title:'更新时间',align:'center',width:200,sortable:false}
			]],
			toolbar: '#we_menu'
		});
/////////工作经历列表
});

//提交学生档案
function submitArch(){
			$('#fmIds').val(fmIds.join(','));
			$('#apIds').val(apIds.join(','));
			$('#eeIds').val(eeIds.join(','));
			$('#weIds').val(weIds.join(','));

			var attNames=[];
	    	var FileCount=parseInt($("#FileCount").val(),10);
	    	for(var i=1;i<=FileCount;i++){
			  attNames.push($('#fCert'+i).val()+'||'+$('#certInstr'+i).val());
	    	}
	    	$('#certFilesId').val(attNames.join(','));
					    	
			var f =$("#addForm");
			f.form('submit', {
				url : '${pageContext.request.contextPath}/archives!save.do',
				success : function(d) {
					var json = $.parseJSON(d);
					if (json.success) {
						parent.dj.messagerShow({
							msg : json.msg,
							title : '提示'
						});
					 	window.parent.closetab();
					}
				}
		
			});
}

$.extend($.fn.validatebox.defaults.rules, {
	sel : {
		validator : function(value) {
			return value != '请选择...';
		},
		message : '此项必须选择！'
	}
});

</script>
</head>
<div class="easyui-layout" data-options="fit:true" class="cs-home-remark">
	<div data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc;">
	<div align="center" style="padding: 5px;overflow: hidden;">
			<form id="addForm" method="post">
				<input type="hidden" id="fmIds" name="fmIds" />
				<input type="hidden" id="apIds" name="apIds" />
				<input type="hidden" id="eeIds" name="eeIds" />
				<input type="hidden" id="weIds" name="weIds" />
				<input type="hidden" id="certFilesId" name="certFiles" value="${archives.certFiles}" />
				<table border="0" align="center" cellpadding="0" cellspacing="0" style="width:750px;">
					<tr>
						<td>
							<table class="tableborder" id="formTable">
								<tr height="40">
					              <td height="45" colspan="4" align="middle" nowrap="nowrap" class="tablemain" width="100%">
					               	<div align="center"><strong>档案新建</strong></div>
					              </td>
					            </tr>
								<tr height="40" class="tablelabel">
									<td nowrap class="tablelabel" width="10%">紧急情况下联系号码：</td>
									<td align="left" class="tablecontent" width="20%">
										<input type="text"  name="urgentTel" value="${archives.urgentTel}" class="easyui-validatebox" data-options="required:true" style="width:98%;padding-left:2px"/>
									</td>
									<td nowrap class="tablelabel" width="10%">毕业学校：</td>
									<td align="left" class="tablecontent" width="20%">
										<input type="text"  name="gradSchool" value="${archives.gradSchool}" style="width:98%;padding-left:2px"/>
									</td>
								</tr>
								<tr height="60">
					              <td nowrap class="tablelabel" width="10%">自我评价：</td>
					              <td colspan="3" class="tablecontent"  align="left">
					              	<textarea name="selfAppr" style="width:99.8%;height:100px;font-size: 14px;">${archives.selfAppr}</textarea>
					              </td>
					            </tr>
								<tr height="60">
					              <td nowrap class="tablelabel" width="10%">备注：</td>
					              <td colspan="3" class="tablecontent"  align="left">
					              	<textarea name="remark" style="width:99.8%;height:100px;font-size: 14px;">${archives.remark}</textarea>
					              </td>
					            </tr>
							</table>
						</td>
					</tr>
				</table>
				<div id="tt" class="easyui-tabs" style="width:750px;">
					<div title="人员基本信息" data-options="iconCls:'icon-role'">
						<table class="tableborder" id="formTable">
								<tr height="40" class="tablelabel">
									<td nowrap class="tablelabel" width="20%">姓名：</td>
									<td align="left" class="tablecontent" width="25%">
										<input type="text"  name="name" value="${archives.name}" class="easyui-validatebox" data-options="required:true" style="width:98%;padding-left:2px"/>
									</td>
									<td nowrap class="tablelabel" width="20%">曾用名：</td>
									<td align="left" class="tablecontent" width="25%">
										<input type="text"  name="nickName" value="${archives.nickName}" style="width:99.8%;padding-left:2px"/>
									</td>
									<td align="center" class="tablecontent" width="10%" rowspan="5" style="text-align: center;">
										<input type="hidden" style="width:100px" id="f1" name="picName" value="${archives.picName}">
										<img id="vImg1" src="${pageContext.request.contextPath}/style/images/man.jpg" style="height:160px;width:138px;text-align:center;margin-bottom:3px;border: 1px dotted #95B8E7;padding:2px;">
<!-- 										<c:if test="${!empty archives.picName}"> -->
<!-- 											<img id="vImg1" src="${pageContext.request.contextPath}/upload/mobile/${archives.picName}" style="height:160px;width:138px;text-align:center;margin-bottom:3px;border: 1px dotted #95B8E7;padding:2px;"> -->
<!-- 										</c:if> -->
<!-- 										<c:if test="${empty archives.picName}"> -->
<!-- 											<c:if test="${archives.sex==1}"> -->
											
<!-- 											</c:if> -->
<!-- 										</c:if> -->
<!-- 										<c:if test="${empty archives.picName}"> -->
<!-- 											<c:if test="${archives.sex==2}"> -->
<!-- 											<img id="vImg1" src="${pageContext.request.contextPath}/style/images/woman.jpg" style="height:160px;width:138px;text-align:center;margin-bottom:3px;border: 1px dotted #95B8E7;padding:2px;"> -->
<!-- 											</c:if> -->
<!-- 										</c:if> -->
										<a class="easyui-linkbutton" data-options="iconCls:'icon-submit'" href="javascript:void(0)" onclick="javascript:uploadFile(1);">上传照片</a>
									</td>
								</tr>
								
								<tr height="40" class="tablelabel">
									<td nowrap class="tablelabel" width="10%">性别：</td>
									<td align="left" class="tablecontent" width="20%">
										<input id="sexId" name="sex" value="${archives.sex}" style="width:200px;padding:2px" />
									</td>
									<td nowrap class="tablelabel" width="10%">民族：</td>
									<td align="left" class="tablecontent" width="20%">
										<input name="nation" value="${archives.nation}" style="width:200px;padding:2px" />
									</td>
								</tr>
								<tr height="40" class="tablelabel">
									<td nowrap class="tablelabel" width="10%">政治面貌：</td>
									<td align="left" class="tablecontent" width="20%">
										<input name="political" value="${archives.political}" style="width:200px;padding:2px" />
									</td>
									<td nowrap class="tablelabel" width="10%">籍贯：</td>
									<td align="left" class="tablecontent" width="20%">
										<input type="text"  name="nativePlace" value="${archives.nativePlace}" style="width:99.8%;padding-left:2px"/>
									</td>
								</tr>
								<tr height="40" class="tablelabel">
									<td nowrap class="tablelabel" width="10%">出生地：</td>
									<td align="left" class="tablecontent" width="20%">
										<input type="text"  name="birthPlace" value="${archives.birthPlace}" style="width:98%;padding-left:2px"/>
									</td>
									<td nowrap class="tablelabel" width="10%">出生日期：</td>
									<td align="left" class="tablecontent" width="20%">
									<input  name="birthDate"  type="text" value="${archives.birthDate}" class="easyui-datebox" data-options="editable:false" style="width:200px;"></input>
									</td>
								</tr>
								<tr height="40" class="tablelabel">
									<td nowrap class="tablelabel" width="10%">户口所在地：</td>
									<td align="left" class="tablecontent" width="20%">
										<input type="text"  name="resPlace" value="${archives.resPlace}" style="width:98%;padding-left:2px"/>
									</td>	
									<td nowrap class="tablelabel" width="10%">户口性质：</td>
									<td align="left" class="tablecontent" width="20%">
										<input name="resType" value="${archives.resType}" style="width:200px;padding:2px" />
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
										<input type="text"  name="weight" value="${archives.weight}" style="width:70%;padding-left:2px"/>
									</td>
								</tr>
								<tr height="40" class="tablelabel">
									<td nowrap class="tablelabel" width="10%">健康状况：</td>
									<td align="left" class="tablecontent" width="20%">
										<input name="health" value="${archives.health}" style="width:200px;padding:2px" />
									</td>
									<td nowrap class="tablelabel" width="10%">手机：</td>
									<td colspan="2" align="left" class="tablecontent" width="20%">
										<input type="text"  name="mobilePhone" value="${archives.mobilePhone}"  class="easyui-validatebox" data-options="required:true" style="width:70%;padding-left:2px"/>
									</td>
								</tr>
								
								<tr height="40" class="tablelabel">
									<td nowrap class="tablelabel" width="10%">家长手机：</td>
									<td align="left" class="tablecontent" width="20%">
										<input type="text"  name="parPhone" value="${archives.parPhone}"  class="easyui-validatebox" data-options="required:true" style="width:98%;padding-left:2px"/>
									</td>
									<td nowrap class="tablelabel" width="10%">QQ：</td>
									<td colspan="2" align="left" class="tablecontent" width="20%">
										<input type="text"  name="qq" value="${archives.qq}" style="width:70%;padding-left:2px"/>
									</td>
								</tr>
								<tr height="40" class="tablelabel">
									<td nowrap class="tablelabel" width="10%">身份证号码：</td>
									<td align="left" class="tablecontent" width="20%">
										<input type="text"  name="identCardNo" value="${archives.identCardNo}" style="width:98%;padding-left:2px"/>
									</td>
									<td nowrap class="tablelabel" width="10%">E-mail：</td>
									<td colspan="2" align="left" class="tablecontent" width="20%">
										<input type="text"  name="email" value="${archives.email}" style="width:70%;padding-left:2px"/>
									</td>
								</tr>
								<tr height="40" class="tablelabel">
									<td nowrap class="tablelabel" width="10%">特长</td>
									<td align="left" class="tablecontent" width="20%">
										<input type="text"  name="special" value="${archives.special}" style="width:98%;padding-left:2px"/>
									</td>	
									<td nowrap class="tablelabel" width="10%">计算机水平：</td>
									<td colspan="2" align="left" class="tablecontent" width="20%">
										<input name="compuLevel" value="${archives.compuLevel}" style="width:242px;padding:2px" />
									</td>
								</tr>
								<tr height="40" class="tablelabel">
									<td nowrap class="tablelabel" width="10%">所学外语语种：</td>
									<td align="left" class="tablecontent" width="20%">
										<input name="foreignType" value="${archives.foreignType}" style="width:200px;padding:2px" />
									</td>
									<td nowrap class="tablelabel" width="10%">兴趣爱好：</td>
									<td colspan="2" align="left" class="tablecontent" width="20%">
										<input type="text"  name="interestAndHobby" value="${archives.interestAndHobby}" style="width:70%;padding-left:2px"/>
									</td>
								</tr>
							</table>
					</div>
					<div title="家庭信息" data-options="iconCls:'icon-group'">
						<table class="tableborder" id="formTable">
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">家庭人口数：</td>
								<td align="left" class="tablecontent" width="20%">
									<input type="text"  name="peoNum" value="${archives.peoNum}" style="width:70%;padding-left:2px"/>
								</td>
								<td nowrap class="tablelabel" width="10%">详细通讯地址：</td>
								<td align="left" class="tablecontent" width="20%">
									<input type="text"  name="addr" value="${archives.addr}" style="width:70%;padding-left:2px"/>
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">联系电话：</td>
								<td align="left" class="tablecontent" width="20%">
									<input type="text"  name="tel" value="${archives.tel}" style="width:70%;padding-left:2px"/>
								</td>
								<td nowrap class="tablelabel" width="10%">家庭经济状况：</td>
								<td align="left" class="tablecontent" width="20%">
									<input name="ecoStatus" value="${archives.ecoStatus}" style="width:200px;padding:2px" />
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">主要经济来源：</td>
								<td align="left" class="tablecontent" width="20%">
									<input type="text"  name="ecoFrom" value="${archives.ecoFrom}" style="width:70%;padding-left:2px"/>
								</td>
								<td nowrap class="tablelabel" width="10%">总收入（万元/年）：</td>
								<td align="left" class="tablecontent" width="20%">
									<input type="text"  name="ecoTotal" value="${archives.ecoTotal}" style="width:70%;padding-left:2px"/>
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">主要支出：</td>
								<td align="left" class="tablecontent" width="20%">
									<input type="text"  name="ecoPay" value="${archives.ecoPay}" style="width:70%;padding-left:2px"/>
								</td>
								<td nowrap class="tablelabel" width="10%">家庭教育背景：</td>
								<td align="left" class="tablecontent" width="20%">
									<input name="teachBack" value="${archives.teachBack}" style="width:200px;padding:2px" />
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">是否有低保证：</td>
								<td align="left" class="tablecontent" width="20%">
									<input name="basicLive" value="${archives.basicLive}" style="width:200px;padding:2px" />
								</td>
								<td nowrap class="tablelabel" width="10%">是否享受过补助：</td>
								<td align="left" class="tablecontent" width="20%">
									<input name="helping" value="${archives.helping}" style="width:200px;padding:2px" />
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
				<table border="0" align="center" cellpadding="0" cellspacing="0" style="width:750px;">
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
	<div data-options="region:'south',border:false" style="text-align:center;padding:5px 0;height:40px">
		<a id="submitBtn" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="javascript:submitArch();">提交</a>　
		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:window.parent.closetab();">关闭</a>
	</div>
</div>



<!-- 不可见 -->
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
<div id="win_we_query" class="easyui-window" data-options="title:'添加工作经历 ',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:650px;height:300px;padding:5px;">
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
<!-- 不可见 -->
</body>
</html>