<%@page import="com.tlzn.util.base.Constants"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="../../public/inc.jsp"></jsp:include>
		<script type="text/javascript">
    var submittergrid;
	var gridHand;
	var gridCheck;
	var gridlog;
	var scoreGrid;
	 $(document).ready(function() {
	    	//提交人信息列表
	    	if(${poll.pollType!='4'}){
	    		submittergrid=$('#submittergrid');
		    	submittergrid.datagrid({
					url:'${pageContext.request.contextPath}/poll!submitter.do',
					width:'auto',
					height:'auto',
					striped:true,
					nowrap:true,
					rownumbers:true,
					fitColumns:true,
					fit:true,
					queryParams: {
						createMan: '${poll.createMan}'
					},
					columns:[[
						{field:'code',title:'编号',align:'center',width:60,sortable:false},
						{field:'name',title:'提交人',align:'center',width:100,sortable:false},
						{field:'committeeName',title:'所属专委会',align:'center',width:100,sortable:false},
						{field:'sexName',title:'性别',align:'left',width:40,sortable:false},				
						{field:'telephone',title:'联系电话',align:'left',width:120,sortable:false},
						{field:'comparyPhone',title:'固定电话',align:'center',width:100,sortable:false},
						{field:'email',title:'电子邮箱',align:'center',width:120,sortable:false},
						{field:'comparyAddress',title:'通讯地址',align:'center',width:200,sortable:false},
						{field:'comparyPost',title:'邮编',align:'center',width:100,sortable:false}
					]]
				});
	    	}
	    	//审查数据
	    	gridCheck=$('#checkgrid');
	    	gridCheck.datagrid({
				url:'${pageContext.request.contextPath}/pollCheck!datagrid.do?poll.pollId='+${poll.pollId},
				width:'auto',
				height:'auto',
				striped:true,
				nowrap:true,
				rownumbers:true,
				singleSelect:true,
				idField:'checkId',
				fitColumns:true,
				fit:true,
				columns:[[
					{field:'checkId',title:'',hidden:true},
					{field:'operatorName',title:'审批人',align:'center',width:150,sortable:false},
					{field:'checkTime',title:'审批时间',align:'left',width:180,sortable:false}
				]],
				view: detailview,
				detailFormatter:function(index,row){  
					return '<div id="tablk-ddv-' + index + '" style="padding:2px"></div>';  
				},  
				onExpandRow: function(index,row){
					var content='';
					if (row.diff==null||row.diff==''){
						content='<br/><br/><center><font color="#ff0000">没有审批数据!</font></center><br/><br/>';
					}else{
						content=row.diff;
					}
					$('#tablk-ddv-'+index).panel({   
						height:'auto',
						border: true,
						style: {borderWidth:2},  
						cache:false,
						content: content
					});  
					gridCheck.datagrid('fixDetailRowHeight',index);  
				}  
				});
	    	scoreGrid=$('#scoreGrid');
			//加载批示记分记录
			scoreGrid.datagrid({
				url:'${pageContext.request.contextPath}/pollScore!scoreRecordDatagrid.do?poll.pollId='+${poll.pollId},
				width: 'auto',
				height:'auto',
				striped:true,
				nowrap:true,
				rownumbers:true,
				singleSelect:true,
				pagination:true,
				pageSize:10,
				pageList:[10,20,50],
				pageNumber:1,
				idField:'id',
				sortName:'id',
				sortOrder : 'asc',
				fit:true,
				checkOnSelect: true, 
				selectOnCheck: true,
				view: detailview,
				detailFormatter:function(index,row){  
					return '<div id="tablk-ddv-' + index + '" style="padding:2px"></div>';  
				},  
				onExpandRow: function(index,row){
					var content='';
					if(row.comment!=null && row.comment!='') {
						content=content+'<center><font color="#ff0000">批示内容:</font></center>'+row.comment+'<br/>';
					}
					if(row.memo!=null && row.memo!='') {
						content=content+'<center><font color="#ff0000">备注:</font></center>'+row.memo+'<br/>';
					}
					if (content==''){
						content='<br/><br/><center><font color="#ff0000">没有内容或备注!</font></center><br/><br/>';
					}
					$('#tablk-ddv-'+index).panel({   
						height:'auto',
						border: true,
						style: {borderWidth:1},  
						cache:false,
						content: content
					});  
					scoreGrid.datagrid('fixDetailRowHeight',index);  
				},
				columns:[[
					{field:'scoreId',title:'ID',width:50,align:'center',checkbox : true},
					{field:'001',title:'计分类型名称',align:'center',width:150,
			            formatter:function(value,rec){
			            		return rec.rules.typeName;
			            }},
					{field:'002',title:'计分名称',align:'center',width:150,
		            	 formatter:function(value,rec){
			            		return rec.rules.name;
			            }},
					{field:'003',title:'分值 ',align:'center',width:50,
		            	 formatter:function(value,rec){
			            		return rec.rules.score;
			            }},
					{field:'creatTime',title:'录入时间',align:'center',width:180}
					
				]]
			});
			//办理信息
			gridHand=$('#handgrid');
			gridHand.datagrid({
				url:'${pageContext.request.contextPath}/pollHandle!handleGrid.do?poll.pollId='+${poll.pollId},
				width:'auto',
				height:'auto',
				striped:true,
				nowrap:true,
				rownumbers:true,
				singleSelect:true,
				idField:'handleId',
				fitColumns:true,
				fit:true,
				columns:[[
					{field:'handleId',title:'',hidden:true},
					{field:'companyId',title:'单位编号',align:'center',width:80,sortable:false,
	            	 formatter:function(value,rec){
		            		return rec.comp.companyId;
		            }},
					{field:'companyName',title:'单位名称',align:'left',width:220,sortable:false,
	            	 formatter:function(value,rec){
		            		return rec.comp.companyName;
		            }},
					{field:'companyType',title:'单位类别',align:'left',width:170,sortable:false,
	            	 formatter:function(value,rec){
		            		return rec.comp.companyTypeName;
		            }},
					{field:'handleTypeName',title:'办理类型',align:'center',width:80,sortable:false,
	            	 formatter:function(value,rec){
		            		return rec.poll.handleTypeName;
		            }},
					{field:'factEnddate',title:'办结日期',align:'center',width:100,sortable:false}
				]],
				view: detailview,
				detailFormatter:function(index,row){  
					return '<div id="tablk-ddv-' + index + '" style="padding:2px"></div>';  
				},  
				onExpandRow: function(index,row){
					var content='';
					if (row.factEnddate==null||row.factEnddate==''){
						content='<br/><br/><center><font color="#ff0000">没有办复报告!</font></center><br/><br/>';
					}else{
						content='<TABLE cellpadding=1 cellspacing=1 align=center class=tableborder><TBODY>';
<%--						content=content+'<tr height="25"><td class=tablelabel nowrap style="padding:6px">沟通方式：</td><td class=tablecontent width=200 style="padding:6px">'+row.answerModeName+'</td>';--%>
<%--						content=content+'<td class=tablelabel nowrap style="padding:6px">解决程度：</td><td class=tablecontent width=200 style="padding:6px">'+row.solveHowName+'</td></tr>';--%>
<%--						content=content+'<tr height="25"><td class=tablelabel nowrap style="padding:6px">是否落实：</td><td class=tablecontent width=200 style="padding:6px">'+row.carryoutFlgName+'</td>';--%>
<%--						content=content+'<td class=tablelabel nowrap style="padding:6px">实际办结日期：</td><td class=tablecontent  width=200 style="padding:6px">'+row.factEnddate+'</td></tr>';--%>
						content=content+'<tr height="25"><td class=tablecontent colspan="4" style="padding:6px" align="left">'+row.reply+'</td></tr>';
						content=content+'</TBODY></TABLE>';
					}
					$('#tablk-ddv-'+index).panel({   
						height:'auto',
						border: true,
						style: {borderWidth:2},  
						cache:false,
						content: content
					});  
					gridHand.datagrid('fixDetailRowHeight',index);  
				}  
				});
			//相关日志
			gridlog=$('#loggrid');
			gridlog.datagrid({
				url:'${pageContext.request.contextPath}/dolog!datagridByWhere.do?keyId='+${poll.pollId}+'&logType=<%=Constants.LOG_TYPE_CODE_POLL%>',
				width:'auto',
				height:'auto',
				striped:true,
				nowrap:true,
				rownumbers:true,
				fitColumns:true,
				fit:true,
				columns:[[
					{field:'logCode',title:'',hidden:true},
					{field:'title',title:'日志标题',align:'left',width:100,sortable:false},
					{field:'info',title:'日志内容',align:'left',width:320,sortable:false},
					{field:'operator',title:'操作人员',align:'center',width:100,sortable:false},
					{field:'operateTime',title:'操作日期',align:'center',width:120,sortable:false}
				]]
			});
				$.ajax({
					url :'${pageContext.request.contextPath}/word!doNotNeedAuth_pollDownload.do?pollId='+${poll.pollId},
					success : function(d) {
						var json = $.parseJSON(d);
						if (json.success) {
							$('#seebtn').linkbutton({text:'下载预览'});
							$('#seebtn').linkbutton('enable');
						}else{
							dj.messagerShow({
								msg : json.msg,
								title : '提示'
							});
						}
					}
				});
	});
