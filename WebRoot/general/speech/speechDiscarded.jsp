<!-- 定稿的发言列表 -->
<%@page import="com.tlzn.util.base.Constants"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	var p;
	var datagrid;
	function initCombox(form){
		initCombobox(form,'status','${pageContext.request.contextPath}/dic!combox.do?ctype=SPEECHSTATUS');
		//initCombobox(form,'speechType','${pageContext.request.contextPath}/dic!combox.do?ctype=NOTICETYPE');
	}

	$(function(){
		initCombox("searchForm"); 
		initCombox("speechDatagrid");
		initCombox("speech_queryForm");
		datagrid = $('#speechDatagrid').datagrid({
			url:'${pageContext.request.contextPath}/speech!datagridZq.do',
        	width: 'auto',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			idField:'speakId',
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
				{field:'speakId',title:'ID',width:100,align:'center',checkbox : true},
				{field:'title',title:'发言主题',width:400,align:'center'}
				]],
			columns:[[
				//{field:'content',title:'发言内容',width:100,align:'center'},
				{field:'meetName',title:'所属会议',width:100,align:'center'},
				{field:'name',title:'发言人',width:100,align:'center'},
				{field:'useSituationName',title:'采用情况',width:100,align:'center'},
				{field:'statusName',title:'状态',width:100,align:'center'},
				{field:'createTime',title:'提交日期',width:100,align:'center',
					formatter: function(value,row,index){
						if (value){
							return value.substring(0,19);
						} else {
							return value;
						}
					}
				},
				{field:'discardTypeName',title:'转弃方向',width:100,align:'center',
					formatter: function(value,row,index){
						if (value){
							return '已转至'+value;
						} else {
							return "";
						}
					}
				}
    		]],
			toolbar : [
			{
				text : '查询',
				iconCls : 'icon-search',
				handler : function() {
					$('#win_speech_query').window('open');
				}
			}, '-', {
				text : '转弃',
				iconCls : 'icon-ok',
				handler : function() {
					discard();
				}
			}
			//, '-',	{
			//	text : '恢复',
			//	iconCls : 'icon-undo',
			//	handler : function() {
			//		recover();
			//	}
			//}
			, '-',	{
				text : '查看详细',
				iconCls : 'icon-info',
				handler : function() {
					view();
				}
			}
			]
		});
	});
	
	//发言人选择部分 开始
	function gettjzgrid(){
		//所有发言人列表
		$('#tjzgrid').datagrid({
			//委员
			url:'${pageContext.request.contextPath}/comm!getCurSecComm.do?groupCode=<%=Constants.DIC_TYPE_YHZB_WY%>',
			width: 'auto',
			height:'auto',
			striped:true,
			nowrap:true,
			pagination:true,
			pageSize:10,
			pageList:[10,15,20],
			singleSelect:true,
			rownumbers:true,
			sortName: 'code',
			sortOrder: 'asc',
			idField:'code',
			fit:true,
			frozenColumns:[[
				{field:'code',title:'编号',checkbox:true,align:'center',width:40},
				{field:'name',title:'提交者',align:'left',width:100,sortable:false}
			]],
			columns:[[
				{field:'groupName',title:'提交者分组',align:'center',width:100,sortable:false},
				{field:'telephone',title:'联系电话',align:'left',width:120,sortable:false},
				{field:'email',title:'电子邮箱',align:'center',width:120,sortable:false},
			]]
		});
	} 
	//发言人过虑
	function tjr_search() {
		$('#tjzgrid').datagrid('load', dj.serializeObject($('#tjr_queryForm')));
	}
	//发言人过虑取消
	function tjr_cleanSearch() {
		$('#tjzgrid').datagrid('load', {});
		$('#wyNameId').val("");
	}
	//发言人选择确定
	function tjr_SelectConfirm() {
		var row = $('#tjzgrid').datagrid('getChecked');
		if (row) {
			$('#codeId').val(row[0].code);
			$('#nameId').val(row[0].name);
			$('#telephoneId').val(row[0].telephone);
			$('#comparyAddressId').val(row[0].comparyAddress);
			$('#emailId').val(row[0].email);
			$('#win_wy_query').window('close');
		}
	}
	//发言人选择部分  结束
	
	//查看
	function view() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			var p = dj.dialog({
				title : '发言详情',
				href : '${pageContext.request.contextPath}/speech!speechSee.do?speakId='+ rows[0].speakId,
				maximized : true,
				maximizable : true,
				width : 800,
				height : 600,
				onLoad : function() {
					initCombox('speechViewForm');
				},
				buttons : [ {
					text : '关闭 ',
					iconCls : 'icon-cancel',
					handler : function() {
						p.dialog('close');
					}
				} ]

			});

		} else if (rows.length > 1) {
			parent.dj.messagerAlert('提示', '同一时间只能查看一条记录！', 'error');
		} else {
			parent.dj.messagerAlert('提示', '请选择要查看的记录！', 'error');
		}
	}
	//转弃
	function discard() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			if(rows[0].discardTypeName) { 
				parent.dj.messagerAlert('提示', '该条发言已经转弃，不能进行重复操作！', 'error');
				return;
			}
			var p = dj.dialog({
				title : ' 发言转弃  ',
				href : '${pageContext.request.contextPath}/speech!speechSee.do?speakId='+ rows[0].speakId,
				maximized : true,
				maximizable : true,
				width : 800,
				height : 600,
				buttons : [
						{
							text : '执行转弃',
							iconCls : 'icon-edit',
							handler : function() {
								$.ajax({
										url : '${pageContext.request.contextPath}/speech!discard.do?speakId='+ rows[0].speakId,
										dataType : 'json',
										success : function(d) {
											parent.dj.messagerShow({
												msg : d.msg,
												title : '提示'
											});
											if (d.success) {
												datagrid.datagrid('reload');
												p.dialog('close');
											}
											
										}
									});
							}
						}, {
							text : ' 取 消 ',
							iconCls : 'icon-cancel',
							handler : function() {
								p.dialog('close');
							}
						} ]

			});
		} else if (rows.length > 1) {
			parent.dj.messagerAlert('提示', '同一时间转弃一条会议发言！', 'error');
		} else {
			parent.dj.messagerAlert('提示', '请选择要转弃的会议发言！', 'error');
		}
	}
	
	//查询
	function _search() {
		datagrid.datagrid('load', dj.serializeObject($('#speech_queryForm')));
		$('#win_speech_query').window('close');
	}

	function cleanSearch() {
		datagrid.datagrid('load', {});
		$('#speech_queryForm input').val('');
		$('#statusId').combobox('setValue', '');
	//	$('#speechTypeId').combobox('setValue', '');
	}
	
