package com.bentengwu.utillib.socket.pojo;

import org.json.JSONObject;

/**
 * 请求WorkServer 的数据封装类．
 *@类名称：WorkServerPOJO.java
 *@文件路径：com.qymen.utillib.socket.pojo
 *@author：email: <a href="bentengwu@163.com"> 伟宏 </a> 
 *@Date 2013-12-9 下午2:19:04 
 *@since 1.0.1
 */
public class WorkServerPOJO {
	private String bizzCode;  //业务码
	private String socketAddr;	//workserver的地址
	private Integer socketPort=9999; //端口   默认端口为9999
	private JSONObject request;
	private JSONObject response;
	
	
	public String getBizzCode() {
		return bizzCode;
	}
	public String getSocketAddr() {
		return socketAddr;
	}
	public Integer getSocketPort() {
		return socketPort;
	}
	public JSONObject getRequest() {
		return request;
	}
	public JSONObject getResponse() {
		return response;
	}
	public void setBizzCode(String bizzCode) {
		this.bizzCode = bizzCode;
	}
	public void setSocketAddr(String socketAddr) {
		this.socketAddr = socketAddr;
	}
	public void setSocketPort(Integer socketPort) {
		this.socketPort = socketPort;
	}
	public void setRequest(JSONObject request) {
		this.request = request;
	}
	public void setResponse(JSONObject response) {
		this.response = response;
	}
}
