package com.bentengwu.utillib.file;

import com.bentengwu.utillib.CommonUtils;
import com.bentengwu.utillib.String.StrUtils;
import org.apache.commons.lang.StringUtils;

import java.net.URL;
import java.net.URLDecoder;

/**
 * 和路径相关的操作都放到这里.
 * @author 伟宏
 */
public class PathUtil {
    public static final String[] OSS = new String[]{"MS-DOS",
            "Windows 1.0 - 2.0",
            "Windows 3.0 – 3.1",
            "Windows 95",
            "Windows 98",
            "Windows ME - Millennium Edition",
            "Windows NT 31. - 4.0",
            "Windows 2000",
            "Windows XP",
            "Windows Vista",
            "Windows 7",
            "Windows 8",
            "Windows 10",
            "Windows Server",
            "Windows Home Server",
            "Windows CE",
            "Windows Mobile",
            "Windows Phone 7-10"};

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
     *  判断是否为windows系统
     *@author thender email: bentengwu@163.com
     *@date 2020/6/9 15:14 
     *  *@param 	
     *@return boolean  true 是  false 否
     **/
    public static final boolean isWinSystem() {
        String os = getOsName();
        for (String item : OSS) {
            if (os.equals(item)) {
                return true;
            }
        }
        return false;
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


    // 获取类实际路径
    public static String getClassAbsolutePath() {
        return getClassAbsolutePath("");
    }
    
    /**
     *  读取绝对路径
     *@author thender email: bentengwu@163.com
     *@date 2020/6/9 15:17 
     *  *@param suffix	 在绝对路径下添加后缀
     *@return java.lang.String
     **/
    public static String getClassAbsolutePath(String suffix) {
        String absolutePath = "";
        try {
            absolutePath = URLDecoder.decode(CommonUtils.class.getClassLoader()
                    .getResource("").getFile(), "utf-8")
                    + suffix;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (PathUtil.isWinSystem() && StringUtils.startsWith(absolutePath,"/")) {
            absolutePath = StringUtils.removeStart(absolutePath, "/");
        }
        return absolutePath;
    }

    
    private static String osVersion = null;
    private static String osName = null;
    private static String pathSeparator =null;
    private static String lineSeparator = null;
    private static String separator =null;
    private static String tmpDir="";
    private static String userdir = null;
}
