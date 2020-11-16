package cn.mulanbay.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * map处理类
 *
 * @author fenghong
 * @create 2017-10-10 16:52
 */
public class MapUtil {

    /**
     * request默认拿到的是数组类型，这里转换为通用的原始参数
     * @param paraMap
     * @return
     */
    public static Map changeRequestMapToNormalMap(Map paraMap){
        // 参数Map
        // 返回值Map
        Map returnMap = new HashMap();
        if(paraMap==null||paraMap.isEmpty()){
            return returnMap;
        }
        Iterator entries = paraMap.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if(null == valueObj){
                value = "";
            }else if(valueObj instanceof String[]){
                String[] values = (String[])valueObj;
                for(int i=0;i<values.length;i++){
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length()-1);
            }else{
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }

}
