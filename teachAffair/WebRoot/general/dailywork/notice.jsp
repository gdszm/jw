<%@page import="com.tlzn.util.base.Constants"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	function initCombox(form){
		initCombobox(form,'status','${pageContext.request.contextPath}/dic!combox.do?ctype=PUBSTATUS');
		initCombobox(form,'noticeType','${pageContext.request.contextPath}/dic!combox.do?ctype=NOTICETYPE');
	}
	var p;
	var datagrid;
	$(function(){
		initCombox("searchForm"); 
		initCombox("noticeDatagrid");
		initCombox("notice_queryForm");
		datagrid = $('#noticeDatagrid').datagrid({
			url:'${pageContext.request.contextPath}/notice!datagrid.do',
        	width: 'auto',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			idField:'id',
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
				{field:'id',title:'ID',width:100,align:'center',checkbox : true},
				{field:'title',title:'公告标题',width:300,align:'center'}
				]],
			columns:[[
				//{field:'content',title:'公告内容',width:100,align:'center'},
				{field:'pubUnitName',title:'发布单位',width:100,align:'center'},
				{field:'replyName',title:'是否回复',width:100,align:'center'},
				{field:'creatDate',title:'创建日期',width:100,align:'center',
					formatter: function(value,row,index){
						if (value){
							return value.substring(0,19);
						} else {
							return value;
						}
					}
				},
				{field:'pubDate',title:'发布日期',width:100,align:'center',
					formatter: function(value,row,index){
						if (value){
							return value.substring(0,19);
						} else {
							return value;
						}
					}	
				},
				{field:'validDate',title:'有效日期',width:100,align:'center',
					formatter: function(value,row,index){
						if (value){
							return value.substring(0,19);
						} else {
							return value;
						}
					}	
				},
				{field:'statusName',title:'状态',width:100,align:'center'},
				{field:'noticeTypeName',title:'公告类型',width:100,align:'center'},
				{field:'atts',title:'附件数',width:100,align:'center',
					formatter: function(value,row,index){
						if (value){
							var attArray = value.split(",");//以逗号作为分隔字符串
							return attArray.length;
						} else {
							return value;
						}
					}		
				},
				{field:'memo',title:'备注',width:100,align:'center',hidden:true}
    		]],   
			toolbar : [
			{
				text : '查询',
				iconCls : 'icon-search',
				handler : function() {
					$('#win_notice_query').window('open');
				}
			}, '-', {
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					remove();
				}
			}, '-', {
				text : '修改',
				iconCls : 'icon-edit',
				handler : function() {
					edit();
				}
			}, '-', {
				text : '发布',
				iconCls : 'icon-enable',
				handler : function() {
					pub();
				}
			}, '-', {
				text : '取消发布',
				iconCls : 'icon-stop',
				handler : function() {
					cancelPub();
				}
			}, '-', {
				text : '取消选中',
				iconCls : 'icon-undo',
				handler : function() {
					datagrid.datagrid('clearChecked');
					datagrid.datagrid('clearSelections');
					datagrid.datagrid('unselectAll');
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
					title : '公告详情',
					href : '${pageContext.request.contextPath}/notice!get.do?id='+rows[0].id,
					maximized:true,
					maximizable:true,
					width : 300,
					height : 200,
					onLoad : function() {
						initCombox('noticeViewForm');
				
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
	//修改 
	function edit() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			//是否可有进行修改操作
			var canDel=true;
			var yfb='<%=Constants.CODE_TYPE_PUBSTATUS_YES%>';
			if(rows[0].status==yfb ) canDel=false;
			if(canDel) {
				var p = dj.dialog({
					title : ' 公告修改 ',
					href : '${pageContext.request.contextPath}/notice!noticeEdit.do?id='+rows[0].id,
					maximized:true,
					maximizable:true,
					width : 800,
					height : 600,
					buttons : [ {
						text : ' 提交修改 ',
						iconCls : 'icon-edit',
						handler : function() {
							var attNames=[];
					    	 //var aNames=[];
					    	 //var aTypes=[];
					    	 //var attNos=[]
					    	 var FileCount=parseInt($("#FileCount").val(),10);
					    	 for(var i=1;i<=FileCount;i++){
					    		  //attNos.push($('#no'+i).val());
								  attNames.push($('#f'+i).val());
						   		  //aNames.push($('#n'+i).val());
						   		 // aTypes.push($('#t'+i) .val());
					    	 }
					    	//$('#attNos').val(attNos.join(','));
					    	$('#attNames').val(attNames.join(','));
							//$('#aNames').val(aNames.join(','));
							//$('#aTypes').val(aTypes.join(','));
							
							var f = p.find('form');
							f.form('submit', {
								url : '${pageContext.request.contextPath}/notice!edit.do',
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
					} ,{ 
						text: ' 取 消 ', 
						iconCls:'icon-cancel',
						handler: function() { 
							p.dialog('close'); 
						} 
					}
					]
				//	onLoad : function() {
				//		initCombox('noticeEditForm');
				//		var f = p.find('form');
				//		f.form('load', {
				//			checkOutEndTime : rows[0].checkOutEndTime,
				//		});
				//	}
				});
			} else {
				parent.dj.messagerAlert('提示', '只能修改未发布的公告公告！', 'error');
			}
		} else if (rows.length > 1) {
			parent.dj.messagerAlert('提示', '同一时间只能编辑一条记录！', 'error');
		} else {
			parent.dj.messagerAlert('提示', '请选择要编辑的记录！', 'error');
		}
	}

	//删除
	function remove() {
		var rows = datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			//是否可有进行删除操作
			var canDel=true;
			var wfb='<%=Constants.CODE_TYPE_PUBSTATUS_YES%>';
			for ( var h = 0; h < rows.length; h++) {
				if(rows[h].status==wfb ) canDel=false;
			}
			if(canDel) {
			parent.dj.messagerConfirm('请确认', '您要删除当前所选项目？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/notice!delete.do',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(d) {
							datagrid.datagrid('load');
							datagrid.datagrid('unselectAll');
							parent.dj.messagerShow({
								title : '提示',
								msg : d.msg
							});
						}
					});
				}
			});
			} else {
				parent.dj.messagerAlert('提示', '只能删除未发布的公告，请重新勾选要删除的公告！', 'error');
			}
		} else {
			parent.dj.messagerAlert('提示', '请勾选要删除的记录！', 'error');
		}
	}
	//发布公告处理
	function pub(){
		var rows = datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			parent.dj.messagerConfirm('请确认', '您要发布当前所选公告？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/notice!pub.do',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(d) {
							datagrid.datagrid('load');
							datagrid.datagrid('unselectAll');
							parent.dj.messagerShow({
								title : '提示',
								msg : d.msg
							});
						}
					});
				}
			});
		} else {
			parent.dj.messagerAlert('提示', '请勾选要发布的公告！', 'error');
		}
	}
	//取消发布公告处理
	function cancelPub(){
		var rows = datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			parent.dj.messagerConfirm('请确认', '您要取消发布当前所选公告？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/notice!cancelPub.do',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(d) {
							datagrid.datagrid('load');
							datagrid.datagrid('unselectAll');
							parent.dj.messagerShow({
								title : '提示',
								msg : d.msg
							});
						}
					});
				}
			});
		} else {
			parent.dj.messagerAlert('提示', '请勾选要取消发布的公告！', 'error');
		}
	}
	//查询
	function _search() {
		datagrid.datagrid('load', dj.serializeObject($('#notice_queryForm')));
		 $('#win_notice_query').window('close');
	}
	
	function cleanSearch() {
		datagrid.datagrid('load', {});
		$('#notice_queryForm input').val('');
		$('#statusId').combobox('setValue', '');
		$('#noticeTypeId').combobox('setValue', '');
	}
