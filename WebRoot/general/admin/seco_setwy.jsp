<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.tlzn.pageModel.sys.Comm"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <script type="text/javascript">
 //定义全局 ,获取俩个选择框对象
 
 var leftSelect,rightSelect;
 $(document).ready(function() {
 	leftSelect = document.getElementById("leftSelect");
  	rightSelect = document.getElementById('rightSelect');
 });
 //向右边移动
 function moveRight(){
 //获取leftSelect 选择框的option集
  var ops=leftSelect.getElementsByTagName("option");
    
    for(var i=0;i<ops.length;i++){
  
    if(ops[i].selected){
  
    rightSelect.appendChild(ops[i--]);  
       
   
       }
    }
 }
 //全部移动到右边
 function moveRightAll(){
  
  var ops=leftSelect.getElementsByTagName("option");
  
    for(var i=0;i<ops.length;i++){
  
    rightSelect.appendChild(ops[i--]);
       
   
    }
 }
 //右边移动到左边
 function moveLeft(){
 
   var ops = rightSelect.getElementsByTagName('option');
  
    for(var i = 0 ; i<ops.length; i++){
   
      if(ops[i].selected){
    
      leftSelect.appendChild(ops[i--]);
    }
   
    }
 }
  //全部移动到右边
 function moveLeftAll(){
  
  var ops=rightSelect.getElementsByTagName("option");
  
    for(var i=0;i<ops.length;i++){
  
    leftSelect.appendChild(ops[i--]);
       
   
    }
 }
 </script>
<div align="center" data-options="region:'center',border:false" style="padding:3px;background:#fff;border:1px solid #ccc; ">
	<form id="setForm" method="post">
		<input type="hidden" name="secondaryId"/>
		<input type="hidden" id="leftdata" name="leftdata"/>
		<input type="hidden" id="rightdata" name="rightdata"/>
		<table width="500" border="0" cellspacing="0" cellpadding="0" align="center">
		 <tr height="30">
			<td align="center" nowrap >届次名称：</td>
			<td align="left" colspan="3"> <input type="text" name="secondayName" readonly="readonly" style="width:280px;padding:2px"></td>
		</tr>
		 <tr>
		 <td width="220" align="center" valign="top">
		  未选择提案人<br><br>
		  <select multiple class="one" id="leftSelect" style="float:left">
			<c:forEach items="${norowlist}" var="item">
				<option value="${item.code}">${item.name}</option>
			</c:forEach>
		  </select>
		  </td>
		  <td width="60" align="center">
		    <input type = "button" value ="  >   " onclick="moveRight()" ><br><br> 
		    <input type = "button" value ="  >>  " onclick="moveRightAll()"><br><br>
		    <input type = "button" value ="  <   " onclick="moveLeft()"><br><br>
		    <input type = "button" value ="  <<  " onclick="moveLeftAll()"><br><br>
		  </td>
		 <td width="220" align="center" valign="top">
		 已选择提案人<br><br>
		  <select multiple class="one" id="rightSelect">
		  	<c:forEach items="${rowlist}" var="item">
				<option value="${item.code}">${item.name}</option>
			</c:forEach>
		  
		  </select>
		 </td>
		 </tr>
		 </table>
	</form>
</div>
