<%@page import="com.tlzn.util.base.Constants"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	function initCombox(form){
		initCombobox(form,'rank','${pageContext.request.contextPath}/dic!combox.do?ctype=rank');
		initCombobox(form,'marry','${pageContext.request.contextPath}/dic!combox.do?ctype=MARRY');
		initCombobox(form,'hasTeacherCert','${pageContext.request.contextPath}/dic!combox.do?ctype=hasNo');
		initCombobox(form,'education','${pageContext.request.contextPath}/dic!combox.do?ctype=EDUCATION');
		initCombobox(form,'degree','${pageContext.request.contextPath}/dic!combox.do?ctype=DEGREE');
		initCombobox(form,'sex','${pageContext.request.contextPath}/dic!combox.do?ctype=SEX');
		$('#'+form+' input[name=cid]').combotree({
			lines : true,
			url : '${pageContext.request.contextPath}/dept!doNotNeedSession_treeRecursive.do',
			onLoadSuccess : function() {
				parent.$.messager.progress('close');
			}
		});
	}
	var p;
	var datagrid;
	$(function(){
//		initCombox("searchForm"); 
//		initCombox("teacherDatagrid");
		initCombox("teacher_queryForm");
		datagrid = $('#teacherDatagrid').datagrid({
			url:'${pageContext.request.contextPath}/teacher!datagrid.do',
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
				{field:'name',title:'教师姓名',width:150,align:'center'},
				{field:'sexName',title:'性别',width:100,align:'center'}
				]],
			columns:[[
				{field:'rankName',title:'教师职称',width:100,align:'center'},
				{field:'marryName',title:'婚姻状况',width:100,align:'center'},
				{field:'hasTeacherCertName',title:'有无教师资格证',width:100,align:'center'},
				{field:'educationName',title:'学历',width:100,align:'center'},
				{field:'gradFrom',title:'毕业院校',width:150,align:'center'},
				{field:'majorName',title:'专业名称',width:150,align:'center'},
				{field:'degreeName',title:'学位',width:100,align:'center'},
				{field:'crtTime',title:'录入系统时间',width:150,align:'center'}
    		]],
			toolbar : [
			{
				text : '查询',
				iconCls : 'icon-search',
				handler : function() {
					$('#win_teacher_query').window('open');
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
					title : '教师详情',
					href : '${pageContext.request.contextPath}/teacher!get.do?id='+rows[0].id,
					maximized:true,
					width : 300,
					height : 200,
					onLoad : function() {
						initCombox('teacherViewForm');
				
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
				title : '新增教师',
				href : '${pageContext.request.contextPath}/teacher!teacherAdd.do',
				maximized:true,
				iconCls:'icon-save',
				buttons : [ {
						text : ' 提交 ',
						iconCls:'icon-ok',
						handler : function() {
								var f = p.find('form');
								f.form('submit', {
									url : '${pageContext.request.contextPath}/teacher!add.do',
									success : function(d) {
										var json = $.parseJSON(d);
										if (json.success) {
											datagrid.datagrid('reload');
											p.dialog('close');
										}
										parent.dj.messagerAlert('提示', json.msg, 'info');
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
					title : '编辑教师信息',
					href : '${pageContext.request.contextPath}/teacher!teacherEdit.do?id='+rows[0].id,
					width : 700,
					height : 420,
					maximized : true,
					buttons : [ {
						text : '提交修改',
						iconCls:'icon-ok',
						handler : function() {
								var f = p.find('form');
								f.form('submit', {
									url : '${pageContext.request.contextPath}/teacher!edit.do',
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
						url : '${pageContext.request.contextPath}/teacher!delete.do',
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
				parent.dj.messagerAlert('提示', '只能删除未发布的教师公告，请重新勾选要删除的教师公告！', 'error');
			}
		} else {
			parent.dj.messagerAlert('提示', '请勾选要删除的记录！', 'error');
		}
	}
	//发布教师处理
	function pub(){
		var rows = datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			parent.dj.messagerConfirm('请确认', '您要发布当前所选教师？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/teacher!pub.do',
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
			parent.dj.messagerAlert('提示', '请勾选要发布的教师！', 'error');
		}
	}
	//取消发布教师处理
	function cancelPub(){
		var rows = datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			parent.dj.messagerConfirm('请确认', '您要取消发布当前所选教师？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/teacher!cancelPub.do',
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
			parent.dj.messagerAlert('提示', '请勾选要取消发布的教师！', 'error');
		}
	}
	//查询
	function _search() {
		datagrid.datagrid('load', dj.serializeObject($('#teacher_queryForm')));
		 $('#win_teacher_query').window('close');
	}
	
	function cleanSearch() {
		datagrid.datagrid('load', {});
		$('#teacher_queryForm input').val('');
		$('#sexIdTeacher').combobox('setValue', '');
		$('#rankId').combobox('setValue', '');
		$('#hasTeacherCertId').combobox('setValue', '');
		$('#educationId').combobox('setValue', '');
		$('#degreeId').combobox('setValue', '');
	}
</script>
</head>
<body class="easyui-layout" data-options="fit:true">
	<!-- 表格 -->
	<div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="teacherDatagrid"></table>
	</div>
	<div id="win_teacher_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:500px;height:374px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="teacher_queryForm">
				<input type="hidden" name="filingFlg" id="filingFlgId"/>
				<table id="poll_queryTable">
					<tr height="25">
						<td nowrap>教师姓名：</td>
						<td colspan="3"><input type="text" class="easyui-validatebox"  name="name" style="width:300px;"></td>
					</tr>
					<tr height="25">
						<td nowrap>性别 ：</td>
						<td colspan="3"> <input id="sexIdTeacher" name="sex" style="width:300px;"></td>
					</tr>
					<tr height="25">
						<td nowrap>所在院系 ：</td>
						<td colspan="3"> <input id="cidId" name="cid"  style="width:300px;"></td>
					</tr>
					<tr height="25">
						<td nowrap>档案编号 ：</td>
						<td> <input name="archNo" class="easyui-validatebox"   style="width:108px;"></td>
						<td nowrap>教师职称 ：</td>
						<td> <input id="rankId" name="rank"  style="width:108px;"></td>
					</tr>
					<tr height="25">
						<td nowrap>有无教师资格证 ：</td>
						<td> <input id="hasTeacherCertId" name="hasTeacherCert" style="width:108px;"></td>
						<td nowrap>学历 ：</td>
						<td> <input id="educationId" name="education" style="width:108px;"></td>
					</tr>
					<tr height="25">
						<td nowrap>毕业院校：</td>
						<td colspan="3"><input type="text" name="gradFrom"  class="easyui-validatebox"   style="width:300px;"></td>
					</tr>
					<tr height="25">
						<td nowrap>专业名称：</td>
						<td colspan="3"><input type="text" name="majorName"  class="easyui-validatebox"   style="width:300px;"></td>
					</tr>
					<tr height="25">
						<td nowrap>学位：</td>
						<td colspan="3"><input id="degreeId" name="degree" style="width:300px;"></td>
					</tr>
					<tr height="25">
						<td nowrap>备注：</td>
						<td colspan="3"><input type="text" name="remark"  class="easyui-validatebox"   style="width:300px;"></td>
					</tr>
					<tr height="25">
						<td nowrap>录入系统时间 ：</td>
						<td colspan="3">
							<input id="crtSTimeId" type="text" name="crtSTime" class="easyui-datebox" data-options="required:false,showSeconds:false" style="width:142px;"></input> 
		 	              	~ 
			              	<input id="crtETimeId" type="text" name="crtETime" class="easyui-datebox" data-options="required:false,showSeconds:false" style="width:142px;"></input>  
						</td>
					</tr>
				</table>
				</form>
				</center>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;height:40px">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)" onclick="_search()">查询</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)" onclick="cleanSearch()">重置</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#win_teacher_query').window('close');">取消</a>
			</div>
		</div>
	</div>
</body>
</html>