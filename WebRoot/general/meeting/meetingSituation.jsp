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
	var beginTime='${beginTime}';
	var endTime='${endTime}';
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
			url:'${pageContext.request.contextPath}/meeting!situationDatagrid.do',
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
				{field:'meetTypeName',title:'会议类型',align:'center',width:100,sortable:true},
				{field:'meetName',title:'会议名称',halign:'center',align:'left',width:300,sortable:true}
			]],
			columns:[[
				{field:'shortName',title:'会议简称',align:'center',width:120,sortable:true},				
				{field:'beginTime',title:'会议时间',align:'center',width:160,sortable:true ,formatter : function(value,row){
						return row.beginTime+"至"+row.endTime;
				}},
				{field:'address',title:'会议地点',align:'center',width:200,sortable:true},
				{field:'depid',title:'承办单位',align:'center',width:120,sortable:true},
				{field:'pubDate',title:'发布时间',align:'center',width:120,sortable:true},
				{field:'pubDepdid',title:'发布单位',align:'center',width:120,sortable:true},
				{field:'statusName',title:'状态',align:'center',width:100,sortable:true}
			]],	
			queryParams: {
				status:statusMore,
				beginTime: beginTime,
				endTime: endTime
			},
			toolbar: '#tb'
		});
	
	});
	
	//查询
	function _search() {
		gridmeeting.datagrid('load', dj.serializeObject($('#meeting_queryForm')));
		 $('#win_meeting_query').window('close');
	}
	//重置
	function cleanSearch() {
		gridmeeting.datagrid({queryParams:{beginTime:'',endTime:'',status:''}
		});
		gridmeeting.datagrid('load', {});

		$('#meeting_queryForm input').val('');
		$("#meetTypeId").combobox('setValue', '');
		$("#statusId").combobox('setValue', '');

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
	//参会人员/出席情况 调整
	function edit(){
		var row=gridmeeting.datagrid('getSelected');
		if(row==null){
			dj.messagerAlert('提示', '请选择会议记录！', 'error');
			return;
		}
		if(row.status==<%=Constants.MEETING_TYPE_STATUS_BJ%>){
			dj.messagerAlert('提示', '选中记录已办结，不能进行此操作！', 'error');
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
					singleSelect:true,
					selectOnCheck:false,
					checkOnSelect:false,
					pagination:true,
					pageSize:20,
					pageList:[20,50,100],
					fit:true,
					columns:[[
						{title : '',field :'id',width:10,sortable:false,checkbox:true},  
						{field:'name',title:'委员',align:'center',width:100,sortable:false},
						{field:'circleName',title:'界别',align:'center',width:100,sortable:false},
						{field:'committeeName',title:'所属专委会',align:'center',width:120,sortable:false},
						{field:'telephone',title:'联系电话',align:'left',width:120,sortable:false},
						{field:'email',title:'电子邮箱',align:'center',width:120,sortable:false},
						{field:'companyName',title:'工作单位',align:'center',width:200,sortable:false},
						{field:'job',title:'职务',align:'center',width:150,sortable:false},
						{field:'statusName',title:'出席状态',align:'center',width:100,sortable:false}
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
							change(); 
						}
					}]
				});
			}
		});
	}
	//添加参会人
	function member_add2(){
		//已经选择好的委员
		var commCodes=[];
		var wyrows=$('#situationgrid').datagrid('getRows');
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
							var wyrows=$('#situationgrid').datagrid('getRows');
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
													$('#situationgrid').datagrid('appendRow',{
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
		var rows=$('#situationgrid').datagrid('getChecked')
		if(rows.length>0){
			parent.dj.messagerConfirm('请确认', '确定删除当前所选参会人员？', function(r) {
				if (r) {
					for(var i = rows.length-1; i>=0 ;i--){
						var rowindex=$('#situationgrid').datagrid('getRowIndex',rows[i]);
						$('#situationgrid').datagrid('deleteRow',rowindex);
					}
					memberSave();
				}
			});	
			
		}else{
			dj.messagerAlert('提示', '请选择要删除的记录！', 'error');
		}
	}
	//人员调整保存
	function memberSave(){
		var rows=$('#situationgrid').datagrid('getRows');
		var ids=[];
		if(rows.length>0){
			for(var i =0; i<rows.length;i++){
				ids.push(rows[i].commCode);
			}
		}
		var meeting=gridmeeting.datagrid('getSelected');
		var meetId=meeting.meetId;
		$.ajax({
			url : '${pageContext.request.contextPath}/meetingMan!memberSave.do',
			data : {
				ids : ids.join(','),
				meetId:meetId
			},
			dataType : 'json',
			success : function(d) {
				$('#situationgrid').datagrid('load');
				
			}
		});
	}
	
	
	
	//请假详情
	function leave(){
		var rows = $('#situationgrid').datagrid('getSelected');
		if (rows) {
			var status=rows.status;
			if(status!=<%=Constants.ACT_TYPE_ASTATUS_QJ%>){
				dj.messagerAlert('提示', '该委员没有请假！', 'error');
				return;
			}
			var p = dj.dialog({
				title : '查看请假详细',
				href : '${pageContext.request.contextPath}/meetingMan!leave.do?id='+rows.id,
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
							url : '${pageContext.request.contextPath}/meetingMan!change.do?status=<%=Constants.ACT_TYPE_ASTATUS_QX%>',
							success : function(d) {
								var json = $.parseJSON(d);
								if (json.success) {
									$('#situationgrid').datagrid('reload');
									p.dialog('close');
								}
								dj.messagerShow({
									msg : json.msg,
									title : '提示'
								});
							}
						});
					},
				}]
			});
		}else {
			dj.messagerAlert('提示', '请选择要调整的记录！', 'error');
		}
	}
	//出情情况更新
	function change(){
		var rows = $('#situationgrid').datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			for ( var i = 0; i < rows.length; i++) {
				ids.push(rows[i].id);
			}
			var dp = dj.dialog({
				title : '出席情况调整',
				href : '${pageContext.request.contextPath}/meetingMan!situationSet.do',
				width : 400,
				height : 260,
				buttons : [{
					text : '提交',
					iconCls:'icon-ok',
					handler : function() {
						var f = dp.find('form');
						$('#ids').val(ids.join(','));
						f.form('submit', {
							url : '${pageContext.request.contextPath}/meetingMan!setSave.do',
							success : function(d) {
								var json = $.parseJSON(d);
								if (json.success) {
									$('#situationgrid').datagrid('reload');
									dp.dialog('close');
								}
								dj.messagerShow({
									msg : json.msg,
									title : '提示'
								});
							}
						});
					},
				},{
					text : '关闭',
					iconCls:'icon-cancel',
					handler : function() {
						dp.dialog('close');
   					},
				}],
				onLoad : function(){
					getcombobox('leaveType','${pageContext.request.contextPath}/dic!combox.do?ctype=<%=Constants.ACT_TYPE_LEAVE%>');
					//下拉列表取值
					$('#setForm input[name=status]').combobox({    
					    url:'${pageContext.request.contextPath}/dic!combox.do?ctype=<%=Constants.ACT_TYPE_ASTATUS%>',    
					    valueField:'cvalue', 
					    panelHeight:'100',
					    textField:'ckey',
					    onChange:function(n,o){
					    	
					    	if(n==<%=Constants.PRESENT_TYPE_STATUS_QJ%>){
					    		document.getElementById( "leave" ).style.display="";
					    		document.getElementById( "leaveres" ).style.display="";
					    		
					    	}else{
					    		document.getElementById( "leave" ).style.display="none";
					    		document.getElementById( "leaveres" ).style.display="none";
					    	}
					    }
					});
				}
			});
		} else {
			parent.dj.messagerAlert('提示', '请勾选要调整的记录！', 'error');
		}
	}

	function save(){
		var rows = gridmeeting.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			parent.dj.messagerConfirm('请确认', '您要办结当前所选会议？办结后不可编辑！', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].meetId);
					}
					$.ajax({
						type:"POST",
						url:"${pageContext.request.contextPath}/meeting!change.do",
						data : {
							ids : ids.join(','),
							status : <%=Constants.MEETING_TYPE_STATUS_BJ%>
						},
						dataType:"json",
						success:function(t){
							if(t.success){
								$.messager.show({msg : t.msg,title : '成功'});
								gridmeeting.datagrid('reload');
							}else{
								$.messager.show({msg : t.msg,title : '失败'});
							}
						}
					});
				}
			});
		} else {
			parent.dj.messagerAlert('提示', '请勾选要办结的会议！', 'error');
		}
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
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-view" plain="true" onclick="view()">查看详细</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit()">参会人员/出席情况 调整</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="save()">办结</a>
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
						<td><input type="text" name="meetName" class="easyui-validatebox"  style="width:300px;padding:2px;"></td>
					</tr>				
					<tr height="25">
						<td nowrap>会议类型：</td>
						<td><input id="meetTypeId" type="text" name="meetType" panelHeight="100px" name="status" style="width:305px;padding:2px;border:1px solid #000;"></td>
					</tr>
					<tr height="25">
						<td nowrap>会议时间：</td>
						<td> <input class="easyui-datebox" name="beginTime" style="width:146px"  value="${beginTime}"/>--<input class="easyui-datebox" name="endTime"  style="width:146px" value="${endTime}" /></td>
					</tr>
					<tr height="25">
						<td nowrap>发布时间：</td>
						<td> <input class="easyui-datebox" name="startDate"   style="width:146px"/>--<input class="easyui-datebox" name="endDate"  style="width:146px"/></td>
					</tr>
					<tr height="25">
						<td nowrap>承办机构：</td>
						<td> <input type="text" name="depid" class="easyui-validatebox" style="width:300px;padding:2px;"></td>
					</tr>
					<tr height="25">
						<td nowrap>会议地点：</td>
						<td> <input type="text" name="address" class="easyui-validatebox" style="width:300px;padding:2px;"></td>
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
