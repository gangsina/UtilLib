package com.bentengwu.utillib;

/**
 * 用于强制类型转换.
 * @Author <a href="bentengwu@163.com">thender.xu</a>
 * @Date 2019/5/30 12:37.
 */
public abstract class UtilConversion {

    /**
     * 用于强制类型转换 .
     * @param _class 对象类型.
     * @param obj 对象实例
     * @return 转换后的类型
     */
    public static <T> T convert(Class<T> _class, Object obj) {
        if (_class == null || obj == null) {
            return null;
        }
        return (T)obj;
    }
}
