<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <script type="text/javascript">
	//记分选择
	function _select() {	
		var sp = dj.dialog({
			title : '批示与采用计分登记',
			href : '${pageContext.request.contextPath}/pollScore!pollInsAndScorSelect.do',
			width : 600,
			height : 450,
			buttons : [ {
				text: '确定 ', 
				iconCls:'icon-ok',
				handler: function() { 
					var rowsSelected = $('#listgrid').datagrid('getSelected');
					if (rowsSelected!=null) {
						$('#rulesId').val(rowsSelected.id);
						$('#rulesNameId').val(rowsSelected.name);
						$('#scoreId').val(rowsSelected.score);

						sp.dialog('close'); 
					} else {
						parent.dj.messagerAlert('提示', '请选择一条记分规则！', 'error');
					}
				} 
			}]
			
		});
	}
	
 </script>
 <div data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc;">
	<center>
		<form id="addScoreForm" class="form-horizontal"  method="post">
		<input type="hidden" id="pollId" name="poll.pollId"/>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		      <tr>
		        <td><table class="tableborder" id="formTable" sizset="false" >
		           <tbody sizset="false" >
		            <tr height="25">
		              <td height="25" colspan="4" align="middle" nowrap="nowrap" class="tablemain">
		                <div align="center"><strong>社情民意信息</strong></div>
		              </td>
		            </tr>
		          <tr height="25" sizset="false">
		              <td height="25" width="80" nowrap="nowrap" class="tablelabel">社情民意编号：</td>
		              <td height="25" width="160" class="tablecontent" sizset="false"><label id="pollCode" />  </td>
		              <td height="25" width="80" nowrap="nowrap" class="tablelabel">年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度：</td>
		              <td height="25" width="160" class="tablecontent" sizset="false" ><label id="secondaryYear" /></td>
		            </tr>
					<tr height="25" sizset="false" >
		              <td height="25"　width="80" nowrap="nowrap" class="tablelabel">标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题：</td>
		              <td height="25" colspan="3" class="tablecontent"  sizset="false" ><label id="tl" /></td>
		            </tr>
		            <tr height="25" sizset="false" >
		              <td height="25" width="80" nowrap="nowrap" class="tablelabel">社情民意类型：</td>
		              <td height="25" width="160" class="tablecontent" sizset="false" ><label id="pType" /></td>
		              <td height="25" width="80" nowrap="nowrap" class="tablelabel">提交者：</td>
		              <td height="25" width="160" class="tablecontent" sizset="false" ><label id="cMan" /></td>
		            </tr>
		             <tr height="25">
		              <td height="25" colspan="4" align="middle" nowrap="nowrap" class="tablemain">
		                <div align="center"><strong>计分信息</strong></div>
		              </td>
		            </tr>
		           <tr height="25" sizset="false" >
		              <td height="25" width="80" nowrap="nowrap" class="tablelabel">计分名称：</td>
		              <td height="25" width="160" class="tablecontent" sizset="false" ><input id="rulesId" name="rules.id" type="hidden" value="" /><input id="rulesNameId" name="rules.name" style="width:80%;" class="easyui-validatebox" readonly="readonly"/><a href="javascript:void(0);" class="easyui-linkbutton" onclick="_select();">选择...</a></td>
		              <td height="25" width="80" nowrap="nowrap" class="tablelabel">分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;值：</td>
		              <td height="25" width="160" class="tablecontent" sizset="false" ><input id="scoreId" name="rules.score" style="width:98%;" class="easyui-validatebox" readonly="readonly"/></td>
		            </tr>
		            <tr height="25" sizset="false" >
		              <td height="25"　width="80" nowrap="nowrap" class="tablelabel">批&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;示：</td>
		              <td height="25" colspan="3" class="tablecontent"  sizset="false" ><textarea id="commentId" name="comment" class="textarea easyui-validatebox" style="height:100px;width: 100%"></textarea></td>
		            </tr>
		            <tr height="25" sizset="false" >
		              <td height="25"　width="80" nowrap="nowrap" class="tablelabel">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
		              <td height="25" colspan="3" class="tablecontent"  sizset="false" ><textarea id="memoTempId" name="memo" class="textarea easyui-validatebox" style="height:60px;width: 100%"></textarea></td>
		            </tr>
		          </tbody>
		        </table></td>
		        </tr>
		    </table>
		   </form>
		 </center>
	</div>
	