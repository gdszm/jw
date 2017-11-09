<%@page import="com.tlzn.util.base.Constants"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	function initCombox(form){
		initCombobox(form,'answerMode','${pageContext.request.contextPath}/dic!combox.do?ctype=GTFS');
		initCombobox(form,'solveHow','${pageContext.request.contextPath}/dic!combox.do?ctype=JJCD');
		initCombobox(form,'carryoutFlg','${pageContext.request.contextPath}/dic!combox.do?ctype=YESNO');
		initCombobox(form,'committeemanOpinion','${pageContext.request.contextPath}/dic!combox.do?ctype=WYYJ');
		getcombobox('handleTypeId','${pageContext.request.contextPath}/dic!combox.do?ctype=BLLX'); 
		initCombobox(form,'status','${pageContext.request.contextPath}/dic!combox.do?ctype=HFZT');
	}
	
	var datagrid;	
	$(document).ready(function() {
		datagrid=$('#listgrid');
		var startType='<%=request.getAttribute("statusType")%>';
		//var url='${pageContext.request.contextPath}/pollHandle!datagrid.do';
		var status;
		var filingFlg;
		if(startType!=""&& startType!=null){
			if(startType=='A'){
				$("#status").val("1");
				status="1";
			}
			if(startType=='B'){
				$("#status").val("3");
				status="3";
			}
			if(startType=='C'){
				$("#status").val("2");
				status="2";
			}
			if(startType=='D'){
				$("#status").val("4");
				status="4";
			}
		}
		datagrid.datagrid({
			url:'${pageContext.request.contextPath}/pollHandle!datagrid.do',
			width: 'auto',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			singleSelect:true,
			pagination:true,
			pageSize:20,
			pageList:[20,50,100],
			pageNumber:1,
			idField:'handleId',
			fit:true,
			queryParams: {
				status: status
			},
			columns:[[
				{field:'pollCode',title:'社情民意编号',align:'center',width:100,formatter:function(val,row){return row.poll.pollCode;}},
			    
				{field:'title',title:'社情民意标题',halign:'center', align:'left',width:420,formatter:function(val,row){return row.poll.title;}},
				{field:'createMan',title:'提交人',align:'center',width:90,formatter:function(val,row){return row.poll.createManName;}},
				{field:'handleTypeName',title:'办理类型',align:'center',width:70,formatter:function(val,row){return row.poll.handleTypeName;}},	
				
				{field:'factEnddate',title:'实际办结日期',align:'center',width:140},
				{field:'statusName',title:'状态',align:'center',width:60}
			]],				
			toolbar : [{
				text : '查询',
				iconCls : 'icon-search',
				handler : function() {
					query();
				}
			}, '-',  {
				text : '签收',
				iconCls : 'icon-edit',
				handler : function() {
					receiv();
				}
			},'-',  {
				text : '办理回复',
				iconCls : 'icon-reply',
				handler : function() {
					add();
				}
			}, '-', {
				text : '申退',
				iconCls : 'icon-refuse',
				handler : function() {
					back();
				}
			}, '-', {
				text : '查看',
				iconCls : 'icon-view',
				handler : function() {
					view();
				}
			}, '-', {
				text : '报表',
				iconCls : 'icon-export',
				handler : function() {
					report();
				}
			}],
			onRowContextMenu : function(e, rowIndex, rowData) {
				e.preventDefault();
				$(this).datagrid('unselectAll');
				$(this).datagrid('selectRow', rowIndex);
				$('#menu').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			},
			onDblClickRow : function(rowIndex, rowData) {
				viewOnDblClick(rowData.poll.pollId);
			}
		});
		//以下初始化查询combox列表
		initCombox('searchForm');
	});
	
  
	function add() {
		var row = datagrid.datagrid('getSelected');
		if (row) {
			if(row.mainFlg=='0'){
				dj.messagerAlert('提示', '对不起，协办单位不能回复！', 'error');
				return;
			}
			if(row.status=='1'){
				dj.messagerAlert('提示', '请先签收社情民意！', 'error');
				return;
			}
			if(row.statusName=='申退中'){
				dj.messagerAlert('提示', '申退中不能回复！', 'error');
				return;
			}
			if(row.replyPass=='1'){
				dj.messagerAlert('提示', '办复报告审查通过，不能进行修改！', 'error');
				return;
			}
			var p = dj.dialog({
				title : '办理回复',
				href :'${pageContext.request.contextPath}/pollHandle!handAE.do?handleId=' + row.handleId,
				maximized:true,
				maximizable:true,
				width : 800,
				height : 500,
				buttons : [ {
					text : '回复',
					align: 'center',
					handler : function() {
						var f = p.find('form');
						f.form('submit', {
							url : '${pageContext.request.contextPath}/pollHandle!add.do',
							success : function(d) {
								var json = $.parseJSON(d);
								if (json.success) {
									datagrid.datagrid('reload');
									p.dialog('close');
								}
								parent.dj.messagerShow({
									msg : json.msg,
									title : '提示'
								});
							}
						});
					}
				} ,
				{
					text : '关闭',
					iconCls:'icon-cancel',
					handler : function() {
						p.dialog('close');
					}
				}],
				onLoad : function() {
					initCombox('addForm');
				}
			});
		}else{
			dj.messagerAlert('提示', '请选择要回复的记录！', 'info');
		}
	}
	
	function receiv(){
		var rows = datagrid.datagrid('getSelections');
		var ids = [];
		if (rows.length > 0) {
			for ( var i = 0; i < rows.length; i++) {
				if(rows[i].status!='<%=Constants.CODE_TYPE_HFZT_NEW%>'){
					$.messager.alert('提示', '选中记录中存在已签收记录！', 'error');
					return;
				}
				ids.push(rows[i].handleId);
			}
			$.ajax({
				url : '${pageContext.request.contextPath}/pollHandle!receiv.do',
				data : {
					ids : ids.join(',')
				},
				dataType : 'json',
				success : function(d) {
					datagrid.datagrid('reload');
					datagrid.datagrid('unselectAll');
					parent.dj.messagerShow({
						msg : d.msg,
						title : '提示'
					});
				}
			});
		} else {
			$.messager.alert('提示', '请勾选记录！', 'error');
		}
	}

	//申退
	function back() {
		var row = datagrid.datagrid('getSelected');
		if (row) {
			if(row.status=='4'){
				dj.messagerAlert('提示', '提案已办复,不能进行申退！', 'error');
				return;
			}
			var p = dj.dialog({
				title : '申退办理',
				href :'${pageContext.request.contextPath}/pollHandle!handBack.do?handleId=' + row.handleId,
				width : 600,
				height : 400,
				buttons : [ {
					text : '申退',
					align: 'center',
					handler : function() {
						var f = p.find('form');
						f.form('submit', {
							url : '${pageContext.request.contextPath}/pollHandle!back.do',
							success : function(d) {
								var json = $.parseJSON(d);
								if (json.success) {
									datagrid.datagrid('reload');
									p.dialog('close');
								}
								parent.dj.messagerShow({
									msg : json.msg,
									title : '提示'
								});
							}
						});
					}
				} ,
				{
					text : '关闭',
					iconCls:'icon-cancel',
					handler : function() {
						p.dialog('close');
					}
				}],
				onLoad : function() {
					initCombox('addForm');
				}
			});
		}else{
			parent.dj.messagerAlert('提示', '请选择要申退的记录！', 'info');
		}
	}
	
	function view(){
		var selectRow = datagrid.datagrid('getSelected');
		if (selectRow) {
			var pollId=selectRow.poll.pollId;
			window.open("${pageContext.request.contextPath}/poll!pollSee.do?pollId="+pollId);
		}else{
			dj.messagerAlert('提示', '请选择要查看的社情民意！', 'error');
		}		
		return false;
	}
	function viewOnDblClick(pollId){
		if (pollId) {
			window.open("${pageContext.request.contextPath}/poll!pollSee.do?pollId="+pollId);
		}else{
			dj.messagerAlert('提示', '请双击要查看的社情民意！', 'error');
		}	
		return false;
	}
	function report(){
		var totalRowNum = datagrid.datagrid('getPager').data("pagination").options.total;
		if(totalRowNum>0){
			$.ajax({
				url : '${pageContext.request.contextPath}/pollHandle!report.do',
				success : function(d) {
					var json = $.parseJSON(d);
					if (json.success) {
						//window.open("${pageContext.request.contextPath}/" + json.obj);
						dj.messagerShow({
							msg : json.msg,
							title : '提示'
						});
						window.location.href=json.obj;
					}
				}
			});
		}else{
			$.messager.alert('提示', '当前无记录，无需导出数据！', 'info');
		}
	}
	
	function query() {
		//$('#searchForm').form('clear');
		$('#win_query').window('open');
	}
	
	function _search() {	//此处方法不能为search()
		datagrid.datagrid('load', dj.serializeObject($('#searchForm')));
		$('#win_query').window('close');
	}
	
	function cleanSearch() {
		datagrid.datagrid('load', {});
		$("input,textarea,select").val("");
	}

