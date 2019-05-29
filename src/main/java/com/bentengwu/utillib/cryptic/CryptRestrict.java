package com.bentengwu.utillib.cryptic;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Enumeration;


/**
 * 这个类的作用是从OPENSSL生成的证书中读取公私钥.
 * @类名称：CryptNoRestrict.java
 * @文件路径：com.yiwopai.common.utils
 * @内容摘要：
 * @author：email: <a href="bentengwu@163.com"> 伟宏 </a>
 * @完成日期：
 * @Date 2013-11-28 下午12:46:33
 * @since 1.6.3
 */

public class CryptRestrict {
	/**
	 * 构造函数
	 * 
	 * @since 1.6.3
	 */
	private CryptRestrict() {
	}

	/**
	 * 获取公钥 <br />
	 * 
	 * @date 2013-11-28 下午2:52:32
	 * @author <a href="bentengwu@163.com">伟宏</a>
	 * @param CertFile
	 *            证书文件
	 * @return 从证书中返回公钥
	 * @throws Exception
	 * @since 1.6.3
	 */
	public static RSAPublicKey getPublicRsaKey(String CertFile)
			throws Exception {
		FileInputStream certfile = null;
		certfile = new FileInputStream(CertFile);
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate x509cert = null;
		try {
			x509cert = (X509Certificate) cf.generateCertificate(certfile);
		} catch (Exception ex) {
			if (certfile != null)
				certfile.close();
			throw ex;
		}
		RSAPublicKey pubkey = (RSAPublicKey) x509cert.getPublicKey();
		return pubkey;
	}
	
	/**
	 * 根据证书的内容 ,获取公钥
	 *<br />
	 *@date 2013-12-9 下午4:47:53
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param certData	证书的内容
	 *@return	公钥	
	 *@throws Exception
	 *@since 1.6.3
	 */
	public static RSAPublicKey getPublicRsaKey(byte[] certData)
			throws Exception {
		InputStream input = null;
		input	=	 new ByteArrayInputStream(certData);
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate x509cert = null;
		try {
			x509cert = (X509Certificate) cf.generateCertificate(input);
		} catch (Exception ex) {
			if (input != null)
				input.close();
			throw ex;
		}
		RSAPublicKey pubkey = (RSAPublicKey) x509cert.getPublicKey();
		return pubkey;
	}

	/**
	 * 获取私钥 <br />
	 * 
	 * @date 2013-11-28 下午2:52:56
	 * @author <a href="bentengwu@163.com">伟宏</a>
	 * @param KeyFile
	 *            证书文件
	 * @param PassWord
	 *            证书密码
	 * @return 返回私钥
	 * @throws Exception
	 * @since 1.6.3
	 */
	public static RSAPrivateCrtKey getPrivateCrtKey(String KeyFile,
			String PassWord) throws Exception {
		FileInputStream fiKeyFile = null;
		fiKeyFile = new FileInputStream(KeyFile);
		KeyStore ks = KeyStore.getInstance("PKCS12");
		try {
			ks.load(fiKeyFile, PassWord.toCharArray());
		} catch (Exception ex) {
			if (fiKeyFile != null)
				fiKeyFile.close();
			throw ex;
		}
		Enumeration myEnum = ks.aliases();
		String keyAlias = null;
		RSAPrivateCrtKey prikey = null;
		// keyAlias = (String) myEnum.nextElement();
		/* IBM JDK必须使用While循环取最后一个别名，才能得到个人私钥别名 */
		while (myEnum.hasMoreElements()) {
			keyAlias = (String) myEnum.nextElement();
			// System.out.println("keyAlias==" + keyAlias);
			if (ks.isKeyEntry(keyAlias)) {
				prikey = (RSAPrivateCrtKey) ks.getKey(keyAlias,
						PassWord.toCharArray());
				break;
			}
		}
		if (prikey == null) {
			throw new Exception("没有找到匹配私钥");
		} else {
			return prikey;
		}
	}
	
	
	/**
	 * 读取私钥
	 *<br />
	 *@date 2013-12-9 下午4:52:51
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param privateKeyFileData	证书的数据
	 *@param PassWord			证书本地密码
	 *@return	私钥
	 *@throws Exception
	 *@since 1.6.3
	 */
	public static RSAPrivateCrtKey getPrivateCrtKey(byte[] privateKeyFileData,
			String PassWord) throws Exception {
		InputStream privateKeyFileDataIn = null;
		privateKeyFileDataIn = new ByteArrayInputStream(privateKeyFileData);
		KeyStore ks = KeyStore.getInstance("PKCS12");
		try {
			ks.load(privateKeyFileDataIn, PassWord.toCharArray());
		} catch (Exception ex) {
			if (privateKeyFileDataIn != null)
				privateKeyFileDataIn.close();
			throw ex;
		}
		Enumeration myEnum = ks.aliases();
		String keyAlias = null;
		RSAPrivateCrtKey prikey = null;
		// keyAlias = (String) myEnum.nextElement();
		/* IBM JDK必须使用While循环取最后一个别名，才能得到个人私钥别名 */
		while (myEnum.hasMoreElements()) {
			keyAlias = (String) myEnum.nextElement();
			// System.out.println("keyAlias==" + keyAlias);
			if (ks.isKeyEntry(keyAlias)) {
				prikey = (RSAPrivateCrtKey) ks.getKey(keyAlias,
						PassWord.toCharArray());
				break;
			}
		}
		if (prikey == null) {
			throw new Exception("没有找到匹配私钥");
		} else {
			return prikey;
		}
	}

