<%@ page language="Java" import="java.util.*" pageEncoding="GB18030"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<meta name="keywords" content="վ��,��ҳ��Ч,js��Ч,������,zzjs,zzjs.net,sky,www.zzjs.net,վ����Ч ��" />
<meta name="description" content="www.zzjs.net,վ����Ч����վ���ر�js��Ч�������롣����������js��Ч���ṩ����������������,����վ����Ч��" />
<title>�������Ϲ�����sky�����ռ���վ����Ч��ӭ��</title>
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
<a href="http://www.zzjs.net/">վ����Ч��</a>,վ���ر��ĸ�������ҳ��Ч�͹����롣zzjs.net��վ��js��Ч��<hr>
<!--��ӭ����վ����Ч����������վ�ռ�����������js��Ч���ṩ�����������أ���ַ��www.zzjs.net��zzjs@msn.com,��.net������վ-->
<h4>��������</h4>
<script language="JavaScript" type="text/javascript">
var marqueeContent=new Array();
marqueeContent[0]="<a href=http://www.zzjs.net target=_blank>վ����Ч��һ�Ź���</a>";
marqueeContent[1]="<a href=http://zzjs.net/ target=_blank>վ����Ч�����Ź���</a>";
marqueeContent[2]="<a href=http://zzjs.net/ target=_blank>վ����Ч�����Ź���</a>";
marqueeContent[3]="<a href=http://zzjs.net/ target=_blank>վ����Ч���ĺŹ���</a>";
var marqueeInterval=new Array();
var marqueeId=0;
var marqueeDelay=2000;
var marqueeHeight=20;
function initMarquee() {
 var str=marqueeContent[0];
 document.write('<div id="marqueeBox" style="overflow:hidden;width:250px;height:'+marqueeHeight+'px" onmouseover="clearInterval(marqueeInterval[0])" onmouseout="marqueeInterval[0]=setInterval(\'startMarquee()\',marqueeDelay)"><div>'+str+'</div></div>');
 marqueeId++;
 marqueeInterval[0]=setInterval("startMarquee()",marqueeDelay);
}//��ӭ����վ����Ч�������ǵ���ַ��www.zzjs.net���ܺüǣ�zzվ����js����js��Ч����վ�ռ�����������js���룬���������������ء�
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
}//��ӭ����վ����Ч�������ǵ���ַ��www.zzjs.net���ܺüǣ�zzվ����js����js��Ч����վ�ռ�����������js���룬���������������ء�
function scrollMarquee() {
 document.getElementById("marqueeBox").scrollTop++;
 if(document.getElementById("marqueeBox").scrollTop%marqueeHeight==(marqueeHeight-1)){
 clearInterval(marqueeInterval[1]);
 }
}//��ӭ����վ����Ч�������ǵ���ַ��www.zzjs.net���ܺüǣ�zzվ����js����js��Ч����վ�ռ�����������js���룬���������������ء�
initMarquee();
</script>
</body>
</html>