package com.bentengwu.utillib.file.excel;
import java.util.List;

/**
 * 一个sheet的数据
 * <br />
 * 配合在使用lib库中的excel方法操作excel时使用.
 *<br />
 *@类名称：ExcelSheetParam.java
 *@文件路径：com.qymen.utillib.file.excel
 *@内容摘要：
 *@author：email: <a href="bentengwu@163.com"> 伟宏 </a> 
 *@完成日期：
 *@Date 2013-11-18 下午2:58:42 
 *@comments:
 */
public class ExcelSheetParam {
	/**
	 * sheet名
	 */
	private String sheetName;   //sheet名
	/**
	 * sheet的一行列名
	 */
	private String[] fieldName; //列名
	/**
	 * sheet的数据
	 */
	private List<String[]> data; //数据
	
	
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public String[] getFieldName() {
		return fieldName;
	}
	public void setFieldName(String[] fieldName) {
		this.fieldName = fieldName;
	}
	public List<String[]> getData() {
		return data;
	}
	public void setData(List<String[]> data) {
		this.data = data;
	}
	
}
