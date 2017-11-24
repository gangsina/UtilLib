/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bentengwu.utillib.code;

import java.io.UnsupportedEncodingException;


/**
 * 这个类用于识别文件的编码方式.
 * @ClassName: cn.thender.xu.oputils.code 
 * @Description: 编码类
 * @author Author: E-mail <a href="mailto:bentengwu@163.com">thender.xu</a>
 * @date Time: 2012-1-20 下午02:53:06
 */
public class CodeGuess {
		/**
		 * 支持的编码类型：
		 * "GB2312","GBK","BIG5","UTF8","UTF-8","UNICODE","EUC_KR","SJIS","EUC_JP","ASCII"
		 */
        public static String[] CODELIST= {"GB2312","GBK","BIG5","UTF8","UTF-8","UNICODE","EUC_KR","SJIS","EUC_JP","ASCII"};
        
        
        /**
         * 分析传入字符串的编码
         * @param con  需要判断的字符串
         * @return  返回字符串编码
         * @throws UnsupportedEncodingException
         */
        public static String getCoding(String con) throws UnsupportedEncodingException{
            String temp =null;
            byte[] bt = null;
            for(String cod : CODELIST){
                bt = con.getBytes(cod);
                temp = new String(bt);
                if(temp.equals(con)){
                    return cod;
                }
            }
           throw new RuntimeException("unknow code!");
        }
        
        
        /**
         * 判断字符串是否为指定字符集编码
         * @param con  字符串
         * @param code 指定的字符集编码
         * @return 1 为是  2为否 3 为无法识别
         */
        public static int isCoding(String con ,String code) throws UnsupportedEncodingException{
            if(!is(code)){
              return 3;
            }
            String temp =null;
            byte[] bt = null;
            bt = con.getBytes(code);
            temp = new String(bt);
            if(temp.equals(con)){
                return 1;
            }
            return 2;
        }
        
        /**
         * 查看是否为目前工具类支持的编码
         * @param code  编码
         * @return 工具类支持  返回true  否则返回false
         */
        public static boolean is(String code){
            for(String str : CODELIST){
                if(code.equalsIgnoreCase(str)){
                    return true;
                }
            }
            return false;
        }

    /**
     * 按照code的编码方式解码字符串
     * @param con
     * @param code
     * @return
     * @throws UnsupportedEncodingException
     */
        public static byte[] getByte(String con,String code) throws UnsupportedEncodingException{
            return con.getBytes(code);
        }

        /**
         * 以code的编码方式组装成字符串
         * @param bt
         * @param code
         * @return
         * @throws UnsupportedEncodingException
         */
        public static String getString(byte[] bt,String code) throws UnsupportedEncodingException{
            String s = new String(bt, code);
            return s;
        }


        /**
         * 将con以beforeCode的编码方式解码，然后以afterCode编码方式组装成字符串
         * @param con
         * @param beforeCode
         * @param afterCode
         * @return
         * @throws UnsupportedEncodingException
         */
        public static String getString(String con,String beforeCode,String afterCode) throws UnsupportedEncodingException{
            byte[] args = con.getBytes(beforeCode);
            String s = new String(args,afterCode);
            return s;
        }

        /**
         *  将con按照beforeCode的编码方式解码，然后以系统默认的编码方式组装成字符串并返回
         * @param con
         * @param beforeCode
         * @return
         */
        public static String getString(String con,String beforeCode) throws UnsupportedEncodingException{
            byte[] args = con.getBytes(beforeCode);
            String s = new String(args);
            return s;
        }
                
}
