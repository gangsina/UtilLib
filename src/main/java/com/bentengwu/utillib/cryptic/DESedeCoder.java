package com.bentengwu.utillib.cryptic;

/**
 * 用于交互是和客户端进行加解密交互的数据加密
 *@类名称：DESedeCoder.java
 *@文件路径：com.yiwopai.common.utils.test
 *@内容摘要：
 *@author：email: <a href="bentengwu@163.com"> 伟宏 </a> 
 *@完成日期：
 *@Date 2013-11-29 下午2:25:49 
 *@since 1.6.3
 */
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import com.bentengwu.utillib.String.StrUtils;
import com.bentengwu.utillib.code.EncodeUtils;

/**
 * DESede加密. <br />
 * 
 * @类名称：DESedeCoder.java
 * @文件路径：com.yiwopai.common.utils.test
 * @author：email: <a href="bentengwu@163.com"> 伟宏 </a>
 * @Date 2013-12-9 下午4:16:52
 */
public class DESedeCoder {
	/**
	 * 密钥算法
	 * 
	 * @since 1.6.3
	 * */
	public static final String KEY_ALGORITHM = "DESede";

	/**
	 * 加密/解密算法/工作模式/填充方式
	 * 
	 * @since 1.6.3
	 * */
	public static final String CIPHER_ALGORITHM = "DESede/ECB/PKCS5Padding";

	/**
	 * 转换密钥
	 * 
	 * @param key
	 *            二进制密钥
	 * @return Key 密钥
	 * @since 1.6.3
	 * */
	public static Key toKey(byte[] key) throws Exception {
		// 实例化Des密钥
		DESedeKeySpec dks = new DESedeKeySpec(key);
		// 实例化密钥工厂
		SecretKeyFactory keyFactory = SecretKeyFactory
				.getInstance(KEY_ALGORITHM);
		// 生成密钥
		SecretKey secretKey = keyFactory.generateSecret(dks);
		return secretKey;
	}

	/**
	 * 加密数据
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @return byte[] 加密后的数据
	 * @since 1.6.3
	 * */
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		// 还原密钥
		Key k = toKey(key);
		// 实例化
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		// 初始化，设置为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, k);
		// 执行操作
		return cipher.doFinal(data);
	}

	/**
	 * @param data 需要加密的内容
	 * @param key	秘钥
	 * @return 加密后的hex编码过的字符串
	 * @throws Exception
	 */
	public static String enECBPKCS5Hex(byte[] data, byte[] key) throws Exception {
		if (data == null || key == null) {
			throw new RuntimeException("Params data or key is not validation");
		}
		return EncodeUtils.hexEncode(encrypt(data, key));
	}

	/**
	 * @param hex3des hex encode data with 3des encrypt
	 * @param key 3des key
	 * @return the decrypt with hex and 3des text
	 */
	public static final byte[] deECBPKCS5Hex(String hex3des, byte[] key) throws Exception {
		if (StrUtils.isEmpty(hex3des) || key == null || key.length == 0) {
			throw new RuntimeException("Params hex3des or key is not validation");
		}
		return decrypt(EncodeUtils.hexDecode(hex3des), key);
	}

	/**
	 * 解密数据
	 * @param data
	 *            待解密数据
	 * @param key
	 *            密钥
	 * @return byte[] 解密后的数据
	 * @since 1.6.3
	 * */
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		// 欢迎密钥
		Key k = toKey(key);
		// 实例化
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		// 初始化，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, k);
		// 执行操作
		return cipher.doFinal(data);
	}
	
	
	
	
	/**
	 * 加密函数
	 * 
	 * @param data
	 *            加密数据
	 * @param key
	 *            密钥
	 * @return 返回加密后的数据
	 */
	public static byte[] CBCEncrypt(byte[] data, byte[] key, byte[] iv) {

		try {
			// 从原始密钥数据创建DESKeySpec对象
			DESKeySpec dks = new DESKeySpec(key);

			// 创建一个密匙工厂，然后用它把DESKeySpec转换成
			// 一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(dks);

			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			// 若采用NoPadding模式，data长度必须是8的倍数
			// Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");

			// 用密匙初始化Cipher对象
			IvParameterSpec param = new IvParameterSpec(iv);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, param);

			// 执行加密操作
			byte encryptedData[] = cipher.doFinal(data);

			return encryptedData;
		} catch (Exception e) {
			System.err.println("DES算法，加密数据出错!");
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 解密函数
	 * 
	 * @param data
	 *            解密数据
	 * @param key
	 *            密钥
	 * @return 返回解密后的数据
	 */
	public static byte[] CBCDecrypt(byte[] data, byte[] key, byte[] iv) {
		try {
			// 从原始密匙数据创建一个DESKeySpec对象
			DESKeySpec dks = new DESKeySpec(key);

			// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
			// 一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(dks);

			// using DES in CBC mode
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			// 若采用NoPadding模式，data长度必须是8的倍数
			// Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");

			// 用密匙初始化Cipher对象
			IvParameterSpec param = new IvParameterSpec(iv);
			cipher.init(Cipher.DECRYPT_MODE, secretKey, param);

			// 正式执行解密操作
			byte decryptedData[] = cipher.doFinal(data);

			return decryptedData;
		} catch (Exception e) {
			System.err.println("DES算法，解密出错。");
			e.printStackTrace();
		}

		return null;
	}
	
	
	
	
	
	 /** 
     * CBC加密 
     * @param key 密钥 
     * @param keyiv IV 
     * @param data 明文 
     * @return Base64编码的密文 
     * @throws Exception 
     */  
    public static byte[] CBCdes3Encode(byte[] key, byte[] keyiv, byte[] data)  
            throws Exception {  
        Key deskey = null;  
        DESedeKeySpec spec = new DESedeKeySpec(key);  
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");  
        deskey = keyfactory.generateSecret(spec);  
        Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");  
        IvParameterSpec ips = new IvParameterSpec(keyiv);  
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);  
        byte[] bOut = cipher.doFinal(data);  
        return bOut;  
    }  
    
    
    /** 
     * CBC解密 
     * @param key 密钥 
     * @param keyiv IV 
     * @param data Base64编码的密文 
     * @return 明文 
     * @throws Exception 
     */  
    public static byte[] CBC3desDecode(byte[] key, byte[] keyiv, byte[] data)  
            throws Exception {  
        Key deskey = null;  
        DESedeKeySpec spec = new DESedeKeySpec(key);  
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");  
        deskey = keyfactory.generateSecret(spec);  
        Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");  
        IvParameterSpec ips = new IvParameterSpec(keyiv);  
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);  
        byte[] bOut = cipher.doFinal(data);  
        return bOut;  
    }  
    
	/**
	 * 进行加解密的测试
	 * 
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String str = "123456781234567812345678";//123456781234567812345678
		System.out.println("原文：" + str);
		String keyStrB = "kjkdSFJKO91238091laJSdkjodfoijwoejfldsjklfjq".substring(0,24);//123456781234567812345678
		System.out.println("密钥: "+keyStrB);
		byte[] key = keyStrB.getBytes();
		System.out.println("秘要的长度: " + key.length);
		// 加密数据
		byte[] data = DESedeCoder.encrypt(str.getBytes(), key);
		System.out.println("加密后：" + EncodeUtils.hexEncode(data));
		// 解密数据
		data = DESedeCoder.decrypt(data, key);
		System.out.println("解密后：" + new String(data));
		
	}
}
