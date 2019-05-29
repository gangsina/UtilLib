package com.bentengwu.utillib;

import java.util.concurrent.TimeUnit;

/**
 * @Author <a href="bentengwu@163.com">thender.xu</a>
 * @Date 2019/3/11 18:17.
 */
public abstract class UtilSleep {

    /**
     * @param mill 1s =1000 mill
     */
    public static final void sleep(int mill) {
        try {
            TimeUnit.MILLISECONDS.sleep(mill);
        } catch (InterruptedException e) {
            System.out.println("[Warn]:"+e.getMessage());
        }
    }
}
