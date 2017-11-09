<%@page import="com.tlzn.util.base.Constants"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	function initCombox(form){
		initCombobox(form,'courseAtt','${pageContext.request.contextPath}/dic!combox.do?ctype=courseAtt');
	}
	var p;
	var datagrid;
	$(function(){
		initCombox("course_queryForm");
		datagrid = $('#courseDatagrid').datagrid({
			url:'${pageContext.request.contextPath}/course!datagrid.do',
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
			fitColumns :false,
			checkOnSelect:false,
			selectOnCheck:false,
			frozenColumns:[[
				{field:'id',title:'ID',width:100,align:'center',checkbox : true},
				{field:'courseId',title:'课程号',width:100,align:'center',
					formatter: function(value,row,index){
						return row.id;
					}
				},
				{field:'sortNo',title:'课程序号',width:100,align:'center'},
				{field:'name',title:'课程名称',width:300,align:'center'},
				{field:'enName',title:'课程英文名称',width:300,align:'center'}
				]],
			columns:[[
				{field:'credit',title:'课程学分',width:100,align:'center'},
				{field:'courseAttName',title:'课程属性',width:150,align:'center'}
// 				{field:'teachMeth',title:'教学方法',width:150,align:'center'},
// 				{field:'teachBook',title:'使用教材',width:100,align:'center'},
// 				{field:'asMethAnReq',title:'考核方式及其要求',width:100,align:'center'},
// 				{field:'comment',title:'备注',width:150,align:'center'},
			]], 
			toolbar : '#tb'
		});
	});
	//查看
	function view() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			var p = dj.dialog({
				title : '课程详情',
				href : '${pageContext.request.contextPath}/course!courseView.do?id='+rows[0].id,
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
			var p = dj.dialog({
			title : '新增课程',
			href : '${pageContext.request.contextPath}/course!courseAdd.do',
			maximized:true,
			width : 700,
			height : 420,
			iconCls:'icon-save',
			buttons : [ {
					text : ' 提交 ',
					iconCls:'icon-ok',
					handler : function() {
						var f = p.find('form');
						f.form('submit', {
							url : '${pageContext.request.contextPath}/course!add.do',
							success : function(d) {
								var json = $.parseJSON(d);
								if (json.success) {
									datagrid.datagrid('reload');
									p.dialog('close');
								}
								$.messager.alert('提示', json.msg, 'info');
							}
						});
					}
				},{
					text: ' 取 消 ', 
					iconCls:'icon-cancel',
					handler: function() { 
						p.dialog('close'); 
					} 
					}],
				onLoad : function() {
					initCombox('addForm');
				}
		});
	}
	//修改 
	function edit() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			var p = dj.dialog({
				title : '编辑课程信息',
				href : '${pageContext.request.contextPath}/course!courseEdit.do?id='+rows[0].id,
				width : 700,
				height : 420,
				maximized : true,
				buttons : [ {
					text : '提交修改',
					iconCls:'icon-ok',
					handler : function() {
							var f = p.find('form');
							f.form('submit', {
								url : '${pageContext.request.contextPath}/course!edit.do',
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
					text: '关闭', 
					iconCls:'icon-cancel',
					handler: function() { 
						p.dialog('close'); 
					} 
				}
				],
				onLoad : function() {
					initCombox('addForm');
				}
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
						url : '${pageContext.request.contextPath}/course!delete.do',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(d) {
							datagrid.datagrid('load');
							datagrid.datagrid('unselectAll');
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
	//发布课程处理
	function pub(){
		var rows = datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			parent.dj.messagerConfirm('请确认', '您要发布当前所选课程？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/course!pub.do',
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
			parent.dj.messagerAlert('提示', '请勾选要发布的课程！', 'error');
		}
	}
	//取消发布课程处理
	function cancelPub(){
		var rows = datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			parent.dj.messagerConfirm('请确认', '您要取消发布当前所选课程？', function(r) {
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
			parent.dj.messagerAlert('提示', '请勾选要取消发布的课程！', 'error');
		}
	}
	
	//下载履职表
	function downWord(){
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			$('#downWordBtId').linkbutton('disable');
			$('#downWordBtId .l-btn-text').html('文件生成中,请稍候...');
			var courseNo=rows[0].courseNo;
// 			var secondaryid=$("#secondaryID").val();
// 			var year=$("#yearID").val();
			//生成课程基本信息表
			$.ajax({
				url:'${pageContext.request.contextPath}/word!doNotNeedAuth_courseBaseInfoDownload.do?courseNo='+courseNo,
					success : function(d) {
					var json = $.parseJSON(d);
					if (json.success) {
							//打开
							$.ajax({
								url :"${pageContext.request.contextPath}/word!doNotNeedAuth_isfile.do?fileName=courseBaseinfo_"+courseNo+".doc",
								success : function(d) {
									var json = $.parseJSON(d);
									if (json.success) {
										$('#downWordBtId').linkbutton('enable');
										$('#downWordBtId .l-btn-text').html('下载履职表');
										window.location.href="${pageContext.request.contextPath}/wordfile/courseBaseinfo_"+courseNo+".doc";
									}else{
										dj.messagerShow({
											msg : json.msg,
											title : '提示'
										});
									}
								}
							});
					}else{
						dj.messagerShow({
							msg : json.msg,
							title : '提示'
						});
					}
				}
			});
		} else if (rows.length > 1) {
			dj.messagerAlert('提示', '同一时间只能下载一位委员的履职表！', 'error');
		} else {
			dj.messagerAlert('提示', '请选择要下载履职表的委员！', 'error');
		}

	}
	
	//查询
	function _search() {
		datagrid.datagrid('load', dj.serializeObject($('#course_queryForm')));
		 $('#win_notice_query').window('close');
	}
	
	function cleanSearch() {
		datagrid.datagrid('load', {});
		$('#course_queryForm input').val('');
		$('#courseAtt').combobox('setValue', '');
	}
	
	$.extend($.fn.validatebox.defaults.rules, {  
		sel: {  
			validator: function(value){ 
				return value != '请选择...';  
			},  
			message: '此项必须选择！'  
		}  
	}); 
</script>
</head>
<body class="easyui-layout" data-options="fit:true">
	<!-- 表格 -->
	<div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="courseDatagrid"></table>
	</div>
	<div id="win_notice_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:500px;height:430px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="course_queryForm">
				<table id="poll_queryTable">
					<tr height="30">
						<td nowrap>课程号：</td>
						<td colspan="3"><input type="text" class="easyui-validatebox"  name="id" style="width:300px;"></td>
					</tr>
					<tr height="30">
						<td nowrap>课程名称：</td>
						<td colspan="3"><input type="text" class="easyui-validatebox"  name="name" style="width:300px;"></td>
					</tr>				
					<tr height="30">
						<td nowrap>课程英文名称：</td>
						<td colspan="3">
							<input type="text" class="easyui-validatebox"  name="enName" style="width:302px;">
						</td>
					</tr>
					<tr height="30">
						<td nowrap>课程序号：</td>
						<td colspan="3">
							<input type="text" class="easyui-validatebox"  name="sortNo" style="width:300px;">
						</td>
					</tr>
					<tr height="30">
						<td nowrap>课程学分：</td>
						<td colspan="3">
							<input type="text" class="easyui-validatebox"  name="credit" style="width:300px;">
						</td>
					</tr>
					<tr height="30">
						<td nowrap>课程属性 ：</td>
						<td colspan="3"> <input id="courseAtt" name="courseAtt" style="width:302px;" panelHeight="130px" ></td>
					</tr>
					<tr height="30">
						<td nowrap>教学方法：</td>
						<td colspan="3"><input type="text" class="easyui-validatebox"  name="teachMeth" style="width:300px;"></td>
					</tr>
					<tr height="30">
						<td nowrap>使用教材：</td>
						<td colspan="3"><input type="text" class="easyui-validatebox"  name="teachBook" style="width:300px;"></td>
					</tr>
					<tr height="30">
						<td nowrap>考核方式及其要求：</td>
						<td colspan="3"><input type="text" class="easyui-validatebox"  name="asMethAnReq" style="width:300px;"></td>
					</tr>	
					<tr height="30">
						<td nowrap>备注：</td>
						<td colspan="3"><input type="text" class="easyui-validatebox"  name="comment" style="width:300px;"></td>
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
	<div id="tb">
			<div style="margin-bottom:1px">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="$('#win_notice_query').window('open');">查询</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="appand();">新增</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit();">修改</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="remove();">删除</a>
<!-- 					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-enable" plain="true" onclick="pub();">发布</a> -->
<!-- 					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-stop" plain="true" onclick="cancelPub();">取消发布</a> -->
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="datagrid.datagrid('clearChecked');datagrid.datagrid('clearSelections');datagrid.datagrid('unselectAll');">取消选中</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-info" plain="true" onclick="view();">查看详细</a>
			</div>
	</div>
</body>
</html>