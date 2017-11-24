package com.bentengwu.utillib.cryptic;

/**
 *
 *@类名称：TripleDES.java
 *@文件路径：com.qymen.utillib.cryptic
 *@author：email: <a href="bentengwu@163.com"> 伟宏 </a> 
 *@Date 2015-5-23 下午5:35:26 
 * @since 2.0.2
 */

import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import com.bentengwu.utillib.code.EncodeUtils;

/**
 * 三重加密 3DES也作 Triple DES,
 * 
 * @author stone
 * @date 2014-03-10 02:14:37
 */
public class TripleDES {
	static {  
        Security.addProvider(new com.sun.crypto.provider.SunJCE());  
    }  
  
    private static final String MCRYPT_TRIPLEDES = "DESede";
    private static final String TRANSFORMATION = "DESede/CBC/PKCS5Padding";  
  
    public static byte[] decrypt(byte[] data, byte[] key, byte[] iv) throws Exception {  
        DESedeKeySpec spec = new DESedeKeySpec(key);  
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(MCRYPT_TRIPLEDES);  
        SecretKey sec = keyFactory.generateSecret(spec);  
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);  
        IvParameterSpec IvParameters = new IvParameterSpec(iv);  
        cipher.init(Cipher.DECRYPT_MODE, sec, IvParameters);  
        return cipher.doFinal(data);  
    }  
  
    public static byte[] encrypt(byte[] data, byte[] key, byte[] iv) throws Exception {  
        DESedeKeySpec spec = new DESedeKeySpec(key);  
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");  
        SecretKey sec = keyFactory.generateSecret(spec);  
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);  
        IvParameterSpec IvParameters = new IvParameterSpec(iv);  
        cipher.init(Cipher.ENCRYPT_MODE, sec, IvParameters);  
        return cipher.doFinal(data);  
    }  
  
    public static byte[] generateSecretKey() throws NoSuchAlgorithmException {  
        KeyGenerator keygen = KeyGenerator.getInstance(MCRYPT_TRIPLEDES);  
        return keygen.generateKey().getEncoded();  
    }  
    
    public static byte[] randomIVBytes() {  
        Random ran = new Random();  
        byte[] bytes = new byte[8];  
        for (int i = 0; i < bytes.length; ++i) {  
            bytes[i] = (byte) ran.nextInt(Byte.MAX_VALUE + 1);  
        }  
        return bytes;  
    }  
  
    public static void main(String args[]) throws Exception {  
        String plainText = "MinGW全称Minimalist GNU For Windows，是个精简的Windows平台C/C++、ADA及Fortran编译器，相比Cygwin而言，体积要小很多，使用较为方便。MinGW提供了一套完整的开源编译工具集，以适合Windows平台应用开发，且不依赖任何第三方C运行时库。";
        String desKey = "cdasdKS245*&868872132^%#";
        String iv = "kLDAkda*";
        
        System.out.println("原文："+plainText);
        System.out.println("密钥："+desKey);
        System.out.println("IV："+iv);
        
        byte[] regirl = plainText.getBytes();
        
        System.out.println(regirl.length);
        
        System.out.println("##############################");
        for(int i=0;i<regirl.length;i++)
        {
        	System.out.print(regirl[i]+" ");
        }
        System.out.println("");
        System.out.println("###########");
        
        byte[] crypt = encrypt(regirl, desKey.getBytes(), iv.getBytes());
        
        System.out.println("##############################");
        for(int i=0;i<crypt.length;i++)
        {
        	System.out.print(crypt[i]+" ");
        }
        System.out.println("");
        System.out.println("###########");
        
        String cryptString = EncodeUtils.base64Encode(crypt);
        System.out.println("方法 3DES加密 需要返回:" + cryptString);
        
        
        byte[] decrypt = decrypt(crypt, desKey.getBytes(), iv.getBytes());
        System.out.println("方法_3des解法 需要返回:" + new String(decrypt));
        
        
        String  cryptBase64_1 = "7ase3kEsq+PFuJcdH8mS7C1cohMgTeE37RLiOyw8BLM=";
        
        byte[] base = EncodeUtils.base64Decode(cryptBase64_1);
        System.out.println("##############################");
        for(int i=0;i<base.length;i++)
        {
        	System.out.print(base[i]+" ");
        }
        
        byte[] dcrypt = decrypt(base, desKey.getBytes(), iv.getBytes());
        System.out.println("final : "+new String(dcrypt));
    }  
  
}
