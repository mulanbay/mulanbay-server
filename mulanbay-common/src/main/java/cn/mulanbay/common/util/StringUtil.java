package cn.mulanbay.common.util;

import java.util.Collection;
import java.util.UUID;

/**
 * String操作工具类
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class StringUtil {

    /**
     * 判断是否为空
     * @param s
     * @return
     */
    public static boolean isEmpty(String s){
        if(s==null||s.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 判断是否为空
     * @param s
     * @return
     */
    public static boolean isNotEmpty(String s){
        return !isEmpty(s);
    }


    /**
     * 判断是否为空
     * @param c
     * @return
     */
    public static boolean isEmpty(Collection c){
        if(c==null||c.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 判断是否为空
     * @param c
     * @return
     */
    public static boolean isNotEmpty(Collection c){
        return !isEmpty(c);
    }

    /**
     * 统计字符出现次数
     * @param target 被遍历的字符
     * @param c
     * @return
     */
    public static int countChar(String target,String c){
        int x=0;
        //遍历数组的每个元素
        for(int i=0;i<=target.length()-1;i++) {
            String getstr=target.substring(i,i+1);
            if(getstr.equals(c)){
                x++;
            }
        }
        return x;
    }

    public static String genUUID(){
        return UUID.randomUUID().toString();
    }

}
