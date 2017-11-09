<%--新增考试--%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<script type="text/javascript">
var editor;
var pMeeting;
$(document).ready(function() {
	
});
//提交考试
function submitSpeech(){
			var f =$("#addForm"); 
			f.form('submit', {
				url : '${pageContext.request.contextPath}/exam!upDateOrAdd.do',
				success : function(d) {
					var json = $.parseJSON(d);
					if (json.success) {
						parent.dj.messagerConfirm('操作提示','提交成功！您是否继续提交新考试?',function(ok){  
							if (ok){
								//window.location.href="${pageContext.request.contextPath}/speech!speechAdd.do";
								document.getElementById("addForm").reset();
								editor.setContent('');
							}else{
								window.parent.closetab();
							}
						});

					}
				}
		
			});
}

//会议选择部分 开始//
function meetingSelect(){
	pMeeting=dj.dialog({
		title : '选择所属会议',
		href : '${pageContext.request.contextPath}/speech!meetingSelect.do',
		width : 600,
		height : 360,
		left:350,
		top:100,
		buttons : [ {
			text : '确定',
			iconCls:'icon-ok',
			handler : function() {
				saveMeetingAdd();
			}
		},{ 
			text: ' 取 消', 
			iconCls:'icon-cancel',
			handler: function() { 
				pMeeting.dialog('close'); 
			}
		}],
		onLoad : function() {
			initMeeing();
		}
	});
}

//会议选择添加
function saveMeetingAdd(){
	var rows=$('#meetinggrid').datagrid('getSelected');
	  if(rows){
		  $('#meetId').val(rows.meetId);
		  $('#meetNameId').val(rows.meetName);
		  $('#meetTypeNameId').val(rows.meetTypeName);
	  }else{
		  $('#meetId').val('');
		  $('#meetNameId').val('');
		  $('#meetTypeNameId').val(''); 
	  }
	  pMeeting.dialog('close');
}
//会议选择部分 结束//
	
