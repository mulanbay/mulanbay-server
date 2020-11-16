package cn.mulanbay.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

/**
 * MD5操作工具类
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class Md5Util {

	private static final Logger logger = LoggerFactory.getLogger(Md5Util.class);

	/**
	 * 十六进制下数字到字符的映射数组
	 */
	private final static String[] HEX_DIGITS = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	public static boolean validate(String pwd, String inputPwd) {
		if (pwd.equals(encodeByMD5(inputPwd))) {
			return true;
		} else {
			return false;
		}
	}

	public static String encodeByMD5(String originpwd) {
		if (originpwd != null) {
			try {
				// 创建具有指定算法名称的信息摘要
				MessageDigest md = MessageDigest.getInstance("MD5");
				// 使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
				byte[] res = md.digest(originpwd.getBytes());
				// 将得到的字节数组变成字符串返回
				String resStr = byteArrayToHexString(res);
				return resStr.toUpperCase();
			} catch (Exception e) {
				logger.error("获取["+originpwd+"]的MD5异常", e);
			}
		}
		return null;
	}

	public static String byteArrayToHexString(byte[] b) {
		StringBuffer res = new StringBuffer();
		for (int i = 0, j = b.length; i < j; i++) {
			res.append(byteToHexString(b[i]));
		}
		return res.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
            n = 256 + n;
        }
		int d1 = n / 16;
		int d2 = n % 16;
		return HEX_DIGITS[d1] + HEX_DIGITS[d2];
	}
}
