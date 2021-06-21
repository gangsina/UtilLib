package com.bentengwu.utillib.ai;

import java.awt.*;

/**
 * @Author thender email: bentengwu@163.com
 * @Date 2020/4/19 0:06.
 */
public abstract class UtilCmd {
    /**
     *@description     用于执行命令
     *@author thender email: bentengwu@163.com
     *@date 2020/4/19 0:10
     *@param cmd  命令
     *@return void
     **/
    public static final void cmd(String cmd) {
        String[] args = new String[]{"cmd.exe", "/c", cmd};
        try {
            Runtime.getRuntime().exec(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     *  进程的名字杀死对应的进程,目前只支持Windows
     *@author thender email: bentengwu@163.com
     *@date 2020/6/8 19:46 
     *  *@param processName	  eg wow.exe
     *@return void
     **/
    public static final void killProcess(String processName) {
        String cmd = "taskkill /F /IM " +processName;
        UtilCmd.cmd(cmd);
    }


    public static void main(String[] args) throws AWTException {
//        String cmd = "tesseract -l chi_sim F:\\cache\\2020-04-18-23-42-07.png F:\\cache\\123";
//        cmd(cmd);
//        killProcess("WowClassic.exe");

    }
}
