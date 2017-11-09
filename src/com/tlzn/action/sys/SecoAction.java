package com.tlzn.action.sys;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.sys.Comm;
import com.tlzn.pageModel.sys.Seco;
import com.tlzn.service.sys.CommServiceI;
import com.tlzn.service.sys.SecoServiceI;
import com.tlzn.util.base.JsonUtil;

@Action(value = "seco", results = { @Result(name = "seco", location = "/general/admin/seco.jsp") ,@Result(name = "secoAE", location = "/general/admin/secoAE.jsp"),@Result(name = "secoSetWy", location = "/general/admin/seco_setwy.jsp")})
public class SecoAction extends BaseAction implements ModelDriven<Seco> {
	/**
	 * 
	 */
	protected final Log log = LogFactory.getLog(getClass());

	private static final long serialVersionUID = 1L;
	private SecoServiceI secoService;
	private CommServiceI commService;
	private Seco seco=new Seco();

	public Seco getModel() {
		return seco;
	}
	
	public String seco() {
		return "seco";
	}

	public String secoAE() {
		return "secoAE";
	}
	
	public String secoSetWy() {
		String secoId=request.getParameter("secoid");
		List<Comm> rowList=commService.findSeco(secoId);
		List<Comm> norowList=commService.findNOSeco(secoId);
		System.out.println("==="+norowList.size());
		request.setAttribute("rowlist", rowList);
		request.setAttribute("norowlist", norowList);
		return "secoSetWy";
	}

	public SecoServiceI getSecoService() {
		return secoService;
	}
	
	@Autowired
	public void setSecoService(SecoServiceI secoService) {
		this.secoService = secoService;
	}

	public CommServiceI getCommService() {
		return commService;
	}
	@Autowired
	public void setCommService(CommServiceI commService) {
		this.commService = commService;
	}

	public void add(){
		Json j = new Json();
		try {
			secoService.save(seco);
			j.setMsg("添加成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg(e.getLocalizedMessage());
		}
		super.writeJson(j);
	}
	
	public void edit(){
		Json j = new Json();
		try {
			secoService.update(seco);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg("编辑失败！");
		}
		super.writeJson(j);
	}

	public void change(){
		Json j = new Json();
		try {
			secoService.change(seco,httpSession);
			j.setSuccess(true);
			j.setMsg("操作成功!");
		} catch (Exception e) {
			j.setMsg("操作失败！");
			e.printStackTrace();
		}
		super.writeJson(j);
	}
	public void setSave(){
		Json j = new Json();
		String secoid=request.getParameter("secondaryId");
		String leftstr=request.getParameter("leftdata");
		String rightstr=request.getParameter("rightdata");
		try {
			secoService.setSave(leftstr,rightstr,secoid);
			j.setSuccess(true);
			j.setMsg("操作成功!");
		} catch (Exception e) {
			j.setMsg("操作失败！");
			e.printStackTrace();
		}
		super.writeJson(j);
	}
	public void doNotNeedAuth_change(){
		System.out.println("ids===="+seco.getIds());
		Json j = new Json();
		try {
			secoService.change(seco,httpSession);
			j.setSuccess(true);
			j.setMsg("操作成功!");
		} catch (Exception e) {
			j.setMsg("操作失败！");
			e.printStackTrace();
		}
		super.writeJson(j);
	}

	public void datagrid() {
		try {
			super.writeDataGridJson(secoService.datagrid(seco));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void datagridNoPage() {
		try {
			super.writeDataGridJson(secoService.datagridNoPage(seco));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void combobox() {
		try {
			super.writeListJson(JsonUtil.listToJsonStr(secoService.combobox()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void comboboxYear() {
		try {
			super.writeListJson(JsonUtil.listToJsonStr(secoService.comboboxYear()));
			@SuppressWarnings("unused")
			List<?> list=secoService.comboboxYear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void doNotNeedAuth_setatt(){
		Json j = new Json();
		try {
			secoService.setAtt(seco);
			j.setSuccess(true);
			j.setMsg("届次设置成功!");
		} catch (Exception e) {
			j.setMsg("届次设置失败！");
			e.printStackTrace();
		}
		super.writeJson(j);
	}

}
