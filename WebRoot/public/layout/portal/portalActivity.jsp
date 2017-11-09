<%--活动管理首页--%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.tlzn.util.base.Constants"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style/inlineBlock.css" type="text/css"></link>
<script type="text/javascript" charset="utf-8"> 
	//设置tabs控件的高度
	var height=($(".tabs-panels").css("height"));
	var h=parseInt(height.replace("px", ""));
	var heightOffset=(h-7)/2;
	$("#tt").attr("style","height:"+heightOffset+"px;margin:0.16% 0.16%;");
$(document).ready(function() {

		//设置datagrid调用地址（根据用户组）
		var url="";
		var userGroup="${sessionInfo.userGroup}";
		var userId="${sessionInfo.userCode}";
		switch(userGroup)
		{
		//委员
		case "<%=Constants.DIC_TYPE_YHZB_WY%>":
		  url='${pageContext.request.contextPath}/activity!doNotNeedAuth_zxdatagrid.do?userGroup='+userGroup+'&code='+userId;
		  break;
		default:
		   url='${pageContext.request.contextPath}/activity!doNotNeedAuth_zxdatagrid.do';
		}
		
		//设置更多
		var a=""; if(userGroup=="<%=Constants.DIC_TYPE_YHZB_WY%>"){a="'我的活动情况'";} else {a="'活动情况'";}; //委员时，标题为我的活动情况。其他标题为活动情况
		var b=""; 
		var DateAdd0=(addDaysToDate(new Date,0));
		if(userGroup=="<%=Constants.DIC_TYPE_YHZB_WY%>")
		{
			b="'/activitypeo!activityOwn.do?status=<%=Constants.ACT_TYPE_STATUS_YFB%>&beginTime="+DateAdd0+"',''";
		} 
		else {
			var DateAdd0=(addDaysToDate(new Date,0));
			b="'/activity!activity.do?status=<%=Constants.ACT_TYPE_STATUS_YFB%>&beginTime="+DateAdd0+"',''";
		};//委员时，更多链接到我的活动情况。其他链接到活动情况
		var c="'red'";
		var d="'black'";
		var title='最新活动<span style="float:right;"><a href="javascript:void(0);" onclick="addTab('+a+','+b+')" style="text-decoration: none;color:black;font-weight:bolder;" onmouseover="this.style.color='+c+'" onmouseout="this.style.color='+d+'">更&nbsp;&nbsp;多>></a></span>';
		
	$('#zxDatagrid').datagrid({
		title:title,
		border:false,
		url:url,
		width: 'auto',
		height:'auto',
		striped:true,
		nowrap:true,
		rownumbers:true,
		singleSelect:true,
		selectOnCheck:false,
		checkOnSelect:false,
		sortName:'aspecies',
		sortOrder:'desc',
		pageSize: 7,
		pageList:[7,8,10,50],
		pageNumber:1,
		idField:'aid',
		fit:true,
		fitColumns :true,
		columns:[[
			{field:'atheme',title:'活动主题',align:'center',width:280,sortable:true},
			{field:'time',title:'时间',align:'center',width:180,sortable:true,formatter : function(value,row){
				if(row.timebeg&&row.timeend){return row.timebeg.substring(0,10)+"至"+row.timeend.substring(0,10);}
			}},
		]],	
	});
	
	 a="'活动情况'";
	 var DateSub1=(addDaysToDate(new Date,-1));
	 b="'/activity!activity.do?status=<%=Constants.ACT_TYPE_STATUS_YFB%>&endTime="+DateSub1+"',''";
	 c="'red'";
	 d="'black'";
	 title='未办结活动<span style="float:right;"><a href="javascript:void(0);" onclick="addTab('+a+','+b+')" style="text-decoration: none;color:black;font-weight:bolder;" onmouseover="this.style.color='+c+'" onmouseout="this.style.color='+d+'">更&nbsp;&nbsp;多>></a></span>';
	$('#wbjDatagrid').datagrid({
		title:title, 
		border:false,
		url:'${pageContext.request.contextPath}/activity!doNotNeedAuth_wbjdatagrid.do',
		width: 'auto',
		height:'auto',
		striped:true,
		nowrap:true,
		rownumbers:true,
		singleSelect:true,
		selectOnCheck:false,
		checkOnSelect:false,
		sortName:'aspecies',
		sortOrder:'desc',
		pageSize: 7,
		pageList:[7,8,10,50],
		pageNumber:1,
		idField:'aid',
		fit:true,
		fitColumns :true,
		columns:[[
			{field:'atheme',title:'活动主题',align:'center',width:280,sortable:true},
			{field:'time',title:'时间',align:'center',width:180,sortable:true,formatter : function(value,row){
				if(row.timebeg&&row.timeend){return row.timebeg.substring(0,10)+"至"+row.timeend.substring(0,10);}
			}},
		]],	
	});
	 a="'活动管理'";
	 b="'/activity!amanage.do?status=<%=Constants.ACT_TYPE_STATUS_WFB%>',''";
	 c="'red'";
	 d="'black'";
	 title='未发布活动<span style="float:right;"><a href="javascript:void(0);" onclick="addTab('+a+','+b+')" style="text-decoration: none;color:black;font-weight:bolder;" onmouseover="this.style.color='+c+'" onmouseout="this.style.color='+d+'">更&nbsp;&nbsp;多>></a></span>';
	$('#wfbDatagrid').datagrid({
		title:title, 
		border:false,
		url:'${pageContext.request.contextPath}/activity!doNotNeedAuth_wfbdatagrid.do',
		width: 'auto',
		height:'auto',
		striped:true,
		nowrap:true,
		rownumbers:true,
		singleSelect:true,
		selectOnCheck:false,
		checkOnSelect:false,
		sortName:'aspecies',
		sortOrder:'desc',
		pageSize: 7,
		pageList:[7,8,10,50],
		pageNumber:1,
		idField:'aid',
		fit:true,
		fitColumns :true,
		columns:[[
			{field:'atheme',title:'活动主题',align:'center',width:280,sortable:true},
			{field:'time',title:'时间',align:'center',width:180,sortable:true,formatter : function(value,row){
				if(row.timebeg&&row.timeend){return row.timebeg.substring(0,10)+"至"+row.timeend.substring(0,10);}
			}},
		]],	
	});
	$('#cjDatagrid').datagrid({
		border:false,
		url:'${pageContext.request.contextPath}/committee!doNotNeedAuth_cjdatagrid.do',
		width: 'auto',
		height:'auto',
		striped:true,
		nowrap:true,
		rownumbers:true,
		singleSelect:true,
		selectOnCheck:false,
		checkOnSelect:false,
		sortName:'aspecies',
		sortOrder:'desc',
		pagination:true,
		pageSize:8,
		pageList:[8,10,20,50,100],
		pageNumber:1,
		idField:'aid',
		fit:true,
		fitColumns :true,
		columns:[[
			{field:'name',title:'姓名',align:'center',width:100,sortable:true},
			{field:'companyName',title:'工作单位',align:'center',width:200,sortable:false},
			{field:'job',title:'职务',align:'center',width:150,sortable:false},
			{field:'partyName',title:'党派',align:'center',width:150},
			{field:'circleName',title:'界别',align:'center',width:100,sortable:false},
			{field:'sexName',title:'性别',align:'center',width:40},
			{field:'telephone',title:'手机号',align:'center',width:100}		
		]],	
	});

	$('#qxDatagrid').datagrid({
		border:false,
		url:'${pageContext.request.contextPath}/committee!doNotNeedAuth_wcjdatagrid.do',
		width: 'auto',
		height:'auto',
		striped:true,
		nowrap:true,
		rownumbers:true,
		singleSelect:true,
		selectOnCheck:false,
		checkOnSelect:false,
		sortName:'aspecies',
		sortOrder:'desc',
		pagination:true,
		pageSize:8,
		pageList:[8,10,20,50,100],
		pageNumber:1,
		idField:'aid',
		fit:true,
		fitColumns :true,
		columns:[[
			{field:'name',title:'姓名',align:'center',width:100,sortable:true},
			{field:'companyName',title:'工作单位',align:'center',width:200,sortable:false},
			{field:'job',title:'职务',align:'center',width:150,sortable:false},
			{field:'partyName',title:'党派',align:'center',width:150},
			{field:'circleName',title:'界别',align:'center',width:100,sortable:false},
			{field:'sexName',title:'性别',align:'center',width:40},
			{field:'telephone',title:'手机号',align:'center',width:100}	
		]],	
	});
	$('#cwDatagrid').datagrid({
		border:false,
		url:'${pageContext.request.contextPath}/committee!doNotNeedAuth_cwdatagrid.do',
		width: 'auto',
		height:'auto',
		striped:true,
		nowrap:true,
		rownumbers:true,
		singleSelect:true,
		selectOnCheck:false,
		checkOnSelect:false,
		sortName:'aspecies',
		sortOrder:'desc',
		pagination:true,
		pageSize:8,
		pageList:[8,10,20,50,100],
		pageNumber:1,
		idField:'aid',
		fit:true,
		fitColumns :true,
		columns:[[
			{field:'name',title:'姓名',align:'center',width:100,sortable:true},
			{field:'companyName',title:'工作单位',align:'center',width:200,sortable:false},
			{field:'job',title:'职务',align:'center',width:150,sortable:false},
			{field:'partyName',title:'党派',align:'center',width:150},
			{field:'circleName',title:'界别',align:'center',width:100,sortable:false},
			{field:'sexName',title:'性别',align:'center',width:40},
			{field:'telephone',title:'手机号',align:'center',width:100}	
		]],	
	});
});

