<%@page import="com.tlzn.util.base.Constants"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript">
	var gridmeeting;
	var p;
	var statusMore='${status}';
	function initCombox(form){
		initCombobox(form,'status','${pageContext.request.contextPath}/dic!combox.do?ctype=<%=Constants.MEETING_TYPE_STATUS%>');
		initCombobox(form,'meetType','${pageContext.request.contextPath}/dic!combox.do?ctype=<%=Constants.MEETING_TYPE%>');
	}
	$(document).ready(function() {
		initCombox("meeting_queryForm");
		$('#statusId').combobox('setValue', '${status}');
		gridmeeting=$('#meetinggrid');
		gridmeeting.datagrid({
			url:'${pageContext.request.contextPath}/meeting!datagrid.do',
			width: 'auto',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			pagination:true,
			pageSize:20,
			pageList:[20,50,100],
			pageNumber:1,
			singleSelect:true,
			selectOnCheck:false,
			checkOnSelect:false,
			idField:'meetId',
			fit:true,
			frozenColumns:[[
				{field:'meetId',width:10,sortable:false,checkbox:true}, 
				{field:'meetTypeName',title:'会议类型',align:'center',width:120,sortable:true},
				{field:'meetName',title:'会议名称',halign:'center',align:'left',width:380,sortable:true}
			]],
			columns:[[
				{field:'shortName',title:'会议简称',align:'center',width:150,sortable:true},				
				{field:'beginTime',title:'会议时间',align:'center',width:160,sortable:true ,formatter : function(value,row){
						return row.beginTime+"至"+row.endTime;
				}},
				{field:'address',title:'会议地点',align:'center',width:200,sortable:true},
				{field:'depid',title:'承办单位',align:'center',width:120,sortable:true},
				{field:'statusName',title:'状态',align:'center',width:100,sortable:true}
			]],	
			queryParams: {
				status: statusMore
			},
			toolbar: '#tb'
		});
		
	});
	
	//添加
	function append(){
		var p = dj.dialog({
			title : '添加会议',
			href : '${pageContext.request.contextPath}/meeting!meetingAdd.do',
			width : 800,
			height : 600,
			maximized:true,
			buttons : [ {
				text : '发 布',
				iconCls:'icon-ok',
				handler : function() {
					var f = p.find('form');
					$("#content").val(UE.getEditor('editor').getContent()); 
					var commData=$('#participantsgrid').datagrid('getRows');
					var comCodes=[];
					if(commData.length>0){
						for(var i =0;i<commData.length;i++){
							comCodes.push(commData[i].commCode);
						}
						$('#commCodes').val(comCodes.join(','));
					}
					var f = p.find('form');
					f.form('submit', {
						url : '${pageContext.request.contextPath}/meeting!add.do?status=<%=Constants.MEETING_TYPE_STATUS_YFB%>',
						success : function(d) {
							var json = $.parseJSON(d);
							if (json.success) {
								gridmeeting.datagrid('reload');
								p.dialog('close');
							}
							parent.dj.messagerShow({
								msg : json.msg,
								title : '提示'
							});
						}
					});
				}
			},{
				text : '保 存',
				iconCls:'icon-save',
				handler : function() {
					$("#content").val(UE.getEditor('editor').getContent()); 
					var commData=$('#participantsgrid').datagrid('getRows');
					var comCodes=[];
					if(commData.length>0){
						for(var i =0;i<commData.length;i++){
							comCodes.push(commData[i].commCode);
						}
						$('#commCodes').val(comCodes.join(','));
						
					}
					var f = p.find('form');
					f.form('submit', {
						url : '${pageContext.request.contextPath}/meeting!add.do?status=<%=Constants.MEETING_TYPE_STATUS_WFB%>',
						success : function(d) {
							var json = $.parseJSON(d);
							if (json.success) {
								gridmeeting.datagrid('reload');
								p.dialog('close');
							}
							parent.dj.messagerShow({
								msg : json.msg,
								title : '提示'
							});
						}
					});
				}
			},{
				text: ' 关闭 ', 
				iconCls:'icon-cancel',
				handler: function() { 
					p.dialog('close'); 
				} 
			}],
			onLoad : function() {
				$(document).ready(function() {
					//参会人员信息列表
					getMemberGrid("");
				});
				initCombobox('addForm','meetType','${pageContext.request.contextPath}/dic!combox.do?ctype=<%=Constants.MEETING_TYPE%>');
				initCombobox('addForm','attendStatus','${pageContext.request.contextPath}/dic!combox.do?ctype=<%=Constants.ACT_TYPE_ASTATUS%>');
			}
		});
	}
	function getMemberGrid(url){
		$('#participantsgrid').datagrid({
			url:url,
			width:'auto',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			fitColumns:true,
			pagination:true,
			pageSize:20,
			pageList:[20,50,100],
			idField:'commCode',
			fit:true,
			columns:[[
				{field:'name',title:'委员',align:'center',width:100,sortable:false},
				{field:'circleName',title:'界别',align:'center',width:100,sortable:false},
				{field:'committeeName',title:'所属专委会',align:'center',width:120,sortable:false},
				{field:'telephone',title:'联系电话',align:'left',width:120,sortable:false},
				{field:'email',title:'电子邮箱',align:'center',width:120,sortable:false},
				{field:'companyName',title:'工作单位',align:'center',width:200,sortable:false},
				{field:'job',title:'职务',align:'center',width:150,sortable:false}
			]],
			toolbar: [{
				text: ' 添加', 
				iconCls: 'icon-add',
				handler: function(){
					member_add2();
				}
			},'-',{
				text: ' 删除 ', 
				iconCls: 'icon-remove',
				handler: function(){
					delete_member();
				}
			}]

		});
	} 
	//添加参会人
	function member_add2(){
		//已经选择好的委员
		var commCodes=[];
		var wyrows=$('#participantsgrid').datagrid('getRows');
		for(var i=0;i<wyrows.length;i++){
			commCodes.push(wyrows[i].commCode);
		}
		var mp= dj.dialog({
			title : '添加参会人员',
			href : '${pageContext.request.contextPath}/comm!memberSelect.do?commCodes='+commCodes,
			width : 750,
			height : 460,
			buttons : [ 
				{
						text: '确定', 
						iconCls:'icon-ok',
						handler: function() { 
							$('#rightdatagrid').datagrid('selectAll');
							var rows=$('#rightdatagrid').datagrid('getRows');
							var wyrows=$('#participantsgrid').datagrid('getRows');
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
													$('#participantsgrid').datagrid('appendRow',{
														commCode:rows[i].code,
														name:rows[i].name,
														committeeName:rows[i].committeeName,
														circleName:rows[i].circleName,
														telephone:rows[i].telephone,
														email:rows[i].email,
														companyName:rows[i].companyName,
														job:rows[i].job
													});
												}
											}
											mp.dialog('close'); 
											parent.dj.messagerAlert('提示', '参会人员添加成功！', 'success');	
										}else{
											parent.dj.messagerAlert('提示', '请选择要添加的记录！', 'error');
											return;
										}
										
									}
								});
						} 
				},
				{
					text: ' 关闭 ', 
					iconCls:'icon-cancel',
					handler: function() { 
						mp.dialog('close'); 
					} 
				}
				],
				onLoad : function() {
					$('#gid').combobox({    
					    url:'${pageContext.request.contextPath}/group!doNotNeedSession_combox.do',
					    valueField:'gid',    
					    textField:'gname'   
					});  
				}
			
		});
	}
	//删除参会人
	function delete_member(){
		var rows=$('#participantsgrid').datagrid('getSelections')
		if(rows.length>0){
			for(var i = rows.length-1; i>=0 ;i--){
				var rowindex=$('#participantsgrid').datagrid('getRowIndex',rows[i]);
				$('#participantsgrid').datagrid('deleteRow',rowindex);
			}
			
		}else{
			dj.messagerAlert('提示', '请选择要删除的记录！', 'error');
		}
	}
	
	//编辑
	function edit(){
		var row=gridmeeting.datagrid('getSelected');
		if(row==null){
			dj.messagerAlert('提示', '请选择要修改的记录！', 'error');
			return;
		}
		if(row.status!=<%=Constants.MEETING_TYPE_STATUS_WFB %>){
			dj.messagerAlert('提示', '选中记录已发布或是已办结,不能进行编辑操作！', 'error');
			return;
		}
		var p = dj.dialog({
			title : '会议编辑',
			href : '${pageContext.request.contextPath}/meeting!meetingEdit.do?meetId='+row.meetId,
			width : 800,
			height : 600,
			maximized:true,
			buttons : [ {
				text : '发 布',
				iconCls:'icon-ok',
				handler : function() {
					var commData=$('#participantsgrid').datagrid('getRows');
					var comCodes=[];
					if(commData.length>0){
						for(var i =0;i<commData.length;i++){
							comCodes.push(commData[i].commCode);
						}
						$('#commCodes').val(comCodes.join(','));
						
					}
					var f = p.find('form');
					f.form('submit', {
						url : '${pageContext.request.contextPath}/meeting!update.do?status=<%=Constants.MEETING_TYPE_STATUS_YFB%>',
						success : function(d) {
							var json = $.parseJSON(d);
							if (json.success) {
								gridmeeting.datagrid('reload');
								p.dialog('close');
							}
							parent.dj.messagerShow({
								msg : json.msg,
								title : '提示'
							});
						}
					});
				}
			},{
				text : '保 存',
				iconCls:'icon-save',
				handler : function() {
					var commData=$('#participantsgrid').datagrid('getRows');
					var comCodes=[];
					if(commData.length>0){
						for(var i =0;i<commData.length;i++){
							comCodes.push(commData[i].commCode);
						}
						$('#commCodes').val(comCodes.join(','));
						
					}
					var f = p.find('form');
					f.form('submit', {
						url : '${pageContext.request.contextPath}/meeting!update.do?status=<%=Constants.MEETING_TYPE_STATUS_WFB%>',
						success : function(d) {
							var json = $.parseJSON(d);
							if (json.success) {
								gridmeeting.datagrid('reload');
								p.dialog('close');
							}
							parent.dj.messagerShow({
								msg : json.msg,
								title : '提示'
							});
						}
					});
				}
			},{
				text: ' 关闭 ', 
				iconCls:'icon-cancel',
				handler: function() { 
					p.dialog('close'); 
				} 
			}],
			onLoad : function() {
				$(document).ready(function() {
					//参会人员信息列表
					getMemberGrid("${pageContext.request.contextPath}/meetingMan!datagridByMeeting.do?meetId="+row.meetId);
				});
				initCombobox('addForm','meetType','${pageContext.request.contextPath}/dic!combox.do?ctype=<%=Constants.MEETING_TYPE%>');
				initCombobox('addForm','attendStatus','${pageContext.request.contextPath}/dic!combox.do?ctype=<%=Constants.ACT_TYPE_ASTATUS%>');
			}
		});
	}
	
	//查询
	function _search() {
		gridmeeting.datagrid('load', dj.serializeObject($('#meeting_queryForm')));
		 $('#win_meeting_query').window('close');
	}
	//重置
	function cleanSearch() {
		gridmeeting.datagrid('load', {});
		$('#meeting_queryForm input').val('');
		$("#meetTypeId").combobox('setValue', '');
		$("#statusId").combobox('setValue', '');
	}
	//删除会议
	function remove(){
		var rows=gridmeeting.datagrid('getChecked');
		if(rows.length>0){
			var ids=[];
			parent.dj.messagerConfirm('请确认', '确定删除当前所选记录？', function(r) {
				if (r) {
					for(var i =0;i<rows.length;i++){
						if(rows[i].status!=<%=Constants.MEETING_TYPE_STATUS_WFB %>){
							dj.messagerAlert('提示', '选中记录中有已发布或已办结记录！', 'error');
							return;
						}
						ids.push(rows[i].meetId);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/meeting!remove.do',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(d) {
							gridmeeting.datagrid('load');
							gridmeeting.datagrid('clearChecked');
							parent.dj.messagerShow({
								title : '提示',
								msg : d.msg
							});
						}
					});
				}
			});	
		}else{
			dj.messagerAlert('提示', '请选择要删除的记录！', 'error');
		}
	}
	//发布会议
	function enable(){
		var rows=gridmeeting.datagrid('getChecked');
		if(rows.length>0){
			var ids=[];
			parent.dj.messagerConfirm('请确认', '确定发布当前所选记录？', function(r) {
				if (r) {
					for(var i =0;i<rows.length;i++){
						if(rows[i].status!=<%=Constants.MEETING_TYPE_STATUS_WFB %>){
							dj.messagerAlert('提示', '选中记录中有已发布或已办结记录！', 'error');
							return;
						}
						ids.push(rows[i].meetId);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/meeting!change.do',
						data : {
							ids : ids.join(','),
							status : <%=Constants.MEETING_TYPE_STATUS_YFB %>
						},
						dataType : 'json',
						success : function(d) {
							gridmeeting.datagrid('load');
							gridmeeting.datagrid('clearChecked');
							parent.dj.messagerShow({
								title : '提示',
								msg : d.msg
							});
						}
					});
				}
			});	
		}else{
			dj.messagerAlert('提示', '请选择要发布的记录！', 'error');
		}
	}
	//取消发布会议
	function stop(){
		var rows=gridmeeting.datagrid('getChecked');
		if(rows.length>0){
			var ids=[];
			parent.dj.messagerConfirm('请确认', '确定取消发布当前所选记录？', function(r) {
				if (r) {
					for(var i =0;i<rows.length;i++){
						if(rows[i].status!=<%=Constants.MEETING_TYPE_STATUS_YFB %>){
							dj.messagerAlert('提示', '选中记录中有未发布或已办结记录！', 'error');
							return;
						}
						var myDate = new Date();
						var mytime=myDate.getTime();
						if(Date.parse(rows[i].beginTime)<mytime){
							dj.messagerAlert('提示', '会议已经开始！不能取消发布', 'error');
							return;
						}
						ids.push(rows[i].meetId);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/meeting!change.do',
						data : {
							ids : ids.join(','),
							status : <%=Constants.MEETING_TYPE_STATUS_WFB %>
						},
						dataType : 'json',
						success : function(d) {
							gridmeeting.datagrid('load');
							gridmeeting.datagrid('clearChecked');
							parent.dj.messagerShow({
								title : '提示',
								msg : d.msg
							});
						}
					});
				}
			});	
		}else{
			dj.messagerAlert('提示', '请选择要取消发布的记录！', 'error');
		}
	}
	//取消选中
	function undo(){
		gridmeeting.datagrid('clearChecked');
		gridmeeting.datagrid('clearSelections');
	}
	
	//详细
	function view(){
		var row=gridmeeting.datagrid('getSelected');
		if(row==null){
			dj.messagerAlert('提示', '请选择要查看的记录！', 'error');
			return;
		}
		var p = dj.dialog({
			title : '会议详情',
			href : '${pageContext.request.contextPath}/meeting!meetingView.do?meetId='+row.meetId,
			width : 800,
			height : 600,
			maximized:true,
			buttons : [ {
				text: ' 关闭 ', 
				iconCls:'icon-cancel',
				handler: function() { 
					p.dialog('close'); 
				} 
			}],
			onLoad:function(){
				$('#situationgrid').datagrid({
					url:'${pageContext.request.contextPath}/meetingMan!datagridByMeeting.do?meetId='+row.meetId,
					width:'auto',
					height:'auto',
					striped:true,
					nowrap:true,
					rownumbers:true,
					fitColumns:true,
					pagination:true,
					pageSize:20,
					pageList:[20,50,100],
					fit:true,
					columns:[[
						{field:'name',title:'委员',align:'center',width:100,sortable:false},
						{field:'circleName',title:'界别',align:'center',width:100,sortable:false},
						{field:'committeeName',title:'所属专委会',align:'center',width:120,sortable:false},
						{field:'telephone',title:'联系电话',align:'left',width:120,sortable:false},
						{field:'email',title:'电子邮箱',align:'center',width:120,sortable:false},
						{field:'companyName',title:'工作单位',align:'center',width:200,sortable:false},
						{field:'job',title:'职务',align:'center',width:150,sortable:false},
						{field:'statusName',title:'出席状态',align:'center',width:100,sortable:false}
					]]
				});
			}
		});
	}
	
	
