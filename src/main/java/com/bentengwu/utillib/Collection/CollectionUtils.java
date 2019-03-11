package com.bentengwu.utillib.Collection;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author <a href="bentengwu@163.com">thender.xu</a>
 * @Date 2017/11/24 16:22.
 */
public abstract class CollectionUtils {

    /**
     * @param collection 集合对象
     * @param args 需要被加入集合的数组
     */
    public static final void addAll(Collection collection,Object...args) {
        for (int i = 0; i < args.length; i++) {
            collection.add(args[i]);
        }
    }

    /**
     * @param collection 集合对象
     * @param args 需要被加入集合的数组
     */
    public static final void addAllArgs(Collection collection,Object[] args) {
        addAll(collection,args);
    }

    /**
     * 建一个带默认值的数组。
     * @param _defaultVal 默认值
     * @param length 长度
     * @return  对应长度的数组。
     */
    public static final <T> T[] newArray(T _defaultVal, int length) {
        List<T> list = new ArrayList<>(length);

        for (int i =0;i<length;i++) {
            list.add(_defaultVal);
        }

       return  (T[])list.toArray();
    }
}
