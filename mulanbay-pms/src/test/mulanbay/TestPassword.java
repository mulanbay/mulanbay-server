package mulanbay;

import cn.mulanbay.common.util.Md5Util;

public class TestPassword {

    public static void main(String[] args) {
        String md5Salt="aaabbbccc";
        System.out.println(Md5Util.encodeByMD5("123456" + md5Salt));
    }
}
