<%@page import="com.tlzn.util.base.Constants"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<!DOCTYPE html>
<html>
	<head>
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
							              <td height="45" colspan="4" align="middle" nowrap="nowrap" class="tablemain">
<!-- 							               	<div align="center"><strong>政协巴彦淖尔市notice.year年度通知公告</strong></div> -->
							               	<div align="center"><strong>公告详情</strong></div>
							              </td>
           								 </tr>
<!-- 										<tr height="25"> -->
<!-- 											<td height="25" width="80px" align="middle" nowrap="nowrap" -->
<!-- 												class="tablelabel"> -->
<!-- 												年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度： -->
<!-- 											</td> -->
<!-- 											<td height="25" width="240px" colspan="3" class="tablecontent" -->
<!-- 												sizset="false" align="left"> -->
<!-- 												notice.year -->
<!-- 											</td> -->
<!-- 										</tr> -->
										<tr height="25">
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题：
											</td>
											<td height="25" colspan="3" width="550px" align="left"
												class="tablecontent">
												${notice.title}
											</td>
										</tr>
										<tr height="25">
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												发布单位：
											</td>
											<td height="25" width="550px" align="left"
												class="tablecontent">
												${notice.pubUnitName}
											</td>
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												是否回复：
											</td>
											<td height="25" width="550px" align="left"
												class="tablecontent">
												${notice.replyName}
											</td>
										</tr>
										<tr height="25">
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												公告类型：
											</td>
											<td height="25" width="550px" align="left"
												class="tablecontent">
												${notice.noticeTypeName}
											</td>
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												状态：
											</td>
											<td height="25" width="550px" align="left"
												class="tablecontent">
												${notice.statusName}
											</td>
										</tr>
										<tr height="25">
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												创建日期：
											</td>
											<td height="25" colspan="3" width="550px" align="left"
												class="tablecontent">
												${notice.creatDate}
											</td>
										</tr>
										<tr height="25">
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												发布日期：
											</td>
											<td height="25" colspan="3" width="550px" align="left"
												class="tablecontent">
												${notice.pubDate}
											</td>
										</tr>
										<tr height="25">
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												有效日期：
											</td>
											<td height="25" colspan="3" width="550px" align="left"
												class="tablecontent">
												${notice.validDate}
											</td>
										</tr>
										<tr>
											<td height="25" colspan="4" class="tablelabel" >
												公告内容
											</td>
										</tr>
										<tr>
											<td colspan="4" class="tablecontent" width="100%" height="260" align="left">
												${notice.content}
											</td>
										</tr>
							            <tr height="25">
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												附件：
											</td>
											<td height="25" colspan="3" width="550px" align="left"
												class="tablecontent">
												<c:forEach items="${attList}" var="att" varStatus="status">
									              	<a id="vImg${status.index+1}" href="upload/mobile/${att}">${att}</a>&nbsp;&nbsp;
										        </c:forEach>
											</td>
										</tr>
										<tr height="25">
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												备注：
											</td>
											<td height="25" colspan="3" width="550px" align="left"
												class="tablecontent">
												<textarea style="width:99.8%;height: 50px; font-size: 14px; " readonly="readonly">${notice.memo}</textarea>
											</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
					</table>
				</center>
			</div>
		</div>
	</body>
</html>