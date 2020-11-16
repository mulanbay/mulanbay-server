package mulanbay;

import cn.mulanbay.common.util.JsonUtil;
import cn.mulanbay.pms.persistent.enums.PeriodType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fenghong
 * @title: GenerateEnumKeyValueJson
 * @description: TODO
 * @date 2019/4/19:11 AM
 */
public class GenerateEnumKeyValueJson {

    public static void main(String[] args) {
        printSelfDefine();
    }

    private static void printSelfDefine() {
        List<KeyValue> list = new ArrayList<>();
        KeyValue kv1 = new KeyValue("YEAR", "年");
        KeyValue kv2 = new KeyValue("MONTH", "月");
        list.add(kv1);
        list.add(kv2);
        System.out.print(JsonUtil.beanToJson(list));
    }

    private static void printEnum() {
        List<KeyValue> list = new ArrayList<>();
        for (PeriodType bp : PeriodType.values()) {
            KeyValue kv = new KeyValue();
            kv.setId(bp.name());
            kv.setText(bp.getName());
            list.add(kv);
        }
        System.out.print(JsonUtil.beanToJson(list));
    }
}
