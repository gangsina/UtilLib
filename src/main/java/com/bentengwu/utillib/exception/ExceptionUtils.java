package com.bentengwu.utillib.exception;

import org.slf4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 *@类名称：ExceptionUtils.java
 *@文件路径：com.qymen.utillib.exception
 *@author：email: <a href="xwh@ewppay.com"> thender </a> 
 *@Date 2015-8-31 上午11:41:56 
 * @since 1.0
 */

public class ExceptionUtils {
	private ExceptionUtils(){}
	
	/**
	 * 将异常转化为字符串.
	 *<br />
	 *@date 2015-8-31 上午11:45:37
	 *@author <a href="xwh@ewppay.com">thender</a>
	 *@param ex	异常
	 *@return	将异常转化为字符串返回.
	 *@since 1.0
	 */
	public static String exception2String(Exception ex)
	{
		StringWriter sw =null;
		PrintWriter pw = null;
		try {
			sw =  new StringWriter();
			pw	=	new PrintWriter(sw);
			ex.printStackTrace(pw);
			return sw.toString();
		} catch (Exception e) {
			return "";
		}finally{
			if(pw!=null)
				pw.close();
		}
		
	}


	public static RuntimeException newRE(String exMessage) {
		return new RuntimeException(exMessage);
	}

	public static RuntimeException newRE(Exception ex,boolean logit,Logger logger) {
		if (logit) {
			log(ex, logger);
		}
		return new RuntimeException(ex.getMessage(),ex);
	}

	public static void log(Exception ex, Logger logger) {
		logger.warn(ex.getMessage());
		logger.debug(ex.getMessage(), ex);
	}
}
