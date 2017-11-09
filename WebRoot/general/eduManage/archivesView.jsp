<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>	
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<div data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc;">
<div align="center" style="padding: 5px;overflow: hidden;">
		<form id="addForm" method="post">
			<input type="hidden" name="archNo" value="${archives.archNo}" /> 
			<input type="hidden" name="baseInfoId" value="${archives.baseInfoId}" />
			 <input type="hidden" name="id" value="${archives.id}" />
			<table border="0" align="center" cellpadding="0" cellspacing="0" style="width:1024px;">
				<tr>
					<td>
						<table class="tableborder" id="formTable">
							<tr height="40">
				              <td height="45" colspan="5" align="middle" nowrap="nowrap" class="tablemain" width="100%">
				               	<div align="center"><strong>档案详情</strong></div>
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
									<p style="height:15px;line-height:15px;padding-left:2px;">${archives.urgentTel}</p>
								</td>
								<td nowrap class="tablelabel" width="10%">毕业学校：</td>
								<td colspan="2" align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${archives.gradSchool}</p>
								</td>
							</tr>
							<tr height="60">
				              <td nowrap class="tablelabel" width="10%">自我评价：</td>
				              <td colspan="4" class="tablecontent"  align="left">
				              	<textarea name="selfAppr" style="width:99.8%;height:100px;font-size: 14px;" readonly="readonly">${archives.selfAppr}</textarea>
				              </td>
				            </tr>
							<tr height="60">
				              <td nowrap class="tablelabel" width="10%">备注：</td>
				              <td colspan="4" class="tablecontent"  align="left">
				              	<textarea name="remark" style="width:99.8%;height:100px;font-size: 14px;" readonly="readonly">${archives.remark}</textarea>
				              </td>
				            </tr>
						</table>
					</td>
				</tr>
			</table>
			<div id="tt" class="easyui-tabs" style="width:1024px;">
				<div title="人员基本信息" data-options="iconCls:'icon-role'">
					<table class="tableborder" id="formTable">
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="20%">姓名：</td>
								<td align="left" class="tablecontent" width="25%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${archives.name}</p>
								</td>
								<td nowrap class="tablelabel" width="20%">曾用名：</td>
								<td align="left" class="tablecontent" width="25%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${archives.nickName}</p>
								</td>
								<td align="center" class="tablecontent" width="10%" rowspan="5" style="text-align: center;">
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
								</td>
							</tr>
							
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">性别：</td>
								<td align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${archives.sexName}</p>
								</td>
								<td nowrap class="tablelabel" width="10%">民族：</td>
								<td align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${archives.nationName}</p>
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">政治面貌：</td>
								<td align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${archives.politicalName}</p>
								</td>
								<td nowrap class="tablelabel" width="10%">籍贯：</td>
								<td align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${archives.nativePlace}</p>
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">出生地：</td>
								<td align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${archives.birthPlace}</p>
								</td>
								<td nowrap class="tablelabel" width="10%">出生日期：</td>
								<td align="left" class="tablecontent" width="20%">
									<input  name="birthDate"  type="text" value="${archives.birthDate}" class="easyui-datebox" data-options="editable:false" style="width:252px;" readonly="readonly"></input>
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">户口所在地：</td>
								<td align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${archives.resPlace}</p>
								</td>	
								<td nowrap class="tablelabel" width="10%">户口性质：</td>
								<td align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${archives.resTypeName}</p>
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">现在住址：</td>
								<td align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${archives.nowAdd}</p>
								</td>
								<td nowrap class="tablelabel" width="10%">照片：</td>
								<td colspan="2" align="left" class="tablecontent" width="20%">
									 <p style="height:15px;line-height:15px;padding-left:2px;">${archives.picName}</p>
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">身高(cm)：</td>
								<td align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${archives.height}</p>
								</td>
								<td nowrap class="tablelabel" width="10%">体重(kg)：</td>
								<td colspan="2" align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${archives.weight}</p>
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">健康状况：</td>
								<td align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${archives.healthName}</p>
								</td>
								<td nowrap class="tablelabel" width="10%">手机：</td>
								<td colspan="2" align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${archives.mobilePhone}</p>
								</td>
							</tr>
							
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">家长手机：</td>
								<td align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${archives.parPhone}</p>
								</td>
								<td nowrap class="tablelabel" width="10%">QQ：</td>
								<td colspan="2" align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${archives.qq}</p>
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">身份证号码：</td>
								<td align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${archives.identCardNo}</p>
								</td>
								<td nowrap class="tablelabel" width="10%">E-mail：</td>
								<td colspan="2" align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${archives.email}</p>
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">特长</td>
								<td align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${archives.special}</p>
								</td>	
								<td nowrap class="tablelabel" width="10%">计算机水平：</td>
								<td colspan="2" align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${archives.compuLevelName}</p>
								</td>
							</tr>
							<tr height="40" class="tablelabel">
								<td nowrap class="tablelabel" width="10%">所学外语语种：</td>
								<td align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${archives.foreignTypeName}</p>
								</td>
								<td nowrap class="tablelabel" width="10%">兴趣爱好：</td>
								<td colspan="2" align="left" class="tablecontent" width="20%">
									<p style="height:15px;line-height:15px;padding-left:2px;">${archives.interestAndHobby}</p>
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
								<p style="height:15px;line-height:15px;padding-left:2px;">${archives.peoNum}</p>
							</td>
							<td nowrap class="tablelabel" width="10%">详细通讯地址：</td>
							<td align="left" class="tablecontent" width="20%">
								<p style="height:15px;line-height:15px;padding-left:2px;">${archives.addr}</p>
							</td>
						</tr>
						<tr height="40" class="tablelabel">
							<td nowrap class="tablelabel" width="10%">联系电话：</td>
							<td align="left" class="tablecontent" width="20%">
								<p style="height:15px;line-height:15px;padding-left:2px;">${archives.tel}</p>
							</td>
							<td nowrap class="tablelabel" width="10%">家庭经济状况：</td>
							<td align="left" class="tablecontent" width="20%">
								<p style="height:15px;line-height:15px;padding-left:2px;">${archives.ecoStatusName}</p>
							</td>
						</tr>
						<tr height="40" class="tablelabel">
							<td nowrap class="tablelabel" width="10%">主要经济来源：</td>
							<td align="left" class="tablecontent" width="20%">
								<p style="height:15px;line-height:15px;padding-left:2px;">${archives.ecoFrom}</p>
							</td>
							<td nowrap class="tablelabel" width="10%">总收入（万元/年）：</td>
							<td align="left" class="tablecontent" width="20%">
								<p style="height:15px;line-height:15px;padding-left:2px;">${archives.ecoTotal}</p>
							</td>
						</tr>
						<tr height="40" class="tablelabel">
							<td nowrap class="tablelabel" width="10%">主要支出：</td>
							<td align="left" class="tablecontent" width="20%">
								<p style="height:15px;line-height:15px;padding-left:2px;">${archives.ecoPay}</p>
							</td>
							<td nowrap class="tablelabel" width="10%">家庭教育背景：</td>
							<td align="left" class="tablecontent" width="20%">
								<p style="height:15px;line-height:15px;padding-left:2px;">${archives.teachBackName}</p>
							</td>
						</tr>
						<tr height="40" class="tablelabel">
							<td nowrap class="tablelabel" width="10%">是否有低保证：</td>
							<td align="left" class="tablecontent" width="20%">
								<p style="height:15px;line-height:15px;padding-left:2px;">${archives.basicLiveName}</p>
							</td>
							<td nowrap class="tablelabel" width="10%">是否享受过补助：</td>
							<td align="left" class="tablecontent" width="20%">
								<p style="height:15px;line-height:15px;padding-left:2px;">${archives.helpingName}</p>
							</td>
						</tr>
						<tr height="60">
							<td nowrap class="tablelabel" width="10%">家庭情况介绍：</td>
							<td colspan="3" class="tablecontent"  align="left">
								<textarea name="introduce" style="width:99.8%;height:100px;font-size: 14px;" readonly="readonly">${archives.introduce}</textarea>
							</td>
						</tr>
						<tr height="60">
							<td class="tablelabel" width="10%">家庭成员如果有特殊情况（疾病、残疾、单亲等）必须进行说明，家庭如果困难，说明困难情况）</td>
							<td colspan="3" class="tablecontent"  align="left">
								<textarea name="specialStatus" style="width:99.8%;height:100px;font-size: 14px;" readonly="readonly">${archives.specialStatus}</textarea>
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
		</form>
