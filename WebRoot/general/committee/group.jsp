<%@ page language="java"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	var datagrid;var rdatagrid;
	var p;

	function initCombox(form){
		initCombobox(form,'type','${pageContext.request.contextPath}/dic!combox.do?ctype=GTYPE');
	
	}
	
	$(function(){
		datagrid = $('#groupDatagrid').datagrid({
			url:'${pageContext.request.contextPath}/group!datagrid.do',
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
			fitColumns:true,
			columns:[[ 
        		{field:'gname',title:'分组名称',width:150,align:'center'},  
        		{field:'typename',title:'分组类型',width:150,align:'center'},          		
    		]],   
			toolbar : [ {
				text : '添加',
				iconCls : 'icon-add',
				handler : function() {
					append();
				}
			}, '-', {
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					remove();
				}
			}, '-', {
				text : '修改',
				iconCls : 'icon-edit',
				handler : function() {
					edit();
				}
			},'-', {
				text : '添加组成员',
				iconCls : 'icon-group',
				handler : function() {
					userGroup();
				}
			}],
			pageSize :20 ,
			pageList : [ 20, 50, 100 ],
			
			onClickRow: function(index, row){
		        user(row.gid);
	        }
		});
		initCombox('searchForm');
	});
	
	function user(gid){
		rdatagrid = $('#usergroueDatagrid').datagrid({
			url:'${pageContext.request.contextPath}/userGroup!datagrid.do?gid='+gid,
			width: 'auto',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			singleSelect:false,
			pageNumber:1,
			idField:'code',
			sortName : 'code',
			sortOrder : 'asc',
			fit:true,
			columns:[[
	       		{field:'name',title:'组成员',width:300,align:'center'},
	       		{field:'circleName',title:'界别',align:'center',width:100,sortable:false},
				{field:'committeeName',title:'所属专委会',align:'center',width:120,sortable:false},
				{field:'companyName',title:'工作单位',align:'center',width:200,sortable:false},
				{field:'job',title:'职务',align:'center',width:120,sortable:false},
	   		]], 
			toolbar : [ {
					text : '删除组成员',
					iconCls : 'icon-remove',
					handler : function() {
						remove_s();
					}
			}],
		});
	};
	
	function append() {
		var p = dj.dialog({
			title : '添加',
			href : '${pageContext.request.contextPath}/group!groupAdd.do',
			width : 400,
			height :260,
			buttons : [ {
				text : '添加',
				handler : function() {
					var f = p.find('form');
					f.form('submit', {
						url : '${pageContext.request.contextPath}/group!add.do',
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
			} ],
			onLoad : function() {
				initCombox('groupAddForm');
			}
		});
	}

	
	function edit() {
		var rows = datagrid.datagrid('getSelected');
		if (rows) {
			var p = dj.dialog({
				title : '编辑',
				href : '${pageContext.request.contextPath}/group!groupEdit.do',
				width : 400,
				height : 260,
				buttons : [ {
					text : '编辑',
					handler : function() {
						var f = p.find('form');
						f.form('submit', {
							url : '${pageContext.request.contextPath}/group!edit.do',
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
				} ],
				onLoad : function() {
					initCombox('EditForm');
					var f = p.find('form');
					f.form('load', {
						gid : rows.gid,
						gname : rows.gname,
						type : rows.type,
					});
				}
			});
			
		}else {
			parent.dj.messagerAlert('提示', '请选择要编辑的记录！', 'error');
		}
	}
		
	function remove(){
		var rows = datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			var userRows=rdatagrid.datagrid('getData');
			if(userRows.rows.length==0){
				parent.dj.messagerConfirm('请确认', '您要删除当前所选项目？', function(r) {
					if (r) {
						for ( var i = 0; i < rows.length; i++) {
							ids.push(rows[i].gid);
						}
						$.ajax({
							url : '${pageContext.request.contextPath}/group!delete.do',
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
				parent.dj.messagerAlert('提示', '请先删除该组的所有组成员，然后再删除该组！', 'error');
			}
		} else {
			parent.dj.messagerAlert('提示', '请选择要删除的组！', 'error');
		}
	}
	function remove_s() {
		var rows = rdatagrid.datagrid('getChecked');
		var gcid = [];
		if (rows.length > 0) {
			parent.dj.messagerConfirm('请确认', '您要删除当前所选项目？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						gcid.push(rows[i].gcid);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/userGroup!delete.do',
						data : {
							gcid : gcid.join(',')
						},
						dataType : 'json',
						success : function(d) {
							rdatagrid.datagrid('load');
							rdatagrid.datagrid('unselectAll');
							parent.dj.messagerShow({
								title : '提示',
								msg : d.msg
							});
						}
					});
				}
			});
		} else {
			parent.dj.messagerAlert('提示', '请勾选要删除的记录！', 'error');
		}
	}

	function userGroup() {
	    var row = datagrid.datagrid('getSelected');
	    if(row){
	    	 var rows = rdatagrid.datagrid('getData').rows;
		    p = dj.dialog({
				title : '选择分组委员',
				href : '${pageContext.request.contextPath}/comm!memberInvite.do',
				width : 650,
				height : 400,
				buttons : [ {
					text : '保存',
					iconCls:'icon-ok',
					handler : function() {
						saveadd(row.gid);
					}
				},{
					text: ' 关闭 ',    
					iconCls:'icon-cancel',
					handler: function() {  
						p.dialog('close'); 
					} 
				}],
			});
	    }else {
		    parent.dj.messagerAlert('提示', '请选择组信息记录！', 'error');
	    } 
	}	
	 //分组成员保存
  function saveadd(gid){
	  var rows=$('#membergrid').datagrid('getSelections');
	  var userids=[];
	  if(rows.length>0){
	    for(var i =0 ; i<rows.length ;i++){
	  		userids.push(rows[i].code);
  		}
	  }
	  $.ajax({
			type : "POST",
			url : '${pageContext.request.contextPath}/userGroup!userSave.do?cids='+userids.join(',')+'&gid='+gid,
			success : function(d) {
				var json = $.parseJSON(d);
				if (json.success) {
					user(gid);
					p.dialog('close');
				}
				parent.dj.messagerShow({
					msg : json.msg,
					title : '提示'
				});
			},
			error: function(e){
				alert("系统异常,请联系管理员!");
			}
		});
  }
	function _search() {
		datagrid.datagrid('load', dj.serializeObject($('#searchForm')));
	}
	function cleanSearch() {
		datagrid.datagrid('load', {});
		$('#searchForm input').val('');
	}

</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false,title:'过滤条件'" style="height: 55px;overflow: hidden;" align="left">
		<form id="searchForm">
			<table class="tableForm datagrid-toolbar" style="width: 100%;height: 100%;">
				<tr>
					<td>
					组名称:<input name="gname" style="width:180px;" />
					组类型:<input name="type" id="type" type="text" style="width:180px;"/>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="_search();">查    询</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-undo'" onclick="cleanSearch();">取消</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
		
<div align="center" data-options="region:'center',border:false" style="height: 100%">
	<form id="setForm" method="post">
		<table width="100%" height="580px"  border="0" cellspacing="0" cellpadding="0" align="center">
		 <tr>
			 <td width="25%" height="100%" align="center" valign="top">
			 	<div data-options="region:'center',border:false" class="selectData" style="height:100%" >
				   <table id="groupDatagrid"></table>	
				</div>
			 </td>
			 <td width="75%" height="100%" align="center" valign="top">
				 	<div data-options="region:'center',border:false" class="selectData" style="height:100%">
						<table id="usergroueDatagrid" ></table>
					</div>
			 </td>
		 </tr>
		</table>
	</form>
</div>
	
	
	
</body>
</html>
