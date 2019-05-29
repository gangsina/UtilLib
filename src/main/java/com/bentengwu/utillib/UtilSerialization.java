package com.bentengwu.utillib;

import com.bentengwu.utillib.file.Rd;
import com.bentengwu.utillib.stream.StreamUtil;
import org.apache.commons.io.FileUtils;

import java.io.*;

/**
 * 用于提供序列化和反序列化的工具类，以前写的也不知道在哪.
 * @Author <a href="bentengwu@163.com">thender.xu</a>
 * @Date 2019/3/13 11:59.
 */
public class UtilSerialization {
    private UtilSerialization() {}

    /**
     * 序列化对象保存到savePath中。
     * @param seriObj 需要被序列化的对象序列化到某个文件中
     * @param savePath 文件
     */
    public static final void serialization(Object seriObj, String savePath) {
        File file = new File(savePath);
        serialization(seriObj, file);
    }


    /**
     * 序列化对象到文件
     * @param seriObj 序列化对象
     * @param file
     */
    public static final void serialization(Object seriObj, File file) {
        ObjectOutputStream oos = null;
        Rd.mkParentDir(file);
        try {
            oos = new ObjectOutputStream(FileUtils.openOutputStream(file));
            oos.writeObject(seriObj);
        } catch (IOException e) {
            UtilLogger.logger.debug(e.getMessage(), e);
            UtilLogger.logger.warn(e.getMessage());
        } finally {
            StreamUtil.close(oos);
        }
    }

    /**
     * @param file 持久化保存的序列化文件
     * @return 反序列化后的对象. 当发生异常时，返回null.
     */
    public static final Object unSerialization(File file) {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
            return ois.readObject();
        } catch (IOException e) {
            UtilLogger.logger.debug(e.getMessage(), e);
            UtilLogger.logger.warn(e.getMessage());
        } catch (ClassNotFoundException e) {
            UtilLogger.logger.debug(e.getMessage(), e);
            UtilLogger.logger.warn(e.getMessage());
        } finally {
            StreamUtil.close(ois);
        }
        return null;
    }

    /**
     * @param file
     * @param _class
     * @param <T>
     * @return 返回对应的序列化对象.
     */
    public static final <T> T unSerialization(File file, Class<T> _class) {
        return (T) unSerialization(file);
    }
}
