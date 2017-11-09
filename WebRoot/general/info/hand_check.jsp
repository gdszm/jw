<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	function initCombox(form){
		initCombobox(form,'solveHow','${pageContext.request.contextPath}/dic!combox.do?ctype=JJCD');
		initCombobox(form,'carryoutFlg','${pageContext.request.contextPath}/dic!combox.do?ctype=YESNO');
		initCombobox(form,'committeemanOpinion','${pageContext.request.contextPath}/dic!combox.do?ctype=WYYJ');
		initCombobox(form,'handleType','${pageContext.request.contextPath}/dic!combox.do?ctype=BLLX');
		initCombobox(form,'stutas','${pageContext.request.contextPath}/dic!combox.do?ctype=TAZT');
	}
	
	var datagrid;	
	$(document).ready(function() {
		datagrid=$('#listgrid');
		var startType='<%=request.getAttribute("statusType")%>';
		var companycode='<%=request.getAttribute("companyId")%>';
		var fieldCode='<%=request.getAttribute("field")%>';
		var url='${pageContext.request.contextPath}/prop!sdatagrid.do?submitFlg=1';
		if(startType!=""&& startType!=null){
			
			if(startType=='I'){
				url+='&stutas=7';
			}
			if(startType=='G'){
				url+='&replyPass=0';
			}
			if(startType=='H'){
				url+='&stutas=4&replyPass=1';
			}
		}
		if(companycode!="" && companycode!="null"){
			url+="&companyId="+companycode;
		}
		if(fieldCode=="tote_all"){
			url+="&handleType=tote_all";
		}
		if(fieldCode=="tote_no"){
			url+="&handleType=tote_no";
		}
		if(fieldCode=="single_all"){
			url+="&handleType=single_all";
		}
		if(fieldCode=="single_no"){
			url+="&handleType=single_no";
		}
		if(fieldCode=="branch_all"){
			url+="&handleType=branch_all";
		}
		if(fieldCode=="branch_no"){
			url+="&handleType=branch_all";
		}
		if(fieldCode=="host_all"){
			url+="&handleType=host_all";
		}
		if(fieldCode=="host_no"){
			url+="&handleType=host_no";
		}
		datagrid.datagrid({
			url:url,
			width: 'auto',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			singleSelect:true,
			pagination:true,
			pageSize:10,
			pageList:[10,15,20],
			pageNumber:1,
			idField:'proposalId',
			sortName : 'proposalId',
			sortOrder : 'asc',
			fit:true,
			columns:[[
				{field:'proposalId',title:'操作',align:'center',width:60,formatter:rowformater},
				{field:'proposalCode',title:'提案编号',align:'center',width:70},
				{field:'title',title:'案&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;由',halign:'center',align:'left',width:380,sortable:true,formatter:function(val,rec){
					var str=val;
					if (rec.stutas=='4'&& rec.replyPass=='0'){
						str='<font color="#009100"><b>（审）</b></font>'+str;
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
					if(rec.submitFlg=='1'&&rec.replyPass=='0'){
						str="未答复";
					}
					if(rec.stutas=='7'&&rec.submitFlg=='1'&&rec.replyPass=='0'){
						str="申退中";
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
				//{field:'secondFlgName',title:'可否附议',align:'center',width:80,sortable:true,hidden:true},
				{field:'propTypeName',title:'内容分类',align:'center',width:80,sortable:true},
				//{field:'handleHowName',title:'办理解决程度',align:'left',width:120,sortable:true},
				{field:'excellentFlgName',title:'优秀提案',align:'center',width:60,sortable:true},
				{field:'stressFlgName',title:'重点提案',align:'center',width:60,sortable:true},
				{field:'leader',title:'督办领导',align:'center',width:80,sortable:true},
				//{field:'adoptFlgName',title:'是否采纳',align:'center',width:80,sortable:true},
				{field:'mergeFlagName',title:'合并提案',align:'center',width:60,sortable:true},
				{field:'undertakeUnit',title:'建议办理单位',halign:'center',align:'left',width:200,sortable:false,hidden:true},
				{field:'adviceUnitName',title:'拟定办理单位',halign:'center',align:'left',width:200,sortable:false,hidden:true},
				{field:'comps',title:'办理单位',halign:'center',align:'left',width:200,sortable:false}
			]],				
			toolbar : [{
				text : '查询',
				iconCls : 'icon-search',
				handler : function() {
					query();
				}
			}, '-',  {
				text : '答复审查',
				iconCls : 'icon-reply',
				handler : function() {
					add();
				}
			}, '-',  {
				text : '调整单位',
				iconCls : 'icon-edit',
				handler : function() {
					change();
				}
			}, '-', {
				text : '拒绝申退',
				iconCls : 'icon-refuse',
				handler : function() {
					refuse();
				}
			}, '-', {
				text : '查看提案',
				iconCls : 'icon-view',
				handler : function() {
					view();
				}
			}],
			onRowContextMenu : function(e, rowIndex, rowData) {
				e.preventDefault();
				$(this).datagrid('unselectAll');
				$(this).datagrid('selectRow', rowIndex);
				$('#menu').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			}
		});
		//以下初始化查询combox列表
		initCombox('searchForm');
	});
    function rowformater(value,row,index){
    	if(row.stutas=='7'){
    		return '<a href="javascript:void(0)" onclick="info('+row.proposalId+')">申退理由</a>';
    	}else{
    		return "";
    	}
    }
    
    //申退理由查看
    function info(propId){
   		 p = dj.dialog({
				title : '申退理由',
				href : '${pageContext.request.contextPath}/hand!backInfo.do?proposalId='+propId,
				width : 620,
				height : 430,
				iconCls:'icon-save',
				buttons : [ {
						text: '关闭', 
						iconCls:'icon-cancel',
						handler: function() { 
							p.dialog('close'); 
						} 
					}]
		});
    }
	function add() {
		var row = datagrid.datagrid('getSelected');
		if (row) {
			var propId=row.proposalId;
			window.open("${pageContext.request.contextPath}/hand!list.do?proposalId="+propId);
		}else{
			dj.messagerAlert('提示', '请选择要查看的提案！', 'error');
		}		
		return false;
	}
	
	function change(){
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			if(rows[0].stutas !='7'){
				parent.dj.messagerAlert('提示', '选中记录不能进行单位调整!', 'error');
				return ;
			}
			p = dj.dialog({
				title : '提案审查立案',
				href : '${pageContext.request.contextPath}/prop!propConfirm.do?proposalId='+rows[0].proposalId,
				maximized:true,
				maximizable:true,
				width : 800,
				height : 500,
				iconCls:'icon-save',
				buttons : [ {
						text : '提交',
						iconCls:'icon-ok',
						handler : function() {
							confirmSave();
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
						//initCombobox('confirmForm','fileMethod','${pageContext.request.contextPath}/dic!combox.do?ctype=LAXS');
						//initCombobox('confirmForm','propertiesType','${pageContext.request.contextPath}/dic!combox.do?ctype=TAFL');
						//initCombobox('confirmForm','businessName','${pageContext.request.contextPath}/dic!combox.do?ctype=YWFZ');
						initCombobox('confirmForm','handleType','${pageContext.request.contextPath}/dic!combox.do?ctype=BLLX');
						initCombobox('confirmForm','analySisUnit','${pageContext.request.contextPath}/dic!combox.do?ctype=FXDW');
						/*var f = p.find('form');
						f.form('load', {
							proposalId:rows[0].proposalId,
							proposalCode:rows[0].proposalCode,
							title:rows[0].title,
							undertakeUnit:rows[0].undertakeUnit,
							fistProposaler:rows[0].fistProposaler,
							fileMethod:rows[0].fileMethod,
							analySisUnit:rows[0].analySisUnit,
							propertiesType:rows[0].propertiesType,
							typeId:rows[0].typeId,
							businessCode:rows[0].businessCode,
							handleType:rows[0].handleType,
														
					    });
					    $('#contents').html(rows[0].content);*/
					    var d=new Date();
						var rq=d.getFullYear() + "-" + d.getMonth()+1 +"-" + d.getDate()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds();
						$('#submitTime').val(rq);
					    
					    $(document).ready(function() {
					    	//提案承办单位列表
							$('#cbdwgrid').datagrid({
								url:'${pageContext.request.contextPath}/hand!datagridByProp.do?proposalId='+rows[0].proposalId,
								width:'auto',
								height:'auto',
								striped:true,
								nowrap:true,
								rownumbers:true,
								fitColumns:true,
								fit:true,
								columns:[[
									{field:'companyId',title:'单位编号',align:'center',width:80,sortable:false},
									{field:'companyName',title:'单位名称',align:'left',width:220,sortable:false},
									{field:'companyTypeName',title:'单位类别',align:'left',width:170,sortable:false},
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
	function confirmSave(){
		var compdata=$('#cbdwgrid').datagrid('getRows');
		var comps=[];
		var mainFlgs=[];
		for(var i = 0; i<compdata.length ;i++){
			comps.push(compdata[i].companyId);
			mainFlgs.push(compdata[i].mainFlg);
		}
		
		$("#companyIds").val(comps.join(','));
		$("#mainFlgs").val(mainFlgs.join(','));
		$('#confirmForm').form('submit', {
			url : '${pageContext.request.contextPath}/prop!confirm.do',
			success : function(d) {
				var json = $.parseJSON(d);
				if (json.success) {
					tagrid.datagrid('reload');
					p.dialog('close');
				}
					dj.messagerShow({
					msg : json.msg,
					title : '提示'
				});
			}
		});
	}
	function dwadd(){
		var handleTypeCode=$('#handleTypeCheck').combobox('getValue')//获取当前选中的值
		var handleTypeName=$('#handleTypeCheck').combobox('getText')//获取当前选中的文字
		if(handleTypeName=="请选择..."){
			parent.dj.messagerAlert('提示', '请先设置办理方式！', 'info');
			return;
		}
		var single=false;
		if(handleTypeCode=='1'){
			single=true;
		}
		secondp = dj.dialog({
			title : '办理单位设置',
			href : '${pageContext.request.contextPath}/prop!checkAdd.do',
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
				if(handleTypeCode=='3'){
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
		if((handleType=='1' && rows.length>1) || (handleType=='1' && cbdwrows.length>0)){
			dj.messagerAlert('提示', '提案是单办提案,只能选择一个办理单位！', 'error');
			return;
		}
		if(rows.length>0){
				var mainhost=$("#hostHandCode").val();
				for(var i = 0; i<rows.length ;i++){
					for(var j=0;j<cbdwrows.length;j++){
						if(rows[i].companyId==cbdwrows[j].companyId){
							dj.messagerAlert('提示', '添加单位已存在！', 'error');
							return;
						}
					}
					if(handleType=='3'){
						if(rows[i].companyId==mainhost){
							$('#cbdwgrid').datagrid('appendRow',{
								companyId:rows[i].companyId,
								companyName:rows[i].companyName,
								companyType:rows[i].companyType,
								companyTypeName:rows[i].companyTypeName,
								mainFlg:'1',
								mainFlgName:'主办'
							});
						}else{
							$('#cbdwgrid').datagrid('appendRow',{
								companyId:rows[i].companyId,
								companyName:rows[i].companyName,
								companyType:rows[i].companyType,
								companyTypeName:rows[i].companyTypeName,
								mainFlg:'0',
								mainFlgName:'协办'
							});
						}
					}else{
						$('#cbdwgrid').datagrid('appendRow',{
							companyId:rows[i].companyId,
							companyName:rows[i].companyName,
							companyType:rows[i].companyType,
							companyTypeName:rows[i].companyTypeName,
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
			dj.messagerAlert('提示', '请选择要删除的记录！', 'error');
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
	//拒绝申退
	function refuse() {
		var row = datagrid.datagrid('getSelected');
		if (row) {
			if(row.stutas!='7'){
				dj.messagerAlert('提示', '当前提案没有申退！', 'error');
				return;
			}
			var propId=row.proposalId;
			$.ajax({
				url : '${pageContext.request.contextPath}/hand!refuse.do?proposalId='+propId,
				success : function(d) {
					var json = $.parseJSON(d);
					if (json.success) {
						dj.messagerShow({
							msg : json.msg,
							title : '提示'
						});
						datagrid.datagrid('reload');
					}
				}
			});
		}else{
			$.messager.alert('提示', '请选择要操作的提案！', 'info');
		}
	}
	
	function view(){
		var selectRow = datagrid.datagrid('getSelected');
		if (selectRow) {
			var propId=selectRow.proposalId;
			window.open("${pageContext.request.contextPath}/prop!propSee.do?proposalId="+propId);
		}else{
			dj.messagerAlert('提示', '请选择要查看的提案！', 'error');
		}		
		return false;
	}

	function report(){
		var totalRowNum = datagrid.datagrid('getPager').data("pagination").options.total;
		if(totalRowNum>0){
			$.ajax({
				url : '${pageContext.request.contextPath}/hand!report.do',
				success : function(d) {
					var json = $.parseJSON(d);
					if (json.success) {
						window.open("${pageContext.request.contextPath}/" + json.obj);
					}
				}
			});
		}else{
			$.messager.alert('提示', '当前无记录，无需导出数据！', 'info');
		}
	}
	
	function query() {
		$('#searchForm').form('clear');
		$('#win_query').window('open');
	}
	
	function _search() {	//此处方法不能为search()
		datagrid.datagrid('load', dj.serializeObject($('#searchForm')));
		$('#win_query').window('close');
	}
	
	function cleanSearch() {
		gridlist.datagrid('load', {});
		$("input,textarea,select").val("");
	}

</script>
</head>

<body class="easyui-layout">
	<!-- 查询窗口-->
	<div id="win_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:550px;height:300px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="searchForm">
				<table id=tableForm>				
					<tr height="30">
						<td nowrap>提案编号：</td>
						<td><input type="text" name="proposalCode" style="width:360px;padding:2px"></td>
					</tr>
					<tr height="30">
						<td nowrap>案&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;由：</td>
						<td> <input type="text" name="title" style="width:360px;padding:2px"></td>
					</tr>
					<tr height="30">
						<td nowrap>第一提案人：</td>
						<td> <input type="text" name="fistProposaler" style="width:360px;padding:2px"></td>
					</tr>
					<tr height="30">
						<td nowrap>承办单位：</td>
						<td> <input type="text" name="companyName" style="width:360px;padding:2px"></td>
					</tr>
					<tr height="30">
						<td nowrap>办理类型：</td>
						<td> <input  name="handleType" style="width:160px;padding:2px"></td>
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
	<!-- Datagrid表格 -->
	<div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="listgrid"></table>
	</div>
	<!-- 菜单 -->
	<div id="menu" class="easyui-menu" style="width:120px;display: none;">
		<div onclick="add();" data-options="iconCls:'icon-reply'">答复审查</div>
		<div onclick="change();" data-options="iconCls:'icon-edit'">调整单位</div>
		<div onclick="refuse();" data-options="iconCls:'icon-refuse'">拒绝申退</div>
		<div onclick="view();" data-options="iconCls:'icon-view'">查看提案</div>
	</div>
</body>
</html>