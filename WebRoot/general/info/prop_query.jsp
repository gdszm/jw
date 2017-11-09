<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript">
	var tagrid;
	var p;
	$(document).ready(function() {
		
		
		$('#gridjc').datagrid({
			width: '100%',
			height:'100%',
			url:'${pageContext.request.contextPath}/seco!datagridNoPage.do',
			fit:true,
			idField:'secondaryId',
			striped:true,
			frozenColumns : [[
				{field:'secondayId',title:'选择',checkbox:true},
			]],
			columns:[[
				{field:'secondayName',title:'届次名称',align:'center',width:140,sortable:false}
			]],
			onLoadSuccess:function(row){ //当表格成功加载时执行默认届次选定                
                var rowData = row.rows;
                $.each(rowData,function(idx, val){//遍历JSON  
                    if(val.status=='1'){  
                        $('#gridjc').datagrid("selectRow", idx); 
						 $('#ids').val(val.secondaryId);
                    }  
                });
				initData();
                   
            }
		});
	});
	function initData(){
		tagrid=$('#tagrid');
		//提案列表
		tagrid.datagrid({
			url:'${pageContext.request.contextPath}/prop!querydatagrid.do',
			width: 'auto',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			singleSelect:true,
			sortName: 'proposalCode',
			sortOrder: 'asc',
			pagination:true,
			pageSize:20,
			pageList:[20,50,100],
			pageNumber:1,
			idField:'proposalId',
			fit:true,
			queryParams:dj.serializeObject($('#ta_queryForm')),
			frozenColumns:[[
				{field:'proposalId',hidden:true}, 
				{field:'secondayName',title:'届次',align:'center',width:80},
				{field:'proposalCode',title:'提案编号',align:'center',width:60}
			]],
			columns:[[
				{field:'title',title:'案&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;由',halign:'center',align:'left',width:380,sortable:true,formatter:function(val,rec){
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
				{field:'fistProposaler',title:'第一提案人',align:'center',width:100,sortable:true},				
				{field:'proposalerNum',title:'提案人数',align:'center',width:80,sortable:true,hidden:true},
				{field:'stutasName',title:'状态',align:'center',width:50,sortable:true,formatter:function(val,rec){
					var str=val;
					if(rec.stutas=='2'&&rec.fileMethod=='0'){
						str="已立案";
					}
					if(rec.stutas=='2'&&rec.fileMethod=='1'){
						str="未立案";
					}
					if(rec.stutas=='2'&&rec.fileMethod=='0'&&rec.submitFlg=='0'){
						str="未交办";
					}
					if(rec.replyPass!='1'&&rec.submitFlg=='1'){
						str="已交办";
					}
					if(rec.stutas=='4'&& rec.replyPass=='1'){
						str="已答复";
					}
					if(rec.stutas=='4'&&rec.replyPass!='1'){
						str="未答复";
					}
					return str;
				}},
				{field:'handleTypeName',title:'办理方式',align:'center',width:60,sortable:true},
				{field:'webFlgName',title:'可否公开',align:'center',width:80,sortable:true,hidden:true},
				{field:'secondFlgName',title:'可否附议',align:'center',width:80,sortable:true,hidden:true},
				{field:'propTypeName',title:'内容分类',align:'center',width:80,sortable:true,hidden:true},
				//{field:'handleHowName',title:'办理解决程度',align:'left',width:120,sortable:true},
				{field:'excellentFlgName',title:'优秀提案',align:'center',width:60,sortable:true},
				{field:'stressFlgName',title:'重点提案',align:'center',width:60,sortable:true},
				{field:'leader',title:'督办领导',align:'center',width:80,sortable:true},
				//{field:'adoptFlgName',title:'是否采纳',align:'center',width:80,sortable:true},
				{field:'mergeFlagName',title:'合并提案',align:'center',width:60,sortable:true},
				{field:'submitTime',title:'交办日期',align:'center',width:100},
				{field:'undertakeUnit',title:'建议办理单位',halign:'center',align:'left',width:200,sortable:false,hidden:true},
				{field:'adviceUnitName',title:'拟定办理单位',halign:'center',align:'left',width:200,sortable:false,hidden:true},
				{field:'comps',title:'办理单位',halign:'center',align:'left',width:200,sortable:false},
				{field:'solveHowName',title:'解决程度',align:'center',width:180},
				//{field:'carryoutFlgName',title:'是否落实 ',align:'center',width:60},
				{field:'committeemanOpinionName',title:'提案人意见',align:'center',width:80}
			]],			
			toolbar: '#tb'
		});
		getcombobox('fileMethod','${pageContext.request.contextPath}/dic!combox.do?ctype=LAXS');
		getcombobox('propertiesType','${pageContext.request.contextPath}/dic!combox.do?ctype=TAFL');
		getcombobox('handleType','${pageContext.request.contextPath}/dic!combox.do?ctype=BLLX');
	
	}
	//提案查询
	function _search() {
		
		var selectRows = $('#gridjc').datagrid('getSelections');
		selectRows.sort(function(a,b){
            return a.secondaryId-b.secondaryId;
        });
        var jcs = [];
		if(selectRows.length > 0){
			for(var i=0; i<selectRows.length; i++){
				jcs.push(selectRows[i].secondaryId);
			}
		}else{
			$.messager.alert('操作提示', "请至少选择一个届次！",'info');
			return false;
		}
		$('#ids').val(jcs.join(','));
		/*$('#proposalCode').val('');
		$('#title').val('');
		$("#stutas").combobox('select', '请选择...');
		$("#fileMethod").combobox('select', '请选择...');
		$("#propertiesType").combobox('select','请选择...');
		$("#handleType").combobox('select', '请选择...');*/
		tagrid.datagrid('load', dj.serializeObject($('#ta_queryForm')));
		$('#win_ta_query').window('close');
	}
	//提案查询重置
	function cleanSearch() {
		tagrid.datagrid('load', {});
		$('#ids').val('');
		$('#proposalCode').val('');
		$('#title').val('');
		$("#fileMethod").combobox('select', '请选择...');
		$("#propertiesType").combobox('select','请选择...');
		$("#handleType").combobox('select', '请选择...');
	}

	//查看提案
	function lookta(){
		var selectRow = tagrid.datagrid('getSelected');
		if (selectRow) {
			var propId=selectRow.proposalId;
			window.open("${pageContext.request.contextPath}/prop!propSee.do?proposalId="+propId);
		}else{
			dj.messagerAlert('提示', '请选择要查看的提案！', 'error');
		}		
		return false;
	}
	//查看提案
	function see(propId){
		window.open("${pageContext.request.contextPath}/prop!propSee.do?proposalId="+propId);
	}
	//报表
	function report(){
		var totalRowNum = tagrid.datagrid('getPager').data("pagination").options.total;
		if(totalRowNum>0){
			$.ajax({
				url : '${pageContext.request.contextPath}/prop!queryreport.do',
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
			<table id="tagrid"></table>
		</div>
	</div>
	<div id="columnmenu" class="easyui-menu" style="width:100px;"></div>

	<div id="tb" style="padding:2px;height:auto">
		<div style="margin-bottom:1px">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="$('#win_ta_query').window('open')">查询</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-view" plain="true" onclick="lookta()">查看</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="report()">报表</a>
		</div>
	</div>
	<div id="win_ta_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:650px;height:300px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="ta_queryForm">
				<input type="hidden" id="ids" name="ids"/>
				<table id="myta_queryTable">	
					<tr height="25">
						<td nowrap>提案编号：</td>
						<td><input type="text" id="proposalCode" name="proposalCode" style="width:300px;padding:2px;border:1px solid #000;"></td>
						<td rowspan="8" width="200">
							<table id="gridjc" ></table>
						</td>
					</tr>
					<tr height="25">
						<td nowrap>案&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;由：</td>
						<td> <input type="text" id="title" name="title" style="width:300px;padding:2px;border:1px solid #000;"></td>
					</tr>
					<tr height="25">
						<td nowrap>第一提案人：</td>
						<td> <input type="text" id="fistProposaler"  name="fistProposaler"  style="width:300px;padding:2px;border:1px solid #000;"/></td>
					</tr>
					<tr height="25">
						<td nowrap>内容分类：</td>
						<td> <input id="propertiesType" panelHeight="auto" name="propertiesType" value="请选择..." style="width:310px;padding:2px;border:1px solid #000;"/>
						</td>
					</tr>
					<tr height="25">
						<td nowrap>办理方式：</td>
						<td><input id="handleType" panelHeight="auto" name="handleType" value="请选择..." style="width:310px;padding:2px;border:1px solid #000;"/>
						 </td>
					</tr>
				</table>
				</form>
				</center>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;height:40px">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)" onclick="_search()">查询</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)" onclick="cleanSearch()">重置</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#win_ta_query').window('close');">取消</a>
			</div>
		</div>
	</div>
</body>
</html>