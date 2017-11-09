<%--社情民意首页--%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.tlzn.util.base.Constants"%>
<script type="text/javascript">
function queryPoll(group,status){
	var url="";
 	if(group=='1'){
 	//委员
 		url='/poll!pollown.do?statusType='+status;
 		addTab('我的社情民意',url,'icon-cx');
 	}else if(group=='2'){
 	//承办单位
 		url='/pollHandle!hand.do?statusType='+status;
 		addTab('社情民意办复',url,'icon-cx');
 	}
 	else if(group=='6'){
 	//信息处长
	url='/poll!pollcheck.do?statusType='+status;
	addTab('社情民意审批',url,'icon-cx');
 	}
 	else if(group=='7'){
 	//秘书长
	url='/poll!pollcheck.do?statusType='+status;
	addTab('社情民意审批',url,'icon-cx');
 	}
 	
	else{
	//管理员and信息员
 		url='/poll!poll.do?statusType='+status;
 		addTab('社情民意管理',url,'icon-cx');
 	}
}
</script>
<center>
	<table border="0" width="70%" cellspacing="0" cellpadding="0" height="auto" style="margin-top: 10px;border: 5px;solid;red;" >
		<tr>
		  <td width="100%" align="center" height="auto" >
		  	  <!--常量（用户级别为委员） -->
	
			  <c:set var="ug_wy" value="<%=Constants.DIC_TYPE_YHZB_WY %>" scope="page"></c:set>
			  <c:set var="ug_cbdw" value="<%=Constants.DIC_TYPE_YHZB_CBDW %>" scope="page"></c:set>
			  <c:set var="ug_xxcz" value="<%=Constants.DIC_TYPE_YHZB_XXCZ %>" scope="page"></c:set>
			  <c:set var="ug_msz" value="<%=Constants.DIC_TYPE_YHZB_MSZ %>" scope="page"></c:set>
			  <c:choose>
					<%--委员--%>
				   <c:when test="${sessionInfo.userGroup==ug_wy}"> 
				   	<TABLE cellpadding=3 cellspacing=1 align=center class=tableborder width="98%" name="board" boder='1'> 
						<TBODY>
						<TR>
							<TD colspan=4 height=31 align=center valign=middle class=tablemain>我的社情民意情况</TD>
						</TR>
						<TR height="35">
							<TD class=tabletitle align=center colspan=2 >项目</TD>
							<TD class=tabletitle align=center >数目</TD>
							<TD class=tabletitle align=center >操作</TD>
						</TR>
						<TR height="35">
							<TD class=tablecontent align=center colspan=2>已保存社情民意</TD>
							<TD class=tablecontent align=center><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countPollMap['ybcPollNum']}</I></B></FONT></TD>
							<TD class=tablecontent align=center ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="queryPoll('1','A');">查看</a></TD>
						</TR>
						<TR height="35">
							<TD class=tablecontent align=center colspan=2>未审查社情民意</TD>
							<TD class=tablecontent align=center><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countPollMap['wscPollNum']}</I></B></FONT></TD>
							<TD class=tablecontent align=center ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="queryPoll('1','B');">查看</a></TD>
						</TR>
						<TR height="35">
							<TD class=tablecontent align=center colspan=2>未采用社情民意</TD>
							<TD class=tablecontent align=center><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countPollMap['wcyPollNum']}</I></B></FONT></TD>
							<TD class=tablecontent align=center ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="queryPoll('1','C');">查看</a></TD>
						</TR>
						<TR height="35">
							<TD class=tablecontent align=center colspan=2>已编辑社情民意</TD>
							<TD class=tablecontent align=center><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countPollMap['ycyPollNum']}</I></B></FONT></TD>
							<TD class=tablecontent align=center ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="queryPoll('1','D');">查看</a></TD>
						</TR>
						<TR height="35">
							<TD class=tablecontent align=center colspan=2>未审核社情民意</TD>
							<TD class=tablecontent align=center><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countPollMap['wshPollNum']}</I></B></FONT></TD>
							<TD class=tablecontent align=center ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="queryPoll('1','E');">查看</a></TD>
						</TR>
						<TR height="35">
							<TD class=tablecontent align=center colspan=2>已审核社情民意</TD>
							<TD class=tablecontent align=center><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countPollMap['yshPollNum']}</I></B></FONT></TD>
							<TD class=tablecontent align=center ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="queryPoll('1','F');">查看</a></TD>
						</TR>
						<TR height="35">
							<TD class=tablecontent align=center colspan=2>已签发社情民意</TD>
							<TD class=tablecontent align=center><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countPollMap['ysqPollNum']}</I></B></FONT></TD>
							<TD class=tablecontent align=center ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="queryPoll('1','G');">查看</a></TD>
						</TR>
						<TR height="35">
							<TD class=tablecontent align=center colspan=2>已印发社情民意</TD>
							<TD class=tablecontent align=center><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countPollMap['yyfPollNum']}</I></B></FONT></TD>
							<TD class=tablecontent align=center ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="queryPoll('1','H');">查看</a></TD>
						</TR>
						</TBODY>
					</TABLE>
				   </c:when> 
					<%--承办单位--%>
				   <c:when test="${sessionInfo.userGroup==ug_cbdw}"> 
						<TABLE cellpadding=3 cellspacing=1 align=center class=tableborder width="98%" name="board">
							<TBODY>
							<TR>
								<TD colspan=4 height=31 align=center valign=middle class=tablemain>办理社情民意情况</TD>
							</TR>
							<TR height="35">
								<TD class=tabletitle align=center colspan=2 >项目</TD>
								<TD class=tabletitle align=center >数目</TD>
								<TD class=tabletitle align=center >操作</TD>
							</TR>
							<TR height="35">
								<TD class=tablecontent align=center colspan=2>新社情民意</TD>
								<TD class=tablecontent align=center><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countPollHandleMap['newPollNum']}</I></B></FONT></TD>
								<TD class=tablecontent align=center ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="queryPoll('2','A');">查看</a></TD>
							</TR>
							<TR height="35">
								<TD class=tablecontent align=center colspan=2>已签收社情民意</TD>
								<TD class=tablecontent align=center><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countPollHandleMap['yqsPollNum']}</I></B></FONT></TD>
								<TD class=tablecontent align=center ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="queryPoll('2','B');">查看</a></TD>
							</TR>
							<TR height="35">
								<TD class=tablecontent align=center colspan=2>申退中社情民意</TD>
								<TD class=tablecontent align=center><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countPollHandleMap['stzPollNum']}</I></B></FONT></TD>
								<TD class=tablecontent align=center ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="queryPoll('2','C');">查看</a></TD>
							</TR>
							<TR height="35">
								<TD class=tablecontent align=center colspan=2>已答复社情民意</TD>
								<TD class=tablecontent align=center><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countPollHandleMap['ydfPollNum']}</I></B></FONT></TD>
								<TD class=tablecontent align=center ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="queryPoll('2','D');">查看</a></TD>
							</TR>
							</TBODY>
						</TABLE>
				   </c:when> 
					<%--信息处长--%>
				   <c:when test="${sessionInfo.userGroup==ug_xxcz}"> 
				   	<TABLE cellpadding=3 cellspacing=1 align=center class=tableborder width="98%" name="board" boder='1'> 
						<TBODY>
						<TR>
							<TD colspan=4 height=31 align=center valign=middle class=tablemain>社情民意审核情况</TD>
						</TR>
						<TR height="35">
							<TD class=tabletitle align=center colspan=2 >项目</TD>
							<TD class=tabletitle align=center >数目</TD>
							<TD class=tabletitle align=center >操作</TD>
						</TR>
						<TR height="35">
							<TD class=tablecontent align=center colspan=2>未审核社情民意</TD>
							<TD class=tablecontent align=center><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countPollMap['wshPollNum']}</I></B></FONT></TD>
							<TD class=tablecontent align=center ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="queryPoll('6','E');">查看</a></TD>
						</TR>
						<TR height="35">
							<TD class=tablecontent align=center colspan=2>已审核社情民意</TD>
							<TD class=tablecontent align=center><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countPollMap['yshPollNum']}</I></B></FONT></TD>
							<TD class=tablecontent align=center ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="queryPoll('6','F');">查看</a></TD>
						</TR>
						</TBODY>
					</TABLE>
					</c:when>
					<%--秘书长--%>
					<c:when test="${sessionInfo.userGroup==ug_msz}"> 
				   	<TABLE cellpadding=3 cellspacing=1 align=center class=tableborder width="98%" name="board" boder='1'> 
						<TBODY>
						<TR>
							<TD colspan=4 height=31 align=center valign=middle class=tablemain>社情民意审签情况</TD>
						</TR>
						<TR height="35">
							<TD class=tabletitle align=center colspan=2 >项目</TD>
							<TD class=tabletitle align=center >数目</TD>
							<TD class=tabletitle align=center >操作</TD>
						</TR>
						<TR height="35">
							<TD class=tablecontent align=center colspan=2>已审核社情民意</TD>
							<TD class=tablecontent align=center><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countPollMap['yshPollNum']}</I></B></FONT></TD>
							<TD class=tablecontent align=center ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="queryPoll('7','F');">查看</a></TD>
						</TR>
						<TR height="35">
							<TD class=tablecontent align=center colspan=2>已签发社情民意</TD>
							<TD class=tablecontent align=center><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countPollMap['ysqPollNum']}</I></B></FONT></TD>
							<TD class=tablecontent align=center ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="queryPoll('7','G');">查看</a></TD>
						</TR>
						</TBODY>
					</TABLE>
				   </c:when> 
				   <c:otherwise> 
					<TABLE cellpadding=3 cellspacing=1 align=center class=tableborder width="98%" name="board">
						<TBODY>
						<TR>
							<TD colspan=4 height=31 align=center valign=middle class=tablemain >社情民意处理情况</TD>
						</TR>
						<TR height="35">
							<TD class=tabletitle align=center colspan=2 >项目</TD>
							<TD class=tabletitle align=center >数目</TD>
							<TD class=tabletitle align=center >操作</TD>
						</TR>
