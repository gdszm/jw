<%@page import="com.tlzn.util.base.Constants"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	function initCombox(form){
		initCombobox(form,'dept','${pageContext.request.contextPath}/dic!combox.do?ctype=yxb');
		initCombobox(form,'major','${pageContext.request.contextPath}/dic!combox.do?ctype=major');
		initCombobox(form,'enSour','${pageContext.request.contextPath}/dic!combox.do?ctype=enSour');
		initCombobox(form,'priPro','${pageContext.request.contextPath}/dic!combox.do?ctype=priPro');
		initCombobox(form,'nation','${pageContext.request.contextPath}/dic!combox.do?ctype=NATION');
		initCombobox(form,'political','${pageContext.request.contextPath}/dic!combox.do?ctype=POLITICAL');
		initCombobox(form,'resType','${pageContext.request.contextPath}/dic!combox.do?ctype=resType');
		initCombobox(form,'health','${pageContext.request.contextPath}/dic!combox.do?ctype=health');
		initCombobox(form,'healthStatus','${pageContext.request.contextPath}/dic!combox.do?ctype=health');
		initCombobox(form,'compuLevel','${pageContext.request.contextPath}/dic!combox.do?ctype=compuLevel');
		initCombobox(form,'foreignType','${pageContext.request.contextPath}/dic!combox.do?ctype=foreignType');
		initCombobox(form,'ecoStatus','${pageContext.request.contextPath}/dic!combox.do?ctype=ecoStatus');
		initCombobox(form,'teachBack','${pageContext.request.contextPath}/dic!combox.do?ctype=teachBack');
		initCombobox(form,'basicLive','${pageContext.request.contextPath}/dic!combox.do?ctype=YESNO');
		initCombobox(form,'helping','${pageContext.request.contextPath}/dic!combox.do?ctype=YESNO');
		initCombobox(form,'apAtt','${pageContext.request.contextPath}/dic!combox.do?ctype=apAtt');
		initCombobox(form,'apMethod','${pageContext.request.contextPath}/dic!combox.do?ctype=apMethod');
	}
	var p;
	var datagrid;
	$(function(){
		initCombox("arch_queryForm");
		datagrid = $('#noticeDatagrid').datagrid({
			url:'${pageContext.request.contextPath}/stu!datagrid.do',
        	width: 'auto',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			idField:'stuNo',
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
				{field:'archNo',title:'档案编号',width:100,align:'center'},
				{field:'stuNo',title:'学号',width:100,align:'center'},
				{field:'name',title:'档案姓名',width:100,align:'center'},
				{field:'sex',title:'性别',width:50,align:'center'},
				]],
			columns:[[
				{field:'dept',title:'院、系、部',width:100,align:'center'},
				{field:'major',title:'专业',width:150,align:'center'},
				{field:'className',title:'班级名称',width:150,align:'center'},
				{field:'inTime',title:'入学时间',width:100,align:'center'}
			]], 
			toolbar : [
			{
				text : '查询',
				iconCls : 'icon-search',
				handler : function() {
					$('#win_notice_query').window('open');
				}
			}, '-', {
				text : '新增',
				iconCls : 'icon-add',
				handler : function() {
					add();
				}
			}, '-', {
				text : '修改',
				iconCls : 'icon-edit',
				handler : function() {
					edit();
				}
			}, '-', {
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					remove();
				}
			}, '-', {
				text : '发布',
				iconCls : 'icon-enable',
				handler : function() {
					pub();
				}
			}, '-', {
				text : '取消发布',
				iconCls : 'icon-stop',
				handler : function() {
					cancelPub();
				}
			}, '-', {
				text : '取消选中',
				iconCls : 'icon-undo',
				handler : function() {
					datagrid.datagrid('clearChecked');
					datagrid.datagrid('clearSelections');
					datagrid.datagrid('unselectAll');
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
	});
	
		//查看
		function view() {
			var rows = datagrid.datagrid('getSelections');
			if (rows.length == 1) {
				var p = dj.dialog({
					title : '档案详情',
					href : '${pageContext.request.contextPath}/notice!get.do?stuNo='+rows[0].stuNo,
					maximized:true,
					width : 300,
					height : 200,
					onLoad : function() {
						initCombox('noticeViewForm');
				
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
	//新增学生档案
	function add() {
			var p = dj.dialog({
			title : '新增学生档案',
			href : '${pageContext.request.contextPath}/stu!stuArchAdd.do',
			width : 700,
			height : 420,
			maximized : true,
			buttons : [ {
					text : ' 保存 ',
					iconCls:'icon-ok',
					handler : function() {
						saveFile();
					}
				},{ 
					text: '关闭', 
					iconCls:'icon-cancel',
					handler: function() { 
						p.dialog('close'); 
					} 
				}],
				onLoad : function() {
					initCombox('addForm');
					initCombox('fmForm');
					initCombox('apForm');
					//initCombox('eeForm');
					initCombox('weForm');
					//家庭成员列表
					gettjzgrid('');
					//奖惩记录信息列表
					getApGrid('');
					//教育经历列表
					getEeGrid('');
					//家庭成员信息列表
					getWeGrid('');
					//initCombobox('editPollForm','adoptFlg','${pageContext.request.contextPath}/dic!combox.do?ctype=YESNO');
				}
		});
	}
	
	
	//修改 
	function edit() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			var p = dj.dialog({
				title : '编辑档案信息',
				href : '${pageContext.request.contextPath}/stu!stuArchEdit.do?stuNo='+rows[0].stuNo,
				width : 700,
				height : 420,
				maximized : true,
				buttons : [ {
					text : '提交修改',
					iconCls:'icon-ok',
					handler : function() {
							var f = p.find('form');
							f.form('submit', {
								url : '${pageContext.request.contextPath}/stu!editArch.do',
								success : function(d) {
									var json = $.parseJSON(d);
									if (json.success) {
										datagrid.datagrid('reload');
										p.dialog('close');
									}
									parent.dj.messagerShow({
										msg : json.msg,
										title : '提示'
									});
								}
							});
					}
				} ,{
					text: '关闭', 
					iconCls:'icon-cancel',
					handler: function() { 
						p.dialog('close'); 
					} 
				}
				],
				onLoad : function() {
					initCombox('addForm');
					initCombox('fmForm');
					initCombox('apForm');
					//initCombox('eeForm');
					initCombox('weForm');
					//家庭成员列表
					gettjzgrid('${pageContext.request.contextPath}/stu!fmDatagrid.do?stuNo=' + rows[0].stuNo);
					//奖惩记录信息列表
					getApGrid('${pageContext.request.contextPath}/stu!apDatagrid.do?stuNo=' + rows[0].stuNo);
					//教育经历列表
					getEeGrid('${pageContext.request.contextPath}/stu!eeDatagrid.do?stuNo=' + rows[0].stuNo);
					//家庭成员信息列表
					getWeGrid('${pageContext.request.contextPath}/stu!weDatagrid.do?stuNo=' + rows[0].stuNo);
					//initCombobox('editPollForm','adoptFlg','${pageContext.request.contextPath}/dic!combox.do?ctype=YESNO');
				}
			});
		} else if (rows.length > 1) {
			parent.dj.messagerAlert('提示', '同一时间只能编辑一条记录！', 'error');
		} else {
			parent.dj.messagerAlert('提示', '请选择要编辑的记录！', 'error');
		}
	}
///////////////////////家庭成员列表
function gettjzgrid(url){
		$('#submittergrid').datagrid({
			url:url,
			width:'920',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			singleSelect:false,
			fitColumns:false,
			border:false,
			frozenColumns:[[
				{field:'id',title:'成员编号',align:'center',width:70,sortable:false},
				{field:'name',title:'姓名',align:'center',width:100,sortable:false}
			]],
			columns:[[
				{field:'age',title:'年龄',align:'center',width:50,sortable:false},
				{field:'familyRole',title:'家庭角色(与本人关系)',align:'center',width:200,sortable:false},
				{field:'unit',title:'工作单位',align:'left',width:120,sortable:false},
				{field:'duty',title:'职业',align:'center',width:100,sortable:false},
				{field:'yearIncome',title:'年收入  (万元)',align:'center',width:120,sortable:false},
				{field:'healthStatusName',title:'健康状况',align:'center',width:100,sortable:false},
				{field:'tel',title:'手机号码',align:'center',width:100,sortable:false},
				{field:'crtTime',title:'创建时间',align:'center',width:200,sortable:false},
				{field:'uptTime',title:'更新时间',align:'center',width:200,sortable:false}
			]],
			toolbar: '#tjr_menu'
		});
	} 
	//添加提案人
	function append_add(){
		var rows=$('#tjzgrid').datagrid('getSelected');
		var wyrows=$('#submittergrid').datagrid('getRows');
		if(rows){
			var flag=0;
			for(var j=0;j<wyrows.length;j++){
				if(rows.code==wyrows[j].code){
					flag=1;
					break;
				}
			}
			if(flag==0){
				$('#submittergrid').datagrid('appendRow',{
					code:rows.code,
					name:rows.name,
					telephone:rows.telephone,
					email:rows.email,
					comparyPhone:rows.comparyPhone,
					comparyAddress:rows.comparyAddress,
					comparyPost:rows.comparyPost,
					groupCode:rows.groupCode,
					groupName:rows.groupName,
					committeeName:rows.committeeName
				});
			}
			parent.dj.messagerAlert('提示', '提案人添加成功！', 'success');	
		}else{
			parent.dj.messagerAlert('提示', '请选择要添加的记录！', 'error');
			return;
		}
	}
	//删除家庭成员
	function delete_fm(){
		var rows=$('#submittergrid').datagrid('getSelections');
		if(rows.length>0){
			dj.messagerConfirm('请确认', '您确定要删除选中的所有家庭成员吗？', function(r) {
				if (r) {
					var ids = [];
					for(var i = rows.length-1; i>=0 ;i--){
						ids.push(rows[i].id);
						//var rowindex=$('#commcodegrid').datagrid('getRowIndex',rows[i]);
						//$('#commcodegrid').datagrid('deleteRow',rowindex);
					}
					$.ajax({
						type:"POST",
						url:"${pageContext.request.contextPath}/familyMem!delete.do",
						data : {
							ids : ids.join(','),
						},
						dataType:"json",
						success:function(t){
							if(t.success){
								parent.dj.messagerAlert('提示', '家庭成员删除成功！', 'success');
							}else{
								parent.dj.messagerAlert('提示', '家庭成员删除失败！', 'success');	
							}
							$('#submittergrid').datagrid('reload');
						}
					});
				}
			});
		}else{
			dj.messagerAlert('提示', '请选择要删除的记录！', 'error');
		}
	}
///////////////////////家庭成员列表
//////////////////////奖惩记录列表
function getApGrid(url){
		$('#apGrid').datagrid({
			url:url,
			width:'1022',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			singleSelect:false,
			fitColumns:false,
			border:false,
			frozenColumns:[[
				{field:'id',title:'编号',align:'center',width:70,sortable:false},
				{field:'apDate',title:'奖惩日期',align:'center',width:100,sortable:false}
			]],
			columns:[[
				{field:'apAttName',title:'奖惩性质',align:'center',width:200,sortable:false},
				{field:'apMethodName',title:'奖惩方式',align:'left',width:120,sortable:false},
				{field:'apScore',title:'奖惩分数/金额',align:'center',width:100,sortable:false},
				{field:'apUnit',title:'奖惩单位',align:'center',width:120,sortable:false},
				{field:'crtTime',title:'创建时间',align:'center',width:200,sortable:false},
				{field:'uptTime',title:'更新时间',align:'center',width:200,sortable:false}
			]],
			toolbar: '#ap_menu'
		});
	} 
	//添加提案人
	function append_add(){
		var rows=$('#tjzgrid').datagrid('getSelected');
		var wyrows=$('#apGrid').datagrid('getRows');
		if(rows){
			var flag=0;
			for(var j=0;j<wyrows.length;j++){
				if(rows.code==wyrows[j].code){
					flag=1;
					break;
				}
			}
			if(flag==0){
				$('#apGrid').datagrid('appendRow',{
					code:rows.code,
					name:rows.name,
					telephone:rows.telephone,
					email:rows.email,
					comparyPhone:rows.comparyPhone,
					comparyAddress:rows.comparyAddress,
					comparyPost:rows.comparyPost,
					groupCode:rows.groupCode,
					groupName:rows.groupName,
					committeeName:rows.committeeName
				});
			}
			parent.dj.messagerAlert('提示', '提案人添加成功！', 'success');	
		}else{
			parent.dj.messagerAlert('提示', '请选择要添加的记录！', 'error');
			return;
		}
	}
	//删除奖惩记录
	function delete_ap(){
		var rows=$('#apGrid').datagrid('getSelections');
		if(rows.length>0){
			dj.messagerConfirm('请确认', '您确定要删除选中的所有奖惩记录吗？', function(r) {
				if (r) {
					var ids = [];
					for(var i = rows.length-1; i>=0 ;i--){
						ids.push(rows[i].id);
						//var rowindex=$('#commcodegrid').datagrid('getRowIndex',rows[i]);
						//$('#commcodegrid').datagrid('deleteRow',rowindex);
					}
					$.ajax({
						type:"POST",
						url:"${pageContext.request.contextPath}/awardPenalty!delete.do",
						data : {
							ids : ids.join(','),
						},
						dataType:"json",
						success:function(t){
							if(t.success){
								parent.dj.messagerAlert('提示', '奖惩记录删除成功！', 'success');
							}else{
								parent.dj.messagerAlert('提示', '奖惩记录删除失败！', 'success');	
							}
							$('#apGrid').datagrid('reload');
						}
					});
				}
			});
		}else{
			dj.messagerAlert('提示', '请选择要删除的记录！', 'error');
		}
	}
////////////////////奖惩记录列表
////////////////////教育经历录列表
function getEeGrid(url){
		$('#eeGrid').datagrid({
			url:url,
			width:'1022',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			singleSelect:false,
			fitColumns:false,
			border:false,
			frozenColumns:[[
				{field:'id',title:'编号',align:'center',width:70,sortable:false},
				{field:'beginDate',title:'开始年月',align:'center',width:100,sortable:false},
				{field:'endDate',title:'结束年月',align:'center',width:100,sortable:false}
			]],
			columns:[[
				{field:'unit',title:'教育单位',align:'center',width:200,sortable:false},
				{field:'major',title:'专业',align:'center',width:200,sortable:false},
				{field:'cert',title:'所获证书',align:'left',width:120,sortable:false},
				{field:'certifier',title:'证明人',align:'left',width:120,sortable:false},
				{field:'crtTime',title:'创建时间',align:'center',width:200,sortable:false},
				{field:'uptTime',title:'更新时间',align:'center',width:200,sortable:false}
			]],
			toolbar: '#ee_menu'
		});
	} 
	//添加提案人
	function append_add(){
		var rows=$('#tjzgrid').datagrid('getSelected');
		var wyrows=$('#eeGrid').datagrid('getRows');
		if(rows){
			var flag=0;
			for(var j=0;j<wyrows.length;j++){
				if(rows.code==wyrows[j].code){
					flag=1;
					break;
				}
			}
			if(flag==0){
				$('#eeGrid').datagrid('appendRow',{
					code:rows.code,
					name:rows.name,
					telephone:rows.telephone,
					email:rows.email,
					comparyPhone:rows.comparyPhone,
					comparyAddress:rows.comparyAddress,
					comparyPost:rows.comparyPost,
					groupCode:rows.groupCode,
					groupName:rows.groupName,
					committeeName:rows.committeeName
				});
			}
			parent.dj.messagerAlert('提示', '提案人添加成功！', 'success');	
		}else{
			parent.dj.messagerAlert('提示', '请选择要添加的记录！', 'error');
			return;
		}
	}
	//删除教育经历
	function delete_ee(){
		var rows=$('#eeGrid').datagrid('getSelections');
		if(rows.length>0){
			dj.messagerConfirm('请确认', '您确定要删除选中的所有教育经历吗？', function(r) {
				if (r) {
					var ids = [];
					for(var i = rows.length-1; i>=0 ;i--){
						ids.push(rows[i].id);
						//var rowindex=$('#commcodegrid').datagrid('getRowIndex',rows[i]);
						//$('#commcodegrid').datagrid('deleteRow',rowindex);
					}
					$.ajax({
						type:"POST",
						url:"${pageContext.request.contextPath}/eduExp!delete.do",
						data : {
							ids : ids.join(','),
						},
						dataType:"json",
						success:function(t){
							if(t.success){
								parent.dj.messagerAlert('提示', '教育经历删除成功！', 'success');
							}else{
								parent.dj.messagerAlert('提示', '教育经历删除失败！', 'success');	
							}
							$('#eeGrid').datagrid('reload');
						}
					});
				}
			});
		}else{
			dj.messagerAlert('提示', '请选择要删除的记录！', 'error');
		}
	}
/////////教育经历列表
	
/////////工作经历列表
function getWeGrid(url){
		$('#weGrid').datagrid({
			url:url,
			width:'1022',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			singleSelect:false,
			fitColumns:false,
			border:false,
			frozenColumns:[[
				{field:'id',title:'成员编号',align:'center',width:70,sortable:false},
				{field:'beginDate',title:'开始日期',align:'center',width:100,sortable:false},
				{field:'endDate',title:'结束日期',align:'center',width:100,sortable:false}
			]],
			columns:[[
				{field:'workUnit',title:'工作单位',align:'center',width:300,sortable:false},
				{field:'workDuty',title:'职务',align:'center',width:200,sortable:false},
				{field:'crtTime',title:'创建时间',align:'center',width:200,sortable:false},
				{field:'uptTime',title:'更新时间',align:'center',width:200,sortable:false}
			]],
			toolbar: '#we_menu'
		});
	} 
	//添加提案人
	function append_add(){
		var rows=$('#tjzgrid').datagrid('getSelected');
		var wyrows=$('#weGrid').datagrid('getRows');
		if(rows){
			var flag=0;
			for(var j=0;j<wyrows.length;j++){
				if(rows.code==wyrows[j].code){
					flag=1;
					break;
				}
			}
			if(flag==0){
				$('#weGrid').datagrid('appendRow',{
					code:rows.code,
					name:rows.name,
					telephone:rows.telephone,
					email:rows.email,
					comparyPhone:rows.comparyPhone,
					comparyAddress:rows.comparyAddress,
					comparyPost:rows.comparyPost,
					groupCode:rows.groupCode,
					groupName:rows.groupName,
					committeeName:rows.committeeName
				});
			}
			parent.dj.messagerAlert('提示', '提案人添加成功！', 'success');	
		}else{
			parent.dj.messagerAlert('提示', '请选择要添加的记录！', 'error');
			return;
		}
	}
	//删除工作经历
	function delete_we(){
		var rows=$('#weGrid').datagrid('getSelections');
		if(rows.length>0){
			dj.messagerConfirm('请确认', '您确定要删除选中的所有工作经历吗？', function(r) {
				if (r) {
					var ids = [];
					for(var i = rows.length-1; i>=0 ;i--){
						ids.push(rows[i].id);
						//var rowindex=$('#commcodegrid').datagrid('getRowIndex',rows[i]);
						//$('#commcodegrid').datagrid('deleteRow',rowindex);
					}
					$.ajax({
						type:"POST",
						url:"${pageContext.request.contextPath}/workExp!delete.do",
						data : {
							ids : ids.join(','),
						},
						dataType:"json",
						success:function(t){
							if(t.success){
								parent.dj.messagerAlert('提示', '工作经历删除成功！', 'success');
							}else{
								parent.dj.messagerAlert('提示', '工作经历删除失败！', 'success');	
							}
							$('#weGrid').datagrid('reload');
						}
					});
				}
			});
		}else{
			dj.messagerAlert('提示', '请选择要删除的记录！', 'error');
		}
	}
/////////工作经历列表	
	//删除
	function remove() {
		var rows = datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			//是否可有进行删除操作
			var canDel=true;
			var wfb='<%=Constants.CODE_TYPE_PUBSTATUS_YES%>';
			for ( var h = 0; h < rows.length; h++) {
				if(rows[h].status==wfb ) canDel=false;
			}
			if(canDel) {
			parent.dj.messagerConfirm('请确认', '您要删除当前所选项目？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/notice!delete.do',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(d) {
							datagrid.datagrid('load');
							datagrid.datagrid('unselectAll');
							parent.dj.messagerShow({
								title : '提示',
								msg : d.msg
							});
						}
					});
				}
			});
			} else {
				parent.dj.messagerAlert('提示', '只能删除未发布的档案公告，请重新勾选要删除的档案公告！', 'error');
			}
		} else {
			parent.dj.messagerAlert('提示', '请勾选要删除的记录！', 'error');
		}
	}
	//发布档案处理
	function pub(){
		var rows = datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			parent.dj.messagerConfirm('请确认', '您要发布当前所选档案？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/stu!pub.do',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(d) {
							datagrid.datagrid('load');
							datagrid.datagrid('unselectAll');
							parent.dj.messagerShow({
								title : '提示',
								msg : d.msg
							});
						}
					});
				}
			});
		} else {
			parent.dj.messagerAlert('提示', '请勾选要发布的档案！', 'error');
		}
	}
	//取消发布档案处理
	function cancelPub(){
		var rows = datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			parent.dj.messagerConfirm('请确认', '您要取消发布当前所选档案？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/notice!cancelPub.do',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(d) {
							datagrid.datagrid('load');
							datagrid.datagrid('unselectAll');
							parent.dj.messagerShow({
								title : '提示',
								msg : d.msg
							});
						}
					});
				}
			});
		} else {
			parent.dj.messagerAlert('提示', '请勾选要取消发布的档案！', 'error');
		}
	}
	//查询
	function _search() {
		datagrid.datagrid('load', dj.serializeObject($('#arch_queryForm')));
		 $('#win_notice_query').window('close');
	}
	
	function cleanSearch() {
		datagrid.datagrid('load', {});
		$('#arch_queryForm input').val('');
		$('#statusId').combobox('setValue', '');
		$('#noticeTypeId').combobox('setValue', '');
	}
	
	$.extend($.fn.validatebox.defaults.rules, {  
		sel: {  
			validator: function(value){ 
				return value != '请选择...';  
			},  
			message: '此项必须选择！'  
		}  
	}); 
