<%@page import="com.tlzn.util.base.Constants"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript">
	var gridpoll;
	var p;
	function initCombox(form){
		initCombobox(form,'status','${pageContext.request.contextPath}/dic!combox.do?ctype=POLLSTATUS');
	}
	$(document).ready(function() {
		gridpoll=$('#pollgrid');
		//gds add start
		var startType='<%=request.getAttribute("statusType")%>';
		var status;
		var filingFlg;
		//已保存
		if(startType=='A'){  
			$("#status").val("0");
			status="0";
		}
		//未审查
		if(startType=='B'){
			$("#status").val("1");
			status="1";
		}
		//未采用
		if(startType=='C'){
			$("#status").val("2");
			status="2";
		}
		//已编辑
		if(startType=='D'){
			$("#status").val("3");
			$("#filingFlgId").val("0");
			status="3";
			filingFlg="0";
		}
		//未审核
		if(startType=='E'){
			$("#status").val("3");
			$("#filingFlg").val("1");
			status="3";
			filingFlg="1";
		}
		//已审核
		if(startType=='F'){
			$("#status").val("4");
			status="4";
		}
		//已签发
		if(startType=='G'){
			$("#status").val("5");
			status="5";
		}
		//已印发
		if(startType=='H'){
			$("#status").val("6");
			status="6";
		}
		//转办中
		if(startType=='I'){
			$("#status").val("7");
			status="7";
		}
		//未办理
		if(startType=='J'){
			$("#status").val("8");
			status="8";
		}
		//申退中
		if(startType=='K'){
			$("#status").val("10");
			status="10";
		}
		//已办理
		if(startType=='L'){
			$("#status").val("9");
			status="9";
		}
		//gds add end
		gridpoll.datagrid({
			url:'${pageContext.request.contextPath}/poll!datagrid.do',
			width: 'auto',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			pagination:true,
			pageSize:20,
			pageList:[20,50,100],
			pageNumber:1,
			idField:'pollId',
			fit:true,
			queryParams: {
				status: status,
				filingFlg: filingFlg
			},
			frozenColumns:[[
				{field:'pollId',hidden:true}, 
				{field:'pollCode',title:'社情民意编号',align:'center',width:100},
				{field:'title',title:'标题',halign:'center',align:'left',width:380,sortable:true,formatter:function(val,rec){
					var str=val;	
					if(val==undefined){}
					if (rec.status=='<%=Constants.CODE_TYPE_POLL_STATUS_WSC%>'){
						str='<font color="#009100"><b>（新）</b></font>'+str;
					}
					if(rec.mergeFlg=='<%=Constants.CODE_TYPE_YESNO_YES%>'){
						var ids=rec.mergeIds.split(",");
						str+='<font color="#0000CD"><b>    ('
						for(var j=0;j<ids.length;j++){
							str+='<a  href="javascript:void(0)" onclick="see('+ids[j]+')">合并'+(j+1)+',</a>'
						}
						str+=')</b></font>'
					}
					return str;
				}}
			]],
			columns:[[
				{field:'createMan',title:'提交者',align:'center',width:100,sortable:true,formatter: function(value, row) {
				    return row.createManName ;
				}},				
				{field:'writer',title:'撰稿人',align:'center',width:100,sortable:true},
				{field:'status',title:'状态',align:'center',width:80,sortable:true,formatter: function(value, row) {
				    return row.statusName;
				}},
				{field:'pollType',title:'类型',align:'center',width:100,sortable:true,formatter: function(value, row) {
				    return row.pollTypeName;
				}},
				{field:'filingFlg',title:'是否提交审签',align:'center',width:80,sortable:true,formatter: function(value, row) {
					return row.filingFlgName;
				}},
				{field:'editor',title:'编发人',align:'center',width:60,sortable:true,formatter: function(value, row) {
				    return row.editorName;
				}},		
				{field:'master',title:'主送',align:'center',width:150,sortable:true},
				{field:'copyMan',title:'抄送',align:'center',width:300,sortable:true},
				{field:'mergeFlg',title:'是否合并',align:'center',width:60,sortable:true,formatter: function(value, row) {
				    return row.mergeFlgName;
				}},				
				{field:'stressFlg',title:'是否重点',align:'center',width:60,sortable:true,formatter: function(value, row) {
				    return row.stressFlgName;
				}},
				{field:'createTime',title:'提交日期',align:'center',width:150,sortable:true}
				/*{field:'handleType',title:'办理方式',align:'center',width:100,sortable:true,formatter: function(value, row) {
				    return row.handleTypeName;
				}},				
				{field:'handleCompName',title:'办理单位',halign:'center',align:'left',width:200,sortable:false}*/
			]],			
			toolbar: '#tb',
			onDblClickRow : function(rowIndex, rowData) {
				see(rowData.pollId);
			}
		});
		initCombox("poll_queryForm");
	});
	//新增
	function addPoll() {
			p = dj.dialog({
			title : '新增社情民意',
			href : '${pageContext.request.contextPath}/poll!pollAdd.do',
			maximized:true,
			maximizable:true,
			width : 800,
			height : 500,
			iconCls:'icon-save',
			buttons : [ {
					text : ' 提 交 ',
					iconCls:'icon-ok',
					handler : function() {
						add();
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
						//提交人信息列表
						gettjzgrid("");
					});
				}
		});
	}
	function gettjzgrid(url){
		$('#submittergrid').datagrid({
			url:url,
			width:'auto',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			singleSelect:true,
			fitColumns:true,
			columns:[[
				{field:'code',title:'编号',align:'center',width:60,sortable:false},
				{field:'name',title:'提交者',align:'center',width:100,sortable:false},
				{field:'groupName',title:'提交者分组',align:'center',width:100,sortable:false},
				{field:'committeeName',title:'所属专委会',align:'center',width:100,sortable:false},
				{field:'telephone',title:'联系电话',align:'left',width:120,sortable:false},
				{field:'comparyPhone',title:'固定电话',align:'center',width:100,sortable:false},
				{field:'email',title:'电子邮箱',align:'center',width:120,sortable:false},
				{field:'comparyAddress',title:'通讯地址',align:'center',width:200,sortable:false},
				{field:'comparyPost',title:'邮编',align:'center',width:100,sortable:false}
			]],
			toolbar: '#tjr_menu'
		});
		//所有提交人列表
		$('#tjzgrid').datagrid({
			url:'${pageContext.request.contextPath}/comm!getCurSecComm.do',
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
			]],
			toolbar : [ {
				text : '添加',
				iconCls : 'icon-add',
				handler : function() {
					append_add();
				}
				}]
		});
		initCombobox('tjr_queryForm','groupCode','${pageContext.request.contextPath}/dic!combox.do?ctype=GROUP');
	} 
	//添加提案人
	function append_add(){
		var rows=$('#tjzgrid').datagrid('getSelected');
		var wyrows=$('#submittergrid').datagrid('getRows');
		if(rows){
			var flag=0;
			for(var j=0;j<wyrows.length;j++){
				if(rows.code==wyrows[j].code){
					flag=1;
					break;
				}
			}
			if(flag==0){
				$('#submittergrid').datagrid('appendRow',{
					code:rows.code,
					name:rows.name,
					telephone:rows.telephone,
					email:rows.email,
					comparyPhone:rows.comparyPhone,
					comparyAddress:rows.comparyAddress,
					comparyPost:rows.comparyPost,
					groupCode:rows.groupCode,
					groupName:rows.groupName,
					committeeName:rows.committeeName
				});
			}
			parent.dj.messagerAlert('提示', '提案人添加成功！', 'success');	
		}else{
			parent.dj.messagerAlert('提示', '请选择要添加的记录！', 'error');
			return;
		}
	}
	//删除提交人
	function delete_add(){
		var rows=$('#submittergrid').datagrid('getSelections')
		if(rows.length>0){
			for(var i = rows.length-1; i>=0 ;i--){
				var rowindex=$('#submittergrid').datagrid('getRowIndex',rows[i]);
				$('#submittergrid').datagrid('deleteRow',rowindex);
			}
			
		}else{
			dj.messagerAlert('提示', '请选择要删除的记录！', 'error');
		}
	}
	//添加社情民意
	function add(){
		var f = p.find('form');
		$("#content").val(UE.getEditor('editor').getContent()); 
		var tjrdata=$('#submittergrid').datagrid('getRows');
		if(tjrdata.length==0){
			dj.messagerAlert('提示', '请指定社情民意提交者！', 'error');
			return;
		}
		var createMans=[];
		for(var i = 0; i<tjrdata.length ;i++){
			createMans.push(tjrdata[i].code);
		}
		$("#createMan").val(createMans.join(',')); 
		f.form('submit', {
			url : '${pageContext.request.contextPath}/poll!submitAdd.do',
			success : function(d) {
				var json = $.parseJSON(d);
				if (json.success) {
					gridpoll.datagrid('reload');
					gridpoll.datagrid('unselectAll');
					p.dialog('close');
				}
					dj.messagerShow({
					msg : json.msg,
					title : '提示'
				});
			}
		});
	}
	//审查社情民意
	function editPoll(){
		var rows = gridpoll.datagrid('getSelections');
		if (rows.length == 1) {
			if(rows[0].filingFlg=='<%=Constants.CODE_TYPE_YESNO_YES%>'){
				parent.dj.messagerAlert('提示', '选中记录已审查,不能进行审查操作!', 'error');
				return ;
			}
			p = dj.dialog({
				title : '审查社情民意',
				href : '${pageContext.request.contextPath}/poll!pollEdit.do?pollId=' + rows[0].pollId,
				maximized:true,
				maximizable:true,
				width : 800,
				height : 500,
				iconCls:'icon-save',
				buttons : [ {
						text : '提交',
						iconCls:'icon-save',
						handler : function() {
							edit(rows[0]);
						}
					},{ 
						text: '取消', 
						iconCls:'icon-cancel',
						handler: function() { 
							p.dialog('close'); 
						} 
					}],
					onLoad : function() {
						var f = p.find('form');
						$(document).ready(function() {
							//提交人信息列表
						 if(rows[0].pollType!='<%=Constants.DIC_TYPE_POLLTYPE_SHZJ%>'){
							 gettjzgrid('${pageContext.request.contextPath}/poll!submitter.do?createMan=' + rows[0].createMan);
						 }
						 initCombobox('editPollForm','adoptFlg','${pageContext.request.contextPath}/dic!combox.do?ctype=YESNO');
							
						});
					}
			});
		} else if (rows.length > 1) {
			parent.dj.messagerAlert('提示', '同一时间只能编辑一条记录！', 'error');
		} else {
			parent.dj.messagerAlert('提示', '请选择要编辑的记录！', 'error');
		}
	}
	
	function edit(row){
		var f =$("#editPollForm");
		//$("#content").val(UE.getEditor('editor').getContent()); 
		if(row.pollType!='<%=Constants.DIC_TYPE_POLLTYPE_SHZJ%>'){
			var tjrdata=$('#submittergrid').datagrid('getRows');
			if(tjrdata.length==0){
				dj.messagerAlert('提示', '请指定社情民意提交者！', 'error');
				return;
			}
			var createMans=[];
			for(var i = 0; i<tjrdata.length ;i++){
				createMans.push(tjrdata[i].code);
			}
			$("#createMan").val(createMans.join(',')); 
		}
		
		f.form('submit', {
			url : '${pageContext.request.contextPath}/poll!checkEdit.do',
			success : function(d) {
				var json = $.parseJSON(d);
				if (json.success) {
					gridpoll.datagrid('reload');
					gridpoll.datagrid('unselectAll');
					p.dialog('close');
				}
					dj.messagerShow({
					msg : json.msg,
					title : '提示'
				});
			}
		});
	}
	
	//查询
	function _search() {
		gridpoll.datagrid('load', dj.serializeObject($('#poll_queryForm')));
		 $('#win_poll_query').window('close');
	}
	//重置
	function cleanSearch() {
		gridpoll.datagrid('load', {});
		$('#poll_queryForm input').val('');
	}
	
	//提案人过虑
	function tjr_search() {
		$('#tjzgrid').datagrid('load', dj.serializeObject($('#tjr_queryForm')));
	}
	//提交人过虑取消
	function tjr_cleanSearch() {
		$('#tjzgrid').datagrid('load', {});
		$('input').val('');
	}
	//查看社情民意
	function lookPoll(){
		var selectRow = gridpoll.datagrid('getSelected');
		if (selectRow) {
			window.open("${pageContext.request.contextPath}/poll!pollSee.do?pollId="+selectRow.pollId);
		}else{
			dj.messagerAlert('提示', '请选择要查看的记录！', 'error');
		}		
		return false;
	}
	//查看社情民意
	function see(pollId){
		window.open("${pageContext.request.contextPath}/poll!pollSee.do?pollId="+pollId);
	}
	//合并社情民意
	function merge(){
		var rows = gridpoll.datagrid('getSelections');
		var ids=[];
		for(var i = 0; i<rows.length ;i++){
			if(rows[i].filingFlg ==<%=Constants.CODE_TYPE_YESNO_YES%>){
				parent.dj.messagerAlert('提示', '社情民意已提交审签,不能进行合并操作!', 'error');
				return;
			}
			ids.push(rows[i].pollId);
			
		}
		
		if (rows.length > 1) {
			parent.dj.messagerConfirm('请确认', '合并社情民意内容将于第一选中社情民意为主,确定合并?', function(r) {
				if (r) {
					p = dj.dialog({
						title : '合并社情民意',
						href : '${pageContext.request.contextPath}/poll!pollMerge.do',
						width : 520,
						height : 350,
						iconCls:'icon-save',
						buttons : [ {
								text : ' 提 交 ',
								iconCls:'icon-ok',
								handler : function() {
									saveSet('${pageContext.request.contextPath}/poll!setMerge.do');
								}
							},{ 
								text: ' 取 消 ', 
								iconCls:'icon-cancel',
								handler: function() { 
									p.dialog('close'); 
								}
							}],
						onLoad : function() {
							var titles=[];
							for(var i = 1; i<rows.length ;i++){
								titles.push(rows[i].title);
							}
							$('#mergePollForm').form('load', {
								ids:ids.join(',')
						    });
						 	$("#mainPoll").text(rows[0].title);
						 	$("#mergePoll").text(titles.join(','));
						 }
					});	
				}
			});
		} else {
			parent.dj.messagerAlert('提示', '请选择要合并的记录！', 'error');
		}
	}
	//取消合并提案
	function cancelMerge(){
		var selectRow = gridpoll.datagrid('getSelections');
		if (selectRow.length==1) {
			var pollId=selectRow[0].pollId;
			if(selectRow[0].mergeFlg=='1'){
				var ids=selectRow[0].mergeIds.split(",");
				parent.dj.messagerConfirm('请确认', '取消合并的社情民意,将使主社情民意、各参考意见作为单独社情民意，确定取消合并?', function(r) {
					if (r) {
						 $.ajax({
								type:"POST",
								url:"${pageContext.request.contextPath}/poll!cancelMerge.do",
								data:{
									pollId:pollId,
									ids:ids
								},
								success:function(d){
									var json = $.parseJSON(d);
									if (json.success) {
										gridpoll.datagrid('clearChecked');
										gridpoll.datagrid('clearSelections');
										gridpoll.datagrid('reload');
									}
									dj.messagerShow({
										msg : json.msg,
										title : '提示'
									});
								}
						});
					}
				});
			} else {
				parent.dj.messagerAlert('提示', '请选择一件已合并的社情民意！', 'error');
			}
		} else {
			parent.dj.messagerAlert('提示', '请选择一件要取消合并的社情民意！', 'error');
		}		
	}
	
	function saveSet(url){
		var f = p.find('form');
		f.form('submit', {
			url : url,
			success : function(d) {
				var json = $.parseJSON(d);
				if (json.success) {
					gridpoll.datagrid('reload');
					gridpoll.datagrid('unselectAll');
					p.dialog('close');
				}
					dj.messagerShow({
					msg : json.msg,
					title : '提示'
				});
			}
		});
	
	}
	//重点提案设置
	function setStress(){
		var rows = gridpoll.datagrid('getSelections');
		if (rows.length == 1) {
			
			p = dj.dialog({
				title : '重点社情民意设置',
				href : '${pageContext.request.contextPath}/poll!pollStress.do',
				width : 520,
				height : 280,
				iconCls:'icon-save',
				buttons : [ {
						text : ' 提 交 ',
						iconCls:'icon-ok',
						handler : function() {
							saveSet('${pageContext.request.contextPath}/poll!setStress.do');
						}
					},{ 
						text: ' 取 消 ', 
						iconCls:'icon-cancel',
						handler: function() { 
							p.dialog('close'); 
						} 
					}],
				onLoad : function() {
					initCombobox('stressPollForm','stressFlg','${pageContext.request.contextPath}/dic!combox.do?ctype=YESNO');
					var f = p.find('form');
					f.form('load', {
						pollId:rows[0].pollId,
				    });
				 	$("#pollCode").text(rows[0].pollCode);
				 	$("#secondaryYear").text(rows[0].tsecondary.year);
				 	$("#tl").text(rows[0].title);
				 	$("#pType").text(rows[0].pollTypeName);
				 	$("#cMan").text(rows[0].createManName);
				 }
			});	
	 	}else if (rows.length > 1) {
			parent.dj.messagerAlert('提示', '同一时间只能设置一条记录！', 'error');
		} else {
			parent.dj.messagerAlert('提示', '请选择要设置的记录！', 'error');
		}
	
	}
	//报表
	function report(){
		var totalRowNum = gridpoll.datagrid('getPager').data("pagination").options.total;
		if(totalRowNum>0){
			$.ajax({
				url : '${pageContext.request.contextPath}/poll!report.do',
				success : function(d) {
					var json = $.parseJSON(d);
					if (json.success) {
						window.location.href=json.obj;
						/*$.ajax({
							url : '${pageContext.request.contextPath}/prop!getDownloadFile.do?fileName='+json.obj
						});*/
					}else{
						dj.messagerShow({
							msg : json.msg,
							title : '提示'
						});
					}
				}
			});
		}else{
			$.messager.alert('提示', '当前无记录，无需导出数据！', 'info');
		}
	}
	//提交审签
	function setFiling(){
		var rows = gridpoll.datagrid('getSelections');
		var ids=[];
		if (rows.length >0) {
			for(var i = 0; i<rows.length ;i++){
				if(rows[i].adoptFlg!=<%=Constants.CODE_TYPE_YESNO_YES%>){
					dj.messagerShow({
						msg : "所选社情民意中有未进行筛选审查的，不能进行提交审签操作!",
						title : '提示'
					});
					return;
				}
				ids.push(rows[i].pollId);
			}
			parent.dj.messagerConfirm('请确认', '您要确定提交审签所选社情民意?', function(r) {
					if (r) {
						$.ajax({
							url : '${pageContext.request.contextPath}/poll!setFiling.do',
							data : {
								ids : ids.join(',')
							},
							dataType : 'json',
							success : function(d) {
								gridpoll.datagrid('load');
								gridpoll.datagrid('unselectAll');
								parent.dj.messagerShow({
									title : '提示',
									msg : d.msg
								});
								
							}
						});
					}
			});
		} else {
			parent.dj.messagerAlert('提示', '请选要提交审签的社情民意！', 'error');
		}
	}
	
	//定稿整理
	function finalVersion(){
		var rows = gridpoll.datagrid('getSelections');
		if (rows.length == 1) {
			if(rows[0].filingFlg!='<%=Constants.CODE_TYPE_YESNO_YES%>'){
				parent.dj.messagerAlert('提示', '选中记录未提交审签,不能进行定稿操作!', 'error');
				return ;
			}
			if(rows[0].issueFlg=='<%=Constants.CODE_TYPE_YESNO_YES%>'){
				parent.dj.messagerAlert('提示', '选中记录已印发,不能进行定稿操作!', 'error');
				return ;
			}
			p = dj.dialog({
				title : '社情民意定稿整理',
				href : '${pageContext.request.contextPath}/poll!pollEdit.do?pollId=' + rows[0].pollId,
				maximized:true,
				maximizable:true,
				width : 800,
				height : 500,
				iconCls:'icon-save',
				buttons : [ {
						text : '提交',
						iconCls:'icon-save',
						handler : function() {
							confirmSave(rows[0]);
						}
					},{ 
						text: '取消', 
						iconCls:'icon-cancel',
						handler: function() { 
							p.dialog('close'); 
						} 
					}],
					onLoad : function() {
						var f = p.find('form');
						$(document).ready(function() {
							//提交人信息列表
						 if(rows[0].pollType!='<%=Constants.DIC_TYPE_POLLTYPE_SHZJ%>'){
							 gettjzgrid('${pageContext.request.contextPath}/poll!submitter.do?createMan=' + rows[0].createMan);
						 }
						 initCombobox('editPollForm','adoptFlg','${pageContext.request.contextPath}/dic!combox.do?ctype=YESNO');
							
						});
					}
			});
		} else if (rows.length > 1) {
			parent.dj.messagerAlert('提示', '同一时间只能编辑一条记录！', 'error');
		} else {
			parent.dj.messagerAlert('提示', '请选择要编辑的记录！', 'error');
		}
	}
	//定稿保存
	function confirmSave(row){
		var f =$("#editPollForm");
		//$("#content").val(UE.getEditor('editor').getContent()); 
		if(row.pollType!='<%=Constants.DIC_TYPE_POLLTYPE_SHZJ%>'){
			var tjrdata=$('#submittergrid').datagrid('getRows');
			if(tjrdata.length==0){
				dj.messagerAlert('提示', '请指定社情民意提交者！', 'error');
				return;
			}
			var createMans=[];
			for(var i = 0; i<tjrdata.length ;i++){
				createMans.push(tjrdata[i].code);
			}
			$("#createMan").val(createMans.join(',')); 
		}
		f.form('submit', {
			url : '${pageContext.request.contextPath}/poll!confirmSave.do',
			success : function(d) {
				var json = $.parseJSON(d);
				if (json.success) {
					gridpoll.datagrid('reload');
					gridpoll.datagrid('unselectAll');
					p.dialog('close');
				}
					dj.messagerShow({
					msg : json.msg,
					title : '提示'
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
			<table id="pollgrid"></table>
		</div>
	</div>
	<div id="columnmenu" class="easyui-menu" style="width:100px;"></div>

	<div id="tb" style="padding:2px;height:auto">
		<div style="margin-bottom:1px">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="$('#win_poll_query').window('open')">查询</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addPoll()">新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editPoll()">筛选审查</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-view" plain="true" onclick="lookPoll()">查看</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-merge" plain="true" onclick="merge()">合并</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="cancelMerge()">取消合并</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-submit" plain="true" onclick="setFiling()">提交审签</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-key" plain="true" onclick="setStress()">设置重点</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="finalVersion()">定稿整理</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="report()">报表</a>
		</div>
	</div>
	<div id="win_poll_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:500px;height:260px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="poll_queryForm">
				<input type="hidden" name="filingFlg" id="filingFlgId"/>
				<table id="poll_queryTable">
					<tr height="25">
						<td nowrap>社情民意编号：</td>
						<td><input type="text" name="pollCode" style="width:300px;padding:2px;border:1px solid #000;"></td>
					</tr>				
					<tr height="25">
						<td nowrap>标题：</td>
						<td><input type="text" name="title" style="width:300px;padding:2px;border:1px solid #000;"></td>
					</tr>
					<tr height="25">
						<td nowrap>提交者：</td>
						<td> <input type="text" name="createMan" style="width:300px;padding:2px;border:1px solid #000;"></td>
					</tr>
					<tr height="25">
						<td nowrap>状态：</td>
						<td> <input id="status" panelHeight="100px" name="status" value="请选择..." style="width:300px;padding:2px;border:1px solid #000;"></td>
					</tr>
				</table>
				</form>
				</center>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;height:40px">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)" onclick="_search()">查询</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)" onclick="cleanSearch()">重置</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#win_poll_query').window('close');">取消</a>
			</div>
		</div>
	</div>
</body>
</html>
