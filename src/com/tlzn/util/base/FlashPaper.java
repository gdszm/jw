package com.tlzn.util.base;
import java.io.BufferedInputStream;  
import java.io.BufferedOutputStream;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.InputStream;  
import java.io.OutputStream;  
import java.util.Date;  
public class FlashPaper extends Thread{
    public static String converter(File srcFile,String fileContentType,String fileFileName) {  
        // SWF 放到服务器下  
        String filename =fileFileName.replace(".doc", ".swf");  
        File file = new File(filename);  
        // 要转换的文件 放到临时目录  
        String docTempFileName = "C:\\" + String.valueOf(new Date().getTime()) + fileContentType;  
        File docTempFile = new File(docTempFileName);  
        copyFile(srcFile, docTempFile);  
        // 转换后的文件 放到临时目录  
        String swfTempFileName = "C:\\" + String.valueOf(new Date().getTime()) + ".swf";  
        File swfTempFile = new File(swfTempFileName);  
        try {
        	//String converter = "D:\\zxta\\Program Files\\Print2Flash3\\print2flash.exe -o " + swfTempFile.getAbsolutePath() + " " + docTempFile.getAbsolutePath();
        	//String converter = "D:\\Program Files\\FlashPaper2.2\\FlashPrinter.exe -o " + swfTempFile.getAbsolutePath() + " " + docTempFile.getAbsolutePath();
        	String converter = "E:\\soft\\flashpaper\\FlashPaper2.2\\FlashPrinter.exe -o " + swfTempFile.getAbsolutePath() + " " + docTempFile.getAbsolutePath();
            Runtime pro = Runtime.getRuntime();  
            pro.exec(converter);  
            // 注意，为了测试，这里只要没有转换工程，就一直等待  
            while(true){  
                if(!swfTempFile.exists()){  
                    Thread.sleep(1000);  
                    continue;  
                }  
                copyFile(swfTempFile, file);  
                // 删除临时文件  
                swfTempFile.delete();  
                docTempFile.delete(); 
                return filename;  
            }  
        } catch (Exception e) {  
            System.out.println("执行失败");  
            e.printStackTrace();  
        }  
        return "";  
    }  
    private static final int BUFFER_SIZE = 102400;  
    private static void copyFile(File src, File dir) {  
        try {  
            InputStream input = null;  
            OutputStream output = null;  
            try {  
                input = new BufferedInputStream(new FileInputStream(src),  
                        BUFFER_SIZE);  
                output = new BufferedOutputStream(new FileOutputStream(dir),  
                        BUFFER_SIZE);  
                byte[] buffer = new byte[BUFFER_SIZE];  
                while (input.read(buffer) > 0) {  
                    output.write(buffer);  
                }  
            } catch (Exception e) {  
                e.printStackTrace();  
            } finally {  
                if (null != input) {  
                    input.close();  
                }  
                if (null != output) {  
                    output.close();  
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}
