<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	function initCombox(form){
		initCombobox(form,'handleType','${pageContext.request.contextPath}/dic!combox.do?ctype=BLLX');
	}
	
	var datagrid;	
	$(document).ready(function() {
		datagrid=$('#listgrid');
		
		datagrid.datagrid({
			url:'${pageContext.request.contextPath}/prop!pdatagrid.do',
			width: 'auto',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			singleSelect:true,
			pagination:true,
			pageSize:10,
			pageList:[10,15,20],
			pageNumber:1,
			idField:'proposalId',
			fit:true,
			columns:[[
				{field:'proposalCode',title:'提案编号',align:'center',width:60,sortable:true},
				{field:'title',title:'案&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;由',halign:'center',align:'left',width:420,sortable:true,formatter:function(val,rec){
					var str=val;
					
					if(rec.mergeFlag=='1'){
						var ids=rec.mergeIds.split(",");
						str+='<font color="#0000CD"><b>    ('
						for(var j=0;j<ids.length;j++){
							str+='<a  href="javascript:void(0)" onclick="see('+ids[j]+')">并案'+(j+1)+',</a>'
						}
						str+=')</b></font>'
					}
					return str;
				}},
				{field:'fistProposaler',title:'第一提案人',align:'center',width:90,sortable:true},				
				{field:'proposalerNum',title:'提案人数',align:'center',width:65,sortable:true},
				{field:'stutasName',title:'状态',align:'center',width:50,sortable:true,formatter:function(val,rec){
					var str=val;
					if(rec.stutas=='2'&&rec.fileMethod=='0'){
						str="已立案";
					}
					if(rec.stutas=='2'&&rec.fileMethod=='1'){
						str="未立案";
					}
					if(rec.stutas=='2'&&rec.fileMethod=='0'&&rec.submitFlg=='0'){
						str="未交办";
					}
					if(rec.replyPass!='1'&&rec.submitFlg=='1'){
						str="已交办";
					}
					if(rec.stutas=='4'&& rec.replyPass=='1'){
						str="已答复";
					}
					if(rec.stutas=='4'&&rec.replyPass!='1'){
						str="未答复";
					}
					return str;
				}},
				//{field:'proposalTypeName',title:'提案类型',align:'center',width:75,sortable:true},
				{field:'secondFlgName',title:'可否附议',align:'center',width:65,sortable:true},
				{field:'propTypeName',title:'内容分类',align:'center',width:80,sortable:true},
				{field:'excellentFlgName',title:'优秀提案',align:'center',width:60,sortable:true},
				{field:'stressFlgName',title:'重点提案',align:'center',width:60,sortable:true},
				{field:'leader',title:'督办领导',align:'center',width:80,sortable:true},
				{field:'mergeFlagName',title:'合并提案',align:'center',width:60,sortable:true}
			]],				
			toolbar : [{
				text : '查询',
				iconCls : 'icon-search',
				handler : function() {
					query();
				}
			}, '-',  {
				text : '新增附议',
				iconCls : 'icon-add',
				handler : function() {
					add();
				}
			}, '-', {
				text : '查看',
				iconCls : 'icon-view',
				handler : function() {
					view();
				}
			}],
			onRowContextMenu : function(e, rowIndex, rowData) {
				e.preventDefault();
				$(this).datagrid('unselectAll');
				$(this).datagrid('selectRow', rowIndex);
				$('#menu').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			}
		});
		//以下初始化查询combox列表
		initCombox('searchForm');
	});

	function add() {
		var row = datagrid.datagrid('getSelected');
		if (row) {
			var p = dj.dialog({
				title : '新增附议',
				href :'${pageContext.request.contextPath}/sponsor!proserAE.do',
				width : 680,
				height : 400,
				buttons : [ {
					text : '附议',
					align: 'center',
					handler : function() {
						var f = p.find('form');
						f.form('submit', {
							url : '${pageContext.request.contextPath}/sponsor!add.do',
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
				} ,
				{
					text : '关闭',
					iconCls:'icon-cancel',
					handler : function() {
						p.dialog('close');
					}
				}],
				onLoad : function() {
					initCombox('addForm');
					$('#proposalId').val(row.proposalId);
					$('#proposalCode').text(row.proposalCode);
					//$('#secondayName').text(row.tsecondary.secondayName);
					$('#title').text(row.title);
					$('#proposalTypeName').text(row.proposalTypeName);
					$('#fistProposaler').text(row.fistProposaler);
				}
			});
		}else{
			dj.messagerAlert('提示', '请选择要附议的记录！', 'info');
		}
	}
	
	function view(){
		var selectRow = datagrid.datagrid('getSelected');
		if (selectRow) {
			var propId=selectRow.proposalId;
			window.open("${pageContext.request.contextPath}/prop!propSee.do?proposalId="+propId);
		}else{
			dj.messagerAlert('提示', '请选择要查看的提案！', 'error');
		}		
		return false;
	}

	function query() {
		$('#searchForm').form('clear');
		$('#win_query').window('open');
	}
	
	function _search() {	//此处方法不能为search()
		datagrid.datagrid('load', dj.serializeObject($('#searchForm')));
		$('#win_query').window('close');
	}
	
	function cleanSearch() {
		gridlist.datagrid('load', {});
		$("input,textarea,select").val("");
	}

</script>
</head>

<body class="easyui-layout">
	<!-- 查询窗口-->
	<div id="win_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:550px;height:200px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="searchForm">
				<table id=tableForm>				
					<tr height="25">
						<td nowrap>提案编号：</td>
						<td><input type="text" name="proposalCode" style="width:360px;padding:2px"></td>
					</tr>
					<tr height="25">
						<td nowrap>案&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;由：</td>
						<td> <input type="text" name="title" style="width:360px;padding:2px"></td>
					</tr>
					<tr height="25">
						<td nowrap>第一提案人：</td>
						<td> <input type="text" name="fistProposaler" style="width:360px;padding:2px"></td>
					</tr>
				</table>
				</form>
				</center>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;height:40px">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)" onclick="_search()">查询</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)" onclick="cleanSearch()">重置</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#win_query').window('close');">取消</a>
			</div>
		</div>
	</div>
	<!-- Datagrid表格 -->
	<div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="listgrid"></table>
	</div>
	<!-- 菜单 -->
	<div id="menu" class="easyui-menu" style="width:120px;display: none;">
		<div onclick="add();" data-options="iconCls:'icon-add'">新增附议</div>
		<div onclick="view();" data-options="iconCls:'icon-view'">查看</div>
	</div>
</body>
</html>