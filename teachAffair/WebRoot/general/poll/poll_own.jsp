<%@page import="com.tlzn.util.base.Constants"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript">
	var gridown;
	var p;
	function initCombox(form){
		initCombobox(form,'status','${pageContext.request.contextPath}/dic!combox.do?ctype=POLLSTATUS');
	}
	$(document).ready(function() {
		gridown=$('#gridown');
		//gds add start
		var startType='<%=request.getAttribute("statusType")%>';
		var status;
		var filingFlg;
		if(startType!=""&& startType!=null){
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
<%--			if(startType=='F'){--%>
<%--				url+='?submitFlg=1';--%>
<%--			}			--%>
<%--			if(startType=='G'){--%>
<%--				url+='?submitFlg=1&replyPass=0';--%>
<%--			}--%>
<%--			if(startType=='H'){--%>
<%--				url+='?status=4&submitFlg=1&replyPass=1';--%>
<%--			}--%>
<%--			if(startType=='I'){--%>
<%--				url+='?status=4&submitFlg=1&replyPass=1';--%>
<%--			}--%>
<%--			if(startType=='J'){--%>
<%--				url+='?status=4&submitFlg=1&replyPass=1';--%>
<%--			}--%>
<%--			if(startType=='K'){--%>
<%--				url+='?status=4&submitFlg=1&replyPass=1';--%>
<%--			}--%>
		}
		//gds add end
		gridown.datagrid({
			url:'${pageContext.request.contextPath}/poll!datagridown.do',
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
			idField:'pollId',
			fit:true,
			queryParams: {
				status: status,
				filingFlg: filingFlg
			},
			frozenColumns:[[
				{field:'pollId',hidden:true}, 
				//{field:'pollCode',title:'社情民意编号',align:'center',width:100,sortable:true},
				{field:'title',title:'标题',halign:'center',align:'left',width:380,sortable:true,formatter:function(val,rec){
					var str=val;				
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
				{field:'handleCompName',title:'办理单位',halign:'center',align:'left',width:200,sortable:false},
				{field:'mergeFlg',title:'是否合并',align:'center',width:60,sortable:true,formatter: function(value, row) {
				    return row.mergeFlgName;
				}},				
				{field:'stressFlg',title:'是否重点',align:'center',width:60,sortable:true,formatter: function(value, row) {
				    return row.stressFlgName;
				}},
				{field:'editor',title:'编发人',align:'center',width:60,sortable:true,formatter: function(value, row) {
				    return row.editorName;
				}},		
				{field:'master',title:'主送',align:'center',width:150,sortable:true},
				{field:'copyMan',title:'抄送',align:'center',width:300,sortable:true},
				{field:'handleType',title:'办理方式',align:'center',width:100,sortable:true,formatter: function(value, row) {
                    return row.handleTypeName;
                }},				
				{field:'createTime',title:'提交日期',align:'center',width:150,sortable:true}
			]],			
			toolbar: '#tb',
			onDblClickRow : function(rowIndex, rowData) {
				lookPollOnDblClick(rowData.pollId);
			}
		});
		initCombox("my_queryForm");
	});
	function add() {
		window.location.href="${pageContext.request.contextPath}/poll!pollsub.do";
	}
	//修改社情民意
	function editPoll(){
		var rows = gridown.datagrid('getSelected');
		if (rows!=null) {
			if(rows.status!=<%=Constants.CODE_TYPE_POLL_STATUS_YBC%>){
				parent.dj.messagerAlert('提示', '选中记录已提交,不能进行修改!', 'error');
				return ;
			}
			p = dj.dialog({
				title : '修改社情民意',
				href : '${pageContext.request.contextPath}/poll!pollOwnEdit.do?pollId=' + rows.pollId,
				maximized:true,
				maximizable:true,
				width : 800,
				height : 500,
				iconCls:'icon-save',
				buttons : [ {
						text : '保存',
						iconCls:'icon-save',
						handler : function() {
							edit();
						}
					},{
						text : '提交',
						iconCls:'icon-submit',
						handler : function() {
							submitEditPoll(rows.pollId);
						}
					},{ 
						text: '取消', 
						iconCls:'icon-cancel',
						handler: function() { 
							p.dialog('close'); 
						} 
					}]
			});
		} else {
			parent.dj.messagerAlert('提示', '请选择要编辑的记录！', 'error');
		}
	}
	
		//工具栏提交提案
	function subPoll(){
		var rows = gridown.datagrid('getSelected');
		if (rows!=null) {
			if(rows.status!=<%=Constants.CODE_TYPE_POLL_STATUS_YBC%>){
				parent.dj.messagerAlert('提示', '选中记录已提交,不需要再次提交!', 'error');
				return ;
			}
			parent.dj.messagerConfirm('请确认', '点击提交后，社情民意不能再进行修改！', function(r) {
				if (r) {
					$.ajax({
						url : '${pageContext.request.contextPath}/poll!submit.do?pollId=' +rows.pollId,
						success : function(d) {
							var json = $.parseJSON(d);
							if (json.success) {
								gridown.datagrid('reload');
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
			parent.dj.messagerAlert('提示', '请选择要提交的记录！', 'error');
		}
	}
	
	function edit(){
		var f =$("#editForm");
		//$("#content").val(UE.getEditor('editor').getContent()); 
		f.form('submit', {
			url : '${pageContext.request.contextPath}/poll!edit.do',
			success : function(d) {
				var json = $.parseJSON(d);
				if (json.success) {
					gridown.datagrid('reload');
					p.dialog('close'); 
				}
				dj.messagerShow({
					msg : json.msg,
					title : '提示'
				});
			}
		});
	}
	   //提交
	function submitEditPoll(pollId){
		parent.dj.messagerConfirm('请确认', '点击提交后，社情民意信息不能再进行修改！', function(r) {
			if (r) {
				var f =$("#editForm");
				f.form('submit', {
					url : '${pageContext.request.contextPath}/poll!submitEdit.do',
					success : function(d) {
						var json = $.parseJSON(d);
						if (json.success) {
							gridown.datagrid('reload');
							p.dialog('close'); 
						}
						dj.messagerShow({
							msg : json.msg,
							title : '提示'
						});
					}
				});
			}
		});
	}
	
	//查询
	function _search() {
		gridown.datagrid('load', dj.serializeObject($('#my_queryForm')));
		 $('#win_my_query').window('close');
	}
	//重置
	function cleanSearch() {
		gridown.datagrid('load', {});
		$('#win_my_query input').val('');
	}
	
	//撤销
	function revoke(){
		var rows = gridown.datagrid('getSelected');
		if (rows!=null) {
			if(rows.status!=<%=Constants.CODE_TYPE_POLL_STATUS_WSC%>){
				parent.dj.messagerAlert('提示', '当前选中记录不能进行撤销操作!', 'error');
				return ;
			}

			parent.dj.messagerConfirm('请确认', '确定要进行提交撤销吗！', function(r) {
				if (r) {
	
					 $.ajax({
						type:"POST",
						url:"${pageContext.request.contextPath}/poll!revoke.do",
						data:{
							pollId:rows.pollId
						},
						success:function(d){
							var json = $.parseJSON(d);
							if (json.success) {
								gridown.datagrid('reload');
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
			parent.dj.messagerAlert('提示', '请选择要撤销的记录！', 'error');
		}
	}
	//查看按钮社情民意
	function lookPoll(){
		var selectRow = gridown.datagrid('getSelected');
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
	//查看被合并的社情民意
	function see(pollId){
		window.open("${pageContext.request.contextPath}/poll!pollSee.do?pollId="+pollId);
	}
</script>
</head>

<body>
	<!-- 表格 -->
	<div class="easyui-layout" data-options="fit:true">						
		<div data-options="region:'center',border:false" style="padding:1px;background:#fff;border:1px solid #ccc;">
			<table id="gridown"></table>
		</div>
	</div>
	<div id="columnmenu" class="easyui-menu" style="width:100px;"></div>

	<div id="tb" style="padding:2px;height:auto">
		<div style="margin-bottom:1px">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="$('#win_my_query').window('open')">查询</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add()">新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editPoll()">修改</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-submit" plain="true" onclick="subPoll()">提交社情民意</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="revoke()">撤销提交</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-view" plain="true" onclick="lookPoll()">查看</a>
		</div>
	</div>
	<div id="win_my_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:500px;height:220px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="my_queryForm">
				<input type="hidden" name="filingFlg" id="filingFlgId"/>
				<table id="my_queryTable">				
					<tr style="width:25px;">
						<td>社情民意编号：</td>
						<td><input type="text" name="pollCode" style="width:300px;padding:2px;border:1px solid #000;"/></td>
					</tr>				
					<tr style="width:25px;">
						<td>标题：</td>
						<td><input type="text" name="title" style="width:300px;padding:2px;border:1px solid #000;"/></td>
					</tr>
					<tr style="width:25px;">
						<td>状态：</td>
						<td> <input id="status" panelHeight="100px" name="status" value="请选择..." style="width:300px;padding:2px;border:1px solid #000;"/></td>
					</tr>
				</table>
				</form>
				</center>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;height:40px">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)" onclick="_search()">查询</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)" onclick="cleanSearch()">重置</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#win_my_query').window('close');">取消</a>
			</div>
		</div>
	</div>
</body>
</html>
