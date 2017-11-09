package com.tlzn.service.base;

import com.tlzn.model.sys.Tdolog;

/**
 * 基础Service
 * 
 * @author 刘平
 * 
 */
public interface BaseServiceI {
	
	public String CreSequence(String seqfield) throws Exception;
	/**
	 * 日志生成
	 * @param dolog
	 * @return void
	 */
	public void saveLog(Tdolog dolog)throws Exception;
	/*
	 * 根据转入的数值，查找对应的汉字名称	
	*/
	public String findDicName(String type,String value);
	/*
	 * 操作人员名称获取	
	*/
	public String findNameByUserid(String userids);
	/*
	 * 提交人员名称获取	
	*/
	public String findNameByCommCode(String commCode);
	/**
	 * 日志生成
	 */
	public void saveLog(String logType,String title,String keyId,String info) throws Exception;
}
