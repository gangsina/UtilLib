/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bentengwu.utillib.file.zip;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.slf4j.Logger;

import java.io.File;
import java.util.List;

/**
 * 用ANT 压缩文件.
 *<br />
 *@类名称：ZipCompress.java
 *@文件路径：com.qymen.utillib.file.zip
 *@内容摘要：
 *@author：email: <a href="bentengwu@163.com"> 伟宏 </a> 
 *@完成日期：
 *@Date 2013-11-18 下午4:07:15 
 *@comments:	
 */
public class ZipCompress {
    private Logger logger = org.slf4j.LoggerFactory.getLogger(ZipCompress.class);
    /**
     * 保存zip的文件路径
     */
    private File zipFile;  
  
    /**
     * 构造工具
     * @param pathName 压缩包的保存路径
     */
    public ZipCompress(String pathName) {  
        zipFile = new File(pathName);  
    }  
    
    
    /**
     *  压缩目录.
     *<br />
     *@date 2013-11-18 下午4:11:07
     *@author <a href="bentengwu@163.com">伟宏</a>
     *@param dir	目录
     */
    public void compress(String dir) {  
        File srcdir = new File(dir);  
        if (!srcdir.exists())  
            throw new RuntimeException(dir + "不存在！");  
        Project prj = new Project();  
        Zip zip = new Zip();  
        zip.setProject(prj);  
        zip.setDestFile(zipFile);  
        FileSet fileSet = new FileSet();  
        fileSet.setProject(prj);
        fileSet.setDir(srcdir);
        //fileSet.setIncludes("**/*.java"); 包括哪些文件或文件夹 eg:zip.setIncludes("*.java");  
        //fileSet.setExcludes(...); 排除哪些文件或文件夹  
        zip.addFileset(fileSet);
        zip.execute();  
    }
    
    /**
     * 将给出的文件加到压缩包.
     *<br />
     *@date 2013-11-18 下午4:11:40
     *@author <a href="bentengwu@163.com">伟宏</a>
     *@param fileList		文件目录
     */
    public void compress(List<File> fileList){
        logger.info("It's to create magazine zip file ");
        Project prj = new Project();  
        Zip zip = new Zip();
        zip.setProject(prj);  
        zip.setDestFile(zipFile);  
        FileSet fileSet = new FileSet();  
        fileSet.setProject(prj);
        for(File f : fileList){
            logger.info("add: {}" , f.getAbsolutePath());
            fileSet.setFile(f);
        }
        zip.addFileset(fileSet);
        zip.execute();
        logger.info("It's finish to create ZIP file : {}" , zipFile.getAbsolutePath());
    }
    
    
    public static void main(String[] args) {
//        ZipCompressorByAnt zca = new ZipCompressorByAnt("E:\\123.zip");  
//        zca.compress("E:\\tmp\\2013_08_07");  
    }
}  