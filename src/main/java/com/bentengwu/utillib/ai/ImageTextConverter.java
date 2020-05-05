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

    public ConverterRet converter(File imageFile, String language) {
        return new ConverterRet(cmdConverter(imageFile.getAbsolutePath(),language));
    }

    /**
     *@description  读取图片文件中的简单文字信息. 复杂文字目前还无法识别,需要外部的通过机器学习后的语言包才可识别.
     *@author thender email: bentengwu@163.com
     *@date 2020/4/28 11:09
     *@param imageFile
     *@return com.bentengwu.utillib.ai.ImageTextConverter.ConverterRet
     **/
    public ConverterRet converterRet(File imageFile) {
        return converter(imageFile,"chi_sim");
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
        String filePath = writeImage(image);
        return cmdConverter(filePath, language);
    }

    /**
     * 通过命令行的方式来解析图片. 对应的图片将会被保存到系统的临时目录中.
     *
     * note: 比较重要, 由于硬盘的写入速度问题,需要1S的等待时间.才能读取到解析后的结果!
     *
     *@author thender email: bentengwu@163.com
     *@date 2020/4/19 0:26
     *@param filePath  图片路径
     *@param language  chi_sim
     *@return java.lang.String 整个字符串
     **/
    public String cmdConverter(String filePath, String language)  {

        String runCmd = StrUtils.replaceEach(cmd,new String[]{"$lan","$png_file","$text_file"},new String[]{language,filePath,filePath});
        UtilCmd.cmd(runCmd);

        File txtFile = new File(filePath + ".txt");
        String strRet =  loopRead(txtFile);

        Rd.rm(txtFile);
        return strRet;
    }

    
    public String writeImage(BufferedImage image) throws IOException
    {
        String systemTempPath = PathUtil.getTmpDir();
        String filePath = systemTempPath  +"saguadan-wow-" +  DateUtil.getCurrentStringDate("yyyyMMddHHmmss");
        writeImage(image, filePath);
        return writeImage(image, filePath);
    }

    public String writeImage(BufferedImage image,String filePath) throws IOException
    {
        File imgFile = new File(filePath);
        return writeImage(image,imgFile);
    }


    public String writeImage(BufferedImage image,File imgFile) throws IOException
    {
        ImageIO.write(image, "png", imgFile);
        return imgFile.getAbsolutePath();
    }


    private String loopRead(File txt) {
        String strRet =  null;
        for (int i = 0; i < 200; i++) {
            if (txt.exists() == false) {
                UtilSleep.sleep(200);
            }
            strRet = Rd.read(txt,"utf-8");
            if (StrUtils.isEmpty(strRet)) {
                UtilSleep.sleep(200);
            }else{
                logger.debug("报告: 解析图片等待了{}次!,相当于{}秒!",i, i*0.2);
                return strRet;
            }
        }
        return null;
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
