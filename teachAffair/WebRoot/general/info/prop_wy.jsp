<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript">
	var gridmyta;
	var p;
	
	$(document).ready(function() {
		gridmyta=$('#mytagrid');
		var startType='<%=request.getAttribute("statusType")%>';
		var url='${pageContext.request.contextPath}/prop!wydatagrid.do';
		if(startType!=""&& startType!=null){
			if(startType=='A'){
				url+='?stutas=1';
			}
			if(startType=='B'){
				url+='?stutas=2&fileMethod=space';
			}
			if(startType=='C'){
				url+='?fileMethod=1';
			}
			if(startType=='D'){
				url+='?fileMethod=0';
			}
			if(startType=='E'){
				url+='?stutas=2&submitFlg=0&fileMethod=0';
			}
			if(startType=='F'){
				url+='?submitFlg=1';
			}			
			if(startType=='G'){
				url+='?submitFlg=1&replyPass=0';
			}
			if(startType=='H'){
				url+='?stutas=4&submitFlg=1&replyPass=1';
			}
		}
		gridmyta.datagrid({
			url:url,
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
			pageList:[10,15,20],
			pageNumber:1,
			idField:'proposalId',
			fit:true,
			frozenColumns:[[
				{field:'proposalId',hidden:true}, 
				{field:'proposalCode',title:'提案编号',align:'center',width:60}
			]],
			columns:[[
				{field:'title',title:'案&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;由',halign:'center',align:'left',width:380,sortable:true,formatter:function(val,rec){
					var str=val;
					if (rec.adviceFlg=='1'){
						str='<font color="red"><b>（退回修改）</b></font>' + str;
					}					
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
				{field:'proposalTypeName',title:'提案类型',align:'center',width:80,sortable:true,hidden:true},
				{field:'stutasName',title:'状态',align:'center',width:50,sortable:true,formatter:function(val,rec){
					var str=val;
					if(rec.stutas=='1'){
						str="未审查";
					}
					if(rec.stutas=='2'){
						str="已审查";
					}
					if(rec.stutas=='2'&&rec.fileMethod=='0'){
						str="已立案";
					}
					if(rec.stutas=='2'&&rec.fileMethod=='1'){
						str="未立案";
					}
					if(rec.stutas=='2'&&rec.fileMethod=='0'&&rec.filingFlg=='1'){
						str="未交办";
					}
					if(rec.filingFlg=='1'&&rec.submitFlg=='1'){
						str="已交办";
					}
					if(rec.submitFlg=='1'&&rec.replyPass!='0'){
						str="未答复";
					}
					if(rec.stutas=='4'&&rec.submitFlg=='1'&& rec.replyPass=='1'){
						str="已答复";
					}
					return str;
				}},
				//{field:'replyPassName',title:'办复审查状态',align:'center',width:80,sortable:true},
				//{field:'fileMethodName',title:'是否立案',align:'center',width:140,sortable:true},
				{field:'handleTypeName',title:'办理方式',align:'center',width:60,sortable:true},
				{field:'webFlgName',title:'可否公开',align:'center',width:80,sortable:true,hidden:true},
				{field:'secondFlgName',title:'可否附议',align:'center',width:80,sortable:true,hidden:true},
				{field:'propTypeName',title:'内容分类',align:'center',width:80,sortable:true,hidden:true},
				//{field:'handleHowName',title:'办理解决程度',align:'left',width:120,sortable:true},
				{field:'excellentFlgName',title:'优秀提案',align:'center',width:60,sortable:true},
				{field:'stressFlgName',title:'重点提案',align:'center',width:60,sortable:true},
				{field:'leader',title:'督办领导',align:'center',width:80,sortable:true},
				//{field:'revokeFlgName',title:'是否撤案',align:'center',width:80,sortable:true},
				{field:'mergeFlagName',title:'合并提案',align:'center',width:60,sortable:true},
				{field:'undertakeUnit',title:'提案人建议办理单位',halign:'center',align:'left',width:200,sortable:false},
				{field:'comps',title:'办理单位',halign:'center',align:'left',width:200,sortable:false},
				{field:'solveHowName',title:'解决程度',align:'center',width:180},
				//{field:'carryoutFlgName',title:'是否落实 ',align:'center',width:60},
				{field:'committeemanOpinionName',title:'提案人意见',align:'center',width:80}
			]],			
			toolbar: '#tb'
		});
	});
	function add() {
		window.location.href="${pageContext.request.contextPath}/prop!propsub.do";
	}
	//修改提案
	function editTa(){
		var rows = gridmyta.datagrid('getSelections');
		if (rows.length == 1) {
			if(rows[0].stutas!='0'){
				parent.dj.messagerAlert('提示', '选中记录已提交,不能进行修改!', 'error');
				return ;
			}
			p = dj.dialog({
				title : '修改提案',
				href : '${pageContext.request.contextPath}/prop!propWyEdit.do?proposalId=' + rows[0].proposalId,
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
							submitTa(rows[0].proposalId);
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
						initCombobox('taeditForm','secondFlg','${pageContext.request.contextPath}/dic!combox.do?ctype=FYYGK');
						initCombobox('taeditForm','webFlg','${pageContext.request.contextPath}/dic!combox.do?ctype=FYYGK');
				}
			});
		} else if (rows.length > 1) {
			parent.dj.messagerAlert('提示', '同一时间只能编辑一条记录！', 'error');
		} else {
			parent.dj.messagerAlert('提示', '请选择要编辑的记录！', 'error');
		}
	}
	
		//工具栏提交提案
	function subTa(){
		var rows = gridmyta.datagrid('getSelections');
		if (rows.length == 1) {
			if(rows[0].stutas!='0'){
				parent.dj.messagerAlert('提示', '选中记录已提交,不需要再次提交!', 'error');
				return ;
			}
			$.ajax({
				url : '${pageContext.request.contextPath}/prop!submit.do?proposalId=' + rows[0].proposalId,
				success : function(d) {
					var json = $.parseJSON(d);
					if (json.success) {
						gridmyta.datagrid('reload');
					}
					dj.messagerShow({
						msg : json.msg,
						title : '提示'
					});
				}
				
			});
		} else if (rows.length > 1) {
			parent.dj.messagerAlert('提示', '同一时间只能编辑一条记录！', 'error');
		} else {
			parent.dj.messagerAlert('提示', '请选择要编辑的记录！', 'error');
		}
	}
	
	function edit(){
		var f =$("#taeditForm");
		//$("#content").val(UE.getEditor('editor').getContent()); 
		f.form('submit', {
			url : '${pageContext.request.contextPath}/prop!edit.do',
			success : function(d) {
				var json = $.parseJSON(d);
				if (json.success) {
					gridmyta.datagrid('reload');
					p.dialog('close');
				}
				dj.messagerShow({
					msg : json.msg,
					title : '提示'
				});
			}
		});
	}
	   //提交提案
	function submitTa(proposalId){
		parent.dj.messagerConfirm('请确认', '点击提交后，提案不能再进行修改！', function(r) {
			if (r) {
				edit();
				$.ajax({
					url : '${pageContext.request.contextPath}/prop!submit.do?proposalId=' + proposalId,
					success : function(d) {
						var json = $.parseJSON(d);
						if (json.success) {
							gridmyta.datagrid('reload');
							
						}
					}
				});
			}
		});
	}
	
	//查询
	function _search() {
		gridmyta.datagrid('load', dj.serializeObject($('#myta_queryForm')));
		 //$('#dolog_queryForm').form('clear');
		 $('#win_dolog_query').window('close');
	}
	//重置
	function cleanSearch() {
		gridmyta.datagrid('load', {});
		$('#myta_queryForm input').val('');
	}
	//设置满意度
	function satisfy(){
		var row = gridmyta.datagrid('getSelected');
		if (row) {
			if(row.stutas!='4'){
				parent.dj.messagerAlert('提示', '选中记录未办理完成,不能进行设置!', 'error');
				return ;
			}
			if(row.replyPass!='1'){
				parent.dj.messagerAlert('提示', '选中记录办复报告未通过,不能进行设置!', 'error');
				return ;
			}
			var propId=row.proposalId;
			window.open("${pageContext.request.contextPath}/hand!satisfy.do?proposalId="+propId);

		} else {
			parent.dj.messagerAlert('提示', '请选择要查看的记录！', 'error');
		}
	}
	//撤案
	function revoke(){
		var rows = gridmyta.datagrid('getSelections');
		if (rows.length >0) {
			if(rows[0].stutas!='1'){
				parent.dj.messagerAlert('提示', '当前提案不能进行撤案操作！', 'error');
				return;
			}

			parent.dj.messagerConfirm('请确认', '确定要进行撤案吗！', function(r) {
				if (r) {
	
					var propIds=[];
					for(var i = 0; i<rows.length ;i++){
						propIds.push(rows[i].proposalId);
					}
					var pids=propIds.join(',');
					 $.ajax({
						type:"POST",
						url:"${pageContext.request.contextPath}/prop!delete.do",
						data:{
							ids:pids
						},
						success:function(d){
							var json = $.parseJSON(d);
							if (json.success) {
								gridmyta.datagrid('reload');
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
			parent.dj.messagerAlert('提示', '请选择要撤案的记录！', 'error');
		}
	}
//查看提案
	function lookta(){
		var selectRow = gridmyta.datagrid('getSelected');
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
		var totalRowNum = gridmyta.datagrid('getPager').data("pagination").options.total;
		if(totalRowNum>0){
			$.ajax({
				url : '${pageContext.request.contextPath}/prop!wyreport.do',
				success : function(d) {
					var json = $.parseJSON(d);
					if (json.success) {
						window.location.href=json.obj;
						/*$.ajax({
							url : '${pageContext.request.contextPath}/download.do?fileName='+json.obj
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
			<table id="mytagrid"></table>
		</div>
	</div>
	<div id="columnmenu" class="easyui-menu" style="width:100px;"></div>

	<div id="tb" style="padding:2px;height:auto">
		<div style="margin-bottom:1px">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="$('#win_myta_query').window('open')">查询</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add()">新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editTa()">修改</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-submit" plain="true" onclick="subTa()">提交提案</a>
			<!--<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-sort" plain="true" onclick="satisfy()">满意度</a>  -->
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="revoke()">撤案</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-view" plain="true" onclick="lookta()">查看</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="report()">报表</a>
		</div>
	</div>
	<div id="win_myta_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:500px;height:220px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="myta_queryForm">
				<table id="myta_queryTable">				
					<tr height="25">
						<td nowrap>提案编号：</td>
						<td><input type="text" name="proposalCode" style="width:360px;padding:2px;border:1px solid #000;"></td>
					</tr>
					<tr height="25">
						<td nowrap>案&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;由：</td>
						<td> <input type="text" name="title" style="width:360px;padding:2px;border:1px solid #000;"></td>
					</tr>
				</table>
				</form>
				</center>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;height:40px">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)" onclick="_search()">查询</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)" onclick="cleanSearch()">重置</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#win_myta_query').window('close');">取消</a>
			</div>
		</div>
	</div>
</body>
</html>
