package cn.mulanbay.pms.web.bean.request;

import cn.mulanbay.pms.persistent.enums.DateGroupType;

import java.util.Date;

/**
 * 时间统计类的基类
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public interface DateStatSearch {

    public Date getStartDate();

    public Date getEndDate();

    public DateGroupType getDateGroupType();

    public Boolean isCompliteDate();

}
