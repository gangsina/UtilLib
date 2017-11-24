package com.bentengwu.utillib.stream;

import java.io.IOException;
import java.io.InputStream;

/** 
 * 一般的流操作
 * @author email: <a href="bentengwu@163.com">徐伟宏</a> 
 * @date 2013-5-27 下午01:25:45 
 */
public class StreamUtil {
	/**
	 *<br /> ----------------------------方法说明-----------------------
	 *<br /> 读取inputStream流中的数据到stringbuilder
	 *<br />-----------------------------------------------------------
	 * @param in	inputStream
	 * @return	将流转化为字符串后进行返回
	 * @throws IOException
	 */
	public static  StringBuilder read(InputStream in) throws IOException{
		 StringBuilder  bw = new StringBuilder();
		  int i=0,c;
		  char b[] = new char[1024];
		  while ((c=in.read())!=-1){
			b[i] = (char)c;
			i++;
			if(i==1024){
				bw.append(b);
				b = new char[1024];
				i=0;
			}
		 }
		  bw.append(b);
		  return bw;
	}
}


