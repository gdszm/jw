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
					<table border="0" align="center" style="width:1024px;">
						<tr>
							<td>
								<table class="tableborder" id="formTable" >
									<tbody >
										<tr height="30">
							              <td height="45" colspan="4" align="center" nowrap="nowrap" class="tablemain">
							               	<div align="center"><strong>政协巴彦淖尔市${speech.year}年度会议发言</strong></div>
							              </td>
							            </tr>
										<tr height="25">
											<td height="25"  width="80px" align="center" nowrap="nowrap"
												class="tablelabel">
												年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度：
											</td>
											<td height="25" width="240px" class="tablecontent"
												align="left" style="padding:0px 2px">
												${speech.year}
											</td>
											<td height="25" width="80px" align="center" nowrap="nowrap"
												class="tablelabel">
												采用情况：
											</td>
											<td height="25" width="240px" class="tablecontent"
												align="left" style="padding:0px 2px">
												${speech.useSituationName}
											</td>
										</tr>
										<tr height="25">
											<td height="25" width="80px" align="center" nowrap="nowrap"
												class="tablelabel">
												标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题：
											</td>
											<td height="25" colspan="3" width="550px" align="left"
												class="tablecontent" style="padding:0px 2px">
												${speech.title}
											</td>
										</tr>
										<tr height="25">
											<td height="25" width="80px" align="center" nowrap="nowrap"
												class="tablelabel">
												状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：
											</td>
											<td height="25" width="550px" align="left"
												class="tablecontent" style="padding:0px 2px">
												${speech.statusName}
											</td>
											<td height="25" width="80px" align="center" nowrap="nowrap"
												class="tablelabel">
												审查日期：
											</td>
											<td height="25" width="550px" align="left"
												class="tablecontent" style="padding:0px 2px">
												${speech.auditTime}
											</td>
										</tr>
										<tr height="25">
											<td height="25" width="80px" align="center" nowrap="nowrap"
												class="tablelabel">
												发&nbsp;言&nbsp;人：
											</td>
											<td height="25" width="550px" align="left"
												class="tablecontent" style="padding:0px 2px">
												${speech.name}
											</td>
											<td height="25" width="80px" align="center" nowrap="nowrap"
												class="tablelabel">
												手&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机：
											</td>
											<td height="25" width="550px" align="left"
												class="tablecontent" style="padding:0px 2px">
												${speech.telephone}
											</td>
										</tr>
										<tr height="25">
											<td height="25" width="80px" align="center" nowrap="nowrap"
												class="tablelabel">
												通讯地址：
											</td>
											<td height="25" width="550px" align="left"
												class="tablecontent" style="padding:0px 2px">
												${speech.comparyAddress}
											</td>
											<td height="25" width="80px" align="center" nowrap="nowrap"
												class="tablelabel">
												电子邮箱：
											</td>
											<td height="25" width="550px" align="left"
												class="tablecontent" style="padding:0px 2px">
												${speech.email}
											</td>
										</tr>
										<tr height="25">
											<td height="25" width="80px" align="center" nowrap="nowrap"
												class="tablelabel" >
												所属会议：
											</td>
											<td height="25" width="550px" align="left"
												class="tablecontent" style="padding:0px 2px">
												${speech.meetName}
											</td>
											<td height="25" width="80px" align="center" nowrap="nowrap"
												class="tablelabel">
												提交日期：
											</td>
											<td height="25" width="550px" align="left"
												class="tablecontent" style="padding:0px 2px">
												${speech.createTime}
											</td>
										</tr>
<%--										<tr height="25">--%>
<%--											<td height="25" width="80px" align="middle" nowrap="nowrap"--%>
<%--												class="tablelabel">--%>
<%--												备注：--%>
<%--											</td>--%>
<%--											<td height="25" colspan="3" width="550px" align="left"--%>
<%--												class="tablecontent" >--%>
<%--												<textarea style="width:99.8%;height: 50px; font-size: 14px;" readonly="readonly" >${speech.memo}</textarea>--%>
<%--											</td>--%>
<%--										</tr>--%>
										<tr>
											<td colspan="4" class="tablecontent" width="100%">
												<div style="width:1024px;height:345px;" > 
													<div title="&nbsp;&nbsp;发言内容&nbsp;&nbsp;"
														style="padding: 3px">
														<div style="padding:12px;line-height:25pt;" class="tanr-class" id="tanr">
															${speech.content}
														</div>
													</div>
												</div>
											</td>
										</tr>
							            <tr height="25">
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												附件描述：
											</td>
											<td height="25" width="80px" align="left" nowrap="nowrap"
												class="tablecontent" style="padding:0px 2px">
												${speech.attsDepict}
											</td>
											<td height="25" colspan="2" width="550px" align="left"
												class="tablecontent" style="padding:0px 2px">
												<c:forEach items="${attList}" var="att" varStatus="status">
									              	<a id="vImg${status.index+1}" href="upload/mobile/${att}">${att}</a>&nbsp;&nbsp;
										        </c:forEach>
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