<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	function initCombox(form){
		initCombobox(form,'status','${pageContext.request.contextPath}/dic!combox.do?ctype=SPEECHSTATUS');
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
		initCombox("speechDatagrid");
		initCombox("speech_queryForm");
		datagrid = $('#speechDatagrid').datagrid({
			url:'${pageContext.request.contextPath}/speech!datagridAdopted.do',
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
			frozenColumns:[[
				{field:'id',title:'speakId',width:100,align:'center',checkbox : true},
				{field:'title',title:'发言主题',width:400,align:'center'}
				]],
			columns:[[
				//{field:'content',title:'通知内容',width:100,align:'center'},
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
				},
				//{field:'auditMan',title:'审核人',width:100,align:'center',},
				{field:'auditTime',title:'审核日期',width:100,align:'center',
					formatter: function(value,row,index){
						if (value){
							return value.substring(0,19);
						} else {
							return value;
						}
					}
				}
    		]],   
			toolbar : '#tb'
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
				{field:'secondayName',title:'届次名称',align:'center',width:140,sortable:false,formatter: function(value, row) {
				   	var str=row.secondayName+"("+row.year+"年度)"
					return str;
				}},				
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
	
	//查看
	function view() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			var p = dj.dialog({
				title : '发言详情',
				href : '${pageContext.request.contextPath}/speech!speechSee.do?speakId='+ rows[0].speakId,
				maximized : true,
				maximizable : true,
				width : 800,
				height : 600,
				onLoad : function() {
					initCombox('speechViewForm');
				},
				buttons : [ {
					text : '关闭 ',
					iconCls : 'icon-cancel',
					handler : function() {
						p.dialog('close');
					}
				} ]

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
			for(var i =0;i<rows.length;i++){
				$('#secondaryID').val(rows[i].secondaryId);
				$('#yearID').val(rows[i].year);
			}
		 }
		datagrid.datagrid('load', dj.serializeObject($('#speech_queryForm')));
		 $('#win_speech_query').window('close');
	}
	
	function cleanSearch() {
		datagrid.datagrid('load', {});
		$('#speech_queryForm input').val('');
		$('#statusId').combobox('setValue', '');
		$('#secondaryId').combobox('setValue', '${sessionSeco.secondaryId}');
		$('#yearID').val('${sessionSeco.year}');
		var rows=$('#gridjc').datagrid('getRows');
		 if(rows.length>0){
			for(var i =0;i<rows.length;i++){
				if(rows[i].status=='1') {
					$('#gridjc').datagrid('uncheckAll');
					$('#gridjc').datagrid('checkRow', i);
				}
			}
		 }
		$('#secondaryID').val("");
	}
	
	//导出发言稿
	function downWord(){
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			$('#downWordBtId').linkbutton('disable');
			$('#downWordBtId .l-btn-text').html('文件生成中,请稍候...');
			var speakId=rows[0].speakId;
			var secondaryid=$("#secondaryID").val();
			var year=$("#yearID").val();
			//生成发言稿
			$.ajax({
				url:'${pageContext.request.contextPath}/word!doNotNeedAuth_speechDownload.do?speakId='+speakId+'&secondaryCode='+secondaryid+'&year='+year,
					success : function(d) {
					var json = $.parseJSON(d);
					if (json.success) {
							//打开
							$.ajax({
								url :"${pageContext.request.contextPath}/word!doNotNeedAuth_isfile.do?fileName=speech_"+year+speakId+".doc",
								success : function(d) {
									var json = $.parseJSON(d);
									if (json.success) {
										$('#downWordBtId').linkbutton('enable');
										$('#downWordBtId .l-btn-text').html('导出');
										window.location.href="${pageContext.request.contextPath}/wordfile/speech_"+year+speakId+".doc";
									}else{
										dj.messagerShow({
											msg : json.msg,
											title : '提示'
										});
									}
								}
							});
					}else{
						dj.messagerShow({
							msg : json.msg,
							title : '提示'
						});
					}
				}
			});
		} else if (rows.length > 1) {
			dj.messagerAlert('提示', '同一时间只能导出一篇发言稿！', 'error');
		} else {
			dj.messagerAlert('提示', '请选择要导出的发言稿！', 'error');
		}

	}
</script>
</head>
<body class="easyui-layout" data-options="fit:true">
	<!-- 表格 -->
	<div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="speechDatagrid"></table>
	</div>
	<div id="win_speech_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:650px;height:270px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="speech_queryForm">
				<input type="hidden" id="secondaryID" name="secondaryId">
				<input type="hidden" id="yearID" name="year">
				<table id="speech_queryTable">
					<tr height="30">
						<td nowrap>发言主题：</td>
						<td><input type="text" name="title" class="easyui-validatebox" style="width:280px;padding:2px;"></td>
						<td rowspan="5" width="200">
							<table id="gridjc"></table>
						</td>
					</tr>			
					<tr height="30">
						<td nowrap>所属会议：</td>
						<td><input type="text" name="meetName" class="easyui-validatebox" style="width:280px;padding:2px;"></td>
					</tr>
					<tr height="30">
						<td nowrap>发言人：</td>
						<td><input type="text" name="name" class="easyui-validatebox" style="width:280px;padding:2px;"></td>
					</tr>
					<tr height="30">
						<td nowrap>提交时间：</td>
						<td> <input class="easyui-datebox" name="createTimeStart"   style="width:136px"/>--<input class="easyui-datebox" name="createTimeEnd"  style="width:136px"/></td>
					</tr>
					
					<tr height="30">
						<td nowrap>状态：</td>
						<td> <input id="statusId" panelHeight="100px" name="status" style="width:286px;padding:2px;border:1px solid #000;"></td>
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
	<div id="tb">
			<div style="margin-bottom:1px">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="javascript:$('#win_speech_query').window('open');">查询</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-info" plain="true" onclick="view()">查看详细</a>
					<a href="javascript:void(0)" id="downWordBtId" class="easyui-linkbutton" iconCls="icon-down" plain="true" onclick="downWord()">导出</a>
			</div>
	</div>
</body>
</html>