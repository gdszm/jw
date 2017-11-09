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
				
		$('#griddata').datagrid({
			url:'${pageContext.request.contextPath}/scoreCount!sumCount.do',
			width: '100%',
			height:'100%',
			fit:true,
			rownumbers:true,
			singleSelect:true,
			striped:true,
			idField:'code',
			frozenColumns : [[
				{title:'提交者',field:'name',width:130}				
			]],
			columns:[
			[
				{title:'采用篇数',field:'adoptnum',align:'right',width:80},
				{title:'批示篇数',field:'issnum',align:'right',width:80},
				{title:'积分',field:'tosum',align:'right',width:80},
				{title:'排名',field:'rownumbers',align:'right',width:80,
					formatter: function(value,row,index){
						return index+1;
				}}
					
			]]
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
	function stat() {
		var groupType = $('input[type="radio"][name="groupType"]:checked').val();
		if (groupType==undefined){
			$.messager.alert('操作提示', "请选择统计项目！",'info');
			return false;
		}
		var selectRows = $('#listgrid').datagrid('getSelected');
		//alert(selectRows.toString());
		
		var param={
			"groupType":groupType,
			"secondaryId":selectRows.secondaryId
		};
		$('#griddata').datagrid('load',param);


	}
	

</script>
</head>

<body class="easyui-layout" >
	<div data-options="region:'center',split:true" title="统计结果" style="padding:3px">
		<div class="easyui-layout" data-options="fit:true">						
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
								<span><input id="ybtj_itemid1" name="groupType" style="height:19px;vertical-align:middle" type="radio" checked="" value=""/><label for="ybtj_itemid1"> 全部</label></span><br/>
								<span><input id="ybtj_itemid2" name="groupType" style="height:19px;vertical-align:middle" type="radio" value="<%=Constants.DIC_TYPE_GROUP_WY%>"/><label for="ybtj_itemid2"> 委员</label></span><br/>
								<span><input id="ybtj_itemid3" name="groupType" style="height:19px;vertical-align:middle" type="radio" value="<%=Constants.DIC_TYPE_GROUP_QXQ%>"/><label for="ybtj_itemid3"> 旗县区政协</label></span><br/>
								<span><input id="ybtj_itemid4" name="groupType" style="height:19px;vertical-align:middle" type="radio" value="<%=Constants.DIC_TYPE_GROUP_ZWH%>"/><label for="ybtj_itemid4"> 各处室、专委会</label></span><br/>
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
