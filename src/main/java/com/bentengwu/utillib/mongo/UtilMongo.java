package com.bentengwu.utillib.mongo;

import com.bentengwu.utillib.UtilAbstract;
import com.bentengwu.utillib.validate.ValidateUtils;
import org.apache.commons.lang.StringUtils;
import org.bson.Document;

/**
 * 提取一些支持Mongo操作的类!
 * @Author <a href="bentengwu@163.com">thender.xu</a>
 * @Date 2019/3/7 18:16.
 */
public  class UtilMongo extends UtilAbstract{

    public static final String _GROUP = "$group";
    public static final String _SUM = "$sum";
    public static final String _SORT = "$sort";
    public static final String _MATCH = "$match";

    public static final String _$ = "$";
    public static final String _DOT = ".";


    private UtilMongo(){}

    /**
     * 使用内部的字段值做条件!
     * @param colNames eg: "name","age"
     * @return eg: { "name":"$name","age":"$age" }
     * throws RuntimeException 当参数为空的时候。
     */
    public static final Document buildDoc(String... colNames) {
        ValidateUtils.validateParams(colNames);
        Document doc = new Document();
        for (String col : colNames) {
            doc.put(col, _$ + col);
        }
        return doc;
    }


    /**
     * @param groupAlias  eg: "_id"
     * @param colNames    eg: "name","age"
     * @return            eg: { "name":"$_id.name","age":"$_id.age" }
     */
    public static final Document buildDocWGroupAlias(String groupAlias, String[] colNames) {
        ValidateUtils.validateParams(colNames);
        ValidateUtils.validateParams(groupAlias);

        Document doc = new Document();
        for (String col : colNames) {
            doc.put(col, StringUtils.join(new String[]{_$, groupAlias, _DOT, col}));
        }
        return doc;
    }


    /**
     * @param groupAlias  eg: "_id"
     * @param colNames    eg: "name","age"
     * @return            eg: {"_id":{ "name":"$_id.name","age":"$_id.age" } }
     */
    public static final Document buildDocWGroupAlias2(String groupAlias, String[] colNames) {
        Document retDoc = new Document(groupAlias, buildDocWGroupAlias(groupAlias, colNames));
        return retDoc;
    }


    /**
     * @param key     eg: "_id"
     * @param subDoc  eg: { "name":"$name","age":"$age" }
     * @return        eg: {"_id" : { "name":"$name","age":"$age" }}
     */
    public static final Document buildDoc(String key, final Document subDoc) {
        Document doc = new Document(key, subDoc);
        return doc;
    }


    /**
     * @param key            eg: "_id"
     * @param subColNames    eg: ["name","age"]
     * @return               eg: {"_id" : { "name":"$name","age":"$age" }}
     */
    public static final Document buildDoc(String key, final String[] subColNames) {
        return buildDoc(key, buildDoc(subColNames));
    }


    /**
     * eg : _count1:{$sum:"$_id._count"}
     *
     * @param groupAlias    "_id"
     * @param sumRetAlias   "_count1"
     * @param sumColName    "_count"
     * @return   _count1:{$sum:"$_id._count"}
     */
    public static final KV buildSumDoc(String groupAlias, String sumRetAlias, String sumColName) {
        Document _sumSubDoc = new Document(_SUM, StringUtils.join(new String[]{_$, groupAlias, _DOT, sumColName}));
        KV _sumDoc = new KV(sumRetAlias, _sumSubDoc);
        return _sumDoc;
    }


    /**
     * @param sumAlias eg: "_count1"
     * @param sumCol    eg: "_count"
     * @return _count1:{$sum:_count}
     */
    public static final KV buildSumDoc(String sumAlias, String sumCol) {
        Document _sumSubDoc = new Document(_SUM, StringUtils.join(new String[]{_$, sumCol}));
        KV _sumDoc = new KV(sumAlias, _sumSubDoc);
        return _sumDoc;
    }

    /**
     * 将kv 加入到 doc中。
     * @param doc
     * @param kv
     */
    public static final void put(final Document doc, KV kv) {
        doc.put(kv.getKey(), kv.getVal());
    }
    /**
     *
     * @param _idAlias    指的是用于group by在那些字段组成 一个新的字段的别名。
     * @param _sumAlias   计算出结果后，给这个结果一个名字
     * @param _sumCol     用于sum的字段.
     * @param groupByCols  可以用sql来理解，用于group by的字段。
     * @return
     *  eg: { "$group" : { "_id" : { "agentName" : "$agentName", "btnName" : "$btnName" }, "_count" : { "$sum" : "$_count" } } }
     *
     */
    public static final Document _sumGroup(String _idAlias, String _sumAlias, String _sumCol,
                                        String[] groupByCols) {
        // group by cols and sum
        Document _id_groupbyColsDoc = buildDoc(_idAlias, groupByCols);
        KV _sumDoc = buildSumDoc(_sumAlias, _sumCol);
        put(_id_groupbyColsDoc, _sumDoc);

        Document groupByDoc = new Document(_GROUP, _id_groupbyColsDoc);
        return groupByDoc;
    }



    /**
     * @param sortCols  eg: _id.agentName,_id.btnName,_count
     * @param order eg: -1,-1,-1
     * @return      eg: $sort:{
         *                  "_id.agentName":-1,
         *                  "_id.btnName":-1,
         *                  _count:-1
                        }
     */
    public static final Document _sort(String[] sortCols, int[] order) {
        Document doc = new Document();
        for (int i = 0; i < sortCols.length; i++) {
            doc.put(sortCols[i], order[i]);
        }
        Document sortDoc = new Document(_SORT, doc);
        return sortDoc;
    }




    public static void main(String[] args) {
        String idAlias = "_id";
        String sumAlias = "_count";
        String sumCol = "_count";
        String[] groupByCols = new String[]{"agentName", "btnName"};
        Document doc = _sumGroup(idAlias, sumAlias, sumCol, groupByCols);
        System.out.println(doc.toJson());

        String[] sortCols = new String[]{"_id.agentName","_id.btnName","_count"};
        int[] order = new int[]{-1, -1, -1};

        System.out.println(_sort(sortCols,order).toJson());
    }


}
