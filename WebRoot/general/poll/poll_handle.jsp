<%@page import="com.tlzn.util.base.Constants"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript">
	var gridHandle;
	var p;
	var rulesGrid;
	//查询
	function _search() {
		gridHandle.datagrid('load', dj.serializeObject($('#poll_queryForm')));
		 $('#win_poll_query').window('close');
	}
	//重置
	function cleanSearch() {
		gridHandle.datagrid('load', {});
		$('#poll_queryForm input').val('');
	}
	
	//查看社情民意
	function lookPoll(){
		var selectRow = gridHandle.datagrid('getSelected');
		if (selectRow) {
			window.open("${pageContext.request.contextPath}/poll!pollSee.do?pollId="+selectRow.pollId);
		}else{
			dj.messagerAlert('提示', '请选择要查看的记录！', 'error');
		}		
		return false;
	}
	//双击行查看社情民意
	function lookPollOnDblClick(pollId){
		if (pollId) {
			window.open("${pageContext.request.contextPath}/poll!pollSee.do?pollId="+pollId);
		}else{
			dj.messagerAlert('提示', '请双击要查看的记录！', 'error');
		}		
		return false;
	}
	$(document).ready(function() {
		gridHandle=$('#gridHandle');
		gridHandle.datagrid({
			url:'${pageContext.request.contextPath}/poll!datagridAssign.do',
			width: 'auto',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			pagination:true,
			pageSize:20,
			pageList:[10,15,20],
			pageNumber:1,
			idField:'pollId',
			fit:true,
			frozenColumns:[[
				{field:'pollId',hidden:true}, 
				{field:'pollCode',title:'社情民意编号',align:'center',width:100,sortable:true},
				{field:'title',title:'标题',halign:'center',align:'left',width:480,sortable:true,formatter:function(val,rec){
					var str=val;	
					if(val==undefined){}
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
				{field:'editor',title:'编发人',align:'center',width:60,sortable:true,formatter: function(value, row) {
				    return row.editorName;
				}},		
				{field:'master',title:'主送',align:'center',width:150,sortable:true},
				{field:'copyMan',title:'抄送',align:'center',width:300,sortable:true},
				 {field:'handleType',title:'办理方式',align:'center',width:100,sortable:true,formatter: function(value, row) {
					    return row.handleTypeName;
					}},				
					{field:'handleCompName',title:'办理单位',halign:'center',align:'left',width:200,sortable:false},
				{field:'mergeFlg',title:'是否合并',align:'center',width:60,sortable:true,formatter: function(value, row) {
				    return row.mergeFlgName;
				}},				
				{field:'stressFlg',title:'是否重点',align:'center',width:60,sortable:true,formatter: function(value, row) {
				    return row.stressFlgName;
				}},
				{field:'createTime',title:'提交日期',align:'center',width:180,sortable:true}
			]],			
			toolbar: '#tb',
			onDblClickRow : function(rowIndex, rowData) {
				lookPollOnDblClick(rowData.pollId);
			}
		});
	});
	
	//转办
	function handleAdd() {
		var rows = gridHandle.datagrid('getSelections');
		if (rows.length == 1) {
			
			p = dj.dialog({
				title : '社情民意转办',
				href : '${pageContext.request.contextPath}/pollHandle!pollHandleAdd.do?poll.pollId='+rows[0].pollId,
				maximized:true,
				maximizable:true,
				width : 800,
				height : 500,
				iconCls:'icon-save',
				buttons : [ {
						text : '提交',
						iconCls:'icon-ok',
						handler : function() {
							handleAddSave();
						}
					},{ 
						text: '取消', 
						iconCls:'icon-cancel',
						handler: function() { 
							p.dialog('close'); 
						} 
					}],
					onLoad : function() {
						//下拉列表取值
						getcombobox('handleTypeCheck','${pageContext.request.contextPath}/dic!combox.do?ctype=BLLX');
					   
						var d=new Date();
						var rq=d.getFullYear() + "-" + (d.getMonth()+1) +"-" + d.getDate();
						$('#submitTime').val(rq);
					    $(document).ready(function() {
					    	//提案承办单位列表
							$('#cbdwgrid').datagrid({
								url:'${pageContext.request.contextPath}/pollHandle!handleGrid.do?poll.pollId='+rows[0].pollId,
								width:'auto',
								height:'auto',
								striped:true,
								nowrap:true,
								rownumbers:true,
								fitColumns:true,
								fit:true,
								columns:[[
									{field:'companyId',title:'单位编号',align:'center',width:80,sortable:false,formatter: function(value, row) {
					                    return row.comp.companyId;
					                }},	
									{field:'companyName',title:'单位名称',align:'left',width:220,sortable:false,formatter: function(value, row) {
					                    return row.comp.companyName;
					                }},	
									{field:'companyType',title:'单位类别',align:'left',width:170,sortable:false,formatter: function(value, row) {
					                    return row.comp.companyTypeName;
					                }},	
									{field:'mainFlgName',title:'主办/协办(会办)',align:'center',width:80,sortable:false}				
								]],
								toolbar : [ {
									text : '添加',
									iconCls : 'icon-add',
									handler : function() {
										dwadd();
									}
									},{
									text : '删除',
									iconCls : 'icon-remove',
									handler : function() {
										delete_cbdwadd();
									}
									}]
							});
						});
				}
			});
		} else if (rows.length > 1) {
			parent.dj.messagerAlert('提示', '同一时间只能操作一条记录！', 'error');
		} else {
			parent.dj.messagerAlert('提示', '请选择要操作的记录！', 'error');
		}
	}
	//保存数据
	function handleAddSave(){
		var compdata=$('#cbdwgrid').datagrid('getRows');
		var comps=[];
		var mainFlgs=[];
		for(var i = 0; i<compdata.length ;i++){
			comps.push(compdata[i].comp.companyId);
			mainFlgs.push(compdata[i].mainFlg);
		}
		
		$("#companyIds").val(comps.join(','));
		$("#mainFlgs").val(mainFlgs.join(','));
		$('#confirmForm').form('submit', {
			url : '${pageContext.request.contextPath}/pollHandle!confirm.do',
			success : function(d) {
				var json = $.parseJSON(d);
				if (json.success) {
					gridHandle.datagrid('reload');
					p.dialog('close');
				}
					dj.messagerShow({
					msg : json.msg,
					title : '提示'
				});
			}
		});
	}
	//添加办理单位
	function dwadd(){
		var handleTypeCode=$('#handleTypeCheck').combobox('getValue')//获取当前选中的值
		var handleTypeName=$('#handleTypeCheck').combobox('getText')//获取当前选中的文字
		if(handleTypeName=="请选择..."){
			parent.dj.messagerAlert('提示', '请先设置办理方式！', 'info');
			return;
		}
		var single=false;
		if(handleTypeCode=='<%=Constants.CODE_TYPE_BLLX_DB%>'){
			single=true;
		}
		secondp = dj.dialog({
			title : '办理单位设置',
			href : '${pageContext.request.contextPath}/prop!checkAdd.do',	//
			width : 650,
			height : 380,
			iconCls:'icon-save',
			buttons : [{
					text : '添加',
					iconCls : 'icon-add',
					handler : function() {
						append_cbdwadd();
					}
				},{
					text: '关闭', 
					iconCls:'icon-cancel',
					handler: function() { 
						secondp.dialog('close'); 
					} 
				}],
			onLoad : function() {
				if(handleTypeCode=='<%=Constants.CODE_TYPE_BLLX_HB%>'){
					$("#hostH").css('display','block');//显示  
					$("#aid").css('display','block');//显示  
				}else{
					$("#hostH").css('display','none');//隐藏
					$("#aid").css('display','none');//隐藏
				}
				$("#ht").text(handleTypeName);
				var hostHand =$("#hostHand");
				var hostHandCode=$("#hostHandCode");
				var aidCompCode =$("#aidCompCode");
				var aidComp=$("#aidComp");
				$(document).ready(function() {
					//承办单位列表
					getcombobox('companyType','${pageContext.request.contextPath}/dic!combox.do?ctype=COMPANYTYPE');
					$('#dwgrid').datagrid({
						url:'${pageContext.request.contextPath}/comp!datagrid.do',
						width: 'auto',
						height:'auto',
						striped:true,
						nowrap:true,
						singleSelect:single,
						pagination:true,
						pageSize:10,
						pageList:[10,15,20],
						rownumbers:true,
						sortName: 'companyId',
						sortOrder: 'asc',
						idField:'companyId',
						fit:true,
						columns:[[
							{field:'companyId',title:'单位编号',checkbox:true,align:'center',width:40},
							{field:'companyName',title:'单位名称',align:'left',width:280,sortable:false},
							{field:'companyTypeName',title:'单位类别',align:'left',width:150,sortable:false}
						]],
						onSelect:function(rowIndex,rowData){
							var strhhc=hostHandCode.val();
							if(strhhc==''){
								hostHandCode.val(rowData.companyId);
								hostHand.text(rowData.companyName);
							}else{
								aidCompCode.val(aidCompCode.val()+rowData.companyId+',');
								aidComp.text(aidComp.text()+rowData.companyName+',');
							}
						},
						onUnselect:function(rowIndex,rowData){
							var strhc=hostHandCode.val();
							hostHandCode.val(strhc.replace(rowData.companyId,''));
							var strh=hostHand.text();
							hostHand.text(strh.replace(rowData.companyName,''));
							var strac=aidCompCode.val();
							aidCompCode.val(strac.replace(rowData.companyId+',',''));
							var stra=aidComp.text();
							aidComp.text(stra.replace(rowData.companyName+',',''));
						}
					});
				});
			}
		});	
		
	}
	
	//添加单位
	function append_cbdwadd(){
		var rows=$('#dwgrid').datagrid('getSelections');
		var cbdwrows=$('#cbdwgrid').datagrid('getRows');
		var handleType=$('#handleTypeCheck').combobox('getValue');
		if((handleType=='<%=Constants.CODE_TYPE_BLLX_DB%>' && rows.length>1) || (handleType=='<%=Constants.CODE_TYPE_BLLX_DB%>' && cbdwrows.length>0)){
			dj.messagerAlert('提示', '社情民意为单办类型,只能选择一个办理单位！', 'error');
			return;
		}
		if(rows.length>0){
				var mainhost=$("#hostHandCode").val();
				if(handleType=='<%=Constants.CODE_TYPE_BLLX_HB%>'){
					$('#cbdwgrid').datagrid('loadData', { total: 0, rows: [] }); 
				}
				for(var i = 0; i<rows.length ;i++){
					for(var j=0;j<cbdwrows.length;j++){
						if(rows[i].companyId==cbdwrows[j].companyId){
							dj.messagerAlert('提示', '添加单位已存在！', 'error');
							return;
						}
					}
					if(handleType=='<%=Constants.CODE_TYPE_BLLX_HB%>'){
						if(rows[i].companyId==mainhost){
							$('#cbdwgrid').datagrid('appendRow',{
								comp:{serialVersionUID:1,
									companyId:rows[i].companyId,
									companyName:rows[i].companyName,
									companyType:rows[i].companyType,
									companyTypeName:rows[i].companyTypeName},
								
								mainFlg:'1',
								mainFlgName:'主办'
							});
						}else{
							$('#cbdwgrid').datagrid('appendRow',{
								comp:{
									serialVersionUID:1,
									companyId:rows[i].companyId,
									companyName:rows[i].companyName,
									companyType:rows[i].companyType,
									companyTypeName:rows[i].companyTypeName},
								mainFlg:'0',
								mainFlgName:'协办'
							});
						}
					}else{
						$('#cbdwgrid').datagrid('appendRow',{
							comp:{
								serialVersionUID:1,
								companyId:rows[i].companyId,
								companyName:rows[i].companyName,
								companyType:rows[i].companyType,
								companyTypeName:rows[i].companyTypeName},
							mainFlg:'',
							mainFlgName:''
						});
					}
				}
			secondp.dialog('close');
		}else{
			parent.dj.messagerAlert('提示', '请选择要添加的记录！', 'error');
		}
		
	}
	//删除提案承办单位
	function delete_cbdwadd(){
		var rows=$('#cbdwgrid').datagrid('getSelections')
		if(rows.length>0){
			for(var i = 0; i<rows.length ;i++){
				var rowindex=$('#cbdwgrid').datagrid('getRowIndex',rows[i]);
				$('#cbdwgrid').datagrid('deleteRow',rowindex);
			}
			
		}else{
			dj.messagerAlert('提示', '请选择要删除的办理单位！', 'error');
		}
	}
	//单位过虑
	function dw_search(){
		$('#dwgrid').datagrid('load', dj.serializeObject($('#dw_queryForm')));
	}
	//单位过虑取消
	function dw_cleanSearch() {
		$('#dwgrid').datagrid('load', {});
		$('input').val('');
	}
	//提交办复
	function handleSubmit(){
		var rows = gridHandle.datagrid('getSelections');
		var ids=[];
		if (rows.length >0) {
			for(var i = 0; i<rows.length ;i++){
				if(rows[i].status !='<%=Constants.CODE_TYPE_POLL_STATUS_ZBZ%>' ){
					parent.dj.messagerAlert('提示', '选中记录中有记录未进行转办设置!', 'error');
					return ;
				}
				ids.push(rows[i].pollId);
			}
			parent.dj.messagerConfirm('请确认', '您要确定提交办理所选社情民意?', function(r) {
				if (r) {
					$.ajax({
						url : '${pageContext.request.contextPath}/poll!pollAssign.do',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(d) {
							gridHandle.datagrid('reload');
							gridHandle.datagrid('unselectAll');
							dj.messagerShow({
								title : '提示',
								msg : d.msg
							});
							
						},
						error: function(d) {
							dj.messagerShow({
								title : '提示',
								msg : d.msg
							});
						}
					});
				}
			});
		} else {
			parent.dj.messagerAlert('提示', '请选择要操作的记录！', 'error');
		}
	}
	
</script>
</head>

<body>
	<!-- 表格 -->
	<div class="easyui-layout" data-options="fit:true">						
		<div data-options="region:'center',border:false" style="padding:1px;background:#fff;border:1px solid #ccc;">
			<table id="gridHandle"></table>
		</div>
	</div>
	<div id="columnmenu" class="easyui-menu" style="width:100px;"></div>

	<div id="tb" style="padding:2px;height:auto">
		<div style="margin-bottom:1px">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="$('#win_poll_query').window('open')">查询</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="handleAdd()">转办</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-submit" plain="true" onclick="handleSubmit()">提交办理</a>	
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-view" plain="true" onclick="lookPoll()">查看</a>
		</div>
	</div>
	<div id="win_poll_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:500px;height:220px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="poll_queryForm">
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
						<td><input type="text" name="createMan" style="width:300px;padding:2px;border:1px solid #000;"></td>
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
