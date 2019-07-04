package com.bentengwu.utillib.servlet;

import com.bentengwu.utillib.Collection.CollectionUtils;
import com.bentengwu.utillib.String.StrUtils;
import com.bentengwu.utillib.code.EncodeUtils;
import com.bentengwu.utillib.file.Rd;
import com.bentengwu.utillib.map.MapUtil;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于对异步ajax上传文件做支持
 *
 * @Author thender email: bentengwu@163.com
 * @Date 2019/7/3 17:49.
 */
public abstract class AjaxUploadSupport {

    /*Return Map Keys*/
    public static final String _key_Content_Disposition ="Content-Disposition";
    public static final String _key_name ="name";
    public static final String _key_filename ="filename";
    public static final String _key_Content_Type ="Content-Type";
    public static final String _key_token ="token";
    public static final String _key_savePath = "savePath";


    /**
     * @param in
     * @param savePath       当 {useOrginalName } 为true时, savePath作为目录地址使用,否则作为文件地址使用.
     * @param useOrginalName true	使用原先的文件名作为上行的文件名. false 不保留原先的文件名
     * @return java.util.Map<java.lang.String, java.lang.String>
     * eg:
     * Content-Disposition: form-data;
     * name="file";
     * filename="gitignore_global.txt"; 有些浏览器可能这里是全路径
     * Content-Type: text/plain;
     * token:xxx;
     * @description 将上行的输入流转化为文档存储到指定位置
     * @author thender email: bentengwu@163.com
     * @date 2019/7/2 23:59
     **/
    public final static Map<String, String> saveServletInputStreamToFile(final ServletInputStream in,
                                                                         final String savePath,
                                                                         boolean useOrginalName
    ) {
        try {
            Map<String, String> retMap = new HashMap<>();
            byte[] _1024args = new byte[1024];
            byte[] line = null;
            /*
             * 不知什么原因最后一行会被追加(hex)0d0a.
             * 如果拿到行就写入,会导致最后一行多写入了内容,需要延迟写入文件.
             * 新增一个延迟写入的字节数组. 当获取到下一行的时候,再写入上一行. 并把上一行复制到下一行.
             * */
            byte[] waitingWriteBytes = null;
            int lines = 0;
            int count = -1;

            String flag = null;  //第一行读到的就是flag.
            String lineStr = null;
            int fileEndLine = -1;
            int flagReadTimes = 0;

            String _savePath = savePath;
            StringBuilder afterFileStr = new StringBuilder();

            while ((count = in.readLine(_1024args, 0, 1024)) != -1) {
                if (line == null) {
                    line = new byte[count];
                    System.arraycopy(_1024args, 0, line, 0, count);
                } else {
                    line = CollectionUtils.mergeBytes(line, _1024args, count);
                }

                if (_1024args[count - 1] == 10) {//完成1行的读取后应该做点什么
                    lines = lines + 1; //行数+1;
                    lineStr = new String(line);

                    if (lines == 1) {
                        flag = lineStr.trim();
                        flagReadTimes++;
                    }

                    if (lines == 2 || lines == 3) {
                        retMap.putAll(MapUtil.kv2Map(lineStr, ":", ";"));
                        retMap.putAll(MapUtil.kv2Map(lineStr, "=", ";"));
                        _savePath = newFilePath(savePath, retMap.get("filename"), useOrginalName);// make sure the key's name.
                        retMap.put(_key_savePath, _savePath);
                    }


                    if (lines > 4 && lineStr.startsWith(flag) && flagReadTimes == 1) {
                        //已经不是文件内容
                        fileEndLine = lines - 1;
                        flagReadTimes++;
                    }

                    //这个是文件的内容,直接写入文件; 第五行(文件写入的第一行) 要用覆盖的方式写入.避免以前有同名文件.
                    if (lines > 5 && fileEndLine == -1) {
                        //采用了延迟写入,读到第六行内容的时候,才开始写入第五行到文件.
                        Rd.write(new File(_savePath), waitingWriteBytes, lines != 6);
                    }

                    if (flagReadTimes == 3) {
                        if (lineStr.startsWith(flag)) {
                            break;
                        } else {
                            afterFileStr.append(lineStr);
                        }
                    }

                    if (fileEndLine > 0 && flagReadTimes == 2) {
                        //这里是文件后面的内容行.
                        byte[] lastBytes = new byte[waitingWriteBytes.length - 2];
                        System.arraycopy(waitingWriteBytes, 0, lastBytes, 0, waitingWriteBytes.length - 2);//去除最后的换行.
                        Rd.write(new File(_savePath), lastBytes, true);

                        if (lineStr.startsWith(flag)) {
                            flagReadTimes++;
                        }
                    }

                    waitingWriteBytes = CollectionUtils.cloneBytes(line);//延迟数据,在下一次读取的时候写入文件.
                    _1024args = new byte[1024]; //重新创建对象
                    line = null; //清空本行的内容,用于写入下一行.
                }
            }

            //read the token value
            String[] strArys = StrUtils.replaceStr(afterFileStr.toString().trim(), "\r", "").split("\n");
            String token = strArys.length > 2 ? strArys[2] : null;
            retMap.put("token", token);

            return retMap;

        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    private static String newFilePath(String savePath, String orginalName, boolean useOrginName) {
        if (!useOrginName) {
            return savePath;
        } else {
            if (StrUtils.isEmpty(orginalName)) {
                return savePath;
            }
            String[] args = orginalName.trim().replaceAll("\\\\", "/").split("/");
            if (args.length == 1) {
                return savePath + File.separator + base64basename(args[0]);
            } else {
                return savePath + File.separator + base64basename(args[args.length - 1]);
            }

        }
    }


    private static String base64basename(String filename) {
        if (filename.indexOf(".") > 0) {
            String[] args = StringUtils.split(filename,".");
            return EncodeUtils.base64UrlSafeEncodeUTF8(args[0].getBytes()) + "." + args[1];
        }else {
            return EncodeUtils.base64UrlSafeEncodeUTF8(filename.getBytes());
        }
    }
}
