/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bentengwu.utillib.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.bentengwu.utillib.String.StrUtils;
import com.bentengwu.utillib.code.EncodeUtils;
import com.bentengwu.utillib.stream.StreamUtil;
import com.bentengwu.utillib.validate.ValidateUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author thender mailto bentengwu@163.com
 */
public class Rd {

    /**
     * 获取字符串值
     *
     * @param path 文件路径
     * @param name
     * @param defaultvalue 默认值
     * @param encode 编码方式
     * @return
     */
    public static String getStringValue(String path, String name, String defaultvalue, String encode) {
        String m = getLineString(path, name, encode);
        if (m.length() > 0 && m.indexOf("=") != -1) {
            try {
                return (m.split("="))[1].toString().trim();
            } catch (ArrayIndexOutOfBoundsException e) {
                return defaultvalue;
            }
        } else {
            return defaultvalue;
        }
    }

    /**
     *
     * @param path 文件路径
     * @param name 要获取的值的名称
     * @param defaultvalue 默认值
     * @param encode 文件的编码方式
     * @return
     */
    public static Integer getIntegerValue(String path, String name, Integer defaultvalue, String encode) {
        try {
            String m = getLineString(path, name, encode);
            if (m.length() > 0 && m.indexOf("=") != -1) {
                String tmp = m.substring(m.indexOf("=") + 1);
                if (StringUtils.isNotBlank(tmp)) {
                    return Integer.parseInt(tmp.trim());
                } else {
                    return defaultvalue;
                }
            } else {
                return defaultvalue;
            }
        } catch (Exception e) {
            return defaultvalue;
        }
    }

    /**
     * @param file 文件路径
     * @param name 行的键值中的键位
     * @param encode 文件编码方式. 如果为null,则默认为UTF8
     * @return 返回一行
     */
    public static String getLineString(String file, String name, String encode) {
        ValidateUtils.validateParams(file,name);
        if (StrUtils.isEmpty(encode)) {
            encode= EncodeUtils.UTF8;
        }
        return getLineString(new File(file), name, encode);
    }

