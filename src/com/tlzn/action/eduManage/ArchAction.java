package com.tlzn.action.eduManage;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.eduManage.Archives;
import com.tlzn.service.eduManage.ArchServiceI;
import com.tlzn.util.base.Util;

@Action(value = "archives", results = {
		@Result(name = "archives", location = "/general/eduManage/archives.jsp"),
		@Result(name = "archivesView", location = "/general/eduManage/archivesView.jsp"),
		//新建档案
		@Result(name = "archivesPub", location = "/general/eduManage/archivesPub.jsp"),
		//添加档案
		@Result(name = "archivesAdd", location = "/general/eduManage/archivesAdd.jsp"),
		@Result(name = "archivesEdit", location = "/general/eduManage/archivesEdit.jsp"),
		@Result(name = "archivesQuery", location = "/general/eduManage/archivesQuery.jsp"),
})
public class ArchAction extends BaseAction implements ModelDriven<Archives>{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ArchAction.class);
	
	private ArchServiceI archivesService;
	
	private Archives archives=new Archives();
	
	public Archives getModel() {
		return archives;
	}
	
	/**
	 * 档案列表页面
	 */
	public String archives(){
		return "archives";
	}
	/**
	 * 档案查询页面
	 */
	public String archivesQuery(){
		return "archivesQuery";
	}
	/**
	 * 
	 * 档案详细页面
	 */
	public String archivesView() {
		Util util = Util.getInstance();
		try {
			String archNo=archives.getArchNo();
			if(!Util.getInstance().isEmpty(archNo)){
				Archives archives =archivesService.get(archNo);
				request.setAttribute("archives",archives);
				
				String atts= util.isEmpty(archives.getCertFiles())?"":archives.getCertFiles();
				Map<String,String> certMap = null;
				if(!Util.getInstance().isEmpty(atts)){
					certMap = new LinkedHashMap<String,String>();
					String attsArry[] = atts.split(",");
					for (int i = 0; i < attsArry.length; i++) {
						if(!util.isEmpty(attsArry[i])) {
							String attArry[] = attsArry[i].split("\\|\\|");
							if(attArry.length>1) {
								certMap.put(attArry[0], attArry[1]);
							} else {
								certMap.put(attArry[0], "");
							}
						}
					}
					request.setAttribute("certMap",certMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "archivesView";
	}
	/**
	 * 档案新增页面
	 */
	public String archivesPub(){
		return "archivesPub";
	}
	/**
	 * 档案添加页面
	 */
	public String archivesAdd(){
		return "archivesAdd";
	}
	/**
	 * 档案修改页面
	 */
	public String archivesEdit(){
		try {
			Util util = Util.getInstance();
			String archNo=archives.getArchNo();
			if(!util.isEmpty(archNo)){
				Archives archives =archivesService.get(archNo);
				request.setAttribute("archives",archives);

				String atts= util.isEmpty(archives.getCertFiles())?"":archives.getCertFiles();
				Map<String,String> certMap = null;
				if(!Util.getInstance().isEmpty(atts)){
					certMap = new LinkedHashMap<String,String>();
					String attsArry[] = atts.split(",");
					for (int i = 0; i < attsArry.length; i++) {
						if(!util.isEmpty(attsArry[i])) {
							String attArry[] = attsArry[i].split("\\|\\|");
							if(attArry.length>1) {
								certMap.put(attArry[0], attArry[1]);
							} else {
								certMap.put(attArry[0], "");
							}
						}
					}
					request.setAttribute("certMap",certMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "archivesEdit";
	}
	/**
	 * 
	 * 查询档案列表
	 */
	public void datagrid() {
		try {
			DataGrid dataGrid = archivesService.datagrid(archives);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询档案列表（不验证权限）
	 */
	public void doNotNeedAuth_datagrid() {
		try {
			DataGrid dataGrid = archivesService.datagrid(archives);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 保存档案
	 */
	public void save() {
		Json j = new Json();
		try {
			if(Util.getInstance().isEmpty(archives.getArchNo())){
				j.setObj(archivesService.save(archives,httpSession));
				j.setSuccess(true);
				j.setMsg("保存成功!");
			}
		} catch (Exception e) {
			j.setMsg("保存失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	/**
	 * 
	 * 修改档案
	 */
	public void edit() {
		Json j = new Json();
		try {
			archivesService.edit(archives,httpSession);
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
	 * 删除档案
	 */
	public void delete() {
		Json j = new Json();
		try {
			archivesService.del(archives);
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
	 * 发布档案
	 */
	public void pub() {
		Json j = new Json();
		try {
			archivesService.pub(archives,httpSession);
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
	 * 取消发布档案
	 */
	public void cancelPub() {
		Json j = new Json();
		try {
			archivesService.cancelPub(archives);
			j.setSuccess(true);
			j.setMsg("取消发布成功!");
		} catch (Exception e) {
			j.setMsg("取消发布失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	/**
	 * 
	 * 查询学生家庭成员列表
	 */
	public void fmDatagrid() {
		try {
			DataGrid dataGrid = archivesService.fmDatagrid(archives);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询奖惩记录列表
	 */
	public void apDatagrid() {
		try {
			DataGrid dataGrid = archivesService.apDatagrid(archives);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询教育经历列表
	 */
	public void eeDatagrid() {
		try {
			DataGrid dataGrid = archivesService.eeDatagrid(archives);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询工作经历列表
	 */
	public void weDatagrid() {
		try {
			DataGrid dataGrid = archivesService.weDatagrid(archives);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	public ArchServiceI getArchivesService() {
		return archivesService;
	}
	@Autowired
	public void setArchivesService(ArchServiceI archivesService) {
		this.archivesService = archivesService;
	}
}
