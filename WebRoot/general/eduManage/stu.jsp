<%@page import="com.tlzn.util.base.Constants"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	function initCombox(form){
		initCombobox(form,'dept','${pageContext.request.contextPath}/dic!combox.do?ctype=yxb');
		initCombobox(form,'major','${pageContext.request.contextPath}/dic!combox.do?ctype=major');
		initCombobox(form,'enSour','${pageContext.request.contextPath}/dic!combox.do?ctype=enSour');
		initCombobox(form,'priPro','${pageContext.request.contextPath}/dic!combox.do?ctype=priPro');
	}
	var p;
	var datagrid;
	$(function(){
		initCombox("stu_queryForm");
		datagrid = $('#stuDatagrid').datagrid({
			url:'${pageContext.request.contextPath}/stu!datagrid.do',
        	width: 'auto',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			idField:'stuNo',
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
				{field:'stuNo',title:'学号',width:100,align:'center'},
				{field:'name',title:'学生姓名',width:100,align:'center'},
				{field:'sex',title:'性别',width:50,align:'center'},
				]],
			columns:[[
				{field:'dept',title:'院、系、部',width:100,align:'center'},
				{field:'major',title:'专业',width:150,align:'center'},
				{field:'className',title:'班级名称',width:150,align:'center'},
				{field:'inTime',title:'入学时间',width:100,align:'center'},
				{field:'enSour',title:'入学来源',width:100,align:'center'},
				{field:'priPro',title:'主要问题',width:200,align:'center'},
				{field:'planAfterGrad',title:'毕业以后的打算',width:100,align:'center'},
				{field:'identCardNo',title:'身份证号码',width:150,align:'center'},
				{field:'special',title:'特长',width:100,align:'center'},
				{field:'compuLevel',title:'计算机水平',width:100,align:'center'},
				{field:'foreignType',title:'所学外语语种',width:100,align:'center'},
				{field:'interestAndHobby',title:'兴趣爱好',width:150,align:'center'},
			]], 
			toolbar : '#tb'
		});
	});
	//查看
	function view() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			var p = dj.dialog({
				title : '学生详情',
				href : '${pageContext.request.contextPath}/stu!stuView.do?stuNo='+rows[0].stuNo,
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
			title : '新增学生',
			href : '${pageContext.request.contextPath}/stu!stuAdd.do',
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
							url : '${pageContext.request.contextPath}/stu!add.do',
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
				title : '编辑学生信息',
				href : '${pageContext.request.contextPath}/stu!stuEdit.do?stuNo='+rows[0].stuNo,
				width : 700,
				height : 420,
				maximized : true,
				buttons : [ {
					text : '提交修改',
					iconCls:'icon-ok',
					handler : function() {
							var f = p.find('form');
							f.form('submit', {
								url : '${pageContext.request.contextPath}/stu!edit.do',
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
						ids.push(rows[i].stuNo);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/stu!delete.do',
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
	//发布学生处理
	function pub(){
		var rows = datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			parent.dj.messagerConfirm('请确认', '您要发布当前所选学生？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/stu!pub.do',
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
			parent.dj.messagerAlert('提示', '请勾选要发布的学生！', 'error');
		}
	}
	//取消发布学生处理
	function cancelPub(){
		var rows = datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			parent.dj.messagerConfirm('请确认', '您要取消发布当前所选学生？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/stu!cancelPub.do',
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
			parent.dj.messagerAlert('提示', '请勾选要取消发布的学生！', 'error');
		}
	}
	
	//下载学生基本信息表
	function downWord(){
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			$('#downWordBtId').linkbutton('disable');
			$('#downWordBtId .l-btn-text').html('文件生成中,请稍候...');
			var stuNo=rows[0].stuNo;
// 			var secondaryid=$("#secondaryID").val();
// 			var year=$("#yearID").val();
			//生成学生基本信息表
			$.ajax({
				url:'${pageContext.request.contextPath}/word!doNotNeedAuth_stuBaseInfoDownload.do?stuNo='+stuNo,
					success : function(d) {
					var json = $.parseJSON(d);
					if (json.success) {
							//打开
							$.ajax({
								url :"${pageContext.request.contextPath}/word!doNotNeedAuth_isfile.do?fileName=stuBaseinfo_"+stuNo+".doc",
								success : function(d) {
									var json = $.parseJSON(d);
									if (json.success) {
										$('#downWordBtId').linkbutton('enable');
										$('#downWordBtId .l-btn-text').html('下载学生基本信息表');
										window.location.href="${pageContext.request.contextPath}/wordfile/stuBaseinfo_"+stuNo+".doc";
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
		datagrid.datagrid('load', dj.serializeObject($('#stu_queryForm')));
		 $('#win_stu_query').window('close');
	}
	
	function cleanSearch() {
		datagrid.datagrid('load', {});
		$('#stu_queryForm input').val('');
		$('#statusId').combobox('setValue', '');
		$('#noticeTypeId').combobox('setValue', '');
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
		<table id="stuDatagrid"></table>
	</div>
	<div id="win_stu_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:450px;height:375px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="stu_queryForm">
				<input type="hidden" name="filingFlg" id="filingFlgId"/>
				<table id="poll_queryTable">
					<tr height="30">
						<td nowrap>学生姓名：</td>
						<td colspan="3"><input type="text" class="easyui-validatebox"  name="name" style="width:300px;"></td>
					</tr>				
					<tr height="30">
						<td nowrap>院、系、部：</td>
						<td colspan="3">
							<input type="text" class="easyui-validatebox"  name="dept" style="width:302px;">
						</td>
					</tr>
					<tr height="30">
						<td nowrap>专业：</td>
						<td colspan="3">
							<input name="major"  style="width:302px;" panelHeight="130px" >
						</td>
					</tr>
					<tr height="30">
						<td nowrap>班级名称：</td>
						<td colspan="3"><input type="text" class="easyui-validatebox"  name="className" style="width:300px;"></td>
					</tr>	
					<tr height="30">
						<td nowrap>入学来源 ：</td>
						<td colspan="3"> <input name="enSour" style="width:302px;" panelHeight="130px" ></td>
					</tr>
					<tr height="30">
						<td nowrap>主要问题 ：</td>
						<td colspan="3"> <input name="priPro"  style="width:302px;" panelHeight="130px" ></td>
					</tr>
					<tr height="30">
						<td nowrap>入学时间 ：</td>
						<td colspan="3">
							<input id="inSTimeId" type="text" class="easyui-datebox" name="inSTime" data-options="required:false,showSeconds:false" style="width:142px;"></input> 
		 	              	~ 
			              	<input id="inETimeId" type="text" class="easyui-datebox" name="inETime" data-options="required:false,showSeconds:false" style="width:142px;"></input>  
						</td>
					</tr>
					<tr height="30">
						<td nowrap>备注：</td>
						<td colspan="3"><input type="text" class="easyui-validatebox"  name="remark" style="width:300px;"></td>
					</tr>	
				</table>
				</form>
				</center>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;height:40px">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)" onclick="_search()">查询</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)" onclick="cleanSearch()">重置</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#win_stu_query').window('close');">取消</a>
			</div>
		</div>
	</div>
	<div id="tb">
			<div style="margin-bottom:1px">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="$('#win_stu_query').window('open');">查询</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="appand();">新增</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit();">修改</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="remove();">删除</a>
<!-- 					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-enable" plain="true" onclick="pub();">发布</a> -->
<!-- 					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-stop" plain="true" onclick="cancelPub();">取消发布</a> -->
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="datagrid.datagrid('clearChecked');datagrid.datagrid('clearSelections');datagrid.datagrid('unselectAll');">取消选中</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-info" plain="true" onclick="view();">查看详细</a>
					<a href="javascript:void(0)" id="downWordBtId" class="easyui-linkbutton" iconCls="icon-down" plain="true" onclick="downWord();">下载学生基本信息表</a>
			</div>
	</div>
</body>
</html>