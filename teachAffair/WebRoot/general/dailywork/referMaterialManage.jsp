<%@page import="com.tlzn.util.base.Constants"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	function initCombox(form){
		initCombobox(form,'datatype','${pageContext.request.contextPath}/dic!combox.do?ctype=MATERIALTYPE');
		initCombobox(form,'status','${pageContext.request.contextPath}/dic!combox.do?ctype=PUBSTATUS');
		$('#referMaterialAddForm input[name=datatype]').combobox({
			url : '${pageContext.request.contextPath}/dic!combox.do?ctype=MATERIALTYPE',
			valueField:'cvalue', 
			panelHeight:'100',
	        textField:'ckey',
	        required:true,
	        validType:'sel'
		});
	}
	var p;
	var datagrid;
	$(function(){
		initCombox("searchForm"); 
		initCombox("referMaterialDatagrid");
		initCombox("referMaterial_queryForm");
		datagrid = $('#noticeDatagrid').datagrid({
			url:'${pageContext.request.contextPath}/referMaterial!datagrid.do?fromtype=2',
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
				//{field:'content',title:'会议参阅资料内容',width:100,align:'center'},
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
					$('#win_notice_query').window('open');
				}
			}, '-', {
				text : '新增',
				iconCls : 'icon-add',
				handler : function() {
					appand();
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
					title : '参阅资料详情',
					href : '${pageContext.request.contextPath}/referMaterial!get.do?dataId='+rows[0].dataId,
					maximized:true,
					maximizable:true,
					width : 800,
					height : 600,
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
		//新增
		function appand() {
				p = dj.dialog({
				title : '参阅资料新增',
				href : '${pageContext.request.contextPath}/referMaterial!referMaterialAdd.do?fromtype=2',
				maximized:true,
				maximizable:true,
				width : 800,
				height : 500,
				iconCls:'icon-save',
				buttons : [ {
						text : ' 保存 ',
						iconCls:'icon-ok',
						handler : function() {
							saveFile();
						}
					},{
						text : ' 发布 ',
						iconCls:'icon-submit',
						handler : function() {
							submitFile();
						}
					},{ 
						text: ' 取 消 ', 
						iconCls:'icon-cancel',
						handler: function() { 
							p.dialog('close'); 
						} 
						}],
					onLoad : function() {
						var f = p.find('form');
						$(document).ready(function() {
							initCombox("referMaterialAddForm");
						});
					}
			});
		}
		//保存参阅资料
		function saveFile(){
			var f =$("#referMaterialAddForm");
			$("#content").val(UE.getEditor('editor').getContent()); 
			add();
			f.form('submit', {
				url : '${pageContext.request.contextPath}/referMaterial!save.do',
				success : function(d) {
					var json = $.parseJSON(d);
					if (json.success) {
						$("#idId").val(json.obj);
						dj.messagerShow({
						msg : json.msg,
						title : '提示'
						});
					}
				}

			});
		}

		//提交参阅资料
		function submitFile(){
					$("#content").val(UE.getEditor('editor').getContent());
					add();
					var f =$("#referMaterialAddForm"); 
					f.form('submit', {
						url : '${pageContext.request.contextPath}/referMaterial!upDateOrAdd.do',
						success : function(d) {
							var json = $.parseJSON(d);
							if (json.success) {
								parent.dj.messagerConfirm('操作提示','发布成功！您是否继续发布新参阅资料?',function(ok){  
									if (ok){
										//window.location.href="${pageContext.request.contextPath}/notice!noticeAdd.do";
										document.getElementById("referMaterialAddForm").reset();
										$('#fromtypeid').combobox('setValue', 2);
									}else{
										datagrid.datagrid('reload');
										p.dialog('close');
									}
								});

							}
						}
				
					});
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
					title : ' 参阅资料修改 ',
					href : '${pageContext.request.contextPath}/referMaterial!referMaterialEdit.do?dataId='+rows[0].dataId,
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
								url : '${pageContext.request.contextPath}/referMaterial!edit.do',
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
				parent.dj.messagerAlert('提示', '只能修改未发布的参考资料！', 'error');
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
							ids.push(rows[i].dataId);
						}
						$.ajax({
							url : '${pageContext.request.contextPath}/referMaterial!delete.do',
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
				parent.dj.messagerAlert('提示', '只能删除未发布的参考资料，请重新勾选要删除的参考资料！', 'error');
			}
		} else {
			parent.dj.messagerAlert('提示', '请勾选要删除的记录！', 'error');
		}
	}
	//发布参阅资料处理
	function pub(){
		var rows = datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			parent.dj.messagerConfirm('请确认', '您要发布当前所选参阅资料？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].dataId);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/referMaterial!pub.do',
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
			parent.dj.messagerAlert('提示', '请勾选要发布的参阅资料！', 'error');
		}
	}
	//取消发布会议参阅资料处理
	function cancelPub(){
		var rows = datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			parent.dj.messagerConfirm('请确认', '您要取消发布当前所选参阅资料？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].dataId);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/referMaterial!cancelPub.do',
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
			parent.dj.messagerAlert('提示', '请勾选要取消发布的参阅资料！', 'error');
		}
	}
	//查询
	function _search() {
		datagrid.datagrid('load', dj.serializeObject($('#referMaterial_queryForm')));
		 $('#win_notice_query').window('close');
	}
	
	function cleanSearch() {
		datagrid.datagrid('load', {});
		$('#referMaterial_queryForm input').val('');
		$('#statusId').combobox('setValue', '');
		$('#datatypeId').combobox('setValue', '');
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
				<form id="referMaterial_queryForm">
				<input type="hidden" name="filingFlg" id="filingFlgId"/>
				<table id="poll_queryTable">
					<tr height="25">
						<td nowrap>参阅资料类型：</td>
						<td colspan="3"><input id="datatypeId" name="datatype" style="width:300px;"></td>
					</tr>
					<tr height="25">
						<td nowrap>参阅资料标题：</td>
						<td colspan="3"><input type="text" class="easyui-validatebox"  name="title" style="width:300px;"></td>
					</tr>				
					<tr height="25">
						<td nowrap>参阅资料内容：</td>
						<td colspan="3"><input type="text" class="easyui-validatebox"  name="content" style="width:300px;"></td>
					</tr>
					<tr height="25">
						<td nowrap>发布机构 ：</td>
						<td colspan="3"> <input type="text" class="easyui-validatebox"  name="depName" style="width:300px;"></td>
					</tr>
					<tr height="25">
						<td nowrap>状态：</td>
						<td colspan="3"><input id="statusId" name="status" style="width:300px;"></td>
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