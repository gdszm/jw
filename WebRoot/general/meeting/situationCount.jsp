<%@page import="com.tlzn.util.base.Constants"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>

<script type="text/javascript">
	var jsondata;
	var listItem;
	var type;
	function initCombox(form){
		initCombobox(form,'sex','${pageContext.request.contextPath}/dic!combox.do?ctype=SEX');
		initCombobox(form,'nation','${pageContext.request.contextPath}/dic!combox.do?ctype=NATION');
		initCombobox(form,'partyCode','${pageContext.request.contextPath}/dic!combox.do?ctype=PARTY');
		initCombobox(form,'circleCode','${pageContext.request.contextPath}/dic!combox.do?ctype=CIRCLE');
		//专委会
		initCombobox(form,'committee','${pageContext.request.contextPath}/dic!combox.do?ctype=COMMITTEE');
		
	}
	$(document).ready(function(){  
		initCombox('searchForm');
		$.ajax({
			url :'${pageContext.request.contextPath}/meetingMan!getColumns.do',
			success : function(data) {
				var column1=data.substring(0,data.indexOf("],[")+1);
				var column2=data.substring(data.indexOf("],[")+2);
		        $('#griddata').datagrid({
					width: '100%',
					height:'100%',
					url:'${pageContext.request.contextPath}/meetingMan!situationCount.do',
					fit:true,
					rownumbers:true,
					singleSelect:true,
					striped:true,
					idField:'id',
					frozenColumns : [[
						{title:'委员',field:'name',width:80,rowspan:2,align:'center',formatter: function(value,row,index){
							return '<a href="javascript:void(0)" onclick="see(\''+row.commCode+'\')">'+row.name+'</a>';
					    }},
						{title:'界别',field:'circleName',width:100,rowspan:2,align:'center'},
						{title:'所属专委会',field:'committeeName',width:120,rowspan:2,align:'center'}
					]],
					columns:[eval(column1),
					         eval(column2)],
			        toolbar: [{
							text: ' 查询', 
							iconCls: 'icon-search',
							handler: function(){
								$('#win_query').window('open');
							}
						}]
					
				});
			}
		});

		$('#listgrid').datagrid({
			width: '100%',
			height:'100%',
			url:'${pageContext.request.contextPath}/seco!datagridNoPage.do',
			fit:true,
			singleSelect:true,
			idField:'secondaryId',
			striped:true,
			checkOnSelect : true,//选择checkbox则选择行
			selectOnCheck : true,//选择行则选择checkbox
			frozenColumns : [[
				{field:'id',title:'选择',checkbox:true},
			]],
			columns:[[
				{field:'secondaryId',title:'届次名称',align:'center',width:140,sortable:false,formatter: function(value, row) {
				   	var str=row.secondayName+"("+row.year+"年度)";
					return str;
				}},				
			]],
			onLoadSuccess:function(row){ //当表格成功加载时执行默认届次选定                
                var rowData = row.rows;
                $.each(rowData,function(idx, val){//遍历JSON  
                    if(val.status=='1'){  
                        $('#listgrid').datagrid("selectRow", idx); 
                        stat();
                    }  
                });
            }
		});
	}); 
	
	//委员基本信息查看
	function see(code){
		var p = dj.dialog({
			title : '委员信息',
			href : '${pageContext.request.contextPath}/committee!view.do?code=' + code,
			width : 650,
			height : 500,
			maximized : true,
			buttons : [ {
				text : '关闭',
				iconCls:'icon-cancel',
				handler : function() {
					p.dialog('close');
				}
			} ]
		});
	}
	function stat() {
		
		var selectRows = $('#listgrid').datagrid('getSelected');
		var param={
			"secondaryId":selectRows.secondaryId
		};
		$('#griddata').datagrid('load',param);


	}
	
	function _search() {	//此处方法不能为search()
		var selectRows =$('#listgrid').datagrid('getSelected');
		var name=$('#searchForm input[name=name]').val();
		var telephone=$('#searchForm input[name=telephone]').val();
		var sex=$('#sex').combobox('getValue');
		var nation=$('#nation').combobox('getValue');
		var partyCode=$('#partyCode').combobox('getValue');
		var circleCode=$('#circleCode').combobox('getValue');
		var committee=$('#committee').combobox('getValue');
		var param={
			"name":name,
			"telephone":telephone,
			"sex":sex,
			"nation":nation,
			"circleCode":circleCode,
			"partyCode":partyCode,
			"committee":committee,
			"secondaryId":selectRows.secondaryId
		};
		$('#griddata').datagrid('load',param);
		$('#win_query').window('close');
	}
	
	function cleanSearch() {
		
		//$('#griddata').datagrid('load', {});
		var name=$('#searchForm input[name=name]').val('');
		var telephone=$('#searchForm input[name=telephone]').val('');
		$('#sex').combobox('setValue', '');
		$('#nation').combobox('setValue', '');
		$('#circleCode').combobox('setValue', '');
		$('#partyCode').combobox('setValue', '');
		$('#committee').combobox('setValue', '');

		var selectRows =$('#listgrid').datagrid('getSelected');
		var name=$('#searchForm input[name=name]').val();
		var telephone=$('#searchForm input[name=telephone]').val();
		var sex=$('#sex').combobox('getValue');
		var nation=$('#nation').combobox('getValue');
		var partyCode=$('#partyCode').combobox('getValue');
		var circleCode=$('#circleCode').combobox('getValue');
		var committee=$('#committee').combobox('getValue');
		var param={
			"name":name,
			"telephone":telephone,
			"sex":sex,
			"nation":nation,
			"circleCode":circleCode,
			"partyCode":partyCode,
			"committee":committee,
			"secondaryId":selectRows.secondaryId
		};
		$('#griddata').datagrid('load',param);
	}	

