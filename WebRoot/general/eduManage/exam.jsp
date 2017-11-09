<%@page import="com.tlzn.util.base.Constants"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	function initCombox(form){
		initCombobox(form,'isLate','${pageContext.request.contextPath}/dic!combox.do?ctype=YESNO');
		initCombobox(form,'isAbsent','${pageContext.request.contextPath}/dic!combox.do?ctype=YESNO');
		initCombobox(form,'isCribbing','${pageContext.request.contextPath}/dic!combox.do?ctype=YESNO');
		initCombobox(form,'resultType','${pageContext.request.contextPath}/dic!combox.do?ctype=RESULTTYPE');
	}
	var p;
	var datagrid;
	$(function(){
		initCombox("searchForm"); 
		initCombox("noticeDatagrid");
		initCombox("notice_queryForm");
		datagrid = $('#noticeDatagrid').datagrid({
			url:'${pageContext.request.contextPath}/exam!datagrid.do',
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
				{field:'examName',title:'考试名称',width:200,align:'center'},
				{field:'year',title:'考试年度',width:80,align:'center'},
				{field:'term',title:'学期',width:40,align:'center'},
				{field:'courseName',title:'考试课程名称',width:200,align:'center'}
			]],  
			columns:[[
				{field:'inviTeacherId',title:'监考教师ID',width:80,align:'center'},
				{field:'resultTypeString',title:'成绩类型',width:80,align:'center'},
				{field:'examSDateString',title:'考试开始日期',width:150,align:'center',
					formatter: function(value,row,index){
						if (value){
							return value.substring(0,19);
						} else {
							return value;
						}
					}
				},
				{field:'examEDateString',title:'考试结束日期',width:150,align:'center',
					formatter: function(value,row,index){
						if (value){
							return value.substring(0,19);
						} else {
							return value;
						}
					}
				}
    		]],   
			toolbar:[
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
				text : '修改',
				iconCls : 'icon-edit',
				handler : function() {
					edit();
				}
			}, '-', {
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					remove();
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
				title : '考试详情',
				href : '${pageContext.request.contextPath}/notice!get.do?id='+rows[0].id,
				maximized:true,
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
//新增
	function appand() {
				p = dj.dialog({
				title : '新增考试',
				href : '${pageContext.request.contextPath}/exam!examAdd.do',
				maximized:true,
				width : 800,
				height : 500,
				iconCls:'icon-save',
				buttons : [ {
						text : '提交 ',
						iconCls:'icon-submit',
						handler : function() {
							submitSpeech();
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
							initCombox("addForm");
							//发言人信息列表
							gettjzgrid();
						});
					}
			});
	}
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
					title : ' 考试修改 ',
					href : '${pageContext.request.contextPath}/notice!noticeEdit.do?id='+rows[0].id,
					maximized:true,
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
				parent.dj.messagerAlert('提示', '只能修改未发布的考试公告！', 'error');
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
				parent.dj.messagerAlert('提示', '只能删除未发布的考试公告，请重新勾选要删除的考试公告！', 'error');
			}
		} else {
			parent.dj.messagerAlert('提示', '请勾选要删除的记录！', 'error');
		}
	}
	//发布考试处理
	function pub(){
		var rows = datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			parent.dj.messagerConfirm('请确认', '您要发布当前所选考试？', function(r) {
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
			parent.dj.messagerAlert('提示', '请勾选要发布的考试！', 'error');
		}
	}
	//取消发布考试处理
	function cancelPub(){
		var rows = datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			parent.dj.messagerConfirm('请确认', '您要取消发布当前所选考试？', function(r) {
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
			parent.dj.messagerAlert('提示', '请勾选要取消发布的考试！', 'error');
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
						<td nowrap>考试名称：</td>
						<td colspan="3"><input type="text" class="easyui-validatebox"  name="examName" style="width:300px;"></td>
					</tr>				
					<tr height="25">
						<td nowrap>考试年度：</td>
						<td colspan="3"><input type="text" class="easyui-validatebox"  name="year" style="width:300px;"></td>
					</tr>
					<tr height="25">
						<td nowrap>考试科目 ：</td>
						<td> <input id="examSubjId" panelHeight="100px" name="examSubj" style="width:125px;"></td>
						<td nowrap>成绩类型 ：</td>
						<td> <input id="resultTypeId" panelHeight="100px" name="resultType"  style="width:125px;"></td>
					</tr>
					<tr height="25">
						<td nowrap>考试开始日期 ：</td>
						<td colspan="3">
							<input id="examSDateStartId" type="text" class="easyui-datebox" name="examSDateStart" data-options="required:false,showSeconds:false" style="width:142px;"></input> 
		 	              	~ 
			              	<input id="examSDateEndId" type="text" class="easyui-datebox" name="examSDateEnd" data-options="required:false,showSeconds:false" style="width:142px;"></input>  
						</td>
					</tr>
					<tr height="25">
						<td nowrap>考试结束日期 ：</td>
						<td colspan="3">
							<input id="examEDateStartId" type="text" class="easyui-datebox" name="examEDateStart" data-options="required:false,showSeconds:false" style="width:142px;"></input> 
		 	              	~ 
			              	<input id="examEDateEndId" type="text" class="easyui-datebox" name="examEDateEnd" data-options="required:false,showSeconds:false" style="width:142px;"></input>  
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