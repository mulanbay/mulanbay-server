package cn.mulanbay.persistent.cache;

/**
 * @Description: 缓存处理
 * @Author: fenghong
 * @Create : 2020/10/9 22:22
 */
public interface CacheProcessor {

    /**
     * 设置缓存
     * @param key
     * @param value
     * @param expireSeconds
     */
    public void set(String key, Object value, int expireSeconds);

    /**
     * 获取缓存
     * @param key
     * @param cls
     * @param <T>
     * @return
     */
    public <T> T get(String key, Class<T> cls);

    /**
     * 删除缓存
     * @param keyPattern
     * @return
     */
    public boolean delete(String keyPattern);


}
