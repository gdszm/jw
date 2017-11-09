<%@page import="com.tlzn.util.base.Constants"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	function initCombox(form){
	//	initCombobox(form,'status','${pageContext.request.contextPath}/dic!combox.do?ctype=PUBSTATUS');
	//	initCombobox(form,'speechType','${pageContext.request.contextPath}/dic!combox.do?ctype=NOTICETYPE');
	}
	var p;
	var datagrid;
	$(function(){
	
	//状态设置
	var startType='<%=request.getAttribute("statusType")%>';
		var status;
		var useSituation;
		if(startType!=""&& startType!=null){
			//已保存
			if(startType=='A'){
				$("#statusId").val("0");
				status="0";
			}
			//未审查
			if(startType=='B'){
				$("#statusId").val("1");
				status="1";
			}
			//已审查-书面发言
			if(startType=='C'){
				$("#statusId").val("2");
				$("#useSituationId").val("1");
				status="2";
				useSituation="1";
			}
			//已审查-口头发言
			if(startType=='D'){
				$("#statusId").val("2");
				$("#useSituationId").val("2");
				status="2";
				useSituation="2";
			}
			//已审查-转弃发言
			if(startType=='E'){
				$("#statusId").val("2");
				$("#useSituationId").val("3");
				status="2";
				useSituation="3";
			}
			//定稿-书面发言
			if(startType=='F'){
				$("#statusId").val("3");
				$("#useSituationId").val("1");
				status="3";
				useSituation="1";
			}
			//定稿-口头发言
			if(startType=='G'){
				$("#statusId").val("3");
				$("#useSituationId").val("2");
				status="3";
				useSituation="2";
			}
		}
		
		initCombox("searchForm"); 
		initCombox("speechDatagrid");
		initCombox("speech_queryForm");
		datagrid = $('#speechDatagrid').datagrid({
			url:'${pageContext.request.contextPath}/speech!datagridown.do',
        	width: 'auto',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			idField:'speakId',
 			singleSelect:true,
			pagination:true,
			pageSize:20,
			pageList:[20,50],
			pageNumber:1,
			fit:true,
			fitColumns :true,
			checkOnSelect:false,
			selectOnCheck:false,
			queryParams: {
				status: status,
				useSituation: useSituation
			},
			frozenColumns:[[
				{field:'speakId',title:'ID',width:100,align:'center',checkbox : true},
				{field:'title',title:'发言主题',width:400,align:'center'}
				]],
			columns:[[
				//{field:'content',title:'发言内容',width:100,align:'center'},
				{field:'meetName',title:'所属会议',width:100,align:'center'},
				{field:'useSituationName',title:'采用情况',width:100,align:'center'},
				{field:'statusName',title:'状态',width:100,align:'center'},
				{field:'createTime',title:'提交日期',width:100,align:'center',
					formatter: function(value,row,index){
						if (value){
							return value.substring(0,19);
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
					$('#win_speech_query').window('open');
				}
			}, '-', {
				text : '新增',
				iconCls : 'icon-add',
				handler : function() {
					pub();
				}
			}
			, '-', {
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
				text : '提交发言',
				iconCls : 'icon-enable',
				handler : function() {
					submitSpeech();
				}
			}, '-', {
				text : '撤销提交',
				iconCls : 'icon-stop',
				handler : function() {
					cancelSubmitSpeech();
				}
			}, '-', {
				text : '取消选中',
				iconCls : 'icon-undo',
				handler : function() {
					datagrid.datagrid('clearSelections');
					datagrid.datagrid('clearChecked');
					datagrid.datagrid('unselectAll');
				}
			}, '-',
			{
				text : '查看详细',
				iconCls : 'icon-info',
				handler : function() {
					lookSpeech();
				}
			}
			],
			onDblClickRow : function(rowIndex, rowData) {
				lookSpeechOnDblClick(rowData.speakId);
			}
		});
	});
	
	//修改 
	function edit() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			//是否可有进行修改操作
			var canDel=true;
			var ybc='<%=Constants.CODE_TYPE_SPEECH_STATUS_YBC%>';
			if(rows[0].status!=ybc ) canDel=false;
				if(canDel) {
					var p = dj.dialog({
						title : ' 发言修改 ',
						href : '${pageContext.request.contextPath}/speech!speechEdit.do?speakId='+rows[0].speakId,
						maximized:true,
						maximizable:true,
						width : 800,
						height : 600,
						buttons : [ {
							text : ' 提交修改 ',
							iconCls : 'icon-edit',
							handler : function() {
								var f =$("#speechEditForm"); 
								$("#content").val(UE.getEditor('editor').getContent());
								add();
								f.form('submit', {
									url : '${pageContext.request.contextPath}/speech!edit.do',
									success : function(d) {
										var json = $.parseJSON(d);
										if (json.success) {
											dj.messagerShow({
											msg : json.msg,
											title : '提示'
											});
											p.dialog('close');
											datagrid.datagrid('load');
										}
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
						],
						onLoad : function() {
							var f = p.find('form');
							$(document).ready(function() {
								//会议选择初始化
								getmeetinggrid("");
							});
						}
					});
				} else {
					parent.dj.messagerAlert('提示', '只能修改已保存的发言！', 'error');
				}
		} else if (rows.length > 1) {
			parent.dj.messagerAlert('提示', '同一时间只能编辑一条记录！', 'error');
		} else {
			parent.dj.messagerAlert('提示', '请选择要编辑的记录！', 'error');
		}
	}
	function add(){
		var attNames=[];
   	 	var FileCount=parseInt($("#FileCount").val(),10);
   		for(var i=1;i<=FileCount;i++){
			  attNames.push($('#f'+i).val());
   	 	}
   		$('#attNames').val(attNames.join(','));
	}
	//删除
	function remove() {
		var rows = datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			
			//是否可有进行删除操作
			var canDel=true;
			var wsh='<%=Constants.CODE_TYPE_SPEECH_STATUS_WSC%>';
			for ( var h = 0; h < rows.length; h++) {
				if(rows[h].status==wsh ) canDel=false;
			}
			if(canDel) {
				parent.dj.messagerConfirm('请确认', '您要删除当前所选勾选发言吗？', function(r) {
					if (r) {
						for ( var i = 0; i < rows.length; i++) {
							ids.push(rows[i].speakId);
						}
						$.ajax({
							url : '${pageContext.request.contextPath}/speech!delete.do',
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
				parent.dj.messagerAlert('提示', '只能删除未提交的发言，请重新勾选要删除的发言！', 'error');
			}
		} else {
			parent.dj.messagerAlert('提示', '请勾选要删除的发言！', 'error');
		}
	}
	//新增会议发言
	function pub(){
		p = dj.dialog({
			title : '新增会议发言',
			href : '${pageContext.request.contextPath}/speech!speechAdd.do',
			maximized:true,
			maximizable:true,
			width : 800,
			height : 500,
			iconCls:'icon-save',
			buttons : [ {
				text : ' 保存 ',
				iconCls:'icon-save',
				handler : function() {
					saveSpeech();
				}
				},{
					text : ' 提 交 ',
					iconCls:'icon-ok',
					handler : function() {
						newSubmitSpeech();
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
						//var editor = UE.getEditor('editor'); ==>解决发布社情民意不渲染问题，改成以下两行
						var editor = new baidu.editor.ui.Editor();
						editor.render('editor');

						editor.addListener('ready',function(){
							 this.setContent('');
						});
						//会议选择初始化
						getmeetinggrid("");
					});
				}
		});
	}

	//会议选择部分 开始
	function getmeetinggrid(url){
		//当前届次未开会议选择列表
		$('#meetinggrid').datagrid({
			url:'${pageContext.request.contextPath}/meeting!getCurSecMeeting.do',
			width: 'auto',
			height:'auto',
			striped:true,
			nowrap:true,
			pagination:true,
			pageSize:10,
			singleSelect:true,
			pageList:[10,15,20],
			rownumbers:true,
			sortName: 'meetId',
			sortOrder: 'asc',
			idField:'meetId',
			fit:true,
			columns:[[
				{field:'meetId',title:'会议编号',checkbox:true,align:'center',width:40},
				{field:'meetName',title:'会议名称',align:'left',width:120,sortable:false},
				{field:'shortName',title:'会议简称',align:'left',width:120},
				{field:'meetTypeName',title:'会议类型',align:'left',width:120}
			]]
		});
		initCombobox('meet_queryForm','meetType','${pageContext.request.contextPath}/dic!combox.do?ctype=MEETINGTYPE');
	}
	//会议选择部分 结束

	//"新增"=>"保存"
	function saveSpeech(){
		var f =$("#speechAddForm");
		//附件隐藏变量赋值
		add();
		f.form('submit', {
			url : '${pageContext.request.contextPath}/speech!save.do',
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

	//"新增"=>"提交"
	function newSubmitSpeech(){
				var f =$("#speechAddForm"); 
				$("#content").val(UE.getEditor('editor').getContent());
				add();
				f.form('submit', {
					url : '${pageContext.request.contextPath}/speech!upDateOrAdd.do',
					success : function(d) {
						var json = $.parseJSON(d);
						if (json.success) {
							parent.dj.messagerConfirm('操作提示','提交成功！您是否继续提交新发言?',function(ok){  
								if (ok){
									//window.location.href="${pageContext.request.contextPath}/notice!noticeAdd.do";
									document.getElementById("speechAddForm").reset();
									//$('#fromtypeid').combobox('setValue', 1);
									UE.getEditor('editor').setContent('');
								}else{
									datagrid.datagrid('reload');
									p.dialog('close');
								}
							});

						}
					}
			
				});
	}
	//"提交发言"
	function submitSpeech(){
//		var rows = datagrid.datagrid('getSelected');
//		if (rows!=null) {
		var rows = datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			for ( var i = 0; i < rows.length; i++) {
				ids.push(rows[i].speakId);
			}
			//if(rows.status!=<%=Constants.CODE_TYPE_SPEECH_STATUS_YBC%>){
			//	parent.dj.messagerAlert('提示', '选中记录已提交,不需要再次提交!', 'error');
			//	return ;
			//}
			parent.dj.messagerConfirm('请确认', '确定要进行提交所勾选发言吗，提交后，发言不能再进行修改！', function(r) {
				if (r) {
					$.ajax({
						url : '${pageContext.request.contextPath}/speech!submit.do',
						data : {
							ids : ids.join(',')
						},
						success : function(d) {
							var json = $.parseJSON(d);
							if (json.success) {
								datagrid.datagrid('reload');
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
			parent.dj.messagerAlert('提示', '请勾选要提交的记录！', 'error');
		}
	}
	
	//"撤销提交"
	function cancelSubmitSpeech(){
		var rows = datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			for ( var i = 0; i < rows.length; i++) {
				ids.push(rows[i].speakId);
			}
			parent.dj.messagerConfirm('请确认', '确定要进行撤销提交所勾选发言吗！', function(r) {
				if (r) {
	
					 $.ajax({
						type:"POST",
						url:"${pageContext.request.contextPath}/speech!cancelSubmit.do",
						data:{
							ids : ids.join(',')
						},
						success:function(d){
							var json = $.parseJSON(d);
							if (json.success) {
								datagrid.datagrid('reload');
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
			parent.dj.messagerAlert('提示', '请勾选要撤销的记录！', 'error');
		}
	}

	//查看
	function lookSpeech() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			var p = dj.dialog({
				title : '会议发言详情',
				href : '${pageContext.request.contextPath}/speech!speechSee.do?speakId='+rows[0].speakId,
				maximized:true,
				maximizable:true,
				width : 800,
				height : 600,
				onLoad : function() {
					initCombox('speechViewForm');
					var f = p.find('form');
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
			parent.dj.messagerAlert('提示', '请选择一条要查看的记录！', 'error');
		}
	}
	
	//双击行查看社情民意
	function lookSpeechOnDblClick(speakId){
		if (speakId) {
//			window.open("${pageContext.request.contextPath}/speech!speechSee.do?speakId="+speakId);
			var p = dj.dialog({
				title : '会议发言详情',
				href : '${pageContext.request.contextPath}/speech!speechSee.do?speakId='+speakId,
				maximized:true,
				maximizable:true,
				width : 800,
				height : 600,
				onLoad : function() {
					initCombox('speechViewForm');
					var f = p.find('form');
				},
				buttons : [ {
					text: '关闭 ', 
					iconCls:'icon-cancel',
					handler: function() { 
					p.dialog('close'); 
					} 
				}]
			});
		}else{
			dj.messagerAlert('提示', '请双击要查看的记录！', 'error');
		}		
		return false;
	}
	//查询
	function _search() {
		datagrid.datagrid('load', dj.serializeObject($('#speech_queryForm')));
		 $('#win_speech_query').window('close');
	}
	
	function cleanSearch() {
		datagrid.datagrid('load', {});
		$('#speech_queryForm input').val('');
	}
</script>
</head>
<body class="easyui-layout" data-options="fit:true">
	<!-- 表格 -->
	<div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="speechDatagrid"></table>
	</div>
	<div id="win_speech_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:500px;height:274px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="speech_queryForm">
				<input type="hidden" name="useSituation" id="useSituationId"/>
				<input type="hidden" name="status" id="statusId"/>
				<table id="speech_queryTable">
					<tr height="25">
						<td nowrap>发言主题：</td>
						<td><input type="text" name="title" class="easyui-validatebox" style="width:280px;padding:2px;"></td>
					</tr>			
					<tr height="25">
						<td nowrap>所属会议：</td>
						<td><input type="text" name="meetName" class="easyui-validatebox" style="width:280px;padding:2px;"></td>
					</tr>
					<tr height="25">
						<td nowrap>提交时间：</td>
						<td> <input class="easyui-datebox" name="createTimeStart"   style="width:137px"/>--<input class="easyui-datebox" name="createTimeEnd"  style="width:137px"/></td>
					</tr>
					
				</table>
				</form>
				</center>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;height:40px">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)" onclick="_search()">查询</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)" onclick="cleanSearch()">重置</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#win_speech_query').window('close');">取消</a>
			</div>
		</div>
	</div>
</body>
</html>