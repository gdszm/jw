<%@ page language="java" pageEncoding="UTF-8"%>
${msg}
<script type="text/javascript" charset="utf-8">
	parent.$.messager.progress('close');
	setTimeout("parent.location.href='${pageContext.request.contextPath}/user!doNotNeedSession_index.do'",2000);
</script>
