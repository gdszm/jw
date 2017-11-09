<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--履职情况统计--%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript">
var datagrid;

	$(document).ready(function() {
		//以下初始化查询combox列表
		initCombobox('searchForm','status','${pageContext.request.contextPath}/dic!combox.do?ctype=STATUS');
		initCombobox('searchForm','sex','${pageContext.request.contextPath}/dic!combox.do?ctype=SEX');
		initCombobox('searchForm','nation','${pageContext.request.contextPath}/dic!combox.do?ctype=NATION');
		initCombobox('searchForm','partyCode','${pageContext.request.contextPath}/dic!combox.do?ctype=PARTY');
		initCombobox('searchForm','circleCode','${pageContext.request.contextPath}/dic!combox.do?ctype=CIRCLE');
		initCombobox('searchForm','committee','${pageContext.request.contextPath}/dic!combox.do?ctype=COMMITTEE');
		
		$.ajax({
			url :'${pageContext.request.contextPath}/fulfil!getFulfilColumns.do',
			success : function(data) {
				var column1=data.substring(0,data.indexOf("],[")+1);
				var column2=data.substring(data.indexOf("],[")+2);
				datagrid=$('#fulfilgrid');
				datagrid.datagrid({
					width: '100%',
					height:'100%',
					url:'${pageContext.request.contextPath}/fulfil!fulfilCount.do',
					fit:true,
					rownumbers:true,
					singleSelect:true,
					striped:true,
					idField:'code',
					frozenColumns : [[
						{field:'name',title:'委员',align:'center',width:100},
						{field:'sex',title:'性别',align:'center',width:40},
						{field:'circleName',title:'界别',align:'center',width:100},
						{field:'committeeName',title:'专委会',align:'center',width:100}
						
					]],
					columns:[eval(column1),
					         eval(column2)],
					onDblClickRow : function(rowIndex, rowData) {
						view(rowData.code);
					},
					toolbar : '#tb',
				});
			}
		});
		
		$('#gridjc').datagrid({
			width: '100%',
			height:'100%',
			url:'${pageContext.request.contextPath}/seco!datagridNoPage.do',
			fit:true,
			idField:'secondaryId',
			striped:true,
			singleSelect:true,
			frozenColumns : [[
				{field:'secondaryId',title:'选择',checkbox:true},
			]],
			columns:[[
				{field:'secondayName',title:'届次名称',align:'center',width:140,sortable:false}
			]],
			onLoadSuccess:function(row){ //当表格成功加载时执行默认届次选定                
                var rowData = row.rows;
                $.each(rowData,function(idx, val){//遍历JSON  
                    if(val.status=='1'){
                    		$('#gridjc').datagrid("checkRow", idx); 
                    		$('#secondaryID').val(val.secondaryId);
                    		$('#yearID').val(val.year);
                    		
                    }  
                });
               
            }
		});
	});
	
	function view(code) {
		window.open("${pageContext.request.contextPath}/fulfil!fulfilDetail.do?code="+code+"&secondaryCode="+$('#secondaryID').val());
	}
	function _search() {	//此处方法不能为search()
		var rows=$('#gridjc').datagrid('getChecked');
		 if(rows.length>0){
			for(var i =0;i<rows.length;i++){
				$('#secondaryID').val(rows[i].secondaryId);
				$('#yearID').val(rows[i].year);
			}
		 }
		datagrid.datagrid('load', dj.serializeObject($('#searchForm')));
		$('#win_fulfil_query').window('close');
	}
	
	function cleanSearch() {
		$("input,textarea,select").val("");
		
		$('#secondaryID').val('${sessionSeco.secondaryId}');
		$('#yearID').val('${sessionSeco.year}');
		$('#sex').combobox('setValue', '');
		$('#nation').combobox('setValue', '');
		$('#partyCode').combobox('setValue', '');
		$('#circleCode').combobox('setValue', '');
		$('#committee').combobox('setValue', '');
		$('#status').combobox('setValue', '');
		
		var rows = $("#gridjc").datagrid("getRows");
		for(var i=0;i<rows.length;i++)
		if(rows[i].status=='1'){
				$("#gridjc").datagrid("unselectAll");
          		$('#gridjc').datagrid("checkRow", i); 
        }
        datagrid.datagrid('load', dj.serializeObject($('#searchForm')));        
	}
	
	//报表
	function report(){
		var totalRowNum = datagrid.datagrid('getRows');
		if(totalRowNum.length>0){
			var rows=$('#gridjc').datagrid('getChecked');
			 if(rows.length>0){
				for(var i =0;i<rows.length;i++){
					$('#secondaryID').val(rows[i].secondaryId);
					$('#yearID').val(rows[i].year);
				}
			 }
			$.ajax({
				url : '${pageContext.request.contextPath}/fulfil!report.do',
				data:dj.serializeObject($('#searchForm')),// 你的formid
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

<body>
		<!-- 表格 -->
	<div class="easyui-layout" data-options="fit:true">						
		<div data-options="region:'center',border:false" style="padding:1px;background:#fff;border:1px solid #ccc;">
			<table id="fulfilgrid" style="overflow: auto;"></table>
		</div>
	</div>
	<div id="columnmenu" class="easyui-menu" style="width:110px;"></div>

	<div id="tb">
		<div style="margin-bottom:1px">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="javascript:$('#win_fulfil_query').window('open');">查询</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="javascript:report();">导出</a>
		</div>
	</div>
	<div id="win_fulfil_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:550px;height:350px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="searchForm" enctype=multipart/form-data method="post">
				<input type="hidden" id="secondaryID" name="secondaryCode">
				<input type="hidden" id="yearID" name="year">
				<table id=tableForm>				
					<tr height="30">
						<td nowrap>姓名：</td>
						<td align="left" colspan="3"><input type="text" name="name" style="width:188px;padding:2px" /></td>
						<td rowspan="8" width="200">
							<table id="gridjc" ></table>
						</td>
					</tr>
					<tr height="30">
						<td nowrap>手机号：</td>
						<td align="left" colspan="3"><input type="text" name="telephone" style="width:188px;padding:2px" /></td>
					</tr>
					<tr height="30">
						<td nowrap>性别：</td>
						<td align="left" ><input type="text" name="sex" id="sex" style="width:148px;padding:2px" /></td>
					</tr>
					<tr height="30">
						<td nowrap>民族：</td>
						<td align="left"><input type="text" name="nation" id="nation" style="width:148px;padding:2px" /></td>
					</tr>
					<tr height="30">
						<td nowrap>党派：</td>
						<td align="left"><input type="text" name="partyCode" id="partyCode" style="width:148px;padding:2px" /></td>
					</tr>
					<tr height="30">
						<td nowrap>界别：</td>
						<td align="left"><input type="text" name="circleCode" id="circleCode" style="width:148px;padding:2px" /></td>
					</tr>
					<tr height="30">
						<td nowrap>专委会：</td>
						<td align="left"><input type="text" name="committee" id="committee" style="width:148px;padding:2px" /></td>
					</tr>
				</table>
				</form>
				</center>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;height:40px">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)" onclick="_search()">查询</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)" onclick="cleanSearch()">重置</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#win_fulfil_query').window('close');">取消</a>
			</div>
		</div>
	</div>
</body>
</html>