</script>
</head>

<body>
	<!-- 表格 -->
	<div class="easyui-layout" data-options="fit:true">						
		<div data-options="region:'center',border:false" style="padding:1px;background:#fff;border:1px solid #ccc;">
			<table id="meetinggrid"></table>
		</div>
	</div>
	<div id="columnmenu" class="easyui-menu" style="width:100px;"></div>

	<div id="tb">
		<div style="margin-bottom:1px">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="javascript:$('#win_meeting_query').window('open');">查询</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="append()">增加</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit()">修改</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="remove()">删除</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-view" plain="true" onclick="view()">查看详细</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-enable" plain="true" onclick="enable()">发布</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-stop" plain="true" onclick="stop()">取消发布</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="undo()">取消选中</a>
				

		</div>
	</div>
	<div id="win_meeting_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:500px;height:320px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="meeting_queryForm">
				<input type="hidden" name="filingFlg" id="filingFlgId"/>
				<table id="poll_queryTable">
					<tr height="25">
						<td nowrap>会议名称：</td>
						<td><input type="text" name="meetName" class="easyui-validatebox" style="width:300px;padding:2px;"></td>
					</tr>				
					<tr height="25">
						<td nowrap>会议类型：</td>
						<td><input id="meetTypeId" type="text" panelHeight="100px" name="meetType" style="width:305px;padding:2px;border:1px solid #000;"></td>
					</tr>
					<tr height="25">
						<td nowrap>会议时间：</td>
						<td> <input class="easyui-datebox" name="beginTime"   style="width:146px"/>--<input class="easyui-datebox" name="endTime"  style="width:146px"/></td>
					</tr>
					<tr height="25">
						<td nowrap>发布时间：</td>
						<td> <input class="easyui-datebox" name="startDate"   style="width:146px"/>--<input class="easyui-datebox" name="endDate"  style="width:146px"/></td>
					</tr>
					<tr height="25">
						<td nowrap>承办单位：</td>
						<td> <input type="text" name="depid" class="easyui-validatebox" style="width:298px;padding:2px;"></td>
					</tr>
					<tr height="25">
						<td nowrap>会议地点：</td>
						<td> <input type="text" name="address" class="easyui-validatebox" style="width:298px;padding:2px;"></td>
					</tr>
					<tr height="25">
						<td nowrap>状态：</td>
						<td> <input id="statusId" panelHeight="100px" name="status" style="width:304px;padding:2px;border:1px solid #000;"></td>
					</tr>
				</table>
				</form>
				</center>
			</div>

		
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;height:40px">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)" onclick="_search()">查询</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)" onclick="cleanSearch()">重置</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#win_meeting_query').window('close');">取消</a>
			</div>
		</div>
	</div>
</body>
</html>
