package com.tlzn.action.eduManage;


import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.eduManage.Bed;
import com.tlzn.service.eduManage.BedServiceI;
import com.tlzn.util.base.Util;

@Action(value = "bed", results = {
		@Result(name = "bed", location = "/general/eduManage/bed.jsp"),
		@Result(name = "bedView", location = "/general/eduManage/bedView.jsp"),
		@Result(name = "bedAdd", location = "/general/eduManage/bedAdd.jsp"),
		@Result(name = "bedEdit", location = "/general/eduManage/bedEdit.jsp"),
		@Result(name = "bedQuery", location = "/general/eduManage/bedQuery.jsp"),
})
public class BedAction extends BaseAction implements ModelDriven<Bed>{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(BedAction.class);
	
	private BedServiceI bedService;
	
	private Bed bed=new Bed();
	
	public Bed getModel() {
		return bed;
	}
	
	/**
	 * 住宿列表页面
	 */
	public String bed(){
		return "bed";
	}
	/**
	 * 住宿查询页面
	 */
	public String bedQuery(){
		return "bedQuery";
	}
	/**
	 * 住宿详细页面
	 */
	public String bedView(){
		return "bedView";
	}
	/**
	 * 住宿新增页面
	 */
	public String bedAdd(){
		return "bedAdd";
	}
	/**
	 * 住宿发布页面
	 */
	public String bedPub(){
		request.setAttribute("bedPub","bedPub");
		return "bedAdd";
	}
	/**
	 * 
	 * 查询住宿列表
	 */
	public void datagrid() {
		try {
			DataGrid dataGrid = bedService.datagrid(bed);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询住宿列表（不验证权限）
	 */
	public void doNotNeedAuth_datagrid() {
		try {
			DataGrid dataGrid = bedService.datagrid(bed);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询住宿列表(住宿查询模块)
	 */
	public void datagridQuery() {
		try {
//			bed.setStatus(Constants.CODE_TYPE_PUBSTATUS_YES);
			DataGrid dataGrid = bedService.datagrid(bed);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	/**
	 * 
	 * 查询 住宿单条（跳转 住宿详细页面）
	 */
	public String get() {
		try {
			String id=bed.getId();
			if(!Util.getInstance().isEmpty(id)){
					Bed bed =bedService.get(id);
					request.setAttribute("bed",bed);
//					String atts= bed.getAtts();
//					List<String> attList = null;
//					if(!Util.getInstance().isEmpty(atts)){
//						attList = new ArrayList<String>();
//						String attArry[] = atts.split(",");
//						for (String att : attArry) {
//							attList.add(att);
//						}
//						request.setAttribute("attList",attList);
//					}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "bedView";
	}
	/**
	 * 住宿修改页面
	 */
	public String bedEdit(){
		try {
			String id=bed.getId();
			if(!Util.getInstance().isEmpty(id)){
				Bed bed =bedService.get(id);
				request.setAttribute("bed",bed);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "bedEdit";
	}
	/**
	 * 
	 * 保存住宿
	 */
	public void save() {
		Json j = new Json();
		try {
			if(Util.getInstance().isEmpty(bed.getId())){
				j.setObj(bedService.save(bed,httpSession));
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
	 * 修改住宿
	 */
	public void edit() {
		Json j = new Json();
		try {
			bedService.edit(bed);
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
	 * 删除住宿
	 */
	public void delete() {
		Json j = new Json();
		try {
			bedService.del(bed);
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
	 * 发布住宿
	 */
	public void pub() {
		Json j = new Json();
		try {
			bedService.pub(bed,httpSession);
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
	 * 取消发布住宿
	 */
	public void cancelPub() {
		Json j = new Json();
		try {
			bedService.cancelPub(bed);
			j.setSuccess(true);
			j.setMsg("取消发布成功!");
		} catch (Exception e) {
			j.setMsg("取消发布失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	public BedServiceI getBedService() {
		return bedService;
	}
	@Autowired
	public void setBedService(BedServiceI bedService) {
		this.bedService = bedService;
	}
}
