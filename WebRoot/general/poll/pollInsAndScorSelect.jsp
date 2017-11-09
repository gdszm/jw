<%@page import="com.tlzn.util.base.Constants"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <script type="text/javascript">
	var datagridSelect;
	$(document).ready(function() {
		datagridSelect=$('#listgrid');
		//加载记分表
		datagridSelect.datagrid({
			url:'${pageContext.request.contextPath}/rules!datagrid.do?status=<%=Constants.CODE_TYPE_QYTY_YES%>', 
			width: 'auto',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			singleSelect:true,
			pagination:true,
			pageSize:10,
			pageList:[10,20,50],
			pageNumber:1,
			idField:'id',
			sortName:'id',
			sortOrder : 'asc',
			fit:true,
			checkOnSelect: true, 
			selectOnCheck: true,
			columns:[[
				{field:'id',title:'选择项',width:50,align:'center',checkbox : true},
				{field:'typeid',title:'计分类型',align:'center',width:100},
				{field:'typeName',title:'计分类型名称',align:'center',width:100},
				{field:'name',title:'计分名称',align:'center',width:100},
				{field:'score',title:'分值 ',align:'center',width:100},
				{field:'statusName',title:'状态',align:'center',width:100}
			]]
		});
		
	});
	function _searchSelect() {	//此处方法不能为search()
		datagridSelect.datagrid('load', dj.serializeObject($('#searchFormSelect')));
	}
	function cleanSearchSelect() {
		datagridSelect.datagrid('load', {});
		$("#nameIdSelect").val("");
	}
 </script>
 <!-- 查询栏 -->
	<div data-options="region:'north',border:false,align:'center'" style="height: 40px; overflow: hidden;" >
		<form id="searchFormSelect">
			<table class="tableForm datagrid-toolbar" style="width: 100%;height: 100%;">
				<tr>
					<td style="text-align: center;width:100%">
					计分名称：<span><input id="nameIdSelect" name="name" style="width:100px;" /></span>
					<span style="text-align: center;width:100px">
						<a href="javascript:void(0);" class="easyui-linkbutton" onclick="_searchSelect();">搜索</a>
					</span>
					<span style="text-align: center;width:100px">
						<a href="javascript:void(0);" class="easyui-linkbutton" onclick="cleanSearchSelect();">取消</a>
					</span>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<!-- Datagrid表格 -->
	<div data-options="region:'center',border:false" style="height:88%">
		<table id="listgrid"></table>
	</div>
	