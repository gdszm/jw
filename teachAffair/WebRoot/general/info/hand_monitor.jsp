<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript">
	var centerTabs;
	var monitorgrid;
	$(document).ready(function() {
		monitorgrid=$('#monitorgrid');
		//列表
		monitorgrid.datagrid({
			url:'${pageContext.request.contextPath}/hand!monitordatagrid.do',
			striped:true,
			nowrap:false, 
			rownumbers:true,
			idField:'companyId',
			sortName:'companyId',
			sortOrder : 'asc',
			fit:true,
			columns:[[
				{field:'companyId',hidden:true,rowspan:2}, 
				{field:'companyName',title:'单位名称',align:'center',width:300,rowspan:2},
				{field:'rate',title:'整体进度(不含会办)',align:'left',width:140,rowspan:2,
				formatter:function(val,rowData,rowIndex){
				    return '<div style="position:relative;width:' + val + '%;height:20px;line-height:20px;text-align:center;background:' + (val<30 ? '#CD2626' : (val<60 ? '#FFB90F' : '#66CD00')) + ';"><div style="position:relative;top:0px;width:140px;height:20px;line-height:20px;text-align:center;z-index:500" >'+val+'%</div></div>'; 
				}},
				{title:'办理总数(不含会办)',align:'center',width:140,colspan:2},				
				{title:'单办',align:'center',width:140,colspan:2},
				{title:'分办',align:'center',width:140,colspan:2},
				{title:'会办(主办)',align:'center',width:140,colspan:2}
			],[
				{field:'tote_all',title:'办理数',align:'center',width:70},				
				{field:'tote_no',title:'未办数',align:'center',width:70},
				{field:'single_all',title:'办理数',align:'center',width:70},				
				{field:'single_no',title:'未办数',align:'center',width:70},
				{field:'branch_all',title:'办理数',align:'center',width:70},				
				{field:'branch_no',title:'未办数',align:'center',width:70},
				{field:'host_all',title:'办理数',align:'center',width:70},				
				{field:'host_no',title:'未办数',align:'center',width:70}
			]],
			onClickCell:function(index,field,value){
				var row=monitorgrid.datagrid('getData').rows[index];
				url='/hand!handquery.do?companyId='+row.companyId+'&field='+field;
 				addTab('办理情况',url,'icon-cx');
			},
	         rownumbers:true  
		});
		
		centerTabs = parent.$('#centerTabs').tabs({
			fit : true,
			border : false,
			onContextMenu : function(e, title) {
				e.preventDefault();
				tabsMenu.menu('show', {
					left : e.pageX,
					top : e.pageY
				}).data('tabTitle', title);
			}
		});
	});
	
	function addTab(cname,curl,ciconCls) {
		if (centerTabs.tabs('exists', cname)) {
			centerTabs.tabs('select', cname);
			var tab =centerTabs.tabs('getSelected'); 
			centerTabs.tabs('update', {
				tab: tab,
				options: {
					title: cname,
					content: '<iframe src="${pageContext.request.contextPath}' + curl + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>'  // 新内容的URL
				}
			});
			
		} else {
			if (curl && curl.length > 0) {
				/*if (curl.indexOf('!druid.do') < 0) {/*数据源监控页面不需要开启等待提示
					$.messager.progress({
						text : '页面加载中....',
						interval : 100
					});
					window.setTimeout(function() {
						try {
							$.messager.progress('close');
						} catch (e) {
						}
					}, 5000);
				}*/
				centerTabs.tabs('add', {
					title : cname,
					closable : true,
					iconCls : ciconCls,
					content : '<iframe src="${pageContext.request.contextPath}' + curl + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
					tools : [ {
						iconCls : 'icon-mini-refresh',
						handler : function() {
							refreshTab(cname);
						}
					} ]
				});
			} else {
				centerTabs.tabs('add', {
					title : cname,
					closable : true,
					iconCls : ciconCls,
					content : '<iframe src="error/err.jsp" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
					tools : [ {
						iconCls : 'icon-mini-refresh',
						handler : function() {
							refreshTab(cname);
						}
					} ]
				});
			}
		}
	}
</script>
</head>
<body>
<!-- 表格 -->
	<div class="easyui-layout" data-options="fit:true">						
		<div data-options="region:'center',border:false" style="padding:1px;background:#fff;border:1px solid #ccc;">
			<table id="monitorgrid"></table>
			
		</div>
	</div>
</body>
</html>