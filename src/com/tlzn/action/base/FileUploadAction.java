package com.tlzn.action.base;

import java.io.BufferedInputStream;  
import java.io.BufferedOutputStream;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.IOException;
import java.util.Date;
  
import org.apache.struts2.ServletActionContext;  
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef; 
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.tlzn.pageModel.base.Json;
import com.tlzn.util.base.Constants;

  
@Action("fileUpload")  
@InterceptorRefs(value = { @InterceptorRef("fileUploadStack") })  
@Results({ @Result(name = "upload", location = "/public/upfile.jsp"),
//	       @Result(name = "upImg", location = "/mobile/upImg.jsp"),
//	       @Result(name = "upVideo", location = "/mobile/upVideo.jsp")
})   
public class FileUploadAction extends BaseAction {  
    private static final long serialVersionUID = 572146812454l;  
  
  //代表上传文件的file对象     
    private File file;     
    //上传文件名     
    private String fileFileName;    
    //旧文件名
    private String oldFileName;
    //上传文件的MIME类型     
    private String fileContentType;     
    //保存上传文件的目录  
    private String uploadDir; 
	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	public String getUploadDir() {
		return uploadDir;
	}
	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}
	public String uploadFile() {
		return "upload";
	}
	
	public String upImgFile() {
		return "upImg";
	}
	public String upVideoFile() {
		return "upVideo";
	}
    public String getOldFileName() {
		return oldFileName;
	}
	public void setOldFileName(String oldFileName) {
		this.oldFileName = oldFileName;
	}
	/** 
     * @方法名称: copyImg 
     * @描述: 图片上传处理 
     * 创建人： 
     * @return void 
     * @param @param srcFile 
     * @param @param destFile 
     * @param @throws IOException      
     */  
     public void upload(){     
         // 得到当前时间自1970年1月1日0时0分0秒开始流逝的毫秒数，将这个毫秒数作为上传文件新的文件名。  
         long now = new Date().getTime();  
         //新文件名称  
         String newFileName;  
         Json j = new Json();
         BufferedOutputStream bos = null;  
         BufferedInputStream bis = null;  
         // 得到保存上传文件的目录的真实路径  
         if(uploadDir==null||"".equals(uploadDir)){
        	 uploadDir = Constants.IMGPATH;
         }
        
         String path = ServletActionContext.getServletContext().getRealPath(uploadDir);  
         
         File dir = new File(path); 
         try {   
	         // 如果这个目录不存在，则创建它。  
	         if (!dir.exists())  
	             dir.mkdir();  
	         int index = fileFileName.lastIndexOf('.'); 
	         // 判断上传文件名是否有扩展名  
	         if (index != -1)  
	             newFileName = now + fileFileName.substring(index);  
	         else  
	             newFileName = Long.toString(now);  
	         
	        
	         
	         // 读取保存在临时目录下的上传文件，写入到新的文件中。  
          
             FileInputStream fis = new FileInputStream(file);  
             bis = new BufferedInputStream(fis);  
             FileOutputStream fos = new FileOutputStream(new File(dir,newFileName));  
             bos = new BufferedOutputStream(fos);  
             byte[] buf = new byte[4096];  
             int len = -1;  
             while ((len = bis.read(buf)) != -1) {  
                 bos.write(buf, 0, len);  
             } 
            
             j.setSuccess(true);
             j.setObj(newFileName);
 			 j.setMsg("文件上传成功!");
         }catch (Exception e) {
        	 j.setMsg("文件上传失败!");
        	 e.printStackTrace();
		}  finally {  
             try {  
                 if (null != bis)  
                     bis.close();  
             } catch (IOException e) {  
                 e.printStackTrace();  
             }  
             try {  
                 if (null != bos)  
                     bos.close();  
             } catch (IOException e) {  
                 e.printStackTrace();  
             } 
             writeJson(j);
         }  
     }   
     /** 
     * @方法名称: delGoodsImage 
     * @描述: 删除图片 
     * 创建人： 
     * @return void 
     * @param @param fileString      
     */  
     public void doNotNeedAuth_delImage(){  
         //判断文件是否存在,如果存在为更新操作  
         if(oldFileName!=null&&!"".equals(oldFileName)){  
        	 // 得到保存上传文件的目录的真实路径  
             if(uploadDir==null||"".equals(uploadDir)){
            	 uploadDir = "/upload/mobile";
             }
             String path = ServletActionContext.getServletContext().getRealPath(uploadDir);  
             File dir = new File(path+"/"+oldFileName);  
             // 如果这个文件已存在，则删除。  
             if (dir.isFile())  
                 dir.delete();  
         }  
     }  
  
}  