package cn.mulanbay.pms.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;

import java.time.Duration;

/**
 * 缓存配置
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
@Configuration
public class CacheConfig {

    private static final Logger logger = LoggerFactory.getLogger(CacheConfig.class);

    @Value("${redis.server.ip}")
    String hostName;

    @Value("${redis.server.port}")
    int port;

    @Value("${redis.server.password}")
    String password;

    @Value("${redis.server.db}")
    int db;

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        logger.debug("初始化cacheManager");
        //初始化一个RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        //设置CacheManager的值序列化方式为json序列化
        RedisSerializer<Object> jsonSerializer = new GenericJackson2JsonRedisSerializer();
        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair
                .fromSerializer(jsonSerializer);
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(pair);
        //设置默认超过期时间是30秒
        defaultCacheConfig.entryTtl(Duration.ofSeconds(30));
        //初始化RedisCacheManager
        return new RedisCacheManager(redisCacheWriter, defaultCacheConfig);
    }

    /**
     * redis
     *
     * @return
     */
    @Primary
    @Bean
    public RedisHttpSessionConfiguration redisHttpSessionConfiguration() {
        RedisHttpSessionConfiguration rhc = new RedisHttpSessionConfiguration();
        rhc.setMaxInactiveIntervalInSeconds(600);
        return rhc;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(hostName);
        config.setPort(port);
        if (password != null && !password.equals("")) {
            config.setPassword(password);
        }
        config.setDatabase(db);
        JedisConnectionFactory jcf = new JedisConnectionFactory(config);
        return jcf;
    }

    @Bean
    public StringRedisSerializer stringRedisSerializer() {
        return new StringRedisSerializer();
    }

    @Bean
    public RedisTemplate redisTemplate(JedisConnectionFactory jedisConnectionFactory, StringRedisSerializer stringRedisSerializer) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        return redisTemplate;
    }
}
