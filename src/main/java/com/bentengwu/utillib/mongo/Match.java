package com.bentengwu.utillib.mongo;

import org.bson.Document;

/**
 * 用于生成简单的Match 表达式。 用于Mongodb检索.
 * @Author <a href="bentengwu@163.com">thender.xu</a>
 * @Date 2019/3/8 17:23.
 */
public class Match {
    public static final String _$MATCH = UtilMongo._MATCH;
    public static final String _$IN = "$in";
    public static final String _$OR = "$or";
    public static final String _$AND = "$and";
    public static final String _$LT = "$lt";
    public static final String _$LTE = "$lte";
    public static final String _$GT = "$gt";
    public static final String _$GTE = "$gte";
    public static final String _$NE = "$ne";
    public static final String _$EQ = "$eq";
    public static final String _$CMP = "$cmp";

    private final Document match = new Document();

    public Match() {}

    /**
     * @return 返回构造对象。
     */
    public static final Match newMatch() {
        return new Match();
    }

    /**
     * 增加简单的key:val的检索条件.
     * @param key
     * @param val
     * @return 当前对象。
     */
    public Match addFilter(String key, String val) {
        match.put(key, val);
        return this;
    }

    /**
     * @param key
     * @param start 开始值
     * @param end   结束值
     * @param type  1:(), 2:[), 3:(] , 4:[]
     * @return 当前对象。
     */
    public Match addBetweenFilter(String key, Object start, Object end, int type) {
        String opt1 = _$GT;
        String opt2 = _$LT;
        if (type == 2) {
            opt1 = _$GTE;
        }else if (type == 3) {
            opt2 = _$LTE;
        }else if (type == 4) {
             opt1 = _$GTE;
             opt2 = _$LTE;
        }
        match.put(key, new Document(opt1, start).append(opt2, end));
        return this;
    }

    /**
     * 增加不相等的条件
     * @param key
     * @param val
     * @return  增加对应条件的Match对象.
     */
    public Match addNeFilter(String key, Object val) {
        match.put(key, new Document(_$NE,val) );
        return this;
    }

    /**
     * @return 返回当前规则的match 的Document对象。
     */
    public Document getMatchDoc() {
        return new Document(_$MATCH,this.match);
    }


    public String toString() {
        return getMatchDoc().toJson();
    }

    public static void main(String[] args) {
        String s = newMatch().addNeFilter("agentName", "--全部--")
        .addBetweenFilter("reportDate","2019-01-20","2019-01-21",4).toString();
        System.out.println(s);
    }
}
