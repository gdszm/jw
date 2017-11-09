<%@page import="com.tlzn.util.base.Constants"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="../../public/inc.jsp"></jsp:include>
	<script type="text/javascript">
    var propGrid;
	var pollGrid;
	var speakGrid;
	var meetingGrid;
	var activityGrid;
	 $(document).ready(function() {
	    	//提案
    		propGrid=$('#propgrid');
    		propGrid.datagrid({
				url:'${pageContext.request.contextPath}/prop!datagridFulfil.do?userCode='+'${comm.code}'+'&secondaryCode='+'${secondary.secondaryId}',
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
				url:'${pageContext.request.contextPath}/poll!datagridFulfil.do?userCode='+'${comm.code}'+'&secondaryCode='+'${secondary.secondaryId}',
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
				url:'${pageContext.request.contextPath}/speech!datagridFulfil.do?userCode='+'${comm.code}'+'&secondaryCode='+'${secondary.secondaryId}',
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
				url:'${pageContext.request.contextPath}/meetingMan!datagridFulfil.do?userCode='+'${comm.code}'+'&secondaryCode='+'${secondary.secondaryId}',
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
				url:'${pageContext.request.contextPath}/activitypeo!datagridFulfil.do?userCode='+'${comm.code}'+'&secondaryCode='+'${secondary.secondaryId}',
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
					{field:'agency',title:'承办机构',align:'center',width:140,sortable:true},
					//{field:'place',title:'地点',align:'center',width:150,sortable:true},
					{field:'astatusName',title:'出席情况',align:'center',width:80,sortable:true}
					
				]]
			});
			
			//生成委员考核表
			//$.ajax({
			//	url:'${pageContext.request.contextPath}/word!doNotNeedAuth_fulfilDownload.do?code='+'${comm.code}'+'&secondaryCode='+'${secondary.secondaryId}'+'&year='+'${secondary.year}',
			//		success : function(d) {
			//		var json = $.parseJSON(d);
			//		if (json.success) {
			//			$('#seebtn').linkbutton({text:'下载预览'});
			//			$('#seebtn').linkbutton('enable');
			//		}else{
			//			dj.messagerShow({
			//				msg : json.msg,
			//				title : '提示'
			//			});
			//		}
			//	}
			//});
	});
