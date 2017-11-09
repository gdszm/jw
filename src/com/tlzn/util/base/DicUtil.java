package com.tlzn.util.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tlzn.model.sys.Tdic;
import com.tlzn.pageModel.sys.Dic;
import com.tlzn.util.base.ServiceLocator;

public class DicUtil {
	private static DicUtil instance;

	protected final Log log = LogFactory.getLog(getClass());

	/**
	 * @return Returns the log.
	 */
	public Log getLog() {
		return log;
	}

	/**
	 * 初始化本类对象
	 *  
	 * @return	CodeDefine	返回CodeDefine
	 */
	public static DicUtil getInstance() {
		if (instance != null)
			return instance;
		else
			return instance = new DicUtil();
	}

	/**
	 * 根据代码类型得到该类别下的字典数据
	 * 
	 * @param ctype 类型
	 * 
	 * @throws AppException
	 * 
	 * @return	ArrayList	返回该类别下的代码Dic对象集合
	 */
	public ArrayList getDic(String ctype) throws Exception
	{
		ArrayList arrlist=null;
		try 
		{
			if(ctype==null||"".equals(ctype)) return null;
			Dic dic=new Dic();
			dic.setCtype(ctype);
			arrlist=(ArrayList)ServiceLocator.getInstance().getDicService().combobox(dic);

		} catch (Exception error) {
			log.error("根据代码类别得到该类别下的字典数据时程序出错：(Dic类中的getDic方法)"
					+ error.getMessage());
			error.printStackTrace();
		}
		return arrlist;
	}

	/**
	 * 根据代码类别和cvalue,得到 dic 对象的ckey
	 * 
	 * @param ctype 代码类别
	 * @param cvalue 代码值
	 * 
	 * @throws Exception
	 * 
	 * @return	String	返回dic 对象的ckey
	 */
	public String getCkey(String ctype,String cvalue) throws Exception
	{	
		String ckey=null;
		if(ctype==null || "".equals(ctype) || cvalue==null || "".equals(cvalue)) return "";
		ArrayList diclist=null;
		Dic dic=null;
		try
		{			
			diclist=getDic(ctype);//根据代码类别ID得到代码类别
			if(diclist!=null)
			{
				Iterator it=diclist.iterator();
				while(it.hasNext())
				{
					dic=(Dic) it.next();
					if(cvalue.equals(dic.getCvalue()))
					ckey=dic.getCkey();
					break;
				}
			}
		}catch (Exception error) {
			log.error("根据字典类别得到字典表ckey时程序出错：(Dic类中的getCkey方法)"
					+ error.getMessage());
		}	
		return cvalue;
	}
	/**
	 * 根据字典类别和cvalue,查找字典数据
	 * @param ctype 字典类别
	 * @param cvalue 
	 * @throws Exception
	 * @return	boolean	返回TRUE 或FALSE
	 */
	public boolean isCode(String ctype,String cvalue) throws Exception
	{	boolean res=false;
		if(ctype==null || "".equals(ctype) || cvalue==null || "".equals(cvalue)) return false;
		ArrayList diclist=null;
		Dic dic=null;
		try
		{			
			diclist=getDic(ctype);//根据代码类别ID得到代码类别
			if(diclist!=null)
			{
				Iterator it=diclist.iterator();
				while(it.hasNext())
				{
					dic=(Dic) it.next();
					if(cvalue.equals(dic.getCvalue()))
						res=true;
					break;
				}
			}
		}catch (Exception error) {
			log.error("根据代码类别ID和codeid,查找代码时程序出错：(CodeDefine类中的isCode方法)"
					+ error.getMessage());
		}	
		return res;
	}	
	
}
