<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<%String aid=request.getParameter("aid");%>

<div data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc;">
<div align="center" style="padding: 5px;overflow: hidden;">
	<div align="center" style="padding: 5px;overflow: hidden;">
	<form id="editForm"  method="post">
<table border="0" align="center" cellpadding="0" cellspacing="0" style="width:1024px;">
    <tr>
      <td>	
        <table class="tableborder" id="formTable">
        		<tr height="30">
	              <td height="45" colspan="4" align="middle" nowrap="nowrap" class="tablemain">
	               	<div align="center"><strong>政协巴彦淖尔市${obj.year}年度活动新增</strong></div>
	              </td>
	            </tr>
				<tr height="30">
					<td nowrap class="tablelabel" width="30%">活动主题：</td>
					<td align="left" class="tablecontent" >${obj.atheme}</td>
				</tr>
				<tr height="30">
					<td nowrap class="tablelabel">活动种类：</td>
					<td align="left" class="tablecontent" >${obj.aspeciesName}</td>
				</tr>
				<tr height="30" id="commtr_1">
					<td nowrap class="tablelabel">活动时间：</td>
					<td align="left" class="tablecontent">${obj.timebeg}--${obj.timeend}</td>
				</tr>
				<tr height="30" id="commtr_1">
					<td nowrap class="tablelabel">活动地点：</td>
					<td align="left" class="tablecontent">${obj.place}</td>
				</tr>
				<tr height="30" id="commtr_3">
					<td nowrap class="tablelabel">发布时间：</td>
					<td align="left" class="tablecontent">${obj.releasetime}</td>
				</tr>
				<tr height="30" id="commtr_3">
					<td nowrap class="tablelabel">发布单位：</td>
					<td align="left" class="tablecontent">${obj.releaseage}</td>
				</tr>
				<tr height="30" id="commtr_4">
					<td nowrap class="tablelabel">承办机构：</td>
					<td align="left" class="tablecontent">${obj.agency}</td>
				</tr>
				<tr height="32" sizset="false" >
	              <td height="25" align="center"　 colspan="4" nowrap="nowrap" class="tablespecial">
	              	<font color="#F00"><strong>重要提示：如提交内容较多，请先在记事本或word中提前录好保存，然后再复制到内容框中，以免在线超时，引起信息丢失！</strong></font>
	             </td>
	            </tr>
	 			<tr>
					<td colspan="4" align="center" class="tablecontent">
						<div class="easyui-tabs" style="width:1024px;height:500px;"> 
							<div title="邀请人信息" style="padding:3px;height:480px;">  
								<table id="commcodegrid"></table> 
							</div>
							<div title="活动内容" style="padding: 3px">
								<div style="padding:12px;line-height:25pt;" class="tanr-class" id="tanr">
									${obj.acontent}
								</div>
							</div>
							
						</div> 
					</td>
				</tr>
		</table>
	</td>
	</tr>
</table>
	</form>
</div>
</div>