package cn.mulanbay.common.aop;

import java.util.Date;

/**
 * 在系统中，经常是页面传入的是结束时间只能精确到天，而后端查询需要精确到23：59：59
 * 定义这个接口通过aop来统一注入，结束日期自动添加23：59：59
 *
 * 实现过程参考
 * @see cn.mulanbay.pms.web.aop.ControllerHandler
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public interface FullEndDateTime {

    Date getEndDate();

    void setEndDate(Date endDate);
}
