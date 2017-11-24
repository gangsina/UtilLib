/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bentengwu.utillib.file.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 * @ClassName: cn.thender.xu.oputils.Zip @Description:
 *
 * @author Author: E-mail <a href="mailto:bentengwu@163.com">thender.xu</a>
 * @date Time: 2012-1-31 下午01:11:03
 */
public class ZipDecompression {
	
    /**
     * @param sZipPathFile 需要解压的文件路径
     * @param sDestPath 存放路径 注意这里在末尾要 加上“\\” 或者 "/"
     * @return 解压的文件名列表
     */
    @SuppressWarnings("rawtypes")
	public static ArrayList Ectract(String sZipPathFile, String sDestPath) {
        ArrayList allFileName = new ArrayList();
        try {
            //先指定压缩档的位置和档名，建立FileInputStream对象
            FileInputStream fins = new FileInputStream(sZipPathFile);
            //将fins传入ZipInputStream中
            ZipInputStream zins = new ZipInputStream(fins);
            ZipEntry ze = null;
            byte ch[] = new byte[256];
            while ((ze = zins.getNextEntry()) != null) {
                File zfile = new File(sDestPath + ze.getName());
                File fpath = new File(zfile.getParentFile().getPath());
                if (ze.isDirectory()) {
                    if (!zfile.exists()) {
                        zfile.mkdirs();
                    }
                    zins.closeEntry();
                } else {
                    if (!fpath.exists()) {
                        fpath.mkdirs();
                    }
                    FileOutputStream fouts = new FileOutputStream(zfile);
                    int i;
                    allFileName.add(zfile.getAbsolutePath());
                    while ((i = zins.read(ch)) != -1) {
                        fouts.write(ch, 0, i);
                    }
                    zins.closeEntry();
                    fouts.close();
                }
            }
            fins.close();
            zins.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return allFileName;
    }

    public static void main(String[] args) {
        ArrayList a = new ArrayList();
        a = ZipDecompression.Ectract("e:\\test\\192_168_176_42.zip", "e:\\test1");
        System.out.println(a.size());
    }
}
