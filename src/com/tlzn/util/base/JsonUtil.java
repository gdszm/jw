package com.tlzn.util.base;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.tlzn.pageModel.base.DataGrid;
public class JsonUtil {	
	/**
	 * DataGrid对象转换JsonStr
	 * @param datagrid
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static String dataGridToJsonStr(DataGrid datagrid) throws IllegalArgumentException, IllegalAccessException{
		if(datagrid==null||datagrid.getRows()==null||datagrid.getRows().size()<1){
			return "{\"rows\":[],\"total\":0}";
		}
		String jsonStr = "{";		
		Class<?> cla = datagrid.getClass();		
		Field fields[] = cla.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
//			System.out.println("数据字段:"+field.getName()+"类型："+field.getType());
			if(field.getType() == long.class){
				jsonStr += "\""+field.getName()+"\":"+field.getLong(datagrid)+",";
			}else if(field.getType() == Date.class){
				jsonStr += "\""+field.getName()+"\":\""+field.get(datagrid)+"\",";
			}else if(field.getType() == double.class){
				jsonStr += "\""+field.getName()+"\":"+field.getDouble(datagrid)+",";
			}else if(field.getType() == float.class){
				jsonStr += "\""+field.getName()+"\":"+field.getFloat(datagrid)+",";
			}else if(field.getType() == int.class){
				jsonStr += "\""+field.getName()+"\":"+field.getInt(datagrid)+",";
			}else if(field.getType() == boolean.class){
				jsonStr += "\""+field.getName()+"\":"+field.getBoolean(datagrid)+",";
			}else if(field.getType() == Integer.class||field.getType() == Boolean.class
					||field.getType() == Double.class||field.getType() == Float.class					
					||field.getType() == Long.class){				
				jsonStr += "\""+field.getName()+"\":"+field.get(datagrid)+",";
			}else if(field.getType() == String.class){
				jsonStr += "\""+field.getName()+"\":\""+field.get(datagrid)+"\",";
			}else if(field.getType() == List.class){
				jsonStr += "\""+field.getName()+"\":"+listToJsonStr((List)field.get(datagrid))+",";
			}			
		}
		jsonStr = jsonStr.substring(0,jsonStr.length()-1);
		jsonStr += "}";
		System.out.println(jsonStr);
		return jsonStr;		
	}	
	/**
	 * 将JAVA对象转换成JSON字符串
	 * @param obj
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("rawtypes")
	public static String objectToJsonStr(Object obj) throws IllegalArgumentException, IllegalAccessException{
		if(obj==null){
			return "";
		}
		String jsonStr = "{";		
		Class<?> cla = obj.getClass();		
		Field fields[] = cla.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			//System.out.println("数据字段:"+field.getName()+"类型："+field.getType());
			if(field.getType() == long.class){
				jsonStr += "\""+field.getName()+"\":"+field.getLong(obj)+",";
			}else if(field.getType() == Date.class){
				jsonStr += "\""+field.getName()+"\":\""+(field.get(obj)==null?"":field.get(obj))+"\",";
			}else if(field.getType() == double.class){
				jsonStr += "\""+field.getName()+"\":"+field.getDouble(obj)+",";
			}else if(field.getType() == float.class){
				jsonStr += "\""+field.getName()+"\":"+field.getFloat(obj)+",";
			}else if(field.getType() == int.class){
				jsonStr += "\""+field.getName()+"\":"+field.getInt(obj)+",";
			}else if(field.getType() == boolean.class){
				jsonStr += "\""+field.getName()+"\":"+field.getBoolean(obj)+",";
			}else if(field.getType() == Integer.class||field.getType() == Boolean.class
					||field.getType() == Double.class||field.getType() == Float.class					
					||field.getType() == Long.class){				
				jsonStr += "\""+field.getName()+"\":"+(field.get(obj)==null?"":field.get(obj))+",";
			}else if(field.getType() == String.class){
				jsonStr += "\""+field.getName()+"\":\""+(field.get(obj)==null?"":field.get(obj))+"\",";
			}else if(field.getType().toString().indexOf("model.")!=-1&&field.get(obj)!=null){				
				jsonStr += "\""+field.getName()+"\":"+subObjectToJsonStr(field.get(obj))+",";
			}			
		}
		jsonStr = jsonStr.substring(0,jsonStr.length()-1);
		jsonStr += "}";
		return jsonStr;		
	}
	
	public static String subObjectToJsonStr(Object obj) throws IllegalArgumentException, IllegalAccessException{
		if(obj==null){
			return "";
		}				
		String jsonStr = "{";			
		Class<?> cla = obj.getClass();		
		Field fields[] = cla.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			//System.out.println("数据字段2:"+field.getName()+"类型2："+field.getType());
			if(field.getType() == long.class){
				jsonStr += "\""+field.getName()+"\":"+field.getLong(obj)+",";
			}else if(field.getType() == Date.class){
				jsonStr += "\""+field.getName()+"\":\""+(field.get(obj)==null?"":field.get(obj))+"\",";
			}else if(field.getType() == double.class){
				jsonStr += "\""+field.getName()+"\":"+field.getDouble(obj)+",";
			}else if(field.getType() == float.class){
				jsonStr += "\""+field.getName()+"\":"+field.getFloat(obj)+",";
			}else if(field.getType() == int.class){
				jsonStr += "\""+field.getName()+"\":"+field.getInt(obj)+",";
			}else if(field.getType() == boolean.class){
				jsonStr += "\""+field.getName()+"\":"+field.getBoolean(obj)+",";
			}else if(field.getType() == Integer.class||field.getType() == Boolean.class
					||field.getType() == Double.class||field.getType() == Float.class					
					||field.getType() == Long.class){				
				jsonStr += "\""+field.getName()+"\":"+(field.get(obj)==null?"":field.get(obj))+",";
			}else if(field.getType() == String.class){
				jsonStr += "\""+field.getName()+"\":\""+(field.get(obj)==null?"":field.get(obj))+"\",";
			}			
			
		}
		jsonStr = jsonStr.substring(0,jsonStr.length()-1);
		jsonStr += "}";		
		return jsonStr;		
	}	
	
	/**
	 * 将JAVA的LIST转换成JSON字符串
	 * @param list
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static String listToJsonStr(List list) throws IllegalArgumentException, IllegalAccessException{
		if(list==null||list.size()==0){
			return "[]";
		}
		/**
		List list2=new ArrayList();
		Tuser user=new Tuser();
		user.setCid("001");
		user.setCname("测试");
		Tdept dept=new Tdept();
		dept.setCid("999");
		dept.setCname("技术部");
		user.setTdept(dept);
		list2.add(user);
		Tuser user2=new Tuser();
		user2.setCid("0012");
		user2.setCname("测试2");
		Tdept dept2=new Tdept();
		dept2.setCid("9992");
		dept2.setCname("技术部2");
		user2.setTdept(dept);
		list2.add(user2);
		list=list2;
		**/
		String jsonStr = "[";
		for (Object object : list) {
			jsonStr += objectToJsonStr(object)+",";
		}
		jsonStr = jsonStr.substring(0,jsonStr.length()-1);
		jsonStr += "]";		
		//System.out.println(jsonStr);
		return jsonStr;
	}
	
	/**
	 * 将JAVA的MAP转换成JSON字符串，
	 * 只转换第一层数据
	 * @param map
	 * @return
	 */
	public static String simpleMapToJsonStr(Map<?,?> map){
		if(map==null||map.isEmpty()){
			return "";
		}
		String jsonStr = "{";
		Set<?> keySet = map.keySet();
		for (Object key : keySet) {
			jsonStr += "\""+key+"\":\""+map.get(key)+"\",";		
		}
		jsonStr = jsonStr.substring(0,jsonStr.length()-1);
		jsonStr += "}";
		return jsonStr;
	}
}