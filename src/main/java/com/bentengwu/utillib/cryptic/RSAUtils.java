package com.bentengwu.utillib.cryptic;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import com.bentengwu.utillib.code.EncodeUtils;

/**
 * 目前用于手机APP和服务端交互时加解密工具 从证书中读取RSA公私钥.及按照公私钥加解密
 * <p>
 * RSA公钥/私钥/签名工具包
 * </p>
 * <p>
 * 字符串格式的密钥在未在特殊说明情况下都为BASE64编码格式<br/>
 * 由于非对称加密速度极其缓慢，一般文件不使用它来加密而是使用对称加密，<br/>
 * 非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安全 <br />
 * 
 * @类名称：RSAUtils.java
 * @文件路径：com.push.core.util
 * @author：email: <a href="bentengwu@163.com"> 伟宏 </a>
 * @Date 2013-12-9 下午3:47:21
 * @since 1.6.3
 */
public class RSAUtils {
	/**
	 * 加密算法RSA
	 * 
	 * @version 1.6.3
	 */
	public static final String KEY_ALGORITHM = "RSA";
	public static final String KEY_ALGORITHM_CP = "RSA/ECB/PKCS1Padding";

	/**
	 * 签名算法
	 * 
	 * @version 1.6.3
	 */
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
	public static final String SIGNATURE_SHA1withRSA = "SHA1withRSA";

	/**
	 * 获取公钥的key
	 * 
	 * @version 1.6.3
	 */
	private static final String PUBLIC_KEY = "RSAPublicKey";

	/**
	 * 获取私钥的key
	 * 
	 * @version 1.6.3
	 */
	private static final String PRIVATE_KEY = "RSAPrivateKey";

	/**
	 * RSA最大加密明文大小
	 * 
	 * @version 1.6.3
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/**
	 * RSA最大解密密文大小
	 * 
	 * @version 1.6.3
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;

	/**
	 * <p>
	 * 生成密钥对(公钥和私钥)
	 * </p>
	 * 
	 * @param publicKeyCerFile
	 *            公钥的文件路径
	 * @param crtPfxFile
	 *            证书文件路径
	 * @param pfxPwd
	 *            证书的开启密码
	 * @return 存放证书公私钥的集合 keys : {@value #PUBLIC_KEY} {@value #PRIVATE_KEY}
	 * @throws Exception
	 * @version 1.6.3
	 */
	public static Map<String, Object> genKeyPair(String publicKeyCerFile,
			String crtPfxFile, String pfxPwd) throws Exception {
		RSAPublicKey publicKey = null;
		RSAPrivateKey privateKey = null;
		publicKey = CryptRestrict.getPublicRsaKey(publicKeyCerFile);
		privateKey = CryptRestrict.getPrivateCrtKey(crtPfxFile, pfxPwd);
		/********************** 梅枭峰给私钥||2013-11-29 ********************/
		// BigInteger mm = new BigInteger(EncodeUtils.hexDecode(m));
		// BigInteger ee = new BigInteger(EncodeUtils.hexDecode(e));
		// RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(mm,ee);
		// KeyFactory keyFactory = KeyFactory.getInstance("RSA",new
		// org.bouncycastle.jce.provider.BouncyCastleProvider());
		// privateKey = (RSAPrivateKey)keyFactory.generatePrivate(keySpec);
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}

	/**
	 * 根据证书的数据读取对应的公私钥 <br />
	 * 
	 * @date 2013-12-9 下午4:59:40
	 * @author <a href="bentengwu@163.com">伟宏</a>
	 * @param publicKeyFileData
	 *            公钥文件数据
	 * @param crtPfxFileData
	 *            pfx文件的数据
	 * @param pfxPwd
	 *            pfx文件的本地密码
	 * @return 公私钥的一个集合
	 * @throws Exception
	 * @since 1.6.3
	 */
	public static Map<String, Object> genKeyPair(byte[] publicKeyFileData,
			byte[] crtPfxFileData, String pfxPwd) throws Exception {
		RSAPublicKey publicKey = null;
		RSAPrivateKey privateKey = null;
		publicKey = CryptRestrict.getPublicRsaKey(publicKeyFileData);
		privateKey = CryptRestrict.getPrivateCrtKey(crtPfxFileData, pfxPwd);
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}

