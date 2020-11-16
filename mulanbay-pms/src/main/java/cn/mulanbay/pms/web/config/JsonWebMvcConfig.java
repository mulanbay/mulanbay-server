package cn.mulanbay.pms.web.config;

import cn.mulanbay.pms.web.interceptor.RequestInterceptor;
import cn.mulanbay.web.bind.CustomMappingJackson2HttpMessageConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * Json相关处理配置
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
@Configuration
public class JsonWebMvcConfig extends WebMvcConfigurationSupport {

    private static final Logger logger = LoggerFactory.getLogger(JsonWebMvcConfig.class);

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    RequestInterceptor requestInterceptor;

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        logger.debug("加入Json 转换器：configureMessageConverters");
        CustomMappingJackson2HttpMessageConverter messageConverter = new CustomMappingJackson2HttpMessageConverter();
        messageConverter.setObjectMapper(objectMapper);
        converters.add(messageConverter);
        super.addDefaultHttpMessageConverters(converters);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.debug("加载拦截器");
        registry.addInterceptor(requestInterceptor).addPathPatterns("/**");
    }


}
