<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div align="center" style="padding: 5px;overflow: hidden;">
	<form id="commMemoEditForm" enctype=multipart/form-data method="post">
		<input name="scoreId"  id="scoreId" type="hidden" value="${pollScore.scoreId}" />
		<table class="tableForm">
			<tr>
				<th>批示:</th>
				<td>
					<textarea id="commentId" name="comment" class="textarea easyui-validatebox" rows="10">${pollScore.comment}</textarea>
				</td>
			</tr>
			<tr>
				<th>备注:</th>
				<td>
					<textarea id="memoId" name="memo" class="textarea easyui-validatebox" rows="10">${pollScore.memo}</textarea>	
				</td>
			</tr>
		</table>
	</form>
</div>