function down(){
	$.ajax({
		url :"${pageContext.request.contextPath}/word!doNotNeedAuth_isfile.do?fileName=poll_"+'${poll.tsecondary.year}'+'${poll.pollId}'+".doc",
		success : function(d) {
			var json = $.parseJSON(d);
			if (json.success) {
				window.location.href="${pageContext.request.contextPath}/wordfile/poll_"+'${poll.tsecondary.year}'+'${poll.pollId}'+".doc";
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
	$.ajax({
		url :'${pageContext.request.contextPath}/word!doNotNeedAuth_isfile.do?fileName=poll_'+"${poll.tsecondary.year}"+"${poll.pollId}"+'.swf',
		success : function(d) {
			var json = $.parseJSON(d);
			if (json.success) {
				 var dh=80;
   				 var dw=90;
				var wh=screen.width*dh/100;
				var ht=screen.height*dw/100;
				//window.showModalDialog('${pageContext.request.contextPath}/general/info/propSwf.jsp?swffile=proposal_'+${poll.pollCode}+'.swf',window,"dialogHeight: "+ht+"px; dialogWidth: "+wh+"px;");
			    window.location.href='${pageContext.request.contextPath}/general/poll/pollSwf.jsp?swffile=poll_'+"${poll.tsecondary.year}"+"${poll.pollId}"+'.swf';		
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
											   	<div align="center"><input type="hidden" name="secondaryId" value="${sessionSeco.secondaryId}"/><strong>政协巴彦淖尔市${sessionSeco.year}年度社情民意</strong></div>
											</td>
										</tr>
										<tr height="25">
											<td height="25"  width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												社情民意编号：
											</td>
											<td height="25" width="240px" class="tablecontent"
												sizset="false" align="left">
												${poll.pollCode}
											</td>
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度：
											</td>
											<td height="25" width="240px" class="tablecontent"
												sizset="false" align="left">
												${poll.tsecondary.year}
											</td>
										</tr>
										<tr height="25">
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题：
											</td>
											<td height="25" colspan="3" width="550px" align="left"
												class="tablecontent">
												${poll.title}
											</td>
										</tr>
										<tr height="25">
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												撰稿人：
											</td>
											<td height="25" colspan="3" width="550px" align="left"
												class="tablecontent">
												${poll.writer}<input type="hidden" id="createMan" value="${poll.createMan}" />
											</td>
										</tr>
										<tr height="25">
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：
											</td>
											<td height="25" width="240px" class="tablecontent"
												sizset="false" align="left">
												${poll.statusName}
											</td>
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												提交日期：
											</td>
											<td height="25" width="240px" class="tablecontent"
												sizset="false" align="left">
												${poll.createTime}
											</td>
										</tr>
										<tr height="25">
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												主&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;送：
											</td>
											<td height="25" width="240px" class="tablecontent"
												sizset="false" align="left" colspan="3">
												${poll.master}
											</td>
										</tr>
										<tr height="25">
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												抄&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;送：
											</td>
											<td height="25" width="240px" class="tablecontent"
												sizset="false" align="left" colspan="3">
												${poll.copyMan}
											</td>
										</tr>
										<c:if test="${poll.pollType=='4'}">
										<tr height="25" sizset="false" >
							              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">提交者：</td>
							              <td height="25" width="240px" class="tablecontent" sizset="false"  align="left">${poll.createMan}</td>
							              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">工作单位：</td>
							              <td height="25" width="240px" class="tablecontent" sizset="false"  align="left">${poll.unit}
							              </td>
							            </tr>
							            <tr height="25" sizset="false" >
							              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">联系电话：</td>
							              <td height="25" width="240px" class="tablecontent" sizset="false" align="left">${poll.phone}</td>
							              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">电子邮箱：</td>
							              <td height="25" width="240px" class="tablecontent" sizset="false"  align="left">${poll.email}
							              </td>
							            </tr>
							             <tr height="25" sizset="false" >
							              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">联系地址：</td>
							              <td height="25" colspan="3" width="240px" class="tablecontent" sizset="false" align="left" >${poll.address}</td>
							            </tr>
							           </c:if>
							           <c:if test="${poll.mergeFlg=='1'}">
											<tr height="25">
												<td height="25" width="80px" align="middle" nowrap="nowrap"
													class="tablelabel">
													合并意见：
												</td>
												<td height="25" colspan="3" valign="middle" width="240px" class="tablecontent"
													sizset="false" align="left">
													<textarea style="width:99%;height: 50px; font-size: 14px; ">${poll.adoptExplain}</textarea>
												</td>
												
											</tr>
										</c:if>
										<tr>
											<td colspan="4" class="tablecontent" width="100%">
												<div class="easyui-tabs" style="width:1024px;height:580px">
													<div title="&nbsp;&nbsp;社情民意审签稿&nbsp;&nbsp;"
														style="padding: 3px">
														<div style="padding:12px;line-height:25pt;" class="tanr-class" id="tanr">
															${poll.endContent}
														</div>
													</div>
													<div title="&nbsp;&nbsp;社情民意原稿&nbsp;&nbsp;"
														style="padding: 3px">
														<div style="padding:12px;line-height:25pt;" class="tanr-class" id="tanr">
															${poll.content}
														</div>
													</div>
													<c:if test="${poll.pollType!='4'}">
													<div title="提交人信息" style="padding:3px;height:580px;">
														<!-- 表格 -->
														<table id="submittergrid">
														</table>
													</div>
													</c:if>
													<c:if test="${sessionInfo.userGroup=='3' ||  sessionInfo.userGroup=='5' || sessionInfo.userGroup=='6' || sessionInfo.userGroup=='7'}">
													<div title="审查审签情况" style="padding: 3px;height:580px;">
														<!-- 表格 -->
														<table id="checkgrid">
														</table>
													</div>
													</c:if>
													<div title="计分信息情况" style="padding: 3px;height:580px;">
														<!-- 表格 -->
														<table id="scoreGrid">
														</table>
													</div>
													<div title="办理信息" style="padding: 3px;height:580px;">
														<!-- 表格 -->
														<table id="handgrid">
														</table>
													</div>
													<c:if test="${sessionInfo.userGroup=='3' || sessionInfo.userGroup=='5' || sessionInfo.userGroup=='6' || sessionInfo.userGroup=='7'}">
														<div title="相关操作日志" style="padding: 3px;height:580px;">
															<table id="loggrid"></table>
														</div>
													</c:if>
												</div>

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
			   <c:if test="${poll.status!='0'&&poll.status!='1'&&poll.status!='2'}">
				<a class="easyui-linkbutton" id="downbtn"  data-options="iconCls:'icon-down'"
					href="javascript:void(0)"  onclick="down();">下载</a>
				<c:if test="${poll.updateFlg!='0'}">
				<a class="easyui-linkbutton" id="seebtn" disabled=true data-options="iconCls:'icon-down'"
					href="javascript:void(0)" onclick="openswf();">文件生成中,请稍候...</a></c:if>
				<c:if test="${poll.updateFlg=='0'}">
				<a class="easyui-linkbutton" id="seebtn"  data-options="iconCls:'icon-down'"
					href="javascript:void(0)" onclick="openswf();">下载预览</a></c:if>
				</c:if>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
					href="javascript:void(0)" onclick="javascript:window.close()"> 关 闭 </a>
			</div>
		</div>
	</body>
</html>