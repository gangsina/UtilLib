package com.bentengwu.utillib.String;

import com.google.common.collect.Lists;

import java.util.*;

/**
 *
 *@类名称：StringFormatUtil.java
 *@文件路径：com.qymen.utillib.String
 *@author：email: <a href="xwh@ewppay.com"> 伟宏 </a> 
 *@Date 2015-7-6 下午1:08:03 
 * @since 2.0.3
 */

public class StringFormatUtil {
    public final static List<String> charList = Lists.newArrayList("0","4","7","1","5","8","2","6","9","3");
	/**
     * 按照java的风格格式化一个对象字符串.
     * 首字母大写,遇到特殊字符则删除,并且紧跟的首字母大写.
     * @param colName 列表
     * @return 返回整理后的列名，用于类中的属性
     */
    public static String formatAsJavaStyple(String colName) {
        char[] arrays = colName.toLowerCase().toCharArray();
        StringBuffer result = new StringBuffer();
        boolean bret = false;  //默认为false  因为首字母要小写
        for (char a : arrays) {
            if (a >= 'a' && a <= 'z') { //如果是a到z之间
                if (bret) {
                    result.append((char) (a - 32));
                    bret = false;
                } else {
                    result.append(a);
                }
            } else { // 如果是特殊字符，那么下一个字母必须大写,打开大写开关
                if (charList.contains(a + "")) {  //如果特殊字符是数字，则继续往下检索
                    result.append(a);
                    bret = false;
                } else {
                    bret = true;
                }
            }
        }
        return result.toString();
    }
    
    


}
