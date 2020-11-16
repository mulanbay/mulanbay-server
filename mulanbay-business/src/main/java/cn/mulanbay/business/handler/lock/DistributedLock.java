package cn.mulanbay.business.handler.lock;

/**
 * ${DESCRIPTION}
 * 分布式锁定义
 * 参考：{@link https://my.oschina.net/dengfuwei/blog/1600681}
 * @author fenghong
 * @create 2018-01-03 9:24
 **/
public interface DistributedLock {

    /**
     * 默认超时时间(毫秒)
     */
    public static final long TIMEOUT_MILLIS = 30000;

    public static final int RETRY_TIMES = Integer.MAX_VALUE;

    public static final long SLEEP_MILLIS = 500;

    public boolean lock(String key);

    public boolean lock(String key, int retryTimes);

    public boolean lock(String key, int retryTimes, long sleepMillis);

    public boolean lock(String key, long expire);

    public boolean lock(String key, long expire, int retryTimes);

    public boolean lock(String key, long expire, int retryTimes, long sleepMillis);

    public boolean releaseLock(String key);

}
