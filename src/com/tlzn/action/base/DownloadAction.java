package com.tlzn.action.base;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;


/**
 * 所下载文件相关的的几个属性:文件格式 contentType, 
 * 获取文件名的方法inputName,
 * 文件内容（显示的）属性contentDisposition, 
 * 限定下载文件 缓冲区的值bufferSize
 * */

@Action(value = "down", results = { @Result(name = "success", type = "stream", params = { "contentType",
		"application/octet-stream;charset=ISO8859-1", "inputName",
		"inputStream", "contentDisposition",
		"attachment;filename=\"/excelfile/proposal_admin.xls\"", "bufferSize", "4096" }) })
public class DownloadAction extends BaseAction {

	private static final long serialVersionUID = 8784555891643520648L;
	private String STORAGEPATH = "/excelfile/proposal_admin.xls";

	private String fileName;// 初始的通过param指定的文件名属性
	private String storageId;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getStorageId() {
		return storageId;
	}

	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}

	// 如果下载文件名为中文，进行字符编码转换
	public String getDownloadFileName() {
		String downloadFileName = fileName;

		try {
			downloadFileName = new String(downloadFileName.getBytes(),
					"ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return downloadFileName;
	}

	public InputStream getInputStream() throws Exception {
		/**
		 * 下载用的Action应该返回一个InputStream实例， 
		 * 该方法对应在result里的inputName属性值为targetFile
		 **/
		try {
			return ServletActionContext.getServletContext().getResourceAsStream(
					STORAGEPATH);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return null;
		}
	}

	public String execute() throws Exception {
		return SUCCESS;
	}

}

