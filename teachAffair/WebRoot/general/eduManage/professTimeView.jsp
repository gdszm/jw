<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc;">
<div align="center" style="padding: 5px;overflow: hidden;">
	<form id="editForm"  method="post">
		<input type="hidden" name="id"  value="${professTime.id}"/>
<table style="width:1024px;">
    <tr>
      <td>		
        <table class="tableborder" id="formTable">
        		<tr height="30">
	              <td height="45" colspan="4" nowrap="nowrap" class="tablemain">
	               	<div align="center"><strong>班级信息详情</strong></div>
	              </td>
	            </tr>
				<tr height="30">
					<td nowrap class="tablelabel">班级名称：</td>
					<td align="left" class="tablecontent">
						<p style="padding-left:2px;">${professTime.className}</p>
					</td>
				</tr>
				<tr height="30">
					<td nowrap class="tablelabel">培养层次：</td>
					<td align="left" class="tablecontent">
						<p style="padding-left:2px;">${professTime.trainLevelName}</p>
					</td>
				</tr>
				<tr height="30">
					<td nowrap class="tablelabel">学制(年)：</td>
					<td align="left" class="tablecontent">
						<p style="padding-left:2px;">${professTime.sysLength}</p>
					</td>
				</tr>
				<tr height="30">
					<td nowrap class="tablelabel">建班年月：</td>
					<td align="left" class="tablecontent">
						<p style="padding-left:2px;">${professTime.buildDate}</p>
					</td>
				</tr>
				<tr height="30">
					<td nowrap class="tablelabel">班主任：</td>
					<td align="left" class="tablecontent">
						<c:if test="${!empty professTime.adminTeacherId}">
							<p style="padding-left:2px;">${professTime.adminTeacherId}|${professTime.adminTeacherName}</p>
						</c:if>
					</td>
				</tr>
				<tr height="30">
					<td nowrap class="tablelabel">班长：</td>
					<td align="left" class="tablecontent">
						<c:if test="${!empty professTime.adminStuId}">
							<p style="padding-left:2px;">${professTime.adminStuId}|${professTime.adminStuName}</p>
						</c:if>
					</td>
				</tr>
				<tr height="30">
					<td nowrap class="tablelabel">团支书：</td>
					<td align="left" class="tablecontent" >
						<c:if test="${!empty professTime.secStuId}">
							<p style="padding-left:2px;">${professTime.secStuId}|${professTime.secStuName}</p>
						</c:if>
					</td>
				</tr>
				<tr height="30">
					<td nowrap class="tablelabel">男生人数：</td>
					<td align="left" class="tablecontent">
						<p style="padding-left:2px;">${professTime.manNum}</p>
					</td>
				</tr>
				<tr height="30">
					<td nowrap class="tablelabel">女生人数：</td>
					<td align="left" class="tablecontent">
						<p style="padding-left:2px;">${professTime.womanNum}</p>
					</td>
				</tr>
				<tr height="30">
					<td nowrap class="tablelabel">住校生人数：</td>
					<td align="left" class="tablecontent">
						<p style="padding-left:2px;">${professTime.livingNum}</p>
					</td>
				</tr>
				<tr height="60">
	              <td nowrap class="tablelabel" width="10%">存在问题：</td>
	              <td colspan="4" class="tablecontent"  align="left">
	              	<textarea name="problem" style="width:99.8%;height:100px;font-size: 14px;" readonly="readonly">${professTime.problem}</textarea>
	              </td>
				</tr>
	            <tr height="60">
	              <td nowrap class="tablelabel" width="10%">主要措施：</td>
	              <td colspan="4" class="tablecontent"  align="left">
	              	<textarea name="proSolve" style="width:99.8%;height:100px;font-size: 14px;" readonly="readonly">${professTime.proSolve}</textarea>
	              </td>
	            </tr>
		</table>
	</td>
	</tr>
</table>
	</form>
</div>
</div>