    /**
     * 从文件中读取键为name的行的字符串.
     * @param f  文件,不允许为空.
     * @param name 键值对中的键位
     * @param encode 文件字符集
     * @return 存在name键位的行,如果name不存在或者文件不存在则返回空字符串.
     */
    public static String getLineString(final File f, String name, String encode) {
        ValidateUtils.validateParams(f,name,encode);
        String s = null;
        StringBuilder sb = new StringBuilder();
        if (f.exists()) {
            FileInputStream fileSm = null;
            InputStreamReader isr = null;
            BufferedReader br = null;
            try {
                fileSm = new FileInputStream(f);
                isr = new InputStreamReader(fileSm, encode);
                br = new BufferedReader(isr);
                while ((s = br.readLine()) != null) {
                    if (s.indexOf("=") != -1 && (s.split("="))[0].toString().trim().equals(name.trim())) {
                        sb.append(s);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException("It's error to read file.", e);
            } finally {
                try {
                    br.close();
                    isr.close();
                    fileSm.close();
                } catch (IOException ex) {
                    throw new RuntimeException("It's fail to close file io stream.", ex);
                }
            }
        }
        return sb.toString();
    }


    public static boolean setValue(String file, String name, String value, String encode) {
        String s = null;
        StringBuffer sb = new StringBuffer();
        File f = new File(file);
        String tempStr = "";
        boolean bret = false;//标记是否已经获取对应的name
        if (f.exists()) {
            FileInputStream fileSm = null;
            InputStreamReader isr = null;
            BufferedReader br = null;
            try {
                fileSm = new FileInputStream(f);
                isr = new InputStreamReader(fileSm, encode);
                br = new BufferedReader(isr);
                while ((s = br.readLine()) != null) {
                    if (s.indexOf("#") == -1 && s.indexOf("=") != -1) {
                        tempStr = s.split("=")[0];
                        if (tempStr.toString().trim().equals(name.trim())) {
                            s = name + "=" + value;
                            bret = true;
                        }
                    }
                    sb.append(s);
                    sb.append(PathUtil.getLineSeparator());
                }

                /**
                 * 如果不存在对应的设置项 则追加到文件最后
                 */
                if (!bret) {
                    StringBuffer temp = new StringBuffer();
                    temp.append(name).append("=").append(value);
                    sb.append(temp);
                    sb.append(PathUtil.getLineSeparator());
                }
            } catch (Exception e) {
                throw new RuntimeException("It's fail to close file io stream.", e);
            } finally {
                try {
                    br.close();
                    isr.close();
                    fileSm.close();
                } catch (IOException ex) {
                    throw new RuntimeException("It's fail to close file io stream.", ex);
                }
            }
            write(file, sb.toString(), encode);
        } else {
            return false;
        }
        return true;
    }

    /**
     * 读文件
     * @param file 文件的绝对路径
     * @return 整个文件的内容
     */
    public static String read(String file, String encode) {
        ValidateUtils.validateParams(file,encode);
        return read(new File(file), encode);
    }

    /**
     * 读取文件
     * @param file 文件
     * @param encode 文件字符集
     * @return 读取文件
     */
    public static String read(File file, String encode) {
        ValidateUtils.validateParams(file,encode);
        String tempStr = null;
        StringBuilder sb = new StringBuilder();
        if (file.exists()) {
            FileInputStream fileSm = null;
            InputStreamReader isr = null;
            BufferedReader br = null;
            try {
                fileSm = new FileInputStream(file);
                isr = new InputStreamReader(fileSm, encode);
                br = new BufferedReader(isr);
                while ((tempStr = br.readLine()) != null) {
                    sb.append(tempStr);
                    sb.append(PathUtil.getLineSeparator());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    br.close();
                    isr.close();
                    fileSm.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 读文件
     * @param file
     * @param encode
     * @return
     */
    public static int getLineCount(String file, String encode) {
        String s = null;
        int len = 0;
        File f = new File(file);
        if (f.exists()) {
            FileInputStream fileSm = null;
            InputStreamReader isr = null;
            BufferedReader br = null;
            try {
                fileSm = new FileInputStream(f);
                isr = new InputStreamReader(fileSm, encode);
                br = new BufferedReader(isr);

                while ((s = br.readLine()) != null) {
                    if (!s.trim().equals("")) {
                        len++;
                    }
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    br.close();
                    isr.close();
                    fileSm.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        return len;
    }

    /**
     * 写文件，，，将覆盖原有文件内容
     *
     * @param path
     * @param content
     */
    public static  void write(String path, String content, String encode) {
        OutputStreamWriter output = null;
        FileOutputStream fw = null;
        try {
            File f = new File(path);
            if (!f.exists()) {
                f.createNewFile();
            }
            fw = new FileOutputStream(f);
            output = new OutputStreamWriter(fw, encode);
            output.write(content);
            output.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                output.close();
                fw.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * 写文件 ，不会覆盖原有文件内容。在最后添加。
     * 注意： 在添加时前，会自动增加换行符。
     * @param path
     * @param content
     */
    public static  void apWrite(String path, String content, String encode) {
        apWrite(new File(path),content,encode,true);
    }

    /**
     * 2019年 恭喜你RD,你已经10岁了！
     * @param f
     * @param content
     * @param encode
     * @param withLineSeparator 是否在追加内容前，加上换行.
     */
    public static  void apWrite(File f, String content, String encode,boolean withLineSeparator) {
        write(f, content, encode, true, withLineSeparator);
    }

    /**
     * @param f 文件
     * @param content 内容
     * @param encode 编码
     * @param append 是否添加到文件末尾。 true 是， false 覆盖.
     * @param withLineSeparator 当 append==true生效. 意思是追加前，是否增加 换行符（换行）
     */
    public static  void write(File f, String content, String encode,boolean append,boolean withLineSeparator) {
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        try {
            mkParentDir(f);
            fos = new FileOutputStream(f, append);
            osw = new OutputStreamWriter(fos, encode);
            if (append && withLineSeparator) osw.write(PathUtil.getLineSeparator());
            osw.write(content);
            osw.flush();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            StreamUtil.close(osw);
        }
    }

    /**
     *@description 将字节数组写入文件.
     *@author thender email: bentengwu@163.com
     *@date 2019/7/3 11:35
     *@param file
     *@param content
     *@param append 是否追加到文件最后. true 追加  false 覆盖.
     *@return void
     **/
    public static final void write(File file, byte[] content, boolean append) {
        FileOutputStream fos = null;
        try {
            mkParentDir(file);
            fos = new FileOutputStream(file, append);
            fos.write(content);
            fos.flush();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            StreamUtil.close(fos);
        }
    }

    /**
     * 存在 为true
     *
     * @param path
     * @return
     */
    public static boolean dirExits(String path) {
        File f = new File(path);
        return dirExits(f);
    }


    public static final boolean exists(File f) {
        return f.exists();
    }

    public static final boolean exists(String f) {
        return exists(new File(f));
    }

    /**
     * 判断文件是否存在
     *
     */
    public static boolean dirExits(File f) {
        return exists(f);
    }

    public static final void mkParentDir(String file) {
        File _file = new File(file);
        mkParentDir(_file);
    }

    public static final void mkParentDir(File file) {
        mkDir(file.getParentFile());
    }

    /**
     * 创建路径
     * @param dir
     * @return
     */
    public static boolean mkDir(String dir) {
        File f = new File(dir);
        return mkDir(f);
    }

    /**
     * 创建路径
     * @param file
     * @return
     */
    public static boolean mkDir(File file) {
        return file.mkdirs();
    }

    /**
     * 创建新文件，当父目录不再是，直接创建父目录后在创建文件，如果文件已经存在，则直接返回true.
     * @param file 文件的绝对路径
     * @return 文件本来就存在时， true
     *         文件不存在时， ==createNewFile()
     */
    public static boolean mkFile(String file) {
        ValidateUtils.validateParams(file);
        return touch(new File(file));
    }

    public static boolean touch(File _file) {
        try {
            if (_file.exists()) {
                return true;
            }else{
                mkParentDir(_file);
                return _file.createNewFile();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void touch(String file) {
        mkFile(file);
    }

    /**
     * 检测文件中是否含有该传入字符串 true 包含 false 不包含
     * @return
     */
    public static boolean chickContent(String con, String key, String encode) {
        String s = read(con, encode);
        if (s.indexOf(key) == -1) {
            return false;
        }
        return true;
    }

    public static void print(Object obj) {
        System.out.println(obj);
    }

    public static void p(Object obj) {
        System.out.println(obj);
    }

    public static void main1(String[] args) {
//        String s = "E:\\tmp\\2013_08_07";
//        String aim = "e:\\tmp\\1";
//        UtilFile.copyDir(s, aim);
        try {

            mkParentDir("e:\\tmp\\asdf\\1\\2\\3.txt");
            mkFile("e:\\tmp\\asdf\\1\\2\\3.txt");
            File f = new File("e:\\tmp\\asdf\\1\\2\\3.txt");
            apWrite(f.getPath(), "asdf", EncodeUtils.UTF8);
            apWrite(f.getPath(), "asdf", EncodeUtils.UTF8);
            apWrite(f.getPath(), "asdf", EncodeUtils.UTF8);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


    public static void main2(String[] args) throws IOException {
        String real = EncodeUtils.hexEncode(FileUtils.readFileToByteArray(new File("E:\\temp\\11\\1.png")));
        System.out.println("==========");
        String after = EncodeUtils.hexEncode(FileUtils.readFileToByteArray(new File("E:\\temp\\1.png")));
        System.out.println(real.equals(after));
        System.out.println(real.length());
        System.out.println(after.length());
        System.out.println(real);
        System.out.println(after);
        System.out.println();
        System.out.println();
        if (!real.equals(after)) {
             char[] r =  real.toCharArray();
             char[ ]    a = after.toCharArray();
            for (int i = 0; i < r.length; i++) {
                if (r[i] == a[i]) {
                    System.out.print(r[i]);
                }else {
                    System.err.print(r[i]);
                }
            }
        }
    }

    public static void main4(String[] args) {
        byte[] a1 = new byte[100];
        a1[0] = 1;


        byte[] a2 = a1;

        System.out.println(a2);
        System.out.println(a1);
    }

    public static void main(String[] args) {
        String basename = FilenameUtils.getBaseName("e:\\temp\\1.png");
        String suffix = FilenameUtils.getExtension("e:\\temp\\1.png");
        String name = FilenameUtils.getName("e:\\temp\\1.png");
        System.out.println(basename);
        System.out.println(suffix);
        System.out.println(name);

    }
}