</script>

<div data-options="region:'center',border:false" style="overflow: hidden;height:100%">
		<div class="out" style="width:100%;height:50%;margin-bottom:2px;">
		<!--常量（用户级别为委员） -->
		<c:set var="ug_wy" value="<%=Constants.DIC_TYPE_YHZB_WY %>" scope="page"></c:set>
		<c:choose>
			<%--委员--%>
			<c:when test="${sessionInfo.userGroup==ug_wy}">
				<div class="in" style="width:99.5%;height:99.6%;margin-left:0.16%;margin-top:0.16%;border:1px solid #95B8E7">
					<table id="zxDatagrid" ></table>
				</div>
			</c:when>
			<c:otherwise>
				<div class="in" style="width:32.9%;height:99.6%;margin-left:0.16%;margin-top:0.16%;border:1px solid #95B8E7">
					<table id="zxDatagrid" ></table>
				</div>
				<div  class="in" style="width:32.9%;height:99.6%;margin-left:0.16%;margin-top:0.16%;border:1px solid #95B8E7">
					<table id="wbjDatagrid" ></table>
				</div>
				<div class="in" style="width:32.9%;height:99.6%;margin-left:0.16%;margin-top:0.16%;border:1px solid #95B8E7">
					<table id="wfbDatagrid" ></table>
				</div>
			</c:otherwise>
		</c:choose>
		</div>
		<div id="tt" class="easyui-tabs out" data-options="border:true">
			<div class="in" title="出席活动>5次的委员" style="width:100%;height:99%;">
				<table id="cjDatagrid" ></table>
			</div>	
			<div  class="in" title="缺席活动>5次的委员" style="width:100%;height:99%;">
				<table id="qxDatagrid" ></table>
			</div>
			<div class="in" title="从未参加活动的委员" style="width:100%;height:99%;">
				<table id="cwDatagrid" ></table>
			</div>
		</div>
</div>
