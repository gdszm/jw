<%--履职管理统合首页--%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.tlzn.util.base.Constants"%>

<script type="text/javascript">
	//页面大小用js
	var height=($(".layout-panel-center").css("height"));
	var h=parseInt(height.replace("px", ""));
	var lineHeight=(h)/18;
//	$("#lzxxTitle").css("height",h/30);	
	$("#addtable tr").css("height",lineHeight-2.1);
	
	$("#titleID").css("height",25);
	//$("#lzmxTABLE tr").css("height",h/6);
	var dgHeight=h/3.21;
	$("#taId").css("height",dgHeight);
	$("#sqmyId").css("height",dgHeight);
	$("#cjhyId").css("height",dgHeight);
	$("#dhfyId").css("height",dgHeight);
	$("#cjhdId").css("height",dgHeight);
	var titleGrid;
    var propGrid;
	var pollGrid;
	var speakGrid;
	var meetingGrid;
	var activityGrid;
	 $(document).ready(function() {
	 //提案
   			
	    	//提案
    		propGrid=$('#propgrid');
    		propGrid.datagrid({
    			title:'提交提案情况',
				url:'${pageContext.request.contextPath}/prop!datagridFulfil.do?userCode='+'${sessionInfo.userCode}'+'&secondaryCode='+'${sessionSeco.secondaryId}',
				width:'auto',
				height:'auto',
				striped:true,
				nowrap:true,
				rownumbers:true,
				singleSelect:true,
				fitColumns:true,
				fit:true,
				columns:[[
					{field:'propTypeName',title:'内容分类',align:'center',width:100,sortable:false},
					{field:'title',title:'案由',halign:'center',width:350,sortable:false},
					{field:'createTime',title:'提交日期',align:'center',width:100,sortable:false,
						formatter: function(value,row,index){
							if (value){
								return value.substring(0,10);
							} else {
								return value;
							}
						}
					},				
					{field:'fileMethod',title:'是否立案',align:'center',width:120,sortable:false,
						formatter: function(value,row,index){
							if (value=='<%=Constants.CODE_TYPE_LAXS_LA%>'){
								return "已立案";
							} else if(value=='<%=Constants.CODE_TYPE_LAXS_BLA%>'){
								return "未立案";
							} else {
								return "";
							}
						}
					}
				]]
			});
	    	//社情民意
	    	pollGrid=$('#pollgrid');
	    	pollGrid.datagrid({
	    		title:'社情民意情况',
				url:'${pageContext.request.contextPath}/poll!datagridFulfil.do?userCode='+'${sessionInfo.userCode}'+'&secondaryCode='+'${sessionSeco.secondaryId}',
				width:'auto',
				height:'auto',
				striped:true,
				nowrap:true,
				rownumbers:true,
				singleSelect:true,
				idField:'pollId',
				fitColumns:true,
				fit:true,
				columns:[[
					{field:'pollId',title:'',hidden:true},
					{field:'title',title:'标题',halign:'center',width:350,sortable:false},
					{field:'createTime',title:'提交日期',align:'center',width:100,sortable:false,
						formatter: function(value,row,index){
							if (value){
								return value.substring(0,10);
							} else {
								return value;
							}
						}
					},				
					//{field:'statusName',title:'状态',align:'left',width:120,sortable:false},
					{field:'adoptFlg',title:'采用情况',align:'center',width:100,sortable:false,
						formatter: function(value,row,index){
								if (value=='<%=Constants.CODE_TYPE_YESNO_YES%>'){
									return "采用";
								} else if(value=='<%=Constants.CODE_TYPE_YESNO_NO%>'){
									return "未采用";
								} else {
									return "";
								}
						}
					}
				]]
				});
	    	//大会发言
	    	speakGrid=$('#speakgrid');
	    	speakGrid.datagrid({
	    		title:'大会发言情况',
				url:'${pageContext.request.contextPath}/speech!datagridFulfil.do?userCode='+'${sessionInfo.userCode}'+'&secondaryCode='+'${sessionSeco.secondaryId}',
				width: 'auto',
				height:'auto',
				striped:true,
				nowrap:true,
				rownumbers:true,
				singleSelect:true,
				fit:true,
				fitColumns:true,
				columns:[[
					{field:'speechId',title:'ID',hidden:true},
					{field:'meetTypeName',title:'会议类型',align:'center',width:150,sortable:true},
					{field:'title',title:'标题',halign:'center',width:450},
					//{field:'meetName',title:'所属会议',align:'center',width:250},
					{field:'createTime',title:'上报日期',align:'center',width:180,
						formatter: function(value,row,index){
							if (value){
								return value.substring(0,10);
							} else {
								return value;
							}
						}
					},
					{field:'useSituationName',title:'采用情况',align:'center',width:100}
				]]
			});
			//参加会议情况
			meetingGrid=$('#meetinggrid');
			meetingGrid.datagrid({
				title:'参加会议情况',
				url:'${pageContext.request.contextPath}/meetingMan!datagridFulfil.do?userCode='+'${sessionInfo.userCode}'+'&secondaryCode='+'${sessionSeco.secondaryId}',
				width:'auto',
				height:'auto',
				striped:true,
				nowrap:true,
				rownumbers:true,
				singleSelect:true,
				idField:'meetingId',
				fitColumns:true,
				fit:true,
				columns:[[
					{field:'meetTypeName',title:'会议类型',align:'center',width:150,sortable:true},
					{field:'meetName',title:'会议名称',halign:'center',align:'left',width:450,sortable:true},
					{field:'beginTime',title:'会议时间',align:'center',width:180,sortable:true ,formatter : function(value,row){
						if(row.beginTime && row.endTime) {
							return row.beginTime.substring(0,10)+"至"+row.endTime.substring(0,10);
						} else {
						
						}
					}},
					{field:'statusName',title:'出席情况',align:'center',width:100,sortable:true}
				]]
				});
			//活动情况
			activityGrid=$('#activitygrid');
			activityGrid.datagrid({
				title:'参加活动情况',
				url:'${pageContext.request.contextPath}/activitypeo!datagridFulfil.do?userCode='+'${sessionInfo.userCode}'+'&secondaryCode='+'${sessionSeco.secondaryId}',
				width:'auto',
				height:'auto',
				striped:true,
				nowrap:true,
				singleSelect:true,
				rownumbers:true,
				fitColumns:true,
				fit:true,
				columns:[[
					{field:'aspeciesName',title:'活动种类',align:'center',width:160,sortable:true},				
					{field:'atheme',title:'活动主题',halign:'center',width:400,sortable:true},
					{field:'time',title:'活动时间',align:'center',width:180,sortable:true,formatter : function(value,row){
						if(row.timebeg&&row.timeend){return row.timebeg+"至"+row.timeend;}
					}},
					{field:'agencyName',title:'承办机构',align:'center',width:140,sortable:true},
					//{field:'place',title:'地点',align:'center',width:150,sortable:true},
					{field:'astatusName',title:'出席情况',align:'center',width:80,sortable:true}
					
				]]
			});
	});
	 
	 function down(){
			$('#downbtn').linkbutton('disable');
			$('#downbtn .l-btn-text').html('文件生成中,请稍候...');
			//生成委员考核表
			$.ajax({
				url:'${pageContext.request.contextPath}/word!doNotNeedAuth_fulfilDownload.do?code='+'${comm.code}'+'&secondaryCode='+'${sessionSeco.secondaryId}'+'&year='+'${sessionSeco.year}',
					success : function(d) {
					var json = $.parseJSON(d);
					if (json.success) {
							//打开
							$.ajax({
								url :"${pageContext.request.contextPath}/word!doNotNeedAuth_isfile.do?fileName=fulfil_"+'${sessionSeco.year}'+'${comm.code}'+".doc",
								success : function(d) {
									var json = $.parseJSON(d);
									if (json.success) {
										$('#downbtn').linkbutton('enable');
										$('#downbtn .l-btn-text').html('下载');
										window.location.href="${pageContext.request.contextPath}/wordfile/fulfil_"+'${sessionSeco.year}'+'${comm.code}'+".doc";
									}else{
										dj.messagerShow({
											msg : json.msg,
											title : '提示'
										});
									}
								}
							});
					}else{
						dj.messagerShow({
							msg : json.msg,
							title : '提示'
						});
					}
				}
			});
		}
		//下载预览
		function openswf(){
			$('#seebtn').linkbutton('disable');
			$('#seebtn .l-btn-text').html('文件生成中,请稍候...');
			//生成委员考核表
			$.ajax({
				url:'${pageContext.request.contextPath}/word!doNotNeedAuth_fulfilDownloadView.do?code='+'${comm.code}'+'&secondaryCode='+'${sessionSeco.secondaryId}'+'&year='+'${sessionSeco.year}',
					success : function(d) {
					var json = $.parseJSON(d);
					if (json.success) {
						$.ajax({
							url :'${pageContext.request.contextPath}/word!doNotNeedAuth_isfile.do?fileName=fulfil_'+"${sessionSeco.year}"+"${comm.code}"+'.swf',
							success : function(d) {
								var json = $.parseJSON(d);
								if (json.success) {
									$('#seebtn').linkbutton('enable');
									$('#seebtn .l-btn-text').html('下载预览');
									 var dh=80;
					   				 var dw=90;
									var wh=screen.width*dh/100;
									var ht=screen.height*dw/100;
									//window.showModalDialog('${pageContext.request.contextPath}/general/info/propSwf.jsp?swffile=proposal_'+${poll.pollCode}+'.swf',window,"dialogHeight: "+ht+"px; dialogWidth: "+wh+"px;");
								    window.location.href='${pageContext.request.contextPath}/general/poll/pollSwf.jsp?swffile=fulfil_'+"${sessionSeco.year}"+"${comm.code}"+'.swf';		
								}else{
									dj.messagerShow({
										msg : json.msg,
										title : '提示'
									});
								}
							}
						});
					}else{
						dj.messagerShow({
							msg : json.msg,
							title : '提示'
						});
					}
				}
			});

		}
