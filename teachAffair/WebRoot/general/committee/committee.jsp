<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	function initCombox(form){
		initCombobox(form,'sex','${pageContext.request.contextPath}/dic!combox.do?ctype=SEX');
		initCombobox(form,'nation','${pageContext.request.contextPath}/dic!combox.do?ctype=NATION');
		initCombobox(form,'partyCode','${pageContext.request.contextPath}/dic!combox.do?ctype=PARTY');
		initCombobox(form,'circleCode','${pageContext.request.contextPath}/dic!combox.do?ctype=CIRCLE');
		initCombobox(form,'eduCode','${pageContext.request.contextPath}/dic!combox.do?ctype=EDUCATION');
		initCombobox(form,'degreeCode','${pageContext.request.contextPath}/dic!combox.do?ctype=DEGREE');
		initCombobox(form,'status','${pageContext.request.contextPath}/dic!combox.do?ctype=STATUS');
		//专委会
		initCombobox(form,'committee','${pageContext.request.contextPath}/dic!combox.do?ctype=COMMITTEE');
		
		$('#' + form + ' input[name=secondaryCode]').combobox({
		    url:'${pageContext.request.contextPath}/seco!combobox.do',    
		    valueField:'secondaryId',    
		    textField:'secondayName',
		    multiple:true
		}); 
	}
	
	var datagrid;	
	$(document).ready(function() {
		datagrid=$('#listgrid');
		datagrid.datagrid({
			url:'${pageContext.request.contextPath}/committee!datagrid.do',
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
			idField:'code',
			sortName : 'code',
			sortOrder : 'asc',
			fit:true,
			frozenColumns:[[
				{field:'edit',title:'操作',align:'center',checkbox:true},
				{field:'name',title:'委员',align:'center',width:150}
			]],
			columns:[[
				{field:'sexName',title:'性别',align:'center',width:40},
				{field:'nationName',title:'民族',align:'center',width:60},
				{field:'birthDate',title:'出生年月',align:'center',width:100},
				{field:'partyName',title:'党派',align:'center',width:200},
				{field:'committeeName',title:'专委会',align:'center',width:200},
				{field:'circleName',title:'界别',align:'center',width:200},
				{field:'email',title:'邮箱',align:'center',width:200},
				{field:'telephone',title:'手机号',align:'center',width:100},
				{field:'job',title:'职务',align:'center',width:200},
				{field:'statusName',title:'状态',align:'center',width:80},
				{field:'secondaryName',title:'有效届次',align:'center',width:300}
			]],				
			toolbar : [{
				text : '查询',
				iconCls : 'icon-search',
				handler : function() {
					query();
				}
			}, '-',  {
				text : '增加',
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
				text : '查看',
				iconCls : 'icon-view',
				handler : function() {
					view();
				}
			}, '-', {
				text : '报表',
				iconCls : 'icon-export',
				handler : function() {
					report();
				}
			}, '-', {
				text : '启用',
				iconCls : 'icon-enable',
				handler : function() {
					change('1');
				}
			}, '-', {
				text : '停用',
				iconCls : 'icon-stop',
				handler : function() {
					change('0');
				}
			}, '-', {
				text : '重置密码',
				iconCls : 'icon-pass',
				handler : function() {
					resetPass();
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
				{field:'secondayName',title:'届次名称',align:'center',width:140,sortable:false}
			]],
			onLoadSuccess:function(row){ //当表格成功加载时执行默认届次选定                
                var rowData = row.rows;
                $.each(rowData,function(idx, val){//遍历JSON  
                    if(val.status=='1'){  
                        $('#gridjc').datagrid("selectRow", idx); 
                        $('#ids').val("");
                    } else {
                    	$('#gridjc').datagrid("unselectRow", idx);
                    }
                });
               
            }
		});
	});
	/////////////////////////////
	function add() {
		var p = dj.dialog({
			title : '添加委员信息',
			href : '${pageContext.request.contextPath}/committee!committeeAE.do',
			width : 700,
			height : 420,
			maximized : true,
			buttons : [ {
				text : '添加',
				handler : function() {
					var f = p.find('form');
					f.form('submit', {
						url : '${pageContext.request.contextPath}/committee!add.do',
						success : function(d) {
							var json = $.parseJSON(d);
							if (json.success) {
								datagrid.datagrid('reload');
								p.dialog('close');
							}
							$.messager.alert('提示', json.msg, 'info');
						}
					});
				}
			} ],
			onLoad : function() {
				initCombox('addForm');
				$('#addForm input[name=sex]').combobox({
					url : '${pageContext.request.contextPath}/dic!combox.do?ctype=SEX',
					valueField:'cvalue', 
					panelHeight:'100',
	        		textField:'ckey',
					onChange: function (n,o) {
						if(n=='1'){
							$('#vImg1').attr("src","${pageContext.request.contextPath}/style/images/man.jpg");
						} else if(n=='2'){
							$('#vImg1').attr("src","${pageContext.request.contextPath}/style/images/woman.jpg");
						} else {
							$('#vImg1').attr("src","${pageContext.request.contextPath}/style/images/man.jpg");
						}
					}
				});
	        
			}
		});
	}
	
	function edit() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			var p = dj.dialog({
				title : '编辑委员信息',
				href : '${pageContext.request.contextPath}/committee!committeeAE.do',
				width : 700,
				height : 420,
				maximized : true,
				buttons : [ {
					text : '编辑',
					handler : function() {
						var oldPhone=$('#oldtelephone').val();
						var newPhone=$('#telephone').val();
						if(oldPhone!=newPhone){
							parent.dj.messagerConfirm('请确认', '委员的手机号码改变后，登陆账号为当前号码，密码为当前号码后六位！', function(r) {
								if (r) {
									var f = p.find('form');
									f.form('submit', {
										url : '${pageContext.request.contextPath}/committee!edit.do',
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
							});
						}else{
							var f = p.find('form');
							f.form('submit', {
								url : '${pageContext.request.contextPath}/committee!edit.do',
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
					}
				} ],
				onLoad : function() {
					initCombox('addForm');
					if(rows[0].picName){
						$('#vImg1').attr("src","${pageContext.request.contextPath}/upload/mobile/"+rows[0].picName);
					} else if(rows[0].sex=='1'){
						$('#vImg1').attr("src","${pageContext.request.contextPath}/style/images/man.jpg");
					} else {
						$('#vImg1').attr("src","${pageContext.request.contextPath}/style/images/woman.jpg");
					}				
					var f = p.find('form');
						f.form('load', {
						code : rows[0].code,
						oldtelephone:rows[0].telephone,
						name : rows[0].name,
						groupCode:rows[0].groupCode,
						sex : rows[0].sex,
						nation : rows[0].nation,
						partyCode : rows[0].partyCode,
						circleCode : rows[0].circleCode,
						eduCode : rows[0].eduCode,
						degreeCode : rows[0].degreeCode,
						vocation : rows[0].vocation,
						title : rows[0].title,
						email : rows[0].email,
						birthDate : rows[0].birthDate,
						telephone : rows[0].telephone,
						job : rows[0].job,
						companyName : rows[0].companyName,
						comparyAddress : rows[0].comparyAddress,
						comparyPhone : rows[0].comparyPhone,
						comparyPost : rows[0].comparyPost,
						familyAddress : rows[0].familyAddress,
						familyPhone : rows[0].familyPhone,
						familyPost : rows[0].familyPost,
						status : rows[0].status,
						linkMan:rows[0].linkMan,
						secondaryCode : rows[0].secondaryCode.split(','),
						committee:rows[0].committee,
						committeeName:rows[0].committeeName,
						picName:rows[0].picName
					});
					$('#addForm input[name=sex]').combobox({
						url : '${pageContext.request.contextPath}/dic!combox.do?ctype=SEX',
						valueField:'cvalue', 
						panelHeight:'100',
		        		textField:'ckey',
						onChange: function (n,o) {
							if(!(rows[0].picName)){
								if(n=='1'){
									$('#vImg1').attr("src","${pageContext.request.contextPath}/style/images/man.jpg");
								} else if(n=='2'){
									$('#vImg1').attr("src","${pageContext.request.contextPath}/style/images/woman.jpg");
								} else {
									$('#vImg1').attr("src","${pageContext.request.contextPath}/style/images/man.jpg");
								}
							}
						}
					});
					
					//alert(1);
				}
			});
		} else if (rows.length > 1) {
			parent.dj.messagerAlert('提示', '同一时间只能编辑一条记录！', 'error');
		} else {
			parent.dj.messagerAlert('提示', '请选择要编辑的记录！', 'error');
		}
	}
	
	function change(status) {
		var rows = datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			parent.dj.messagerConfirm('请确认', '您要启用当前所选项目？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].code);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/committee!change.do',
						data : {
							ids : ids.join(','),
							status:status
						},
						dataType : 'json',
						success : function(d) {
							datagrid.datagrid('reload');
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
			parent.dj.messagerAlert('提示', '请勾选要启用的记录！', 'error');
		}
	}
	function resetPass() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			parent.dj.messagerConfirm('请确认', '您要重置该记录密码吗？', function(r) {
				if (r) {
					$.ajax({
						url : '${pageContext.request.contextPath}/committee!resetPass.do',
						data : {
							code: rows[0].code
						},
						dataType : 'json',
						success : function(d) {
							datagrid.datagrid('reload');
							datagrid.datagrid('unselectAll');
							parent.dj.messagerShow({
								title : '提示',
								msg : d.msg
							});
						}
					});
				}
			});
		} else if (rows.length > 1) {
			parent.dj.messagerAlert('提示', '同一时间只能编辑一条记录！', 'error');
		} else {
			parent.dj.messagerAlert('提示', '请选择要编辑的记录！', 'error');
		}
	}
	function view(){
		var row = datagrid.datagrid('getSelected');
		if (row) {
			var p = dj.dialog({
				title : '委员信息',
				href : '${pageContext.request.contextPath}/committee!view.do?code=' + row.code,
				width : 650,
				height : 500,
				maximized : true,
				buttons : [ {
					text : '关闭',
					iconCls:'icon-cancel',
					handler : function() {
						datagrid.datagrid('reload');
						p.dialog('close');
					}
				} ]
			});
		} else {
			$.messager.alert('提示', '请勾选要查看的记录！', 'error');
		}
	}

	function report(){
		var totalRowNum = datagrid.datagrid('getPager').data("pagination").options.total;
		if(totalRowNum>0){
			$.ajax({
				url : '${pageContext.request.contextPath}/committee!report.do',
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
		//$('#searchForm').form('clear');
		$('#win_query').window('open');
	}
	
	function _search() {	//此处方法不能为search()
		datagrid.datagrid('load', dj.serializeObject($('#searchForm')));
		$('#win_query').window('close');
	}
	
	function cleanSearch() {
		datagrid.datagrid('load', {});
		$("input,textarea,select").val("");
 		$("#sex").combobox('setValue', '');
		$("#nation").combobox('setValue', '');
		$("#partyCode").combobox('setValue', '');
		$("#circleCode").combobox('setValue', '');
		$("#committee").combobox('setValue', '');
		$("#status").combobox('setValue', '');
		$('#gridjc').datagrid('load', {});
	}
</script>
</head>

<body class="easyui-layout">
	<!-- 查询窗口-->
	<div id="win_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:550px;height:360px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="searchForm" enctype=multipart/form-data method="post">
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
					<tr height="30">
						<td nowrap>状态：</td>
						<td align="left"><input type="text" id="status" name="status"  style="width:194px;padding:2px" /></td>
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
		<div onclick="add();" data-options="iconCls:'icon-add'">增加</div>
		<div onclick="edit();" data-options="iconCls:'icon-edit'">修改</div>
		<div onclick="view();" data-options="iconCls:'icon-view'">查看</div>
		<div onclick="report();" data-options="iconCls:'icon-export'">报表</div>
		<div onclick="change('1');" data-options="iconCls:'icon-enable'">启用</div>
		<div onclick="change('0');" data-options="iconCls:'icon-stop'">停用</div>
	</div>
</body>
</html>