<%@page import="com.tlzn.util.base.Constants"%>
<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript" charset="utf-8">
	var p;
	var professListDatagrid;
	$(function() {
		initCombox("professList_queryForm");
		professListDatagrid = $('#professListDatagrid').datagrid({
			url : '${pageContext.request.contextPath}/profess!datagrid.do?teacherId='+'${teacherId}',
			width : 'auto',
			height : 'auto',
			striped : true,
			nowrap : true,
			rownumbers : true,
			idField : 'id',
			singleSelect : true,
			pagination : true,
			pageSize : 20,
			pageList : [ 20, 50 ],
			pageNumber : 1,
			fit : true,
			fitColumns : false,
			checkOnSelect : false,
			selectOnCheck : false,
			frozenColumns : [ [ {
				field : 'id',
				title : 'ID',
				width : 100,
				align : 'center',
				checkbox : true
			}, {
				field : 'courseId',
				title : '课程号',
				width : 50,
				align : 'center'
			},{
				field : 'courseName',
				title : '课程名称',
				width : 120,
				align : 'center',
			},{
				field : 'courseAttName',
				title : '课程属性',
				width : 80,
				align : 'center',
			}
			]],
			columns : [ [{
				field : 'sortNo',
				title : '课程序号',
				width : 50,
				align : 'center',
			},{
				field : 'credit',
				title : '课程学分',
				width : 60,
				align : 'center',
			},{
				field : 'courseCon',
				title : '课容量',
				width : 80,
				align : 'center',
			},{
				field : 'courseSelNum',
				title : '选课人数',
				width : 80,
				align : 'center',
			},{
				field : 'courseSpareNum',
				title : '课余量',
				width : 80,
				align : 'center',
			},{
				field : 'weeks',
				title : '周次',
				width : 300,
				align : 'center',
			},{
				field : 'week',
				title : '星期',
				width : 200,
				align : 'center',
			},{
				field : 'section',
				title : '节次',
				width : 200,
				align : 'center',
			},{
				field : 'campusName',
				title : '校区',
				width : 120,
				align : 'center',
			},{
				field : 'teachingbuildingName',
				title : '教学楼',
				width : 120,
				align : 'center',
			},{
				field : 'classroomName',
				title : '教室',
				width : 120,
				align : 'center',
			}
			] ],
			toolbar : '#tbList'
		});
	});
	//查看
	/* function view2() {
		var rows = professListDatagrid.datagrid('getSelections');
		if (rows.length == 1) {
			var p = dj
					.dialog({
						title : '授课详情',
						href : '${pageContext.request.contextPath}/profess!professView.do?id='
								+ rows[0].id,
						maximized : true,
						width : 300,
						height : 200,
						onLoad : function() {
							initCombox('noticeViewForm');

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
	} */
	//新增
	function appand2() {
		var p = dj.dialog({
					title : '新增授课',
					href : '${pageContext.request.contextPath}/profess!professAdd.do?teacherId='+'${teacherId}',
					width : 700,
					height : 420,
					maximized : true,
					iconCls : 'icon-save',
					buttons : [
							{
								text : ' 提交 ',
								iconCls : 'icon-ok',
								handler : function() {
									var f = p.find('form');
									f.form('submit',{
										url : '${pageContext.request.contextPath}/profess!add.do',
										success : function(d) {
											var json = $.parseJSON(d);
											if (json.success) {
												professListDatagrid.datagrid('reload');
												p.dialog('close');
											}
											$.messager.alert('提示',json.msg,'info');
										}
									});
								}
							}, {
								text : ' 取 消 ',
								iconCls : 'icon-cancel',
								handler : function() {
									p.dialog('close');
								}
							} ],
					onLoad : function() {
						initCombox('addForm');
					}
				});
	}
	//修改
	function edit2() {
		var rows = professListDatagrid.datagrid('getSelections');
		if (rows.length == 1) {
			var p = dj.dialog({
						title : '修改授课',
						href : '${pageContext.request.contextPath}/profess!professEdit.do?id='+ rows[0].id,
						width : 700,
						height : 420,
						maximized : true,
						buttons : [
								{
									text : '提交修改',
									iconCls : 'icon-ok',
									handler : function() {
										var f = p.find('form');
										f.form('submit',{
												url : '${pageContext.request.contextPath}/profess!edit.do',
												success : function(d) {
													var json = $.parseJSON(d);
													if (json.success) {
														professListDatagrid.datagrid('reload');
														p.dialog('close');
													}
													parent.dj.messagerShow({msg : json.msg,title : '提示'});
												}
											});
									}
								}, {
									text : '关闭',
									iconCls : 'icon-cancel',
									handler : function() {
										p.dialog('close');
									}
								} ],
						onLoad : function() {
							initCombox('editForm');
						}
					});
		} else if (rows.length > 1) {
			parent.dj.messagerAlert('提示', '同一时间只能管理一条授课信息！', 'error');
		} else {
			parent.dj.messagerAlert('提示', '请选择要管理的一条授课信息！', 'error');
		}
	}

	//删除
	function remove2() {
		var rows = professListDatagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			parent.dj
					.messagerConfirm(
							'请确认',
							'您要删除当前所选项目？',
							function(r) {
								if (r) {
									for ( var i = 0; i < rows.length; i++) {
										ids.push(rows[i].id);
									}
									$
											.ajax({
												url : '${pageContext.request.contextPath}/profess!delete.do',
												data : {
													ids : ids.join(',')
												},
												dataType : 'json',
												success : function(d) {
													professListDatagrid.datagrid('load');
													professListDatagrid.datagrid('unselectAll');
													professListDatagrid.datagrid('unselectAll');
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
	//查询
	function _search2() {
		professListDatagrid.datagrid('load', dj.serializeObject($('#professList_queryForm')));
		$('#win_professList_query').window('close');
	}

	function cleanSearch2() {
		professListDatagrid.datagrid('load', {});
		$('#professList_queryForm input').val('');
		$('#sexId').combobox('setValue', '');
		$('#courseAtt').combobox('setValue', '');
	}

	$.extend($.fn.validatebox.defaults.rules, {
		sel : {
			validator : function(value) {
				return value != '请选择...';
			},
			message : '此项必须选择！'
		}
	});
</script>
<div class="easyui-layout" data-options="fit:true">
	<!-- 表格 -->
	<div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="professListDatagrid"></table>
	</div>
	<!-- <div id="win_professList_query" class="easyui-window"
		data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true"
		style="width:500px;height:320px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false"
				style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
					<form id="professList_queryForm">
						<table id="poll_queryTable">
							<tr height="30">
								<td nowrap>教师编号：</td>
								<td colspan="3"><input type="text"
									class="easyui-validatebox" name="teacherId" style="width:300px;">
								</td>
							</tr>
							<tr height="30">
								<td nowrap>教师姓名：</td>
								<td colspan="3"><input type="text" class="easyui-validatebox" name="teacherName" style="width:300px;">
								</td>
							</tr>
							<tr height="30">
								<td nowrap>教师性别：</td>
								<td colspan="3"><input id="sexId" name="sex" style="width:302px;" panelHeight="130px">
								</td>
							</tr> 
							<tr height="30">
								<td nowrap>授课门数(包含)：</td>
								<td colspan="3">
								<input type="text" class="easyui-numberbox" data-options="min:0,max:100" name="courseSuStart" style="width:140px;">
								~
								<input type="text" class="easyui-numberbox" data-options="min:0,max:100" name="courseSuEnd" style="width:140px;">
								</td>
							</tr>
						</table>
					</form>
				</center>
			</div>
			<div data-options="region:'south',border:false"
				style="text-align:right;padding:5px 0;height:40px">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)" onclick="_search2()">查询</a> 
				<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)" onclick="cleanSearch2()">重置</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#win_professList_query').window('close');">取消</a>
			</div>
		</div>
	</div> -->
	<div id="tbList">
		<div style="margin-bottom:1px">
<!-- 			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="$('#win_professList_query').window('open');">查询</a>  -->
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="appand2();">新增</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit2();">修改</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="remove2();">删除</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="professListDatagrid.datagrid('clearChecked');professListDatagrid.datagrid('clearSelections');professListDatagrid.datagrid('unselectAll');">取消选中</a>
<!-- 			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-info" plain="true" onclick="view2();">查看详细</a> -->
		</div>
	</div>
</div>