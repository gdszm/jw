<%@page import="com.tlzn.util.base.Constants"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

 <script type="text/javascript">
 	var pollId ;
	$(document).ready(function() {
		scoreGrid=$('#scoreGrid');
		//加载批示记分记录
		scoreGrid.datagrid({
			url:'${pageContext.request.contextPath}/pollScore!scoreRecordDatagrid.do?poll.pollId='+pollId,
			width: 'auto',
			height:'auto',
			striped:true,
			nowrap:true,
			rownumbers:true,
			singleSelect:true,
			pagination:true,
			pageSize:10,
			pageList:[10,20,50],
			pageNumber:1,
			idField:'id',
			sortName:'id',
			sortOrder : 'asc',
			fit:true,
			checkOnSelect: true, 
			selectOnCheck: true,
			view: detailview,
			detailFormatter:function(index,row){  
				return '<div id="tablk-ddv-' + index + '" style="padding:2px"></div>';  
			},  
			onExpandRow: function(index,row){
				var content='';
				if(row.comment!=null && row.comment!='') {
					content=content+'<center><font color="#ff0000">批示内容:</font></center>'+row.comment+'<br/>';
				}
				if(row.memo!=null && row.memo!='') {
					content=content+'<center><font color="#ff0000">备注:</font></center>'+row.memo+'<br/>';
				}
				if (content==''){
					content='<br/><br/><center><font color="#ff0000">没有内容或备注!</font></center><br/><br/>';
				}
				$('#tablk-ddv-'+index).panel({   
					height:'auto',
					border: true,
					style: {borderWidth:1},  
					cache:false,
					content: content
				});  
				scoreGrid.datagrid('fixDetailRowHeight',index);  
			},
			columns:[[
				{field:'scoreId',title:'ID',width:50,align:'center',checkbox : true},
				{field:'001',title:'计分类型名称',align:'center',width:150,
		            formatter:function(value,rec){
		            		return rec.rules.typeName;
		            }},
				{field:'002',title:'计分名称',align:'center',width:150,
	            	 formatter:function(value,rec){
		            		return rec.rules.name;
		            }},
				{field:'003',title:'分值 ',align:'center',width:50,
	            	 formatter:function(value,rec){
		            		return rec.rules.score;
		            }},
				{field:'creatTime',title:'录入时间',align:'center',width:180},
				{field:'editOpt',title:'操作',width:50,align:'center',width:150,
		            formatter:function(value,rec){
		            	//计分类型名称：领导审批
		            	if(rec.rules.typeid==<%=Constants.DIC_TYPE_RULESTYPE_LDPS%>) {
						    var editButton='<a class="editcls" onclick="_edit(\''+rec.scoreId+'\')" href="javascript:void(0)">修改批示和备注</a>';   
		            		return editButton;
		            	} else {
		            		return '';
		            	}
		            }
		        }, 
				{field:'delOpt',title:'操作',width:50,align:'center',width:150,
		            formatter:function(value,rec){
				    	return '<a class="delcls" onclick="_del(\''+rec.scoreId+'\')" href="javascript:void(0)">删除</a>';   
		            }  
		        } 
			]],
			onLoadSuccess : function(data){
			//提交按钮格式化成easyui按钮
			$('.editcls').linkbutton({text:'修改批示和备注',plain:true,iconCls:'icon-edit'});
			$('.delcls').linkbutton({text:'删除',plain:true,iconCls:'icon-edit'});
			}
		});
	});
	//修改批示和备注
	function _edit(scoreId) {	
		//var selectRow = scoreGrid.datagrid('getSelected');
		var p = dj.dialog({
			title : '批注和备注修改',
			href : '${pageContext.request.contextPath}/pollScore!commMemoEdit.do?scoreId='+scoreId,
			width : 800,
			height : 600,
			buttons : [ {
				text : ' 提交 ',
				iconCls : 'icon-edit',
				handler : function() {
					var f = p.find('form');
					f.form('submit', {
						url : '${pageContext.request.contextPath}/pollScore!commMemo_submit.do',
						success : function(d) {
							var json = $.parseJSON(d);
							if (json.success) {
								scoreGrid.datagrid('reload');
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
				var f = p.find('form');
				f.form('load', {
					scoreId : scoreId
				});
			}
		});
	}
	//删除记分记录
	function _del(scoreId) {	
		parent.dj.messagerConfirm('请确认', '您要删除该条记分记录吗？', function(r) {
			if (r) {
				$.ajax({
					url : '${pageContext.request.contextPath}/pollScore!scoreRecord_del.do',
					data : {
						scoreId : scoreId
					},
					dataType : 'json',
					success : function(d) {
						scoreGrid.datagrid('reload');
						parent.dj.messagerShow({
							title : '提示',
							msg : d.msg
						});
					}
				});
			}
		});
	}
 </script>
<!-- 表格 -->
 <div data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc;">
	<center>
		<form id="addScoreForm" class="form-horizontal"  method="post">
		<input type="hidden" id="pollId" name="poll.pollId"/>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		      <tr>
		        <td><table class="tableborder" id="formTable" sizset="false" >
		           <tbody sizset="false" >
		            <tr height="25">
		              <td height="25" colspan="4" align="middle" nowrap="nowrap" class="tablemain">
		                <div align="center"><strong>社情民意信息</strong></div>
		              </td>
		            </tr>
		          <tr height="25" sizset="false">
		              <td height="25" width="80" nowrap="nowrap" class="tablelabel">社情民意编号：</td>
		              <td height="25" width="160" class="tablecontent" sizset="false"><label id="pollCode" />  </td>
		              <td height="25" width="80" nowrap="nowrap" class="tablelabel">年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度：</td>
		              <td height="25" width="160" class="tablecontent" sizset="false" ><label id="secondaryYear" /></td>
		            </tr>
					<tr height="25" sizset="false" >
		              <td height="25"　width="80" nowrap="nowrap" class="tablelabel">标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题：</td>
		              <td height="25" colspan="3" class="tablecontent"  sizset="false" ><label id="tl" /></td>
		            </tr>
		            <tr height="25" sizset="false" >
		              <td height="25" width="80" nowrap="nowrap" class="tablelabel">社情民意类型：</td>
		              <td height="25" width="160" class="tablecontent" sizset="false" ><label id="pType" /></td>
		              <td height="25" width="80" nowrap="nowrap" class="tablelabel">提交者：</td>
		              <td height="25" width="160" class="tablecontent" sizset="false" ><label id="cMan" /></td>
		            </tr>
		             <tr height="25">
		              <td height="25" colspan="4" align="middle" nowrap="nowrap" class="tablemain">
		                <div align="center"><strong>计分信息</strong></div>
		              </td>
		            </tr>
		           <tr height="420" sizset="false" >
		              <td colspan="4" align="middle" nowrap="nowrap" >
		              <div class="easyui-layout" data-options="fit:true">						
							<div data-options="region:'center',border:false" style="padding:1px;background:#fff;border:1px solid #ccc;">
								<table id="scoreGrid"></table>
							</div>
						</div>
						</td>
		            </tr>
		          </tbody>
		        </table></td>
		        </tr>
		    </table>
		   </form>
		 </center>
	</div>
	

	