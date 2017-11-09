<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" charset="utf-8">
	var centerTabs;
	var tabsMenu;
	$(function() {
		tabsMenu = $('#tabsMenu').menu({
			onClick : function(item) {
				var curTabTitle = $(this).data('tabTitle');
				var type = $(item.target).attr('type');

				if (type === 'refresh') {
					refreshTab(curTabTitle);
					return;
				}

				if (type === 'close') {
					var t = centerTabs.tabs('getTab', curTabTitle);
					if (t.panel('options').closable) {
						centerTabs.tabs('close', curTabTitle);
					}
					return;
				}

				var allTabs = centerTabs.tabs('tabs');
				var closeTabsTitle = [];

				$.each(allTabs, function() {
					var opt = $(this).panel('options');
					if (opt.closable && opt.title != curTabTitle && type === 'closeOther') {
						closeTabsTitle.push(opt.title);
					} else if (opt.closable && type === 'closeAll') {
						closeTabsTitle.push(opt.title);
					}
				});

				for ( var i = 0; i < closeTabsTitle.length; i++) {
					centerTabs.tabs('close', closeTabsTitle[i]);
				}
			}
		});

		centerTabs = $('#centerTabs').tabs({
			fit : true,
			border : false,
			onContextMenu : function(e, title) {
				e.preventDefault();
				tabsMenu.menu('show', {
					left : e.pageX,
					top : e.pageY
				}).data('tabTitle', title);
			}
		});
	});
	function addTab(cname,curl,ciconCls) {
		if (centerTabs.tabs('exists', cname)) {
			centerTabs.tabs('select', cname);
			var tab =centerTabs.tabs('getSelected'); 
			centerTabs.tabs('update', {
				tab: tab,
				options: {
					title: cname,
					content: '<iframe src="${pageContext.request.contextPath}' + curl + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>'  // 新内容的URL
				}
			});
			
		} else {
			if (curl && curl.length > 0) {
				/*if (curl.indexOf('!druid.do') < 0) {/*数据源监控页面不需要开启等待提示
					$.messager.progress({
						text : '页面加载中....',
						interval : 100
					});
					window.setTimeout(function() {
						try {
							$.messager.progress('close');
						} catch (e) {
						}
					}, 5000);
				}*/
				centerTabs.tabs('add', {
					title : cname,
					closable : true,
					iconCls : ciconCls,
					content : '<iframe src="${pageContext.request.contextPath}' + curl + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
					tools : [ {
						iconCls : 'icon-mini-refresh',
						handler : function() {
							refreshTab(cname);
						}
					} ]
				});
			} else {
				centerTabs.tabs('add', {
					title : cname,
					closable : true,
					iconCls : ciconCls,
					content : '<iframe src="error/err.jsp" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
					tools : [ {
						iconCls : 'icon-mini-refresh',
						handler : function() {
							refreshTab(cname);
						}
					} ]
				});
			}
		}
	}
	function refreshTab(title) {
		var tab = centerTabs.tabs('getTab', title);
		centerTabs.tabs('update', {
			tab : tab,
			options : tab.panel('options')
		});
	}
	function closetab(){
	// if ($('#tabs').tabs('exists', title)){
	// 	alert("1-"+title);
	// 	$('#tabs').tabs('close', title);
	// }else{
	// 	alert("2-"+title);
	// }
	var tab = $('#centerTabs').tabs('getSelected');
	var s = tab.panel('options').title;
	$('#centerTabs').tabs('close', s);
}
</script>

<%--各模块首页显示--%>
<div id="centerTabs">

<%--<c:forEach var="mainPage" items="${sessionInfo.menus}" varStatus="status">--%>
<%--	<c:choose>--%>
<%--		<c:when test="${modCode==mainPage.cid}">--%>
<%--			<div title="首页" data-options="border:false,href:'${pageContext.request.contextPath}${mainPage.curl}'" ></div>--%>
<%--		</c:when>--%>
<%--	</c:choose>--%>
<%--</c:forEach>--%>

<%--	<div title="首页" data-options="border:false,href:'${pageContext.request.contextPath}/prop!doNotNeedAuth_propCount.do'" ></div>--%>
</div>

<div id="tabsMenu" style="width: 120px;display:none;">
	<div type="refresh">刷新</div>
	<div class="menu-sep"></div>
	<div type="close">关闭</div>
	<div type="closeOther">关闭其他</div>
	<div type="closeAll">关闭所有</div>
</div>