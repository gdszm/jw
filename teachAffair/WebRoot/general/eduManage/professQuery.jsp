<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.tlzn.util.base.Constants"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	function initCombox(form){
		initCombobox(form,'status','${pageContext.request.contextPath}/dic!combox.do?ctype=PUBSTATUS');
		initCombobox(form,'professType','${pageContext.request.contextPath}/dic!combox.do?ctype=NOTICETYPE');
		$('#secondaryId').combobox({    
		    url:'${pageContext.request.contextPath}/seco!combobox.do',    
		    valueField:'secondaryId',    
		    textField:'secondayName',
		    multiple:true
		 }); 
	}
	var p;
	var datagrid;
	$(function(){
		initCombox("searchForm"); 
		initCombox("professDatagrid");
		initCombox("profess_queryForm");
		datagrid = $('#professDatagrid').datagrid({
			url:"${pageContext.request.contextPath}/profess!datagridQuery.do",
        	width: 'auto',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			idField:'id',
 			singleSelect:true,
			pagination:true,
			pageSize:20,
			pageList:[20,50],
			pageNumber:1,
			fit:true,
			fitColumns :true,
			checkOnSelect:false,
			selectOnCheck:false,
			frozenColumns:[[
				{field:'id',title:'ID',width:100,align:'center',checkbox : true},
				{field:'title',title:'授课标题',width:400,align:'center'}
				]],
			columns:[[
				//{field:'content',title:'授课内容',width:100,align:'center'},
				{field:'pubUnitName',title:'发布单位',width:100,align:'center'},
				{field:'replyName',title:'是否回复',width:100,align:'center'},
				{field:'creatDate',title:'创建日期',width:100,align:'center',
					formatter: function(value,row,index){
						if (value){
							return value.substring(0,19);
						} else {
							return value;
						}
					}
				},
				{field:'pubDate',title:'发布日期',width:100,align:'center',
					formatter: function(value,row,index){
						if (value){
							return value.substring(0,19);
						} else {
							return value;
						}
					}	
				},
				{field:'validDate',title:'有效日期',width:100,align:'center',
					formatter: function(value,row,index){
						if (value){
							return value.substring(0,19);
						} else {
							return value;
						}
					}	
				},
				{field:'statusName',title:'状态',width:100,align:'center'},
				{field:'professTypeName',title:'授课类型',width:100,align:'center'},
				{field:'atts',title:'附件数',width:100,align:'center',
					formatter: function(value,row,index){
						if (value){
							var attArray = value.split(",");//以逗号作为分隔字符串
							return attArray.length;
						} else {
							return value;
						}
					}		
				},
				{field:'memo',title:'备注',width:100,align:'center',hidden:true}
    		]],   
			toolbar : [
			{
				text : '查询',
				iconCls : 'icon-search',
				handler : function() {
					$('#win_profess_query').window('open');
				}
			}, '-',
			{
				text : '查看详细',
				iconCls : 'icon-info',
				handler : function() {
					view();
				}
			}
			]
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
				   	var str=row.secondayName+"("+row.year+"年度)"
					return str;
				}},				
			]],
			onLoadSuccess:function(row){ //当表格成功加载时执行默认届次选定                
                var rowData = row.rows;
                $.each(rowData,function(idx, val){//遍历JSON  
                    if(val.status=='1'){  
                        $('#gridjc').datagrid("selectRow", idx); 
                    }  
                });
            }
		});
	});
	
		//查看
		function view() {
			var rows = datagrid.datagrid('getSelections');
			if (rows.length == 1) {
				var p = dj.dialog({
					title : '授课详情',
					href : '${pageContext.request.contextPath}/profess!get.do?id='+rows[0].id,
					maximized:true,
					maximizable:true,
					width : 800,
					height : 600,
					onLoad : function() {
						initCombox('professViewForm');
						var f = p.find('form');
						f.form('load', {
							professNo : rows[0].professNo,
							professName : rows[0].professName,
							professType: rows[0].professType,
							checkInStartTime : rows[0].checkInStartTime,
							checkInEndTime : rows[0].checkInEndTime,
							checkOutStartTime : rows[0].checkOutStartTime,
							checkOutEndTime : rows[0].checkOutEndTime,
							status : rows[0].status,
							optorName : rows[0].optorName,
							optTime : rows[0].optTime
						});
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
				parent.dj.messagerAlert('提示', '请选择要查看的记录！', 'error');
			}
		}

	//查询
	function _search() {
		 var rows=$('#gridjc').datagrid('getChecked');
		 if(rows.length>0){
			var ids=[];
			for(var i =0;i<rows.length;i++){
				ids.push(rows[i].secondaryId);
				$('#secondarySelectedId').val(ids);
			}
		 }
		 datagrid.datagrid('load', dj.serializeObject($('#profess_queryForm')));
		 $('#win_profess_query').window('close');
	}
	
	function cleanSearch() {
		datagrid.datagrid('load', {});
		$('#profess_queryForm input').val('');
		$('#statusId').combobox('setValue', '');
		$('#professTypeId').combobox('setValue', '');
		$('#secondaryId').combobox('setValue', '${sessionSeco.secondaryId}');
		var rows=$('#gridjc').datagrid('getRows');
		 if(rows.length>0){
			for(var i =0;i<rows.length;i++){
				if(rows[i].status=='1') {
					$('#gridjc').datagrid('uncheckAll');
					$('#gridjc').datagrid('checkRow', i);
				}
			}
		 }
		$('#secondarySelectedId').val("");
	}
</script>
</head>
<body class="easyui-layout" data-options="fit:true">
	<!-- 表格 -->
	<div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="professDatagrid"></table>
	</div>
	<div id="win_profess_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:700px;height:300px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="profess_queryForm">
				<input type="hidden" name="secondaryId" id="secondarySelectedId"/>
				<table id="poll_queryTable">
<!-- 					<tr height="30"> -->
<!-- 						<td nowrap>届次：</td> -->
<!-- 						<td colspan="3"><input id="secondaryId"  name="secondaryId" style="width:125px;" value="${sessionSeco.secondaryId}"></td> -->
<!-- 					</tr> -->
					<tr height="30">
						<td nowrap>授课标题：</td>
						<td colspan="3"><input type="text" class="easyui-validatebox"  name="title" style="width:300px;"></td>
						<td rowspan="6" width="200">
							<table id="gridjc"></table>
						</td>
					</tr>				
					<tr height="30">
						<td nowrap>授课内容：</td>
						<td colspan="3"><input type="text" class="easyui-validatebox"  name="content" style="width:300px;"></td>
						<td/>
					</tr>
					<tr height="30">
						<td nowrap>发布单位 ：</td>
						<td colspan="3"> <input type="text" class="easyui-validatebox"  name="pubUnitName" style="width:300px;"></td>
						<td/>
					</tr>
					<tr height="30">
						<td nowrap>授课类型 ：</td>
						<td colspan="3"> <input id="professTypeId"  name="professType" style="width:300px;"></td>
						<td/>
					</tr>
					<tr height="30">
						<td nowrap>创建日期 ：</td>
						<td colspan="3">
							<input id="creatDateStartId" type="text" class="easyui-datebox" name="creatDateStart" data-options="required:false,showSeconds:false" style="width:142px;"></input> 
		 	              	~ 
			              	<input id="creatDateEndId" type="text" class="easyui-datebox" name="creatDateEnd" data-options="required:false,showSeconds:false" style="width:142px;"></input>  
						</td>
						<td/>
					</tr>
					<tr height="30">
						<td nowrap>发布日期 ：</td>
						<td colspan="3">
							<input id="pubDateStartId" type="text" class="easyui-datebox" name="pubDateStart" data-options="required:false,showSeconds:false" style="width:142px;"></input> 
		 	              	~ 
			              	<input id="pubDateEndId" type="text" class="easyui-datebox" name="pubDateEnd" data-options="required:false,showSeconds:false" style="width:142px;"></input>  
						</td>
						<td/>
					</tr>
				</table>
				</form>
				</center>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;height:40px">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)" onclick="_search()">查询</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)" onclick="cleanSearch()">重置</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#win_profess_query').window('close');">取消</a>
			</div>
		</div>
	</div>
</body>
</html>