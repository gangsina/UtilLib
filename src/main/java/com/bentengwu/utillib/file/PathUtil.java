package com.bentengwu.utillib.file;

import java.net.URL;

/**
 * 和路径相关的操作都放到这里.
 * @author 伟宏
 */
public class PathUtil {
	public static void main(String[] args) {
		System.out.println(getClassPath());
	}
	
	/**
	 *  读取class所在的文件绝对路径
	 *<br />
	 *@date 2015-4-9 上午10:18:30
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@return clazz所在文件路径
	 *@since 2.0.2
	 */
	public static String getResourcePath(Class clazz)
	{
		URL url = clazz.getResource("");
		return url==null?"":url.toString();
	}
	
	/**
	 * 获取classpath所在路径。项目的classpath路径.
         * 注意：
         *      该方法在swing项目中 打包成可运行的jar之后将无法获取classpath.
	 *<br />
	 *@date 2015-4-9 上午10:27:23
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@return 项目的classpath路径
	 *@since 2.0.2
	 */
	public static String getClassPath()
	{
		URL url = PathUtil.class.getResource("/");
		return url==null?"":url.toString();
	}
	
        /**
         * 
         * @return  临时文件路径 
         */
	public static String getTmpDir(){
		if(tmpDir.equals("")){
			 String property = "java.io.tmpdir";
			 tmpDir = System.getProperty(property);
		}
		return tmpDir;
	}
        
	
        /**
         *  获取当前路径
         * @return  当前路径
         */
       public static String getDir(){
           if(userdir==null){
             userdir  = System.getProperty("user.dir");
           }
            return userdir;
       }
       
    /**
     * @return 路径中的分隔符  windows 是"\\" linux 是 "/"
     */
    public static String getFileSeparator(){
        if(separator==null)
            separator=System.getProperty("file.separator");
        return separator;
    }

    /**
     * 返回行分隔符
     * @return  换行符.
     */
    public static String getLineSeparator(){
        if(lineSeparator==null)
            lineSeparator = System.getProperty("line.separator");
          return lineSeparator;
    }


    
/**
 * 获取系统多个路径之间的分隔符
 * @return  多个路径之间的分隔符 通常是 分号  ";".
 */
    public static String getPathSeparator(){
        if(pathSeparator==null){
            pathSeparator = System.getProperty("path.separator");
        }
        return pathSeparator;
    }


    /*
     * 获取操作系统名称
     * @return  操作系统名称
     */
    public static String getOsName(){
        if(osName==null){
            osName = System.getProperty("os.name");
        }
        return osName;
    }

    /**
     * 获取操作系统版本
     * @return  操作系统版本
     */
    public static String getOsVersion(){
        if(osVersion ==null){
            osVersion = System.getProperty("os.version");
        }
        return osVersion;
    }
    
    private static String osVersion = null;
    private static String osName = null;
    private static String pathSeparator =null;
    private static String lineSeparator = null;
    private static String separator =null;
    private static String tmpDir="";
    private static String userdir = null;
}
