<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.tlzn.util.base.Constants"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	function initCombox(form){
		initCombobox(form,'datatype','${pageContext.request.contextPath}/dic!combox.do?ctype=MATERIALTYPE');
		//initCombobox(form,'status','${pageContext.request.contextPath}/dic!combox.do?ctype=PUBSTATUS');
	}
	var p;
	var datagrid;
	$(function(){
		initCombox("searchForm"); 
		initCombox("referMaterialDatagrid");
		initCombox("referMaterial_queryForm");
		var statusPubYes='<%=Constants.CODE_TYPE_PUBSTATUS_YES%>';
		var url='${pageContext.request.contextPath}/referMaterial!datagrid.do?fromtype=2&status='+statusPubYes;
		datagrid = $('#referMaterialDatagrid').datagrid({
			url:url,
        	width: 'auto',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			idField:'dataId',
 			singleSelect:true,
			pagination:true,
			pageSize:20,
			pageList:[20,50],
			pageNumber:1,
			fit:true,
			fitColumns :true,
			checkOnSelect:false,
			selectOnCheck:false,
			frozenColumns:[[
				{field:'dataId',title:'ID',width:100,align:'center',checkbox : true},
				{field:'datatypeName',title:'资料类型',width:100,align:'center'},
				{field:'title',title:'资料标题',width:550,align:'center'}
				]],
			columns:[[
				//{field:'content',title:'会议资料内容',width:100,align:'center'},
				{field:'depName',title:'发布机构',width:170,align:'center'},
				{field:'pubdate',title:'发布日期',width:150,align:'center',
					formatter: function(value,row,index){
						if (value){
							return value.substring(0,19);
						} else {
							return value;
						}
					}	
				},
				{field:'fromtypeName',title:'来源',width:100,align:'center'},
				{field:'statusName',title:'状态',width:100,align:'center'},
				{field:'atts',title:'附件数',width:100,align:'center',
					formatter: function(value,row,index){
						if (value){
							var attArray = value.split(",");//以逗号作为分隔字符串
							return attArray.length;
						} else {
							return value;
						}
					}		
				}
			]],   
			toolbar : [
			{
				text : '查询',
				iconCls : 'icon-search',
				handler : function() {
					$('#win_referMaterial_query').window('open');
				}
			}, '-',
			{
				text : '查看详细',
				iconCls : 'icon-info',
				handler : function() {
					view();
				}
			}
			]
		});
	});
	
		//查看
		function view() {
			var rows = datagrid.datagrid('getSelections');
			if (rows.length == 1) {
				var p = dj.dialog({
					title : '资料详情',
					href : '${pageContext.request.contextPath}/referMaterial!get.do?dataId='+rows[0].dataId,
					maximized:true,
					maximizable:true,
					width : 800,
					height : 600,
					onLoad : function() {
						initCombox('noticeViewForm');
						var f = p.find('form');
						f.form('load', {
							noticeNo : rows[0].noticeNo,
							noticeName : rows[0].noticeName,
							noticeType: rows[0].noticeType,
							checkInStartTime : rows[0].checkInStartTime,
							checkInEndTime : rows[0].checkInEndTime,
							checkOutStartTime : rows[0].checkOutStartTime,
							checkOutEndTime : rows[0].checkOutEndTime,
							status : rows[0].status,
							optorName : rows[0].optorName,
							optTime : rows[0].optTime
						});
					},
					buttons : [ {
						text: '关闭 ', 
						iconCls:'icon-cancel',
						handler: function() { 
						p.dialog('close'); 
						} 
					}]
					
				});
				
			} else if (rows.length > 1) {
				parent.dj.messagerAlert('提示', '同一时间只能查看一条记录！', 'error');
			} else {
				parent.dj.messagerAlert('提示', '请选择要查看的记录！', 'error');
			}
		}
		
	//查询
	function _search() {
		datagrid.datagrid('load', dj.serializeObject($('#referMaterial_queryForm')));
		 $('#win_referMaterial_query').window('close');
	}
	
	function cleanSearch() {
		datagrid.datagrid('load', {});
		$('#referMaterial_queryForm input').val('');
		$('#datatypeId').combobox('setValue', '');
	}
</script>
</head>
<body class="easyui-layout" data-options="fit:true">
	<!-- 表格 -->
	<div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="referMaterialDatagrid"></table>
	</div>
	<div id="win_referMaterial_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:500px;height:274px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="referMaterial_queryForm">
				<input type="hidden" name="filingFlg" id="filingFlgId"/>
				<table id="poll_queryTable">
					<tr height="25">
						<td nowrap>资料类型：</td>
						<td colspan="3"><input id="datatypeId" name="datatype" style="width:300px;"></td>
					</tr>
					<tr height="25">
						<td nowrap>资料标题：</td>
						<td colspan="3"><input type="text" class="easyui-validatebox"  name="title" style="width:300px;"></td>
					</tr>				
					<tr height="25">
						<td nowrap>资料内容：</td>
						<td colspan="3"><input type="text" class="easyui-validatebox"  name="content" style="width:300px;"></td>
					</tr>
					<tr height="25">
						<td nowrap>发布机构 ：</td>
						<td colspan="3"> <input type="text" class="easyui-validatebox"  name="depName" style="width:300px;"></td>
					</tr>
					<tr height="25">
						<td nowrap>发布日期 ：</td>
						<td colspan="3">
							<input id="pubDateStartId" type="text" class="easyui-datebox" name="pubDateStart" data-options="required:false,showSeconds:false" style="width:142px;"></input> 
		 	              	~ 
			              	<input id="pubDateEndId" type="text" class="easyui-datebox" name="pubDateEnd" data-options="required:false,showSeconds:false" style="width:142px;"></input>  
						</td>
					</tr>
				</table>
				</form>
				</center>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;height:40px">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)" onclick="_search()">查询</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)" onclick="cleanSearch()">重置</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#win_referMaterial_query').window('close');">取消</a>
			</div>
		</div>
	</div>
</body>
</html>