	/**
	 * 将ASCII字符串转换成十六进制数据
	 * 
	 * @param len
	 *            ASCII字符串长度
	 * @param data_in
	 *            待转换的ASCII字符串
	 * @param data_out
	 *            已转换的十六进制数据
	 * @since 1.6.3
	 */
	private static void Ascii2Hex(int len, byte data_in[], byte data_out[]) {
		byte temp1[] = new byte[1];
		byte temp2[] = new byte[1];
		for (int i = 0, j = 0; i < len; j++) {
			temp1[0] = data_in[i];
			temp2[0] = data_in[i + 1];
			if (temp1[0] >= '0' && temp1[0] <= '9') {
				temp1[0] -= '0';
				temp1[0] = (byte) (temp1[0] << 4);

				temp1[0] = (byte) (temp1[0] & 0xf0);

			} else if (temp1[0] >= 'a' && temp1[0] <= 'f') {
				temp1[0] -= 0x57;
				temp1[0] = (byte) (temp1[0] << 4);
				temp1[0] = (byte) (temp1[0] & 0xf0);
			}

			if (temp2[0] >= '0' && temp2[0] <= '9') {
				temp2[0] -= '0';

				temp2[0] = (byte) (temp2[0] & 0x0f);

			} else if (temp2[0] >= 'a' && temp2[0] <= 'f') {
				temp2[0] -= 0x57;

				temp2[0] = (byte) (temp2[0] & 0x0f);
			}
			data_out[j] = (byte) (temp1[0] | temp2[0]);

			i += 2;
		}

	}

	/**
	 * 将十六进制数据转换成ASCII字符串
	 * 
	 * @param len
	 *            十六进制数据长度
	 * @param data_in
	 *            待转换的十六进制数据
	 * @param data_out
	 *            已转换的ASCII字符串
	 * @since 1.6.3
	 */
	private static void Hex2Ascii(int len, byte data_in[], byte data_out[]) {
		byte temp1[] = new byte[1];
		byte temp2[] = new byte[1];
		for (int i = 0, j = 0; i < len; i++) {
			temp1[0] = data_in[i];
			temp1[0] = (byte) (temp1[0] >>> 4);
			temp1[0] = (byte) (temp1[0] & 0x0f);
			temp2[0] = data_in[i];
			temp2[0] = (byte) (temp2[0] & 0x0f);
			if (temp1[0] >= 0x00 && temp1[0] <= 0x09) {
				(data_out[j]) = (byte) (temp1[0] + '0');
			} else if (temp1[0] >= 0x0a && temp1[0] <= 0x0f) {
				(data_out[j]) = (byte) (temp1[0] + 0x57);
			}

			if (temp2[0] >= 0x00 && temp2[0] <= 0x09) {
				(data_out[j + 1]) = (byte) (temp2[0] + '0');
			} else if (temp2[0] >= 0x0a && temp2[0] <= 0x0f) {
				(data_out[j + 1]) = (byte) (temp2[0] + 0x57);
			}
			j += 2;
		}
	}
}
