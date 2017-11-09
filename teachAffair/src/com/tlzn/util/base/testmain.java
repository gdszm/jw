package com.tlzn.util.base;

public class testmain {

	/**
	 * 功能
	 * 
	 * @param 参数
	 * 
	 * @throws 	DataAccessException
	 * @throws 	AppException
	 * 
	 * @return 		返回值
	 */
	public static void main(String[] args) {
		int i=7;
		int j=3;
		float result =(float)i/j;
		System.out.println(result);
		System.out.println(Math.round(((float)i/j)*100)/100.0);//或System.out.println(Math.round(result*10)/10f);

	}

}