<!--材料上传区域 -->
			<table border="0" align="center" cellpadding="0" cellspacing="0" style="width:1024px;">
				<tr height="25"  >
	              <td height="25" width="20%" nowrap="nowrap" class="tablelabel">材料上传区域：<input type="button" value="添加" onclick="addFile()"/></td>
	              <td id="fil" height="100" colspan="5" class="tablecontent"  align="left" style="padding-left:10px;">
<!-- 						<input type="hidden" id="FileCount" value="${fn:length(attList)}">	 -->
<!-- 		              	<c:forEach items="${attList}" var="att" varStatus="status"> -->
<!-- 	              			<input id="no${status.index+1}"  type="hidden" value="${att}" /> -->
<!-- 			              	<input type="text" style="width:100px" readonly id="fCert${status.index+1}" name="fCert${status.index+1}" value="${att}" > -->
<!-- 							<input type="button" value="上传" onclick="uploadCertFile(${status.index+1})"><br/> -->
<!-- 							材料说明：<input type="text" style="width:100px" readonly id="certInstr${status.index+1}" name="certInstr${status.index+1}" value="${att}" > -->
<!-- 						</c:forEach> -->
						
<!-- 						<input type="hidden" id="FileCount" value="${fn:length(certList)}">	 -->
<!-- 		              	<c:forEach items="${certList}" var="att" varStatus="status"> -->
<!-- 	              			<input id="no${status.index+1}"  type="hidden" value="${att}" /> -->
<!-- 			              	<input type="text" style="width:100px" readonly id="fCert${status.index+1}" name="fCert${status.index+1}" value="${att}" > -->
<!-- 							<input type="button" value="上传" onclick="uploadCertFile(${status.index+1})"><br/> -->
<!-- 							材料说明：<input type="text" style="width:100px" readonly id="certInstr${status.index+1}" name="certInstr${status.index+1}" value="${att}" > -->
<!-- 						</c:forEach> -->


