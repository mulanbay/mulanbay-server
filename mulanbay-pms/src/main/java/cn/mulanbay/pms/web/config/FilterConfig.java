package cn.mulanbay.pms.web.config;

import cn.mulanbay.web.filter.MultipleRequestFilter;
import cn.mulanbay.web.filter.XssFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import java.util.HashMap;
import java.util.Map;

/**
 * 过滤器配置
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
@Configuration
public class FilterConfig
{
    @Value("${xss.enabled}")
    private String enabled;

    @Value("${xss.excludes}")
    private String excludes;

    @Value("${xss.urlPatterns}")
    private String urlPatterns;

    /**
     * xss 攻击预防
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean xssFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns(urlPatterns.split(","));
        registration.setName("xssFilter");
        registration.setOrder(1);
        Map<String, String> initParameters = new HashMap<String, String>();
        initParameters.put("excludes", excludes);
        initParameters.put("enabled", enabled);
        registration.setInitParameters(initParameters);
        return registration;
    }

    /**
     * 支持重复读
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean multipleRequestFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new MultipleRequestFilter());
        registration.addUrlPatterns("/*");
        registration.setName("multipleRequestFilter");
        registration.setOrder(2);
        return registration;
    }

}
