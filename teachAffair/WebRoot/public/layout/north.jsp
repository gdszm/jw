<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<style>
#noticeId {
	text-decoration: none;
	color:black;
}
#noticeId:hover {
	color:white;
	cursor: pointer;
}
.noticeClass {
	text-decoration: none;
	color:black;
}
.noticeClass:hover {
	color:white;
	cursor: pointer;
}
/* 滚动通知公告 开始 */
.scrollDiv {
	height: 40px; /* 必要元素 */
	line-height: 40px;
	/* border: #ccc 1px solid; */
	overflow: hidden; /* 必要元素 */
}

.scrollDiv li {
	height: 25px;
	padding-left: 10px;
}

.scrollDiv ul,li {
	list-style-type: none;
	margin: 0px;
}
.scrollDiv ul,.scrollDiv li {
	list-style-type: none;
	margin: 0px;
}
/* 滚动通知公告 结束 */
</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/pgwmenu.min.js"></script>

<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
//			$('#secondaryCode').combobox({    
//		    url:'${pageContext.request.contextPath}/seco!combobox.do',    
//		    valueField:'secondaryId',    
//		    textField:'secondayName'
//		    }); 
		//高亮当前系统
		$('.dh ul li').each(function(){
			$(this).removeClass();
			
			if ($(this).attr('rel')=='${modCode}'){
				$(this).addClass('selected');
			}
		});
		//管理员登录时，弹出托管到期
