<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript">
	var tagrid;
	var p;
	var secondp;
	$(document).ready(function() {
		tagrid=$('#tagrid');
		var startType='<%=request.getAttribute("statusType")%>';
		var url='${pageContext.request.contextPath}/prop!datagrid.do';
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
				url+='?fileMethod=0&filingFlg=0';
			}
			if(startType=='E'){
				url+='?stutas=2&fileMethod=0&filingFlg=1';
			}
			if(startType=='F'){
				url+='?filingFlg=1&submitFlg=1&replyPass=space';
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
				{field:'proposalId',hidden:true}, 
				{field:'proposalCode',title:'提案编号',align:'center',width:70}
			]],
			columns:[[
				{field:'title',title:'案&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;由',halign:'center',align:'left',width:380,sortable:true,formatter:function(val,rec){
					var str=val;
					var scfzst=rec.stutasName;
					if (scfzst==null){scfzst="";}
					if (scfzst.indexOf('未审查')>-1){
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
					if(rec.submitFlg=='1'&&rec.replyPass=='0'){
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
				{field:'propTypeName',title:'内容分类',align:'center',width:80,sortable:true},
				{field:'typeId',title:'分类编号',align:'center',width:80,sortable:true},
				//{field:'handleHowName',title:'办理解决程度',align:'left',width:120,sortable:true},
				{field:'excellentFlgName',title:'优秀提案',align:'center',width:60,sortable:true},
				{field:'stressFlgName',title:'重点提案',align:'center',width:60,sortable:true},
				{field:'leader',title:'督办领导',align:'center',width:80,sortable:true},
				//{field:'adoptFlgName',title:'是否采纳',align:'center',width:80,sortable:true},
				{field:'mergeFlagName',title:'合并提案',align:'center',width:60,sortable:true},
				{field:'undertakeUnit',title:'建议办理单位',halign:'center',align:'left',width:200,sortable:false,hidden:true},
				{field:'adviceUnitName',title:'拟定办理单位',halign:'center',align:'left',width:200,sortable:false},
				{field:'comps',title:'办理单位',halign:'center',align:'left',width:200,sortable:false,hidden:true},
				{field:'solveHowName',title:'解决程度',align:'center',width:180},
				//{field:'carryoutFlgName',title:'是否落实 ',align:'center',width:60},
				{field:'committeemanOpinionName',title:'委员意见',align:'center',width:80}
			]],			
			toolbar: '#tb'
		});
		getcombobox('stutas','${pageContext.request.contextPath}/dic!combox.do?ctype=TAZT')
		getcombobox('proposalType','${pageContext.request.contextPath}/dic!combox.do?ctype=TALX')
		getcombobox('propertiesType','${pageContext.request.contextPath}/dic!combox.do?ctype=TAFL');
		getcombobox('handleType','${pageContext.request.contextPath}/dic!combox.do?ctype=BLLX');

	});
	//新增提案
	function addTa() {
			p = dj.dialog({
			title : '新增提案',
			href : '${pageContext.request.contextPath}/prop!propAdd.do',
			maximized:true,
			maximizable:true,
			width : 800,
			height : 500,
			iconCls:'icon-save',
			buttons : [ {
					text : ' 提 交 ',
					iconCls:'icon-ok',
					handler : function() {
						add();
					}
				},{ 
					text: ' 取 消 ', 
					iconCls:'icon-cancel',
					handler: function() { 
						p.dialog('close'); 
					} 
					}],
				onLoad : function() {
					var f = p.find('form');
					//下拉列表取值
					getcombobox('secondFlg','${pageContext.request.contextPath}/dic!combox.do?ctype=FYYGK');
					getcombobox('webFlg','${pageContext.request.contextPath}/dic!combox.do?ctype=FYYGK');
					$(document).ready(function() {
						//提案人信息列表
						$('#tawygrid').datagrid({
							url:'',
							width:'auto',
							height:'auto',
							striped:true,
							nowrap:true,
							rownumbers:true,
							singleSelect:true,
							fitColumns:true,
							columns:[[
								{field:'code',title:'编号',align:'center',width:60,sortable:false},
								{field:'name',title:'提案人',align:'center',width:100,sortable:false},
								{field:'groupName',title:'提案人分组',align:'center',width:100,sortable:false},
								//{field:'sexName',title:'性别',align:'left',width:40,sortable:false},				
								{field:'telephone',title:'联系电话',align:'left',width:120,sortable:false},
								{field:'comparyPhone',title:'固定电话',align:'center',width:100,sortable:false},
								{field:'email',title:'电子邮箱',align:'center',width:120,sortable:false},
								{field:'comparyAddress',title:'通讯地址',align:'center',width:200,sortable:false},
								{field:'comparyPost',title:'邮编',align:'center',width:100,sortable:false},
								//{field:'companyName',title:'单位名称',align:'center',width:70,sortable:false},
								//{field:'jobName',title:'职务',align:'center',width:90,sortable:false},				
								{field:'hostFlgName',title:'是否主提',align:'center',width:80,sortable:false}				
							]],
							toolbar: '#tar_menu'
						});
						//所有提案人列表
						$('#tazgrid').datagrid({
							url:'${pageContext.request.contextPath}/comm!getCurSecComm.do',
							width: 'auto',
							height:'auto',
							striped:true,
							nowrap:true,
							pagination:true,
							pageSize:10,
							pageList:[10,15,20],
							rownumbers:true,
							sortName: 'code',
							sortOrder: 'asc',
							idField:'code',
							fit:true,
							frozenColumns:[[
								{field:'code',title:'编号',checkbox:true,align:'center',width:40},
								{field:'name',title:'提案人',align:'left',width:100,sortable:false}
							]],
							columns:[[
								{field:'groupName',title:'提案人分组',align:'center',width:100,sortable:false},
								{field:'telephone',title:'联系电话',align:'left',width:120,sortable:false},
								{field:'email',title:'电子邮箱',align:'center',width:120,sortable:false},
							]]
						});
						getcombobox('groupCode','${pageContext.request.contextPath}/dic!combox.do?ctype=GROUP');
						getcombobox('hostFlg','${pageContext.request.contextPath}/dic!combox.do?ctype=YESNO');
					});
				}
		});
	}
	
	//修改提案
	function editTa(){
		var rows = tagrid.datagrid('getSelections');
		if (rows.length == 1) {
			if(rows[0].fileMethod=="1"||rows[0].fileMethod=="0"){
				parent.dj.messagerAlert('提示', '选中记录已审查立案,不能进行初审操作!', 'error');
				return ;
			}
			p = dj.dialog({
				title : '初审提案',
				href : '${pageContext.request.contextPath}/prop!propEdit.do?proposalId=' + rows[0].proposalId,
				maximized:true,
				maximizable:true,
				width : 800,
				height : 500,
				iconCls:'icon-save',
				buttons : [{
						text : ' 提 交 ',
						iconCls:'icon-ok',
						handler : function() {
							edit();
						}
					},{ 
						text: ' 取 消 ', 
						iconCls:'icon-cancel',
						handler: function() { 
							p.dialog('close'); 
						} 
					}],
					onLoad : function() {
						
						//下拉列表取值
						initCombobox('taeditForm','secondFlg','${pageContext.request.contextPath}/dic!combox.do?ctype=FYYGK');
						initCombobox('taeditForm','webFlg','${pageContext.request.contextPath}/dic!combox.do?ctype=FYYGK');
					    $(document).ready(function() {
					    	//提案人信息列表
							$('#tawygrid').datagrid({
								url:'${pageContext.request.contextPath}/sponsor!datagrid.do?proposalId='+rows[0].proposalId,
								width:'auto',
								height:'auto',
								striped:true,
								nowrap:true,
								rownumbers:true,
								singleSelect:true,
								fitColumns:true,
								columns:[[
									{field:'code',title:'编号',align:'center',width:60,sortable:false},
									{field:'name',title:'提案人',align:'center',width:100,sortable:false},
									{field:'groupName',title:'提案人分组',align:'center',width:100,sortable:false},
									//{field:'sexName',title:'性别',align:'left',width:40,sortable:false},				
									{field:'telephone',title:'联系电话',align:'left',width:120,sortable:false},
									{field:'comparyPhone',title:'固定电话',align:'center',width:100,sortable:false},
									{field:'email',title:'电子邮箱',align:'center',width:120,sortable:false},
									{field:'comparyAddress',title:'通讯地址',align:'center',width:200,sortable:false},
									{field:'comparyPost',title:'邮编',align:'center',width:100,sortable:false},
									//{field:'companyName',title:'单位名称',align:'center',width:70,sortable:false},
									//{field:'jobName',title:'职务',align:'center',width:90,sortable:false},				
									{field:'hostFlgName',title:'是否主提',align:'center',width:80,sortable:false}				
								]],
								toolbar: '#tar_menu'
							});
							//所有提案人列表
							$('#tazgrid').datagrid({
								url:'${pageContext.request.contextPath}/comm!getCurSecComm.do',
								width: 'auto',
								height:'auto',
								striped:true,
								nowrap:true,
								pagination:true,
								pageSize:10,
								pageList:[10,15,20],
								rownumbers:true,
								sortName: 'code',
								sortOrder: 'asc',
								idField:'code',
								fit:true,
								frozenColumns:[[
									{field:'code',title:'编号',checkbox:true,align:'center',width:40},
									{field:'name',title:'提案人',align:'left',width:100,sortable:false}
								]],
								columns:[[
									{field:'groupName',title:'提案人分组',align:'center',width:100,sortable:false},
									{field:'telephone',title:'联系电话',align:'left',width:120,sortable:false},
									{field:'email',title:'电子邮箱',align:'center',width:120,sortable:false},
								]]
							});
							getcombobox('groupCode','${pageContext.request.contextPath}/dic!combox.do?ctype=GROUP');
							getcombobox('hostFlg','${pageContext.request.contextPath}/dic!combox.do?ctype=YESNO');
						});
				}
			});
		} else if (rows.length > 1) {
			parent.dj.messagerAlert('提示', '同一时间只能编辑一条记录！', 'error');
		} else {
			parent.dj.messagerAlert('提示', '请选择要编辑的记录！', 'error');
		}
	}
	
	//退回修改页面
	function backedit(data){
			secondp = dj.dialog({
			title : '退回修改建议',
			href : '${pageContext.request.contextPath}/prop!propBackEdit.do',
			width : 520,
			height : 350,
			iconCls:'icon-save',
			buttons : [ {
						text : ' 提 交 ',
						iconCls:'icon-ok',
						handler : function() {
							backeditsave();
						}
					},{ 
						text: ' 取 消 ', 
						iconCls:'icon-cancel',
						handler: function() { 
							secondp.dialog('close'); 
						} 
					}],
					onLoad : function() {
						$("#backEditForm").form('load', {
							proposalId:data.proposalId,
							proposalCode:data.proposalCode
					    });
					 	$("#propCode").text(data.proposalCode);
					 	$("#secondaryCode").text(data.secondayName);
					 	$("#tl").text(data.title);
					 	$("#proposalType").text(data.proposalTypeName);
					 	$("#fistProposaler").text(data.fistProposaler);
					 	$("#adviceInfo").text(data.adviceInfo);
					}
		});
	}
	//添加提案
	function add(){
		var f = p.find('form');
		$("#content").val(UE.getEditor('editor').getContent()); 
		var tardata=$('#tawygrid').datagrid('getRows');
		var tars=[];
		var hostflags=[];
		var fistProposalers=[];
		var proposalType="";
		for(var i = 0; i<tardata.length ;i++){
			tars.push(tardata[i].code);
			if(tardata[i].hostFlg=='1'){
				fistProposalers.push(tardata[i].name);
				proposalType=tardata[i].groupCode;
			}
			hostflags.push(tardata[i].hostFlg);
		}
		if(fistProposalers.length==0){
			dj.messagerAlert('提示', '请指定提案的第一提案人！', 'error');
			return;
		}
		$("#sponsorIds").val(tars.join(','));
		
		$("#fistProposaler").val(fistProposalers.join(','));
		$("#hostFlgs").val(hostflags.join(','));
		$("#proposalType").val(proposalType);
		f.form('submit', {
			url : '${pageContext.request.contextPath}/prop!submit.do',
			success : function(d) {
				var json = $.parseJSON(d);
				if (json.success) {
					tagrid.datagrid('reload');
					tagrid.datagrid('unselectAll');
					p.dialog('close');
				}
					dj.messagerShow({
					msg : json.msg,
					title : '提示'
				});
			}
		});
	}
	
	//修改提案
	function edit(){
		var f = p.find('form');
		//alert(UE.getEditor('editor').getContent());
		//$("#content").val(UE.getEditor('editor').getContent()); 
		var tardata=$('#tawygrid').datagrid('getRows');
		var tars=[];
		var hostflags=[];
		var fistProposaler=[];
		var proposalType="";
		for(var i = 0; i<tardata.length ;i++){
			tars.push(tardata[i].code);
			if(tardata[i].hostFlg=='1'){
				fistProposaler.push(tardata[i].name);
				proposalType=tardata[i].groupCode;
			}
			hostflags.push(tardata[i].hostFlg);
		}
		$("#sponsorIds").val(tars.join(','));
		$("#fistProposaler").val(fistProposaler.join(','));
		$("#hostFlgs").val(hostflags.join(','));
		$("#proposalType").val(proposalType);
		f.form('submit', {
			url : '${pageContext.request.contextPath}/prop!checkEdit.do',
			success : function(d) {
				var json = $.parseJSON(d);
				if (json.success) {
					tagrid.datagrid('reload');
					tagrid.datagrid('unselectAll');
					p.dialog('close');
				}
					dj.messagerShow({
					msg : json.msg,
					title : '提示'
				});
			}
		});
	}
	//退回修改保存
	function backeditsave(){
		var f = secondp.find('form');
		f.form('submit', {
			url : '${pageContext.request.contextPath}/prop!backEditSave.do',
			success : function(d) {
				var json = $.parseJSON(d);
				if (json.success) {
					tagrid.datagrid('reload');
					tagrid.datagrid('unselectAll');
					secondp.dialog('close');
					p.dialog('close');
				}
					dj.messagerShow({
					msg : json.msg,
					title : '提示'
				});
			}
		});
	}
	//删除提案人
	function delete_add(){
		var rows=$('#tawygrid').datagrid('getSelections')
		if(rows.length>0){
			for(var i = rows.length-1; i>=0 ;i--){
				var rowindex=$('#tawygrid').datagrid('getRowIndex',rows[i]);
				$('#tawygrid').datagrid('deleteRow',rowindex);
			}
			
		}else{
			dj.messagerAlert('提示', '请选择要删除的记录！', 'error');
		}
	}
	
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
	//提案人过虑
	function tar_search() {
		$('#tazgrid').datagrid('load', dj.serializeObject($('#tar_queryForm')));
	}
	//提案人过虑取消
	function tar_cleanSearch() {
		$('#tazgrid').datagrid('load', {});
		$('input').val('');
	}
	//审查立案
	function checkfiling(){
		var rows = tagrid.datagrid('getSelections');
		if (rows.length == 1) {
			if(rows[0].filingFlg=='1'){
				parent.dj.messagerAlert('提示', '选中记录已确定转办,不能进行审查立案操作!', 'error');
				return ;
			}
			p = dj.dialog({
				title : '提案审查立案',
				href : '${pageContext.request.contextPath}/prop!propCheck.do?proposalId='+rows[0].proposalId,
				maximized:true,
				maximizable:true,
				width : 800,
				height : 500,
				iconCls:'icon-save',
				buttons : [ {
						text : ' 提 交 ',
						iconCls:'icon-ok',
						handler : function() {
							cbdw_add();
						}
					},{ 
						text: ' 取 消 ', 
						iconCls:'icon-cancel',
						handler: function() { 
							p.dialog('close'); 
						} 
					}],
					onLoad : function() {
						
						//下拉列表取值
						$('#tacheckForm input[name=fileMethod]').combobox({    
						    url:'${pageContext.request.contextPath}/dic!combox.do?ctype=LAXS',    
						    valueField:'cvalue', 
						    panelHeight:'100',
						    textField:'ckey',
						    onChange:function(n,o){
						    	
						    	if(n=='1'){
						    		document.getElementById( "noFillingTr" ).style.display="";
						    		
						    	}else{
						    		document.getElementById( "noFillingTr" ).style.display="none";
						    	}
						    }
						});
						initCombobox('tacheckForm','secondFlg','${pageContext.request.contextPath}/dic!combox.do?ctype=FYYGK');
						initCombobox('tacheckForm','webFlg','${pageContext.request.contextPath}/dic!combox.do?ctype=FYYGK');
						//initCombobox('tacheckForm','fileMethod','${pageContext.request.contextPath}/dic!combox.do?ctype=LAXS');
						initCombobox('tacheckForm','propertiesType','${pageContext.request.contextPath}/dic!combox.do?ctype=TAFL');
						initCombobox('tacheckForm','businessCode','${pageContext.request.contextPath}/dic!combox.do?ctype=YWFZ');
						initCombobox('tacheckForm','handleType','${pageContext.request.contextPath}/dic!combox.do?ctype=BLLX');
						initCombobox('tacheckForm','analySisUnit','${pageContext.request.contextPath}/dic!combox.do?ctype=FXDW');
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
						var rq=d.getFullYear() + "-" + (d.getMonth()+1) +"-" + d.getDate()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds();
						$('#checkTime').val(rq);
					    
					    $(document).ready(function() {
					    	//提案人信息列表
							$('#tawygrid').datagrid({
								url:'${pageContext.request.contextPath}/sponsor!datagrid.do?proposalId='+rows[0].proposalId,
								width:'auto',
								height:'auto',
								striped:true,
								nowrap:true,
								rownumbers:true,
								singleSelect:true,
								fitColumns:true,
								columns:[[
									{field:'code',title:'编号',align:'center',width:60,sortable:false},
									{field:'name',title:'提案人',align:'center',width:100,sortable:false},
									{field:'groupName',title:'提案人分组',align:'center',width:100,sortable:false},
									//{field:'sexName',title:'性别',align:'left',width:40,sortable:false},				
									{field:'telephone',title:'联系电话',align:'left',width:120,sortable:false},
									{field:'comparyPhone',title:'固定电话',align:'center',width:100,sortable:false},
									{field:'email',title:'电子邮箱',align:'center',width:120,sortable:false},
									{field:'comparyAddress',title:'通讯地址',align:'center',width:200,sortable:false},
									{field:'comparyPost',title:'邮编',align:'center',width:100,sortable:false},
									//{field:'companyName',title:'单位名称',align:'center',width:70,sortable:false},
									//{field:'jobName',title:'职务',align:'center',width:90,sortable:false},				
									{field:'hostFlgName',title:'是否主提',align:'center',width:80,sortable:false}				
								]],
								toolbar: '#tar_menu'
							});
							//所有提案人列表
							$('#tazgrid').datagrid({
								url:'${pageContext.request.contextPath}/comm!getCurSecComm.do',
								width: 'auto',
								height:'auto',
								striped:true,
								nowrap:true,
								pagination:true,
								pageSize:10,
								pageList:[10,15,20],
								rownumbers:true,
								sortName: 'code',
								sortOrder: 'asc',
								idField:'code',
								fit:true,
								frozenColumns:[[
									{field:'code',title:'编号',checkbox:true,align:'center',width:40},
									{field:'name',title:'提案人',align:'left',width:100,sortable:false}
								]],
								columns:[[
									{field:'groupName',title:'提案人分组',align:'center',width:100,sortable:false},
									{field:'telephone',title:'联系电话',align:'left',width:120,sortable:false},
									{field:'email',title:'电子邮箱',align:'center',width:120,sortable:false},
								]]
							});
							getcombobox('groupCode','${pageContext.request.contextPath}/dic!combox.do?ctype=GROUP');
							getcombobox('hostFlg','${pageContext.request.contextPath}/dic!combox.do?ctype=YESNO');
							
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
									text : ' 添 加 ',
									iconCls : 'icon-add',
									handler : function() {
										dwadd();
									}
									},{
									text : ' 删 除 ',
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
	function cbdw_add(){
		var tardata=$('#tawygrid').datagrid('getRows');
		var tars=[];
		var hostflags=[];
		var fistProposaler=[];
		var proposalType="";
		for(var i = 0; i<tardata.length ;i++){
			tars.push(tardata[i].code);
			if(tardata[i].hostFlg=='1'){
				fistProposaler.push(tardata[i].name);
				proposalType=tardata[i].groupCode;
			}
			hostflags.push(tardata[i].hostFlg);
		}
		$("#sponsorIds").val(tars.join(','));
		$("#fistProposaler").val(fistProposaler.join(','));
		$("#hostFlgs").val(hostflags.join(','));
		$("#proposalType").val(proposalType);
		var compdata=$('#cbdwgrid').datagrid('getRows');
		var comps=[];
		var compNames=[];
		var mainFlgs=[];
		for(var i = 0; i<compdata.length ;i++){
			comps.push(compdata[i].companyId);
			compNames.push(compdata[i].companyName);
			mainFlgs.push(compdata[i].mainFlg);
		}
		$("#companyIds").val(comps.join(','));
		$("#adviceUnit").val(comps.join(','));
		$("#mainFlgs").val(mainFlgs.join(','));
		$('#tacheckForm').form('submit', {
			url : '${pageContext.request.contextPath}/prop!checkFiling.do',
			success : function(d) {
				var json = $.parseJSON(d);
				if (json.success) {
					tagrid.datagrid('reload');
					tagrid.datagrid('unselectAll');
					p.dialog('close');
				}
					dj.messagerShow({
					msg : json.msg,
					title : '提示'
				});
			}
		});
	}
	//添加单位页面
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
					text : ' 添 加 ',
					iconCls : 'icon-add',
					handler : function() {
						append_cbdwadd();
					}
				},{
					text: ' 关 闭 ', 
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
					getcombobox('companyType','${pageContext.request.contextPath}/dic!combox.do?ctype=COMPANYTYPE');
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
		var selectRow = tagrid.datagrid('getSelections');
		if (selectRow.length==1) {
			var propId=selectRow[0].proposalId;
			window.open("${pageContext.request.contextPath}/prop!propSee.do?proposalId="+propId);
		}else{
			dj.messagerAlert('提示', '请选择一件要查看的提案！', 'error');
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
				url : '${pageContext.request.contextPath}/prop!report.do',
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
	//拒绝采纳设置
	function setAdopt(){
		var rows = tagrid.datagrid('getSelections');
			if (rows.length == 1) {
				if(rows[0].stutas!='0'){
					parent.dj.messagerAlert('提示', '选中记录已审查,不能进行拒绝采纳设置!', 'error');
					return ;
				}
				p = dj.dialog({
					title : '拒绝采纳设置',
					href : '${pageContext.request.contextPath}/prop!propAdopt.do',
					width : 520,
					height : 320,
					iconCls:'icon-save',
					buttons : [ {
							text : ' 提 交 ',
							iconCls:'icon-ok',
							handler : function() {
								saveSet('${pageContext.request.contextPath}/prop!setAdopt.do');
							}
						},{ 
							text: ' 取 消 ', 
							iconCls:'icon-cancel',
							handler: function() { 
								p.dialog('close'); 
							} 
						}],
					onLoad : function() {
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
	//重点提案设置
	function setStress(){
		var rows = tagrid.datagrid('getSelections');
		if (rows.length == 1) {
			
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
							
							if(${sessionSeco.period}=='0'){
								if(rows[0].filingFlg!='1'){
									parent.dj.messagerConfirm('请确认', '注意：设置为重点提案提交后，所有立案提案编号会重新排序，是否提交？', function(r) {
										if (r) {
											saveSet('${pageContext.request.contextPath}/prop!setStress.do');
										}
									});
								}else{
									saveSet('${pageContext.request.contextPath}/prop!setStress.do');
								}
								
							}else{
								saveSet('${pageContext.request.contextPath}/prop!setStress.do');
							}
							
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
	//优秀提案设置
	function setFine(){
		var rows = tagrid.datagrid('getSelections');
		if (rows.length == 1) {
			p = dj.dialog({
				title : '优秀提案设置',
				href : '${pageContext.request.contextPath}/prop!propFine.do',
				width : 520,
				height : 280,
				iconCls:'icon-save',
				buttons : [ {
						text : ' 提 交 ',
						iconCls:'icon-ok',
						handler : function() {
							saveSet('${pageContext.request.contextPath}/prop!setFine.do');
						}
					},{ 
						text: ' 取 消 ', 
						iconCls:'icon-cancel',
						handler: function() { 
							p.dialog('close'); 
						} 
					}],
				onLoad : function() {
					initCombobox('fineForm','excellentFlg','${pageContext.request.contextPath}/dic!combox.do?ctype=YESNO');
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
	function saveSet(url){
		var f = p.find('form');
		f.form('submit', {
			url : url,
			success : function(d) {
				var json = $.parseJSON(d);
				if (json.success) {
					tagrid.datagrid('reload');
					tagrid.datagrid('unselectAll');
					p.dialog('close');
				}
					dj.messagerShow({
					msg : json.msg,
					title : '提示'
				});
			}
		});
	
	}
	
	//合并提案
	function merge(){
		var rows = tagrid.datagrid('getSelections');
		var ids=[];
		for(var i = 0; i<rows.length ;i++){
			if(rows[i].filingFlg =='1'){
				parent.dj.messagerAlert('提示', '提案已确定转办,不能进行合并操作!', 'error');
				return;
			}
			ids.push(rows[i].proposalId);
			
		}
		
		if (rows.length > 1) {
			parent.dj.messagerConfirm('请确认', '合并提案内容将于第一选中提案为主,确定合并?', function(r) {
				if (r) {
					p = dj.dialog({
						title : '合并提案',
						href : '${pageContext.request.contextPath}/prop!propMerge.do',
						width : 520,
						height : 350,
						iconCls:'icon-save',
						buttons : [ {
								text : ' 提 交 ',
								iconCls:'icon-ok',
								handler : function() {
									saveSet('${pageContext.request.contextPath}/prop!setMerge.do');
								}
							},{ 
								text: ' 取 消 ', 
								iconCls:'icon-cancel',
								handler: function() { 
									p.dialog('close'); 
								} 
							}],
						onLoad : function() {
							var titles=[];
							for(var i = 1; i<rows.length ;i++){
								titles.push(rows[i].title);
							}
							$('#mergeForm').form('load', {
								ids:ids.join(',')
						    });
						 	$("#mainProp").text(rows[0].title);
						 	$("#mergeProp").text(titles.join(','));
						 }
					});	
				}
			});
		} else {
			parent.dj.messagerAlert('提示', '请选择要合并的提案！', 'error');
		}
	}
	//取消合并提案
	function cancelMerge(){
		var selectRow = tagrid.datagrid('getSelections');
		if (selectRow.length==1) {
			var propId=selectRow[0].proposalId;
			if(selectRow[0].mergeFlag=='1'){
				var ids=selectRow[0].mergeIds.split(",");
				parent.dj.messagerConfirm('请确认', '取消合并的提案,将使主提案、各参考意见作为单独提案，确定取消合并?', function(r) {
					if (r) {
						 $.ajax({
								type:"POST",
								url:"${pageContext.request.contextPath}/prop!cancelMerge.do",
								data:{
									proposalId:propId,
									ids:ids
								},
								success:function(d){
									var json = $.parseJSON(d);
									if (json.success) {
										tagrid.datagrid('clearChecked');
										tagrid.datagrid('clearSelections');
										tagrid.datagrid('reload');
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
				parent.dj.messagerAlert('提示', '请选择一件已合并的提案！', 'error');
			}
		} else {
			parent.dj.messagerAlert('提示', '请选择一件要取消合并的提案！', 'error');
		}		
	}
	//撤案
	function revoke(){
		var rows = tagrid.datagrid('getSelections');
		if (rows.length == 1) {
			if(rows[0].fileMethod=='0'){
				parent.dj.messagerAlert('提示', '当前提案不能进行撤回操作！', 'error');
				return;
			}

			parent.dj.messagerConfirm('请确认', '确定要进行撤回吗！', function(r) {
				if (r) {
	
					var propIds=[];
					propIds.push(rows[0].proposalId);
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
								tagrid.datagrid('reload');
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
			parent.dj.messagerAlert('提示', '请选择一件要撤回的提案！', 'error');
		}
	}
	//确定立案
	function setFiling(){
		var rows = tagrid.datagrid('getSelections');
		var ids=[];
		if (rows.length >0) {
			for(var i = 0; i<rows.length ;i++){
				if(rows[i].fileMethod!='0'){
					dj.messagerShow({
						msg : "所选提案中有提案未审查立案，不能进行确定转办操作!",
						title : '提示'
					});
					return;
				}
				ids.push(rows[i].proposalId);
			}
			parent.dj.messagerConfirm('请确认', '您要确定转办所选提案?', function(r) {
					if (r) {
						$.ajax({
							url : '${pageContext.request.contextPath}/prop!setFiling.do',
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
								parent.dj.messagerConfirm('请确认', '当前届次所有提案是否审查立案完毕，是否将当前届次状态改为平时？', function(r) {
										if (r) {
											$.ajax({
												url : '${pageContext.request.contextPath}/seco!doNotNeedAuth_setatt.do?secondaryId='+${sessionSeco.secondaryId},
												dataType : 'json',                                                                      
												success : function(m) {
													parent.dj.messagerShow({
														title : '提示',
														msg : m.msg
													});
												}
											});
										}
								});
							}
						});
					}
			});
		} else {
			parent.dj.messagerAlert('提示', '请选要确定转办的提案！', 'error');
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
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addTa()">新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editTa()">初审</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-refuse" plain="true" onclick="revoke()">提案撤回</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-view" plain="true" onclick="lookta()">查看</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-merge" plain="true" onclick="merge()">合并提案</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="cancelMerge()">取消合并提案</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-check" plain="true" onclick="checkfiling()">审查立案</a>
			<!--<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-refuse" plain="true" onclick="setAdopt()">拒绝采纳</a>-->
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-key" plain="true" onclick="setStress()">重点提案</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-good" plain="true" onclick="setFine()">优秀提案</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-submit" plain="true" onclick="setFiling()">确定转办</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="report()">报表</a>
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
						<td> <input type="text"  panelHeight="auto" name="fistProposaler"  style="width:354px;padding:2px;border:1px solid #000;"/></td>
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