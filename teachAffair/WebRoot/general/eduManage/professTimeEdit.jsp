<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<script type="text/javascript">
var arrayweeks;var arrayweeks;var arrayweeks;
if($("#weeksValId").val()) {
	arrayweeks = $("#weeksValId").val().split(",");
	$.each(arrayweeks,function(i,item){
	$("input[name='weeks'][value="+item+"]").attr("checked","checked");
});
}
if($("#weekValId").val()) {
	arrayweek = $("#weekValId").val().split(",");
	$.each(arrayweek,function(i,item){
	$("input[name='week'][value="+item+"]").attr("checked","checked");
});
}
if($("#sectionValId").val()) {
	arraysection = $("#sectionValId").val().split(",");
	$.each(arraysection,function(i,item){
	$("input[name='section'][value="+item+"]").attr("checked","checked");
});
}

//根据Value值设置checkbox为选中值：
// $("input:checkbox[value='1']").attr('checked','true');
</script>
<div data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc;">
<div align="center" style="padding: 5px;overflow: hidden;">
	<form id="editForm"  method="post">
		<input type="hidden" name="id"  value="${professTime.id}"/>
		<input id="weeksValId" type="hidden" name="weeksVal"  value="${professTime.weeks}"/>
		<input id="weekValId"  type="hidden" name="weekVal"  value="${professTime.week}"/>
		<input id="sectionValId" type="hidden" name="sectionVal"  value="${professTime.section}"/>
<table style="width:100%;">
    <tr>
      <td>
        <table class="tableborder" id="formTable">
        		<tr height="30">
	              <td height="45" colspan="2" nowrap="nowrap" class="tablemain">
	               	<div align="center"><strong>授课时间信息修改</strong></div>
	              </td>
	            </tr>
				<tr height="50">
					<td nowrap class="tablelabel">周次：</td>
					<td align="left" class="tablecontent">
					
<!-- 						<input type="text"  name="weeks" value="${professTime.weeks}" class="easyui-validatebox" data-options="required:true" style="width:300px;padding:2px" /> -->
						&nbsp;&nbsp;1<input id="weeks1" type="checkbox" name="weeks" value="1" /> 
						&nbsp;&nbsp;2<input id="weeks2" type="checkbox" name="weeks" value="2" /> 
						&nbsp;&nbsp;3<input id="weeks3" type="checkbox" name="weeks" value="3" /> 
						&nbsp;&nbsp;4<input id="weeks4" type="checkbox" name="weeks" value="4" /> 
						&nbsp;&nbsp;5<input id="weeks5" type="checkbox" name="weeks" value="5" /> 
						&nbsp;&nbsp;6<input id="weeks6" type="checkbox" name="weeks" value="6" /> 
						&nbsp;&nbsp;7<input id="weeks7" type="checkbox" name="weeks" value="7" /> 
						&nbsp;&nbsp;8<input id="weeks8" type="checkbox" name="weeks" value="8" /> 
						&nbsp;&nbsp;9<input id="weeks9" type="checkbox" name="weeks" value="9" /> 
						&nbsp;&nbsp;10<input id="weeks10" type="checkbox" name="weeks" value="10" />
						&nbsp;&nbsp;11<input id="weeks11" type="checkbox" name="weeks" value="11" />
						&nbsp;&nbsp;12<input id="weeks12" type="checkbox" name="weeks" value="12" />
						&nbsp;&nbsp;13<input id="weeks13" type="checkbox" name="weeks" value="13" />
						&nbsp;&nbsp;14<input id="weeks14" type="checkbox" name="weeks" value="14" />
						<br/><br/>
						&nbsp;&nbsp;15<input id="weeks15" type="checkbox" name="weeks" value="15" />
						&nbsp;&nbsp;16<input id="weeks16" type="checkbox" name="weeks" value="16" />
						&nbsp;&nbsp;17<input id="weeks17" type="checkbox" name="weeks" value="17" />
						&nbsp;&nbsp;18<input id="weeks18" type="checkbox" name="weeks" value="18" />
						&nbsp;&nbsp;19<input id="weeks19" type="checkbox" name="weeks" value="19" />
						&nbsp;&nbsp;20<input id="weeks20" type="checkbox" name="weeks" value="20" />
						&nbsp;&nbsp;21<input id="weeks21" type="checkbox" name="weeks" value="21" />
						&nbsp;&nbsp;22<input id="weeks22" type="checkbox" name="weeks" value="22" />
						&nbsp;&nbsp;23<input id="weeks23" type="checkbox" name="weeks" value="23" />
						&nbsp;&nbsp;24<input id="weeks24" type="checkbox" name="weeks" value="24" />
						&nbsp;&nbsp;25<input id="weeks25" type="checkbox" name="weeks" value="25" />
					</td>
				</tr>
				<tr height="50">
					<td nowrap class="tablelabel">星期：</td>
					<td align="left" class="tablecontent">
<!-- 						<input id="weekId" name="week" value="${professTime.week}" style="width:300px;padding:2px"/> -->
						&nbsp;&nbsp;星期一<input id="week1" type="checkbox" name="week" value="1" /> 
						&nbsp;&nbsp;星期二<input id="week2" type="checkbox" name="week" value="2" /> 
						&nbsp;&nbsp;星期三<input id="week3" type="checkbox" name="week" value="3" /> 
						&nbsp;&nbsp;星期四<input id="week4" type="checkbox" name="week" value="4" /> 
						&nbsp;&nbsp;星期五<input id="week5" type="checkbox" name="week" value="5" /> 
						&nbsp;&nbsp;星期六<input id="week6" type="checkbox" name="week" value="6" /> 
						&nbsp;&nbsp;星期日<input id="week7" type="checkbox" name="week" value="7" /> 
	
					</td>
				</tr>
				<tr height="50">
					<td nowrap class="tablelabel">节次：</td>
					<td align="left" class="tablecontent">
<!-- 						<input type="text"  name="section" value="${professTime.section}" style="width:300px;padding:2px" /> -->
						&nbsp;&nbsp;1<input id="section1" type="checkbox" name="section" value="1" /> 
						&nbsp;&nbsp;2<input id="section2" type="checkbox" name="section" value="2" /> 
						&nbsp;&nbsp;3<input id="section3" type="checkbox" name="section" value="3" /> 
						&nbsp;&nbsp;4<input id="section4" type="checkbox" name="section" value="4" /> 
						&nbsp;&nbsp;5<input id="section5" type="checkbox" name="section" value="5" /> 
						&nbsp;&nbsp;6<input id="section6" type="checkbox" name="section" value="6" /> 
						&nbsp;&nbsp;7<input id="section7" type="checkbox" name="section" value="7" /> 
						&nbsp;&nbsp;8<input id="section8" type="checkbox" name="section" value="8" /> 
						&nbsp;&nbsp;9<input id="section9" type="checkbox" name="section" value="9" /> 
						&nbsp;&nbsp;10<input id="section10" type="checkbox" name="section" value="10" />
						&nbsp;&nbsp;11<input id="section11" type="checkbox" name="section" value="11" />
					</td>
				</tr>
		</table>
	</td>
	</tr>
</table>
	</form>
</div>
</div>