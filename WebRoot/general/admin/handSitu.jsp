<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	function initCombox(form){
		initCombobox(form,'solveHow','${pageContext.request.contextPath}/dic!combox.do?ctype=JJCD');
		initCombobox(form,'carryoutFlg','${pageContext.request.contextPath}/dic!combox.do?ctype=YESNO');
		initCombobox(form,'committeemanOpinion','${pageContext.request.contextPath}/dic!combox.do?ctype=WYYJ');
		initCombobox(form,'handleType','${pageContext.request.contextPath}/dic!combox.do?ctype=BLLX');
		initCombobox(form,'stutas','${pageContext.request.contextPath}/dic!combox.do?ctype=TAZT');
	}
	
	var datagrid;	
	$(document).ready(function() {
		datagrid=$('#listgrid');
		var startType='<%=request.getAttribute("statusType")%>';
		var companycode='<%=request.getAttribute("companyId")%>';
		var fieldCode='<%=request.getAttribute("field")%>';
		var url='${pageContext.request.contextPath}/prop!situdatagrid.do?submitFlg=1';
		if(startType!=""&& startType!=null){
			
			if(startType=='I'){
				url+='&stutas=7';
			}
			if(startType=='G'){
				url+='&replyPass=0';
			}
			if(startType=='H'){
				url+='&stutas=4&replyPass=1';
			}
		}
		if(companycode!="" && companycode!="null"){
			url+="&companyId="+companycode;
		}
		if(fieldCode=="tote_all"){
			url+="&handleType=tote_all";
		}
		if(fieldCode=="tote_no"){
			url+="&handleType=tote_no";
		}
		if(fieldCode=="single_all"){
			url+="&handleType=single_all";
		}
		if(fieldCode=="single_no"){
			url+="&handleType=single_no";
		}
		if(fieldCode=="branch_all"){
			url+="&handleType=branch_all";
		}
		if(fieldCode=="branch_no"){
			url+="&handleType=branch_all";
		}
		if(fieldCode=="host_all"){
			url+="&handleType=host_all";
		}
		if(fieldCode=="host_no"){
			url+="&handleType=host_no";
		}
		datagrid.datagrid({
			url:url,
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
			sortName : 'proposalId',
			sortOrder : 'asc',
			fit:true,
			columns:[[
				{field:'proposalCode',title:'提案编号',align:'center',width:80},
				{field:'title',title:'案&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;由',halign:'center',align:'left',width:420,formatter:function(val,rec){
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
				{field:'proposalTypeName',title:'提案类型',align:'center',width:90,hidden:true},
				{field:'fistProposaler',title:'第一提案人',align:'center',width:90},				
				{field:'proposalerNum',title:'提案人数',align:'center',width:60,hidden:true},
				{field:'handleTypeName',title:'办理类型',align:'center',width:70},				
				{field:'demandEnddate',title:'要求办结日期',align:'center',width:90},
				//{field:'submitFlgName',title:'提交状态 ',align:'center',width:60},
				{field:'stutasName',title:'状态',align:'center',width:50,sortable:true,formatter:function(val,rec){
					var str=val;
					if(rec.stutas=='2'&&rec.fileMethod=='0'){
						str="已立案";
					}
					if(rec.stutas=='2'&&rec.fileMethod=='1'){
						str="未立案";
					}
					if(rec.stutas=='2'&&rec.fileMethod=='0'&&rec.filingFlg=='1'){
						str="未交办";
					}
					if(rec.filingFlg=='1'&&rec.submitFlg=='1'){
						str="已交办";
					}
					if(rec.submitFlg=='1'&&rec.replyPass=='0'){
						str="未答复";
					}
					if(rec.stutas=='4'&&rec.submitFlg=='1'&& rec.replyPass=='1'){
						str="已答复";
					}
					return str;
				}},
				{field:'comps',title:'办理单位',halign:'center',align:'left',width:200,sortable:false},
				{field:'solveHowName',title:'解决程度',align:'center',width:180},
				//{field:'carryoutFlgName',title:'是否落实 ',align:'center',width:60},
				{field:'committeemanOpinionName',title:'提案人意见',align:'center',width:90}
			]],				
			toolbar : [{
				text : '查询',
				iconCls : 'icon-search',
				handler : function() {
					query();
				}
			}, '-',  {
				text : '答复情况',
				iconCls : 'icon-reply',
				handler : function() {
					see();
				}
			}, '-', {
				text : '查看提案',
				iconCls : 'icon-view',
				handler : function() {
					view();
				}
			}, '-', {
				text : '统计报表',
				iconCls : 'icon-export',
				handler : function() {
					countreport();
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

	function see() {
		var row = datagrid.datagrid('getSelected');
		if (row) {
			var propId=row.proposalId;
			window.open("${pageContext.request.contextPath}/hand!handSee.do?proposalId="+propId);
		}else{
			dj.messagerAlert('提示', '请选择要查看的提案！', 'error');
		}		
		return false;
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
	//报表
	function countreport(){
		
		var totalRowNum = datagrid.datagrid('getPager').data("pagination").options.total;
		if(totalRowNum>0){
			$.ajax({
				url : '${pageContext.request.contextPath}/prop!assigncountreport.do',
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
</script>
</head>

<body class="easyui-layout">
	<!-- 查询窗口-->
	<div id="win_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:550px;height:300px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="searchForm">
				<table id=tableForm>				
					<tr height="30">
						<td nowrap>提案编号：</td>
						<td><input type="text" name="proposalCode" style="width:360px;padding:2px"></td>
					</tr>
					<tr height="30">
						<td nowrap>案&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;由：</td>
						<td> <input type="text" name="title" style="width:360px;padding:2px"></td>
					</tr>
					<tr height="30">
						<td nowrap>第一提案人：</td>
						<td> <input type="text" name="fistProposaler" style="width:360px;padding:2px"></td>
					</tr>
					<tr height="30">
						<td nowrap>承办单位：</td>
						<td> <input type="text" name="companyName" style="width:360px;padding:2px"></td>
					</tr>
					<tr height="30">
						<td nowrap>办理类型：</td>
						<td> <input  name="handleType" style="width:160px;padding:2px"></td>
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
		<div onclick="see();" data-options="iconCls:'icon-reply'">答复情况</div>
		<!-- <div onclick="change();" data-options="iconCls:'icon-edit'">调整单位</div>
		<div onclick="refuse();" data-options="iconCls:'icon-refuse'">拒绝申退</div> -->
		<div onclick="view();" data-options="iconCls:'icon-view'">查看提案</div>
	</div>
</body>
</html>