package com.bentengwu.utillib.http;

import com.bentengwu.utillib.String.StrUtils;
import com.bentengwu.utillib.code.EncodeUtils;
import com.bentengwu.utillib.cryptic.DESedeCoder;
import com.bentengwu.utillib.map.MapUtil;
import com.bentengwu.utillib.date.DateUtil;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @Description: http 工具类
 * @author Author: E-mail <a href="mailto:bentengwu@163.com">thender.xu</a>
 * @date Time: 2012-1-20 下午04:29:47
 */
public class HttpUtils {
	protected final static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	/**
	 * 会在request head 设置如下的值:
	 *          encrypt: 1/-1
	 *          sign: md5(body+randomStr)
	 *          length : len(body)
	 * 会在 request body 中增加 timestamp 和 randomStr
	 * @param requestMaps 请求参数和值的键值对;
	 * @param encryptKey 请求时,需要对包体加密用的3DES的key;
	 * @param url 请求的地址; 不支持https
	 * @return 请求返回的值(解密后的值)
	 */
	public final static String requestBy3DesPost(final String url,final Map<String, Object> requestMap,final String encryptKey) throws Exception {
		requestMap.put("timestamp", DateUtil.convertDate2String(new Date(), "yyyyMMddHHmmss"));
		requestMap.put("randomStr", StrUtils.getRandomNumLetters(8));

		String _body = MapUtil._2String(requestMap);
		byte[] _key = StrUtils.getBytesUTF8(encryptKey);
		byte[] requestBytes = DESedeCoder.encrypt(StrUtils.getBytesUTF8(_body), _key);
		//request body
		String body = EncodeUtils.hexEncode(requestBytes);
		//sign
		String sign = EncodeUtils.md5(body+requestMap.get("randomStr"));

		Map<String, String> header = new HashMap<String, String>();
		header.put("encrypt","1");
		header.put("sign",sign);
		header.put("length", body.length()+"");

		logger.debug("request body text-->{}, body-->{}, header-->{} ",_body,body,header);

		byte[] responseBytesCiphertext = requestByPost(url, StrUtils.getBytesUTF8(body), header, ContentType.OCTET_STREAM);
		byte[] _responseBytesText = DESedeCoder.decrypt(EncodeUtils.hexDecode(new String(responseBytesCiphertext)),_key) ;

		logger.debug("response body text--{}, body-->{}",new String(_responseBytesText,EncodeUtils.UTF8),new String(responseBytesCiphertext));

		return new String(_responseBytesText,EncodeUtils.UTF8);
	}