<%--						<TR height="35">--%>
<%--							<TD class=tablecontent align=center colspan=2>已保存社情民意</TD>--%>
<%--							<TD class=tablecontent align=center><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countPollMap['ybcPollNum']}</I></B></FONT></TD>--%>
<%--							<TD class=tablecontent align=center ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="queryPoll('3','A');">查看</a></TD>--%>
<%--						</TR>--%>
						<TR height="35">
							<TD class=tablecontent align=center colspan=2>未审查社情民意</TD>
							<TD class=tablecontent align=center><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countPollMap['wscPollNum']}</I></B></FONT></TD>
							<TD class=tablecontent align=center ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="queryPoll('3','B');">查看</a></TD>
						</TR>
						<TR height="35">
							<TD class=tablecontent align=center colspan=2>未采用社情民意</TD>
							<TD class=tablecontent align=center><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countPollMap['wcyPollNum']}</I></B></FONT></TD>
							<TD class=tablecontent align=center ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="queryPoll('3','C');">查看</a></TD>
						</TR>
						<TR height="35">
							<TD class=tablecontent align=center colspan=2>已编辑社情民意</TD>
							<TD class=tablecontent align=center><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countPollMap['ycyPollNum']}</I></B></FONT></TD>
							<TD class=tablecontent align=center ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="queryPoll('3','D');">查看</a></TD>
						</TR>
						<TR height="35">
							<TD class=tablecontent align=center colspan=2>未审核社情民意</TD>
							<TD class=tablecontent align=center><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countPollMap['wshPollNum']}</I></B></FONT></TD>
							<TD class=tablecontent align=center ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="queryPoll('3','E');">查看</a></TD>
						</TR>
						<TR height="35">
							<TD class=tablecontent align=center colspan=2>已审核社情民意</TD>
							<TD class=tablecontent align=center><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countPollMap['yshPollNum']}</I></B></FONT></TD>
							<TD class=tablecontent align=center ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="queryPoll('3','F');">查看</a></TD>
						</TR>
						<TR height="35">
							<TD class=tablecontent align=center colspan=2>已签发社情民意</TD>
							<TD class=tablecontent align=center><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countPollMap['ysqPollNum']}</I></B></FONT></TD>
							<TD class=tablecontent align=center ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="queryPoll('3','G');">查看</a></TD>
						</TR>
						<TR height="35">
							<TD class=tablecontent align=center colspan=2>已印发社情民意</TD>
							<TD class=tablecontent align=center><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countPollMap['yyfPollNum']}</I></B></FONT></TD>
							<TD class=tablecontent align=center ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="queryPoll('3','H');">查看</a></TD>
						</TR>
						
						<TR height="35">
							<TD class=tablecontent align=center colspan=2>转办中社情民意</TD>
							<TD class=tablecontent align=center><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countPollMap['zbzPollNum']}</I></B></FONT></TD>
							<TD class=tablecontent align=center ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="queryPoll('3-2','I');">查看</a></TD>
						</TR>
						<TR height="35">
							<TD class=tablecontent align=center colspan=2>未办理社情民意</TD>
							<TD class=tablecontent align=center><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countPollMap['wblPollNum']}</I></B></FONT></TD>
							<TD class=tablecontent align=center ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="queryPoll('3-2','J');">查看</a></TD>
						</TR>
						<TR height="35">
							<TD class=tablecontent align=center colspan=2>申退中社情民意</TD>
							<TD class=tablecontent align=center><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countPollMap['stzPollNum']}</I></B></FONT></TD>
							<TD class=tablecontent align=center ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="queryPoll('3-2','K');">查看</a></TD>
						</TR>
						<TR height="35">
							<TD class=tablecontent align=center colspan=2>已办理社情民意</TD>
							<TD class=tablecontent align=center><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countPollMap['yblPollNum']}</I></B></FONT></TD>
							<TD class=tablecontent align=center ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="queryPoll('3-2','L');">查看</a></TD>
						</TR>
						</TBODY>
					</TABLE>
				   </c:otherwise>  
				</c:choose>  
		  </td>
		</tr>
	</table>
	
	</center>
