package cn.mulanbay.common.aop;

import java.util.Date;

/**
 * 得到某个事件的发生事件
 * 很多功能记录其实记录延后，并非当时的发生事件
 * 比如：吃药是在11：30吃的，但是到了晚上才会有时间记录，这时需要返回的是11：30的时间，而并非当前的时间
 *
 * 实现过程参考
 * @see cn.mulanbay.pms.web.aop.ControllerHandler
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public interface BindOccurTime {

    /**
     * 发生时间
     *
     * @return
     */
    public Date getOccurTime();

}