//		if("${sessionInfo.roleIds}"=="137abaa8-62ac-494c-8b1a-f3397de2d3ce"){
//			alert("服务器托管到期，请与技术支持18647887009联系！");
//		}
	});
	function initCombox(form){
		initCombobox(form,'sex','${pageContext.request.contextPath}/dic!combox.do?ctype=SEX');
		initCombobox(form,'nation','${pageContext.request.contextPath}/dic!combox.do?ctype=NATION');
		//initCombobox(form,'groupCode','${pageContext.request.contextPath}/dic!combox.do?ctype=GROUP');
		initCombobox(form,'partyCode','${pageContext.request.contextPath}/dic!combox.do?ctype=PARTY');
		initCombobox(form,'circleCode','${pageContext.request.contextPath}/dic!combox.do?ctype=CIRCLE');
		initCombobox(form,'eduCode','${pageContext.request.contextPath}/dic!combox.do?ctype=EDUCATION');
		initCombobox(form,'degreeCode','${pageContext.request.contextPath}/dic!combox.do?ctype=DEGREE');
		//initCombobox(form,'vocation','${pageContext.request.contextPath}/dic!combox.do?ctype=VOCATION');
		//initCombobox(form,'title','${pageContext.request.contextPath}/dic!combox.do?ctype=TITLE');
		//initCombobox(form,'job','${pageContext.request.contextPath}/dic!combox.do?ctype=JOB');
		initCombobox(form,'status','${pageContext.request.contextPath}/dic!combox.do?ctype=STATUS');
		
		initCombobox(form,'companyType','${pageContext.request.contextPath}/dic!combox.do?ctype=COMPANYTYPE');
		
		initCombobox(form,'committee','${pageContext.request.contextPath}/dic!combox.do?ctype=COMMITTEE');
	}
	
	function logout(b) {	
		$('#sessionInfoDiv').html('');
		//alert("${pageContext.request.contextPath}");
		$.post('${pageContext.request.contextPath}/user!doNotNeedSession_logout.do', function() {
			location.replace('${pageContext.request.contextPath}/');
		});
	}
	
	function edit() {
		var href,url;
		if(${sessionInfo.userGroup}=='1'){
			href='${pageContext.request.contextPath}/user!doNotNeedAuth_userComm.do';
			url='${pageContext.request.contextPath}/comm!doNotNeedAuth_userEdit.do';
			changeInfo(href, url, 550);
		}else if(${sessionInfo.userGroup}=='2'){
			href='${pageContext.request.contextPath}/user!doNotNeedAuth_userComp.do';
			url='${pageContext.request.contextPath}/comp!doNotNeedAuth_userEdit.do';
			changeInfo(href, url, 350);
		}else {
			changePass();	//修改密码窗口
		}
	}
	
	function changeInfo(href, url, h){	//修改信息窗口
		var p = dj.dialog({
			title : '资料编辑',
			href : href,
			width : 550,
			height : h,
			buttons : [ {
				text : '资料修改',
				handler : function() {
					if(${sessionInfo.userGroup}=='1'){
						var oldPhone=$('#oldtelephone').val();
						var newPhone=$('#telephone').val();
						if(oldPhone!=newPhone){
							parent.dj.messagerConfirm('请确认', '您的手机号码改变后，登陆账号为当前号码，密码为当前号码后六位！', function(r) {
								if (r) {
									$('#commForm').form('submit', {
										url : url,
										success : function(d) {
											var json = $.parseJSON(d);
											if (json.success) {
												p.dialog('close');
											}
											dj.messagerShow({
												msg : json.msg,
												title : '提示'
											});
										}
									});
								}
								
							});
						
						}else{
							$('#commForm').form('submit', {
								url : url,
								success : function(d) {
									var json = $.parseJSON(d);
									if (json.success) {
										p.dialog('close');
									}
									dj.messagerShow({
										msg : json.msg,
										title : '提示'
									});
								}
							});
						
						}
						
					} else {
						$('#commForm').form('submit', {
								url : url,
								success : function(d) {
									var json = $.parseJSON(d);
									if (json.success) {
										p.dialog('close');
									}
									dj.messagerShow({
										msg : json.msg,
										title : '提示'
									});
								}
							});
					}
				}
			} ],
			toolbar : [{
				text : '密码修改',
				iconCls : 'icon-pass',
				handler : function() {
					changePass();	//修改密码窗口
				}
			}],
			onLoad : function() {
				initCombox('commForm');
			}
		});
	}
	
	function changePass(){
		var s=dj.dialog({
			title : '密码修改',
			href : '${pageContext.request.contextPath}/user!doNotNeedAuth_userPass.do',
			width : 400,
			height : 200,
			buttons : [ {
				text : '密码修改',
				handler : function() {
					$('#passForm').form('submit', {
						url : '${pageContext.request.contextPath}/user!doNotNeedAuth_changePass.do',
						success : function(d) {
							var json = $.parseJSON(d);
							if (json.success) {
								s.dialog('close');
							}
							dj.messagerShow({
								msg : json.msg,
								title : '提示'
							});
						}
					});
				}
			}]
		});
	}
	
	function showUserInfo() {
		var p = parent.dj.dialog({
			title : '用户信息',
			href : '${pageContext.request.contextPath}/user!doNotNeedAuth_userInfo.do',
			width : 490,
			height : 285,
			buttons : [ {
				text : '修改密码',
				handler : function() {
					var f = p.find('form');
					f.form('submit', {
						url : '${pageContext.request.contextPath}/user!doNotNeedAuth_editUserInfo.do',
						success : function(d) {
							var json = $.parseJSON(d);
							if (json.success) {
								p.dialog('close');
							}
							parent.dj.messagerShow({
								msg : json.msg,
								title : '提示'
							});
						}
					});
				}
			} ],
			onLoad : function() {
				var authIds = p.find('ul');
				var authIdsTree = authIds.tree({
					url : '${pageContext.request.contextPath}/auth!doNotNeedSession_treeRecursive.do',
					lines : true,
					checkbox : true,
					onLoadSuccess : function(node, data) {
						var f = p.find('form');
						var ids = f.find('input[name=authIds]').val();
						var idList = dj.getList(ids);
						if (idList.length > 0) {
							for ( var i = 0; i < idList.length; i++) {
								var n = authIdsTree.tree('find', idList[i]);
								authIdsTree.tree('check', n.target);
							}
						}
						authIdsTree.unbind();
					}
				});
			}
		});
	}
	function setjc(){	
		//alert("123abc"+"${pageContext.request.contextPath}");
		/*$('#set_jc').combobox({    
		    url:'${pageContext.request.contextPath}/seco!combobox.do',    
		    valueField:'secondaryId',    
		    textField:'secondayName',
		    multiple:true
		 });*/ 
		$('#win_jc').window('open');
	}
	function savesetjc(){
		$('#jc_setForm').form('submit', {
			url : '${pageContext.request.contextPath}/seco!doNotNeedAuth_change.do',
			success : function(d) {
				var json = $.parseJSON(d);
				if (json.success) {
					$('#win_jc').window('close');
					location.reload();
					
				}
				parent.dj.messagerShow({
					msg : json.msg,
					title : '提示'
				});
			}
		});
		
	}
	//查看通知公告
	function viewNotice(id) {
			var p = dj.dialog({
				title : '通知详情',
				href : '${pageContext.request.contextPath}/notice!get.do?id='+id,
				maximized:true,
				maximizable:true,
				width : 800,
				height : 600,
				onLoad : function() {
					//initCombox('noticeViewForm');
				},
				buttons : [ {
					text: '关闭 ', 
					iconCls:'icon-cancel',
					handler: function() { 
					p.dialog('close'); 
					} 
				}]
				
			});
	
	}
