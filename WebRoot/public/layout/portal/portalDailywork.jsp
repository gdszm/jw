<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.tlzn.util.base.Constants"%>
<style>
.datagrid-cell{line-height:24px}
.tabs {
  list-style-type: none;
  height: 26px;
  margin: 0px;
  padding: 0px;
  padding-left: 4px;
  width: auto; 
  border-style: solid;
  border-width: 0 0 1px 0;
}
</style>
<script type="text/javascript">
	//设置通知公告和tabs控件的高度
	var height=($(".tabs-panels").css("height"));
	var h=parseInt(height.replace("px", ""));
	var heightOffset=(h-8)/2;
	$("#noticeDatagridId").attr("style","height:"+heightOffset+"px;margin:2px;width:99.5%;border:1px solid #95B8E7");
	$("#tt").attr("style","height:"+heightOffset+"px;margin:2px;");
	
	var datagrid;var meetFileDatagrid;var referDatagrid;
	$(function(){
		var a="'通知查询'";
		var b="'/notice!noticeQuery.do',''";
		var c="'red'";
		var d="'black'";
		var url='通知公告<span style="float:right;"><a href="javascript:void(0);" onclick="addTab('+a+','+b+')" style="text-decoration: none;color:black;font-weight:bolder;" onmouseover="this.style.color='+c+'" onmouseout="this.style.color='+d+'">更&nbsp;&nbsp;多>></a></span>';
		datagrid = $('#noticeDatagrid').datagrid({
			title:url,  
			border:false,
			url:'${pageContext.request.contextPath}/notice!doNotNeedAuth_datagrid.do?status=<%=Constants.CODE_TYPE_PUBSTATUS_YES%>&page=1&rows=9',
        	width: 'auto',	
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			idField:'id',
			singleSelect:true,
			pagination:false,
			pageSize:9,
			pageList:[9],
			pageNumber:1,
			fit:true,
			fitColumns :true,
			checkOnSelect:false,
			selectOnCheck:false,
			columns:[[
				//{field:'id',title:'ID',width:100,align:'center',checkbox : true},
				{field:'title',title:'通知标题',width:350,align:'center'},
				{field:'pubDate',title:'发布日期',width:120,align:'center',
					formatter: function(value,row,index){
						if (value){
							return value.substring(0,10);
						} else {
							return value;
						}
					}	
				},
				{field:'validDate',title:'有效日期',width:120,align:'center',
					formatter: function(value,row,index){
						if (value){
							return value.substring(0,10);
						} else {
							return value;
						}
					}	
				}
    		]],
    		onLoadSuccess: function(data){
    		//	rows = datagrid.datagrid('getRows');
    		//	datagrid.datagrid({title:  "通知公告("+rows.length+")"});
			}
		});
		meetFileDatagrid = $('#meetFileDatagrid').datagrid({
		
			url:'${pageContext.request.contextPath}/meetFile!doNotNeedAuth_datagrid.do?fromtype=1&status=<%=Constants.CODE_TYPE_PUBSTATUS_YES%>',
			border:false,
        	width: 'auto',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			idField:'dataId',
			singleSelect:true,
			pagination:true,
			pageSize:8,
			pageList:[8,10,20,50],
			pageNumber:1,
			fit:true,
			fitColumns :true,
			checkOnSelect:false,
			selectOnCheck:false,
			columns:[[
				//{field:'dataId',title:'ID',width:100,align:'center',checkbox : true},	
				{field:'datatypeName',title:'文件类型',width:150,align:'center'},
				{field:'title',title:'文件标题',width:400,align:'center'},
				{field:'depName',title:'发布机构',width:250,align:'center'},
				{field:'pubdate',title:'发布日期',width:250,align:'center',
					formatter: function(value,row,index){
						if (value){
							return value.substring(0,19);
						} else {
							return value;
						}
					}	
				}
    		]],
    		onLoadSuccess: function(data){
			}
		});
		
		referDatagrid = $('#referDatagrid').datagrid({
			url:'${pageContext.request.contextPath}/referMaterial!doNotNeedAuth_datagrid.do?fromtype=2&status=<%=Constants.CODE_TYPE_PUBSTATUS_YES%>',
			border:false,
        	width: 'auto',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			idField:'dataId',
			singleSelect:true,
			pagination:true,
			pageSize:8,
			pageList:[8,10,20,50],
			pageNumber:1,
			fit:true,
			fitColumns :true,
			checkOnSelect:false,
			selectOnCheck:false,
			columns:[[
				//{field:'dataId',title:'ID',width:100,align:'center',checkbox : true},
				{field:'datatypeName',title:'资料类型',width:150,align:'center'},
				{field:'title',title:'资料标题',width:400,align:'center'},
				{field:'depName',title:'发布机构',width:250,align:'center'},
				{field:'pubdate',title:'发布日期',width:250,align:'center',
					formatter: function(value,row,index){
						if (value){
							return value.substring(0,19);
						} else {
							return value;
						}
					}		
				},
    		]],
    		onLoadSuccess: function(data){
    		//	rows = datagrid.datagrid('getRows');
    		//	datagrid.datagrid({title:  "通知公告("+rows.length+")"});
			}
		});
		
		//设置tabs控件的更多
		$('#tt').tabs({
		    onSelect:function(title,index){
		       if(title=='会议文件') {
			    	a="'文件查询'";
					b="'/meetFile!meetFileQuery.do',''";
		    	} 
		    	if(title=='参阅资料') {
		    		a="'资料查询'";
					b="'/referMaterial!referMaterialManage.do',''";
		    	}
		    	c="'red'";
				d="'black'";
				var bl='';	
				url='<li class="moreId" style="float:right;"><a href="javascript:void(0);" onclick="addTab('+a+','+b+')" style="text-decoration: none;color:black;font-weight:bolder;line-height: 25px;" onmouseover="this.style.color='+c+'" onmouseout="this.style.color='+d+'">更&nbsp;&nbsp;多>></a></li>';
				url2=' style="float:right;"><a href="javascript:void(0);" onclick="addTab('+a+','+b+')" style="text-decoration: none;color:black;font-weight:bolder;line-height: 25px;" onmouseover="this.style.color='+c+'" onmouseout="this.style.color='+d+'">更&nbsp;&nbsp;多>></a>';
				url=bl+url;
				console.info($("#tt .tabs-header .tabs li:first-child"));
				$(".moreId").remove();
				$("#tt .tabs-header .tabs li:last-child").after(url);
		    }
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true">
	<!-- 表格 -->
	<div id="centerId" data-options="region:'center',border:false">
		<div id="noticeDatagridId" style="margin:2px;width:99.5%;border:1px solid #95B8E7">   
 			 <table id="noticeDatagrid" ></table>
		</div>  		
		<div id="tt" class="easyui-tabs" style="margin:2px;">
			<div id='meetFileTabId' title="会议文件" data-options="closable:false">
				<table id="meetFileDatagrid"></table>
			</div>
			<div id='referTabId'  title="参阅资料" data-options="closable:false">
				<table id="referDatagrid"></table>
			</div>
		</div>
	</div>
</div>

</html>
