package com.bentengwu.utillib.Collection;

import java.util.Collection;

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
}