</script>
<style>
	.tablemain2 {
    BACKGROUND-COLOR: #A5E5F2;
    COLOR: #000000;
    font-size: 14px;
    font-weight: bold;
    border-top:solid #95B8E7 1px;
    border-right:solid #95B8E7 1px;
    }
    #addtable {
    	height:100%
    }
</style>
	<div class="easyui-layout" data-options="fit:true" class="cs-home-remark">
		<div data-options="region:'center',border:false" class="cs-home-remark">
<center>
<div style="background:#fff;border:1px solid #red;">
<div align="center" style="padding: 2px;overflow: hidden;">
	<table border="0" align="center" cellpadding="0" cellspacing="0" style="width:100%;height:100%;">
			<tr>	
				<td width="23%" height="100%">
					<table id="addtable" class="tableborder">
						<tr id="titleID" class="tablelabel">
							<td nowrap class="tablecontent"  colspan="3">
<!-- 								<table id="titleGrid"></table> -->
									<div class="panel" style="display: block; width: auto;">
										<div class="panel-header panel-header-noborder">
											<div class="panel-title">委员基本信息</div>
										</div>
									</div></td>
						</tr>
						<tr class="tablelabel">
							<td nowrap class="tablelabel" width="2%">委&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;员：</td>
							<td align="left" class="tablecontent" width="15%" style="padding-left:3px;">
								${comm.name}
							</td>
