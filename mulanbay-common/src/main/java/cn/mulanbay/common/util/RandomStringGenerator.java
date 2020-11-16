package cn.mulanbay.common.util;

import java.util.Random;

/**
 * 随机数处理类
 *
 * Copied from wxpay-scanpay-java-sdk-1.0
 * @see https://pay.weixin.qq.com/wiki/doc/api/app.php?chapter=11_1
 * @author rizenguo
 * @create 2014-10-29 14:18
 */
public class RandomStringGenerator {

    /**
     * 获取一定长度的随机字符串
     * @param length 指定字符串长度
     * @return 一定长度的字符串
     */
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

}