</script>
</head>

<body class="easyui-layout">
	<!-- 查询窗口-->
	<div id="win_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:550px;height:300px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="searchForm">
				<table id=tableForm>				
					<tr height="30">
						<td nowrap>社情民意编号：</td>
						<td><input type="text" name="poll.pollCode" style="width:360px;padding:2px"></td>
					</tr>
					<tr height="30">
						<td nowrap>社情民意标题：</td>
						<td> <input type="text" name="poll.title" style="width:360px;padding:2px"></td>
					</tr>
					<tr height="30">
						<td nowrap>社情民意提交人：</td>
						<td> <input type="text" name="poll.createManName" style="width:360px;padding:2px"></td>
					</tr>
					<tr height="30">
						<td nowrap>办理类型：</td>
						<td> <input id="handleTypeId"  name="poll.handleType" style="width:160px;padding:2px"></td>
					</tr>
					<tr height="30">
						<td nowrap>回复状态：</td>
						<td> <input id="status"  name="status" style="width:160px;padding:2px"></td>
					</tr>
				</table>
				</form>
				</center>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;height:40px">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)" onclick="_search()">查询</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)" onclick="cleanSearch()">重置</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#win_query').window('close');">取消</a>
			</div>
		</div>
	</div>
	<!-- Datagrid表格 -->
	<div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="listgrid"></table>
	</div>
	<!-- 菜单 -->
	<div id="menu" class="easyui-menu" style="width:120px;display: none;">
		<div onclick="receiv();" data-options="iconCls:'icon-edit'">签收</div>
		<div onclick="back();" data-options="iconCls:'icon-refuse'">申退</div>
		<div onclick="add();" data-options="iconCls:'icon-reply'">办理回复</div>
		<div onclick="view();" data-options="iconCls:'icon-view'">查看</div>
		<div onclick="report();" data-options="iconCls:'icon-export'">报表</div>
	</div>
</body>
</html>