</script>
</head>
<body class="easyui-layout" data-options="fit:true">
	<!-- 表格 -->
	<div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="noticeDatagrid"></table>
	</div>
	<div id="win_notice_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:450px;height:375px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="arch_queryForm">
				<input type="hidden" name="filingFlg" id="filingFlgId"/>
				<table id="poll_queryTable">
					<tr height="30">
						<td nowrap>档案姓名：</td>
						<td colspan="3"><input type="text" class="easyui-validatebox"  name="name" style="width:300px;"></td>
					</tr>				
					<tr height="30">
						<td nowrap>院、系、部：</td>
						<td colspan="3">
							<input type="text" class="easyui-validatebox"  name="dept" style="width:302px;">
						</td>
					</tr>
					<tr height="30">
						<td nowrap>专业：</td>
						<td colspan="3">
							<input name="major"  style="width:302px;" panelHeight="130px" >
						</td>
					</tr>
					<tr height="30">
						<td nowrap>班级名称：</td>
						<td colspan="3"><input type="text" class="easyui-validatebox"  name="className" style="width:300px;"></td>
					</tr>	
					<tr height="30">
						<td nowrap>入学来源 ：</td>
						<td colspan="3"> <input name="enSour" style="width:302px;" panelHeight="130px" ></td>
					</tr>
					<tr height="30">
						<td nowrap>主要问题 ：</td>
						<td colspan="3"> <input name="priPro"  style="width:302px;" panelHeight="130px" ></td>
					</tr>
					<tr height="30">
						<td nowrap>入学时间 ：</td>
						<td colspan="3">
							<input id="inSTimeId" type="text" class="easyui-datebox" name="inSTime" data-options="required:false,showSeconds:false" style="width:142px;"></input> 
		 	              	~ 
			              	<input id="inETimeId" type="text" class="easyui-datebox" name="inETime" data-options="required:false,showSeconds:false" style="width:142px;"></input>  
						</td>
					</tr>
					<tr height="30">
						<td nowrap>备注：</td>
						<td colspan="3"><input type="text" class="easyui-validatebox"  name="remark" style="width:300px;"></td>
					</tr>	
				</table>
				</form>
				</center>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;height:40px">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)" onclick="_search()">查询</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" href="javascript:void(0)" onclick="cleanSearch()">重置</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#win_notice_query').window('close');">取消</a>
			</div>
		</div>
	</div>
</body>
</html>