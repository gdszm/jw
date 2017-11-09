<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.tlzn.util.base.Constants"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript">
var activity;
var aid;
var beginTime='${beginTime}';
var endTime='${endTime}';
var statusMore='${status}';
function initCombox(form){
	initCombobox(form,'aspecies','${pageContext.request.contextPath}/dic!combox.do?ctype=ASPECIES');
	initCombobox(form,'actStatus','${pageContext.request.contextPath}/dic!combox.do?ctype=HSTATUS');
}
	$(document).ready(function(){
		var f = $('#win_dolog_query');
		initCombox("win_dolog_query");
		$('#status').combobox('setValue', '${status}');
		activity=$('#Activitygrid');
		activity.datagrid({
			url:'${pageContext.request.contextPath}/activity!situationDatagrid.do',
			width: 'auto',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			singleSelect:true,
			selectOnCheck:false,
			checkOnSelect:false,
			pagination:true,
			sortName:'aspecies',
			sortOrder:'desc',
			pageSize:20,
			pageList:[20,50,100],
			pageNumber:1,
			idField:'aid',
			fit:true,
			frozenColumns:[[
				{title : '',field :'id',width:10,sortable:false,checkbox:true},  
				{field:'aspeciesName',title:'活动种类',align:'center',width:100,sortable:true},				
				{field:'atheme',title:'活动主题',align:'center',width:400,sortable:true}
			]],
			columns:[[
				{field:'time',title:'时间',align:'center',width:300,sortable:true,formatter : function(value,row){
					if(row.timebeg&&row.timeend){return row.timebeg+"至"+row.timeend;}
				}},
				{field:'agency',title:'承办单位',align:'center',width:140,sortable:true},
				{field:'place',title:'地点',align:'center',width:150,sortable:true},
				{field:'invitnumb',title:'邀请人数',align:'center',width:80,sortable:true},
				{field:'attendnumb',title:'出席人数',align:'center',width:80,sortable:true},
				{field:'statusName',title:'状态',align:'center',width:100,sortable:true}
			]],	
			queryParams: {
				status:statusMore,
				timebeg: beginTime,
				timeend: endTime
			},
			toolbar: '#tb'
		});
	});

	function view() {
		var rows = activity.datagrid('getSelected');
		if (rows) {
			var aid=rows.aid;
			var p = dj.dialog({
				title : '查看活动',
				href : '${pageContext.request.contextPath}/activity!actView.do?aid='+aid,
				width : 600,
				height : 450,
				maximized : true,
				buttons : [{
					text : '关闭',
					iconCls:'icon-cancel',
					handler : function() {
						p.dialog('close');
					},
				}],
				onLoad : function() {
					$('#commcodegrid').datagrid({
						url:'${pageContext.request.contextPath}/activitypeo!peoQuery.do?aid='+aid,
						width: 'auto',
						height:'auto',
						striped:true,
						nowrap:true,
						pagination:true,
						pageSize:10,
						pageList:[10,15,20],
						rownumbers:true,
						sortName: 'auid',
						sortOrder: 'asc',
						idField:'auid',
						fit:true,
						columns:[[
							{field:'name',title:'委员',align:'center',width:100,sortable:false},
							{field:'circleName',title:'界别',align:'center',width:100,sortable:false},
							{field:'committeeName',title:'所属专委会',align:'center',width:120,sortable:false},
							{field:'telephone',title:'联系电话',align:'left',width:120,sortable:false},
							{field:'email',title:'电子邮箱',align:'center',width:120,sortable:false},
							{field:'companyName',title:'工作单位',align:'center',width:200,sortable:false},
							{field:'job',title:'职务',align:'center',width:150,sortable:false},
							{field:'astatusName',title:'出席情况',align:'center',width:70,sortable:false}
						]],
					});
				}
			});
		}else {
			dj.messagerAlert('提示', '请选择要查看的记录！', 'error');
		}
	}

	function edit() {
		var rows = activity.datagrid('getSelected');
		if (rows) {
			var status=rows.status;
			if(status==<%=Constants.ACT_TYPE_STATUS_BJ%>){
				dj.messagerAlert('提示', '已办结记录不能修改！', 'error');
				return;
			}
			aid=rows.aid;
			var p = dj.dialog({
				title : '查看活动',
				href : '${pageContext.request.contextPath}/activity!actView.do?aid='+aid,
				width : 600,
				height : 450,
				maximized : true,
				buttons : [{
					text : '关闭',
					iconCls:'icon-cancel',
					handler : function() {
						p.dialog('close');
					},
				}],
				onLoad : function() {
					$('#commcodegrid').datagrid({
						url:'${pageContext.request.contextPath}/activitypeo!peoQuery.do?aid='+aid,
						width: 'auto',
						height:'auto',
						striped:true,
						nowrap:true,
						rownumbers:true,
						singleSelect:true,
						selectOnCheck:false,
						checkOnSelect:false,
						pagination:true,
						sortName:'auid',
						sortOrder:'desc',
						pageSize:20,
						pageList:[20,50,100],
						pageNumber:1,
						idField:'auid',
						fit:true,
						columns:[[
							{title : '编号',field :'auid',width:10,sortable:false,checkbox:true},  
							{field:'name',title:'委员',align:'center',width:100,sortable:false},
							{field:'circleName',title:'界别',align:'center',width:100,sortable:false},
							{field:'committeeName',title:'所属专委会',align:'center',width:120,sortable:false},
							{field:'telephone',title:'联系电话',align:'left',width:120,sortable:false},
							{field:'email',title:'电子邮箱',align:'center',width:120,sortable:false},
							{field:'companyName',title:'工作单位',align:'center',width:200,sortable:false},
							{field:'job',title:'职务',align:'center',width:150,sortable:false},
							{field:'astatusName',title:'出席情况',align:'center',width:70,sortable:false}
						]],
						toolbar: [{
							text: ' 添加', 
							iconCls: 'icon-add',
							handler: function(){
								member_add();
							}
						},'-',{
							text: ' 删除 ', 
							iconCls: 'icon-remove',
							handler: function(){
								delete_member();
							}
						},'-',{
							text : '请假详情',
							iconCls : 'icon-view',
							handler : function() {
								leave();
							}
						},'-',{
							text : '出席情况调整',
							iconCls : 'icon-edit',
							handler : function() {
								enable(); 
							}
						}]
					});
				}
			});
		}else {
			dj.messagerAlert('提示', '请选择要调整的记录！', 'error');
		}
	}
	//添加邀請人
	function member_add(){
		//已经选择好的委员
		var commCodes=[];
		var wyrows=$('#commcodegrid').datagrid('getRows');
		for(var i=0;i<wyrows.length;i++){
			commCodes.push(wyrows[i].commCode);
		}
		var mp= dj.dialog({
			title : '添加参加活动人员',
			href : '${pageContext.request.contextPath}/comm!memberSelect.do?commCodes='+commCodes,
			width : 750,
			height : 460,
			buttons : [ {
				text : '添加',
				iconCls:'icon-ok',
				handler : function() {
					$('#rightdatagrid').datagrid('selectAll');
					var rows=$('#rightdatagrid').datagrid('getRows');
					var wyrows=$('#commcodegrid').datagrid('getRows');
					var ids = [];
					parent.dj.messagerConfirm('请确认', '您确定要添加右边栏目中的所有人员 吗？', function(r) {
						if (r) {
							if(rows.length>0){
								for(var i=0;i<rows.length;i++){
									var flag=0;
									for(var j=0;j<wyrows.length;j++){
										if(rows[i].code==wyrows[j].commCode){
											flag=1;
											break;
										}
									}
									if(flag==0){
										$('#commcodegrid').datagrid('appendRow',{
											commCode:rows[i].code,
											name:rows[i].name,
											committeeName:rows[i].committeeName,
											circleName:rows[i].circleName,
											telephone:rows[i].telephone,
											email:rows[i].email,
											companyName:rows[i].companyName,
											job:rows[i].job
										});
										ids.push(rows[i].code);
									}
								}
								$.ajax({
									type:"POST",
									url:"${pageContext.request.contextPath}/activitypeo!add.do",
									data : {
										code : ids.join(','),
										aid : aid
									},
									dataType:"json",
									success:function(t){
										if(t.success){
											parent.dj.messagerAlert('提示', '参加活动人员添加成功！', 'success');	
										}else{
											parent.dj.messagerAlert('提示', '参加活动人员添加失败！', 'success');	
										}
									}
								});
								mp.dialog('close'); 
							}else{
								parent.dj.messagerAlert('提示', '请选择要添加的记录！', 'error');
								return;
							}
							
						}
					});
				}
				},{
				text: ' 关闭 ', 
				iconCls:'icon-cancel',
				handler: function() { 
					mp.dialog('close'); 
				} 
			}],
				onLoad : function() {
					$('#gid').combobox({    
					    url:'${pageContext.request.contextPath}/group!doNotNeedSession_combox.do',
					    valueField:'gid',    
					    textField:'gname'   
					});  
				}
			
		});
	}	
	//删除邀請人
	function delete_member(){
		var rows=$('#commcodegrid').datagrid('getSelections');
		if(rows.length>0){
			var ids = [];
			for(var i = rows.length-1; i>=0 ;i--){
				ids.push(rows[i].auid);
				var rowindex=$('#commcodegrid').datagrid('getRowIndex',rows[i]);
				$('#commcodegrid').datagrid('deleteRow',rowindex);
			}
			$.ajax({
				type:"POST",
				url:"${pageContext.request.contextPath}/activitypeo!delete.do",
				data : {
					ids : ids.join(','),
				},
				dataType:"json",
				success:function(t){
					if(t.success){
						parent.dj.messagerAlert('提示', '参会人员删除成功！', 'success');	
					}else{
						parent.dj.messagerAlert('提示', '参会人员删除失败！', 'success');	
					}
				}
			});
		}else{
			dj.messagerAlert('提示', '请选择要删除的记录！', 'error');
		}
	}
	
	function leave2(){
		var rows = $('#commcodegrid').datagrid('getSelected');
		if (rows) {
			var auid=rows.auid;
			var astatus=rows.astatus;
			if(astatus!=<%=Constants.ACT_TYPE_ASTATUS_QJ%>){
				dj.messagerAlert('提示', '该人员没有请假！', 'error');
				return;
			}
			var p = dj.dialog({
				title : '查看请假详细',
				href : '${pageContext.request.contextPath}/activitypeo!leave.do?auid='+auid,
				width : 400,
				height : 200,
				buttons : [{
					text : '关闭',
					iconCls:'icon-no',
					handler : function() {
						p.dialog('close');
   					},
				}],
				onLoad : function(){
					getcombobox('leaveType','${pageContext.request.contextPath}/dic!combox.do?ctype=LEAVE');
				}
			});
		}else {
			dj.messagerAlert('提示', '请选择要调整的记录！', 'error');
		}
	}
	//请假详情
	function leave(){
		var rows = $('#commcodegrid').datagrid('getSelected');
		if (rows) {
			var astatus=rows.astatus;
			if(astatus!=<%=Constants.ACT_TYPE_ASTATUS_QJ%>){
				dj.messagerAlert('提示', '该委员没有请假！', 'error');
				return;
			}
			var p = dj.dialog({
				title : '查看请假详细',
				href : '${pageContext.request.contextPath}/activitypeo!leave.do?auid='+rows.auid,
				width : 400,
				height : 200,
				buttons : [{
					text : '同意',
					iconCls:'icon-ok',
					handler : function() {
						p.dialog('close');
   					},
				},{
					text : '不同意',
					iconCls:'icon-no',
					handler : function() {
						var f = p.find('form');
						f.form('submit', {
							url : '${pageContext.request.contextPath}/activitypeo!change.do?astatus=<%=Constants.ACT_TYPE_ASTATUS_QX%>',
							success : function(d) {
								var json = $.parseJSON(d);
								if (json.success) {
									$('#commcodegrid').datagrid('reload');
									p.dialog('close');
								}
								dj.messagerShow({
									msg : json.msg,
									title : '提示'
								});
							}
						});
					},
				}],
				onLoad : function(){
					getcombobox('leaveType','${pageContext.request.contextPath}/dic!combox.do?ctype=<%=Constants.ACT_TYPE_LEAVE%>');
				}
			});
		}else {
			dj.messagerAlert('提示', '请选择要调整的记录！', 'error');
		}
	}
	function enable(){
		var rows = $('#commcodegrid').datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			for ( var i = 0; i < rows.length; i++) {
				ids.push(rows[i].auid);
			}
			var p = dj.dialog({
				title : '出席情况调整',
				href : '${pageContext.request.contextPath}/activitypeo!actAstatus.do',
				width : 400,
				height : 200,
				buttons : [{
					text : '提交',
					iconCls:'icon-ok',
					handler : function() {
						var f = p.find('form');
						f.form('submit', {
							url : '${pageContext.request.contextPath}/activitypeo!astatus.do?auid='+ids.join(','),
							success : function(d) {
								var json = $.parseJSON(d);
								if (json.success) {
									$('#commcodegrid').datagrid('reload');
									p.dialog('close');
								}
								dj.messagerShow({
									msg : json.msg,
									title : '提示'
								});
							}
						});
					},
				}],
				onLoad : function(){
					var f=p.find('form');
					var astatus = f.find('input[id=astatus]');
					astatus.combobox({    
					    url:'${pageContext.request.contextPath}/dic!combox.do?ctype=ASTATUS',  
					    valueField:'cvalue',    
					    textField:'ckey',
					    onChange:function(n,o){
					    	if(n==<%=Constants.ACT_TYPE_ASTATUS_QJ%>){
					    		document.getElementById("commtr_2").style.display="";
					    		document.getElementById("commtr_6").style.display="";
					    	}else{
					    		document.getElementById("commtr_2").style.display="none";
					    		document.getElementById("commtr_6").style.display="none";
					    	}
						}
					}); 
					getcombobox('leaveType','${pageContext.request.contextPath}/dic!combox.do?ctype=LEAVE');
				}
			});
		} else {
			parent.dj.messagerAlert('提示', '请勾选要调整的记录！', 'error');
		}
	}

	function save(){
		var rows = activity.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			parent.dj.messagerConfirm('请确认', '您要办结当前所选活动？办结后不可编辑！', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].aid);
					}
					$.ajax({
						type:"POST",
						url:"${pageContext.request.contextPath}/activity!change.do",
						data : {
							ids : ids.join(','),
							status : <%=Constants.ACT_TYPE_STATUS_BJ%>
						},
						dataType:"json",
						success:function(t){
							if(t.success){
								$.messager.show({msg : t.msg,title : '成功'});
								activity.datagrid('reload');
							}else{
								$.messager.show({msg : t.msg,title : '失败'});
							}
						}
					});
				}
			});
		} else {
			parent.dj.messagerAlert('提示', '请勾选要办结的活动！', 'error');
		}
	}
	
	//邀请人员过虑
	function tar_search() {
		$('#tazgrid').datagrid('load', dj.serializeObject($('#tar_queryForm')));
	}
	
	//邀请人员过虑取消
	function tar_cleanSearch() {
		$('#tazgrid').datagrid('load', {});
		$('input').val('');
	}
	//查询
	function _search() {
		activity.datagrid('load', dj.serializeObject($('#dolog_queryForm')));
		 $('#win_dolog_query').window('close');
	}

	//重置
	function cleanSearch() {
		activity.datagrid({queryParams:{timebeg:'',timeend:'',status:''}
		});
		activity.datagrid('load', {});
		$('#dolog_queryForm').form('clear');
		 
		$('#aspeciesId').combobox('setValue', '');
		$('#status').combobox('setValue', '');
	}
