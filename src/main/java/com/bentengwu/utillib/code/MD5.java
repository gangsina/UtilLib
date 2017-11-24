package com.bentengwu.utillib.code;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Title: MD5.java
 * 
 * @author email: <a href="bentengwu@163.com">徐伟宏</a>
 * @date 2013-3-29 下午03:10:21
 * @version : 1.6.3
 * @Description:
 * @since 1.6.3
 */
public class MD5 {

	/**
	 * 对content进行MD5
	 * @param content  明文
	 * @param key 密钥
	 * @param code 明文及密钥需要按照什么字符集进行解码
	 * @return 返回加密后的字符串，如果加密异常，返回null
	 * @since 1.6.3
	 */
	public static String md5(String content, String key, String code) {
		String tpCode = code;
		if (tpCode == null) {
			tpCode = "utf-8";
		}
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(content.getBytes(tpCode));
			String result = "";
			byte[] temp;
			temp = md5.digest(key.getBytes(tpCode));
			for (int i = 0; i < temp.length; i++) {
				result += Integer.toHexString(
						(0x000000ff & temp[i]) | 0xffffff00).substring(6);
			}
			return result;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 对content进行MD5
	 * @param content 明文
	 * @param code 明文及密钥需要按照什么字符集进行解码
	 * @return 返回加密后的字符串，如果加密异常，返回null
	 * @since 1.6.3
	 */
	public static String md5(String content, String code) {
		String tpCode = code;
		if (tpCode == null) {
			tpCode = "utf-8";
		}
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(content.getBytes(tpCode));
			String result = "";
			byte[] temp;
			temp = md5.digest();
			for (int i = 0; i < temp.length; i++) {
				result += Integer.toHexString(
						(0x000000ff & temp[i]) | 0xffffff00).substring(6);
			}
			return result;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 用hmac_md5 对字符串进行信息摘要 处理
	 *<br />
	 *@date 2013-12-4 下午5:27:15
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param key	密钥
	 *@param data	字符串的数据字节数组
	 *@return	用hmac_md5 处理的字符串字节数组
	 *@since 1.6.3
	 *@throws NoSuchAlgorithmException
	 */
	public static byte[] md5HmacBytes(byte[] key, byte[] data)
			throws NoSuchAlgorithmException {

		int length = 64;
		byte[] ipad = new byte[length];
		byte[] opad = new byte[length];
		for (int i = 0; i < 64; i++) {
			ipad[i] = 0x36;
			opad[i] = 0x5C;
		}
		byte[] actualKey = key; // Actual key.
		byte[] keyArr = new byte[length]; // Key bytes of 64 bytes length

		if (key.length > length) {
			actualKey = md5(key);
		}
		for (int i = 0; i < actualKey.length; i++) {
			keyArr[i] = actualKey[i];
		}

		if (actualKey.length < length) {
			for (int i = actualKey.length; i < keyArr.length; i++)
				keyArr[i] = 0x00;
		}

		byte[] kIpadXorResult = new byte[length];
		for (int i = 0; i < length; i++) {
			kIpadXorResult[i] = (byte) (keyArr[i] ^ ipad[i]);
		}

		byte[] firstAppendResult = new byte[kIpadXorResult.length + data.length];
		for (int i = 0; i < kIpadXorResult.length; i++) {
			firstAppendResult[i] = kIpadXorResult[i];
		}
		for (int i = 0; i < data.length; i++) {
			firstAppendResult[i + keyArr.length] = data[i];
		}

		byte[] firstHashResult = md5(firstAppendResult);

		byte[] kOpadXorResult = new byte[length];
		for (int i = 0; i < length; i++) {
			kOpadXorResult[i] = (byte) (keyArr[i] ^ opad[i]);
		}

		byte[] secondAppendResult = new byte[kOpadXorResult.length
				+ firstHashResult.length];
		for (int i = 0; i < kOpadXorResult.length; i++) {
			secondAppendResult[i] = kOpadXorResult[i];
		}
		for (int i = 0; i < firstHashResult.length; i++) {
			secondAppendResult[i + keyArr.length] = firstHashResult[i];
		}

		byte[] hmacMd5Bytes = md5(secondAppendResult);

		return hmacMd5Bytes;

	}
	
	/**
	 * 默认MD5  不需要key. 加这个方法是为了统一和IOS的MD5.
	 *<br />
	 *@date 2013-12-4 下午5:24:32
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param str	需要被MD5的字符串的字节数组
	 *@return	被MD5的字符串的字节数组.
	 *@throws NoSuchAlgorithmException
	 *@Since 1.6.3
	 */
	private static byte[] md5(byte[] str) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(str);
		return md.digest();
	}

	/**
	 * @param content 明文
	 * @param ciphertext 信息摘要
	 * @param key 密钥
	 * @param code 字符集
	 * @return 如果密文是明文按照密钥加密的结果，返回true 否则 返回false
	 * @since 1.6.3
	 */
	public static Boolean isEqual(String content, String ciphertext,
			String key, String code) {
		return ciphertext.equals(md5(content, key, code));
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("123456");
		System.out.print("md5:");
		System.out.println(MD5
				.md5("123456", EncodeUtils.DEFAULT_URL_ENCODING));
		
		System.out.println(MD5.md5("123456", "1234",
				EncodeUtils.DEFAULT_URL_ENCODING));
		System.out.println(new String(md5HmacBytes("1234".getBytes(), "12345678".getBytes())));
	}
}
