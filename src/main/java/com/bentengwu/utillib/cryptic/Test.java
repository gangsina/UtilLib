package com.bentengwu.utillib.cryptic;

import com.bentengwu.utillib.code.EncodeUtils;
import com.bentengwu.utillib.code.MD5;

/**
 * 
 * @类名称：Test.java
 * @文件路径：com.qymen.demos.des_3
 * @author：email: <a href="bentengwu@163.com"> thender </a>
 * @Date 2014-11-4 下午3:15:43
 * @since 3.0
 */

public class Test {
	public static void main(String[] args) throws Exception {
		String str = "123456";
		System.out.println("原文：" + 123456);
		str = MD5.md5(str, "utf-8");
		System.out.println("MD5:"+str);
		
		String keyStrB = "123456781234567812345678";
		System.out.println("密钥: "+keyStrB);
		
		byte[] key = keyStrB.getBytes("utf-8");
		System.out.println("秘要的长度: " + key.length);
		
		String iv = "12345678";
		System.out.println("iv:"+iv);
		// 加密数据
//		byte[] data = DESedeCoder.encrypt(str.getBytes(), key);
//		byte[] data = DESedeCoder.CBCdes3Encode(key, iv.getBytes(), str.getBytes());
		byte[] data = TripleDES.encrypt("12345678".getBytes(), "12a5@#2a122asdfaasoiqkwu".getBytes(), "556qweaq".getBytes());
		System.out.println("字符串加密后：" + EncodeUtils.base64Encode(data));
		// 解密数据
//		data = DESedeCoder.CBC3desDecode(key, iv.getBytes(), data);
		data = TripleDES.decrypt(data, "12a5@#2a122asdfaasoiqkwu".getBytes(), "556qweaq".getBytes());
		System.out.println("解密后：" + new String(data));
		
//		String beforeAppKey = "k{QW_!@LDKDJQLxuweihognDNFALQWIENSAKFsjdkalqke,n123jakdfnzlQQMLA{QW_!@LDKDJQLDNFALQWIENSAKF";
//		
//		String sysKey = "3140234b537875776569686f6e6748444b485155592640234a4c4a46534a4c4145574c5157454a44";
//		System.err.println("sysKey: " + sysKey);
//		String realSysKey = new String(Hex.decodeHex(sysKey.toCharArray())).substring(0, 24);
//		System.err.println("realSysKey : "+realSysKey);
//		
//		System.out.println("beforeAppKey : "+beforeAppKey);
//		String realAppKey = StringUtils.left(beforeAppKey, 24);
//		System.err.println("realAppKey : "+realAppKey);
//		
//		String appKey = EncodeUtils.hexEncode(DESedeCoder.encrypt(beforeAppKey.getBytes(), realSysKey.getBytes()));
//		System.err.println("appKey : "+ appKey);
		
		
		
		String s = "12a5@#2a122asdfaasoiqkwuiewrn1j341k";
		System.out.println(s.substring(0, 24).length());
		System.out.println();
		
	}
}
