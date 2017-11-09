package com.tlzn.service.info;

import java.util.List;

import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.info.Proposaler;

/**
 * 提案人service
 * @author zhangle
 *
 */
public interface SponsorServiceI {
	
	public DataGrid datagrid(Proposaler proposaler)throws Exception;
	
	public List<Proposaler> find(Proposaler proposaler)throws Exception;

	public String save(Proposaler proser, String string) throws Exception;
	
}