	/**
	 * 用私钥对信息生成数字签名
	 * 
	 * @param data
	 *            已加密数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * 
	 * @return
	 * @throws Exception
	 * @since 1.6.3
	 */
	public static String sign(byte[] data, String privateKey) throws Exception {
		byte[] keyBytes = EncodeUtils.base64Decode(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(privateK);
		signature.update(data);
		return EncodeUtils.base64Encode(signature.sign());
	}

	/**
	 * 校验数字签名
	 * 
	 * @param data
	 *            已加密数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @param sign
	 *            数字签名
	 * 
	 * @return
	 * @throws Exception
	 * @since 1.6.3
	 * 
	 */
	public static boolean verify(byte[] data, String publicKey, String sign)
			throws Exception {
		byte[] keyBytes = EncodeUtils.base64Decode(publicKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey publicK = keyFactory.generatePublic(keySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(publicK);
		signature.update(data);
		return signature.verify(EncodeUtils.base64Decode(sign));
	}

	/**
	 * 私钥解密
	 * 
	 * @param encryptedData
	 *            已加密数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * @return
	 * @throws Exception
	 * @since 1.6.3
	 */
	public static byte[] decryptByPrivateKey(byte[] encryptedData,
			String privateKey) throws Exception {
		byte[] keyBytes = EncodeUtils.base64Decode(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_CP);
		cipher.init(Cipher.DECRYPT_MODE, privateK);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher
						.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher
						.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}

	/**
	 * 公钥解密
	 * 
	 * @param encryptedData
	 *            已加密数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @return
	 * @throws Exception
	 * @since 1.6.3
	 */
	public static byte[] decryptByPublicKey(byte[] encryptedData,
			String publicKey) throws Exception {
		byte[] keyBytes = EncodeUtils.base64Decode(publicKey);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicK);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher
						.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher
						.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}

	/**
	 * 公钥加密
	 * 
	 * @param data
	 *            源数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @return
	 * @throws Exception
	 * @since 1.6.3
	 */
	public static byte[] encryptByPublicKey(byte[] data, String publicKey)
			throws Exception {
		byte[] keyBytes = EncodeUtils.base64Decode(publicKey);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicK);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData;
	}

	/**
	 * 私钥加密
	 * 
	 * @param data
	 *            源数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * @return
	 * @throws Exception
	 * @since 1.6.3
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String privateKey)
			throws Exception {
		byte[] keyBytes = EncodeUtils.base64Decode(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateK);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData;
	}

	/**
	 * 获取私钥
	 * 
	 * @param keyMap
	 *            密钥对
	 * @return
	 * @throws Exception
	 * @since 1.6.3
	 */
	public static String getPrivateKey(Map<String, Object> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return EncodeUtils.base64Encode(key.getEncoded());
	}

	/**
	 * 获取公钥
	 * 
	 * @param keyMap
	 *            密钥对
	 * @return 公钥
	 * @throws Exception
	 * @since 1.6.3
	 */
	public static String getPublicKey(Map<String, Object> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return EncodeUtils.base64Encode(key.getEncoded());
	}

	/**
	 * 读取公钥的Modulus <br />
	 * 
	 * @date 2013-12-9 下午5:21:36
	 * @author <a href="bentengwu@163.com">伟宏</a>
	 * @param keyMap
	 *            公私钥的集合
	 * @return 公钥的Modulus
	 * @since 1.6.3
	 */
	public static BigInteger getModulus(Map<String, Object> keyMap) {
		RSAPublicKey publicKey = (RSAPublicKey) keyMap.get(PUBLIC_KEY);
		return publicKey.getModulus();
	}

	/**
	 * 读取公钥的Exponent <br />
	 * 
	 * @date 2013-12-9 下午5:21:06
	 * @author <a href="bentengwu@163.com">伟宏</a>
	 * @param keyMap
	 *            公私钥的集合
	 * @return 公钥的Exponent
	 * @since 1.6.3
	 */
	public static BigInteger getPublicExponent(Map<String, Object> keyMap) {
		RSAPublicKey publicKey = (RSAPublicKey) keyMap.get(PUBLIC_KEY);
		return publicKey.getPublicExponent();
	}

	/**
	 * 根据公钥证书的数据,获取公钥证书的modules和exponent <br />
	 * 
	 * @date 2014-1-6 下午3:35:08
	 * @author <a href="bentengwu@163.com">伟宏</a>
	 * @param publicKeyFileData
	 *            证书的数据
	 * @return {@link PublicKeyEntity} 证书的modules和exponent
	 * @throws Exception
	 *             不符合证书规范的证书
	 */
	public static PublicKeyEntity getPublicKeyEntity(byte[] publicKeyFileData)
			throws Exception {
		RSAPublicKey publicKey = CryptRestrict
				.getPublicRsaKey(publicKeyFileData);
		String modules = EncodeUtils.hexEncode(publicKey.getModulus()
				.toByteArray());
		PublicKeyEntity entity = new PublicKeyEntity();
		entity.setModules(modules);
		entity.setExponent(EncodeUtils.hexEncode(publicKey.getPublicExponent()
				.toByteArray()));
		return entity;
	}

	public static PrivateKey readPKCS8privateKey(String privateKey)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] keyBytes = EncodeUtils.base64Decode(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		return privateK;
	}

