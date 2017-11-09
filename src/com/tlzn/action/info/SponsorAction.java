package com.tlzn.action.info;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.tlzn.action.base.BaseAction;
import com.tlzn.pageModel.base.Json;
import com.tlzn.pageModel.info.Proposaler;
import com.tlzn.pageModel.sys.Comm;
import com.tlzn.service.info.SponsorServiceI;

@Action(value = "sponsor", results = { @Result(name = "proser", location = "/general/admin/proser.jsp") ,@Result(name = "proserAE", location = "/general/admin/proserAE.jsp") ,@Result(name = "proserMy", location = "/general/admin/proserMy.jsp")})
public class SponsorAction extends BaseAction implements ModelDriven<Proposaler>{
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(SponsorAction.class);
	
	private SponsorServiceI sponsorService;
	private Proposaler sponsor=new Proposaler();
	
	
	public SponsorServiceI getSponsorService() {
		return sponsorService;
	}
	
	@Autowired
	public void setSponsorService(SponsorServiceI sponsorService) {
		this.sponsorService = sponsorService;
	}
	
	public Proposaler getModel() {
		return sponsor;
	}
	
	public String proser() {
		return "proser";
	}

	public String proserAE() {
		return "proserAE";
	}

	public String proserMy() {
		return "proserMy";
	}

	public void add(){
		Json j = new Json();
		try {
			String msg=sponsorService.save(sponsor, ((Comm)httpSession.getAttribute("sessionComm")).getCode());
			j.setMsg(msg);
			j.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg(e.getLocalizedMessage());
		}
		super.writeJson(j);
	}
	
	public void datagrid(){
		try {
			writeDataGridJson(sponsorService.datagrid(sponsor));
		} catch (Exception e) {
			//logger.info(e.getMessage());
			e.printStackTrace();
		}
	}
}
