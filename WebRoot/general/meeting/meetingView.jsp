<%@ page language="java" pageEncoding="UTF-8" %>

		<DIV class="easyui-layout" data-options="fit:true"
			class="cs-home-remark">
			<div data-options="region:'center',border:false"
				style="padding: 10px; border: 1px solid #ccc;"
				class="cs-home-remark">
				<center>
					<table border="0" align="center" cellpadding="0"
						cellspacing="0" style="width:1024px;">
						<tr>
							<td>
								<table class="tableborder" id="formTable" sizset="false">
									<tbody sizset="false">
										<tr height="30">
											<td height="45" colspan="4" align="middle" nowrap="nowrap"
												class="tablemain">
											   	<div align="center"><strong>政协巴彦淖尔市${obj.secondaryYear}年度会议</strong></div>
											</td>
										</tr>
										<tr height="25">
											<td height="25"  width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												会议名称：
											</td>
											<td height="25" colspan="3" width="240px" class="tablecontent"
												sizset="false" align="left">
												${obj.meetName}
											</td>
											
										</tr>
										<tr height="25">
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												会议简称：
											</td>
											<td height="25" width="240px" class="tablecontent"
												sizset="false" align="left">
												${obj.shortName}
											</td>
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度：
											</td>
											<td height="25" width="240px" class="tablecontent"
												sizset="false" align="left">
												${obj.secondaryYear}
											</td>
											
										</tr>
										<tr>
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												会议类型：
											</td>
											<td height="25"  width="550px" align="left"
												class="tablecontent">
												${obj.meetTypeName}
											</td>
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												会议时间：
											</td>
											<td height="25"  width="550px" align="left"
												class="tablecontent">
												${obj.beginTime}至${obj.endTime}
											</td>
										</tr>
										<tr height="25">
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												会议地点：
											</td>
											<td height="25" width="240px" class="tablecontent"
												sizset="false" align="left" colspan="3">
												${obj.address}
											</td>
										</tr>
										<tr height="25">
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												发布时间：
											</td>
											<td height="25" width="240px" class="tablecontent"
												sizset="false" align="left">
											${obj.pubDate}
											</td>
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												发布单位：
											</td>
											<td height="25" width="240px" class="tablecontent"
												sizset="false" align="left">
												${obj.pubDepdName}
											</td>
										</tr>
										<tr height="25">
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												承办单位：
											</td>
											<td height="25" width="240px" class="tablecontent"
												sizset="false" align="left">
												${obj.depName}
											</td>
											<td height="25" width="80px" align="middle" nowrap="nowrap"
												class="tablelabel">
												状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：
											</td>
											<td height="25" width="240px" class="tablecontent"
												sizset="false" align="left" >
												${obj.statusName}
											</td>
										</tr>
										<tr height="25" sizset="false" >
							              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">邀请人数：</td>
							              <td height="25" width="240px" class="tablecontent" sizset="false"  align="left">${obj.total}</td>
							              <td height="25" width="80px" nowrap="nowrap" class="tablelabel">参会人数：</td>
							              <td height="25" width="240px" class="tablecontent" sizset="false"  align="left">${obj.attendance}
							              </td>
							            </tr>
							            
										<tr>
											<td colspan="4" class="tablecontent" width="100%">
												<div class="easyui-tabs" style="width:1024px;height:580px">
													<div title="参会情况" style="padding:3px;height:580px;">
														<!-- 表格 -->
														<table id="situationgrid">
														</table>
													</div>
													<div title="会议内容"
														style="padding: 3px">
														<div style="padding:12px;line-height:25pt;" class="tanr-class" id="tanr">
															${obj.content}
														</div>
													</div>
													
												</div>

											</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
					</table>
				</center>
			</div>
		</div>
