<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../public/inc.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/highcharts/chart.js" charset="utf-8"></script>

<script type="text/javascript">
	var jsondata;
	var radioVal="";
	$(document).ready(function(){

		$('#secondayListgrid').datagrid({
			width: '100%',
			height:'100%',
			url:'${pageContext.request.contextPath}/seco!datagridNoPage.do',
			fit:true,
			idField:'secondaryId',
			striped:true,
			singleSelect:true,
			checkOnSelect:true,
			selectOnCheck:true,
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
                    	//延迟3毫秒加载，否则不执行下面行。
                    	setTimeout(function(){
                    		$('#secondayListgrid').datagrid("selectRow", idx); 
                    	},500);
                    }  
                });
            }
		});
		$('#griddata').datagrid({
			width: '100%',
			height:'100%',
			url:'${pageContext.request.contextPath}/scoreCount!classCount.do', //计分名称
			fit:true,
			rownumbers:true,
			singleSelect:true,
			nowrap:true,
			striped:true,
			idField:'scoreId',
			pageNumber:1,
			frozenColumns : [[
				{field:'editOpt',title:'操作',width:60,align:'center',
				    formatter:function(value,rec){
				    	var viewButton='<a class="viewcls" onclick="_view(\''+rec.poll.pollId+'\')" href="javascript:void(0)">详细</a>';
				    	return  viewButton;
				    }
				},
				{title:'社情民意编号',field:'pollCode',width:100,align:'center',formatter: function(value, row) {
				    	if(row.poll.pollCode==null||"null"==row.poll.pollCode){
				    		return "";
				    	}else{
				    		return row.poll.pollCode;
				    	}
					
				}},
				{title:'标题',field:'title',width:380,align:'left',formatter: function(value, row) {
				    return row.poll.title;
				}}
				
			]],
			columns:[
			[
				{title:'提交者',field:'createMan',align:'center',width:100
					,formatter: function(value, row) {
						if(row.poll.createManName==null||"null"==row.poll.createManName){
				    		return "";
				    	}else{
				    		return row.poll.createManName;
				    	}
					    
					}
				},
				{title:'撰稿人',field:'writer',align:'center',width:80,formatter: function(value, row) {
				    if(row.poll.writer==null||"null"==row.poll.writer){
			    		return "";
			    	}else{
			    		return row.poll.writer;
			    	}
				}},
				{title:'提交日期',field:'createTime',align:'center',width:150,formatter: function(value, row) {
				    return row.poll.createTime;
				}},
				{title:'记分ID',field:'scoreId',align:'center',width:80,hidden:true},
				{title:'记分名称',field:'name',align:'center',width:180,formatter: function(value, row) {
				    return row.rules.name;
				}},
				{title:'分值',field:'score',align:'center',width:80,formatter: function(value, row) {
				    return row.rules.score;
				}}
				
			]],
			onDblClickRow : function(rowIndex, rowData) {
				_view(rowData.poll.pollId);
			}
		});
	});
	
	//查看社情民意
	function _view(pollId) {	
		window.open("${pageContext.request.contextPath}/poll!pollSee.do?pollId="+pollId);
	}
 	
	function stat() {
		var groupType = $('input[type="radio"][name="groupType"]:checked').val();
		if (groupType==undefined){
			$.messager.alert('操作提示', "请选择统计项目！",'info');
			return false;
		}
		var selectRows = $('#secondayListgrid').datagrid('getSelected');
		//alert(selectRows.toString());
		
		var param={
			"rules.id":groupType,
			"poll.tsecondary.secondaryId":selectRows.secondaryId
		};
		$('#griddata').datagrid('load',param);


	}
	//查询
	function _search() {
		var groupType = $('input[type="radio"][name="groupType"]:checked').val();
		if (groupType==undefined){
			$.messager.alert('操作提示', "请选择统计项目！",'info');
			return false;
		}
		var selectRows = $('#secondayListgrid').datagrid('getSelected');
		//alert(selectRows.toString());
		
		var pollCode=$('#searchForm input[name="pollCode"]').val();
		var title=$('#searchForm input[name="title"]').val();
		var createMan=$('#searchForm input[name="createMan"]').val();
		var param={
			"poll.pollCode":pollCode,
			"poll.title":title,
			"poll.createMan":createMan,
			"rules.id":groupType,
			"poll.tsecondary.secondaryId":selectRows.secondaryId
		};
		$('#griddata').datagrid('load',param);
	}
	//重置
	function cleanSearch() {
		var groupType = $('input[type="radio"][name="groupType"]:checked').val();
		var selectRows = $('#secondayListgrid').datagrid('getSelected');
		$('#griddata').datagrid('load', {"rules.id":groupType,"poll.tsecondary.secondaryId":selectRows.secondaryId});
		$('#searchForm input').val('');
	}
</script>
</head>
<body class="easyui-layout" >
	<div data-options="region:'north',border:false,title:'搜索条件'" style="height: 55px;overflow: hidden;" align="left">
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
							<form id="statItemForm" class="form-horizontal" action="/zxta/tjfx/blstat" method="post">
								<%--全部--%>
								<span>
								<input class="groupTypeClass" name="groupType"style="height:19px;vertical-align:middle" type="radio" checked="checked" value="" /> 
								<label for="ybtj_itemid2">全部</label>
								</span>
								<br/>
								<%--循环输出计分名称--%>
								<c:forEach var="rule" items="${ruleList}" varStatus="status">
									<span>
										<input class="groupTypeClass" name="groupType"style="height:19px;vertical-align:middle" type="radio" value="${rule.id}" /> 
										<label for="ybtj_itemid2">${rule.name}</label>
									</span>
									<br/>
								</c:forEach>
							</form>
							</td>
						</tr>
					</table>
				</div>
				<div data-options="region:'center',border:false" style="padding:1px;background:#fff;border:1px solid #ccc;">
					<table id="secondayListgrid"></table>
				</div>
				<div data-options="region:'south',border:false" style="text-align:center;padding:5px 0;height:40px">
					<a class="easyui-linkbutton"  href="javascript:void(0)" onclick="stat();">统计</a>
				</div>
			</div>
		</div>
</body>
 </html>
