package cn.mulanbay.pms.handler;

import cn.mulanbay.business.handler.BaseHandler;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.cache.CacheProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 缓存实现
 * 目前由redis方案
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Component
public class CacheHandler extends BaseHandler implements CacheProcessor {

    public static final int DEFAULT_EXPIRE_SECONDS = 300;

    public static final int NO_EXPIRE = 0;

    @Value("${system.namespace}")
    String namespace;

    @Autowired
    private RedisTemplate redisTemplate;

    public CacheHandler() {
        super("缓存处理");
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void reload() {
        super.reload();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public Boolean selfCheck() {
        this.set(getFullKey("test"), "testData", 10);
        String value = this.getForString(getFullKey("test"));
        return "testData".equals(value);
    }

    /**
     * @param key
     * @param value         需要实现序列化接口
     * @param expireSeconds
     */
    public void addToList(String key, Object value, int expireSeconds) {
        if (expireSeconds == -1) {
            expireSeconds = DEFAULT_EXPIRE_SECONDS;
        }
        redisTemplate.opsForList().leftPush(this.getFullKey(key), value);
        if (expireSeconds > NO_EXPIRE) {
            redisTemplate.expire(getFullKey(key), expireSeconds, TimeUnit.SECONDS);
        }
    }

    /**
     * 获取list
     *
     * @param key
     */
    public List getList(String key) {
        return redisTemplate.opsForList().range(getFullKey(key), 0, -1);
    }

    /**
     * @param key
     * @param value         需要实现序列化接口
     * @param expireSeconds
     */
    @Override
    public void set(String key, Object value, int expireSeconds) {
        if (expireSeconds == -1) {
            expireSeconds = DEFAULT_EXPIRE_SECONDS;
        }
        if (expireSeconds == NO_EXPIRE) {
            redisTemplate.opsForValue().set(getFullKey(key), value);
        } else {
            redisTemplate.opsForValue().set(getFullKey(key), value, expireSeconds, TimeUnit.SECONDS);
        }
    }

    /**
     * HashMap模式
     *
     * @param key
     * @param value         需要实现序列化接口
     * @param expireSeconds
     */
    public void setHash(String key, Map value, int expireSeconds) {
        if (expireSeconds == -1) {
            expireSeconds = DEFAULT_EXPIRE_SECONDS;
        }
        redisTemplate.opsForHash().putAll(getFullKey(key), value);
        if (expireSeconds > NO_EXPIRE) {
            redisTemplate.expire(getFullKey(key), expireSeconds, TimeUnit.SECONDS);
        }
    }

    public <T> T getHash(String key, String mapKey, Class<T> cls) {
        return (T) redisTemplate.opsForHash().get(getFullKey(key), mapKey);
    }

    /**
     * 获取缓存
     *
     * @param key
     * @param cls
     * @param <T>
     * @return
     */
    @Override
    public <T> T get(String key, Class<T> cls) {
        return (T) redisTemplate.opsForValue().get(getFullKey(key));
    }

    /**
     * 获取缓存
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(getFullKey(key));
    }

    /**
     * 获取缓存
     *
     * @param key
     * @return
     */
    public String getForString(String key) {
        return (String) redisTemplate.opsForValue().get(getFullKey(key));
    }

    /**
     * 删除缓存
     *
     * @param key
     */
    @Override
    public boolean delete(String key) {
        return redisTemplate.delete(getFullKey(key));
    }

    /**
     * 批量删除key
     *
     * @param pattern:abc:*
     */
    public Long deleteByPattern(String pattern) {
        Set<String> keys = redisTemplate.keys(getFullKey(pattern));
        if (StringUtil.isNotEmpty(keys)) {
            return redisTemplate.delete(keys);
        }
        return 0L;
    }

    /**
     * 增加
     *
     * @param key
     * @param n
     * @return
     */
    public long incre(String key, long n) {
        return redisTemplate.opsForValue().increment(getFullKey(key), n);
    }

    /**
     * 获取key的全路径
     *
     * @param key
     * @return
     */
    private String getFullKey(String key) {
        return namespace + ":" + key;
    }

}