</script>
<div data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc;">
<center>
<form id="addForm" class="form-horizontal"  method="post">
<input type="hidden"  id="idId" name="speakId" />
<input type="hidden"  id="content" name="content" />
<%--附件上传用--%>
<input type="hidden"  id="attNames" name="atts" value="" />
<table border="0" align="center" style="width:1024px;">
      <tr>
        <td><table  class="tableborder" id="formTable"  >
          <tbody  >
           <tr height="30">
              <td height="45" colspan="4" align="middle" nowrap="nowrap" class="tablemain">
               	<div align="center"><strong>考试发布</strong></div>
              </td>
            </tr>
              <td height="35" width="80px" nowrap="nowrap" class="tablelabel">考试名称：</td>
              <td height="35" colspan="3" class="tablecontent"  align="left">
              	<input type="text" id="title" name="examName" class="easyui-validatebox" data-options="required:true" value="${speech.title}"  style="width:99.6%;" />
              </td>
            </tr>
            <tr height="35"  >
              <td height="35" width="80px" nowrap="nowrap" class="tablelabel">考试年度：</td>
              <td height="35"  class="tablecontent"  align="left">
             	 <input id="yearId" name="year"  value="${year}" type="text" style="width:99%;" />
              </td>
              <td height="35" width="80px" nowrap="nowrap" class="tablelabel">学期：</td>
              <td height="35"  class="tablecontent"  align="left">
              	 <input id="termId" name="term"  value="${term}" type="text" style="width:99%;" />
              </td>
            </tr>
             <tr height="35"  >
              <td height="35" nowrap="nowrap" class="tablelabel">考试开始日期：</td>
              <td height="35"  class="tablecontent"  align="left">
             	 <input name="examSDate" class="easyui-datebox" data-options="required:true" />
              </td>
              <td height="35" nowrap="nowrap" class="tablelabel">考试结束日期：</td>
              <td height="35"  class="tablecontent"  align="left">
             	 <input name="examEDate" class="easyui-datebox" data-options="required:true" />
              </td>
            </tr>
            <tr height="35"  >
               <td height="35" nowrap="nowrap" class="tablelabel">考试地点：</td>
              <td height="35"  class="tablecontent"  align="left">
              	 <input id="examPlaceId" name="examPlace"  value="${examPlace}" type="text" style="width:99%;" />
              </td>
              <td height="35"  nowrap="nowrap" class="tablelabel">监考教师ID：</td>
              <td height="35"  class="tablecontent"  align="left">
              	 <input id="inviTeacherIdId" name="inviTeacherId"  value="${inviTeacherId}" type="text" style="width:99%;" />
              </td>
            </tr>
            <tr height="35"  >
              <td height="35" nowrap="nowrap" class="tablelabel">是否迟到：</td>
              <td height="35"  class="tablecontent"  align="left">
             	<input type="text" name=isLate style="width:395px;padding:2px" />
              </td>
              <td height="35"  nowrap="nowrap" class="tablelabel">是否缺考：</td>
              <td height="35"  class="tablecontent"  align="left">
              	 <input type="text" name="isAbsent" style="width:423px;padding:2px" />
              </td>
            </tr>
            <tr height="35"  >
              <td height="35" nowrap="nowrap" class	="tablelabel">是否作弊：</td>
              <td height="35"  class="tablecontent"  align="left">
             	 <input type="text" name="isCribbing" style="width:395px;padding:2px" />
              </td>
              <td height="35" nowrap="nowrap" class="tablelabel">成绩类型：</td>
              <td height="35"  class="tablecontent"  align="left">
              	<input type="text" name="resultType" style="width:423px;padding:2px" />
              </td>
            </tr>
             <tr height="35">
              <td height="35" nowrap="nowrap" class="tablelabel">考试课程：</td>
              <td height="35" class="tablecontent" align="left" style="width:320px;">
              	<input id="courseId"  name="courseId"  type="text"/>
              	<input id="courseNameId"  name="courseName"  class="easyui-validatebox" data-options="required:true" style="width:305px;"/>
              	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="meetingSelect()">选择</a>
             	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="$('#courseId').val('');$('#courseNameId').val('');">取消</a>
              </td>
              <td height="35" nowrap="nowrap" class="tablelabel">课程名称：</td>
              <td height="35" class="tablecontent" colspan="3"  align="left">
              	<input id="meetTypeNameId"  name="meetTypeName"  class="easyui-validatebox" style="width:99%;" readonly="readonly"/>
              </td>
            </tr>
            <tr height="25"  >
	             <td height="25" nowrap="nowrap" class="tablelabel">备注：</td>
	             <td height="25" colspan="3" class="tablecontent"  align="left">
	             	<textarea name="comment" style="width:99.5%;height: 80px; font-size: 14px; " ></textarea>
	             </td>
            </tr>
          </tbody>
        </table></td>
        </tr>
    </table>
   </form>
 </center>
</div>
<%--弹出选择考试人窗口--%>
<%--<div id="win_wy_query" class="easyui-window" data-options="title:'添加提交者',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:650px;height:360px;padding:5px;">--%>
<%--	<div class="easyui-layout" data-options="fit:true">--%>
<%--		<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">--%>
<%--			<center>--%>
<%--			--%>
<%--			<table id="tar_queryTable">			--%>
<%--				<tr height="25">--%>
<%--					<td colspan="4">--%>
<%--					<form id="tjr_queryForm">--%>
<%--					提交者：--%>
<%--						<input id="wyNameId" type="text" name="name" style="width:450px;padding:2px"/>--%>
<%--<!-- 					提交者分组：	<input id="groupCode" name="groupCode" style="width:120px;padding:2px"/> -->--%>
<%--						<a href="javascript:void(0);" class="easyui-linkbutton" onclick="tjr_search();">搜索</a>--%>
<%--						<a href="javascript:void(0);" class="easyui-linkbutton" onclick="tjr_cleanSearch();">取消</a>--%>
<%--					</form>--%>
<%--					</td>--%>
<%--				--%>
<%--				</tr>--%>
<%--				<tr height="200" >--%>
<%--					<td colspan="4" nowrap> <table id="tjzgrid"></table></td>--%>
<%--				</tr>--%>
<%--			</table>--%>
<%--			--%>
<%--			</center>--%>
<%--		</div>--%>
<%--		<div data-options="region:'south',border:false" style="text-align:center;padding:5px 0;height:40px">--%>
<%--			<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="tjr_SelectConfirm();">确定</a>&nbsp;&nbsp;&nbsp;&nbsp;--%>
<%--			<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#win_wy_query').window('close');"> 关 闭 </a>--%>
<%--		</div>--%>
<%--	</div>--%>
<%--</div>--%>

