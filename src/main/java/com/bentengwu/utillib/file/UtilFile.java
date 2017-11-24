/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bentengwu.utillib.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.io.FileUtils;

import com.bentengwu.utillib.code.EncodeUtils;

/**
 * 对apache.io.FileUtils做一层简单的优化.
 * 方便自己对文件的操作.
 * @author 伟宏
 */
public class UtilFile {
    /**
     * 复制目录 将源目录中的内容拷贝到目标目录中.
     * @param srcFile 原目录
     * @param aimFile 目标目录
     */
    public static void copyDir(File srcFile, File aimFile){
        try {
            if(!Rd.dirExits(aimFile)){
              Rd.mkDir(aimFile);
            }
            FileUtils.copyDirectory(srcFile, aimFile);
        } catch (IOException ex) {
            throw new RuntimeException("It's error to copy files",ex);
        }
    }
    
    /**
     *  复制目录 将源目录中的内容拷贝到目标目录中.
     * @param srcDir    原目录
     * @param aimDir     目标目录
     */
    public static void copyDir(String srcDir, String aimDir){
        copyDir(new File(srcDir),new File(aimDir));
    }
    
    
    /**
     * 将srcDir目录中的内容及其本身一起 拷贝到 aimDir目录中.
     * @param srcDir 源目录
     * @param aimDir 目标目录
     */
    public static void copyDirIntoDir(String srcDir,String aimDir){
        File src = new File(srcDir);
        File aim = new File(aimDir);
        copyDirIntoDir(src,aim);
    }
    
    /**
     * 将srcDir目录中的内容及其本身一起 拷贝到aimDir目录中.
     * @param srcDir 源目录
     * @param aimDir 目标目录
     */
    public static void copyDirIntoDir(File srcDir,File aimDir){
        if(!Rd.dirExits(aimDir)){
            Rd.mkDir(aimDir);
        }
        File inAimDir = new File ( aimDir.getAbsolutePath() + File.separator + srcDir.getName());
        copyDir(srcDir, inAimDir);
    }
    
    /**
     * 删除文件中意startWith开头的行.并保存到文件.
     *@param startWith
     *@param filePath
     */
	public static void delLineInFile(String startWith,String filePath,String savePath){
		StringBuilder 	sb  = 	new StringBuilder();
		FileInputStream in 	= 	null;
		BufferedReader 	rd  = 	null;
		String 			s	=	null; 
		try{
			in = new FileInputStream(filePath);
			rd	=	new BufferedReader(new InputStreamReader(in, "utf-8"));
			 while ((s = rd.readLine()) != null) {
				 if(!s.startsWith(startWith)){
					 sb.append(s);
					 if(s.trim().endsWith(";")){
						 sb.append("\n");
					 }
				 }
              }
		}catch(IOException ex){
			ex.printStackTrace();
		}finally{
			try {
				rd.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		Rd.write(savePath, sb.toString(), EncodeUtils.DEFAULT_URL_ENCODING);
	}
	
	
	public static void main(String[] args) {
		delLineInFile("commit","e:\\tmp\\3.sql","e:\\tmp\\3.sql");
	}
}
