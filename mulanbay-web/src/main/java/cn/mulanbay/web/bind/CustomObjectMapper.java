package cn.mulanbay.web.bind;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;

/**
 * json序列化个性化配置
 * 复写默认SpringMVC中json序列化
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class CustomObjectMapper extends ObjectMapper {

	private static final Logger logger = LoggerFactory.getLogger(CustomObjectMapper.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 3188159062390016860L;

	static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public CustomObjectMapper() {
		super();
		logger.debug("初始化自定义ObjectMapper");
		//是否支持映射对象没有对应的字段
		this.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		//解决特殊字符：Illegal unquoted character
		this.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true) ;
		//取消timestamps形式
		this.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		//配置该objectMapper在反序列化时，忽略目标对象没有的属性。凡是使用该objectMapper反序列化时，都会拥有该特性
		this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
		// 设置null值不参与序列化(字段不被显示)
		this.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		this.setDateFormat(simpleDateFormat);
	}
}