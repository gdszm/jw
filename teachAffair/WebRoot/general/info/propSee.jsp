<%@page import="com.tlzn.util.base.Constants"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="../../public/inc.jsp"></jsp:include>
		<script type="text/javascript">
	var gridtawyk;
	var gridtablk;
	var gridtalog;
	 $(document).ready(function() {
	    	//提案人信息列表
	    	gridtawyk=$('#tawygrid');
			gridtawyk.datagrid({
				url:'${pageContext.request.contextPath}/sponsor!datagrid.do?proposalId='+${obj.proposalId},
				width:'auto',
				height:'auto',
				striped:true,
				nowrap:true,
				rownumbers:true,
				sortName: 'hostFlg',
				sortOrder: 'desc',
				fitColumns:true,
				fit:true,
				columns:[[
					{field:'proposalerId',title:'',hidden:true},
					{field:'code',title:'编号',align:'center',width:60,sortable:false},
					{field:'name',title:'提案人',align:'center',width:100,sortable:false},
					{field:'groupName',title:'提案人分组',align:'center',width:100,sortable:false},
					//{field:'sexName',title:'性别',align:'left',width:40,sortable:false},				
					{field:'telephone',title:'联系电话',align:'left',width:120,sortable:false},
					{field:'comparyPhone',title:'固定电话',align:'center',width:100,sortable:false},
					{field:'email',title:'电子邮箱',align:'center',width:120,sortable:false},
					{field:'comparyAddress',title:'通讯地址',align:'center',width:200,sortable:false},
					{field:'comparyPost',title:'邮编',align:'center',width:100,sortable:false},
					//{field:'groupCode',hidden:true},
					//{field:'groupName',title:'提案人分组',align:'center',width:90,sortable:false},
					{field:'hostFlgName',title:'是否第一提案人',align:'center',width:80,sortable:false}				
				]],
				view: detailview,
				detailFormatter: function(rowIndex, row){
					return '<div style="min-height:60px"><p>' +row.remark+ '</p>' +
							'</div>';
				}
			});
			//提案办理信息
			gridtablk=$('#tablkgrid');
			gridtablk.datagrid({
				url:'${pageContext.request.contextPath}/hand!datagridByProp.do?proposalId='+${obj.proposalId},
				width:'auto',
				height:'auto',
				striped:true,
				nowrap:true,
				rownumbers:true,
				singleSelect:true,
				idField:'handleReplyId',
				fitColumns:true,
				fit:true,
				columns:[[
					{field:'handleReplyId',title:'',hidden:true},
					{field:'companyId',title:'单位编号',align:'center',width:80,sortable:false},
					{field:'companyName',title:'单位名称',align:'left',width:220,sortable:false},
					{field:'companyType',title:'单位类别',align:'left',width:170,sortable:false},
					{field:'handleTypeName',title:'办理类型',align:'center',width:80,sortable:false},
					/*{field:'solveHowName',title:'解决程度',align:'center',width:80,sortable:false},
					{field:'carryoutFlgName',title:'是否落实',align:'center',width:80,sortable:false},
					{field:'commOpName',title:'提案人意见',align:'center',width:80,sortable:false},*/
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
						content=content+'<tr height="25"><td class=tablelabel nowrap style="padding:6px">沟通方式：</td><td class=tablecontent width=200 style="padding:6px">'+row.answerModeName+'</td>';
						content=content+'<td class=tablelabel nowrap style="padding:6px">解决程度：</td><td class=tablecontent width=200 style="padding:6px">'+row.solveHowName+'</td></tr>';
						content=content+'<tr height="25"><td class=tablelabel nowrap style="padding:6px">提案人意见：</td><td class=tablecontent width=200 style="padding:6px">'+row.commOpName+'</td>';
						content=content+'<td class=tablelabel nowrap style="padding:6px">实际办结日期：</td><td class=tablecontent  width=200 style="padding:6px">'+row.factEnddate+'</td></tr>';
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
					gridtablk.datagrid('fixDetailRowHeight',index);  
				}  
				})
			gridtalog=$('#taloggrid');
			gridtalog.datagrid({
				url:'${pageContext.request.contextPath}/dolog!datagridByWhere.do?keyId='+${obj.proposalId}+'&logType=<%=Constants.LOG_TYPE_CODE_PROP%>',
				width:'auto',
				height:'auto',
				striped:true,
				nowrap:true,
				rownumbers:true,
				singleSelect:true,
				pagination:true,
				sortName:'operator',
				sortOrder:'desc',
				pageSize:20,
				pageList:[20,50,100],
				pageNumber:1,
				idField:'logCode',
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
					url :'${pageContext.request.contextPath}/word!doNotNeedAuth_download.do?proposalId='+${obj.proposalId},
					success : function(d) {
						var json = $.parseJSON(d);
						if (json.success) {
							$('#seebtn').linkbutton({text:'转办预览'});
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
		url :"${pageContext.request.contextPath}/word!doNotNeedAuth_isfile.do?fileName=proposal_"+'${obj.proposalCode}'+".doc",
		success : function(d) {
			var json = $.parseJSON(d);
			if (json.success) {
				window.location.href="${pageContext.request.contextPath}/wordfile/proposal_"+'${obj.proposalCode}'+".doc";
			}else{
				dj.messagerShow({
					msg : json.msg,
					title : '提示'
				});
			}
		}
	});
}
//提案预览
function openswf(){
	$.ajax({
		url :'${pageContext.request.contextPath}/word!doNotNeedAuth_isfile.do?fileName=proposal_'+"${obj.proposalCode}"+'.swf',
		success : function(d) {
			var json = $.parseJSON(d);
			if (json.success) {
				 var dh=80;
   				 var dw=90;
				var wh=screen.width*dh/100;
				var ht=screen.height*dw/100;
				//window.showModalDialog('${pageContext.request.contextPath}/general/info/propSwf.jsp?swffile=proposal_'+${obj.proposalCode}+'.swf',window,"dialogHeight: "+ht+"px; dialogWidth: "+wh+"px;");
			    window.location.href='${pageContext.request.contextPath}/general/info/propSwf.jsp?swffile=proposal_'+"${obj.proposalCode}"+'.swf';		
			}else{
				dj.messagerShow({
					msg : json.msg,
					title : '提示'
				});
			}
		}
	});
}
/*function createXmlHttp() { 
	var xmlHttp;
	if (window.XMLHttpRequest) { 
		xmlHttp = new XMLHttpRequest(); 
		if (xmlHttp.overrideMimeType) { 
		xmlHttp.overrideMimeType("text/xml"); 
		} 
	} else if (window.ActiveXObject) { 
		try { 
			xmlHttp = new ActiveXObject("MSXML2.XMLHTTP"); 
		} catch (e) { 
			try { 
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP"); 
			} catch (e) { 
				alert("不能创建XmlHttpRequest"); 
			} 
		} 
	}
	
	return xmlHttp;
} 
function lgm(url)
{ 

    var xmlHttp = createXmlHttp();
   
    if(xmlHttp)
    {

        xmlHttp.open("get", url, false); 
        xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		
		 xmlHttp.onreadystatechange=function()
       {
       
            if(xmlHttp.readyState==4)
            {
            
             if(xmlHttp.status==0 || xmlHttp.status==200)
             {
               alert(xmlHttp.status);   
              var resultM=xmlHttp.responseText;
			  //alert(resultM);		  
            document.getElementById("AllAction").value+=resultM;
             }
            }
            else
            {
            
            }
        };
		xmlHttp.send(null); //null,对ff浏览器是必须的
    }      
}*/
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
			   										<c:if test="${obj.meetingFlg=='0'}">
											   			<div align="center"><input type="hidden" name="secondaryId" value="${sessionSeco.secondaryId}"/><strong>政协巴彦淖尔市第${fn:substring( sessionSeco.secondayName,0,fn:indexOf(sessionSeco.secondayName,'届')+1)}委员会第${fn:substring(sessionSeco.secondayName,fn:indexOf(sessionSeco.secondayName,'届')+1,fn:length(sessionSeco.secondayName))}会议提案</strong></div>
											   		</c:if>
											   		<c:if test="${obj.meetingFlg=='1'}">
											   			<div align="center"><input type="hidden" name="secondaryId" value="${sessionSeco.secondaryId}"/><strong>政协巴彦淖尔市第${fn:substring( sessionSeco.secondayName,0,fn:indexOf(sessionSeco.secondayName,'届')+1)}委员会${sessionSeco.year}年度提案</strong></div>
											   		</c:if>
											</td>
										</tr>
										<tr height="25">
											<td height="25"  width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												提案编号：
											</td>
											<td height="25" width="240px" class="tablecontent"
												sizset="false" align="left">
												${obj.proposalCode}
											</td>
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												届&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;次：
											</td>
											<td height="25" width="240px" class="tablecontent"
												sizset="false" align="left">
												${obj.secondayName}
											</td>
										</tr>
										<tr height="25">
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												案&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;由：
											</td>
											<td height="25" colspan="3" width="550px" align="left"
												class="tablecontent">
												${obj.title}
											</td>
										</tr>
										<tr height="25">
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												提案类型：
											</td>
											<td height="25" width="240px" class="tablecontent"
												sizset="false" align="left">
												${obj.proposalTypeName}
											</td>
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												提案状态：
											</td>
											<td height="25" width="240px" class="tablecontent"
												sizset="false" align="left">
												<c:choose>  
						                      	  <c:when test="${obj.stutas=='2'&&obj.fileMethod=='0'}"> 
						                      	  		已立案
												   </c:when>  
												    <c:when test="${obj.stutas=='2'&&obj.fileMethod=='1'}"> 
						                      	  		未立案
												   </c:when>
												    <c:when test="${obj.stutas=='2'&&obj.fileMethod=='0'&&obj.submitFlg=='0'}"> 
						                      	  		未交办
												   </c:when>
												   <c:when test="${obj.replyPass!='1'&&obj.submitFlg=='1'}"> 
						                      	  		已交办
												   </c:when>
												   <c:when test="${obj.stutas=='1'&&obj.replyPass!='1'}"> 
						                      	  		未答复
												   </c:when>
												   <c:when test="${obj.stutas=='4'&&obj.replyPass=='1'}"> 
						                      	  		已答复
												   </c:when>
												   <c:otherwise> 
												   	${obj.stutasName}
												   </c:otherwise>
												</c:choose>  
													
												
											</td>
										</tr>
										<tr height="25">
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												可否附议：
											</td>
											<td height="25" width="240px" class="tablecontent"
												sizset="false" align="left">
												${obj.secondFlgName}
											</td>
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												可否公开：
											</td>
											<td height="25" colspan="3" width="240px" class="tablecontent"
												sizset="false" align="left">
												${obj.webFlgName}
											</td>
										</tr>
										<tr height="25">
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												内容分类：
											</td>
											<td height="25" width="240px" class="tablecontent"
												sizset="false" align="left">
												${obj.propTypeName}
											</td>
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												分类号：
											</td>
											<td height="25" width="240px" class="tablecontent"
												sizset="false" align="left">
												${obj.typeId}
											</td>
										</tr>
										<c:if test="${obj.mergeFlag=='1'}">
											<tr height="25">
												<td height="25" width="80px" align="middle" nowrap="nowrap"
													class="tablelabel">
													合并意见：
												</td>
												<td height="25" colspan="3" valign="middle" width="240px" class="tablecontent"
													sizset="false" align="left">
													<textarea style="width:99%;height: 50px; font-size: 14px; ">${obj.adoptExplain}</textarea>
												</td>
												
											</tr>
										</c:if>
										<c:if test="${obj.fileMethod=='1'}">
											<tr height="40">
												<td height="40" width="80px" align="middle" nowrap="nowrap"
													class="tablelabel">
													未立案理由：
												</td>
												<td height="40" colspan="3" valign="middle" width="240px" class="tablecontent"
													sizset="false" align="left">
													<textarea style="width:99%;height: 80px; font-size: 14px;  ">${obj.noFillingReason}</textarea>
												</td>
												
											</tr>
										</c:if>
										<tr>
											<td colspan="4" class="tablecontent" width="100%">
												<div class="easyui-tabs" style="width:1024px;height:auto">
													<div title="&nbsp;&nbsp;提案内容&nbsp;&nbsp;"
														style="padding: 3px">
														<div style="padding:12px;line-height:25pt;" class="tanr-class" id="tanr">
															${obj.content}
														</div>
													</div>
													<div title="提案人信息" style="padding:3px;height:580px;">
														<!-- 表格 -->
														<table id="tawygrid">
														</table>
													</div>
													<div title="提案办理信息" style="padding: 3px;height:580px;">
														<!-- 表格 -->
														<table id="tablkgrid">
														</table>
													</div>
													<c:if test="${sessionInfo.userGroup=='3'}">
														<div title="相关操作日志" style="padding: 3px;height:580px;">
															<table id="taloggrid"></table>
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
				<a class="easyui-linkbutton" id="downbtn"  data-options="iconCls:'icon-down'"
					href="javascript:void(0)"  onclick="down();">下载</a>
				<c:if test="${obj.updateFlg!='0'}">
				<a class="easyui-linkbutton" id="seebtn" disabled=true data-options="iconCls:'icon-down'"
					href="javascript:void(0)" onclick="openswf();">文件生成中,请稍候...</a></c:if>
				<c:if test="${obj.updateFlg=='0'}">
				<a class="easyui-linkbutton" id="seebtn"  data-options="iconCls:'icon-down'"
					href="javascript:void(0)" onclick="openswf();">转办预览</a></c:if>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
					href="javascript:void(0)" onclick="javascript:window.close()"> 关 闭 </a>
			</div>
		</div>
	</body>
</html>