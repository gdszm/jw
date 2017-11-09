package com.tlzn.service.poll;

import java.util.List;

import java.util.Map;
import javax.servlet.http.HttpSession;
import com.tlzn.model.poll.TpollHandle;
import com.tlzn.pageModel.base.DataGrid;

public interface PollHandleServiceI {
	
	public DataGrid handleDatagrid(TpollHandle handle)throws Exception;
	
	public void confirm(TpollHandle handle) throws Exception;
	
	public List<TpollHandle> getPollHandle(TpollHandle handle)throws Exception;
	
	public void refuse(TpollHandle handle) throws Exception;
	
	public void pollRefuse(TpollHandle handle) throws Exception;
	
	public void handleChange(TpollHandle handle) throws Exception;
	
	public TpollHandle findObj(TpollHandle handle) throws Exception;
	
	public void reject(TpollHandle handle) throws Exception;
	
	public void checkPass(TpollHandle handle)throws Exception;
	public DataGrid datagrid(TpollHandle handle, String name) throws Exception;

	public void receiv(TpollHandle handle)  throws Exception;

	public void back(TpollHandle handle) throws Exception;

	public String report(TpollHandle handle, String companyId) throws Exception;

	public void save(TpollHandle handle)  throws Exception;

	public Map<String, Integer> pollHandleCount(HttpSession httpSession)  throws Exception;

}
