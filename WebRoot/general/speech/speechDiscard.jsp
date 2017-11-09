<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	var p;
	var datagrid;
	function initCombox(form){
		initCombobox(form,'status','${pageContext.request.contextPath}/dic!combox.do?ctype=PUBSTATUS');
		initCombobox(form,'noticeType','${pageContext.request.contextPath}/dic!combox.do?ctype=NOTICETYPE');
	}
	$(function(){
		datagrid = $('#noticeDatagrid').datagrid({
			//url:'${pageContext.request.contextPath}/notice!datagrid.do',
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
			frozenColumns:[[
				{field:'id',title:'ID',width:100,align:'center',checkbox : true},
				{field:'title',title:'发言主题',width:400,align:'center'}
				]],
			columns:[[
				//{field:'content',title:'通知内容',width:100,align:'center'},
				{field:'pubUnit',title:'所属会议',width:100,align:'center'},
				{field:'realname',title:'发言人',width:100,align:'center'},
				{field:'adopt',title:'采用情况',width:100,align:'center'},
				{field:'status',title:'状态',width:100,align:'center'},
				{field:'pubDate',title:'提交日期',width:100,align:'center'},
				{field:'discardType',title:'转弃方向',width:100,align:'center'}
				
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
					pub();
				}
			}, '-', {
				text : '恢复',
				iconCls : 'icon-undo',
				handler : function() {
					cancelPub();
				}
			}, '-',
			{
				text : '查看详细',
				iconCls : 'icon-info',
				handler : function() {
					//view();
				}
			}
			],
			data : [ {
				title:'医疗服务和健康养老结合 养老模式必由发展之路',
				pubUnit : '调研会',
				realname : '杜长宽',
				adopt : '书面发言',
				status:'转弃',
				pubDate : '2016-10-13',
				discardType:"转至社情民意"
			} ]
		});
	});
	
		//查看
		function view() {
			var rows = datagrid.datagrid('getSelections');
			if (rows.length == 1) {
				var p = dj.dialog({
					title : '通知详情',
					href : '${pageContext.request.contextPath}/notice!get.do?id='+rows[0].id,
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
	//修改 
	function edit() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			var p = dj.dialog({
				title : ' 通知修改 ',
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
			parent.dj.messagerAlert('提示', '请勾选要删除的记录！', 'error');
		}
	}
	//发布通知处理
	function pub(){
		var rows = datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			parent.dj.messagerConfirm('请确认', '您要发布当前所选通知？', function(r) {
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
			parent.dj.messagerAlert('提示', '请勾选要发布的通知！', 'error');
		}
	}
	//取消发布通知处理
	function cancelPub(){
		var rows = datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			parent.dj.messagerConfirm('请确认', '您要取消发布当前所选通知？', function(r) {
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
			parent.dj.messagerAlert('提示', '请勾选要取消发布的通知！', 'error');
		}
	}
	//查询
	function _search() {
		datagrid.datagrid('load', dj.serializeObject($('#notice_queryForm')));
		 $('#win_speech_query').window('close');
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
	<div id="win_speech_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:500px;height:274px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="speech_queryForm">
				<input type="hidden" name="filingFlg" id="filingFlgId"/>
				<table id="speech_queryTable">
					<tr height="25">
						<td nowrap>发言主题：</td>
						<td><input type="text" name="pollCode" style="width:280px;padding:2px;border:1px solid #000;"></td>
					</tr>			
					<tr height="25">
						<td nowrap>所属会议：</td>
						<td><input type="text" name="title" style="width:280px;padding:2px;border:1px solid #000;"></td>
					</tr>
					<tr height="25">
						<td nowrap>发言人：</td>
						<td><input type="text" name="title" style="width:280px;padding:2px;border:1px solid #000;"></td>
					</tr>
					<tr height="25">
						<td nowrap>提交时间：</td>
						<td> <input class="easyui-datebox" name="timebeg"   style="width:130px"/>--<input class="easyui-datebox" name="timeend"  style="width:130px"/></td>
					</tr>
					
					<tr height="25">
						<td nowrap>状态：</td>
						<td> <input id="status" panelHeight="100px" name="status" value="请选择..." style="width:280px;padding:2px;border:1px solid #000;"></td>
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