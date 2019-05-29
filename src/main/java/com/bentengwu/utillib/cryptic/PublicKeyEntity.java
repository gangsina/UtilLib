package com.bentengwu.utillib.cryptic;

/**
 *解析公钥对模进行base64处理后pojo类.
 *@类名称：PublicKeyEntity.java
 *@文件路径：com.qymen.utillib.cryptic
 *@author：email: <a href="bentengwu@163.com"> 伟宏 </a> 
 *@Date 2014-1-6 下午3:25:23 
 *@since 1.0
 */
public class PublicKeyEntity {
	private String modules;
	private String exponent;
	
	public String getModules() {
		return modules;
	}
	public String getExponent() {
		return exponent;
	}
	public void setModules(String modules) {
		this.modules = modules;
	}
	public void setExponent(String exponent) {
		this.exponent = exponent;
	}
}
