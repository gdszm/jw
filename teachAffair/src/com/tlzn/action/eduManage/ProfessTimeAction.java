package com.tlzn.action.eduManage;


import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.eduManage.ProfessTime;
import com.tlzn.service.eduManage.ProfessTimeServiceI;
import com.tlzn.util.base.Util;

@Action(value = "professTime", results = {
		@Result(name = "professTime", location = "/general/eduManage/professTime.jsp"),
		@Result(name = "professTimeView", location = "/general/eduManage/professTimeView.jsp"),
		@Result(name = "professTimeAdd", location = "/general/eduManage/professTimeAdd.jsp"),
		@Result(name = "professTimeEdit", location = "/general/eduManage/professTimeEdit.jsp"),
		//授课时间选择页面
		@Result(name = "professTimeSelect", location = "/general/eduManage/professTimeSelect.jsp")

})
public class ProfessTimeAction extends BaseAction implements ModelDriven<ProfessTime>{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ProfessTimeAction.class);
	
	private ProfessTimeServiceI professTimeService;
	
	private ProfessTime professTime=new ProfessTime();
	public ProfessTime getModel() {
		return professTime;
	}
	
	/**
	 * 授课时间页面
	 */
	public String professTime(){
		return "professTime";
	}
	/**
	 * 授课时间详细页面
	 */
	public String professTimeView(){
		return "professTimeView";
	}
	/**
	 * 授课时间新增页面
	 */
	public String professTimeAdd(){
		return "professTimeAdd";
	}
	/**
	 * 授课时间新增页面
	 */
	public String professTimePub(){
		request.setAttribute("professTimePub","professTimePub");
		return "professTimeAdd";
	}
	
	/**
	 * 弹出授课时间选择页面
	 */
	public String professTimeSelect(){
		return "professTimeSelect";
	}
	
	/**
	 * 
	 * 查询授课时间列表
	 */
	public void datagrid() {
		try {
			DataGrid dataGrid = professTimeService.datagrid(professTime);
			writeDataGridJson(dataGrid);
		} catch (Exception e) {  
			e.printStackTrace();
			
			logger.info(e.getMessage());
		}
	}

	/**
	 * 
	 * 查询 授课时间单条（跳转 授课时间详细页面）
	 */
	public String get() {
		try {
			String id=professTime.getId();
			if(!Util.getInstance().isEmpty(id)){
					ProfessTime professTime =professTimeService.get(id);
					request.setAttribute("professTime",professTime);
//					String atts= professTime.getAtts();
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
		return "professTimeView";
	}
	/**
	 * 授课时间修改页面
	 */
	public String professTimeEdit(){
		try {
			String id=professTime.getId();
			if(!Util.getInstance().isEmpty(id)){
					ProfessTime professTime =professTimeService.get(id);
					request.setAttribute("professTime",professTime);
//					String atts= professTime.getAtts();
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
		return "professTimeEdit";
	}
	/**
	 * 
	 * 保存授课时间
	 */
	public void add() {
		Json j = new Json();
		try {
			if(Util.getInstance().isEmpty(professTime.getId())){
				j.setObj(professTimeService.save(professTime,httpSession));
			} else {
//				j.setObj(professTimeService.saveForUpate(professTime,httpSession));
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
	 * 修改授课时间
	 */
	public void edit() {
		Json j = new Json();
		try {
			professTimeService.edit(professTime);
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
	 * 删除授课时间
	 */
	public void delete() {
		Json j = new Json();
		try {
			professTimeService.del(professTime);
			j.setSuccess(true);
			j.setMsg("删除成功!");
		} catch (Exception e) {
			j.setMsg("删除失败!");
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		writeJson(j);
	}
	public ProfessTimeServiceI getProfessTimeService() {
		return professTimeService;
	}
	@Autowired
	public void setProfessTimeService(ProfessTimeServiceI professTimeService) {
		this.professTimeService = professTimeService;
	}
}
