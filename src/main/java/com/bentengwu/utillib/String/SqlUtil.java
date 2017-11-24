/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bentengwu.utillib.String;


import org.apache.commons.lang.StringEscapeUtils;

import java.util.regex.Pattern;

/**
 *
 * @author Thender.Xu
 */
public class SqlUtil {
    
    /*
     * 删除SQL中的注释
     */
    public static String rmAnnotation(String sql){
        Pattern p = Pattern.compile("(?ms)('(?:''|[^'])*')|--.*?$|/\\*.*?\\*/");
        String result = p.matcher(sql).replaceAll("$1");
        return result;
    }


    /**
     * 将字符串的指给转义下,让数据库能进行保存.
     * @param val
     * @return 返回转义后的字符串,保证字符串能够用SQL的形式保存到数据库中.
     */
    public static String escapeSql(String val){
        return StringEscapeUtils.escapeSql(val);
    }
}
