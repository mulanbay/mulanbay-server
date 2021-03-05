package cn.mulanbay.pms.persistent.util;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.persistent.enums.DateGroupType;

import java.text.MessageFormat;

/**
 * mysql字段拼装处理
 *
 * @author fenghong
 * @create 2018-02-17 22:53
 */
public class MysqlUtil {

    /**
     * 时间函数
     *
     * @param field
     * @param dateGroupType
     * @return
     */
    public static String dateTypeMethod(String field, DateGroupType dateGroupType) {
        String method = null;
        switch (dateGroupType){
            case HOUR:
                method = "hour";
                break;
            case DAYOFMONTH:
                //统计都是哪些小时点练习的
                method = "hour";
                break;
            case DAYOFWEEK:
                //星期索引，周一=1，周二=2，周日=7
                return " WEEKDAY(" + field + ")+1 ";
            case WEEK:
                method = "weekofyear";
                break;
            case MONTH:
                method = "month";
                break;
            case YEAR:
                return " CAST(DATE_FORMAT(" + field + ",'%Y') AS signed) ";
            case DAY:
                return " CAST(DATE_FORMAT(" + field + ",'%Y%m%d') AS signed) ";
            case DAYCALENDAR:
                return " CAST(DATE_FORMAT(" + field + ",'%Y%m%d') AS signed) ";
            case YEARMONTH:
                return " CAST(DATE_FORMAT(" + field + ",'%Y%m') AS signed) ";
            case HOURMINUTE:
                //这里是除以100，不是60，比如：7.5代表的是7点50分，而不是7点半
                return "(CAST(DATE_FORMAT(" + field + ",'%H') AS signed)+CAST(DATE_FORMAT(" + field + ",'%i') AS signed)/100)";
            default:
                throw new ApplicationException(PmsErrorCode.UN_SUPPORT_DATE_GROUP_TYPE);
        }
        return " " + method + "(" + field + ") ";
    }

    /**
     * 替换调绑定的值
     * 如果sqlContent有单引号，MessageFormat.format会把单引号删除导致sql异常
     * 解决方法：sqlContent的单引号用两个单引号代替
     * 需要在之前就替换掉，如果放在replaceBindValues方法中替换，导致判断逻辑比较复杂，因为有些单引号其实不需要替换的，如select '早餐' as name
     * 如果sql语句中没有需要替换的占位符，那么就不需要加单引号
     * 比如：原来是select DATE_FORMAT(startDate,'%Y-%m-%d') as date...修改为select DATE_FORMAT(startDate,''%Y-%m-%d'') as date ...
     *
     * @param sqlContent
     * @param bindValues
     * @return
     * @link http://blog.csdn.net/a258831020/article/details/46820855
     */
    public static String replaceBindValues(String sqlContent, String bindValues) {
        if (!StringUtil.isEmpty(bindValues)) {
            //替换用户选择的值
            String[] ss = bindValues.split(",");
            sqlContent = MessageFormat.format(sqlContent, ss);
        }
        return sqlContent;
    }

    public static String replaceBindValues2(String sqlContent, String bindValues) {
        if (!StringUtil.isEmpty(bindValues)) {
            //替换用户选择的值
            String[] ss = bindValues.split(",");
            for (int i = 0; i < ss.length; i++) {
                sqlContent = sqlContent.replace("{" + i + "}", ss[i]);
            }
        }
        return sqlContent;
    }
}
