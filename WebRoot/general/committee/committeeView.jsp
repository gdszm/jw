<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc;">
<div align="center" style="padding: 5px;overflow: hidden;">
	<form id="addForm" method="post">
	<table border="0" align="center" cellpadding="0" cellspacing="0" style="width:1024px;">
    <tr>
      <td>	
		<table id="addtable" class="tableborder">				
			<tr height="40">
				              <td height="45" colspan="5" align="middle" nowrap="nowrap" class="tablemain" width="100%">
				               	<div align="center"><strong>政协巴彦淖尔市委员信息</strong></div>
				              </td>
				            </tr>
				            <tr height="40" class="tablelabel">
				          		<td nowrap class="tablelabel" width="10%">委&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;员：</td>
								<td align="left" class="tablecontent" width="15%">
									${comm.name}
								</td>
								<td nowrap class="tablelabel" width="10%">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</td>
								<td align="left" class="tablecontent" width="15%">
									${comm.sexName}
								</td>
								<td align="center" class="tablecontent" width="12%" rowspan="5" style="text-align: center;vertical-align:middle;">
									<c:if test="${empty comm.picName}">
										<c:if test="${comm.sex==1}">
											<img id="vImg1" src="${pageContext.request.contextPath}/style/images/man.jpg" style="width:97%;text-align:center;padding:2px;">
										</c:if>
										<c:if test="${comm.sex==2}">
											<img id="vImg1" src="${pageContext.request.contextPath}/style/images/woman.jpg" style="width:97%;text-align:center;padding:2px;">
										</c:if>
									</c:if>
									<c:if test="${!empty comm.picName}">
										<img id="vImg1" src="${pageContext.request.contextPath}/upload/mobile/${comm.picName}" style="width:97%;text-align:center;padding:2px;">
									</c:if>
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">专&nbsp;&nbsp;委&nbsp;&nbsp;会：</td>
								<td align="left" class="tablecontent" width="20%">${comm.committeeName}</td>
								<td nowrap class="tablelabel" width="10%">党&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;派：</td>
								<td align="left" class="tablecontent" width="20%">${comm.partyName}</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">界&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</td>
								<td align="left" class="tablecontent" width="20%">${comm.circleName}</td>
								<td nowrap class="tablelabel" width="10%">学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位：</td>
								<td align="left" class="tablecontent" width="20%">${comm.degreeName}</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;历：</td>
								<td align="left" class="tablecontent" width="20%">${comm.eduName}</td>
								<td nowrap class="tablelabel" width="10%">出生年月：</td>
								<td align="left" class="tablecontent" width="20%">
									${comm.birthDate}
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;业：</td>
								<td align="left" class="tablecontent" width="20%">
									${comm.vocation}
								</td>
								<td nowrap class="tablelabel" width="10%">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称：</td>
								<td align="left" class="tablecontent" width="20%">
									${comm.title}
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;务：</td>
								<td align="left" class="tablecontent" width="20%">${comm.job}</td>
								<td nowrap class="tablelabel" width="10%">电子邮箱：</td>
								<td align="left" class="tablecontent" width="20%" colspan="2">
									${comm.email}
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族：</td>
								<td align="left" class="tablecontent" width="20%">
									${comm.nationName}
								</td>
								<td nowrap class="tablelabel" width="10%">手&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机：</td>
								<td align="left" class="tablecontent" width="20%" colspan="2">
									${comm.telephone}
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">固定电话：</td>
								<td align="left" class="tablecontent" width="20%">
									${comm.comparyPhone}
								</td>
								<td nowrap class="tablelabel" width="10%">有效届次：</td>
								<td align="left" class="tablecontent" width="20%" colspan="2">
									${comm.secondaryName}
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">单位名称：</td>
								<td align="left" class="tablecontent" width="20%" colspan="4">
									${comm.companyName}
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">通讯地址：</td>
								<td align="left" class="tablecontent" width="20%">	
									${comm.comparyAddress}
								</td>
								<td nowrap class="tablelabel" width="10%">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编：</td>
								<td align="left" class="tablecontent" width="20%" colspan="2">
									${comm.comparyPost}
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">家庭地址：</td>
								<td align="left" class="tablecontent" width="20%" colspan="4">
									${comm.familyAddress}
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：</td>
								<td align="left" class="tablecontent" width="20%" colspan="4">
									${comm.statusName}
								</td>
							</tr>
						</table>
					</td>
				</tr>
		</table>
	</form>
</div>
</div>