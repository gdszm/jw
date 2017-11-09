<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" charset="utf-8">
function initCombox(form){
	initCombobox(form,'sex','${pageContext.request.contextPath}/dic!combox.do?ctype=SEX');
	initCombobox(form,'nation','${pageContext.request.contextPath}/dic!combox.do?ctype=NATION');
	initCombobox(form,'partyCode','${pageContext.request.contextPath}/dic!combox.do?ctype=PARTY');
	initCombobox(form,'circleCode','${pageContext.request.contextPath}/dic!combox.do?ctype=CIRCLE');
	initCombobox(form,'eduCode','${pageContext.request.contextPath}/dic!combox.do?ctype=EDUCATION');
	initCombobox(form,'degreeCode','${pageContext.request.contextPath}/dic!combox.do?ctype=DEGREE');
	initCombobox(form,'status','${pageContext.request.contextPath}/dic!combox.do?ctype=STATUS');
	//专委会
	initCombobox(form,'committee','${pageContext.request.contextPath}/dic!combox.do?ctype=COMMITTEE');
}
	var datagrid;	
	$(document).ready(function() {
		datagrid=$('#listgrid');
		
		datagrid.datagrid({
			url:'${pageContext.request.contextPath}/committee!doNotNeedAuth_currentDatagrid.do',
			width: 'auto',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			singleSelect:true,
			pagination:true,
			pageSize:20,
			pageList:[20,50,100],
			pageNumber:1,
			idField:'code',
			fit:true,
			columns:[[
				{field:'name',title:'委员',align:'center',width:100},
				{field:'sexName',title:'性别',align:'center',width:40},
				{field:'nationName',title:'民族',align:'center',width:60},
				{field:'birthDate',title:'出生日期',align:'center',width:100},
				{field:'partyName',title:'党派',align:'center',width:150},
				{field:'committeeName',title:'专委会',align:'center',width:100},
				{field:'circleName',title:'界别',align:'center',width:100},
				{field:'email',title:'邮箱',align:'center',width:100},
				{field:'telephone',title:'手机号',align:'center',width:100},
				{field:'companyName',title:'工作单位',width:200,align:'center'},
				{field:'job',title:'职务',align:'center',width:200},
				{field:'statusName',title:'状态',align:'center',width:80},
				{field:'secondaryName',title:'有效届次',align:'center',width:100}
			]],				
			toolbar : [{
				text : '查询',
				iconCls : 'icon-search',
				handler : function() {
					query();
				}
			}, '-',{
				text : '履职情况',
				iconCls : 'icon-view',
				handler : function() {
					var row = datagrid.datagrid('getSelected');
					view(row.code);
				}
			}],
			onDblClickRow: function(rowIndex, rowData){
				view(rowData.code);
			}

		});
		//以下初始化查询combox列表
		initCombox('searchForm');
	});
	/////////////////////////////
	function view(code){
			window.open("${pageContext.request.contextPath}/fulfil!fulfilDetail.do?code="+code+"&secondaryCode="+${sessionSeco.secondaryId});
	}
	function query() {
		$('#win_query').window('open');
	}
	
	function _search() {	//此处方法不能为search()
		datagrid.datagrid('load', dj.serializeObject($('#searchForm')));
		$('#win_query').window('close');
	}
	
	function cleanSearch() {
		datagrid.datagrid('load', {});
		$("#name").val('');
		$("#telephone").val('');
 		$("#sex").combobox('select', '请选择...');
		$("#nation").combobox('select', '请选择...');
		$("#partyCode").combobox('select', '请选择...');
		$("#circleCode").combobox('select', '请选择...');
		$("#committee").combobox('select', '请选择...');
		$("#status").combobox('select', '请选择...');
 	}
</script>

<!-- Datagrid表格 -->
<div class="easyui-layout" data-options="fit:true">						
	<div data-options="region:'center',border:false" style="padding:1px;background:#fff;border:1px solid #ccc;">
		<table id="listgrid"></table>
	</div>
</div>
<!-- 查询窗口-->
<div id="win_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:500px;height:300px;padding:5px;">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
			<center>
			<form id="searchForm" enctype=multipart/form-data method="post">
			<table id=tableForm>				
				<tr height="30">
					<td nowrap>姓名：</td>
					<td align="left" ><input type="text" id="name" name="name" style="width:150px;padding:2px" /></td>
					<td nowrap>手机号：</td>
					<td align="left" ><input type="text" id="telephone" name="telephone" style="width:150px;padding:2px" /></td>
					
				</tr>
				<tr height="30">
					<td nowrap>性别：</td>
					<td align="left" ><input type="text" name="sex" id="sex" style="width:148px;padding:2px" /></td>
					<td nowrap>民族：</td>
					<td align="left"><input type="text" name="nation" id="nation" style="width:148px;padding:2px" /></td>
				</tr>
				<tr height="30">
					<td nowrap>党派：</td>
					<td align="left"><input type="text" name="partyCode" id="partyCode" style="width:148px;padding:2px" /></td>
					<td nowrap>界别：</td>
					<td align="left"><input type="text" name="circleCode" id="circleCode" style="width:148px;padding:2px" /></td>
				</tr>
				<tr height="30">
					<td nowrap>专委会：</td>
					<td align="left"><input type="text" name="committee" id="committee" style="width:148px;padding:2px" /></td>
					<td nowrap>状态：</td>
					<td align="left"><input type="text" id="status" name="status"  style="width:148px;padding:2px" /></td>
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
