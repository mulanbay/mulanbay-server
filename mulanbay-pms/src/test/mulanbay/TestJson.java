package mulanbay;

import cn.mulanbay.common.util.JsonUtil;
import cn.mulanbay.pms.web.bean.request.CommonTreeSearch;

public class TestJson {

    public static void main(String[] args) {
        CommonTreeSearch ct = new CommonTreeSearch();
        ct.setUserId(11L);
        System.out.println(JsonUtil.beanToJson(ct));
    }

}
