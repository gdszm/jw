<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc;">
<div align="center" style="padding: 5px;overflow: hidden;">
	<form id="editForm"  method="post">
		<input type="hidden" name="id"  value="${classes.id}"/>
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
						<p style="padding-left:2px;">${classes.className}</p>
					</td>
				</tr>
				<tr height="30">
					<td nowrap class="tablelabel">培养层次：</td>
					<td align="left" class="tablecontent">
						<p style="padding-left:2px;">${classes.trainLevelName}</p>
					</td>
				</tr>
				<tr height="30">
					<td nowrap class="tablelabel">学制(年)：</td>
					<td align="left" class="tablecontent">
						<p style="padding-left:2px;">${classes.sysLength}</p>
					</td>
				</tr>
				<tr height="30">
					<td nowrap class="tablelabel">建班年月：</td>
					<td align="left" class="tablecontent">
						<p style="padding-left:2px;">${classes.buildDate}</p>
					</td>
				</tr>
				<tr height="30">
					<td nowrap class="tablelabel">班主任：</td>
					<td align="left" class="tablecontent">
						<c:if test="${!empty classes.adminTeacherId}">
							<p style="padding-left:2px;">${classes.adminTeacherId}|${classes.adminTeacherName}</p>
						</c:if>
					</td>
				</tr>
				<tr height="30">
					<td nowrap class="tablelabel">班长：</td>
					<td align="left" class="tablecontent">
						<c:if test="${!empty classes.adminStuId}">
							<p style="padding-left:2px;">${classes.adminStuId}|${classes.adminStuName}</p>
						</c:if>
					</td>
				</tr>
				<tr height="30">
					<td nowrap class="tablelabel">团支书：</td>
					<td align="left" class="tablecontent" >
						<c:if test="${!empty classes.secStuId}">
							<p style="padding-left:2px;">${classes.secStuId}|${classes.secStuName}</p>
						</c:if>
					</td>
				</tr>
				<tr height="30">
					<td nowrap class="tablelabel">男生人数：</td>
					<td align="left" class="tablecontent">
						<p style="padding-left:2px;">${classes.manNum}</p>
					</td>
				</tr>
				<tr height="30">
					<td nowrap class="tablelabel">女生人数：</td>
					<td align="left" class="tablecontent">
						<p style="padding-left:2px;">${classes.womanNum}</p>
					</td>
				</tr>
				<tr height="30">
					<td nowrap class="tablelabel">住校生人数：</td>
					<td align="left" class="tablecontent">
						<p style="padding-left:2px;">${classes.livingNum}</p>
					</td>
				</tr>
				<tr height="60">
	              <td nowrap class="tablelabel" width="10%">存在问题：</td>
	              <td colspan="4" class="tablecontent"  align="left">
	              	<textarea name="problem" style="width:99.8%;height:100px;font-size: 14px;" readonly="readonly">${classes.problem}</textarea>
	              </td>
				</tr>
	            <tr height="60">
	              <td nowrap class="tablelabel" width="10%">主要措施：</td>
	              <td colspan="4" class="tablecontent"  align="left">
	              	<textarea name="proSolve" style="width:99.8%;height:100px;font-size: 14px;" readonly="readonly">${classes.proSolve}</textarea>
	              </td>
	            </tr>
		</table>
	</td>
	</tr>
</table>
	</form>
</div>
</div>