package com.bentengwu.utillib.aliyun;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 这个类用于支持OSS的操作.
 * @Author <a href="bentengwu@163.com">thender.xu</a>
 * @Date 2019/3/2 15:14.
 */
public abstract class OssUtils {

    /**
     * 获取oss的客户端对象.
     * @param endpoint  访问节点,使用时注意区分内外网.
     * @param accessKeyId
     * @param accessKeySecret
     * @return  oss的客户端对象
     */
    public static final OSSClient newOSSClient(String endpoint, String accessKeyId, String accessKeySecret) {
        return new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }
    /**
     * 获取列表分页的参数对象.
     * @param bucketName 文件库名称
     * @param prefix    前缀,可以理解为目录地址.
     * @param marker    分页开始的标志,可以理解为在所有行中的"第几行"开始获取数据.
     * @param delimiter 只能以"/"来结束,从目前情况来看. 如果是"/" 那只检索当前"prefix"下object,子目录下的不做检索. 而且文件对象和目录对象放在不同的集合中返回,很不方便.
     * @param maxKeys   最大行数
     * @return 列表分页的参数对象
     */
    public static final ListObjectsRequest newListObjectsRequest(
            String bucketName,
            String prefix,
            String marker,
            String delimiter,
            int maxKeys
    ) {
        return new ListObjectsRequest(bucketName, prefix, marker, delimiter, maxKeys);
    }
    /**
     * 分页获取.
     * 参考URL:
     * https://help.aliyun.com/document_detail/84841.html?spm=a2c4g.11186623.2.25.16c845dcuE0nUR#concept-84841-zh
     * @param ossClient
     * @param listObjectsRequest 分页的信息.
     * @return  获取的信息.
     */
    public static final ObjectListing listOSSObjectSummary(OSSClient ossClient ,ListObjectsRequest listObjectsRequest) {
        ObjectListing objectListing = ossClient.listObjects(listObjectsRequest);
        return objectListing;
    }


    /**
     * 下载文件.
     * @param ossClient 客户端对象
     * @param bucketName 文件库名字.
     * @param key   被下载对象
     * @param localFilePath 保存地址.
     * @return 被下载对象的meta信息.
     */
    public static final ObjectMetadata download(OSSClient ossClient ,String bucketName, String key, String localFilePath) {
        return download(ossClient,bucketName,key,new File(localFilePath));
    }

    public static final ObjectMetadata download(OSSClient ossClient ,String bucketName, String key, File localFile) {
        System.out.println("download : "+ key +"----->"+ localFile.getAbsolutePath());
        if (!localFile.exists()) {
            try {
                FileUtils.touch(localFile);
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        return ossClient.getObject(new GetObjectRequest(bucketName, key), localFile);
    }

    /**
     * 读取对应的文件
     * @param ossClient 客户端对象
     * @param bucketName 文件库名字.
     * @param key 文件名路径
     * @return  对应文件的字节数组.
     * @throws IOException
     */
    public static final byte[] downloadOSSObject(OSSClient ossClient,String bucketName, String key) throws IOException {
        OSSObject object = ossClient.getObject(bucketName, key);

        // 获取Object的输入流
        InputStream objectContent = object.getObjectContent();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int len;
        while ((len = objectContent.read(buff)) != -1) {
            bos.write(buff, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    /**
     * 获取文件简单头信息.
     * @param ossClient 客户端对象
     * @param bucketName 文件库名字.
     * @param key   文件名路径
     * @return 文件简单头信息.
     */
    public static final SimplifiedObjectMeta getSimpleObjectMeta(OSSClient ossClient,String bucketName, String key) {
        SimplifiedObjectMeta objectMeta = ossClient.getSimplifiedObjectMeta(bucketName, key);
        System.out.println(objectMeta);
        return objectMeta;
    }

    /**
     * 获取文件简单头信息.
     * @param ossClient 客户端对象
     * @param bucketName 文件库名字.
     * @param key   文件名路径
     * @return 文件简单头信息.
     */
    public static final ObjectMetadata getObjectMetaData(OSSClient ossClient,String bucketName, String key) {
        ObjectMetadata objectMeta = ossClient.getObjectMetadata(bucketName, key);
        System.out.println(objectMeta);
        return objectMeta;
    }

}
