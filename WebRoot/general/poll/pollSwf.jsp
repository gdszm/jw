<%@ page language= "java" contentType ="text/html; charset=UTF-8"  
    pageEncoding="UTF-8" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/jquery.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/flexpaper_flash.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/flexpaper_flash_debug.js"></script>  
 
<style type="text/css" media="screen">  
                   html, body   { height:100%;}  
                   body { margin :0px; padding:0px; overflow:auto ; text-align:center;}    
                   #flashContent { display :none; }  
        </style>  
</head>  
<body>  
	<center>
        <div style="text-align:center;width:100%; margin-left:0;margin-right:0;" id= "c" align="center" > 
              <a id= "viewerPlaceHolder"  style="text-align:center; width :1000px;height:700px;display:block;margin-left:auto;margin-right:auto;" ></a>  
               
              <script type="text/javascript" >  
                         var fp = new FlexPaperViewer(   
                                     '${pageContext.request.contextPath}/FlexPaperViewer',  
                                     'viewerPlaceHolder', { config : {  
                                     SwfFile : escape( '${pageContext.request.contextPath}/wordfile/<%=request.getParameter("swffile")%>'),  
                                      Scale : 0.6, 
				                         ZoomTransition : 'easeOut',//变焦过渡
				                         ZoomTime : 0.5,
				                         ZoomInterval : 0.2,//缩放滑块-移动的缩放基础[工具栏]
				                         FitPageOnLoad : true,//自适应页面
				                         FitWidthOnLoad : true,//自适应宽度
				                         FullScreenAsMaxWindow : false,//全屏按钮-新页面全屏[工具栏]
				                         ProgressiveLoading : false,//分割加载
				                         MinZoomSize : 0.2,//最小缩放
				                         MaxZoomSize : 3,//最大缩放
				                         SearchMatchAll : true,
				                         InitViewMode : 'Portrait',//初始显示模式(SinglePage,TwoPage,Portrait)
				                          
				                         ViewModeToolsVisible : true,//显示模式工具栏是否显示
				                         ZoomToolsVisible : true,//缩放工具栏是否显示
				                         NavToolsVisible : true,//跳页工具栏
				                         CursorToolsVisible : false,
				                         SearchToolsVisible : true,
				                            PrintPaperAsBitmap:false,
				                         localeChain: 'zh_CN'
                                     }});  
              </script>              
        </div>  
      </center>
</body>  
</html>  