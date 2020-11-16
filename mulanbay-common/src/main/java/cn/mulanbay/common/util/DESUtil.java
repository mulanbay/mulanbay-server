package cn.mulanbay.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * DES加解密处理类
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class DESUtil {

    private static final Logger logger = LoggerFactory.getLogger(DESUtil.class);

	//private String key = "12345678";

	private static final String KEY_ALGORITHM = "DES";

	private static final String DEFAULT_CIPHER_ALGORITHM = "DES/CBC/PKCS5Padding";

	private static final String CHAR_SET = "utf-8";

	/**
     * 加密逻辑方法
     * @param message
     * @param key
     * @return
     * @throws Exception
     */
    private static byte[] encryptProcess(String message, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(CHAR_SET));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes(CHAR_SET));
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        return cipher.doFinal(message.getBytes(CHAR_SET));
    }

    /**
     * 解密逻辑方法
     * @param message
     * @param key
     * @return
     * @throws Exception
     */
    private static String decryptProcess(String message,String key) throws Exception {
            byte[] bytesrc =convertHexString(message);
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(CHAR_SET));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            IvParameterSpec iv = new IvParameterSpec(key.getBytes(CHAR_SET));
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            byte[] retByte = cipher.doFinal(bytesrc);
            return new String(retByte);
    }

    /**
     * 加密方法
     */
    public static  String encrypt(String message,String key){
        logger.info("加密原串为：" + message);
        String enStr = null;
        try {
             String orignStr=java.net.URLEncoder.encode(message, CHAR_SET);
             enStr=toHexString(encryptProcess(orignStr, key));
        } catch (Exception e) {
            logger.error("参数加密异常！", e);
        }
        return enStr;
    }


    /**
     * 解密方法
     */
    public static  String decrypt(String message,String key){
        String decStr = null;
        try {
            logger.debug("解密串：" + message);
            logger.debug("解密KEY:" + key);
            decStr = java.net.URLDecoder.decode(decryptProcess(message,key), CHAR_SET) ;
            logger.info("参数解密结果：" + decStr);
        }catch (Exception e) {
            logger.error("参数解密异常！", e);
        }
        return decStr;
    }

    /**
	 * 16进制数组数转化
	 *
	 * @param ss
	 * @return
	 */
	public static  byte[] convertHexString(String ss) throws Exception {
		byte[] digest = new byte[ss.length() / 2];
		for (int i = 0; i < digest.length; i++) {
			String byteString = ss.substring(2 * i, 2 * i + 2);
			int byteValue = Integer.parseInt(byteString, 16);
			digest[i] = (byte) byteValue;
		}
		return digest;
	}

	/**
	 * 十六进制数转化
	 *
	 * @param b
	 * @return
	 * @throws Exception
	 */
	public static  String toHexString(byte[] b) throws Exception {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String plainText = Integer.toHexString(0xff & b[i]);
			if (plainText.length() < 2) {
                plainText = "0" + plainText;
            }
			hexString.append(plainText);
		}

		return hexString.toString();
	}

}
