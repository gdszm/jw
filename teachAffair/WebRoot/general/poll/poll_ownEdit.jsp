<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<script type="text/javascript">
var editor = new baidu.editor.ui.Editor({ 
        textarea:'content' 
    }); 
    editor.render("editor");
    
</script>
<div class="easyui-layout" data-options="fit:true" class="cs-home-remark">
		<div data-options="region:'center',border:false" style="padding:10px;border:1px solid #ccc;" class="cs-home-remark">
			<center>
			<form id="editForm" class="form-horizontal"  method="post">
				<input type="hidden"  id="pollId" name="pollId" value="${obj.pollId}" />
				<input type="hidden"  id="secondaryId" name="tsecondary.secondaryId" value="${obj.tsecondary.secondaryId}" />
				<table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
			      <tr>
			        <td><table  class="tableborder" id="formTable" sizset="false" >
			          <tbody sizset="false">
			           <tr height="30">
			              <td height="45" colspan="4" align="center" nowrap="nowrap"  class="tablemain" >
					   			<div align="center"><strong>政协巴彦淖尔市${sessionSeco.year}年度社情民意提交</strong></div>
			              </td>
			            </tr>
			            <tr height="25" sizset="false" >
			              <td height="25" width="100px" nowrap="nowrap" class="tablelabel" >标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题：</td>
			              <td height="25" colspan="3" class="tablecontent" sizset="false" align="left"><input type="text" id="title" name="title" value="${obj.title}" class="easyui-validatebox" data-options="required:true" style="width:99%;padding:2px" /></td>
			            </tr>
						<tr height="25" sizset="false" >
			              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">提交者：</td>
			              <td height="25" width="99%" class="tablecontent" sizset="false"  align="left"><input type="hidden" name="createMan" value="${sessionComm.code}" /><input name="createManName" type="text" class="bian" readonly="readonly" id="fistProposaler" style="width:99%" value="${sessionComm.name}" /></td>
			              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">所属专委会：</td>
			              <td height="25" width="99%" class="tablecontent" sizset="false"  align="left"><input name="committee" id="committee" type="hidden" value="${sessionComm.committee}" /><input name="committeeName" class="bian" id="committeeName" type="text" readonly="readonly" value="${sessionComm.committeeName}"  style="width:99%" />
			              </td>
			            </tr>
			            <tr height="25" sizset="false" >
			              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">联系电话：</td>
			              <td height="25" width="99%" class="tablecontent" sizset="false"  align="left"><input name="telephone" class="bian" id="telephone" type="text" readonly="readonly" value="${sessionComm.telephone}"  style="width:99%" />
			              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">电子邮箱：</td>
			              <td height="25" class="tablecontent" sizset="false"  align="left"><input name="email" class="bian" id="email" type="text" readonly="readonly" value="${sessionComm.email}"  style="width:99%" />
			              </td>
			            </tr>
			            <tr height="25" sizset="false" >
			              <td height="25" width="100px" nowrap="nowrap" class="tablelabel">联系地址：</td>
			              <td height="25" class="tablecontent" colspan="3" sizset="false" align="left"><input name="comparyAddress"  class="bian" id="comparyAddress" type="text" readonly="readonly" value="${sessionComm.comparyAddress}" style="width:98%;padding:2px" /></td>
			              </td>
			            </tr>
			            <tr height="32" sizset="false" >
			              <td height="25" align="center"　 colspan="4" nowrap="nowrap" class="tablespecial">
			              	<strong>重要提示：如提交内容较多，请先在记事本或word中提前录好保存，然后再复制到内容框中，以免在线超时，引起信息丢失！</strong>
			             </td>
			            </tr>
			            <tr >
			              <td height="25" colspan="4" align="center"  class="tablecontent"> 
							<script id="editor" type="text/plain" style="width:99%;height:650px;">${obj.content}</script>
						  </td>
			            </tr>
			          </tbody>
			        </table></td>
			        </tr>
			    </table>
		</form>
	</center>
	</div>