</script>
</head>
<body class="easyui-layout" data-options="fit:true">
	<!-- 表格 -->
	<div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="speechDatagrid"></table>
	</div>
	<div id="win_speech_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:500px;height:274px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="speech_queryForm">
				<table id="speech_queryTable">
					<tr height="25">
						<td nowrap>发言主题：</td>
						<td><input type="text" name="title" class="easyui-validatebox" style="width:280px;padding:2px;"></td>
					</tr>			
					<tr height="25">
						<td nowrap>所属会议：</td>
						<td><input type="text" name="meetName" class="easyui-validatebox" style="width:280px;padding:2px;"></td>
					</tr>
					<tr height="25">
						<td nowrap>发言人：</td>
						<td><input type="text" name="name" class="easyui-validatebox" style="width:280px;padding:2px;"></td>
					</tr>
					<tr height="25">
						<td nowrap>提交时间：</td>
						<td> <input class="easyui-datebox" name="createTimeStart"   style="width:136px"/>--<input class="easyui-datebox" name="createTimeEnd"  style="width:136px"/></td>
					</tr>
					<tr height="25">
						<td nowrap>状态：</td>
						<td> <input id="statusId" panelHeight="100px" name="status" style="width:286px;padding:2px;border:1px solid #000;"></td>
					</tr>
				</table>
				</form>
				</center>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;height:40px">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)" onclick="_search()">查询</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)" onclick="cleanSearch()">重置</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#win_speech_query').window('close');">取消</a>
			</div>
		</div>
	</div>
</body>
</html>