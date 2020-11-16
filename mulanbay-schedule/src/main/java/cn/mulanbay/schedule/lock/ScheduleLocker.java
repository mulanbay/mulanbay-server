package cn.mulanbay.schedule.lock;

/**
 * ${DESCRIPTION}
 * 调度锁
 * @author fenghong
 * @create 2017-11-11 12:25
 **/
public interface ScheduleLocker {

    LockStatus lock(String identityKey, long expiredSeconds);

    LockStatus unlock(String identityKey);

}