<!-- 							<td nowrap class="tablelabel" width="5%">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</td> -->
<!-- 							<td align="left" class="tablecontent" width="15%"> -->
<!-- 								${comm.sexName}</td> -->
							<td align="center" class="tablecontent" width="12%" rowspan="4"
								style="text-align: center;vertical-align:middle;">
<!-- 										<img id="vImg1" src="${pageContext.request.contextPath}/style/images/man.jpg" style="height:95%;width:95%;text-align:center;padding:2px;"> -->
									<c:if test="${empty comm.picName}">
									<c:if test="${comm.sex==1}">
										<img id="vImg1" src="${pageContext.request.contextPath}/style/images/man.jpg" style="width:95%;text-align:center;padding:2px;">
									</c:if>
									<c:if test="${comm.sex==2}">
										<img id="vImg1" src="${pageContext.request.contextPath}/style/images/woman.jpg" style="width:95%;text-align:center;padding:2px;">
									</c:if>
									</c:if>
									<c:if test="${!empty comm.picName}">
										<img id="vImg1" src="${pageContext.request.contextPath}/upload/mobile/${comm.picName}" style="width:95%;text-align:center;padding:2px;">
									</c:if>
							</td>
						</tr>
						<tr class="tablelabel">
							<td nowrap class="tablelabel">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</td>
							<td align="left" class="tablecontent" style="padding-left:3px;">
								${comm.sexName}
							</td>
						</tr>
						<tr class="tablelabel">
							<td nowrap class="tablelabel">民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族：</td>
							<td align="left" class="tablecontent" style="padding-left:3px;">
								${comm.nationName}
							</td>
						</tr>
						<tr class="tablelabel">
							<td nowrap class="tablelabel">党&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;派：</td>
							<td align="left" class="tablecontent" style="padding-left:3px;">
								${comm.partyName}
							</td>
						</tr>
						<tr class="tablelabel">
							<td nowrap class="tablelabel">专&nbsp;&nbsp;委&nbsp;&nbsp;会：</td>
							<td align="left" class="tablecontent" colspan="2" style="padding-left:3px;">
								${comm.committeeName}</td>
						</tr>
						<tr class="tablelabel">
							<td nowrap class="tablelabel">学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位：</td>
							<td align="left" class="tablecontent" colspan="2" style="padding-left:3px;">
								${comm.degreeName}
							</td>
						</tr>
						<tr class="tablelabel">
							<td nowrap class="tablelabel">学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;历：</td>
							<td align="left" class="tablecontent" colspan="2" style="padding-left:3px;">
								${comm.eduName}
							</td>
						</tr>
						<tr class="tablelabel">
							<td nowrap class="tablelabel">出生年月：</td>
							<td align="left" class="tablecontent" colspan="2" style="padding-left:3px;">
								${comm.birthDate}
							</td>
						</tr>
						<tr class="tablelabel">
							<td nowrap class="tablelabel">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;业：</td>
							<td align="left" class="tablecontent" colspan="2" style="padding-left:3px;">
								${comm.vocation}
							</td>
						</tr>
						<tr class="tablelabel">
							<td nowrap class="tablelabel">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称：</td>
							<td align="left" class="tablecontent" colspan="2" style="padding-left:3px;">
								${comm.title}
							</td>
						</tr>
						<tr class="tablelabel">
							<td nowrap class="tablelabel">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;务：</td>
							<td align="left" class="tablecontent" colspan="2" style="padding-left:3px;">
								${comm.job}
							</td>
						</tr>
						<tr class="tablelabel">
							<td nowrap class="tablelabel">手&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机：</td>
							<td align="left" class="tablecontent" colspan="2" style="padding-left:3px;">
								${comm.telephone}
							</td>
						</tr>
						<tr class="tablelabel">
							<td nowrap class="tablelabel">固定电话：</td>
							<td align="left" class="tablecontent" colspan="2" style="padding-left:3px;">
								${comm.comparyPhone}
							</td>
						</tr>
						<tr class="tablelabel">
							<td nowrap class="tablelabel">电子邮箱：</td>
							<td align="left" class="tablecontent" colspan="2" style="padding-left:3px;">
								${comm.email}
							</td>
						</tr>
						<tr class="tablelabel">
							<td nowrap class="tablelabel">单位名称：</td>
							<td align="left" class="tablecontent" colspan="2" style="padding-left:3px;">
								${comm.companyName}
							</td>
						</tr>
						<tr class="tablelabel">
							<td nowrap class="tablelabel">通讯地址：</td>
							<td align="left" class="tablecontent" colspan="2" style="padding-left:3px;">
								${comm.comparyAddress}</td>
						</tr>
						<tr class="tablelabel">
							<td nowrap class="tablelabel">家庭地址：
							<td align="left" class="tablecontent" colspan="2" style="padding-left:3px;">
								${comm.familyAddress}
							</td>
						</tr>
					</table>
				</td>
				<td width="77%"> 
					<TABLE ID="lzmxTABLE" CLASS="TABLEBORDER" border="0" align="center"  cellpadding="0" cellspacing="0" style="width:100%;height:100%;vertical-align:top;height:100%;">
						<tr>
							<td align="left" nowrap="nowrap" width="100%" valign="top" style="white-space:nowrap;">
								<div id="taId" style="width:49.6%;float:left;margin:1px;">
									<table id="propgrid"></table>
								</div>
								<div id="sqmyId" style="width:49.6%;float:left;margin:1px;">
									<table id="pollgrid"></table>
								</div>
								<div id="dhfyId"  style="width:49.6%;float:left;margin:1px;">
									<table id="speakgrid"></table>
								</div>
								<div id="cjhyId"  style="width:49.6%;float:left;margin:1px;">
									<table id="meetinggrid"></table>	
								</div>
								<div id="cjhdId" style="width:99.45%;float:left;margin:1px;">
									<table id="activitygrid"></table>
								</div>
								<div style="clear:both;"></div>
							</td>
								
						</tr>
