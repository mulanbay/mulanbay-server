package cn.mulanbay.web.bind;

import cn.mulanbay.common.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;

/**
 * controller类返回结果处理类（Unicode转换）
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class CustomMappingJackson2HttpMessageConverter extends
		MappingJackson2HttpMessageConverter {

	private static final Logger logger = LoggerFactory.getLogger(CustomMappingJackson2HttpMessageConverter.class);

	ObjectMapper objectMapper;

	@Override
	protected Object readInternal(Class<? extends Object> clazz,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * String unicode转换
	 * Unicode转换
	 * @return
	 */
	@Override
    public ObjectMapper getObjectMapper() {
		if (objectMapper == null) {
			logger.debug("JsonUtil.createObjectMapper()");
			objectMapper = JsonUtil.createObjectMapper();
		}
		return objectMapper;
	}

	/**
	 *
	 * @param object
	 *            一般为ResultBean
	 * @param outputMessage
	 * @throws IOException
	 * @throws HttpMessageNotWritableException
	 */
	@Override
	protected void writeInternal(Object object, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		this.setObjectMapper(getObjectMapper());
		super.writeInternal(object, outputMessage);
	}

}
