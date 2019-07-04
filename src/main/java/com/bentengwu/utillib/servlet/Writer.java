package com.bentengwu.utillib.servlet;

import com.bentengwu.utillib.CommonUtils;
import com.bentengwu.utillib.code.EncodeUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @Author thender email: bentengwu@163.com
 * @Date 2019/7/3 18:51.
 */
public abstract class Writer {


    public static void write(HttpServletResponse resp,byte[] data) {
        write(resp, data, "text/html; charset=utf-8");
    }

    //  image/png
    //  "text/html; charset=utf-8"
    public static void write(HttpServletResponse resp, byte[] data, String contentType) {
        try {
            resp.setContentType(contentType);
            resp.getOutputStream().write(data);
            resp.getOutputStream().flush();
            resp.getOutputStream().close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static void writeStr(HttpServletResponse resp, String data) {
        write(resp,data.getBytes());
    }
}
