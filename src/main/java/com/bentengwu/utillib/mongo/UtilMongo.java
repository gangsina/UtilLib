package com.bentengwu.utillib.mongo;

import com.bentengwu.utillib.UtilAbstract;
import com.bentengwu.utillib.validate.ValidateUtils;
import org.apache.commons.lang.StringUtils;
import org.bson.Document;

import javax.print.Doc;

/**
 * 提取一些支持Mongo操作的类!
 * @Author <a href="bentengwu@163.com">thender.xu</a>
 * @Date 2019/3/7 18:16.
 */
public  class UtilMongo extends UtilAbstract{

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
            doc.put(col, "$" + col);
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
            doc.put(col, StringUtils.join(new String[]{"$", groupAlias, ".", col}));
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
        Document _sumSubDoc = new Document("$sum", StringUtils.join(new String[]{"$", groupAlias, ".", sumColName}));
        KV _sumDoc = new KV(sumRetAlias, _sumSubDoc);
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
     * @param groupAlias   指的是用于group by在那些字段组成 一个新的字段的别名。
     * @param sumRetAlias  计算出结果后，给这个结果一个名字
     * @param sumColName   用于sum的字段.
     * @param cols         计算前的结果，用于计算的数据源。
     * @param groupByCols  可以用sql来理解，用于group by的字段。
     * @return
     */
    public static final Document[] _sumGroup(String groupAlias, String sumRetAlias, String sumColName,
                                        String[] cols, String[] groupByCols) {
        //group cols
        Document _id_colsDoc = buildDoc(groupAlias, cols);
        Document groupDoc = new Document("$group", _id_colsDoc);

        // group by cols and sum
        Document _id_groupbyColsDoc = buildDocWGroupAlias2(groupAlias, groupByCols);
        KV _sumDoc = buildSumDoc(groupAlias, sumRetAlias, sumColName);
        put(_id_groupbyColsDoc,_sumDoc);

        Document groupByDoc = new Document("$group", _id_groupbyColsDoc);

        Document[] retDoc = new Document[2];
        retDoc[0] = groupDoc;
        retDoc[1] = groupByDoc;

        return retDoc;
    }

    /**
     * @param cols  eg: _id.agentName,_id.btnName,_count
     * @param order eg: -1,-1,-1
     * @return      eg: $sort:{
         *                  "_id.agentName":-1,
         *                  "_id.btnName":-1,
         *                  _count:-1
                        }
     */
    public static final Document _sort(String[] cols, int[] order) {
        Document doc = new Document();
        KV kv = null;
        for (int i = 0; i < cols.length; i++) {
            doc.put(cols[i], order[i]);
        }
        Document sortDoc = new Document("$sort", doc);
        return sortDoc;
    }
}
