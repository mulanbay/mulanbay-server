package cn.mulanbay.business;


import cn.mulanbay.common.exception.ErrorCode;

/**
 * 公共业务类错误代码
 *
 * @author fenghong
 * @create 2017-10-19 21:50
 **/
public class BusinessErrorCode extends ErrorCode {

    //分布式锁琐失败
    public final static int BUSINESS_LOCK_ERROR = 20101;

    //分布式锁执行异常
    public final static int BUSINESS_LOCK_EXECUTE_PROCEED_ERROR = 20102;

    //处理类未找到
    public final static int HANDLER_NOT_FOUND = 20103;

    //处理类的命令未找到
    public final static int HANDLER_CMD_NOT_FOUND = 20104;

    //该处理类不支持命令
    public final static int HANDLER_CMD_NOT_SUPPORT = 20105;

    //未找到相关类名
    public static final int CLASS_NAME_NOT_FOUND= 20106;

}
