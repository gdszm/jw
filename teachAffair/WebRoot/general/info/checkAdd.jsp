<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
	<center>
	
	<table >				
		<tr height="25">
			<td nowrap>
				<form id="dw_queryForm">
				单位过滤：
					<input type="text" name="companyName" id="companyName" style="width:100px;padding:2px"/>
				单位类型：
					<input  name="companyType" id="companyType" style="width:80px;padding:2px"/>
					<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dw_search();">搜索</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dw_cleanSearch();">取消</a>
				</form>
			</td>
			<td>
				办理方式：<label id="ht"></label>
			</td>				
		</tr>
		<tr height="200" >
			<td colspan="2" nowrap> <table id="dwgrid"></table></td>
		</tr>
		<tr height="25" >
			<td colspan="2" nowrap><div align="left"  id="hostH"><input type="hidden" id="hostHandCode"  />主办单位：<label id="hostHand"></label></div></td>
		</tr>
		<tr height="25" >
			<td colspan="2" nowrap><div align="left" id="aid"><input type="hidden" id="aidCompCode"  />协办单位：<label id="aidComp"></label></div></td>
		</tr>
	</table>
	
	</center>
</div>