<!-- 						<tr>  -->
<!-- 							<td align="left" nowrap="nowrap" width="100%" valign="top" style="border:1px solid red;"> -->
<!-- 								社情民意情况 -->
<!-- 							</td> -->
<!-- 						</tr> -->
<!-- 						<tr> -->
<!-- 							<td align="left" nowrap="nowrap" width="100%" valign="top" style="border:1px solid red;"> -->
<!-- 								大会发言情况 -->
<!-- 							</td> -->
<!-- 						</tr> -->
<!-- 						<tr> -->
<!-- 							<td align="left" nowrap="nowrap" width="100%" valign="top" style="border:1px solid red;"> -->
<!-- 								参加会议情况 -->
<!-- 							</td> -->
<!-- 						</tr> -->
<!-- 						<tr> -->
<!-- 							<td align="left" nowrap="nowrap" width="100%" valign="top" style="border:1px solid red;"> -->
<!-- 								参加活动情况 -->
<!-- 							</td> -->
<!-- 						</tr> -->
					</TABLE>
				</td>
			</tr>
		</table>
	</div>
	
</div>
</center>
</div>
	<div data-options="region:'south',border:true"
		style="text-align:center;padding:5px 0;height:40px">
		<a class="easyui-linkbutton" id="downbtn"
			data-options="iconCls:'icon-down'" href="javascript:void(0)"
			onclick="down();">履职情况下载</a> 
		<a class="easyui-linkbutton" id="seebtn"
			data-options="iconCls:'icon-down'" href="javascript:void(0)"
			onclick="openswf();">履职情况下载预览</a>
		</td>
	</div>