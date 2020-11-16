package mulanbay;

import cn.mulanbay.common.util.ClazzUtils;
import cn.mulanbay.pms.persistent.enums.AccountType;

import java.util.List;

public class TestClassNames {

    public static void main(String[] args) {

        String packageName = "cn.mulanbay.pms.persistent.domain";
        List<String> list = ClazzUtils.getClazzName(packageName, false);
        for (String s : list) {
            int n = s.lastIndexOf(".");
            System.out.println(s);
            System.out.println(s.substring(n + 1, s.length()));
        }
        System.out.println(AccountType.class.getPackage().getName());
    }

}
