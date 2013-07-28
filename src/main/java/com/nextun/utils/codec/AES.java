package com.nextun.utils.codec;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

public class AES {
  private static String defaultKey = "1234567890123456"; //16bit
  private static Hex hex = new Hex();
  /**
   * 对数据用AES算法加密
   * 
   * @param plainText
   *          要加密的字节数组
   * @param key
   *          密钥
   * @return 加密后的字节数组
   */
  public static byte[] encrypt(byte[] plainText, byte[] key) {
    try {
      SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
      byte[] decryptText = cipher.doFinal(plainText);
      return decryptText;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 对AES算法加密的数据解密
   * 
   * @param cipherText
   *          要解密的字节数组
   * @param key
   *          密钥
   * @return 解密后的字节数组
   */
  public static byte[] decrypt(byte[] cipherText, byte[] key) {
    try {
      SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.DECRYPT_MODE, skeySpec);
      byte[] plainText = cipher.doFinal(cipherText);
      return plainText;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  
  public static String encrypt(String plainText) throws Exception  {
    return byte2Hex(encrypt( plainText.getBytes(),defaultKey.getBytes("UTF-8")));
  }
  
  public static String decrypt(String cipherText) throws Exception {
    return new String(decrypt( hex2Byte(cipherText), defaultKey.getBytes("UTF-8")));
  }
  
  private static String byte2Hex(byte[] b) throws Exception {
    return new String(hex.encode(b));
  }
  
  private static byte[] hex2Byte(String b) throws Exception {
    return hex.decode(b.getBytes());
  }
  
  public static void main(String[] args) throws Exception {
    String a = "aaaafasdgqergqerger我";
/*    byte[] key = defaultKey.getBytes("utf-8");
    byte[] plainText = a.getBytes("utf-8");
    byte[] encryptText = encrypt(plainText,key);
    System.out.println("plainText:"+plainText);
    System.out.println("encrypt:"+encryptText);*/
    
    //String decryptText = decrypt(encryptText,key).toString();
    
    //System.out.println("decryptText:"+new String(decrypt(encryptText,key)));

    String b = encrypt(a);
    System.out.println("加密前"+a);
    System.out.println("加密中"+b);
    System.out.println("加密後"+decrypt(b));
  }
}