	/**
	 * 获取post内容 转为字符串
	 * @param in  输入流   如：ServletInputStream sin = request.getInputStream();
	 * @return  读取流后，转化为byte数组
	 * @throws IOException  抛出IO异常
	 */
	public static String getContentFromStream(InputStream in)
			throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int bLen = 0;
		while ((bLen = in.read(buffer)) > 0) {
			baos.write(buffer, 0, bLen);
		}
		String xml = new String(baos.toByteArray(),"UTF-8");
		baos.close();
		in.close();
		buffer = null;
		return xml;
	}
	
	
	/**
	 * 获取post内容 转为byte数组
	 * @param in  输入流   如：ServletInputStream sin = request.getInputStream();
	 * @return  读取流后，转化为byte数组
	 * @throws IOException  抛出IO异常
	 */
	public static byte[] getBytesFromStream(InputStream in)
			throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int bLen = 0;
		while ((bLen = in.read(buffer)) > 0) {
			baos.write(buffer, 0, bLen);
		}
		in.close();
		return baos.toByteArray();
	}
	
	/**
	 * 发送HTTP请求,并返回结果
	 * @param url
	 * @param headerMap
	 * @return
	 * @throws IOException 
	 * @throws HttpException 
	 */
	public static String requestByGet(String url,Map<String, String> headerMap)throws IOException{
		String response = null;
		GetMethod get = null;
		HttpClient client = null;
		try {
			client = new HttpClient();
			client.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, 
		    		new DefaultHttpMethodRetryHandler(0,false)); 
			client.getParams().setBooleanParameter("http.protocol.expect-continue", false);
			get = new GetMethod(url);
			get.setRequestHeader("Accept","application/xml");
			get.addRequestHeader("Connection", "close"); 
			if (headerMap != null) {
				Set<String> set = headerMap.keySet();
				for (String key : set) {
					get.setRequestHeader(key, headerMap.get(key));
				}
			}
			client.executeMethod(get);
			response = getContentFromStream(get.getResponseBodyAsStream());
		} catch (IOException e) {
			throw e;
		}finally{
			if(get!=null){
				get.releaseConnection();
			}
			client = null;
		}
		return response;
	}
	
	
	/**
	 * 按照http协议  发送数据
	 * @param url       接收地址
	 * @param bytes     数据
	 * @return    返回结果   new String(result,"utf-8") 后 如果为"success"  说明发送成功   否则发送失败
	 * @throws IOException   
	 */
	public static byte [] requestByPost(String url,byte [] bytes) throws IOException {
		return requestByPost(url, bytes, null, null);
	}

	/**
	 * @param url
	 * @param bytes 请求报文
	 * @param headerMap	需要增加的头参数
	 * @param contentType  default "text/xml; charset=UTF-8"
	 * @return response的内容
	 * @throws IOException 异常信息
	 */
	public static byte [] requestByPost(String url,byte [] bytes,final Map<String, String> headerMap,final String contentType) throws IOException{
		HttpClient client = new HttpClient();
		client.getParams().setBooleanParameter("http.protocol.expect-continue", false);
		client.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(0,false));
		PostMethod post = new PostMethod(url);
		post.addRequestHeader("Connection", "close");
		if (headerMap != null) {
			Set<String> set = headerMap.keySet();
			for (String key : set) {
				post.setRequestHeader(key, headerMap.get(key));
			}
		}
		// post方式
		post.setRequestEntity(new ByteArrayRequestEntity(bytes, StringUtils.isBlank(contentType)?"text/xml; charset=UTF-8":contentType));
		client.executeMethod(post);
		byte[] byteList = getBytesFromStream(post.getResponseBodyAsStream());
		post.releaseConnection();
		return byteList;
	}


	
	
	/**
	 * 请求url地址的内容   只支持utf-8
	 * @param url  		请求地址
	 * @param params
	 * @return  		返回Url地址的内容
	 * @throws IOException
	 */
	public static String requestByPost(String url,Map<String, String> params) throws IOException{
		HttpClient client = new HttpClient();
		client.getParams().setBooleanParameter("http.protocol.expect-continue", false);
		PostMethod post = new PostMethod(url);
		post.addRequestHeader("Connection", "close"); 
		Set<String> keySet = params.keySet();
		for(String key : keySet){
			String value = params.get(key);
			post.addParameter(key, value);
		}
		client.executeMethod(post);
		byte[] byteList = getBytesFromStream(post.getResponseBodyAsStream());
		post.releaseConnection();
		return new String(byteList,"UTF-8");
	}
	
	
	/**
	 * 请求一个地址 参数可以自己组装在url中。 不返回数据
	 * @param url  请求地址
	 * @param headerMap  
	 * @throws IOException
	 */
	public static void requestByGetNoResponse(String url,Map<String, String> headerMap)throws IOException{
		GetMethod get = null;
		HttpClient client = null;
		try {
			client = new HttpClient();
			client.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, 
		    		new DefaultHttpMethodRetryHandler(0,false)); 
			client.getParams().setBooleanParameter("http.protocol.expect-continue", false);
			get = new GetMethod(url);
			get.setRequestHeader("Accept","application/xml");
			get.addRequestHeader("Connection", "close"); 
			if (headerMap != null) {
				Set<String> set = headerMap.keySet();
				for (String key : set) {
					get.setRequestHeader(key, headerMap.get(key));
				}
			}
			client.executeMethod(get);
		} catch (IOException e) {
			throw e;
		}finally{
			if(get!=null){
				get.releaseConnection();
			}
			client = null;
		}
	}

	/**
	 * 先读  X-FORWARDED-FOR 失败后 读 X-Real-IP 再失败后 读 request.getRemoteAddr()
	 * @param request
	 * @return 获取失败： Failed to get IP
	 * 			获取成功： 返回对应的IP。
	 */
	public static final String getIp(HttpServletRequest request) {
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getHeader("X-Real-IP");
		}

		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}

		return ipAddress==null?"Failed to get IP":ipAddress;
	}


	public static void main(String[] args) throws IOException{
		String href = "http://www.baidu.com";
		String s = HttpUtils.requestByGet(href, null);
		System.out.println(s);
	}
}
