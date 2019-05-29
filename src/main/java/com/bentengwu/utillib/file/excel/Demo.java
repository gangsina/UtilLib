/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bentengwu.utillib.file.excel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.bentengwu.utillib.String.StrUtils;

/**
 *
 * @author 伟宏
 */
public class Demo {
    static void makeExcel_demo() throws IOException{
         String[] fieldNames = new String[]{"国家","厂商","机型","下载用户数","应用名称","结算比例","原始收入","合同比例","当前汇率","预估分成","统计日期"};
    	   List<String[]> ss=new ArrayList<String[]>();   
    	   ss.add(new String[]{"印尼","aux","0.00000023323"});
    	   ss.add(new String[]{"中国","asfd","0.00000023323"});
    	   List<ExcelSheetParam> list= new ArrayList<ExcelSheetParam>();
    	   ExcelSheetParam p =null;
    	   p = new ExcelSheetParam();
    	   p.setData(ss);
    	   p.setFieldName(fieldNames);
    	   p.setSheetName("aa");
    	   list.add(p);
    	   p = new ExcelSheetParam();
    	   p.setData(new ArrayList<String[]>());
    	   p.setFieldName(fieldNames);
    	   p.setSheetName("bb");
    	   list.add(p);
           ExcelUtils.makeExcel(list, "e:\\tmp\\1.xls");
    }
    
    static void getDataFromSheet_demo() throws IOException{
    	File file = new File("G:\\templates\\colleagueTemplates.xls");
    	List<String[]> list = ExcelUtils.getDataFromSheet(file, 0);
    	for(String[] args : list){
    		System.out.println(StrUtils.arrayToString(args));
    	}
    }
    
     public static void main(String[] args) throws Exception {
         makeExcel_demo();
//         getDataFromSheet_demo();
     }
}
