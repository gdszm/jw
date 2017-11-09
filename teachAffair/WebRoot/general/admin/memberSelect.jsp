<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <script type="text/javascript">
  var rightids=$("#commCodesId").val();
  $(document).ready(function() {
	 treeRead();
	 leftReadData('',rightids);
	 rightReadData(rightids);	
  });
  
   function treeRead(){
    	$('#depttree').tree({    
		    url:'${pageContext.request.contextPath}/dept!doNotNeedSession_treeRecursive.do',
		    onClick: function(node){
				leftReadData(node.id,rightids);
			}
		});
    }
//数据读取
 function leftReadData(deptid,ids){
		$('#leftdatagrid').datagrid({
			url : '${pageContext.request.contextPath}/comm!selectDatagrid.do?page=1&rows=100000&ids='+ids,
			striped:true,
			nowrap:true,
			rownumbers:true,
			idField:'code',
			fit:true,
			fitColumns :false,
			frozenColumns:[[
			{
				title : '编号',
				field : 'code',
				width : 10,
				sortable : true,
				checkbox : true
			}, {
				title : '用户名',
				field : 'name',
				width : 60,
				sortable : true,
				formatter: function(value,row,index){
					return  '<span title='+value+'>'+value+'</span>';
				}
			}
			]],
			columns : [ [ {
				title : '工作单位',
				field : 'companyName',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					return  '<span title='+value+'>'+value+'</span>';
				}	
			},{
				title : '职务',
				field : 'job',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					return  '<span title='+value+'>'+value+'</span>';
				}	
			}, {
				title : '专委会',
				field : 'committeeName',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					return  '<span title='+value+'>'+value+'</span>';
				}
			},{
				title : '电话',
				field : 'telephone',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					return  '<span title='+value+'>'+value+'</span>';
				}
			}
			]]
			
		});
	 }
 
  //数据读取
 function rightReadData(ids){
		$('#rightdatagrid').datagrid({
			url : '${pageContext.request.contextPath}/comm!commsDatagrid.do?page=1&rows=100000&ids='+ids,
			striped:true,
			nowrap:true,
			rownumbers:true,
			idField:'code',
			fit:true,
			fitColumns :false,
			frozenColumns:[[
			{
				title : '编号',
				field : 'code',
				width : 10,
				sortable : true,
				checkbox : true
			}, {
				title : '用户名',
				field : 'name',
				width : 60,
				sortable : true,
				formatter: function(value,row,index){
					return  '<span title='+value+'>'+value+'</span>';
				}
			}
			]],
			columns : [ [ {
				title : '工作单位',
				field : 'companyName',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					return  '<span title='+value+'>'+value+'</span>';
				}	
			},{
				title : '职务',
				field : 'job',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					return  '<span title='+value+'>'+value+'</span>';
				}	
			}, {
				title : '专委会',
				field : 'committeeName',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					return  '<span title='+value+'>'+value+'</span>';
				}
			},{
				title : '电话',
				field : 'telephone',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					return  '<span title='+value+'>'+value+'</span>';
				}
			}
			]]
			
		});

 }
 //向右边移动
 function moveRight(){
 //获取leftdatagrid 选择框的数据集
  var rows=$('#leftdatagrid').datagrid('getSelections');
	if(rows.length>0){
		for(var i = rows.length-1; i>=0 ;i--){
			$('#rightdatagrid').datagrid('appendRow',rows[i]);
			var rowindex=$('#leftdatagrid').datagrid('getRowIndex',rows[i]);
			$('#leftdatagrid').datagrid('deleteRow',rowindex);
		}
	}
 }
 //全部移动到右边
 function moveRightAll(){
  
   var rows=$('#leftdatagrid').datagrid('getData');
	if(rows.length>0){
		for(var i = rows.length-1; i>=0 ;i--){
			$('#rightdatagrid').datagrid('appendRow',rows[i]);
			var rowindex=$('#leftdatagrid').datagrid('getRowIndex',rows[i]);
			$('#leftdatagrid').datagrid('deleteRow',rowindex);
		}
	}
 }
 //右边移动到左边
 function moveLeft(){
  //获取rightdatagrid 选择框的数据集
  var rows=$('#rightdatagrid').datagrid('getSelections');
  var rowtree=$('#depttree').tree('getSelected');
	if(rows.length>0){
		for(var i = rows.length-1; i>=0 ;i--){
			if(rowtree==null ||rows[i].deptId==rowtree.id){
				$('#leftdatagrid').datagrid('appendRow',rows[i]);
			}
			var rowindex=$('#rightdatagrid').datagrid('getRowIndex',rows[i]);
			$('#rightdatagrid').datagrid('deleteRow',rowindex);
		}
	}
 }
  //全部移动到右边
 function moveLeftAll(){
  
 var rows=$('#rightdatagrid').datagrid('getData');
  var rowtree=$('#depttree').tree('getSelected');
	if(rows.length>0){
		for(var i = rows.length-1; i>=0 ;i--){
			if(rowtree==null || rows[i].deptId==rowtree.id){
				$('#leftdatagrid').datagrid('appendRow',rows[i]);
			}
			var rowindex=$('#rightdatagrid').datagrid('getRowIndex',rows[i]);
			$('#rightdatagrid').datagrid('deleteRow',rowindex);
		}
	}
 }
  
   //可选用户过虑
	function user_search(){
		$('#leftdatagrid').datagrid('load', dj.serializeObject($('#setForm')));
	}
	function user_reset() {
		$('#leftdatagrid').datagrid('load', {});
		$('#nameId').val('');
		$('#nameId').val('');
		$('#gid').combobox('setValue', '');
	}
 </script>
<div align="center" data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc; ">
	<form id="setForm" method="post">
		<input type="hidden" name="commCodes" id="commCodesId" value="${commCodes}">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
		 <tr height="30">
			<td align="left" colspan="4">
				  委员姓名：<input id="nameId" type="text" name="name"  style="width:100px;padding:2px">
				 用户组：<input id="gid" type="text" name="gid"  style="width:100px;padding:2px">
				<a id="searchbtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="user_search();">查询</a>
				<a id="resetbtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-undo'" onclick="user_reset();">重置</a>
			</td>
			
		</tr>
		 <tr>
		 <td width="25%" align="center" valign="top">
		 	<div class="selectData">
			 	<div style="background-color:#5AC7FF;text-align:center;height: 20px">可选用户</div>
			 	<div data-options="region:'center',border:false" style="height:328px; overflow: auto;" >
					<table id="leftdatagrid" ></table>
				</div>
			 </div>
		</td>
		  <td width="5%" align="center">
		  	<div class="selectData" style="overflow:hidden;">
			    <input type = "button" value ="  >>  " style="margin-top: 100px" onclick="moveRight()"/>
			    <br/>
			    <input type = "button" value ="  <<  " style="margin-top: 50px"  onclick="moveLeft()"/>
			</div>
		    
		  </td>
		 <td width="30%" align="center" valign="top">
		 <div class="selectData">
		 	<div style="background-color:#5AC7FF;text-align:center;height:20px">已选用户</div>
		 	<div data-options="region:'center',border:false" style="height:328px;overflow: auto;">
				<table id="rightdatagrid" ></table>
			</div>
		 </div>
		 </tr>
		 </table>
	</form>
</div>
