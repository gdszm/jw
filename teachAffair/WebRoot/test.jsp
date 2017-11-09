<%@ page language="Java" import="java.util.*" pageEncoding="GB18030"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<meta name="keywords" content="站长,网页特效,js特效,广告代码,zzjs,zzjs.net,sky,www.zzjs.net,站长特效 网" />
<meta name="description" content="www.zzjs.net,站长特效网，站长必备js特效及广告代码。大量高质量js特效，提供高质量广告代码下载,尽在站长特效网" />
<title>文字向上滚动，sky整理收集，站长特效欢迎您</title>
<style type="text/css">
<!--
body {
 margin: 0px;
 font-size: 12px;
 color: #938C43;
 line-height: 150%;
 text-align:center;
}
a:link{color: #9D943A;font-size:12px;}
a:hover{color: #FF3300;font-size:12px;}
a:visited{color: #9D943A;font-size:12px;}
a.red:link{color: #ff0000;font-size:12px;}
a.red:hover{color: #ff0000;font-size:12px;}
a.red:visited{color: #ff0000;font-size:12px;}
#marqueeBox{background:#f7f7f7;border:1px solid silver;padding:1px;text-align:center;margin:0 auto;}
-->
</style>
</head>
<body>
<a href="http://www.zzjs.net/">站长特效网</a>,站长必备的高质量网页特效和广告代码。zzjs.net，站长js特效。<hr>
<!--欢迎来到站长特效网，我们网站收集大量高质量js特效，提供许多广告代码下载，网址：www.zzjs.net，zzjs@msn.com,用.net打造靓站-->
<h4>滚动新闻</h4>
<script language="JavaScript" type="text/javascript">
var marqueeContent=new Array();
marqueeContent[0]="<a href=http://www.zzjs.net target=_blank>站长特效网一号滚动</a>";
marqueeContent[1]="<a href=http://zzjs.net/ target=_blank>站长特效网二号滚动</a>";
marqueeContent[2]="<a href=http://zzjs.net/ target=_blank>站长特效网三号滚动</a>";
marqueeContent[3]="<a href=http://zzjs.net/ target=_blank>站长特效网四号滚动</a>";
var marqueeInterval=new Array();
var marqueeId=0;
var marqueeDelay=2000;
var marqueeHeight=20;
function initMarquee() {
 var str=marqueeContent[0];
 document.write('<div id="marqueeBox" style="overflow:hidden;width:250px;height:'+marqueeHeight+'px" onmouseover="clearInterval(marqueeInterval[0])" onmouseout="marqueeInterval[0]=setInterval(\'startMarquee()\',marqueeDelay)"><div>'+str+'</div></div>');
 marqueeId++;
 marqueeInterval[0]=setInterval("startMarquee()",marqueeDelay);
}//欢迎来到站长特效网，我们的网址是www.zzjs.net，很好记，zz站长，js就是js特效，本站收集大量高质量js代码，还有许多广告代码下载。
function startMarquee() {
 var str=marqueeContent[marqueeId];
 marqueeId++;
 if(marqueeId>=marqueeContent.length) marqueeId=0;
 if(document.getElementById("marqueeBox").childNodes.length==1) {
 var nextLine=document.createElement('DIV');
 nextLine.innerHTML=str;
 document.getElementById("marqueeBox").appendChild(nextLine);
 }
 else {
  document.getElementById("marqueeBox").childNodes[0].innerHTML=str;
  document.getElementById("marqueeBox").appendChild(document.getElementById("marqueeBox").childNodes[0]);
  document.getElementById("marqueeBox").scrollTop=0;
 }
 clearInterval(marqueeInterval[1]);
 marqueeInterval[1]=setInterval("scrollMarquee()",20);
}//欢迎来到站长特效网，我们的网址是www.zzjs.net，很好记，zz站长，js就是js特效，本站收集大量高质量js代码，还有许多广告代码下载。
function scrollMarquee() {
 document.getElementById("marqueeBox").scrollTop++;
 if(document.getElementById("marqueeBox").scrollTop%marqueeHeight==(marqueeHeight-1)){
 clearInterval(marqueeInterval[1]);
 }
}//欢迎来到站长特效网，我们的网址是www.zzjs.net，很好记，zz站长，js就是js特效，本站收集大量高质量js代码，还有许多广告代码下载。
initMarquee();
</script>
</body>
</html>