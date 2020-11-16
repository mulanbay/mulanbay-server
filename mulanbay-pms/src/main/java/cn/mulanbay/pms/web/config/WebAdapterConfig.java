package cn.mulanbay.pms.web.config;

import cn.mulanbay.web.conveter.StringToDateConverter;
import cn.mulanbay.web.conveter.StringToStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;

/**
 * SpringMVC适配器配置
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
@Configuration
public class WebAdapterConfig extends WebMvcConfigurationSupport {

    private static final Logger logger = LoggerFactory.getLogger(WebAdapterConfig.class);

    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    @Autowired
    ConversionService conversionService;

    /**
     * String 和 Date之间的转换
     */
    @PostConstruct
    public void init() {
        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) handlerAdapter.getWebBindingInitializer();
        initializer.setConversionService(conversionService);
        this.mvcConversionService().addConverter(new StringToDateConverter());
        this.mvcConversionService().addConverter(new StringToStringConverter());
        logger.debug("WebAdapterConfig加载ConversionService");
    }

}