</script>
</head>

<body class="easyui-layout" >
	<div data-options="region:'center',split:true" title="统计结果" style="padding:3px">
		
		<div class="easyui-layout"  style="width: 100%;height: 95%;">						
			<div data-options="region:'center',border:false" style="padding:1px;background:#fff;border:1px solid #ccc;">
				<table id="griddata"></table>
			</div>
		</div>
		
	</div>	
	<div data-options="region:'east',iconCls:'icon-reload',split:true" title="统计设置" style="width:210px;padding:3px">
			<div class="easyui-layout" data-options="fit:true">
				<div data-options="region:'center',border:false" style="padding:1px;background:#fff;border:1px solid #ccc;">
					<table id="listgrid"></table>
				</div>
				<div data-options="region:'south',border:false" style="text-align:center;padding:5px 0;height:30%">
					<a class="easyui-linkbutton"  href="javascript:void(0)" onclick="stat();">统计</a>
				</div>
			</div>
		</div>
	
	<!-- 查询窗口-->
	<div id="win_query" class="easyui-window" data-options="title:'查询设置',iconCls:'icon-search',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:550px;height:280px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<center>
				<form id="searchForm">
				<table id=tableForm>				
					<tr height="30">
						<td nowrap>姓名：</td>
						<td align="left" colspan="3"><input type="text"   name="name" style="width:356px;padding:2px" /></td>
					</tr>
					<tr height="30">
						<td nowrap>手机号：</td>
						<td align="left" colspan="3"><input type="text" name="telephone" style="width:356px;padding:2px" /></td>
					</tr>
					<tr height="30">
						<td nowrap>性别：</td>
						<td align="left" ><input id="sex" name="sex" style="width:148px;padding:2px" /></td>
						<td nowrap>民族：</td>
						<td align="left"><input id="nation"  name="nation" style="width:148px;padding:2px" /></td>
					</tr>
					<tr height="30">
						<td nowrap>党派：</td>
						<td align="left"><input id="partyCode"  name="partyCode" style="width:148px;padding:2px" /></td>
						<td nowrap>界别：</td>
						<td align="left"><input id="circleCode"  name="circleCode" style="width:148px;padding:2px" /></td>
					</tr>
					<tr height="30">
						<td nowrap>专委会：</td>
						<td align="left" colspan="3" ><input id="committee"  name="committee" style="width:148px;padding:2px" /></td>
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
</body>
 </html>
