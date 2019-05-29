package com.bentengwu.utillib.mongo;

/**
 * @Author <a href="bentengwu@163.com">thender.xu</a>
 * @Date 2019/3/7 19:37.
 */
public class KV {
    String key;
    Object val;
    public KV(String _key, Object _val) {
        this.key = _key;
        this.val = _val;
    }

    String getKey() {
        return this.key;
    }

    Object getVal() {
        return this.val;
    }
}