function down(){
	$('#downbtn').linkbutton('disable');
	$('#downbtn .l-btn-text').html('文件生成中,请稍候...');
	//生成委员考核表
	$.ajax({
		url:'${pageContext.request.contextPath}/word!doNotNeedAuth_fulfilDownload.do?code='+'${comm.code}'+'&secondaryCode='+'${secondary.secondaryId}'+'&year='+'${secondary.year}',
			success : function(d) {
			var json = $.parseJSON(d);
			if (json.success) {
					//打开
					$.ajax({
						url :"${pageContext.request.contextPath}/word!doNotNeedAuth_isfile.do?fileName=fulfil_"+'${secondary.year}'+'${comm.code}'+".doc",
						success : function(d) {
							var json = $.parseJSON(d);
							if (json.success) {
								$('#downbtn').linkbutton('enable');
								$('#downbtn .l-btn-text').html('下载');
								window.location.href="${pageContext.request.contextPath}/wordfile/fulfil_"+'${secondary.year}'+'${comm.code}'+".doc";
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
		url:'${pageContext.request.contextPath}/word!doNotNeedAuth_fulfilDownloadView.do?code='+'${comm.code}'+'&secondaryCode='+'${secondary.secondaryId}'+'&year='+'${secondary.year}',
			success : function(d) {
			var json = $.parseJSON(d);
			if (json.success) {
				$.ajax({
					url :'${pageContext.request.contextPath}/word!doNotNeedAuth_isfile.do?fileName=fulfil_'+"${secondary.year}"+"${comm.code}"+'.swf',
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
						    window.location.href='${pageContext.request.contextPath}/general/poll/pollSwf.jsp?swffile=fulfil_'+"${secondary.year}"+"${comm.code}"+'.swf';		
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
	</head>
	<body>
		<DIV class="easyui-layout" data-options="fit:true"
			class="cs-home-remark">
			<div data-options="region:'center',border:false"
				style="padding: 10px; border: 1px solid #ccc;"
				class="cs-home-remark">
				<center>
					<table border="0" align="center" cellpadding="0"
						cellspacing="0" style="width:1024px;">
						<tr>
							<td>
								<table class="tableborder" id="formTable" sizset="false">
									<tbody sizset="false">
										<tr height="30">
											<td height="45" colspan="4" align="middle" nowrap="nowrap"
												class="tablemain">
											   	<div align="center"><input type="hidden" name="secondaryId" value="${secondary.secondaryId}"/><strong>政协巴彦淖尔市${secondary.year}年度委员履职信息</strong></div>
											</td>
										</tr>
										<tr height="25">
											<td height="25"  width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												姓名：
											</td>
											<td height="25" width="240px" class="tablecontent"
												sizset="false" align="left">
												${comm.name}
											</td>
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：
											</td>
											<td height="25" width="240px" class="tablecontent"
												sizset="false" align="left">
												${comm.sexName}
											</td>
										</tr>
										<tr height="25">
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族：
											</td>
											<td height="25"  width="550px" align="left"
												class="tablecontent">
												${comm.nationName}
											</td>
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												出生日期：
											</td>
											<td height="25"  width="550px" align="left"
												class="tablecontent">
												${comm.birthDate}
											</td>
										</tr>
										<tr height="25">
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												政治面貌：
											</td>
											<td height="25" width="550px" align="left"
												class="tablecontent">
												${comm.partyName}
											</td>
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												所属专委会：
											</td>
											<td height="25" width="550px" align="left"
												class="tablecontent">
												${comm.committeeName}
											</td>
										</tr>
										<tr height="25">
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												工作单位：
											</td>
											<td height="25" width="550px" align="left"
												class="tablecontent" colspan="3">
												${comm.companyName}
											</td>
										</tr>
										<tr height="25">
											<td colspan="4" align="left" class="tablemain" width="100%">提交提案情况</td>
										</tr>
										<tr >
											<td height="200" colspan="4" align="center"  class="tablecontent" width="100%">
												<table id="propgrid">
												</table>
											</td>
										</tr>
										<tr height="25">
											<td  colspan="4" align="left" class="tablemain" width="100%">社情民意情况</td>
										</tr>
										<tr >
											<td height="200" colspan="4" align="center"  class="tablecontent" width="100%">
												<table id="pollgrid">
												</table>
											</td>
										</tr>
										<tr height="25">
											<td colspan="4" align="left" class="tablemain" width="100%">大会发言情况</td>
										</tr>
										<tr >
											<td height="200" colspan="4" align="center"  class="tablecontent" width="100%">
												<table id="speakgrid">
												</table>
											</td>
										</tr>
										<tr height="25">
											<td colspan="4" align="left" class="tablemain" width="100%">参加会议情况</td>
										</tr>
										<tr >
											<td height="200" colspan="4" align="center"  class="tablecontent" width="100%">
												<table id="meetinggrid">
												</table>
											</td>
										</tr>
										<tr height="25">
											<td colspan="4" align="left" class="tablemain" width="100%">参加活动情况</td>
										</tr>
										<tr >
											<td height="200" colspan="4" align="center"  class="tablecontent" width="100%">
												<table id="activitygrid">
												</table>
											</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
					</table>
				</center>
			</div>
			<div data-options="region:'south',border:false"
				style="text-align: center; padding: 5px 0; height: 40px">
			   <!--启用 -->
			   <c:set value="<%=Constants.CODE_TYPE_QYTY_YES%>" scope="page" var="status"></c:set>
<!-- 			   <c:if test="${comm.status==status}"> -->
					<a class="easyui-linkbutton" id="downbtn"  data-options="iconCls:'icon-down'"
						href="javascript:void(0)"  onclick="down();">下载</a>
<%--					<c:if test="${poll.updateFlg!='0'}">--%>
<!-- 						<a class="easyui-linkbutton" id="seebtn" disabled="true" data-options="iconCls:'icon-down'" -->
<!-- 						href="javascript:void(0)" onclick="">文件生成中,请稍候...</a> -->
<%--					</c:if>--%>
<%--					<c:if test="${poll.updateFlg=='0'}">--%>
						<a class="easyui-linkbutton" id="seebtn"  data-options="iconCls:'icon-down'"
						href="javascript:void(0)" onclick="openswf();">下载预览</a>
<%--					</c:if>--%>
<!-- 				</c:if> -->
				
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
					href="javascript:void(0)" onclick="javascript:window.close()"> 关 闭 </a>
			</div>
		</div>
	</body>
</html>