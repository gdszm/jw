<%@page import="com.tlzn.util.base.Constants"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	function initCombox(form){
		initCombobox(form,'useSituation','${pageContext.request.contextPath}/dic!combox.do?ctype=USESITUATION');
	}
	var p;
	var datagrid;
	$(function(){
		initCombox("searchForm"); 
		initCombox("speechDatagrid");
		initCombox("speech_queryForm");
		datagrid = $('#speechDatagrid').datagrid({
			url:'${pageContext.request.contextPath}/speech!datagridQuery.do',
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
			fitColumns:true,
			checkOnSelect:false,
			selectOnCheck:false,
			frozenColumns:[[
				{field:'speakId',title:'ID',width:100,align:'center',checkbox : true},
				{field:'title',title:'发言主题',width:400,align:'center'}
				]],
			columns:[[
				//{field:'content',title:'发言内容',width:100,align:'center'},
				{field:'meetName',title:'所属会议',width:100,align:'center'},
				{field:'name',title:'发言人',width:100,align:'center'},
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
		
		$('#gridjc').datagrid({
			width: '100%',
			height:'100%',
			url:'${pageContext.request.contextPath}/seco!datagridNoPage.do',
			fit:true,
			idField:'secondaryId',
			striped:true,
			frozenColumns : [[
				{field:'secondaryId',title:'选择',checkbox:true},
			]],
			columns:[[
				{field:'secondayName',title:'届次名称',align:'center',width:140,sortable:false,formatter: function(value, row) {
				   	var str=row.secondayName+"("+row.year+"年度)";
					return str;
				}},				
			]],
			onLoadSuccess:function(row){ //当表格成功加载时执行默认届次选定                
                var rowData = row.rows;
                $.each(rowData,function(idx, val){//遍历JSON  
                    if(val.status=='1'){  
                        $('#gridjc').datagrid("selectRow", idx); 
                    } else {
                    	$('#gridjc').datagrid("unselectRow", idx); 
                    }
                });
            }
		});
	});
	

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
		$('#gridjc').datagrid('load', {});
		$('#speech_queryForm input').val('');
		$("#useSituationId").combobox('setValue', '');
	}
</script>
</head>
<body class="easyui-layout" data-options="fit:true">
	<!-- 表格 -->
	<div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="speechDatagrid"></table>
	</div>
	<div id="win_speech_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:630px;height:360px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="speech_queryForm">
				<input type="hidden" name="filingFlg" id="filingFlgId"/>
				<table id="speech_queryTable">
					<tr height="30">
						<td nowrap>发言主题：</td>
						<td><input type="text" name="title" class="easyui-validatebox"  style="width:280px;padding:2px;"></td>
						<td rowspan="7" width="200">
							<table id="gridjc" ></table>
						</td>
					</tr>
					<tr height="30">
						<td nowrap>所属会议：</td>
						<td><input type="text" name="meetName" class="easyui-validatebox"  style="width:280px;padding:2px;"></td>
					</tr>
					<tr height="30">
						<td nowrap>发言人：</td>
						<td><input type="text" name="name" class="easyui-validatebox"  style="width:280px;padding:2px;"></td>
					</tr>
					<tr height="30">
						<td nowrap>采用情况：</td>
						<td> <input id="useSituationId" panelHeight="100px" name="useSituation" style="width:285px;padding:2px;border:1px solid #000;"></td>
					</tr>
					<tr height="30">
						<td nowrap>提交时间：</td>
						<td> <input class="easyui-datebox" name="createTimeStart"   style="width:136px"/>--<input class="easyui-datebox" name="createTimeEnd"  style="width:136px"/></td>
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