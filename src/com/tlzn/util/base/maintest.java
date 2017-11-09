package com.tlzn.util.base;

import java.io.File;

public class maintest {

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
		File file=new File("d://test.doc");
		String fileName=FlashPaper.converter(file,".doc", "test");
		System.out.println(fileName);

	}

}
