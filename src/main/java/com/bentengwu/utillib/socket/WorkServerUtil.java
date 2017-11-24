package com.bentengwu.utillib.socket;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import com.bentengwu.utillib.code.EncodeUtils;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bentengwu.utillib.socket.pojo.WorkServerPOJO;

/**
 * 用于和WorkServer交互的工具类
 *@类名称：WorkServerUtil.java
 *@文件路径：com.qymen.utillib.socket.pojo
 *@author：email: <a href="bentengwu@163.com"> 伟宏 </a> 
 *@Date 2013-12-9 下午2:22:54 
 *@since 1.0.1
 */
public class WorkServerUtil {
	private final static Logger logger	=	LoggerFactory.getLogger("WorkServerUtil");
	/**
	 * 避免小白程序员实例化工具类
	 * @since 1.0.1
	 */
	private WorkServerUtil(){}
	
	
	/**
	 * 请求WorkServer处理对应业务的接口
	 *<br />
	 *@date 2013-12-9 下午2:24:52
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param pojo	请求WorkServer　处理对应业务的接口.
	 *@since 1.0.1
	 */
	@SuppressWarnings("resource")
	public static void doHandle(WorkServerPOJO pojo){
		Socket 			socket 	= 	null;
		OutputStream 	netOut	=	null;
		InputStream 	inputs	=	null;
		try{
			socket	= 	new Socket(pojo.getSocketAddr(), pojo.getSocketPort());
			netOut	=	socket.getOutputStream();
			/**********************设置业务类型||2013-12-9********************/
			netOut.write(pojo.getBizzCode().getBytes());
			if(pojo.getRequest()	!=	null){
				byte[] cb = pojo.getRequest().toString().getBytes(EncodeUtils.UTF8);
				netOut.write(cb);
			}
			netOut.write(10);
			netOut.flush();
			
			inputs = socket.getInputStream();
			int b, count = 0;
			StringBuilder resBuffer = new StringBuilder();
			while ((b = inputs.read()) != 10) {
				resBuffer.append((char) b);
				count++;
				if (count > 10485000) {
					throw new RuntimeException("The Byte Data Cann't Been Large 10485000B");
				}
			}
			
			JSONObject jb = new JSONObject(resBuffer.toString());
			pojo.setResponse(jb);
		}catch(Exception ex){
			logger.error(ex.getMessage(), ex);
			throw new RuntimeException("It's error to handle workserver request.");
		}finally{
			try {
				if(netOut	!=	null){
					netOut.close();
				}
				if(inputs	!=	null){
					inputs.close();
				}
				if (socket != null) {
					TimeUnit.MILLISECONDS.sleep(1);
					socket.close();
				}
			} catch (Exception ex) {
				logger.error("Fail To Close Socket{}", ex);
			}
		}
	}
	
	
	public static void main(String[] args) throws JSONException, IOException {
		WorkServerPOJO pojo = new WorkServerPOJO();
		pojo.setBizzCode("10000");
		pojo.setSocketAddr("192.168.1.100");
		WorkServerUtil.doHandle(pojo);
		System.out.println(pojo.getResponse());
		String	p1		=	"e:\\tmp\\crt.pfx";
		String 	p2		=	"e:\\tmp\\public.cer";
		System.out.println(pojo.getResponse().getString("privateCrt").getBytes().length);
		System.out.println(pojo.getResponse().getString("publicCrt").getBytes().length);
		byte[] crt		=	EncodeUtils.base64Decode(pojo.getResponse().getString("privateCrt"));
		byte[] pubCer	=	EncodeUtils.base64Decode(pojo.getResponse().getString("publicCrt"));
		System.out.println(crt.length);
		System.out.println(pubCer.length);
		FileUtils.writeByteArrayToFile(new File(p1), crt);
		FileUtils.writeByteArrayToFile(new File(p2), pubCer);
	}
}