</script>
</head>
<body class="easyui-layout" data-options="fit:true">
	<!-- 表格 -->
	<div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="noticeDatagrid"></table>
	</div>
	<div id="win_notice_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:500px;height:274px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="notice_queryForm">
				<input type="hidden" name="filingFlg" id="filingFlgId"/>
				<table id="poll_queryTable">
					<tr height="25">
						<td nowrap>公告标题：</td>
						<td colspan="3"><input type="text" class="easyui-validatebox"  name="title" style="width:300px;"></td>
					</tr>				
					<tr height="25">
						<td nowrap>公告内容：</td>
						<td colspan="3"><input type="text" class="easyui-validatebox"  name="content" style="width:300px;"></td>
					</tr>
					<tr height="25">
						<td nowrap>发布单位 ：</td>
						<td colspan="3"> <input type="text" class="easyui-validatebox"  name="pubUnitName" style="width:300px;"></td>
					</tr>
					<tr height="25">
						<td nowrap>公告类型 ：</td>
						<td> <input id="noticeTypeId" panelHeight="100px" name="noticeType" style="width:125px;"></td>
						<td nowrap>状态 ：</td>
						<td> <input id="statusId" panelHeight="100px" name="status"  style="width:125px;"></td>
					</tr>
					<tr height="25">
						<td nowrap>创建日期 ：</td>
						<td colspan="3">
							<input id="creatDateStartId" type="text" class="easyui-datebox" name="creatDateStart" data-options="required:false,showSeconds:false" style="width:142px;"></input> 
		 	              	~ 
			              	<input id="creatDateEndId" type="text" class="easyui-datebox" name="creatDateEnd" data-options="required:false,showSeconds:false" style="width:142px;"></input>  
						</td>
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
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#win_notice_query').window('close');">取消</a>
			</div>
		</div>
	</div>
</body>
</html>