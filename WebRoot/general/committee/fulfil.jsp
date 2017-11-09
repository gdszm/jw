<%@ page language="java" pageEncoding="UTF-8"%>
<%--委员履职管理页面--%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	var p;
	var datagrid;
	$(function(){
		datagrid = $('#noticeDatagrid').datagrid({
			url:'${pageContext.request.contextPath}/committee!datagrid.do',
        	width: 'auto',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			idField:'code',
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
				{field:'code',title:'ID',width:100,align:'center',checkbox : true},
				{field:'name',title:'姓名',width:100,align:'center'}
				]],
			columns:[[
				{field:'companyName',title:'工作单位',width:200,align:'center'},
				{field:'job',title:'职务',width:200,align:'center'},
				{field:'committeeName',title:'所属专委会',width:150,align:'center'},
				{field:'sexName',title:'性别',width:40,align:'center'},
				{field:'birthDate',title:'出生日期',width:80,align:'center',
					formatter: function(value,row,index){
						if (value){
							return value.substring(0,19);
						} else {
							return value;
						}
					}	
				},
				{field:'nationName',title:'民族',width:50,align:'center'},
				//{field:'title',title:'籍贯',width:100,align:'center'},
				{field:'eduName',title:'学历',width:50,align:'center'},
				{field:'circleName',title:'界别',width:100,align:'center'},
				{field:'partyName',title:'党派',width:100,align:'center'},
				{field:'telephone',title:'移动电话',width:80,align:'center'}
    		]],   
			toolbar : '#tb'
			});
			
		//以下初始化查询combox列表
		initCombobox('searchForm','status','${pageContext.request.contextPath}/dic!combox.do?ctype=STATUS');
		initCombobox('searchForm','sex','${pageContext.request.contextPath}/dic!combox.do?ctype=SEX');
		initCombobox('searchForm','nation','${pageContext.request.contextPath}/dic!combox.do?ctype=NATION');
		initCombobox('searchForm','partyCode','${pageContext.request.contextPath}/dic!combox.do?ctype=PARTY');
		initCombobox('searchForm','circleCode','${pageContext.request.contextPath}/dic!combox.do?ctype=CIRCLE');
		initCombobox('searchForm','committee','${pageContext.request.contextPath}/dic!combox.do?ctype=COMMITTEE');

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
                    		$('#gridjc').datagrid("selectRow", idx); 
                    		$('#secondaryID').val(val.secondaryId);
                    		$('#yearID').val(val.year);
                    } else {
                    	$('#gridjc').datagrid("unselectRow", idx); 
                    }
                });
               
            }
		});
	});
	
	function view() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			var code=rows[0].code;
			window.open("${pageContext.request.contextPath}/fulfil!fulfilDetail.do?code="+code+"&secondaryCode="+$('#secondaryID').val());
		} else if (rows.length > 1) {
			dj.messagerAlert('提示', '同一时间只能查看一条记录！', 'error');
		} else {
			dj.messagerAlert('提示', '请选择要查看的记录！', 'error');
		}
		
		//window.open("${pageContext.request.contextPath}/fulfil!fulfilDetail.do");
	}
	//下载履职表
	function downWord(){
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			$('#downWordBtId').linkbutton('disable');
			$('#downWordBtId .l-btn-text').html('文件生成中,请稍候...');
			var code=rows[0].code;
			var secondaryid=$("#secondaryID").val();
			var year=$("#yearID").val();
			//生成委员考核表
			$.ajax({
				url:'${pageContext.request.contextPath}/word!doNotNeedAuth_fulfilDownload.do?code='+code+'&secondaryCode='+secondaryid+'&year='+year,
					success : function(d) {
					var json = $.parseJSON(d);
					if (json.success) {
							//打开
							$.ajax({
								url :"${pageContext.request.contextPath}/word!doNotNeedAuth_isfile.do?fileName=fulfil_"+year+code+".doc",
								success : function(d) {
									var json = $.parseJSON(d);
									if (json.success) {
										$('#downWordBtId').linkbutton('enable');
										$('#downWordBtId .l-btn-text').html('下载履职表');
										window.location.href="${pageContext.request.contextPath}/wordfile/fulfil_"+year+code+".doc";
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
			dj.messagerAlert('提示', '同一时间只能下载一位委员的履职表！', 'error');
		} else {
			dj.messagerAlert('提示', '请选择要下载履职表的委员！', 'error');
		}

	}
	function query() {
		$('#win_query').window('open');
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
		$('#win_query').window('close');
	}
	
	function cleanSearch() {
		$("input,textarea,select").val("");
		$('#gridjc').datagrid('load', {});
		
        datagrid.datagrid('load', dj.serializeObject($('#searchForm')));        
	}
</script>
</head>
<body class="easyui-layout" data-options="fit:true">
	<!-- 表格 -->
	<div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="noticeDatagrid"></table>
	</div>
	<div id="win_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:550px;height:350px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="searchForm" enctype=multipart/form-data method="post">
				<input type="hidden" id="secondaryID" name="secondaryId">
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
						<td align="left" ><input type="text" name="sex" id="sex" style="width:194px;padding:2px" /></td>
					</tr>
					<tr height="30">
						<td nowrap>民族：</td>
						<td align="left"><input type="text" name="nation" id="nation" style="width:194px;padding:2px" /></td>
					</tr>
					<tr height="30">
						<td nowrap>党派：</td>
						<td align="left"><input type="text" name="partyCode" id="partyCode" style="width:194px;padding:2px" /></td>
					</tr>
					<tr height="30">
						<td nowrap>界别：</td>
						<td align="left"><input type="text" name="circleCode" id="circleCode" style="width:194px;padding:2px" /></td>
					</tr>
					<tr height="30">
						<td nowrap>专委会：</td>
						<td align="left"><input type="text" name="committee" id="committee" style="width:194px;padding:2px" /></td>
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
	<div id="tb">
			<div style="margin-bottom:1px">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="javascript:query();">查询</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-info" plain="true" onclick="view()">履职信息</a>
					<a href="javascript:void(0)" id="downWordBtId" class="easyui-linkbutton" iconCls="icon-down" plain="true" onclick="downWord()">下载履职表</a>
			</div>
	</div>
</body>
</html>