</script>
<script>
function AutoScroll(obj) {
	$(obj).find("ul:first").animate({
		marginTop : "-25px"
	}, 500, function() {
		$(this).css({
			marginTop : "0px"
		}).find("li:first").appendTo(this);
	});
}
//弹出帮助
function openHelp() {
		var p = dj.dialog({
			title : '系统帮助',
			href : '${pageContext.request.contextPath}/public/layout/portal/portalHelp.jsp',
			width : 400,
			height : 300,
			onLoad : function() {
				initCombox('noticeViewForm');
			},
			buttons : [ {
				text: '关闭 ', 
				iconCls:'icon-cancel',
				handler: function() { 
				p.dialog('close'); 
				} 
			}]
			
		});
}
$(document).ready(function() {
	setInterval('AutoScroll("#scrollDiv_keleyi_com")', 3000);
});
</script>
<div id="zui">
	<div id="top">
		  <table width="100%" height="80" border="0" cellpadding="0" cellspacing="0" bordercolor="#cf0101">
		    <tr>
		      <td colspan="2"><div id="logo"><img src="${pageContext.request.contextPath}/style/images/logo.png" width="63" height="53" /></div>
		          <div id="weizi">
		            <table width="100%" border="0" cellpadding="0" cellspacing="0">
		              <tr>
		                <td><img src="${pageContext.request.contextPath}/style/images/wenzi.png" width="478" height="44" /></td>
		                <td><table width="100%" border="0" align="right" cellpadding="0" cellspacing="0">
		                    <tr>
		                      <td>&nbsp;</td>
		                      <td>&nbsp;</td>
		                    </tr>
		                    <tr>
		                      <td id="zi">
		                      <c:choose>  
		                      	  <c:when test="${sessionInfo.userGroup=='1'}"> 
		                      	  		<c:if test="${sessionSeco.period=='0'}">
								   			<div id="zi" >${sessionSeco.secondayName}会议</div>
								   		</c:if>
								   		<c:if test="${sessionSeco.period=='1'}">
								   			<div id="zi" >${fn:substring(sessionSeco.secondayName,0,fn:indexOf(sessionSeco.secondayName,'届')+1)}(${sessionSeco.year}平时)</div>
								   		</c:if>
								   </c:when>  
								   <c:when test="${sessionInfo.userGroup=='2'}"> 
										<c:if test="${sessionSeco.period=='0'}">
								   			<div id="zi" >${sessionSeco.secondayName}会议</div>
								   		</c:if>
								   		<c:if test="${sessionSeco.period=='1'}">
								   			<div id="zi" >${fn:substring(sessionSeco.secondayName,0,fn:indexOf(sessionSeco.secondayName,'届')+1)}(${sessionSeco.year}平时)</div>
								   		</c:if>
								   </c:when> 
								    <c:when test="${sessionInfo.userGroup=='4'}"> 
										<c:if test="${sessionSeco.period=='0'}">
								   			<div id="zi" >${sessionSeco.secondayName}会议</div>
								   		</c:if>
								   		<c:if test="${sessionSeco.period=='1'}">
								   			<div id="zi" >${fn:substring(sessionSeco.secondayName,0,fn:indexOf(sessionSeco.secondayName,'届')+1)}(${sessionSeco.year}平时)</div>
								   		</c:if>
								   </c:when> 
								    <c:when test="${sessionInfo.userGroup=='5'}"> 
										<c:if test="${sessionSeco.period=='0'}">
								   			<div id="zi" >${sessionSeco.secondayName}会议</div>
								   		</c:if>
								   		<c:if test="${sessionSeco.period=='1'}">
								   			<div id="zi" >${fn:substring(sessionSeco.secondayName,0,fn:indexOf(sessionSeco.secondayName,'届')+1)}(${sessionSeco.year}平时)</div>
								   		</c:if>
								   </c:when> 
								    <c:when test="${sessionInfo.userGroup=='6'}"> 
										<c:if test="${sessionSeco.period=='0'}">
								   			<div id="zi" >${sessionSeco.secondayName}会议</div>
								   		</c:if>
								   		<c:if test="${sessionSeco.period=='1'}">
								   			<div id="zi" >${fn:substring(sessionSeco.secondayName,0,fn:indexOf(sessionSeco.secondayName,'届')+1)}(${sessionSeco.year}平时)</div>
								   		</c:if>
								   </c:when> 
								    <c:when test="${sessionInfo.userGroup=='7'}"> 
										<c:if test="${sessionSeco.period=='0'}">
								   			<div id="zi" >${sessionSeco.secondayName}会议</div>
								   		</c:if>
								   		<c:if test="${sessionSeco.period=='1'}">
								   			<div id="zi" >${fn:substring(sessionSeco.secondayName,0,fn:indexOf(sessionSeco.secondayName,'届')+1)}(${sessionSeco.year}平时)</div>
								   		</c:if>
								   </c:when> 
								   <c:otherwise> 
									   <a href="javascript:void(0)" plain="true" onclick="setjc();">
											<c:if test="${sessionSeco.period=='0'}">
									   			<div id="zi" >${sessionSeco.secondayName}会议</div>
									   		</c:if>
									   		<c:if test="${sessionSeco.period=='1'}">
									   			<div id="zi" >${fn:substring(sessionSeco.secondayName,0,fn:indexOf(sessionSeco.secondayName,'届')+1)}(${sessionSeco.year}平时)</div>
									   		</c:if>
										</a>
								   </c:otherwise>
								</c:choose>  
		                      
		                      </td>
		                      <td id="zi">&nbsp;</td>
		                    </tr>
		                </table></td>
		              </tr>
		            </table>
		          </div></td>
		      <td width="28%" colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		        <tr>
		          <td colspan="2"><c:choose>  
						   <c:when test="${sessionInfo.userGroup=='1' && sessionComm.groupCode=='1'}"> 
						   	 	<div id="dl"><img src="${pageContext.request.contextPath}/style/images/dl22.png" width="30" height="33" /></div><div id="zi" id="hyn"><c:if test="${sessionInfo.userId != null}">${sessionInfo.realName} 委员，欢迎您！</c:if></div>
						   </c:when>  
						   <c:otherwise> 
						   		<div id="dl"><img src="${pageContext.request.contextPath}/style/images/dl22.png" width="30" height="33" /></div><div id="zi" id="hyn"><c:if test="${sessionInfo.userId != null}">${sessionInfo.realName}，欢迎您！</c:if></div>
						   </c:otherwise>  
						</c:choose>
				  </td>
		        </tr>
		        <tr>
		          <td width="30%">
		          	<a href="javascript:void(0)" style="text-decoration : none"  plain="true" onclick="edit();">
		          		<div id="mmxg"><img src="${pageContext.request.contextPath}/style/images/xg.png" width="20" height="22" /></div>
						<c:choose>  
						   <c:when test="${sessionInfo.userGroup=='1'}"> 
						   	<div id="zi" >个人资料</div>
						   </c:when>  
						   <c:when test="${sessionInfo.userGroup=='2'}"> 
						   	<div id="zi" >个人资料</div>
						   </c:when> 
						   <c:otherwise> 
						   	<div id="zi" >密码修改</div>
						   </c:otherwise>  
						</c:choose>  
					</a>				  
				  </td>
		          <td width="70%">
					<a href="javascript:void(0)" style="text-decoration : none" plain="true" onclick="logout(1);">
						<div id="mmxg"><img src="${pageContext.request.contextPath}/style/images/dui.png" width="30" height="30" /></div>
						<div id="zi" >安全退出</div>
					</a>
					</td>
		        </tr>
		      </table></td>
		    </tr>
		  </table>
	</div>
	<div class="dh">
		<ul class="pgwMenuCustom">
		<c:forEach var="item" items="${sessionInfo.menus}" >
			<c:if test="${item.pid=='0'}">
				<li rel="${item.cid}"><a href="${pageContext.request.contextPath}/user!doNotNeedSession_index.do?modCode=${item.cid}">${item.cname}</a></li>
				
		    </c:if>
	    </c:forEach>
	    	<li><a href="javascript:void(0)" onclick="openHelp();">帮助</a></li>
		</ul>
		<div class="Font" style="font-size:14px;">
			<a id="noticeId" href="javascript:void(0)" onclick="addTab('通知查询','/notice!noticeQuery.do','');">通知公告:</a>
		</div>
		<div id="scrollDiv_keleyi_com" class="scrollDiv">
		    <ul>
		        <c:forEach var="item" items="${noticeList}" >
					<li><a  class="noticeClass" href="javascript:void(0)" onclick="viewNotice(${item.id});" style="font-size:14px;">${item.title}</a></li>
			    </c:forEach>
		    </ul>
		</div>
		<div style="clear: both;"></div>
	</div>
	
