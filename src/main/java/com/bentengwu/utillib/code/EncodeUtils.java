package com.bentengwu.utillib.code;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringEscapeUtils;

/**
 * <br />各种格式的编码加码工具类.
 * <br />集成Commons-Codec,Commons-Lang及JDK提供的编解码方法.
 *<br />
 *@类名称：EncodeUtils.java
 *@文件路径：com.qymen.utillib.code
 *@内容摘要：
 *@author：email: <a href="bentengwu@163.com"> 伟宏 </a> 
 *@完成日期：
 *@Date 2013-11-14 下午3:26:11 
 *@comments:
 */
public class EncodeUtils {
	/**
	 * utf8
	 */
	public static final String UTF8 = "UTF-8";
	/**
	 * utf-8
	 */
	public static final String DEFAULT_URL_ENCODING = UTF8;
	
	/**
	 * GBK
	 */
	public static final String GBK = "GBK";
	/**
	 * GB2312
	 */
	public static final String GB2312 = "GB2312";
	/**
	 * GB1830
	 */
	public static final String GB1830 = "GB1830";
	
	/**
	 * iso-8859-1
	 */
	public static final String ISO8859_1 = "iso-8859-1";
	
	/**
	 * Hex编码.
	 *<br />
	 *@date 2013-11-14 下午3:27:17
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param input	字节流
	 *@return	用Hex编码.
	 */
	public static String hexEncode(byte[] input) {
		return Hex.encodeHexString(input);
	}

	/**
	 * Hex解码.
	 *<br />
	 *@date 2013-11-14 下午3:27:50
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param input	需要被Hex解码的字符串
	 *@return	解码后的字符串
	 */
	public static byte[] hexDecode(String input) {
		try {
			return Hex.decodeHex(input.toCharArray());
		} catch (DecoderException e) {
			throw new IllegalStateException("Hex Decoder exception", e);
		}
	}

	/**
	 * Base64编码.
	 *<br />
	 *@date 2013-11-14 下午3:28:21
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param input	需要被编码的字符串
	 *@return	Base64编码.后的字符串
	 */
	public static String base64Encode(byte[] input) {
		if(input==null || input.length==0){
			return "";
		}
		return new String(Base64.encodeBase64(input));
	}

	/**
	 *  Base64编码, URL安全(将Base64中的URL非法字符如+,/=转为其他字符, 见RFC3548).
	 *<br />
	 *@date 2013-11-14 下午3:28:49
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param input 需要被编码的字符串
	 *@return	Base64编码.后的字符串
	 */
	public static String base64UrlSafeEncode(byte[] input) {
		return Base64.encodeBase64URLSafeString(input);
	}
	
	
	/**
	 * byte[] 数组按照默认字符集进行解码，然后改成UTF-8编码。
	 * <br />一般统一使用一种字符集,一般是UTF8,那就不需要用这种办法
	 *<br />
	 *@date 2013-11-14 下午3:29:28
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param input	需要被处理的字节数组
	 *@return	返回被编码后的字符串
	 */
	public static String base64UrlSafeEncodeUTF8(byte[] input){
		try{
			return new String(Base64.encodeBase64URLSafeString(input).getBytes(DEFAULT_URL_ENCODING),
					DEFAULT_URL_ENCODING);
		}catch (Exception e) {
			return base64UrlSafeEncode(input);
		}
	}
	
	/**
	 *  Base64解码.
	 *<br />
	 *@date 2013-11-14 下午3:31:00
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param input	需要被解码的字符串
	 *@return	解码后的字符串
	 */
	public static byte[] base64Decode(String input) {
		return Base64.decodeBase64(input);
	}

	/**
	 * URL 编码, Encode默认为UTF-8. 
	 *<br />
	 *@date 2013-11-14 下午3:31:17
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param input	需要被编码的字符串
	 *@return	返回编码后的字符串
	 */
	public static String urlEncode(String input) {
		return urlEncode(input, DEFAULT_URL_ENCODING);
	}

	/**
	 * URL 编码.
	 *<br />
	 *@date 2013-11-14 下午3:37:16
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param input		URL
	 *@param encoding	字符集
	 *@return	返回用字符集进行urlencode后的URL
	 */
	public static String urlEncode(String input, String encoding) {
		try {
			return URLEncoder.encode(input, encoding);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Unsupported Encoding Exception", e);
		}
	}

	/**
	 *  URL 解码, Encode默认为UTF-8. 
	 *<br />
	 *@date 2013-11-14 下午3:38:26
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param input	URL
	 *@return	用utf8进行urldecode一个Url.
	 */
	public static String urlDecode(String input) {
		return urlDecode(input, DEFAULT_URL_ENCODING);
	}

	/**
	 * 用制定的字符集进行urldecode
	 *<br />
	 *@date 2013-11-14 下午3:39:02
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param input			url
	 *@param encoding		字符集
	 *@return	返回urlDecode后的url.
	 */
	public static String urlDecode(String input, String encoding) {
		try {
			return URLDecoder.decode(input, encoding);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Unsupported Encoding Exception", e);
		}
	}
	
	/**
	 * 对Html进行转义
	 *<br />
	 *@date 2013-11-14 下午3:36:17
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param html	需要被转义的HTML
	 *@return	转以后的HTML
	 */
	public static String htmlEscape(String html) {
		return StringEscapeUtils.escapeHtml(html);
	}

	/**
	 * 取消HTML的转义
	 *<br />
	 *@date 2013-11-14 下午3:35:33
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param htmlEscaped	需要被取消转义的HTML
	 *@return	返回被取消转义后的HTML
	 */
	public static String htmlUnescape(String htmlEscaped) {
		return StringEscapeUtils.unescapeHtml(htmlEscaped);
	}
	/**
	 *  Xml 转义
	 *<br />
	 *@date 2013-11-14 下午3:32:26
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param xml	需要被转义的字符串
	 *@return	返回转义后的字符串
	 */
	public static String xmlEscape(String xml) {
		return StringEscapeUtils.escapeXml(xml);
	}

	/**
	 * Xml取消转义
	 *<br />
	 *@date 2013-11-14 下午3:31:52
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param xmlEscaped 需要被取消转义的XML
	 *@return	被取消转义的字符串
	 */
	public static String xmlUnescape(String xmlEscaped) {
		return StringEscapeUtils.unescapeXml(xmlEscaped);
	}

	/**
	 * 推荐使用当前类中的方法;
	 * @param content
	 * @return 成功签名的字符串
	 */
	public static final String md5(String content) {
		return DigestUtils.md5Hex(content);
	}
}
