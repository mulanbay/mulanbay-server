package cn.mulanbay.common.exception;

/**
 * 消息通知
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public interface MessageNotify {

    /**
     *
     * @param code 错误代码
     * @param title
     * @param content
     */
    public void notifyMsg(int code,String title,String content);
}