</div>
<div id="win_jc" class="easyui-window" data-options="title:'届次切换',iconCls:'icon-column',modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true" style="width:400px;height:140px;padding:5px;">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
			<center>
			<form id="jc_setForm">
			<input type="hidden" name="status" value="1"/>
			<table id="jc_setTable">				
				<tr height="25">
					<td nowrap>
							当前届次：
					</td>
					<td>
						<input name="secondaryCode" id="secondaryCode" data-options="required:true" style="width:260px"/>
					</td>
				</tr>
			</table>
			</form>
			</center>
		</div>
		<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;height:40px">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="javascript:savesetjc();">确定</a>
			<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#win_jc').window('close');">取消</a>
		</div>
	</div>
</div>		
<div id="layout_north_pfMenu" style="width: 120px; display: none;">
	<div onclick="dj.changeTheme('default');">默认</div>
	<div onclick="dj.changeTheme('gray');">灰色</div>
	<div onclick="dj.changeTheme('metro');">白色</div>
	<div onclick="dj.changeTheme('cupertino');">浅蓝</div>
	<div onclick="dj.changeTheme('dark-hive');">黑色</div>
	<div onclick="dj.changeTheme('pepper-grinder');">银白</div>
	<div onclick="dj.changeTheme('sunny');">阳光</div>
</div>
