package com.bentengwu.utillib.ai;

import com.bentengwu.utillib.String.StrUtils;
import com.bentengwu.utillib.UtilSleep;
import com.bentengwu.utillib.date.DateUtil;
import com.bentengwu.utillib.file.PathUtil;
import com.bentengwu.utillib.file.Rd;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.rmi.CORBA.Util;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 用途： 用于读取图片中的文字转化为字符串文字。
 * 测试： 效果并不是太理想，当图片背景相对复杂时就无法识别。 当文字中带有特殊字符时，识别也会失败。
 * 单例
 * @Author thender email: bentengwu@163.com
 * @Date 2020/4/16 10:13.
 *
 * 使用谷歌维护的 tesseract OCR 来作为文字识别的软件.
 * https://opensource.google/projects/tesseract
 * https://github.com/tesseract-ocr/tesseract
 *
 * 安装包下载地址 ： https://digi.bib.uni-mannheim.de/tesseract/tesseract-ocr-setup-4.00.00dev.exe
 *
 * 参考地址
 * https://segmentfault.com/a/1190000014086067
 * https://www.programcreek.com/java-api-examples/?class=net.sourceforge.tess4j.Tesseract&method=setLanguage
 * https://github.com/tesseract-ocr/tesseract/issues/1702
 *
 *
 * 2020年4月18日 --> 测试 tess4j 结果不是很理想. 决定改用命令行的方式来解析图片转化文字到文本后读取.
 */
public  class ImageTextConverter {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private static final ImageTextConverter instance = new ImageTextConverter();
    private static final String _env = System.getenv("TESSDATA_PREFIX");
    private static final String cmd = "tesseract -l $lan $png_file $text_file";

    private ImageTextConverter() {
    }

    public static final ImageTextConverter getInstance() {
        return instance;
    }

    public class ConverterRet {
        int code; // 1成功 -1 失败
        String retStr; //转化的结果
        Exception err; //转化失败的异常

        ConverterRet(String retStr) {
            code = 1;
            this.retStr = retStr;
        }

        ConverterRet(Exception ex) {
            code = -1;
            retStr = ex.getMessage();
            err = ex;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getRetStr() {
            return retStr;
        }

        public void setRetStr(String retStr) {
            this.retStr = retStr;
        }

        public Exception getErr() {
            return err;
        }

        public void setErr(Exception err) {
            this.err = err;
        }
    }



    public ConverterRet converter(File file, String lan, String dpi) {
        try {
            return new ConverterRet(newTesseract(lan,dpi).doOCR(file));
        } catch (TesseractException ex) {
            return new ConverterRet(ex);
        }
    }


    public ConverterRet converter(BufferedImage image) {
       return converter(image,"chi_sim","70");
    }

    public ConverterRet converter(BufferedImage image, String language, String dpi) {
        try {
            return new ConverterRet(cmdConverter(image,language));
        } catch (Exception ex) {
            return new ConverterRet(ex);
        }
    }

    /**
     * 通过命令行的方式来解析图片. 对应的图片将会被保存到系统的临时目录中.
     *
     * note: 比较重要, 由于硬盘的写入速度问题,需要1S的等待时间.才能读取到解析后的结果!
     *
     *@author thender email: bentengwu@163.com
     *@date 2020/4/19 0:26
     *@param image  图片数据
     *@param language  chi_sim
     *@return java.lang.String 整个字符串
     **/
    public String cmdConverter(BufferedImage image, String language) throws IOException {
        String systemTempPath = PathUtil.getTmpDir();
        String filePath = systemTempPath  +"saguadan-wow-" +  DateUtil.getCurrentStringDate("yyyyMMddHHmmss");
        File imgFile = new File(filePath);
        ImageIO.write(image, "png", imgFile);

        String runCmd = StrUtils.replaceEach(cmd,new String[]{"$lan","$png_file","$text_file"},new String[]{language,filePath,filePath});
        UtilCmd.cmd(runCmd);
        logger.debug("执行的命令是-->{}",runCmd);
        UtilSleep.sleep(1000);//fixme 如果硬盘写入速度不够,可能这里的时间还要调整.

        File txtFile = new File(filePath + ".txt");
        String strRet =  Rd.read(txtFile,"utf-8");

        // 清理临时文件
        Rd.rm(imgFile);
        Rd.rm(txtFile);
        return strRet;
    }

    @Deprecated
    protected Tesseract newTesseract(String lan, String dpi) {
        Tesseract tesseract = new Tesseract();
        if (StrUtils.isEmpty(lan)) {
            lan = "eng";
        }
        if (StrUtils.isEmpty(dpi)) {
            dpi = "70";
        }
        tesseract.setLanguage(lan);
        tesseract.setDatapath(_env);
        tesseract.setTessVariable("user_defined_dpi",dpi);
        return tesseract;
    }

    public static void main(String[] args) throws Exception {

//        long start = System.currentTimeMillis();
//        for (int i = 0; i < 1; i++) {
//            ConverterRet ret =  getInstance().converter(new File("F:\\cache\\2020-04-18-23-26-14.png"), "chi_sim", "280");
//            UtilSleep.sleep(10);
//            System.out.println(ret.retStr);
//        }
//        long end = System.currentTimeMillis();
//
//        System.out.println((start - end)/1000);

            BufferedImage image =   ImageIO.read(new File("F:\\cache\\2020-04-18-23-42-07.png"));
            ConverterRet ret = getInstance().converter(image);

            System.out.println(ret.retStr);
    }
}
