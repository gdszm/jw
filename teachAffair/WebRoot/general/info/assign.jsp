<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript">
	var tagrid;
	var p;
	$(document).ready(function() {
		tagrid=$('#tagrid');
		var startType='<%=request.getAttribute("statusType")%>';
		var url='${pageContext.request.contextPath}/prop!assigndatagrid.do?filingFlg=1';
		if(startType!=""&& startType!=null){
			
			if(startType=='E'){
				url+='&stutas=2&fileMethod=0';
			}
			if(startType=='F'){
				url+='&submitFlg=1&replyPass=space';
			}
		}
		//提案列表
		tagrid.datagrid({
			url:url,
			width: 'auto',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			sortName: 'proposalCode',
			sortOrder: 'asc',
			pagination:true,
			pageSize:20,
			pageList:[20,50,100],
			pageNumber:1,
			idField:'proposalId',
			fit:true,
			frozenColumns:[[
				{field:'proposalId',checkbox:true,align:'center',width:40}, 
				{field:'proposalCode',title:'提案编号',align:'center',width:60}
			]],
			columns:[[
				{field:'title',title:'案&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;由',halign:'center',align:'left',width:380,sortable:true,formatter:function(val,rec){
					var str=val;
					if (rec.submitFlg=='0'){
						str='<font color="#009100"><b>（新）</b></font>'+str;
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
				{field:'proposalerNum',title:'提案人数',align:'center',width:80,sortable:true},
				{field:'proposalTypeName',title:'提案类型',align:'center',width:80,sortable:true},
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
					if(rec.stutas=='4'&&rec.submitFlg=='1'&& rec.replyPass=='1'){
						str="已答复";
					}
					
					return str;
				}},
				//{field:'fileMethodName',title:'是否立案',align:'center',width:140,sortable:true},
				//{field:'checkTime',title:'立案日期',align:'center',width:120,sortable:true},
				//{field:'submitFlgName',title:'交办状态',align:'center',width:80,sortable:true},
				{field:'submitTime',title:'交办日期',align:'center',width:120,sortable:true},
				{field:'handleTypeName',title:'办理方式',align:'center',width:60,sortable:true},
				{field:'webFlgName',title:'可否公开',align:'center',width:80,sortable:true,hidden:true},
				{field:'secondFlgName',title:'可否附议',align:'center',width:80,sortable:true,hidden:true},
				{field:'propTypeName',title:'内容分类',align:'center',width:80,sortable:true},
				{field:'excellentFlgName',title:'优秀提案',align:'center',width:60,sortable:true},
				{field:'stressFlgName',title:'重点提案',align:'center',width:60,sortable:true},
				{field:'leader',title:'督办领导',align:'center',width:80,sortable:true},
				//{field:'adoptFlgName',title:'是否采纳',align:'center',width:80,sortable:true},
				{field:'mergeFlagName',title:'合并提案',align:'center',width:60,sortable:true},
				{field:'undertakeUnit',title:'建议办理单位',halign:'center',align:'left',width:200,sortable:false,hidden:true},
				{field:'adviceUnitName',title:'拟定办理单位',halign:'center',align:'left',width:200,sortable:false,hidden:true},
				{field:'comps',title:'办理单位',align:'left',width:200,sortable:false}
			]],			
			toolbar: '#tb'
		});
		getcombobox('propertiesType','${pageContext.request.contextPath}/dic!combox.do?ctype=TAFL');
		getcombobox('handleType','${pageContext.request.contextPath}/dic!combox.do?ctype=BLLX');

	});
		
	//提案查询
	function _search() {
		tagrid.datagrid('load', dj.serializeObject($('#ta_queryForm')));
		/*$('#proposalCode').val('');
		$('#title').val('');
		$("#stutas").combobox('select', '请选择...');
		$("#fileMethod").combobox('select', '请选择...');
		$("#propertiesType").combobox('select','请选择...');
		$("#handleType").combobox('select', '请选择...');*/
		$('#win_ta_query').window('close');
	}
	//提案查询重置
	function cleanSearch() {
		tagrid.datagrid('load', {});
		$('#proposalCode').val('');
		$('#title').val('');
		$("#stutas").combobox('select', '请选择...');
		$("#fileMethod").combobox('select', '请选择...');
		$("#propertiesType").combobox('select','请选择...');
		$("#handleType").combobox('select', '请选择...');
	}
	//确定承办单位
	function confirm(){
	var rows = tagrid.datagrid('getSelections');
		if (rows.length == 1) {
			/*if(rows[0].adoptFlg =='0'){
				parent.dj.messagerAlert('提示', '选中记录未采纳!', 'error');
				return ;
			}*/
			if(rows[0].stutas=='4'){
				parent.dj.messagerAlert('提示', '选中记录已办理!', 'error');
				return ;
			}
			p = dj.dialog({
				title : '确定办理单位',
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
						initCombobox('confirmForm','handleType','${pageContext.request.contextPath}/dic!combox.do?ctype=BLLX');
					    initCombobox('confirmForm','analySisUnit','${pageContext.request.contextPath}/dic!combox.do?ctype=FXDW');
					    var d=new Date();
						var rq=d.getFullYear() + "-" + (d.getMonth()+1) +"-" + d.getDate();
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
	//重点提案设置
	function setStress(){
		var rows = tagrid.datagrid('getSelections');
		if (rows.length == 1) {
			if(rows[i].submitFlg =='1'){
				dj.messagerShow({
					msg : "所选提案中有提案己交办!",
					title : '提示'
				});
				return;
			}
			p = dj.dialog({
				title : '重点提案设置',
				href : '${pageContext.request.contextPath}/prop!propStress.do',
				width : 520,
				height : 280,
				iconCls:'icon-save',
				buttons : [ {
						text : ' 提 交 ',
						iconCls:'icon-ok',
						handler : function() {
							saveSet('${pageContext.request.contextPath}/prop!setStress.do');
						}
					},{ 
						text: ' 取 消 ', 
						iconCls:'icon-cancel',
						handler: function() { 
							p.dialog('close'); 
						} 
					}],
				onLoad : function() {
					initCombobox('stressForm','stressFlg','${pageContext.request.contextPath}/dic!combox.do?ctype=YESNO');
					var f = p.find('form');
					f.form('load', {
						proposalId:rows[0].proposalId,
						proposalCode:rows[0].proposalCode
				    });
				 	$("#propCode").text(rows[0].proposalCode);
				 	$("#secondaryCode").text(rows[0].secondayName);
				 	$("#tl").text(rows[0].title);
				 	$("#proposalType").text(rows[0].proposalTypeName);
				 	$("#fistProposaler").text(rows[0].fistProposaler);
				 }
			});	
	 	}else if (rows.length > 1) {
			parent.dj.messagerAlert('提示', '同一时间只能设置一条记录！', 'error');
		} else {
			parent.dj.messagerAlert('提示', '请选择要设置的记录！', 'error');
		}
	
	}
	//批量交办
	function batchset(){
		var rows = tagrid.datagrid('getSelections');
		var ids=[];
		for(var i = 0; i<rows.length ;i++){
			if(rows[i].submitFlg =='1'){
				dj.messagerShow({
					msg : "所选提案中有提案己交办!",
					title : '提示'
				});
				return;
			}
			ids.push(rows[i].proposalId);
		}
		parent.dj.messagerConfirm('请确认', '您要交办当前所选提案?', function(r) {
				if (r) {
					$.ajax({
						url : '${pageContext.request.contextPath}/prop!assgnSet.do',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(d) {
							tagrid.datagrid('load');
							tagrid.datagrid('unselectAll');
							parent.dj.messagerShow({
								title : '提示',
								msg : d.msg
							});
						}
					});
				}
		});
	}
	//报表
	function report(){
		
		var totalRowNum = tagrid.datagrid('getPager').data("pagination").options.total;
		if(totalRowNum>0){
			$.ajax({
				url : '${pageContext.request.contextPath}/prop!assignreport.do',
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
	//报表
	function countreport(){
		
		var totalRowNum = tagrid.datagrid('getPager').data("pagination").options.total;
		if(totalRowNum>0){
			$.ajax({
				url : '${pageContext.request.contextPath}/prop!assigncountreport.do',
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
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-check" plain="true" onclick="confirm()">确定办理单位</a>
			<!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-key" plain="true" onclick="setStress()">重点提案</a> -->
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-submit" plain="true" onclick="batchset()">批量交办</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="report()">详情报表</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="countreport()">统计报表</a>
		</div>
	</div>
	<div id="win_ta_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:500px;height:280px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="ta_queryForm">
				<table id="myta_queryTable">	
							
					<tr height="25">
						<td nowrap>提案编号：</td>
						<td><input type="text" id="proposalCode" name="proposalCode" style="width:354px;padding:2px;border:1px solid #000;"></td>
					</tr>
					<tr height="25">
						<td nowrap>案&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;由：</td>
						<td> <input type="text" id="title" name="title" style="width:354px;padding:2px;border:1px solid #000;"></td>
					</tr>
					<tr height="25">
						<td nowrap>第一提案人：</td>
						<td> <input type="text" id="fistProposaler" name="fistProposaler" style="width:354px;padding:2px;border:1px solid #000;"></td>
					</tr>
					<tr height="25">
						<td nowrap>内容分类：</td>
						<td> <input id="propertiesType" panelHeight="auto" name="propertiesType" value="请选择..." style="width:360px;padding:2px;border:1px solid #000;"/>
						</td>
					</tr>
					<tr height="25">
						<td nowrap>办理方式：</td>
						<td><input id="handleType" panelHeight="auto" name="handleType" value="请选择..." style="width:360px;padding:2px;border:1px solid #000;"/>
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