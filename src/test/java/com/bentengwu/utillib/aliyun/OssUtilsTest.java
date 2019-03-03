package com.bentengwu.utillib.aliyun; 

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import com.bentengwu.utillib.date.DateUtil;
import com.bentengwu.utillib.file.UtilFile;
import com.bentengwu.utillib.reflection.UtilReflection;
import org.apache.commons.io.FileUtils;
import org.apache.poi.util.SystemOutLogger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

/** 
* OssUtils Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 2, 2019</pre> 
* @version 1.0 
*/ 
public class OssUtilsTest {
    private static final String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    private static final OSSClient ossClient = OssUtils.newOSSClient(endpoint, "LTAIb8erP0dPgVkc", "8DT957VFMLU24qdzPt1NyVH0LE3JjB");

    private static final String bucketName = "hhkj-private";
    private static  final String prefix = "operation/button/";

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: newListObjectsRequest(String bucketName, String prefix, String marker, String delimiter, int maxKeys) 
* 
*/ 
@Test
public void testNewListObjectsRequest() throws Exception {
    try {
        ListObjectsRequest objectsRequest = OssUtils.newListObjectsRequest(
                bucketName, prefix, null, "2019-03-01.txt", 10
        );
        System.out.println(objectsRequest.toString());
    } catch (Exception e) {
        e.printStackTrace();
        Assert.fail();
    }

}

/** 
* 
* Method: listOSSObjectSummary(OSSClient ossClient, ListObjectsRequest listObjectsRequest) 
* 
*/ 
@Test
public void testListOSSObjectSummary() throws Exception {
    String _prefix = prefix+"10000001/";
    ListObjectsRequest objectsRequest = null;
    String marker = null;
    ObjectListing objectListing;
    int pageIndex = 0;
    int i = 0;

    do{
        objectsRequest = OssUtils.newListObjectsRequest(
                bucketName, _prefix, marker, null, 10);
        objectListing = OssUtils.listOSSObjectSummary(ossClient, objectsRequest);
        marker = objectListing.getNextMarker();

        System.out.print("-------------------->>");
        System.out.print(++pageIndex);
        System.out.println("<<--------------------");

        List<OSSObjectSummary> ooss = objectListing.getObjectSummaries();
        for (OSSObjectSummary oos : ooss) {
            System.out.print("--->>");
            System.out.print(++i);
            System.out.print("<<---");
            System.out.println(oos.getKey());
        }

        for (String commonPrefix : objectListing.getCommonPrefixes()) {
            System.out.print("--->>");
            System.out.print(++i);
            System.out.print("<<---");
            System.out.println(commonPrefix);
        }
        
    }while (objectListing.isTruncated());

}

/** 
* 
* Method: download(OSSClient ossClient, String bucketName, String key, String localFilePath) 
* 
*/ 
@Test
public void testDownload() throws Exception {
    String key =prefix +"10000001/13977/2018-11-21.txt";
    String localOssFilePath = "E:\\tmp\\2018-11-21\\10000001_13977_2018-11-21.txt";
    FileUtils.touch(new File(localOssFilePath));
    TimeUnit.MILLISECONDS.sleep(10);
    OssUtils.download(ossClient, bucketName,key, localOssFilePath);
}

/** 
* 
* Method: downloadOSSObject(OSSClient ossClient, String bucketName, String key) 
* 
*/ 
@Test
public void testDownloadOSSObject() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getSimpleObjectMeta(OSSClient ossClient, String bucketName, String key) 
* 
*/ 
@Test
public void testGetSimpleObjectMeta() throws Exception {
    String key =prefix +"10000001/13977/2018-11-21.txt";
    SimplifiedObjectMeta som =  OssUtils.getSimpleObjectMeta(ossClient, bucketName, key);
    System.out.println("++++++++++++++++++++++++++++++\n"+som.getETag()+"\n"
            +DateUtil.convertDate2String(som.getLastModified(),DateUtil.LONG_DATE_FORMAT)+"\n"
    +som.getSize());
}

/** 
* 
* Method: getObjectMetaData(OSSClient ossClient, String bucketName, String key) 
* 
*/ 
@Test
public void testGetObjectMetaData() throws Exception { 
//TODO: Test goes here...
    String key =prefix +"10000001/13977/2018-11-21.txt";
    ObjectMetadata om =  OssUtils.getObjectMetaData(ossClient, bucketName, key);
    System.out.println("================================");
    System.out.println(UtilReflection.toStringRefl(om));
}


} 
