package com.bentengwu.utillib.Collection;

import com.bentengwu.utillib.String.StrUtils;
import com.bentengwu.utillib.reflection.UtilReflection;
import com.google.common.collect.Lists;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
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

    /**
     *@description  清理集合中的null对象
     *@author thender email: bentengwu@163.com
     *@date 2019/6/28 11:20
     *@param collection
     *@return int 被清理的对象个数.
     **/
    public static final int clearEmptyElement(final Collection collection) {
        return clearFieldEmptyElement(collection, null);
    }

    /**
     *@description 清除实例对象集合中， 一个或者多个实例字段fields为空的实例对象。
     * 同时实例对象本身为null或者toString为空也要清除。
     *
     * 使用举例 ：
     * Eras entity = new Eras();
     *     entity.setStart_date("aaa");
     *     entity.setEnd_date("bbb");
     *
     *     Set<Eras> set = new HashSet<>();
     *     set.add(entity);
     *
     *     entity = new Eras();
     *     entity.setStart_date("aaa");
     *     entity.setEnd_date("");
     *     set.add(entity);
     *
     *     entity = new Eras();
     *     entity.setStart_date("");
     *     entity.setEnd_date("bb");
     *     set.add(entity);
     *
     *     int size =  CollectionUtils.clearFieldEmptyElement(set,"start_date,end_date");
     *
     *@author thender email: bentengwu@163.com
     *@date 2019/6/28 11:34
     *@param collection  _class实例对象集合
     *@param fields	 多个逗号分隔.
     *@return int
     **/
    public static final int clearFieldEmptyElement(final Collection collection ,String fields) {
        Iterator iterator =  collection.iterator();
        List list = Lists.newArrayList();
        Object obj = null;
        String[] fileds_array = StrUtils.split(fields, ",");
        while (iterator.hasNext()) {
            if ((obj = iterator.next()) == null || obj.toString().trim().equals("")) {
                list.add(obj);
            }
            if (fileds_array != null && fileds_array.length > 0) {
                for (String field : fileds_array) {
                   Object fieldVal =  UtilReflection.getFieldValueFix(obj, field);
                    if (fieldVal == null || fieldVal.toString().equals("")) {
                        list.add(obj);
                        break;
                    }
                }
            }
        }
        collection.removeAll(list);
        return list.size();
    }


    /**
     *@description  byteA + byteB
     *@author thender email: bentengwu@163.com
     *@date 2019/7/3 16:24
     *@param bytesA
     *@param bytesB
     *@return byte[] byteA + byteB
     **/
    public static final byte[] mergeBytes(byte[] bytesA, byte[] bytesB){
        return mergeBytes(bytesA,bytesB,bytesB.length);
    }

    /**
     *@description  byteA + byteB(len)
     *@author thender email: bentengwu@163.com
     *@date 2019/7/3 16:24
     *@param bytesA
     *@param bytesB
     *@return byte[]  byteA + byteB(len)
     **/
    public static final byte[] mergeBytes(byte[] bytesA, byte[] bytesB, int len){
        byte[] mergeBytes = new byte[bytesA.length + len];
        System.arraycopy(bytesA,0,mergeBytes,0,bytesA.length);
        System.arraycopy(bytesB,0,mergeBytes,bytesA.length,len);
        return mergeBytes;
    }

    /**
     *@description  复制一个一样的队列.
     *@author thender email: bentengwu@163.com
     *@date 2019/7/3 17:23
     *@param bytes
     *@return byte[]
     **/
    public static final byte[] cloneBytes(byte[] bytes) {
        byte[] newBytes = new byte[bytes.length];
        System.arraycopy(bytes, 0, newBytes, 0, bytes.length);
        return newBytes;
    }

    /**
     *  判断数组中是否包含该对象,如果包含返回它的下标,如果不包含,则返回-1
     *@author thender email: bentengwu@163.com
     *@date 2020/5/8 12:59
     *  *@param objs	数组
     *@param item	检查的对象
     *@return int {item} 在 {objs} 中的下标.  如果不存在或者两个参数存在null的情况都返回-1.
     **/
    public static final int getIndex(Object[] objs, Object item) {
        if (objs == null || item == null) {
            return -1;
        }
        for (int i = 0; i < objs.length; i++) {
            if (objs[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

}
