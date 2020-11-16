package mulanbay;

import cn.mulanbay.common.util.JsonUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestString2 {

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("name:", "sdsd");
        map.put("id", "233");
        String json = JsonUtil.beanToJson(map);
        System.out.println(json);
        String vv = findJsonValue("id",json);
        System.out.println(vv);

    }

    /*
     * 根据key和josn字符串取出特定的value
     */
    public static String findJsonValue(String key, String json) {
        String regex = "\"" + key + "\": \"(.*?)\"|(\\d*)";
        Matcher matcher = Pattern.compile(regex).matcher(json);
        String value = null;
        if (matcher.find()) {
            System.out.println(matcher.group(0));
            value = matcher.group().split("\\:")[1].replace("\"", "").trim();
        }
        return value;
    }

}
