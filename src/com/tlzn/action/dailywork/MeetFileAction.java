package com.tlzn.action.dailywork;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.dailywork.FileData;
import com.tlzn.service.dailywork.MeetFileServiceI;
import com.tlzn.util.base.Util;

@Action(value = "meetFile", results = {
		@Result(name = "meetFileManage", location = "/general/dailywork/meetFileManage.jsp"),
		
		@Result(name = "meetFileView", location = "/general/dailywork/meetFileView.jsp"),
		@Result(name = "meetFileAdd", location = "/general/dailywork/meetFileAdd.jsp"),
		@Result(name = "meetFileEdit", location = "/general/dailywork/meetFileEdit.jsp"),
		
		@Result(name = "meetFileQuery", location = "/general/dailywork/meetFileQuery.jsp"),
})
public class MeetFileAction extends BaseAction implements ModelDriven<FileData>{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(MeetFileAction.class);
	
	private MeetFileServiceI meetFileService;
	
	private FileData meetFile=new FileData();
	public FileData getModel() {
		return meetFile;
	}
	
	/**
	 * 会议文件管理页面
	 */
	public String meetFileManage(){
		return "meetFileManage";
	}
	/**
	 * 会议文件查询页面
	 */
	public String meetFileQuery(){
		return "meetFileQuery";
	}
	/**
	 * 会议文件详细页面
	 */
	public String meetFileView(){
		return "meetFileView";
	}
	/**
	 * 会议文件新增页面
	 */
	public String meetFileAdd(){
//		Object obj=request.getAttribute("fromtype");
//		String fromtype=obj==null?"":(String)obj;
//		meetFile.setFromtype(fromtype);
//		request.setAttribute("meetFile",meetFile);
		return "meetFileAdd";
	}
	/**
	 * 
	 * 查询会议文件列表
	 */
	public void doNotNeedAuth_datagrid() {
		try {
//			String secondaryId=((Seco) httpSession.getAttribute("sessionSeco")).getSecondaryId();
//			meetFile.setSecondaryId(secondaryId);
			DataGrid dataGrid = meetFileService.datagrid(meetFile);
			
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询会议文件列表
	 */
	public void datagrid() {
		try {
//			String secondaryId=((Seco) httpSession.getAttribute("sessionSeco")).getSecondaryId();
//			meetFile.setSecondaryId(secondaryId);
			DataGrid dataGrid = meetFileService.datagrid(meetFile);
			
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询会议文件列表(通知查询模块)
	 */
	public void datagridQuery() {
		try {
			DataGrid dataGrid = meetFileService.datagrid(meetFile);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询 会议文件单条（跳转 会议文件详细页面）
	 */
	public String get() {
		try {
			String id=meetFile.getDataId();
			if(!Util.getInstance().isEmpty(id)){
					FileData meetFile =meetFileService.get(id);
					request.setAttribute("meetFile",meetFile);
					String atts= meetFile.getAtts();
					List<String> attList = null;
					if(!Util.getInstance().isEmpty(atts)){
						attList = new ArrayList<String>();
						String attArry[] = atts.split(",");
						for (String att : attArry) {
							attList.add(att);
						}
						request.setAttribute("attList",attList);
					}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "meetFileView";
	}
	/**
	 * 会议文件修改页面
	 */
	public String meetFileEdit(){
		try {
			String id=meetFile.getDataId();
			if(!Util.getInstance().isEmpty(id)){
					FileData meetFile =meetFileService.get(id);
					request.setAttribute("meetFile",meetFile);
					
					String atts= meetFile.getAtts();
					List<String> attList = null;
					if(!Util.getInstance().isEmpty(atts)){
						attList = new ArrayList<String>();
						String attArry[] = atts.split(",");
						for (String att : attArry) {
							attList.add(att);
						}
						request.setAttribute("attList",attList);
					}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "meetFileEdit";
	}
	/**
	 * 
	 * 保存会议文件
	 */
	public void save() {
		Json j = new Json();
		try {
			System.out.println("content==="+meetFile.getContent());
			if(Util.getInstance().isEmpty(meetFile.getDataId())){
				j.setObj(meetFileService.save(meetFile,httpSession));
			} else {
				j.setObj(meetFileService.saveForUpate(meetFile,httpSession));
			}
			j.setSuccess(true);
			j.setMsg("保存成功!");
		} catch (Exception e) {
			j.setMsg("保存失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	/**
	 * 
	 * 更新或新增会议文件
	 */
	public void upDateOrAdd() {
		Json j = new Json();
		try {
			j.setObj(meetFileService.upDateOrAdd(meetFile,httpSession));
			j.setSuccess(true);
			j.setMsg("操作成功!");
		} catch (Exception e) {
			j.setMsg("操作失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	
	/**
	 * 
	 * 修改会议文件
	 */
	public void edit() {
		Json j = new Json();
		try {
			meetFileService.edit(meetFile);
			j.setSuccess(true);
			j.setMsg("修改成功!");
		} catch (Exception e) {
			j.setMsg("修改失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	/**
	 * 删除会议文件
	 */
	public void delete() {
		Json j = new Json();
		try {
			meetFileService.del(meetFile);
			j.setSuccess(true);
			j.setMsg("删除成功!");
		} catch (Exception e) {
			j.setMsg("删除失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	/**
	 * 发布会议文件
	 */
	public void pub() {
		Json j = new Json();
		try {
			meetFileService.pub(meetFile,httpSession);
			j.setSuccess(true);
			j.setMsg("发布成功!");
		} catch (Exception e) {
			j.setMsg("发布失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	/**
	 * 取消发布会议文件
	 */
	public void cancelPub() {
		Json j = new Json();
		try {
			meetFileService.cancelPub(meetFile);
			j.setSuccess(true);
			j.setMsg("取消发布成功!");
		} catch (Exception e) {
			j.setMsg("取消发布失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	public MeetFileServiceI getMeetFileService() {
		return meetFileService;
	}
	@Autowired
	public void setMeetFileService(MeetFileServiceI meetFileService) {
		this.meetFileService = meetFileService;
	}

}
