<%--会议发言首页--%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.tlzn.util.base.Constants"%>
<script type="text/javascript">
function querySpeech(group,status){
	var url="";
 	if(group=='1'){
 	//委员
 		url='/speech!speechOwn.do?statusType='+status;
 		addTab('我的发言',url,'icon-cx');
 	} else{
 		url='/speech!speech.do?statusType='+status;
 		addTab('发言管理',url,'icon-cx');
 	}
}
</script>
<style>
#wyTableId tr td,#glyTableId tr td{
  text-align: center;
}
</style>
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
				   	<TABLE id="wyTableId" cellpadding=3 cellspacing=1 align=center class=tableborder width="98%" name="board" boder='1'> 
						<TBODY>
						<TR>
							<TD colspan=4 height=31 align=center valign=middle class=tablemain>我的会议发言情况</TD>
						</TR>
						<TR height="35">
							<TD class=tabletitle align=center colspan=2 width="30%">项目</TD>
							<TD class=tabletitle align=center >数目</TD>
							<TD class=tabletitle align=center >操作</TD>
						</TR>
						<TR height="35">
							<TD class=tablecontent colspan=2>已保存会议发言</TD>
							<TD class=tablecontent><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countSpeechMap['ybcSpeechNum']}</I></B></FONT></TD>
							<TD class=tablecontent ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="querySpeech('1','A');">查看</a></TD>
						</TR>
						<TR height="35">
							<TD class=tablecontent colspan=2>未审查会议发言</TD>
							<TD class=tablecontent><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countSpeechMap['wscSpeechNum']}</I></B></FONT></TD>
							<TD class=tablecontent ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="querySpeech('1','B');">查看</a></TD>
						</TR>
						<TR height="35">
							<TD class=tablecontent width="15%" rowspan="3">已审查会议发言</TD>
							<TD class=tablecontent>书面发言</TD>
							<TD class=tablecontent><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countSpeechMap['ysc_smfy_Num']}</I></B></FONT></TD>
							<TD class=tablecontent ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="querySpeech('1','C');">查看</a></TD>
						</TR>
						<TR height="35">
							<TD class=tablecontent>口头发言</TD>
							<TD class=tablecontent><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countSpeechMap['ysc_ktfy_Num']}</I></B></FONT></TD>
							<TD class=tablecontent ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="querySpeech('1','D');">查看</a></TD>
						</TR>
						<TR height="35">
							<TD class=tablecontent>转弃发言</TD>
							<TD class=tablecontent><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countSpeechMap['ysc_zq_Num']}</I></B></FONT></TD>
							<TD class=tablecontent ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="querySpeech('1','E');">查看</a></TD>
						</TR>
						<TR height="35">
							<TD class=tablecontent rowspan=2>已定稿会议发言</TD>
							<TD class=tablecontent>书面发言</TD>
							<TD class=tablecontent><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countSpeechMap['dg_smfy_Num']}</I></B></FONT></TD>
							<TD class=tablecontent ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="querySpeech('1','F');">查看</a></TD>
						</TR>
						<TR height="35">
							<TD class=tablecontent>口头发言</TD>
							<TD class=tablecontent><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countSpeechMap['dg_ktfy_Num']}</I></B></FONT></TD>
							<TD class=tablecontent ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="querySpeech('1','G');">查看</a></TD>
						</TR>
						</TBODY>
					</TABLE>
				   </c:when> 
				   <c:otherwise> 
					<TABLE cellpadding=3 cellspacing=1 align=center class=tableborder width="98%" name="board">
						<TBODY>
						<TR>
							<TD colspan=4 height=31 align=center valign=middle class=tablemain>会议发言情况</TD>
						</TR>
						<TR height="35">
							<TD class=tabletitle align=center colspan=2 width="30%">项目</TD>
							<TD class=tabletitle align=center >数目</TD>
							<TD class=tabletitle align=center >操作</TD>
						</TR>
						<TR height="35">
							<TD class=tablecontent colspan=2>未审查会议发言</TD>
							<TD class=tablecontent><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countSpeechMap['wscSpeechNum']}</I></B></FONT></TD>
							<TD class=tablecontent ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="querySpeech('3','B');">查看</a></TD>
						</TR>
						<TR height="35">
							<TD class=tablecontent width="15%" rowspan="3">已审查会议发言</TD>
							<TD class=tablecontent>书面发言</TD>
							<TD class=tablecontent><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countSpeechMap['ysc_smfy_Num']}</I></B></FONT></TD>
							<TD class=tablecontent ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="querySpeech('3','C');">查看</a></TD>
						</TR>
						<TR height="35">
							<TD class=tablecontent>口头发言</TD>
							<TD class=tablecontent><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countSpeechMap['ysc_ktfy_Num']}</I></B></FONT></TD>
							<TD class=tablecontent ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="querySpeech('3','D');">查看</a></TD>
						</TR>
						<TR height="35">
							<TD class=tablecontent>转弃发言</TD>
							<TD class=tablecontent><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countSpeechMap['ysc_zq_Num']}</I></B></FONT></TD>
							<TD class=tablecontent ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="querySpeech('3','E');">查看</a></TD>
						</TR>
						<TR height="35">
							<TD class=tablecontent rowspan=2>已定稿会议发言</TD>
							<TD class=tablecontent>书面发言</TD>
							<TD class=tablecontent><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countSpeechMap['dg_smfy_Num']}</I></B></FONT></TD>
							<TD class=tablecontent ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="querySpeech('3','F');">查看</a></TD>
						</TR>
						<TR height="35">
							<TD class=tablecontent>口头发言</TD>
							<TD class=tablecontent><FONT COLOR="#FF3333" face="Arial" size="3"><B><I>${countSpeechMap['dg_ktfy_Num']}</I></B></FONT></TD>
							<TD class=tablecontent ><a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="querySpeech('3','G');">查看</a></TD>
						</TR>
						</TBODY>
					</TABLE>
				   </c:otherwise>  
				</c:choose>  
		  </td>
		</tr>
	</table>
	
	</center>