<!-- 							<input type="hidden" id="FileCount" value="${fn:length(certList)}"> -->
<!-- 							<c:forEach items="${certList}" var="attMap" varStatus="status"> -->
<!-- 								<c:forEach items="${attMap}" var="att"> -->
<!-- 								<input id="no${status.index+1}"  type="hidden" value="${att.key}" /> -->
<!-- 			              		<input type="text" style="width:100px" readonly id="fCert${status.index+1}" name="fCert${status.index+1}" value="${att.key}" > -->
<!-- 								<input type="button" value="上传" onclick="uploadCertFile(${status.index+1})"> -->
<!-- 								材料说明：<input type="text" style="width:100px" readonly id="certInstr${status.index+1}" name="certInstr${status.index+1}" value="${att.value}" > -->
<!-- 								</c:forEach> -->
<!-- 							</c:forEach> -->
							
								<input type="hidden" id="FileCount" value="${fn:length(certMap)}">
								<c:forEach items="${certMap}" var="att" varStatus="status">
									<input id="no${status.index+1}"  type="hidden" value="${att.key}" />
				              		<input type="text" style="width:150px" id="fCert${status.index+1}" name="fCert${status.index+1}" value="${att.key}"  readonly="readonly">
									&nbsp;材料说明：<input type="text" style="width:350px" id="certInstr${status.index+1}" name="certInstr${status.index+1}" value="${att.value}" readonly="readonly">
									<br/>
									<img id="vCertImg${status.index+1}" src="${pageContext.request.contextPath}/upload/mobile/${att.key}" width="230px">
									<br/>
								</c:forEach>
	              </td>
	            </tr>
			</table>
<!--材料上传 -->
	</div>
</div>