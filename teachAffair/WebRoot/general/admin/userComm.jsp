<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<script type="text/javascript">
//文件上传
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
function uploadPic(num){
	pFile = dj.dialog({
		title : '文件上传',
		href : '${pageContext.request.contextPath}/fileUpload!uploadFile.do',
		width : 320,
		height : 160,
		buttons : [ {
			text : '确定',
			iconCls:'icon-ok',
			handler : function() {
				doUploadPic(pFile,'${pageContext.request.contextPath}','picName'+num,'img'+num);
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
</script>
<div align="center" style="padding: 5px;overflow: hidden;">
	<form id="commForm" method="post">
		<input type="hidden" id="oldtelephone" name="oldtelephone" value="${sessionComm.telephone}"  >
		<input type="hidden" name="code" value="${sessionComm.code}" />
		<table id="addtable">				
			<tr height="30">
				<td nowrap>提案人：</td>
				<td align="left" colspan="2">
					<input type="text" name="name" value="${sessionComm.name}" class="easyui-validatebox" data-options="required:true,missingMessage:'请填姓名'" style="width:270px;padding:2px" />
				</td>
				<c:if test="${sessionComm.groupCode=='1'}">
					<td align="center" width="10%" rowspan="5" style="text-align: center;">
						<input type="hidden" id="picName1" name="picName" value="${sessionComm.picName}" >
								<c:choose>
									<c:when test="${!empty sessionComm.picName}">
										<img id="img1" src="${pageContext.request.contextPath}/upload/mobile/${sessionComm.picName}" style="height:113px;text-align:center;margin-bottom:3px;border: 1px dotted #95B8E7;padding:2px;">
									</c:when>
									<c:otherwise>
	<%--									<c:if test="${sessionComm.sex==1}">--%>
	<%--										<img id="img1" src="${pageContext.request.contextPath}/style/images/man.jpg" style="height:113px;text-align:center;margin-bottom:3px;border: 1px dotted #95B8E7;padding:2px;">--%>
	<%--									</c:if>--%>
	<%--									<c:if test="${sessionComm.sex==2}">--%>
	<%--										<img id="img1" src="${pageContext.request.contextPath}/style/images/woman.jpg" style="height:113px;text-align:center;margin-bottom:3px;border: 1px dotted #95B8E7;padding:2px;">--%>
	<%--									</c:if>--%>
										
										<c:choose>
										<c:when test="${sessionComm.sex==1}">
											<img id="img1" src="${pageContext.request.contextPath}/style/images/man.jpg" style="height:113px;text-align:center;margin-bottom:3px;border: 1px dotted #95B8E7;padding:2px;">
										</c:when>
										<c:when test="${sessionComm.sex==2}">
											<img id="img1" src="${pageContext.request.contextPath}/style/images/woman.jpg" style="height:113px;text-align:center;margin-bottom:3px;border: 1px dotted #95B8E7;padding:2px;">
										</c:when>
										<c:otherwise>
											<img id="img1" src="${pageContext.request.contextPath}/style/images/man.jpg" style="height:113px;text-align:center;margin-bottom:3px;border: 1px dotted #95B8E7;padding:2px;">
										</c:otherwise>
									</c:choose>
								
									</c:otherwise>
								</c:choose>
	<%--					<img id="img1" src="${pageContext.request.contextPath}/style/images/man.jpg" style="height:100px;width:100px;text-align:center;margin-bottom:3px;border: 1px dotted #95B8E7;padding:2px;">--%>
						<a class="easyui-linkbutton" data-options="iconCls:'icon-submit'" href="javascript:void(0)" onclick="javascript:uploadPic(1);">上传照片</a>
					</td>
				</c:if>
				<c:if test="${sessionComm.groupCode!='1'}">
					<td rowspan="5"></td>
				</c:if>
			</tr>
			<c:if test="${sessionComm.groupCode=='1'}">
				<tr height="30">
					<td nowrap>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</td>
					<td align="left" colspan="2"><input name="sex" value="${sessionComm.sex}" style="width:275px;padding:2px" /></td>
				</tr>
				<tr height="30">
					<td nowrap>专&nbsp;&nbsp;委&nbsp;&nbsp;会：</td>
					<td align="left" colspan="2"><input name="committee" value="${sessionComm.committee}" style="width:275px;padding:2px" /></td>
				</tr>
				<tr height="30">
					<td nowrap>党&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;派：</td>
					<td align="left" colspan="2"><input name="partyCode" value="${sessionComm.partyCode}" style="width:275px;padding:2px" /></td>
				</tr>
				<tr height="30">
					<td nowrap>界&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</td>
					<td align="left" colspan="2"><input name="circleCode" value="${sessionComm.circleCode}" style="width:275px;padding:2px" /></td>
				</tr>
				<tr height="30">
					<td nowrap>学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;历：</td>
					<td align="left"><input  name="eduCode" value="${sessionComm.eduCode}" style="width:161px;padding:2px" /></td>
					<td nowrap>学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位：</td>
					<td align="left"><input name="degreeCode" value="${sessionComm.degreeCode}" style="width:154px;padding:2px" /></td>
				</tr>	
				<tr height="30">
					<td nowrap>出生年月：</td>
					<td align="left"> <input type="text" value="<fmt:formatDate value="${sessionComm.birthDate}" pattern="yyyy-MM-dd" /> " class="easyui-datebox" name="birthDate" style="width:161px;padding:2px" /></td>
					<td nowrap>职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;业：</td>
					<td align="left"><input type="text" name="vocation" value="${sessionComm.vocation}" style="width:148px;padding:2px" /></td>
				</tr>
				<tr height="30">
					<td nowrap>职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称：</td>
					<td align="left"><input type="text" name="title" value="${sessionComm.title}" style="width:155px;padding:2px" /></td>
					<td nowrap>职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;务：</td>
					<td align="left"><input type="text" name="job" value="${sessionComm.job}" style="width:148px;padding:2px" /></td>
				</tr>
			</c:if>
			<c:if test="${sessionComm.groupCode!='1'}">
				<tr height="30">
					<td nowrap>联系人：</td>
					<td align="left" colspan="3"> <input type="text" name="linkMan" value="${sessionComm.linkMan}" style="width:270px;padding:2px" /></td>
				</tr>
				<tr height="30">
					<td nowrap>电子邮箱：</td>
					<td align="left" colspan="3"> <input type="text" name="email" value="${sessionComm.email}" style="width:270px;padding:2px" /></td>
				</tr>
				<tr height="30">
					<td nowrap>民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族：</td>
					<td align="left" colspan="3"><input name="nation" value="${sessionComm.nation}" style="width:275px;padding:2px" /></td>
				</tr>
				<tr height="30">
					<td nowrap>手机号码：</td>
					<td align="left" colspan="3"><input type="text" id="telephone" name="telephone" value="${sessionComm.telephone}" class="easyui-validatebox" required="true" validType="length[7,12]" style="width:270px;padding:2px"></td>
				</tr>
				<tr height="30">
					<td nowrap>固定电话：</td>
					<td align="left" colspan="3"><input name="comparyPhone" value="${sessionComm.comparyPhone}" style="width:270px;padding:2px" /></td>
				</tr>
				<tr height="30">
					<td nowrap>邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编：</td>
					<td align="left" colspan="3"><input type="text" value="${sessionComm.comparyPost}" name="comparyPost" style="width:270px;padding:2px" /></td>
				</tr>
				<tr height="30">
					<td nowrap>通讯地址：</td>
					<td align="left" colspan="3"><input type="text" value="${sessionComm.comparyAddress}" name="comparyAddress" style="width:270px;padding:2px" /></td>
				</tr>
			</c:if>
			<c:if test="${sessionComm.groupCode=='1'}">
				<tr height="30">
					<td nowrap>电子邮箱：</td>
					<td align="left"> <input type="text" name="email" value="${sessionComm.email}" style="width:155px;padding:2px" /></td>
					<td nowrap>民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族：</td>
					<td align="left"><input name="nation" value="${sessionComm.nation}" style="width:154px;padding:2px" /></td>
				</tr>
			
				<tr height="30">
					<td nowrap>手机号码：</td>
					<td align="left"><input type="text" id="telephone" name="telephone" value="${sessionComm.telephone}" class="easyui-validatebox" required="true" validType="length[7,12]" style="width:155px;padding:2px"></td>
					<td nowrap>固定电话：</td>
					<td align="left"><input name="comparyPhone" value="${sessionComm.comparyPhone}" style="width:148px;padding:2px" /></td>
				</tr>
				<tr height="30">
					<td nowrap>单位名称：</td>
					<td align="left"><input type="text" value="${sessionComm.companyName}" name="companyName" style="width:155px;padding:2px" /></td>
					<td nowrap>邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编：</td>
					<td align="left"><input type="text" value="${sessionComm.comparyPost}" name="comparyPost" style="width:148px;padding:2px" /></td>
					
				</tr>
				<tr height="30">
					<td nowrap>通讯地址：</td>
					<td align="left" colspan="3"><input type="text" value="${sessionComm.comparyAddress}" name="comparyAddress" style="width:426px;padding:2px" /></td>
				</tr>
				<tr height="30">
					<td nowrap>家庭地址：</td>
					<td align="left" colspan="3"><input type="text" value="${sessionComm.familyAddress}" name="familyAddress" style="width:426px;padding:2px" /></td>
				</tr>
			</c:if>
			<c:if test="${sessionComm.groupCode=='1'}">
				<tr height="30">
					<td nowrap colspan="4"><font color="#F00">“手机号等同用户名，修改电话用户名也随即修改”</font></td>
				</tr>
			</c:if>
		</table>
	</form>
</div>
