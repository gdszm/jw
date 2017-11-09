package com.tlzn.service.speech;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.tlzn.model.speech.Tspeech;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.speech.Speech;

public interface SpeechServiceI {
	public Speech get(String id) throws Exception;
	public DataGrid datagrid(Speech speech)throws Exception;
	public DataGrid datagridOwn(Speech speech) throws Exception;
	public DataGrid datagridDg(Speech speech) throws Exception;
	public DataGrid datagridZq(Speech speech) throws Exception;
	public DataGrid datagridAdopted(Speech speech) throws Exception;
	public DataGrid datagridFulfil(Speech speech,String userCode, String secondaryCode) throws Exception;
	public String save(Speech speech,HttpSession httpSession) throws Exception;
	public String saveForUpate(Speech speech,HttpSession httpSession) throws Exception;
	public String upDateOrAdd(Speech speech,HttpSession httpSession) throws Exception;
	public String edit(Speech speech,HttpSession httpSession) throws Exception;
	public String audit(Speech speech,HttpSession httpSession) throws Exception;
	public String confirm(Speech speech,HttpSession httpSession) throws Exception;
	public String discard(Speech speech,HttpSession httpSession) throws Exception;
	public void del(Speech speech) throws Exception;
	public void submit(Speech speech) throws Exception;
	public void cancelSubmit(Speech speech) throws Exception;
//	public void edit(Speech speech) throws Exception;
	/**
	 * 
		 * 会议发言首页统计
		 * @param  meeting
		 * @param  httpSession
		 * @throws 	Exception
		 * @return 	void
	 */
	public Map<String, Integer> speechCount(HttpSession httpSession);
	
}