</script>
</head>

<body>
		<!-- 表格 -->
	<div class="easyui-layout" data-options="fit:true">						
		<div data-options="region:'center',border:false" style="padding:1px;background:#fff;border:1px solid #ccc;">
			<table id="Activitygrid" style="overflow: auto;"></table>
		</div>
	</div>
	<div id="columnmenu" class="easyui-menu" style="width:110px;"></div>

	<div id="tb">
		<div style="margin-bottom:1px">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="javascript:$('#win_dolog_query').window('open');">查询</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-view" plain="true" onclick="view()">查看详细</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit()">人员/出席情况 调整</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="save()">办结</a>
		</div>
	</div>
	<div id="win_dolog_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:500px;height:280px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="dolog_queryForm" enctype=multipart/form-data method="post">
					<table id="dolog_queryTable" >				
						<tr height="25">
							<td nowrap>活动种类：</td>
							<td><input type="text" id="aspeciesId" name="aspecies" style="width:360px;padding:2px;border:1px solid #000;"/></td>
						</tr>
						<tr height="25">
							<td nowrap>活动主题：</td>
							<td> <input type="text" name="atheme" class="easyui-validatebox" style="width:354px;padding:2px;"/></td>
						</tr>
						<tr height="25">
							<td nowrap>活动地点：</td>
							<td>  <input type="text" name="place" class="easyui-validatebox" style="width:354px;padding:2px;"/></td>
						</tr>
						<tr height="25">
							<td nowrap>承办单位：</td>
							<td>  <input type="text" name="agency" class="easyui-validatebox" style="width:354px;padding:2px;"/></td>
						</tr>
						<!-- <tr height="25">
							<td nowrap>所属界次：</td>
							<td>  <input type="text" name="secondaryId" style="width:360px;padding:2px;border:1px solid #000;"/></td>
						</tr> -->
						<tr height="25">
							<td nowrap>活动日期：</td>
							<td align="left"> <input class="easyui-datebox" name="timebeg" style="width:174px" value="${beginTime}"/>--<input class="easyui-datebox" name="timeend"  style="width:174px" value="${endTime}" /></td>
						</tr>
						<tr height="25">
							<td nowrap>状态：</td>
							<td> <input id="status" panelHeight="100px" name="status" style="width:360px;padding:2px;border:1px solid #000;"></td>
						</tr>
					</table>
				</form>
				</center> 
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;height:40px">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)" onclick="_search()">查询</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)" onclick="cleanSearch()">重置</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#win_dolog_query').window('close');">取消</a>
			</div>
		</div>
	</div>
</body>
</html>