	public static PublicKey readPKCS8PublicKey(String publicKey)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] keyBytes = EncodeUtils.base64Decode(publicKey);
//		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicK = keyFactory.generatePublic(x509KeySpec);
		return publicK;
	}

	public static void main(String[] args) throws Exception {
		// String s =
		// "LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tCk1JSURVakNDQXJ1Z0F3SUJBZ0lKQUlPamIrQzEyKzNOTUEwR0NTcUdTSWIzRFFFQkJRVUFNSG94Q3pBSkJnTlYKQkFZVEFtTnVNUXN3Q1FZRFZRUUlFd0o2YWpFTE1Ba0dBMVVFQnhNQ2FIb3hEREFLQmdOVkJBb1RBMlYzY0RFTQpNQW9HQTFVRUN4TURaWGR3TVJNd0VRWURWUVFERXdwMGFHVnVaR1Z5SjNoMU1TQXdIZ1lKS29aSWh2Y05BUWtCCkZoRmlaVzUwWlc1bmQzVkFNVFl6TG1OdmJUQWVGdzB4TXpFeU1USXdNekU1TWpkYUZ3MHhOREV5TURjd016RTUKTWpkYU1Ib3hDekFKQmdOVkJBWVRBbU51TVFzd0NRWURWUVFJRXdKNmFqRUxNQWtHQTFVRUJ4TUNhSG94RERBSwpCZ05WQkFvVEEyVjNjREVNTUFvR0ExVUVDeE1EWlhkd01STXdFUVlEVlFRREV3cDBhR1Z1WkdWeUozaDFNU0F3CkhnWUpLb1pJaHZjTkFRa0JGaEZpWlc1MFpXNW5kM1ZBTVRZekxtTnZiVENCbnpBTkJna3Foa2lHOXcwQkFRRUYKQUFPQmpRQXdnWWtDZ1lFQXd1Tzl2RHRtczdsaVpvMmxKL3puWnF6ZEd1UERBRkJYUkxjVDVwZEZaUWdCZWRqbQpUaHhyOXE3eEVQa2RnMUVrZTRpREhWN0UwbGo4RnpGMVJaYXVIVUpPNVJ0NlZBb09icnRiS0drZW0xc3lJSDVrCm0zcmR1L3UwajROQXlYb2xjVElzQTMyT01EOU5VUy9BWExmSzFCVG55MUkxelpRTlRZdVA3blpLcTZNQ0F3RUEKQWFPQjN6Q0IzREFkQmdOVkhRNEVGZ1FVcy9uVmZvQ0FxelIyRmZHWVFZS2RSc2tIYThVd2dhd0dBMVVkSXdTQgpwRENCb1lBVXMvblZmb0NBcXpSMkZmR1lRWUtkUnNrSGE4V2hmcVI4TUhveEN6QUpCZ05WQkFZVEFtTnVNUXN3CkNRWURWUVFJRXdKNmFqRUxNQWtHQTFVRUJ4TUNhSG94RERBS0JnTlZCQW9UQTJWM2NERU1NQW9HQTFVRUN4TUQKWlhkd01STXdFUVlEVlFRREV3cDBhR1Z1WkdWeUozaDFNU0F3SGdZSktvWklodmNOQVFrQkZoRmlaVzUwWlc1bgpkM1ZBTVRZekxtTnZiWUlKQUlPamIrQzEyKzNOTUF3R0ExVWRFd1FGTUFNQkFmOHdEUVlKS29aSWh2Y05BUUVGCkJRQURnWUVBYUJqakJjQjZGMTl5TTFUUVNrVVhGcDNqSUVBdkkrYmI4b1VWb3UwT3VVUGZoNE1EbmVUK1lyRHkKWXBOU3lvMmZGeWlrNkdpSVY4SzBjcFFOenhCd2E5T05lMVQwL2dBL1lRN2JrbzJnOGtkVVZ3clRkZFgra0pSMApIc2xKYlJmOVVFRXFCTnppWllxT3RiTkJXSTZVZHljVEM0aHBNT1hmRHBScEp5amRBUGM9Ci0tLS0tRU5EIENFUlRJRklDQVRFLS0tLS0K";
		// byte[] b = EncodeUtils.base64Decode(s);
		// String pfxFile =
		// "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";
		// byte[] pfxFileByte = EncodeUtils.base64Decode(pfxFile);
		// Map map = genKeyPair(b, pfxFileByte, "ewp2012");
		// BigInteger publicMo = getModulus(map);
		// BigInteger publicEx = getPublicExponent(map);
		// byte[] modules = publicMo.toByteArray();
		// byte[] e = publicEx.toByteArray();
		// System.out.println(EncodeUtils.hexEncode(modules));
		// System.out.println(EncodeUtils.hexEncode(e));

		// String ss =
		// "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDKBCUDVrjCpWxxXhuY2c9j8MO8R+L1zP8qqYKiBDlcSaHmC2ncxIRQzExOBEQv4ZDkuGinuL1INteno/4dzAoZOdzNzzA2F7JDpom/aU6Fba65wECc1FsmN8tqlzOFJLxTyobHFtNYG0dGxBWKrF4xq7eiNoON3F/Tlot/mlsciQIDAQAB";
		String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALZCDaoDCJOmTDpe0h3eYZjnNQv/VWKYf+dqPmoycHqUs9OO0c1wlAYRe/Vd3inBeV9vsqhuV3IVF+t29UlTD6LF1G8GmXEtdmLtDEuHUKjDguhlm/NGOWrOj8VywyVmxz0D/D4SZ0t2SQ4ADVns8rgin2APCwQ9Z75yyVtk8bChAgMBAAECgYAqB3dxMjvjGKFRgJZBrwugoAi6mIoPL42nRvfXs4P+sJX7fl7hWkm4UIXeYmQeCvC7Ul3Hu5JMy5J1iTwtSYs8IW7aNanEdcTg+jFXR/bSBPO7GirfiAb6/S7s+3aYgGda8SML6lppJ6f/SwO9my4Co47VW4wJKBSm2nZGjp7/ZQJBANzQSa6tquRzbvh4Lxy/fFprEx3panVUVby9nTvk0FVA31zgvyceDG3PAZqEgsefjo9GhS7CFof7JTtVACYdvb8CQQDTTPS8jmva/JI7lOqCNli1w3Vwyr7WGnlUQeJii2Y19pTMSfEZOCVnNt51KzDW50wsJXabfRsaeAzokS+2uumfAkAxTK+muE93Qe58uxiJqpbw+QcDnqGOII4j3ZHFVYjN//Xenq8O5L6Rpa4N+ZfZSd5iTrRdhCfTpFbjsE6gM/OxAkBMB0ukMOa0A98mGx4KPj3LIQo3zGvAJAR2AcLQTHI5hoDhxNVAishCjCadKC5JlS3+UHiN2AURkBKs99igOj7nAkAM5ST0hktO783wW5D+VzEDRfF5qcPLoXpb1j1p581/gXXsQoAi3AKdKWyKGGEgcvcQ/Dpmpwdqt4qRt6CFA21v";
		// byte[] byts = FileUtils.readFileToByteArray(new
		// File("e:\\tmp\\rsapub_pri.txt"));
		// byte[] publicBt = privateKey.getBytes();

		PrivateKey key = readPKCS8privateKey(privateKey);
		RSAPrivateKey priKey = (RSAPrivateKey) key;
		// key.
		System.out.println(key.getFormat());
		System.out.println(key.getAlgorithm());

		
		System.out.println("私钥 	: " + privateKey);
		System.out.println("私钥 权	: "+EncodeUtils.hexEncode(priKey.getModulus()
				.toByteArray()));
		System.out.println("私钥 模	: "+EncodeUtils.hexEncode(priKey.getPrivateExponent()
				.toByteArray()));
		
		
		String publicKey= "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDC4728O2azuWJmjaUn/OdmrN0a48MAUFdEtxPml0VlCAF52OZOHGv2rvEQ+R2DUSR7iIMdXsTSWPwXMXVFlq4dQk7lG3pUCg5uu1soaR6bWzIgfmSbet27+7SPg0DJeiVxMiwDfY4wP01RL8Bct8rUFOfLUjXNlA1Ni4/udkqrowIDAQAB";
		System.out.println("公钥 : "  + publicKey);
		
		
		byte[] bbaa = encryptByPublicKey("wu4g1S8J".getBytes("utf-8"), publicKey);
		System.out.println("asdfjakd  :"+EncodeUtils.hexEncode(bbaa));
		
		
		
		PublicKey publicK = readPKCS8PublicKey(publicKey);
		RSAPublicKey pubK = (RSAPublicKey) publicK;
		System.out.println(" 公钥模 :"+EncodeUtils.hexEncode(pubK.getModulus()
				.toByteArray()));
		System.out.println(" 公钥权: "+EncodeUtils.hexEncode(pubK.getPublicExponent()
				.toByteArray()));
		
		
		
		// PublicKeyEntity entity = getPublicKeyEntity(publicBt);

		// PublicKeyEntity entity = getPublicKeyEntity(b);
		// System.out.println(entity.getExponent());
		// System.out.println(entity.getModules());
	}

}
