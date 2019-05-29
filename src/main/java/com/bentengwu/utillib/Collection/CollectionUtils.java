package com.bentengwu.utillib.Collection;

import com.google.common.collect.Lists;

import java.lang.reflect.Array;
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
        T[] array = (T[]) Array.newInstance(_defaultVal.getClass(), length);
        for (int i =0;i<length;i++) {
            array[i] = _defaultVal;
        }
        return array;
    }

    /**
     * @param fromArray 数据源数组
     * @param fromArrayStartIndex 开始copy的下标
     * @param toArray   接收数据的目标数组
     * @param toArrayStartIndex 开始接收数据的下标.
     * @param len 复制的长度（个数）
     * @param <T>
     * @return  接收完数据后的数组. 即 toArray
     *          null : fromArray == null || toArray == null
     * @throw [error]:fromArray.length < fromArrayStartIndex + len
     *        [error]:toArray.length < toArrayStartIndex + len
     */
    public static final <T> T[] copy(final T[] fromArray, int fromArrayStartIndex, final T[] toArray, int toArrayStartIndex, int len) {
        if (fromArray == null || toArray == null) {
            return null;
        }

        if (fromArray.length < fromArrayStartIndex + len) {
            throw new RuntimeException("[error]:fromArray.length < fromArrayStartIndex + len");
        }

        if (toArray.length < toArrayStartIndex + len) {
            throw new RuntimeException("[error]:toArray.length < toArrayStartIndex + len");
        }

        for (int i = 0; i < len; i++) {
            toArray[toArrayStartIndex + i] = fromArray[fromArrayStartIndex + i];
        }
        return toArray;
    }
}
