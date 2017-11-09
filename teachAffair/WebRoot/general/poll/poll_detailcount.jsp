<%@page import="com.tlzn.util.base.Constants"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/highcharts/chart.js" charset="utf-8"></script>

<script type="text/javascript">
	var jsondata;
	var listItem;
	var type;

	$(document).ready(function(){  
		
		$.ajax({
			url :'${pageContext.request.contextPath}/scoreCount!getColumns.do',
			success : function(data) {
				var column1=data.substring(0,data.indexOf("],[")+1);
				var column2=data.substring(data.indexOf("],[")+2);
		        $('#griddata').datagrid({
					width: '100%',
					height:'100%',
					url:'${pageContext.request.contextPath}/scoreCount!detailCount.do',
					fit:true,
					rownumbers:true,
					singleSelect:true,
					striped:true,
					idField:'pollId',
					frozenColumns : [[
						{field:'pollId',title:'操作',align:'center',width:60,formatter:rowformater},
						{title:'社情民意编号',field:'pollCode',width:80,rowspan:2},
						{title:'标题',field:'title',width:240,rowspan:2}
					]],
					columns:[eval(column1),
					         eval(column2)],
					onDblClickRow : function(rowIndex, rowData) {
						see(rowData.pollId);
					}
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
				   	var str=row.secondayName+"("+row.year+"年度)"
					return str;
				}},				
			]],
			onLoadSuccess:function(row){ //当表格成功加载时执行默认届次选定                
                var rowData = row.rows;
                $.each(rowData,function(idx, val){//遍历JSON  
                    if(val.status=='1'){  
                        $('#listgrid').datagrid("selectRow", idx); 
                        //stat();
                    }  
                });
            }
		});
	}); 
	
	function rowformater(value,row,index){
    	return '<a href="javascript:void(0)" onclick="see('+row.pollId+')">详细</a>';
    }
	//查看社情民意
	function see(pollId){
		window.open("${pageContext.request.contextPath}/poll!pollSee.do?pollId="+pollId);
	}
	function stat() {
		var pollType = $('input[type="radio"][name="pollType"]:checked').val();
		if (pollType==undefined){
			$.messager.alert('操作提示', "请选择统计项目！",'info');
			return false;
		}
		var selectRows = $('#listgrid').datagrid('getSelected');
		//alert(selectRows.toString());
		
		var param={
			"poll.pollType":pollType,
			"poll.tsecondary.secondaryId":selectRows.secondaryId
		};
		$('#griddata').datagrid('load',param);


	}
	//查询
	function _search() {
		var pollType = $('input[type="radio"][name="pollType"]:checked').val();
		if (pollType==undefined){
			$.messager.alert('操作提示', "请选择统计项目！",'info');
			return false;
		}
		var selectRows = $('#listgrid').datagrid('getSelected');
		//alert(selectRows.toString());
		var pollCode=$('#searchForm input[name="pollCode"]').val();
		var title=$('#searchForm input[name="title"]').val();
		var createMan=$('#searchForm input[name="createMan"]').val();
		var param={
			"poll.pollCode":pollCode,
			"poll.title":title,
			"poll.createMan":createMan,
			"poll.pollType":pollType,
			"poll.tsecondary.secondaryId":selectRows.secondaryId
		};
		$('#griddata').datagrid('load',param);
	}
	//重置
	function cleanSearch() {
		var pollType = $('input[type="radio"][name="pollType"]:checked').val();
		var selectRows = $('#listgrid').datagrid('getSelected');
		$('#griddata').datagrid('load', {"poll.pollType":pollType,"poll.tsecondary.secondaryId":selectRows.secondaryId});
		$('#searchForm input').val('');
	}

</script>
</head>

<body class="easyui-layout" >
	<div data-options="region:'center',split:true" title="统计结果" style="padding:3px">
		<div>		
		<form id="searchForm">
			<table class="tableForm datagrid-toolbar" style="width: 100%;height: 30px;">
				<tr>
					<td>
					社情民意编号：<input name="pollCode" style="width:120px;" />
					标题：<input name="title" style="width:220px;" />
					提交者：<input name="createMan" style="width:80px;padding:2px" />
					<a href="javascript:void(0);" class="easyui-linkbutton" onclick="_search();">搜索</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" onclick="cleanSearch();">取消</a>
					</td>
				</tr>
			</table>
		</form>
		</div>	
		<div class="easyui-layout"  style="width: 100%;height: 95%;">						
			<div data-options="region:'center',border:false" style="padding:1px;background:#fff;border:1px solid #ccc;">
				<table id="griddata"></table>
			</div>
		</div>
		
	</div>	
	<div data-options="region:'east',iconCls:'icon-reload',split:true" title="统计设置" style="width:210px;padding:3px">
			<div class="easyui-layout" data-options="fit:true">
				<div data-options="region:'north',border:false" style="padding:2px;background:#fff;border:1px solid #ccc;height:260px">
					<table id="formTable">
						<tr>
							<td>&nbsp;&nbsp;</td>
							<td valign="middle">
							<form id="statItemForm" class="form-horizontal" action="/zxta/tjfx/blstat" method="post"></span><br/>
								<span><input id="ybtj_itemid1" name="pollType" style="height:19px;vertical-align:middle" type="radio" checked="" value=""/><label for="ybtj_itemid1"> 全部</label></span><br/>
								<c:forEach var="item" items="${ptList}">
									<span><input id="ybtj_itemid2" name="pollType" style="height:19px;vertical-align:middle" type="radio" value="${item.cvalue}"/><label for="ybtj_itemid2">${item.ckey}</label></span><br/>
								</c:forEach> 
								
							</form>
							</td>
						</tr>
					</table>
				</div>
				<div data-options="region:'center',border:false" style="padding:1px;background:#fff;border:1px solid #ccc;">
					<table id="listgrid"></table>
				</div>
				<div data-options="region:'south',border:false" style="text-align:center;padding:5px 0;height:40px">
					<a class="easyui-linkbutton"  href="javascript:void(0)" onclick="stat();">统计</a>
				</div>
			</div>
		</div>
</body>
 </html>
