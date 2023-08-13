package cn.mulanbay.pms.web.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 解决有时时间返回变成时间差的问题
 *
 * @author fenghong
 * @create 2023-08-13
 */
@Configuration
public class DateFormatConfig  implements WebMvcConfigurer {

    /**
     * 使用此方法, 以下 spring-boot: jackson时间格式化 配置 将会失效
     * spring.jackson.time-zone=GMT+8
     * spring.jackson.date-format=yyyy-MM-dd HH:mm:ss
     * 原因: 会覆盖 @EnableAutoConfiguration 关于 WebMvcAutoConfiguration 的配置
     * */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = converter.getObjectMapper();
        // 时间格式化
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // 设置格式化内容
        converter.setObjectMapper(objectMapper);
        converters.add(0, converter);
    }

}
