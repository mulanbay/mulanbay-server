package cn.mulanbay.schedule;

import cn.mulanbay.common.exception.ErrorCode;

/**
 * 调度模块错误代码
 *
 * @author fenghong
 * @create 2017-10-19 21:50
 **/
public class ScheduleErrorCode extends ErrorCode {

    /**
     * 创建触发器异常
     */
    public final static int TRIGGER_CREATE_ERROR=30001;

    /**
     * 触发器参数为空
     */
    public final static int TRIGGER_PARA_NULL =30002;

    /**
     * 调度器参数长度不正确
     */
    public final static int TRIGGER_PARA_LENGTH_ERROR=30003;

    /**
     * 调度器参数个数不正确
     */
    public final static int TRIGGER_PARA_FORMAT_ERROR=30004;

    /**
     * 获取有效调度器失败
     */
    public final static int TRIGGER_GET_ACTIVE_LIST_ERROR=30005;

    /**
     * 检查调度日志异常
     */
    public final static int TRIGGER_LOG_CHECK_ERROR=30006;

    /**
     * 获取自动重做的调度日志
     */
    public final static int TRIGGER_GET_AUTO_REDO_LOG_ERROR=30007;

    public final static int TRIGGER_CANNOT_REDO=30008;

    public final static int SCHEDULE_NOT_ENABLED=30009;

    public final static int SCHEDULE_ALREADY_EXECED=30010;

    /**
     * 参数定义构建异常
     */
    public final static int JOB_PARA_BUILD_ERROR=30011;

    /**
     * 参数定义构建异常
     */
    public final static int JOB_PARA_PARSE_ERROR=30012;

    /**
     * 分布式锁无法获取
     */
    public final static int DISTRIBUTE_LOCK_NOT_FOUND=30013;

    /**
     * 调度器执行参数检查异常
     */
    public final static int TRIGGER_EXEC_PARA_CHECK_ERROR=30014;

    /**
     * 调度执行异常
     */
    public final static int TRIGGER_EXEC_ERROR=30015;

    /**
     * 刷新调度异常
     */
    public final static int REFRESH_TRIGGER_FAIL=30016;

    /**
     * 该调度无法在本服务器上运行
     */
    public final static int TRIGGER_CANNOT_RUN_HERE=30017;

    /**
     * 更新调度服务器异常
     */
    public final static int UPDATE_TASK_SERVER_ERROR=30018;

}
