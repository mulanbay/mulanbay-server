package cn.mulanbay.pms.web.config;

import cn.mulanbay.business.handler.HandlerManager;
import cn.mulanbay.business.handler.MessageHandler;
import cn.mulanbay.business.handler.lock.RedisDistributedLock;
import cn.mulanbay.business.processor.ConfigPropertiesProcessor;
import cn.mulanbay.schedule.impl.ScheduleLockHandler;
import cn.mulanbay.web.conveter.StringToDateConverter;
import cn.mulanbay.web.conveter.StringToStringConverter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * SpringMVC相关配置
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
@Configuration
public class WebConfig {

    private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);

    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    /**
     * 资源文件管理，支持国际化
     *
     * @return
     */
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("utf-8");
        messageSource.setCacheSeconds(120);
        return messageSource;
    }

    /**
     * 配置文件处理
     * @return
     */
    @Bean
    public ConfigPropertiesProcessor configPropertiesProcessor() {
        ConfigPropertiesProcessor pp = new ConfigPropertiesProcessor();
        pp.setIgnoreUnresolvablePlaceholders(true);
        pp.setIgnoreResourceNotFound(true);
        //todo
        pp.setLocations();
        pp.setFileEncoding("utf-8");
        return pp;
    }

    /**
     * 资源文件配置的处理
     * @return
     */
    @Bean
    public MessageHandler messageHandler() {
        return new MessageHandler();
    }

    /**
     * 基于Redis的分布式锁
     * @param redisTemplate
     * @return
     */
    @Bean
    public RedisDistributedLock redisDistributedLock(RedisTemplate redisTemplate) {
        return new RedisDistributedLock(redisTemplate);
    }

    /**
     * 调度器分布式锁
     * @return
     */
    @Bean
    public ScheduleLockHandler scheduleLockHandler() {
        return new ScheduleLockHandler();
    }

    /**
     * 木兰湾处理器管理类
     * @return
     */
    @Bean
    public HandlerManager handlerManager() {
        return new HandlerManager();
    }

    /**
     * json处理类
     * @param builder
     * @return
     */
    @Primary
    @Bean
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        logger.debug("初始化objectMapper");
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        //是否支持映射对象没有对应的字段
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        //解决特殊字符：Illegal unquoted character
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        //取消timestamps形式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //配置该objectMapper在反序列化时，忽略目标对象没有的属性。凡是使用该objectMapper反序列化时，都会拥有该特性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 设置null值不参与序列化(字段不被显示)NON_EMPTY会导致空的list也不会被序列化
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setDateFormat(simpleDateFormat);
        return objectMapper;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        //设置日期格式
        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
        //设置中文编码格式
        List<MediaType> list = new ArrayList<MediaType>();
        list.add(MediaType.APPLICATION_JSON);
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(list);
        return mappingJackson2HttpMessageConverter;
    }

    @Bean
    public ConversionServiceFactoryBean conversionService() {
        ConversionServiceFactoryBean cs = new ConversionServiceFactoryBean();
        Set<Converter> converters = new HashSet<>();
        converters.add(new StringToDateConverter());
        converters.add(new StringToStringConverter());
        cs.setConverters(converters);
        return cs;
    }
}
