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
	var gridoptions={};

	$(document).ready(function(){  
		$("#tr_ridio_single :radio").click(function(){
			type = $('input[type="radio"][name="single"]:checked').val(); 
			changeChart(type,jsondata);
		});
		
		gridoptions={
			width: '100%',
			height:'100%',
			fit:true,
			rownumbers:true,
			singleSelect:true,
			striped:true,
			frozenColumns : [[
				{title:'项目',field:'item',width:170,rowspan:2}				
			]],
			columns:[[
				{title:'',align:'center',colspan:2},
			],
			[
				{title:'提案数',field:'value2',align:'right',width:80},
				{title:'百分比',field:'percent2',align:'right',width:80,formatter:function(val){
					if(val==null||val==''){
						return '';
					}else{
						return val+'%';
					}
				}}
			]]
		};
		$('#griddata').datagrid(gridoptions);

		$('#listgrid').datagrid({
			width: '100%',
			height:'100%',
			url:'${pageContext.request.contextPath}/seco!datagridNoPage.do',
			fit:true,
			idField:'secondaryId',
			striped:true,
			frozenColumns : [[
				{field:'id',title:'选择',checkbox:true},
			]],
			columns:[[
				{field:'secondayName',title:'届次名称',align:'center',width:140,sortable:false}
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
		$('.datagrid-header .datagrid-cell').css('text-align', 'center');
	}); 
	
	function stat() {
		var itemid = $('input[type="radio"][name="id"]:checked').val();
		if (itemid==undefined){
			$.messager.alert('操作提示', "请选择统计项目！",'info');
			return false;
		}
		var selectRows = $('#listgrid').datagrid('getSelections');
		//alert(selectRows[i].toString());
		selectRows.sort(function(a,b){
            return a.secondaryId-b.secondaryId;
        });
		var jcs = [];
		var columns1=[];
		var columns2=[];
		/*if(itemid=='MERGE'){
			if(selectRows.length > 0){
				if (selectRows.length<6){
					for(var i=0; i<selectRows.length; i++){
						jcs.push(selectRows[i].secondaryId);
						columns1.push("{title:'"+selectRows[i].secondayName+"',align:'center'}");
						columns2.push("{title:'提案数',field:'value"+(i)+"',align:'center'}");
					}
				}else{
					$.messager.alert('操作提示', "最多选择五个届次！",'info');
					return false;
				}
			}else{
				$.messager.alert('操作提示', "请至少选择一个届次！",'info');
				return false;
			}
		}else{*/
			if(selectRows.length > 0){
				if (selectRows.length<6){
					for(var i=0; i<selectRows.length; i++){
						jcs.push(selectRows[i].secondaryId);
						columns1.push("{title:'"+selectRows[i].secondayName+"',align:'center',colspan:2}");
						columns2.push("{title:'提案数',field:'value"+(i)+"',align:'right',width:80}");
						columns2.push("{title:'百分比',field:'percent"+(i)+"',align:'right',width:80,formatter:function(val){if(val==null||val==''){return '0%';}else{return val+'%';}}}");
					}
				}else{
					$.messager.alert('操作提示', "最多选择五个届次！",'info');
					return false;
				}
			}else{
				$.messager.alert('操作提示', "请至少选择一个届次！",'info');
				return false;
			}
		//}
		var s1="["+columns1.join(',')+"]";
		var s2="["+columns2.join(',')+"]";
		var s3="["+s1+","+s2+"]";
		gridoptions.columns = eval(s3); 
		$('#griddata').datagrid(gridoptions);
		var param={
			"itemid":itemid,
			"jc":jcs.join(','),
			"countType":'info'
		};
		postInfoByParam("${pageContext.request.contextPath}/count!datagrid.do", param, function(response){
			var json = $.parseJSON(response);
			var jsongirddata=json.grid;
			jsondata=json.chart;
			listItem=json.item;
			type = $('input[type="radio"][name="single"]:checked').val(); 
			changeChart(type,jsondata);
			$('#griddata').datagrid('loadData',jsongirddata);
			$('.datagrid-header .datagrid-cell').css('text-align', 'center');
		});
		return false;
	}
	
	function changeChart(type,data){
		var id='chartdiv';
		var title='提案信息统计图表';
		if(type=='column')column(id, title, listItem, data);
		if(type=='column2')column2(id, title, listItem, data);
		if(type=='line')line(id, title, listItem, data);
		if(type=='area')area(id, title, listItem, data);
		if(type=='donut')donut(id, title, listItem, data);
		if(type=='spider')spider(id, title, listItem, data);
		if(type=='donut3D')donut3D(id, title, listItem, data);
}

</script>
</head>

<body class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:false" style="padding:0px" class="cs-home-remark">
			<div class="easyui-tabs" data-options="fit:true,border:false">
				<div title="统计结果" data-options="closable:false" style="padding:3px;">
					<div class="easyui-layout" data-options="fit:true">						
						<div data-options="region:'center',border:false" style="padding:2px;background:#fff;border:1px solid #ccc;">
							<table id="griddata"></table>
						</div>
					</div>
				</div>
				<div title="统计图形" data-options="closable:false" style="padding:3px">
					<div class="easyui-layout" data-options="fit:true">
						<div data-options="region:'center',border:false" style="padding:2px;background:#fff;border:1px solid #ccc;">
							<div id="chartdiv" align="center" style="min-width: 800px;max-width: 800px;margin:0 auto;"></div>
						</div>					
						<div id="chartradiodiv" data-options="region:'south',border:false" style="text-align:center;padding:5px 0;height:40px">
							<table id="radioTable" width="100%">
								<tr height="25" id="tr_ridio_single">
									<td align="center">
										<input type='radio' value='column' id='column' name='single' checked="checked" style="vertical-align:middle"><label for="column">柱状图</label></input>　　       
										<input type='radio' value='column2' id='column2' name='single' style="vertical-align:middle"><label for="column2">堆积柱图</label></input>　　       
										<input type='radio' value='line' id='line' name='single' style="vertical-align:middle"><label for="line">折线图</label></input>　　       
										<input type='radio' value='area' id='area' name='single' style="vertical-align:middle"><label for="area">面积图</label></input> 　　
										<input type='radio' value='donut' id='donut' name='single' style="vertical-align:middle"><label for="donut">半圆图</label></input> 　　
										<input type='radio' value='spider' id='spider' name='single' style="vertical-align:middle"><label for="spider">雷达图</label></input> 　　
										<input type='radio' value='donut3D' id='donut3D' name='single' style="vertical-align:middle"><label for="donut3D">3D圆环图</label></input> 　　
									</td>
								</tr>
							</table>
						</div>
					</div>
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
								<!--<span><input id="ybtj_itemid1" name="id" style="height:19px;vertical-align:middle" type="radio" value="TAZT"/><label for="ybtj_itemid1"> 提案状态</label></span><br/>-->
								<span><input id="ybtj_itemid2" name="id" style="height:19px;vertical-align:middle" type="radio" checked="checked" value="TALX"/><label for="ybtj_itemid2"> 提案类型</label></span><br/>
								<span><input id="ybtj_itemid3" name="id" style="height:19px;vertical-align:middle" type="radio" value="TAFL"/><label for="ybtj_itemid3"> 内容分类</label></span><br/>
								<!--<span><input id="ybtj_itemid4" name="id" style="height:19px;vertical-align:middle" type="radio" value="YWFZ"/><label for="ybtj_itemid4"> 业务分组</label></span><br/>-->
								<span><input id="ybtj_itemid5" name="id" style="height:19px;vertical-align:middle" type="radio" value="LAXS"/><label for="ybtj_itemid5"> 立案状态</label></span><br/>
								<span><input id="ybtj_itemid6" name="id" style="height:19px;vertical-align:middle" type="radio" value="BLLX"/><label for="ybtj_itemid6"> 办理方式</label></span><br/>
								<span><input id="ybtj_itemid7" name="id" style="height:19px;vertical-align:middle" type="radio" value="FY"/><label for="ybtj_itemid7"> 可否附议</label></span><br/>
								<span><input id="ybtj_itemid8" name="id" style="height:19px;vertical-align:middle" type="radio" value="GK"/><label for="ybtj_itemid8"> 可否公开</label></span><br/>
								<span><input id="ybtj_itemid9" name="id" style="height:19px;vertical-align:middle" type="radio" value="MERGE"/><label for="ybtj_itemid9"> 合并提案</label></span><br/>
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
		
	</div>
    </body>
 </html>
